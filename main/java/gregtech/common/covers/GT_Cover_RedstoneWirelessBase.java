/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import java.util.Map;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 10:   */ import net.minecraftforge.fluids.Fluid;
/* 11:   */ 
/* 12:   */ public abstract class GT_Cover_RedstoneWirelessBase
/* 13:   */   extends GT_CoverBehavior
/* 14:   */ {
/* 15:   */   public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced)
/* 16:   */   {
/* 17:13 */     GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte)0));
/* 18:14 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 22:   */   {
/* 23:19 */     if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide > 3) && (((aY > 0.375D) && (aY < 0.625D)) || ((aSide < 2) && (((aZ > 0.375D) && (aZ < 0.625D)) || (aSide == 2) || (aSide == 3))))))
/* 24:   */     {
/* 25:20 */       GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte)0));
/* 26:21 */       aCoverVariable = GT_Utility.stackToInt(aPlayer.inventory.getCurrentItem());
/* 27:22 */       aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
/* 28:23 */       GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aCoverVariable);
/* 29:24 */       return true;
/* 30:   */     }
/* 31:26 */     return false;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 35:   */   {
/* 36:31 */     if (((aX > 0.375D) && (aX < 0.625D)) || ((aSide <= 3) || (((aY > 0.375D) && (aY < 0.625D)) || ((aSide >= 2) || (((aZ <= 0.375D) || (aZ >= 0.625D)) && (aSide != 2) && (aSide != 3))))))
/* 37:   */     {
/* 38:34 */       GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf((byte)0));
/* 39:35 */       float[] tCoords = GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ);
/* 40:36 */       switch ((byte)((byte)(int)(tCoords[0] * 2.0F) + 2 * (byte)(int)(tCoords[1] * 2.0F)))
/* 41:   */       {
/* 42:   */       case 0: 
/* 43:38 */         aCoverVariable -= 32;
/* 44:39 */         break;
/* 45:   */       case 1: 
/* 46:41 */         aCoverVariable += 32;
/* 47:42 */         break;
/* 48:   */       case 2: 
/* 49:44 */         aCoverVariable -= 1024;
/* 50:45 */         break;
/* 51:   */       case 3: 
/* 52:47 */         aCoverVariable += 1024;
/* 53:   */       }
/* 54:   */     }
/* 55:51 */     GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aCoverVariable);
/* 56:52 */     return aCoverVariable;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 60:   */   {
/* 61:57 */     return true;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 65:   */   {
/* 66:62 */     return true;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 70:   */   {
/* 71:67 */     return true;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 75:   */   {
/* 76:72 */     return true;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 80:   */   {
/* 81:77 */     return true;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 85:   */   {
/* 86:82 */     return true;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public String getDescription(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 90:   */   {
/* 91:87 */     return "Frequency: " + aCoverVariable;
/* 92:   */   }
/* 93:   */   
/* 94:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 95:   */   {
/* 96:92 */     return 1;
/* 97:   */   }
/* 98:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_RedstoneWirelessBase
 * JD-Core Version:    0.7.0.1
 */