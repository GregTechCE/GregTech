package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GT_ItemMaxStacksizeLoader
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Changing maximum Stacksizes if configured.");

        ItemList.Upgrade_Overclocker.getItem().setMaxStackSize(GT_Mod.gregtechproxy.mUpgradeCount);
        Items.cake.setMaxStackSize(64);
        Items.wooden_door.setMaxStackSize(8);
        Items.iron_door.setMaxStackSize(8);
        if (OrePrefixes.plank.mDefaultStackSize < 64) {
            Item.getItemFromBlock(Blocks.wooden_slab).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.double_wooden_slab).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.oak_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.jungle_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.birch_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.spruce_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.acacia_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.dark_oak_stairs).setMaxStackSize(OrePrefixes.plank.mDefaultStackSize);
        }
        if (OrePrefixes.block.mDefaultStackSize < 64) {
            Item.getItemFromBlock(Blocks.stone_slab).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.double_stone_slab).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.nether_brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sandstone_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.stone_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.stone_brick_stairs).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.packed_ice).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.ice).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.soul_sand).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.glowstone).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.iron_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.gold_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.emerald_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lapis_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.diamond_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.clay).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.redstone_lamp).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.dirt).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.grass).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.mycelium).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.gravel).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sand).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.brick_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.wool).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.melon_block).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.pumpkin).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lit_pumpkin).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.dispenser).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.obsidian).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.piston).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sticky_piston).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.crafting_table).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.glass).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.jukebox).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.anvil).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.chest).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.trapped_chest).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.noteblock).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.mob_spawner).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.bookshelf).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.furnace).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lit_furnace).setMaxStackSize(OrePrefixes.block.mDefaultStackSize);
        }
    }
}
