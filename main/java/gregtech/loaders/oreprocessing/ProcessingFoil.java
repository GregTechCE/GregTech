/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public class ProcessingFoil
/* 11:   */   implements IOreRecipeRegistrator
/* 12:   */ {
/* 13:   */   public ProcessingFoil()
/* 14:   */   {
/* 15:14 */     OrePrefixes.foil.add(this);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 19:   */   {
/* 20:19 */     GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[70], aMaterial.mRGBa, false), null);
/* 21:   */   }
/* 22:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingFoil
 * JD-Core Version:    0.7.0.1
 */