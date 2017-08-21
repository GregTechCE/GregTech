package gregtech.api.metatileentity;

import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@link UIFactory} implementation for {@link IGregTechTileEntity}
 */
public class MetaTileEntityUIFactory extends UIFactory<IGregTechTileEntity> {

    public static final MetaTileEntityUIFactory INSTANCE = new MetaTileEntityUIFactory();
    private MetaTileEntityUIFactory() {}

    public void init() {
        UIFactory.FACTORY_REGISTRY.register(0, "meta_tile_entity_factory", this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ModularUI<IGregTechTileEntity> createUITemplate(IGregTechTileEntity holder, EntityPlayer entityPlayer) {
        return (ModularUI<IGregTechTileEntity>) holder.getMetaTileEntity().createUI(entityPlayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IGregTechTileEntity readHolderFromSyncData(PacketBuffer syncData) {
        return (IGregTechTileEntity) Minecraft.getMinecraft().theWorld.getTileEntity(syncData.readBlockPos());
    }

    @Override
    protected void writeHolderToSyncData(PacketBuffer syncData, IGregTechTileEntity holder) {
        syncData.writeBlockPos(holder.getWorldPos());
    }

}
