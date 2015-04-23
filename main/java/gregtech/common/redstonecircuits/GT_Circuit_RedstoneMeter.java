/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_RedstoneMeter
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_RedstoneMeter(int aIndex)
/* 10:   */   {
/* 11: 8 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:13 */     aCircuitData[0] = 1;
/* 17:14 */     aCircuitData[1] = 15;
/* 18:15 */     aCircuitData[2] = 0;
/* 19:16 */     aCircuitData[3] = 15;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 23:   */   {
/* 24:21 */     if (aCircuitData[0] < 0) {
/* 25:21 */       aCircuitData[0] = 0;
/* 26:   */     }
/* 27:22 */     if (aCircuitData[0] > 15) {
/* 28:22 */       aCircuitData[0] = 15;
/* 29:   */     }
/* 30:23 */     if (aCircuitData[1] < 0) {
/* 31:23 */       aCircuitData[1] = 0;
/* 32:   */     }
/* 33:24 */     if (aCircuitData[1] > 15) {
/* 34:24 */       aCircuitData[1] = 15;
/* 35:   */     }
/* 36:25 */     if (aCircuitData[1] < aCircuitData[0]) {
/* 37:25 */       aCircuitData[1] = aCircuitData[0];
/* 38:   */     }
/* 39:26 */     if (aCircuitData[2] < 0) {
/* 40:26 */       aCircuitData[2] = 0;
/* 41:   */     }
/* 42:27 */     if (aCircuitData[2] > 1) {
/* 43:27 */       aCircuitData[2] = 1;
/* 44:   */     }
/* 45:28 */     if (aCircuitData[3] < 0) {
/* 46:28 */       aCircuitData[3] = 0;
/* 47:   */     }
/* 48:29 */     if (aCircuitData[3] > 15) {
/* 49:29 */       aCircuitData[3] = 15;
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 54:   */   {
/* 55:34 */     byte tRedstone = getStrongestRedstone(aRedstoneCircuitBlock);
/* 56:35 */     aRedstoneCircuitBlock.setRedstone((byte)(((tRedstone >= aCircuitData[0]) && (tRedstone <= aCircuitData[1]) ? 1 : 0) != (aCircuitData[2] != 0 ? 1 : 0) ? (byte)aCircuitData[3] : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String getName()
/* 60:   */   {
/* 61:40 */     return "Redstone Meter";
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String getDescription()
/* 65:   */   {
/* 66:45 */     return "Checks Boundaries";
/* 67:   */   }
/* 68:   */   
/* 69:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 70:   */   {
/* 71:50 */     switch (aCircuitDataIndex)
/* 72:   */     {
/* 73:   */     case 0: 
/* 74:51 */       return "Lower";
/* 75:   */     case 1: 
/* 76:52 */       return "Upper";
/* 77:   */     case 2: 
/* 78:53 */       return "Invert:";
/* 79:   */     case 3: 
/* 80:54 */       return "RS Out:";
/* 81:   */     }
/* 82:56 */     return "";
/* 83:   */   }
/* 84:   */   
/* 85:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 86:   */   {
/* 87:61 */     return false;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 91:   */   {
/* 92:66 */     if (aCircuitDataIndex == 2) {
/* 93:66 */       return aCircuitData[2] == 0 ? "OFF" : "ON";
/* 94:   */     }
/* 95:67 */     return null;
/* 96:   */   }
/* 97:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_RedstoneMeter
 * JD-Core Version:    0.7.0.1
 */