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
/* 14:   */ public class Behaviour_Crowbar
/* 15:   */   extends Behaviour_None
/* 16:   */ {
/* 17:   */   private final int mVanillaCosts;
/* 18:   */   private final int mEUCosts;
/* 19:   */   
/* 20:   */   public Behaviour_Crowbar(int aVanillaCosts, int aEUCosts)
/* 21:   */   {
/* 22:18 */     this.mVanillaCosts = aVanillaCosts;
/* 23:19 */     this.mEUCosts = aEUCosts;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 27:   */   {
/* 28:24 */     if (aWorld.isRemote) {
/* 29:25 */       return false;
/* 30:   */     }
/* 31:27 */     if (GT_ModHandler.getModItem("Railcraft", "fluid.creosote.bucket", 1L) != null) {
/* 32:27 */       return false;
/* 33:   */     }
/* 34:28 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/* 35:29 */     if (aBlock == null) {
/* 36:29 */       return false;
/* 37:   */     }
/* 38:30 */     byte aMeta = (byte)aWorld.getBlockMetadata(aX, aY, aZ);
/* 39:33 */     if (aBlock == Blocks.rail)
/* 40:   */     {
/* 41:34 */       if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer))
/* 42:   */       {
/* 43:35 */         aWorld.isRemote = true;
/* 44:36 */         aWorld.setBlock(aX, aY, aZ, aBlock, (aMeta + 1) % 10, 0);
/* 45:37 */         aWorld.isRemote = false;
/* 46:38 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(0)), 1.0F, -1.0F, aX, aY, aZ);
/* 47:   */       }
/* 48:40 */       return true;
/* 49:   */     }
/* 50:42 */     if ((aBlock == Blocks.detector_rail) || (aBlock == Blocks.activator_rail) || (aBlock == Blocks.golden_rail))
/* 51:   */     {
/* 52:43 */       if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer))
/* 53:   */       {
/* 54:44 */         aWorld.isRemote = true;
/* 55:45 */         aWorld.setBlock(aX, aY, aZ, aBlock, aMeta / 8 * 8 + (aMeta % 8 + 1) % 6, 0);
/* 56:46 */         aWorld.isRemote = false;
/* 57:47 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(0)), 1.0F, -1.0F, aX, aY, aZ);
/* 58:   */       }
/* 59:49 */       return true;
/* 60:   */     }
/* 61:51 */     return false;
/* 62:   */   }
/* 63:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Crowbar
 * JD-Core Version:    0.7.0.1
 */