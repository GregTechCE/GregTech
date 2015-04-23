/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_Repeater
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_Repeater(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 1;
/* 17:15 */     aCircuitData[4] = 0;
/* 18:16 */     aCircuitData[5] = -1;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 22:   */   {
/* 23:21 */     if (aCircuitData[0] < 1) {
/* 24:21 */       aCircuitData[0] = 1;
/* 25:   */     }
/* 26:22 */     if (aCircuitData[4] < 0) {
/* 27:22 */       aCircuitData[4] = 0;
/* 28:   */     }
/* 29:23 */     if (aCircuitData[5] < -1) {
/* 30:23 */       aCircuitData[5] = -1;
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 35:   */   {
/* 36:28 */     if (getAnyRedstone(aRedstoneCircuitBlock))
/* 37:   */     {
/* 38:29 */       aCircuitData[4] += 1;
/* 39:30 */       if (aCircuitData[5] < 0) {
/* 40:31 */         aCircuitData[5] = 0;
/* 41:   */       }
/* 42:   */     }
/* 43:34 */     if ((aCircuitData[5] >= 0) && (aCircuitData[5] < aCircuitData[0])) {
/* 44:35 */       aCircuitData[5] += 1;
/* 45:   */     }
/* 46:37 */     if (aCircuitData[4] > 0)
/* 47:   */     {
/* 48:38 */       if (aCircuitData[5] >= aCircuitData[0])
/* 49:   */       {
/* 50:39 */         aCircuitData[4] -= 1;
/* 51:40 */         aRedstoneCircuitBlock.setRedstone((byte)15, aRedstoneCircuitBlock.getOutputFacing());
/* 52:   */       }
/* 53:   */       else
/* 54:   */       {
/* 55:42 */         aRedstoneCircuitBlock.setRedstone((byte)0, aRedstoneCircuitBlock.getOutputFacing());
/* 56:   */       }
/* 57:   */     }
/* 58:   */     else
/* 59:   */     {
/* 60:45 */       aRedstoneCircuitBlock.setRedstone((byte)0, aRedstoneCircuitBlock.getOutputFacing());
/* 61:46 */       aCircuitData[5] = -1;
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public String getName()
/* 66:   */   {
/* 67:52 */     return "Repeater";
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String getDescription()
/* 71:   */   {
/* 72:57 */     return "Delays RS-Signal";
/* 73:   */   }
/* 74:   */   
/* 75:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 76:   */   {
/* 77:62 */     switch (aCircuitDataIndex)
/* 78:   */     {
/* 79:   */     case 0: 
/* 80:63 */       return "Delay";
/* 81:   */     }
/* 82:65 */     return "";
/* 83:   */   }
/* 84:   */   
/* 85:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 86:   */   {
/* 87:70 */     return false;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 91:   */   {
/* 92:75 */     if (aCircuitDataIndex > 0) {
/* 93:75 */       return "";
/* 94:   */     }
/* 95:76 */     return null;
/* 96:   */   }
/* 97:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_Repeater
 * JD-Core Version:    0.7.0.1
 */