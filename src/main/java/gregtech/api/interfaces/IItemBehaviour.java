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
import net.minecraft.world.World;

import java.util.List;

public interface IItemBehaviour<E extends Item> {
    public boolean onLeftClickEntity(E aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity);

    public boolean onItemUse(E aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ);

    public boolean onItemUseFirst(E aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ);

    public ItemStack onItemRightClick(E aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer);

    public List<String> getAdditionalToolTips(E aItem, List<String> aList, ItemStack aStack);

    public void onUpdate(E aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand);

    public boolean isItemStackUsable(E aItem, ItemStack aStack);

    public boolean canDispense(E aItem, IBlockSource aSource, ItemStack aStack);

    public ItemStack onDispense(E aItem, IBlockSource aSource, ItemStack aStack);

    public boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack);

    public EntityArrow getProjectile(E aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ);

    public EntityArrow getProjectile(E aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed);
}