package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler.IEnergyChangeListener;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SimpleTextWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.SimpleOverlayRenderer;
import gregtech.api.render.Textures;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityItemCollector extends TieredMetaTileEntity implements IEnergyChangeListener {

    private static final int[] INVENTORY_SIZES = {1, 4, 9, 16, 25};
    private static final double MOTION_MULTIPLIER = 0.04;
    private static final int EU_PER_ITEM_MOVE = 4;

    private final int maxItemSuckingRange;
    private int itemSuckingRange;
    private AxisAlignedBB areaBoundingBox;
    private BlockPos areaCenterPos;
    private boolean hasElectricCharge;

    public MetaTileEntityItemCollector(ResourceLocation metaTileEntityId, int tier, int maxItemSuckingRange) {
        super(metaTileEntityId, tier);
        this.maxItemSuckingRange = maxItemSuckingRange;
        this.itemSuckingRange = maxItemSuckingRange;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityItemCollector(metaTileEntityId, getTier(), maxItemSuckingRange);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        SimpleOverlayRenderer renderer = hasElectricCharge ? Textures.BLOWER_ACTIVE_OVERLAY : Textures.BLOWER_OVERLAY;
        renderer.renderSided(EnumFacing.UP, renderState, translation, pipeline);
        Textures.AIR_VENT_OVERLAY.renderSided(EnumFacing.DOWN, renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public void onEnergyChanged(IEnergyContainer container, boolean isInitialChange) {
        super.onEnergyChanged(container, isInitialChange);
        boolean hasElectricChargeNow = container.getEnergyStored() >= EU_PER_ITEM_MOVE;
        if(hasElectricCharge != hasElectricChargeNow) {
            this.hasElectricCharge = hasElectricChargeNow;
            if(!isInitialChange) writeCustomData(100, buffer -> buffer.writeBoolean(hasElectricChargeNow));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(hasElectricCharge);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.hasElectricCharge = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == 100) hasElectricCharge = buf.readBoolean();
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote) {
            BlockPos selfPos = getPos();
            if(areaCenterPos == null || areaBoundingBox == null || areaCenterPos.getX() != selfPos.getX() ||
                areaCenterPos.getZ() != selfPos.getZ() || areaCenterPos.getY() != selfPos.getY() + 1) {
                this.areaCenterPos = selfPos.up();
                this.areaBoundingBox = new AxisAlignedBB(areaCenterPos).grow(itemSuckingRange, 1.0, itemSuckingRange);
            }
            List<EntityItem> itemsInRange = getWorld().getEntitiesWithinAABB(EntityItem.class, areaBoundingBox);
            int maxItemEntitiesSucked = (int) (energyContainer.getEnergyStored() / EU_PER_ITEM_MOVE);
            int itemEntitiesSucked = 0;
            for(EntityItem entityItem : itemsInRange) {
                double distanceX = (areaCenterPos.getX() + 0.5) - entityItem.posX;
                double distanceZ = (areaCenterPos.getZ() + 0.5) - entityItem.posZ;
                double distance = MathHelper.sqrt(distanceX * distanceX + distanceZ * distanceZ);
                if(distance >= 0.7) {
                    double directionX = distanceX / distance;
                    double directionZ = distanceZ / distance;
                    entityItem.motionX = directionX * MOTION_MULTIPLIER * (getTier() - 1);
                    entityItem.motionZ = directionZ * MOTION_MULTIPLIER * (getTier() - 1);
                    entityItem.velocityChanged = true;
                    itemEntitiesSucked++;
                } else {
                    ItemStack itemStack = entityItem.getItem();
                    ItemStack remainder = ItemHandlerHelper.insertItemStacked(exportItems, itemStack, false);
                    if(remainder.isEmpty()) {
                        entityItem.setDead();
                        itemEntitiesSucked++;
                    } else if(itemStack.getCount() > remainder.getCount()) {
                        entityItem.setItem(remainder);
                        itemEntitiesSucked++;
                    }
                }
                if(itemEntitiesSucked >= maxItemEntitiesSucked) {
                    break;
                }
            }
            if(itemEntitiesSucked > 0) {
                long totalEnergy = itemEntitiesSucked * EU_PER_ITEM_MOVE;
                energyContainer.addEnergy(-totalEnergy);
            }
            if(getTimer() % 5 == 0) {
                pushItemsIntoNearbyHandlers(getFrontFacing());
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.item_collector.collect_range", maxItemSuckingRange, maxItemSuckingRange));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(), GTValues.VN[getTier()]));
        tooltip.add(I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(INVENTORY_SIZES[MathHelper.clamp(getTier(), 0, INVENTORY_SIZES.length - 1)]);
    }

    @Override
    public boolean canPlaceCoverOnSide(EnumFacing side) {
        return side != EnumFacing.DOWN && side != EnumFacing.UP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        return canPlaceCoverOnSide(side) ? super.getCapability(capability, side) : null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("CollectRange", itemSuckingRange);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.itemSuckingRange = data.getInteger("CollectRange");
    }

    protected void setItemSuckingRange(int itemSuckingRange) {
        this.itemSuckingRange = itemSuckingRange;
        this.areaBoundingBox = null;
        markDirty();
    }

    protected void adjustSuckingRange(int amount) {
        setItemSuckingRange(MathHelper.clamp(itemSuckingRange + amount, 1, maxItemSuckingRange));
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(exportItems.getSlots());
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
            18 + 25 + 18 * rowSize + 94)
            .label(10, 5, getMetaFullName());

        builder.widget(new ClickButtonWidget(10, 20, 20, 20, "-1", data -> adjustSuckingRange(-1)));
        builder.widget(new ClickButtonWidget(146, 20, 20, 20, "+1", data -> adjustSuckingRange(+1)));
        builder.widget(new ImageWidget(30, 20, 116, 20, GuiTextures.DISPLAY));
        builder.widget(new SimpleTextWidget(88, 30, "gregtech.machine.item_collector.gui.collect_range", 0xFFFFFF, () -> Integer.toString(itemSuckingRange)));

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < rowSize; x++) {
                int index = y * rowSize + x;
                builder.widget(new SlotWidget(exportItems, index, 89 - rowSize * 9 + x * 18, 45 + y * 18, true, false)
                    .setBackgroundTexture(GuiTextures.SLOT));
            }
        }
        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 45 + 18 * rowSize + 12);
        return builder.build(getHolder(), entityPlayer);
    }
}
