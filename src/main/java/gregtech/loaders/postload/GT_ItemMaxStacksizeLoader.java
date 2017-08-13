package gregtech.loaders.postload;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GT_ItemMaxStacksizeLoader
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Changing maximum Stacksizes if configured.");

        //ItemList.Upgrade_Overclocker.getItem().setMaxStackSize(GT_Mod.gregtechproxy.mUpgradeCount);
        Items.CAKE.setMaxStackSize(64);
        Items.OAK_DOOR.setMaxStackSize(8);
        Items.IRON_DOOR.setMaxStackSize(8);
        if (OrePrefix.plank.defaultStackSize < 64) {
            Item.getItemFromBlock(Blocks.WOODEN_SLAB).setMaxStackSize(OrePrefix.plank.defaultStackSize);
            Item.getItemFromBlock(Blocks.DOUBLE_STONE_SLAB).setMaxStackSize(OrePrefix.plank.defaultStackSize);
            Item.getItemFromBlock(Blocks.OAK_STAIRS).setMaxStackSize(OrePrefix.plank.defaultStackSize);
        }
        if (OrePrefix.block.defaultStackSize < 64) {
            /*
            Item.getItemFromBlock(Blocks.stone_slab).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.double_stone_slab).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.brick_stairs).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.nether_brick_stairs).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sandstone_stairs).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.stone_stairs).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.stone_brick_stairs).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.packed_ice).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.ice).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.soul_sand).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.glowstone).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.snow).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.iron_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.gold_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.emerald_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lapis_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.diamond_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.clay).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.redstone_lamp).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.dirt).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.grass).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.mycelium).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.gravel).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sand).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.brick_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.wool).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.melon_block).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.pumpkin).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lit_pumpkin).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.dispenser).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.obsidian).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.piston).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.sticky_piston).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.crafting_table).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.glass).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.jukebox).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.anvil).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.chest).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.trapped_chest).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.noteblock).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.mob_spawner).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.bookshelf).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.furnace).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
            Item.getItemFromBlock(Blocks.lit_furnace).setMaxStackSize(OrePrefix.block.mDefaultStackSize);
        */
        }
    }
}
