package gregtech.common.gui.widget;

import gregtech.api.capability.impl.ItemHandlerDelegate;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.common.metatileentities.storage.CraftingRecipeResolver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class CraftingSlotWidget extends SlotWidget {

    private CraftingRecipeResolver recipeResolver;
    private boolean canTakeStack = false;

    public CraftingSlotWidget(CraftingRecipeResolver recipeResolver, int slotIndex, int xPosition, int yPosition) {
        //pass delegate here to avoid using IItemHandlerModifiable-related tricks by SlotItemHandler
        super(createItemHandler(recipeResolver), slotIndex, xPosition, yPosition, true, false);
        this.recipeResolver = recipeResolver;
    }

    private static IItemHandler createItemHandler(CraftingRecipeResolver resolver) {
        return new ItemHandlerDelegate(resolver == null ? new ItemStackHandler(1) : resolver.getCraftingResultInventory());
    }

    @Override
    protected SlotItemHandler createSlot(IItemHandler itemHandler, int index, int x, int y) {
        return new WidgetSlotDelegate(itemHandler, index, x, y) {
            @Override
            public void putStack(@Nonnull ItemStack stack) {
                if (stack.isEmpty()) return;
                ((IItemHandlerModifiable) ((ItemHandlerDelegate) itemHandler).delegate).setStackInSlot(index, stack);
            }
        };
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (recipeResolver == null) {
            return;
        }
        boolean isRecipeValid = recipeResolver.checkRecipeValid();
        if (isRecipeValid != canTakeStack) {
            this.canTakeStack = isRecipeValid;
            writeUpdateInfo(1, buf -> buf.writeBoolean(canTakeStack));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.canTakeStack = buffer.readBoolean();
        }
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        if (!super.canTakeStack(player)) {
            return false;
        }
        if (recipeResolver == null) {
            return canTakeStack;
        }
        return recipeResolver.checkRecipeValid();
    }

    @Override
    protected ItemStack onItemTake(EntityPlayer thePlayer, ItemStack stack) {
        if (recipeResolver == null) {
            return canTakeStack ? stack : ItemStack.EMPTY;
        }
        if (!recipeResolver.checkRecipeValid()) {
            return ItemStack.EMPTY;
        }
        recipeResolver.performRecipe(thePlayer);
        recipeResolver.handlePostItemCraft(stack, thePlayer);
        recipeResolver.checkRecipeValid();
        return stack;
    }
}
