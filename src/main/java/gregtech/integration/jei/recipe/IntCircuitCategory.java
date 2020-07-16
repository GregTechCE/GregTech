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
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
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
            new ScaledRenderer<>(FIRST_SLOT_SCALE, GTJeiPlugin.ingredientRegistry.getIngredientRenderer(VanillaTypes.ITEM)));

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
                positions[i][0],
                positions[i][1]
            );
            stacks.setBackground(i + 1, slotBase);
        }
        stacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
    }
}
