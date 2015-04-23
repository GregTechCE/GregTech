/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class ProcessingPlate9
/* 12:   */   implements IOreRecipeRegistrator
/* 13:   */ {
/* 14:   */   public ProcessingPlate9()
/* 15:   */   {
/* 16:15 */     OrePrefixes.plateDense.add(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 20:   */   {
/* 21:20 */     GT_ModHandler.removeRecipeByOutput(aStack);
/* 22:21 */     GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[76], aMaterial.mRGBa, false), null);
/* 23:   */   }
/* 24:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPlate9
 * JD-Core Version:    0.7.0.1
 */