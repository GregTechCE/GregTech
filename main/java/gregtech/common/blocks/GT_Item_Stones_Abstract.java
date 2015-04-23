/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.util.GT_LanguageManager;
/*  5:   */ import java.util.List;
/*  6:   */ import net.minecraft.block.Block;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.item.ItemBlock;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class GT_Item_Stones_Abstract
/* 12:   */   extends ItemBlock
/* 13:   */ {
/* 14:   */   public GT_Item_Stones_Abstract(Block par1)
/* 15:   */   {
/* 16:15 */     super(par1);
/* 17:16 */     setMaxDamage(0);
/* 18:17 */     setHasSubtypes(true);
/* 19:18 */     setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getUnlocalizedName(ItemStack aStack)
/* 23:   */   {
/* 24:23 */     return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getMetadata(int aMeta)
/* 28:   */   {
/* 29:28 */     return aMeta;
/* 30:   */   }
/* 31:   */   
/* 32:31 */   private final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
/* 33:   */   
/* 34:   */   public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
/* 35:   */   {
/* 36:35 */     super.addInformation(aStack, aPlayer, aList, aF3_H);
/* 37:36 */     if (aStack.getItemDamage() % 8 >= 3) {
/* 38:36 */       aList.add(this.mNoMobsToolTip);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Stones_Abstract
 * JD-Core Version:    0.7.0.1
 */