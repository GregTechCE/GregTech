/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ 
/*  5:   */ public class GT_IteratorRandom
/*  6:   */   extends Random
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9: 8 */   public int mIterationStep = 2147483647;
/* 10:   */   
/* 11:   */   public int nextInt(int aParameter)
/* 12:   */   {
/* 13:11 */     if ((this.mIterationStep == 0) || (this.mIterationStep > aParameter)) {
/* 14:12 */       this.mIterationStep = aParameter;
/* 15:   */     }
/* 16:14 */     return --this.mIterationStep;
/* 17:   */   }
/* 18:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_IteratorRandom
 * JD-Core Version:    0.7.0.1
 */