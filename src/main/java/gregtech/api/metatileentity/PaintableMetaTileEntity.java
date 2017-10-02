package gregtech.api.metatileentity;

import gregtech.api.capability.internal.IPaintable;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public abstract class PaintableMetaTileEntity extends MetaTileEntity implements IPaintable {

    private EnumDyeColor dyeColor = null;

    public PaintableMetaTileEntity(IMetaTileEntityFactory factory) {
        super(factory);
    }

    @Override
    public void saveNBTData(NBTTagCompound data) {
        super.saveNBTData(data);
        if(dyeColor != null) {
            data.setInteger("Color", dyeColor.ordinal());
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound data) {
        super.loadNBTData(data);
        if(data.hasKey("Color", Constants.NBT.TAG_INT)) {
            this.dyeColor = EnumDyeColor.values()[data.getInteger("Color")];
        }
    }

    @Nullable
    @Override
    public EnumDyeColor getColor() {
        return dyeColor;
    }

    @Override
    public void setColor(@Nullable EnumDyeColor color) {
        this.dyeColor = color;
        if(!getWorld().isRemote) {
            markDirty();
            holder.writeCustomData(3, buf -> buf.writeByte(color == null ? -1 : color.ordinal()));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        switch (dataId) {
            case 3:
                int ordinal = buf.readByte();
                this.dyeColor = ordinal == -1 ? null : EnumDyeColor.values()[ordinal];
                markBlockForRenderUpdate();
                break;
            default:
                super.receiveCustomData(dataId, buf);
        }

    }

    @Override
    public void writeInitialData(PacketBuffer buf) {
        super.writeInitialData(buf);
        buf.writeByte(dyeColor == null ? -1 : dyeColor.ordinal());
    }

    @Override
    public void receiveInitialData(PacketBuffer buf) {
        super.receiveInitialData(buf);
        int ordinal = buf.readByte();
        this.dyeColor = ordinal == -1 ? null : EnumDyeColor.values()[ordinal];
    }

}
