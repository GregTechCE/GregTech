package gregtech.api.gui.impl;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ModularUIContainer extends Container {

    private final HashMap<Slot, INativeWidget> slotMap = new HashMap<>();

    public ModularUIContainer(ModularUI<?> modularUI) {
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget instanceof INativeWidget)
                .map(widget -> ((INativeWidget) widget))
                .forEach(nativeWidget -> {
                    Slot slot = nativeWidget.allocateSlotHandle();
                    slotMap.put(slot, nativeWidget);
                    addSlotToContainer(slot);
                });
    }

    @Nullable
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        Slot slot = getSlot(slotId);
        ItemStack result = slotMap.get(slot).slotClick(dragType, clickTypeIn, player);
        if(result == INativeWidget.VANILLA_LOGIC) {
            return super.slotClick(slotId, dragType, clickTypeIn, player);
        }
        return result;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotMap.get(slotIn).canMergeSlot(stack);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
