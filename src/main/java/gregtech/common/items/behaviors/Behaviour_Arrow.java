package gregtech.common.items.behaviors;

import gregtech.api.enums.SubTag;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.entities.GT_Entity_Arrow;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Behaviour_Arrow
        extends Behaviour_None {
    public static Behaviour_Arrow DEFAULT_WOODEN = new Behaviour_Arrow(GT_Entity_Arrow.class, 1.0F, 6.0F);
    public static Behaviour_Arrow DEFAULT_PLASTIC = new Behaviour_Arrow(GT_Entity_Arrow.class, 1.5F, 6.0F);
    private final int mLevel;
    private final Enchantment mEnchantment;
    private final float mSpeedMultiplier;
    private final float mPrecision;
    private final Class<? extends GT_Entity_Arrow> mArrow;

    public Behaviour_Arrow(Class<? extends GT_Entity_Arrow> aArrow, float aSpeed, float aPrecision) {
        this(aArrow, aSpeed, aPrecision, null, 0);
    }

    public Behaviour_Arrow(Class<? extends GT_Entity_Arrow> aArrow, float aSpeed, float aPrecision, Enchantment aEnchantment, int aLevel) {
        this.mArrow = aArrow;
        this.mSpeedMultiplier = aSpeed;
        this.mPrecision = aPrecision;
        this.mEnchantment = aEnchantment;
        this.mLevel = aLevel;
    }

    @Override
    public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity, EnumHand hand) {
        if ((aEntity instanceof EntityLivingBase)) {
            GT_Utility.GT_EnchantmentHelper.applyBullshitA((EntityLivingBase) aEntity, aPlayer, aStack);
            GT_Utility.GT_EnchantmentHelper.applyBullshitB(aPlayer, aEntity, aStack);
            if (!aPlayer.capabilities.isCreativeMode) {
                aStack.stackSize -= 1;
            }
            if (aStack.stackSize <= 0) {
                aPlayer.setHeldItem(hand, null);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isItemStackUsable(GT_MetaBase_Item aItem, ItemStack aStack) {
        if ((this.mEnchantment != null) && (this.mLevel > 0)) {
            NBTTagCompound tNBT = GT_Utility.ItemNBT.getNBT(aStack);
            if (!tNBT.getBoolean("GT.HasBeenUpdated")) {
                tNBT.setBoolean("GT.HasBeenUpdated", true);
                GT_Utility.ItemNBT.setNBT(aStack, tNBT);
                GT_Utility.ItemNBT.addEnchantment(aStack, this.mEnchantment, this.mLevel);
            }
        }
        return true;
    }

    @Override
    public boolean canDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack) {
        return true;
    }

    @Override
    public ItemStack onDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack) {
        World aWorld = aSource.getWorld();
        IPosition tPosition = BlockDispenser.getDispensePosition(aSource);
        EnumFacing tFacing = aSource.getWorld().getBlockState(aSource.getBlockPos()).getValue(BlockDispenser.FACING);
        GT_Entity_Arrow tEntityArrow = (GT_Entity_Arrow) getProjectile(aItem, SubTag.PROJECTILE_ARROW, aStack, aWorld, tPosition.getX(), tPosition.getY(), tPosition.getZ());
        if (tEntityArrow != null) {
            tEntityArrow.setThrowableHeading(tFacing.getFrontOffsetX(), tFacing.getFrontOffsetY() + 0.1F, tFacing.getFrontOffsetZ(), this.mSpeedMultiplier * 1.1F, this.mPrecision);
            tEntityArrow.setArrowStack(aStack);
            tEntityArrow.setPickup(EntityArrow.PickupStatus.ALLOWED);
            aWorld.spawnEntityInWorld(tEntityArrow);
            if (aStack.stackSize < 100) {
                aStack.stackSize -= 1;
            }
            return aStack;
        }
        return super.onDispense(aItem, aSource, aStack);
    }

    @Override
    public boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack) {
        return aProjectileType == SubTag.PROJECTILE_ARROW;
    }

    @Override
    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        if (!hasProjectile(aItem, aProjectileType, aStack)) {
            return null;
        }
        GT_Entity_Arrow rArrow = (GT_Entity_Arrow) GT_Utility.callConstructor(this.mArrow.getName(), -1, null, true, new Object[]{aWorld, Double.valueOf(aX), Double.valueOf(aY), Double.valueOf(aZ)});
        rArrow.setArrowStack(aStack);
        return rArrow;
    }

    @Override
    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        if (!hasProjectile(aItem, aProjectileType, aStack)) {
            return null;
        }
        GT_Entity_Arrow rArrow = (GT_Entity_Arrow) GT_Utility.callConstructor(this.mArrow.getName(), -1, null, true, new Object[]{aWorld, aEntity, Float.valueOf(this.mSpeedMultiplier * aSpeed)});
        rArrow.setArrowStack(aStack);
        return rArrow;
    }
}
