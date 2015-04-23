/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.common.GT_Proxy;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class GT_Tool_Drill_HV
/* 10:   */   extends GT_Tool_Drill_LV
/* 11:   */ {
/* 12:   */   public int getToolDamagePerBlockBreak()
/* 13:   */   {
/* 14:13 */     return GT_Mod.gregtechproxy.mHardRock ? 400 : 800;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getToolDamagePerDropConversion()
/* 18:   */   {
/* 19:18 */     return 1600;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getToolDamagePerContainerCraft()
/* 23:   */   {
/* 24:23 */     return 12800;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getToolDamagePerEntityAttack()
/* 28:   */   {
/* 29:28 */     return 3200;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getBaseQuality()
/* 33:   */   {
/* 34:33 */     return 1;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public float getBaseDamage()
/* 38:   */   {
/* 39:38 */     return 3.0F;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public float getSpeedMultiplier()
/* 43:   */   {
/* 44:43 */     return 9.0F;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public float getMaxDurabilityMultiplier()
/* 48:   */   {
/* 49:48 */     return 4.0F;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 53:   */   {
/* 54:53 */     return aIsToolHead ? gregtech.api.items.GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadDrill.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
/* 55:   */   }
/* 56:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Drill_HV
 * JD-Core Version:    0.7.0.1
 */