package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class GT_BookAndLootLoader
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Adding worldgenerated Chest Content.");
        if (GT_Mod.gregtechproxy.mIncreaseDungeonLoot) {
            ChestGenHooks tChest = ChestGenHooks.getInfo("bonusChest");
            tChest.setMax(tChest.getMax() + 8);
            tChest.setMin(tChest.getMin() + 4);
            tChest = ChestGenHooks.getInfo("dungeonChest");
            tChest.setMax(tChest.getMax() + 6);
            tChest.setMin(tChest.getMin() + 3);
            tChest = ChestGenHooks.getInfo("pyramidDesertyChest");
            tChest.setMax(tChest.getMax() + 8);
            tChest.setMin(tChest.getMin() + 4);
            tChest = ChestGenHooks.getInfo("pyramidJungleChest");
            tChest.setMax(tChest.getMax() + 16);
            tChest.setMin(tChest.getMin() + 8);
            tChest = ChestGenHooks.getInfo("pyramidJungleDispenser");
            tChest.setMax(tChest.getMax() + 2);
            tChest.setMin(tChest.getMin() + 1);
            tChest = ChestGenHooks.getInfo("mineshaftCorridor");
            tChest.setMax(tChest.getMax() + 4);
            tChest.setMin(tChest.getMin() + 2);
            tChest = ChestGenHooks.getInfo("villageBlacksmith");
            tChest.setMax(tChest.getMax() + 12);
            tChest.setMin(tChest.getMin() + 6);
            tChest = ChestGenHooks.getInfo("strongholdCrossing");
            tChest.setMax(tChest.getMax() + 8);
            tChest.setMin(tChest.getMin() + 4);
            tChest = ChestGenHooks.getInfo("strongholdCorridor");
            tChest.setMax(tChest.getMax() + 6);
            tChest.setMin(tChest.getMin() + 3);
            tChest = ChestGenHooks.getInfo("strongholdLibrary");
            tChest.setMax(tChest.getMax() + 16);
            tChest.setMin(tChest.getMin() + 8);
        }
        ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(ItemList.Bottle_Purple_Drink.get(1L, new Object[0]), 8, 16, 2));


        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(ItemList.Bottle_Purple_Drink.get(1L, new Object[0]), 8, 16, 80));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 6, 120));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 6, 30));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 6, 60));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 6, 60));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 1, 6, 60));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 1, 6, 10));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 6, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 6, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 6, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 6, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 6, 20));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 6, 40));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 6, 40));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 1, 6, 40));
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 3, 40));

        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 2));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 4, 16, 12));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Platinum, 1L), 2, 8, 4));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 4));
        ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 4));

        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(ItemList.Coin_Gold_Ancient.get(1L, new Object[0]), 16, 64, 10));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(ItemList.ZPM.getWithCharge(1L, 2147483647, new Object[0]), 1, 1, 1));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 16, 12));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 2));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 4));
        ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 4));

        ChestGenHooks.addItem("pyramidJungleDispenser", new WeightedRandomChestContent(new ItemStack(Items.fire_charge, 1), 2, 8, 30));
        ChestGenHooks.addItem("pyramidJungleDispenser", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 8, 16, 20));


        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 4, 12));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 4, 3));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 4, 6));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 4, 6));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 4, 2));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 4, 2));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 4, 2));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 4, 4));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 4, 4));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 4, 2));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 4, 2));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.DamascusSteel, 1L), 1, 4, 1));
        ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.DamascusSteel, 1L), 1, 4, 1));

        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(ItemList.McGuffium_239.get(1L, new Object[0]), 1, 1, 1));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 4, 6));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 2, 8, 6));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 2, 8, 12));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 4, 12, 12));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 12, 12));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Brass, 1L), 4, 12, 12));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 4, 12, 1));

        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 6));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(ItemList.McGuffium_239.get(1L, new Object[0]), 1, 1, 10));

        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.DamascusSteel, 1L), 4, 8, 6));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Steel, 1L), 8, 16, 12));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Bronze, 1L), 8, 16, 12));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Manganese, 1L), 4, 8, 12));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Neodymium, 1L), 4, 8, 6));
        ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Chrome, 1L), 2, 4, 6));

        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.DamascusSteel, 1L), 1, 4, 6));
        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.DamascusSteel, 1L), 1, 4, 6));
        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 16, 48, 6));
        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.SterlingSilver, 1L), 8, 24, 6));
    }
}
