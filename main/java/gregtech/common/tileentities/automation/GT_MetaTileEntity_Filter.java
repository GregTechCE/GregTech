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
/* 10:   */ import gregtech.common.gui.GT_Container_Filter;
/* 11:   */ import gregtech.common.gui.GT_GUIContainer_Filter;
/* 12:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.nbt.NBTTagCompound;
/* 15:   */ 
/* 16:   */ public class GT_MetaTileEntity_Filter
/* 17:   */   extends GT_MetaTileEntity_Buffer
/* 18:   */ {
/* 19:17 */   public boolean bIgnoreNBT = false;
/* 20:17 */   public boolean bInvertFilter = false;
/* 21:   */   
/* 22:   */   public GT_MetaTileEntity_Filter(int aID, String aName, String aNameRegional, int aTier)
/* 23:   */   {
/* 24:20 */     super(aID, aName, aNameRegional, aTier, 19, "Filtering incoming Items");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public GT_MetaTileEntity_Filter(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures)
/* 28:   */   {
/* 29:24 */     super(aName, aTier, aInvSlotCount, aDescription, aTextures);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 33:   */   {
/* 34:29 */     return new GT_MetaTileEntity_Filter(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ITexture getOverlayIcon()
/* 38:   */   {
/* 39:34 */     return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_FILTER);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isValidSlot(int aIndex)
/* 43:   */   {
/* 44:37 */     return aIndex < 9;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 48:   */   {
/* 49:41 */     return new GT_Container_Filter(aPlayerInventory, aBaseMetaTileEntity);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 53:   */   {
/* 54:46 */     return new GT_GUIContainer_Filter(aPlayerInventory, aBaseMetaTileEntity);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void saveNBTData(NBTTagCompound aNBT)
/* 58:   */   {
/* 59:51 */     super.saveNBTData(aNBT);
/* 60:52 */     aNBT.setBoolean("bInvertFilter", this.bInvertFilter);
/* 61:53 */     aNBT.setBoolean("bIgnoreNBT", this.bIgnoreNBT);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void loadNBTData(NBTTagCompound aNBT)
/* 65:   */   {
/* 66:58 */     super.loadNBTData(aNBT);
/* 67:59 */     this.bInvertFilter = aNBT.getBoolean("bInvertFilter");
/* 68:60 */     this.bIgnoreNBT = aNBT.getBoolean("bIgnoreNBT");
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 72:   */   {
/* 73:65 */     if (!super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) {
/* 74:65 */       return false;
/* 75:   */     }
/* 76:66 */     if (this.bInvertFilter)
/* 77:   */     {
/* 78:67 */       for (byte i = 9; i < 18; i = (byte)(i + 1)) {
/* 79:67 */         if (GT_Utility.areStacksEqual(this.mInventory[i], aStack, this.bIgnoreNBT)) {
/* 80:67 */           return false;
/* 81:   */         }
/* 82:   */       }
/* 83:68 */       return true;
/* 84:   */     }
/* 85:70 */     return GT_Utility.areStacksEqual(this.mInventory[(aIndex + 9)], aStack, this.bIgnoreNBT);
/* 86:   */   }
/* 87:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.automation.GT_MetaTileEntity_Filter
 * JD-Core Version:    0.7.0.1
 */