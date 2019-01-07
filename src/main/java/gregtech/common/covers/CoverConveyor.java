package gregtech.common.covers;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

public class CoverConveyor extends CoverBehavior implements CoverWithUI, ITickable {

    private static final Pattern ORE_DICTIONARY_FILTER = Pattern.compile("\\*?[a-zA-Z0-9_]*\\*?");

    private final int tier;
    private final int maxItemTransferRate;
    private int transferRate;
    private ConveyorMode conveyorMode;
    private ItemStackHandler filterTypeInventory;
    private FilterMode filterMode;
    private String oreDictionaryFilter;
    private ItemStackHandler itemFilterSlots;
    private boolean ignoreDamage = true;
    private boolean ignoreNBTData = true;

    public CoverConveyor(MetaTileEntity metaTileEntity, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(metaTileEntity, attachedSide);
        this.tier = tier;
        this.maxItemTransferRate = itemsPerSecond;
        this.transferRate = maxItemTransferRate;
        this.conveyorMode = ConveyorMode.IMPORT;
        this.filterTypeInventory = new FilterItemStackHandler();
        this.filterMode = FilterMode.NONE;
        this.oreDictionaryFilter = "";
        this.itemFilterSlots = new ItemStackHandler(9) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    public void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        metaTileEntity.markDirty();
    }

