package gregtech.loaders.postload;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_RecyclerBlacklistLoader
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Adding Stuff to the Recycler Blacklist.");
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easymobgrinderrecycling", true)) {
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.arrow, 1, 0));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.bone, 1, 0));
            GT_ModHandler.addToRecyclerBlackList(ItemList.Dye_Bonemeal.get(1L, new Object[0]));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.rotten_flesh, 1, 0));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.string, 1, 0));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.egg, 1, 0));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easystonerecycling", true)) {
            ItemStack tStack = new ItemStack(Blocks.cobblestone, 1, 0);
            while (tStack != null) {
                GT_ModHandler.addToRecyclerBlackList(tStack);
                tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack});
            }
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.cobblestone_wall, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.sandstone_stairs, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.stone_stairs, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.stone_brick_stairs, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getSmeltingOutput(new ItemStack(Blocks.stone, 1, 0), false, null));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.glass, 1, 0), null, null, new ItemStack(Blocks.glass, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.stone, 1, 0), null, null, new ItemStack(Blocks.stone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.cobblestone, 1, 0), null, null, new ItemStack(Blocks.cobblestone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.stone, 1, 0), null, new ItemStack(Blocks.stone, 1, 0), null, new ItemStack(Blocks.stone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.stone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.stone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.cobblestone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.cobblestone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.sandstone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.sand, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sandstone, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.glass, 1, 0)}));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Blocks.glass, 1, 0), new ItemStack(Blocks.glass, 1, 0)}));
        }
    }
}
