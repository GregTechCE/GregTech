package gregtech.common.items.behaviors;

import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IItemBehaviour;
import gregtech.api.items.GT_MetaBase_Item;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_None
        implements IItemBehaviour<GT_MetaBase_Item> {
    public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        return false;
    }

    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return false;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return false;
    }

    public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        return aStack;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        return aList;
    }

    public void onUpdate(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand) {
    }

    public boolean isItemStackUsable(GT_MetaBase_Item aItem, ItemStack aStack) {
        return true;
    }

    public boolean canDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack) {
        return false;
    }

    public ItemStack onDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack) {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(aSource.getBlockMetadata());
        IPosition iposition = BlockDispenser.func_149939_a(aSource);
        ItemStack itemstack1 = aStack.splitStack(1);
        BehaviorDefaultDispenseItem.doDispense(aSource.getWorld(), itemstack1, 6, enumfacing, iposition);
        return aStack;
    }

    public boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack) {
        return false;
    }

    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        return null;
    }

    public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        return null;
    }
}
