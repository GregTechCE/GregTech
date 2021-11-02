package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gregtech.api.GTValues;
import gregtech.api.capability.ConfigurationContext;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.impl.ItemHandlerDelegate;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
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
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.*;

public class CoverConveyor extends CoverBehavior implements CoverWithUI, ITickable, IControllable {

    public final int tier;
    public final int maxItemTransferRate;
    protected int transferRate;
    protected ConveyorMode conveyorMode;
    protected ManualImportExportMode manualImportExportMode = ManualImportExportMode.DISABLED;
    protected final ItemFilterContainer itemFilterContainer;
    protected int itemsLeftToTransferLastSecond;
    private CoverableItemHandlerWrapper itemHandlerWrapper;
    protected boolean isWorkingAllowed = true;

    public CoverConveyor(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide);
        this.tier = tier;
        this.maxItemTransferRate = itemsPerSecond;
        this.transferRate = maxItemTransferRate;
        this.itemsLeftToTransferLastSecond = transferRate;
        this.conveyorMode = ConveyorMode.EXPORT;
        this.itemFilterContainer = new ItemFilterContainer(this);
    }

    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    protected void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxItemTransferRate));
    }

    protected void setConveyorMode(ConveyorMode conveyorMode) {
        this.conveyorMode = conveyorMode;
        coverHolder.markDirty();
    }

    public ConveyorMode getConveyorMode() {
        return conveyorMode;
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
        long timer = coverHolder.getOffsetTimer();
        if (timer % 5 == 0 && isWorkingAllowed && itemsLeftToTransferLastSecond > 0) {
            TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
            IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
            IItemHandler myItemHandler = coverHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide);
            if (itemHandler != null && myItemHandler != null) {
                int totalTransferred = doTransferItems(itemHandler, myItemHandler, itemsLeftToTransferLastSecond);
                this.itemsLeftToTransferLastSecond -= totalTransferred;
            }
        }
        if (timer % 20 == 0) {
            this.itemsLeftToTransferLastSecond = transferRate;
        }
    }

    protected int doTransferItems(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        return doTransferItemsAny(itemHandler, myItemHandler, maxTransferAmount);
    }

    protected int doTransferItemsAny(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        if (conveyorMode == ConveyorMode.IMPORT) {
            return moveInventoryItems(itemHandler, myItemHandler, maxTransferAmount);
        } else if (conveyorMode == ConveyorMode.EXPORT) {
            return moveInventoryItems(myItemHandler, itemHandler, maxTransferAmount);
        }
        return 0;
    }

    protected int doTransferItemsByGroup(IItemHandler itemHandler, IItemHandler myItemHandler, Map<Object, GroupItemInfo> itemInfos, int maxTransferAmount) {
        if (conveyorMode == ConveyorMode.IMPORT) {
            return moveInventoryItems(itemHandler, myItemHandler, itemInfos, maxTransferAmount);
        } else if (conveyorMode == ConveyorMode.EXPORT) {
            return moveInventoryItems(myItemHandler, itemHandler, itemInfos, maxTransferAmount);
        }
        return 0;
    }

    protected Map<Object, GroupItemInfo> doCountDestinationInventoryItemsByMatchIndex(IItemHandler itemHandler, IItemHandler myItemHandler) {
        if (conveyorMode == ConveyorMode.IMPORT) {
            return countInventoryItemsByMatchSlot(myItemHandler);
        } else if (conveyorMode == ConveyorMode.EXPORT) {
            return countInventoryItemsByMatchSlot(itemHandler);
        }
        return Collections.emptyMap();
    }

    protected Map<ItemStackKey, TypeItemInfo> doCountSourceInventoryItemsByType(IItemHandler itemHandler, IItemHandler myItemHandler) {
        if (conveyorMode == ConveyorMode.IMPORT) {
            return countInventoryItemsByType(itemHandler);
        } else if (conveyorMode == ConveyorMode.EXPORT) {
            return countInventoryItemsByType(myItemHandler);
        }
        return Collections.emptyMap();
    }

    protected boolean doTransferItemsExact(IItemHandler itemHandler, IItemHandler myItemHandler, TypeItemInfo itemInfo) {
        if (conveyorMode == ConveyorMode.IMPORT) {
            return moveInventoryItemsExact(itemHandler, myItemHandler, itemInfo);
        } else if (conveyorMode == ConveyorMode.EXPORT) {
            return moveInventoryItemsExact(myItemHandler, itemHandler, itemInfo);
        }
        return false;
    }

    protected boolean moveInventoryItemsExact(IItemHandler sourceInventory, IItemHandler targetInventory, TypeItemInfo itemInfo) {
        //first, compute how much can we extract in reality from the machine,
        //because totalCount is based on what getStackInSlot returns, which may differ from what
        //extractItem() will return
        ItemStack resultStack = itemInfo.itemStack.copy();
        int totalExtractedCount = 0;
        int itemsLeftToExtract = itemInfo.totalCount;

        for(int i = 0; i < itemInfo.slots.size(); i++) {
            int slotIndex = itemInfo.slots.get(i);
            ItemStack extractedStack = sourceInventory.extractItem(slotIndex, itemsLeftToExtract, true);
            if(!extractedStack.isEmpty() &&
                ItemStack.areItemsEqual(resultStack, extractedStack) &&
                ItemStack.areItemStackTagsEqual(resultStack, extractedStack)) {
                totalExtractedCount += extractedStack.getCount();
                itemsLeftToExtract -= extractedStack.getCount();
            }
            if(itemsLeftToExtract == 0) {
                break;
            }
        }
        //if amount of items extracted is not equal to the amount of items we
        //wanted to extract, abort item extraction
        if(totalExtractedCount != itemInfo.totalCount) {
            return false;
        }
        //adjust size of the result stack accordingly
        resultStack.setCount(totalExtractedCount);

        //now, see how much we can insert into destination inventory
        //if we can't insert as much as itemInfo requires, and remainder is empty, abort, abort
        ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, resultStack, true);
        if(!remainder.isEmpty()) {
            return false;
        }

        //otherwise, perform real insertion and then remove items from the source inventory
        ItemHandlerHelper.insertItemStacked(targetInventory, resultStack, false);

        //perform real extraction of the items from the source inventory now
        itemsLeftToExtract = itemInfo.totalCount;
        for(int i = 0; i < itemInfo.slots.size(); i++) {
            int slotIndex = itemInfo.slots.get(i);
            ItemStack extractedStack = sourceInventory.extractItem(slotIndex, itemsLeftToExtract, false);
            if(!extractedStack.isEmpty() &&
                ItemStack.areItemsEqual(resultStack, extractedStack) &&
                ItemStack.areItemStackTagsEqual(resultStack, extractedStack)) {
                itemsLeftToExtract -= extractedStack.getCount();
            }
            if(itemsLeftToExtract == 0) {
                break;
            }
        }
        return true;
    }

    protected int moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory, Map<Object, GroupItemInfo> itemInfos, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        for (int i = 0; i < sourceInventory.getSlots(); i++) {
            ItemStack itemStack = sourceInventory.getStackInSlot(i);
            if(itemStack.isEmpty()) {
                continue;
            }
            Object matchSlotIndex = itemFilterContainer.matchItemStack(itemStack);
            if (matchSlotIndex == null || !itemInfos.containsKey(matchSlotIndex)) {
                continue;
            }

            GroupItemInfo itemInfo = itemInfos.get(matchSlotIndex);

            ItemStack extractedStack = sourceInventory.extractItem(i, Math.min(itemInfo.totalCount, itemsLeftToTransfer), true);

            ItemStack remainderStack = ItemHandlerHelper.insertItemStacked(targetInventory, extractedStack, true);
            int amountToInsert = extractedStack.getCount() - remainderStack.getCount();

            if (amountToInsert > 0) {
                extractedStack = sourceInventory.extractItem(i, amountToInsert, false);

                if(!extractedStack.isEmpty()) {

                    ItemHandlerHelper.insertItemStacked(targetInventory, extractedStack, false);
                    itemsLeftToTransfer -= extractedStack.getCount();
                    itemInfo.totalCount -= extractedStack.getCount();

                    if (itemInfo.totalCount == 0) {
                        itemInfos.remove(matchSlotIndex);
                        if(itemInfos.isEmpty()) {
                            break;
                        }
                    }
                    if(itemsLeftToTransfer == 0) {
                        break;
                    }
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    protected int moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, itemsLeftToTransfer, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            if (!itemFilterContainer.testItemStack(sourceStack)) {
                continue;
            }
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, true);
            int amountToInsert = sourceStack.getCount() - remainder.getCount();

            if (amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                if (!sourceStack.isEmpty()) {
                    ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, false);
                    itemsLeftToTransfer -= sourceStack.getCount();

                    if (itemsLeftToTransfer == 0) {
                        break;
                    }
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    protected static class TypeItemInfo {
        public final ItemStack itemStack;
        public final Object filterSlot;
        public final TIntList slots;
        public int totalCount;

        public TypeItemInfo(ItemStack itemStack, Object filterSlot, TIntList slots, int totalCount) {
            this.itemStack = itemStack;
            this.filterSlot = filterSlot;
            this.slots = slots;
            this.totalCount = totalCount;
        }
    }

    protected static class GroupItemInfo {
        public final Object filterSlot;
        public final Set<ItemStackKey> itemStackTypes;
        public int totalCount;

        public GroupItemInfo(Object filterSlot, Set<ItemStackKey> itemStackTypes, int totalCount) {
            this.filterSlot = filterSlot;
            this.itemStackTypes = itemStackTypes;
            this.totalCount = totalCount;
        }
    }

    protected Map<ItemStackKey, TypeItemInfo> countInventoryItemsByType(IItemHandler inventory) {
        Map<ItemStackKey, TypeItemInfo> result = new HashMap<>();
        for (int srcIndex = 0; srcIndex < inventory.getSlots(); srcIndex++) {
            ItemStack itemStack = inventory.getStackInSlot(srcIndex);
            if (itemStack.isEmpty()) {
                continue;
            }
            Object transferSlotIndex = itemFilterContainer.matchItemStack(itemStack);
            if (transferSlotIndex == null) {
                continue;
            }
            ItemStackKey itemStackKey = new ItemStackKey(itemStack);
            if(!result.containsKey(itemStackKey)) {
                TypeItemInfo itemInfo = new TypeItemInfo(itemStack.copy(), transferSlotIndex, new TIntArrayList(), 0);
                itemInfo.totalCount += itemStack.getCount();
                itemInfo.slots.add(srcIndex);
                result.put(itemStackKey, itemInfo);
            } else {
                TypeItemInfo itemInfo = result.get(itemStackKey);
                itemInfo.totalCount += itemStack.getCount();
                itemInfo.slots.add(srcIndex);
            }
        }
        return result;
    }

    protected Map<Object, GroupItemInfo> countInventoryItemsByMatchSlot(IItemHandler inventory) {
        HashMap<Object, GroupItemInfo> result = new HashMap<>();
        for (int srcIndex = 0; srcIndex < inventory.getSlots(); srcIndex++) {
            ItemStack itemStack = inventory.getStackInSlot(srcIndex);
            if (itemStack.isEmpty()) {
                continue;
            }
            Object transferSlotIndex = itemFilterContainer.matchItemStack(itemStack);
            if (transferSlotIndex == null) {
                continue;
            }
            ItemStackKey itemStackKey = new ItemStackKey(itemStack);
            if(!result.containsKey(transferSlotIndex)) {
                GroupItemInfo itemInfo = new GroupItemInfo(transferSlotIndex, new HashSet<>(), 0);
                itemInfo.itemStackTypes.add(itemStackKey);
                itemInfo.totalCount += itemStack.getCount();
                result.put(transferSlotIndex, itemInfo);
            } else {
                GroupItemInfo itemInfo = result.get(transferSlotIndex);
                itemInfo.itemStackTypes.add(itemStackKey);
                itemInfo.totalCount += itemStack.getCount();
            }
        }
        return result;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide) != null;
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler delegate = (IItemHandler) defaultValue;
            if (itemHandlerWrapper == null || itemHandlerWrapper.delegate != delegate) {
                this.itemHandlerWrapper = new CoverableItemHandlerWrapper(delegate);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandlerWrapper);
        }
        if(capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        if (capability == GregtechTileCapabilities.CAPABILITY_CONFIGURABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONFIGURABLE.cast(this);
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
            ConveyorMode.class, this::getConveyorMode, this::setConveyorMode));
        primaryGroup.addWidget(new CycleButtonWidget(10, 166, 113, 20,
            ManualImportExportMode.class, this::getManualImportExportMode, this::setManualImportExportMode)
            .setTooltipHoverString("cover.universal.manual_import_export.mode.description"));

        this.itemFilterContainer.initUI(70, primaryGroup::addWidget);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 190 + 82)
            .widget(primaryGroup)
            .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 7, 190);
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
        tagCompound.setInteger("ConveyorMode", conveyorMode.ordinal());
        tagCompound.setBoolean("WorkingAllowed", isWorkingAllowed);
        tagCompound.setInteger("ManualImportExportMode", manualImportExportMode.ordinal());
        tagCompound.setTag("Filter", this.itemFilterContainer.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.conveyorMode = ConveyorMode.values()[tagCompound.getInteger("ConveyorMode")];
        //LEGACY SAVE FORMAT SUPPORT
        if (tagCompound.hasKey("AllowManualIO")) {
            this.manualImportExportMode = tagCompound.getBoolean("AllowManualIO")
            ? ManualImportExportMode.FILTERED
            : ManualImportExportMode.DISABLED;
        }
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

    @Override
    public NBTTagCompound copyConfiguration(final ConfigurationContext context) {
        final NBTTagCompound tagCompound = super.copyConfiguration(context);
        tagCompound.setInteger("TransferRate", this.transferRate);
        tagCompound.setInteger("ConveyorMode", this.conveyorMode.ordinal());
        tagCompound.setInteger("ManualImportExportMode", this.manualImportExportMode.ordinal());
        tagCompound.setTag("Filter", this.itemFilterContainer.copyConfiguration(context));
        return tagCompound;
    }

    @Override
    public void pasteConfiguration(final ConfigurationContext context, final NBTTagCompound tagCompound) {
        super.pasteConfiguration(context, tagCompound);
        setTransferRate(tagCompound.getInteger("TransferRate"));
        setConveyorMode(ConveyorMode.values()[tagCompound.getInteger("ConveyorMode")]);
        //LEGACY SAVE FORMAT SUPPORT
        if (tagCompound.hasKey("AllowManualIO")) {
            setManualImportExportMode(tagCompound.getBoolean("AllowManualIO")
            ? ManualImportExportMode.FILTERED
            : ManualImportExportMode.DISABLED);
        }
        if (tagCompound.hasKey("ManualImportExportMode")) {
            setManualImportExportMode(ManualImportExportMode.values()[tagCompound.getInteger("ManualImportExportMode")]);
        }
        if (tagCompound.hasKey("FilterInventory")) {
            this.itemFilterContainer.pasteConfiguration(context, tagCompound);
        } else {
            final NBTTagCompound filterComponent = tagCompound.getCompoundTag("Filter");
            this.itemFilterContainer.pasteConfiguration(context, filterComponent);
        }
    }

    public enum ConveyorMode implements IStringSerializable {
        IMPORT("cover.conveyor.mode.import"),
        EXPORT("cover.conveyor.mode.export");

        public final String localeName;

        ConveyorMode(String localeName) {
            this.localeName = localeName;
        }

        @Override
        public String getName() {
            return localeName;
        }
    }

    private class CoverableItemHandlerWrapper extends ItemHandlerDelegate {

        public CoverableItemHandlerWrapper(IItemHandler delegate) {
            super(delegate);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (conveyorMode == ConveyorMode.EXPORT && manualImportExportMode == ManualImportExportMode.DISABLED) {
                return stack;
            }
            if (!itemFilterContainer.testItemStack(stack) && manualImportExportMode == ManualImportExportMode.FILTERED) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (conveyorMode == ConveyorMode.IMPORT && manualImportExportMode == ManualImportExportMode.DISABLED) {
                return ItemStack.EMPTY;
            }
            ItemStack resultStack = super.extractItem(slot, amount, true);
            if (!itemFilterContainer.testItemStack(resultStack) && manualImportExportMode == ManualImportExportMode.FILTERED) {
                return ItemStack.EMPTY;
            }
            if (!simulate) {
                super.extractItem(slot, amount, false);
            }
            return resultStack;
        }
    }
}
