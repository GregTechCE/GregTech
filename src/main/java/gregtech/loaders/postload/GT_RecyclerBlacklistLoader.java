package gregtech.loaders.postload;

import gregtech.api.GregTech_API;
import gregtech.api.ConfigCategories;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.util.GTLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_RecyclerBlacklistLoader
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Adding Stuff to the Recycler Blacklist.");
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easymobgrinderrecycling", true)) {
            ModHandler.addToRecyclerBlackList(new ItemStack(Items.ARROW, 1, 0));
            ModHandler.addToRecyclerBlackList(new ItemStack(Items.BONE, 1, 0));
            ModHandler.addToRecyclerBlackList(ItemList.Dye_Bonemeal.get(1));


            ModHandler.addToRecyclerBlackList(new ItemStack(Items.ROTTEN_FLESH, 1, 0));


            ModHandler.addToRecyclerBlackList(new ItemStack(Items.STRING, 1, 0));


            ModHandler.addToRecyclerBlackList(new ItemStack(Items.EGG, 1, 0));
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "easystonerecycling", true)) {
            ItemStack tStack = new ItemStack(Blocks.COBBLESTONE, 1, 0);
            while (tStack != null) {
                ModHandler.addToRecyclerBlackList(tStack);
                tStack = ModHandler.getRecipeOutput(tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack, tStack);
            }
            ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.GRAVEL, 1, 32767));
            ModHandler.addToRecyclerBlackList(new ItemStack(Items.FLINT, 1, 32767));
            ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.COBBLESTONE_WALL, 1, 32767));
            ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.SANDSTONE_STAIRS, 1, 32767));
            ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.STONE_STAIRS, 1, 32767));
            ModHandler.addToRecyclerBlackList(new ItemStack(Blocks.STONE_BRICK_STAIRS, 1, 32767));
            ModHandler.addToRecyclerBlackList(ModHandler.getSmeltingOutput(new ItemStack(Blocks.STONE, 1, 0), false, null));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0), null, null, new ItemStack(Blocks.GLASS, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), null, null, new ItemStack(Blocks.STONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.COBBLESTONE, 1, 0), null, null, new ItemStack(Blocks.COBBLESTONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), null, new ItemStack(Blocks.STONE, 1, 0), null, new ItemStack(Blocks.STONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.STONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.COBBLESTONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.COBBLESTONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.SAND, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.SANDSTONE, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0)));
            ModHandler.addToRecyclerBlackList(ModHandler.getRecipeOutput(new ItemStack(Blocks.GLASS, 1, 0), new ItemStack(Blocks.GLASS, 1, 0)));
        }
    }
}
