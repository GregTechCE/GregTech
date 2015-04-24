/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.Dyes;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.enums.SubTag;
/*    */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*    */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingToolHeadHammer implements IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingToolHeadHammer()
/*    */   {
/* 18 */     OrePrefixes.toolHeadHammer.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     if ((aMaterial != Materials.Stone) && (aMaterial != Materials.Flint)) {
/* 24 */       GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { aOreDictName, OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 25 */       GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "XX ", "XXS", "XX ", Character.valueOf('X'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(Materials.Wood) : OrePrefixes.ingot.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 26 */       GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats((aMaterial.contains(SubTag.BOUNCY)) || (aMaterial.contains(SubTag.WOOD)) ? 14 : 12, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "XX ", "XXS", "XX ", Character.valueOf('X'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(Materials.Wood) : OrePrefixes.gem.get(aMaterial), Character.valueOf('S'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 27 */       if (aMaterial != Materials.Rubber) GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(44, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "xRR", " SR", "S f", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 28 */       if ((!aMaterial.contains(SubTag.WOOD)) && (!aMaterial.contains(SubTag.BOUNCY)) && (!aMaterial.contains(SubTag.NO_SMASHING))) {
/* 29 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(16, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "IhI", "III", " I ", Character.valueOf('I'), OrePrefixes.ingot.get(aMaterial) });
/* 30 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(20, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "hDS", "DSD", "SDf", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('D'), Dyes.dyeBlue });
/* 31 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(22, 1, aMaterial, aMaterial.mHandleMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { " fS", " Sh", "W  ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('W'), OrePrefixes.stick.get(aMaterial.mHandleMaterial) });
/* 32 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(26, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PfP", "hPd", "STS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('T'), OrePrefixes.screw.get(aMaterial) });
/* 33 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(28, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "SWS", "SSS", "xSh", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 32767) });
/* 34 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(30, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PfP", "PdP", "STS", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial), Character.valueOf('T'), OrePrefixes.screw.get(aMaterial) });
/* 35 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(34, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "fPh", " S ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial) });
/* 36 */         GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(36, 1, aMaterial, aMaterial, null), GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PPf", "PP ", "Sh ", Character.valueOf('S'), OrePrefixes.stick.get(aMaterial), Character.valueOf('P'), OrePrefixes.plate.get(aMaterial) });
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingToolHeadHammer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */