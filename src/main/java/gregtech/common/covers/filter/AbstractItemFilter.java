package gregtech.common.covers.filter;

import gregtech.api.gui.Widget;
import gregtech.api.util.IDirtyNotifiable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Consumer;

public abstract class AbstractItemFilter {

    private IDirtyNotifiable dirtyNotifiable;

    public abstract boolean testItemStack(ItemStack itemStack);

    public abstract int getTotalOccupiedHeight();

    public abstract void initUI(int y, Consumer<Widget> widgetGroup);

    public abstract void writeToNBT(NBTTagCompound tagCompound);

    public abstract void readFromNBT(NBTTagCompound tagCompound);

    final void setDirtyNotifiable(IDirtyNotifiable dirtyNotifiable) {
        this.dirtyNotifiable = dirtyNotifiable;
    }

    public final void markDirty() {
        if(dirtyNotifiable != null) {
            dirtyNotifiable.markAsDirty();
        }
    }
}
