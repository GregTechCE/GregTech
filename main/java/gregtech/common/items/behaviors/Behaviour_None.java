/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.SubTag;
/*  4:   */ import gregtech.api.interfaces.IItemBehaviour;
/*  5:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  6:   */ import java.util.List;
/*  7:   */ import net.minecraft.block.BlockDispenser;
/*  8:   */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*  9:   */ import net.minecraft.dispenser.IBlockSource;
/* 10:   */ import net.minecraft.dispenser.IPosition;
/* 11:   */ import net.minecraft.entity.Entity;
/* 12:   */ import net.minecraft.entity.EntityLivingBase;
/* 13:   */ import net.minecraft.entity.player.EntityPlayer;
/* 14:   */ import net.minecraft.entity.projectile.EntityArrow;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ import net.minecraft.util.EnumFacing;
/* 17:   */ import net.minecraft.world.World;
/* 18:   */ 
/* 19:   */ public class Behaviour_None
/* 20:   */   implements IItemBehaviour<GT_MetaBase_Item>
/* 21:   */ {
/* 22:   */   public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity)
/* 23:   */   {
/* 24:24 */     return false;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 28:   */   {
/* 29:29 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 33:   */   {
/* 34:34 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer)
/* 38:   */   {
/* 39:39 */     return aStack;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 43:   */   {
/* 44:44 */     return aList;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void onUpdate(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand) {}
/* 48:   */   
/* 49:   */   public boolean isItemStackUsable(GT_MetaBase_Item aItem, ItemStack aStack)
/* 50:   */   {
/* 51:54 */     return true;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean canDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack)
/* 55:   */   {
/* 56:59 */     return false;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public ItemStack onDispense(GT_MetaBase_Item aItem, IBlockSource aSource, ItemStack aStack)
/* 60:   */   {
/* 61:64 */     EnumFacing enumfacing = BlockDispenser.func_149937_b(aSource.getBlockMetadata());
/* 62:65 */     IPosition iposition = BlockDispenser.func_149939_a(aSource);
/* 63:66 */     ItemStack itemstack1 = aStack.splitStack(1);
/* 64:67 */     BehaviorDefaultDispenseItem.doDispense(aSource.getWorld(), itemstack1, 6, enumfacing, iposition);
/* 65:68 */     return aStack;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean hasProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack)
/* 69:   */   {
/* 70:73 */     return false;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ)
/* 74:   */   {
/* 75:78 */     return null;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public EntityArrow getProjectile(GT_MetaBase_Item aItem, SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed)
/* 79:   */   {
/* 80:83 */     return null;
/* 81:   */   }
/* 82:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_None
 * JD-Core Version:    0.7.0.1
 */