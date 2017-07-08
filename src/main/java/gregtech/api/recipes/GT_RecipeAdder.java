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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GT_RecipeAdder implements IGT_RecipeAdder {


    /**
     * Adds a Centrifuge Recipe
     *
     * Inputs Non-null
     * Outputs Nullable
     *
     * @return true if the Recipe got added, otherwise false.
     */
    // TODO RecipeBuilder cells
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
    //done
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
    //done
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

//    public boolean addCNCRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
//        if ((aInput1 == null) || (aOutput1 == null)) {
//            return false;
//        }
//        if ((GregTech_API.sRecipeFile.get("cnc", aOutput1, aDuration)) <= 0) {
//            return false;
//        }
//        return true;
//    }

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
        	new Recipe.ImplosionRecipe(aInput1, ItemList.Block_Powderbarrel.get(tGunpowder), aOutput1, aOutput2);
        }
        if(tDynamite < 17){
			new Recipe.ImplosionRecipe(aInput1, GT_ModHandler.getIC2Item(ItemName.dynamite, tDynamite), aOutput1, aOutput2);
        }
		new Recipe.ImplosionRecipe(aInput1, new ItemStack(Blocks.TNT, tTNT), aOutput1, aOutput2);
		new Recipe.ImplosionRecipe(aInput1, GT_ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, tITNT), aOutput1, aOutput2);

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
        new Recipe.DistillationTowerRecipe(aInput, aOutputs, aOutput2, aDuration, aEUt);
        return false;
    }

    public boolean addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType) {
        if (aInput1 == null) {
            return false;
        }
        new Recipe.FuelRecipe(aInput1, aOutput1, GregTech_API.sRecipeFile.get("fuel_" + aType, aInput1, aEU), aType);
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

    // TODO separate RecipeBuilder for this one or just set FluidStack amount to 750 in anonymous RecipeBuilder
    public boolean addBrewingRecipe(ItemStack aIngredient, Fluid aInput, Fluid aOutput, boolean aHidden) {
        if ((aIngredient == null) || (aInput == null) || (aOutput == null)) {
            return false;
        }
        if (!GregTech_API.sRecipeFile.get("brewing", aOutput.getUnlocalizedName(), true)) {
            return false;
        }

        Recipe tRecipe = RecipeMap.BREWING_RECIPES.addRecipe(false, new ItemStack[]{aIngredient}, null, null, new FluidStack[]{new FluidStack(aInput, 750)}, new FluidStack[]{new FluidStack(aOutput, 750)}, 128, 4, 0);
        if ((aHidden) && (tRecipe != null)) {
            tRecipe.hidden = true;
        }
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
                Recipe sRecipe = RecipeMap.ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{Materials.Oxygen.getGas(aDuration)}, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
                if ((hidden) && (sRecipe != null)) {
                   sRecipe.hidden = true;
                }
                for (Materials tMaterial : new Materials[]{Materials.Argon, Materials.Nitrogen}) {
                    if (tMaterial.mPlasma != null) {
                        int tPlasmaAmount = (int) Math.max(1L, aDuration / (tMaterial.getMass() * 16L));
                        Recipe tRecipe = RecipeMap.PLASMA_ARC_FURNACE_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, new FluidStack[]{tMaterial.getPlasma(tPlasmaAmount)}, new FluidStack[]{tMaterial.getGas(tPlasmaAmount)}, Math.max(1, aDuration / 16), Math.max(1, aEUt / 3), 0);
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
                Recipe tRecipe = RecipeMap.MACERATOR_RECIPES.addRecipe(true, new ItemStack[]{aInput}, aOutputs, null, aChances, null, null, aDuration, aEUt, 0);
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
        
        Recipe.GT_Recipe_AssemblyLine.ASSEMBLYLINE_RECIPES.add(new Recipe.GT_Recipe_AssemblyLine( aResearchItem, aResearchTime, aInputs, aFluidInputs, aOutput, aDuration, aEUt));
        return true;
	}

}
