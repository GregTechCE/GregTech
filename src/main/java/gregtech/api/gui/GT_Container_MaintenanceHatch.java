package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GT_Container_MaintenanceHatch extends GT_ContainerMetaTile_Machine {

    public GT_Container_MaintenanceHatch(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 0, 80, 35, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex != 0) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        ItemStack tStack = aPlayer.inventory.getItemStack();
        if (tStack != null) {
            ((GT_MetaTileEntity_Hatch_Maintenance) mTileEntity.getMetaTileEntity()).onToolClick(tStack, aPlayer);
            if (tStack.stackSize <= 0) aPlayer.inventory.setItemStack(null);
        }
        return null;
    }
}