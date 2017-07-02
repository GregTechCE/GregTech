package gregtech.api.recipes;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.internal.IGT_RecipeAdder;
import gregtech.api.util.GT_ModHandler;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GT_RecipeAdder implements IGT_RecipeAdder {

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
    public boolean addFusionReactorRecipe(FluidStack firstInput, FluidStack secondInput, FluidStack output, int duration, int EUt, int energyToStart) {
        if (firstInput == null || secondInput == null || output == null || duration < 1 || EUt < 1 || energyToStart < 1) {
            return false;
        }

        if((output != null) && (duration = GregTech_API.sRecipeFile.get("fusion", output.getFluid().getName(), duration)) <= 0) {
            return false;
        }

        GT_Recipe recipe = new GT_Recipe(true, null, null, null, null, new FluidStack[]{firstInput, secondInput}, new FluidStack[]{output}, Math.max(duration, 1), EUt, MathHelper.clamp_int(energyToStart, 0, 160000000));
        RecipeMap.FUSION_RECIPES.addRecipe(recipe);
        return true;
    }

    /**
     * Adds a Centrifuge Recipe
     *
     * Inputs Non-null
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    public boolean addCentrifugeRecipe(ItemStack firstInput, int emptyCellsCount, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int duration) {
        return addCentrifugeRecipe(firstInput, emptyCellsCount < 0 ? ItemList.IC2_Fuel_Can_Empty.get(-emptyCellsCount) : emptyCellsCount > 0 ? ItemList.Cell_Empty.get(emptyCellsCount) : null, null, null, firstOutput, secondOutput, thirdOutput, fourthOutput, fifthOutput, sixthOutput, null, duration, 5);
    }

    /**
     * Adds a Centrifuge Recipe
     *
     * Everything is Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    public boolean addCentrifugeRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int[] outputChances, int duration, int EUt) {
        if ((firstInput == null && fluidInput == null) || (firstOutput == null && fluidOutput == null)) {
            return false;
        }
        if (firstInput != null && (duration = GregTech_API.sRecipeFile.get("centrifuge", firstInput, duration)) <= 0) {
            return false;
        }
        if (fluidInput != null && (duration = GregTech_API.sRecipeFile.get("centrifuge", fluidInput.getFluid().getName(), duration)) <= 0) {
            return false;
        }
        RecipeMap.CENTRIFUGE_RECIPES.addRecipe(true, new ItemStack[]{firstInput, secondInput}, new ItemStack[]{firstOutput, secondOutput, thirdOutput, fourthOutput, fifthOutput, sixthOutput}, null, outputChances, new FluidStack[]{fluidInput}, new FluidStack[]{fluidOutput}, duration, EUt, 0);
        return true;
    }

    /**
     * Adds a Electrolyzer Recipe
     *
     * Inputs Non-Null
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    public boolean addElectrolyzerRecipe(ItemStack firstInput, int emptyCellsCount, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int duration, int EUt) {
        return addElectrolyzerRecipe(firstInput, emptyCellsCount < 0 ? ItemList.IC2_Fuel_Can_Empty.get(-emptyCellsCount) : emptyCellsCount > 0 ? ItemList.Cell_Empty.get(emptyCellsCount) : null, null, null, firstOutput, secondOutput, thirdOutput, fourthOutput, fifthOutput, sixthOutput, null, duration, EUt);
    }

    /**
     * Adds a Electrolyzer Recipe
     *
     * Inputs Non-Null (fluid input nullable, second input nullable)
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    public boolean addElectrolyzerRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack firstOutput, ItemStack secondOutput, ItemStack thirdOutput, ItemStack fourthOutput, ItemStack fifthOutput, ItemStack sixthOutput, int[] outputChances, int duration, int EUt) {
        if (((firstInput == null) && (fluidInput == null)) || ((firstOutput == null) && (fluidOutput == null))) {
            return false;
        }
        if ((firstInput != null) && ((duration = GregTech_API.sRecipeFile.get("electrolyzer", firstInput, duration)) <= 0)) {
            return false;
        }
        if ((fluidInput != null) && ((duration = GregTech_API.sRecipeFile.get("electrolyzer", fluidInput.getFluid().getName(), duration)) <= 0)) {
            return false;
        }
        RecipeMap.ELECTROLYZER_RECIPES.addRecipe(true, new ItemStack[]{firstInput, secondInput}, new ItemStack[]{firstOutput, secondOutput, thirdOutput, fourthOutput, fifthOutput, sixthOutput}, null, outputChances, new FluidStack[]{fluidInput}, new FluidStack[]{fluidOutput}, duration, EUt, 0);
        return true;
    }

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     */
    public boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack output, int duration) {
        return addChemicalRecipe(firstInput, secondInput, null, null, output, duration);
    }

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     */
    public boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack output, int duration) {
        return addChemicalRecipe(firstInput, secondInput, fluidInput, fluidOutput, output, duration, 30);
    }

    /**
     * Adds a Chemical Recipe
     *
     * @param firstInput   must be != null
     * @param secondInput   must be != null
     * @param output  must be != null
     * @param duration must be > 0
     * @param EUt   must be > 0
     */
    public boolean addChemicalRecipe(ItemStack firstInput, ItemStack secondInput, FluidStack fluidInput, FluidStack fluidOutput, ItemStack output, int duration, int EUt) {
        if (((firstInput == null) && (fluidInput == null)) || ((output == null) && (fluidOutput == null))) {
            return false;
        }
        if ((output != null) && ((duration = GregTech_API.sRecipeFile.get("chemicalreactor", output, duration)) <= 0)) {
            return false;
        }
        if ((fluidOutput != null) && ((duration = GregTech_API.sRecipeFile.get("chemicalreactor", fluidOutput.getFluid().getName(), duration)) <= 0)) {
            return false;
        }
        if (EUt <= 0) {
            return false;
        }
        RecipeMap.CHEMICAL_RECIPES.addRecipe(true, new ItemStack[]{firstInput, secondInput}, new ItemStack[]{output}, null, null, new FluidStack[]{fluidInput}, new FluidStack[]{fluidOutput}, duration, EUt, 0);
        return true;
    }

    public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel) {
        return addBlastRecipe(aInput1, aInput2, null, null, aOutput1, aOutput2, aDuration, aEUt, aLevel);
    }

    public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("blastfurnace", aInput1, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.BLAST_RECIPES.addRecipe(true, new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1, aOutput2}, null, null, new FluidStack[]{aFluidInput}, null, aDuration, aEUt, aLevel);
        return true;
    }

    /**
     * Adds a Canning Machine Recipe
     *
     * @param firstInput   must be != null
     * @param firstOutput  must be != null
     * @param duration must be > 0, 100 ticks is standard.
     * @param EUt      should be > 0, 1 EU/t is standard.
     */
    public boolean addCannerRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack firstOutput, ItemStack secondOutput, int duration, int EUt) {
        if (firstInput == null || firstOutput == null) {
            return false;
        }
        if ((duration = GregTech_API.sRecipeFile.get("canning", firstInput, duration)) <= 0) {
            return false;
        }

        GT_Recipe recipe = new GT_Recipe(true, secondInput == null ? new ItemStack[]{firstInput} : new ItemStack[]{firstInput, secondInput}, new ItemStack[]{firstOutput, secondOutput}, null, null, null, null, Math.max(duration, 1), Math.max(EUt, 1), 0);
        RecipeMap.CANNER_RECIPES.addRecipe(recipe);
        return true;
    }
    
	@Override
	public boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt) {
		return addAlloySmelterRecipe(aInput1, aInput2, aOutput1, aDuration, aEUt, false);
	}

    public boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt, boolean hidden) {
        if ((aInput1 == null) || (aOutput1 == null || Materials.Graphite.contains(aInput1))) {
            return false;
        }
        if ((aInput2 == null) && ((OrePrefixes.ingot.contains(aInput1)) || (OrePrefixes.dust.contains(aInput1)) || (OrePrefixes.gem.contains(aInput1)))) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("alloysmelting", aInput2 == null ? aInput1 : aOutput1, aDuration)) <= 0) {
            return false;
        }
        GT_Recipe tRecipe = new GT_Recipe.AlloySmelterRecipe(aInput1, aInput2, aEUt, aDuration, aOutput1);
         if (hidden) {
            tRecipe.hidden = true;
        }
        return true;
    }

