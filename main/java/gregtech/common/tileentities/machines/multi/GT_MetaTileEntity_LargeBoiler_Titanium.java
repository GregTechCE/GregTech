/*  1:   */ package gregtech.common.tileentities.machines.multi;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ 
/*  8:   */ public class GT_MetaTileEntity_LargeBoiler_Titanium
/*  9:   */   extends GT_MetaTileEntity_LargeBoiler
/* 10:   */ {
/* 11:   */   public GT_MetaTileEntity_LargeBoiler_Titanium(int aID, String aName, String aNameRegional)
/* 12:   */   {
/* 13:10 */     super(aID, aName, aNameRegional);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public GT_MetaTileEntity_LargeBoiler_Titanium(String aName)
/* 17:   */   {
/* 18:14 */     super(aName);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 22:   */   {
/* 23:19 */     return new GT_MetaTileEntity_LargeBoiler_Titanium(this.mName);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Block getCasingBlock()
/* 27:   */   {
/* 28:22 */     return GregTech_API.sBlockCasings4;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public byte getCasingMeta()
/* 32:   */   {
/* 33:23 */     return 2;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public byte getCasingTextureIndex()
/* 37:   */   {
/* 38:24 */     return 50;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Block getPipeBlock()
/* 42:   */   {
/* 43:26 */     return GregTech_API.sBlockCasings2;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public byte getPipeMeta()
/* 47:   */   {
/* 48:27 */     return 14;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Block getFireboxBlock()
/* 52:   */   {
/* 53:29 */     return GregTech_API.sBlockCasings4;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public byte getFireboxMeta()
/* 57:   */   {
/* 58:30 */     return 3;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public byte getFireboxTextureIndex()
/* 62:   */   {
/* 63:31 */     return 51;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public int getEUt()
/* 67:   */   {
/* 68:33 */     return 800;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int getEfficiencyIncrease()
/* 72:   */   {
/* 73:34 */     return 8;
/* 74:   */   }
/* 75:   */ }