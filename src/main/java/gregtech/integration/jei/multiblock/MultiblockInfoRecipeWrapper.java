package gregtech.integration.jei.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import gregtech.api.gui.GuiTextures;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.render.scene.SceneRenderCallback;
import gregtech.api.render.scene.WorldSceneRenderer;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ItemStackKey;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.recipes.RecipeLayout;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.vecmath.Vector3f;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MultiblockInfoRecipeWrapper implements IRecipeWrapper, SceneRenderCallback {
    private static final int MAX_PARTS = 20;

    private static class MBPattern {
        final WorldSceneRenderer sceneRenderer;
        final List<ItemStack> parts;
        public MBPattern(final WorldSceneRenderer sceneRenderer, final List<ItemStack> parts) {
            this.sceneRenderer = sceneRenderer;
            this.parts = parts;
        }
    }

    private final MultiblockInfoPage infoPage;
    private MBPattern[] patterns;
    private Map<GuiButton, Runnable> buttons = new HashMap<>();
    private RecipeLayout recipeLayout;
    private List<ItemStack> allItemStackInputs = new ArrayList<>();
    private ItemStack controllerStack;

    private int layerIndex = -1;
    private int currentRendererPage = 0;
    private int lastMouseX;
    private int lastMouseY;
    private float panX;
    private float panY;
    private float rotationYaw;
    private float rotationPitch;
    private float zoom;

    private GuiButton buttonPreviousPattern;
    private GuiButton buttonNextPattern;
    private GuiButton nextLayerButton;
    private IDrawable slot;

    private ItemStack tooltipBlockStack;

    public MultiblockInfoRecipeWrapper(MultiblockInfoPage infoPage) {
        this.infoPage = infoPage;
        this.controllerStack = infoPage.getController().getStackForm();
        HashSet<ItemStackKey> drops = new HashSet<>();
        drops.add(new ItemStackKey(controllerStack));
        this.patterns = infoPage.getMatchingShapes().stream()
            .map(it -> initializePattern(it, drops))
            .toArray(MBPattern[]::new);
        drops.forEach(it -> allItemStackInputs.add(it.getItemStack()));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, allItemStackInputs);
        ingredients.setOutput(ItemStack.class, controllerStack);
    }

    public void setRecipeLayout(RecipeLayout layout, IGuiHelper guiHelper) {
        this.recipeLayout = layout;
        IDrawable border = layout.getRecipeCategory().getBackground();
        this.buttons.clear();
        this.nextLayerButton = new GuiButton(0, border.getWidth() - 25, 70, 20, 20, "");
        this.buttonPreviousPattern = new GuiButton(0, border.getWidth()-46, 90, 20, 20, "<");
        this.buttonNextPattern = new GuiButton(0, border.getWidth() - 25, 90, 20, 20, ">");
        this.buttons.put(nextLayerButton, this::toggleNextLayer);
        this.buttons.put(buttonPreviousPattern, () -> switchRenderPage(-1));
        this.buttons.put(buttonNextPattern, () -> switchRenderPage(1));
        boolean isPagesDisabled = patterns.length == 1;
        this.buttonPreviousPattern.visible = !isPagesDisabled;
        this.buttonNextPattern.visible = !isPagesDisabled;
        this.buttonPreviousPattern.enabled = false;
        this.buttonNextPattern.enabled = patterns.length > 1;
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        this.slot = guiHelper.createDrawable(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18, 18, 18);
        for (int i = 0; i < MAX_PARTS; ++i)
           itemStackGroup.init(i, true, 18*i-180*(i/10), border.getHeight()-36+18*(i/10));
        this.panX = 0.0f;
        this.panY = 0.0f;
        this.zoom = 1.0f;
        this.rotationYaw = -45.0f;
        this.rotationPitch = 0.0f;
        this.currentRendererPage = 0;
        setNextLayer(-1);
        updateParts(); 
    }

    public WorldSceneRenderer getCurrentRenderer() {
        return patterns[currentRendererPage].sceneRenderer;
    }

    public int getLayerIndex() {
        return layerIndex;
    }

    private void toggleNextLayer() {
        WorldSceneRenderer renderer = getCurrentRenderer();
        int height = (int) renderer.getSize().getY() - 1;
        if (++this.layerIndex > height) {
            //if current layer index is more than max height, reset it
            //to display all layers
            this.layerIndex = -1;
        }
        setNextLayer(layerIndex);
    }

    private void setNextLayer(int newLayer) {
        this.layerIndex = newLayer;
        this.nextLayerButton.displayString = "L:" + (layerIndex == -1 ? "A" : Integer.toString(layerIndex + 1));
    }

    private void switchRenderPage(int amount) {
        int maxIndex = patterns.length - 1;
        int newIndex = Math.max(0, Math.min(currentRendererPage + amount, maxIndex));
        if (currentRendererPage != newIndex) {
            this.currentRendererPage = newIndex;
            this.buttonNextPattern.enabled = newIndex < maxIndex;
            this.buttonPreviousPattern.enabled = newIndex > 0;
            updateParts();
        }
    }

    private void updateParts() {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        List<ItemStack> parts = this.patterns[currentRendererPage].parts;
        int limit = Math.min(parts.size(), MAX_PARTS);
        for (int i = 0; i < limit; ++i)
            itemStackGroup.set(i, parts.get(i));
        for (int i = parts.size(); i < limit; ++i)
            itemStackGroup.set(i, (ItemStack) null);
    }

    private boolean shouldDisplayBlock(BlockPos pos) {
        if (getLayerIndex() == -1)
            return true;
        WorldSceneRenderer renderer = getCurrentRenderer();
        int minHeight = (int) renderer.world.getMinPos().getY();
        int relativeHeight = pos.getY() - minHeight;
        return relativeHeight == getLayerIndex();
    }

    @Override
    public void preRenderScene(WorldSceneRenderer renderer) {
        Vector3f size = renderer.getSize();
        Vector3f minPos = renderer.world.getMinPos();
        minPos = new Vector3f(minPos);
        minPos.add(new Vector3f(0.0f, -1.0f, 0.5f));

        GlStateManager.scale(zoom, zoom, zoom);
        GlStateManager.translate(panX, panY, 0);
        GlStateManager.translate(-minPos.x, -minPos.y, -minPos.z);
        Vector3 centerPosition = new Vector3(size.x / 2.0f, size.y / 2.0f, size.z / 2.0f);
        GlStateManager.translate(centerPosition.x, centerPosition.y, centerPosition.z);
        GlStateManager.scale(2.0, 2.0, 2.0);
        GlStateManager.translate(-centerPosition.x, -centerPosition.y, -centerPosition.z);
        GlStateManager.translate(minPos.x, minPos.y, minPos.z);

        GlStateManager.translate(centerPosition.x, centerPosition.y, centerPosition.z);
        GlStateManager.rotate(rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(rotationPitch, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-centerPosition.x, -centerPosition.y, -centerPosition.z);

        if (layerIndex >= 0) {
            GlStateManager.translate(0.0, -layerIndex + 1, 0.0);
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        WorldSceneRenderer renderer = getCurrentRenderer();
        int scenePosY = 0;
        //noinspection UnnecessaryLocalVariable,SuspiciousNameCombination
        int sceneHeight = recipeHeight-36;
        renderer.render(recipeLayout.getPosX(), recipeLayout.getPosY() + scenePosY, recipeWidth, sceneHeight, 0xC6C6C6);
        drawText(minecraft, recipeWidth);
        for (int i = 0; i < MAX_PARTS; ++i) {
            this.slot.draw(minecraft, 18*i-180*(i/10), recipeHeight-36+18*(i/10));
        }
        // Hmmm, the buttons need to be last otherwise sometimes highlighting 
        // the button by mousing over it, leaks into other gui elements?
        for (GuiButton button : buttons.keySet()) {
            button.drawButton(minecraft, mouseX, mouseY, 0.0f);
        }

        this.tooltipBlockStack = null;
        BlockPos pos = renderer.getLastHitBlock();
        boolean insideView = mouseX >= 0 && mouseY >= scenePosY &&
            mouseX < recipeWidth && mouseY < (scenePosY + sceneHeight);
        boolean leftClickHeld = Mouse.isButtonDown(0);
        boolean rightClickHeld = Mouse.isButtonDown(1);
        boolean isHoldingShift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        if (insideView) {
            if (leftClickHeld) {
                if (isHoldingShift) {
                    int mouseDeltaY = mouseY - lastMouseY;
                    this.rotationPitch += mouseDeltaY * 2.0f;
                } else {
                    int mouseDeltaX = mouseX - lastMouseX;
                    this.rotationYaw += mouseDeltaX * 2.0f;
                }
            } else if (rightClickHeld) {
                int mouseDeltaY = mouseY - lastMouseY;
                if (isHoldingShift) {
                    this.zoom *= Math.pow(1.05d, -mouseDeltaY);
                } else {
                    int mouseDeltaX = mouseX - lastMouseX;
                    this.panX -= mouseDeltaX / 2.0f;
                    this.panY -= mouseDeltaY / 2.0f;
                }
            }
        }

        if (!(leftClickHeld || rightClickHeld) && pos != null && !renderer.world.isAirBlock(pos)) {
            IBlockState blockState = renderer.world.getBlockState(pos);
            RayTraceResult result = new CuboidRayTraceResult(new Vector3(0.5, 0.5, 0.5).add(pos), pos, EnumFacing.UP, new IndexedCuboid6(null, Cuboid6.full), 1.0);
            ItemStack itemStack = blockState.getBlock().getPickBlock(blockState, result, renderer.world, pos, minecraft.player);
            if (itemStack != null && !itemStack.isEmpty()) {
                this.tooltipBlockStack = itemStack;
            }
        }

        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }

    private void drawText(Minecraft minecraft, int recipeWidth) {
        String localizedName = I18n.format(infoPage.getController().getMetaFullName());
        GTUtility.drawCenteredSizedText(recipeWidth / 2, 0, localizedName, 0x333333, 1.3);
        FontRenderer fontRenderer = minecraft.fontRenderer;
        List<String> lines = Arrays.stream(infoPage.getDescription())
            .flatMap(s -> fontRenderer.listFormattedStringToWidth(s, recipeWidth).stream())
            .collect(Collectors.toList());
        for (int i = 0; i < lines.size(); i++) {
            String lineText = lines.get(i);
            int x = (recipeWidth - fontRenderer.getStringWidth(lineText)) / 2;
            int y = 8 + i * fontRenderer.FONT_HEIGHT;
            fontRenderer.drawString(lineText, x, y, 0x333333);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        for (Entry<GuiButton, Runnable> button : buttons.entrySet()) {
            if (button.getKey().mousePressed(minecraft, mouseX, mouseY)) {
                button.getValue().run();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        if (tooltipBlockStack != null && !tooltipBlockStack.isEmpty() && !Mouse.isButtonDown(0)) {
            Minecraft minecraft = Minecraft.getMinecraft();
            ITooltipFlag flag = minecraft.gameSettings.advancedItemTooltips ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL;
            List<String> tooltip = tooltipBlockStack.getTooltip(minecraft.player, flag);
            EnumRarity rarity = tooltipBlockStack.getRarity();
            for (int k = 0; k < tooltip.size(); ++k) {
                if (k == 0) {
                    tooltip.set(k, rarity.rarityColor + tooltip.get(k));
                } else {
                    tooltip.set(k, TextFormatting.GRAY + tooltip.get(k));
                }
            }
            return tooltip;
        }
        return Collections.emptyList();
    }

    private static class PartInfo {
        final ItemStackKey itemStackKey;
        boolean isController = false;
        boolean isTile = false;
        final int blockId;
        int amount = 0;

        PartInfo(final ItemStackKey itemStackKey, final BlockInfo blockInfo) {
            this.itemStackKey = itemStackKey;
            this.blockId = Block.getIdFromBlock(blockInfo.getBlockState().getBlock());
            TileEntity tileEntity = blockInfo.getTileEntity();
            if (tileEntity != null) {
                this.isTile = true;
                MetaTileEntity mte = ((MetaTileEntityHolder) tileEntity).getMetaTileEntity();
                if (mte instanceof MultiblockControllerBase)
                    this.isController = true;
            }
        }

        ItemStack getItemStack() {
            ItemStack result = this.itemStackKey.getItemStack();
            result.setCount(this.amount);
            return result;
        }
    }

    private static void gatherBlockDrops(World world, Map<BlockPos, BlockInfo> blocks, Set<ItemStackKey> drops, Map<ItemStackKey, PartInfo> partsMap) {
        NonNullList<ItemStack> dropsList = NonNullList.create();
        for (Entry<BlockPos, BlockInfo> entry : blocks.entrySet()) {
            BlockPos pos = entry.getKey();
            IBlockState blockState = world.getBlockState(pos);
            NonNullList<ItemStack> blockDrops = NonNullList.create();
            blockState.getBlock().getDrops(blockDrops, world, pos, blockState, 0);
            dropsList.addAll(blockDrops);

            for (ItemStack itemStack : blockDrops) {
                ItemStackKey itemStackKey = new ItemStackKey(itemStack);
                PartInfo partInfo = partsMap.get(itemStackKey);
                if (partInfo == null) {
                    partInfo = new PartInfo(itemStackKey, entry.getValue());
                    partsMap.put(itemStackKey, partInfo);
                }
                ++partInfo.amount;
            }
        }
        for (ItemStack itemStack : dropsList) {
            drops.add(new ItemStackKey(itemStack));
        }
    }


    private MBPattern initializePattern(MultiblockShapeInfo shapeInfo, Set<ItemStackKey> blockDrops) {
        Map<BlockPos, BlockInfo> blockMap = new HashMap<>();
        BlockInfo[][][] blocks = shapeInfo.getBlocks();
        for (int z = 0; z < blocks.length; z++) {
            BlockInfo[][] aisle = blocks[z];
            for (int y = 0; y < aisle.length; y++) {
                BlockInfo[] column = aisle[y];
                for (int x = 0; x < column.length; x++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockInfo blockInfo = column[x];
                    blockMap.put(blockPos, blockInfo);
                }
            }
        }
        WorldSceneRenderer worldSceneRenderer = new WorldSceneRenderer(blockMap);
        worldSceneRenderer.world.updateEntities();
        HashMap<ItemStackKey, PartInfo> partsMap = new HashMap<>();
        gatherBlockDrops(worldSceneRenderer.world, blockMap, blockDrops, partsMap);
        worldSceneRenderer.setRenderCallback(this);
        worldSceneRenderer.setRenderFilter(this::shouldDisplayBlock);
        ArrayList<PartInfo> partInfos = new ArrayList<>(partsMap.values());
        partInfos.sort((one, two) -> {
            if (one.isController) return -1;
            if (two.isController) return +1;
            if (one.isTile && !two.isTile) return -1;
            if (two.isTile && !one.isTile) return +1;
            if (one.blockId != two.blockId) return two.blockId - one.blockId;
            return two.amount - one.amount;
        });
        ArrayList<ItemStack> parts = new ArrayList<ItemStack>();
        for (PartInfo partInfo : partInfos) {
           parts.add(partInfo.getItemStack());
        }
        return new MBPattern(worldSceneRenderer, parts);
    }

}
