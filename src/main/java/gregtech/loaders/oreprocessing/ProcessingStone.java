package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStone
        implements IOreRecipeRegistrator {
    public ProcessingStone() {
        OrePrefixes.stone.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Block aBlock = GT_Utility.getBlockFromStack(aStack);
        switch (aMaterial.mName) {
            case "NULL":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3L, new Object[]{aStack}), new ItemStack(Blocks.redstone_torch, 2), Materials.Redstone.getMolten(144L), new ItemStack(Items.repeater, 1), 100, 4);
                break;
            case "Sand":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.sand, 1, 0), null, 10, false);
                break;
            case "Endstone":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.Endstone, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tungsten, 1L), 5, false);
                break;
            case "Netherrack":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.Netherrack, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 1L), 5, false);
                break;
            case "NetherBrick":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), new ItemStack(Blocks.nether_brick_fence, 1), 100, 4);
                break;
            case "Obsidian":
                if (aBlock != Blocks.air) aBlock.setResistance(20.0F);
                GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 2L), GT_Utility.copyAmount(5L, new Object[]{aStack}), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem("Forestry", "thermionicTubes", 4L, 6), 64, 32);
                GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1L), GT_Utility.copyAmount(3L, new Object[]{aStack}), Materials.Glass.getMolten(720L), new ItemStack(Blocks.beacon, 1, 0), 32, 16);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1L), new ItemStack(Blocks.ender_chest, 1), 400, 4);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1L, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L)), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 10, true);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
                break;
            case "Concrete":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 100, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L));
                break;
            case "Rhyolite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PotassiumFeldspar, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Quartz, 1L), 20, false);
                break;
            case "Komatiite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Biotite, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Uranium, 1L), 5, false);
                break;
            case "Dacite":
            case "Andesite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Obsidian, 1L), 20, false);
                break;
            case "Gabbro":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.PotassiumFeldspar, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Pyrite, 1L), 20, false);
                break;
            case "Eclogite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Rutile, 1L), 10, false);
                break;
            case "Soapstone":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.Talc, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Chromite, 1L), 10, false);
                break;
            case "Greenschist":
            case "Blueschist":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Glauconite, 2L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Basalt, 1L), 100, false);
                break;
            case "Gneiss":
            case "Migmatite":
             	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SiliconDioxide, 1L), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.GraniteBlack, 1L), 50, false);
                break;
            case "Redrock":
            case "Marble":
            case "Basalt":
            case "Quartzite":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 10, false);
                break;
            case "Flint":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 2L), new ItemStack(Items.flint, 1), 50, false);
                break;
            case "GraniteBlack":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1L), 1, false);
                break;
            case "GraniteRed":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Uranium, 1L), 1, false);
        }
    }
}
