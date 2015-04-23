/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.util.IIcon;
/*  9:   */ 
/* 10:   */ public class GT_Block_Casings4
/* 11:   */   extends GT_Block_Casings_Abstract
/* 12:   */ {
/* 13:   */   public GT_Block_Casings4()
/* 14:   */   {
/* 15:12 */     super(GT_Item_Casings4.class, "gt.blockcasings4", GT_Material_Casings.INSTANCE);
/* 16:13 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 17:13 */       Textures.BlockIcons.CASING_BLOCKS[(i + 48)] = new GT_CopiedBlockTexture(this, 6, i);
/* 18:   */     }
/* 19:14 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Robust Tungstensteel Casing");
/* 20:15 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Clean Stainless Steel Casing");
/* 21:16 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Stable Titanium Casing");
/* 22:17 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Titanium Firebox Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Fusion Casing");
/* 23:   */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Fusion Coil");
/* 24:   */ 	GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Fusion Casing MK II");
/* 25:   */ 
/* 35:30 */     ItemList.Casing_RobustTungstenSteel.set(new ItemStack(this, 1, 0));
/* 36:31 */     ItemList.Casing_CleanStainlessSteel.set(new ItemStack(this, 1, 1));
/* 37:32 */     ItemList.Casing_StableTitanium.set(new ItemStack(this, 1, 2));
/* 38:33 */     ItemList.Casing_Firebox_Titanium.set(new ItemStack(this, 1, 3));
				ItemList.Casing_Fusion.set(new ItemStack(this,1,6));
				ItemList.Casing_Fusion2.set(new ItemStack(this,1,8));
				ItemList.Casing_Fusion_Coil.set(new ItemStack(this,1,7));
/* 39:   */   }
/* 40:   */   
/* 41:   */   public IIcon getIcon(int aSide, int aMeta)
/* 42:   */   {
/* 43:50 */     switch (aMeta)
/* 44:   */     {
/* 45:   */     case 0: 
/* 46:51 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 47:   */     case 1: 
/* 48:52 */       return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getIcon();
/* 49:   */     case 2: 
/* 50:53 */       return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
/* 51:   */     case 3: 
/* 52:54 */       return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TITANIUM.getIcon() : Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
/* 53:   */     case 4: 
/* 54:55 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW.getIcon();
/* 55:   */     case 5: 
/* 56:56 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS.getIcon();
/* 57:   */     case 6: 
/* 58:57 */       return Textures.BlockIcons.MACHINE_CASING_FUSION.getIcon();
/* 59:   */     case 7: 
/* 60:58 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_COIL.getIcon();
/* 61:   */     case 8: 
/* 62:59 */       return Textures.BlockIcons.MACHINE_CASING_FUSION_2.getIcon();
/* 63:   */     case 9: 
/* 64:60 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 65:   */     case 10: 
/* 66:61 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 67:   */     case 11: 
/* 68:62 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 69:   */     case 12: 
/* 70:63 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 71:   */     case 13: 
/* 72:64 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 73:   */     case 14: 
/* 74:65 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 75:   */     case 15: 
/* 76:66 */       return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 77:   */     }
/* 78:68 */     return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 79:   */   }
/* 80:   */ }