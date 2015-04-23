/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.gui.GT_Slot_Holo;
/*  5:   */ import gregtech.api.gui.GT_Slot_Render;
/*  6:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  7:   */ import gregtech.api.util.GT_Utility;
/*  8:   */ import gregtech.common.tileentities.automation.GT_MetaTileEntity_TypeFilter;
/*  9:   */ import java.util.List;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 12:   */ import net.minecraft.inventory.Slot;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class GT_Container_TypeFilter
/* 16:   */   extends GT_ContainerMetaTile_Machine
/* 17:   */ {
/* 18:   */   public GT_Container_TypeFilter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 19:   */   {
/* 20:17 */     super(aInventoryPlayer, aTileEntity);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void addSlots(InventoryPlayer aInventoryPlayer)
/* 24:   */   {
/* 25:22 */     addSlotToContainer(new Slot(this.mTileEntity, 0, 98, 5));
/* 26:23 */     addSlotToContainer(new Slot(this.mTileEntity, 1, 116, 5));
/* 27:24 */     addSlotToContainer(new Slot(this.mTileEntity, 2, 134, 5));
/* 28:25 */     addSlotToContainer(new Slot(this.mTileEntity, 3, 98, 23));
/* 29:26 */     addSlotToContainer(new Slot(this.mTileEntity, 4, 116, 23));
/* 30:27 */     addSlotToContainer(new Slot(this.mTileEntity, 5, 134, 23));
/* 31:28 */     addSlotToContainer(new Slot(this.mTileEntity, 6, 98, 41));
/* 32:29 */     addSlotToContainer(new Slot(this.mTileEntity, 7, 116, 41));
/* 33:30 */     addSlotToContainer(new Slot(this.mTileEntity, 8, 134, 41));
/* 34:   */     
/* 35:32 */     addSlotToContainer(new GT_Slot_Render(this.mTileEntity, 9, 35, 23));
/* 36:   */     
/* 37:34 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 8, 63, false, true, 1));
/* 38:35 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 26, 63, false, true, 1));
/* 39:36 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 44, 63, false, true, 1));
/* 40:37 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 62, 63, false, true, 1));
/* 41:38 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 80, 63, false, true, 1));
/* 42:   */   }
/* 43:   */   
/* 44:   */   public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer)
/* 45:   */   {
/* 46:43 */     if (aSlotIndex < 9) {
/* 47:43 */       return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 48:   */     }
/* 49:45 */     Slot tSlot = (Slot)this.inventorySlots.get(aSlotIndex);
/* 50:46 */     if (tSlot != null)
/* 51:   */     {
/* 52:47 */       if (this.mTileEntity.getMetaTileEntity() == null) {
/* 53:47 */         return null;
/* 54:   */       }
/* 55:48 */       if (aSlotIndex == 9)
/* 56:   */       {
/* 57:49 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).clickTypeIcon(aMouseclick != 0);
/* 58:50 */         return null;
/* 59:   */       }
/* 60:51 */       if (aSlotIndex == 10)
/* 61:   */       {
/* 62:52 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bOutput);
/* 63:53 */         if (((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bOutput) {
/* 64:54 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
/* 65:   */         } else {
/* 66:56 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
/* 67:   */         }
/* 68:57 */         return null;
/* 69:   */       }
/* 70:58 */       if (aSlotIndex == 11)
/* 71:   */       {
/* 72:59 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
/* 73:60 */         if (((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
/* 74:61 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Redstone if slots contain something");
/* 75:   */         } else {
/* 76:63 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Redstone");
/* 77:   */         }
/* 78:64 */         return null;
/* 79:   */       }
/* 80:65 */       if (aSlotIndex == 12)
/* 81:   */       {
/* 82:66 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvert);
/* 83:67 */         if (((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvert) {
/* 84:68 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Redstone");
/* 85:   */         } else {
/* 86:70 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Redstone");
/* 87:   */         }
/* 88:71 */         return null;
/* 89:   */       }
/* 90:72 */       if (aSlotIndex == 13)
/* 91:   */       {
/* 92:73 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvertFilter = (!((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvertFilter);
/* 93:74 */         if (((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bInvertFilter) {
/* 94:75 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Filter");
/* 95:   */         } else {
/* 96:77 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Filter");
/* 97:   */         }
/* 98:78 */         return null;
/* 99:   */       }
/* :0:79 */       if (aSlotIndex == 14)
/* :1:   */       {
/* :2:80 */         ((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bNBTAllowed = (!((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bNBTAllowed);
/* :3:81 */         if (((GT_MetaTileEntity_TypeFilter)this.mTileEntity.getMetaTileEntity()).bNBTAllowed) {
/* :4:82 */           GT_Utility.sendChatToPlayer(aPlayer, "Allow Items with NBT");
/* :5:   */         } else {
/* :6:84 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't allow Items with NBT");
/* :7:   */         }
/* :8:85 */         return null;
/* :9:   */       }
/* ;0:   */     }
/* ;1:88 */     return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* ;2:   */   }
/* ;3:   */   
/* ;4:   */   public int getSlotCount()
/* ;5:   */   {
/* ;6:93 */     return 9;
/* ;7:   */   }
/* ;8:   */   
/* ;9:   */   public int getShiftClickSlotCount()
/* <0:   */   {
/* <1:98 */     return 9;
/* <2:   */   }
/* <3:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_TypeFilter
 * JD-Core Version:    0.7.0.1
 */