/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_Timer
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_Timer(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 2;
/* 17:15 */     aCircuitData[1] = 1;
/* 18:16 */     aCircuitData[2] = 2;
/* 19:17 */     aCircuitData[4] = 0;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 23:   */   {
/* 24:22 */     if (aCircuitData[0] < 2) {
/* 25:22 */       aCircuitData[0] = 2;
/* 26:   */     }
/* 27:23 */     if (aCircuitData[1] < 1) {
/* 28:23 */       aCircuitData[1] = 1;
/* 29:   */     }
/* 30:24 */     if (aCircuitData[2] < 2) {
/* 31:24 */       aCircuitData[2] = 2;
/* 32:   */     }
/* 33:25 */     if (aCircuitData[3] < 0) {
/* 34:25 */       aCircuitData[3] = 0;
/* 35:   */     }
/* 36:26 */     if (aCircuitData[3] > 1) {
/* 37:26 */       aCircuitData[3] = 1;
/* 38:   */     }
/* 39:27 */     if (aCircuitData[4] < 0) {
/* 40:27 */       aCircuitData[4] = 0;
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 45:   */   {
/* 46:32 */     if (aCircuitData[3] == 1)
/* 47:   */     {
/* 48:33 */       if (getAnyRedstone(aRedstoneCircuitBlock)) {
/* 49:34 */         aCircuitData[4] += 1;
/* 50:   */       } else {
/* 51:36 */         aCircuitData[4] = 0;
/* 52:   */       }
/* 53:   */     }
/* 54:39 */     else if (getAnyRedstone(aRedstoneCircuitBlock)) {
/* 55:40 */       aCircuitData[4] = 0;
/* 56:   */     } else {
/* 57:42 */       aCircuitData[4] += 1;
/* 58:   */     }
/* 59:46 */     if (aCircuitData[4] >= aCircuitData[0])
/* 60:   */     {
/* 61:47 */       if (aCircuitData[1] > 1)
/* 62:   */       {
/* 63:48 */         if (aCircuitData[4] >= aCircuitData[0] + (aCircuitData[1] - 1) * aCircuitData[2])
/* 64:   */         {
/* 65:49 */           aRedstoneCircuitBlock.setRedstone((byte)15, aRedstoneCircuitBlock.getOutputFacing());
/* 66:50 */           aCircuitData[4] = 0;
/* 67:   */         }
/* 68:   */         else
/* 69:   */         {
/* 70:52 */           aRedstoneCircuitBlock.setRedstone((byte)((aCircuitData[4] - aCircuitData[0]) % aCircuitData[2] == 0 ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 71:   */         }
/* 72:   */       }
/* 73:   */       else
/* 74:   */       {
/* 75:55 */         aRedstoneCircuitBlock.setRedstone((byte)15, aRedstoneCircuitBlock.getOutputFacing());
/* 76:56 */         aCircuitData[4] = 0;
/* 77:   */       }
/* 78:   */     }
/* 79:   */     else {
/* 80:59 */       aRedstoneCircuitBlock.setRedstone((byte)0, aRedstoneCircuitBlock.getOutputFacing());
/* 81:   */     }
/* 82:   */   }
/* 83:   */   
/* 84:   */   public String getName()
/* 85:   */   {
/* 86:65 */     return "Timer";
/* 87:   */   }
/* 88:   */   
/* 89:   */   public String getDescription()
/* 90:   */   {
/* 91:70 */     return "Pulses Redstone";
/* 92:   */   }
/* 93:   */   
/* 94:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 95:   */   {
/* 96:75 */     switch (aCircuitDataIndex)
/* 97:   */     {
/* 98:   */     case 0: 
/* 99:76 */       return "Delay";
/* :0:   */     case 1: 
/* :1:77 */       return "Pulses";
/* :2:   */     case 2: 
/* :3:78 */       return "Length";
/* :4:   */     case 3: 
/* :5:79 */       return aCircuitData[aCircuitDataIndex] == 1 ? "RS => ON" : "RS => OFF";
/* :6:   */     case 4: 
/* :7:80 */       return "Time";
/* :8:   */     }
/* :9:82 */     return "";
/* ;0:   */   }
/* ;1:   */   
/* ;2:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* ;3:   */   {
/* ;4:87 */     return false;
/* ;5:   */   }
/* ;6:   */   
/* ;7:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* ;8:   */   {
/* ;9:92 */     if (aCircuitDataIndex == 3) {
/* <0:92 */       return "";
/* <1:   */     }
/* <2:93 */     return null;
/* <3:   */   }
/* <4:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_Timer
 * JD-Core Version:    0.7.0.1
 */