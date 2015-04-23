/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OreDictNames;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingCircuit
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingCircuit()
/* 16:   */   {
/* 17:14 */     OrePrefixes.circuit.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:19 */     switch (aMaterial.ordinal())
/* 23:   */     {
/* 24:   */     case 1: 
/* 25:   */     case 2: 
/* 26:   */     case 3: 
/* 27:   */     case 4: 
/* 28:   */     case 5: 
/* 29:   */     case 6: 
/* 30:21 */       if (!GT_OreDictUnificator.isBlacklisted(aStack)) {
/* 31:21 */         GT_ModHandler.removeRecipeByOutput(aStack);
/* 32:   */       }
/* 33:   */       break;
/* 34:   */     case 7: 
/* 35:24 */       GT_ModHandler.removeRecipeByOutput(aStack);
/* 36:25 */       GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Primitive.get(1L, new Object[0]), new Object[] { GT_ModHandler.getIC2Item("casingadviron", 1L), OrePrefixes.wireGt01.get(Materials.RedAlloy), OrePrefixes.wireGt01.get(Materials.RedAlloy), OrePrefixes.wireGt01.get(Materials.Tin) });
/* 37:26 */       break;
/* 38:   */     case 8: 
/* 39:28 */       GT_ModHandler.removeRecipeByOutput(aStack);
/* 40:29 */       GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), new Object[] { "WWW", "CPC", "WWW", Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Primitive), Character.valueOf('W'), OreDictNames.craftingWireCopper, Character.valueOf('P'), OrePrefixes.plate.get(Materials.Steel) });
/* 41:30 */       GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), new Object[] { "WCW", "WPW", "WCW", Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Primitive), Character.valueOf('W'), OreDictNames.craftingWireCopper, Character.valueOf('P'), OrePrefixes.plate.get(Materials.Steel) });
/* 42:31 */       GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), new Object[] { ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCircuit
 * JD-Core Version:    0.7.0.1
 */