//    public boolean addCNCRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
//        if ((aInput1 == null) || (aOutput1 == null)) {
//            return false;
//        }
//        if ((GregTech_API.sRecipeFile.get("cnc", aOutput1, aDuration)) <= 0) {
//            return false;
//        }
//        return true;
//    }

    public boolean addLatheRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
        if (aInput1 == null || aOutput1 == null) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("lathe", aInput1, aDuration)) <= 0) {
            return false;
        }
        new GT_Recipe.LatheRecipe(aInput1, aOutput1, aOutput2, aDuration, aEUt);
        new GT_Recipe(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1, aOutput2}, null, null, null, null, aDuration, aEUt, 0);
        if (mInputs.length > 0 && mOutputs[0] != null) {
            RecipeMap.LATHE_RECIPES.addRecipe(this);
        }
        return true;
    }

    public boolean addCutterRecipe(ItemStack aInput, FluidStack aLubricant, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
        if ((aInput == null) || (aLubricant == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("cutting", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.CUTTER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2}, null, new FluidStack[]{aLubricant}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addCutterRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("cutting", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.CUTTER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2}, null, new FluidStack[]{Materials.Water.getFluid(Math.max(4, Math.min(1000, aDuration * aEUt / 320)))}, null, aDuration * 2, aEUt, 0);
        RecipeMap.CUTTER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2}, null, new FluidStack[]{GT_ModHandler.getDistilledWater(Math.max(3, Math.min(750, aDuration * aEUt / 426)))}, null, aDuration * 2, aEUt, 0);
        RecipeMap.CUTTER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2}, null, new FluidStack[]{Materials.Lubricant.getFluid(Math.max(1, Math.min(250, aDuration * aEUt / 1280)))}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("assembling", aOutput1, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.ASSEMBLER_RECIPES.addRecipe(true, new ItemStack[]{aInput1, aInput2 == null ? aInput1 : aInput2}, new ItemStack[]{aOutput1}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, ItemStack aOutput1, int aDuration, int aEUt) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("assembling", aOutput1, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.ASSEMBLER_RECIPES.addRecipe(true, aInput2 == null ? new ItemStack[] {aInput1} : new ItemStack[]{aInput1, aInput2}, new ItemStack[]{aOutput1}, null, new FluidStack[]{aFluidInput == null ? null : aFluidInput}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addWiremillRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("wiremill", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.WIREMILL_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addPolarizerRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("polarizer", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.POLARIZER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addBenderRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("bender", aInput1, aDuration)) <= 0) {
            return false;
        }
        new GT_Recipe.BenderRecipe(aEUt, aDuration, aInput1, aOutput1);
        return true;
    }

    public boolean addExtruderRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aShape == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("extruder", aOutput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.EXTRUDER_RECIPES.addRecipe(true, new ItemStack[]{aInput, aShape}, new ItemStack[]{aOutput}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addSlicerRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aShape == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("slicer", aOutput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.SLICER_RECIPES.addRecipe(true, new ItemStack[]{aInput, aShape}, new ItemStack[]{aOutput}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addImplosionRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aInput2 = GregTech_API.sRecipeFile.get("implosion", aInput1, aInput2)) <= 0) {
            return false;
        }
        int tExplosives = aInput2 > 0 ? aInput2 < 64 ? aInput2 : 64 : 1;
        int tGunpowder = tExplosives * 2;
        int tDynamite = tExplosives * 4;
        int tTNT = Math.max(1, tExplosives/2);
        int tITNT = Math.max(1, tExplosives/4);
        if(tGunpowder < 65){
        	new GT_Recipe.ImplosionRecipe(aInput1, ItemList.Block_Powderbarrel.get(tGunpowder), aOutput1, aOutput2);
        }
        if(tDynamite < 17){
			new GT_Recipe.ImplosionRecipe(aInput1, GT_ModHandler.getIC2Item(ItemName.dynamite, tDynamite), aOutput1, aOutput2);
        }
		new GT_Recipe.ImplosionRecipe(aInput1, new ItemStack(Blocks.TNT, tTNT), aOutput1, aOutput2);
		new GT_Recipe.ImplosionRecipe(aInput1, GT_ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, tITNT), aOutput1, aOutput2);

        return true;
    }

	@Deprecated
    public boolean addDistillationRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt) {
        return false;
    }


    @Override
    public boolean addUniversalDistillationRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt) {
        if (aOutputs.length > 0) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 0L, new Object[0]), aInput, aOutputs[0], aDuration * 2, aEUt / 4, false);
        }
        if (aOutputs.length > 1) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), aInput, aOutputs[1], aDuration * 2, aEUt / 4, false);
        }
        if (aOutputs.length > 2) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), aInput, aOutputs[2], aDuration * 2, aEUt / 4, false);
        }
        if (aOutputs.length > 3) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0]), aInput, aOutputs[3], aDuration * 2, aEUt / 4, false);
        }
        if (aOutputs.length > 4) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), aInput, aOutputs[4], aDuration * 2, aEUt / 4, false);
        }
        if (aOutputs.length > 5) {
            addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 5L, new Object[0]), aInput, aOutputs[5], aDuration * 2, aEUt / 4, false);
        }

        return addDistillationTowerRecipe(aInput, aOutputs, aOutput2, aDuration, aEUt);
    }

    public boolean addDistillationTowerRecipe(FluidStack aInput, FluidStack[] aOutputs, ItemStack aOutput2, int aDuration, int aEUt) {
        if (aInput == null || aOutputs == null || aOutputs.length < 1 || aOutputs.length > 5) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("distillation", aInput.getUnlocalizedName(), aDuration)) <= 0) {
            return false;
        }
        new GT_Recipe.DistillationTowerRecipe(aInput, aOutputs, aOutput2, aDuration, aEUt);
        return false;
    }

    public boolean addVacuumFreezerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("vacuumfreezer", aInput1, aDuration)) <= 0) {
            return false;
        }
        new GT_Recipe.VacuumRecipe(aInput1, aOutput1, aDuration);
        return true;
    }

    public boolean addGrinderRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4) {
        return false;
    }

    public boolean addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType) {
        if (aInput1 == null) {
            return false;
        }
        new GT_Recipe.FuelRecipe(aInput1, aOutput1, GregTech_API.sRecipeFile.get("fuel_" + aType, aInput1, aEU), aType);
        return true;
    }

