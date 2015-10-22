package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_ScrapboxDropLoader
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: (re-)adding Scrapbox Drops.");

        GT_ModHandler.addScrapboxDrop(9.5F, new ItemStack(Items.wooden_hoe));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_axe));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_sword));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_shovel));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_pickaxe));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.sign));
        GT_ModHandler.addScrapboxDrop(9.5F, new ItemStack(Items.stick));
        GT_ModHandler.addScrapboxDrop(5.0F, new ItemStack(Blocks.dirt));
        GT_ModHandler.addScrapboxDrop(3.0F, new ItemStack(Blocks.grass));
        GT_ModHandler.addScrapboxDrop(3.0F, new ItemStack(Blocks.gravel));
        GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Blocks.pumpkin));
        GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Blocks.soul_sand));
        GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Blocks.netherrack));
        GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.bone));
        GT_ModHandler.addScrapboxDrop(9.0F, new ItemStack(Items.rotten_flesh));
        GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_porkchop));
        GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_beef));
        GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_chicken));
        GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Items.apple));
        GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Items.bread));
        GT_ModHandler.addScrapboxDrop(0.1F, new ItemStack(Items.cake));
        GT_ModHandler.addScrapboxDrop(1.0F, ItemList.IC2_Food_Can_Filled.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(2.0F, ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(0.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1L));
        GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, ItemList.Cell_Empty.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(5.0F, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L));
        GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.leather));
        GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.feather));
        GT_ModHandler.addScrapboxDrop(0.7F, GT_ModHandler.getIC2Item("plantBall", 1L));
        GT_ModHandler.addScrapboxDrop(3.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
        GT_ModHandler.addScrapboxDrop(0.6F, new ItemStack(Items.slime_ball));
        GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Rubber, 1L));
        GT_ModHandler.addScrapboxDrop(2.7F, GT_ModHandler.getIC2Item("suBattery", 1L));
        GT_ModHandler.addScrapboxDrop(3.6F, ItemList.Circuit_Primitive.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(0.8F, ItemList.Circuit_Parts_Advanced.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(1.8F, ItemList.Circuit_Board_Basic.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(0.4F, ItemList.Circuit_Board_Advanced.get(1L, new Object[0]));
        GT_ModHandler.addScrapboxDrop(0.2F, ItemList.Circuit_Board_Elite.get(1L, new Object[0]));
        if (!GT_Mod.gregtechproxy.mDisableIC2Cables) {
            GT_ModHandler.addScrapboxDrop(2.0F, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
            GT_ModHandler.addScrapboxDrop(0.4F, GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));
        }
        GT_ModHandler.addScrapboxDrop(0.9F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L));
        GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L));
        GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L));
        GT_ModHandler.addScrapboxDrop(2.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1L));
        GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1L));
        GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 1L));
        GT_ModHandler.addScrapboxDrop(1.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 1L));
        GT_ModHandler.addScrapboxDrop(1.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bauxite, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lead, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zinc, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 1L));
        GT_ModHandler.addScrapboxDrop(1.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Obsidian, 1L));
        GT_ModHandler.addScrapboxDrop(1.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lazurite, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Pyrite, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1L));
        GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodalite, 1L));
        GT_ModHandler.addScrapboxDrop(4.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 1L));
        GT_ModHandler.addScrapboxDrop(4.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Flint, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 1L));
        GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Endstone, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GarnetRed, 1L));
        GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GarnetYellow, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L));
        GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1L));
    }
}
