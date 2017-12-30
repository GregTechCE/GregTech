package gregtech.api.gui;

import gregtech.api.gui.impl.ModularUIContainer;
import gregtech.api.gui.impl.ModularUIGui;
import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketUIOpen;
import gregtech.api.util.GTControlledRegistry;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implement and register to {@link #FACTORY_REGISTRY} to be able to create and open ModularUI's
 * createUITemplate should return equal gui both on server and client side, or sync will break!
 * @param <E> UI holder type
 */
public abstract class UIFactory<E extends IUIHolder> {

    public static final GTControlledRegistry<UIFactory<?>> FACTORY_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);

    public final void openUI(E holder, EntityPlayerMP player) {
        if (player instanceof FakePlayer) {
            return;
        }

        ModularUI<E> uiTemplate = createUITemplate(holder, player);
        uiTemplate.initWidgets();
        if (player.openContainer != player.inventoryContainer) {
            player.closeScreen();
        }
        player.getNextWindowId();

        PacketBuffer serializedHolder = new PacketBuffer(Unpooled.buffer());
        writeHolderToSyncData(serializedHolder, holder);
        PacketBuffer widgetsInitData = new PacketBuffer(Unpooled.buffer());
        uiTemplate.writeWidgetData(widgetsInitData);
        int uiFactoryId = FACTORY_REGISTRY.getIDForObject(this);
        PacketUIOpen packet = new PacketUIOpen(uiFactoryId, serializedHolder, widgetsInitData, player.currentWindowId);
        NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(packet), player);
        player.openContainer = new ModularUIContainer(uiTemplate);
        player.openContainer.windowId = player.currentWindowId;
        player.openContainer.addListener(player);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.openContainer));
    }

    @SideOnly(Side.CLIENT)
    public final void initClientUI(PacketBuffer serializedHolder, PacketBuffer widgetsInitData, int windowId) {
        E holder = readHolderFromSyncData(serializedHolder);
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayerSP entityPlayer = minecraft.player;
        ModularUI<E> uiTemplate = createUITemplate(holder, entityPlayer);
        uiTemplate.readWidgetData(widgetsInitData);
        uiTemplate.initWidgets();
        minecraft.addScheduledTask(() -> {
            minecraft.displayGuiScreen(new ModularUIGui(uiTemplate));
            entityPlayer.openContainer.windowId = windowId;
        });
    }

    protected abstract ModularUI<E> createUITemplate(E holder, EntityPlayer entityPlayer);

    @SideOnly(Side.CLIENT)
    protected abstract E readHolderFromSyncData(PacketBuffer syncData);
    protected abstract void writeHolderToSyncData(PacketBuffer syncData, E holder);


}
