package gregtech.loaders.oreprocessing;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ProcessingOre implements IOreRegistrationHandler {
    private ArrayList<Materials> mAlreadyListedOres = new ArrayList(1000);

    public ProcessingOre() {
        for (OrePrefix tPrefix : OrePrefix.values()) {
            if ((tPrefix.name().startsWith("ore")) && (tPrefix != OrePrefix.orePoor) && (tPrefix != OrePrefix.oreSmall) && (tPrefix != OrePrefix.oreRich) && (tPrefix != OrePrefix.oreNormal)) {
                tPrefix.addProcessingHandler(this);
            }
        }
    }

    public void registerOre(UnificationEntry uEntry, String modame, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean tIsRich = (uEntry.orePrefix == OrePrefix.oreNether) || (uEntry.orePrefix == OrePrefix.oreEnd) || (uEntry.orePrefix == OrePrefix.oreDense);

        if (uEntry.material == Materials.Oilsands) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1, stack), null, null, Materials.Oil.getFluid(tIsRich ? 1000 : 500), new ItemStack(net.minecraft.init.Blocks.SAND, 1, 0), null, null, null, null, null, new int[]{tIsRich ? '?' : '?'}, tIsRich ? 2000 : 1000, 5);
        } else {
            registerStandardOreRecipes(uEntry.orePrefix, uEntry.material, GT_Utility.copyAmount(1, stack), Math.max(1, GregTech_API.sOPStuff.get(ConfigCategories.Materials.oreprocessingoutputmultiplier, uEntry.material.toString(), 1)) * (tIsRich ? 2 : 1));
        }
    }

    private boolean registerStandardOreRecipes(OrePrefix prefix, Material material, ItemStack aOreStack, int multiplier) {
        if ((aOreStack == null) || (material == null)) return false;
        ModHandler.addValuableOre(GT_Utility.getBlockFromStack(aOreStack), aOreStack.getItemDamage(), material.mOreValue);
        Materials tMaterial = material.mOreReplacement;
        Materials tPrimaryByMaterial = null;
        multiplier = Math.max(1, multiplier);
        aOreStack = GT_Utility.copyAmount(1, aOreStack);
        aOreStack.stackSize = 1;


        ItemStack tIngot = OreDictionaryUnifier.get(OrePrefix.ingot, material.mDirectSmelting, 1L);
        ItemStack tGem = OreDictionaryUnifier.get(OrePrefix.gem, tMaterial, 1L);
        ItemStack tSmeltInto = tIngot == null ? null : material.contains(SubTag.SMELTING_TO_GEM) ? OreDictionaryUnifier.get(OrePrefix.gem, tMaterial.mDirectSmelting, OreDictionaryUnifier.get(OrePrefix.crystal, tMaterial.mDirectSmelting, OreDictionaryUnifier.get(OrePrefix.gem, tMaterial, OreDictionaryUnifier.get(OrePrefix.crystal, tMaterial, 1L), 1L), 1L), 1L) : tIngot;

        ItemStack tDust = OreDictionaryUnifier.get(OrePrefix.dust, tMaterial, tGem, 1);
        ItemStack tCleaned = OreDictionaryUnifier.get(OrePrefix.crushedPurified, tMaterial, tDust, 1);
        ItemStack tCrushed = OreDictionaryUnifier.get(OrePrefix.crushed, tMaterial, material.mOreMultiplier * multiplier);
        ItemStack tPrimaryByProduct = null;

        if (tCrushed == null) {
            tCrushed = OreDictionaryUnifier.get(OrePrefix.dustImpure, tMaterial, GT_Utility.copyAmount(material.mOreMultiplier * multiplier, tCleaned, tDust, tGem), material.mOreMultiplier * multiplier);
        }

        ArrayList<ItemStack> tByProductStacks = new ArrayList();

        for (Materials tMat : material.mOreByProducts) {
            ItemStack tByProduct = OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
            if (tByProduct != null) tByProductStacks.add(tByProduct);
            if (tPrimaryByProduct == null) {
                tPrimaryByMaterial = tMat;
                tPrimaryByProduct = OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
                if (OreDictionaryUnifier.get(OrePrefix.dustSmall, tMat, 1) == null)
                    OreDictionaryUnifier.get(OrePrefix.dustTiny, tMat, OreDictionaryUnifier.get(OrePrefix.nugget, tMat, 2L), 2L);
            }
            OreDictionaryUnifier.get(OrePrefix.dust, tMat, 1L);
            if (OreDictionaryUnifier.get(OrePrefix.dustSmall, tMat, 1) == null)
                OreDictionaryUnifier.get(OrePrefix.dustTiny, tMat, OreDictionaryUnifier.get(OrePrefix.nugget, tMat, 2L), 2L);
        }
        if ((!tByProductStacks.isEmpty()) && (!this.mAlreadyListedOres.contains(material))) {
            this.mAlreadyListedOres.add(material);
            GT_Recipe.GT_Recipe_Map.sByProductList.addFakeRecipe(false, new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.ore, material, aOreStack, 1L)}, tByProductStacks.toArray(new ItemStack[tByProductStacks.size()]), null, null, null, null, 0, 0, 0);
        }

        if (tPrimaryByMaterial == null) tPrimaryByMaterial = tMaterial;
        if (tPrimaryByProduct == null) tPrimaryByProduct = tDust;
        boolean tHasSmelting = false;

        if (tSmeltInto != null) {
            if ((material.mBlastFurnaceRequired) || (material.mDirectSmelting.mBlastFurnaceRequired)) {
                ModHandler.removeFurnaceSmelting(aOreStack);
            } else {
                if (GT_Mod.gregtechproxy.mTEMachineRecipes) {
                    ModHandler.addInductionSmelterRecipe(aOreStack, new ItemStack(net.minecraft.init.Blocks.SAND, 1), GT_Utility.mul(multiplier * (material.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 1 : 2) * material.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag_Rich.get(1L), 300 * multiplier, 10 * multiplier);
                    ModHandler.addInductionSmelterRecipe(aOreStack, ItemList.TE_Slag_Rich.get(multiplier), GT_Utility.mul(multiplier * (material.contains(SubTag.INDUCTIONSMELTING_LOW_OUTPUT) ? 2 : 3) * material.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(multiplier), 300 * multiplier, 95);
                }
                tHasSmelting = ModHandler.addSmeltingRecipe(aOreStack, GT_Utility.copyAmount(multiplier * material.mSmeltingMultiplier, tSmeltInto));
            }

            if (material.contains(SubTag.BLASTFURNACE_CALCITE_TRIPLE)) {
                GT_Values.RA.addBlastRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Calcite, multiplier), null, null, GT_Utility.mul(multiplier * 3 * material.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(1L, OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1L)), tSmeltInto.stackSize * 500, 120, 1500);
            } else if (material.contains(SubTag.BLASTFURNACE_CALCITE_DOUBLE)) {
                GT_Values.RA.addBlastRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Calcite, multiplier), null, null, GT_Utility.mul(multiplier * 2 * material.mSmeltingMultiplier, tSmeltInto), ItemList.TE_Slag.get(1L, OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1L)), tSmeltInto.stackSize * 500, 120, 1500);
            }
        }

        if (!tHasSmelting) {
            ModHandler.addSmeltingRecipe(aOreStack, OreDictionaryUnifier.get(OrePrefix.gem, tMaterial.mDirectSmelting, Math.max(1, multiplier * material.mSmeltingMultiplier / 2)));
        }

        if (tCrushed != null) {
            GT_Values.RA.addForgeHammerRecipe(aOreStack, GT_Utility.copy(GT_Utility.copyAmount(tCrushed.stackSize, tGem), tCrushed), 16, 10);
            ModHandler.addPulverisationRecipe(aOreStack, GT_Utility.mul(2L, tCrushed), tMaterial.contains(SubTag.PULVERIZING_CINNABAR) ? OreDictionaryUnifier.get(OrePrefix.crystal, Materials.Cinnabar, OreDictionaryUnifier.get(OrePrefix.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1, tPrimaryByProduct), 1), 1) : OreDictionaryUnifier.get(OrePrefix.gem, tPrimaryByMaterial, GT_Utility.copyAmount(1L, tPrimaryByProduct), 1L), tPrimaryByProduct == null ? 0 : tPrimaryByProduct.stackSize * 10 * multiplier * material.mByProductMultiplier, OreDictionaryUnifier.getDust(prefix.mSecondaryMaterial), 50, true);
        }
        return true;
    }
}
