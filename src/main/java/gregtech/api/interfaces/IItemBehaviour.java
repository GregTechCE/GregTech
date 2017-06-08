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

    boolean onLeftClickEntity(E item, ItemStack itemStack, EntityPlayer player, Entity entity);

    boolean onItemUse(E item, ItemStack itemStack, EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand);

    boolean onItemUseFirst(E item, ItemStack itemStack, EntityPlayer player, World world, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand);

    ItemStack onItemRightClick(E item, ItemStack itemStack, World world, EntityPlayer player, EnumHand hand);

    List<String> getAdditionalToolTips(E item, List<String> list, ItemStack itemStack);

    void onUpdate(E item, ItemStack itemStack, World world, Entity player, int timer, boolean isInHand);

    boolean isItemStackUsable(E item, ItemStack itemStack);

    boolean canDispense(E item, IBlockSource source, ItemStack itemStack);

    ItemStack onDispense(E item, IBlockSource source, ItemStack itemStack);

    boolean hasProjectile(GT_MetaBase_Item item, SubTag projectileType, ItemStack itemStack);

    EntityArrow getProjectile(E item, SubTag aProjectileType, ItemStack itemStack, World world, double x, double y, double z);

    EntityArrow getProjectile(E item, SubTag aProjectileType, ItemStack itemStack, World world, EntityLivingBase entity, float speed);

}