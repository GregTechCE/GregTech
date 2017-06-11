package gregtech.api.interfaces.internal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IGT_RecipeAdder {

    /**
     * Adds a Fusion reactor Recipe
     *
     * Inputs Non-null
     * Outputs Nullable
     *
     * @param EUt The EU generated per Tick (can even be negative!)
     * @param energyToStart EU needed for heating the Reactor up (must be >= 0)
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addFusionReactorRecipe(FluidStack firstInput, FluidStack secondInput, FluidStack output, int duration, int EUt, int energyToStart);

    /**
     * Adds a Centrifuge Recipe
     *
     * Inputs Non-null
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addCentrifugeRecipe(ItemStack firstInput, int emptyCellsCount, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int duration);

    /**
     * Adds a Centrifuge Recipe
     *
     * Everything is Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addCentrifugeRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int[] outputChances, int duration, int EUt);

    /**
     * Adds a Electrolyzer Recipe
     *
     * Inputs Non-Null
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addElectrolyzerRecipe(ItemStack firstInput, int emptyCellsCount, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int duration, int EUt);

    /**
     * Adds a Electrolyzer Recipe
     *
     * Inputs Non-Null (fluid input nullable, second input nullable)
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    boolean addElectrolyzerRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int[] outputChances, int duration, int EUt);

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     */
    boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack output, int duration);

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     */
    boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack output, int duration);

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUtick   must be > 0
     */
    boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack output, int duration, int EUtick);

    /**
     * Adds a Blast Furnace Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   can be null
     * @param firstOutput  must be != null
     * @param secondOutput  can be null
     * @param duration must be > 0
     * @param EUt      should be > 0
     * @param aLevel    should be > 0 is the minimum Heat Level needed for this Recipe
     */
    boolean addBlastRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt, int aLevel);

    /**
     * Adds a Canning Machine Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0, 100 ticks is standard.
     * @param EUt      should be > 0, 1 EU/t is standard.
     */
    boolean addCannerRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt);

    /**
     * Adds an Alloy Smelter Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   can be null
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addAlloySmelterRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, int duration, int EUt);

    boolean addAlloySmelterRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, int duration, int EUt, boolean hidden);

    
    /**
     * Adds a CNC-Machine Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addCNCRecipe(ItemStack firstInput, ItemStack firstOutput, int duration, int EUt);

    /**
     * Adds a Circuit Assembler Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addAssemblerRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, int duration, int EUt);

    /**
     * Adds a Circuit Assembler Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addAssemblerRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, ItemStack firstOutput, int duration, int EUt);

    /**
     * Adds a Assemblyline Recipe
     *
     * @param inputs   must be != null, 4-16 inputs
     * @param fluidInputs 0-4 fluids
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addAssemblylineRecipe(ItemStack aResearchItem, int aResearchTime, ItemStack[] inputs, FluidStack[] fluidInputs, ItemStack firstOutput, int duration, int EUt);

    /**
     * Adds a Forge Hammer Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addForgeHammerRecipe(ItemStack firstInput, ItemStack firstOutput, int duration, int EUt);

    /**
     * Adds a Wiremill Recipe
     *
     * @param input   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addWiremillRecipe(ItemStack input, ItemStack output, int duration, int EUt);

    /**
     * Adds a Polariser Recipe
     *
     * @param input   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addPolarizerRecipe(ItemStack input, ItemStack output, int duration, int EUt);

    /**
     * Adds a Plate Bending Machine Recipe
     *
     * @param input   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addBenderRecipe(ItemStack input, ItemStack output, int duration, int EUt);

    /**
     * Adds a Extruder Machine Recipe
     *
     * @param input   must be != null
     * @param aShape    must be != null, Set the stackSize to 0 if you don't want to let it consume this Item.
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addExtruderRecipe(ItemStack input, ItemStack aShape, ItemStack output, int duration, int EUt);

    /**
     * Adds a Slicer Machine Recipe
     *
     * @param input   must be != null
     * @param aShape    must be != null, Set the stackSize to 0 if you don't want to let it consume this Item.
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt      should be > 0
     */
    boolean addSlicerRecipe(ItemStack input, ItemStack aShape, ItemStack output, int duration, int EUt);

    /**
     * Adds an Implosion Compressor Recipe
     *
     * @param firstInput  must be != null
     * @param secondInput  amount of ITNT, should be > 0
     * @param firstOutput must be != null
     * @param secondOutput can be null
     */
    boolean addImplosionRecipe(ItemStack firstInput, int secondInput, ItemStack firstOutput, ItemStack secondOutput);

    /**
     * Adds a Grinder Recipe
     *
     * @param firstInput  must be != null
     * @param secondInput  id for the Cell needed for this Recipe
     * @param firstOutput must be != null
     * @param secondOutput can be null
     * @param thirdOutput can be null
     * @param forthOutput can be null
     */
    boolean addGrinderRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack forthOutput);

    /**
     * Adds a Distillation Tower Recipe
     *
     * @param input  must be != null
     * @param outputs must be != null 1-5 Fluids
     * @param secondOutput can be null
     */
    boolean addDistillationTowerRecipe(FluidStack input, FluidStack[] outputs, ItemStack secondOutput, int duration, int EUt);


    boolean addSimpleArcFurnaceRecipe(ItemStack input, FluidStack fluidInput, ItemStack[] outputs, int[] chances, int duration, int EUt);

    boolean addPlasmaArcFurnaceRecipe(ItemStack input, FluidStack fluidInput, ItemStack[] outputs, int[] chances, int duration, int EUt);

    boolean addPlasmaArcFurnaceRecipe(ItemStack input, FluidStack fluidInput, ItemStack[] outputs, FluidStack aFluidPutput, int[] chances, int duration, int EUt);


    /**
     * Adds a Distillation Tower Recipe
     */
    boolean addDistillationRecipe(ItemStack firstInput, int secondInput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack forthOutput, int duration, int EUt);

    /**
     * Adds a Lathe Machine Recipe
     */
    boolean addLatheRecipe(ItemStack firstInput, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt);

    /**
     * Adds a Cutter Recipe
     */
    boolean addCutterRecipe(ItemStack input, FluidStack aLubricant, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt);

    /**
     * Adds Cutter Recipes with default Lubricants
     */
    boolean addCutterRecipe(ItemStack input, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt);

    /**
     * Adds a Boxing Recipe
     */
    boolean addBoxingRecipe(ItemStack containedItem, ItemStack emptyBox, ItemStack fullBox, int duration, int EUt);

    /**
     * Adds an Unboxing Recipe
     */
    boolean addUnboxingRecipe(ItemStack fullBox, ItemStack containedItem, ItemStack emptyBox, int duration, int EUt);

    /**
     * Adds a Vacuum Freezer Recipe
     *
     * @param input   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     */
    boolean addVacuumFreezerRecipe(ItemStack input, ItemStack output, int duration);

    /**
     * Adds a Fuel for My Generators
     *
     * @param firstInput  must be != null
     * @param firstOutput can be null
     * @param aEU      EU per MilliBucket. If no Liquid Form of this Container is available, then it will give you EU*1000 per Item.
     * @param aType    0 = Diesel; 1 = Gas Turbine; 2 = Thermal; 3 = Dense Fluid; 4 = Plasma; 5 = Magic; And if something is unclear or missing, then look at the GT_Recipe-Class
     */
    boolean addFuel(ItemStack firstInput, ItemStack firstOutput, int aEU, int aType);

    /**
     * Adds an Amplifier Recipe for the Amplifabricator
     */
    boolean addAmplifier(ItemStack amplifierItem, int duration, int aAmplifierAmountOutputted);

    /**
     * Adds a Recipe for the Brewing Machine (intentionally limited to Fluid IDs)
     */
    boolean addBrewingRecipe(ItemStack ingredient, Fluid input, Fluid output, boolean aHidden);

    /**
     * Adds a Recipe for the Fermenter
     */
    boolean addFermentingRecipe(FluidStack input, FluidStack output, int duration, boolean aHidden);

    /**
     * Adds a Recipe for the Fluid Heater
     */
    boolean addFluidHeaterRecipe(ItemStack circuit, FluidStack input, FluidStack output, int duration, int EUt);

    /**
     * Adds a Recipe for the Distillery
     */
    boolean addDistilleryRecipe(ItemStack circuit, FluidStack input, FluidStack output, int duration, int EUt, boolean hidden);

    /**
     * Adds a Recipe for the Fluid Solidifier
     */
    boolean addFluidSolidifierRecipe(ItemStack mold, FluidStack input, ItemStack output, int duration, int EUt);

    /**
     * Adds a Recipe for Fluid Smelting
     */
    boolean addFluidSmelterRecipe(ItemStack input, ItemStack remains, FluidStack output, int chance, int duration, int EUt);
   
    /**
     * Adds a Recipe for Fluid Smelting
     */
    boolean addFluidSmelterRecipe(ItemStack input, ItemStack aRemains, FluidStack output, int chance, int duration, int EUt, boolean hidden);

    /**
     * Adds a Recipe for Fluid Extraction
     */
    boolean addFluidExtractionRecipe(ItemStack input, ItemStack aRemains, FluidStack output, int chance, int duration, int EUt);

    /**
     * Adds a Recipe for the Fluid Canner
     */
    boolean addFluidCannerRecipe(ItemStack input, ItemStack output, FluidStack fluidInput, FluidStack fluidOutput);

    /**
     * Adds a Recipe for the Chemical Bath
     */
    boolean addChemicalBathRecipe(ItemStack input, FluidStack aBathingFluid, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, int[] chances, int duration, int EUt);

    /**
     * Adds a Recipe for the Electromagnetic Separator
     */
    boolean addElectromagneticSeparatorRecipe(ItemStack input, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, int[] chances, int duration, int EUt);

    /**
     * Adds a Recipe for the Printer
     */
    boolean addPrinterRecipe(ItemStack input, FluidStack fluid, ItemStack specialSlot, ItemStack output, int duration, int EUt);

    /**
     * Adds a Recipe for the Autoclave
     */
    boolean addAutoclaveRecipe(ItemStack input, FluidStack fluidInput, ItemStack output, int chance, int duration, int EUt);

    /**
     * Adds a Recipe for the Mixer
     */
    boolean addMixerRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack thirdInput, ItemStack forthInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack output, int duration, int EUt);

    /**
     * Adds a Recipe for the Laser Engraver
     */
    boolean addLaserEngraverRecipe(ItemStack itemToEngrave, ItemStack lens, ItemStack engravedItem, int duration, int EUt);

    /**
     * Adds a Recipe for the Forming Press
     */
    boolean addFormingPressRecipe(ItemStack itemToImprint, ItemStack form, ItemStack imprintedItem, int duration, int EUt);

    /**
     * Adds a Recipe for the Sifter. (up to 9 Outputs)
     */
    boolean addSifterRecipe(ItemStack itemToSift, ItemStack[] siftedItems, int[] chances, int duration, int EUt);

    /**
     * Adds a Recipe for the Arc Furnace. (up to 4 Outputs)
     */
    boolean addArcFurnaceRecipe(ItemStack input, ItemStack[] outputs, int[] chances, int duration, int EUt);

    /**
     * Adds a Recipe for the Arc Furnace. (up to 4 Outputs)
     */
    boolean addArcFurnaceRecipe(ItemStack input, ItemStack[] outputs, int[] chances, int duration, int EUt, boolean hidden);

    
    /**
     * Adds a Recipe for the GT Pulveriser. (up to 4 Outputs)
     */
    boolean addPulveriserRecipe(ItemStack input, ItemStack[] outputs, int[] chances, int duration, int EUt);

    /**
     * Adds a Recipe for the GT Pulveriser. (up to 4 Outputs)
     */
    boolean addPulveriserRecipe(ItemStack input, ItemStack[] outputs, int[] chances, int duration, int EUt, boolean hidden);

    /**
     * Adds a Distillation Tower Recipe
     * Every Fluid also gets seperate distillation recipes
     *
     * @param input  must be != null
     * @param outputs must be != null 1-5 Fluids
     * @param secondOutput can be null
     */
    boolean addUniversalDistillationRecipe(FluidStack input, FluidStack[] outputs, ItemStack secondOutput, int duration, int EUt);

    /**
     * Adds Pyrolyse Recipe
     *
     */
    boolean addPyrolyseRecipe(ItemStack input, FluidStack fluidInput, int intCircuit, ItemStack output, FluidStack fluidOutput, int duration, int EUt);

    /**
     * Adds Oil Cracking Recipe
     *
     */
    boolean addCrackingRecipe(FluidStack input, FluidStack output, int duration, int EUt);

    /**
     * Adds a Sound to the Sonictron9001
     * you should NOT call this in the preInit-Phase!
     *
     * @param itemStack = The Item you want to display for this Sound
     * @param soundName = The Name of the Sound in the resources/newsound-folder like Vanillasounds
     * @return true if the Sound got added, otherwise false.
     */
    boolean addSonictronSound(ItemStack itemStack, String soundName);

}
