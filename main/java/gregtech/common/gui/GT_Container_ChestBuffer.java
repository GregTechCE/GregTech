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
/* 14:   */ public class GT_Container_ChestBuffer
/* 15:   */   extends GT_ContainerMetaTile_Machine
/* 16:   */ {
/* 17:   */   public GT_Container_ChestBuffer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 18:   */   {
/* 19:16 */     super(aInventoryPlayer, aTileEntity);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addSlots(InventoryPlayer aInventoryPlayer)
/* 23:   */   {
/* 24:21 */     for (int y = 0; y < 3; y++) {
/* 25:21 */       for (int x = 0; x < 9; x++) {
/* 26:21 */         addSlotToContainer(new Slot(this.mTileEntity, x + y * 9, 8 + x * 18, 5 + y * 18));
/* 27:   */       }
/* 28:   */     }
/* 29:22 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 8, 63, false, true, 1));
/* 30:23 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 26, 63, false, true, 1));
/* 31:24 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 44, 63, false, true, 1));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer)
/* 35:   */   {
/* 36:29 */     if (aSlotIndex < 27) {
/* 37:29 */       return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 38:   */     }
/* 39:31 */     Slot tSlot = (Slot)this.inventorySlots.get(aSlotIndex);
/* 40:32 */     if (tSlot != null)
/* 41:   */     {
/* 42:33 */       if (this.mTileEntity.getMetaTileEntity() == null) {
/* 43:33 */         return null;
/* 44:   */       }
/* 45:34 */       if (aSlotIndex == 27)
/* 46:   */       {
/* 47:35 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput);
/* 48:36 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bOutput) {
/* 49:37 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
/* 50:   */         } else {
/* 51:39 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
/* 52:   */         }
/* 53:40 */         return null;
/* 54:   */       }
/* 55:41 */       if (aSlotIndex == 28)
/* 56:   */       {
/* 57:42 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
/* 58:43 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
/* 59:44 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Redstone if no Slot is free");
/* 60:   */         } else {
/* 61:46 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Redstone");
/* 62:   */         }
/* 63:47 */         return null;
/* 64:   */       }
/* 65:48 */       if (aSlotIndex == 29)
/* 66:   */       {
/* 67:49 */         ((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert);
/* 68:50 */         if (((GT_MetaTileEntity_ChestBuffer)this.mTileEntity.getMetaTileEntity()).bInvert) {
/* 69:51 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Redstone");
/* 70:   */         } else {
/* 71:53 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Redstone");
/* 72:   */         }
/* 73:54 */         return null;
/* 74:   */       }
/* 75:   */     }
/* 76:58 */     return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int getSlotCount()
/* 80:   */   {
/* 81:64 */     return 27;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public int getShiftClickSlotCount()
/* 85:   */   {
/* 86:69 */     return 27;
/* 87:   */   }
/* 88:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_ChestBuffer
 * JD-Core Version:    0.7.0.1
 */