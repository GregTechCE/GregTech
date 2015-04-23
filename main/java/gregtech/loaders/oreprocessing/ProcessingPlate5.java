/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Tools;
/*  5:   */ import gregtech.api.enums.GT_Values;
/*  6:   */ import gregtech.api.enums.ItemList;
/*  7:   */ import gregtech.api.enums.Materials;
/*  8:   */ import gregtech.api.enums.OrePrefixes;
/*  9:   */ import gregtech.api.enums.SubTag;
/* 10:   */ import gregtech.api.enums.ToolDictNames;
/* 11:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/* 12:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 13:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 14:   */ import gregtech.api.util.GT_Config;
/* 15:   */ import gregtech.api.util.GT_ModHandler;
/* 16:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/* 17:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 18:   */ import gregtech.api.util.GT_Utility;
/* 19:   */ import net.minecraft.item.ItemStack;
/* 20:   */ 
/* 21:   */ public class ProcessingPlate5
/* 22:   */   implements IOreRecipeRegistrator
/* 23:   */ {
/* 24:   */   public ProcessingPlate5()
/* 25:   */   {
/* 26:17 */     OrePrefixes.plateQuintuple.add(this);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 30:   */   {
/* 31:22 */     GT_ModHandler.removeRecipeByOutput(aStack);
/* 32:23 */     GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[75], aMaterial.mRGBa, false), null);
/* 33:24 */     if ((!aMaterial.contains(SubTag.NO_SMASHING)) && (GregTech_API.sRecipeFile.get(ConfigCategories.Tools.hammerquintupleplate, OrePrefixes.plate.get(aMaterial).toString(), true)))
/* 34:   */     {
/* 35:25 */       GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "I", "B", "h", Character.valueOf('I'), OrePrefixes.plateQuadruple.get(aMaterial), Character.valueOf('B'), OrePrefixes.plate.get(aMaterial) });
/* 36:26 */       GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new Object[] { ToolDictNames.craftingToolForgeHammer, OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial), OrePrefixes.plate.get(aMaterial) });
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:28 */       GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 5L), ItemList.Circuit_Integrated.getWithDamage(0L, 5L, new Object[0]), Materials.Glue.getFluid(40L), GT_Utility.copyAmount(1L, new Object[] { aStack }), 160, 8);
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPlate5
 * JD-Core Version:    0.7.0.1
 */