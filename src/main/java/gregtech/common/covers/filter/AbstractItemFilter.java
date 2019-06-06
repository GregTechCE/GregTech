package gregtech.common.covers.filter;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Consumer;

public abstract class AbstractItemFilter {

    private IUIHolder holder;

    public abstract boolean testItemStack(ItemStack itemStack);

    public abstract int getTotalOccupiedHeight();

    public abstract void initUI(int y, Consumer<Widget> widgetGroup);

    public abstract void writeToNBT(NBTTagCompound tagCompound);

    public abstract void readFromNBT(NBTTagCompound tagCompound);

    public final void setHolder(IUIHolder holder) {
        this.holder = holder;
    }

    public IUIHolder getHolder() {
        return holder;
    }

    public final void markDirty() {
        if(holder != null) {
            holder.markAsDirty();
        }
    }
}
