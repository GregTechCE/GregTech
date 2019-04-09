package gregtech.common.covers;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.ItemHandlerDelegate;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CoverItemFilter extends CoverBehavior implements CoverWithUI {

    protected ItemStackHandler itemFilterSlots;
    protected boolean ignoreDamage = true;
    protected boolean ignoreNBT = true;
    protected ItemFilterMode filterMode = ItemFilterMode.FILTER_INSERT;
    protected ItemHandlerFiltered itemHandler;

    public CoverItemFilter(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.itemFilterSlots = new ItemStackHandler(9) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    protected void setIgnoreDamage(boolean ignoreDamage) {
        this.ignoreDamage = ignoreDamage;
        coverHolder.markDirty();
    }

    protected void setIgnoreNBT(boolean ignoreNBT) {
        this.ignoreNBT = ignoreNBT;
        coverHolder.markDirty();
    }

    protected void setFilterMode(ItemFilterMode filterMode) {
        this.filterMode = filterMode;
        coverHolder.markDirty();
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide) != null;
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!playerIn.world.isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup itemFilterGroup = new WidgetGroup();
        itemFilterGroup.addWidget(new LabelWidget(10, 5, "cover.item_filter.title"));
        itemFilterGroup.addWidget(new CycleButtonWidget(10, 20, 110, 20,
            GTUtility.mapToString(ItemFilterMode.values(), it -> it.localeName),
            () -> filterMode.ordinal(), (newMode) -> setFilterMode(ItemFilterMode.values()[newMode])));
        for (int i = 0; i < 9; i++) {
            itemFilterGroup.addWidget(new PhantomSlotWidget(itemFilterSlots, i, 10 + 18 * (i % 3), 46 + 18 * (i / 3)).setBackgroundTexture(GuiTextures.SLOT));
        }
        itemFilterGroup.addWidget(new ToggleButtonWidget(74, 45, 20, 20, GuiTextures.BUTTON_FILTER_DAMAGE, () -> ignoreDamage, this::setIgnoreDamage));
        itemFilterGroup.addWidget(new ToggleButtonWidget(99, 45, 20, 20, GuiTextures.BUTTON_FILTER_NBT, () -> ignoreNBT, this::setIgnoreNBT));

        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 128)
            .widget(itemFilterGroup)
            .bindPlayerHotbar(player.inventory, GuiTextures.SLOT, 8, 105)
            .build(this, player);
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.ITEM_FILTER_FILTER_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("FilterInventory", itemFilterSlots.serializeNBT());
        tagCompound.setBoolean("IgnoreDamage", ignoreDamage);
        tagCompound.setBoolean("IgnoreNBT", ignoreNBT);
        tagCompound.setInteger("FilterMode", filterMode.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.itemFilterSlots.deserializeNBT(tagCompound.getCompoundTag("FilterInventory"));
        this.ignoreDamage = tagCompound.getBoolean("IgnoreDamage");
        this.ignoreNBT = tagCompound.getBoolean("IgnoreNBT");
        this.filterMode = ItemFilterMode.values()[tagCompound.getInteger("FilterMode")];
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler delegate = (IItemHandler) defaultValue;
            if (itemHandler == null || itemHandler.delegate != delegate) {
                this.itemHandler = new ItemHandlerFilteredImpl(delegate);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return defaultValue;
    }

    private class ItemHandlerFilteredImpl extends ItemHandlerFiltered {

        public ItemHandlerFilteredImpl(IItemHandler delegate) {
            super(delegate);
        }

        @Override
        public ItemFilterMode getFilterMode() {
            return filterMode;
        }

        @Override
        public IItemHandler getFilterSlots() {
            return itemFilterSlots;
        }

        @Override
        public boolean isIgnoreDamage() {
            return ignoreDamage;
        }

        @Override
        public boolean isIgnoreNBT() {
            return ignoreNBT;
        }
    }

    public static abstract class ItemHandlerFiltered extends ItemHandlerDelegate {

        public ItemHandlerFiltered(IItemHandler delegate) {
            super(delegate);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            ItemFilterMode filterMode = getFilterMode();
            if (filterMode == ItemFilterMode.FILTER_EXTRACT) {
                return stack;
            } else if (itemFilterMatch(getFilterSlots(), isIgnoreDamage(), isIgnoreNBT(), stack) == -1) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemFilterMode filterMode = getFilterMode();
            if (filterMode == ItemFilterMode.FILTER_INSERT) {
                return ItemStack.EMPTY;
            }
            ItemStack result = super.extractItem(slot, amount, true);
            if (itemFilterMatch(getFilterSlots(), isIgnoreDamage(), isIgnoreNBT(), result) == -1) {
                return ItemStack.EMPTY;
            }
            if (!simulate) {
                super.extractItem(slot, amount, false);
            }
            return result;
        }

        public abstract ItemFilterMode getFilterMode();

        public abstract IItemHandler getFilterSlots();

        public abstract boolean isIgnoreDamage();

        public abstract boolean isIgnoreNBT();
    }

    public static int itemFilterMatch(IItemHandler filterSlots, boolean ignoreDamage, boolean ignoreNBTData, ItemStack itemStack) {
        for (int i = 0; i < filterSlots.getSlots(); i++) {
            ItemStack filterStack = filterSlots.getStackInSlot(i);
            if (!filterStack.isEmpty() && areItemsEqual(ignoreDamage, ignoreNBTData, filterStack, itemStack)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean areItemsEqual(boolean ignoreDamage, boolean ignoreNBTData, ItemStack filterStack, ItemStack itemStack) {
        if (ignoreDamage) {
            if (!filterStack.isItemEqualIgnoreDurability(itemStack)) {
                return false;
            }
        } else if (!filterStack.isItemEqual(itemStack)) {
            return false;
        }
        return ignoreNBTData || ItemStack.areItemStackTagsEqual(filterStack, itemStack);
    }
}
