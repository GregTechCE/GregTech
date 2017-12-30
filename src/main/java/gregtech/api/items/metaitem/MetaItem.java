package gregtech.api.items.metaitem;

import com.google.common.collect.ImmutableList;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.impl.ThermalFluidHandlerItemStack;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.metaitem.stats.IElectricStats;
import gregtech.api.items.metaitem.stats.IFluidStats;
import gregtech.api.items.metaitem.stats.IFoodStats;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.metaitem.stats.IItemDurabilityManager;
import gregtech.api.items.metaitem.stats.IItemUseManager;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import gregtech.api.items.metaitem.stats.INuclearStats;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import ic2.api.item.IBoxable;
import ic2.api.item.ISpecialElectricItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * MetaItem is item that can have up to Short.MAX_VALUE items inside one id.
 * These items even can be edible, have custom behaviours, be electric or act like fluid containers!
 * They can also have different burn time, plus be handheld, oredicted, no-unificated or invisible!
 * They also can be reactor components.
 *
 * You can also extend this class and occupy some of it's MetaData, and just pass an meta offset in constructor, and everything will work properly.
 *
 * Items are added in MetaItem via {@link #addItem(int, String, String...)}. You will get {@link MetaValueItem} instance, which you can configure in builder-alike pattern:
 * {@code addItem(0, "test_item").addStats(new ElectricStats(10000, 1,  false)) }
 * This will add single-use (unchargeable) LV battery with initial capacity 10000 EU
 */
@SuppressWarnings("deprecation")
public abstract class MetaItem<T extends MetaItem<?>.MetaValueItem> extends Item implements ISpecialElectricItem, IReactorComponent, IBoxable {

    private TShortObjectMap<T> metaItems = new TShortObjectHashMap<>();

    protected final short metaItemOffset;

    public MetaItem(short metaItemOffset) {
        setUnlocalizedName("meta_item");
        setHasSubtypes(true);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        this.metaItemOffset = metaItemOffset;
    }

    @SideOnly(Side.CLIENT)
    public void registerColor() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(this::getColorForItemStack, this);
    }

    @SideOnly(Side.CLIENT)
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        return 0xFFFFFF;
    }

    protected abstract T constructMetaValueItem(short metaValue, String unlocalizedName, String... nameParameters);

    public final T addItem(int metaValue, String unlocalizedName, String... nameParameters) {
        Validate.inclusiveBetween(0, Short.MAX_VALUE - 1, metaValue, "MetaItem ID should be in range from 0 to Short.MAX_VALUE-1");
        T metaValueItem = constructMetaValueItem((short) metaValue, unlocalizedName, nameParameters);
        metaItems.put((short) metaValue, metaValueItem);
        return metaValueItem;
    }

    public final T getItem(short metaValue) {
        return metaItems.get(metaValue);
    }

    public final T getItem(ItemStack itemStack) {
        return getItem((short) (itemStack.getItemDamage() - metaItemOffset));
    }

    //////////////////////////////////////////////////////////////////
    //                 ISpecialElectricItem Implementation          //
    //////////////////////////////////////////////////////////////////

    private IElectricStats getElectricStats(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return ElectricStats.EMPTY;
        }
        IElectricStats electricStats = metaValueItem.getElectricStats();
        if (electricStats == null) {
            return ElectricStats.EMPTY;
        }
        return electricStats;
    }

    @Override
    public IElectricStats getManager(ItemStack itemStack) {
        return getElectricStats(itemStack);
    }

    //////////////////////////////////////////////////////////////////
    //                     FluidContainer Stuff                     //
    //////////////////////////////////////////////////////////////////

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        T metaValueItem = getItem(stack);
        if(metaValueItem == null) {
            return null;
        }
        IFluidStats fluidStats = metaValueItem.getFluidStats();
        if (fluidStats == null) {
            return null;
        }
        return new ThermalFluidHandlerItemStack(stack,
            fluidStats.getCapacity(stack),
            fluidStats.getMinFluidTemperature(stack),
            fluidStats.getMaxFluidTemperature(stack));
    }

    //////////////////////////////////////////////////////////////////
    //                 INuclearStats  Implementation            //
    //////////////////////////////////////////////////////////////////
    
    private INuclearStats getNuclearStats(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return null;
        }
        return metaValueItem.getNuclearStats();
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack stack) {
        T metaValueItem = getItem(stack);
        INuclearStats nuclearStats = metaValueItem.getNuclearStats();
        return metaValueItem != null && nuclearStats != null;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            nuclearStats.processChamber(stack, reactor, x, y, heatrun);
        }
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.acceptUraniumPulse(stack, reactor, pulsingStack, youX, youY, pulseX, pulseY, heatrun);
        }
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.canStoreHeat(stack, reactor, x, y);
        }
        return false;
    }

    @Override
    public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.getMaxHeat(stack, reactor, x, y);
        }
        return 0;
    }

    @Override
    public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.getCurrentHeat(stack, reactor, x, y);
        }
        return 0;
    }

    @Override
    public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.alterHeat(stack, reactor, x, y, heat);
        }
        return heat;
    }

    @Override
    public float influenceExplosion(ItemStack stack, IReactor reactor) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        if(nuclearStats != null) {
            return nuclearStats.influenceExplosion(stack, reactor);
        }
        return 1.0f;
    }

    @Override
    public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
        INuclearStats nuclearStats = getNuclearStats(stack);
        return nuclearStats != null && nuclearStats.canBePlacedIn(stack, reactor);
    }

    //////////////////////////////////////////////////////////////////

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return super.getItemBurnTime(itemStack);
        }
        return metaValueItem.getBurnValue();
    }

    //////////////////////////////////////////////////////////////////
    //      Behaviours and Use Manager Implementation               //
    //////////////////////////////////////////////////////////////////

    private IItemUseManager getUseManager(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return null;
        }
        return metaValueItem.getUseManager();
    }

    private IItemDurabilityManager getDurabilityManager(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return null;
        }
        return metaValueItem.getDurabilityManager();
    }

    public List<IItemBehaviour> getBehaviours(ItemStack itemStack) {
        T metaValueItem = getItem(itemStack);
        if(metaValueItem == null) {
            return ImmutableList.of();
        }
        return metaValueItem.getBehaviours();
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        T metaValueItem = getItem(stack);
        if(metaValueItem == null) {
            return 64;
        }
        return metaValueItem.getMaxStackSize();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        IItemUseManager useManager = getUseManager(stack);
        if(useManager != null) {
            return useManager.getUseAction(stack);
        }
        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        IItemUseManager useManager = getUseManager(stack);
        if(useManager != null) {
            return useManager.getMaxItemUseDuration(stack);
        }
        return 0;
    }


    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if(player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if(useManager != null) {
                useManager.onItemUsingTick(stack, (EntityPlayer) player, count);
            }
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
        if(player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if(useManager != null) {
                useManager.onPlayerStoppedItemUsing(stack, (EntityPlayer) player, timeLeft);
            }
        }
    }

    @Nullable
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
        if(player instanceof EntityPlayer) {
            IItemUseManager useManager = getUseManager(stack);
            if(useManager != null) {
                return useManager.onItemUseFinish(stack, (EntityPlayer) player);
            }
        }
        return stack;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        boolean returnValue = false;
        for(IItemBehaviour behaviour : getBehaviours(stack)) {
            if(behaviour.onLeftClickEntity(stack, player, entity)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        for(IItemBehaviour behaviour : getBehaviours(itemStack)) {
            ActionResult<ItemStack> behaviourResult = behaviour.onItemRightClick(world, player, hand);
            itemStack = behaviourResult.getResult();
            if (behaviourResult.getType() != EnumActionResult.PASS) {
                return ActionResult.newResult(behaviourResult.getType(), itemStack);
            } else if (itemStack.isEmpty()) {
                return ActionResult.newResult(EnumActionResult.PASS, ItemStack.EMPTY);
            }
        }
        IItemUseManager useManager = getUseManager(itemStack);
        if(useManager != null && useManager.canStartUsing(itemStack, player)) {
            useManager.onItemUseStart(itemStack, player);
            player.setActiveHand(hand);
            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
        }
        return ActionResult.newResult(EnumActionResult.PASS, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        ItemStack originalStack = stack.copy();
        for(IItemBehaviour behaviour : getBehaviours(stack)) {
            ActionResult<ItemStack> behaviourResult = behaviour.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
            stack = behaviourResult.getResult();
            if(behaviourResult.getType() != EnumActionResult.PASS) {
                if(!ItemStack.areItemStacksEqual(originalStack, stack))
                    player.setHeldItem(hand, stack);
                return behaviourResult.getType();
            } else if(stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                return EnumActionResult.PASS;
            }
        }
        EnumAction useAction = getItemUseAction(stack);
        if(useAction != EnumAction.NONE) {
            player.setActiveHand(hand);
            return EnumActionResult.SUCCESS;
        }
        if(!ItemStack.areItemStacksEqual(originalStack, stack))
            player.setHeldItem(hand, stack);
        return EnumActionResult.PASS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack.getItemDamage() >= metaItemOffset) {
            T item = getItem(stack);

            if (item.nameParameters.length != 0) {
                String[] localizedParams = new String[item.nameParameters.length];
                for (int i = 0; i < item.nameParameters.length; i++) {
                    localizedParams[i] = I18n.format(item.nameParameters[i]);
                }

                return I18n.format("metaitem." + item.unlocalizedName + ".name", (Object[]) localizedParams);
            }
            return I18n.format("metaitem." + item.unlocalizedName + ".name");
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        T item = getItem(itemStack);
        if (item != null) {
            String unlocalizedTooltip = "metaitem." + item.unlocalizedName + ".tooltip";
            if (I18n.hasKey(unlocalizedTooltip)) {
                lines.add(I18n.format(unlocalizedTooltip));
            }

            IElectricStats electricStats = getManager(itemStack);
            if (electricStats.getMaxCharge(itemStack) > 0) {
                lines.add(I18n.format("metaitem.generic.electric_item.tooltip",
                    (long) electricStats.getCharge(itemStack),
                    (long) electricStats.getMaxCharge(itemStack),
                    GTValues.V[electricStats.getTier(itemStack)]));
            }
            /*if (getCapacity(itemStack) > 0) {
                FluidStack fluid = getFluid(itemStack);
                if (fluid != null) {
                    lines.add(I18n.format("metaitem.generic.fluid_container.tooltip",
                        fluid.amount,
                        getCapacity(itemStack),
                        fluid.getLocalizedName()));
                } else lines.add(I18n.format("metaitem.generic.fluid_container.tooltip_empty"));
            }*/
            for (IItemBehaviour behaviour : getBehaviours(itemStack)) {
                behaviour.addInformation(itemStack, lines);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (this.isInCreativeTab(tab)) {
            for (T enabledItem : metaItems.valueCollection()) {
                if (enabledItem.isVisible()) {
                    ItemStack itemStack = enabledItem.getStackForm();
                    /*if(getCapacity(itemStack) > 0) {
                        for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                            if(electricStats.getMaxCharge(itemStack) > 0) {
                                ItemStack chargedFilledStack = itemStack.copy();
                                fill(chargedFilledStack, new FluidStack(fluid, Integer.MAX_VALUE), true);
                                electricStats.charge(chargedFilledStack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
                                subItems.add(chargedFilledStack);
                            } else {
                                ItemStack filledStack = itemStack.copy();
                                fill(filledStack, new FluidStack(fluid, Integer.MAX_VALUE), true);
                                subItems.add(filledStack);
                            }
                        }
                    }*/
                    IElectricStats electricStats = getManager(itemStack);
                    if (electricStats.getMaxCharge(itemStack) > 0) {
                        ItemStack chargedStack = itemStack.copy();
                        electricStats.charge(chargedStack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
                        subItems.add(chargedStack);
                    }
                    subItems.add(itemStack);
                }
            }
        }
    }

    public class MetaValueItem {

        public MetaItem<T> getMetaItem() {
            return MetaItem.this;
        }

        public final int metaValue;

        public final String unlocalizedName;

        //Parameters can be either localized or not
        protected final String[] nameParameters;

        @Nullable
        private IElectricStats electricStats;
        @Nullable
        private IFluidStats fluidStats;
        @Nullable
        private INuclearStats nuclearStats;
        private List<IItemBehaviour> behaviours = new ArrayList<>();
        @Nullable
        private IItemUseManager useManager;
        @Nullable
        private IItemDurabilityManager durabilityManager;
        private int burnValue = 0;
        private boolean visible = true;
        private int maxStackSize = 64;

        protected MetaValueItem(int metaValue, String unlocalizedName, String... nameParameters) {
            this.metaValue = metaValue;
            this.unlocalizedName = unlocalizedName;
            this.nameParameters = nameParameters;
        }

        public MetaValueItem setMaterialInfo(ItemMaterialInfo materialInfo) {
            if(materialInfo == null) {
                throw new IllegalArgumentException("Cannot add null ItemMaterialInfo.");
            }
            OreDictUnifier.registerOre(getStackForm(), materialInfo);
            return this;
        }

        public MetaValueItem setUnificationData(OrePrefix prefix, @Nullable Material material) {
            if(prefix == null) {
                throw new IllegalArgumentException("Cannot add null OrePrefix.");
            }
            OreDictUnifier.registerOre(getStackForm(), prefix, material);
            return this;
        }

        public MetaValueItem addOreDict(String oreDictName) {
            if(oreDictName == null) {
                throw new IllegalArgumentException("Cannot add null OreDictName.");
            }
            OreDictionary.registerOre(oreDictName, getStackForm());
            return this;
        }

        public MetaValueItem addOreDict(OreDictNames oreDictName) {
            if(oreDictName == null) {
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
            if(maxStackSize <= 0) {
                throw new IllegalArgumentException("Cannot set Max Stack Size to negative or zero value.");
            }
            this.maxStackSize = maxStackSize;
            return this;
        }

        public MetaValueItem setBurnValue(int burnValue) {
            if(burnValue <= 0) {
                throw new IllegalArgumentException("Cannot set Burn Value to negative or zero number.");
            }
            this.burnValue = burnValue;
            return this;
        }

        public MetaValueItem addStats(IMetaItemStats... stats) {
            for(IMetaItemStats metaItemStats : stats) {
                if(metaItemStats instanceof IItemDurabilityManager) {
                    setDurabilityManager((IItemDurabilityManager) metaItemStats);
                }
                if(metaItemStats instanceof IItemUseManager) {
                    setUseManager((IItemUseManager) metaItemStats);
                }
                if(metaItemStats instanceof IFoodStats) {
                    setFoodStats((IFoodStats) metaItemStats);
                }
                if (metaItemStats instanceof IItemBehaviour) {
                    addBehaviour((IItemBehaviour) metaItemStats);
                }
                if (metaItemStats instanceof IElectricStats) {
                    setElectricStats((IElectricStats) metaItemStats);
                }
                if (metaItemStats instanceof IFluidStats) {
                    setFluidStats((IFluidStats) metaItemStats);
                }
                if (metaItemStats instanceof INuclearStats) {
                    setNuclearStats((INuclearStats) metaItemStats);
                }
            }
            return this;
        }

        protected void setFoodStats(IFoodStats foodStats) {
            addBehaviour(new FoodUseManager(foodStats));
        }

        protected void setDurabilityManager(IItemDurabilityManager durabilityManager) {
            if(durabilityManager == null) {
                throw new IllegalArgumentException("Cannot set Durability Manager to null.");
            }
            if(this.durabilityManager != null) {
                throw new IllegalStateException("Tried to set Durability Manager to " + durabilityManager + ", but they're already set to " + this.durabilityManager);
            }
            this.durabilityManager = durabilityManager;
        }

        protected void setElectricStats(IElectricStats electricStats) {
            if(electricStats == null) {
                throw new IllegalArgumentException("Cannot set Electric Stats to null.");
            }
            if(this.electricStats != null) {
                throw new IllegalStateException("Tried to set Electric Stats to " + electricStats + ", but they're already set to " + this.electricStats);
            }
            this.electricStats = electricStats;
        }

        protected MetaValueItem setFluidStats(IFluidStats fluidStats) {
            if(fluidStats == null) {
                throw new IllegalArgumentException("Cannot set Fluid Stats to null.");
            }
            if(this.fluidStats != null) {
                throw new IllegalStateException("Tried to set Fluid Stats to " + fluidStats + ", but they're already set to " + this.fluidStats);
            }
            this.fluidStats = fluidStats;
            return this;
        }
        
        protected void setNuclearStats(INuclearStats nuclearStats) {
            if(nuclearStats == null) {
                throw new IllegalArgumentException("Cannot set Nuclear Stats to null.");
            }
            this.nuclearStats = nuclearStats;
            setMaxStackSize(1);
        }

        protected void setUseManager(IItemUseManager useManager) {
            if(this.useManager != null) {
                throw new IllegalStateException("Tried to set Use Manager to " + useManager + ", but it's already set to " + this.useManager);
            }
            this.useManager = useManager;
        }

        protected void addBehaviour(IItemBehaviour behaviour) {
            if (behaviour == null) {
                throw new IllegalArgumentException("Cannot add null behaviour.");
            }
            this.behaviours.add(behaviour);
        }

        public int getMetaValue() {
            return metaValue;
        }

        public IElectricStats getElectricStats() {
            return electricStats;
        }

        public IFluidStats getFluidStats() {
            return fluidStats;
        }

        public INuclearStats getNuclearStats() {
            return nuclearStats;
        }

        public IItemDurabilityManager getDurabilityManager() {
            return durabilityManager;
        }

        public List<IItemBehaviour> getBehaviours() {
            return ImmutableList.copyOf(behaviours);
        }

        public IItemUseManager getUseManager() {
            return useManager;
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

        public ItemStack getStackForm(int amount) {
            return new ItemStack(MetaItem.this, amount, metaItemOffset + metaValue);
        }

        public ItemStack getStackForm() {
            return getStackForm(1);
        }

    }

}
