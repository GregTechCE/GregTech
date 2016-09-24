package gregtech.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.automation.GT_MetaTileEntity_Regulator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

public class GT_Container_Regulator
        extends GT_ContainerMetaTile_Machine {
    public int[] mTargetSlots = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    public GT_Container_Regulator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        this.mTargetSlots = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};

        addSlotToContainer(new Slot(this.mTileEntity, 0, 8, 6));
        addSlotToContainer(new Slot(this.mTileEntity, 1, 26, 6));
        addSlotToContainer(new Slot(this.mTileEntity, 2, 44, 6));
        addSlotToContainer(new Slot(this.mTileEntity, 3, 8, 24));
        addSlotToContainer(new Slot(this.mTileEntity, 4, 26, 24));
        addSlotToContainer(new Slot(this.mTileEntity, 5, 44, 24));
        addSlotToContainer(new Slot(this.mTileEntity, 6, 8, 42));
        addSlotToContainer(new Slot(this.mTileEntity, 7, 26, 42));
        addSlotToContainer(new Slot(this.mTileEntity, 8, 44, 42));

        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 9, 64, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 10, 81, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 11, 98, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 12, 64, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 13, 81, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 14, 98, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 15, 64, 41, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 16, 81, 41, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 17, 98, 41, false, true, 1));

        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 7, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 24, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 119, 41, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 136, 41, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 153, 41, false, true, 1));

        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 18, 8, 63, false, true, 1));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 9) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if (tSlot != null) {
            if (this.mTileEntity.getMetaTileEntity() == null) {
                return null;
            }
            if (aSlotIndex == 27) {
                ((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).bOutput);
                if (((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).bOutput) {
                    GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
                }
                return null;
            }
            if ((aSlotIndex < 18)) {
                ItemStack tStack = aPlayer.inventory.getItemStack();
                if (tStack != null) {
                    tSlot.putStack(GT_Utility.copy(new Object[]{tStack}));
                } else if (tSlot.getStack() != null) {
                    if (aMouseclick == 0) {
                        tSlot.getStack().stackSize -= (aShifthold == 1 ? 8 : 1);
                        if (tSlot.getStack().stackSize <= 0) {
                            tSlot.putStack(null);
                        }
                    } else {
                        tSlot.getStack().stackSize += (aShifthold == 1 ? 8 : 1);
                        if (tSlot.getStack().stackSize > tSlot.getStack().getMaxStackSize()) {
                            tSlot.getStack().stackSize = tSlot.getStack().getMaxStackSize();
                        }
                    }
                }
                return null;
            }
            if ((aSlotIndex < 27)) {
                ((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).mTargetSlots[(aSlotIndex - 18)] = Math.min(99, Math.max(0, ((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).mTargetSlots[(aSlotIndex - 18)] + (aMouseclick == 0 ? -1 : 1) * (aShifthold == 0 ? 1 : 16)));
                return null;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
            return;
        }
        this.mTargetSlots = new int[9];
        for (int i = 0; i < 9; i++) {
            this.mTargetSlots[i] = ((GT_MetaTileEntity_Regulator) this.mTileEntity.getMetaTileEntity()).mTargetSlots[i];
        }
        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting) var2.next();
            for (int i = 0; i < 9; i++) {
                var1.sendProgressBarUpdate(this, 100 + i, this.mTargetSlots[i]);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                this.mTargetSlots[0] = par2;
                break;
            case 101:
                this.mTargetSlots[1] = par2;
                break;
            case 102:
                this.mTargetSlots[2] = par2;
                break;
            case 103:
                this.mTargetSlots[3] = par2;
                break;
            case 104:
                this.mTargetSlots[4] = par2;
                break;
            case 105:
                this.mTargetSlots[5] = par2;
                break;
            case 106:
                this.mTargetSlots[6] = par2;
                break;
            case 107:
                this.mTargetSlots[7] = par2;
                break;
            case 108:
                this.mTargetSlots[8] = par2;
        }
    }

    public int getSlotCount() {
        return 9;
    }

    public int getShiftClickSlotCount() {
        return 9;
    }
}
