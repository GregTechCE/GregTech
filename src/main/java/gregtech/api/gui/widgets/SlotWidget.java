package gregtech.api.gui.widgets;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import gregtech.api.metatileentity.IMetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public abstract class SlotWidget<T extends IUIHolder> extends Widget<T> implements INativeWidget {

    protected Slot slotReference;

    protected IItemHandler itemHandler;

    private final int slotIndex;
    private final int xPosition;
    private final int yPosition;

    protected boolean canTakeItems;
    protected boolean canPutItems;

    public SlotWidget(IItemHandler itemHandler, int slotIndex, int xPosition, int yPosition, boolean canTakeItems, boolean canPutItems) {
        super(Widget.SLOT_DRAW_PRIORITY);
        this.itemHandler = itemHandler;
        this.slotIndex = slotIndex;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.canTakeItems = canTakeItems;
        this.canPutItems = canPutItems;
    }

    public SlotWidget(IItemHandler itemHandler, int slotIndex, int xPosition, int yPosition) {
        this(itemHandler, slotIndex, xPosition, yPosition, true, true);
    }

    public boolean canPutStack(ItemStack stack) {
        return canPutItems;
    }

    public boolean canTakeStack(EntityPlayer player) {
        return canTakeItems;
    }

    public boolean isEnabled() {
        return true;
    }

    public void onSlotChanged() {}

    @Override
    public boolean canMergeSlot(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack slotClick(int dragType, ClickType clickTypeIn, EntityPlayer player) {
        return INativeWidget.VANILLA_LOGIC;
    }

    @Override
    public final Slot allocateSlotHandle() {
       return slotReference;
    }

    @Override
    public void initWidget() {
        this.slotReference = new SlotItemHandler(itemHandler, slotIndex, xPosition, yPosition) {

            @Override
            public boolean isItemValid(ItemStack stack) {
                return SlotWidget.this.canPutStack(stack) && super.isItemValid(stack);
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn) {
                return SlotWidget.this.canTakeStack(playerIn) && super.canTakeStack(playerIn);
            }

            @Override
            public void onSlotChanged() {
                SlotWidget.this.onSlotChanged();
            }

            @Override
            public boolean isEnabled() {
                return SlotWidget.this.isEnabled();
            }
        };
    }

    @Override
    public void draw(int mouseX, int mouseY) {
    }

    @Override
    public void writeInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    public void readInitialSyncInfo(PacketBuffer buffer) {
    }

    @Override
    public void readUpdateInfo(PacketBuffer buffer) {
    }

}
