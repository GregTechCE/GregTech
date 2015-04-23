/*   1:    */ package gregtech.common.gui;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*   6:    */ import gregtech.api.gui.GT_Slot_Holo;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.util.GT_Utility;
/*   9:    */ import gregtech.common.tileentities.automation.GT_MetaTileEntity_Regulator;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  14:    */ import net.minecraft.inventory.ICrafting;
/*  15:    */ import net.minecraft.inventory.Slot;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ 
/*  18:    */ public class GT_Container_Regulator
/*  19:    */   extends GT_ContainerMetaTile_Machine
/*  20:    */ {
/*  21:    */   public GT_Container_Regulator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/*  22:    */   {
/*  23: 22 */     super(aInventoryPlayer, aTileEntity);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void addSlots(InventoryPlayer aInventoryPlayer)
/*  27:    */   {
/*  28: 27 */     this.mTargetSlots = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*  29:    */     
/*  30: 29 */     addSlotToContainer(new Slot(this.mTileEntity, 0, 8, 6));
/*  31: 30 */     addSlotToContainer(new Slot(this.mTileEntity, 1, 26, 6));
/*  32: 31 */     addSlotToContainer(new Slot(this.mTileEntity, 2, 44, 6));
/*  33: 32 */     addSlotToContainer(new Slot(this.mTileEntity, 3, 8, 24));
/*  34: 33 */     addSlotToContainer(new Slot(this.mTileEntity, 4, 26, 24));
/*  35: 34 */     addSlotToContainer(new Slot(this.mTileEntity, 5, 44, 24));
/*  36: 35 */     addSlotToContainer(new Slot(this.mTileEntity, 6, 8, 42));
/*  37: 36 */     addSlotToContainer(new Slot(this.mTileEntity, 7, 26, 42));
/*  38: 37 */     addSlotToContainer(new Slot(this.mTileEntity, 8, 44, 42));
/*  39:    */     
/*  40: 39 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 9, 64, 7, false, true, 1));
/*  41: 40 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 81, 7, false, true, 1));
/*  42: 41 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 11, 98, 7, false, true, 1));
/*  43: 42 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 12, 64, 24, false, true, 1));
/*  44: 43 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 13, 81, 24, false, true, 1));
/*  45: 44 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 14, 98, 24, false, true, 1));
/*  46: 45 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 15, 64, 41, false, true, 1));
/*  47: 46 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 16, 81, 41, false, true, 1));
/*  48: 47 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 17, 98, 41, false, true, 1));
/*  49:    */     
/*  50: 49 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 7, false, true, 1));
/*  51: 50 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 7, false, true, 1));
/*  52: 51 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 7, false, true, 1));
/*  53: 52 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 24, false, true, 1));
/*  54: 53 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 24, false, true, 1));
/*  55: 54 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 24, false, true, 1));
/*  56: 55 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 41, false, true, 1));
/*  57: 56 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 41, false, true, 1));
/*  58: 57 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 41, false, true, 1));
/*  59:    */     
/*  60: 59 */     addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 8, 63, false, true, 1));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer)
/*  64:    */   {
/*  65: 66 */     if (aSlotIndex < 9) {
/*  66: 66 */       return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/*  67:    */     }
/*  68: 67 */     Slot tSlot = (Slot)this.inventorySlots.get(aSlotIndex);
/*  69: 68 */     if (tSlot != null)
/*  70:    */     {
/*  71: 69 */       if (this.mTileEntity.getMetaTileEntity() == null) {
/*  72: 69 */         return null;
/*  73:    */       }
/*  74: 70 */       if (aSlotIndex == 27)
/*  75:    */       {
/*  76: 71 */         ((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).bOutput);
/*  77: 72 */         if (((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).bOutput) {
/*  78: 73 */           GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
/*  79:    */         } else {
/*  80: 75 */           GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
/*  81:    */         }
/*  82: 76 */         return null;
/*  83:    */       }
/*  84: 77 */       if ((aSlotIndex >= 9) && (aSlotIndex < 18))
/*  85:    */       {
/*  86: 78 */         ItemStack tStack = aPlayer.inventory.getItemStack();
/*  87: 79 */         if (tStack != null) {
/*  88: 80 */           tSlot.putStack(GT_Utility.copy(new Object[] { tStack }));
/*  89: 82 */         } else if (tSlot.getStack() != null) {
/*  90: 83 */           if (aMouseclick == 0)
/*  91:    */           {
/*  92: 84 */             tSlot.getStack().stackSize -= (aShifthold == 1 ? 8 : 1);
/*  93: 85 */             if (tSlot.getStack().stackSize <= 0) {
/*  94: 86 */               tSlot.putStack(null);
/*  95:    */             }
/*  96:    */           }
/*  97:    */           else
/*  98:    */           {
/*  99: 89 */             tSlot.getStack().stackSize += (aShifthold == 1 ? 8 : 1);
/* 100: 90 */             if (tSlot.getStack().stackSize > tSlot.getStack().getMaxStackSize()) {
/* 101: 91 */               tSlot.getStack().stackSize = tSlot.getStack().getMaxStackSize();
/* 102:    */             }
/* 103:    */           }
/* 104:    */         }
/* 105: 96 */         return null;
/* 106:    */       }
/* 107: 97 */       if ((aSlotIndex >= 18) && (aSlotIndex < 27))
/* 108:    */       {
/* 109: 98 */         ((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).mTargetSlots[(aSlotIndex - 18)] = Math.min(99, Math.max(0, ((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).mTargetSlots[(aSlotIndex - 18)] + (aMouseclick == 0 ? -1 : 1) * (aShifthold == 0 ? 1 : 16)));
/* 110: 99 */         return null;
/* 111:    */       }
/* 112:    */     }
/* 113:102 */     return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
/* 114:    */   }
/* 115:    */   
/* 116:105 */   public int[] mTargetSlots = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/* 117:    */   
/* 118:    */   public void detectAndSendChanges()
/* 119:    */   {
/* 120:109 */     super.detectAndSendChanges();
/* 121:110 */     if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
/* 122:110 */       return;
/* 123:    */     }
/* 124:111 */     this.mTargetSlots = new int[9];
/* 125:112 */     for (int i = 0; i < 9; i++) {
/* 126:112 */       this.mTargetSlots[i] = ((GT_MetaTileEntity_Regulator)this.mTileEntity.getMetaTileEntity()).mTargetSlots[i];
/* 127:    */     }
/* 128:114 */     Iterator var2 = this.crafters.iterator();
/* 129:115 */     while (var2.hasNext())
/* 130:    */     {
/* 131:116 */       ICrafting var1 = (ICrafting)var2.next();
/* 132:117 */       for (int i = 0; i < 9; i++) {
/* 133:117 */         var1.sendProgressBarUpdate(this, 100 + i, this.mTargetSlots[i]);
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   @SideOnly(Side.CLIENT)
/* 139:    */   public void updateProgressBar(int par1, int par2)
/* 140:    */   {
/* 141:124 */     super.updateProgressBar(par1, par2);
/* 142:125 */     switch (par1)
/* 143:    */     {
/* 144:    */     case 100: 
/* 145:126 */       this.mTargetSlots[0] = par2; break;
/* 146:    */     case 101: 
/* 147:127 */       this.mTargetSlots[1] = par2; break;
/* 148:    */     case 102: 
/* 149:128 */       this.mTargetSlots[2] = par2; break;
/* 150:    */     case 103: 
/* 151:129 */       this.mTargetSlots[3] = par2; break;
/* 152:    */     case 104: 
/* 153:130 */       this.mTargetSlots[4] = par2; break;
/* 154:    */     case 105: 
/* 155:131 */       this.mTargetSlots[5] = par2; break;
/* 156:    */     case 106: 
/* 157:132 */       this.mTargetSlots[6] = par2; break;
/* 158:    */     case 107: 
/* 159:133 */       this.mTargetSlots[7] = par2; break;
/* 160:    */     case 108: 
/* 161:134 */       this.mTargetSlots[8] = par2;
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getSlotCount()
/* 166:    */   {
/* 167:140 */     return 9;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getShiftClickSlotCount()
/* 171:    */   {
/* 172:145 */     return 9;
/* 173:    */   }
/* 174:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_Regulator
 * JD-Core Version:    0.7.0.1
 */