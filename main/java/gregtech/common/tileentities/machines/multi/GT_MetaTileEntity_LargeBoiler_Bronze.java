/*  1:   */ package gregtech.common.tileentities.machines.multi;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ 
/*  8:   */ public class GT_MetaTileEntity_LargeBoiler_Bronze
/*  9:   */   extends GT_MetaTileEntity_LargeBoiler
/* 10:   */ {
/* 11:   */   public GT_MetaTileEntity_LargeBoiler_Bronze(int aID, String aName, String aNameRegional)
/* 12:   */   {
/* 13:10 */     super(aID, aName, aNameRegional);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public GT_MetaTileEntity_LargeBoiler_Bronze(String aName)
/* 17:   */   {
/* 18:14 */     super(aName);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 22:   */   {
/* 23:19 */     return new GT_MetaTileEntity_LargeBoiler_Bronze(this.mName);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Block getCasingBlock()
/* 27:   */   {
/* 28:22 */     return GregTech_API.sBlockCasings1;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public byte getCasingMeta()
/* 32:   */   {
/* 33:23 */     return 10;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public byte getCasingTextureIndex()
/* 37:   */   {
/* 38:24 */     return 10;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Block getPipeBlock()
/* 42:   */   {
/* 43:26 */     return GregTech_API.sBlockCasings2;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public byte getPipeMeta()
/* 47:   */   {
/* 48:27 */     return 12;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Block getFireboxBlock()
/* 52:   */   {
/* 53:29 */     return GregTech_API.sBlockCasings3;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public byte getFireboxMeta()
/* 57:   */   {
/* 58:30 */     return 13;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public byte getFireboxTextureIndex()
/* 62:   */   {
/* 63:31 */     return 45;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int getEUt()
/* 67:   */   {
/* 68:33 */     return 400;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int getEfficiencyIncrease()
/* 72:   */   {
/* 73:34 */     return 16;
/* 74:   */   }
/* 75:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeBoiler_Bronze
 * JD-Core Version:    0.7.0.1
 */