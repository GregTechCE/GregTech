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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingPlank implements IOreRegistrationHandler {
    public ProcessingPlank() {
        OrePrefix.plank.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (aOreDictName.startsWith("plankWood")) {
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 2L), null, 10, 8);
            GT_Values.RA.addCNCRecipe(GT_Utility.copyAmount(4, stack), OreDictionaryUnifier.get(OrePrefix.gearGt, Materials.Wood, 1L), 800, 1);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), new ItemStack(Blocks.NOTEBLOCK, 1), 200, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8, stack), OreDictionaryUnifier.get(OrePrefix.gem, Materials.Diamond, 1L), new ItemStack(Blocks.JUKEBOX, 1), 400, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.screw, Materials.Iron, 1L), ItemList.Crate_Empty.get(1L), 200, 1);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.screw, Materials.WroughtIron, 1L), ItemList.Crate_Empty.get(1L), 200, 1);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.screw, Materials.Steel, 1L), ItemList.Crate_Empty.get(1L), 200, 1);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1, stack), ItemList.Circuit_Integrated.getWithDamage(0, 1), new ItemStack(Blocks.WOODEN_BUTTON, 1), 100, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2, stack), ItemList.Circuit_Integrated.getWithDamage(0, 2), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), 200, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3, stack), ItemList.Circuit_Integrated.getWithDamage(0, 3), new ItemStack(Blocks.TRAPDOOR, 1), 300, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4, stack), ItemList.Circuit_Integrated.getWithDamage(0, 4), new ItemStack(Blocks.CRAFTING_TABLE, 1), 400, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(6, stack), ItemList.Circuit_Integrated.getWithDamage(0, 6), new ItemStack(Items.OAK_DOOR, 1), 600, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8, stack), ItemList.Circuit_Integrated.getWithDamage(0, 8), new ItemStack(Blocks.CHEST, 1), 800, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(6, stack), new ItemStack(Items.BOOK, 3), new ItemStack(Blocks.BOOKSHELF, 1), 400, 4);

            if (stack.getItemDamage() == 32767) {
                for (byte i = 0; i < 64; i = (byte) (i + 1)) {
                    ItemStack tStack = GT_Utility.copyMetaData(i, stack);
                    ItemStack tOutput = ModHandler.getRecipeOutput(tStack, tStack, tStack);
                    if ((tOutput != null) && (tOutput.stackSize >= 3)) {
                        GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, tStack), GT_Utility.copyAmount(tOutput.stackSize / 3, tOutput), null, 25, 4);
                        ModHandler.removeRecipe(tStack, tStack, tStack);
                        ModHandler.addCraftingRecipe(GT_Utility.copyAmount(tOutput.stackSize / 3, tOutput), "sP", Character.valueOf('P'), tStack);
                    }
                    if((tStack == null) && (i >= 16)) break;
                }
            } else {
                ItemStack tOutput = ModHandler.getRecipeOutput(stack, stack, stack);
                if ((tOutput != null) && (tOutput.stackSize >= 3)) {
                    GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, stack), GT_Utility.copyAmount(tOutput.stackSize / 3, tOutput), null, 25, 4);
                    ModHandler.removeRecipe(stack, stack, stack);
                    ModHandler.addCraftingRecipe(GT_Utility.copyAmount(tOutput.stackSize / 3, tOutput), "sP", Character.valueOf('P'), stack);
                }
            }
        }
    }
}
