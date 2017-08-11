package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.util.GTLog;
import gregtech.api.unification.GT_OreDictUnificator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootTableList;

public class GT_BookAndLootLoader
        implements Runnable {
    public void run() {

        GTLog.out.println("GT_Mod: Adding worldgenerated Chest Content.");
        new ChestGenHooks();

        if (GT_Mod.gregtechproxy.mIncreaseDungeonLoot) {
            ChestGenHooks.addRolls(LootTableList.CHESTS_SPAWN_BONUS_CHEST, 2, 4);
            ChestGenHooks.addRolls(LootTableList.CHESTS_SIMPLE_DUNGEON, 1, 3);
            ChestGenHooks.addRolls(LootTableList.CHESTS_DESERT_PYRAMID, 2, 4);
            ChestGenHooks.addRolls(LootTableList.CHESTS_JUNGLE_TEMPLE, 4, 8);
            ChestGenHooks.addRolls(LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER, 0, 2);
            ChestGenHooks.addRolls(LootTableList.CHESTS_ABANDONED_MINESHAFT, 1, 3);
            ChestGenHooks.addRolls(LootTableList.CHESTS_VILLAGE_BLACKSMITH, 2, 6);
            ChestGenHooks.addRolls(LootTableList.CHESTS_STRONGHOLD_CROSSING, 2, 4);
            ChestGenHooks.addRolls(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, 2, 4);
            ChestGenHooks.addRolls(LootTableList.CHESTS_STRONGHOLD_LIBRARY, 4, 8);
        }
        
        ChestGenHooks.addItem(LootTableList.CHESTS_SPAWN_BONUS_CHEST, ItemList.Bottle_Purple_Drink.get(1L), 8, 16, 2);

        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, ItemList.Bottle_Holy_Water.get(1L), 4, 8, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, ItemList.Bottle_Purple_Drink.get(1L), 8, 16, 40);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 6, 30);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 6, 7);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 1, 6, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 3, 10);

        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, ItemList.Bottle_Holy_Water.get(1L), 4, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 4, 16, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Platinum, 1L), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 3);

        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, ItemList.Coin_Gold_Ancient.get(1L), 16, 64, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, ItemList.ZPM.getWithCharge(1L, 2147483647), 1, 1, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 16, 12);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 4);

        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER, new ItemStack(Items.FIRE_CHARGE, 1), 2, 8, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER, GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 8, 16, 10);


        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 4, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.DamascusSteel, 1L), 1, 4, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.DamascusSteel, 1L), 1, 4, 1);

        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, ItemList.McGuffium_239.get(1L), 1, 1, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 4, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 2, 8, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Brass, 1L), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 4, 12, 1);

        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, ItemList.Bottle_Holy_Water.get(1L), 4, 8, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, ItemList.McGuffium_239.get(1L), 1, 1, 8);

        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.DamascusSteel, 1L), 4, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Steel, 1L), 8, 16, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Bronze, 1L), 8, 16, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Manganese, 1L), 4, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Neodymium, 1L), 4, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Chrome, 1L), 2, 4, 4);

        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.DamascusSteel, 1L), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.DamascusSteel, 1L), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 16, 48, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.SterlingSilver, 1L), 8, 24, 4);
    }
}
