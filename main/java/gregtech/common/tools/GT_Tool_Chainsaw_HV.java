/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.ItemIcons;
/*  4:   */ import gregtech.api.interfaces.IIconContainer;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class GT_Tool_Chainsaw_HV
/*  8:   */   extends GT_Tool_Chainsaw_LV
/*  9:   */ {
/* 10:   */   public int getToolDamagePerBlockBreak()
/* 11:   */   {
/* 12:12 */     return 800;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public int getToolDamagePerDropConversion()
/* 16:   */   {
/* 17:17 */     return 1600;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getToolDamagePerContainerCraft()
/* 21:   */   {
/* 22:22 */     return 12800;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getToolDamagePerEntityAttack()
/* 26:   */   {
/* 27:27 */     return 3200;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getBaseQuality()
/* 31:   */   {
/* 32:32 */     return 1;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public float getBaseDamage()
/* 36:   */   {
/* 37:37 */     return 4.0F;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public float getSpeedMultiplier()
/* 41:   */   {
/* 42:42 */     return 4.0F;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public float getMaxDurabilityMultiplier()
/* 46:   */   {
/* 47:47 */     return 4.0F;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 51:   */   {
/* 52:52 */     return aIsToolHead ? gregtech.api.items.GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
/* 53:   */   }
/* 54:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Chainsaw_HV
 * JD-Core Version:    0.7.0.1
 */