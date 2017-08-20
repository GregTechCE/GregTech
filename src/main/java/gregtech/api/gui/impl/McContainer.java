package gregtech.api.gui.impl;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.ModularUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class McContainer extends Container {

    private final ModularUI<?> modularUI;

    public McContainer(ModularUI<?> modularUI) {
        this.modularUI = modularUI;
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget instanceof INativeWidget)
                .map(widget -> ((INativeWidget) widget).getNativeHandle())
                .forEach(this::addSlotToContainer);
    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return super.canMergeSlot(stack, slotIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
