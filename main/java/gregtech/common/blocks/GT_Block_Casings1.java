/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  7:   */ import gregtech.api.util.GT_LanguageManager;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ import net.minecraft.util.IIcon;
/* 10:   */ import net.minecraft.world.IBlockAccess;
/* 11:   */ 
/* 12:   */ public class GT_Block_Casings1
/* 13:   */   extends GT_Block_Casings_Abstract
/* 14:   */ {
/* 15:   */   public GT_Block_Casings1()
/* 16:   */   {
/* 17:14 */     super(GT_Item_Casings1.class, "gt.blockcasings", GT_Material_Casings.INSTANCE);
/* 18:15 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 19:15 */       Textures.BlockIcons.CASING_BLOCKS[i] = new GT_CopiedBlockTexture(this, 6, i);
/* 20:   */     }
/* 21:16 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "ULV Machine Casing");
/* 22:17 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "LV Machine Casing");
/* 23:18 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "MV Machine Casing");
/* 24:19 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "HV Machine Casing");
/* 25:20 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "EV Machine Casing");
/* 26:21 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "IV Machine Casing");
/* 27:22 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "LuV Machine Casing");
/* 28:23 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "ZPM Machine Casing");
/* 29:24 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "UV Machine Casing");
/* 30:25 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "MAX Machine Casing");
/* 31:26 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Bronze Plated Bricks");
/* 32:27 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Heat Proof Machine Casing");
/* 33:28 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cupronickel Coil Block");
/* 34:29 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Kanthal Coil Block");
/* 35:30 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Nichrome Coil Block");
/* 36:31 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Superconducting Coil Block");
/* 37:32 */     ItemList.Casing_ULV.set(new ItemStack(this, 1, 0));
/* 38:33 */     ItemList.Casing_LV.set(new ItemStack(this, 1, 1));
/* 39:34 */     ItemList.Casing_MV.set(new ItemStack(this, 1, 2));
/* 40:35 */     ItemList.Casing_HV.set(new ItemStack(this, 1, 3));
/* 41:36 */     ItemList.Casing_EV.set(new ItemStack(this, 1, 4));
/* 42:37 */     ItemList.Casing_IV.set(new ItemStack(this, 1, 5));
/* 43:38 */     ItemList.Casing_LuV.set(new ItemStack(this, 1, 6));
/* 44:39 */     ItemList.Casing_ZPM.set(new ItemStack(this, 1, 7));
/* 45:40 */     ItemList.Casing_UV.set(new ItemStack(this, 1, 8));
/* 46:41 */     ItemList.Casing_MAX.set(new ItemStack(this, 1, 9));
/* 47:42 */     ItemList.Casing_BronzePlatedBricks.set(new ItemStack(this, 1, 10));
/* 48:43 */     ItemList.Casing_HeatProof.set(new ItemStack(this, 1, 11));
/* 49:44 */     ItemList.Casing_Coil_Cupronickel.set(new ItemStack(this, 1, 12));
/* 50:45 */     ItemList.Casing_Coil_Kanthal.set(new ItemStack(this, 1, 13));
/* 51:46 */     ItemList.Casing_Coil_Nichrome.set(new ItemStack(this, 1, 14));
/* 52:47 */     ItemList.Casing_Coil_Superconductor.set(new ItemStack(this, 1, 15));
/* 53:   */   }
/* 54:   */   
/* 55:   */   public IIcon getIcon(int aSide, int aMeta)
/* 56:   */   {
/* 57:52 */     if ((aMeta >= 0) && (aMeta < 16))
/* 58:   */     {
/* 59:53 */       switch (aMeta)
/* 60:   */       {
/* 61:   */       case 10: 
/* 62:54 */         return Textures.BlockIcons.MACHINE_BRONZEPLATEDBRICKS.getIcon();
/* 63:   */       case 11: 
/* 64:55 */         return Textures.BlockIcons.MACHINE_HEATPROOFCASING.getIcon();
/* 65:   */       case 12: 
/* 66:56 */         return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL.getIcon();
/* 67:   */       case 13: 
/* 68:57 */         return Textures.BlockIcons.MACHINE_COIL_KANTHAL.getIcon();
/* 69:   */       case 14: 
/* 70:58 */         return Textures.BlockIcons.MACHINE_COIL_NICHROME.getIcon();
/* 71:   */       case 15: 
/* 72:59 */         return Textures.BlockIcons.MACHINE_COIL_SUPERCONDUCTOR.getIcon();
/* 73:   */       }
/* 74:61 */       if (aSide == 0) {
/* 75:61 */         return Textures.BlockIcons.MACHINECASINGS_BOTTOM[aMeta].getIcon();
/* 76:   */       }
/* 77:62 */       if (aSide == 1) {
/* 78:62 */         return Textures.BlockIcons.MACHINECASINGS_TOP[aMeta].getIcon();
/* 79:   */       }
/* 80:63 */       return Textures.BlockIcons.MACHINECASINGS_SIDE[aMeta].getIcon();
/* 81:   */     }
/* 82:66 */     return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 83:   */   }
/* 84:   */   
/* 85:   */   public int colorMultiplier(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 86:   */   {
/* 87:71 */     return aWorld.getBlockMetadata(aX, aY, aZ) > 9 ? super.colorMultiplier(aWorld, aX, aY, aZ) : gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[0] << 16 | gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[1] << 8 | gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[2];
/* 88:   */   }
/* 89:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Casings1
 * JD-Core Version:    0.7.0.1
 */