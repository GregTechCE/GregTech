/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingStick
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingStick()
/* 17:   */   {
/* 18:15 */     OrePrefixes.stick.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 24:20 */       GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.bolt, aMaterial, 4L), null, (int)Math.max(aMaterial.getMass() * 2L, 1L), 4);
/* 25:   */     }
/* 26:21 */     if (!aMaterial.contains(SubTag.NO_SMASHING))
/* 27:   */     {
/* 28:22 */       GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stickLong, aMaterial, 1L), (int)Math.max(aMaterial.getMass(), 1L), 16);
/* 29:23 */       GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.copy(new Object[] { GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L) }), 50, 4);
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingStick
 * JD-Core Version:    0.7.0.1
 */