package gregtech.api.gui.impl;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.ContainerSizeInfo;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.WidgetUIAccess;
import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketUIClientAction;
import gregtech.api.net.PacketUIWidgetUpdate;
import gregtech.api.util.GTUtility;
import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.ChestContainer.IsLargeCallback;
import invtweaks.api.container.ChestContainer.RowSizeCallback;
import invtweaks.api.container.ContainerSection;
import invtweaks.api.container.ContainerSectionCallback;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ChestContainer
public class ModularUIContainer extends Container implements WidgetUIAccess {

    private final HashMap<Slot, INativeWidget> slotMap = new HashMap<>();
    private final ModularUI modularUI;

    public boolean accumulateWidgetUpdateData = false;
    public List<PacketUIWidgetUpdate> accumulatedUpdates = new ArrayList<>();

    public ModularUIContainer(ModularUI modularUI) {
        this.modularUI = modularUI;
        modularUI.guiWidgets.values().forEach(widget -> widget.setUiAccess(this));
        modularUI.guiWidgets.values().stream()
                .flatMap(widget -> widget.getNativeWidgets().stream())
                .forEach(nativeWidget -> {
                    Slot slot = nativeWidget.allocateSlotHandle();
                    slotMap.put(slot, nativeWidget);
                    addSlotToContainer(slot);
                });
    }

    public ModularUI getModularUI() {
        return modularUI;
    }

    @ContainerSectionCallback
    public Map<ContainerSection, List<Slot>> getContainerSections() {
        HashMap<ContainerSection, List<Slot>> result = new HashMap<>();

        List<Slot> containerSlots = slotMap.entrySet().stream()
            .filter(it -> !it.getValue().getSlotLocationInfo().isPlayerInventory)
            .map(Entry::getKey)
            .collect(Collectors.toList());

        List<Pair<Slot, Boolean>> inventorySlots = slotMap.entrySet().stream()
            .filter(it -> !it.getValue().getSlotLocationInfo().isPlayerInventory)
            .map(it -> Pair.of(it.getKey(), it.getValue().getSlotLocationInfo().isHotbarSlot))
            .collect(Collectors.toList());

        result.put(ContainerSection.CHEST, containerSlots);

        result.put(ContainerSection.INVENTORY, inventorySlots.stream()
            .map(Entry::getKey)
            .collect(Collectors.toList()));

        result.put(ContainerSection.INVENTORY_NOT_HOTBAR, inventorySlots.stream()
            .filter(it -> !it.getRight()).map(Entry::getKey)
            .collect(Collectors.toList()));

        result.put(ContainerSection.INVENTORY_HOTBAR, inventorySlots.stream()
            .filter(Pair::getRight).map(Entry::getKey)
            .collect(Collectors.toList()));

        return result;
    }

    @RowSizeCallback
    public int getInventoryRowSize() {
        ContainerSizeInfo sizeInfo = modularUI.getContainerSlotsSizeInfo();
        return sizeInfo == null ? 9 : sizeInfo.rowSize;
    }

    @IsLargeCallback
    public boolean isLargeInventory() {
        ContainerSizeInfo sizeInfo = modularUI.getContainerSlotsSizeInfo();
        return sizeInfo != null && sizeInfo.columns > 0;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        modularUI.triggerCloseListeners();
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        modularUI.guiWidgets.values().forEach(Widget::detectAndSendChanges);
        modularUI.triggerOpenListeners();
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
        if(slotMap.get(slot).getSlotLocationInfo().isPlayerInventory) {
            //if we clicked on player inventory slot, move to container inventory, inverting indexes
            List<Slot> containerSlots = slotMap.entrySet().stream()
                .filter(s -> !s.getValue().getSlotLocationInfo().isPlayerInventory)
                .map(Entry::getKey)
                .sorted(Comparator.comparing(s -> s.slotNumber))
                .collect(Collectors.toList());
            mergedStack = GTUtility.mergeItemStack(remainingStack, containerSlots);
        } else {
            //if we clicked on a container inventory, move to player inventory
            List<Slot> inventorySlots = slotMap.entrySet().stream()
                .filter(s -> s.getValue().getSlotLocationInfo().isPlayerInventory)
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

    @Override
    public void writeClientAction(Widget widget, int updateId, Consumer<PacketBuffer> payloadWriter) {
        int widgetId = modularUI.guiWidgets.inverse().get(widget);
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeVarInt(updateId);
        payloadWriter.accept(packetBuffer);
        if(modularUI.entityPlayer instanceof EntityPlayerSP) {
            PacketUIClientAction widgetUpdate = new PacketUIClientAction(windowId, widgetId, packetBuffer);
            NetworkHandler.channel.sendToServer(NetworkHandler.packet2proxy(widgetUpdate));
        }
    }

    @Override
    public void writeUpdateInfo(Widget widget, int updateId, Consumer<PacketBuffer> payloadWriter) {
        int widgetId = modularUI.guiWidgets.inverse().get(widget);
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeVarInt(updateId);
        payloadWriter.accept(packetBuffer);
        if(modularUI.entityPlayer instanceof EntityPlayerMP) {
            PacketUIWidgetUpdate widgetUpdate = new PacketUIWidgetUpdate(windowId, widgetId, packetBuffer);
            if(!accumulateWidgetUpdateData) {
                NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(widgetUpdate), (EntityPlayerMP) modularUI.entityPlayer);
            } else {
                accumulatedUpdates.add(widgetUpdate);
            }
        }
    }
}
