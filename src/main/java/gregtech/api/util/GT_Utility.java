package gregtech.api.util;


import gregtech.api.GregTech_API;
import gregtech.api.damagesources.GT_DamageSources;
import gregtech.api.enchants.Enchantment_Radioactivity;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.SubTag;
import gregtech.api.events.BlockScanningEvent;
import gregtech.api.interfaces.IDebugableBlock;
import gregtech.api.interfaces.IProjectileItem;
import gregtech.api.interfaces.tileentity.*;
import gregtech.api.items.GT_Generic_Item;
import gregtech.api.net.GT_Packet_Sound;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.threads.GT_Runnable_Sound;
import gregtech.common.GT_Proxy;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.recipe.*;
import ic2.core.IC2Potion;
import ic2.core.ref.ItemName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.*;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * Just a few Utility Functions I use.
 */
public class GT_Utility {
    /**
     * Forge screwed the Fluid Registry up again, so I make my own, which is also much more efficient than the stupid Stuff over there.
     */
    private static final List<FluidContainerData> sFluidContainerList = new ArrayList<FluidContainerData>();
    private static final Map<GT_ItemStack, FluidContainerData> sFilledContainerToData = new HashMap<GT_ItemStack, FluidContainerData>();
    private static final Map<GT_ItemStack, Map<Fluid, FluidContainerData>> sEmptyContainerToFluidToData = new HashMap<GT_ItemStack, Map<Fluid, FluidContainerData>>();
    public static volatile int VERSION = 509;
    public static boolean TE_CHECK = false, BC_CHECK = false, CHECK_ALL = true, RF_CHECK = false;
    private static int sBookCount = 0;

    static {
        GregTech_API.sItemStackMappings.add(sFilledContainerToData);
        GregTech_API.sItemStackMappings.add(sEmptyContainerToFluidToData);
    }

    public static Field getPublicField(Object aObject, String aField) {
        Field rField = null;
        try {
            rField = aObject.getClass().getDeclaredField(aField);
        } catch (Throwable e) {/*Do nothing*/}
        return rField;
    }

    public static Field getField(Object aObject, String aField) {
        Field rField = null;
        try {
            rField = aObject.getClass().getDeclaredField(aField);
            rField.setAccessible(true);
        } catch (Throwable e) {/*Do nothing*/}
        return rField;
    }

    public static Field getField(Class aObject, String aField) {
        Field rField = null;
        try {
            rField = aObject.getDeclaredField(aField);
            rField.setAccessible(true);
        } catch (Throwable e) {/*Do nothing*/}
        return rField;
    }

    public static Method getMethod(Class aObject, String aMethod, Class<?>... aParameterTypes) {
        Method rMethod = null;
        try {
            rMethod = aObject.getMethod(aMethod, aParameterTypes);
            rMethod.setAccessible(true);
        } catch (Throwable e) {/*Do nothing*/}
        return rMethod;
    }

    public static Method getMethod(Object aObject, String aMethod, Class<?>... aParameterTypes) {
        Method rMethod = null;
        try {
            rMethod = aObject.getClass().getMethod(aMethod, aParameterTypes);
            rMethod.setAccessible(true);
        } catch (Throwable e) {/*Do nothing*/}
        return rMethod;
    }

