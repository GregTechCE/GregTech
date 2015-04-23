/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class GT_Tool_Wrench_HV
/* 10:   */   extends GT_Tool_Wrench_LV
/* 11:   */ {
/* 12:   */   public int getToolDamagePerBlockBreak()
/* 13:   */   {
/* 14:12 */     return 800;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getToolDamagePerDropConversion()
/* 18:   */   {
/* 19:17 */     return 1600;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getToolDamagePerContainerCraft()
/* 23:   */   {
/* 24:22 */     return 12800;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getToolDamagePerEntityAttack()
/* 28:   */   {
/* 29:27 */     return 3200;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getBaseQuality()
/* 33:   */   {
/* 34:32 */     return 1;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public float getBaseDamage()
/* 38:   */   {
/* 39:37 */     return 2.0F;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public float getSpeedMultiplier()
/* 43:   */   {
/* 44:42 */     return 4.0F;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public float getMaxDurabilityMultiplier()
/* 48:   */   {
/* 49:47 */     return 4.0F;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean canBlock()
/* 53:   */   {
/* 54:52 */     return false;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 58:   */   {
/* 59:57 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_HV;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 63:   */   {
/* 64:62 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 65:   */   }
/* 66:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Wrench_HV
 * JD-Core Version:    0.7.0.1
 */