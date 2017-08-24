package gregtech.api.util;


import gregtech.api.GregTechAPI;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import ic2.core.IC2Potion;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static gregtech.api.GTValues.*;

public class GTUtility {

    //magic is here
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
                    attributeLines.add(new Tuple<>(entry.getKey().getAttributeUnlocalizedName(),
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

    public static boolean isStringValid(String aString) {
        return aString != null && !aString.isEmpty();
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

    @SafeVarargs
    public static <T> boolean arrayContainsNonNull(T... array) {
        if (array != null) {
            for (Object object : array)
                if (object != null) return true;
        }
        return false;
    }

    public static boolean areUnificationEqual(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areItemsEqual(OreDictionaryUnifier.getUnificated(stackA), OreDictionaryUnifier.getUnificated(stackB));
    }

    public static void playSound(World world, double x, double y, double z, ResourceLocation soundName, SoundCategory category, float strength, float modulation) {
        world.playSound(x, y, z, SoundEvent.REGISTRY.getObject(soundName), category, strength, modulation, false);
    }

    public static void playSound(EntityPlayer player, World world, double x, double y, double z, ResourceLocation soundName, SoundCategory category, float strength, float modulation) {
        world.playSound(player, x, y, z, SoundEvent.REGISTRY.getObject(soundName), category, strength, modulation);
    }

    public static void setCoordsOnFire(World world, BlockPos pos) {
        for(EnumFacing side : EnumFacing.VALUES) {
            BlockPos offset = pos.offset(side);
            if(world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
                world.setBlockState(offset, Blocks.FIRE.getDefaultState());
            }
        }
    }

    public static boolean isWearingFullSuit(EntityLivingBase entity, Set<SimpleItemStack> suitParts) {
        for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
            if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
                ItemStack equipment = entity.getItemStackFromSlot(slot);
                if (equipment != null) {
                    if (!suitParts.contains(new SimpleItemStack(equipment)))
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
        UnificationEntry data = OreDictionaryUnifier.getUnificationEntry(stack);
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
            entity.addPotionEffect(new PotionEffect(IC2Potion.radiation, level * 180 * amountOfItems));
            return true;
        }
        return false;
    }

    public static List<ItemStack> copyStackList(List<ItemStack> itemStacks) {
        List<ItemStack> stacks = new ArrayList<>(itemStacks.size());
        for (int i = 0; i < itemStacks.size(); i++) {
            stacks.set(i, copy(itemStacks.get(i)));
        }
        return stacks;
    }

    public static List<FluidStack> copyFluidList(List<FluidStack> fluidStacks) {
        List<FluidStack> stacks = new ArrayList<>(fluidStacks.size());
        for (int i = 0; i < fluidStacks.size(); i++) stacks.set(i, fluidStacks.get(i).copy());
        return stacks;
    }

    public static FluidStack[] copyFluidArray(FluidStack... stacks) {
        FluidStack[] rStacks = new FluidStack[stacks.length];
        for (int i = 0; i < stacks.length; i++) if (stacks[i] != null) rStacks[i] = stacks[i].copy();
        return rStacks;
    }

    public static ItemStack copy(ItemStack... stacks) {
        for (ItemStack stack : stacks)
            if (stack != null) return stack.copy();
        return null;
    }

    public static ItemStack copyAmount(int amount, ItemStack... stacks) {
        ItemStack stack = copy(stacks);
        if (stack == null) return null;
        if (amount > 64) amount = 64;
        else if (amount == -1) amount = 111;
        else if (amount < 0) amount = 0;
        stack.stackSize = (byte) amount;
        return stack;
    }

    public static <E> E selectItemInList(int index, E replacement, List<E> list) {
        if (list == null || list.isEmpty()) return replacement;
        if (list.size() <= index) return list.get(list.size() - 1);
        if (index < 0) return list.get(0);
        return list.get(index);
    }

    public static boolean isStackInList(ItemStack stack, Collection<SimpleItemStack> list) {
        if (stack == null) return false;
        return isStackInList(new SimpleItemStack(stack), list);
    }

    public static boolean isStackInList(SimpleItemStack stack, Collection<SimpleItemStack> list) {
        return stack != null && (list.contains(stack) || list.contains(new SimpleItemStack(stack.item, stack.stackSize, W)));
    }

    /**
     * Translates a material amount into an amount of fluid in fluid material units.
     */
    public static long translateMaterialToFluidAmount(long materialAmount, boolean roundUp) {
        return translateMaterialToAmount(materialAmount, L, roundUp);
    }

    /**
     * Translates a material amount into an amount of fluid.
     */
    public static long translateMaterialToAmount(long materialAmount, long amountPerUnit, boolean roundUp) {
        return Math.max(0, ((materialAmount * amountPerUnit) / M) + (roundUp && (materialAmount * amountPerUnit) % M > 0 ? 1 : 0));
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

}