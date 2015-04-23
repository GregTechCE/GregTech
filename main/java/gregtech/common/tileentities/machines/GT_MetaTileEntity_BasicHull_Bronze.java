/*  1:   */ package gregtech.common.tileentities.machines;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.interfaces.ITexture;
/*  6:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  7:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  8:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull_NonElectric;
/*  9:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 10:   */ 
/* 11:   */ public class GT_MetaTileEntity_BasicHull_Bronze
/* 12:   */   extends GT_MetaTileEntity_BasicHull_NonElectric
/* 13:   */ {
/* 14:   */   public GT_MetaTileEntity_BasicHull_Bronze(int aID, String aName, String aNameRegional, int aTier, String aDescription)
/* 15:   */   {
/* 16:13 */     super(aID, aName, aNameRegional, aTier, aDescription);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public GT_MetaTileEntity_BasicHull_Bronze(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/* 20:   */   {
/* 21:17 */     super(aName, aTier, aDescription, aTextures);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 25:   */   {
/* 26:22 */     return new GT_MetaTileEntity_BasicHull_Bronze(this.mName, this.mTier, this.mDescription, this.mTextures);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ITexture[][][] getTextureSet(ITexture[] aTextures)
/* 30:   */   {
/* 31:27 */     ITexture[][][] rTextures = new ITexture[3][17][];
/* 32:28 */     for (byte i = -1; i < 16; i = (byte)(i + 1))
/* 33:   */     {
/* 34:29 */       rTextures[0][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
/* 35:30 */       rTextures[1][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
/* 36:31 */       rTextures[2][(i + 1)] = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZE_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)) };
/* 37:   */     }
/* 38:33 */     return rTextures;
/* 39:   */   }
/* 40:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.GT_MetaTileEntity_BasicHull_Bronze
 * JD-Core Version:    0.7.0.1
 */