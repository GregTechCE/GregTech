package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class MTETrait {

    protected MetaTileEntity metaTileEntity;
    protected int index;

    public void setMetaTileEntityAndIndex(MetaTileEntity metaTileEntity, int index) {
        this.metaTileEntity = metaTileEntity;
        this.index = index;
    }

    public MetaTileEntity getMetaTileEntity() {
        return metaTileEntity;
    }

    public abstract String getName();

    /**
     * Returns a capability that this trait is implementing
     * May return null to indicate that this trait does not implement any capability
     * if you return any capability here, you should implement it's interface
     * @return implemented capability
     */
    public abstract @Nullable Capability<?> getImplementingCapability();

    public void onFrontFacingSet(EnumFacing newFrontFacing) {
    }

    public void update() {
    }

    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    public void deserializeNBT(NBTTagCompound compound) {
    }

    public void writeInitialData(PacketBuffer buffer) {
    }

    public void receiveInitialData(PacketBuffer buffer) {
    }

    public void receiveCustomData(int id, PacketBuffer buffer) {
    }

    public final void writeCustomData(int id, Consumer<PacketBuffer> writer) {
        Preconditions.checkElementIndex(id, 100, "Only 0-100 sync ids allowed");
        metaTileEntity.writeCustomData(index * 100 + id, writer);
    }

    final void readSyncData(int id, PacketBuffer buffer) {
        if(id >= index * 100 && id < (index + 1) * 100) {
            receiveCustomData(id - index * 100, buffer);
        }
    }

}
