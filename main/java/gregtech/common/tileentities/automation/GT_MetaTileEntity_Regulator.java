/*  1:   */ package gregtech.common.tileentities.automation;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  6:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Buffer;
/*  8:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import gregtech.common.gui.GT_Container_Regulator;
/* 11:   */ import gregtech.common.gui.GT_GUIContainer_Regulator;

/* 12:   */ import java.util.Arrays;

/* 13:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ import net.minecraft.nbt.NBTTagCompound;
/* 16:   */ 
/* 17:   */ public class GT_MetaTileEntity_Regulator
/* 18:   */   extends GT_MetaTileEntity_Buffer
/* 19:   */ {
/* 20:20 */   public int[] mTargetSlots = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 21:   */   
/* 22:   */   public GT_MetaTileEntity_Regulator(int aID, String aName, String aNameRegional, int aTier)
/* 23:   */   {
/* 24:23 */     super(aID, aName, aNameRegional, aTier, 19, "Regulating incoming Items");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public GT_MetaTileEntity_Regulator(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures)
/* 28:   */   {
/* 29:27 */     super(aName, aTier, aInvSlotCount, aDescription, aTextures);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 33:   */   {
/* 34:32 */     return new GT_MetaTileEntity_Regulator(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ITexture getOverlayIcon()
/* 38:   */   {
/* 39:37 */     return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_REGULATOR);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isValidSlot(int aIndex)
/* 43:   */   {
/* 44:40 */     return aIndex < 9;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 48:   */   {
/* 49:44 */     return new GT_Container_Regulator(aPlayerInventory, aBaseMetaTileEntity);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 53:   */   {
/* 54:49 */     return new GT_GUIContainer_Regulator(aPlayerInventory, aBaseMetaTileEntity);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void saveNBTData(NBTTagCompound aNBT)
/* 58:   */   {
/* 59:54 */     super.saveNBTData(aNBT);
/* 60:55 */     aNBT.setInteger("mTargetSlot1", this.mTargetSlots[0]);
/* 61:56 */     aNBT.setInteger("mTargetSlot2", this.mTargetSlots[1]);
/* 62:57 */     aNBT.setInteger("mTargetSlot3", this.mTargetSlots[2]);
/* 63:58 */     aNBT.setInteger("mTargetSlot4", this.mTargetSlots[3]);
/* 64:59 */     aNBT.setInteger("mTargetSlot5", this.mTargetSlots[4]);
/* 65:60 */     aNBT.setInteger("mTargetSlot6", this.mTargetSlots[5]);
/* 66:61 */     aNBT.setInteger("mTargetSlot7", this.mTargetSlots[6]);
/* 67:62 */     aNBT.setInteger("mTargetSlot8", this.mTargetSlots[7]);
/* 68:63 */     aNBT.setInteger("mTargetSlot9", this.mTargetSlots[8]);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void loadNBTData(NBTTagCompound aNBT)
/* 72:   */   {
/* 73:68 */     super.loadNBTData(aNBT);
/* 74:69 */     this.mTargetSlots[0] = aNBT.getInteger("mTargetSlot1");
/* 75:70 */     this.mTargetSlots[1] = aNBT.getInteger("mTargetSlot2");
/* 76:71 */     this.mTargetSlots[2] = aNBT.getInteger("mTargetSlot3");
/* 77:72 */     this.mTargetSlots[3] = aNBT.getInteger("mTargetSlot4");
/* 78:73 */     this.mTargetSlots[4] = aNBT.getInteger("mTargetSlot5");
/* 79:74 */     this.mTargetSlots[5] = aNBT.getInteger("mTargetSlot6");
/* 80:75 */     this.mTargetSlots[6] = aNBT.getInteger("mTargetSlot7");
/* 81:76 */     this.mTargetSlots[7] = aNBT.getInteger("mTargetSlot8");
/* 82:77 */     this.mTargetSlots[8] = aNBT.getInteger("mTargetSlot9");
/* 83:   */   }
/* 84:   */   
/* 85:   */   public void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer)
/* 86:   */   {
/* 87:82 */     int i = 0;
/* 88:82 */     for (int tCosts = 0; i < 9; i++) {
/* 89:82 */       if (this.mInventory[(i + 9)] != null)
/* 90:   */       {
/* 91:83 */         tCosts = GT_Utility.moveOneItemStackIntoSlot(getBaseMetaTileEntity(), getBaseMetaTileEntity().getTileEntityAtSide(getBaseMetaTileEntity().getBackFacing()), getBaseMetaTileEntity().getBackFacing(), this.mTargetSlots[i], Arrays.asList(new ItemStack[] { this.mInventory[(i + 9)] }), false, (byte)this.mInventory[(i + 9)].stackSize, (byte)this.mInventory[(i + 9)].stackSize, (byte)64, (byte)1) * 3;
/* 92:84 */         if (tCosts > 0)
/* 93:   */         {
/* 94:84 */           this.mSuccess = 50;getBaseMetaTileEntity().decreaseStoredEnergyUnits(tCosts, true); break;
/* 95:   */         }
/* 96:   */       }
/* 97:   */     }
/* 98:   */   }
/* 99:   */   
/* :0:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* :1:   */   {
/* :2:90 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (GT_Utility.areStacksEqual(aStack, this.mInventory[(aIndex + 9)]));
/* :3:   */   }
/* :4:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.automation.GT_MetaTileEntity_Regulator
 * JD-Core Version:    0.7.0.1
 */