    public static Field getField(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class) aObject).getDeclaredField(aField) : (aObject instanceof String) ? Class.forName((String) aObject).getDeclaredField(aField) : aObject.getClass().getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField;
        } catch (Throwable e) {
            if (aLogErrors) e.printStackTrace(GT_Log.err);
        }
        return null;
    }

    public static Object getFieldContent(Object aObject, String aField, boolean aPrivate, boolean aLogErrors) {
        try {
            Field tField = (aObject instanceof Class) ? ((Class) aObject).getDeclaredField(aField) : (aObject instanceof String) ? Class.forName((String) aObject).getDeclaredField(aField) : aObject.getClass().getDeclaredField(aField);
            if (aPrivate) tField.setAccessible(true);
            return tField.get(aObject instanceof Class || aObject instanceof String ? null : aObject);
        } catch (Throwable e) {
            if (aLogErrors) e.printStackTrace(GT_Log.err);
        }
        return null;
    }

    public static Object callPublicMethod(Object aObject, String aMethod, Object... aParameters) {
        return callMethod(aObject, aMethod, false, false, true, aParameters);
    }

    public static Object callPrivateMethod(Object aObject, String aMethod, Object... aParameters) {
        return callMethod(aObject, aMethod, true, false, true, aParameters);
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

            Method tMethod = (aObject instanceof Class) ? ((Class) aObject).getMethod(aMethod, tParameterTypes) : aObject.getClass().getMethod(aMethod, tParameterTypes);
            if (aPrivate) tMethod.setAccessible(true);
            return tMethod.invoke(aObject, aParameters);
        } catch (Throwable e) {
            if (aLogErrors) e.printStackTrace(GT_Log.err);
        }
        return null;
    }

    public static Object callConstructor(String aClass, int aConstructorIndex, Object aReplacementObject, boolean aLogErrors, Object... aParameters) {
        if (aConstructorIndex < 0) {
            try {
                for (Constructor tConstructor : Class.forName(aClass).getConstructors()) {
                    try {
                        return tConstructor.newInstance(aParameters);
                    } catch (Throwable e) {/*Do nothing*/}
                }
            } catch (Throwable e) {
                if (aLogErrors) e.printStackTrace(GT_Log.err);
            }
        } else {
            try {
                return Class.forName(aClass).getConstructors()[aConstructorIndex].newInstance(aParameters);
            } catch (Throwable e) {
                if (aLogErrors) e.printStackTrace(GT_Log.err);
            }
        }
        return aReplacementObject;
    }

    public static String capitalizeString(String aString) {
        if (aString != null && aString.length() > 0)
            return aString.substring(0, 1).toUpperCase() + aString.substring(1);
        return E;
    }

    @SideOnly(Side.CLIENT)
    public static TextureAtlasSprite getTexture(String location) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location);
    }
    @SideOnly(Side.CLIENT)
    public static TextureAtlasSprite getTexture(ResourceLocation location) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    }

    public static int fillFluidTank(World world, BlockPos blockPos, EnumFacing side, FluidStack fill) {
        TileEntity tileEntity = world.getTileEntity(blockPos.offset(side));
        if(tileEntity != null && fill != null) {
            if(tileEntity instanceof IFluidHandler) {
                IFluidHandler fluidHandler = (IFluidHandler) tileEntity;
                return fluidHandler.fill(side.getOpposite(), fill, true);
            }
            if(tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite())) {
                net.minecraftforge.fluids.capability.IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                return fluidHandler.fill(fill, true);
            }
        }
        return 0;
    }

    public static int shortsToIntColor(short[] shorts) {
        return shorts[0] << 16 | shorts[1] << 8 | shorts[2];
    }

    public static boolean getPotion(EntityLivingBase aPlayer, int aPotionIndex) {
        try {
            Field tPotionHashmap = null;

            Field[] var3 = EntityLiving.class.getDeclaredFields();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field var6 = var3[var5];
                if (var6.getType() == HashMap.class) {
                    tPotionHashmap = var6;
                    tPotionHashmap.setAccessible(true);
                    break;
                }
            }

            if (tPotionHashmap != null)
                return ((HashMap) tPotionHashmap.get(aPlayer)).get(Integer.valueOf(aPotionIndex)) != null;
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
        return false;
    }

    public static String getClassName(Object aObject) {
        if (aObject == null) return "null";
        return aObject.getClass().getName().substring(aObject.getClass().getName().lastIndexOf(".") + 1);
    }

    public static void removePotion(EntityLivingBase aPlayer, int aPotionIndex) {
        try {
            Field tPotionHashmap = null;

            Field[] var3 = EntityLiving.class.getDeclaredFields();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field var6 = var3[var5];
                if (var6.getType() == HashMap.class) {
                    tPotionHashmap = var6;
                    tPotionHashmap.setAccessible(true);
                    break;
                }
            }

            if (tPotionHashmap != null) ((HashMap) tPotionHashmap.get(aPlayer)).remove(Integer.valueOf(aPotionIndex));
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
    }

    public static boolean getFullInvisibility(EntityPlayer aPlayer) {
        try {
            if (aPlayer.isInvisible()) {
                for (int i = 0; i < 4; i++) {
                    if (aPlayer.inventory.armorInventory[i] != null) {
                        //if (aPlayer.inventory.armorInventory[i].getItem() instanceof GT_EnergyArmor_Item) {
                        //if ((((GT_EnergyArmor_Item) aPlayer.inventory.armorInventory[i].getItem()).mSpecials & 512) != 0) {
                        //    if (GT_ModHandler.canUseElectricItem(aPlayer.inventory.armorInventory[i], 10000)) {
                        //        return true;
                        //    }
                        //}
                        //}
                    }
                }
            }
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
        return false;
    }

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

    public static byte getOppositeSide(int aSide) {
        return (byte) EnumFacing.VALUES[aSide].getOpposite().getIndex();
    }

    public static byte getTier(long l) {
        byte i = -1;
        while (++i < V.length) if (l <= V[i]) return i;
        return i;
    }

    public static void sendChatToPlayer(EntityPlayer aPlayer, String aChatMessage) {
        if (aPlayer instanceof EntityPlayerMP && aChatMessage != null) {
            aPlayer.addChatComponentMessage(new TextComponentString(aChatMessage));
        }
    }

    public static void checkAvailabilities() {
        if (CHECK_ALL) {
            try {
                //Class tClass = IItemDuct.class;
                //tClass.getCanonicalName();
                //TE_CHECK = true;
            } catch (Throwable e) {/**/}
            try {
                //Class tClass = buildcraft.api.transport.IPipeTile.class;
                //tClass.getCanonicalName();
                //BC_CHECK = true;
            } catch (Throwable e) {/**/}
            try {
                Class tClass = cofh.api.energy.IEnergyReceiver.class;
                tClass.getCanonicalName();
                RF_CHECK = true;
            } catch (Throwable e) {/**/}
            CHECK_ALL = false;
        }
    }

    public static boolean isConnectableNonInventoryPipe(Object aTileEntity, int aSide) {
        if (aTileEntity == null) return false;
        //checkAvailabilities();
        //if (TE_CHECK) if (aTileEntity instanceof IItemDuct) return true;
        //if (BC_CHECK) if (aTileEntity instanceof buildcraft.api.transport.IPipeTile)
        //    return ((buildcraft.api.transport.IPipeTile) aTileEntity).isPipeConnected(ForgeDirection.getOrientation(aSide));
        return false;
    }

    /**
     * Moves Stack from Inv-Slot to Inv-Slot, without checking if its even allowed.
     *
     * @return the Amount of moved Items
     */
    public static byte moveStackIntoPipe(IInventory aTileEntity1, Object aTileEntity2, int[] aGrabSlots, int aGrabFrom, int aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        return moveStackIntoPipe(aTileEntity1, aTileEntity2, aGrabSlots, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, true);
    }

   /**
    * Moves Stack from Inv-Slot to Inv-Slot, without checking if its even allowed.
    *
    * @return the Amount of moved Items
    */
    public static byte moveStackIntoPipe(IInventory aTileEntity1, Object aTileEntity2, int[] aGrabSlots, int aGrabFrom, int aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce, boolean dropItem) {
        if (aTileEntity1 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMaxMoveAtOnce <= 0 || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;

        EnumFacing tDirection = EnumFacing.VALUES[aGrabFrom];
        if (aTileEntity1 instanceof TileEntity && tDirection != null && tDirection.getOpposite() == EnumFacing.VALUES[aPutTo]) {
            int tX = ((TileEntity) aTileEntity1).getPos().getX() + tDirection.getFrontOffsetX(), tY = ((TileEntity) aTileEntity1).getPos().getY() + tDirection.getFrontOffsetY(), tZ = ((TileEntity) aTileEntity1).getPos().getZ() + tDirection.getFrontOffsetZ();
            if (!hasBlockHitBox(((TileEntity) aTileEntity1).getWorld(), ((TileEntity) aTileEntity1).getPos()) && dropItem) {
                for (int aGrabSlot : aGrabSlots) {
                    if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(aGrabSlot), true, aInvertFilter)) {
                        if (isAllowedToTakeFromSlot(aTileEntity1, aGrabSlot, (byte) aGrabFrom, aTileEntity1.getStackInSlot(aGrabSlot))) {
                            if (Math.max(aMinMoveAtOnce, aMinTargetStackSize) <= aTileEntity1.getStackInSlot(aGrabSlot).stackSize) {
                                ItemStack tStack = copyAmount(Math.min(aTileEntity1.getStackInSlot(aGrabSlot).stackSize, Math.min(aMaxMoveAtOnce, aMaxTargetStackSize)), aTileEntity1.getStackInSlot(aGrabSlot));
                                EntityItem tEntity = new EntityItem(((TileEntity) aTileEntity1).getWorld(), tX + 0.5, tY + 0.5, tZ + 0.5, tStack);
                                tEntity.motionX = tEntity.motionY = tEntity.motionZ = 0;
                                ((TileEntity) aTileEntity1).getWorld().spawnEntityInWorld(tEntity);
                                aTileEntity1.decrStackSize(aGrabSlot, tStack.stackSize);
                                aTileEntity1.markDirty();
                                return (byte) tStack.stackSize;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Moves Stack from Inv-Slot to Inv-Slot, without checking if its even allowed. (useful for internal Inventory Operations)
     *
     * @return the Amount of moved Items
     */
    public static byte moveStackFromSlotAToSlotB(IInventory aTileEntity1, IInventory aTileEntity2, int aGrabFrom, int aPutTo, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        if (aTileEntity1 == null || aTileEntity2 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMaxMoveAtOnce <= 0 || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;

        ItemStack tStack1 = aTileEntity1.getStackInSlot(aGrabFrom), tStack2 = aTileEntity2.getStackInSlot(aPutTo), tStack3 = null;
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

    public static boolean isAllowedToTakeFromSlot(IInventory aTileEntity, int aSlot, byte aSide, ItemStack aStack) {
        if (aSide > 6) {
            return isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 0, aStack)
                    || isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 1, aStack)
                    || isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 2, aStack)
                    || isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 3, aStack)
                    || isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 4, aStack)
                    || isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte) 5, aStack);
        }
        return !(aTileEntity instanceof ISidedInventory) || ((ISidedInventory) aTileEntity).canExtractItem(aSlot, aStack, EnumFacing.VALUES[aSide]);
    }

    public static boolean isAllowedToPutIntoSlot(IInventory aTileEntity, int aSlot, byte aSide, ItemStack aStack, byte aMaxStackSize) {
        ItemStack tStack = aTileEntity.getStackInSlot(aSlot);
        if (tStack != null && (!areStacksEqual(tStack, aStack) || tStack.stackSize >= tStack.getMaxStackSize()))
            return false;
        if (aSide > 6) {
            return isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 0, aStack, aMaxStackSize)
                    || isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 1, aStack, aMaxStackSize)
                    || isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 2, aStack, aMaxStackSize)
                    || isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 3, aStack, aMaxStackSize)
                    || isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 4, aStack, aMaxStackSize)
                    || isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte) 5, aStack, aMaxStackSize);
        }
        return !(aTileEntity instanceof ISidedInventory && !((ISidedInventory) aTileEntity).canInsertItem(aSlot, aStack, EnumFacing.VALUES[aSide])) && aTileEntity.isItemValidForSlot(aSlot, aStack);
    }

    /**
     * Moves Stack from Inv-Side to Inv-Side.
     *
     * @return the Amount of moved Items
     */
    public static byte moveOneItemStack(Object aTileEntity1, Object aTileEntity2, byte aGrabFrom, byte aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        if (aTileEntity1 instanceof IInventory)
            return moveOneItemStack((IInventory) aTileEntity1, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, true);
        return 0;
    }

    /**
     * This is only because I needed an additional Parameter for the Double Chest Check.
     */
    private static byte moveOneItemStack(IInventory aTileEntity1, Object aTileEntity2, byte aGrabFrom, byte aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce, boolean aDoCheckChests) {
        if (aTileEntity1 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;

        int[] tGrabSlots = null;
        if (aTileEntity1 instanceof ISidedInventory)
            tGrabSlots = ((ISidedInventory) aTileEntity1).getSlotsForFace(EnumFacing.VALUES[aGrabFrom]);
        if (tGrabSlots == null) {
            tGrabSlots = new int[aTileEntity1.getSizeInventory()];
            for (int i = 0; i < tGrabSlots.length; i++) tGrabSlots[i] = i;
        }

        if (aTileEntity2 instanceof IInventory) {
            int[] tPutSlots = null;
            if (aTileEntity2 instanceof ISidedInventory)
                tPutSlots = ((ISidedInventory) aTileEntity2).getSlotsForFace(EnumFacing.VALUES[aPutTo]);

            if (tPutSlots == null) {
                tPutSlots = new int[((IInventory) aTileEntity2).getSizeInventory()];
                for (int i = 0; i < tPutSlots.length; i++) tPutSlots[i] = i;
            }

            for (int i = 0; i < tGrabSlots.length; i++) {
                byte tMovedItemCount = 0;
                for (int j = 0; j < tPutSlots.length; j++) {
                    if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(tGrabSlots[i]), true, aInvertFilter)) {
                        if (isAllowedToTakeFromSlot(aTileEntity1, tGrabSlots[i], aGrabFrom, aTileEntity1.getStackInSlot(tGrabSlots[i]))) {
                            if (isAllowedToPutIntoSlot((IInventory) aTileEntity2, tPutSlots[j], aPutTo, aTileEntity1.getStackInSlot(tGrabSlots[i]), aMaxTargetStackSize)) {
                                tMovedItemCount = moveStackFromSlotAToSlotB(aTileEntity1, (IInventory) aTileEntity2, tGrabSlots[i], tPutSlots[j], aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
                            }
                        }
                    }
                }
            if (tMovedItemCount > 0) return tMovedItemCount;
            }

            if (aDoCheckChests && aTileEntity1 instanceof TileEntityChest) {
                TileEntityChest tTileEntity1 = (TileEntityChest) aTileEntity1;
                if (tTileEntity1.adjacentChestChecked) {
                    byte tAmount = 0;
                    if (tTileEntity1.adjacentChestXNeg != null) {
                        tAmount = moveOneItemStack(tTileEntity1.adjacentChestXNeg, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity1.adjacentChestZNeg != null) {
                        tAmount = moveOneItemStack(tTileEntity1.adjacentChestZNeg, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity1.adjacentChestXPos != null) {
                        tAmount = moveOneItemStack(tTileEntity1.adjacentChestXPos, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity1.adjacentChestZPos != null) {
                        tAmount = moveOneItemStack(tTileEntity1.adjacentChestZPos, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    }
                    if (tAmount != 0) return tAmount;
                }
            }
            if (aDoCheckChests && aTileEntity2 instanceof TileEntityChest) {
                TileEntityChest tTileEntity2 = (TileEntityChest) aTileEntity2;
                if (tTileEntity2.adjacentChestChecked) {
                    byte tAmount = 0;
                    if (tTileEntity2.adjacentChestXNeg != null) {
                        tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestXNeg, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity2.adjacentChestZNeg != null) {
                        tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestZNeg, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity2.adjacentChestXPos != null) {
                        tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestXPos, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    } else if (tTileEntity2.adjacentChestZPos != null) {
                        tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestZPos, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
                    }
                    if (tAmount != 0) return tAmount;
                }
            }
        }

        return moveStackIntoPipe(aTileEntity1, aTileEntity2, tGrabSlots, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, aDoCheckChests);
    }

    /**
     * Moves Stack from Inv-Side to Inv-Slot.
     *
     * @return the Amount of moved Items
     */
    public static byte moveOneItemStackIntoSlot(Object aTileEntity1, Object aTileEntity2, byte aGrabFrom, int aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        if (aTileEntity1 == null || !(aTileEntity1 instanceof IInventory) || aPutTo < 0 || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;

        int[] tGrabSlots = null;
        if (aTileEntity1 instanceof ISidedInventory)
            tGrabSlots = ((ISidedInventory) aTileEntity1).getSlotsForFace(EnumFacing.VALUES[aGrabFrom]);
        if (tGrabSlots == null) {
            tGrabSlots = new int[((IInventory) aTileEntity1).getSizeInventory()];
            for (int i = 0; i < tGrabSlots.length; i++) tGrabSlots[i] = i;
        }

        if (aTileEntity2 != null && aTileEntity2 instanceof IInventory) {
            for (int i = 0; i < tGrabSlots.length; i++) {
                if (listContainsItem(aFilter, ((IInventory) aTileEntity1).getStackInSlot(tGrabSlots[i]), true, aInvertFilter)) {
                    if (isAllowedToTakeFromSlot((IInventory) aTileEntity1, tGrabSlots[i], aGrabFrom, ((IInventory) aTileEntity1).getStackInSlot(tGrabSlots[i]))) {
                        if (isAllowedToPutIntoSlot((IInventory) aTileEntity2, aPutTo, (byte) 6, ((IInventory) aTileEntity1).getStackInSlot(tGrabSlots[i]), aMaxTargetStackSize)) {
                            byte tMovedItemCount = moveStackFromSlotAToSlotB((IInventory) aTileEntity1, (IInventory) aTileEntity2, tGrabSlots[i], aPutTo, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
                            if (tMovedItemCount > 0) return tMovedItemCount;
                        }
                    }
                }
            }
        }

        moveStackIntoPipe(((IInventory) aTileEntity1), aTileEntity2, tGrabSlots, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
        return 0;
    }

    /**
     * Moves Stack from Inv-Slot to Inv-Slot.
     *
     * @return the Amount of moved Items
     */
    public static byte moveFromSlotToSlot(IInventory aTileEntity1, IInventory aTileEntity2, int aGrabFrom, int aPutTo, List<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
        if (aTileEntity1 == null || aTileEntity2 == null || aGrabFrom < 0 || aPutTo < 0 || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce)
            return 0;
        if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(aGrabFrom), true, aInvertFilter)) {
            if (isAllowedToTakeFromSlot(aTileEntity1, aGrabFrom, (byte) 6, aTileEntity1.getStackInSlot(aGrabFrom))) {
                if (isAllowedToPutIntoSlot(aTileEntity2, aPutTo, (byte) 6, aTileEntity1.getStackInSlot(aGrabFrom), aMaxTargetStackSize)) {
                    byte tMovedItemCount = moveStackFromSlotAToSlotB(aTileEntity1, aTileEntity2, aGrabFrom, aPutTo, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
                    if (tMovedItemCount > 0) return tMovedItemCount;
                }
            }
        }
        return 0;
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
        return areStacksEqual(GT_OreDictUnificator.get(aStack1), GT_OreDictUnificator.get(aStack2), aIgnoreNBT);
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

    public static void reInit() {
        sFilledContainerToData.clear();
        sEmptyContainerToFluidToData.clear();
        for (FluidContainerData tData : sFluidContainerList) {
            sFilledContainerToData.put(new GT_ItemStack(tData.filledContainer), tData);
            Map<Fluid, FluidContainerData> tFluidToContainer = sEmptyContainerToFluidToData.get(new GT_ItemStack(tData.emptyContainer));
            if (tFluidToContainer == null) {
                sEmptyContainerToFluidToData.put(new GT_ItemStack(tData.emptyContainer), tFluidToContainer = new HashMap<Fluid, FluidContainerData>());
                GregTech_API.sFluidMappings.add(tFluidToContainer);
            }
            tFluidToContainer.put(tData.fluid.getFluid(), tData);
        }
    }

    public static void addFluidContainerData(FluidContainerData aData) {
        sFluidContainerList.add(aData);
        sFilledContainerToData.put(new GT_ItemStack(aData.filledContainer), aData);
        Map<Fluid, FluidContainerData> tFluidToContainer = sEmptyContainerToFluidToData.get(new GT_ItemStack(aData.emptyContainer));
        if (tFluidToContainer == null) {
            sEmptyContainerToFluidToData.put(new GT_ItemStack(aData.emptyContainer), tFluidToContainer = new HashMap<Fluid, FluidContainerData>());
            GregTech_API.sFluidMappings.add(tFluidToContainer);
        }
        tFluidToContainer.put(aData.fluid.getFluid(), aData);
    }

    public static ItemStack fillFluidContainer(FluidStack aFluid, ItemStack aStack, boolean aRemoveFluidDirectly, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack) || aFluid == null) return null;
        if (GT_ModHandler.isWater(aFluid) && ItemList.Bottle_Empty.isStackEqual(aStack)) {
            if (aFluid.amount >= 250) {
                if (aRemoveFluidDirectly) aFluid.amount -= 250;
                return new ItemStack(Items.POTIONITEM, 1, 0);
            }
            return null;
        }
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) aStack.getItem()).getFluid(aStack) == null && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) <= aFluid.amount) {
            if (aRemoveFluidDirectly)
                aFluid.amount -= ((IFluidContainerItem) aStack.getItem()).fill(aStack = copyAmount(1, aStack), aFluid, true);
            else
                ((IFluidContainerItem) aStack.getItem()).fill(aStack = copyAmount(1, aStack), aFluid, true);
            return aStack;
        }
        Map<Fluid, FluidContainerData> tFluidToContainer = sEmptyContainerToFluidToData.get(new GT_ItemStack(aStack));
        if (tFluidToContainer == null) return null;
        FluidContainerData tData = tFluidToContainer.get(aFluid.getFluid());
        if (tData == null || tData.fluid.amount > aFluid.amount) return null;
        if (aRemoveFluidDirectly) aFluid.amount -= tData.fluid.amount;
        return copyAmount(1, tData.filledContainer);
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

    public static boolean containsFluid(ItemStack aStack, FluidStack aFluid, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack) || aFluid == null) return false;
        aStack = copyAmount(1, aStack);
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem containerItem = (IFluidContainerItem) aStack.getItem();
            return aFluid.isFluidEqual(containerItem.getFluid(aStack));

        }
        if(aCheckIFluidContainerItems && aStack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            net.minecraftforge.fluids.capability.IFluidHandler fluidHandler = aStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
            if(fluidHandler.getTankProperties().length == 0) return false;
            return aFluid.isFluidEqual(fluidHandler.getTankProperties()[0].getContents());
        }
        FluidContainerData tData = sFilledContainerToData.get(new GT_ItemStack(aStack));
        return tData != null && tData.fluid.isFluidEqual(aFluid);
    }

    public static FluidStack getFluidForFilledItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack)) return null;
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0)
            return ((IFluidContainerItem) aStack.getItem()).drain(copyAmount(1, aStack), Integer.MAX_VALUE, true);
        FluidContainerData tData = sFilledContainerToData.get(new GT_ItemStack(aStack));
        return tData == null ? null : tData.fluid.copy();
    }

    public static ItemStack getContainerForFilledItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack)) return null;
        FluidContainerData tData = sFilledContainerToData.get(new GT_ItemStack(aStack));
        if (tData != null) return copyAmount(1, tData.emptyContainer);
        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            ((IFluidContainerItem) aStack.getItem()).drain(aStack = copyAmount(1, aStack), Integer.MAX_VALUE, true);
            return aStack;
        }
        return null;
    }

    public static ItemStack getContainerItem(ItemStack aStack, boolean aCheckIFluidContainerItems) {
        if (isStackInvalid(aStack)) return null;
        if (aStack.getItem().hasContainerItem(aStack)) return aStack.getItem().getContainerItem(aStack);
        /** These are all special Cases, in which it is intended to have only GT Blocks outputting those Container Items */
        if (ItemList.Cell_Empty.isStackEqual(aStack, false, true)) return null;
        if (ItemList.IC2_Fuel_Can_Filled.isStackEqual(aStack, false, true)) return ItemList.IC2_Fuel_Can_Empty.get(1);
        if (aStack.getItem() == Items.POTIONITEM || aStack.getItem() == Items.EXPERIENCE_BOTTLE || ItemList.TF_Vial_FieryBlood.isStackEqual(aStack) || ItemList.TF_Vial_FieryTears.isStackEqual(aStack))
            return ItemList.Bottle_Empty.get(1);

        if (aCheckIFluidContainerItems && aStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) aStack.getItem()).getCapacity(aStack) > 0) {
            ItemStack tStack = copyAmount(1, aStack);
            ((IFluidContainerItem) aStack.getItem()).drain(tStack, Integer.MAX_VALUE, true);
            if (!areStacksEqual(aStack, tStack)) return tStack;
            return null;
        }

        int tCapsuleCount = GT_ModHandler.getCapsuleCellContainerCount(aStack);
        if (tCapsuleCount > 0) return ItemList.Cell_Empty.get(tCapsuleCount);

        if (ItemList.IC2_ForgeHammer.isStackEqual(aStack) || ItemList.IC2_WireCutter.isStackEqual(aStack))
            return copyMetaData(Items.FEATHER.getDamage(aStack) + 1, aStack);
        return null;
    }

    public static synchronized boolean removeIC2BottleRecipe(ItemStack aContainer, ItemStack aInput, Map<ic2.api.recipe.ICannerBottleRecipeManager.Input, RecipeOutput> aRecipeList, ItemStack aOutput){
        if ((isStackInvalid(aInput) && isStackInvalid(aOutput) && isStackInvalid(aContainer)) || aRecipeList == null) return false;
        boolean rReturn = false;
        Iterator<Map.Entry<ic2.api.recipe.ICannerBottleRecipeManager.Input, RecipeOutput>> tIterator = aRecipeList.entrySet().iterator();
        aOutput = GT_OreDictUnificator.get(aOutput);
        while (tIterator.hasNext()) {
            Map.Entry<ic2.api.recipe.ICannerBottleRecipeManager.Input, RecipeOutput> tEntry = tIterator.next();
            if (aInput == null || tEntry.getKey().matches(aContainer, aInput)) {
                List<ItemStack> tList = tEntry.getValue().items;
                if (tList != null) for (ItemStack tOutput : tList)
                    if (aOutput == null || areStacksEqual(GT_OreDictUnificator.get(tOutput), aOutput)) {
                        tIterator.remove();
                        rReturn = true;
                        break;
                    }
            }
        }
        return rReturn;
    }

    public static synchronized boolean removeSimpleIC2MachineRecipe(ItemStack aInput, Iterable<IMachineRecipeManager.RecipeIoContainer> aRecipeList, ItemStack aOutput) {
        if ((isStackInvalid(aInput) && isStackInvalid(aOutput)) || aRecipeList == null) return false;
        boolean rReturn = false;
        Iterator<IMachineRecipeManager.RecipeIoContainer> tIterator = aRecipeList.iterator();
        aOutput = GT_OreDictUnificator.get(aOutput);
        while (tIterator.hasNext()) {
            IMachineRecipeManager.RecipeIoContainer tEntry = tIterator.next();
            if (aInput == null || tEntry.input.matches(aInput)) {
                List<ItemStack> tList = tEntry.output.items;
                if (tList != null) for (ItemStack tOutput : tList)
                    if (aOutput == null || areStacksEqual(GT_OreDictUnificator.get(tOutput), aOutput)) {
                        tIterator.remove();
                        rReturn = true;
                        break;
                    }
            }
        }
        return rReturn;
    }

    public static boolean addSimpleIC2MachineRecipe(ItemStack aInput, IMachineRecipeManager aRecipeList, NBTTagCompound aNBT, Object... aOutput) {
        if (isStackInvalid(aInput) || aOutput.length == 0 || aRecipeList == null) return false;
        ItemData tOreName = GT_OreDictUnificator.getAssociation(aInput);
        for (int i = 0; i < aOutput.length; i++) {
            if (aOutput[i] == null) {
                System.out.println("EmptyIC2Output!" + aInput.getUnlocalizedName());
                return false;
            }
        }
        ItemStack[] tStack = GT_OreDictUnificator.getStackArray(true, aOutput);
        if(tStack==null||(tStack.length>0&&GT_Utility.areStacksEqual(aInput, tStack[0])))return false;
        if (tOreName != null) {
            //Catch Fossils Archeology Revival crash
            if(tOreName.toString().equals("dustAsh")&&tStack[0].getUnlocalizedName().equals("tile.volcanicAsh"))return false;
            aRecipeList.addRecipe(new RecipeInputOreDict(tOreName.toString(), aInput.stackSize), aNBT, true, tStack);
        } else {
            aRecipeList.addRecipe(new RecipeInputItemStack(copy(aInput), aInput.stackSize), aNBT, true, tStack);
        }
        return true;
    }

    public static ItemStack getWrittenBook(String aMapping, ItemStack aStackToPutNBT) {
        if (isStringInvalid(aMapping)) return null;
        ItemStack rStack = GregTech_API.sBookList.get(aMapping);
        if (rStack == null) return aStackToPutNBT;
        if (aStackToPutNBT != null) {
            aStackToPutNBT.setTagCompound(rStack.getTagCompound());
            return aStackToPutNBT;
        }
        return copyAmount(1, rStack);
    }

    public static ItemStack getWrittenBook(String aMapping, String aTitle, String aAuthor, String... aPages) {
        if (isStringInvalid(aMapping)) return null;
        ItemStack rStack = GregTech_API.sBookList.get(aMapping);
        if (rStack != null) return copyAmount(1, rStack);
        if (isStringInvalid(aTitle) || isStringInvalid(aAuthor) || aPages.length <= 0) return null;
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
                    GT_Log.err.println("WARNING: String for written Book too long! -> " + aPages[i]);
            } else {
                GT_Log.err.println("WARNING: Too much Pages for written Book! -> " + aTitle);
                break;
            }
        }
        tNBTList.appendTag(new NBTTagString("Credits to " + aAuthor + " for writing this Book. This was Book Nr. " + sBookCount + " at its creation. Gotta get 'em all!"));
        tNBT.setTag("pages", tNBTList);
        rStack.setTagCompound(tNBT);
        GT_Log.out.println("GT_Mod: Added Book to Book List  -  Mapping: '" + aMapping + "'  -  Name: '" + aTitle + "'  -  Author: '" + aAuthor + "'");
        GregTech_API.sBookList.put(aMapping, rStack);
        return copy(rStack);
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength) {
        return doSoundAtClient(aSoundName, aTimeUntilNextSound, aSoundStrength, GT.getThePlayer());
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength, Entity aEntity) {
        if (aEntity == null) return false;
        return doSoundAtClient(aSoundName, aTimeUntilNextSound, aSoundStrength, aEntity.posX, aEntity.posY, aEntity.posZ);
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength, double aX, double aY, double aZ) {
        return doSoundAtClient(aSoundName, aTimeUntilNextSound, aSoundStrength, 1.01818028F, aX, aY, aZ);
    }

    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength, BlockPos pos) {
        return doSoundAtClient(aSoundName, aTimeUntilNextSound, aSoundStrength, 1.01818028F, pos.getX(), pos.getY(), pos.getZ());
    }


    public static boolean doSoundAtClient(String aSoundName, int aTimeUntilNextSound, float aSoundStrength, float aSoundModulation, double aX, double aY, double aZ) {
        if (isStringInvalid(aSoundName) || !FMLCommonHandler.instance().getEffectiveSide().isClient() || GT.getThePlayer() == null || !GT.getThePlayer().worldObj.isRemote)
            return false;
        new GT_Runnable_Sound(GT.getThePlayer().worldObj, MathHelper.floor_double(aX), MathHelper.floor_double(aY), MathHelper.floor_double(aZ), aTimeUntilNextSound, aSoundName, aSoundStrength, aSoundModulation).run();
        return true;
    }

    public static boolean sendSoundToPlayers(World aWorld, String aSoundName, float aSoundStrength, float aSoundModulation, int aX, int aY, int aZ) {
        if (isStringInvalid(aSoundName) || aWorld == null || aWorld.isRemote) return false;
        NW.sendToAllAround(aWorld, new GT_Packet_Sound(aSoundName, aSoundStrength, aSoundModulation, aX, (short) aY, aZ), aX, aY, aZ);
        return true;
    }
    public static boolean sendSoundToPlayers(World aWorld, String aSoundName, float aSoundStrength, float aSoundModulation, BlockPos blockPos) {
        return sendSoundToPlayers(aWorld, aSoundName, aSoundStrength, aSoundModulation, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }


    public static int stackToIntHash(ItemStack aStack) {
        if (isStackInvalid(aStack)) return 0;
        return Item.getIdFromItem(aStack.getItem()) | (Items.FEATHER.getDamage(aStack) << 16);
    }

    public static boolean arrayContains(Object aObject, Object... aObjects) {
        return listContains(aObject, Arrays.asList(aObjects));
    }

    public static boolean listContains(Object aObject, Collection aObjects) {
        if (aObjects == null) return false;
        return aObjects.contains(aObject);
    }

    public static <T> boolean arrayContainsNonNull(T... aArray) {
        if (aArray != null) for (Object tObject : aArray) if (tObject != null) return true;
        return false;
    }

    public static <T> ArrayList<T> getArrayListWithoutNulls(T... aArray) {
        if (aArray == null) return new ArrayList<T>();
        ArrayList<T> rList = new ArrayList<T>(Arrays.asList(aArray));
        for (int i = 0; i < rList.size(); i++) if (rList.get(i) == null) rList.remove(i--);
        return rList;
    }

    public static <T> ArrayList<T> getArrayListWithoutTrailingNulls(T... aArray) {
        if (aArray == null) return new ArrayList<T>();
        ArrayList<T> rList = new ArrayList<T>(Arrays.asList(aArray));
        for (int i = rList.size() - 1; i >= 0 && rList.get(i) == null; ) rList.remove(i--);
        return rList;
    }

    public static Block getBlock(Object aBlock) {
        return (Block) aBlock;
    }

    public static Block getBlockFromStack(Object aStack) {
        if (isStackInvalid(aStack)) return null;
        return Block.getBlockFromItem(((ItemStack) aStack).getItem());
    }

    public static boolean isBlockValid(Object aBlock) {
        return aBlock instanceof Block;
    }

    public static boolean isBlockInvalid(Object aBlock) {
        return aBlock == null || !(aBlock instanceof Block);
    }

    public static boolean isStringValid(Object aString) {
        return aString != null && !aString.toString().isEmpty();
    }

    public static boolean isStringInvalid(Object aString) {
        return aString == null || aString.toString().isEmpty();
    }

    public static boolean isStackValid(Object aStack) {
        return (aStack instanceof ItemStack) && ((ItemStack) aStack).getItem() != null && ((ItemStack) aStack).stackSize >= 0;
    }

    public static boolean isStackInvalid(Object aStack) {
        return aStack == null || !(aStack instanceof ItemStack) || ((ItemStack) aStack).getItem() == null || ((ItemStack) aStack).stackSize < 0;
    }

    public static boolean isDebugItem(ItemStack aStack) {
        return /*ItemList.Armor_Cheat.isStackEqual(aStack, T, T) || */areStacksEqual(GT_ModHandler.getIC2Item(ItemName.debug_item, 1), aStack, true);
    }

    public static ItemStack updateItemStack(ItemStack aStack) {
        if (isStackValid(aStack) && aStack.getItem() instanceof GT_Generic_Item)
            ((GT_Generic_Item) aStack.getItem()).isItemStackUsable(aStack);
        return aStack;
    }

    public static boolean isOpaqueBlock(World aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlockState(new BlockPos(aX, aY, aZ)).isOpaqueCube();
    }

    public static boolean isOpaqueBlock(World aWorld, BlockPos blockPos) {
        return aWorld.getBlockState(blockPos).isOpaqueCube();
    }

    public static boolean isBlockAir(World aWorld, int aX, int aY, int aZ) {
        return aWorld.isAirBlock(new BlockPos(aX, aY, aZ));
    }

    public static boolean hasBlockHitBox(World aWorld, BlockPos blockPos) {
        AxisAlignedBB box = aWorld.getBlockState(blockPos).getCollisionBoundingBox(aWorld, blockPos);
        return box != null && (box.maxX != box.minX && box.maxZ != box.minZ && box.maxY != box.minY);
    }

    public static void setCoordsOnFire(World aWorld, int aX, int aY, int aZ, boolean aReplaceCenter) {
        BlockPos blockPos = new BlockPos(aX, aY, aZ);
        if (aReplaceCenter)
            if (aWorld.getBlockState(blockPos).getCollisionBoundingBox(aWorld, blockPos) == null)
                aWorld.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.east()).getCollisionBoundingBox(aWorld, blockPos.east()) == null)
            aWorld.setBlockState(blockPos.east(), Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.south()).getCollisionBoundingBox(aWorld, blockPos.south()) == null)
            aWorld.setBlockState(blockPos.south(), Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.west()).getCollisionBoundingBox(aWorld, blockPos.west()) == null)
            aWorld.setBlockState(blockPos.west(), Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.north()).getCollisionBoundingBox(aWorld, blockPos.north()) == null)
            aWorld.setBlockState(blockPos.north(), Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.up()).getCollisionBoundingBox(aWorld, blockPos.up()) == null)
            aWorld.setBlockState(blockPos.up(), Blocks.FIRE.getDefaultState());
        if (aWorld.getBlockState(blockPos.down()).getCollisionBoundingBox(aWorld, blockPos.down()) == null)
            aWorld.setBlockState(blockPos.down(), Blocks.FIRE.getDefaultState());
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
    public static String parseNumberToString(int aNumber) {
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

        if (tString.equals(E)) tString = "0";

        return negative ? "-" + tString : tString;
    }

    public static NBTTagCompound getNBTContainingBoolean(NBTTagCompound aNBT, Object aTag, boolean aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setBoolean(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingByte(NBTTagCompound aNBT, Object aTag, byte aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setByte(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingShort(NBTTagCompound aNBT, Object aTag, short aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setShort(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingInteger(NBTTagCompound aNBT, Object aTag, int aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setInteger(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingFloat(NBTTagCompound aNBT, Object aTag, float aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setFloat(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingDouble(NBTTagCompound aNBT, Object aTag, double aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        aNBT.setDouble(aTag.toString(), aValue);
        return aNBT;
    }

    public static NBTTagCompound getNBTContainingString(NBTTagCompound aNBT, Object aTag, Object aValue) {
        if (aNBT == null) aNBT = new NBTTagCompound();
        if (aValue == null) return aNBT;
        aNBT.setString(aTag.toString(), aValue.toString());
        return aNBT;
    }

    public static boolean isWearingFullFrostHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sFrostHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullHeatHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sHeatHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullBioHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sBioHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullRadioHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sRadioHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullElectroHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sElectroHazmatList)) return false;
        return true;
    }

    public static boolean isWearingFullGasHazmat(EntityLivingBase aEntity) {
        for (EntityEquipmentSlot i : EntityEquipmentSlot.values())
            if (!isStackInList(aEntity.getItemStackFromSlot(i), GregTech_API.sGasHazmatList)) return false;
        return true;
    }

    public static float getHeatDamageFromItem(ItemStack aStack) {
        ItemData tData = GT_OreDictUnificator.getItemData(aStack);
        return tData == null ? 0 : (tData.mPrefix == null ? 0 : tData.mPrefix.mHeatDamage) + (tData.hasValidMaterialData() ? tData.mMaterial.mMaterial.mHeatDamage : 0);
    }

    public static int getRadioactivityLevel(ItemStack aStack) {
        ItemData tData = GT_OreDictUnificator.getItemData(aStack);
        if (tData != null && tData.hasValidMaterialData()) {
            if (tData.mMaterial.mMaterial.mEnchantmentArmors instanceof Enchantment_Radioactivity)
                return tData.mMaterial.mMaterial.mEnchantmentArmorsLevel;
            if (tData.mMaterial.mMaterial.mEnchantmentTools instanceof Enchantment_Radioactivity)
                return tData.mMaterial.mMaterial.mEnchantmentToolsLevel;
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment_Radioactivity.INSTANCE, aStack);
    }

    public static boolean isImmuneToBreathingGasses(EntityLivingBase aEntity) {
        return isWearingFullGasHazmat(aEntity);
    }

    public static boolean applyHeatDamage(EntityLivingBase aEntity, float aDamage) {
        if (aDamage > 0 && aEntity != null && aEntity.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null && !isWearingFullHeatHazmat(aEntity)) {
            aEntity.attackEntityFrom(GT_DamageSources.getHeatDamage(), aDamage);
            return true;
        }
        return false;
    }

    public static boolean applyFrostDamage(EntityLivingBase aEntity, float aDamage) {
        if (aDamage > 0 && aEntity != null && !isWearingFullFrostHazmat(aEntity)) {
            aEntity.attackEntityFrom(GT_DamageSources.getFrostDamage(), aDamage);
            return true;
        }
        return false;
    }

    public static boolean applyElectricityDamage(EntityLivingBase aEntity, long aVoltage, long aAmperage) {
        long aDamage = getTier(aVoltage) * aAmperage * 4;
        if (aDamage > 0 && aEntity != null && !isWearingFullElectroHazmat(aEntity)) {
            aEntity.attackEntityFrom(GT_DamageSources.getElectricDamage(), aDamage);
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

    public static ItemStack setStack(Object aSetStack, Object aToStack) {
        if (isStackInvalid(aSetStack) || isStackInvalid(aToStack)) return null;
        ((ItemStack) aSetStack).setItem(((ItemStack) aToStack).getItem());
        ((ItemStack) aSetStack).stackSize = ((ItemStack) aToStack).stackSize;
        Items.FEATHER.setDamage((ItemStack) aSetStack, Items.FEATHER.getDamage((ItemStack) aToStack));
        ((ItemStack) aSetStack).setTagCompound(((ItemStack) aToStack).getTagCompound());
        return (ItemStack) aSetStack;
    }

    public static FluidStack[] copyFluidArray(FluidStack... aStacks) {
        FluidStack[] rStacks = new FluidStack[aStacks.length];
        for (int i = 0; i < aStacks.length; i++) if (aStacks[i] != null) rStacks[i] = aStacks[i].copy();
        return rStacks;
    }

    public static ItemStack[] copyStackArray(Object... aStacks) {
        ItemStack[] rStacks = new ItemStack[aStacks.length];
        for (int i = 0; i < aStacks.length; i++) rStacks[i] = copy(aStacks[i]);
        return rStacks;
    }

    public static ItemStack copy(Object... aStacks) {
        for (Object tStack : aStacks)
            if (isStackValid(tStack)) return ((ItemStack) tStack).copy();
        return null;
    }

    public static ItemStack copyAmount(long aAmount, Object... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (isStackInvalid(rStack)) return null;
        if (aAmount > 64) aAmount = 64;
        else if (aAmount == -1) aAmount = 111;
        else if (aAmount < 0) aAmount = 0;
        rStack.stackSize = (byte) aAmount;
        return rStack;
    }

    public static ItemStack copyMetaData(long aMetaData, Object... aStacks) {
        ItemStack rStack = copy(aStacks);
        if (isStackInvalid(rStack)) return null;
        Items.FEATHER.setDamage(rStack, (short) aMetaData);
        return rStack;
    }

    public static ItemStack copyAmountAndMetaData(long aAmount, long aMetaData, Object... aStacks) {
        ItemStack rStack = copyAmount(aAmount, aStacks);
        if (isStackInvalid(rStack)) return null;
        Items.FEATHER.setDamage(rStack, (short) aMetaData);
        return rStack;
    }

    /**
     * returns a copy of an ItemStack with its Stacksize being multiplied by aMultiplier
     */
    public static ItemStack mul(long aMultiplier, Object... aStacks) {
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

    public static FluidStack loadFluid(NBTTagCompound aNBT, String aTagName) {
        return loadFluid(aNBT.getCompoundTag(aTagName));
    }

    /**
     * Loads an ItemStack properly.
     */
    public static ItemStack loadItem(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack rStack = ItemStack.loadItemStackFromNBT(aNBT);
        try {
            if (rStack != null && (rStack.getItem().getClass().getName().startsWith("ic2.core.migration"))) {
                rStack.getItem().onUpdate(rStack, DW, null, 0, false);
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }
        return GT_OreDictUnificator.get(true, rStack);
    }

    /**
     * Loads an FluidStack properly.
     */
    public static FluidStack loadFluid(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        return FluidStack.loadFluidStackFromNBT(aNBT);
    }

    public static <E> E selectItemInList(int aIndex, E aReplacement, List<E> aList) {
        if (aList == null || aList.isEmpty()) return aReplacement;
        if (aList.size() <= aIndex) return aList.get(aList.size() - 1);
        if (aIndex < 0) return aList.get(0);
        return aList.get(aIndex);
    }

    public static <E> E selectItemInList(int aIndex, E aReplacement, E... aList) {
        if (aList == null || aList.length == 0) return aReplacement;
        if (aList.length <= aIndex) return aList[aList.length - 1];
        if (aIndex < 0) return aList[0];
        return aList[aIndex];
    }

    public static boolean isStackInList(ItemStack aStack, Collection<GT_ItemStack> aList) {
        if (aStack == null) {
            return false;
        }
        return isStackInList(new GT_ItemStack(aStack), aList);
    }

    public static boolean isStackInList(GT_ItemStack aStack, Collection<GT_ItemStack> aList) {
        return aStack != null && (aList.contains(aStack) || aList.contains(new GT_ItemStack(aStack.mItem, aStack.mStackSize, W)));
    }

    /**
     * re-maps all Keys of a Map after the Keys were weakened.
     */
    public static <X, Y> Map<X, Y> reMap(Map<X, Y> aMap) {
        Map<X, Y> tMap = new HashMap<X, Y>();
        tMap.putAll(aMap);
        aMap.clear();
        aMap.putAll(tMap);
        return aMap;
    }

    /**
     * Why the fuck do neither Java nor Guava have a Function to do this?
     */
    public static <X, Y extends Comparable> LinkedHashMap<X, Y> sortMapByValuesAcending(Map<X, Y> aMap) {
        List<Map.Entry<X, Y>> tEntrySet = new LinkedList<Map.Entry<X, Y>>(aMap.entrySet());
        Collections.sort(tEntrySet, new Comparator<Map.Entry<X, Y>>() {
            @Override
            public int compare(Entry<X, Y> aValue1, Entry<X, Y> aValue2) {
                return aValue1.getValue().compareTo(aValue2.getValue());
            }
        });
        LinkedHashMap<X, Y> rMap = new LinkedHashMap<X, Y>();
        for (Map.Entry<X, Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
        return rMap;
    }

    /**
     * Why the fuck do neither Java nor Guava have a Function to do this?
     */
    public static <X, Y extends Comparable> LinkedHashMap<X, Y> sortMapByValuesDescending(Map<X, Y> aMap) {
        List<Map.Entry<X, Y>> tEntrySet = new LinkedList<Map.Entry<X, Y>>(aMap.entrySet());
        Collections.sort(tEntrySet, new Comparator<Map.Entry<X, Y>>() {
            @Override
            public int compare(Entry<X, Y> aValue1, Entry<X, Y> aValue2) {
                return aValue2.getValue().compareTo(aValue1.getValue());//FB: RV - RV_NEGATING_RESULT_OF_COMPARETO
            }
        });
        LinkedHashMap<X, Y> rMap = new LinkedHashMap<X, Y>();
        for (Map.Entry<X, Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
        return rMap;
    }

    /**
     * Translates a Material Amount into an Amount of Fluid in Fluid Material Units.
     */
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
        try {
            if (DimensionManager.getProvider(aDimensionID).getClass().getName().contains("com.xcompwiz.mystcraft"))
                return true;
        } catch (Throwable e) {/*Do nothing*/}
        try {
            if (DimensionManager.getProvider(aDimensionID).getClass().getName().contains("TwilightForest")) return true;
        } catch (Throwable e) {/*Do nothing*/}
        return GregTech_API.sDimensionalList.contains(aDimensionID);
    }

    public static boolean moveEntityToDimensionAtCoords(Entity aEntity, int aDimension, double aX, double aY, double aZ) {
        WorldServer tTargetWorld = DimensionManager.getWorld(aDimension), tOriginalWorld = DimensionManager.getWorld(aEntity.worldObj.provider.getDimension());
        if (tTargetWorld != null && tOriginalWorld != null && tTargetWorld != tOriginalWorld) {

            if (aEntity instanceof EntityPlayerMP) {
                EntityPlayerMP aPlayer = (EntityPlayerMP) aEntity;
//                aPlayer.dimension = aDimension;
//                aPlayer.connection.sendPacket(new SPacketRespawn(aPlayer.dimension, aPlayer.worldObj.getDifficulty(), aPlayer.worldObj.getWorldInfo().getTerrainType(), aPlayer.interactionManager.getGameType()));
//                tOriginalWorld.removeEntityDangerously(aPlayer);
//                aPlayer.isDead = false;
//                aPlayer.setWorld(tTargetWorld);
//                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().preparePlayer(aPlayer, tOriginalWorld);
//                aPlayer.connection.setPlayerLocation(aX + 0.5, aY + 0.5, aZ + 0.5, aPlayer.rotationYaw, aPlayer.rotationPitch);
//                aPlayer.interactionManager.setWorld(tTargetWorld);
//                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().updateTimeAndWeatherForPlayer(aPlayer, tTargetWorld);
//                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().syncPlayerInventory(aPlayer);
//                Iterator tIterator = aPlayer.getActivePotionEffects().iterator();
//                while (tIterator.hasNext()) {
//                    PotionEffect potioneffect = (PotionEffect) tIterator.next();
//                    aPlayer.connection.sendPacket(new SPacketEntityEffect(aPlayer.getEntityId(), potioneffect));
//                }
//                FMLCommonHandler.instance().firePlayerChangedDimensionEvent(aPlayer, tOriginalWorld.provider.getDimension(), aDimension);
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
        int oil = tRandom.nextInt(3);
        double amount = tRandom.nextInt(50) + tRandom.nextDouble();
        oil = tRandom.nextInt(4);
//		System.out.println("Oil: "+(aX/96)+" "+(aZ/96)+" "+oil+" "+amount);
//		amount = 40;
        Fluid tFluid = null;
        switch (oil) {
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
        ChunkPos tPos = new ChunkPos(aX/16, aZ/16);
        int[] tInts = new int[2];
    	if(GT_Proxy.chunkData.containsKey(tPos)){
    		tInts = GT_Proxy.chunkData.get(tPos);
    		if(tInts.length>0){
    			if(tInts[0]>0){tAmount = tInts[0];}
    		}
    		GT_Proxy.chunkData.remove(tPos);
    	}
    	tAmount = tAmount - 5;
    	tInts[0] = tAmount;
    	GT_Proxy.chunkData.put(tPos, tInts);
        return new FluidStack(tFluid, tAmount);
    }

    public static int getCoordinateScan(ArrayList<String> aList, EntityPlayer aPlayer, World aWorld, int aScanLevel, BlockPos blockPos, EnumFacing aSide, float aClickX, float aClickY, float aClickZ) {
        if (aList == null) return 0;

        ArrayList<String> tList = new ArrayList<>();
        int rEUAmount = 0;

        TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
        IBlockState tBlock = aWorld.getBlockState(blockPos);

        tList.add("----- X: " + blockPos.getX() + " Y: " + blockPos.getY() + " Z: " + blockPos.getZ() + " -----");
        try {
            if (tTileEntity instanceof IInventory)
                tList.add("Name: " + ((IInventory) tTileEntity).getName() + "  MetaData: " + aWorld.getBlockState(blockPos));
            else
                tList.add("Name: " + tBlock.getBlock().getUnlocalizedName() + "  MetaData: " + tBlock.getBlock().getMetaFromState(tBlock));

            //tList.add("Hardness: " + tBlock.getBlockHardness(aWorld, blockPos) + "  Blast Resistance: " + tBlock.getBlock().getExplosionResistance(aPlayer, aWorld, aX, aY, aZ, aPlayer.posX, aPlayer.posY, aPlayer.posZ));
            if (tBlock.getBlock().isBeaconBase(aWorld, blockPos, blockPos.add(0, 1, 0))) tList.add("Is valid Beacon Pyramid Material");
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
        if (tTileEntity != null) {
            try {
                if (tTileEntity instanceof IFluidHandler) {
                    rEUAmount += 500;
                    FluidTankInfo[] tTanks = ((IFluidHandler) tTileEntity).getTankInfo(aSide);
                    if (tTanks != null) for (byte i = 0; i < tTanks.length; i++) {
                        tList.add("Tank " + i + ": " + GT_Utility.formatNumbers((tTanks[i].fluid == null ? 0 : tTanks[i].fluid.amount)) + " / " + GT_Utility.formatNumbers(tTanks[i].capacity) + " " + getFluidName(tTanks[i].fluid, true));
                    }
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.reactor.IReactorChamber) {
                    rEUAmount += 500;
                    tTileEntity = (TileEntity) (((ic2.api.reactor.IReactorChamber) tTileEntity).getReactorInstance());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.reactor.IReactor) {
                    rEUAmount += 500;
                    tList.add("Heat: " + ((ic2.api.reactor.IReactor) tTileEntity).getHeat() + "/" + ((ic2.api.reactor.IReactor) tTileEntity).getMaxHeat()
                            + "  HEM: " + ((ic2.api.reactor.IReactor) tTileEntity).getHeatEffectModifier() + "  Base EU Output: "/* + ((ic2.api.reactor.IReactor)tTileEntity).getOutput()*/);
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.tile.IWrenchable) {
                    rEUAmount += 100;
                    tList.add("Facing: " + ((ic2.api.tile.IWrenchable) tTileEntity).getFacing(tTileEntity.getWorld(), blockPos));
                    tList.add(((ic2.api.tile.IWrenchable) tTileEntity).wrenchCanRemove(tTileEntity.getWorld(), blockPos, aPlayer) ? "You can remove this with a Wrench" : "You can NOT remove this with a Wrench");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergyTile) {
                    rEUAmount += 200;
                    //aList.add(((ic2.api.energy.tile.IEnergyTile)tTileEntity).isAddedToEnergyNet()?"Added to E-net":"Not added to E-net! Bug?");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergySink) {
                    rEUAmount += 400;
                    //aList.add("Demanded Energy: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).demandsEnergy());
                    //tList.add("Max Safe Input: " + getTier(((ic2.api.energy.tile.IEnergySink)tTileEntity).getSinkTier()));
                    //tList.add("Max Safe Input: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).getMaxSafeInput());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergySource) {
                    rEUAmount += 400;
                    //aList.add("Max Energy Output: " + ((ic2.api.energy.tile.IEnergySource)tTileEntity).getMaxEnergyOutput());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.energy.tile.IEnergyConductor) {
                    rEUAmount += 200;
                    tList.add("Conduction Loss: " + ((ic2.api.energy.tile.IEnergyConductor) tTileEntity).getConductionLoss());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ic2.api.tile.IEnergyStorage) {
                    rEUAmount += 200;
                    tList.add("Contained Energy: " + ((ic2.api.tile.IEnergyStorage) tTileEntity).getStored() + " of " + ((ic2.api.tile.IEnergyStorage) tTileEntity).getCapacity());
                    //aList.add(((ic2.api.tile.IEnergyStorage)tTileEntity).isTeleporterCompatible(ic2.api.Direction.YP)?"Teleporter Compatible":"Not Teleporter Compatible");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IUpgradableMachine) {
                    rEUAmount += 500;
                    if (((IUpgradableMachine) tTileEntity).hasMufflerUpgrade()) tList.add("Has Muffler Upgrade");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IMachineProgress) {
                    rEUAmount += 400;
                    int tValue = 0;
                    if (0 < (tValue = ((IMachineProgress) tTileEntity).getMaxProgress()))
                        tList.add("Progress: " + GT_Utility.formatNumbers(tValue) + " / " + GT_Utility.formatNumbers(((IMachineProgress) tTileEntity).getProgress()));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof ICoverable) {
                    rEUAmount += 300;
                    String tString = ((ICoverable) tTileEntity).getCoverBehaviorAtSide((byte) aSide.getIndex()).getDescription((byte) aSide.getIndex(), ((ICoverable) tTileEntity).getCoverIDAtSide((byte) aSide.getIndex()), ((ICoverable) tTileEntity).getCoverDataAtSide((byte) aSide.getIndex()), (ICoverable) tTileEntity);
                    if (tString != null && !tString.equals(E)) tList.add(tString);
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IBasicEnergyContainer && ((IBasicEnergyContainer) tTileEntity).getEUCapacity() > 0) {
                    tList.add("Max IN: " + ((IBasicEnergyContainer) tTileEntity).getInputVoltage() + " EU");
                    tList.add("Max OUT: " + ((IBasicEnergyContainer) tTileEntity).getOutputVoltage() + " EU at " + ((IBasicEnergyContainer) tTileEntity).getOutputAmperage() + " Amperes");
                    tList.add("Energy: " + GT_Utility.formatNumbers(((IBasicEnergyContainer) tTileEntity).getStoredEU()) + " / " + GT_Utility.formatNumbers(((IBasicEnergyContainer) tTileEntity).getEUCapacity()) + "EU");
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IGregTechTileEntity) {
                    tList.add("Owned by: " + ((IGregTechTileEntity) tTileEntity).getOwnerName());
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
                if (tTileEntity instanceof IGregTechDeviceInformation && ((IGregTechDeviceInformation) tTileEntity).isGivingInformation()) {
                    tList.addAll(Arrays.asList(((IGregTechDeviceInformation) tTileEntity).getInfoData()));
                }
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
            try {
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
            } catch (Throwable e) {
                if (D1) e.printStackTrace(GT_Log.err);
            }
        }
        if (aPlayer.capabilities.isCreativeMode&&GT_Values.D1) {
            FluidStack tFluid = getUndergroundOil(aWorld, blockPos.getX(), blockPos.getY());
            tList.add("Oil in Chunk: " + tFluid.amount + " " + tFluid.getLocalizedName());
        }
        //if(aPlayer.capabilities.isCreativeMode){
        ChunkPos pos = new ChunkPos(blockPos.getX() / 16, blockPos.getZ() / 16);
            if(GT_Proxy.chunkData.containsKey(pos)){
                int[] tPollution = GT_Proxy.chunkData.get(pos);
                if(tPollution.length>1){
                    tList.add("Pollution in Chunk: "+tPollution[1]);
                }else{
                    tList.add("No Pollution in Chunk");
                }
            }
        //}

        try {
            if (tBlock instanceof IDebugableBlock) {
                rEUAmount += 500;
                ArrayList<String> temp = ((IDebugableBlock) tBlock).getDebugInfo(aPlayer, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3);
                if (temp != null) tList.addAll(temp);
            }
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }

        BlockScanningEvent tEvent = new BlockScanningEvent(aWorld, aPlayer, blockPos.getX(), blockPos.getY(), blockPos.getZ(), (byte) aSide.getIndex(), aScanLevel, tBlock.getBlock(), tTileEntity, tList, aClickX, aClickY, aClickZ);
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
     * returns -1 if invalid. Even though that could never happen.
     */
    public static byte determineWrenchingSide(byte aSide, float aX, float aY, float aZ) {
        byte tBack = getOppositeSide(aSide);
        switch (aSide) {
            case 0:
            case 1:
                if (aX < 0.25) {
                    if (aZ < 0.25) return tBack;
                    if (aZ > 0.75) return tBack;
                    return 4;
                }
                if (aX > 0.75) {
                    if (aZ < 0.25) return tBack;
                    if (aZ > 0.75) return tBack;
                    return 5;
                }
                if (aZ < 0.25) return 2;
                if (aZ > 0.75) return 3;
                return aSide;
            case 2:
            case 3:
                if (aX < 0.25) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return 4;
                }
                if (aX > 0.75) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return 5;
                }
                if (aY < 0.25) return 0;
                if (aY > 0.75) return 1;
                return aSide;
            case 4:
            case 5:
                if (aZ < 0.25) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return 2;
                }
                if (aZ > 0.75) {
                    if (aY < 0.25) return tBack;
                    if (aY > 0.75) return tBack;
                    return 3;
                }
                if (aY < 0.25) return 0;
                if (aY > 0.75) return 1;
                return aSide;
        }
        return -1;
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
            ArrayList<String> tTagsToRemove = new ArrayList<String>();
            for (Object tKey : aNBT.getKeySet()) {
                NBTBase tValue = aNBT.getTag((String) tKey);
                if (tValue == null || (tValue instanceof NBTPrimitive && ((NBTPrimitive) tValue).getInt() == 0) || (tValue instanceof NBTTagString && isStringInvalid(((NBTTagString) tValue).getString())))
                    tTagsToRemove.add((String) tKey);
            }
            for (Object tKey : tTagsToRemove) aNBT.removeTag((String) tKey);
            aStack.setTagCompound(aNBT.hasNoTags() ? null : aNBT);
        }

        public static NBTTagCompound getNBT(ItemStack aStack) {
            NBTTagCompound rNBT = aStack.getTagCompound();
            return rNBT == null ? new NBTTagCompound() : rNBT;
        }

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

        public static void setBookAuthor(ItemStack aStack, String aAuthor) {
            NBTTagCompound tNBT = getNBT(aStack);
            tNBT.setString("author", aAuthor);
            setNBT(aStack, tNBT);
        }

        public static String getBookAuthor(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            return tNBT.getString("author");
        }

        public static void setProspectionData(ItemStack aStack, int aX, int aY, int aZ, int aDim, FluidStack aFluid, String[] aOres) {
            NBTTagCompound tNBT = getNBT(aStack);
            String tData = aX + "," + aY + "," + aZ + "," + aDim + "," + (aFluid.amount / 5000) + "," + aFluid.getLocalizedName() + ",";
            for (String tString : aOres) {
                tData += tString + ",";
            }
            tNBT.setString("prospection", tData);
            setNBT(aStack, tNBT);
        }

        public static void convertProspectionData(ItemStack aStack) {
            NBTTagCompound tNBT = getNBT(aStack);
            String tData = tNBT.getString("prospection");
            String[] tDataArray = tData.split(",");
            if (tDataArray.length > 6) {
                tNBT.setString("author", "X: " + tDataArray[0] + " Y: " + tDataArray[1] + " Z: " + tDataArray[2] + " Dim: " + tDataArray[3]);
                NBTTagList tNBTList = new NBTTagList();
                String tOres = " Prospected Ores: ";
                for (int i = 6; tDataArray.length > i; i++) {
                    tOres += (tDataArray[i] + " ");
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
                        try {
                            short short1 = nbttaglist.getCompoundTagAt(i).getShort("id");
                            short short2 = nbttaglist.getCompoundTagAt(i).getShort("lvl");
                            if (Enchantment.getEnchantmentByID(short1) != null)
                                aBullshitModifier.calculateModifier(Enchantment.getEnchantmentByID(short1), short2);
                        } catch (Throwable e) {
                            //
                        }
                    }
                }
            }
        }

        private static void applyArrayOfBullshit(IBullshit aBullshitModifier, ItemStack[] aStacks) {
            ItemStack[] aitemstack1 = aStacks;
            int i = aStacks.length;
            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = aitemstack1[j];
                applyBullshit(aBullshitModifier, itemstack);
            }
        }

        public static void applyBullshitA(EntityLivingBase aPlayer, Entity aEntity, ItemStack aStack) {
            mBullshitIteratorA.mPlayer = aPlayer;
            mBullshitIteratorA.mEntity = aEntity;
            //if (aPlayer != null) applyArrayOfBullshit(mBullshitIteratorA, aPlayer.getLastActiveItems());
            if (aStack != null) applyBullshit(mBullshitIteratorA, aStack);
        }

        public static void applyBullshitB(EntityLivingBase aPlayer, Entity aEntity, ItemStack aStack) {
            mBullshitIteratorB.mPlayer = aPlayer;
            mBullshitIteratorB.mEntity = aEntity;
            //if (aPlayer != null) applyArrayOfBullshit(mBullshitIteratorB, aPlayer.last);
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