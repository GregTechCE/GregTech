/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class GT_Cover_RedstoneTransmitterExternal
/*  8:   */   extends GT_Cover_RedstoneWirelessBase
/*  9:   */ {
/* 10:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 11:   */   {
/* 12: 9 */     GregTech_API.sWirelessRedstone.put(Integer.valueOf(aCoverVariable), Byte.valueOf(aInputRedstone));
/* 13:10 */     return aCoverVariable;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 17:   */   {
/* 18:15 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 22:   */   {
/* 23:20 */     return 1;
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_RedstoneTransmitterExternal
 * JD-Core Version:    0.7.0.1
 */