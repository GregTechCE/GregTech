/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  6:   */ import gregtech.api.util.GT_ModHandler;
/*  7:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  8:   */ import gregtech.api.util.GT_Utility;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class ProcessingWire16
/* 12:   */   implements IOreRecipeRegistrator
/* 13:   */ {
/* 14:   */   public ProcessingWire16()
/* 15:   */   {
/* 16:14 */     OrePrefixes.wireGt16.add(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 20:   */   {
/* 21:19 */     GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 16L), new Object[] { aOreDictName });
/* 22:20 */     GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new Object[] { OrePrefixes.wireGt08.get(aMaterial), OrePrefixes.wireGt08.get(aMaterial) });
/* 23:21 */     GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new Object[] { OrePrefixes.wireGt12.get(aMaterial), OrePrefixes.wireGt04.get(aMaterial) });
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingWire16
 * JD-Core Version:    0.7.0.1
 */