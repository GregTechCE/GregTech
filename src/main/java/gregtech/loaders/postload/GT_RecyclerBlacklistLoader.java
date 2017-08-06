package gregtech.loaders.postload;

import gregtech.api.GregTech_API;
import gregtech.api.ConfigCategories;
import gregtech.api.items.ItemList;
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
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.ARROW, 1, 0));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.BONE, 1, 0));
            GT_ModHandler.addToRecyclerBlackList(ItemList.Dye_Bonemeal.get(1L));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.ROTTEN_FLESH, 1, 0));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.STRING, 1, 0));


            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.EGG, 1, 0));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easystonerecycling", true)) {
            ItemStack tStack = new ItemStack(Blocks.COBBLESTONE, 1, 0);
            while (tStack != null) {
                GT_ModHandler.addToRecyclerBlackList(tStack);
                tStack = GT_ModHandler.getRecipeOutput(tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack);
            }
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.GRAVEL, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Items.FLINT, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.COBBLESTONE_WALL, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.SANDSTONE_STAIRS, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.STONE_STAIRS, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.STONE_BRICK_STAIRS, 1, 32767));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getSmeltingOutput(new ItemStack(Blocks.STONE, 1, 0), false, null));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0), null, null, new ItemStack(Blocks.GLASS, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), null, null, new ItemStack(Blocks.STONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.COBBLESTONE, 1, 0), null, null, new ItemStack(Blocks.COBBLESTONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), null, new ItemStack(Blocks.STONE, 1, 0), null, new ItemStack(Blocks.STONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.STONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.COBBLESTONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.COBBLESTONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.SAND, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0)));
            GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.GLASS, 1, 0)));
        }
    }
}
