package gregtech.integration.jei.multiblock;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import gregtech.api.render.scene.SceneRenderCallback;
import gregtech.api.render.scene.WorldSceneRenderer;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ItemStackKey;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.gui.recipes.RecipeLayout;
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

    private final MultiblockInfoPage infoPage;
    private WorldSceneRenderer[] sceneRenders;
    private Map<GuiButton, Runnable> buttons = new HashMap<>();
    private RecipeLayout recipeLayout;
    private List<ItemStack> allItemStackInputs = new ArrayList<>();
    private ItemStack controllerStack;

    private int layerIndex = -1;
    private int currentRendererPage = 0;
    private int lastMouseX;
    private int lastMouseY;
    private float rotationYaw;
    private float rotationPitch;

    private GuiButton buttonPreviousPattern;
    private GuiButton buttonNextPattern;
    private GuiButton nextLayerButton;

    private ItemStack tooltipBlockStack;

    public MultiblockInfoRecipeWrapper(MultiblockInfoPage infoPage) {
        this.infoPage = infoPage;
        this.controllerStack = infoPage.getController().getStackForm();
        HashSet<ItemStackKey> drops = new HashSet<>();
        drops.add(new ItemStackKey(controllerStack));
        this.sceneRenders = infoPage.getMatchingShapes().stream()
            .map(it -> initializeSceneRenderer(it, drops))
            .toArray(WorldSceneRenderer[]::new);
        drops.forEach(it -> allItemStackInputs.add(it.getItemStack()));
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, allItemStackInputs);
        ingredients.setOutput(ItemStack.class, controllerStack);
    }

    public void setRecipeLayout(RecipeLayout layout) {
        this.recipeLayout = layout;
        IDrawable border = layout.getRecipeCategory().getBackground();
        this.buttons.clear();
        this.nextLayerButton = new GuiButton(0, border.getWidth() - 30, 70, 20, 20, "");
        this.buttonPreviousPattern = new GuiButton(0, 10, border.getHeight() - 30, 20, 20, "<");
        this.buttonNextPattern = new GuiButton(0, border.getWidth() - 30, border.getHeight() - 30, 20, 20, ">");
        this.buttons.put(nextLayerButton, this::toggleNextLayer);
        this.buttons.put(buttonPreviousPattern, () -> switchRenderPage(-1));
        this.buttons.put(buttonNextPattern, () -> switchRenderPage(1));
        boolean isPagesDisabled = sceneRenders.length == 1;
        this.buttonPreviousPattern.visible = !isPagesDisabled;
        this.buttonNextPattern.visible = !isPagesDisabled;
        this.buttonPreviousPattern.enabled = false;
        this.buttonNextPattern.enabled = sceneRenders.length > 1;
        this.rotationYaw = -45.0f;
        this.rotationPitch = 0.0f;
        this.currentRendererPage = 0;
        setNextLayer(-1);
    }

    public WorldSceneRenderer getCurrentRenderer() {
        return sceneRenders[currentRendererPage];
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
        int maxIndex = sceneRenders.length - 1;
        int newIndex = Math.max(0, Math.min(currentRendererPage + amount, maxIndex));
        if (currentRendererPage != newIndex) {
            this.currentRendererPage = newIndex;
            this.buttonNextPattern.enabled = newIndex < maxIndex;
            this.buttonPreviousPattern.enabled = newIndex > 0;
        }
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
        int sceneHeight = recipeWidth;
        renderer.render(recipeLayout.getPosX(), recipeLayout.getPosY() + scenePosY, recipeWidth, sceneHeight, 0xC6C6C6);
        drawText(minecraft, recipeWidth);
        for (GuiButton button : buttons.keySet()) {
            button.drawButton(minecraft, mouseX, mouseY, 0.0f);
        }

        this.tooltipBlockStack = null;
        BlockPos pos = renderer.getLastHitBlock();
        boolean leftClickHeldAndInsideView = Mouse.isButtonDown(0) &&
            mouseX >= 0 && mouseY >= scenePosY &&
            mouseX < recipeWidth && mouseY < (scenePosY + sceneHeight);
        boolean isHoldingShift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

        if (!leftClickHeldAndInsideView && pos != null && !renderer.world.isAirBlock(pos)) {
            IBlockState blockState = renderer.world.getBlockState(pos);
            RayTraceResult result = new CuboidRayTraceResult(new Vector3(0.5, 0.5, 0.5).add(pos), pos, EnumFacing.UP, new IndexedCuboid6(null, Cuboid6.full), 1.0);
            ItemStack itemStack = blockState.getBlock().getPickBlock(blockState, result, renderer.world, pos, minecraft.player);
            if (itemStack != null && !itemStack.isEmpty()) {
                this.tooltipBlockStack = itemStack;
            }
        }

        if (leftClickHeldAndInsideView) {
            if (isHoldingShift) {
                int mouseDeltaY = mouseY - lastMouseY;
                this.rotationPitch += mouseDeltaY * 2.0f;
            } else {
                int mouseDeltaX = mouseX - lastMouseX;
                this.rotationYaw += mouseDeltaX * 2.0f;
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

    private static void gatherBlockDrops(World world, Collection<BlockPos> positions, Set<ItemStackKey> drops) {
        NonNullList<ItemStack> dropsList = NonNullList.create();
        for (BlockPos pos : positions) {
            IBlockState blockState = world.getBlockState(pos);
            blockState.getBlock().getDrops(dropsList, world, pos, blockState, 0);
        }
        for (ItemStack itemStack : dropsList) {
            drops.add(new ItemStackKey(itemStack));
        }
    }

    private WorldSceneRenderer initializeSceneRenderer(MultiblockShapeInfo shapeInfo, Set<ItemStackKey> blockDrops) {
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
        gatherBlockDrops(worldSceneRenderer.world, blockMap.keySet(), blockDrops);
        worldSceneRenderer.setRenderCallback(this);
        worldSceneRenderer.setRenderFilter(this::shouldDisplayBlock);
        return worldSceneRenderer;
    }

}
