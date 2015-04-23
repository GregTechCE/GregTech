/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  9:   */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingToolHeadBuzzSaw
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingToolHeadBuzzSaw()
/* 16:   */   {
/* 17:15 */     OrePrefixes.toolHeadBuzzSaw.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:20 */     GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(140, 1, aMaterial, Materials.StainlessSteel, new long[] { 100000L, 32L, 1L, -1L }), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PBM", "dXG", "SGP", Character.valueOf('X'), aOreDictName, Character.valueOf('M'), ItemList.Electric_Motor_LV.get(1L, new Object[0]), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel), Character.valueOf('P'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), Character.valueOf('B'), ItemList.Battery_RE_LV_Lithium.get(1L, new Object[0]) });
/* 23:21 */     GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(140, 1, aMaterial, Materials.StainlessSteel, new long[] { 75000L, 32L, 1L, -1L }), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PBM", "dXG", "SGP", Character.valueOf('X'), aOreDictName, Character.valueOf('M'), ItemList.Electric_Motor_LV.get(1L, new Object[0]), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel), Character.valueOf('P'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), Character.valueOf('B'), ItemList.Battery_RE_LV_Cadmium.get(1L, new Object[0]) });
/* 24:22 */     GT_ModHandler.addCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(140, 1, aMaterial, Materials.StainlessSteel, new long[] { 50000L, 32L, 1L, -1L }), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PBM", "dXG", "SGP", Character.valueOf('X'), aOreDictName, Character.valueOf('M'), ItemList.Electric_Motor_LV.get(1L, new Object[0]), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel), Character.valueOf('P'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), Character.valueOf('B'), ItemList.Battery_RE_LV_Sodium.get(1L, new Object[0]) });
/* 25:   */   }
/* 26:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingToolHeadBuzzSaw
 * JD-Core Version:    0.7.0.1
 */