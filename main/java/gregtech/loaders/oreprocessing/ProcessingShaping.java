/*   1:    */ package gregtech.loaders.oreprocessing;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.GT_Values;
/*   4:    */ import gregtech.api.enums.ItemList;
/*   5:    */ import gregtech.api.enums.Materials;
/*   6:    */ import gregtech.api.enums.OrePrefixes;
/*   7:    */ import gregtech.api.enums.SubTag;
/*   8:    */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*   9:    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  10:    */ import gregtech.api.util.GT_ModHandler;
/*  11:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  12:    */ import gregtech.api.util.GT_Utility;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ 
/*  17:    */ public class ProcessingShaping
/*  18:    */   implements IOreRecipeRegistrator
/*  19:    */ {
/*  20:    */   public ProcessingShaping()
/*  21:    */   {
/*  22: 20 */     OrePrefixes.ingot.add(this);
/*  23: 21 */     OrePrefixes.dust.add(this);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*  27:    */   {
/*  28: 26 */     if (((aMaterial == Materials.Glass) || (GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L) != null)) && (!aMaterial.contains(SubTag.NO_SMELTING)))
/*  29:    */     {
/*  30: 27 */       int tAmount = (int)(aPrefix.mMaterialAmount / 3628800L);
/*  31: 28 */       if ((tAmount > 0) && (tAmount <= 64) && (aPrefix.mMaterialAmount % 3628800L == 0L))
/*  32:    */       {
/*  33: 29 */         int tVoltageMultiplier = aMaterial.mBlastFurnaceTemp >= 2800 ? 64 : 16;
/*  34: 31 */         if (aMaterial.contains(SubTag.NO_SMASHING)) {
/*  35: 32 */           tVoltageMultiplier /= 4;
/*  36: 34 */         } else if (aPrefix.name().startsWith(OrePrefixes.dust.name())) {
/*  37: 34 */           return;
/*  38:    */         }
/*  39: 37 */         if (!OrePrefixes.block.isIgnored(aMaterial.mSmeltInto))
/*  40:    */         {
/*  41: 38 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Extruder_Block.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial.mSmeltInto, tAmount), 10 * tAmount, 8 * tVoltageMultiplier);
/*  42: 39 */           GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Mold_Block.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial.mSmeltInto, tAmount), 5 * tAmount, 4 * tVoltageMultiplier);
/*  43:    */         }
/*  44: 41 */         if ((aPrefix != OrePrefixes.ingot) || (aMaterial != aMaterial.mSmeltInto)) {
/*  45: 42 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, tAmount), 10, 4 * tVoltageMultiplier);
/*  46:    */         }
/*  47: 45 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Tiny.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeTiny, aMaterial.mSmeltInto, tAmount * 2), 4 * tAmount, 8 * tVoltageMultiplier);
/*  48: 46 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Small.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeSmall, aMaterial.mSmeltInto, tAmount), 8 * tAmount, 8 * tVoltageMultiplier);
/*  49: 47 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Medium.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, aMaterial.mSmeltInto, tAmount), 24 * tAmount, 8 * tVoltageMultiplier);
/*  50: 48 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Large.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, aMaterial.mSmeltInto, tAmount), 48 * tAmount, 8 * tVoltageMultiplier);
/*  51: 49 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(12L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Huge.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeHuge, aMaterial.mSmeltInto, tAmount), 96 * tAmount, 8 * tVoltageMultiplier);
/*  52: 50 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Plate.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  53: 51 */         if (tAmount * 2 <= 64) {
/*  54: 51 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial.mSmeltInto, tAmount * 2), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  55:    */         }
/*  56: 52 */         if (tAmount * 2 <= 64) {
/*  57: 52 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Wire.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial.mSmeltInto, tAmount * 2), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  58:    */         }
/*  59: 53 */         if (tAmount * 8 <= 64) {
/*  60: 53 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Bolt.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial.mSmeltInto, tAmount * 8), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  61:    */         }
/*  62: 54 */         if (tAmount * 4 <= 64) {
/*  63: 54 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Ring.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial.mSmeltInto, tAmount * 4), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  64:    */         }
/*  65: 55 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Sword.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  66: 56 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Pickaxe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  67: 57 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Shovel.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  68: 58 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Axe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  69: 59 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Hoe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  70: 60 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), ItemList.Shape_Extruder_Hammer.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadHammer, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 6L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  71: 61 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_File.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadFile, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  72: 62 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Saw.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  73: 63 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Shape_Extruder_Gear.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 5L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  74:    */         
/*  75: 65 */         GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Plate.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 2 * tVoltageMultiplier);
/*  76: 66 */         GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Shape_Mold_Gear.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 10L * tAmount, tAmount), 2 * tVoltageMultiplier);
/*  77: 67 */         switch (aMaterial.mSmeltInto.ordinal())
/*  78:    */         {
/*  79:    */         case 1: 
/*  80: 69 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Bottle.get(0L, new Object[0]), new ItemStack(Items.glass_bottle, 1), tAmount * 32, 16);
/*  81: 70 */           GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Mold_Bottle.get(0L, new Object[0]), new ItemStack(Items.glass_bottle, 1), tAmount * 64, 4);
/*  82: 71 */           break;
/*  83:    */         case 2: 
/*  84: 73 */           if (tAmount * 2 <= 64) {
/*  85: 73 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingadviron", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  86:    */           }
/*  87: 74 */           if (tAmount * 2 <= 64) {
/*  88: 74 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingadviron", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*  89:    */           }
/*  90:    */           break;
/*  91:    */         case 3: 
/*  92:    */         case 4: 
/*  93: 77 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Cell.get(0L, new Object[0]), GT_ModHandler.getIC2Item("fuelRod", tAmount), tAmount * 128, 32);
/*  94: 78 */           if (tAmount * 2 <= 64) {
/*  95: 78 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingiron", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  96:    */           }
/*  97: 79 */           if (tAmount * 2 <= 64) {
/*  98: 79 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingiron", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*  99:    */           }
/* 100: 80 */           if (tAmount * 31 <= 64) {
/* 101: 80 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(31L, new Object[] { aStack }), ItemList.Shape_Mold_Anvil.get(0L, new Object[0]), new ItemStack(Blocks.anvil, 1, 0), tAmount * 512, 4 * tVoltageMultiplier);
/* 102:    */           }
/* 103:    */           break;
/* 104:    */         case 5: 
/* 105: 83 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Cell.get(0L, new Object[0]), ItemList.Cell_Empty.get(tAmount, new Object[0]), tAmount * 128, 32);
/* 106: 84 */           if (tAmount * 2 <= 64) {
/* 107: 84 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingtin", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 108:    */           }
/* 109: 85 */           if (tAmount * 2 <= 64) {
/* 110: 85 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingtin", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/* 111:    */           }
/* 112:    */           break;
/* 113:    */         case 6: 
/* 114: 88 */           if (tAmount * 2 <= 64) {
/* 115: 88 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinglead", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 116:    */           }
/* 117: 89 */           if (tAmount * 2 <= 64) {
/* 118: 89 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinglead", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/* 119:    */           }
/* 120:    */           break;
/* 121:    */         case 7: 
/* 122:    */         case 8: 
/* 123: 92 */           if (tAmount * 2 <= 64) {
/* 124: 92 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingcopper", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 125:    */           }
/* 126: 93 */           if (tAmount * 2 <= 64) {
/* 127: 93 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingcopper", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/* 128:    */           }
/* 129:    */           break;
/* 130:    */         case 9: 
/* 131: 96 */           if (tAmount * 2 <= 64) {
/* 132: 96 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingbronze", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 133:    */           }
/* 134: 97 */           if (tAmount * 2 <= 64) {
/* 135: 97 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingbronze", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/* 136:    */           }
/* 137:    */           break;
/* 138:    */         case 10: 
/* 139:100 */           if (tAmount * 2 <= 64) {
/* 140:100 */             GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinggold", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 141:    */           }
/* 142:101 */           if (tAmount * 2 <= 64) {
/* 143:101 */             GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinggold", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/* 144:    */           }
/* 145:    */           break;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingShaping
 * JD-Core Version:    0.7.0.1
 */