/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class GT_Tool_Screwdriver_LV
/* 10:   */   extends GT_Tool_Screwdriver
/* 11:   */ {
/* 12:   */   public float getMaxDurabilityMultiplier()
/* 13:   */   {
/* 14:12 */     return 1.0F;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getToolDamagePerContainerCraft()
/* 18:   */   {
/* 19:17 */     return 200;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 23:   */   {
/* 24:22 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadScrewdriver.mTextureIndex] : Textures.ItemIcons.HANDLE_ELECTRIC_SCREWDRIVER;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 28:   */   {
/* 29:27 */     return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 30:   */   }
/* 31:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Screwdriver_LV
 * JD-Core Version:    0.7.0.1
 */