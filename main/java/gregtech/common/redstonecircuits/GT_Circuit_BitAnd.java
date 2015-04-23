/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_BitAnd
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_BitAnd(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 0;
/* 17:15 */     aCircuitData[1] = 0;
/* 18:16 */     aCircuitData[2] = 0;
/* 19:17 */     aCircuitData[3] = 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 23:   */   {
/* 24:22 */     if (aCircuitData[0] < 0) {
/* 25:22 */       aCircuitData[0] = 0;
/* 26:   */     }
/* 27:23 */     if (aCircuitData[1] < 0) {
/* 28:23 */       aCircuitData[1] = 0;
/* 29:   */     }
/* 30:24 */     if (aCircuitData[2] < 0) {
/* 31:24 */       aCircuitData[2] = 0;
/* 32:   */     }
/* 33:25 */     if (aCircuitData[3] < 0) {
/* 34:25 */       aCircuitData[3] = 0;
/* 35:   */     }
/* 36:26 */     if (aCircuitData[0] > 1) {
/* 37:26 */       aCircuitData[0] = 1;
/* 38:   */     }
/* 39:27 */     if (aCircuitData[1] > 1) {
/* 40:27 */       aCircuitData[1] = 1;
/* 41:   */     }
/* 42:28 */     if (aCircuitData[2] > 1) {
/* 43:28 */       aCircuitData[2] = 1;
/* 44:   */     }
/* 45:29 */     if (aCircuitData[3] > 1) {
/* 46:29 */       aCircuitData[3] = 1;
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 51:   */   {
/* 52:34 */     aRedstoneCircuitBlock.setRedstone((byte)((getStrongestRedstone(aRedstoneCircuitBlock) & (aCircuitData[0] | aCircuitData[1] << 1 | aCircuitData[2] << 2 | aCircuitData[3] << 3)) != 0 ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getName()
/* 56:   */   {
/* 57:39 */     return "Hardcode Bit-AND";
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getDescription()
/* 61:   */   {
/* 62:44 */     return "( signal & this ) != 0";
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 66:   */   {
/* 67:49 */     return "Bit " + aCircuitDataIndex + ":";
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 71:   */   {
/* 72:54 */     return false;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 76:   */   {
/* 77:59 */     return aCircuitData[aCircuitDataIndex] == 0 ? "OFF" : "ON";
/* 78:   */   }
/* 79:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_BitAnd
 * JD-Core Version:    0.7.0.1
 */