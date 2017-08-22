package gregtech.api.gui.widgets;

import gregtech.api.capability.internal.ISimpleSlotInventory;
import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.Widget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;

public abstract class SlotWidget extends Widget<ISimpleSlotInventory> implements INativeWidget {

    protected Slot slotReference;

    private final int slotIndex;
    private final int xPosition;
    private final int yPosition;

    protected boolean canTakeItems;
    protected boolean canPutItems;

    public SlotWidget(int slotIndex, int xPosition, int yPosition, boolean canTakeItems, boolean canPutItems) {
        super(Widget.SLOT_DRAW_PRIORITY);
        this.slotIndex = slotIndex;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.canTakeItems = canTakeItems;
        this.canPutItems = canPutItems;
    }

    public SlotWidget(int slotIndex, int xPosition, int yPosition) {
        this(slotIndex, xPosition, yPosition, true, true);
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {}

    public boolean canPutStack(ItemStack stack) {
        return true;
    }

    public boolean canTakeStack(EntityPlayer player) {
        return true;
    }

    public boolean canBeHovered() {
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
        this.slotReference = new Slot(new IInventoryWrapper(gui.holder), slotIndex, xPosition, yPosition) {
            @Override
            public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
                super.onPickupFromSlot(playerIn, stack);
                SlotWidget.this.onPickupFromSlot(playerIn, stack);
            }

            @Override
            public boolean isItemValid(@Nullable ItemStack stack) {
                return SlotWidget.this.canPutStack(stack);
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn) {
                return SlotWidget.this.canTakeStack(playerIn);
            }

            @Override
            public void onSlotChanged() {
                SlotWidget.this.onSlotChanged();
            }

            @Override
            public boolean canBeHovered() {
                return SlotWidget.this.canBeHovered();
            }

            @Override
            public int getSlotStackLimit() {
                return gui.holder.getMaxStackSize(slotIndex);
            }

            @Override
            public boolean isHere(IInventory inv, int slotIn) {
                return inv.equals(inventory) && slotIn == slotIn; //Use equals instead of == to handle IInventoryWrappers properly
            }

            @Override
            public boolean isSameInventory(Slot other) {
                return other.inventory != null && other.inventory.equals(inventory); //Use equals instead of == to handle IInventoryWrappers properly
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
