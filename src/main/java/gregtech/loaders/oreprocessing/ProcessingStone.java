package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStone
        implements IOreRegistrationHandler {
    public ProcessingStone() {
        OrePrefix.stone.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        Block aBlock = GT_Utility.getBlockFromStack(stack);
        switch (uEntry.material.toString()) {
            case "NULL":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3, stack), new ItemStack(Blocks.REDSTONE_TORCH, 2), Materials.Redstone.getMolten(144L), new ItemStack(Items.REPEATER, 1), 100, 4);
                break;
            case "Sand":
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), new ItemStack(Blocks.SAND, 1, 0), null, 10, false);
                break;
            case "Endstone":
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Endstone, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Tungsten, 1), 5, false);
                break;
            case "Netherrack":
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Netherrack, 1), OreDictionaryUnifier.get(OrePrefix.nugget, Materials.Gold, 1), 5, false);
                break;
            case "NetherBrick":
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1, stack), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.NETHER_BRICK_FENCE, 1), 100, 4);
                break;
            case "Obsidian":
                if (aBlock != Blocks.AIR) aBlock.setResistance(20.0F);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), GT_Utility.copyAmount(5L, stack), Materials.Glass.getMolten(72L), ModHandler.getModItem("Forestry", "thermionicTubes", 4L, 6), 64, 32);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.gem, Materials.NetherStar, 1L), GT_Utility.copyAmount(3L, stack), Materials.Glass.getMolten(720L), new ItemStack(Blocks.BEACON, 1, 0), 32, 16);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, stack), ItemList.IC2_Compressed_Coal_Ball.get(8L), ItemList.IC2_Compressed_Coal_Chunk.get(1L), 400, 4);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, stack), OreDictionaryUnifier.get(OrePrefix.gem, Materials.EnderEye, 1L), new ItemStack(Blocks.ENDER_CHEST, 1), 400, 4);
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1, OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1)), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material), 10, true);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1L), null, 200, 32);
                break;
            case "Concrete":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), null, 100, 32);
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1));
                break;
            case "Rhyolite":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Quartz, 1), 20, false);
            break;
            case "Komatiite":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Biotite, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Uranium, 1), 5, false);
            break;
            case "Dacite":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Obsidian, 1), 20, false);
            break;
            case "Gabbro":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.PotassiumFeldspar, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Pyrite, 1), 20, false);
            break;
            case "Eclogite":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Rutile, 1), 10, false);
            break;
            case "Soapstone":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.Talc, 1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Chromite, 1), 10, false);
            break;
            case "Greenschist":
            case "Blueschist":
            	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Glauconite, 2), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Basalt, 1), 100, false);
            break;
            case "Gneiss":
            case "Migmatite":
             	ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), OreDictionaryUnifier.get(OrePrefix.dustImpure, Materials.GraniteBlack, 1), 50, false);
                break;
            case "Redrock":
            case "Marble":
            case "Basalt":
            case "Quartzite":
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), 10, false);
                break;
            case "Flint":
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 2L), new ItemStack(Items.FLINT, 1), 50, false);
                break;
            case "GraniteBlack":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), null, 200, 32);
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Thorium, 1), 1, false);
                break;
            case "GraniteRed":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1L), null, 200, 32);
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Uranium, 1), 1, false);
            case "Andesite":
            case "Diorite":
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), null, 200, 32);
                ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dustImpure, uEntry.material, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Stone, 1), 1, false);
                break;
        }
    }
}
