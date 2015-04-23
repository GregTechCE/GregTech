/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_Randomizer
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_Randomizer(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 1;
/* 17:15 */     aCircuitData[4] = 0;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 21:   */   {
/* 22:20 */     if (aCircuitData[0] < 1) {
/* 23:20 */       aCircuitData[0] = 1;
/* 24:   */     }
/* 25:21 */     if (aCircuitData[3] < 0) {
/* 26:21 */       aCircuitData[3] = 0;
/* 27:   */     }
/* 28:22 */     if (aCircuitData[3] > 1) {
/* 29:22 */       aCircuitData[3] = 1;
/* 30:   */     }
/* 31:23 */     if (aCircuitData[4] < 0) {
/* 32:23 */       aCircuitData[4] = 0;
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 37:   */   {
/* 38:28 */     if (aCircuitData[3] == 1)
/* 39:   */     {
/* 40:29 */       if (getAnyRedstone(aRedstoneCircuitBlock)) {
/* 41:30 */         aCircuitData[4] += 1;
/* 42:   */       } else {
/* 43:32 */         aCircuitData[4] = 0;
/* 44:   */       }
/* 45:   */     }
/* 46:35 */     else if (getAnyRedstone(aRedstoneCircuitBlock)) {
/* 47:36 */       aCircuitData[4] = 0;
/* 48:   */     } else {
/* 49:38 */       aCircuitData[4] += 1;
/* 50:   */     }
/* 51:42 */     if (aCircuitData[4] >= aCircuitData[0])
/* 52:   */     {
/* 53:43 */       aCircuitData[4] = 0;
/* 54:44 */       aRedstoneCircuitBlock.setRedstone((byte)aRedstoneCircuitBlock.getRandom(16), aRedstoneCircuitBlock.getOutputFacing());
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String getName()
/* 59:   */   {
/* 60:50 */     return "Randomizer";
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String getDescription()
/* 64:   */   {
/* 65:55 */     return "Randomizes Redstone";
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 69:   */   {
/* 70:60 */     switch (aCircuitDataIndex)
/* 71:   */     {
/* 72:   */     case 0: 
/* 73:61 */       return "Delay";
/* 74:   */     case 3: 
/* 75:62 */       return aCircuitData[aCircuitDataIndex] == 1 ? "RS => ON" : "RS => OFF";
/* 76:   */     case 4: 
/* 77:63 */       return "Status";
/* 78:   */     }
/* 79:65 */     return "";
/* 80:   */   }
/* 81:   */   
/* 82:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 83:   */   {
/* 84:70 */     return false;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* 88:   */   {
/* 89:75 */     if (aCircuitDataIndex != 0) {
/* 90:75 */       return "";
/* 91:   */     }
/* 92:76 */     return null;
/* 93:   */   }
/* 94:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_Randomizer
 * JD-Core Version:    0.7.0.1
 */