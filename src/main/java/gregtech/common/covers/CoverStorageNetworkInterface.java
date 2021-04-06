package gregtech.common.covers;

import java.util.Set;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IItemInfo;
import gregtech.api.capability.IStorageNetwork;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SimpleTextWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.util.ItemStackKey;
import gregtech.common.covers.filter.ItemFilterContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

/*
 * This is a cover for something that has the IStorageNetwork capability i.e. an inventory pipe 
 * It gives simple conveyor like functionality with the adjacent tile/ITEM_HANDLER on this face
 * 
 * Review: Maybe allow two IStorageNetworks to connect, e.g. moving items between different inventory pipe networks
 *         This can already be done using an intermediate chest/inventory
 * 
 * Review: Additional covers to add, requires more invasive changes
 *         Storage Cover - instead of automatically adding inventories to the storage network, require a cover to configure it
 *                         currently the only way to configure attached inventories is with an item filter cover, but this can't be
 *                         done for non gregtech tiles, e.g. a vanilla chest or a storage drawer network
 *                         This also removes a problem with a machine acting as storage before you get a chance to configure its cover
 *         P2P           - This would move items directly between inventories using some kind of channel without having 
 *                         to be first stored in the network - channels configured using IC's?
 *         Robot Arm     - Conveyor is to Robot Arm as CoverStorageNetworkInterface is to this additional cover
 *                         Personally, I find the Robot Arms behaviours nonintuitive so I wouldn't know if I have
 *                         implemented them correctly
 *         Stock Level   - Emits a redstone signal based on items in the network. e.g. == 0. < 100, >= 10, etc.
 *         Keep in Stock - Add a cover configurable with amounts to keep in stock in the storage network
 *                         When there is not enough stock it would do something like the JEI processing with the tile
 *                         on its adjacent face to get its recipe manager. 
 *                         This would then be used to find recipes that can be satisfied from the items in the storage network
 *                         The idea is to use many of these in a network to implement autocrafting chains
 *                         Complications: Fluids and nonconsumed items e.g. ICs and molds/extruder shapes
 *                                        Multiblocks - inputs need to go to a different tile
 *                         Integration: Maybe include a mechanism to also include known non gregtech tiles, e.g. a furnace
 *                                      Use the integrated dynamics capability to get recipes if available?
 *                                      Allow a recipe to configured explicitly (something like the ae2/rs patterns)
 *                         Usability: Insight/control is probably required into what recipes are (or are not) being run
 *                         RecipeLogic: filtering which recipes to run on additional information, e.g. energy tier, coil blocks, etc.
 *                         
 * Review: Allow the Workbench to use the storage network 
 *         currently inventory pipes dont connect because it doesnt have the inventory capability (intentionally?)
 *         and the workbench obviously doesn't look for the new IStorageNetwork capability
 * Review: I am sure there are many other features, e.g. wireless access
 */
public class CoverStorageNetworkInterface extends CoverBehavior implements CoverWithUI, ITickable, IControllable {

    public final int tier;
    public final int maxItemTransferRate;
    protected int transferRate;
    protected InterfaceMode interfaceMode;
    protected ManualImportExportMode manualImportExportMode = ManualImportExportMode.DISABLED;
    protected final ItemFilterContainer itemFilterContainer;
    protected int itemsLeftToTransferLastSecond;
    private CoverableStorageNetworkWrapper storageNetworkWrapper;
    protected boolean isWorkingAllowed = true;

