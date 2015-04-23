/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_Pulser
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_Pulser(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 1;
/* 17:15 */     aCircuitData[1] = 16;
/* 18:16 */     aCircuitData[4] = 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 22:   */   {
/* 23:21 */     if (aCircuitData[0] < 1) {
/* 24:21 */       aCircuitData[0] = 1;
/* 25:   */     }
/* 26:22 */     if (aCircuitData[1] < 0) {
/* 27:22 */       aCircuitData[1] = 0;
/* 28:   */     }
/* 29:23 */     if (aCircuitData[1] > 16) {
/* 30:23 */       aCircuitData[1] = 16;
/* 31:   */     }
/* 32:24 */     if (aCircuitData[4] < 0) {
/* 33:24 */       aCircuitData[4] = 0;
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 38:   */   {
/* 39:29 */     byte tRedstone = aCircuitData[1] == 0 ? getWeakestNonZeroRedstone(aRedstoneCircuitBlock) : getStrongestRedstone(aRedstoneCircuitBlock);
/* 40:30 */     if (aCircuitData[4] == 0) {
/* 41:30 */       aCircuitData[5] = tRedstone;
/* 42:   */     }
/* 43:31 */     if ((tRedstone > 0) || (aCircuitData[4] > 0))
/* 44:   */     {
/* 45:31 */       int tmp40_39 = 4; int[] tmp40_38 = aCircuitData; int tmp42_41 = tmp40_38[tmp40_39];tmp40_38[tmp40_39] = (tmp42_41 + 1);
/* 46:31 */       if ((tmp42_41 >= aCircuitData[0]) && (tRedstone <= 0)) {
/* 47:31 */         aCircuitData[4] = 0;
/* 48:   */       }
/* 49:   */     }
/* 50:32 */     aRedstoneCircuitBlock.setRedstone((byte)((aCircuitData[4] > 0) && (aCircuitData[4] <= aCircuitData[0]) ? (byte)aCircuitData[1] : (aCircuitData[1] <= 0) || (aCircuitData[1] > 15) ? (byte)aCircuitData[5] : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getName()
/* 54:   */   {
/* 55:37 */     return "Pulser";
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String getDescription()
/* 59:   */   {
/* 60:42 */     return "Limits&Enlengths";
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 64:   */   {
/* 65:47 */     switch (aCircuitDataIndex)
/* 66:   */     {
/* 67:   */     case 0: 
/* 68:48 */       return "Length";
/* 69:   */     case 1: 
/* 70:49 */       return "RS Out";
/* 71:   */     }
/* 72:51 */     return "";
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 76:   */   {
/* 77:56 */     return false;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 81:   */   {
/* 82:61 */     if (aCircuitDataIndex == 1)
/* 83:   */     {
/* 84:62 */       if (aCircuitData[aCircuitDataIndex] == 16) {
/* 85:62 */         return "HIGHEST";
/* 86:   */       }
/* 87:63 */       if (aCircuitData[aCircuitDataIndex] == 0) {
/* 88:63 */         return "LOWEST";
/* 89:   */       }
/* 90:   */     }
/* 91:65 */     return aCircuitDataIndex > 1 ? "" : null;
/* 92:   */   }
/* 93:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_Pulser
 * JD-Core Version:    0.7.0.1
 */