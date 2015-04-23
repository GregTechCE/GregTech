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
/* 10:   */ import gregtech.common.gui.GT_Container_ChestBuffer;
/* 11:   */ import gregtech.common.gui.GT_GUIContainer_ChestBuffer;
/* 12:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 13:   */ 
/* 14:   */ public class GT_MetaTileEntity_ChestBuffer
/* 15:   */   extends GT_MetaTileEntity_Buffer
/* 16:   */ {
/* 17:   */   public GT_MetaTileEntity_ChestBuffer(int aID, String aName, String aNameRegional, int aTier)
/* 18:   */   {
/* 19:16 */     super(aID, aName, aNameRegional, aTier, 28, "Buffering lots of incoming Items");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public GT_MetaTileEntity_ChestBuffer(int aID, String aName, String aNameRegional, int aTier, int aInvSlotCount, String aDescription)
/* 23:   */   {
/* 24:20 */     super(aID, aName, aNameRegional, aTier, aInvSlotCount, aDescription);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public GT_MetaTileEntity_ChestBuffer(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures)
/* 28:   */   {
/* 29:24 */     super(aName, aTier, aInvSlotCount, aDescription, aTextures);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 33:   */   {
/* 34:29 */     return new GT_MetaTileEntity_ChestBuffer(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ITexture getOverlayIcon()
/* 38:   */   {
/* 39:34 */     return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_CHESTBUFFER);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isValidSlot(int aIndex)
/* 43:   */   {
/* 44:37 */     return aIndex < this.mInventory.length - 1;
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected void moveItems(IGregTechTileEntity aBaseMetaTileEntity, long aTimer)
/* 48:   */   {
/* 49:41 */     fillStacksIntoFirstSlots();
/* 50:42 */     super.moveItems(aBaseMetaTileEntity, aTimer);
/* 51:43 */     fillStacksIntoFirstSlots();
/* 52:   */   }
/* 53:   */   
/* 54:   */   protected void fillStacksIntoFirstSlots()
/* 55:   */   {
/* 56:47 */     for (int i = 0; i < this.mInventory.length - 1; i++) {
/* 57:47 */       for (int j = i + 1; j < this.mInventory.length - 1; j++) {
/* 58:47 */         if ((this.mInventory[j] != null) && ((this.mInventory[i] == null) || (GT_Utility.areStacksEqual(this.mInventory[i], this.mInventory[j])))) {
/* 59:48 */           GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), j, i, (byte)64, (byte)1, (byte)64, (byte)1);
/* 60:   */         }
/* 61:   */       }
/* 62:   */     }
/* 63:   */   }
/* 64:   */   
/* 65:   */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 66:   */   {
/* 67:54 */     return new GT_Container_ChestBuffer(aPlayerInventory, aBaseMetaTileEntity);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 71:   */   {
/* 72:59 */     return new GT_GUIContainer_ChestBuffer(aPlayerInventory, aBaseMetaTileEntity);
/* 73:   */   }
/* 74:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.automation.GT_MetaTileEntity_ChestBuffer
 * JD-Core Version:    0.7.0.1
 */