    public CoverStorageNetworkInterface(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide);
        this.tier = tier;
        this.maxItemTransferRate = itemsPerSecond;
        this.transferRate = maxItemTransferRate;
        this.itemsLeftToTransferLastSecond = transferRate;
        // Review: Placing the cover will immediately start importing items before the user has a chance to configure a filter
        // A conveyor defaults to export but in this case it would immediately start filling up the attached machine with
        // random things from the storage network
        // Neither is a good default if the attached tile is a chest?
        this.interfaceMode = InterfaceMode.IMPORT;
        this.itemFilterContainer = new ItemFilterContainer(this);
    }

    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    // Review: Maybe setting a default transfer rate of zero will fix the default configuration issue above?
    protected void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxItemTransferRate));
    }

    protected void setInterfaceMode(InterfaceMode conveyorMode) {
        this.interfaceMode = conveyorMode;
        coverHolder.markDirty();
    }

    public InterfaceMode getInterfaceMode() {
        return interfaceMode;
    }

    public ManualImportExportMode getManualImportExportMode() {
        return manualImportExportMode;
    }

    protected void setManualImportExportMode(ManualImportExportMode manualImportExportMode) {
        this.manualImportExportMode = manualImportExportMode;
        coverHolder.markDirty();
    }

    @Override
    public void update() {
        long timer = coverHolder.getTimer();
        if (timer % 5 == 0 && isWorkingAllowed && itemsLeftToTransferLastSecond > 0) {
            TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
            IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
            IStorageNetwork myStorageNetwork = coverHolder.getCapability(GregtechCapabilities.CAPABILITY_STORAGE_NETWORK, attachedSide);
            if (itemHandler != null && myStorageNetwork != null) {
                int totalTransferred = doTransferItems(itemHandler, myStorageNetwork, itemsLeftToTransferLastSecond);
                this.itemsLeftToTransferLastSecond -= totalTransferred;
            }
        }
        if (timer % 20 == 0) {
            this.itemsLeftToTransferLastSecond = transferRate;
        }
    }

    protected int doTransferItems(IItemHandler itemHandler, IStorageNetwork myStorageNetwork, int maxTransferAmount) {
        if (interfaceMode == InterfaceMode.IMPORT) {
            return moveInventoryItems(itemHandler, myStorageNetwork, maxTransferAmount);
        } else if (interfaceMode == InterfaceMode.EXPORT) {
            return moveInventoryItems(myStorageNetwork, itemHandler, maxTransferAmount);
        }
        return 0;
    }

    protected int moveInventoryItems(IItemHandler sourceInventory, IStorageNetwork targetInventory, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, itemsLeftToTransfer, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            if (!itemFilterContainer.testItemStack(sourceStack)) {
                continue;
            }
            ItemStackKey sourceStackKey = new ItemStackKey(sourceStack);
            int amountToInsert = targetInventory.insertItem(sourceStackKey, sourceStack.getCount(), true);

            if (amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                if (!sourceStack.isEmpty()) {
                    targetInventory.insertItem(sourceStackKey, sourceStack.getCount(), false);
                    itemsLeftToTransfer -= sourceStack.getCount();

                    if (itemsLeftToTransfer == 0) {
                        break;
                    }
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    protected int moveInventoryItems(IStorageNetwork sourceInventory, IItemHandler targetInventory, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        // REVIEW: more efficient to iterate over the item filter inventory when it has one and its a whitelist? 
        for (ItemStackKey key : sourceInventory.getStoredItems()) {
            if (!itemFilterContainer.testItemStack(key.getItemStack())) {
                continue;
            }
            int extracted = sourceInventory.extractItem(key, itemsLeftToTransfer, true);
            if (extracted == 0) {
                continue;
            }
            ItemStack sourceStack = key.getItemStack();
            sourceStack.setCount(extracted);
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, true);
            int amountToInsert = sourceStack.getCount() - remainder.getCount();

            if (amountToInsert > 0) {
                extracted = sourceInventory.extractItem(key, amountToInsert, false);
                if (extracted == 0) {
                    continue;
                }
                sourceStack = key.getItemStack();
                sourceStack.setCount(extracted);
                ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, false);
                itemsLeftToTransfer -= sourceStack.getCount();

                if (itemsLeftToTransfer == 0) {
                    break;
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(GregtechCapabilities.CAPABILITY_STORAGE_NETWORK, attachedSide) != null;
    }
    
    @Override
    public boolean shouldCoverInteractWithOutputside() {
        return true;
    }
    
    @Override
    public void onRemoved() {
        NonNullList<ItemStack> drops = NonNullList.create();
        MetaTileEntity.clearInventory(drops, itemFilterContainer.getFilterInventory());
        for (ItemStack itemStack : drops) {
            Block.spawnAsEntity(coverHolder.getWorld(), coverHolder.getPos(), itemStack);
        }
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        Textures.CONVEYOR_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == GregtechCapabilities.CAPABILITY_STORAGE_NETWORK) {
            IStorageNetwork delegate = (IStorageNetwork) defaultValue;
            if (storageNetworkWrapper == null || storageNetworkWrapper.delegate != delegate) {
                this.storageNetworkWrapper = new CoverableStorageNetworkWrapper(delegate);
            }
            return GregtechCapabilities.CAPABILITY_STORAGE_NETWORK.cast(storageNetworkWrapper);
        }
        if(capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return defaultValue;
    }

    protected String getUITitle() {
        return "cover.conveyor.title";
    }

    protected ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this, player);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, getUITitle(), GTValues.VN[tier]));
        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustTransferRate(data.isShiftClick ? -100 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustTransferRate(data.isShiftClick ? +100 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(88, 30, "cover.conveyor.transfer_rate", 0xFFFFFF, () -> Integer.toString(transferRate)));

        primaryGroup.addWidget(new CycleButtonWidget(10, 45, 75, 20,
            InterfaceMode.class, this::getInterfaceMode, this::setInterfaceMode));
        primaryGroup.addWidget(new CycleButtonWidget(10, 166, 113, 20,
            ManualImportExportMode.class, this::getManualImportExportMode, this::setManualImportExportMode)
            .setTooltipHoverString("cover.universal.manual_import_export.mode.description"));

        this.itemFilterContainer.initUI(70, primaryGroup::addWidget);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 190 + 82)
            .widget(primaryGroup)
            .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 8, 190);
        return buildUI(builder, player);
    }

    @Override
    public boolean isWorkingEnabled() {
        return isWorkingAllowed;
    }

    @Override
    public void setWorkingEnabled(boolean isActivationAllowed) {
        this.isWorkingAllowed = isActivationAllowed;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferRate", transferRate);
        tagCompound.setInteger("InterfaceMode", interfaceMode.ordinal());
        tagCompound.setBoolean("WorkingAllowed", isWorkingAllowed);
        tagCompound.setInteger("ManualImportExportMode", manualImportExportMode.ordinal());
        tagCompound.setTag("Filter", this.itemFilterContainer.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.interfaceMode = InterfaceMode.values()[tagCompound.getInteger("InterfaceMode")];
        if(tagCompound.hasKey("FilterInventory")) {
            this.itemFilterContainer.deserializeNBT(tagCompound);
        } else {
            NBTTagCompound filterComponent = tagCompound.getCompoundTag("Filter");
            this.itemFilterContainer.deserializeNBT(filterComponent);
        }
        if(tagCompound.hasKey("WorkingAllowed")) {
            this.isWorkingAllowed = tagCompound.getBoolean("WorkingAllowed");
        }
        if(tagCompound.hasKey("ManualImportExportMode")) {
            this.manualImportExportMode = ManualImportExportMode.values()[tagCompound.getInteger("ManualImportExportMode")];
        }
    }

    public enum InterfaceMode implements IStringSerializable {
        IMPORT("cover.conveyor.mode.import"),
        EXPORT("cover.conveyor.mode.export");

        public final String localeName;

        InterfaceMode(String localeName) {
            this.localeName = localeName;
        }

        @Override
        public String getName() {
            return localeName;
        }
    }

    private class CoverableStorageNetworkWrapper implements IStorageNetwork {

        private IStorageNetwork delegate;
        
        public CoverableStorageNetworkWrapper(final IStorageNetwork delegate) {
            this.delegate = delegate;
        }

        @Override
        public Set<ItemStackKey> getStoredItems() {
            return delegate.getStoredItems();
        }

        @Override
        public IItemInfo getItemInfo(ItemStackKey stackKey) {
            return delegate.getItemInfo(stackKey);
        }

        @Override
        public int insertItem(ItemStackKey itemStack, int amount, boolean simulate) {
            if (interfaceMode == InterfaceMode.EXPORT && manualImportExportMode == ManualImportExportMode.DISABLED) {
                return 0;
            }
            if (!itemFilterContainer.testItemStack(itemStack.getItemStackRaw()) && manualImportExportMode == ManualImportExportMode.FILTERED) {
                return 0;
            }
            return delegate.insertItem(itemStack, amount, simulate);
        }

        @Override
        public int extractItem(ItemStackKey itemStack, int amount, boolean simulate) {
            if (interfaceMode == InterfaceMode.IMPORT && manualImportExportMode == ManualImportExportMode.DISABLED) {
                return 0;
            }
            int result = delegate.extractItem(itemStack, amount, true);
            if (!itemFilterContainer.testItemStack(itemStack.getItemStackRaw()) && manualImportExportMode == ManualImportExportMode.FILTERED) {
                return 0;
            }
            if (!simulate) {
                result = delegate.extractItem(itemStack, amount, false);
            }
            return result;
        }
    }
}
