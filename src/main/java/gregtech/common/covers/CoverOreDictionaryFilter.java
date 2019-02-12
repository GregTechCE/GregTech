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
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TextFieldWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.render.Textures;
import gregtech.api.unification.OreDictUnifier;
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

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class CoverOreDictionaryFilter extends CoverBehavior implements CoverWithUI {

    public static final Pattern ORE_DICTIONARY_FILTER = Pattern.compile("\\*?[a-zA-Z0-9_]*\\*?");

    protected String oreDictionaryFilter;
    protected ItemFilterMode filterMode = ItemFilterMode.FILTER_INSERT;
    protected ItemHandlerOreDictFiltered itemHandler;

    public CoverOreDictionaryFilter(ICoverable coverHolder, EnumFacing attachedSide) {
        super(coverHolder, attachedSide);
        this.oreDictionaryFilter = "";
    }

    protected void setOreDictionaryFilter(String oreDictionaryFilter) {
        this.oreDictionaryFilter = oreDictionaryFilter;
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
        if(!playerIn.world.isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup oreDictFilterGroup = new WidgetGroup();
        oreDictFilterGroup.addWidget(new LabelWidget(10, 5, "cover.ore_dictionary_filter.title"));
        oreDictFilterGroup.addWidget(new CycleButtonWidget(10, 20, 110, 20,
            GTUtility.mapToString(ItemFilterMode.values(), it -> it.localeName),
            () -> filterMode.ordinal(), (newMode) -> setFilterMode(ItemFilterMode.values()[newMode])));

        oreDictFilterGroup.addWidget(new LabelWidget(10, 45, "cover.ore_dictionary_filter.title1"));
        oreDictFilterGroup.addWidget(new LabelWidget(10, 55, "cover.ore_dictionary_filter.title2"));
        oreDictFilterGroup.addWidget(new TextFieldWidget(10, 70, 100, 12, true, () -> oreDictionaryFilter, this::setOreDictionaryFilter)
            .setMaxStringLength(64).setValidator(str -> ORE_DICTIONARY_FILTER.matcher(str).matches()));
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 90)
            .widget(oreDictFilterGroup)
            .build(this, player);
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox) {
        Textures.ORE_DICTIONARY_FILTER_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("OreDictionaryFilter", oreDictionaryFilter);
        tagCompound.setInteger("FilterMode", filterMode.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.oreDictionaryFilter = tagCompound.getString("OreDictionaryFilter");
        this.filterMode = ItemFilterMode.values()[tagCompound.getInteger("FilterMode")];
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler delegate = (IItemHandler) defaultValue;
            if(itemHandler == null || itemHandler.delegate != delegate) {
                this.itemHandler = new ItemHandlerOreDictFilteredImpl(delegate);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
        }
        return defaultValue;
    }

    private class ItemHandlerOreDictFilteredImpl extends ItemHandlerOreDictFiltered {

        public ItemHandlerOreDictFilteredImpl(IItemHandler delegate) {
            super(delegate);
        }

        @Override
        public ItemFilterMode getFilterMode() {
            return filterMode;
        }

        @Override
        public String getOreDictName() {
            return oreDictionaryFilter;
        }
    }

    public static abstract class ItemHandlerOreDictFiltered extends ItemHandlerDelegate {

        public ItemHandlerOreDictFiltered(IItemHandler delegate) {
            super(delegate);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            ItemFilterMode filterMode = getFilterMode();
            if (filterMode == ItemFilterMode.FILTER_EXTRACT) {
                return stack;
            } else if (oreDictionaryFilterMatch(getOreDictName(), stack) == -1) {
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
            if(oreDictionaryFilterMatch(getOreDictName(), result) == -1) {
                return ItemStack.EMPTY;
            }
            if(!simulate) {
                super.extractItem(slot, amount, false);
            }
            return result;
        }

        public abstract ItemFilterMode getFilterMode();

        public abstract String getOreDictName();
    }

    public static int oreDictionaryFilterMatch(String oreDictionaryFilter, ItemStack itemStack) {
        if(oreDictionaryFilter.isEmpty()) {
            return -1;
        }
        boolean startWildcard = oreDictionaryFilter.charAt(0) == '*';
        boolean endWildcard = oreDictionaryFilter.charAt(oreDictionaryFilter.length() - 1) == '*';
        if(startWildcard) {
            oreDictionaryFilter = oreDictionaryFilter.substring(1);
        }
        if(endWildcard) {
            oreDictionaryFilter = oreDictionaryFilter.substring(0, oreDictionaryFilter.length() - 1);
        }
        for(String stackOreName : OreDictUnifier.getOreDictionaryNames(itemStack)) {
            if(areOreDictNamesEqual(startWildcard, endWildcard, oreDictionaryFilter, stackOreName)) {
                return 0;
            }
        }
        return -1;
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
}
