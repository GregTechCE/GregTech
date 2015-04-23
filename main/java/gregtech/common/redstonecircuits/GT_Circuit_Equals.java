/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_Equals
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_Equals(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 0;
/* 17:15 */     aCircuitData[1] = 0;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 21:   */   {
/* 22:20 */     if (aCircuitData[0] < 0) {
/* 23:20 */       aCircuitData[0] = 0;
/* 24:   */     }
/* 25:21 */     if (aCircuitData[0] > 15) {
/* 26:21 */       aCircuitData[0] = 15;
/* 27:   */     }
/* 28:22 */     if (aCircuitData[1] < 0) {
/* 29:22 */       aCircuitData[3] = 0;
/* 30:   */     }
/* 31:23 */     if (aCircuitData[1] > 1) {
/* 32:23 */       aCircuitData[3] = 1;
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 37:   */   {
///* 38:28 */     if (getStrongestRedstone(aRedstoneCircuitBlock) == aCircuitData[0]) {
///* 39:28 */       if (getStrongestRedstone(aRedstoneCircuitBlock) != (aCircuitData[1] == 0 ? aRedstoneCircuitBlock : aCircuitData[0]))
///* 40:   */       {
///* 41:28 */         tmpTernaryOp = 15;
///* 42:   */         break label36;
///* 43:   */       }
///* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getName()
/* 48:   */   {
/* 49:33 */     return "Equals";
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String getDescription()
/* 53:   */   {
/* 54:38 */     return "signal == this";
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 58:   */   {
/* 59:43 */     switch (aCircuitDataIndex)
/* 60:   */     {
/* 61:   */     case 0: 
/* 62:44 */       return "Signal";
/* 63:   */     case 1: 
/* 64:45 */       return aCircuitData[1] == 0 ? "Equal" : "Unequal";
/* 65:   */     }
/* 66:47 */     return "";
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 70:   */   {
/* 71:52 */     return false;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 75:   */   {
/* 76:57 */     if (aCircuitDataIndex > 0) {
/* 77:57 */       return "";
/* 78:   */     }
/* 79:58 */     return null;
/* 80:   */   }
/* 81:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_Equals
 * JD-Core Version:    0.7.0.1
 */