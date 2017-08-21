package gregtech.api.gui;

import com.google.common.collect.ImmutableBiMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ModularUI<H> {

    public final ImmutableBiMap<Integer, Widget> guiWidgets;

    public final ResourceLocation backgroundPath;
    public final int width, height;

    /**
     * UIHolder of this modular UI
     * Can be tile entity in world impl or item impl
     */
    public final H holder;
    public final EntityPlayer entityPlayer;

    public ModularUI(ImmutableBiMap<Integer, Widget> guiWidgets, ResourceLocation backgroundPath, int width, int height, H holder, EntityPlayer entityPlayer) {
        this.guiWidgets = guiWidgets;
        this.backgroundPath = backgroundPath;
        this.width = width;
        this.height = height;
        this.holder = holder;
        this.entityPlayer = entityPlayer;
    }

    public void initWidgets() {
        guiWidgets.values().forEach(Widget::initWidget);
    }

    public void writeWidgetData(PacketBuffer dataBuffer) {
        for(int guiWidgetId : guiWidgets.keySet()) {
            dataBuffer.writeInt(guiWidgetId);
            guiWidgets.get(guiWidgetId).writeInitialSyncInfo(dataBuffer);
        }
    }

    @SideOnly(Side.CLIENT)
    public void readWidgetData(PacketBuffer dataBuffer) {
        for(int i = 0; i < guiWidgets.size(); i++) {
            guiWidgets.get(dataBuffer.readInt()).readInitialSyncInfo(dataBuffer);
        }
    }

}
