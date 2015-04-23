/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_CombinationLock
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_CombinationLock(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 1;
/* 17:15 */     aCircuitData[1] = 0;
/* 18:16 */     aCircuitData[2] = 0;
/* 19:17 */     aCircuitData[3] = 0;
/* 20:18 */     aCircuitData[4] = 0;
/* 21:19 */     aCircuitData[5] = 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 25:   */   {
/* 26:24 */     if (aCircuitData[0] < 1) {
/* 27:24 */       aCircuitData[0] = 1;
/* 28:   */     }
/* 29:25 */     if (aCircuitData[1] < 0) {
/* 30:25 */       aCircuitData[1] = 0;
/* 31:   */     }
/* 32:26 */     if (aCircuitData[2] < 0) {
/* 33:26 */       aCircuitData[2] = 0;
/* 34:   */     }
/* 35:27 */     if (aCircuitData[3] < 0) {
/* 36:27 */       aCircuitData[3] = 0;
/* 37:   */     }
/* 38:28 */     if (aCircuitData[0] > 15) {
/* 39:28 */       aCircuitData[0] = 15;
/* 40:   */     }
/* 41:29 */     if (aCircuitData[1] > 15) {
/* 42:29 */       aCircuitData[1] = 15;
/* 43:   */     }
/* 44:30 */     if (aCircuitData[2] > 15) {
/* 45:30 */       aCircuitData[2] = 15;
/* 46:   */     }
/* 47:31 */     if (aCircuitData[3] > 15) {
/* 48:31 */       aCircuitData[3] = 15;
/* 49:   */     }
/* 50:32 */     if (aCircuitData[4] < 0) {
/* 51:32 */       aCircuitData[4] = 0;
/* 52:   */     }
/* 53:33 */     if (aCircuitData[4] > 3) {
/* 54:33 */       aCircuitData[4] = 3;
/* 55:   */     }
/* 56:34 */     if (aCircuitData[5] < 0) {
/* 57:34 */       aCircuitData[5] = 0;
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 62:   */   {
/* 63:39 */     while ((aCircuitData[aCircuitData[4]] == 0) && (aCircuitData[4] < 4)) {
/* 64:39 */       aCircuitData[4] += 1;
/* 65:   */     }
/* 66:40 */     if (aCircuitData[4] < 4)
/* 67:   */     {
/* 68:41 */       int tRedstone = getStrongestRedstone(aRedstoneCircuitBlock);
/* 69:42 */       if (tRedstone > 0)
/* 70:   */       {
/* 71:43 */         if (aCircuitData[5] == 0) {
/* 72:44 */           if (tRedstone == aCircuitData[aCircuitData[4]]) {
/* 73:45 */             aCircuitData[4] += 1;
/* 74:   */           } else {
/* 75:47 */             aCircuitData[4] = 0;
/* 76:   */           }
/* 77:   */         }
/* 78:50 */         aCircuitData[5] = 1;
/* 79:   */       }
/* 80:   */       else
/* 81:   */       {
/* 82:52 */         aCircuitData[5] = 0;
/* 83:   */       }
/* 84:54 */       aRedstoneCircuitBlock.setRedstone((byte)0, aRedstoneCircuitBlock.getOutputFacing());
/* 85:   */     }
/* 86:   */     else
/* 87:   */     {
/* 88:56 */       aRedstoneCircuitBlock.setRedstone((byte)15, aRedstoneCircuitBlock.getOutputFacing());
/* 89:57 */       aCircuitData[4] = 0;
/* 90:   */     }
/* 91:   */   }
/* 92:   */   
/* 93:   */   public String getName()
/* 94:   */   {
/* 95:63 */     return "Combination Lock";
/* 96:   */   }
/* 97:   */   
/* 98:   */   public String getDescription()
/* 99:   */   {
/* :0:68 */     return "Checks Combinations";
/* :1:   */   }
/* :2:   */   
/* :3:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* :4:   */   {
/* :5:73 */     return "Power " + aCircuitDataIndex;
/* :6:   */   }
/* :7:   */   
/* :8:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* :9:   */   {
/* ;0:78 */     return false;
/* ;1:   */   }
/* ;2:   */   
/* ;3:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* ;4:   */   {
/* ;5:83 */     return null;
/* ;6:   */   }
/* ;7:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_CombinationLock
 * JD-Core Version:    0.7.0.1
 */