/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.entity.Entity;
/* 10:   */ import net.minecraft.entity.boss.EntityWither;
/* 11:   */ import net.minecraft.init.Blocks;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraft.util.IIcon;
/* 14:   */ import net.minecraft.world.IBlockAccess;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class GT_Block_Granites
/* 18:   */   extends GT_Block_Stones_Abstract
/* 19:   */ {
/* 20:   */   public GT_Block_Granites()
/* 21:   */   {
/* 22:18 */     super(GT_Item_Granites.class, "gt.blockgranites");
/* 23:19 */     setResistance(60.0F);
/* 24:20 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Black Granite");
/* 25:21 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Black Granite Cobblestone");
/* 26:22 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Black Granite Cobblestone");
/* 27:23 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Black Granite Bricks");
/* 28:24 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Black Granite Bricks");
/* 29:25 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Black Granite Bricks");
/* 30:26 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Black Granite");
/* 31:27 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Black Granite");
/* 32:28 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Red Granite");
/* 33:29 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Red Granite Cobblestone");
/* 34:30 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Red Granite Cobblestone");
/* 35:31 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Red Granite Bricks");
/* 36:32 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Red Granite Bricks");
/* 37:33 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Red Granite Bricks");
/* 38:34 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Red Granite");
/* 39:35 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Red Granite");
/* 40:36 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 0));
/* 41:37 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 1));
/* 42:38 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 2));
/* 43:39 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 3));
/* 44:40 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 4));
/* 45:41 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 5));
/* 46:42 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 6));
/* 47:43 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(this, 1, 7));
/* 48:44 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 8));
/* 49:45 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 9));
/* 50:46 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 10));
/* 51:47 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 11));
/* 52:48 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 12));
/* 53:49 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 13));
/* 54:50 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 14));
/* 55:51 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(this, 1, 15));
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int getHarvestLevel(int aMeta)
/* 59:   */   {
/* 60:56 */     return 3;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/* 64:   */   {
/* 65:61 */     return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ) * 3.0F;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public IIcon getIcon(int aSide, int aMeta)
/* 69:   */   {
/* 70:64 */     if ((aMeta >= 0) && (aMeta < 16)) {
/* 71:64 */       return gregtech.api.enums.Textures.BlockIcons.GRANITES[aMeta].getIcon();
/* 72:   */     }
/* 73:64 */     return gregtech.api.enums.Textures.BlockIcons.GRANITES[0].getIcon();
/* 74:   */   }
/* 75:   */   
/* 76:   */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/* 77:   */   {
/* 78:68 */     return !(entity instanceof EntityWither);
/* 79:   */   }
/* 80:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Granites
 * JD-Core Version:    0.7.0.1
 */