package gregtech.api.util;


import gregtech.api.GregTechAPI;
import gregtech.api.capability.*;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.enchants.EnchantmentRadioactivity;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.enums.SubTag;
import gregtech.api.events.BlockScanningEvent;
import gregtech.api.items.IDebugableBlock;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.common.GT_Proxy;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.IC2Potion;
import ic2.core.ref.ItemName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

import static gregtech.api.GTValues.*;

public class GT_Utility {

    private static int sBookCount = 0;

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

    public static World getBlockAcessWorld(IBlockAccess blockAccess) {
        if(blockAccess instanceof ChunkCache) {
            return ObfuscationReflectionHelper.getPrivateValue(ChunkCache.class, (ChunkCache) blockAccess, 4); //worldObj
        } else if(blockAccess instanceof World) {
            return (World) blockAccess;
        }
        return null; //unknown implementation
    }

    public static Field getField(Object aObject, String aField) {
        Field rField = null;
        try {
            rField = aObject.getClass().getDeclaredField(aField);
            rField.setAccessible(true);
        } catch (Throwable throwable) {}
        return rField;
    }

    public static Field getField(Class aObject, String aField) {
        Field rField = null;
        try {
            rField = aObject.getDeclaredField(aField);
            rField.setAccessible(true);
        } catch (Throwable throwable) {}
        return rField;
    }

    public static Method getMethod(Class<?> aObject, String aMethod, Class<?>... aParameterTypes) {
        Method rMethod = null;
        try {
            rMethod = aObject.getMethod(aMethod, aParameterTypes);
            rMethod.setAccessible(true);
        } catch (Throwable throwable) {}
        return rMethod;
    }

