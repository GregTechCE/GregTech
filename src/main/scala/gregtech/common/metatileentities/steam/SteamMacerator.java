package gregtech.common.metatileentities.steam;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.SteamRecipeMapWorkableHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreIngredient;

public class SteamMacerator extends SteamMetaTileEntity {

    public SteamMacerator(String metaTileEntityId, boolean isHighPressure) {
        super(metaTileEntityId, RecipeMaps.MACERATOR_RECIPES, Textures.MACERATOR_OVERLAY, isHighPressure);
        this.workableHandler = new SteamRecipeMapWorkableHandler(this,
            workableHandler.recipeMap, GTValues.V[isHighPressure ? 1 : 0], steamFluidTank, 1.0);
    }

    private static class SimpleMaceratorRecipeMapWorkableHandler extends SteamRecipeMapWorkableHandler {

        public SimpleMaceratorRecipeMapWorkableHandler(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, long maxVoltage, IFluidTank steamFluidTank, double conversionRate) {
            super(tileEntity, recipeMap, maxVoltage, steamFluidTank, conversionRate);
        }

        @Override
        protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
            Recipe recipe = super.findRecipe(Math.max(maxVoltage, GTValues.V[1]), inputs, fluidInputs); //use 32 EU/t for recipe finding, to include ore recipes
            if(recipe == null)
                return null;
            CountableIngredient ingredient = recipe.getInputs().get(0);
            if(!isOreInputRecipe(ingredient.getIngredient())) {
                if(recipe.getEUt() > maxVoltage)
                    //it is needed because we use 32 EU/t to include ore recipes
                    return null; //return null if we can't afford such recipe
                return recipe;
            }
            //recipes are guaranteed to have at least one output
            ItemStack firstOutput = recipe.getOutputs().get(0);
            firstOutput.setCount(firstOutput.getCount() / 2); //divide output amount by 2
            int recipeEUt = recipe.getEUt() / 3; //divide EU/t amount by 3
            int duration = recipe.getDuration() / 2; //also divide duration by 2 since we output less
            //final recipe won't change, so it can be buffered by machine safely
            return recipeMap.recipeBuilder()
                .inputs(ingredient)
                .outputs(firstOutput)
                .EUt(recipeEUt).duration(duration)
                .build().getResult();
        }

        private boolean isOreInputRecipe(Ingredient ingredient) {
            if(!(ingredient instanceof OreIngredient))
                return false; //do not mess with non-oredict recipes
            ItemStack[] matchingStacks = ingredient.getMatchingStacks();
            if(matchingStacks == null || matchingStacks.length == 0)
                return false; //do not try to inspect empty ore ingredients
            return OreDictUnifier.getOreDictionaryNames(matchingStacks[0])
                .stream().anyMatch(s -> s.startsWith("ore"));
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SteamMacerator(metaTileEntityId, isHighPressure);
    }

    @Override
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player)
            .widget(101, new SlotWidget(this.importItems, 0, 53, 25)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getFullGuiTexture("slot_%s_macerator_background")))
            .widget(102, new ProgressWidget(workableHandler::getProgressPercent, 78, 25, 20, 18)
                .setProgressBar(getFullGuiTexture("progress_bar_%s_macerator"),
                    getFullGuiTexture("progress_bar_%s_macerator_filled"),
                    ProgressWidget.MoveType.HORIZONTAL))
            .widget(103, new SlotWidget(this.exportItems, 0, 107, 25, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE))
            .build(getHolder(), player);
    }
}