//    public boolean addSonictronSound(ItemStack aItemStack, String aSoundName) {
//        if ((aItemStack == null) || (aSoundName == null) || (aSoundName.equals(""))) {
//            return false;
//        }
//        GT_Mod.gregtechproxy.mSoundItems.add(aItemStack);
//        GT_Mod.gregtechproxy.mSoundNames.add(aSoundName);
//        if (aSoundName.startsWith("note.")) {
//            GT_Mod.gregtechproxy.mSoundCounts.add(25);
//        } else {
//            GT_Mod.gregtechproxy.mSoundCounts.add(1);
//        }
//        return true;
//    }

    public boolean addForgeHammerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
        if ((aInput1 == null) || (aOutput1 == null)) {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("forgehammer", aOutput1, true)) {
            return false;
        }
        RecipeMap.HAMMER_RECIPES.addRecipe(true, new ItemStack[]{aInput1}, new ItemStack[]{aOutput1}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addBoxingRecipe(ItemStack aContainedItem, ItemStack aEmptyBox, ItemStack aFullBox, int aDuration, int aEUt) {
        if ((aContainedItem == null) || (aFullBox == null)) {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("boxing", aFullBox, true)) {
            return false;
        }
        RecipeMap.BOXINATOR_RECIPES.addRecipe(true, new ItemStack[]{aContainedItem, aEmptyBox}, new ItemStack[]{aFullBox}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addUnboxingRecipe(ItemStack aFullBox, ItemStack aContainedItem, ItemStack aEmptyBox, int aDuration, int aEUt) {
        if ((aFullBox == null) || (aContainedItem == null)) {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("unboxing", aFullBox, true)) {
            return false;
        }
        RecipeMap.UNBOXINATOR_RECIPES.addRecipe(true, new ItemStack[]{aFullBox}, new ItemStack[]{aContainedItem, aEmptyBox}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addAmplifier(ItemStack aAmplifierItem, int aDuration, int aAmplifierAmountOutputted) {
        if ((aAmplifierItem == null) || (aAmplifierAmountOutputted <= 0)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("amplifier", aAmplifierItem, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.AMPLIFIERS.addRecipe(true, new ItemStack[]{aAmplifierItem}, null, null, null, new FluidStack[]{Materials.UUAmplifier.getFluid(aAmplifierAmountOutputted)}, aDuration, 32, 0);
        return true;
    }

    public boolean addBrewingRecipe(ItemStack aIngredient, Fluid aInput, Fluid aOutput, boolean aHidden) {
        if ((aIngredient == null) || (aInput == null) || (aOutput == null)) {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("brewing", aOutput.getUnlocalizedName(), true)) {
            return false;
        }
        GT_Recipe tRecipe = RecipeMap.BREWING_RECIPES.addRecipe(false, new ItemStack[]{aIngredient}, null, null, new FluidStack[]{new FluidStack(aInput, 750)}, new FluidStack[]{new FluidStack(aOutput, 750)}, 128, 4, 0);
        if ((aHidden) && (tRecipe != null)) {
            tRecipe.hidden = true;
        }
        return true;
    }

    public boolean addFermentingRecipe(FluidStack aInput, FluidStack aOutput, int aDuration, boolean aHidden) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("fermenting", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
            return false;
        }
        GT_Recipe tRecipe = RecipeMap.FERMENTING_RECIPES.addRecipe(false, null, null, null, new FluidStack[]{aInput}, new FluidStack[]{aOutput}, aDuration, 2, 0);
        if ((aHidden) && (tRecipe != null)) {
            tRecipe.hidden = true;
        }
        return true;
    }

    public boolean addDistilleryRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt, boolean aHidden) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("distillery", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
            return false;
        }
        GT_Recipe tRecipe = RecipeMap.DISTILLERY_RECIPES.addRecipe(true, new ItemStack[]{aCircuit}, null, null, new FluidStack[]{aInput}, new FluidStack[]{aOutput}, aDuration, aEUt, 0);
        if ((aHidden) && (tRecipe != null)) {
            tRecipe.hidden = true;
        }
        return true;
    }

    public boolean addFluidSolidifierRecipe(ItemStack aMold, FluidStack aInput, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aMold == null) || (aInput == null) || (aOutput == null)) {
            return false;
        }
        if (aInput.isFluidEqual(Materials.PhasedGold.getMolten(144))) {
            aInput = Materials.VibrantAlloy.getMolten(aInput.amount);
        }
        if (aInput.isFluidEqual(Materials.PhasedIron.getMolten(144))) {
            aInput = Materials.PulsatingIron.getMolten(aInput.amount);
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("fluidsolidifier", aOutput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.FLUID_SOLIDFICATION_RECIPES.addRecipe(true, new ItemStack[]{aMold}, new ItemStack[]{aOutput}, null, new FluidStack[]{aInput}, null, aDuration, aEUt, 0);
        return true;
    }
    
	@Override
	public boolean addFluidSmelterRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt) {
		return addFluidSmelterRecipe(aInput, aRemains, aOutput, aChance, aDuration, aEUt, false);
	}

    public boolean addFluidSmelterRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt, boolean hidden) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if (aOutput.isFluidEqual(Materials.PhasedGold.getMolten(1))) {
            aOutput = Materials.VibrantAlloy.getMolten(aOutput.amount);
        }
        if (aOutput.isFluidEqual(Materials.PhasedIron.getMolten(1))) {
            aOutput = Materials.PulsatingIron.getMolten(aOutput.amount);
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("fluidsmelter", aInput, aDuration)) <= 0) {
            return false;
        }
        GT_Recipe tRecipe = RecipeMap.FLUID_EXTRACTION_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aRemains}, null, new int[]{aChance}, null, new FluidStack[]{aOutput}, aDuration, aEUt, 0);
        if ((hidden) && (tRecipe != null)) {
           tRecipe.hidden = true;
        }
        return true;
    }

    public boolean addFluidExtractionRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if (aOutput.isFluidEqual(Materials.PhasedGold.getMolten(1))) {
            aOutput = Materials.VibrantAlloy.getMolten(aOutput.amount);
        }
        if (aOutput.isFluidEqual(Materials.PhasedIron.getMolten(1))) {
            aOutput = Materials.PulsatingIron.getMolten(aOutput.amount);
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("fluidextractor", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.FLUID_EXTRACTION_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aRemains}, null, new int[]{aChance}, null, new FluidStack[]{aOutput}, aDuration, aEUt, 0);
        return true;
    }

    public boolean addFluidCannerRecipe(ItemStack aInput, ItemStack aOutput, FluidStack aFluidInput, FluidStack aFluidOutput) {
        if ((aInput != null) && (aOutput != null)) {
            if ((aFluidInput == null ? 1 : 0) != (aFluidOutput == null ? 1 : 0)) {
            }
        } else {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("fluidcanner", aOutput, true)) {
            return false;
        }
        RecipeMap.FLUID_CANNER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, null, new FluidStack[]{aFluidInput}, new FluidStack[]{aFluidOutput}, aFluidOutput == null ? aFluidInput.amount / 62 : aFluidOutput.amount / 62, 1, 0);
        return true;
    }

    public boolean addChemicalBathRecipe(ItemStack aInput, FluidStack aBathingFluid, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt) {
        if ((aInput == null) || (aBathingFluid == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("chemicalbath", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.CHEMICAL_BATH_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2, aOutput3}, null, aChances, new FluidStack[]{aBathingFluid}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addElectromagneticSeparatorRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput1 == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("electromagneticseparator", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput1, aOutput2, aOutput3}, null, aChances, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addPrinterRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aSpecialSlot, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aFluid == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("printer", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.PRINTER_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, aSpecialSlot, null, new FluidStack[]{aFluid}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addAutoclaveRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aOutput, int aChance, int aDuration, int aEUt) {
        if ((aInput == null) || (aFluid == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("autoclave", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.AUTOCLAVE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, new ItemStack[]{aOutput}, null, new int[]{aChance}, new FluidStack[]{aFluid}, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addMixerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aInput3, ItemStack aInput4, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration, int aEUt) {
        if (((aInput1 == null) && (aFluidInput == null)) || ((aOutput == null) && (aFluidOutput == null))) {
            return false;
        }
        if ((aOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("mixer", aOutput, aDuration)) <= 0)) {
            return false;
        }
        if ((aFluidOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("mixer", aFluidOutput.getFluid().getName(), aDuration)) <= 0)) {
            return false;
        }
        RecipeMap.MIXER_RECIPES.addRecipe(true, new ItemStack[]{aInput1, aInput2, aInput3, aInput4}, new ItemStack[]{aOutput}, null, null, new FluidStack[]{aFluidInput}, new FluidStack[]{aFluidOutput}, aDuration, aEUt, 0);
        return true;
    }

    public boolean addLaserEngraverRecipe(ItemStack aItemToEngrave, ItemStack aLens, ItemStack aEngravedItem, int aDuration, int aEUt) {
        if ((aItemToEngrave == null) || (aLens == null) || (aEngravedItem == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("laserengraving", aEngravedItem, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.LASER_ENGRAVER_RECIPES.addRecipe(true, new ItemStack[]{aItemToEngrave, aLens}, new ItemStack[]{aEngravedItem}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addFormingPressRecipe(ItemStack aItemToImprint, ItemStack aForm, ItemStack aImprintedItem, int aDuration, int aEUt) {
        if ((aItemToImprint == null) || (aForm == null) || (aImprintedItem == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("press", aImprintedItem, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.PRESS_RECIPES.addRecipe(true, new ItemStack[]{aItemToImprint, aForm}, new ItemStack[]{aImprintedItem}, null, null, null, aDuration, aEUt, 0);
        return true;
    }

    public boolean addFluidHeaterRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("fluidheater", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
            return false;
        }
        RecipeMap.FLUID_HEATER_RECIPES.addRecipe(true, new ItemStack[]{aCircuit}, null, null, new FluidStack[]{aInput}, new FluidStack[]{aOutput}, aDuration, aEUt, 0);
        return true;
    }

    public boolean addSifterRecipe(ItemStack aItemToSift, ItemStack[] aSiftedItems, int[] aChances, int aDuration, int aEUt) {
        if ((aItemToSift == null) || (aSiftedItems == null)) {
            return false;
        }
        for (ItemStack tStack : aSiftedItems) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("sifter", aItemToSift, aDuration)) <= 0) {
                    return false;
                }
                RecipeMap.SIFTER_RECIPES.addRecipe(true, new ItemStack[]{aItemToSift}, aSiftedItems, null, aChances, null, null, aDuration, aEUt, 0);
                return true;
            }
        }
        return false;
    }
    

	@Override
	public boolean addArcFurnaceRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt) {
		return addArcFurnaceRecipe(aInput, aOutputs, aChances, aDuration, aEUt,	false);
	}

    public boolean addArcFurnaceRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt,	boolean hidden) {
        if ((aInput == null) || (aOutputs == null)) {
            return false;
        }
        for (ItemStack tStack : aOutputs) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("arcfurnace", aInput, aDuration)) <= 0) {
                    return false;
                }
                GT_Recipe sRecipe = RecipeMap.ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{Materials.Oxygen.getGas(aDuration)}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
                if ((hidden) && (sRecipe != null)) {
                   sRecipe.hidden = true;
                }
                for (Materials tMaterial : new Materials[]{Materials.Argon, Materials.Nitrogen}) {
                    if (tMaterial.mPlasma != null) {
                        int tPlasmaAmount = (int) Math.max(1L, aDuration / (tMaterial.getMass() * 16L));
                        GT_Recipe tRecipe = RecipeMap.PLASMA_ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{tMaterial.getPlasma(tPlasmaAmount)}, new FluidStack[]{tMaterial.getGas(tPlasmaAmount)}, Math.max(1, aDuration / 16), Math.max(1, aEUt / 3), 0);
                        if ((hidden) && (tRecipe != null)) {
                           tRecipe.hidden = true;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean addSimpleArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutputs == null) || aFluidInput == null) {
            return false;
        }
        for (ItemStack tStack : aOutputs) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("arcfurnace", aInput, aDuration)) <= 0) {
                    return false;
                }
                RecipeMap.ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{aFluidInput}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
                return true;
            }
        }
        return false;
    }

    public boolean addPlasmaArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutputs == null) || aFluidInput == null) {
            return false;
        }
        for (ItemStack tStack : aOutputs) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("arcfurnace", aInput, aDuration)) <= 0) {
                    return false;
                }
                RecipeMap.PLASMA_ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{aFluidInput}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
                return true;
            }
        }
        return false;
    }

    public boolean addPlasmaArcFurnaceRecipe(ItemStack aInput, FluidStack aFluidInput, ItemStack[] aOutputs, FluidStack aFluidOutput, int[] aChances, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutputs == null) || aFluidInput == null) {
            return false;
        }
        for (ItemStack tStack : aOutputs) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("arcfurnace", aInput, aDuration)) <= 0) {
                    return false;
                }
                RecipeMap.PLASMA_ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{aFluidInput}, new FluidStack[]{aFluidOutput}, Math.max(1, aDuration), Math.max(1, aEUt), 0);
                return true;
            }
        }
        return false;
    }
    

	@Override
	public boolean addPulveriserRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt) {
		return addPulveriserRecipe(aInput, aOutputs, aChances, aDuration, aEUt, false);
	}

    public boolean addPulveriserRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt, boolean hidden) {
        if ((aInput == null) || (aOutputs == null)) {
            return false;
        }
        for (ItemStack tStack : aOutputs) {
            if (tStack != null) {
                if ((aDuration = GregTech_API.sRecipeFile.get("pulveriser", aInput, aDuration)) <= 0) {
                    return false;
                }
                GT_Recipe tRecipe = RecipeMap.MACERATOR_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, null, null, aDuration, aEUt, 0);
                if ((hidden) && (tRecipe != null)) {
                   tRecipe.hidden = true;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addPyrolyseRecipe(ItemStack aInput, FluidStack aFluidInput, int intCircuit, ItemStack aOutput, FluidStack aFluidOutput, int aDuration, int aEUt) {
        if (aInput == null) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("pyrolyse", aInput, aDuration)) <= 0) {
            return false;
        }
        RecipeMap.PYROLYSE_RECIPES.addRecipe(false, new ItemStack[]{aInput, ItemList.Circuit_Integrated.getWithDamage(0L, intCircuit, new Object[0])}, new ItemStack[]{aOutput}, null, null, new FluidStack[]{aFluidInput}, new FluidStack[]{aFluidOutput}, aDuration, aEUt, 0);
        return true;
    }

    @Override
    public boolean addCrackingRecipe(FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt) {
        if ((aInput == null) || (aOutput == null)) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("cracking", aInput.getUnlocalizedName(), aDuration)) <= 0) {
            return false;
        }
        RecipeMap.CRAKING_RECIPES.addRecipe(true, null, null, null, null, new FluidStack[]{aInput}, new FluidStack[]{aOutput}, aDuration, aEUt, 0);
        RecipeMap.CRAKING_RECIPES.addRecipe(true, null, null, null, null, new FluidStack[]{aInput, GT_ModHandler.getSteam(aInput.amount)}, new FluidStack[]{aOutput, Materials.Hydrogen.getGas(aInput.amount)}, aDuration, aEUt, 0);
        RecipeMap.CRAKING_RECIPES.addRecipe(true, null, null, null, null, new FluidStack[]{aInput, Materials.Hydrogen.getGas(aInput.amount)}, new FluidStack[]{new FluidStack(aOutput.getFluid(), (int) (aOutput.amount * 1.3))}, aDuration, aEUt, 0);
        return true;
    }

	@Override
	public boolean addAssemblylineRecipe(ItemStack aResearchItem, int aResearchTime, ItemStack[] aInputs, FluidStack[] aFluidInputs, ItemStack aOutput, int aDuration, int aEUt) {
        if ((aResearchItem==null)||(aResearchTime<=0)||(aInputs == null) || (aOutput == null) || aInputs.length>15 || aInputs.length<4) {
            return false;
        }
        if ((aDuration = GregTech_API.sRecipeFile.get("assemblingline", aOutput, aDuration)) <= 0) {
            return false;
        } 
        for(ItemStack tItem : aInputs){
        	if(tItem==null)System.out.println("addAssemblylineRecipe"+aResearchItem.getDisplayName());
        }
        RecipeMap.SCANNER_FAKE_RECIPES.addFakeRecipe(false, new ItemStack[]{aResearchItem}, new ItemStack[]{aOutput}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1L, "Research result", new Object[0])}, null, null, aResearchTime, 30, 0);
        
        GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes.add(new GT_Recipe.GT_Recipe_AssemblyLine( aResearchItem, aResearchTime, aInputs, aFluidInputs, aOutput, aDuration, aEUt));
        return true;
	}




}
