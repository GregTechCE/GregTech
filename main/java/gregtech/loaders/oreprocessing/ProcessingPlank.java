/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.init.Blocks;
/* 13:   */ import net.minecraft.init.Items;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ 
/* 16:   */ public class ProcessingPlank
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingPlank()
/* 20:   */   {
/* 21:19 */     OrePrefixes.plank.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:24 */     if (aOreDictName.startsWith("plankWood"))
/* 27:   */     {
/* 28:25 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 2L), null, 10, 8);
/* 29:26 */       GT_Values.RA.addCNCRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.gearGt, Materials.Wood, 1L), 800, 1);
/* 30:27 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), new ItemStack(Blocks.noteblock, 1), 200, 4);
/* 31:28 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1L), new ItemStack(Blocks.jukebox, 1), 400, 4);
/* 32:29 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Iron, 1L), ItemList.Crate_Empty.get(1L, new Object[0]), 200, 1);
/* 33:30 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.WroughtIron, 1L), ItemList.Crate_Empty.get(1L, new Object[0]), 200, 1);
/* 34:31 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1L), ItemList.Crate_Empty.get(1L, new Object[0]), 200, 1);
/* 35:32 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), new ItemStack(Blocks.wooden_button, 1), 100, 4);
/* 36:33 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), new ItemStack(Blocks.wooden_pressure_plate, 1), 200, 4);
/* 37:34 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 3L, new Object[0]), new ItemStack(Blocks.trapdoor, 1), 300, 4);
/* 38:35 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), new ItemStack(Blocks.crafting_table, 1), 400, 4);
/* 39:36 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 6L, new Object[0]), new ItemStack(Items.wooden_door, 1), 600, 4);
/* 40:37 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), new ItemStack(Blocks.chest, 1), 800, 4);
/* 41:38 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), new ItemStack(Items.book, 3), new ItemStack(Blocks.bookshelf, 1), 400, 4);
/* 42:40 */       if (aStack.getItemDamage() == 32767)
/* 43:   */       {
/* 44:41 */         for (byte i = 0; i < 16; i = (byte)(i + 1))
/* 45:   */         {
/* 46:42 */           ItemStack tStack = GT_Utility.copyMetaData(i, new Object[] { aStack });ItemStack tOutput = GT_ModHandler.getRecipeOutput(new ItemStack[] { tStack, tStack, tStack });
/* 47:43 */           if ((tOutput != null) && (tOutput.stackSize >= 3))
/* 48:   */           {
/* 49:44 */             GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { tStack }), GT_Utility.copyAmount(tOutput.stackSize / 3, new Object[] { tOutput }), null, 25, 4);
/* 50:45 */             GT_ModHandler.removeRecipe(new ItemStack[] { tStack, tStack, tStack });
/* 51:46 */             GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(tOutput.stackSize / 3, new Object[] { tOutput }), new Object[] { "sP", Character.valueOf('P'), tStack });
/* 52:   */           }
/* 53:   */         }
/* 54:   */       }
/* 55:   */       else
/* 56:   */       {
/* 57:50 */         ItemStack tOutput = GT_ModHandler.getRecipeOutput(new ItemStack[] { aStack, aStack, aStack });
/* 58:51 */         if ((tOutput != null) && (tOutput.stackSize >= 3))
/* 59:   */         {
/* 60:52 */           GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copyAmount(tOutput.stackSize / 3, new Object[] { tOutput }), null, 25, 4);
/* 61:53 */           GT_ModHandler.removeRecipe(new ItemStack[] { aStack, aStack, aStack });
/* 62:54 */           GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(tOutput.stackSize / 3, new Object[] { tOutput }), new Object[] { "sP", Character.valueOf('P'), aStack });
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPlank
 * JD-Core Version:    0.7.0.1
 */