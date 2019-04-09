package gregtech.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.ServerWidgetGroup;
import gregtech.api.render.Textures;
import gregtech.api.unification.stack.ItemAndMetadata;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class CoverRoboticArm extends CoverConveyor {

    protected TransferMode transferMode;
    protected boolean transferSameItemsOnly;

    public CoverRoboticArm(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
        this.transferMode = TransferMode.TRANSFER_ANY;
        this.transferSameItemsOnly = true;
        this.itemFilterSlots = new ItemStackHandler(9) {
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.ARM_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void update() {
        if(isWorkingAllowed) {
            this.transferMode.executor.accept(this);
        }
    }

    protected void doTransferExact() {
        if (coverHolder.getTimer() % 20 == 0L) {
            TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
            IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
            IItemHandler myItemHandler = coverHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide);
            if (itemHandler == null || myItemHandler == null) {
                return;
            }
            Map<ItemAndMetadata, ItemInfo> sourceItemAmount = doCountSourceInventoryItemsByType(itemHandler, myItemHandler);
            int[] itemsTransferLimits = new int[filterMode.maxMatchSlots];
            if (filterMode == FilterType.ITEM_FILTER) {
                for (int i = 0; i < itemsTransferLimits.length; i++) {
                    ItemStack filterStack = itemFilterSlots.getStackInSlot(i);
                    itemsTransferLimits[i] = Math.min(filterStack.getCount(), transferRate);
                }
            } else if (filterMode == FilterType.ORE_DICTIONARY_FILTER) {
                itemsTransferLimits[0] = Math.min(transferRate, 64);
            } else {
                itemsTransferLimits[0] = Math.min(transferRate, 64);
            }
            Iterator<ItemAndMetadata> iterator = sourceItemAmount.keySet().iterator();
            while(iterator.hasNext()) {
                ItemAndMetadata key = iterator.next();
                ItemInfo sourceInfo = sourceItemAmount.get(key);
                int itemAmount = sourceInfo.totalCount;
                int itemToMoveAmount = itemsTransferLimits[sourceInfo.filterSlot];
                if(itemAmount >= itemToMoveAmount) {
                    sourceInfo.totalCount = itemToMoveAmount;
                } else {
                    iterator.remove();
                }
            }
            for(ItemInfo itemInfo : sourceItemAmount.values()) {
                doTransferItemsExact(itemHandler, myItemHandler, itemInfo);
            }
        }
    }

    private void doKeepExact() {
        if (coverHolder.getTimer() % 20 == 0L) {
            TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
            IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
            IItemHandler myItemHandler = coverHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide);
            if (itemHandler == null || myItemHandler == null) {
                return;
            }
            int[] currentItemAmount = doCountDestinationInventoryItemsByMatchIndex(itemHandler, myItemHandler);
            int[] keepItemAmount = new int[currentItemAmount.length];
            if (filterMode == FilterType.ITEM_FILTER) {
                for (int i = 0; i < keepItemAmount.length; i++) {
                    ItemStack filterStack = itemFilterSlots.getStackInSlot(i);
                    keepItemAmount[i] = filterStack.getCount();
                }
            } else if (filterMode == FilterType.ORE_DICTIONARY_FILTER) {
                keepItemAmount[0] = transferRate;
            } else {
                keepItemAmount[0] = transferRate;
            }
            int[] itemsToMove = calculateItemsToMove(currentItemAmount, keepItemAmount, transferRate);
            doTransferItemsInternal(itemHandler, myItemHandler, transferRate, itemsToMove);
        }
    }

    private static int[] calculateItemsToMove(int[] currentItemAmount, int[] keepItemAmount, int maxItemsToMove) {
        int[] resultAmount = new int[currentItemAmount.length];
        for (int i = 0; i < resultAmount.length; i++) {
            int currentAmount = currentItemAmount[i];
            int minAmount = keepItemAmount[i];
            if (minAmount > currentAmount) {
                resultAmount[i] = Math.max(minAmount - currentAmount, maxItemsToMove);
            }
        }
        return resultAmount;
    }

    public void setTransferMode(TransferMode transferMode) {
        this.transferMode = transferMode;
        coverHolder.markDirty();
    }

    @Override
    protected void onFilterModeUpdated() {
        super.onFilterModeUpdated();
        if (filterMode == FilterType.NONE) {
            setTransferMode(TransferMode.TRANSFER_ANY);
        }
    }

    @Override
    protected String getUITitle() {
        return "cover.robotic_arm.title";
    }

    @Override
    protected ModularUI buildUI(Builder builder, EntityPlayer player) {
        ServerWidgetGroup filterGroup = new ServerWidgetGroup(() -> filterMode != FilterType.NONE);
        filterGroup.addWidget(new CycleButtonWidget(91, 45, 75, 20,
            GTUtility.mapToString(TransferMode.values(), it -> it.localeName),
            () -> transferMode.ordinal(), (newMode) -> setTransferMode(TransferMode.values()[newMode]))
            .setTooltipHoverString("cover.robotic_arm.transfer_mode.description"));
        return super.buildUI(builder.widget(filterGroup), player);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferMode", transferMode.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferMode = TransferMode.values()[tagCompound.getInteger("TransferMode")];
    }

    public enum TransferMode {
        TRANSFER_ANY("cover.robotic_arm.transfer_mode.transfer_any", CoverConveyor::doTransferAny),
        TRANSFER_EXACT("cover.robotic_arm.transfer_mode.transfer_exact", CoverRoboticArm::doTransferExact),
        KEEP_EXACT("cover.robotic_arm.transfer_mode.keep_exact", CoverRoboticArm::doKeepExact);

        public final String localeName;
        protected final Consumer<CoverRoboticArm> executor;

        TransferMode(String localeName, Consumer<CoverRoboticArm> executor) {
            this.localeName = localeName;
            this.executor = executor;
        }
    }
}
