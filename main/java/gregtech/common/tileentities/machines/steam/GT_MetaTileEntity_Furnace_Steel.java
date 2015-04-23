/*  1:   */ package gregtech.common.tileentities.machines.steam;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.gui.GT_GUIContainer_BasicMachine;
/*  6:   */ import gregtech.api.interfaces.ITexture;
/*  7:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  8:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  9:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Steel;
/* 10:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 11:   */ import gregtech.api.util.GT_ModHandler;
/* 12:   */ import gregtech.api.util.GT_Utility;

/* 13:   */ import java.util.Map;

/* 14:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ 
/* 17:   */ public class GT_MetaTileEntity_Furnace_Steel
/* 18:   */   extends GT_MetaTileEntity_BasicMachine_Steel
/* 19:   */ {
/* 20:   */   public GT_MetaTileEntity_Furnace_Steel(int aID, String aName, String aNameRegional)
/* 21:   */   {
/* 22:18 */     super(aID, aName, aNameRegional, "Smelting things with compressed Steam", 1, 1, true);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public GT_MetaTileEntity_Furnace_Steel(String aName, String aDescription, ITexture[][][] aTextures)
/* 26:   */   {
/* 27:22 */     super(aName, aDescription, aTextures, 1, 1, true);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 31:   */   {
/* 32:27 */     return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "SteelFurnace.png", "smelting");
/* 33:   */   }
/* 34:   */   
/* 35:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 36:   */   {
/* 37:32 */     return new GT_MetaTileEntity_Furnace_Steel(this.mName, this.mDescription, this.mTextures);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int checkRecipe()
/* 41:   */   {
/* 42:37 */     if (null != (this.mOutputItems[0] = GT_ModHandler.getSmeltingOutput(getInputAt(0), true, getOutputAt(0))))
/* 43:   */     {
/* 44:38 */       this.mEUt = 12;
/* 45:39 */       this.mMaxProgresstime = 128;
/* 46:40 */       return 2;
/* 47:   */     }
/* 48:42 */     return 0;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 52:   */   {
/* 53:47 */     if (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {}
/* 54:47 */     return GT_ModHandler.getSmeltingOutput(GT_Utility.copyAmount(64L, new Object[] { aStack }), false, null) != null;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
/* 58:   */   {
/* 59:52 */     super.startSoundLoop(aIndex, aX, aY, aZ);
/* 60:53 */     if (aIndex == 1) {
/* 61:53 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(207)), 10, 1.0F, aX, aY, aZ);
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void startProcess()
/* 66:   */   {
/* 67:58 */     sendLoopStart((byte)1);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public ITexture[] getSideFacingActive(byte aColor)
/* 71:   */   {
/* 72:63 */     return new ITexture[] { super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_FURNACE_ACTIVE) };
/* 73:   */   }
/* 74:   */   
/* 75:   */   public ITexture[] getSideFacingInactive(byte aColor)
/* 76:   */   {
/* 77:68 */     return new ITexture[] { super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_FURNACE) };
/* 78:   */   }
/* 79:   */   
/* 80:   */   public ITexture[] getFrontFacingActive(byte aColor)
/* 81:   */   {
/* 82:73 */     return new ITexture[] { super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE_ACTIVE) };
/* 83:   */   }
/* 84:   */   
/* 85:   */   public ITexture[] getFrontFacingInactive(byte aColor)
/* 86:   */   {
/* 87:78 */     return new ITexture[] { super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_FURNACE) };
/* 88:   */   }
/* 89:   */   
/* 90:   */   public ITexture[] getTopFacingActive(byte aColor)
/* 91:   */   {
/* 92:83 */     return new ITexture[] { super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_FURNACE_ACTIVE) };
/* 93:   */   }
/* 94:   */   
/* 95:   */   public ITexture[] getTopFacingInactive(byte aColor)
/* 96:   */   {
/* 97:88 */     return new ITexture[] { super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_FURNACE) };
/* 98:   */   }
/* 99:   */   
/* :0:   */   public ITexture[] getBottomFacingActive(byte aColor)
/* :1:   */   {
/* :2:93 */     return new ITexture[] { super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_FURNACE_ACTIVE) };
/* :3:   */   }
/* :4:   */   
/* :5:   */   public ITexture[] getBottomFacingInactive(byte aColor)
/* :6:   */   {
/* :7:98 */     return new ITexture[] { super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_FURNACE) };
/* :8:   */   }
/* :9:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.steam.GT_MetaTileEntity_Furnace_Steel
 * JD-Core Version:    0.7.0.1
 */