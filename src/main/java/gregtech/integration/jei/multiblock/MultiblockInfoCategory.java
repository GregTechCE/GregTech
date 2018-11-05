package gregtech.integration.jei.multiblock;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.infos.*;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.gui.recipes.RecipeLayout;
import net.minecraft.client.resources.I18n;

public class MultiblockInfoCategory implements IRecipeCategory<MultiblockInfoRecipeWrapper> {

    private final IDrawable background;

    public MultiblockInfoCategory(IJeiHelpers helpers) {
        this.background = helpers.getGuiHelper().createBlankDrawable(176, 166);
    }

    public static void registerRecipes(IModRegistry registry) {
        registry.addRecipes(Lists.newArrayList(
            new MultiblockInfoRecipeWrapper(new PrimitiveBlastFurnaceInfo()),
            new MultiblockInfoRecipeWrapper(new VacuumFreezerInfo()),
            new MultiblockInfoRecipeWrapper(new ImplosionCompressorInfo()),
            new MultiblockInfoRecipeWrapper(new PyrolyzeOvenInfo()),
            new MultiblockInfoRecipeWrapper(new CrackerUnitInfo()),
            new MultiblockInfoRecipeWrapper(new DieselEngineInfo()),
            new MultiblockInfoRecipeWrapper(new DistillationTowerInfo()),
            new MultiblockInfoRecipeWrapper(new ElectricBlastFurnaceInfo()),
            new MultiblockInfoRecipeWrapper(new MultiSmelterInfo()),
            new MultiblockInfoRecipeWrapper(new LargeBoilerInfo(MetaTileEntities.LARGE_BRONZE_BOILER)),
            new MultiblockInfoRecipeWrapper(new LargeBoilerInfo(MetaTileEntities.LARGE_STEEL_BOILER)),
            new MultiblockInfoRecipeWrapper(new LargeBoilerInfo(MetaTileEntities.LARGE_TITANIUM_BOILER)),
            new MultiblockInfoRecipeWrapper(new LargeBoilerInfo(MetaTileEntities.LARGE_TUNGSTENSTEEL_BOILER)),
            new MultiblockInfoRecipeWrapper(new LargeTurbineInfo(MetaTileEntities.LARGE_STEAM_TURBINE)),
            new MultiblockInfoRecipeWrapper(new LargeTurbineInfo(MetaTileEntities.LARGE_GAS_TURBINE)),
            new MultiblockInfoRecipeWrapper(new LargeTurbineInfo(MetaTileEntities.LARGE_PLASMA_TURBINE))
        ), "gregtech:multiblock_info");
    }

    @Override
    public String getUid() {
        return "gregtech:multiblock_info";
    }

    @Override
    public String getTitle() {
        return I18n.format("gregtech.multiblock.title");
    }

    @Override
    public String getModName() {
        return GTValues.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MultiblockInfoRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeWrapper.setRecipeLayout((RecipeLayout) recipeLayout);
    }

}
