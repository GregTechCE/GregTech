package gregtech.api.gui.widgets;

import gregtech.api.gui.INativeWidget;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotWidget<T extends IUIHolder> extends Widget<T> implements INativeWidget {

    protected Slot slotReference;

    public final IItemHandlerModifiable itemHandler;

    public final int slotIndex;
    public final int xPosition;
    public final int yPosition;

    protected boolean canTakeItems;
    protected boolean canPutItems;

    protected TextureArea[] backgroundTexture;

    public SlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition, boolean canTakeItems, boolean canPutItems) {
        super(Widget.SLOT_DRAW_PRIORITY);
        this.itemHandler = itemHandler;
        this.slotIndex = slotIndex;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.canTakeItems = canTakeItems;
        this.canPutItems = canPutItems;
    }

    @Override
    public void detectAndSendChanges() {
    }

    public SlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition) {
        this(itemHandler, slotIndex, xPosition, yPosition, true, true);
    }

    /**
     * Sets array of background textures used by slot
     * they are drawn on top of each other
     */
    public SlotWidget<T> setBackgroundTexture(TextureArea... backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        return this;
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

    public void onSlotChanged() {
        gui.holder.markDirty();
    }

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
            public boolean isItemValid(@Nonnull ItemStack stack) {
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
    @SideOnly(Side.CLIENT)
    public void drawInBackground(float partialTicks, int mouseX, int mouseY) {
        if(backgroundTexture != null) {
            for(TextureArea backgroundTexture : this.backgroundTexture) {
                backgroundTexture.draw(this.xPosition - 1, this.yPosition - 1, 18, 18);
            }
        }
    }

}
