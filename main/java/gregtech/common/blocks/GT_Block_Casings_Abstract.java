/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.GregTech_API;
/*   6:    */ import gregtech.api.items.GT_Generic_Block;
/*   7:    */ import gregtech.api.util.GT_LanguageManager;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Random;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.creativetab.CreativeTabs;
/*  14:    */ import net.minecraft.entity.Entity;
/*  15:    */ import net.minecraft.entity.EnumCreatureType;
/*  16:    */ import net.minecraft.init.Blocks;
/*  17:    */ import net.minecraft.item.Item;
/*  18:    */ import net.minecraft.item.ItemBlock;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.util.StatCollector;
/*  21:    */ import net.minecraft.world.IBlockAccess;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ 
/*  24:    */ public abstract class GT_Block_Casings_Abstract
/*  25:    */   extends GT_Generic_Block
/*  26:    */ {
/*  27:    */   public GT_Block_Casings_Abstract(Class<? extends ItemBlock> aItemClass, String aName, Material aMaterial)
/*  28:    */   {
/*  29: 29 */     super(aItemClass, aName, aMaterial);
/*  30: 30 */     setStepSound(soundTypeMetal);
/*  31: 31 */     setCreativeTab(GregTech_API.TAB_GREGTECH);
/*  32: 32 */     GregTech_API.registerMachineBlock(this, -1);
/*  33: 33 */     GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + 32767 + ".name", "Any Sub Block of this");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getHarvestTool(int aMeta)
/*  37:    */   {
/*  38: 38 */     return "wrench";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getHarvestLevel(int aMeta)
/*  42:    */   {
/*  43: 43 */     return 2;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/*  47:    */   {
/*  48: 48 */     return Blocks.iron_block.getBlockHardness(aWorld, aX, aY, aZ);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public float getExplosionResistance(Entity aTNT)
/*  52:    */   {
/*  53: 53 */     return Blocks.iron_block.getExplosionResistance(aTNT);
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected boolean canSilkHarvest()
/*  57:    */   {
/*  58: 58 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void onBlockAdded(World aWorld, int aX, int aY, int aZ)
/*  62:    */   {
/*  63: 61 */     if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
/*  64: 61 */       GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getUnlocalizedName()
/*  69:    */   {
/*  70: 62 */     return this.mUnlocalizedName;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getLocalizedName()
/*  74:    */   {
/*  75: 63 */     return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ)
/*  79:    */   {
/*  80: 64 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ)
/*  84:    */   {
/*  85: 65 */     return true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean renderAsNormalBlock()
/*  89:    */   {
/*  90: 66 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isOpaqueCube()
/*  94:    */   {
/*  95: 67 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void breakBlock(World aWorld, int aX, int aY, int aZ, Block aBlock, int aMetaData)
/*  99:    */   {
/* 100: 71 */     if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
/* 101: 71 */       GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
/* 106:    */   {
/* 107: 76 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int damageDropped(int par1)
/* 111:    */   {
/* 112: 81 */     return par1;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getDamageValue(World par1World, int par2, int par3, int par4)
/* 116:    */   {
/* 117: 86 */     return par1World.getBlockMetadata(par2, par3, par4);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int quantityDropped(Random par1Random)
/* 121:    */   {
/* 122: 91 */     return 1;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Item getItemDropped(int par1, Random par2Random, int par3)
/* 126:    */   {
/* 127: 96 */     return Item.getItemFromBlock(this);
/* 128:    */   }
/* 129:    */   
/* 130:    */   @SideOnly(Side.CLIENT)
/* 131:    */   public void registerBlockIcons(IIconRegister aIconRegister) {}
/* 132:    */   
/* 133:    */   @SideOnly(Side.CLIENT)
/* 134:    */   public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList)
/* 135:    */   {
/* 136:107 */     for (int i = 0; i < 16; i++) {
/* 137:107 */       aList.add(new ItemStack(aItem, 1, i));
/* 138:    */     }
/* 139:    */   }
/* 140:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Casings_Abstract
 * JD-Core Version:    0.7.0.1
 */