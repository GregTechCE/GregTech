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
/* 10:   */ public class GT_Block_Casings3
/* 11:   */   extends GT_Block_Casings_Abstract
/* 12:   */ {
/* 13:   */   public GT_Block_Casings3()
/* 14:   */   {
/* 15:12 */     super(GT_Item_Casings3.class, "gt.blockcasings3", GT_Material_Casings.INSTANCE);
/* 16:13 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 17:13 */       Textures.BlockIcons.CASING_BLOCKS[(i + 32)] = new GT_CopiedBlockTexture(this, 6, i);
/* 18:   */     }
/* 19:14 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Yellow Stripes Block");
/* 20:15 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Yellow Stripes Block");
/* 21:16 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Radioactive Hazard Sign Block");
/* 22:17 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Bio Hazard Sign Block");
/* 23:18 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Explosion Hazard Sign Block");
/* 24:19 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fire Hazard Sign Block");
/* 25:20 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Acid Hazard Sign Block");
/* 26:21 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Magic Hazard Sign Block");
/* 27:22 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Frost Hazard Sign Block");
/* 28:23 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Noise Hazard Sign Block");
/* 29:24 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Grate Casing");
/* 30:25 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Vent Casing");
/* 31:26 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Radiation Proof Casing");
/* 32:27 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Bronze Firebox Casing");
/* 33:28 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Steel Firebox Casing");
/* 34:29 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Tungstensteel Firebox Casing");
/* 35:30 */     ItemList.Casing_Stripes_A.set(new ItemStack(this, 1, 0));
/* 36:31 */     ItemList.Casing_Stripes_B.set(new ItemStack(this, 1, 1));
/* 37:32 */     ItemList.Casing_RadioactiveHazard.set(new ItemStack(this, 1, 2));
/* 38:33 */     ItemList.Casing_BioHazard.set(new ItemStack(this, 1, 3));
/* 39:34 */     ItemList.Casing_ExplosionHazard.set(new ItemStack(this, 1, 4));
/* 40:35 */     ItemList.Casing_FireHazard.set(new ItemStack(this, 1, 5));
/* 41:36 */     ItemList.Casing_AcidHazard.set(new ItemStack(this, 1, 6));
/* 42:37 */     ItemList.Casing_MagicHazard.set(new ItemStack(this, 1, 7));
/* 43:38 */     ItemList.Casing_FrostHazard.set(new ItemStack(this, 1, 8));
/* 44:39 */     ItemList.Casing_NoiseHazard.set(new ItemStack(this, 1, 9));
/* 45:40 */     ItemList.Casing_Grate.set(new ItemStack(this, 1, 10));
/* 46:41 */     ItemList.Casing_Vent.set(new ItemStack(this, 1, 11));
/* 47:42 */     ItemList.Casing_RadiationProof.set(new ItemStack(this, 1, 12));
/* 48:43 */     ItemList.Casing_Firebox_Bronze.set(new ItemStack(this, 1, 13));
/* 49:44 */     ItemList.Casing_Firebox_Steel.set(new ItemStack(this, 1, 14));
/* 50:45 */     ItemList.Casing_Firebox_TungstenSteel.set(new ItemStack(this, 1, 15));
/* 51:   */   }
/* 52:   */   
/* 53:   */   public IIcon getIcon(int aSide, int aMeta)
/* 54:   */   {
/* 55:50 */     switch (aMeta)
/* 56:   */     {
/* 57:   */     case 0: 
/* 58:51 */       return Textures.BlockIcons.MACHINE_CASING_STRIPES_A.getIcon();
/* 59:   */     case 1: 
/* 60:52 */       return Textures.BlockIcons.MACHINE_CASING_STRIPES_B.getIcon();
/* 61:   */     case 2: 
/* 62:53 */       return Textures.BlockIcons.MACHINE_CASING_RADIOACTIVEHAZARD.getIcon();
/* 63:   */     case 3: 
/* 64:54 */       return Textures.BlockIcons.MACHINE_CASING_BIOHAZARD.getIcon();
/* 65:   */     case 4: 
/* 66:55 */       return Textures.BlockIcons.MACHINE_CASING_EXPLOSIONHAZARD.getIcon();
/* 67:   */     case 5: 
/* 68:56 */       return Textures.BlockIcons.MACHINE_CASING_FIREHAZARD.getIcon();
/* 69:   */     case 6: 
/* 70:57 */       return Textures.BlockIcons.MACHINE_CASING_ACIDHAZARD.getIcon();
/* 71:   */     case 7: 
/* 72:58 */       return Textures.BlockIcons.MACHINE_CASING_MAGICHAZARD.getIcon();
/* 73:   */     case 8: 
/* 74:59 */       return Textures.BlockIcons.MACHINE_CASING_FROSTHAZARD.getIcon();
/* 75:   */     case 9: 
/* 76:60 */       return Textures.BlockIcons.MACHINE_CASING_NOISEHAZARD.getIcon();
/* 77:   */     case 10: 
/* 78:61 */       return Textures.BlockIcons.MACHINE_CASING_GRATE.getIcon();
/* 79:   */     case 11: 
/* 80:62 */       return Textures.BlockIcons.MACHINE_CASING_VENT.getIcon();
/* 81:   */     case 12: 
/* 82:63 */       return Textures.BlockIcons.MACHINE_CASING_RADIATIONPROOF.getIcon();
/* 83:   */     case 13: 
/* 84:64 */       return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_BRONZE.getIcon() : Textures.BlockIcons.MACHINE_BRONZEPLATEDBRICKS.getIcon();
/* 85:   */     case 14: 
/* 86:65 */       return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_STEEL.getIcon() : Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 87:   */     case 15: 
/* 88:66 */       return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TUNGSTENSTEEL.getIcon() : Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
/* 89:   */     }
/* 90:68 */     return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
/* 91:   */   }
/* 92:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Casings3
 * JD-Core Version:    0.7.0.1
 */