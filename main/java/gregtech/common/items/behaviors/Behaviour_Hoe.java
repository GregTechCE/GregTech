/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.eventhandler.*;
/*  5:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import gregtech.api.util.GT_LanguageManager;
/*  8:   */ import gregtech.api.util.GT_Utility;
/*  9:   */ import java.util.List;
/* 10:   */ import net.minecraft.block.Block;
/* 11:   */ import net.minecraft.block.Block.SoundType;
/* 12:   */ import net.minecraft.entity.player.EntityPlayer;
/* 13:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 14:   */ import net.minecraft.init.Blocks;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ import net.minecraft.world.World;
/* 17:   */ import net.minecraftforge.common.MinecraftForge;
/* 18:   */ import net.minecraftforge.event.entity.player.UseHoeEvent;
/* 19:   */ 
/* 20:   */ public class Behaviour_Hoe
/* 21:   */   extends Behaviour_None
/* 22:   */ {
/* 23:   */   private final int mCosts;
/* 24:   */   
/* 25:   */   public Behaviour_Hoe(int aCosts)
/* 26:   */   {
/* 27:23 */     this.mCosts = aCosts;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 31:   */   {
/* 32:28 */     if (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack)) {
/* 33:28 */       return false;
/* 34:   */     }
/* 35:29 */     UseHoeEvent event = new UseHoeEvent(aPlayer, aStack, aWorld, aX, aY, aZ);
/* 36:30 */     if (MinecraftForge.EVENT_BUS.post(event)) {
/* 37:30 */       return false;
/* 38:   */     }
/* 39:32 */     if (event.getResult() == Event.Result.ALLOW)
/* 40:   */     {
/* 41:33 */       if (!aPlayer.capabilities.isCreativeMode) {
/* 42:33 */         ((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts);
/* 43:   */       }
/* 44:34 */       return true;
/* 45:   */     }
/* 46:37 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/* 47:39 */     if ((aSide != 0) && (GT_Utility.isAirBlock(aWorld, aX, aY + 1, aZ)) && ((aBlock == Blocks.grass) || (aBlock == Blocks.dirt)))
/* 48:   */     {
/* 49:40 */       aWorld.playSoundEffect(aX + 0.5F, aY + 0.5F, aZ + 0.5F, Blocks.farmland.stepSound.getStepResourcePath(), (Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);
/* 50:41 */       if (aWorld.isRemote) {
/* 51:41 */         return true;
/* 52:   */       }
/* 53:42 */       aWorld.setBlock(aX, aY, aZ, Blocks.farmland);
/* 54:43 */       if (!aPlayer.capabilities.isCreativeMode) {
/* 55:43 */         ((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts);
/* 56:   */       }
/* 57:44 */       return true;
/* 58:   */     }
/* 59:46 */     return false;
/* 60:   */   }
/* 61:   */   
/* 62:49 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.hoe", "Can till Dirt");
/* 63:   */   
/* 64:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 65:   */   {
/* 66:53 */     aList.add(this.mTooltip);
/* 67:54 */     return aList;
/* 68:   */   }
/* 69:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Hoe
 * JD-Core Version:    0.7.0.1
 */