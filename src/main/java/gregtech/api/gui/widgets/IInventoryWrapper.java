package gregtech.api.gui.widgets;

import gregtech.api.capability.internal.ISimpleSlotInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

/**
 * Just an IInventory wrapper to pass to Slot instances,
 * since vanilla is too dumb to handle slots with null inventory properly
 */
public class IInventoryWrapper implements IInventory {

    public final ISimpleSlotInventory inventory;

    public IInventoryWrapper(ISimpleSlotInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSlotsCount();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory.getStackInSlot(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stackInSlot = inventory.getStackInSlot(index);
        if(stackInSlot == null) {
            return null;
        }
        ItemStack result = stackInSlot.splitStack(count);
        inventory.setStackInSlot(index, stackInSlot);
        return result;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = inventory.getStackInSlot(index);
        inventory.setStackInSlot(index, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        inventory.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.getSlotsCount(); i++) {
            inventory.setStackInSlot(i, null);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64; //Handled by Slot implementation
    }

    @Override
    public void markDirty() {
        //Handled by ISimpleInventory implementation
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true; //Handled by ModularUI implementation
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        //NOOP
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        //NOOP
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false; //Handled by ISimpleInventory implementation
    }

    @Override
    public int getField(int id) {
        return 0; //NOOP
    }

    @Override
    public void setField(int id, int value) {
        //NOOP
    }

    @Override
    public int getFieldCount() {
        return 0; //NOOP
    }

    @Override
    public String getName() {
        return ""; //Handled by ModularUI implementation
    }

    @Override
    public boolean hasCustomName() {
        return false; //Handled by ModularUI implementation
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(""); //Handled by ModularUI implementation
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IInventoryWrapper && ((IInventoryWrapper) obj).inventory == inventory;
    }

}
