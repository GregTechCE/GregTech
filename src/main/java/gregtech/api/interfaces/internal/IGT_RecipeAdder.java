package gregtech.api.interfaces.internal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IGT_RecipeAdder {
    /**
     * Adds a FusionreactorRecipe
     * Does not work anymore!
     */
    @Deprecated
    public boolean addFusionReactorRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aFusionDurationInTicks, int aFusionEnergyPerTick, int aEnergyNeededForStartingFusion);

    /**
     * Adds a FusionreactorRecipe
     *
     * @param aInput1                        = first Input (not null, and respects StackSize)
     * @param aInput2                        = second Input (not null, and respects StackSize)
     * @param aOutput                        = Output of the Fusion (can be null, and respects StackSize)
     * @param aFusionDurationInTicks         = How many ticks the Fusion lasts (must be > 0)
     * @param aFusionEnergyPerTick           = The EU generated per Tick (can even be negative!)
     * @param aEnergyNeededForStartingFusion = EU needed for heating the Reactor up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    public boolean addFusionReactorRecipe(FluidStack aInput1, FluidStack aInput2, FluidStack aOutput1, int aFusionDurationInTicks, int aFusionEnergyPerTick, int aEnergyNeededForStartingFusion);

    /**
     * Adds a Centrifuge Recipe
     *
     * @param aInput1    must be != null
     * @param aCellInput this is for the needed Cells, > 0 for Tincellcount, < 0 for negative Fuelcancount, == 0 for nothing
     * @param aOutput1   must be != null
     * @param aOutput2   can be null
     * @param aOutput3   can be null
     * @param aOutput4   can be null
     * @param aDuration  must be > 0
     */
    public boolean addCentrifugeRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int aDuration);

    /**
     * Adds a Centrifuge Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aOutput2  can be null
     * @param aOutput3  can be null
     * @param aOutput4  can be null
     * @param aDuration must be > 0
     */
    public boolean addCentrifugeRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Electrolyzer Recipe
     *
     * @param aInput1    must be != null
     * @param aCellInput this is for the needed Cells, > 0 for Tincellcount, < 0 for negative Fuelcancount, == 0 for nothing
     * @param aOutput1   must be != null
     * @param aOutput2   can be null
     * @param aOutput3   can be null
     * @param aOutput4   can be null
     * @param aDuration  must be > 0
     * @param aEUt       should be > 0
     */
    public boolean addElectrolyzerRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int aDuration, int aEUt);

    /**
     * Adds a Electrolyzer Recipe
     *
     * @param aInput1    must be != null
     * @param aCellInput this is for the needed Cells, > 0 for Tincellcount, < 0 for negative Fuelcancount, == 0 for nothing
     * @param aOutput1   must be != null
     * @param aOutput2   can be null
     * @param aOutput3   can be null
     * @param aOutput4   can be null
     * @param aDuration  must be > 0
     * @param aEUt       should be > 0
     */
    public boolean addElectrolyzerRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Chemical Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     */
    public boolean addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput, int aDuration);

    /**
     * Adds a Chemical Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     */
    public boolean addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration);

    /**
     * Adds a Chemical Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUtick   must be > 0
     */
    public boolean addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration, int aEUtick);


    /**
     * Adds a Blast Furnace Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   can be null
     * @param aOutput1  must be != null
     * @param aOutput2  can be null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     * @param aLevel    should be > 0 is the minimum Heat Level needed for this Recipe
     */
    @Deprecated
    public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel);

    /**
     * Adds a Blast Furnace Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   can be null
     * @param aOutput1  must be != null
     * @param aOutput2  can be null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     * @param aLevel    should be > 0 is the minimum Heat Level needed for this Recipe
     */
    public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel);

    /**
     * Adds a Canning Machine Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0, 100 ticks is standard.
     * @param aEUt      should be > 0, 1 EU/t is standard.
     */
    public boolean addCannerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt);

    /**
     * Adds an Alloy Smelter Recipe
     *
     * @param aInput1   must be != null
     * @param aInput2   can be null
     * @param aOutput1  must be != null
     * @param aOutput2  can be null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt);

    public boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt, boolean hidden);

    
    /**
     * Adds a CNC-Machine Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addCNCRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a Circuit Assembler Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a Circuit Assembler Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, ItemStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a Assemblyline Recipe
     *
     * @param aInputs   must be != null, 4-16 inputs
     * @param aFluidInputs 0-4 fluids
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addAssemblylineRecipe(ItemStack[] aInputs, FluidStack[] aFluidInputs, ItemStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a Forge Hammer Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addForgeHammerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt);

    /**
     * Adds a Wiremill Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addWiremillRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Polariser Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addPolarizerRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Plate Bending Machine Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addBenderRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Extruder Machine Recipe
     *
     * @param aInput1   must be != null
     * @param aShape    must be != null, Set the stackSize to 0 if you don't want to let it consume this Item.
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addExtruderRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Slicer Machine Recipe
     *
     * @param aInput1   must be != null
     * @param aShape    must be != null, Set the stackSize to 0 if you don't want to let it consume this Item.
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     * @param aEUt      should be > 0
     */
    public boolean addSlicerRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds an Implosion Compressor Recipe
     *
     * @param aInput1  must be != null
     * @param aInput2  amount of ITNT, should be > 0
     * @param aOutput1 must be != null
     * @param aOutput2 can be null
     */
    public boolean addImplosionRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2);

    /**
     * Adds a Grinder Recipe
     *
     * @param aInput1  must be != null
     * @param aInput2  id for the Cell needed for this Recipe
     * @param aOutput1 must be != null
     * @param aOutput2 can be null
     * @param aOutput3 can be null
     * @param aOutput4 can be null
     */
    public boolean addGrinderRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4);

    /**
     * Adds a Distillation Tower Recipe
     *
     * @param aInput1  must be != null
     * @param aOutputs must be != null 1-5 Fluids
     * @param aOutput2 can be null
     */
    public boolean addDistillationTowerRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt);


    public boolean addSimpleArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt);

    public boolean addPlasmaArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt);

    public boolean addPlasmaArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, FluidStack aFluidPutput, int[] aChances, int aDuration, int aEUt);


    /**
     * Adds a Distillation Tower Recipe
     */
    public boolean addDistillationRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt);

    /**
     * Adds a Lathe Machine Recipe
     */
    public boolean addLatheRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt);

    /**
     * Adds a Cutter Recipe
     */
    public boolean addCutterRecipe(ItemStack aInput, FluidStack aLubricant, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt);

    /**
     * Adds Cutter Recipes with default Lubricants
     */
    public boolean addCutterRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt);

    /**
     * Adds a Boxing Recipe
     */
    public boolean addBoxingRecipe(ItemStack aContainedItem, ItemStack aEmptyBox, ItemStack aFullBox, int aDuration, int aEUt);

    /**
     * Adds an Unboxing Recipe
     */
    public boolean addUnboxingRecipe(ItemStack aFullBox, ItemStack aContainedItem, ItemStack aEmptyBox, int aDuration, int aEUt);

    /**
     * Adds a Vacuum Freezer Recipe
     *
     * @param aInput1   must be != null
     * @param aOutput1  must be != null
     * @param aDuration must be > 0
     */
    public boolean addVacuumFreezerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration);

    /**
     * Adds a Fuel for My Generators
     *
     * @param aInput1  must be != null
     * @param aOutput1 can be null
     * @param aEU      EU per MilliBucket. If no Liquid Form of this Container is available, then it will give you EU*1000 per Item.
     * @param aType    0 = Diesel; 1 = Gas Turbine; 2 = Thermal; 3 = Dense Fluid; 4 = Plasma; 5 = Magic; And if something is unclear or missing, then look at the GT_Recipe-Class
     */
    public boolean addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType);

    /**
     * Adds an Amplifier Recipe for the Amplifabricator
     */
    public boolean addAmplifier(ItemStack aAmplifierItem, int aDuration, int aAmplifierAmountOutputted);

    /**
     * Adds a Recipe for the Brewing Machine (intentionally limited to Fluid IDs)
     */
    public boolean addBrewingRecipe(ItemStack aIngredient, Fluid aInput, Fluid aOutput, boolean aHidden);

    /**
     * Adds a Recipe for the Fermenter
     */
    public boolean addFermentingRecipe(FluidStack aInput, FluidStack aOutput, int aDuration, boolean aHidden);

    /**
     * Adds a Recipe for the Fluid Heater
     */
    public boolean addFluidHeaterRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Distillery
     */
    public boolean addDistilleryRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt, boolean aHidden);

    /**
     * Adds a Recipe for the Fluid Solidifier
     */
    public boolean addFluidSolidifierRecipe(ItemStack aMold, FluidStack aInput, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Recipe for Fluid Smelting
     */
    public boolean addFluidSmelterRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt);
   
    /**
     * Adds a Recipe for Fluid Smelting
     */
    public boolean addFluidSmelterRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt, boolean hidden);

    /**
     * Adds a Recipe for Fluid Extraction
     */
    public boolean addFluidExtractionRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Fluid Canner
     */
    public boolean addFluidCannerRecipe(ItemStack aInput, ItemStack aOutput, FluidStack aFluidInput, FluidStack aFluidOutput);

    /**
     * Adds a Recipe for the Chemical Bath
     */
    public boolean addChemicalBathRecipe(ItemStack aInput, FluidStack aBathingFluid, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Electromagnetic Separator
     */
    public boolean addElectromagneticSeparatorRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Printer
     */
    public boolean addPrinterRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aSpecialSlot, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Autoclave
     */
    public boolean addAutoclaveRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aOutput, int aChance, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Mixer
     */
    public boolean addMixerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aInput3, ItemStack aInput4, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Laser Engraver
     */
    public boolean addLaserEngraverRecipe(ItemStack aItemToEngrave, ItemStack aLens, ItemStack aEngravedItem, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Forming Press
     */
    public boolean addFormingPressRecipe(ItemStack aItemToImprint, ItemStack aForm, ItemStack aImprintedItem, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Sifter. (up to 9 Outputs)
     */
    public boolean addSifterRecipe(ItemStack aItemToSift, ItemStack[] aSiftedItems, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Arc Furnace. (up to 4 Outputs)
     */
    public boolean addArcFurnaceRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the Arc Furnace. (up to 4 Outputs)
     */
    public boolean addArcFurnaceRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt, boolean hidden);

    
    /**
     * Adds a Recipe for the GT Pulveriser. (up to 4 Outputs)
     */
    public boolean addPulveriserRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt);

    /**
     * Adds a Recipe for the GT Pulveriser. (up to 4 Outputs)
     */
    public boolean addPulveriserRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt, boolean hidden);

    /**
     * Adds a Distillation Tower Recipe
     * Every Fluid also gets seperate distillation recipes
     *
     * @param aInput1  must be != null
     * @param aOutputs must be != null 1-5 Fluids
     * @param aOutput2 can be null
     */
    public boolean addUniversalDistillationRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt);

    /**
     * Adds Pyrolyse Recipe
     *
     * @param aInput
     * @param intCircuit
     * @param aOutput
     * @param aFluidOutput
     * @param aDuration
     * @param aEUt
     */
    public boolean addPyrolyseRecipe(ItemStack aInput, FluidStack aFluidInput, int intCircuit, ItemStack aOutput, FluidStack aFluidOutput, int aDuration, int aEUt);

    /**
     * Adds Oil Cracking Recipe
     *
     * @param aInput
     * @param aOutput
     * @param aDuration
     * @param aEUt
     */
    public boolean addCrackingRecipe(FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt);

    /**
     * Adds a Sound to the Sonictron9001
     * you should NOT call this in the preInit-Phase!
     *
     * @param aItemStack = The Item you want to display for this Sound
     * @param aSoundName = The Name of the Sound in the resources/newsound-folder like Vanillasounds
     * @return true if the Sound got added, otherwise false.
     */
    public boolean addSonictronSound(ItemStack aItemStack, String aSoundName);
}
