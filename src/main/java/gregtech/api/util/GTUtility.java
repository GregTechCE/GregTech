package gregtech.api.util;


import com.google.common.collect.Lists;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.IDamagableItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.common.ConfigHolder;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
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
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import static gregtech.api.GTValues.V;

public class GTUtility {

    public static BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    public static BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

    public static long castToLong(BigInteger value) {
        return value.compareTo(LONG_MAX) >= 0 ? Long.MAX_VALUE : value.compareTo(LONG_MIN) <= 0 ? Long.MIN_VALUE : value.longValue();
    }

    public static long castedSum(long... values) {
        if (values.length == 0) return 0L;
        if (values.length == 1) return values[0];
        int p = 0;
        // try combine the values first, in order not to use BigInteger so frequently.
        for (int i = 1; i < values.length; i++) {
            if (values[i] == 0L) continue;
            if ((values[i] > 0 && Long.MAX_VALUE - values[p] >= values[i])
                || (values[i] < 0 && Long.MIN_VALUE - values[i] <= values[p])) {
                values[p] += values[i];
            } else {
                values[++p] = values[i];
            }
        }
        if (p == 0) return values[0];
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i <= p; i++) {
            result = result.add(BigInteger.valueOf(values[i]));
        }
        return castToLong(result);
    }

    public static BigInteger sum(long... values) {
        if (values.length == 0) return BigInteger.ZERO;
        if (values.length == 1) return BigInteger.valueOf(values[0]);
        int p = 0;
        // try combine the values first in order not to use BigInteger so frequently.
        for (int i = 1; i < values.length; i++) {
            if (values[i] == 0L) continue;
            if ((values[i] > 0 && Long.MAX_VALUE - values[p] >= values[i])
                || (values[i] < 0 && Long.MIN_VALUE - values[i] <= values[p])) {
                values[p] += values[i];
            } else {
                values[++p] = values[i];
            }
        }
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i <= p; i++) {
            result = result.add(BigInteger.valueOf(values[i]));
        }
        return result;
    }

    public static  <T> String[] mapToString(T[] array, Function<T, String> mapper) {
        String[] result = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            result[i] = mapper.apply(array[i]);
        }
        return result;
    }

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
        Color c = new Color(rgbColor);

        Map<Double, EnumDyeColor> distances = new HashMap<>();
        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            Color c2 = new Color(dyeColor.colorValue);

            double distance = (c.getRed() - c2.getRed()) * (c.getRed() - c2.getRed())
                + (c.getGreen() - c2.getGreen()) * (c.getGreen() - c2.getGreen())
                + (c.getBlue() - c2.getBlue()) * (c.getBlue() - c2.getBlue());

            distances.put(distance, dyeColor);
        }

        double min = Collections.min(distances.keySet());
        return distances.get(min);
    }

    public static int multiplyColor(int c1, int c2) {
        int r = (((c1 >>> 16) * (c2 >>> 16)) & 0xFF00) << 16;
        int g = (((c1 >> 8 & 0xFF) * (c2 >> 8 & 0xFF)) & 0xFF00) << 8;
        int b = ((c1 & 0xFF) * (c2 & 0xFF)) & 0xFF00;
        return r | g | b;
    }

    //just because CCL uses a different color format
    //0xRRGGBBAA
    public static int convertRGBtoOpaqueRGBA_CL(int colorValue) {
        int r = (colorValue >> 16) & 0xFF;
        int g = (colorValue >> 8) & 0xFF;
        int b = (colorValue & 0xFF);
        return (r & 0xFF) << 24 | (g & 0xFF) << 16 | (b & 0xFF) << 8 | (0xFF);
    }

    //0xAARRGGBB
    public static int convertRGBtoOpaqueRGBA_MC(int colorValue) {
        int r = (colorValue >> 16) & 0xFF;
        int g = (colorValue >> 8) & 0xFF;
        int b = (colorValue & 0xFF);
        //noinspection NumericOverflow
        return 0xFF << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public static void setItem(ItemStack itemStack, ItemStack newStack) {
        try {
            Field itemField = Arrays.stream(ItemStack.class.getDeclaredFields())
                .filter(field -> field.getType() == Item.class)
                .findFirst().orElseThrow(ReflectiveOperationException::new);
            itemField.setAccessible(true);
            //replace item field instance
            itemField.set(itemStack, newStack.getItem());
            //set damage then
            itemStack.setItemDamage(newStack.getItemDamage());
            Method forgeInit = ItemStack.class.getDeclaredMethod("forgeInit");
            forgeInit.setAccessible(true);
            //reinitialize forge capabilities and delegate reference
            forgeInit.invoke(itemStack);
        } catch (ReflectiveOperationException exception) {
            //should be impossible, actually
            throw new RuntimeException(exception);
        }
    }

    /**
     * Attempts to merge given ItemStack with ItemStacks in slot list supplied
     * If it's not possible to merge it fully, it will attempt to insert it into first empty slots
     */
    public static boolean mergeItemStack(ItemStack itemStack, List<Slot> slots) {
        if(itemStack.isEmpty())
            return false; //if we are merging empty stack, return

        boolean merged = false;
        //iterate non-empty slots first
        //to try to insert stack into them
        for(Slot slot : slots) {
            if(!slot.isItemValid(itemStack))
                continue; //if itemstack cannot be placed into that slot, continue
            ItemStack stackInSlot = slot.getStack();
            if(!ItemStack.areItemsEqual(itemStack, stackInSlot) ||
                !ItemStack.areItemStackTagsEqual(itemStack, stackInSlot))
                continue; //if itemstacks don't match, continue
            int slotMaxStackSize = Math.min(stackInSlot.getMaxStackSize(), slot.getItemStackLimit(stackInSlot));
            int amountToInsert = Math.min(itemStack.getCount(), slotMaxStackSize - stackInSlot.getCount());
            if(amountToInsert == 0)
                continue; //if we can't insert anything, continue
            //shrink our stack, grow slot's stack and mark slot as changed
            stackInSlot.grow(amountToInsert);
            itemStack.shrink(amountToInsert);
            slot.onSlotChanged();
            merged = true;
            if(itemStack.isEmpty())
                return true; //if we inserted all items, return
        }

        //then try to insert itemstack into empty slots
        //breaking it into pieces if needed
        for(Slot slot : slots) {
            if(!slot.isItemValid(itemStack))
                continue; //if itemstack cannot be placed into that slot, continue
            if(slot.getHasStack())
                continue; //if slot contains something, continue
            int amountToInsert = Math.min(itemStack.getCount(), slot.getItemStackLimit(itemStack));
            if(amountToInsert == 0)
                continue; //if we can't insert anything, continue
            //split our stack and put result in slot
            ItemStack stackInSlot = itemStack.splitStack(amountToInsert);
            slot.putStack(stackInSlot);
            merged = true;
            if(itemStack.isEmpty())
                return true; //if we inserted all items, return
        }
        return merged;
    }

    public static boolean isBlockOrePrefixed(IBlockAccess world, BlockPos pos, IBlockState blockState, OrePrefix targetPrefix, List<ItemStack> drops) {
        for(ItemStack itemStack : drops) {
            OrePrefix orePrefix = OreDictUnifier.getPrefix(itemStack);
            if(orePrefix == targetPrefix)
                return true;
        }
        return false;
    }

    public static long getBlockMaterialAmount(IBlockAccess world, BlockPos pos, IBlockState blockState, Material targetMaterial, List<ItemStack> drops) {
        for(ItemStack itemStack : drops) {
            MaterialStack materialStack = OreDictUnifier.getMaterial(itemStack);
            if(materialStack != null && materialStack.material == targetMaterial)
                return materialStack.amount;
        }
        return 0L;
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

    @SideOnly(Side.CLIENT)
    public static void drawCenteredSizedText(int x, int y, String string, int color, double sizeMultiplier) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int textWidth = fontRenderer.getStringWidth(string);
        int textHeight = fontRenderer.FONT_HEIGHT;
        GlStateManager.pushMatrix();
        GlStateManager.scale(sizeMultiplier, sizeMultiplier, 0.0);
        GlStateManager.translate(-textWidth * sizeMultiplier / 2, -textHeight * sizeMultiplier / 2, 0);
        fontRenderer.drawString(string, x, y, color);
        GlStateManager.popMatrix();
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
            //if item implements IDamagableItem, it manages it's own durability itself
            IDamagableItem damagableItem = (IDamagableItem) item;
            return damagableItem.doDamageToItem(itemStack, vanillaDamage, simulate);

        } else if (itemStack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null)) {
            //if we're using electric item, use default energy multiplier for textures
            IElectricItem capability = itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            int energyNeeded = vanillaDamage * ConfigHolder.energyUsageMultiplier;
            //noinspection ConstantConditions
            return capability.discharge(energyNeeded, Integer.MAX_VALUE, true, false, simulate) == energyNeeded;

        } else if (itemStack.isItemStackDamageable()) {
            if (!simulate && itemStack.attemptDamageItem(vanillaDamage, new Random(), null)) {
                //if we can't accept more damage, just shrink stack and mark it as broken
                //actually we would play broken animation here, but we don't have an entity who holds item
                itemStack.shrink(1);
            }
            return true;
        }
        return false;
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
            if (voltage == V[tier]) {
                return tier;
            } else if (voltage < V[tier]) {
                return (byte) Math.max(0, tier - 1);
            }
        }
        return tier;
    }

    public static BiomeDictionary.Type getBiomeTypeTagByName(String name) {
        Map<String, BiomeDictionary.Type> byName = ReflectionHelper.getPrivateValue(BiomeDictionary.Type.class, null, "byName");
        return byName.get(name);
    }

    public static List<Tuple<ItemStack, Integer>> getGrassSeedEntries() {
        ArrayList<Tuple<ItemStack, Integer>> result = new ArrayList<>();
        try {
            Field seedListField = ForgeHooks.class.getDeclaredField("seedList");
            seedListField.setAccessible(true);
            Class<?> seedEntryClass = Class.forName("net.minecraftforge.common.ForgeHooks$SeedEntry");
            Field seedField = seedEntryClass.getDeclaredField("seed");
            seedField.setAccessible(true);
            List<WeightedRandom.Item> seedList = (List<WeightedRandom.Item>) seedListField.get(null);
            for(WeightedRandom.Item seedEntryObject : seedList) {
                ItemStack seedStack = (ItemStack) seedField.get(seedEntryObject);
                int chanceValue = seedEntryObject.itemWeight;
                if(!seedStack.isEmpty())
                    result.add(new Tuple<>(seedStack, chanceValue));
            }
        } catch (ReflectiveOperationException exception) {
            GTLog.logger.error("Failed to get forge grass seed list", exception);
        }
        return result;
    }

    public static <T> int getRandomItem(Random random, List<? extends Entry<Integer, T>> randomList, int size) {
        if(randomList.isEmpty())
            return -1;
        int[] baseOffsets = new int[size];
        int currentIndex = 0;
        for(int i = 0; i < size; i++) {
            Entry<Integer, T> entry = randomList.get(i);
            if(entry.getKey() <= 0) {
                throw new IllegalArgumentException("Invalid weight: " + entry.getKey());
            }
            currentIndex += entry.getKey();
            baseOffsets[i] = currentIndex;
        }
        int randomValue = random.nextInt(currentIndex);
        for(int i = 0; i < size; i++) {
            if(randomValue < baseOffsets[i])
                return i;
        }
        throw new IllegalArgumentException("Invalid weight");
    }

    @Nullable
    public static EnumFacing determineWrenchingSide(EnumFacing facing, float x, float y, float z) {
        EnumFacing opposite = facing.getOpposite();
        switch (facing) {
            case DOWN:
            case UP:
                if (x < 0.25) {
                    if (z < 0.25) return opposite;
                    if (z > 0.75) return opposite;
                    return EnumFacing.WEST;
                }
                if (x > 0.75) {
                    if (z < 0.25) return opposite;
                    if (z > 0.75) return opposite;
                    return EnumFacing.EAST;
                }
                if (z < 0.25) return EnumFacing.NORTH;
                if (z > 0.75) return EnumFacing.SOUTH;
                return facing;
            case NORTH:
            case     SOUTH:
                if (x < 0.25) {
                    if (y < 0.25) return opposite;
                    if (y > 0.75) return opposite;
                    return EnumFacing.WEST;
                }
                if (x > 0.75) {
                    if (y < 0.25) return opposite;
                    if (y > 0.75) return opposite;
                    return EnumFacing.EAST;
                }
                if (y < 0.25) return EnumFacing.DOWN;
                if (y > 0.75) return EnumFacing.UP;
                return facing;
            case WEST:
            case     EAST:
                if (z < 0.25) {
                    if (y < 0.25) return opposite;
                    if (y > 0.75) return opposite;
                    return EnumFacing.NORTH;
                }
                if (z > 0.75) {
                    if (y < 0.25) return opposite;
                    if (y > 0.75) return opposite;
                    return EnumFacing.SOUTH;
                }
                if (y < 0.25) return EnumFacing.DOWN;
                if (y > 0.75) return EnumFacing.UP;
                return facing;
        }
        return null;
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
                if (equipment.isEmpty() || !suitParts.contains(new SimpleItemStack(equipment))) {
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

    public static List<FluidStack> copyFluidList(List<FluidStack> fluidStacks) {
        FluidStack[] stacks = new FluidStack[fluidStacks.size()];
        for (int i = 0; i < fluidStacks.size(); i++) stacks[i] = fluidStacks.get(i).copy();
        return Lists.newArrayList(stacks);
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

    public static <T extends Comparable<T>> IBlockState[] getAllPropertyValues(IBlockState blockState, IProperty<T> property) {
        Collection<T> allowedValues = property.getAllowedValues();
        IBlockState[] resultArray = new IBlockState[allowedValues.size()];
        int index = 0;
        for(T propertyValue : allowedValues) {
            resultArray[index++] = blockState.withProperty(property, propertyValue);
        }
        return resultArray;
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

	public static long createFlag(int id) {
		return 1L << id;
	}

}