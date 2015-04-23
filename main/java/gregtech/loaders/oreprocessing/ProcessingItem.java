/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.objects.ItemData;
/*  9:   */ import gregtech.api.objects.MaterialStack;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import java.io.PrintStream;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ 
/* 16:   */ public class ProcessingItem
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingItem()
/* 20:   */   {
/* 21:18 */     OrePrefixes.item.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:23 */     if (GT_OreDictUnificator.getItemData(aStack) == null) {
/* 27:26 */       if (!aOreDictName.equals("itemCertusQuartz")) {
/* 28:29 */         if (!aOreDictName.equals("itemNetherQuartz")) {
/* 29:32 */           if (aOreDictName.equals("itemSilicon"))
/* 30:   */           {
/* 31:33 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Silicon, 3628800L, new MaterialStack[0]));
/* 32:34 */             GT_Values.RA.addFormingPressRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 0L, 19), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 20), 200, 16);
/* 33:   */           }
/* 34:36 */           else if (aOreDictName.equals("itemWheat"))
/* 35:   */           {
/* 36:37 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Wheat, 3628800L, new MaterialStack[0]));
/* 37:   */           }
/* 38:39 */           else if (aOreDictName.equals("itemManganese"))
/* 39:   */           {
/* 40:40 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Manganese, 3628800L, new MaterialStack[0]));
/* 41:   */           }
/* 42:42 */           else if (aOreDictName.equals("itemSalt"))
/* 43:   */           {
/* 44:43 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Salt, 3628800L, new MaterialStack[0]));
/* 45:   */           }
/* 46:45 */           else if (aOreDictName.equals("itemMagnesium"))
/* 47:   */           {
/* 48:46 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Magnesium, 3628800L, new MaterialStack[0]));
/* 49:   */           }
/* 50:48 */           else if ((aOreDictName.equals("itemPhosphorite")) || (aOreDictName.equals("itemPhosphorus")))
/* 51:   */           {
/* 52:49 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Phosphorus, 3628800L, new MaterialStack[0]));
/* 53:   */           }
/* 54:51 */           else if (aOreDictName.equals("itemSulfur"))
/* 55:   */           {
/* 56:52 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Sulfur, 3628800L, new MaterialStack[0]));
/* 57:   */           }
/* 58:54 */           else if ((aOreDictName.equals("itemAluminum")) || (aOreDictName.equals("itemAluminium")))
/* 59:   */           {
/* 60:55 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Aluminium, 3628800L, new MaterialStack[0]));
/* 61:   */           }
/* 62:57 */           else if (aOreDictName.equals("itemSaltpeter"))
/* 63:   */           {
/* 64:58 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Saltpeter, 3628800L, new MaterialStack[0]));
/* 65:   */           }
/* 66:60 */           else if (aOreDictName.equals("itemUranium"))
/* 67:   */           {
/* 68:61 */             GT_OreDictUnificator.addItemData(aStack, new ItemData(Materials.Uranium, 3628800L, new MaterialStack[0]));
/* 69:   */           }
/* 70:   */           else
/* 71:   */           {
/* 72:64 */             System.out.println("Item Name: " + aOreDictName + " !!!Unknown Item detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
/* 73:   */           }
/* 74:   */         }
/* 75:   */       }
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingItem
 * JD-Core Version:    0.7.0.1
 */