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
/* 11:   */ public abstract class GT_Item_Casings_Abstract
/* 12:   */   extends ItemBlock
/* 13:   */ {
/* 14:   */   public GT_Item_Casings_Abstract(Block par1)
/* 15:   */   {
/* 16:15 */     super(par1);
/* 17:16 */     setMaxDamage(0);
/* 18:17 */     setHasSubtypes(true);
/* 19:18 */     setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getMetadata(int aMeta)
/* 23:   */   {
/* 24:23 */     return aMeta;
/* 25:   */   }
/* 26:   */   
/* 27:26 */   protected final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");
/* 28:27 */   protected final String mNoTileEntityToolTip = GT_LanguageManager.addStringLocalization("gt.notileentityinthisblock", "This is NOT a TileEntity!");
/* 29:28 */   protected final String mCoil01Tooltip = GT_LanguageManager.addStringLocalization("gt.coil01tooltip", "Base Heating Capacity = 1800 Kelvin");
/* 30:29 */   protected final String mCoil02Tooltip = GT_LanguageManager.addStringLocalization("gt.coil02tooltip", "Base Heating Capacity = 2700 Kelvin");
/* 31:30 */   protected final String mCoil03Tooltip = GT_LanguageManager.addStringLocalization("gt.coil03tooltip", "Base Heating Capacity = 3600 Kelvin");
/* 32:31 */   protected final String mBlastProofTooltip = GT_LanguageManager.addStringLocalization("gt.blastprooftooltip", "This Block is Blast Proof");
/* 33:   */   
/* 34:   */   public String getUnlocalizedName(ItemStack aStack)
/* 35:   */   {
/* 36:35 */     return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
/* 40:   */   {
/* 41:40 */     super.addInformation(aStack, aPlayer, aList, aF3_H);
/* 42:41 */     aList.add(this.mNoMobsToolTip);
/* 43:42 */     aList.add(this.mNoTileEntityToolTip);
/* 44:   */   }
/* 45:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Casings_Abstract
 * JD-Core Version:    0.7.0.1
 */