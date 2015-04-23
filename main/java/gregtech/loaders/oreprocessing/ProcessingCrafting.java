/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OreDictNames;
/*  7:   */ import gregtech.api.enums.OrePrefixes;
/*  8:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  9:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import net.minecraft.init.Blocks;
/* 14:   */ import net.minecraft.init.Items;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ 
/* 17:   */ public class ProcessingCrafting
/* 18:   */   implements IOreRecipeRegistrator
/* 19:   */ {
/* 20:   */   public ProcessingCrafting()
/* 21:   */   {
/* 22:19 */     OrePrefixes.crafting.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:24 */     if (aOreDictName.equals(OreDictNames.craftingQuartz.toString()))
/* 28:   */     {
/* 29:25 */       GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.redstone_torch, 3, 32767), GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Concrete.getMolten(144L), new ItemStack(Items.comparator, 1, 0), 800, 1);
/* 30:   */     }
/* 31:26 */     else if (aOreDictName.equals(OreDictNames.craftingWireCopper.toString()))
/* 32:   */     {
/* 33:27 */       GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
/* 34:   */     }
/* 35:28 */     else if (aOreDictName.equals(OreDictNames.craftingWireTin.toString()))
/* 36:   */     {
/* 37:29 */       GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
/* 38:   */     }
/* 39:30 */     else if (aOreDictName.equals(OreDictNames.craftingLensBlue.toString()))
/* 40:   */     {
/* 41:31 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
/* 42:32 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
/* 43:33 */       GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1L, new Object[0]), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L, new Object[0]), 256, 480);
/* 44:   */     }
/* 45:34 */     else if (aOreDictName.equals(OreDictNames.craftingLensYellow.toString()))
/* 46:   */     {
/* 47:35 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
/* 48:36 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
/* 49:   */     }
/* 50:37 */     else if (aOreDictName.equals(OreDictNames.craftingLensCyan.toString()))
/* 51:   */     {
/* 52:38 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
/* 53:39 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
/* 54:   */     }
/* 55:40 */     else if (aOreDictName.equals(OreDictNames.craftingLensRed.toString()))
/* 56:   */     {
/* 57:41 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1L, 0), 50, 120);
/* 58:   */       
/* 59:43 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
/* 60:44 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.AnnealedCopper, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
/* 61:45 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
/* 62:46 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
/* 63:47 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Platinum, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Wiring_Elite.get(1L, new Object[0]), 64, 480);
/* 64:   */     }
/* 65:48 */     else if (aOreDictName.equals(OreDictNames.craftingLensGreen.toString()))
/* 66:   */     {
/* 67:49 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Olivine, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
/* 68:50 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
/* 69:   */     }
/* 70:51 */     else if (aOreDictName.equals(OreDictNames.craftingLensWhite.toString()))
/* 71:   */     {
/* 72:52 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
/* 73:53 */       GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
/* 74:   */       
/* 75:55 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.sandstone, 1, 2), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.sandstone, 1, 1), 50, 16);
/* 76:56 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.stone, 1, 0), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.stonebrick, 1, 3), 50, 16);
/* 77:57 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.quartz_block, 1, 0), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(Blocks.quartz_block, 1, 1), 50, 16);
/* 78:58 */       GT_Values.RA.addLaserEngraverRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L), GT_Utility.copyAmount(0L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
/* 79:   */     }
/* 80:   */   }
/* 81:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCrafting
 * JD-Core Version:    0.7.0.1
 */