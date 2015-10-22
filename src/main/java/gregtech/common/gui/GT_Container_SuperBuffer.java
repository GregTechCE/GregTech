package gregtech.common.gui;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.automation.GT_MetaTileEntity_ChestBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_SuperBuffer
        extends GT_ContainerMetaTile_Machine {
    public GT_Container_SuperBuffer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 8, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 26, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 256, 44, 63, false, true, 1));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 0) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if (tSlot != null) {
            if (this.mTileEntity.getMetaTileEntity() == null) {
                return null;
            }
            if (aSlotIndex == 0) {
                ((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bOutput);
                if (((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bOutput) {
                    GT_Utility.sendChatToPlayer(aPlayer, "Emit Energy to Outputside");
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Energy");
                }
                return null;
            }
            if (aSlotIndex == 1) {
                ((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
                if (((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
                    GT_Utility.sendChatToPlayer(aPlayer, "Emit Redstone if no Slot is free");
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "Don't emit Redstone");
                }
                return null;
            }
            if (aSlotIndex == 2) {
                ((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bInvert);
                if (((GT_MetaTileEntity_ChestBuffer) this.mTileEntity.getMetaTileEntity()).bInvert) {
                    GT_Utility.sendChatToPlayer(aPlayer, "Invert Redstone");
                } else {
                    GT_Utility.sendChatToPlayer(aPlayer, "Don't invert Redstone");
                }
                return null;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    public int getSlotCount() {
        return 0;
    }

    public int getShiftClickSlotCount() {
        return 0;
    }
}