    public void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxItemTransferRate));
    }

    public void setConveyorMode(ConveyorMode conveyorMode) {
        this.conveyorMode = conveyorMode;
        metaTileEntity.markDirty();
    }

    public void setIgnoreDamage(boolean ignoreDamage) {
        this.ignoreDamage = ignoreDamage;
        metaTileEntity.markDirty();
    }

    public void setIgnoreNBTData(boolean ignoreNBTData) {
        this.ignoreNBTData = ignoreNBTData;
        metaTileEntity.markDirty();
    }

    public void setOreDictionaryFilter(String filter) {
        this.oreDictionaryFilter = filter;
        metaTileEntity.markDirty();
    }

    @Override
    public void update() {
        TileEntity tileEntity = metaTileEntity.getWorld().getTileEntity(metaTileEntity.getPos().offset(attachedSide));
        IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
        IItemHandler myItemHandler = metaTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide);
        if(itemHandler == null || myItemHandler == null) {
            return;
        }
        if(conveyorMode == ConveyorMode.IMPORT) {
            moveInventoryItems(itemHandler, myItemHandler);
        } else if(conveyorMode == ConveyorMode.EXPORT) {
            moveInventoryItems(myItemHandler, itemHandler);
        }
    }

    protected void moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory) {
        int itemsLeftToTransfer = transferRate;
        for(int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, itemsLeftToTransfer, true);
            if(sourceStack.isEmpty() || !filterMode.matcher.test(this, sourceStack)) {
                continue;
            }
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, true);
            int amountToInsert = sourceStack.getCount() - remainder.getCount();
            if(amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, false);
                itemsLeftToTransfer -= sourceStack.getCount();
                if(itemsLeftToTransfer == 0) break;
            }
        }
    }

    @Override
    public boolean canAttach() {
        return metaTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide) != null;
    }

    @Override
    public void onRemoved() {
        NonNullList<ItemStack> drops = NonNullList.create();
        MetaTileEntity.clearInventory(drops, filterTypeInventory);
        for(ItemStack itemStack : drops) {
            Block.spawnAsEntity(metaTileEntity.getWorld(), metaTileEntity.getPos(), itemStack);
        }
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.CONVEYOR_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, float hitX, float hitY, float hitZ) {
        if(!metaTileEntity.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, "cover.conveyor.title", GTValues.VN[tier]));
        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustTransferRate(data.isShiftClick ? -100 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustTransferRate(data.isShiftClick ? +100 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(88, 30, "cover.conveyor.transfer_rate", 0xFFFFFF, () -> Integer.toString(transferRate)));

        primaryGroup.addWidget(new CycleButtonWidget(10, 45, 100, 20, ConveyorMode.getLocaleNames(),
            () -> conveyorMode.ordinal(), newMode -> setConveyorMode(ConveyorMode.values()[newMode])));
        primaryGroup.addWidget(new LabelWidget(10, 70, "cover.conveyor.item_filter.title"));
        primaryGroup.addWidget(new SlotWidget(filterTypeInventory, 0, 10, 85)
            .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FILTER_SLOT_OVERLAY));

        ServerWidgetGroup itemFilterGroup = new ServerWidgetGroup(() -> filterMode == FilterMode.ITEM_FILTER);
        for(int i = 0; i < 9; i++) {
            itemFilterGroup.addWidget(new PhantomSlotWidget(itemFilterSlots, i, 10 + 18 * (i % 3), 106 + 18 * (i / 3))
                .setBackgroundTexture(GuiTextures.SLOT));
        }
        itemFilterGroup.addWidget(new ToggleButtonWidget(74, 105, 20, 20, GuiTextures.BUTTON_FILTER_DAMAGE, () -> ignoreDamage, this::setIgnoreDamage));
        itemFilterGroup.addWidget(new ToggleButtonWidget(99, 105, 20, 20, GuiTextures.BUTTON_FILTER_NBT, () -> ignoreNBTData, this::setIgnoreNBTData));

        ServerWidgetGroup oreDictFilterGroup = new ServerWidgetGroup(() -> filterMode == FilterMode.ORE_DICTIONARY_FILTER);
        oreDictFilterGroup.addWidget(new LabelWidget(10, 106, "cover.conveyor.ore_dictionary.title"));
        oreDictFilterGroup.addWidget(new LabelWidget(10, 116, "cover.conveyor.ore_dictionary.title2"));
        oreDictFilterGroup.addWidget(new TextFieldWidget(10, 126, 100, 12, true, () -> oreDictionaryFilter, this::setOreDictionaryFilter)
            .setMaxStringLength(64).setValidator(str -> ORE_DICTIONARY_FILTER.matcher(str).matches()));

        return ModularUI.builder(GuiTextures.BACKGROUND_EXTENDED, 176, 198)
            .widget(primaryGroup)
            .widget(itemFilterGroup)
            .widget(oreDictFilterGroup)
            .bindPlayerHotbar(player.inventory, GuiTextures.SLOT, 8, 170)
            .build(this, player);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferRate", transferRate);
        tagCompound.setInteger("ConveyorMode", conveyorMode.ordinal());
        tagCompound.setTag("FilterInventory", filterTypeInventory.serializeNBT());
        tagCompound.setTag("ItemFilter", itemFilterSlots.serializeNBT());
        tagCompound.setString("OreDictionaryFilter", oreDictionaryFilter);
        tagCompound.setBoolean("IgnoreDamage", ignoreDamage);
        tagCompound.setBoolean("IgnoreNBT", ignoreNBTData);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        this.conveyorMode = ConveyorMode.values()[tagCompound.getInteger("ConveyorMode")];
        this.filterTypeInventory.deserializeNBT(tagCompound.getCompoundTag("FilterInventory"));
        this.itemFilterSlots.deserializeNBT(tagCompound.getCompoundTag("ItemFilter"));
        this.oreDictionaryFilter = tagCompound.getString("OreDictionaryFilter");
        this.ignoreDamage = tagCompound.getBoolean("IgnoreDamage");
        this.ignoreNBTData = tagCompound.getBoolean("IgnoreNBT");
    }

    public enum ConveyorMode {
        IMPORT("cover.conveyor.mode.import"),
        EXPORT("cover.conveyor.mode.export");

        public final String localeName;

        ConveyorMode(String localeName) {
            this.localeName = localeName;
        }

        public static String[] getLocaleNames() {
            ConveyorMode[] values = values();
            String[] names = new String[values.length];
            for(int i = 0; i < values.length; i++) {
                names[i] = values[i].localeName;
            }
            return names;
        }
    }

    public enum FilterMode {
        NONE((cover, stack) -> true),
        ITEM_FILTER(CoverConveyor::itemFilterMatch),
        ORE_DICTIONARY_FILTER(CoverConveyor::oreDictionaryFilterMatch);

        public final BiPredicate<CoverConveyor, ItemStack> matcher;

        FilterMode(BiPredicate<CoverConveyor, ItemStack> matcher) {
            this.matcher = matcher;
        }
    }

    private class FilterItemStackHandler extends ItemStackHandler {

        public FilterItemStackHandler() {
            super(1);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!MetaItems.ITEM_FILTER.isItemEqual(stack) &&
                !MetaItems.ORE_DICTIONARY_FILTER.isItemEqual(stack)) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onLoad() {
            onContentsChanged(0);
        }

        @Override
        protected void onContentsChanged(int slot) {
            ItemStack itemStack = getStackInSlot(slot);
            CoverConveyor.this.filterMode = getFilterMode(itemStack);
        }

        private FilterMode getFilterMode(ItemStack itemStack) {
            if(itemStack.isEmpty()) {
                return FilterMode.NONE;
            } else if(MetaItems.ITEM_FILTER.isItemEqual(itemStack)) {
                return FilterMode.ITEM_FILTER;
            } else if(MetaItems.ORE_DICTIONARY_FILTER.isItemEqual(itemStack)) {
                return FilterMode.ORE_DICTIONARY_FILTER;
            } else return FilterMode.NONE;
        }
    }

    private static boolean oreDictionaryFilterMatch(CoverConveyor cover, ItemStack itemStack) {
        String oreDictName = cover.oreDictionaryFilter;
        if(oreDictName.isEmpty()) {
            return false;
        }
        boolean startWildcard = oreDictName.charAt(0) == '*';
        boolean endWildcard = oreDictName.charAt(oreDictName.length() - 1) == '*';
        if(startWildcard) {
            oreDictName = oreDictName.substring(1);
        }
        if(endWildcard) {
            oreDictName = oreDictName.substring(0, oreDictName.length() - 1);
        }
        for(String stackOreName : OreDictUnifier.getOreDictionaryNames(itemStack)) {
            if(areOreDictNamesEqual(startWildcard, endWildcard, oreDictName, stackOreName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean itemFilterMatch(CoverConveyor cover, ItemStack itemStack) {
        IItemHandler itemHandler = cover.itemFilterSlots;
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack filterStack = itemHandler.getStackInSlot(i);
            if(!filterStack.isEmpty() && areItemsEqual(cover, filterStack, itemStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean areOreDictNamesEqual(boolean startWildcard, boolean endWildcard, String oreDictName, String stackOreName) {
        if(startWildcard && endWildcard) {
            return stackOreName.contains(oreDictName);
        } else if(startWildcard) {
            return stackOreName.endsWith(oreDictName);
        } else if(endWildcard) {
            return stackOreName.startsWith(oreDictName);
        } else {
            return stackOreName.equals(oreDictName);
        }
    }

    private static boolean areItemsEqual(CoverConveyor cover, ItemStack filterStack, ItemStack itemStack) {
        if(cover.ignoreDamage) {
            if(!filterStack.isItemEqualIgnoreDurability(itemStack)) {
                return false;
            }
        } else if (!filterStack.isItemEqual(itemStack)) {
            return false;
        }
        return cover.ignoreNBTData || ItemStack.areItemStackTagsEqual(filterStack, itemStack);
    }
}
