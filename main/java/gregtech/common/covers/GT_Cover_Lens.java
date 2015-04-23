/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Cover_Lens
/*  7:   */   extends GT_CoverBehavior
/*  8:   */ {
/*  9:   */   private final byte mColor;
/* 10:   */   
/* 11:   */   public GT_Cover_Lens(byte aColor)
/* 12:   */   {
/* 13:10 */     this.mColor = aColor;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public byte getLensColor(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 17:   */   {
/* 18:15 */     return this.mColor;
/* 19:   */   }
/* 20:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Lens
 * JD-Core Version:    0.7.0.1
 */