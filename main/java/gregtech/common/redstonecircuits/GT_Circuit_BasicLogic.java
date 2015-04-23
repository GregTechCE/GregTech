/*  1:   */ package gregtech.common.redstonecircuits;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.IRedstoneCircuitBlock;
/*  4:   */ import gregtech.api.util.GT_CircuitryBehavior;
/*  5:   */ 
/*  6:   */ public class GT_Circuit_BasicLogic
/*  7:   */   extends GT_CircuitryBehavior
/*  8:   */ {
/*  9:   */   public GT_Circuit_BasicLogic(int aIndex)
/* 10:   */   {
/* 11: 9 */     super(aIndex);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 15:   */   {
/* 16:14 */     aCircuitData[0] = 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 20:   */   {
/* 21:19 */     if (aCircuitData[0] < 0) {
/* 22:19 */       aCircuitData[0] = 0;
/* 23:   */     }
/* 24:20 */     if (aCircuitData[0] > 13) {
/* 25:20 */       aCircuitData[0] = 13;
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock)
/* 30:   */   {
/* 31:25 */     if (aCircuitData[0] < 2) {
/* 32:26 */       aRedstoneCircuitBlock.setRedstone((byte)(aCircuitData[0] % 2 == (getAnyRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 33:27 */     } else if (aCircuitData[0] < 4) {
/* 34:28 */       aRedstoneCircuitBlock.setRedstone((byte)(aCircuitData[0] % 2 == (getOneRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 35:29 */     } else if (aCircuitData[0] < 6) {
/* 36:30 */       aRedstoneCircuitBlock.setRedstone((byte)(aCircuitData[0] % 2 == (getAllRedstone(aRedstoneCircuitBlock) ? 0 : 1) ? 15 : 0), aRedstoneCircuitBlock.getOutputFacing());
/* 37:31 */     } else if (aCircuitData[0] < 7) {
/* 38:32 */       aRedstoneCircuitBlock.setRedstone((byte)(15 - getStrongestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
/* 39:33 */     } else if (aCircuitData[0] < 9) {
/* 40:34 */       aRedstoneCircuitBlock.setRedstone((byte)((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ (getStrongestRedstone(aRedstoneCircuitBlock) | getWeakestRedstone(aRedstoneCircuitBlock))), aRedstoneCircuitBlock.getOutputFacing());
/* 41:35 */     } else if (aCircuitData[0] < 11) {
/* 42:36 */       aRedstoneCircuitBlock.setRedstone((byte)((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ getStrongestRedstone(aRedstoneCircuitBlock) ^ getWeakestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
/* 43:37 */     } else if (aCircuitData[0] < 13) {
/* 44:38 */       aRedstoneCircuitBlock.setRedstone((byte)((aCircuitData[0] % 2 == 0 ? 15 : 0) ^ getStrongestRedstone(aRedstoneCircuitBlock) & getWeakestRedstone(aRedstoneCircuitBlock)), aRedstoneCircuitBlock.getOutputFacing());
/* 45:39 */     } else if (aCircuitData[0] < 14) {
/* 46:40 */       aRedstoneCircuitBlock.setRedstone((byte)(getStrongestRedstone(aRedstoneCircuitBlock) ^ 0xF), aRedstoneCircuitBlock.getOutputFacing());
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String getName()
/* 51:   */   {
/* 52:46 */     return "Basic Logic";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getDescription()
/* 56:   */   {
/* 57:51 */     return "Regular Logic Gates";
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex)
/* 61:   */   {
/* 62:56 */     if (aCircuitDataIndex == 0) {
/* 63:57 */       switch (aCircuitData[0])
/* 64:   */       {
/* 65:   */       case 0: 
/* 66:58 */         return "OR";
/* 67:   */       case 1: 
/* 68:59 */         return "NOR";
/* 69:   */       case 2: 
/* 70:60 */         return "XOR";
/* 71:   */       case 3: 
/* 72:61 */         return "XNOR";
/* 73:   */       case 4: 
/* 74:62 */         return "AND";
/* 75:   */       case 5: 
/* 76:63 */         return "NAND";
/* 77:   */       case 6: 
/* 78:64 */         return "INVERT";
/* 79:   */       case 7: 
/* 80:65 */         return "BIT_OR";
/* 81:   */       case 8: 
/* 82:66 */         return "BIT_NOR";
/* 83:   */       case 9: 
/* 84:67 */         return "BIT_XOR";
/* 85:   */       case 10: 
/* 86:68 */         return "BIT_XNOR";
/* 87:   */       case 11: 
/* 88:69 */         return "BIT_AND";
/* 89:   */       case 12: 
/* 90:70 */         return "BIT_NAND";
/* 91:   */       case 13: 
/* 92:71 */         return "BIT_INVERT";
/* 93:   */       }
/* 94:   */     }
/* 95:74 */     return "";
/* 96:   */   }
/* 97:   */   
/* 98:   */   public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex)
/* 99:   */   {
/* :0:79 */     return false;
/* :1:   */   }
/* :2:   */   
/* :3:   */   public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex)
/* :4:   */   {
/* :5:84 */     return "";
/* :6:   */   }
/* :7:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.redstonecircuits.GT_Circuit_BasicLogic
 * JD-Core Version:    0.7.0.1
 */