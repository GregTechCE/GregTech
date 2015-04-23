/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Cover_Blastproof
/*  7:   */   extends GT_CoverBehavior
/*  8:   */ {
/*  9:   */   private final float mLevel;
/* 10:   */   
/* 11:   */   public GT_Cover_Blastproof(float aLevel)
/* 12:   */   {
/* 13:11 */     this.mLevel = aLevel;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 17:   */   {
/* 18:16 */     return this.mLevel;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isSimpleCover()
/* 22:   */   {
/* 23:21 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Blastproof
 * JD-Core Version:    0.7.0.1
 */