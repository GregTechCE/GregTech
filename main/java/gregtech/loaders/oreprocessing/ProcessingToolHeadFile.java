/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.enums.SubTag;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  9:   */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingToolHeadFile
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingToolHeadFile()
/* 16:   */   {
/* 17:15 */     OrePrefixes.toolHeadFile.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:20 */     GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(18, 1, aMaterial, aMaterial.mHandleMaterial, null), new Object[] { aOreDictName, OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 23:21 */     if ((!aMaterial.contains(SubTag.NO_SMASHING)) && (!aMaterial.contains(SubTag.BOUNCY))) {
/* 24:22 */       GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(18, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "P", "P", "S", Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingToolHeadFile
 * JD-Core Version:    0.7.0.1
 */