package gregtech.api.gui.impl;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ModularUIContainer extends Container {

    private final HashMap<Slot, INativeWidget> slotMap = new HashMap<>();
    private final ModularUI modularUI;

    public ModularUIContainer(ModularUI modularUI) {
        this.modularUI = modularUI;
        modularUI.guiWidgets.values().stream()
                .filter(widget -> widget instanceof INativeWidget)
                .map(widget -> ((INativeWidget) widget))
                .forEach(nativeWidget -> {
                    Slot slot = nativeWidget.allocateSlotHandle();
                    slotMap.put(slot, nativeWidget);
                    addSlotToContainer(slot);
                });
    }

    public ModularUI getModularUI() {
        return modularUI;
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        modularUI.guiWidgets.values().forEach(Widget::detectAndSendChanges);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        modularUI.guiWidgets.values().forEach(Widget::detectAndSendChanges);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId >= 0 && slotId < inventorySlots.size()) {
            Slot slot = getSlot(slotId);
            ItemStack result = slotMap.get(slot).slotClick(dragType, clickTypeIn, player);
            if(result == INativeWidget.VANILLA_LOGIC) {
                return super.slotClick(slotId, dragType, clickTypeIn, player);
            }
            return result;
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if(slot == null || !slot.getHasStack()) {
            //return empty if we can't transfer it
            return ItemStack.EMPTY;
        }
        ItemStack remainingStack = slot.getStack();
        boolean mergedStack;
        if(slotMap.get(slot).isPlayerInventorySlot()) {
            //if we clicked on player inventory slot, move to container inventory, inverting indexes
            List<Slot> containerSlots = slotMap.entrySet().stream()
                .filter(s -> !s.getValue().isPlayerInventorySlot())
                .map(Entry::getKey)
                .sorted(Comparator.comparing(s -> s.slotNumber))
                .collect(Collectors.toList());
            mergedStack = GTUtility.mergeItemStack(remainingStack, containerSlots);
        } else {
            //if we clicked on a container inventory, move to player inventory
            List<Slot> inventorySlots = slotMap.entrySet().stream()
                .filter(s -> s.getValue().isPlayerInventorySlot())
                .map(Entry::getKey)
                .sorted(Collections.reverseOrder(Comparator.comparing(s -> s.slotNumber)))
                .collect(Collectors.toList());
            mergedStack = GTUtility.mergeItemStack(remainingStack, inventorySlots);
        }
        if(!mergedStack) {
            return ItemStack.EMPTY; //if we didn't merge anything, return empty stack
        }

        if (remainingStack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        return remainingStack;
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
