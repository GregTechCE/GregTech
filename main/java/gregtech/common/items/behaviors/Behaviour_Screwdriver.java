/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  5:   */ import gregtech.api.util.GT_ModHandler;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import java.util.Map;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class Behaviour_Screwdriver
/* 15:   */   extends Behaviour_None
/* 16:   */ {
/* 17:   */   private final int mVanillaCosts;
/* 18:   */   private final int mEUCosts;
/* 19:   */   
/* 20:   */   public Behaviour_Screwdriver(int aVanillaCosts, int aEUCosts)
/* 21:   */   {
/* 22:17 */     this.mVanillaCosts = aVanillaCosts;
/* 23:18 */     this.mEUCosts = aEUCosts;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 27:   */   {
/* 28:23 */     if (aWorld.isRemote) {
/* 29:24 */       return false;
/* 30:   */     }
/* 31:26 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/* 32:27 */     if (aBlock == null) {
/* 33:27 */       return false;
/* 34:   */     }
/* 35:28 */     byte aMeta = (byte)aWorld.getBlockMetadata(aX, aY, aZ);
/* 36:31 */     if ((aBlock == Blocks.unpowered_repeater) || (aBlock == Blocks.powered_repeater))
/* 37:   */     {
/* 38:32 */       if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer))
/* 39:   */       {
/* 40:33 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aMeta / 4 * 4 + (aMeta % 4 + 1) % 4, 3);
/* 41:34 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
/* 42:   */       }
/* 43:36 */       return true;
/* 44:   */     }
/* 45:38 */     if ((aBlock == Blocks.unpowered_comparator) || (aBlock == Blocks.powered_comparator))
/* 46:   */     {
/* 47:39 */       if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer))
/* 48:   */       {
/* 49:40 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aMeta / 4 * 4 + (aMeta % 4 + 1) % 4, 3);
/* 50:41 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
/* 51:   */       }
/* 52:43 */       return true;
/* 53:   */     }
/* 54:45 */     return false;
/* 55:   */   }
/* 56:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Screwdriver
 * JD-Core Version:    0.7.0.1
 */