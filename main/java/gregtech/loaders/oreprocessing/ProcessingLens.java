/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Dyes;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*  7:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  8:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  9:   */ import gregtech.api.interfaces.ITexture;
/* 10:   */ import gregtech.api.objects.GT_MultiTexture;
/* 11:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 12:   */ import gregtech.common.covers.GT_Cover_Lens;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class ProcessingLens
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingLens()
/* 19:   */   {
/* 20:16 */     OrePrefixes.lens.add(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 24:   */   {
/* 25:21 */     GregTech_API.registerCover(aStack, new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_LENS, aMaterial.mRGBa, false) }), new GT_Cover_Lens(aMaterial.mColor.mIndex));
/* 26:   */   }
/* 27:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingLens
 * JD-Core Version:    0.7.0.1
 */