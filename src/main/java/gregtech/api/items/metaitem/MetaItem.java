package gregtech.api.items.metaitem;

import com.google.common.collect.ImmutableList;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.impl.CombinedCapabilityProvider;
import gregtech.api.capability.impl.ElectricItem;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.RenderUtil;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.*;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.util.*;

/**
 * MetaItem is item that can have up to Short.MAX_VALUE items inside one id.
 * These items even can be edible, have custom behaviours, be electric or act like fluid containers!
 * They can also have different burn time, plus be handheld, oredicted, no-unificated or invisible!
 * They also can be reactor components.
 *
 * You can also extend this class and occupy some of it's MetaData, and just pass an meta offset in constructor, and everything will work properly.
 *
 * Items are added in MetaItem via {@link #addItem(int, String)}. You will get {@link MetaValueItem} instance, which you can configure in builder-alike pattern:
 * {@code addItem(0, "test_item").addStats(new ElectricStats(10000, 1,  false)) }
 * This will add single-use (not rechargeable) LV battery with initial capacity 10000 EU
 */
@SuppressWarnings("deprecation")
public abstract class MetaItem<T extends MetaItem<?>.MetaValueItem> extends Item implements ItemUIFactory {

    private static final List<MetaItem<?>> META_ITEMS = new ArrayList<>();

    public static List<MetaItem<?>> getMetaItems() {
        return Collections.unmodifiableList(META_ITEMS);
    }

    protected TShortObjectMap<T> metaItems = new TShortObjectHashMap<>();
    private Map<String, T> names = new HashMap<>();
    protected TShortObjectMap<ModelResourceLocation> metaItemsModels = new TShortObjectHashMap<>();
    protected TShortObjectHashMap<ModelResourceLocation[]> specialItemsModels = new TShortObjectHashMap<>();
    private static final ModelResourceLocation MISSING_LOCATION = new ModelResourceLocation("builtin/missing", "inventory");

    protected final short metaItemOffset;

    public MetaItem(short metaItemOffset) {
        setUnlocalizedName("meta_item");
        setHasSubtypes(true);
        this.metaItemOffset = metaItemOffset;
        META_ITEMS.add(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerColor() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(this::getColorForItemStack, this);
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for(short itemMetaKey : metaItems.keys()) {
            T metaValueItem = metaItems.get(itemMetaKey);
            int numberOfModels = metaValueItem.getModelAmount();
            if (numberOfModels > 1) {
                ModelResourceLocation[] resourceLocations = new ModelResourceLocation[numberOfModels];
                for (int i = 0; i < resourceLocations.length; i++) {
                    ResourceLocation resourceLocation = new ResourceLocation(GTValues.MODID, formatModelPath(metaValueItem) + "/" + (i + 1));
                    ModelBakery.registerItemVariants(this, resourceLocation);
                    resourceLocations[i] = new ModelResourceLocation(resourceLocation, "inventory");
                }
                specialItemsModels.put((short) (metaItemOffset + itemMetaKey), resourceLocations);
                continue;
            }
            ResourceLocation resourceLocation = new ResourceLocation(GTValues.MODID, formatModelPath(metaValueItem));
            ModelBakery.registerItemVariants(this, resourceLocation);
            metaItemsModels.put((short) (metaItemOffset + itemMetaKey), new ModelResourceLocation(resourceLocation, "inventory"));
        }

        ModelLoader.setCustomMeshDefinition(this, itemStack -> {
            short itemDamage = formatRawItemDamage((short) itemStack.getItemDamage());
            if(specialItemsModels.containsKey(itemDamage)) {
                int modelIndex = getModelIndex((short) (itemDamage - metaItemOffset), itemStack);
                return specialItemsModels.get(itemDamage)[modelIndex];
            }
            if(metaItemsModels.containsKey(itemDamage)) {
                return metaItemsModels.get(itemDamage);
            }
            return MISSING_LOCATION;
        });
    }

    protected String formatModelPath(T metaValueItem) {
        return "metaitems/" + metaValueItem.unlocalizedName;
    }

    protected int getModelIndex(short metaItemKey, ItemStack itemStack) {
        IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        long itemCharge = electricItem.discharge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
        return (int) Math.min(((itemCharge / (electricItem.getMaxCharge() * 1.0)) * 7), 7);
    }

    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        IFluidHandlerItem fluidContainerItem = ItemHandlerHelper.copyStackWithSize(stack, 1)
            .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if(tintIndex == 0 && fluidContainerItem != null) {
            FluidStack fluidStack = fluidContainerItem.drain(Integer.MAX_VALUE, false);
            return fluidStack == null ? 0x666666 : RenderUtil.getFluidColor(fluidStack);
        }
        return 0xFFFFFF;
    }

