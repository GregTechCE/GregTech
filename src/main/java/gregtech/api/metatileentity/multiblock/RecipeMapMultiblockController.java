package gregtech.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.MultiblockRecipeMapWorkable;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class RecipeMapMultiblockController extends MultiblockWithDisplayBase {

    public final RecipeMap<?> recipeMap;
    protected MultiblockRecipeMapWorkable recipeMapWorkable;

    public RecipeMapMultiblockController(String metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.recipeMapWorkable = new MultiblockRecipeMapWorkable(this, recipeMap, this::checkRecipe);
    }

    protected boolean shouldUseEnergyOutputs() {
        return false;
    }

    /**
     * Performs extra checks for validity of given recipe before multiblock
     * will start it's processing.
     */
    protected boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        ItemHandlerList importItemsList = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        FluidTankList importFluidsList = new FluidTankList(getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        ItemHandlerList exportItemsList = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        FluidTankList exportFluidsList = new FluidTankList(getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        EnergyContainerList energyContainerList = new EnergyContainerList(getAbilities(shouldUseEnergyOutputs() ? MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY));
        this.recipeMapWorkable.reinitializeAbilities(importItemsList, importFluidsList, exportItemsList, exportFluidsList, energyContainerList);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.recipeMapWorkable.resetAbilities();
    }

    @Override
    protected void updateFormedValid() {
        this.recipeMapWorkable.updateWorkable();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            boolean isGenerator = shouldUseEnergyOutputs();
            IEnergyContainer energyContainer = recipeMapWorkable.getEnergyContainer();
            if(!isGenerator && energyContainer.getEnergyCapacity() > 0) {
                long maxVoltage = shouldUseEnergyOutputs() ? energyContainer.getOutputVoltage() : energyContainer.getInputVoltage();
                String voltageName = GTValues.VN[GTUtility.getTierByVoltage(maxVoltage)];
                textList.add(new TextComponentTranslation("gregtech.multiblock.max_energy_per_tick", maxVoltage, voltageName));
            }

            if (!recipeMapWorkable.isWorkingEnabled()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.work_paused"));

            } else if (recipeMapWorkable.isActive()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.running"));
                if (!isGenerator) {
                    //show current progress for standard multiblocks
                    int currentProgress = (int) (recipeMapWorkable.getProgressPercent() * 100);
                    textList.add(new TextComponentTranslation("gregtech.multiblock.progress", currentProgress));
                } else {
                    //for generators, show generated EU/t instead
                    int recipeEUt = -recipeMapWorkable.getRecipeEUt();
                    textList.add(new TextComponentTranslation("gregtech.multiblock.generation_eu", recipeEUt));
                }

            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.idling"));
            }

            if (recipeMapWorkable.isHasNotEnoughEnergy()) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.not_enough_energy").setStyle(new Style().setColor(TextFormatting.RED)));
            }
        }
    }

    @Override
    protected BooleanSupplier getValidationPredicate() {
        return () -> {
            //basically check minimal requirements for inputs count & amperage
            int itemInputsCount = getAbilities(MultiblockAbility.IMPORT_ITEMS)
                .stream().mapToInt(IItemHandler::getSlots).sum();
            int fluidInputsCount = getAbilities(MultiblockAbility.IMPORT_FLUIDS).size();
            int maxEnergyHatches = getAbilities(MultiblockAbility.INPUT_ENERGY).size();
            return itemInputsCount >= recipeMap.getMinInputs() &&
                fluidInputsCount >= recipeMap.getMinFluidInputs() &&
                maxEnergyHatches * 4 >= recipeMap.getAmperage();
        };
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, pipeline, getFrontFacing(), recipeMapWorkable.isActive());
    }
}
