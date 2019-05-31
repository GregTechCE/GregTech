package gregtech.common.covers.filter;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Consumer;

public abstract class AbstractItemFilter {

    private IUIHolder holder;

    public abstract int getMaxMatchSlots();

    /**
     * @return number in [0..maxMatchSlots) bounds, presuming match slot of item stack,
     * or -1 if it didn't match. Match slots are used for advanced supplying
     * techniques like robotic arm modes, but are mostly ignored for other covers
     */
    public abstract int matchItemStack(ItemStack itemStack);

    /**
     * @return total occupied height
     */
    public abstract int initUI(int y, Consumer<Widget> widgetGroup);

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
