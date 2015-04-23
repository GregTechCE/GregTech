/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  4:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  5:   */ import gregtech.api.util.GT_LanguageManager;
/*  6:   */ import ic2.api.crops.ICropTile;
/*  7:   */ import java.util.List;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.tileentity.TileEntity;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class Behaviour_Sense
/* 15:   */   extends Behaviour_None
/* 16:   */ {
/* 17:   */   private final int mCosts;
/* 18:   */   
/* 19:   */   public Behaviour_Sense(int aCosts)
/* 20:   */   {
/* 21:19 */     this.mCosts = aCosts;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 25:   */   {
/* 26:24 */     if (aWorld.isRemote) {
/* 27:24 */       return false;
/* 28:   */     }
/* 29:25 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 30:26 */     if ((tTileEntity instanceof ICropTile))
/* 31:   */     {
/* 32:27 */       for (int i = -1; i < 2; i++) {
/* 33:27 */         for (int j = -1; j < 2; j++) {
/* 34:27 */           for (int k = -1; k < 2; k++) {
/* 35:27 */             if ((aStack.stackSize > 0) && (((tTileEntity = aWorld.getTileEntity(aX + i, aY + j, aZ + k)) instanceof ICropTile)) && (((ICropTile)tTileEntity).harvest(true)) && (!aPlayer.capabilities.isCreativeMode)) {
/* 36:27 */               ((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts);
/* 37:   */             }
/* 38:   */           }
/* 39:   */         }
/* 40:   */       }
/* 41:28 */       return true;
/* 42:   */     }
/* 43:30 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:33 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.sense", "Rightclick to harvest Crop Sticks");
/* 47:   */   
/* 48:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 49:   */   {
/* 50:37 */     aList.add(this.mTooltip);
/* 51:38 */     return aList;
/* 52:   */   }
/* 53:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Sense
 * JD-Core Version:    0.7.0.1
 */