    protected abstract T constructMetaValueItem(short metaValue, String unlocalizedName);

    public final T addItem(int metaValue, String unlocalizedName) {
        Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, metaValue + metaItemOffset, "MetaItem ID should be in range from 0 to Short.MAX_VALUE-1");
        T metaValueItem = constructMetaValueItem((short) metaValue, unlocalizedName);
        metaItems.put((short) metaValue, metaValueItem);
        names.put(unlocalizedName, metaValueItem);
        return metaValueItem;
    }

    public final Collection<T> getAllItems() {
        return Collections.unmodifiableCollection(metaItems.valueCollection());
    }

    public final T getItem(short metaValue) {
        return metaItems.get(formatRawItemDamage(metaValue));
    }

    public final T getItem(String valueName) {
        return names.get(valueName);
    }

    public final T getItem(ItemStack itemStack) {
        return getItem((short) (itemStack.getItemDamage() - metaItemOffset));
    }

    protected short formatRawItemDamage(short metaValue) {
        return metaValue;
    }

    public void registerSubItems() {}

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        T metaValueItem = getItem(stack);
        if (metaValueItem == null) {
            return null;
        }
        ArrayList<ICapabilityProvider> providers = new ArrayList<>();
        for(IMetaItemStats metaItemStats : metaValueItem.getAllStats()) {
            if(metaItemStats instanceof IItemCapabilityProvider) {
                IItemCapabilityProvider provider = (IItemCapabilityProvider) metaItemStats;
                providers.add(provider.createProvider(stack));
            }
        }
        return new CombinedCapabilityProvider(providers);
    }

    //////////////////////////////////////////////////////////////////

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if (metaValueItem == null) {
            return super.getItemBurnTime(itemStack);
        }
        return metaValueItem.getBurnValue();
    }

    //////////////////////////////////////////////////////////////////
    //      Behaviours and Use Manager Implementation               //
    //////////////////////////////////////////////////////////////////

    private IItemUseManager getUseManager(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if (metaValueItem == null) {
            return null;
        }
        return metaValueItem.getUseManager();
    }

    private IItemDurabilityManager getDurabilityManager(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if (metaValueItem == null) {
            return null;
        }
        return metaValueItem.getDurabilityManager();
    }

    public List<IItemBehaviour> getBehaviours(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if (metaValueItem == null) {
            return ImmutableList.of();
        }
        return metaValueItem.getBehaviours();
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        T metaValueItem = getItem(stack);
        if (metaValueItem == null) {
            return 64;
        }
        return metaValueItem.getMaxStackSize();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        IItemUseManager useManager = getUseManager(stack);
        if (useManager != null) {
            return useManager.getUseAction(stack);
        }
        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        IItemUseManager useManager = getUseManager(stack);
        if (useManager != null) {
            return useManager.getMaxItemUseDuration(stack);
        }
        return 0;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if (useManager != null) {
                useManager.onItemUsingTick(stack, (EntityPlayer) player, count);
            }
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
        if (player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if (useManager != null) {
                useManager.onPlayerStoppedItemUsing(stack, (EntityPlayer) player, timeLeft);
            }
        }
    }

    @Nullable
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
        if (player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if (useManager != null) {
                return useManager.onItemUseFinish(stack, (EntityPlayer) player);
            }
        }
        return stack;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        boolean returnValue = false;
        for (IItemBehaviour behaviour : getBehaviours(stack)) {
            if (behaviour.onLeftClickEntity(stack, player, entity)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        for (IItemBehaviour behaviour : getBehaviours(itemStack)) {
            ActionResult<ItemStack> behaviourResult = behaviour.onItemRightClick(world, player, hand);
            itemStack = behaviourResult.getResult();
            if (behaviourResult.getType() != EnumActionResult.PASS) {
                return ActionResult.newResult(behaviourResult.getType(), itemStack);
            } else if (itemStack.isEmpty()) {
                return ActionResult.newResult(EnumActionResult.PASS, ItemStack.EMPTY);
            }
        }
        IItemUseManager useManager = getUseManager(itemStack);
        if (useManager != null && useManager.canStartUsing(itemStack, player)) {
            useManager.onItemUseStart(itemStack, player);
            player.setActiveHand(hand);
            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
        }
        return ActionResult.newResult(EnumActionResult.PASS, itemStack);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        for (IItemBehaviour behaviour : getBehaviours(itemStack)) {
            EnumActionResult behaviourResult = behaviour.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
            if (behaviourResult != EnumActionResult.PASS) {
                return behaviourResult;
            } else if (itemStack.isEmpty()) {
                return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack originalStack = stack.copy();
        for (IItemBehaviour behaviour : getBehaviours(stack)) {
            ActionResult<ItemStack> behaviourResult = behaviour.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
            stack = behaviourResult.getResult();
            if (behaviourResult.getType() != EnumActionResult.PASS) {
                if (!ItemStack.areItemStacksEqual(originalStack, stack))
                    player.setHeldItem(hand, stack);
                return behaviourResult.getType();
            } else if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.getItemDamage() >= metaItemOffset) {
            T item = getItem(stack);
            IFluidHandlerItem fluidHandlerItem = ItemHandlerHelper.copyStackWithSize(stack, 1)
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if(fluidHandlerItem != null) {
                FluidStack fluidInside = fluidHandlerItem.drain(Integer.MAX_VALUE, false);
                String name = fluidInside == null ? "metaitem.fluid_cell.empty" : fluidInside.getUnlocalizedName();
                return I18n.format("metaitem." + item.unlocalizedName + ".name", I18n.format(name));
            }
            return I18n.format("metaitem." + item.unlocalizedName + ".name");
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        T item = getItem(itemStack);
        if (item != null) {
            String unlocalizedTooltip = "metaitem." + item.unlocalizedName + ".tooltip";
            if (I18n.hasKey(unlocalizedTooltip)) {
                lines.addAll(Arrays.asList(I18n.format(unlocalizedTooltip).split("/n")));
            }

            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if (electricItem != null) {
                lines.add(I18n.format("metaitem.generic.electric_item.tooltip",
                    electricItem.discharge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false, true),
                    electricItem.getMaxCharge(),
                    electricItem.getTier()));
            }

            IFluidHandlerItem fluidHandler = ItemHandlerHelper.copyStackWithSize(itemStack, 1)
                .getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (fluidHandler != null) {
                IFluidTankProperties fluidTankProperties = fluidHandler.getTankProperties()[0];
                FluidStack fluid = fluidTankProperties.getContents();
                if (fluid != null) {
                    lines.add(I18n.format("metaitem.generic.fluid_container.tooltip",
                        fluid.amount,
                        fluidTankProperties.getCapacity(),
                        fluid.getLocalizedName()));
                } else lines.add(I18n.format("metaitem.generic.fluid_container.tooltip_empty"));
            }

            for (IItemBehaviour behaviour : getBehaviours(itemStack)) {
                behaviour.addInformation(itemStack, lines);
            }
        }
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] {GregTechAPI.TAB_GREGTECH, GregTechAPI.TAB_GREGTECH_MATERIALS};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if(tab != GregTechAPI.TAB_GREGTECH && tab != CreativeTabs.SEARCH)
            return;

        for (T enabledItem : metaItems.valueCollection()) {
            if (!enabledItem.isVisible())
                continue;

            ItemStack itemStack = enabledItem.getStackForm();
            subItems.add(itemStack.copy());

            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if (electricItem != null) {
                electricItem.charge(Long.MAX_VALUE, Integer.MAX_VALUE, true, false);
                subItems.add(itemStack);
            }

            if (tab == CreativeTabs.SEARCH && itemStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
                for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                    ItemStack containerStack = itemStack.copy();
                    IFluidHandlerItem fluidContainer = containerStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                    fluidContainer.fill(new FluidStack(fluid, Integer.MAX_VALUE), true);
                    if (fluidContainer.drain(Integer.MAX_VALUE, false) == null)
                        continue; //do not add empty containers multiple times
                    subItems.add(fluidContainer.getContainer());
                }
            }

        }
    }

    @Override
    public ModularUI createUI(PlayerInventoryHolder holder, EntityPlayer entityPlayer) {
        ItemStack itemStack = holder.getCurrentItem();
        T metaValueItem = getItem(itemStack);
        ItemUIFactory uiFactory = metaValueItem == null ? null : metaValueItem.getUIManager();
        return uiFactory == null ? null : uiFactory.createUI(holder, entityPlayer);
    }

    public class MetaValueItem {

        public MetaItem<T> getMetaItem() {
            return MetaItem.this;
        }

        public final int metaValue;

        public final String unlocalizedName;

        private List<IMetaItemStats> allStats = new ArrayList<>();
        private List<IItemBehaviour> behaviours = new ArrayList<>();
        private IItemUseManager useManager;
        private ItemUIFactory uiManager;
        private IItemDurabilityManager durabilityManager;

        private int burnValue = 0;
        private boolean visible = true;
        private int maxStackSize = 64;
        private int modelAmount = 1;

        protected MetaValueItem(int metaValue, String unlocalizedName) {
            this.metaValue = metaValue;
            this.unlocalizedName = unlocalizedName;
        }

        public MetaValueItem setMaterialInfo(ItemMaterialInfo materialInfo) {
            if (materialInfo == null) {
                throw new IllegalArgumentException("Cannot add null ItemMaterialInfo.");
            }
            OreDictUnifier.registerOre(getStackForm(), materialInfo);
            return this;
        }

        public MetaValueItem setUnificationData(OrePrefix prefix, @Nullable Material material) {
            if (prefix == null) {
                throw new IllegalArgumentException("Cannot add null OrePrefix.");
            }
            OreDictUnifier.registerOre(getStackForm(), prefix, material);
            return this;
        }

        public MetaValueItem addOreDict(String oreDictName) {
            if (oreDictName == null) {
                throw new IllegalArgumentException("Cannot add null OreDictName.");
            }
            OreDictionary.registerOre(oreDictName, getStackForm());
            return this;
        }

        public MetaValueItem addOreDict(OreDictNames oreDictName) {
            if (oreDictName == null) {
                throw new IllegalArgumentException("Cannot add null OreDictName.");
            }
            OreDictionary.registerOre(oreDictName.name(), getStackForm());
            return this;
        }

        public MetaValueItem setInvisible() {
            this.visible = false;
            return this;
        }

        public MetaValueItem setMaxStackSize(int maxStackSize) {
            if (maxStackSize <= 0) {
                throw new IllegalArgumentException("Cannot set Max Stack Size to negative or zero value.");
            }
            this.maxStackSize = maxStackSize;
            return this;
        }

        public MetaValueItem setBurnValue(int burnValue) {
            if (burnValue <= 0) {
                throw new IllegalArgumentException("Cannot set Burn Value to negative or zero number.");
            }
            this.burnValue = burnValue;
            return this;
        }

        public MetaValueItem setModelAmount(int modelAmount) {
            if (modelAmount <= 0) {
                throw new IllegalArgumentException("Cannot set amount of models to negative or zero number.");
            }
            this.modelAmount = modelAmount;
            return this;
        }

        public MetaValueItem addStats(IMetaItemStats... stats) {
            for (IMetaItemStats metaItemStats : stats) {
                if (metaItemStats instanceof IItemDurabilityManager)
                    this.durabilityManager = (IItemDurabilityManager) metaItemStats;
                if (metaItemStats instanceof IItemUseManager)
                    this.useManager = (IItemUseManager) metaItemStats;
                if (metaItemStats instanceof IFoodBehavior)
                    this.useManager = new FoodUseManager((IFoodBehavior) metaItemStats);
                if (metaItemStats instanceof ItemUIFactory)
                    this.uiManager = (ItemUIFactory) metaItemStats;
                if (metaItemStats instanceof IItemBehaviour)
                    this.behaviours.add((IItemBehaviour) metaItemStats);
                this.allStats.add(metaItemStats);
            }
            return this;
        }

        public int getMetaValue() {
            return metaValue;
        }

        public List<IMetaItemStats> getAllStats() {
            return Collections.unmodifiableList(allStats);
        }

        public List<IItemBehaviour> getBehaviours() {
            return Collections.unmodifiableList(behaviours);
        }

        @Nullable
        public IItemDurabilityManager getDurabilityManager() {
            return durabilityManager;
        }

        @Nullable
        public IItemUseManager getUseManager() {
            return useManager;
        }

        @Nullable
        public ItemUIFactory getUIManager() {
            return uiManager;
        }

        public int getBurnValue() {
            return burnValue;
        }

        public int getMaxStackSize() {
            return maxStackSize;
        }

        public boolean isVisible() {
            return visible;
        }

        public int getModelAmount() {
            return modelAmount;
        }

        public ItemStack getStackForm(int amount) {
            return new ItemStack(MetaItem.this, amount, metaItemOffset + metaValue);
        }

        public boolean isItemEqual(ItemStack itemStack) {
            return itemStack.getItem() == MetaItem.this && itemStack.getItemDamage() == (metaItemOffset + metaValue);
        }

        public ItemStack getStackForm() {
            return getStackForm(1);
        }

        /**
         * Attempts to get an fully charged variant of this electric item
         * @param chargeAmount amount of charge
         * @return charged electric item stack
         * @throws java.lang.IllegalStateException if this item is not electric item
         */
        public ItemStack getChargedStack(long chargeAmount) {
            ItemStack itemStack = getStackForm(1);
            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if(electricItem == null) {
                throw new IllegalStateException("Not an electric item.");
            }
            electricItem.charge(chargeAmount, Integer.MAX_VALUE, true, false);
            return itemStack;
        }

        /**
         * Attempts to get an electric item variant with override of max charge
         * @param maxCharge new max charge of this electric item
         * @return item stack with given max charge
         * @throws java.lang.IllegalStateException if this item is not electric item or uses custom implementation
         */
        public ItemStack getMaxChargeOverrideStack(long maxCharge) {
            ItemStack itemStack = getStackForm(1);
            IElectricItem electricItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if(electricItem == null) {
                throw new IllegalStateException("Not an electric item.");
            }
            if(!(electricItem instanceof ElectricItem)) {
                throw new IllegalStateException("Only standard ElectricItem implementation supported, but this item uses " + electricItem.getClass());
            }
            ((ElectricItem) electricItem).setMaxChargeOverride(maxCharge);
            return itemStack;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .append("metaValue", metaValue)
                .append("unlocalizedName", unlocalizedName)
                .toString();
        }
    }

}
