package gregtech.api.metatileentity.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class RecipeMapMultiblockController extends MultiblockWithDisplayBase {

    public final RecipeMap<?> recipeMap;
    protected MultiblockRecipeMapWorkable recipeMapWorkable;
    protected IEnergyContainer energyContainer;

    public RecipeMapMultiblockController(String metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId);
        this.recipeMap = recipeMap;
        this.recipeMapWorkable = new MultiblockRecipeMapWorkable(this);
        resetTileAbilities();
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //as inventories are temporary
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    /**
     * @return true if multiblock uses energy emitter hatches, false otherwise
     */
    protected boolean shouldUseEnergyOutputs() {
        return false;
    }

    /**
     * Performs extra checks for validity of given recipe before multiblock
     * will start it's processing.
     */
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    @Override
    protected void updateFormedValid() {
        this.recipeMapWorkable.updateWorkable();
    }

    private void initializeAbilities() {
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.importFluids = new FluidTankList(getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.exportFluids = new FluidTankList(getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        this.energyContainer = new EnergyContainerList(getAbilities(shouldUseEnergyOutputs() ?
            MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY));
    }

    private void resetTileAbilities() {
        this.importItems = new ItemStackHandler(0);
        this.importFluids = new FluidTankList();
        this.exportItems = new ItemStackHandler(0);
        this.exportFluids = new FluidTankList();
    }

    @Override
    protected void initializeInventory() {
        ItemStackHandler emptyInventory = new ItemStackHandler(0);
        FluidTankList emptyFluidInventory = new FluidTankList();
        this.itemInventory = new ItemHandlerProxy(emptyInventory, emptyInventory);
        this.fluidInventory = new FluidHandlerProxy(emptyFluidInventory, emptyFluidInventory);
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
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
            //basically check minimal requirements for inputs count
            int itemInputsCount = getAbilities(MultiblockAbility.IMPORT_ITEMS)
                .stream().mapToInt(IItemHandler::getSlots).sum();
            int fluidInputsCount = getAbilities(MultiblockAbility.IMPORT_FLUIDS).size();
            return itemInputsCount >= recipeMap.getMinInputs() &&
                fluidInputsCount >= recipeMap.getMinFluidInputs();
        };
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), recipeMapWorkable.isActive());
    }
}
