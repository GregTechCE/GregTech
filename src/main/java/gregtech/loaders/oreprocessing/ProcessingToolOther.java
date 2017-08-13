package gregtech.loaders.oreprocessing;

import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.Dyes;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingToolOther implements IOreRegistrationHandler {
    public ProcessingToolOther() {
        OrePrefix.toolHeadHammer.add(this);
    }

    @Override
    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if ((aMaterial != Materials.Stone) && (aMaterial != Materials.Flint)) {
            if (aMaterial != Materials.Rubber) {
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.PLUNGER, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"xRR", " SR", "S f", 'S', OrePrefix.stick.get(aMaterial), 'R', OrePrefix.plate.get(Materials.Rubber)});
            }
            if ((!aMaterial.contains(SubTag.WOOD)) && (!aMaterial.contains(SubTag.BOUNCY)) && (!aMaterial.contains(SubTag.NO_SMASHING))) {
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.WRENCH, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"IhI", "III", " I ", 'I', OrePrefix.ingot.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.CROWBAR, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"hDS", "DSD", "SDf", 'S', OrePrefix.stick.get(aMaterial), 'D', Dyes.dyeBlue});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.SCREWDRIVER, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{" fS", " Sh", "W  ", 'S', OrePrefix.stick.get(aMaterial), 'W', OrePrefix.stick.get(aMaterial.mHandleMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.WIRECUTTER, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"PfP", "hPd", "STS", 'S', OrePrefix.stick.get(aMaterial), 'P', OrePrefix.plate.get(aMaterial), 'T', OrePrefix.screw.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.SCOOP, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"SWS", "SSS", "xSh", 'S', OrePrefix.stick.get(aMaterial), 'W', new ItemStack(Blocks.WOOL, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.BRANCHCUTTER, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"PfP", "PdP", "STS", 'S', OrePrefix.stick.get(aMaterial), 'P', OrePrefix.plate.get(aMaterial), 'T', OrePrefix.screw.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.KNIFE, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"fPh", " S ", 'S', OrePrefix.stick.get(aMaterial), 'P', OrePrefix.plate.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.BUTCHERYKNIFE, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"PPf", "PP ", "Sh ", 'S', OrePrefix.stick.get(aMaterial), 'P', OrePrefix.plate.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(GT_MetaGenerated_Tool_01.SOLDERING_IRON_LV, 1, aMaterial, Materials.Rubber, new long[]{100000L, 32L, 1L, -1L}), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"LBf", "Sd ", "P  ", 'B', OrePrefix.bolt.get(aMaterial), 'P', OrePrefix.plate.get(Materials.Rubber), 'S', OrePrefix.stick.get(Materials.Iron), 'L', ItemList.Battery_RE_LV_Lithium.get(1L, new Object[0])});
            }
        }
    }
}
