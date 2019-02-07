package gregtech.api.metatileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class MTETrait {

    protected MetaTileEntity metaTileEntity;

    public MTETrait(MetaTileEntity metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
        metaTileEntity.addMetaTileEntityTrait(this);
    }

    public MetaTileEntity getMetaTileEntity() {
        return metaTileEntity;
    }

    public abstract String getName();

    public abstract int getNetworkID();

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
        metaTileEntity.writeTraitData(this, id, writer);
    }

    protected static final class TraitNetworkIds {
        public static final int TRAIT_ID_ENERGY_CONTAINER = 1;
        public static final int TRAIT_ID_WORKABLE = 2;
    }

}
