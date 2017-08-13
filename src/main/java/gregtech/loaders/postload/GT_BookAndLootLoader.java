package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootTableList;

public class GT_BookAndLootLoader implements Runnable {

    public void run() {
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
        
        ChestGenHooks.addItem(LootTableList.CHESTS_SPAWN_BONUS_CHEST, ItemList.Bottle_Purple_Drink.get(1), 8, 16, 2);

        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, ItemList.Bottle_Holy_Water.get(1), 4, 8, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, ItemList.Bottle_Purple_Drink.get(1), 8, 16, 40);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Silver, 1), 1, 6, 30);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Lead, 1), 1, 6, 7);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel, 1), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze, 1), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Manganese, 1), 1, 6, 15);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel, 1), 1, 6, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Emerald, 1), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Ruby, 1), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Sapphire, 1), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GreenSapphire, 1), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Olivine, 1), 1, 6, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetRed, 1), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetYellow, 1), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Neodymium, 1), 1, 6, 10);
        ChestGenHooks.addItem(LootTableList.CHESTS_SIMPLE_DUNGEON, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Chrome, 1), 1, 3, 10);

        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, ItemList.Bottle_Holy_Water.get(1), 4, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Silver, 1), 4, 16, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Platinum, 1), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Ruby, 1), 2, 8, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Sapphire, 1), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GreenSapphire, 1), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Olivine, 1), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetRed, 1), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_DESERT_PYRAMID, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetYellow, 1), 2, 8, 3);

        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, ItemList.Coin_Gold_Ancient.get(1), 16, 64, 5);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, ItemList.ZPM.getWithCharge(1, 2147483647), 1, 1, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze, 1), 4, 16, 12);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Ruby, 1), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Sapphire, 1), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GreenSapphire, 1), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Olivine, 1), 2, 8, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetRed, 1), 2, 8, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetYellow, 1), 2, 8, 4);

        ChestGenHooks.addItem(LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER, new ItemStack(Items.FIRE_CHARGE, 1), 2, 8, 15);

        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Silver, 1), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Lead, 1), 1, 4, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel, 1), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze, 1), 1, 4, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Sapphire, 1), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GreenSapphire, 1), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Olivine, 1), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetRed, 1), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.GarnetYellow, 1), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Ruby, 1), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Emerald, 1), 1, 4, 2);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.toolHeadPickaxe, Materials.DamascusSteel, 1), 1, 4, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_ABANDONED_MINESHAFT, OreDictionaryUnifier.get(OrePrefix.toolHeadShovel, Materials.DamascusSteel, 1), 1, 4, 1);

        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, ItemList.McGuffium_239.get(1), 1, 1, 1);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Chrome, 1), 1, 4, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.dust, Materials.Neodymium, 1), 2, 8, 3);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Manganese, 1), 2, 8, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Steel, 1), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze, 1), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Brass, 1), 4, 12, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_VILLAGE_BLACKSMITH, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.DamascusSteel, 1), 4, 12, 1);

        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, ItemList.Bottle_Holy_Water.get(1), 4, 8, 6);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CROSSING, ItemList.McGuffium_239.get(1), 1, 1, 8);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, OreDictionaryUnifier.get(OrePrefix.toolHeadSword, Materials.DamascusSteel, 1), 1, 4, 4);
        ChestGenHooks.addItem(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, OreDictionaryUnifier.get(OrePrefix.toolHeadAxe, Materials.DamascusSteel, 1), 1, 4, 4);
       
    }

}
