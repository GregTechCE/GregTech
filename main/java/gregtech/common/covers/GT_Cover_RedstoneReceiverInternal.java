/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class GT_Cover_RedstoneReceiverInternal
/*  8:   */   extends GT_Cover_RedstoneWirelessBase
/*  9:   */ {
/* 10:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 11:   */   {
/* 12: 9 */     return aCoverVariable;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public byte getRedstoneInput(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 16:   */   {
/* 17:14 */     return GregTech_API.sWirelessRedstone.get(Integer.valueOf(aCoverVariable)) == null ? 0 : ((Byte)GregTech_API.sWirelessRedstone.get(Integer.valueOf(aCoverVariable))).byteValue();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 21:   */   {
/* 22:19 */     return 1;
/* 23:   */   }
/* 24:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_RedstoneReceiverInternal
 * JD-Core Version:    0.7.0.1
 */