package gregtech.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.gui.widgets.*;
import gregtech.api.render.Textures;
import gregtech.api.unification.stack.ItemAndMetadata;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class CoverRoboticArm extends CoverConveyor {

    protected TransferMode transferMode;
    protected int transferStackSize;

    public CoverRoboticArm(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide, tier, itemsPerSecond);
        this.transferMode = TransferMode.TRANSFER_ANY;
        this.itemFilterContainer.setMaxStackSize(transferMode.maxStackSize);
        this.transferStackSize = 1;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.ARM_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    protected int doTransferItems(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        switch (transferMode) {
            case TRANSFER_ANY: return doTransferAny(itemHandler, myItemHandler, maxTransferAmount);
            case TRANSFER_EXACT: return doTransferExact(itemHandler, myItemHandler, maxTransferAmount);
            case KEEP_EXACT: return doKeepExact(itemHandler, myItemHandler, maxTransferAmount);
            default: return 0;
        }
    }

    protected int doTransferExact(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        Map<ItemAndMetadata, ItemInfo> sourceItemAmount = doCountSourceInventoryItemsByType(itemHandler, myItemHandler);
        int[] itemsTransferLimits = new int[itemFilterContainer.getMaxMatchSlots()];
        for (int i = 0; i < itemsTransferLimits.length; i++) {
            int slotTransferLimit = itemFilterContainer.getSlotStackSize(i);
            if (slotTransferLimit == -1) {
                itemsTransferLimits[i] = transferStackSize;
            } else {
                itemsTransferLimits[i] = Math.min(transferMode.maxStackSize, slotTransferLimit);
            }
        }
        Iterator<ItemAndMetadata> iterator = sourceItemAmount.keySet().iterator();
        while (iterator.hasNext()) {
            ItemAndMetadata key = iterator.next();
            ItemInfo sourceInfo = sourceItemAmount.get(key);
            int itemAmount = sourceInfo.totalCount;
            int itemToMoveAmount = itemsTransferLimits[sourceInfo.filterSlot];
            if (itemAmount >= itemToMoveAmount) {
                sourceInfo.totalCount = itemToMoveAmount;
            } else {
                iterator.remove();
            }
        }
        int itemsTransferred = 0;
        for (ItemInfo itemInfo : sourceItemAmount.values()) {
            if(maxTransferAmount >= itemInfo.totalCount) {
                boolean result = doTransferItemsExact(itemHandler, myItemHandler, itemInfo);
                itemsTransferred += result ? itemInfo.totalCount : 0;
                maxTransferAmount -= result ? itemInfo.totalCount : 0;
            }
        }
        return itemsTransferred;
    }

    protected int doKeepExact(IItemHandler itemHandler, IItemHandler myItemHandler, int maxTransferAmount) {
        int[] currentItemAmount = doCountDestinationInventoryItemsByMatchIndex(itemHandler, myItemHandler);
        int[] keepItemAmount = new int[currentItemAmount.length];
        for (int i = 0; i < keepItemAmount.length; i++) {
            int slotTransferLimit = itemFilterContainer.getSlotStackSize(i);
            if (slotTransferLimit == -1) {
                keepItemAmount[i] = transferStackSize;
            } else {
                keepItemAmount[i] = slotTransferLimit;
            }
        }
        int[] itemsToMove = calculateItemsToMove(currentItemAmount, keepItemAmount);
        int[] itemsMovedArray = doTransferItemsInternal(itemHandler, myItemHandler, maxTransferAmount, itemsToMove);
        return Arrays.stream(itemsMovedArray).sum();
    }

    private static int[] calculateItemsToMove(int[] currentItemAmount, int[] keepItemAmount) {
        int[] resultAmount = new int[currentItemAmount.length];
        for (int i = 0; i < resultAmount.length; i++) {
            int currentAmount = currentItemAmount[i];
            int minAmount = keepItemAmount[i];
            if (minAmount > currentAmount) {
                resultAmount[i] = minAmount - currentAmount;
            }
        }
        return resultAmount;
    }

    public void setTransferMode(TransferMode transferMode) {
        this.transferMode = transferMode;
        coverHolder.markDirty();
        itemFilterContainer.setMaxStackSize(transferMode.maxStackSize);
        this.transferStackSize = MathHelper.clamp(transferStackSize, 1, transferMode.maxStackSize);
    }

    public TransferMode getTransferMode() {
        return transferMode;
    }

    public void adjustTransferStackSize(int amount) {
        setTransferStackSize(transferStackSize + amount);
    }

    public void setTransferStackSize(int transferStackSize) {
        this.transferStackSize = MathHelper.clamp(transferStackSize, 1, transferMode.maxStackSize);
        coverHolder.markDirty();
    }

    public int getTransferStackSize() {
        return transferStackSize;
    }

    @Override
    protected String getUITitle() {
        return "cover.robotic_arm.title";
    }

    @Override
    protected ModularUI buildUI(Builder builder, EntityPlayer player) {
        WidgetGroup filterGroup = new WidgetGroup();
        filterGroup.addWidget(new CycleButtonWidget(91, 45, 75, 20,
            GTUtility.mapToString(TransferMode.values(), it -> it.localeName),
            () -> transferMode.ordinal(), (newMode) -> setTransferMode(TransferMode.values()[newMode]))
            .setTooltipHoverString("cover.robotic_arm.transfer_mode.description"));

        ServerWidgetGroup stackSizeGroup = new ServerWidgetGroup(() -> transferMode.maxStackSize > 1 && itemFilterContainer.getSlotStackSize(0) == -1);
        stackSizeGroup.addWidget(new ClickButtonWidget(91, 70, 20, 20, "-1", data -> adjustTransferStackSize(data.isShiftClick ? -10 : -1)));
        stackSizeGroup.addWidget(new ClickButtonWidget(146, 70, 20, 20, "+1", data -> adjustTransferStackSize(data.isShiftClick ? +10 : +1)));
        stackSizeGroup.addWidget(new ImageWidget(111, 70, 35, 20, GuiTextures.DISPLAY));
        stackSizeGroup.addWidget(new SimpleTextWidget(128, 80, "", 0xFFFFFF, () -> Integer.toString(transferStackSize)));

        return super.buildUI(builder.widget(filterGroup).widget(stackSizeGroup), player);
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
        TRANSFER_ANY("cover.robotic_arm.transfer_mode.transfer_any", 1),
        TRANSFER_EXACT("cover.robotic_arm.transfer_mode.transfer_exact", 64),
        KEEP_EXACT("cover.robotic_arm.transfer_mode.keep_exact", 1024);

        public final String localeName;
        public final int maxStackSize;

        TransferMode(String localeName, int maxStackSize) {
            this.localeName = localeName;
            this.maxStackSize = maxStackSize;
        }
    }
}
