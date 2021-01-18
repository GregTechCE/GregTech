package gregtech.common.pipelike.inventory.tile;

import javax.annotation.Nullable;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IStorageNetwork;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.AbstractWidgetGroup;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TabGroup;
import gregtech.api.gui.widgets.TabGroup.TabLocation;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.gui.widgets.tab.ItemTabInfo;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.util.Position;
import gregtech.common.gui.widget.ItemListGridWidget;
import gregtech.common.inventory.IItemList;
import gregtech.common.pipelike.inventory.InventoryPipeType;
import gregtech.common.pipelike.inventory.net.InventoryPipeNet;
import gregtech.common.pipelike.inventory.net.WorldInventoryPipeNet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileEntityInventoryPipe extends TileEntityPipeBase<InventoryPipeType, EmptyNodeData> {

    @Override
    public Class<InventoryPipeType> getPipeTypeClass() {
        return InventoryPipeType.class;
    }

    @Override
    public boolean supportsTicking() {
        return false;
    }

    IStorageNetwork getStorageNetwork() {
        World world = getPipeWorld();
        if (world == null || world.isRemote)
            return null;
        InventoryPipeNet pipeNet = WorldInventoryPipeNet.getWorldPipeNet(world).getNetFromPos(getPos());
        if (pipeNet == null)
            return null;
        return pipeNet.getStorageNetwork();
    }
    
    @Nullable
    @Override
    public <T> T getCapabilityInternal(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == GregtechCapabilities.CAPABILITY_STORAGE_NETWORK) {
            return GregtechCapabilities.CAPABILITY_STORAGE_NETWORK.cast(getStorageNetwork());
        }
        return super.getCapabilityInternal(capability, facing);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return true;
    }

    private AbstractWidgetGroup createItemListTab() {
        WidgetGroup widgetGroup = new WidgetGroup();
        widgetGroup.addWidget(new LabelWidget(5, 20, "gregtech.pipe.inventory.storage_note_1"));
        IItemList storageNetwork = null;
        World world = getPipeWorld();
        if (!world.isRemote) {
            WorldInventoryPipeNet worldPipeNet = WorldInventoryPipeNet.getWorldPipeNet(world);
            InventoryPipeNet pipeNet = worldPipeNet.getNetFromPos(getPipePos());
            storageNetwork = pipeNet == null ? null : pipeNet.getStorageNetwork();
        }
        widgetGroup.addWidget(new ItemListGridWidget(2, 45, 9, 5, storageNetwork));
        return widgetGroup;
    }

    @Override
    public ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.builder(GuiTextures.BORDERED_BACKGROUND, 176, 221)
            .bindPlayerInventory(entityPlayer.inventory, 140);
        builder.label(5, 5, "gregtech.inventory_pipe.name");

        TabGroup tabGroup = new TabGroup(TabLocation.HORIZONTAL_TOP_LEFT, Position.ORIGIN);
        tabGroup.addTab(new ItemTabInfo("gregtech.pipe.inventory.tab.storage_network", new ItemStack(Blocks.CHEST)), createItemListTab());
        builder.widget(tabGroup);

        return builder.build(this, entityPlayer);
    }
}
