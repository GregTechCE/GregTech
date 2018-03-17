package gregtech.api.metatileentity;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@link UIFactory} implementation for {@link MetaTileEntity}
 */
public class MetaTileEntityUIFactory extends UIFactory<MetaTileEntity> {

    public static final MetaTileEntityUIFactory INSTANCE = new MetaTileEntityUIFactory();
    private MetaTileEntityUIFactory() {}

    public void init() {
        UIFactory.FACTORY_REGISTRY.register(0, "meta_tile_entity_factory", this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ModularUI<MetaTileEntity> createUITemplate(MetaTileEntity holder, EntityPlayer entityPlayer) {
        return (ModularUI<MetaTileEntity>) (Object) holder.createUI(entityPlayer); //go fuck yourself java ok?
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected MetaTileEntity readHolderFromSyncData(PacketBuffer syncData) {
        return (MetaTileEntity) Minecraft.getMinecraft().world.getTileEntity(syncData.readBlockPos());
    }

    @Override
    protected void writeHolderToSyncData(PacketBuffer syncData, MetaTileEntity holder) {
        syncData.writeBlockPos(holder.getPos());
    }

}
