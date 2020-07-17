package gregtech.integration.jei.recipe;

import com.google.common.base.Suppliers;
import gregtech.api.GTValues;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.common.items.MetaItems;
import gregtech.integration.jei.GTJeiPlugin;
import gregtech.integration.jei.utils.render.ScaledDrawable;
import gregtech.integration.jei.utils.render.ScaledRenderer;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IntCircuitCategory implements IRecipeCategory<IntCircuitRecipeWrapper> {

    public static final String UID = GTValues.MODID + ":" + MetaItems.INTEGRATED_CIRCUIT.unlocalizedName;
    private static final int SLOT_SIZE = 18;
    private static final int ROW_LENGTH = 6;
    private static final int FIRST_SLOT_SCALE = 2;
    private final IDrawableStatic slotBase;
    private final ScaledDrawable scaledSlot;

    private final IDrawable iconDrawable;
    private final IDrawable backgroundDrawable;

    private final Supplier<IIngredientRenderer<ItemStack>> firstItemRenderer =
        Suppliers.memoize(() ->
            new ScaledRenderer<>(FIRST_SLOT_SCALE,
                new SlicedItemRenderer(GTJeiPlugin.ingredientRegistry.getIngredientRenderer(VanillaTypes.ITEM))));

    private final Supplier<IIngredientRenderer<ItemStack>> otherItemRenderer =
        Suppliers.memoize(() ->
            new SlicedItemRenderer(GTJeiPlugin.ingredientRegistry.getIngredientRenderer(VanillaTypes.ITEM)));

    public IntCircuitCategory(IGuiHelper guiHelper) {
        iconDrawable = guiHelper.createDrawableIngredient(MetaItems.INTEGRATED_CIRCUIT.getStackForm());
        int width = 108; // SLOT_SIZE * ROW_LENGTH
        int height = 108; // ((IntCircuitIngredient.CIRCUIT_MAX + 4) / ROW_LENGTH) * SLOT_SIZE;
        backgroundDrawable = guiHelper.createBlankDrawable(width, height);
        slotBase = guiHelper.drawableBuilder(new ResourceLocation(GTValues.MODID, "textures/gui/base/slot.png"), 0, 0, SLOT_SIZE, SLOT_SIZE)
            .setTextureSize(SLOT_SIZE, SLOT_SIZE)
            .build();
        scaledSlot = new ScaledDrawable(FIRST_SLOT_SCALE, slotBase);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("metaitem.circuit.integrated.gui");
    }

    @Override
    public String getModName() {
        return GTValues.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return backgroundDrawable;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return iconDrawable;
    }

    private static final int shortenedRowLength = ROW_LENGTH - FIRST_SLOT_SCALE;
    private static final int[][] positions =
        Stream.concat(
            IntStream.range(0, shortenedRowLength * FIRST_SLOT_SCALE)
                .mapToObj(i -> new int[]{
                    SLOT_SIZE * FIRST_SLOT_SCALE + SLOT_SIZE * (i % shortenedRowLength),
                    SLOT_SIZE * (i / shortenedRowLength)
                }),
            IntStream.range(0, IntCircuitIngredient.CIRCUIT_MAX - (shortenedRowLength * FIRST_SLOT_SCALE))
                .mapToObj(i -> new int[]{
                    SLOT_SIZE * (i % ROW_LENGTH),
                    SLOT_SIZE * FIRST_SLOT_SCALE + SLOT_SIZE * (i / ROW_LENGTH)
                })
        )
            .toArray(int[][]::new);

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IntCircuitRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0, recipeWrapper.input, firstItemRenderer.get(), 0, 0, SLOT_SIZE * FIRST_SLOT_SCALE, SLOT_SIZE * FIRST_SLOT_SCALE, FIRST_SLOT_SCALE, FIRST_SLOT_SCALE);
        stacks.setBackground(0, scaledSlot);
        for (int i = 0; i < positions.length; i++) {
            stacks.init(i + 1,
                recipeWrapper.input,
                otherItemRenderer.get(),
                positions[i][0],
                positions[i][1],
                SLOT_SIZE,
                SLOT_SIZE,
                1,
                1
            );
            stacks.setBackground(i + 1, slotBase);
        }
        stacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }

    private static class SlicedItemRenderer implements IIngredientRenderer<ItemStack> {
        public static final int ITEM_WIDTH = 16;
        private final IIngredientRenderer<ItemStack> delegate;

        public SlicedItemRenderer(IIngredientRenderer<ItemStack> delegate) {
            this.delegate = delegate;
        }

        @Override
        public List<String> getTooltip(Minecraft minecraft, ItemStack ingredient, ITooltipFlag tooltipFlag) {
            return delegate.getTooltip(minecraft, ingredient, tooltipFlag);
        }

        @Override
        public FontRenderer getFontRenderer(Minecraft minecraft, ItemStack ingredient) {
            return delegate.getFontRenderer(minecraft, ingredient);
        }

        @Override
        public void render(Minecraft minecraft, int xPosition, int yPosition, @Nullable ItemStack ingredient) {
            if (ingredient == null)
                return;

            Framebuffer fb = minecraft.getFramebuffer();
            if (!fb.isStencilEnabled()) {
                fb.enableStencil();
            }

            final int mask = 0xFF;
            final int val = 1;

            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GlStateManager.clear(GL11.GL_STENCIL_BUFFER_BIT);
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
            GL11.glStencilFunc(GL11.GL_ALWAYS, val, mask);
            GL11.glStencilMask(mask);

            GlStateManager.colorMask(false, false, false, false);
            GlStateManager.depthMask(false);

            Tessellator tes = Tessellator.getInstance();
            BufferBuilder buf = tes.getBuffer();
            buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION);
            buf.pos(xPosition + ITEM_WIDTH, yPosition, 0).endVertex();
            buf.pos(xPosition, yPosition + ITEM_WIDTH, 0).endVertex();
            buf.pos(xPosition + ITEM_WIDTH, yPosition + ITEM_WIDTH, 0).endVertex();
            tes.draw();

            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.depthMask(true);

            GL11.glStencilFunc(GL11.GL_EQUAL, val, mask);

            delegate.render(minecraft, xPosition, yPosition, ingredient);
            GL11.glDisable(GL11.GL_STENCIL_TEST);
        }
    }
}
