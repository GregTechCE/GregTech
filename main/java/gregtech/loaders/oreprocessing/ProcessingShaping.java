/*     */ package gregtech.loaders.oreprocessing;
/*     */ 
/*     */ import gregtech.api.enums.GT_Values;
/*     */ import gregtech.api.enums.ItemList;
/*     */ import gregtech.api.enums.Materials;
/*     */ import gregtech.api.enums.OrePrefixes;
/*     */ import gregtech.api.enums.SubTag;
/*     */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*     */ import gregtech.api.util.GT_ModHandler;
/*     */ import gregtech.api.util.GT_OreDictUnificator;
/*     */ import gregtech.api.util.GT_Utility;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ProcessingShaping implements gregtech.api.interfaces.IOreRecipeRegistrator
/*     */ {
/*     */   public ProcessingShaping()
/*     */   {
/*  20 */     OrePrefixes.ingot.add(this);
/*  21 */     OrePrefixes.dust.add(this);
/*     */   }
/*     */   
/*     */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*     */   {
/*  26 */     if (((aMaterial == Materials.Glass) || (GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L) != null)) && (!aMaterial.contains(SubTag.NO_SMELTING))) {
/*  27 */       int tAmount = (int)(aPrefix.mMaterialAmount / 3628800L);
/*  28 */       if ((tAmount > 0) && (tAmount <= 64) && (aPrefix.mMaterialAmount % 3628800L == 0L)) {
/*  29 */         int tVoltageMultiplier = aMaterial.mBlastFurnaceTemp >= 2800 ? 64 : 16;
/*     */         
/*  31 */         if (aMaterial.contains(SubTag.NO_SMASHING)) {
/*  32 */           tVoltageMultiplier /= 4;
/*     */         }
/*  34 */         else if (aPrefix.name().startsWith(OrePrefixes.dust.name())) { return;
/*     */         }
/*     */         
/*  37 */         if (!OrePrefixes.block.isIgnored(aMaterial.mSmeltInto)) {
/*  38 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Extruder_Block.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial.mSmeltInto, tAmount), 10 * tAmount, 8 * tVoltageMultiplier);
/*  39 */           GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), ItemList.Shape_Mold_Block.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial.mSmeltInto, tAmount), 5 * tAmount, 4 * tVoltageMultiplier);
/*     */         }
/*  41 */         if ((aPrefix != OrePrefixes.ingot) || (aMaterial != aMaterial.mSmeltInto)) {
/*  42 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial.mSmeltInto, tAmount), 10, 4 * tVoltageMultiplier);
/*     */         }
/*     */         
/*  45 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Tiny.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeTiny, aMaterial.mSmeltInto, tAmount * 2), 4 * tAmount, 8 * tVoltageMultiplier);
/*  46 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Small.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeSmall, aMaterial.mSmeltInto, tAmount), 8 * tAmount, 8 * tVoltageMultiplier);
/*  47 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Medium.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeMedium, aMaterial.mSmeltInto, tAmount), 24 * tAmount, 8 * tVoltageMultiplier);
/*  48 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Large.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeLarge, aMaterial.mSmeltInto, tAmount), 48 * tAmount, 8 * tVoltageMultiplier);
/*  49 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(12L, new Object[] { aStack }), ItemList.Shape_Extruder_Pipe_Huge.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.pipeHuge, aMaterial.mSmeltInto, tAmount), 96 * tAmount, 8 * tVoltageMultiplier);
/*  50 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Plate.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  51 */         if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.stick, aMaterial.mSmeltInto, tAmount * 2), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  52 */         if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Wire.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial.mSmeltInto, tAmount * 2), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  53 */         if (tAmount * 8 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Bolt.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial.mSmeltInto, tAmount * 8), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  54 */         if (tAmount * 4 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Ring.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.ring, aMaterial.mSmeltInto, tAmount * 4), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
/*  55 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Sword.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  56 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Pickaxe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  57 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Shovel.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  58 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Shape_Extruder_Axe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  59 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Hoe.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  60 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), ItemList.Shape_Extruder_Hammer.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadHammer, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 6L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  61 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_File.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadFile, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  62 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Saw.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.toolHeadSaw, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*  63 */         GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Shape_Extruder_Gear.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 5L * tAmount, tAmount), 8 * tVoltageMultiplier);
/*     */         
/*  65 */         GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Plate.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 2L * tAmount, tAmount), 2 * tVoltageMultiplier);
/*  66 */         GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Shape_Mold_Gear.get(0L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.gearGt, aMaterial.mSmeltInto, tAmount), (int)Math.max(aMaterial.getMass() * 10L * tAmount, tAmount), 2 * tVoltageMultiplier);
/*  67 */         switch (aMaterial.mSmeltInto) {
/*     */         case Glass: 
/*  69 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Bottle.get(0L, new Object[0]), new ItemStack(Items.glass_bottle, 1), tAmount * 32, 16);
/*  70 */           GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Mold_Bottle.get(0L, new Object[0]), new ItemStack(Items.glass_bottle, 1), tAmount * 64, 4);
/*  71 */           break;
/*     */         case Steel: 
/*  73 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingadviron", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  74 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingadviron", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         case Iron: case WroughtIron: 
/*  77 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Cell.get(0L, new Object[0]), GT_ModHandler.getIC2Item("fuelRod", tAmount), tAmount * 128, 32);
/*  78 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingiron", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  79 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingiron", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*  80 */           if (tAmount * 31 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(31L, new Object[] { aStack }), ItemList.Shape_Mold_Anvil.get(0L, new Object[0]), new ItemStack(Blocks.anvil, 1, 0), tAmount * 512, 4 * tVoltageMultiplier);
/*     */           break;
/*     */         case Tin: 
/*  83 */           GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Extruder_Cell.get(0L, new Object[0]), ItemList.Cell_Empty.get(tAmount, new Object[0]), tAmount * 128, 32);
/*  84 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingtin", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  85 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingtin", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         case Lead: 
/*  88 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinglead", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  89 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinglead", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         case Copper: case AnnealedCopper: 
/*  92 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingcopper", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  93 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingcopper", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         case Bronze: 
/*  96 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingbronze", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/*  97 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casingbronze", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         case Gold: 
/* 100 */           if (tAmount * 2 <= 64) GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinggold", tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
/* 101 */           if (tAmount * 2 <= 64) GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Shape_Mold_Casing.get(0L, new Object[0]), GT_ModHandler.getIC2Item("casinggold", tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingShaping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */