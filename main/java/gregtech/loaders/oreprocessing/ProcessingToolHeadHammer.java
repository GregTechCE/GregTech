/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Dyes;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/* 10:   */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/* 11:   */ import net.minecraft.init.Blocks;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class ProcessingToolHeadHammer
/* 15:   */   implements IOreRecipeRegistrator
/* 16:   */ {
/* 17:   */   public ProcessingToolHeadHammer()
/* 18:   */   {
/* 19:18 */     OrePrefixes.toolHeadHammer.add(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 23:   */   {
/* 24:23 */     if ((aMaterial != Materials.Stone) && (aMaterial != Materials.Flint))
/* 25:   */     {
/* 26:24 */       GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { aOreDictName, OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 27:25 */       GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "XX ", "XXS", "XX ", Character.valueOf('X'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(Materials.Wood) : OrePrefixes.ingot.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 28:26 */       GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "XX ", "XXS", "XX ", Character.valueOf('X'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(Materials.Wood) : OrePrefixes.gem.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 29:27 */       if (aMaterial != Materials.Rubber) {
/* 30:27 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(44, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "xRR", " SR", "S f", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 31:   */       }
/* 32:28 */       if ((!aMaterial.contains(SubTag.WOOD)) && (!aMaterial.contains(SubTag.BOUNCY)) && (!aMaterial.contains(SubTag.NO_SMASHING)))
/* 33:   */       {
/* 34:29 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(16, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "IhI", "III", " I ", Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial) });
/* 35:30 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(20, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "hDS", "DSD", "SDf", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('D'), Dyes.dyeBlue });
/* 36:31 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(22, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { " fS", " Sh", "W  ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('W'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 37:32 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(26, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PfP", "hPd", "STS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('T'), OrePrefixes.screw.get(aMaterial) });
/* 38:33 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(28, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "SWS", "SSS", "xSh", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 32767) });
/* 39:34 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(30, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PfP", "PdP", "STS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('T'), OrePrefixes.screw.get(aMaterial) });
/* 40:35 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(34, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "fPh", " S ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial) });
/* 41:36 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(36, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PPf", "PP ", "Sh ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial) });
/* 42:   */       }
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingToolHeadHammer
 * JD-Core Version:    0.7.0.1
 */