    public static Field getField(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class) aObject).getDeclaredField(aField) : (aObject instanceof String) ? Class.forName((String) aObject).getDeclaredField(aField) : aObject.getClass().getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField;
        } catch (Throwable throwable) {
            if (aLogErrors) {
                throwable.printStackTrace(GTLog.err);
            }
        }
        return null;
    }

    public static Object getFieldContent(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class) aObject).getDeclaredField(aField) : (aObject instanceof String) ? Class.forName((String) aObject).getDeclaredField(aField) : aObject.getClass().getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField.get(aObject instanceof Class || aObject instanceof String ? null : aObject);
        } catch (Throwable e) {
            if (aLogErrors) e.printStackTrace(GTLog.err);
        }
        return null;
    }

    public static Object callMethod(Object aObject, String aMethod, boolean aPrivate, boolean aUseUpperCasedDataTypes, boolean aLogErrors, Object... aParameters) {
        try {
            Class<?>[] tParameterTypes = new Class<?>[aParameters.length];
            for (byte i = 0; i < aParameters.length; i++) {
                if (aParameters[i] instanceof Class) {
                    tParameterTypes[i] = (Class) aParameters[i];
                    aParameters[i] = null;
                } else {
                    tParameterTypes[i] = aParameters[i].getClass();
                }
                if (!aUseUpperCasedDataTypes) {
                    if (tParameterTypes[i] == Boolean.class) tParameterTypes[i] = boolean.class;
                    else if (tParameterTypes[i] == Byte.class) tParameterTypes[i] = byte.class;
                    else if (tParameterTypes[i] == Short.class) tParameterTypes[i] = short.class;
                    else if (tParameterTypes[i] == Integer.class) tParameterTypes[i] = int.class;
                    else if (tParameterTypes[i] == Long.class) tParameterTypes[i] = long.class;
                    else if (tParameterTypes[i] == Float.class) tParameterTypes[i] = float.class;
                    else if (tParameterTypes[i] == Double.class) tParameterTypes[i] = double.class;
                }
            }

            Method tMethod = (aObject instanceof Class) ? ((Class<?>) aObject).getMethod(aMethod, tParameterTypes) :
                    aObject.getClass().getMethod(aMethod, tParameterTypes);
            if (aPrivate) tMethod.setAccessible(true);
            return tMethod.invoke(aObject, aParameters);
        } catch (Throwable throwable) {
            if (aLogErrors) {
                throwable.printStackTrace(GTLog.err);
            }
        }
        return null;
    }

    public static Object callConstructor(String aClass, int aConstructorIndex, Object aReplacementObject, boolean aLogErrors, Object... aParameters) {
        if (aConstructorIndex < 0) {
            try {
                for (Constructor tConstructor : Class.forName(aClass).getConstructors()) {
                    try {
                        return tConstructor.newInstance(aParameters);
                    } catch (Throwable throwable) {}
                }
            } catch (Throwable e) {
                if (aLogErrors) e.printStackTrace(GTLog.err);
            }
        } else {
            try {
                return Class.forName(aClass).getConstructors()[aConstructorIndex].newInstance(aParameters);
            } catch (Throwable throwable) {
                if(aLogErrors) {
                    throwable.printStackTrace(GTLog.err);
                }
            }
        }
        return aReplacementObject;
    }

    public static PotionEffect copyPotionEffect(PotionEffect sample) {
        PotionEffect potionEffect = new PotionEffect(sample.getPotion(), sample.getDuration(), sample.getAmplifier(), sample.getIsAmbient(), sample.doesShowParticles());
        potionEffect.setCurativeItems(sample.getCurativeItems());
        return potionEffect;
    }
    
    @SuppressWarnings("deprecation")
    public static void addPotionTooltip(Iterable<PotionEffect> potions, List<String> lines) {
        ArrayList<Tuple<String, AttributeModifier>> attributeLines = new ArrayList<>();
        
        for (PotionEffect potionEffect : potions) {
            String line = I18n.translateToLocal(potionEffect.getEffectName()).trim();
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
                line = line + " " + I18n.translateToLocal("potion.potency." + potionEffect.getAmplifier()).trim();
            }
            if (potionEffect.getDuration() > 20) {
                line = line + " (" + Potion.getPotionDurationString(potionEffect, 1.0f) + ")";
            }
            if (potion.isBadEffect()) {
                lines.add(TextFormatting.RED + line);
            }
            else {
                lines.add(TextFormatting.BLUE + line);
            }
        }
        if (!attributeLines.isEmpty()) {
            lines.add("");
            lines.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("potion.whenDrank"));
            
            for (Tuple<String, AttributeModifier> tuple : attributeLines) {
                AttributeModifier modifier = tuple.getSecond();
                double d0 = modifier.getAmount();
                double d1;
                if (modifier.getOperation() != 1 && modifier.getOperation() != 2) {
                    d1 = modifier.getAmount();
                }
                else {
                    d1 = modifier.getAmount() * 100.0D;
                }
                if (d0 > 0.0D) {
                    lines.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("attribute.modifier.plus." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + tuple.getFirst())));
                }
                else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    lines.add(TextFormatting.RED + I18n.translateToLocalFormatted("attribute.modifier.take." + modifier.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + tuple.getFirst())));
                }
            }

        }
    }

    public static String capitalizeString(String aString) {
        if (aString != null && aString.length() > 0)
            return aString.substring(0, 1).toUpperCase() + aString.substring(1);
        return "";
    }

    public static int fillFluidTank(World world, BlockPos blockPos, EnumFacing side, FluidStack fill) {
        TileEntity tileEntity = world.getTileEntity(blockPos.offset(side));
        if (tileEntity != null && fill != null) {
            if (tileEntity instanceof IFluidHandler) {
                IFluidHandler fluidHandler = (IFluidHandler) tileEntity;
                return fluidHandler.fill(side.getOpposite(), fill, true);
            }
            if (tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite())) {
                net.minecraftforge.fluids.capability.IFluidHandler fluidHandler =
                        tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                return fluidHandler.fill(fill, true);
            }
        }
        return 0;
    }

    @SuppressWarnings("unused")
    public static ItemStack suckOneItemStackAt(World aWorld, double aX, double aY, double aZ, double aL, double aH, double aW) {
        for (EntityItem tItem : aWorld.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(aX, aY, aZ, aX + aL, aY + aH, aZ + aW))) {
            if (!tItem.isDead) {
                aWorld.removeEntity(tItem);
                tItem.setDead();
                return tItem.getEntityItem();
            }
        }
        return null;
    }

    public static byte getTier(long l) {
        byte i = -1;
        while (++i < V.length) if (l <= V[i]) return i;
        return i;
    }

    public static void checkAvailabilities() {
        if (CHECK_ALL) {
            try {
                Class tClass = cofh.api.energy.IEnergyReceiver.class;
                tClass.getCanonicalName();
                RF_CHECK = true;
            } catch (Throwable exception) {}
            CHECK_ALL = false;
        }
    }

    /**
     * Moves Stack from Inv-Slot to Inv-Slot, without checking if its even allowed. (useful for internal Inventory Operations)
     *
     * @return the Amount of moved Items
     */
    public static byte moveStackFromSlotAToSlotB(IInventory aTileEntity1, IInventory aTileEntity2, int aGrabFrom, int aPutTo, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        if (aTileEntity1 == null || aTileEntity2 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMaxMoveAtOnce <= 0 || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;

        ItemStack tStack1 = aTileEntity1.getStackInSlot(aGrabFrom), tStack2 = aTileEntity2.getStackInSlot(aPutTo), tStack3;
        if (tStack1 != null) {
            if (tStack2 != null && !areStacksEqual(tStack1, tStack2)) return 0;
            tStack3 = copy(tStack1);
            aMaxTargetStackSize = (byte) Math.min(aMaxTargetStackSize, Math.min(tStack3.getMaxStackSize(), Math.min(tStack2 == null ? Integer.MAX_VALUE : tStack2.getMaxStackSize(), aTileEntity2.getInventoryStackLimit())));
            tStack3.stackSize = Math.min(tStack3.stackSize, aMaxTargetStackSize - (tStack2 == null ? 0 : tStack2.stackSize));
            if (tStack3.stackSize > aMaxMoveAtOnce) tStack3.stackSize = aMaxMoveAtOnce;
            if (tStack3.stackSize + (tStack2 == null ? 0 : tStack2.stackSize) >= Math.min(tStack3.getMaxStackSize(), aMinTargetStackSize) && tStack3.stackSize >= aMinMoveAtOnce) {
                tStack3 = aTileEntity1.decrStackSize(aGrabFrom, tStack3.stackSize);
                aTileEntity1.markDirty();
                if (tStack3 != null) {
                    if (tStack2 == null) {
                        aTileEntity2.setInventorySlotContents(aPutTo, copy(tStack3));
                        aTileEntity2.markDirty();
                    } else {
                        tStack2.stackSize += tStack3.stackSize;
                        aTileEntity2.markDirty();
                    }
                    return (byte) tStack3.stackSize;
                }
            }
        }
        return 0;
    }

    @SuppressWarnings("unused")
    public static boolean isAllowedToTakeFromSlot(IInventory aTileEntity, int aSlot, EnumFacing aSide, ItemStack aStack) {
        if (aSide == null) {
            for(EnumFacing side : EnumFacing.VALUES) {
                if(isAllowedToTakeFromSlot(aTileEntity, aSlot, side, aStack))
                    return true;
            }
        }
        return !(aTileEntity instanceof ISidedInventory) ||
                ((ISidedInventory) aTileEntity).canExtractItem(aSlot, aStack, aSide);
    }

    @SuppressWarnings("unused")
    public static boolean isAllowedToPutIntoSlot(IInventory aTileEntity, int aSlot, EnumFacing aSide, ItemStack aStack, byte aMaxStackSize) {
        ItemStack tStack = aTileEntity.getStackInSlot(aSlot);
        if (tStack != null && (!areStacksEqual(tStack, aStack) || tStack.stackSize >= tStack.getMaxStackSize()))
            return false;
        if (aSide == null) {
            for(EnumFacing side : EnumFacing.VALUES) {
                if(isAllowedToPutIntoSlot(aTileEntity, aSlot, side, aStack, aMaxStackSize))
                    return true;
            }
        }
        aStack = copyAmount(aMaxStackSize, aStack);
        return !(aTileEntity instanceof ISidedInventory &&
                !((ISidedInventory) aTileEntity).canInsertItem(aSlot, aStack, aSide)) &&
                aTileEntity.isItemValidForSlot(aSlot, aStack);
    }

    public static boolean listContainsItem(Collection<ItemStack> aList, ItemStack aStack, boolean aTIfListEmpty, boolean aInvertFilter) {
        if (aStack == null || aStack.stackSize < 1) return false;
        if (aList == null) return aTIfListEmpty;
        while (aList.contains(null)) aList.remove(null);
        if (aList.size() < 1) return aTIfListEmpty;
        Iterator<ItemStack> tIterator = aList.iterator();
        ItemStack tStack = null;
        while (tIterator.hasNext())
            if ((tStack = tIterator.next()) != null && areStacksEqual(aStack, tStack)) return !aInvertFilter;
        return aInvertFilter;
    }

    //TODO delete after ModHandler rewite
    public static <T> boolean arrayContainsNonNull(T... aArray) {
        if (aArray != null) for (Object tObject : aArray) if (tObject != null) return true;
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean areStacksOrToolsEqual(ItemStack aStack1, ItemStack aStack2) {
        if (aStack1 != null && aStack2 != null && aStack1.getItem() == aStack2.getItem()) {
            if (aStack1.getItem().isDamageable()) return true;
            return ((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null)) && (aStack1.getTagCompound() == null || aStack1.getTagCompound().equals(aStack2.getTagCompound())) && (Items.FEATHER.getDamage(aStack1) == Items.FEATHER.getDamage(aStack2) || Items.FEATHER.getDamage(aStack1) == W || Items.FEATHER.getDamage(aStack2) == W);
        }
        return false;
    }

    public static boolean areFluidsEqual(FluidStack aFluid1, FluidStack aFluid2) {
        return areFluidsEqual(aFluid1, aFluid2, false);
    }

    public static boolean areFluidsEqual(FluidStack aFluid1, FluidStack aFluid2, boolean aIgnoreNBT) {
        return aFluid1 != null && aFluid2 != null && aFluid1.getFluid() == aFluid2.getFluid() && (aIgnoreNBT || ((aFluid1.tag == null) == (aFluid2.tag == null)) && (aFluid1.tag == null || aFluid1.tag.equals(aFluid2.tag)));
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
        return areStacksEqual(aStack1, aStack2, false);
    }

    public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return aStack1 != null && aStack2 != null && aStack1.getItem() == aStack2.getItem() && (aIgnoreNBT || ((aStack1.getTagCompound() == null) == (aStack2.getTagCompound() == null)) && (aStack1.getTagCompound() == null || aStack1.getTagCompound().equals(aStack2.getTagCompound()))) && (Items.FEATHER.getDamage(aStack1) == Items.FEATHER.getDamage(aStack2) || Items.FEATHER.getDamage(aStack1) == W || Items.FEATHER.getDamage(aStack2) == W);
    }

    public static boolean areUnificationsEqual(ItemStack aStack1, ItemStack aStack2) {
        return areUnificationsEqual(aStack1, aStack2, false);
    }

    public static boolean areUnificationsEqual(ItemStack aStack1, ItemStack aStack2, boolean aIgnoreNBT) {
        return areStacksEqual(OreDictionaryUnifier.get(aStack1), OreDictionaryUnifier.get(aStack2), aIgnoreNBT);
    }

    public static String getFluidName(Fluid aFluid, boolean aLocalized) {
        if (aFluid == null) return E;
        String rName = aLocalized ? aFluid.getLocalizedName(new FluidStack(aFluid, 0)) : aFluid.getUnlocalizedName();
        if (rName.contains("fluid.") || rName.contains("tile."))
            return capitalizeString(rName.replaceAll("fluid.", E).replaceAll("tile.", E));
        return rName;
    }

    public static String getFluidName(FluidStack aFluid, boolean aLocalized) {
        if (aFluid == null) return E;
        return getFluidName(aFluid.getFluid(), aLocalized);
    }

    public static void addFluidContainerData(FluidContainerData aData) {
        sFluidContainerList.add(aData);
        sFilledContainerToData.put(new SimpleItemStack(aData.filledContainer), aData);
        Map<Fluid, FluidContainerData> tFluidToContainer = sEmptyContainerToFluidToData.get(new SimpleItemStack(aData.emptyContainer));
        if (tFluidToContainer == null) {
            sEmptyContainerToFluidToData.put(new SimpleItemStack(aData.emptyContainer), tFluidToContainer = new HashMap<>());
            GregTechAPI.sFluidMappings.add(tFluidToContainer);
        }
        tFluidToContainer.put(aData.fluid.getFluid(), aData);
    }

    public static ItemStack fillFluidContainer(FluidStack aFluid, ItemStack aStack, boolean aRemoveFluidDirectly, boolean aCheckIFluidContainerItems) {
        if (!isStackValid(aStack) || aFluid == null) return null;
        if (GT_ModHandler.isWater(aFluid) && ItemList.Bottle_Empty.isStackEqual(aStack)) {
            if (aFluid.amount >= 250) {
                if (aRemoveFluidDirectly) aFluid.amount -= 250;
                return new ItemStack(Items.POTIONITEM, 1, 0);
            }
            return null;
        }
        Map<Fluid, FluidContainerData> tFluidToContainer = sEmptyContainerToFluidToData.get(new SimpleItemStack(aStack));
        if(tFluidToContainer != null) {
            FluidContainerData tData = tFluidToContainer.get(aFluid.getFluid());
            if(tData != null && tData.fluid.amount <= aFluid.amount) {
                if(aRemoveFluidDirectly) aFluid.amount -= tData.fluid.amount;
                return copyAmount(1, tData.filledContainer);
            }
        }

        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem &&
                ((IFluidContainerItem) aStack.getItem()).getFluid(aStack) != null &&
                ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) <= aFluid.amount) {
            aStack = copyAmount(1, aStack);
            int filled = ((IFluidContainerItem) aStack.getItem()).fill(aStack, aFluid, true);
            if (filled > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            if(aRemoveFluidDirectly) aFluid.amount -= filled;
            return null;
        }

        if(aCheckIFluidContainerItems && aStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            aStack = copyAmount(1, aStack);
            int filled = aStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).fill(aFluid, true);
            if (filled > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            if(aRemoveFluidDirectly) aFluid.amount -= filled;
            return null;
        }

        return null;
    }

    public static ItemStack getFluidDisplayStack(Fluid aFluid) {
        return aFluid == null ? null : getFluidDisplayStack(new FluidStack(aFluid, 0), false);
    }

    public static ItemStack getFluidDisplayStack(FluidStack aFluid, boolean aUseStackSize) {
        if (aFluid == null || aFluid.getFluid() == null) return null;
        int tmp = FluidRegistry.getFluidID(aFluid.getFluid());

        ItemStack rStack = ItemList.Display_Fluid.getWithDamage(aUseStackSize ? aFluid.amount / 1000 : 1, tmp);
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setLong("mFluidDisplayAmount", aFluid.amount);
        tNBT.setLong("mFluidDisplayHeat", aFluid.getFluid().getTemperature(aFluid));
        tNBT.setBoolean("mFluidState", aFluid.getFluid().isGaseous(aFluid));
        rStack.setTagCompound(tNBT);
        return rStack;
    }

    public static boolean containsFluid(ItemStack aStack, Fluid fluid, boolean aCheckIFluidContainerItems) {
        if (!isStackValid(aStack) || fluid == null) return false;
        FluidStack contents = getFluidForFilledItem(aStack, aCheckIFluidContainerItems);
        if(contents != null) return contents.getFluid() == fluid;
        return false;
    }

    public static FluidStack getFluidForFilledItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (!isStackValid(aStack)) return null;
        FluidContainerData tData = sFilledContainerToData.get(new SimpleItemStack(aStack));
        if(tData != null) return tData.fluid.copy();
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem &&
                ((IFluidContainerItem) aStack.getItem()).getFluid(aStack) != null &&
                ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            aStack = copyAmount(1, aStack);
            return ((IFluidContainerItem) aStack.getItem()).drain(aStack, Integer.MAX_VALUE, true);
        }
        if(aCheckIFluidContainerItems && aStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            aStack = copyAmount(1, aStack);
            return aStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(Integer.MAX_VALUE, true);
        }
        return null;
    }

    public static ItemStack getContainerForFilledItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (!isStackValid(aStack)) return null;
        FluidContainerData tData = sFilledContainerToData.get(new SimpleItemStack(aStack));
        if (tData != null) return copyAmount(1, tData.emptyContainer);
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem &&
                ((IFluidContainerItem) aStack.getItem()).getFluid(aStack) != null &&
                ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            aStack = copyAmount(1, aStack);
            FluidStack drained = ((IFluidContainerItem) aStack.getItem()).drain(aStack, Integer.MAX_VALUE, true);
            if (drained != null && drained.amount > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            return null;
        }

        if(aCheckIFluidContainerItems && aStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            aStack = copyAmount(1, aStack);
            FluidStack drained = aStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(Integer.MAX_VALUE, true);
            if (drained != null && drained.amount > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            return null;
        }
        return null;
    }

    public static ItemStack getContainerItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (isStackValid(aStack)) return null;
        if (aStack.getItem().hasContainerItem(aStack)) return aStack.getItem().getContainerItem(aStack);
        /* These are all special Cases, in which it is intended to have only GT Blocks outputting those Container Items */
        if (ItemList.Cell_Empty.isStackEqual(aStack, false, true)) return null;
        if (ItemList.IC2_Fuel_Can_Filled.isStackEqual(aStack, false, true)) return ItemList.IC2_Fuel_Can_Empty.get(1);
        if (aStack.getItem() == Items.POTIONITEM || aStack.getItem() == Items.EXPERIENCE_BOTTLE || ItemList.TF_Vial_FieryBlood.isStackEqual(aStack) || ItemList.TF_Vial_FieryTears.isStackEqual(aStack))
            return ItemList.Bottle_Empty.get(1);

        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem &&
                ((IFluidContainerItem) aStack.getItem()).getFluid(aStack) != null &&
                ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            aStack = copyAmount(1, aStack);
            FluidStack drained = ((IFluidContainerItem) aStack.getItem()).drain(aStack, Integer.MAX_VALUE, true);
            if (drained != null && drained.amount > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            return null;
        }

        if(aCheckIFluidContainerItems && aStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            aStack = copyAmount(1, aStack);
            FluidStack drained = aStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(Integer.MAX_VALUE, true);
            if (drained != null && drained.amount > 0 && !areStacksEqual(aStack, aStack)) return aStack;
            return null;
        }

        int tCapsuleCount = GT_ModHandler.getCapsuleCellContainerCount(aStack);
        if (tCapsuleCount > 0) return ItemList.Cell_Empty.get(tCapsuleCount);

        if (ItemList.IC2_ForgeHammer.isStackEqual(aStack) || ItemList.IC2_WireCutter.isStackEqual(aStack))
            return copyMetaData(Items.FEATHER.getDamage(aStack) + 1, aStack);
        return null;
    }

    public static synchronized boolean removeSimpleIC2MachineRecipe(ItemStack aInput, Iterable<IMachineRecipeManager.RecipeIoContainer> aRecipeList, ItemStack aOutput) {
        if ((!isStackValid(aInput) && !isStackValid(aOutput)) || aRecipeList == null) return false;
        boolean rReturn = false;
        Iterator<IMachineRecipeManager.RecipeIoContainer> tIterator = aRecipeList.iterator();
        aOutput = OreDictionaryUnifier.get(aOutput);
        while (tIterator.hasNext()) {
            IMachineRecipeManager.RecipeIoContainer tEntry = tIterator.next();
            if (aInput == null || tEntry.input.matches(aInput)) {
                List<ItemStack> tList = tEntry.output.items;
                if (tList != null) for (ItemStack tOutput : tList)
                    if (aOutput == null || areStacksEqual(OreDictionaryUnifier.get(tOutput), aOutput)) {
                        tIterator.remove();
                        rReturn = true;
                        break;
                    }
            }
        }
        return rReturn;
    }

    public static boolean addSimpleIC2MachineRecipe(ItemStack aInput, IMachineRecipeManager aRecipeList, NBTTagCompound aNBT, Object... aOutput) {
        if (!isStackValid(aInput) || aOutput.length == 0 || aRecipeList == null) return false;
        ItemMaterialInfo tOreName = OreDictionaryUnifier.getAssociation(aInput);
        for (int i = 0; i < aOutput.length; i++) {
            if (aOutput[i] == null) {
                GTLog.err.println("EmptyIC2Output!" + aInput.getUnlocalizedName());
                return false;
            }
        }
        ItemStack[] tStack = OreDictionaryUnifier.getStackArray(true, aOutput);
        if (tStack == null || (tStack.length > 0 && GT_Utility.areStacksEqual(aInput, tStack[0]))) return false;
        if (tOreName != null) {
            //Catch Fossils Archeology Revival crash
            if (tOreName.toString().equals("dustAsh") && tStack[0].getUnlocalizedName().equals("tile.volcanicAsh"))
                return false;
            aRecipeList.addRecipe(new RecipeInputOreDict(tOreName.toString(), aInput.stackSize), aNBT, true, tStack);
        } else {
            aRecipeList.addRecipe(new RecipeInputItemStack(copy(aInput), aInput.stackSize), aNBT, true, tStack);
        }
        return true;
    }

    public static ItemStack getWrittenBook(String aMapping, ItemStack aStackToPutNBT) {
        if (!isStringValid(aMapping)) return null;
        ItemStack rStack = GregTechAPI.sBookList.get(aMapping);
        if (rStack == null) return aStackToPutNBT;
        if (aStackToPutNBT != null) {
            aStackToPutNBT.setTagCompound(rStack.getTagCompound());
            return aStackToPutNBT;
        }
        return copyAmount(1, rStack);
    }

    public static ItemStack getWrittenBook(String aMapping, String aTitle, String aAuthor, String... aPages) {
        if (!isStringValid(aMapping)) return null;
        ItemStack rStack = GregTechAPI.sBookList.get(aMapping);
        if (rStack != null) return copyAmount(1, rStack);
        if (!isStringValid(aTitle) || !isStringValid(aAuthor) || aPages.length <= 0) return null;
        sBookCount++;
        rStack = new ItemStack(Items.WRITTEN_BOOK, 1);
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setString("title", GT_LanguageManager.addStringLocalization("Book." + aTitle + ".Name", aTitle));
        tNBT.setString("author", aAuthor);
        NBTTagList tNBTList = new NBTTagList();
        for (byte i = 0; i < aPages.length; i++) {
            aPages[i] = GT_LanguageManager.addStringLocalization("Book." + aTitle + ".Page" + ((i < 10) ? "0" + i : i), aPages[i]);
            if (i < 48) {
                if (aPages[i].length() < 256)
                    tNBTList.appendTag(new NBTTagString(aPages[i]));
                else
                    GTLog.err.println("WARNING: String for written Book too long! -> " + aPages[i]);
            } else {
                GTLog.err.println("WARNING: Too much Pages for written Book! -> " + aTitle);
                break;
            }
        }
        tNBTList.appendTag(new NBTTagString("Credits to " + aAuthor + " for writing this Book. This was Book Nr. " + sBookCount + " at its creation. Gotta get 'em all!"));
        tNBT.setTag("pages", tNBTList);
        rStack.setTagCompound(tNBT);
        GTLog.out.println("GT_Mod: Added Book to Book List  -  Mapping: '" + aMapping + "'  -  Name: '" + aTitle + "'  -  Author: '" + aAuthor + "'");
        GregTechAPI.sBookList.put(aMapping, rStack);
        return copy(rStack);
    }

    public static void playSound(World world, double x, double y, double z, ResourceLocation soundName, SoundCategory category, float strength, float modulation) {
        world.playSound(x, y, z, SoundEvent.REGISTRY.getObject(soundName), category, strength, modulation, false);
    }

    public static void playSound(EntityPlayer player, World world, double x, double y, double z, ResourceLocation soundName, SoundCategory category, float strength, float modulation) {
        world.playSound(player, x, y, z, SoundEvent.REGISTRY.getObject(soundName), category, strength, modulation);
    }

    public static int stackToIntHash(ItemStack aStack) {
        if (!isStackValid(aStack)) return 0;
        return Item.getIdFromItem(aStack.getItem()) | (Items.FEATHER.getDamage(aStack) << 16);
    }

    public static boolean isStringValid(String aString) {
        return aString != null && !aString.isEmpty();
    }

    public static boolean isStackValid(ItemStack aStack) {
        return aStack.getItem() != null && aStack.stackSize >= 0;
    }

    public static boolean isDebugItem(ItemStack aStack) {
        return areStacksEqual(GT_ModHandler.getIC2Item(ItemName.debug_item, 1), aStack, true);
    }

    public static boolean hasBlockHitBox(World aWorld, BlockPos blockPos) {
        AxisAlignedBB box = aWorld.getBlockState(blockPos).getCollisionBoundingBox(aWorld, blockPos);
        return box != null && (box.maxX != box.minX && box.maxZ != box.minZ && box.maxY != box.minY);
    }

    public static void setCoordsOnFire(World aWorld, BlockPos blockPos, boolean aReplaceCenter) {
        IBlockState fire = Blocks.FIRE.getDefaultState();
        if (aReplaceCenter)
            if (aWorld.getBlockState(blockPos).getCollisionBoundingBox(aWorld, blockPos) == null)
                aWorld.setBlockState(blockPos, fire);
        if (aWorld.getBlockState(blockPos.east()).getCollisionBoundingBox(aWorld, blockPos.east()) == null)
            aWorld.setBlockState(blockPos.east(), fire);
        if (aWorld.getBlockState(blockPos.south()).getCollisionBoundingBox(aWorld, blockPos.south()) == null)
            aWorld.setBlockState(blockPos.south(), fire);
        if (aWorld.getBlockState(blockPos.west()).getCollisionBoundingBox(aWorld, blockPos.west()) == null)
            aWorld.setBlockState(blockPos.west(), fire);
        if (aWorld.getBlockState(blockPos.north()).getCollisionBoundingBox(aWorld, blockPos.north()) == null)
            aWorld.setBlockState(blockPos.north(), fire);
        if (aWorld.getBlockState(blockPos.up()).getCollisionBoundingBox(aWorld, blockPos.up()) == null)
            aWorld.setBlockState(blockPos.up(), fire);
        if (aWorld.getBlockState(blockPos.down()).getCollisionBoundingBox(aWorld, blockPos.down()) == null)
            aWorld.setBlockState(blockPos.down(), fire);
    }

    public static ItemStack getProjectile(SubTag aProjectileType, IInventory aInventory) {
        if (aInventory != null) for (int i = 0, j = aInventory.getSizeInventory(); i < j; i++) {
            ItemStack rStack = aInventory.getStackInSlot(i);
            if (isStackValid(rStack) && rStack.getItem() instanceof IProjectileItem && ((IProjectileItem) rStack.getItem()).hasProjectile(aProjectileType, rStack))
                return updateItemStack(rStack);
        }
        return null;
    }

    public static void removeNullStacksFromInventory(IInventory aInventory) {
        if (aInventory != null) for (int i = 0, j = aInventory.getSizeInventory(); i < j; i++) {
            ItemStack tStack = aInventory.getStackInSlot(i);
            if (tStack != null && (tStack.stackSize == 0 || tStack.getItem() == null))
                aInventory.setInventorySlotContents(i, null);
        }
    }

    /**
     * Converts a Number to a String
     */
    public static String makeFancyNumberString(int aNumber) {
        boolean temp = true, negative = false;

        if (aNumber < 0) {
            aNumber *= -1;
            negative = true;
        }

        StringBuilder tStringB = new StringBuilder();
        for (int i = 1000000000; i > 0; i /= 10) {
            int tDigit = (aNumber / i) % 10;
            if (temp && tDigit != 0) temp = false;
            if (!temp) {
                tStringB.append(tDigit);
                if (i != 1) for (int j = i; j > 0; j /= 1000) if (j == 1) tStringB.append(",");
            }
        }
        String tString = tStringB.toString();

        if (tString.equals("")) tString = "0";

        return negative ? "-" + tString : tString;
    }

    public static boolean isWearingFullFrostHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sFrostHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullHeatHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sHeatHazmatList)) return false;
        return true;
    }

    @SuppressWarnings("unused")
    public static boolean isWearingFullBioHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sBioHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullRadioHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sRadioHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullElectroHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sElectroHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullGasHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTechAPI.sGasHazmatList)) return false;
        return true;
    }

    public static float getHeatDamageFromItem(ItemStack aStack) {
        ItemMaterialInfo tData = OreDictionaryUnifier.getItemData(aStack);
        return tData == null ? 0 : (tData.mPrefix == null ? 0 : tData.mPrefix.mHeatDamage) + (tData.hasValidMaterialData() ? tData.mMaterial.mMaterial.mHeatDamage : 0);
    }

    public static int getRadioactivityLevel(ItemStack aStack) {
        ItemMaterialInfo tData = OreDictionaryUnifier.getItemData(aStack);
        if (tData != null && tData.hasValidMaterialData()) {
            if (tData.mMaterial.mMaterial.mEnchantmentArmors instanceof EnchantmentRadioactivity)
                return tData.mMaterial.mMaterial.mEnchantmentArmorsLevel;
            if (tData.mMaterial.mMaterial.mEnchantmentTools instanceof EnchantmentRadioactivity)
                return tData.mMaterial.mMaterial.mEnchantmentToolsLevel;
        }
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentRadioactivity.INSTANCE, aStack);
    }

    @SuppressWarnings("unused")
    public static boolean isImmuneToBreathingGasses(EntityLivingBase aEntity) {
        return isWearingFullGasHazmat(aEntity);
    }

    public static boolean applyHeatDamage(EntityLivingBase aEntity, float aDamage) {
        if (aDamage > 0 && aEntity != null && aEntity.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null && !isWearingFullHeatHazmat(aEntity)) {
            aEntity.attackEntityFrom(DamageSources.getHeatDamage(), aDamage);
            return true;
        }
        return false;
    }

    public static boolean applyFrostDamage(EntityLivingBase aEntity, float aDamage) {
        if (aDamage > 0 && aEntity != null && !isWearingFullFrostHazmat(aEntity)) {
            aEntity.attackEntityFrom(DamageSources.getFrostDamage(), aDamage);
            return true;
        }
        return false;
    }

    public static boolean applyElectricityDamage(EntityLivingBase aEntity, long aVoltage, long aAmperage) {
        long aDamage = getTier(aVoltage) * aAmperage * 4;
        if (aDamage > 0 && aEntity != null && !isWearingFullElectroHazmat(aEntity)) {
            aEntity.attackEntityFrom(DamageSources.getElectricDamage(), aDamage);
            return true;
        }
        return false;
    }

    public static boolean applyRadioactivity(EntityLivingBase aEntity, int aLevel, int aAmountOfItems) {
        if (aLevel > 0 && aEntity != null && aEntity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && aEntity.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD && !isWearingFullRadioHazmat(aEntity)) {
            aEntity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, aLevel * 140 * aAmountOfItems));
            aEntity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, aLevel * 130 * aAmountOfItems));
            aEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, aLevel * 150 * aAmountOfItems));
            aEntity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, aLevel * 130 * aAmountOfItems));
            aEntity.addPotionEffect(new PotionEffect(IC2Potion.radiation, aLevel * 180 * aAmountOfItems));
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack setStack(ItemStack aSetStack, ItemStack aToStack) {
        if (!isStackValid(aSetStack) || !isStackValid(aToStack)) return null;
        aSetStack.setItem(aToStack.getItem());
        aSetStack.stackSize = aToStack.stackSize;
        Items.FEATHER.setDamage(aSetStack, Items.FEATHER.getDamage(aToStack));
        aSetStack.setTagCompound(aToStack.getTagCompound());
        return aSetStack;
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

    public static FluidStack[] copyFluidArray(FluidStack... aStacks) {
        FluidStack[] rStacks = new FluidStack[aStacks.length];
        for (int i = 0; i < aStacks.length; i++) if (aStacks[i] != null) rStacks[i] = aStacks[i].copy();
        return rStacks;
    }

    public static ItemStack copy(ItemStack... aStacks) {
        for (ItemStack tStack : aStacks)
            if (isStackValid(tStack)) return tStack.copy();
        return null;
    }

    public static ItemStack copyAmount(int aAmount, ItemStack... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (!isStackValid(rStack)) return null;
        if (aAmount > 64) aAmount = 64;
        else if (aAmount == -1) aAmount = 111;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = (byte) aAmount;
        return rStack;
    }

    public static ItemStack copyMetaData(long aMetaData, ItemStack... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (!isStackValid(rStack)) return null;
        Items.FEATHER.setDamage(rStack, (short) aMetaData);
        return rStack;
    }

    public static ItemStack copyAmountAndMetaData(long aAmount, long aMetaData, ItemStack... aStacks) {
        ItemStack rStack = copyAmount(aAmount, aStacks);
        if (isStackValid(rStack)) return null;
        Items.FEATHER.setDamage(rStack, (short) aMetaData);
        return rStack;
    }

    /**
     * returns a copy of an ItemStack with its Stacksize being multiplied by aMultiplier
     */
    public static ItemStack mul(long aMultiplier, ItemStack... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (rStack == null) return null;
        rStack.stackSize *= aMultiplier;
        return rStack;
    }

    /**
     * Loads an ItemStack properly.
     */
    public static ItemStack loadItem(NBTTagCompound aNBT, String aTagName) {
        return loadItem(aNBT.getCompoundTag(aTagName));
    }

    /**
     * Loads an ItemStack properly.
     */
    public static ItemStack loadItem(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack rStack = ItemStack.loadItemStackFromNBT(aNBT);
        return OreDictionaryUnifier.get(true, rStack);
    }

    public static <E> E selectItemInList(int aIndex, E aReplacement, List<E> aList) {
        if (aList == null || aList.isEmpty()) return aReplacement;
        if (aList.size() <= aIndex) return aList.get(aList.size() - 1);
        if (aIndex < 0) return aList.get(0);
        return aList.get(aIndex);
    }

    public static boolean isStackInList(ItemStack aStack, Collection<SimpleItemStack> aList) {
        if (aStack == null) {
            return false;
        }
        return isStackInList(new SimpleItemStack(aStack), aList);
    }

    public static boolean isStackInList(SimpleItemStack aStack, Collection<SimpleItemStack> aList) {
        return aStack != null && (aList.contains(aStack) || aList.contains(new SimpleItemStack(aStack.mItem, aStack.mStackSize, W)));
    }

    /**
     * re-maps all Keys of a Map after the Keys were weakened.
     */
    public static <X, Y> Map<X, Y> reMap(Map<X, Y> aMap) {
        Map<X, Y> tMap = new HashMap<>();
        tMap.putAll(aMap);
        aMap.clear();
        aMap.putAll(tMap);
        return aMap;
    }

    /**
     * Why the fuck do neither Java nor Guava have a Function to do this?
     */
    @SuppressWarnings("unchecked")
    public static <X, Y extends Comparable> LinkedHashMap<X, Y> sortMapByValuesAcending(Map<X, Y> aMap) {
        List<Map.Entry<X, Y>> tEntrySet = new LinkedList<>(aMap.entrySet());
        tEntrySet.sort((aValue1, aValue2) -> aValue1.getValue().compareTo(aValue2.getValue()));
        LinkedHashMap<X, Y> rMap = new LinkedHashMap<>();
        for (Map.Entry<X, Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
        return rMap;
    }

    /**
     * Why the fuck do neither Java nor Guava have a Function to do this?
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <X, Y extends Comparable> LinkedHashMap<X, Y> sortMapByValuesDescending(Map<X, Y> aMap) {
        List<Map.Entry<X, Y>> tEntrySet = new LinkedList<>(aMap.entrySet());
        tEntrySet.sort((aValue1, aValue2) -> aValue2.getValue().compareTo(aValue1.getValue()));
        LinkedHashMap<X, Y> rMap = new LinkedHashMap<>();
        for (Map.Entry<X, Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
        return rMap;
    }

    /**
     * Translates a Material Amount into an Amount of Fluid in Fluid Material Units.
     */
    @SuppressWarnings("unused")
    public static long translateMaterialToFluidAmount(long aMaterialAmount, boolean aRoundUp) {
        return translateMaterialToAmount(aMaterialAmount, L, aRoundUp);
    }

    /**
     * Translates a Material Amount into an Amount of Fluid. Second Parameter for things like Bucket Amounts (1000) and similar
     */
    public static long translateMaterialToAmount(long aMaterialAmount, long aAmountPerUnit, boolean aRoundUp) {
        return Math.max(0, ((aMaterialAmount * aAmountPerUnit) / M) + (aRoundUp && (aMaterialAmount * aAmountPerUnit) % M > 0 ? 1 : 0));
    }

    /**
     * This checks if the Dimension is really a Dimension and not another Planet or something.
     * Used for my Teleporter.
     */
    public static boolean isRealDimension(int aDimensionID) {
        String clazzName = DimensionManager.getProvider(aDimensionID).getClass().getName().toLowerCase();
        if (clazzName.contains("mystcraft") || clazzName.contains("twilightforest") || clazzName.contains("rftools"))
            return true;
        return GregTechAPI.sDimensionalList.contains(aDimensionID);
    }

    public static boolean moveEntityToDimensionAtCoords(Entity aEntity, int aDimension, double aX, double aY, double aZ) {
        WorldServer tTargetWorld = DimensionManager.getWorld(aDimension), tOriginalWorld = DimensionManager.getWorld(aEntity.worldObj.provider.getDimension());
        if (tTargetWorld != null && tOriginalWorld != null && tTargetWorld != tOriginalWorld) {

            if (aEntity instanceof EntityPlayerMP) {
                EntityPlayerMP aPlayer = (EntityPlayerMP) aEntity;
                aPlayer.changeDimension(aDimension);
                aPlayer.connection.setPlayerLocation(aX + 0.5, aY + 0.5, aZ + 0.5, aPlayer.rotationYaw, aPlayer.rotationPitch);
            } else {
                aEntity.setPosition(aX + 0.5, aY + 0.5, aZ + 0.5);
                aEntity.worldObj.removeEntity(aEntity);
                aEntity.dimension = aDimension;
                aEntity.isDead = false;
                Entity tNewEntity = EntityList.createEntityByName(EntityList.getEntityString(aEntity), tTargetWorld);
                if (tNewEntity != null) {

                    NBTTagCompound nbttagcompound = aEntity.writeToNBT(new NBTTagCompound());
                    nbttagcompound.removeTag("Dimension");
                    tNewEntity.readFromNBT(nbttagcompound);
                    tNewEntity.timeUntilPortal = aEntity.timeUntilPortal;

                    aEntity.setDead();
                    tNewEntity.isDead = false;
                    boolean temp = tNewEntity.forceSpawn;
                    tNewEntity.forceSpawn = true;
                    tTargetWorld.spawnEntityInWorld(tNewEntity);
                    tNewEntity.forceSpawn = temp;
                    tNewEntity.isDead = false;
                    aEntity = tNewEntity;
                }
            }

            if (aEntity instanceof EntityLivingBase) {
                aEntity.setPositionAndUpdate(aX, aY, aZ);
            } else {
                aEntity.setPosition(aX, aY, aZ);
            }

            tOriginalWorld.resetUpdateEntityTick();
            tTargetWorld.resetUpdateEntityTick();
            return true;
        }
        return false;
    }

    public static FluidStack getUndergroundOil(World aWorld, int aX, int aZ) {
        Random tRandom = new Random((aWorld.getSeed() + (aX / 96) + (7 * (aZ / 96))));
        double amount = tRandom.nextInt(50) + tRandom.nextDouble();
        Fluid tFluid;
        switch (tRandom.nextInt(4)) {
            case 0:
                tFluid = Materials.NatruralGas.mGas;
                break;
            case 1:
                tFluid = Materials.OilLight.mFluid;
                break;
            case 2:
                tFluid = Materials.OilMedium.mFluid;
                break;
            case 3:
                tFluid = Materials.OilHeavy.mFluid;
                break;
            default:
                tFluid = Materials.Oil.mFluid;
        }
        int tAmount = (int) (Math.pow(amount, 5) / 100);
        ChunkPos tPos = new ChunkPos(aX / 16, aZ / 16);
        int[] tInts = new int[2];
        if (GT_Proxy.chunkData.containsKey(tPos)) {
            tInts = GT_Proxy.chunkData.get(tPos);
            if (tInts.length > 0) {
                if (tInts[0] > 0) {
                    tAmount = tInts[0];
                }
            }
            GT_Proxy.chunkData.remove(tPos);
        }
        tAmount = tAmount - 5;
        tInts[0] = tAmount;
        GT_Proxy.chunkData.put(tPos, tInts);
        return new FluidStack(tFluid, tAmount);
    }

    @SuppressWarnings("deprecation")
    public static int getCoordinateScan(ArrayList<String> aList, EntityPlayer aPlayer, World aWorld, int aScanLevel, BlockPos blockPos, EnumFacing aSide, float aClickX, float aClickY, float aClickZ) {
        ArrayList<String> tList = new ArrayList<>();
        int rEUAmount = 0;

        TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
        IBlockState tBlock = aWorld.getBlockState(blockPos);

        tList.add("----- X: " + blockPos.getX() + " Y: " + blockPos.getY() + " Z: " + blockPos.getZ() + " -----");

        if (tTileEntity instanceof IInventory)
            tList.add("Name: " + ((IInventory) tTileEntity).getName() + "  MetaData: " + aWorld.getBlockState(blockPos));
        else
            tList.add("Name: " + tBlock.getBlock().getUnlocalizedName() + "  MetaData: " + tBlock.getBlock().getMetaFromState(tBlock));

        tList.add("Hardness: " + tBlock.getBlockHardness(aWorld, blockPos) + "  Blast Resistance: " + tBlock.getBlock().getExplosionResistance(aWorld, blockPos, aPlayer, null));
        if (tBlock.getBlock().isBeaconBase(aWorld, blockPos, blockPos.add(0, 1, 0)))
            tList.add("Is valid Beacon Pyramid Material");

        if (tTileEntity != null) {

            if (tTileEntity instanceof IFluidHandler) {
                rEUAmount += 500;
                FluidTankInfo[] tTanks = ((IFluidHandler) tTileEntity).getTankInfo(aSide);
                if (tTanks != null) for (byte i = 0; i < tTanks.length; i++) {
                    tList.add("Tank " + i + ": " + GT_Utility.formatNumbers((tTanks[i].fluid == null ? 0 : tTanks[i].fluid.amount)) + " / " + GT_Utility.formatNumbers(tTanks[i].capacity) + " " + getFluidName(tTanks[i].fluid, true));
                }
            }

            if (tTileEntity instanceof ic2.api.reactor.IReactorChamber) {
                rEUAmount += 500;
                tTileEntity = (TileEntity) (((ic2.api.reactor.IReactorChamber) tTileEntity).getReactorInstance());
            }


            if (tTileEntity instanceof ic2.api.reactor.IReactor) {
                rEUAmount += 500;
                tList.add("Heat: " + ((ic2.api.reactor.IReactor) tTileEntity).getHeat() + "/" + ((ic2.api.reactor.IReactor) tTileEntity).getMaxHeat()
                        + "  HEM: " + ((ic2.api.reactor.IReactor) tTileEntity).getHeatEffectModifier() + "  Base EU Output: "/* + ((ic2.api.reactor.IReactor)tTileEntity).getOutput()*/);
            }

            if (tTileEntity instanceof ic2.api.tile.IWrenchable) {
                rEUAmount += 100;
                tList.add("Facing: " + ((ic2.api.tile.IWrenchable) tTileEntity).getFacing(tTileEntity.getWorld(), blockPos));
                tList.add(((ic2.api.tile.IWrenchable) tTileEntity).wrenchCanRemove(tTileEntity.getWorld(), blockPos, aPlayer) ? "You can remove this with a Wrench" : "You can NOT remove this with a Wrench");
            }

            if (tTileEntity instanceof ic2.api.energy.tile.IEnergySink) {
                rEUAmount += 400;
                aList.add("Demanded Energy: " + ((ic2.api.energy.tile.IEnergySink) tTileEntity).getDemandedEnergy());
                tList.add("Max Safe Input Tier: " + getTier(((ic2.api.energy.tile.IEnergySink) tTileEntity).getSinkTier()));
            }

            if (tTileEntity instanceof ic2.api.energy.tile.IEnergySource) {
                rEUAmount += 400;
                aList.add("Energy Output Tier: " + ((ic2.api.energy.tile.IEnergySource) tTileEntity).getSourceTier());
            }

            if (tTileEntity instanceof ic2.api.energy.tile.IEnergyConductor) {
                rEUAmount += 200;
                tList.add("Conduction Loss: " + ((ic2.api.energy.tile.IEnergyConductor) tTileEntity).getConductionLoss());
            }

            if (tTileEntity instanceof ic2.api.tile.IEnergyStorage) {
                rEUAmount += 200;
                tList.add("Contained Energy: " + ((ic2.api.tile.IEnergyStorage) tTileEntity).getStored() + " of " + ((ic2.api.tile.IEnergyStorage) tTileEntity).getCapacity());
                aList.add(((ic2.api.tile.IEnergyStorage) tTileEntity).isTeleporterCompatible(EnumFacing.UP) ? "Teleporter Compatible" : "Not Teleporter Compatible");
            }

            if (tTileEntity instanceof IUpgradable) {
                rEUAmount += 500;
                if (((IUpgradable) tTileEntity).hasMufflerUpgrade()) tList.add("Has Muffler Upgrade");
            }

            if (tTileEntity instanceof IWorkable) {
                rEUAmount += 400;
                int tValue;
                if (0 < (tValue = ((IWorkable) tTileEntity).getMaxProgress()))
                    tList.add("Progress: " + GT_Utility.formatNumbers(tValue) + " / " + GT_Utility.formatNumbers(((IWorkable) tTileEntity).getProgress()));
            }

            if (tTileEntity instanceof ICoverable) {
                rEUAmount += 300;
                String tString = ((ICoverable) tTileEntity).getCoverBehaviorAtSide(aSide).getDescription(aSide, ((ICoverable) tTileEntity).getCoverIDAtSide(aSide), ((ICoverable) tTileEntity).getCoverDataAtSide(aSide), (ICoverable) tTileEntity);
                if (tString != null && !tString.equals(E)) tList.add(tString);
            }

            if (tTileEntity instanceof IEnergyContainer && ((IEnergyContainer) tTileEntity).getEUCapacity() > 0) {
                tList.add("Max IN: " + ((IEnergyContainer) tTileEntity).getInputVoltage() + " EU");
                tList.add("Max OUT: " + ((IEnergyContainer) tTileEntity).getOutputVoltage() + " EU at " + ((IEnergyContainer) tTileEntity).getOutputAmperage() + " Amperes");
                tList.add("Energy: " + GT_Utility.formatNumbers(((IEnergyContainer) tTileEntity).getStoredEU()) + " / " + GT_Utility.formatNumbers(((IEnergyContainer) tTileEntity).getEUCapacity()) + "EU");
            }

            if (tTileEntity instanceof IGregTechTileEntity) {
                tList.add("Owned by: " + ((IGregTechTileEntity) tTileEntity).getOwnerId());
            }

            if (tTileEntity instanceof IDescribable && ((IDescribable) tTileEntity).isGivingInformation()) {
                tList.addAll(Arrays.asList(((IDescribable) tTileEntity).getInfoData()));
            }

            if (tTileEntity instanceof ic2.api.crops.ICropTile) {
                ICropTile cropTile = (ICropTile) tTileEntity;
                if (cropTile.getScanLevel() < 4) {
                    rEUAmount += 10000;
                    cropTile.setScanLevel((byte) 4);
                }
                if (cropTile.getCrop() != null) {
                    rEUAmount += 1000;
                    CropProperties properties = cropTile.getCrop().getProperties();
                    tList.add("Type -- Crop-Name: " + I18n.translateToLocal(cropTile.getCrop().getUnlocalizedName())
                            + "  Growth: " + cropTile.getCurrentSize()
                            + "  Gain: " + cropTile.getCrop().getGain(cropTile)
                            + "  Resistance: " + properties.getDefensive()
                    );
                    tList.add("Plant -- Fertilizer: " + cropTile.getStorageNutrients()
                            + "  Water: " + cropTile.getStorageWater()
                            + "  Weed-Ex: " + cropTile.getStorageWeedEX()
                            + "  Scan-Level: " + cropTile.getScanLevel()
                    );
                    tList.add("Environment -- Nutrients: " + cropTile.getTerrainNutrients()
                            + "  Humidity: " + cropTile.getTerrainHumidity()
                            + "  Air-Quality: " + cropTile.getTerrainAirQuality()
                    );
                    tList.add("Discovered by: " + cropTile.getCrop().getDiscoveredBy());
                }
            }

        }

        if (aPlayer.capabilities.isCreativeMode) {
            FluidStack tFluid = getUndergroundOil(aWorld, blockPos.getX(), blockPos.getY());
            tList.add("Oil in Chunk: " + tFluid.amount + " " + tFluid.getLocalizedName());
        }

        ChunkPos pos = new ChunkPos(blockPos.getX() / 16, blockPos.getZ() / 16);
        if (GT_Proxy.chunkData.containsKey(pos)) {
            int[] tPollution = GT_Proxy.chunkData.get(pos);
            if (tPollution.length > 1) {
                tList.add("Pollution in Chunk: " + tPollution[1]);
            } else {
                tList.add("No Pollution in Chunk");
            }
        }

        if (tBlock instanceof IDebugableBlock) {
            rEUAmount += 500;
            ArrayList<String> temp = ((IDebugableBlock) tBlock).getDebugInfo(aPlayer, blockPos, 3);
            if (temp != null) tList.addAll(temp);
        }

        BlockScanningEvent tEvent = new BlockScanningEvent(aWorld, aPlayer, blockPos, aSide, aScanLevel, tBlock, tTileEntity, tList, aClickX, aClickY, aClickZ);
        tEvent.mEUCost = rEUAmount;
        MinecraftForge.EVENT_BUS.post(tEvent);
        if (!tEvent.isCanceled()) aList.addAll(tList);
        return tEvent.mEUCost;
    }

    /**
     * @return an Array containing the X and the Y Coordinate of the clicked Point, with the top left Corner as Origin, like on the Texture Sheet. return values should always be between [0.0F and 0.99F].
     */
    public static float[] getClickedFacingCoords(byte aSide, float aX, float aY, float aZ) {
        switch (aSide) {
            case 0:
                return new float[]{Math.min(0.99F, Math.max(0, 1 - aX)), Math.min(0.99F, Math.max(0, aZ))};
            case 1:
                return new float[]{Math.min(0.99F, Math.max(0, aX)), Math.min(0.99F, Math.max(0, aZ))};
            case 2:
                return new float[]{Math.min(0.99F, Math.max(0, 1 - aX)), Math.min(0.99F, Math.max(0, 1 - aY))};
            case 3:
                return new float[]{Math.min(0.99F, Math.max(0, aX)), Math.min(0.99F, Math.max(0, 1 - aY))};
            case 4:
                return new float[]{Math.min(0.99F, Math.max(0, aZ)), Math.min(0.99F, Math.max(0, 1 - aY))};
            case 5:
                return new float[]{Math.min(0.99F, Math.max(0, 1 - aZ)), Math.min(0.99F, Math.max(0, 1 - aY))};
            default:
                return new float[]{0.5F, 0.5F};
        }
    }

    /**
     * This Function determines the direction a Block gets when being Wrenched.
     */
    public static EnumFacing determineWrenchingSide(EnumFacing aSide, float aX, float aY, float aZ) {
        EnumFacing tBack = aSide.getOpposite();
        switch (aSide) {
            case DOWN:
            case UP:
                if (aX < 0.25) {
                    if (aZ < 0.25) return tBack;
                    if (aZ > 0.75) return tBack;
                    return EnumFacing.WEST;
                }
                if (aX > 0.75) {
                    if (aZ < 0.25) return tBack;
                    if (aZ > 0.75) return tBack;
                    return EnumFacing.EAST;
                }
                if (aZ < 0.25) return EnumFacing.NORTH;
                if (aZ > 0.75) return EnumFacing.SOUTH;
                return aSide;
            case NORTH:
            case SOUTH:
                if (aX < 0.25) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return EnumFacing.WEST;
                }
                if (aX > 0.75) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return EnumFacing.EAST;
                }
                if (aY < 0.25) return EnumFacing.DOWN;
                if (aY > 0.75) return EnumFacing.UP;
                return aSide;
            case WEST:
            case EAST:
                if (aZ < 0.25) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return EnumFacing.NORTH;
                }
                if (aZ > 0.75) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return EnumFacing.SOUTH;
                }
                if (aY < 0.25) return EnumFacing.DOWN;
                if (aY > 0.75) return EnumFacing.UP;
                return aSide;
        }
        throw new IllegalArgumentException();
    }

    public static String formatNumbers(long aNumber) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        return formatter.format(aNumber);
    }

    public static class ItemNBT {

        public static void setNBT(ItemStack aStack, NBTTagCompound aNBT) {
            if (aNBT == null) {
                aStack.setTagCompound(null);
                return;
            }
            ArrayList<String> tTagsToRemove = new ArrayList<>();
            for (Object tKey : aNBT.getKeySet()) {
                NBTBase tValue = aNBT.getTag((String) tKey);
                if (tValue == null || (tValue instanceof NBTPrimitive && ((NBTPrimitive) tValue).getInt() == 0) || (tValue instanceof NBTTagString && !isStringValid(((NBTTagString) tValue).getString())))
                    tTagsToRemove.add((String) tKey);
            }
            for (Object tKey : tTagsToRemove) aNBT.removeTag((String) tKey);
            aStack.setTagCompound(aNBT.hasNoTags() ? null : aNBT);
        }

        public static NBTTagCompound getNBT(ItemStack aStack) {
            NBTTagCompound rNBT = aStack.getTagCompound();
            return rNBT == null ? new NBTTagCompound() : rNBT;
        }

        @SuppressWarnings("unused")
        public static void setPunchCardData(ItemStack aStack, String aPunchCardData) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setString("GT.PunchCardData", aPunchCardData);
            setNBT(aStack, tNBT);
        }

        public static String getPunchCardData(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            return tNBT.getString("GT.PunchCardData");
        }

        public static void setLighterFuel(ItemStack aStack, long aFuel) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setLong("GT.LighterFuel", aFuel);
            setNBT(aStack, tNBT);
        }

        public static long getLighterFuel(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            return tNBT.getLong("GT.LighterFuel");
        }

        @SuppressWarnings("unused")
        public static void setMapID(ItemStack aStack, short aMapID) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setShort("map_id", aMapID);
            setNBT(aStack, tNBT);
        }

        public static short getMapID(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            if (!tNBT.hasKey("map_id")) return -1;
            return tNBT.getShort("map_id");
        }

        public static void setBookTitle(ItemStack aStack, String aTitle) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setString("title", aTitle);
            setNBT(aStack, tNBT);
        }

        public static String getBookTitle(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            return tNBT.getString("title");
        }

        @SuppressWarnings("unused")
        public static void setBookAuthor(ItemStack aStack, String aAuthor) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setString("author", aAuthor);
            setNBT(aStack, tNBT);
        }

        public static String getBookAuthor(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            return tNBT.getString("author");
        }

        public static void setProspectionData(ItemStack aStack, BlockPos pos, int aDim, FluidStack aFluid, String[] aOres) {
            NBTTagCompound tNBT = getNBT(aStack);
            StringBuilder tData = new StringBuilder(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "," + aDim + "," + (aFluid.amount / 5000) + "," + aFluid.getLocalizedName() + ",");
            for (String tString : aOres) {
                tData.append(tString).append(",");
            }
            tNBT.setString("prospection", tData.toString());
            setNBT(aStack, tNBT);
        }

        public static void convertProspectionData(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            String tData = tNBT.getString("prospection");
            String[] tDataArray = tData.split(",");
            if (tDataArray.length > 6) {
                tNBT.setString("author", "X: " + tDataArray[0] + " Y: " + tDataArray[1] + " Z: " + tDataArray[2] + " Dim: " + tDataArray[3]);
                NBTTagList tNBTList = new NBTTagList();
                StringBuilder tOres = new StringBuilder(" Prospected Ores: ");
                for (int i = 6; tDataArray.length > i; i++) {
                    tOres.append(tDataArray[i]).append(" ");
                }
                tNBTList.appendTag(new NBTTagString("Prospection Data From: X" + tDataArray[0] + " Z:" + tDataArray[2] + " Dim:" + tDataArray[3] + " Produces " + tDataArray[4] + "L " + tDataArray[5] + " " + tOres));
                tNBT.setTag("pages", tNBTList);
            }
            setNBT(aStack, tNBT);
        }

        public static void addEnchantment(ItemStack aStack, Enchantment aEnchantment, int aLevel) {
            NBTTagCompound tNBT = getNBT(aStack), tEnchantmentTag;
            if (!tNBT.hasKey("ench", 9)) tNBT.setTag("ench", new NBTTagList());
            NBTTagList tList = tNBT.getTagList("ench", 10);

            boolean temp = true;

            for (int i = 0; i < tList.tagCount(); i++) {
                tEnchantmentTag = tList.getCompoundTagAt(i);
                if (tEnchantmentTag.getShort("id") == Enchantment.getEnchantmentID(aEnchantment)) {
                    tEnchantmentTag.setShort("id", (short) Enchantment.getEnchantmentID(aEnchantment));
                    tEnchantmentTag.setShort("lvl", (byte) aLevel);
                    temp = false;
                    break;
                }
            }

            if (temp) {
                tEnchantmentTag = new NBTTagCompound();
                tEnchantmentTag.setShort("id", (short) Enchantment.getEnchantmentID(aEnchantment));
                tEnchantmentTag.setShort("lvl", (byte) aLevel);
                tList.appendTag(tEnchantmentTag);
            }
            aStack.setTagCompound(tNBT);
        }
    }

    /**
     * THIS IS BULLSHIT!!! WHY DO I HAVE TO DO THIS SHIT JUST TO HAVE ENCHANTS PROPERLY!?!
     */
    public static class GT_EnchantmentHelper {
        private static final BullshitIteratorA mBullshitIteratorA = new BullshitIteratorA();
        private static final BullshitIteratorB mBullshitIteratorB = new BullshitIteratorB();

        private static void applyBullshit(IBullshit aBullshitModifier, ItemStack aStack) {
            if (aStack != null) {
                NBTTagList nbttaglist = aStack.getEnchantmentTagList();
                if (nbttaglist != null) {
                    for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                        short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
                        short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
                        if (Enchantment.getEnchantmentByID(short1) != null)
                            aBullshitModifier.calculateModifier(Enchantment.getEnchantmentByID(short1), short2);
                    }
                }
            }
        }

        public static void applyBullshitA(EntityLivingBase aPlayer, Entity aEntity, ItemStack aStack) {
            mBullshitIteratorA.mPlayer = aPlayer;
            mBullshitIteratorA.mEntity = aEntity;
            if (aStack != null) applyBullshit(mBullshitIteratorA, aStack);
        }

        public static void applyBullshitB(EntityLivingBase aPlayer, Entity aEntity, ItemStack aStack) {
            mBullshitIteratorB.mPlayer = aPlayer;
            mBullshitIteratorB.mEntity = aEntity;
            if (aStack != null) applyBullshit(mBullshitIteratorB, aStack);
        }

        interface IBullshit {
            void calculateModifier(Enchantment aEnchantment, int aLevel);
        }

        static final class BullshitIteratorA implements IBullshit {
            public EntityLivingBase mPlayer;
            public Entity mEntity;

            BullshitIteratorA() {
            }

            @Override
            public void calculateModifier(Enchantment aEnchantment, int aLevel) {
                aEnchantment.calcModifierDamage(aLevel, DamageSource.causeThornsDamage(mEntity));
            }
        }

        static final class BullshitIteratorB implements IBullshit {
            public EntityLivingBase mPlayer;
            public Entity mEntity;

            BullshitIteratorB() {
            }

            @Override
            public void calculateModifier(Enchantment aEnchantment, int aLevel) {
                aEnchantment.calcModifierDamage(aLevel, DamageSource.causeMobDamage(mPlayer));
            }
        }
    }

}