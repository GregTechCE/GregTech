/*   1:    */ package gregtech.loaders.preload;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.ItemList;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.enums.OrePrefixes;
/*   6:    */ import gregtech.api.objects.ItemData;
/*   7:    */ import gregtech.api.objects.MaterialStack;
/*   8:    */ import gregtech.api.util.GT_Log;
/*   9:    */ import gregtech.api.util.GT_ModHandler;
/*  10:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  11:    */ import gregtech.api.util.GT_Utility;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ 
/*  17:    */ public class GT_Loader_ItemData
/*  18:    */   implements Runnable
/*  19:    */ {
/*  20:    */   public void run()
/*  21:    */   {
/*  22: 20 */     GT_Log.out.println("GT_Mod: Loading Item Data Tags");
/*  23: 21 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "item.giantPick", 1L, 0), new ItemData(Materials.Stone, 696729600L, new MaterialStack[] { new MaterialStack(Materials.Wood, 464486400L) }));
/*  24: 22 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "item.giantSword", 1L, 0), new ItemData(Materials.Stone, 464486400L, new MaterialStack[] { new MaterialStack(Materials.Wood, 232243200L) }));
/*  25: 23 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "tile.GiantLog", 1L, 32767), new ItemData(Materials.Wood, 232243200L, new MaterialStack[0]));
/*  26: 24 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "tile.GiantCobble", 1L, 32767), new ItemData(Materials.Stone, 232243200L, new MaterialStack[0]));
/*  27: 25 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "tile.GiantObsidian", 1L, 32767), new ItemData(Materials.Obsidian, 232243200L, new MaterialStack[0]));
/*  28: 26 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "item.minotaurAxe", 1L, 0), new ItemData(Materials.Diamond, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Wood, OrePrefixes.stick.mMaterialAmount * 2L) }));
/*  29: 27 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "item.armorShards", 1L, 0), new ItemData(Materials.Knightmetal, 403200L, new MaterialStack[0]));
/*  30: 28 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("TwilightForest", "item.shardCluster", 1L, 0), new ItemData(Materials.Knightmetal, 3628800L, new MaterialStack[0]));
/*  31: 29 */     GT_OreDictUnificator.addItemData(ItemList.TF_LiveRoot.get(1L, new Object[0]), new ItemData(Materials.LiveRoot, 3628800L, new MaterialStack[0]));
/*  32: 30 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 10), new ItemData(Materials.CertusQuartz, 1814400L, new MaterialStack[0]));
/*  33: 31 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 11), new ItemData(Materials.NetherQuartz, 1814400L, new MaterialStack[0]));
/*  34: 32 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 12), new ItemData(Materials.Fluix, 1814400L, new MaterialStack[0]));
/*  35: 33 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.quartz_block, 1, 32767), new ItemData(Materials.NetherQuartz, 14515200L, new MaterialStack[0]));
/*  36: 34 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L, 32767), new ItemData(Materials.CertusQuartz, 14515200L, new MaterialStack[0]));
/*  37: 35 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzPillar", 1L, 32767), new ItemData(Materials.CertusQuartz, 14515200L, new MaterialStack[0]));
/*  38: 36 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L, 32767), new ItemData(Materials.CertusQuartz, 14515200L, new MaterialStack[0]));
/*  39: 37 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.wheat, 1, 32767), new ItemData(Materials.Wheat, 3628800L, new MaterialStack[0]));
/*  40: 38 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.hay_block, 1, 32767), new ItemData(Materials.Wheat, 32659200L, new MaterialStack[0]));
/*  41: 39 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.snowball, 1, 32767), new ItemData(Materials.Snow, 907200L, new MaterialStack[0]));
/*  42: 40 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.snow, 1, 32767), new ItemData(Materials.Snow, 3628800L, new MaterialStack[0]));
/*  43: 41 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.glowstone, 1, 32767), new ItemData(Materials.Glowstone, 14515200L, new MaterialStack[0]));
/*  44: 42 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.redstone_lamp, 1, 32767), new ItemData(Materials.Glowstone, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Redstone, OrePrefixes.dust.mMaterialAmount * 4L) }));
/*  45: 43 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.lit_redstone_lamp, 1, 32767), new ItemData(Materials.Glowstone, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Redstone, OrePrefixes.dust.mMaterialAmount * 4L) }));
/*  46: 44 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Forestry", "craftingMaterial", 1L, 5), new ItemData(Materials.Ice, 3628800L, new MaterialStack[0]));
/*  47: 45 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.ice, 1, 32767), new ItemData(Materials.Ice, 3628800L, new MaterialStack[0]));
/*  48: 46 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.packed_ice, 1, 32767), new ItemData(Materials.Ice, 7257600L, new MaterialStack[0]));
/*  49: 47 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.clay_ball, 1, 32767), new ItemData(Materials.Clay, 1814400L, new MaterialStack[0]));
/*  50: 48 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.clay, 1, 32767), new ItemData(Materials.Clay, 7257600L, new MaterialStack[0]));
/*  51: 49 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.hardened_clay, 1, 32767), new ItemData(Materials.Clay, 3628800L, new MaterialStack[0]));
/*  52: 50 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stained_hardened_clay, 1, 32767), new ItemData(Materials.Clay, 3628800L, new MaterialStack[0]));
/*  53: 51 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.brick_block, 1, 32767), new ItemData(Materials.Clay, 3628800L, new MaterialStack[0]));
/*  54: 52 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("Uran238", 1L), new ItemData(Materials.Uranium, 3628800L, new MaterialStack[0]));
/*  55: 53 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("Uran235", 1L), new ItemData(Materials.Uranium235, 3628800L, new MaterialStack[0]));
/*  56: 54 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("Plutonium", 1L), new ItemData(Materials.Plutonium, 3628800L, new MaterialStack[0]));
/*  57: 55 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("smallUran235", 1L), new ItemData(Materials.Uranium235, 403200L, new MaterialStack[0]));
/*  58: 56 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("smallPlutonium", 1L), new ItemData(Materials.Plutonium, 403200L, new MaterialStack[0]));
/*  59: 57 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Iron.get(1L, new Object[0]), new ItemData(Materials.Iron, 1814400L, new MaterialStack[0]));
/*  60: 58 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Gold.get(1L, new Object[0]), new ItemData(Materials.Gold, 1814400L, new MaterialStack[0]));
/*  61: 59 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Bronze.get(1L, new Object[0]), new ItemData(Materials.Bronze, 1814400L, new MaterialStack[0]));
/*  62: 60 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Copper.get(1L, new Object[0]), new ItemData(Materials.Copper, 1814400L, new MaterialStack[0]));
/*  63: 61 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Tin.get(1L, new Object[0]), new ItemData(Materials.Tin, 1814400L, new MaterialStack[0]));
/*  64: 62 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Lead.get(1L, new Object[0]), new ItemData(Materials.Lead, 1814400L, new MaterialStack[0]));
/*  65: 63 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Item_Casing_Steel.get(1L, new Object[0]), new ItemData(Materials.Steel, 1814400L, new MaterialStack[0]));
/*  66: 64 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.book, 1, 32767), new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]));
/*  67: 65 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.written_book, 1, 32767), new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]));
/*  68: 66 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.writable_book, 1, 32767), new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]));
/*  69: 67 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.enchanted_book, 1, 32767), new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]));
/*  70: 68 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.golden_apple, 1, 1), new ItemData(Materials.Gold, OrePrefixes.block.mMaterialAmount * 8L, new MaterialStack[0]));
/*  71: 69 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.golden_apple, 1, 0), new ItemData(Materials.Gold, OrePrefixes.ingot.mMaterialAmount * 8L, new MaterialStack[0]));
/*  72: 70 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.golden_carrot, 1, 0), new ItemData(Materials.Gold, OrePrefixes.nugget.mMaterialAmount * 8L, new MaterialStack[0]));
/*  73: 71 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.speckled_melon, 1, 0), new ItemData(Materials.Gold, OrePrefixes.nugget.mMaterialAmount * 8L, new MaterialStack[0]));
/*  74: 72 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.minecart, 1), new ItemData(Materials.Iron, 18144000L, new MaterialStack[0]));
/*  75: 73 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.iron_door, 1), new ItemData(Materials.Iron, 21772800L, new MaterialStack[0]));
/*  76: 74 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.cauldron, 1), new ItemData(Materials.Iron, 25401600L, new MaterialStack[0]));
/*  77: 75 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.iron_bars, 8, 32767), new ItemData(Materials.Iron, 10886400L, new MaterialStack[0]));
/*  78: 76 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getIC2Item("ironFurnace", 1L), new ItemData(Materials.Iron, 18144000L, new MaterialStack[0]));
/*  79: 77 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), new ItemData(Materials.Tin, 1814400L, new MaterialStack[0]));
/*  80: 78 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Fuel_Rod_Empty.get(1L, new Object[0]), new ItemData(Materials.Iron, 3628800L, new MaterialStack[0]));
/*  81: 79 */     GT_OreDictUnificator.addItemData(ItemList.IC2_Fuel_Can_Empty.get(1L, new Object[0]), new ItemData(Materials.Tin, 25401600L, new MaterialStack[0]));
/*  82: 80 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.light_weighted_pressure_plate, 1, 32767), new ItemData(Materials.Gold, 7257600L, new MaterialStack[0]));
/*  83: 81 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1, 32767), new ItemData(Materials.Iron, 7257600L, new MaterialStack[0]));
/*  84: 82 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1L, 0), new ItemData(Materials.Steel, 108864000L, new MaterialStack[0]));
/*  85: 83 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1L, 1), new ItemData(Materials.Steel, 72576000L, new MaterialStack[0]));
/*  86: 84 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1L, 2), new ItemData(Materials.Steel, 36288000L, new MaterialStack[0]));
/*  87: 85 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 0), new ItemData(Materials.Iron, 108864000L, new MaterialStack[0]));
/*  88: 86 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 1), new ItemData(Materials.Iron, 72576000L, new MaterialStack[0]));
/*  89: 87 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.anvil, 1, 2), new ItemData(Materials.Iron, 36288000L, new MaterialStack[0]));
/*  90: 88 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.hopper, 1, 32767), new ItemData(Materials.Iron, 18144000L, new MaterialStack[] { new MaterialStack(Materials.Wood, 29030400L) }));
/*  91: 89 */     GT_OreDictUnificator.addItemData(ItemList.Cell_Universal_Fluid.get(1L, new Object[0]), new ItemData(Materials.Tin, 7257600L, new MaterialStack[] { new MaterialStack(Materials.Glass, 1360800L) }));
/*  92: 90 */     GT_OreDictUnificator.addItemData(ItemList.Cell_Empty.get(1L, new Object[0]), new ItemData(Materials.Tin, 7257600L, new MaterialStack[0]));
/*  93: 91 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.tripwire_hook, 1, 32767), new ItemData(Materials.Iron, OrePrefixes.ring.mMaterialAmount * 2L, new MaterialStack[] { new MaterialStack(Materials.Wood, 3628800L) }));
/*  94: 92 */     GT_OreDictUnificator.addItemData(ItemList.Bottle_Empty.get(1L, new Object[0]), new ItemData(Materials.Glass, 3628800L, new MaterialStack[0]));
/*  95: 93 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.potionitem, 1, 32767), new ItemData(Materials.Glass, 3628800L, new MaterialStack[0]));
/*  96: 94 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stained_glass, 1, 32767), new ItemData(Materials.Glass, 3628800L, new MaterialStack[0]));
/*  97: 95 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.glass, 1, 32767), new ItemData(Materials.Glass, 3628800L, new MaterialStack[0]));
/*  98: 96 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stained_glass_pane, 1, 32767), new ItemData(Materials.Glass, 1360800L, new MaterialStack[0]));
/*  99: 97 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.glass_pane, 1, 32767), new ItemData(Materials.Glass, 1360800L, new MaterialStack[0]));
/* 100: 98 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.clock, 1, 32767), new ItemData(Materials.Gold, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 101: 99 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.compass, 1, 32767), new ItemData(Materials.Iron, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 102:100 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.iron_horse_armor, 1, 32767), new ItemData(Materials.Iron, 29030400L, new MaterialStack[] { new MaterialStack(Materials.Leather, 21772800L) }));
/* 103:101 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.golden_horse_armor, 1, 32767), new ItemData(Materials.Gold, 29030400L, new MaterialStack[] { new MaterialStack(Materials.Leather, 21772800L) }));
/* 104:102 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.diamond_horse_armor, 1, 32767), new ItemData(Materials.Diamond, 29030400L, new MaterialStack[] { new MaterialStack(Materials.Leather, 21772800L) }));
/* 105:103 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.leather, 1, 32767), new ItemData(Materials.Leather, 3628800L, new MaterialStack[0]));
/* 106:104 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.beacon, 1, 32767), new ItemData(Materials.NetherStar, 3628800L, new MaterialStack[] { new MaterialStack(Materials.Obsidian, 10886400L), new MaterialStack(Materials.Glass, 18144000L) }));
/* 107:105 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.enchanting_table, 1, 32767), new ItemData(Materials.Diamond, 7257600L, new MaterialStack[] { new MaterialStack(Materials.Obsidian, 14515200L), new MaterialStack(Materials.Paper, 10886400L) }));
/* 108:106 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.ender_chest, 1, 32767), new ItemData(Materials.EnderEye, 3628800L, new MaterialStack[] { new MaterialStack(Materials.Obsidian, 29030400L) }));
/* 109:107 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.bookshelf, 1, 32767), new ItemData(Materials.Paper, 32659200L, new MaterialStack[] { new MaterialStack(Materials.Wood, 21772800L) }));
/* 110:108 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.lever, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[] { new MaterialStack(Materials.Wood, 1814400L) }));
/* 111:109 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.ice, 1, 32767), new ItemData(Materials.Ice, 3628800L, new MaterialStack[0]));
/* 112:110 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.packed_ice, 1, 32767), new ItemData(Materials.Ice, 7257600L, new MaterialStack[0]));
/* 113:111 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.snow, 1, 32767), new ItemData(Materials.Snow, 3628800L, new MaterialStack[0]));
/* 114:112 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.snowball, 1, 32767), new ItemData(Materials.Snow, 907200L, new MaterialStack[0]));
/* 115:113 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.snow_layer, 1, 32767), new ItemData(Materials.Snow, -1L, new MaterialStack[0]));
/* 116:114 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.sand, 1, 32767), new ItemData(Materials.Sand, 3628800L, new MaterialStack[0]));
/* 117:115 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.sandstone, 1, 32767), new ItemData(Materials.Sand, 3628800L, new MaterialStack[0]));
/* 118:116 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 0), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 119:117 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 8), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 120:118 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 0), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 121:119 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 8), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 122:120 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 1), new ItemData(Materials.Sand, 1814400L, new MaterialStack[0]));
/* 123:121 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 9), new ItemData(Materials.Sand, 1814400L, new MaterialStack[0]));
/* 124:122 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 1), new ItemData(Materials.Sand, 3628800L, new MaterialStack[0]));
/* 125:123 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 9), new ItemData(Materials.Sand, 3628800L, new MaterialStack[0]));
/* 126:124 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 2), new ItemData(Materials.Wood, 1814400L, new MaterialStack[0]));
/* 127:125 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 10), new ItemData(Materials.Wood, 1814400L, new MaterialStack[0]));
/* 128:126 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 2), new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
/* 129:127 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 10), new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
/* 130:128 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 3), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 131:129 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 11), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 132:130 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 3), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 133:131 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 11), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 134:132 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 5), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 135:133 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_slab, 1, 13), new ItemData(Materials.Stone, 1814400L, new MaterialStack[0]));
/* 136:134 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 5), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 137:135 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.double_stone_slab, 1, 13), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 138:136 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 139:137 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.furnace, 1, 32767), new ItemData(Materials.Stone, 29030400L, new MaterialStack[0]));
/* 140:138 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.lit_furnace, 1, 32767), new ItemData(Materials.Stone, 29030400L, new MaterialStack[0]));
/* 141:139 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stonebrick, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 142:140 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.cobblestone, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 143:141 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.mossy_cobblestone, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 144:142 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_button, 1, 32767), new ItemData(Materials.Stone, 3628800L, new MaterialStack[0]));
/* 145:143 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.stone_pressure_plate, 1, 32767), new ItemData(Materials.Stone, 7257600L, new MaterialStack[0]));
/* 146:144 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.ladder, 1, 32767), new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
/* 147:145 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.wooden_button, 1, 32767), new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
/* 148:146 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.wooden_pressure_plate, 1, 32767), new ItemData(Materials.Wood, 7257600L, new MaterialStack[0]));
/* 149:147 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.fence, 1, 32767), new ItemData(Materials.Wood, 5443200L, new MaterialStack[0]));
/* 150:148 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.bowl, 1, 32767), new ItemData(Materials.Wood, 3628800L, new MaterialStack[0]));
/* 151:149 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.sign, 1, 32767), new ItemData(Materials.Wood, 7257600L, new MaterialStack[0]));
/* 152:150 */     GT_OreDictUnificator.addItemData(new ItemStack(Items.wooden_door, 1, 32767), new ItemData(Materials.Wood, 21772800L, new MaterialStack[0]));
/* 153:151 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.chest, 1, 32767), new ItemData(Materials.Wood, 29030400L, new MaterialStack[0]));
/* 154:152 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.trapped_chest, 1, 32767), new ItemData(Materials.Wood, 32659200L, new MaterialStack[] { new MaterialStack(Materials.Iron, OrePrefixes.ring.mMaterialAmount * 2L) }));
/* 155:153 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.unlit_redstone_torch, 1, 32767), new ItemData(Materials.Wood, 1814400L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 156:154 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.redstone_torch, 1, 32767), new ItemData(Materials.Wood, 1814400L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 157:155 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.noteblock, 1, 32767), new ItemData(Materials.Wood, 29030400L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 158:156 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.jukebox, 1, 32767), new ItemData(Materials.Wood, 29030400L, new MaterialStack[] { new MaterialStack(Materials.Diamond, 3628800L) }));
/* 159:157 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.crafting_table, 1, 32767), new ItemData(Materials.Wood, 14515200L, new MaterialStack[0]));
/* 160:158 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.piston, 1, 32767), new ItemData(Materials.Stone, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Wood, 10886400L) }));
/* 161:159 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.sticky_piston, 1, 32767), new ItemData(Materials.Stone, 14515200L, new MaterialStack[] { new MaterialStack(Materials.Wood, 10886400L) }));
/* 162:160 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.dispenser, 1, 32767), new ItemData(Materials.Stone, 25401600L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 163:161 */     GT_OreDictUnificator.addItemData(new ItemStack(Blocks.dropper, 1, 32767), new ItemData(Materials.Stone, 25401600L, new MaterialStack[] { new MaterialStack(Materials.Redstone, 3628800L) }));
/* 164:162 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Thaumcraft", "ItemNuggetChicken", 1L, 32767), new ItemData(Materials.MeatCooked, 403200L, new MaterialStack[0]));
/* 165:163 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Thaumcraft", "ItemNuggetBeef", 1L, 32767), new ItemData(Materials.MeatCooked, 403200L, new MaterialStack[0]));
/* 166:164 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Thaumcraft", "ItemNuggetPork", 1L, 32767), new ItemData(Materials.MeatCooked, 403200L, new MaterialStack[0]));
/* 167:165 */     GT_OreDictUnificator.addItemData(GT_ModHandler.getModItem("Thaumcraft", "ItemNuggetFish", 1L, 32767), new ItemData(Materials.MeatCooked, 403200L, new MaterialStack[0]));
/* 168:167 */     for (ItemStack tItem : new ItemStack[] { GT_ModHandler.getModItem("TwilightForest", "item.meefRaw", 1L, 0), GT_ModHandler.getModItem("TwilightForest", "item.venisonRaw", 1L, 0), new ItemStack(Items.porkchop), new ItemStack(Items.beef), new ItemStack(Items.chicken), new ItemStack(Items.fish) }) {
/* 169:167 */       if (tItem != null) {
/* 170:168 */         GT_OreDictUnificator.addItemData(GT_Utility.copyMetaData(32767L, new Object[] { tItem }), new ItemData(Materials.MeatRaw, 3628800L, new MaterialStack[] { new MaterialStack(Materials.Bone, 403200L) }));
/* 171:    */       }
/* 172:    */     }
/* 173:170 */     for (ItemStack tItem : new ItemStack[] { GT_ModHandler.getModItem("TwilightForest", "item.meefSteak", 1L, 0), GT_ModHandler.getModItem("TwilightForest", "item.venisonCooked", 1L, 0), new ItemStack(Items.cooked_porkchop), new ItemStack(Items.cooked_beef), new ItemStack(Items.cooked_chicken), new ItemStack(Items.cooked_fished) }) {
/* 174:170 */       if (tItem != null) {
/* 175:171 */         GT_OreDictUnificator.addItemData(GT_Utility.copyMetaData(32767L, new Object[] { tItem }), new ItemData(Materials.MeatCooked, 3628800L, new MaterialStack[] { new MaterialStack(Materials.Bone, 403200L) }));
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.preload.GT_Loader_ItemData
 * JD-Core Version:    0.7.0.1
 */