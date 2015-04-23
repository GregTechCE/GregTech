/*   1:    */ package gregtech.common.tileentities.machines.steam;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.gui.GT_GUIContainer_BasicMachine;
/*   6:    */ import gregtech.api.interfaces.ITexture;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*   9:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Steel;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_ModHandler;
/*  12:    */ import gregtech.api.util.GT_Utility;

/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Random;

/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class GT_MetaTileEntity_Macerator_Steel
/*  20:    */   extends GT_MetaTileEntity_BasicMachine_Steel
/*  21:    */ {
/*  22:    */   public GT_MetaTileEntity_Macerator_Steel(int aID, String aName, String aNameRegional)
/*  23:    */   {
/*  24: 21 */     super(aID, aName, aNameRegional, "Macerating your Ores", 1, 1, false);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public GT_MetaTileEntity_Macerator_Steel(String aName, String aDescription, ITexture[][][] aTextures)
/*  28:    */   {
/*  29: 25 */     super(aName, aDescription, aTextures, 1, 1, false);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  33:    */   {
/*  34: 30 */     return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "SteelMacerator.png", "ic2.macerator");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  38:    */   {
/*  39: 35 */     return new GT_MetaTileEntity_Macerator_Steel(this.mName, this.mDescription, this.mTextures);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
/*  43:    */   {
/*  44: 40 */     super.onPreTick(aBaseMetaTileEntity, aTick);
/*  45: 41 */     if ((aBaseMetaTileEntity.isClientSide()) && (aBaseMetaTileEntity.isActive()) && (aBaseMetaTileEntity.getFrontFacing() != 1) && (aBaseMetaTileEntity.getCoverIDAtSide((byte)1) == 0) && (!aBaseMetaTileEntity.getOpacityAtSide((byte)1)))
/*  46:    */     {
/*  47: 42 */       Random tRandom = aBaseMetaTileEntity.getWorld().rand;
/*  48: 43 */       aBaseMetaTileEntity.getWorld().spawnParticle("smoke", aBaseMetaTileEntity.getXCoord() + 0.8F - tRandom.nextFloat() * 0.6F, aBaseMetaTileEntity.getYCoord() + 0.9F + tRandom.nextFloat() * 0.2F, aBaseMetaTileEntity.getZCoord() + 0.8F - tRandom.nextFloat() * 0.6F, 0.0D, 0.0D, 0.0D);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int checkRecipe()
/*  53:    */   {
/*  54: 49 */     if (null != (this.mOutputItems[0] = GT_ModHandler.getMaceratorOutput(getInputAt(0), true, getOutputAt(0))))
/*  55:    */     {
/*  56: 50 */       this.mEUt = 6;
/*  57: 51 */       this.mMaxProgresstime = 400;
/*  58: 52 */       return 2;
/*  59:    */     }
/*  60: 54 */     return 0;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/*  64:    */   {
/*  65: 59 */     if (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {}
/*  66: 59 */     return GT_ModHandler.getMaceratorOutput(GT_Utility.copyAmount(64L, new Object[] { aStack }), false, null) != null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
/*  70:    */   {
/*  71: 64 */     super.startSoundLoop(aIndex, aX, aY, aZ);
/*  72: 65 */     if (aIndex == 1) {
/*  73: 65 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(201)), 10, 1.0F, aX, aY, aZ);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void startProcess()
/*  78:    */   {
/*  79: 70 */     sendLoopStart((byte)1);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ITexture[] getSideFacingActive(byte aColor)
/*  83:    */   {
/*  84: 75 */     return new ITexture[] { super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_MACERATOR_ACTIVE) };
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ITexture[] getSideFacingInactive(byte aColor)
/*  88:    */   {
/*  89: 80 */     return new ITexture[] { super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_MACERATOR) };
/*  90:    */   }
/*  91:    */   
/*  92:    */   public ITexture[] getFrontFacingActive(byte aColor)
/*  93:    */   {
/*  94: 85 */     return new ITexture[] { super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_MACERATOR_ACTIVE) };
/*  95:    */   }
/*  96:    */   
/*  97:    */   public ITexture[] getFrontFacingInactive(byte aColor)
/*  98:    */   {
/*  99: 90 */     return new ITexture[] { super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_MACERATOR) };
/* 100:    */   }
/* 101:    */   
/* 102:    */   public ITexture[] getTopFacingActive(byte aColor)
/* 103:    */   {
/* 104: 95 */     return new ITexture[] { super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR_ACTIVE) };
/* 105:    */   }
/* 106:    */   
/* 107:    */   public ITexture[] getTopFacingInactive(byte aColor)
/* 108:    */   {
/* 109:100 */     return new ITexture[] { super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_MACERATOR) };
/* 110:    */   }
/* 111:    */   
/* 112:    */   public ITexture[] getBottomFacingActive(byte aColor)
/* 113:    */   {
/* 114:105 */     return new ITexture[] { super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_MACERATOR_ACTIVE) };
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ITexture[] getBottomFacingInactive(byte aColor)
/* 118:    */   {
/* 119:110 */     return new ITexture[] { super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_MACERATOR) };
/* 120:    */   }
/* 121:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.steam.GT_MetaTileEntity_Macerator_Steel
 * JD-Core Version:    0.7.0.1
 */