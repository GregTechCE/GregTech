package gregtech.common.metatileentities.electric;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.*;
import gregtech.api.gui.widgets.tab.ItemTabInfo;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.BlockUtility;
import gregtech.api.util.GregFakePlayer;
import gregtech.common.covers.filter.ItemFilterContainer;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityBlockBreaker extends TieredMetaTileEntity {

    private EnumFacing outputFacing;
    private int breakProgressTicksLeft;
    private float currentBlockHardness;
    private final ItemFilterContainer itemFilterContainer;

    public MetaTileEntityBlockBreaker(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        initializeInventory();
        this.itemFilterContainer = new ItemFilterContainer(this::markDirty);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityBlockBreaker(metaTileEntityId, getTier());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.ROCK_CRUSHER_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getOutputFacing(), renderState, translation, pipeline);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote && getTimer() % 5 == 0) {
            pushItemsIntoNearbyHandlers(getOutputFacing());
        }
        if(!getWorld().isRemote) {
            if(breakProgressTicksLeft > 0) {
                --this.breakProgressTicksLeft;
                if(breakProgressTicksLeft == 0 && energyContainer.getEnergyStored() >= getEnergyPerBlockBreak()) {
                    BlockPos blockPos = getPos().offset(getFrontFacing());
                    IBlockState blockState = getWorld().getBlockState(blockPos);
                    EntityPlayer entityPlayer = GregFakePlayer.get((WorldServer) getWorld());
                    float hardness = blockState.getBlockHardness(getWorld(), blockPos);

                    if(hardness >= 0.0f && getWorld().isBlockModifiable(entityPlayer, blockPos) &&
                        Math.abs(hardness - currentBlockHardness) < 0.5f) {
                        List<ItemStack> drops = attemptBreakBlockAndObtainDrops(blockPos, blockState, entityPlayer);
                        addToInventoryOrDropItems(drops);
                    }
                    this.breakProgressTicksLeft = 0;
                    this.currentBlockHardness = 0.0f;
                    energyContainer.removeEnergy(getEnergyPerBlockBreak());
                }
            }

            if(breakProgressTicksLeft == 0 && isBlockRedstonePowered()) {
                BlockPos blockPos = getPos().offset(getFrontFacing());
                IBlockState blockState = getWorld().getBlockState(blockPos);
                EntityPlayer entityPlayer = GregFakePlayer.get((WorldServer) getWorld());
                float hardness = blockState.getBlockHardness(getWorld(), blockPos);
                boolean skipBlock = blockState.getMaterial() == Material.AIR ||
                    blockState.getBlock().isAir(blockState, getWorld(), getPos());
                if(hardness >= 0.0f && !skipBlock && getWorld().isBlockModifiable(entityPlayer, blockPos)) {
                    this.breakProgressTicksLeft = getTicksPerBlockBreak(hardness);
                    this.currentBlockHardness = hardness;
                }
            }
        }
    }

    @Override
    protected boolean canMachineConnectRedstone(EnumFacing side) {
        return true;
    }

    private void addToInventoryOrDropItems(List<ItemStack> drops) {
        EnumFacing outputFacing = getOutputFacing();
        double itemSpawnX = getPos().getX() + 0.5 + outputFacing.getFrontOffsetX();
        double itemSpawnY = getPos().getX() + 0.5 + outputFacing.getFrontOffsetX();
        double itemSpawnZ = getPos().getX() + 0.5 + outputFacing.getFrontOffsetX();
        for(ItemStack itemStack : drops) {
            ItemStack remainStack = ItemHandlerHelper.insertItemStacked(exportItems, itemStack, false);
            if(!remainStack.isEmpty()) {
                EntityItem entityitem = new EntityItem(getWorld(), itemSpawnX, itemSpawnY, itemSpawnZ, remainStack);
                entityitem.setDefaultPickupDelay();
                getWorld().spawnEntity(entityitem);
            }
        }
    }

    private List<ItemStack> attemptBreakBlockAndObtainDrops(BlockPos blockPos, IBlockState blockState, EntityPlayer entityPlayer) {
        TileEntity tileEntity = getWorld().getTileEntity(blockPos);
        boolean result = blockState.getBlock().removedByPlayer(blockState, getWorld(), blockPos, entityPlayer, true);
        if(result) {
            getWorld().playEvent(null, 2001, blockPos, Block.getStateId(blockState));
            blockState.getBlock().onBlockDestroyedByPlayer(getWorld(), blockPos, blockState);

            BlockUtility.startCaptureDrops();
            blockState.getBlock().harvestBlock(getWorld(), entityPlayer, blockPos, blockState, tileEntity, ItemStack.EMPTY);
            return BlockUtility.stopCaptureDrops();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onWrenchClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) {
            EnumFacing currentOutputSide = getOutputFacing();
            if (currentOutputSide == facing ||
                getFrontFacing() == facing) return false;
            setOutputFacing(facing);
            return true;
        }
        return super.onWrenchClick(playerIn, hand, facing, hitResult);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("OutputFacing", getOutputFacing().getIndex());
        data.setInteger("BlockBreakProgress", breakProgressTicksLeft);
        data.setFloat("BlockHardness", currentBlockHardness);
        data.setTag("ItemFilter", itemFilterContainer.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.outputFacing = EnumFacing.VALUES[data.getInteger("OutputFacing")];
        this.breakProgressTicksLeft = data.getInteger("BlockBreakProgress");
        this.currentBlockHardness = data.getFloat("BlockHardness");
        if (data.hasKey("ItemFilter")) {
            this.itemFilterContainer.deserializeNBT(data.getCompoundTag("ItemFilter"));
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(getOutputFacing().getIndex());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputFacing = EnumFacing.VALUES[buf.readByte()];
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == 100) {
            this.outputFacing = EnumFacing.VALUES[buf.readByte()];
            getHolder().scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        //use direct outputFacing field instead of getter method because otherwise
        //it will just return SOUTH for null output facing
        return super.isValidFrontFacing(facing) && facing != outputFacing;
    }

    public EnumFacing getOutputFacing() {
        return outputFacing == null ? EnumFacing.SOUTH : outputFacing;
    }

    public void setOutputFacing(EnumFacing outputFacing) {
        this.outputFacing = outputFacing;
        if (!getWorld().isRemote) {
            getHolder().notifyBlockUpdate();
            writeCustomData(100, buf -> buf.writeByte(outputFacing.getIndex()));
            markDirty();
        }
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.outputFacing == null) {
            //set initial output facing as opposite to front
            setOutputFacing(frontFacing.getOpposite());
        }
    }

    private int getEnergyPerBlockBreak() {
        return (int) GTValues.V[getTier()];
    }

    private int getInventorySize() {
        int sizeRoot = (1 + getTier());
        return sizeRoot * sizeRoot;
    }

    private int getTicksPerBlockBreak(float blockHardness) {
        int ticksPerOneDurability = 5;
        int totalTicksPerBlock = (int) Math.ceil(ticksPerOneDurability * blockHardness);
        float efficiencyMultiplier = 1.0f - getEfficiencyMultiplier();
        return (int) Math.ceil(totalTicksPerBlock * efficiencyMultiplier);
    }

    private float getEfficiencyMultiplier() {
        return 1.0f - MathHelper.clamp(1.0f - 0.2f * (getTier() - 1.0f), 0.1f, 1.0f);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(getInventorySize());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(getInventorySize());
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * rowSize + 94)
            .label(10, 5, getMetaFullName());

        TabGroup primaryTabGroup = new TabGroup(TabGroup.TabLocation.HORIZONTAL_TOP_LEFT);

        //Initialize and populate inventory tab, containing output slots
        WidgetGroup inventoryTab = new WidgetGroup();
        inventoryTab.addWidget(new LabelWidget(10, 15, "gregtech.machine.block_breaker.tab.inventory"));

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < rowSize; x++) {
                int index = y * rowSize + x;
                int xPosition = 89 - rowSize * 9 + x * 18;
                int yPosition = 30 + y * 18;
                inventoryTab.addWidget(new SlotWidget(exportItems, index, xPosition, yPosition, true, false).setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        //Initialize separate tab for item filter, which will contain filter-specific content and slot for it
        WidgetGroup blockFilterTab = new WidgetGroup();
        blockFilterTab.addWidget(new LabelWidget(10, 15, "gregtech.machine.block_breaker.tab.block_filter"));
        this.itemFilterContainer.initUI(30, blockFilterTab::addWidget);

        //Initialize general settings tab. This will contain area size and a special widget for configuring each block individually
        WidgetGroup settingsTab = new WidgetGroup();
        blockFilterTab.addWidget(new LabelWidget(10, 15, "gregtech.machine.block_breaker.tab.settings"));

        builder.widget(new ClickButtonWidget(10, 30, 20, 20, "-1", data -> {}));
        builder.widget(new ClickButtonWidget(146, 30, 20, 20, "+1", data -> {}));
        builder.widget(new ImageWidget(30, 30, 116, 20, GuiTextures.DISPLAY));
        builder.widget(new SimpleTextWidget(88, 40, "gregtech.machine.block_breaker.working_area", 0xFFFFFF, () -> Integer.toString(0)));

        

        //Add the tabs we created earlier into the tab group
        primaryTabGroup.addTab(new ItemTabInfo("gregtech.machine.block_breaker.tab.settings", OreDictUnifier.get(OrePrefix.gear, Materials.Aluminium)), settingsTab);
        primaryTabGroup.addTab(new ItemTabInfo("gregtech.machine.block_breaker.tab.block_filter", MetaItems.ITEM_FILTER.getStackForm()), blockFilterTab);
        primaryTabGroup.addTab(new ItemTabInfo("gregtech.machine.block_breaker.tab.inventory", new ItemStack(Blocks.CHEST)), inventoryTab);


        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 18 + 18 * rowSize + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", getInventorySize()));
        tooltip.add(I18n.format("gregtech.machine.block_breaker.speed_bonus", (int) (getEfficiencyMultiplier() * 100)));
        tooltip.add(I18n.format("gregtech.machine.block_breaker.consumption", getEnergyPerBlockBreak()));
    }
}
