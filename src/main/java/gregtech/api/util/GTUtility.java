package gregtech.api.util;


import com.google.common.collect.Lists;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.IDamagableItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.awt.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

import static gregtech.api.GTValues.*;

public class GTUtility {

    //magic is here
    @SuppressWarnings("unchecked")
    public static <T, R> Class<T> getActualTypeParameter(Class<? extends R> thisClass, Class<R> declaringClass, int index) {
        Type type = thisClass.getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != declaringClass) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[index];
    }

    public static PotionEffect copyPotionEffect(PotionEffect sample) {
        PotionEffect potionEffect = new PotionEffect(sample.getPotion(), sample.getDuration(), sample.getAmplifier(), sample.getIsAmbient(), sample.doesShowParticles());
        potionEffect.setCurativeItems(sample.getCurativeItems());
        return potionEffect;
    }

    /**
     * Determines dye color nearest to specified RGB color
     */
    public static EnumDyeColor determineDyeColor(int rgbColor) {
        int rA = (rgbColor & 0xff0000) >> 16;
        int gA = (rgbColor & 0xff00) >> 8;
        int bA = (rgbColor & 0xff);
        float[] hsb = Color.RGBtoHSB(rA, gA, bA, new float[3]);
        return EnumDyeColor.values()[indexOfClosest(hsb[0], hueDyeValues)];
    }

    private static double[] hueDyeValues = Arrays.stream(EnumDyeColor.values())
        .mapToDouble(color -> {
            int rA = (color.colorValue & 0xff0000) >> 16;
            int gA = (color.colorValue & 0xff00) >> 8;
            int bA = (color.colorValue & 0xff);
            float[] hsb = Color.RGBtoHSB(rA, gA, bA, new float[3]);
            return hsb[0];
        })
        .toArray();

    private static int indexOfClosest(double of, double[] in) {
        double min = Double.POSITIVE_INFINITY;
        int closestIndex = -1;

        for (int i = 0; i < in.length; i++) {
            double diff = Math.abs(in[i] - of);

            if (diff < min) {
                min = diff;
                closestIndex = i;
            }
        }

        return closestIndex;
    }

    //just because CCL uses a different color format
    public static int convertRGBtoOpaqueRGBA(int colorValue) {
        int r = (colorValue >> 16) & 0xFF;
        int g = (colorValue >> 8) & 0xFF;
        int b = (colorValue & 0xFF);
        return (r & 0xFF) << 24 | (g & 0xFF) << 16 | (b & 0xFF) << 8 | (255 & 0xFF);
    }

    /**
     * Adds potion tooltip into given lines list
     *
     * @param potions potion effects to add to tooltip
     * @param lines   description lines
     */
    @SideOnly(Side.CLIENT)
    public static void addPotionTooltip(Iterable<PotionEffect> potions, List<String> lines) {
        ArrayList<Tuple<String, AttributeModifier>> attributeLines = new ArrayList<>();

        for (PotionEffect potionEffect : potions) {
            String line = I18n.format(potionEffect.getEffectName());
            Potion potion = potionEffect.getPotion();
            Map<IAttribute, AttributeModifier> attributes = potionEffect.getPotion().getAttributeModifierMap();
            if (!attributes.isEmpty()) {
                for (Map.Entry<IAttribute, AttributeModifier> entry : attributes.entrySet()) {
                    AttributeModifier modifier = entry.getValue();
                    attributeLines.add(new Tuple<>(entry.getKey().getName(),
                            new AttributeModifier(modifier.getName(),
                                    potion.getAttributeModifierAmount(potionEffect.getAmplifier(), modifier),
                                    modifier.getOperation())));
                }
            }
            if (potionEffect.getAmplifier() > 0) {
                line = line + " " + I18n.format("potion.potency." + potionEffect.getAmplifier());
            }
            if (potionEffect.getDuration() > 20) {
                line = line + " (" + Potion.getPotionDurationString(potionEffect, 1.0f) + ")";
            }
            if (potion.isBadEffect()) {
                lines.add(TextFormatting.RED + line);
            } else {
                lines.add(TextFormatting.BLUE + line);
            }
        }
        if (!attributeLines.isEmpty()) {
            lines.add("");
            lines.add(TextFormatting.DARK_PURPLE + I18n.format("potion.whenDrank"));

            for (Tuple<String, AttributeModifier> tuple : attributeLines) {
                AttributeModifier modifier = tuple.getSecond();
                double d0 = modifier.getAmount();
                double d1;
                if (modifier.getOperation() != 1 && modifier.getOperation() != 2) {
                    d1 = modifier.getAmount();
                } else {
                    d1 = modifier.getAmount() * 100.0D;
                }
                if (d0 > 0.0D) {
                    lines.add(TextFormatting.BLUE + I18n.format("attribute.modifier.plus." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.format("attribute.name." + tuple.getFirst())));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    lines.add(TextFormatting.RED + I18n.format("attribute.modifier.take." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.format("attribute.name." + tuple.getFirst())));
                }
            }

        }
    }

    /**
     * Applies specific amount of damage to item, either to durable items (which implement IDamagableItem)
     * or to electric items, which have capability IElectricItem
     * Damage amount is equal to EU amount used for electric items
     * @return if damage was applied successfully
     */
    public static boolean doDamageItem(ItemStack itemStack, int vanillaDamage, boolean simulate) {
        Item item = itemStack.getItem();
        if (item instanceof IDamagableItem) {
            return ((IDamagableItem) item).doDamageToItem(itemStack, vanillaDamage, simulate);
        } else if (itemStack.hasCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null)) {
            IElectricItem capability = itemStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
            int energyNeeded = vanillaDamage * ConfigHolder.energyUsageMultiplier;
            return capability != null
                && capability.canUse(energyNeeded)
                && capability.discharge(energyNeeded, Integer.MAX_VALUE, true, false, simulate) == energyNeeded;
        } else {
            return false;
        }
    }

    public static void writeItems(IItemHandler handler, String tagName, NBTTagCompound tag) {
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                NBTTagCompound stackTag = new NBTTagCompound();

                stackTag.setInteger("Slot", i);

                handler.getStackInSlot(i).writeToNBT(stackTag);

                tagList.appendTag(stackTag);
            }
        }

        tag.setTag(tagName, tagList);
    }

    public static void readItems(IItemHandlerModifiable handler, String tagName, NBTTagCompound tag) {
        if (tag.hasKey(tagName)) {
            NBTTagList tagList = tag.getTagList(tagName, Constants.NBT.TAG_COMPOUND);

            for (int i = 0; i < tagList.tagCount(); i++) {
                int slot = tagList.getCompoundTagAt(i).getInteger("Slot");

                if (slot >= 0 && slot < handler.getSlots()) {
                    handler.setStackInSlot(slot, new ItemStack(tagList.getCompoundTagAt(i)));
                }
            }
        }
    }

    public static boolean isStringValid(String aString) {
        return aString != null && !aString.isEmpty();
    }

    public static boolean isBetweenExclusive(long start, long end, long value) {
        return start < value && value < end;
    }

    public static boolean isBetweenInclusive(long start, long end, long value) {
        return start <= value && value <= end;
    }

    /**
     * Capitalizes string, making first letter upper case
     *
     * @return capitalized string
     */
    public static String capitalizeString(String string) {
        if (string != null && string.length() > 0)
            return string.substring(0, 1).toUpperCase() + string.substring(1);
        return "";
    }

    /**
     * @return lowest tier that can handle passed voltage
     */
    public static byte getTierByVoltage(long voltage) {
        byte tier = 0;
        while (++tier < V.length) {
            if (voltage <= V[tier])
                return tier;
        }
        return tier;
    }

    public static byte getTierByVoltageRoundDown(long voltage) {
        byte tier = 0;
        while (++tier < V.length) {
            if (voltage < V[tier])
                return (byte) (tier - 1);
        }
        return tier;
    }

    /**
     * @return a list of itemstack linked with given item handler
     * modifications in list will reflect on item handler and wise-versa
     */
    public static List<ItemStack> itemHandlerToList(IItemHandlerModifiable inputs) {
        return new AbstractList<ItemStack>() {
            @Override
            public ItemStack set(int index, ItemStack element) {
                ItemStack oldStack = inputs.getStackInSlot(index);
                inputs.setStackInSlot(index, element == null ? ItemStack.EMPTY : element);
                return oldStack;
            }

            @Override
            public ItemStack get(int index) {
                return inputs.getStackInSlot(index);
            }

            @Override
            public int size() {
                return inputs.getSlots();
            }
        };
    }

    /**
     * @return a list of fluidstack linked with given fluid handler
     * modifications in list will reflect on fluid handler and wise-versa
     */
    public static List<FluidStack> fluidHandlerToList(IMultipleTankHandler fluidInputs) {
        List<IFluidTank> backedList = fluidInputs.getFluidTanks();
        return new AbstractList<FluidStack>() {
            @Override
            public FluidStack set(int index, FluidStack element) {
                IFluidTank fluidTank = backedList.get(index);
                FluidStack oldStack = fluidTank.getFluid();
                if(!(fluidTank instanceof FluidTank))
                    return oldStack;
                ((FluidTank) backedList.get(index)).setFluid(element);
                return oldStack;
            }

            @Override
            public FluidStack get(int index) {
                return backedList.get(index).getFluid();
            }

            @Override
            public int size() {
                return backedList.size();
            }
        };
    }

    public static boolean isWearingFullSuit(EntityLivingBase entity, Set<SimpleItemStack> suitParts) {
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
                ItemStack equipment = entity.getItemStackFromSlot(slot);
                if (!equipment.isEmpty() && !suitParts.contains(new SimpleItemStack(equipment))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isWearingFullFrostHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.frostHazmatList);
    }

    public static boolean isWearingFullHeatHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.heatHazmatList);
    }

    public static boolean isWearingFullBioHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.bioHazmatList);
    }

    public static boolean isWearingFullRadioHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.radioHazmatList);
    }

    public static boolean isWearingFullElectroHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.electroHazmatList);
    }

    public static boolean isWearingFullGasHazmat(EntityLivingBase entity) {
        return isWearingFullSuit(entity, GregTechAPI.gasHazmatList);
    }

    public static float getHeatDamageFromItem(ItemStack stack) {
        UnificationEntry data = OreDictUnifier.getUnificationEntry(stack);
        return data == null ? 0 : data.orePrefix.heatDamage;
    }

    public static boolean applyHeatDamage(EntityLivingBase entity, float damage) {
        if (damage > 0 && entity.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null && !isWearingFullHeatHazmat(entity)) {
            entity.attackEntityFrom(DamageSources.getHeatDamage(), damage);
            return true;
        }
        return false;
    }

    public static boolean applyFrostDamage(EntityLivingBase entity, float damage) {
        if (damage > 0 && !isWearingFullFrostHazmat(entity)) {
            entity.attackEntityFrom(DamageSources.getFrostDamage(), damage);
            return true;
        }
        return false;
    }

    public static boolean applyElectricityDamage(EntityLivingBase entity, long voltage, long amperage) {
        long damage = getTierByVoltage(voltage) * amperage * 4;
        if (damage > 0 && !isWearingFullElectroHazmat(entity)) {
            entity.attackEntityFrom(DamageSources.getElectricDamage(), damage);
            return true;
        }
        return false;
    }

    public static boolean applyRadioactivity(EntityLivingBase entity, int level, int amountOfItems) {
        if (level > 0 && entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && entity.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD && !isWearingFullRadioHazmat(entity)) {
            entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, level * 140 * amountOfItems));
            entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, level * 130 * amountOfItems));
            entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, level * 150 * amountOfItems));
            entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, level * 130 * amountOfItems));
            entity.attackEntityFrom(DamageSources.getRadioactiveDamage(), level * 6 * amountOfItems);
            return true;
        }
        return false;
    }

    public static <T> boolean iterableContains(Iterable<T> list, Predicate<T> predicate) {
        for (T t : list) {
            if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    public static int amountOfNonNullElements(List<?> collection) {
        int amount = 0;
        for(Object object : collection) {
            if(object != null) amount++;
        }
        return amount;
    }

    public static int amountOfNonEmptyStacks(List<ItemStack> collection) {
        int amount = 0;
        for(ItemStack object : collection) {
            if(object != null && !object.isEmpty()) amount++;
        }
        return amount;
    }

    public static NBTTagCompound getOrCreateNbtCompound(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        return compound == null ? new NBTTagCompound() : compound;
    }

    public static NonNullList<ItemStack> copyStackList(List<ItemStack> itemStacks) {
        ItemStack[] stacks = new ItemStack[itemStacks.size()];
        for (int i = 0; i < itemStacks.size(); i++) {
            stacks[i] = copy(itemStacks.get(i));
        }
        return NonNullList.from(ItemStack.EMPTY, stacks);
    }

    public static ItemStack[] copyStackArray(ItemStack... stacks) {
        ItemStack[] itemStacks = new ItemStack[stacks.length];
        for (int i = 0; i < stacks.length; i++) itemStacks[i] = stacks[i].copy();
        return itemStacks;
    }

    public static List<FluidStack> copyFluidList(List<FluidStack> fluidStacks) {
        FluidStack[] stacks = new FluidStack[fluidStacks.size()];
        for (int i = 0; i < fluidStacks.size(); i++) stacks[i] = fluidStacks.get(i).copy();
        return Lists.newArrayList(stacks);
    }

    public static FluidStack[] copyFluidArray(FluidStack... stacks) {
        FluidStack[] fluidStacks = new FluidStack[stacks.length];
        for (int i = 0; i < stacks.length; i++) if (stacks[i] != null) fluidStacks[i] = stacks[i].copy();
        return fluidStacks;
    }

    public static ItemStack copy(ItemStack... stacks) {
        for (ItemStack stack : stacks)
            if (!stack.isEmpty()) return stack.copy();
        return ItemStack.EMPTY;
    }

    public static ItemStack copyAmount(int amount, ItemStack... stacks) {
        ItemStack stack = copy(stacks);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        if (amount > 64) amount = 64;
        else if (amount == -1) amount = 111;
        else if (amount < 0) amount = 0;
        stack.setCount(amount);
        return stack;
    }

    public static FluidStack copyAmount(int amount, FluidStack fluidStack) {
        FluidStack stack = fluidStack.copy();
        stack.amount = amount;
        return stack;
    }

    public static <M, E extends M> E selectItemInList(int index, E replacement, List<? extends M> list, Class<E> minClass) {
        if (list.isEmpty())
            return replacement;

        M maybeResult;
        if (list.size() <= index) {
            maybeResult = list.get(list.size() - 1);
        } else if (index < 0) {
            maybeResult = list.get(0);
        } else maybeResult = list.get(index);

        if(minClass.isAssignableFrom(maybeResult.getClass())) {
            return minClass.cast(maybeResult);
        }
        return replacement;
    }

    public static <M> M getItem(List<? extends M> list, int index, M replacement) {
        if(index >= 0 && index < list.size())
            return list.get(index);
        return replacement;
    }

    public static boolean isStackInList(ItemStack stack, Collection<SimpleItemStack> list) {
        if (stack.isEmpty()) return false;
        return isStackInList(new SimpleItemStack(stack), list);
    }

    public static boolean isStackInList(SimpleItemStack stack, Collection<SimpleItemStack> list) {
        return stack != null && (list.contains(stack) || list.contains(new SimpleItemStack(stack.item, stack.stackSize, W)));
    }

    /**
     * Translates a material amount into an amount of fluid in fluid material units.
     */
    public static int mat2FlAmount(long materialAmount) {
        return (int) translateMaterialToAmount(materialAmount, L);
    }

    /**
     * Translates a material amount into an amount of fluid.
     */
    public static long translateMaterialToAmount(long materialAmount, long amountPerUnit) {
        return (materialAmount * amountPerUnit) / M;
    }

    /**
     * This checks if the dimension is really a dimension and not another planet or something.
     * Used for my teleporter.
     */
    public static boolean isRealDimension(int dimensionID) {
        String clazzName = DimensionManager.getProvider(dimensionID).getClass().getName().toLowerCase();
        if (clazzName.contains("mystcraft") || clazzName.contains("twilightforest") || clazzName.contains("rftools"))
            return true;
        return GregTechAPI.dimensionalList.contains(dimensionID);
    }

    public static EnumFacing determineWrenchingSide(EnumFacing side, float x, float y, float z) {
        EnumFacing tBack = side.getOpposite();
        switch (side) {
            case DOWN:
            case UP:
                if (x < 0.25) {
                    if (z < 0.25) return tBack;
                    if (z > 0.75) return tBack;
                    return EnumFacing.WEST;
                }
                if (x > 0.75) {
                    if (z < 0.25) return tBack;
                    if (z > 0.75) return tBack;
                    return EnumFacing.EAST;
                }
                if (z < 0.25) return EnumFacing.NORTH;
                if (z > 0.75) return EnumFacing.SOUTH;
                return side;
            case NORTH:
            case SOUTH:
                if (x < 0.25) {
                    if (y < 0.25) return tBack;
                    if (y > 0.75) return tBack;
                    return EnumFacing.WEST;
                }
                if (x > 0.75) {
                    if (y < 0.25) return tBack;
                    if (y > 0.75) return tBack;
                    return EnumFacing.EAST;
                }
                if (y < 0.25) return EnumFacing.DOWN;
                if (y > 0.75) return EnumFacing.UP;
                return side;
            case WEST:
            case EAST:
                if (z < 0.25) {
                    if (y < 0.25) return tBack;
                    if (y > 0.75) return tBack;
                    return EnumFacing.NORTH;
                }
                if (z > 0.75) {
                    if (y < 0.25) return tBack;
                    if (y > 0.75) return tBack;
                    return EnumFacing.SOUTH;
                }
                if (y < 0.25) return EnumFacing.DOWN;
                if (y > 0.75) return EnumFacing.UP;
                return side;
        }
        return side;
    }

	public static long createFlag(int id) {
		return 1L << id;
	}

}