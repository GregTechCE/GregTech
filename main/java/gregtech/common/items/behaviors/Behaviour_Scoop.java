/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import forestry.api.lepidopterology.EnumFlutterType;
/*  4:   */ import forestry.api.lepidopterology.IAlleleButterflySpecies;
/*  5:   */ import forestry.api.lepidopterology.IButterfly;
/*  6:   */ import forestry.api.lepidopterology.IButterflyGenome;
/*  7:   */ import forestry.api.lepidopterology.IButterflyRoot;
/*  8:   */ import forestry.api.lepidopterology.IEntityButterfly;
/*  9:   */ import forestry.api.lepidopterology.ILepidopteristTracker;
/* 10:   */ import gregtech.api.items.GT_MetaBase_Item;
/* 11:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/* 12:   */ import gregtech.api.util.GT_LanguageManager;
/* 13:   */ import java.util.List;
/* 14:   */ import net.minecraft.entity.Entity;
/* 15:   */ import net.minecraft.entity.item.EntityItem;
/* 16:   */ import net.minecraft.entity.player.EntityPlayer;
/* 17:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 18:   */ import net.minecraft.item.ItemStack;
/* 19:   */ import net.minecraft.world.World;
/* 20:   */ 
/* 21:   */ public class Behaviour_Scoop
/* 22:   */   extends Behaviour_None
/* 23:   */ {
/* 24:   */   private final int mCosts;
/* 25:   */   
/* 26:   */   public Behaviour_Scoop(int aCosts)
/* 27:   */   {
/* 28:21 */     this.mCosts = aCosts;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity)
/* 32:   */   {
/* 33:26 */     if ((aEntity instanceof IEntityButterfly))
/* 34:   */     {
/* 35:27 */       if (aPlayer.worldObj.isRemote) {
/* 36:27 */         return true;
/* 37:   */       }
/* 38:28 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/* 39:   */       {
/* 40:29 */         Object tButterfly = ((IEntityButterfly)aEntity).getButterfly();
/* 41:30 */         ((IButterfly)tButterfly).getGenome().getPrimary().getRoot().getBreedingTracker(aEntity.worldObj, aPlayer.getGameProfile()).registerCatch((IButterfly)tButterfly);
/* 42:31 */         aPlayer.worldObj.spawnEntityInWorld(new EntityItem(aPlayer.worldObj, aEntity.posX, aEntity.posY, aEntity.posZ, ((IButterfly)tButterfly).getGenome().getPrimary().getRoot().getMemberStack(((IButterfly)tButterfly).copy(), EnumFlutterType.BUTTERFLY.ordinal())));
/* 43:32 */         aEntity.setDead();
/* 44:   */       }
/* 45:34 */       return true;
/* 46:   */     }
/* 47:36 */     return false;
/* 48:   */   }
/* 49:   */   
/* 50:39 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.scoop", "Catches Butterflies on Leftclick");
/* 51:   */   
/* 52:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 53:   */   {
/* 54:43 */     aList.add(this.mTooltip);
/* 55:44 */     return aList;
/* 56:   */   }
/* 57:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Scoop
 * JD-Core Version:    0.7.0.1
 */