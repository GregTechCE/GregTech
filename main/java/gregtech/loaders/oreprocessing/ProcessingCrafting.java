/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OreDictNames;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingCrafting implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingCrafting()
/*    */   {
/* 19 */     OrePrefixes.crafting.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 24 */     if (aOreDictName.equals(OreDictNames.craftingQuartz.toString())) {
/* 25 */       GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.redstone_torch, 3, 32767), GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Concrete.getMolten(144L), new ItemStack(net.minecraft.init.Items.comparator, 1, 0), 800, 1);
/* 26 */     } else if (aOreDictName.equals(OreDictNames.craftingWireCopper.toString())) {
/* 27 */       GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
/* 28 */     } else if (aOreDictName.equals(OreDictNames.craftingWireTin.toString())) {
/* 29 */       GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
/* 30 */     } else if (aOreDictName.equals(OreDictNames.craftingLensBlue.toString())) {
/* 31 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
/* 32 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
/* 33 */       GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1L, new Object[0]), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L, new Object[0]), 256, 480);
/* 34 */     } else if (aOreDictName.equals(OreDictNames.craftingLensYellow.toString())) {
/* 35 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
/* 36 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
/* 37 */     } else if (aOreDictName.equals(OreDictNames.craftingLensCyan.toString())) {
/* 38 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
/* 39 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
/* 40 */     } else if (aOreDictName.equals(OreDictNames.craftingLensRed.toString())) {
/* 41 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1L, 0), 50, 120);
/*    */       
/* 43 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
/* 44 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.AnnealedCopper, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
/* 45 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
/* 46 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
/* 47 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Platinum, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Elite.get(1L, new Object[0]), 64, 480);
/* 48 */     } else if (aOreDictName.equals(OreDictNames.craftingLensGreen.toString())) {
/* 49 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Olivine, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
/* 50 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
/* 51 */     } else if (aOreDictName.equals(OreDictNames.craftingLensWhite.toString())) {
/* 52 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
/* 53 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
/*    */       
/* 55 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.sandstone, 1, 2), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.sandstone, 1, 1), 50, 16);
/* 56 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.stone, 1, 0), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.stonebrick, 1, 3), 50, 16);
/* 57 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.quartz_block, 1, 0), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.quartz_block, 1, 1), 50, 16);
/* 58 */       GT_Values.RA.addLaserEngraverRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */