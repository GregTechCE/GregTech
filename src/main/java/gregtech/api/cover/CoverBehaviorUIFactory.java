package gregtech.api.cover;

import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CoverBehaviorUIFactory extends UIFactory<CoverBehavior> {

    public static final CoverBehaviorUIFactory INSTANCE = new CoverBehaviorUIFactory();
    private CoverBehaviorUIFactory() {}

    public void init() {
        UIFactory.FACTORY_REGISTRY.register(2, new ResourceLocation(GTValues.MODID, "cover_behavior_factory"), this);
    }

    @Override
    protected ModularUI createUITemplate(CoverBehavior holder, EntityPlayer entityPlayer) {
        return ((CoverWithUI) holder).createUI(entityPlayer);
    }

    @Override
    protected CoverBehavior readHolderFromSyncData(PacketBuffer syncData) {
        BlockPos blockPos = syncData.readBlockPos();
        EnumFacing attachedSide = EnumFacing.VALUES[syncData.readByte()];
        MetaTileEntityHolder holder = (MetaTileEntityHolder) Minecraft.getMinecraft().world.getTileEntity(blockPos);
        if (holder != null) {
            MetaTileEntity metaTileEntity = holder.getMetaTileEntity();
            return metaTileEntity.getCoverAtSide(attachedSide);
        }
        return null;
    }

    @Override
    protected void writeHolderToSyncData(PacketBuffer syncData, CoverBehavior holder) {
        syncData.writeBlockPos(holder.metaTileEntity.getPos());
        syncData.writeByte(holder.attachedSide.ordinal());
    }
}
