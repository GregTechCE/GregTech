package gregtech.api.interfaces;

import gregtech.api.enums.SubTag;
import gregtech.api.items.GT_MetaBase_Item;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IItemBehaviour<E extends Item> {

    boolean onLeftClickEntity(E aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity, EnumHand hand);

    boolean onItemUse(E aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing aSide, float hitX, float hitY, float hitZ, EnumHand hand);

    boolean onItemUseFirst(E aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing aSide, float hitX, float hitY, float hitZ, EnumHand hand);

    ItemStack onItemRightClick(E aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer, EnumHand hand);

    List<String> getAdditionalToolTips(E aItem, List<String> aList, ItemStack aStack);

    void onUpdate(E aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand);

    boolean isItemStackUsable(E aItem, ItemStack aStack);

    boolean canDispense(E aItem, IBlockSource aSource, ItemStack aStack);

    ItemStack onDispense(E aItem, IBlockSource aSource, ItemStack aStack);

    boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack);

    EntityArrow getProjectile(E aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ);

    EntityArrow getProjectile(E aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed);

}