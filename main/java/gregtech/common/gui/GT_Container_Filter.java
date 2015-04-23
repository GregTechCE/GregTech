/*   1:    */ package gregtech.common.gui;
/*   2:    */ 
/*   3:    */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*   4:    */ import gregtech.api.gui.GT_Slot_Holo;
/*   5:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   6:    */ import gregtech.api.util.GT_Utility;
/*   7:    */ import gregtech.common.tileentities.automation.GT_MetaTileEntity_Filter;
/*   8:    */ import java.util.List;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  11:    */ import net.minecraft.inventory.Slot;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ 
/*  14:    */ public class GT_Container_Filter
/*  15:    */   extends GT_ContainerMetaTile_Machine
/*  16:    */ {
/*  17:    */   public GT_Container_Filter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/*  18:    */   {
/*  19: 17 */     super(aInventoryPlayer, aTileEntity);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void addSlots(InventoryPlayer aInventoryPlayer)
/*  23:    */   {
/*  24: 22 */     addSlotToContainer(new Slot(this.mTileEntity, 0, 98, 5));
/*  25: 23 */     addSlotToContainer(new Slot(this.mTileEntity, 1, 116, 5));
/*  26: 24 */     addSlotToContainer(new Slot(this.mTileEntity, 2, 134, 5));
/*  27: 25 */     addSlotToContainer(new Slot(this.mTileEntity, 3, 98, 23));
/*  28: 26 */     addSlotToContainer(new Slot(this.mTileEntity, 4, 116, 23));
/*  29: 27 */     addSlotToContainer(new Slot(this.mTileEntity, 5, 134, 23));
/*  30: 28 */     addSlotToContainer(new Slot(this.mTileEntity, 6, 98, 41));
/*  31: 29 */     addSlotToContainer(new Slot(this.mTileEntity, 7, 116, 41));
/*  32: 30 */     addSlotToContainer(new Slot(this.mTileEntity, 8, 134, 41));
/*  33:    */     
/*  34: 32 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 9, 18, 6, false, true, 1));
/*  35: 33 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 35, 6, false, true, 1));
/*  36: 34 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 11, 52, 6, false, true, 1));
/*  37: 35 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 12, 18, 23, false, true, 1));
/*  38: 36 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 13, 35, 23, false, true, 1));
/*  39: 37 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 14, 52, 23, false, true, 1));
/*  40: 38 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 15, 18, 40, false, true, 1));
/*  41: 39 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 16, 35, 40, false, true, 1));
/*  42: 40 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 17, 52, 40, false, true, 1));
/*  43:    */     
/*  44: 42 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 8, 63, false, true, 1));
/*  45: 43 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 26, 63, false, true, 1));
/*  46: 44 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 44, 63, false, true, 1));
/*  47: 45 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 62, 63, false, true, 1));
/*  48: 46 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 80, 63, false, true, 1));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer)
/*  52:    */   {
/*  53: 51 */     if (aSlotIndex < 9) {
/*  54: 51 */       return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/*  55:    */     }
/*  56: 53 */     Slot tSlot = (Slot)this.inventorySlots.get(aSlotIndex);
/*  57: 54 */     if (tSlot != null)
/*  58:    */     {
/*  59: 55 */       if (this.mTileEntity.getMetaTileEntity() == null) {
/*  60: 55 */         return null;
/*  61:    */       }
/*  62: 56 */       if (aSlotIndex < 18)
/*  63:    */       {
/*  64: 57 */         ItemStack tStack = aPlayer.inventory.getItemStack();
/*  65: 58 */         if (tStack == null)
/*  66:    */         {
/*  67: 59 */           tStack = tSlot.getStack();
/*  68: 60 */           if (aMouseclick == 0) {
/*  69: 61 */             tSlot.putStack(null);
/*  70: 63 */           } else if (tStack != null) {
/*  71: 64 */             tStack.setItemDamage(32767);
/*  72:    */           }
/*  73:    */         }
/*  74:    */         else
/*  75:    */         {
/*  76: 68 */           tSlot.putStack(GT_Utility.copyAmount(1L, new Object[] { tStack }));
/*  77:    */         }
/*  78: 70 */         return null;
/*  79:    */       }
/*  80: 71 */       if (aSlotIndex == 18)
/*  81:    */       {
/*  82: 72 */         ((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bOutput);
/*  83: 73 */         if (((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bOutput) {
/*  84: 74 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
/*  85:    */         } else {
/*  86: 76 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
/*  87:    */         }
/*  88: 77 */         return null;
/*  89:    */       }
/*  90: 78 */       if (aSlotIndex == 19)
/*  91:    */       {
/*  92: 79 */         ((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
/*  93: 80 */         if (((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
/*  94: 81 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Redstone if slots contain something");
/*  95:    */         } else {
/*  96: 83 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Redstone");
/*  97:    */         }
/*  98: 84 */         return null;
/*  99:    */       }
/* 100: 85 */       if (aSlotIndex == 20)
/* 101:    */       {
/* 102: 86 */         ((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvert);
/* 103: 87 */         if (((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvert) {
/* 104: 88 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Redstone");
/* 105:    */         } else {
/* 106: 90 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Redstone");
/* 107:    */         }
/* 108: 91 */         return null;
/* 109:    */       }
/* 110: 92 */       if (aSlotIndex == 21)
/* 111:    */       {
/* 112: 93 */         ((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvertFilter = (!((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvertFilter);
/* 113: 94 */         if (((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bInvertFilter) {
/* 114: 95 */           GT_Utility.sendChatToPlayer(aPlayer, "Invert Filter");
/* 115:    */         } else {
/* 116: 97 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Filter");
/* 117:    */         }
/* 118: 98 */         return null;
/* 119:    */       }
/* 120: 99 */       if (aSlotIndex == 22)
/* 121:    */       {
/* 122:100 */         ((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bIgnoreNBT = (!((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bIgnoreNBT);
/* 123:101 */         if (((GT_MetaTileEntity_Filter)this.mTileEntity.getMetaTileEntity()).bIgnoreNBT) {
/* 124:102 */           GT_Utility.sendChatToPlayer(aPlayer, "Ignore NBT");
/* 125:    */         } else {
/* 126:104 */           GT_Utility.sendChatToPlayer(aPlayer, "NBT has to match");
/* 127:    */         }
/* 128:105 */         return null;
/* 129:    */       }
/* 130:    */     }
/* 131:108 */     return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getSlotCount()
/* 135:    */   {
/* 136:113 */     return 9;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getShiftClickSlotCount()
/* 140:    */   {
/* 141:118 */     return 9;
/* 142:    */   }
/* 143:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_Filter
 * JD-Core Version:    0.7.0.1
 */