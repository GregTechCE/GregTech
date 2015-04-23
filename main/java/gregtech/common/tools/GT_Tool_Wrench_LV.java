/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import net.minecraft.entity.Entity;
/*  8:   */ import net.minecraft.entity.player.EntityPlayer;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class GT_Tool_Wrench_LV
/* 12:   */   extends GT_Tool_Wrench
/* 13:   */ {
/* 14:   */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/* 15:   */   {
/* 16:14 */     return aOriginalDamage;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getToolDamagePerBlockBreak()
/* 20:   */   {
/* 21:19 */     return 50;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getToolDamagePerDropConversion()
/* 25:   */   {
/* 26:24 */     return 100;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getToolDamagePerContainerCraft()
/* 30:   */   {
/* 31:29 */     return 800;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getToolDamagePerEntityAttack()
/* 35:   */   {
/* 36:34 */     return 200;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getBaseQuality()
/* 40:   */   {
/* 41:39 */     return 0;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public float getBaseDamage()
/* 45:   */   {
/* 46:44 */     return 1.0F;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public float getSpeedMultiplier()
/* 50:   */   {
/* 51:49 */     return 2.0F;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public float getMaxDurabilityMultiplier()
/* 55:   */   {
/* 56:54 */     return 1.0F;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public boolean canBlock()
/* 60:   */   {
/* 61:59 */     return false;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 65:   */   {
/* 66:64 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadWrench.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 70:   */   {
/* 71:69 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 72:   */   }
/* 73:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Wrench_LV
 * JD-Core Version:    0.7.0.1
 */