/*   1:    */ package gregtech.loaders.preload;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.ItemList;
/*   5:    */ import gregtech.api.enums.Materials;
/*   6:    */ import gregtech.api.enums.OreDictNames;
/*   7:    */ import gregtech.api.enums.OrePrefixes;
/*   8:    */ import gregtech.api.util.GT_Log;
/*   9:    */ import gregtech.api.util.GT_ModHandler;
/*  10:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.init.Items;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ 
/*  16:    */ public class GT_Loader_OreDictionary
/*  17:    */   implements Runnable
/*  18:    */ {
/*  19:    */   public void run()
/*  20:    */   {
/*  21: 19 */     GT_Log.out.println("GT_Mod: Register OreDict Entries of Non-GT-Items.");
/*  22: 20 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Empty, ItemList.Cell_Empty.get(1L, new Object[0]));
/*  23: 21 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Lava, ItemList.Cell_Lava.get(1L, new Object[0]));
/*  24: 22 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Lava, GT_ModHandler.getIC2Item("lavaCell", 1L));
/*  25: 23 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Water, ItemList.Cell_Water.get(1L, new Object[0]));
/*  26: 24 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Water, GT_ModHandler.getIC2Item("waterCell", 1L));
/*  27: 25 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.Creosote, GT_ModHandler.getModItem("Railcraft", "fluid.creosote.cell", 1L));
/*  28:    */     
/*  29:    */ 
/*  30: 28 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.UUMatter, GT_ModHandler.getIC2Item("uuMatterCell", 1L));
/*  31: 29 */     GT_OreDictUnificator.set(OrePrefixes.cell, Materials.ConstructionFoam, GT_ModHandler.getIC2Item("CFCell", 1L));
/*  32:    */     
/*  33: 31 */     GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.Empty, new ItemStack(Items.bucket, 1, 0));
/*  34: 32 */     GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.Water, new ItemStack(Items.water_bucket, 1, 0));
/*  35: 33 */     GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.Lava, new ItemStack(Items.lava_bucket, 1, 0));
/*  36: 34 */     GT_OreDictUnificator.set(OrePrefixes.bucket, Materials.Milk, new ItemStack(Items.milk_bucket, 1, 0));
/*  37:    */     
/*  38: 36 */     GT_OreDictUnificator.set(OrePrefixes.bottle, Materials.Empty, new ItemStack(Items.glass_bottle, 1, 0));
/*  39: 37 */     GT_OreDictUnificator.set(OrePrefixes.bottle, Materials.Water, new ItemStack(Items.potionitem, 1, 0));
/*  40:    */     
/*  41: 39 */     GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.Iridium, GT_ModHandler.getIC2Item("iridiumPlate", 1L));
/*  42: 40 */     GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.Advanced, GT_ModHandler.getIC2Item("advancedAlloy", 1L));
/*  43: 41 */     GT_OreDictUnificator.set(OrePrefixes.plateAlloy, Materials.Carbon, GT_ModHandler.getIC2Item("carbonPlate", 1L));
/*  44:    */     
/*  45: 43 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Coal, new ItemStack(Blocks.coal_ore, 1));
/*  46: 44 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Iron, new ItemStack(Blocks.iron_ore, 1));
/*  47: 45 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Lapis, new ItemStack(Blocks.lapis_ore, 1));
/*  48: 46 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Redstone, new ItemStack(Blocks.redstone_ore, 1));
/*  49: 47 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Redstone, new ItemStack(Blocks.lit_redstone_ore, 1));
/*  50: 48 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Gold, new ItemStack(Blocks.gold_ore, 1));
/*  51: 49 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Diamond, new ItemStack(Blocks.diamond_ore, 1));
/*  52: 50 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Emerald, new ItemStack(Blocks.emerald_ore, 1));
/*  53: 51 */     GT_OreDictUnificator.set(OrePrefixes.ore, Materials.NetherQuartz, new ItemStack(Blocks.quartz_ore, 1));
/*  54: 52 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Copper, GT_ModHandler.getIC2Item("copperIngot", 1L));
/*  55: 53 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Tin, GT_ModHandler.getIC2Item("tinIngot", 1L));
/*  56: 54 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Lead, GT_ModHandler.getIC2Item("leadIngot", 1L));
/*  57: 55 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Bronze, GT_ModHandler.getIC2Item("bronzeIngot", 1L));
/*  58: 56 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Silver, GT_ModHandler.getIC2Item("silverIngot", 1L));
/*  59: 57 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Iridium, GT_ModHandler.getIC2Item("iridiumOre", 1L));
/*  60: 58 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Lapis, new ItemStack(Items.dye, 1, 4));
/*  61: 59 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.EnderEye, new ItemStack(Items.ender_eye, 1));
/*  62: 60 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.EnderPearl, new ItemStack(Items.ender_pearl, 1));
/*  63: 61 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Diamond, new ItemStack(Items.diamond, 1));
/*  64: 62 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Emerald, new ItemStack(Items.emerald, 1));
/*  65: 63 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Coal, new ItemStack(Items.coal, 1, 0));
/*  66: 64 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Charcoal, new ItemStack(Items.coal, 1, 1));
/*  67: 65 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.NetherQuartz, new ItemStack(Items.quartz, 1));
/*  68: 66 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.NetherStar, new ItemStack(Items.nether_star, 1));
/*  69: 67 */     GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.Gold, new ItemStack(Items.gold_nugget, 1));
/*  70: 68 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Gold, new ItemStack(Items.gold_ingot, 1));
/*  71: 69 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Iron, new ItemStack(Items.iron_ingot, 1));
/*  72: 70 */     GT_OreDictUnificator.set(OrePrefixes.plate, Materials.Paper, new ItemStack(Items.paper, 1));
/*  73: 71 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Sugar, new ItemStack(Items.sugar, 1));
/*  74: 72 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Bone, ItemList.Dye_Bonemeal.get(1L, new Object[0]));
/*  75: 73 */     GT_OreDictUnificator.set(OrePrefixes.stick, Materials.Wood, new ItemStack(Items.stick, 1));
/*  76: 74 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Redstone, new ItemStack(Items.redstone, 1));
/*  77: 75 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Gunpowder, new ItemStack(Items.gunpowder, 1));
/*  78: 76 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Glowstone, new ItemStack(Items.glowstone_dust, 1));
/*  79: 77 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Blaze, new ItemStack(Items.blaze_powder, 1));
/*  80: 78 */     GT_OreDictUnificator.set(OrePrefixes.stick, Materials.Blaze, new ItemStack(Items.blaze_rod, 1));
/*  81: 79 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Iron, new ItemStack(Blocks.iron_block, 1, 0));
/*  82: 80 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Gold, new ItemStack(Blocks.gold_block, 1, 0));
/*  83: 81 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Diamond, new ItemStack(Blocks.diamond_block, 1, 0));
/*  84: 82 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Emerald, new ItemStack(Blocks.emerald_block, 1, 0));
/*  85: 83 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Lapis, new ItemStack(Blocks.lapis_block, 1, 0));
/*  86: 84 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Coal, new ItemStack(Blocks.coal_block, 1, 0));
/*  87: 85 */     GT_OreDictUnificator.set(OrePrefixes.block, Materials.Redstone, new ItemStack(Blocks.redstone_block, 1, 0));
/*  88: 87 */     if (Blocks.ender_chest != null) {
/*  89: 88 */       GT_OreDictUnificator.registerOre(OreDictNames.enderChest, new ItemStack(Blocks.ender_chest, 1));
/*  90:    */     }
/*  91: 89 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingAnvil, new ItemStack(Blocks.anvil, 1));
/*  92: 90 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingAnvil, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1L, 0));
/*  93: 91 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingIndustrialDiamond, ItemList.IC2_Industrial_Diamond.get(1L, new Object[0]));
/*  94: 92 */     GT_OreDictUnificator.registerOre(OrePrefixes.dust, Materials.Wood, GT_ModHandler.getModItem("ThermalExpansion", "sawdust", 1L));
/*  95: 93 */     GT_OreDictUnificator.registerOre(OrePrefixes.glass, Materials.Reinforced, GT_ModHandler.getIC2Item("reinforcedGlass", 1L));
/*  96: 94 */     GT_OreDictUnificator.registerOre(OrePrefixes.glass, Materials.Reinforced, GT_ModHandler.getModItem("ThermalExpansion", "glassHardened", 1L));
/*  97:    */     
/*  98: 96 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1L, 6));
/*  99: 97 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1L, 7));
/* 100: 98 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.brick.abyssal", 1L, 32767));
/* 101: 99 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.brick.quarried", 1L, 32767));
/* 102:100 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Obsidian, new ItemStack(Blocks.obsidian, 1, 32767));
/* 103:101 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.mossy_cobblestone, 1, 32767));
/* 104:102 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.mossy_cobblestone, 1, 32767));
/* 105:103 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneCobble, new ItemStack(Blocks.cobblestone, 1, 32767));
/* 106:104 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneSmooth, new ItemStack(Blocks.stone, 1, 32767));
/* 107:105 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneBricks, new ItemStack(Blocks.stonebrick, 1, 32767));
/* 108:106 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneMossy, new ItemStack(Blocks.stonebrick, 1, 1));
/* 109:107 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneCracked, new ItemStack(Blocks.stonebrick, 1, 2));
/* 110:108 */     GT_OreDictUnificator.registerOre(OrePrefixes.stoneChiseled, new ItemStack(Blocks.stonebrick, 1, 3));
/* 111:109 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Sand, new ItemStack(Blocks.sandstone, 1, 32767));
/* 112:110 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Netherrack, new ItemStack(Blocks.netherrack, 1, 32767));
/* 113:111 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.NetherBrick, new ItemStack(Blocks.nether_brick, 1, 32767));
/* 114:112 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Endstone, new ItemStack(Blocks.end_stone, 1, 32767));
/* 115:    */     
/* 116:114 */     GT_OreDictUnificator.registerOre("paperResearchFragment", GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1L, 9));
/* 117:115 */     GT_OreDictUnificator.registerOre("itemCertusQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1));
/* 118:116 */     GT_OreDictUnificator.registerOre("itemNetherQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 10));
/* 119:117 */     GT_OreDictUnificator.registerOre("itemCertusQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 11));
/* 120:118 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 1));
/* 121:119 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 10));
/* 122:120 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 11));
/* 123:121 */     GT_OreDictUnificator.registerOre("cropLemon", ItemList.FR_Lemon.get(1L, new Object[0]));
/* 124:122 */     GT_OreDictUnificator.registerOre("cropCoffee", ItemList.IC2_CoffeeBeans.get(1L, new Object[0]));
/* 125:123 */     GT_OreDictUnificator.registerOre("cropPotato", ItemList.Food_Raw_Potato.get(1L, new Object[0]));
/* 126:124 */     GT_OreDictUnificator.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item("reBattery", 1L));
/* 127:125 */     GT_OreDictUnificator.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item("chargedReBattery", 1L, 32767));
/* 128:126 */     GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.Basic, GT_ModHandler.getIC2Item("reBattery", 1L));
/* 129:127 */     GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.Basic, GT_ModHandler.getIC2Item("chargedReBattery", 1L, 32767));
/* 130:128 */     GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.Advanced, GT_ModHandler.getIC2Item("advBattery", 1L, 32767));
/* 131:129 */     GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.Elite, GT_ModHandler.getIC2Item("energyCrystal", 1L, 32767));
/* 132:130 */     GT_OreDictUnificator.registerOre(OrePrefixes.battery, Materials.Master, GT_ModHandler.getIC2Item("lapotronCrystal", 1L, 32767));
/* 133:    */     
/* 134:132 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWireCopper, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
/* 135:133 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWireGold, GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));
/* 136:134 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWireIron, GT_ModHandler.getIC2Item("insulatedIronCableItem", 1L));
/* 137:135 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWireTin, GT_ModHandler.getIC2Item("insulatedTinCableItem", 1L, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L)));
/* 138:    */     
/* 139:137 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.redstone_torch, 1, 32767));
/* 140:138 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.unlit_redstone_torch, 1, 32767));
/* 141:    */     
/* 142:140 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(Blocks.crafting_table, 1));
/* 143:141 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(GregTech_API.sBlockMachines, 1, 16));
/* 144:    */     
/* 145:143 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.piston, 1, 32767));
/* 146:144 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.sticky_piston, 1, 32767));
/* 147:    */     
/* 148:146 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingSafe, new ItemStack(GregTech_API.sBlockMachines, 1, 45));
/* 149:147 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingSafe, GT_ModHandler.getIC2Item("personalSafe", 1L));
/* 150:    */     
/* 151:149 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.chest, 1, 32767));
/* 152:150 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.trapped_chest, 1, 32767));
/* 153:    */     
/* 154:152 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.furnace, 1, 32767));
/* 155:153 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.lit_furnace, 1, 32767));
/* 156:    */     
/* 157:155 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingPump, GT_ModHandler.getIC2Item("pump", 1L));
/* 158:156 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingElectromagnet, GT_ModHandler.getIC2Item("magnetizer", 1L));
/* 159:157 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingTeleporter, GT_ModHandler.getIC2Item("teleporter", 1L));
/* 160:    */     
/* 161:159 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingMacerator, GT_ModHandler.getIC2Item("macerator", 1L));
/* 162:160 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingMacerator, new ItemStack(GregTech_API.sBlockMachines, 1, 50));
/* 163:    */     
/* 164:    */ 
/* 165:163 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingExtractor, GT_ModHandler.getIC2Item("extractor", 1L));
/* 166:164 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingExtractor, new ItemStack(GregTech_API.sBlockMachines, 1, 51));
/* 167:    */     
/* 168:166 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingCompressor, GT_ModHandler.getIC2Item("compressor", 1L));
/* 169:167 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingCompressor, new ItemStack(GregTech_API.sBlockMachines, 1, 52));
/* 170:    */     
/* 171:169 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingRecycler, GT_ModHandler.getIC2Item("recycler", 1L));
/* 172:170 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingRecycler, new ItemStack(GregTech_API.sBlockMachines, 1, 53));
/* 173:    */     
/* 174:172 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingIronFurnace, GT_ModHandler.getIC2Item("ironFurnace", 1L));
/* 175:    */     
/* 176:174 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingCentrifuge, new ItemStack(GregTech_API.sBlockMachines, 1, 62));
/* 177:    */     
/* 178:176 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingInductionFurnace, GT_ModHandler.getIC2Item("inductionFurnace", 1L));
/* 179:    */     
/* 180:    */ 
/* 181:179 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingElectricFurnace, GT_ModHandler.getIC2Item("electroFurnace", 1L));
/* 182:180 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingElectricFurnace, new ItemStack(GregTech_API.sBlockMachines, 1, 54));
/* 183:    */     
/* 184:182 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingGenerator, GT_ModHandler.getIC2Item("generator", 1L));
/* 185:    */     
/* 186:184 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingGeothermalGenerator, GT_ModHandler.getIC2Item("geothermalGenerator", 1L));
/* 187:    */     
/* 188:186 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingFeather, new ItemStack(Items.feather, 1, 32767));
/* 189:187 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingFeather, GT_ModHandler.getModItem("TwilightForest", "item.tfFeather", 1L, 32767));
/* 190:    */     
/* 191:189 */     GT_OreDictUnificator.registerOre("itemWheat", new ItemStack(Items.wheat, 1, 32767));
/* 192:190 */     GT_OreDictUnificator.registerOre("paperEmpty", new ItemStack(Items.paper, 1, 32767));
/* 193:191 */     GT_OreDictUnificator.registerOre("paperMap", new ItemStack(Items.map, 1, 32767));
/* 194:192 */     GT_OreDictUnificator.registerOre("paperMap", new ItemStack(Items.filled_map, 1, 32767));
/* 195:193 */     GT_OreDictUnificator.registerOre("bookEmpty", new ItemStack(Items.book, 1, 32767));
/* 196:194 */     GT_OreDictUnificator.registerOre("bookWritable", new ItemStack(Items.writable_book, 1, 32767));
/* 197:195 */     GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(Items.written_book, 1, 32767));
/* 198:196 */     GT_OreDictUnificator.registerOre("bookEnchanted", new ItemStack(Items.enchanted_book, 1, 32767));
/* 199:197 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.book, 1, 32767));
/* 200:198 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.writable_book, 1, 32767));
/* 201:199 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.written_book, 1, 32767));
/* 202:200 */     GT_OreDictUnificator.registerOre(OreDictNames.craftingBook, new ItemStack(Items.enchanted_book, 1, 32767));
/* 203:    */     
/* 204:202 */     GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.Basic, GT_ModHandler.getIC2Item("electronicCircuit", 1L));
/* 205:203 */     GT_OreDictUnificator.registerOre(OrePrefixes.circuit, Materials.Advanced, GT_ModHandler.getIC2Item("advancedCircuit", 1L));
/* 206:    */   }
/* 207:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.preload.GT_Loader_OreDictionary
 * JD-Core Version:    0.7.0.1
 */