package gregtech.api.pipenet.tile;

import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PipeTileUIFactory<T extends IPipeTile> extends UIFactory<T> {
    
    public static final PipeTileUIFactory INSTANCE = new PipeTileUIFactory();

    private PipeTileUIFactory() {
    }

    public void init() {
        UIFactory.FACTORY_REGISTRY.register(666, new ResourceLocation(GTValues.MODID, "pipe_tile_factory"), this);
    }

    @Override
    protected ModularUI createUITemplate(T pipe, EntityPlayer entityPlayer) {
        return pipe.createUI(entityPlayer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected T readHolderFromSyncData(PacketBuffer syncData) {
        return (T) Minecraft.getMinecraft().world.getTileEntity(syncData.readBlockPos());
    }

    @Override
    protected void writeHolderToSyncData(PacketBuffer syncData, T pipe) {
        syncData.writeBlockPos(pipe.getPipePos());
    }

}
