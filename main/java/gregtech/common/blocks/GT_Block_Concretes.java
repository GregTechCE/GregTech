/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.block.BlockLiquid;
/* 10:   */ import net.minecraft.entity.Entity;
/* 11:   */ import net.minecraft.entity.EntityLivingBase;
/* 12:   */ import net.minecraft.init.Blocks;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.AxisAlignedBB;
/* 15:   */ import net.minecraft.util.IIcon;
/* 16:   */ import net.minecraft.world.World;
/* 17:   */ import net.minecraftforge.fluids.IFluidBlock;
/* 18:   */ 
/* 19:   */ public class GT_Block_Concretes
/* 20:   */   extends GT_Block_Stones_Abstract
/* 21:   */ {
/* 22:   */   public GT_Block_Concretes()
/* 23:   */   {
/* 24:21 */     super(GT_Item_Concretes.class, "gt.blockconcretes");
/* 25:22 */     setResistance(20.0F);
/* 26:23 */     this.slipperiness = 0.9F;
/* 27:24 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Dark Concrete");
/* 28:25 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Dark Concrete Cobblestone");
/* 29:26 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Dark Concrete Cobblestone");
/* 30:27 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Dark Concrete Bricks");
/* 31:28 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Dark Concrete Bricks");
/* 32:29 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Dark Concrete Bricks");
/* 33:30 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Dark Concrete");
/* 34:31 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Dark Concrete");
/* 35:32 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Light Concrete");
/* 36:33 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Light Concrete Cobblestone");
/* 37:34 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Light Concrete Cobblestone");
/* 38:35 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Light Concrete Bricks");
/* 39:36 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Light Concrete Bricks");
/* 40:37 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Light Concrete Bricks");
/* 41:38 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Light Concrete");
/* 42:39 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Light Concrete");
/* 43:40 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 0));
/* 44:41 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 1));
/* 45:42 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 2));
/* 46:43 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 3));
/* 47:44 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 4));
/* 48:45 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 5));
/* 49:46 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 6));
/* 50:47 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 7));
/* 51:48 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 8));
/* 52:49 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 9));
/* 53:50 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 10));
/* 54:51 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 11));
/* 55:52 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 12));
/* 56:53 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 13));
/* 57:54 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 14));
/* 58:55 */     GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 15));
/* 59:   */   }
/* 60:   */   
/* 61:   */   public int getHarvestLevel(int aMeta)
/* 62:   */   {
/* 63:60 */     return 1;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/* 67:   */   {
/* 68:65 */     return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ);
/* 69:   */   }
/* 70:   */   
/* 71:   */   public IIcon getIcon(int aSide, int aMeta)
/* 72:   */   {
/* 73:68 */     if ((aMeta >= 0) && (aMeta < 16)) {
/* 74:68 */       return gregtech.api.enums.Textures.BlockIcons.CONCRETES[aMeta].getIcon();
/* 75:   */     }
/* 76:68 */     return gregtech.api.enums.Textures.BlockIcons.CONCRETES[0].getIcon();
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity aEntity)
/* 80:   */   {
/* 81:72 */     Block tBlock = aWorld.getBlock(aX, aY + 1, aZ);
/* 82:73 */     if (((aEntity instanceof EntityLivingBase)) && (!(tBlock instanceof IFluidBlock)) && (!(tBlock instanceof BlockLiquid)) && (aEntity.onGround) && (!aEntity.isInWater()) && (!aEntity.isWet())) {
/* 83:74 */       if (aEntity.isSneaking())
/* 84:   */       {
/* 85:75 */         aEntity.motionX *= 0.8999999761581421D;
/* 86:76 */         aEntity.motionZ *= 0.8999999761581421D;
/* 87:   */       }
/* 88:   */       else
/* 89:   */       {
/* 90:78 */         aEntity.motionX *= 1.100000023841858D;
/* 91:79 */         aEntity.motionZ *= 1.100000023841858D;
/* 92:   */       }
/* 93:   */     }
/* 94:   */   }
/* 95:   */   
/* 96:   */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ)
/* 97:   */   {
/* 98:86 */     Block tBlock = aWorld.getBlock(aX, aY + 1, aZ);
/* 99:87 */     if (((tBlock instanceof IFluidBlock)) || ((tBlock instanceof BlockLiquid))) {
/* :0:87 */       return super.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
/* :1:   */     }
/* :2:88 */     return AxisAlignedBB.getBoundingBox(aX, aY, aZ, aX + 1, aY + 0.875D, aZ + 1);
/* :3:   */   }
/* :4:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Concretes
 * JD-Core Version:    0.7.0.1
 */