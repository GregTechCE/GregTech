package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStone
        implements IOreRegistrationHandler {
    public ProcessingStone() {
        OrePrefixes.stone.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Block aBlock = GT_Utility.getBlockFromStack(aStack);
        switch (aMaterial.mName) {
            case "NULL":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3L, aStack), new ItemStack(Blocks.REDSTONE_TORCH, 2), Materials.Redstone.getMolten(144L), new ItemStack(Items.REPEATER, 1), 100, 4);
                break;
            case "Sand":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, aStack), new ItemStack(Blocks.SAND, 1, 0), null, 10, false);
                break;
            case "Endstone":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, Materials.Endstone, 1L), OreDictionaryUnifier.get(OrePrefixes.dustTiny, Materials.Tungsten, 1L), 5, false);
                break;
            case "Netherrack":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, Materials.Netherrack, 1L), OreDictionaryUnifier.get(OrePrefixes.nugget, Materials.Gold, 1L), 5, false);
                break;
            case "NetherBrick":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.NETHER_BRICK_FENCE, 1), 100, 4);
                break;
            case "Obsidian":
                if (aBlock != Blocks.AIR) aBlock.setResistance(20.0F);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Redstone, 2L), GT_Utility.copyAmount(5L, aStack), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem("Forestry", "thermionicTubes", 4L, 6), 64, 32);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefixes.gem, Materials.NetherStar, 1L), GT_Utility.copyAmount(3L, aStack), Materials.Glass.getMolten(720L), new ItemStack(Blocks.BEACON, 1, 0), 32, 16);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.IC2_Compressed_Coal_Ball.get(8L), ItemList.IC2_Compressed_Coal_Chunk.get(1L), 400, 4);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, aStack), OreDictionaryUnifier.get(OrePrefixes.gem, Materials.EnderEye, 1L), new ItemStack(Blocks.ENDER_CHEST, 1), 400, 4);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, aStack), GT_ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1L, OreDictionaryUnifier.get(OrePrefixes.dust, aMaterial, 1L)), OreDictionaryUnifier.get(OrePrefixes.dust, aMaterial, 1L), 10, true);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
                break;
            case "Concrete":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.plate, aMaterial, 1), null, 100, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, aMaterial, 1));
                break;
            case "Rhyolite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Quartz, 1), 20, false);
            break;
            case "Komatiite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Biotite, 1), OreDictionaryUnifier.get(OrePrefixes.dustTiny, Materials.Uranium, 1), 5, false);
            break;
            case "Dacite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Obsidian, 1), 20, false);
            break;
            case "Gabbro":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Pyrite, 1), 20, false);
            break;
            case "Eclogite":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefixes.dustTiny, Materials.Rutile, 1), 10, false);
            break;
            case "Soapstone":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, Materials.Talc, 1), OreDictionaryUnifier.get(OrePrefixes.dustTiny, Materials.Chromite, 1), 10, false);
            break;
            case "Greenschist":
            case "Blueschist":
            	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustSmall, Materials.Glauconite, 2L), OreDictionaryUnifier.get(OrePrefixes.dustSmall, Materials.Basalt, 1), 100, false);
            break;
            case "Gneiss":
            case "Migmatite":
             	GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefixes.dustImpure, Materials.GraniteBlack, 1), 50, false);
                break;
            case "Redrock":
            case "Marble":
            case "Basalt":
            case "Quartzite":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, aMaterial, 1), OreDictionaryUnifier.get(OrePrefixes.dust, aMaterial, 1), 10, false);
                break;
            case "Flint":
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, aMaterial, 2L), new ItemStack(Items.FLINT, 1), 50, false);
                break;
            case "GraniteBlack":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.plate, aMaterial, 1), null, 200, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, aMaterial, 1), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Thorium, 1), 1, false);
                break;
            case "GraniteRed":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, aMaterial, 1L), OreDictionaryUnifier.get(OrePrefixes.dustSmall, Materials.Uranium, 1L), 1, false);
            case "Andesite":
            case "Diorite":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.plate, aMaterial, 1), null, 200, 32);
                GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, aStack), OreDictionaryUnifier.get(OrePrefixes.dustImpure, aMaterial, 1), OreDictionaryUnifier.get(OrePrefixes.dustSmall, Materials.Stone, 1), 1, false);
                break;
        }
    }
}
