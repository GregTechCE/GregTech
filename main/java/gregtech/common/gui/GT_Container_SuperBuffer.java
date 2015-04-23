/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.gui.GT_Slot_Holo;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import gregtech.common.tileentities.automation.GT_MetaTileEntity_ChestBuffer;
/*  8:   */ import java.util.List;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 11:   */ import net.minecraft.inventory.Slot;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class GT_Container_SuperBuffer
/* 15:   */   extends GT_ContainerMetaTile_Machine
/* 16:   */ {
/* 17:   */   public GT_Container_SuperBuffer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 18:   */   {
/* 19:16 */     super(aInventoryPlayer, aTileEntity);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addSlots(InventoryPlayer aInventoryPlayer)
/* 23:   */   {
/* 24:21 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 8, 63, false, true, 1));
/* 25:22 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 26, 63, false, true, 1));
/* 26:23 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 44, 63, false, true, 1));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer)
/* 30:   */   {
/* 31:28 */     if (aSlotIndex < 0) {
/* 32:28 */       return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 33:   */     }
/* 34:30 */     Slot tSlot = (Slot)this.inventorySlots.get(aSlotIndex);
/* 35:31 */     if (tSlot != null)
/* 36:   */     {
/* 37:32 */       if (this.mTileEntity.getMetaTileEntity() == null) {
/* 38:32 */         return null;
/* 39:   */       }
/* 40:33 */       if (aSlotIndex == 0)
/* 41:   */       {
/* 42:34 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput);
/* 43:35 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput) {
/* 44:36 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
/* 45:   */         } else {
/* 46:38 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
/* 47:   */         }
/* 48:39 */         return null;
/* 49:   */       }
/* 50:40 */       if (aSlotIndex == 1)
/* 51:   */       {
/* 52:41 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
/* 53:42 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
/* 54:43 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Redstone if no Slot is free");
/* 55:   */         } else {
/* 56:45 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Redstone");
/* 57:   */         }
/* 58:46 */         return null;
/* 59:   */       }
/* 60:47 */       if (aSlotIndex == 2)
/* 61:   */       {
/* 62:48 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert);
/* 63:49 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert) {
/* 64:50 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Redstone");
/* 65:   */         } else {
/* 66:52 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Redstone");
/* 67:   */         }
/* 68:53 */         return null;
/* 69:   */       }
/* 70:   */     }
/* 71:57 */     return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 72:   */   }
/* 73:   */   
/* 74:   */   public int getSlotCount()
/* 75:   */   {
/* 76:62 */     return 0;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int getShiftClickSlotCount()
/* 80:   */   {
/* 81:67 */     return 0;
/* 82:   */   }
/* 83:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_SuperBuffer
 * JD-Core Version:    0.7.0.1
 */