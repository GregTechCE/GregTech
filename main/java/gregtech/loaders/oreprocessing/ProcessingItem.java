/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.objects.ItemData;
/*    */ import gregtech.api.objects.MaterialStack;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingItem implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingItem()
/*    */   {
/* 18 */     OrePrefixes.item.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 23 */     if (GT_OreDictUnificator.getItemData(aStack) == null)
/*    */     {
/*    */ 
/* 26 */       if (!aOreDictName.equals("itemCertusQuartz"))
/*    */       {
/*    */ 
/* 29 */         if (!aOreDictName.equals("itemNetherQuartz"))
/*    */         {
/*    */ 
/* 32 */           if (aOreDictName.equals("itemSilicon")) {
/* 33 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Silicon, 3628800L, new MaterialStack[0]));
/* 34 */             GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 0L, 19), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 20), 200, 16);
/*    */           }
/* 36 */           else if (aOreDictName.equals("itemWheat")) {
/* 37 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Wheat, 3628800L, new MaterialStack[0]));
/*    */           }
/* 39 */           else if (aOreDictName.equals("itemManganese")) {
/* 40 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Manganese, 3628800L, new MaterialStack[0]));
/*    */           }
/* 42 */           else if (aOreDictName.equals("itemSalt")) {
/* 43 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Salt, 3628800L, new MaterialStack[0]));
/*    */           }
/* 45 */           else if (aOreDictName.equals("itemMagnesium")) {
/* 46 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Magnesium, 3628800L, new MaterialStack[0]));
/*    */           }
/* 48 */           else if ((aOreDictName.equals("itemPhosphorite")) || (aOreDictName.equals("itemPhosphorus"))) {
/* 49 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Phosphorus, 3628800L, new MaterialStack[0]));
/*    */           }
/* 51 */           else if (aOreDictName.equals("itemSulfur")) {
/* 52 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Sulfur, 3628800L, new MaterialStack[0]));
/*    */           }
/* 54 */           else if ((aOreDictName.equals("itemAluminum")) || (aOreDictName.equals("itemAluminium"))) {
/* 55 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Aluminium, 3628800L, new MaterialStack[0]));
/*    */           }
/* 57 */           else if (aOreDictName.equals("itemSaltpeter")) {
/* 58 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Saltpeter, 3628800L, new MaterialStack[0]));
/*    */           }
/* 60 */           else if (aOreDictName.equals("itemUranium")) {
/* 61 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Uranium, 3628800L, new MaterialStack[0]));
/*    */           }
/*    */           else {
/* 64 */             System.out.println("Item Name: " + aOreDictName + " !!!Unknown Item detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */