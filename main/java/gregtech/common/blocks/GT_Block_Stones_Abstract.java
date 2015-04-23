/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.GregTech_API;
/*   6:    */ import gregtech.api.enums.GT_Values;
/*   7:    */ import gregtech.api.enums.ItemList;
/*   8:    */ import gregtech.api.enums.Materials;
/*   9:    */ import gregtech.api.enums.OreDictNames;
/*  10:    */ import gregtech.api.enums.OrePrefixes;
/*  11:    */ import gregtech.api.interfaces.IIconContainer;
/*  12:    */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  13:    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  14:    */ import gregtech.api.items.GT_Generic_Block;
/*  15:    */ import gregtech.api.util.GT_ModHandler;
/*  16:    */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  17:    */ import gregtech.api.util.GT_Utility;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Random;
/*  20:    */ import net.minecraft.block.Block;
/*  21:    */ import net.minecraft.block.material.Material;
/*  22:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  23:    */ import net.minecraft.creativetab.CreativeTabs;
/*  24:    */ import net.minecraft.entity.EnumCreatureType;
/*  25:    */ import net.minecraft.init.Blocks;
/*  26:    */ import net.minecraft.item.Item;
/*  27:    */ import net.minecraft.item.ItemBlock;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.util.IIcon;
/*  30:    */ import net.minecraft.util.StatCollector;
/*  31:    */ import net.minecraft.world.IBlockAccess;
/*  32:    */ import net.minecraft.world.World;
/*  33:    */ 
/*  34:    */ public class GT_Block_Stones_Abstract
/*  35:    */   extends GT_Generic_Block
/*  36:    */   implements IOreRecipeRegistrator
/*  37:    */ {
/*  38:    */   public GT_Block_Stones_Abstract(Class<? extends ItemBlock> aItemClass, String aName)
/*  39:    */   {
/*  40: 31 */     super(aItemClass, aName, Material.rock);
/*  41: 32 */     OrePrefixes.crafting.add(this);
/*  42: 33 */     setStepSound(soundTypeStone);
/*  43: 34 */     setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
/*  44: 35 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 7));
/*  45: 36 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0));
/*  46: 37 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 2), new ItemStack(this, 1, 0));
/*  47: 38 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 0));
/*  48: 39 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 4), new ItemStack(this, 1, 0));
/*  49: 40 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 5), new ItemStack(this, 1, 0));
/*  50: 41 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 6), new ItemStack(this, 1, 0));
/*  51: 42 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 7), new ItemStack(this, 1, 0));
/*  52: 43 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 15));
/*  53: 44 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 9), new ItemStack(this, 1, 8));
/*  54: 45 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 10), new ItemStack(this, 1, 8));
/*  55: 46 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 8));
/*  56: 47 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 12), new ItemStack(this, 1, 8));
/*  57: 48 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 13), new ItemStack(this, 1, 8));
/*  58: 49 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 14), new ItemStack(this, 1, 8));
/*  59: 50 */     GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 15), new ItemStack(this, 1, 8));
/*  60: 51 */     GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), new ItemStack(this, 1, 3), 50, 4);
/*  61: 52 */     GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 8), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), new ItemStack(this, 1, 11), 50, 4);
/*  62: 53 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "f", "X", Character.valueOf('X'), new ItemStack(this, 1, 7) });
/*  63: 54 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 14), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "f", "X", Character.valueOf('X'), new ItemStack(this, 1, 15) });
/*  64: 55 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "h", "X", Character.valueOf('X'), new ItemStack(this, 1, 3) });
/*  65: 56 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 12), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "h", "X", Character.valueOf('X'), new ItemStack(this, 1, 11) });
/*  66: 57 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "h", "X", Character.valueOf('X'), new ItemStack(this, 1, 0) });
/*  67: 58 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 9), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "h", "X", Character.valueOf('X'), new ItemStack(this, 1, 8) });
/*  68: 59 */     GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 4), 16, 10);
/*  69: 60 */     GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 12), 16, 10);
/*  70: 61 */     GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 16, 10);
/*  71: 62 */     GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 9), 16, 10);
/*  72: 63 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "XX", "XX", Character.valueOf('X'), new ItemStack(this, 4, 0) });
/*  73: 64 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "XX", "XX", Character.valueOf('X'), new ItemStack(this, 4, 8) });
/*  74: 65 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "XX", "XX", Character.valueOf('X'), new ItemStack(this, 4, 7) });
/*  75: 66 */     GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "XX", "XX", Character.valueOf('X'), new ItemStack(this, 4, 15) });
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*  79:    */   {
/*  80: 71 */     if (aOreDictName.equals(OreDictNames.craftingLensWhite.toString()))
/*  81:    */     {
/*  82: 72 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 7), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(this, 1, 6), 50, 16);
/*  83: 73 */       GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 15), GT_Utility.copyAmount(0L, new Object[] { aStack }), new ItemStack(this, 1, 14), 50, 16);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getHarvestTool(int aMeta)
/*  88:    */   {
/*  89: 79 */     return "pickaxe";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getHarvestLevel(int aMeta)
/*  93:    */   {
/*  94: 84 */     return 1;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/*  98:    */   {
/*  99: 89 */     return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ) * 3.0F;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getUnlocalizedName()
/* 103:    */   {
/* 104: 92 */     return this.mUnlocalizedName;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getLocalizedName()
/* 108:    */   {
/* 109: 93 */     return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 113:    */   {
/* 114: 94 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 118:    */   {
/* 119: 95 */     return true;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean renderAsNormalBlock()
/* 123:    */   {
/* 124: 96 */     return true;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isOpaqueCube()
/* 128:    */   {
/* 129: 97 */     return true;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public IIcon getIcon(int aSide, int aMeta)
/* 133:    */   {
/* 134: 98 */     if ((aMeta >= 0) && (aMeta < 16)) {
/* 135: 98 */       return gregtech.api.enums.Textures.BlockIcons.GRANITES[aMeta].getIcon();
/* 136:    */     }
/* 137: 98 */     return null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
/* 141:    */   {
/* 142:102 */     return world.getBlockMetadata(x, y, z) % 8 < 3;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int damageDropped(int par1)
/* 146:    */   {
/* 147:107 */     return par1 % 8 == 0 ? par1 + 1 : par1;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int getDamageValue(World par1World, int par2, int par3, int par4)
/* 151:    */   {
/* 152:112 */     return par1World.getBlockMetadata(par2, par3, par4);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int quantityDropped(Random par1Random)
/* 156:    */   {
/* 157:117 */     return 1;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Item getItemDropped(int par1, Random par2Random, int par3)
/* 161:    */   {
/* 162:122 */     return Item.getItemFromBlock(this);
/* 163:    */   }
/* 164:    */   
/* 165:    */   @SideOnly(Side.CLIENT)
/* 166:    */   public void registerBlockIcons(IIconRegister aIconRegister) {}
/* 167:    */   
/* 168:    */   @SideOnly(Side.CLIENT)
/* 169:    */   public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList)
/* 170:    */   {
/* 171:133 */     for (int i = 0; i < 16; i++) {
/* 172:133 */       aList.add(new ItemStack(aItem, 1, i));
/* 173:    */     }
/* 174:    */   }
/* 175:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Stones_Abstract
 * JD-Core Version:    0.7.0.1
 */