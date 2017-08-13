package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ProcessingOre implements IOreRegistrationHandler {
    private ArrayList<Materials> mAlreadyListedOres = new ArrayList(1000);

    public ProcessingOre() {
        for (OrePrefix tPrefix : OrePrefix.values())
            if ((tPrefix.name().startsWith("ore")) && (tPrefix != OrePrefix.orePoor) && (tPrefix != OrePrefix.oreSmall) && (tPrefix != OrePrefix.oreRich) && (tPrefix != OrePrefix.oreNormal))
                tPrefix.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        boolean tIsRich = (aPrefix == OrePrefix.oreNether) || (aPrefix == OrePrefix.oreEnd) || (aPrefix == OrePrefix.oreDense);

        if (aMaterial == Materials.Oilsands) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, aStack), null, null, Materials.Oil.getFluid(tIsRich ? 1000L : 500L), new ItemStack(net.minecraft.init.Blocks.SAND, 1, 0), null, null, null, null, null, new int[]{tIsRich ? '?' : '?'}, tIsRich ? 2000 : 1000, 5);
        } else {
            registerStandardOreRecipes(aPrefix, aMaterial, GT_Utility.copyAmount(1L, aStack), Math.max(1, gregtech.api.GregTech_API.sOPStuff.get(ConfigCategories.Materials.oreprocessingoutputmultiplier, aMaterial.toString(), 1)) * (tIsRich ? 2 : 1));
        }
    }

    private boolean registerStandardOreRecipes(OrePrefix aPrefix, Materials aMaterial, ItemStack aOreStack, int aMultiplier) {
        if ((aOreStack == null) || (aMaterial == null)) return false;
        GT_ModHandler.addValuableOre(GT_Utility.getBlockFromStack(aOreStack), aOreStack.getItemDamage(), aMaterial.mOreValue);
        Materials tMaterial = aMaterial.mOreReplacement;
        Materials tPrimaryByMaterial = null;
        aMultiplier = Math.max(1, aMultiplier);
        aOreStack = GT_Utility.copyAmount(1L, aOreStack);
        aOreStack.stackSize = 1;


        ItemStack tIngot = OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial.mDirectSmelting, 1L);
        ItemStack tGem = OreDictionaryUnifier.get(OrePrefix.gem, tMaterial, 1L);
        ItemStack tSmeltInto = tIngot == null ? null : aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OreDictionaryUnifier.get(OrePrefix.gem, tMaterial.mDirectSmelting, OreDictionaryUnifier.get(OrePrefix.crystal, tMaterial.mDirectSmelting, OreDictionaryUnifier.get(OrePrefix.gem, tMaterial, OreDictionaryUnifier.get(OrePrefix.crystal, tMaterial, 1L), 1L), 1L), 1L) : tIngot;

        ItemStack tDust = OreDictionaryUnifier.get(OrePrefix.dust, tMaterial, tGem, 1L);
        ItemStack tCleaned = OreDictionaryUnifier.get(OrePrefix.crushedPurified, tMaterial, tDust, 1L);
        ItemStack tCrushed = OreDictionaryUnifier.get(OrePrefix.crushed, tMaterial, aMaterial.mOreMultiplier * aMultiplier);
        ItemStack tPrimaryByProduct = null;

        if (tCrushed == null) {
            tCrushed = OreDictionaryUnifier.get(OrePrefix.dustImpure, tMaterial, GT_Utility.copyAmount(aMaterial.mOreMultiplier * aMultiplier, tCleaned, tDust, tGem), aMaterial.mOreMultiplier * aMultiplier);
        }

        ArrayList<ItemStack> tByProductStacks = new ArrayList();

        for (Materials tMat : aMaterial.mOreByProducts) {
            ItemStack tByProduct = OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
            if (tByProduct != null) tByProductStacks.add(tByProduct);
            if (tPrimaryByProduct == null) {
                tPrimaryByMaterial = tMat;
                tPrimaryByProduct = OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
                if (OreDictionaryUnifier.get(OrePrefix.dustSmall, tMat, 1L) == null)
                    OreDictionaryUnifier.get(OrePrefix.dustTiny, tMat, OreDictionaryUnifier.get(OrePrefix.nugget, tMat, 2L), 2L);
            }
            OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
            if (OreDictionaryUnifier.get(OrePrefix.dustSmall, tMat, 1L) == null)
                OreDictionaryUnifier.get(OrePrefix.dustTiny, tMat, OreDictionaryUnifier.get(OrePrefix.nugget, tMat, 2L), 2L);
        }
        if ((!tByProductStacks.isEmpty()) && (!this.mAlreadyListedOres.contains(aMaterial))) {
            this.mAlreadyListedOres.add(aMaterial);
            gregtech.api.util.GT_Recipe.GT_Recipe_Map.sByProductList.addFakeRecipe(false, new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.ore, aMaterial, aOreStack, 1L)}, tByProductStacks.toArray(new ItemStack[tByProductStacks.size()]), null, null, null, null, 0, 0, 0);
        }

        if (tPrimaryByMaterial == null) tPrimaryByMaterial = tMaterial;
        if (tPrimaryByProduct == null) tPrimaryByProduct = tDust;
        boolean tHasSmelting = false;

        if (tSmeltInto != null) {
            if ((aMaterial.mBlastFurnaceRequired) || (aMaterial.mDirectSmelting.mBlastFurnaceRequired)) {
                GT_ModHandler.removeFurnaceSmelting(aOreStack);
            } else {
                if (GT_Mod.gregtechproxy.mTEMachineRecipes) {
                    GT_ModHandler.addInductionSmelterRecipe(aOreStack, new ItemStack(net.minecraft.init.Blocks.SAND, 1), GT_Utility.mul(aMultiplier * (aMaterial.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 1 : 2) * aMaterial.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag_Rich.get(1L), 300 * aMultiplier, 10 * aMultiplier);
                    GT_ModHandler.addInductionSmelterRecipe(aOreStack, ItemList.TE_Slag_Rich.get(aMultiplier), GT_Utility.mul(aMultiplier * (aMaterial.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 2 : 3) * aMaterial.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(aMultiplier), 300 * aMultiplier, 95);
                }
                tHasSmelting = GT_ModHandler.addSmeltingRecipe(aOreStack, GT_Utility.copyAmount(aMultiplier * aMaterial.mSmeltingMultiplier, tSmeltInto));
            }

            if (aMaterial.contains(SubTag.BLASTFURNACE_CALCITE_TRIPLE)) {
                GT_Values.RA.addBlastRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Calcite, aMultiplier), null, null, GT_Utility.mul(aMultiplier * 3 * aMaterial.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(1L, OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1L)), tSmeltInto.stackSize * 500, 120, 1500);
            } else if (aMaterial.contains(SubTag.BLASTFURNACE_CALCITE_DOUBLE)) {
                GT_Values.RA.addBlastRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Calcite, aMultiplier), null, null, GT_Utility.mul(aMultiplier * 2 * aMaterial.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(1L, OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1L)), tSmeltInto.stackSize * 500, 120, 1500);
            }
        }

        if (!tHasSmelting) {
            GT_ModHandler.addSmeltingRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.gem, tMaterial.mDirectSmelting, Math.max(1, aMultiplier * aMaterial.mSmeltingMultiplier / 2)));
        }

        if (tCrushed != null) {
            GT_Values.RA.addForgeHammerRecipe(aOreStack, GT_Utility.copy(GT_Utility.copyAmount(tCrushed.stackSize, tGem), tCrushed), 16, 10);
            GT_ModHandler.addPulverisationRecipe(aOreStack, GT_Utility.mul(2L, tCrushed), tMaterial.contains(SubTag.PULVERIZING_CINNABAR) ? OreDictionaryUnifier.get(OrePrefix.crystal, Materials.Cinnabar, OreDictionaryUnifier.get(OrePrefix.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1L, tPrimaryByProduct), 1L), 1L) : OreDictionaryUnifier.get(OrePrefix.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1L, tPrimaryByProduct), 1L), tPrimaryByProduct == null ? 0 : tPrimaryByProduct.stackSize * 10 * aMultiplier * aMaterial.mByProductMultiplier, OreDictionaryUnifier.getDust(aPrefix.mSecondaryMaterial), 50, true);
        }
        return true;
    }
}
