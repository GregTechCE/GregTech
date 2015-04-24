/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.GregTech_API;
/*   6:    */ import gregtech.api.enums.Materials;
/*   7:    */ import gregtech.api.enums.OrePrefixes;
/*   8:    */ import gregtech.api.items.GT_Generic_Block;
/*   9:    */ import gregtech.api.util.GT_LanguageManager;
/*  10:    */ import gregtech.api.util.GT_ModHandler;
/*  11:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  12:    */ import gregtech.common.render.GT_Renderer_Block;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import net.minecraft.block.Block;
/*  16:    */ import net.minecraft.block.ITileEntityProvider;
/*  17:    */ import net.minecraft.block.material.Material;
/*  18:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  19:    */ import net.minecraft.creativetab.CreativeTabs;
/*  20:    */ import net.minecraft.entity.Entity;
/*  21:    */ import net.minecraft.entity.boss.EntityDragon;
/*  22:    */ import net.minecraft.init.Blocks;
/*  23:    */ import net.minecraft.item.Item;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.tileentity.TileEntity;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.util.StatCollector;
/*  28:    */ import net.minecraft.world.IBlockAccess;
/*  29:    */ import net.minecraft.world.World;
/*  30:    */ 
/*  31:    */ public class GT_Block_Ores
/*  32:    */   extends GT_Generic_Block
/*  33:    */   implements ITileEntityProvider
/*  34:    */ {
/*  35: 35 */   public static ThreadLocal<GT_TileEntity_Ores> mTemporaryTileEntity = new ThreadLocal();
/*  36:    */   
/*  37:    */   public GT_Block_Ores()
/*  38:    */   {
/*  39: 38 */     super(GT_Item_Ores.class, "gt.blockores", Material.rock);
/*  40: 39 */     this.isBlockContainer = true;
/*  41: 40 */     setStepSound(soundTypeStone);
/*  42: 41 */     setCreativeTab(GregTech_API.TAB_GREGTECH_ORES);
/*  43: 42 */     for (int i = 0; i < 16; i++) {
/*  44: 42 */       GT_ModHandler.addValuableOre(this, i, 1);
/*  45:    */     }
/*  46: 43 */     for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
/*  47: 43 */       if (GregTech_API.sGeneratedMaterials[i] != null)
/*  48:    */       {
/*  49: 44 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  50: 45 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 1000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  51: 46 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 2000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  52: 47 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 3000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  53: 48 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 4000) + ".name", getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  54: 49 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 16000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  55: 50 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 17000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  56: 51 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 18000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  57: 52 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 19000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  58: 53 */         GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + (i + 20000) + ".name", "Small " + getLocalizedName(GregTech_API.sGeneratedMaterials[i]));
/*  59: 54 */         if ((GregTech_API.sGeneratedMaterials[i].mTypes & 0x8) != 0)
/*  60:    */         {
/*  61: 55 */           GT_OreDictUnificator.registerOre(OrePrefixes.ore.get(GregTech_API.sGeneratedMaterials[i]), new ItemStack(this, 1, i));
/*  62: 56 */           GT_OreDictUnificator.registerOre(OrePrefixes.oreNetherrack.get(GregTech_API.sGeneratedMaterials[i]), new ItemStack(this, 1, i + 1000));
/*  63: 57 */           GT_OreDictUnificator.registerOre(OrePrefixes.oreEndstone.get(GregTech_API.sGeneratedMaterials[i]), new ItemStack(this, 1, i + 2000));
/*  64: 58 */           GT_OreDictUnificator.registerOre(OrePrefixes.oreBlackgranite.get(GregTech_API.sGeneratedMaterials[i]), new ItemStack(this, 1, i + 3000));
/*  65: 59 */           GT_OreDictUnificator.registerOre(OrePrefixes.oreRedgranite.get(GregTech_API.sGeneratedMaterials[i]), new ItemStack(this, 1, i + 4000));
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71: 64 */   public static boolean FUCKING_LOCK = false;
/*  72:    */   
/*  73:    */   public void onNeighborChange(IBlockAccess aWorld, int aX, int aY, int aZ, int aTileX, int aTileY, int aTileZ)
/*  74:    */   {
/*  75: 68 */     if (!FUCKING_LOCK)
/*  76:    */     {
/*  77: 69 */       FUCKING_LOCK = true;
/*  78: 70 */       TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/*  79: 71 */       if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/*  80: 71 */         ((GT_TileEntity_Ores)tTileEntity).onUpdated();
/*  81:    */       }
/*  82:    */     }
/*  83: 73 */     FUCKING_LOCK = false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void onNeighborBlockChange(World aWorld, int aX, int aY, int aZ, Block aBlock)
/*  87:    */   {
/*  88: 78 */     if (!FUCKING_LOCK)
/*  89:    */     {
/*  90: 79 */       FUCKING_LOCK = true;
/*  91: 80 */       TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/*  92: 81 */       if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/*  93: 81 */         ((GT_TileEntity_Ores)tTileEntity).onUpdated();
/*  94:    */       }
/*  95:    */     }
/*  96: 83 */     FUCKING_LOCK = false;
/*  97:    */   }
/*  98:    */   
/*  99:    */  public String getLocalizedName(Materials aMaterial) {
	/*  87 */     switch (aMaterial) {
	/*     */     case InfusedAir: case InfusedDull: case InfusedEarth: case InfusedEntropy: case InfusedFire: case InfusedOrder: case InfusedVis: case InfusedWater: 
	/*  89 */       return aMaterial.mDefaultLocalName + " Infused Stone";
	/*     */     case Vermiculite: case Bentonite: case Kaolinite: case Talc: case BasalticMineralSand: case GraniticMineralSand: case GlauconiteSand: case CassiteriteSand: case GarnetSand: case QuartzSand: case Pitchblende: case FullersEarth: 
	/*  91 */       return aMaterial.mDefaultLocalName;
	/*     */     }
	/*  93 */     return aMaterial.mDefaultLocalName + OrePrefixes.ore.mLocalizedMaterialPost;
	/*     */   }
/* 128:    */   
/* 129:    */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/* 130:    */   {
/* 131: 99 */     super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
/* 132:100 */     TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
/* 133:101 */     return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
/* 137:    */   {
/* 138:106 */     return (!(entity instanceof EntityDragon)) && (super.canEntityDestroy(world, x, y, z, entity));
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getHarvestTool(int aMeta)
/* 142:    */   {
/* 143:111 */     return aMeta < 8 ? "pickaxe" : "shovel";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getHarvestLevel(int aMeta)
/* 147:    */   {
/* 148:116 */     return aMeta % 8;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/* 152:    */   {
/* 153:121 */     return 1.0F + getHarvestLevel(aWorld.getBlockMetadata(aX, aY, aZ)) * 1.0F;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public float getExplosionResistance(Entity par1Entity, World aWorld, int aX, int aY, int aZ, double explosionX, double explosionY, double explosionZ)
/* 157:    */   {
/* 158:126 */     return 1.0F + getHarvestLevel(aWorld.getBlockMetadata(aX, aY, aZ)) * 1.0F;
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected boolean canSilkHarvest()
/* 162:    */   {
/* 163:131 */     return false;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getUnlocalizedName()
/* 167:    */   {
/* 168:134 */     return "gt.blockores";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getLocalizedName()
/* 172:    */   {
/* 173:135 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getRenderType()
/* 177:    */   {
/* 178:136 */     if (GT_Renderer_Block.INSTANCE == null) {
/* 179:136 */       return super.getRenderType();
/* 180:    */     }
/* 181:136 */     return GT_Renderer_Block.INSTANCE.mRenderID;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 185:    */   {
/* 186:137 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 190:    */   {
/* 191:138 */     return true;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean hasTileEntity(int aMeta)
/* 195:    */   {
/* 196:139 */     return true;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean renderAsNormalBlock()
/* 200:    */   {
/* 201:140 */     return true;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isOpaqueCube()
/* 205:    */   {
/* 206:141 */     return true;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public TileEntity createNewTileEntity(World aWorld, int aMeta)
/* 210:    */   {
/* 211:142 */     return createTileEntity(aWorld, aMeta);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public IIcon getIcon(IBlockAccess aIBlockAccess, int aX, int aY, int aZ, int aSide)
/* 215:    */   {
/* 216:143 */     return Blocks.stone.getIcon(0, 0);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public IIcon getIcon(int aSide, int aMeta)
/* 220:    */   {
/* 221:144 */     return Blocks.stone.getIcon(0, 0);
/* 222:    */   }
/* 223:    */   
/* 224:    */   @SideOnly(Side.CLIENT)
/* 225:    */   public void registerBlockIcons(IIconRegister aIconRegister) {}
/* 226:    */   
/* 227:    */   public int getDamageValue(World aWorld, int aX, int aY, int aZ)
/* 228:    */   {
/* 229:154 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 230:155 */     if ((tTileEntity != null) && ((tTileEntity instanceof GT_TileEntity_Ores))) {
/* 231:155 */       return ((GT_TileEntity_Ores)tTileEntity).getMetaData();
/* 232:    */     }
/* 233:156 */     return 0;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void breakBlock(World aWorld, int aX, int aY, int aZ, Block par5, int par6)
/* 237:    */   {
/* 238:161 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 239:162 */     if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/* 240:162 */       mTemporaryTileEntity.set((GT_TileEntity_Ores)tTileEntity);
/* 241:    */     }
/* 242:163 */     super.breakBlock(aWorld, aX, aY, aZ, par5, par6);
/* 243:164 */     aWorld.removeTileEntity(aX, aY, aZ);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public ArrayList<ItemStack> getDrops(World aWorld, int aX, int aY, int aZ, int aMeta, int aFortune)
/* 247:    */   {
/* 248:169 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 249:170 */     if ((tTileEntity instanceof GT_TileEntity_Ores)) {
/* 250:170 */       return ((GT_TileEntity_Ores)tTileEntity).getDrops(aFortune);
/* 251:    */     }
/* 252:171 */     return mTemporaryTileEntity.get() == null ? new ArrayList() : ((GT_TileEntity_Ores)mTemporaryTileEntity.get()).getDrops(aFortune);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public TileEntity createTileEntity(World aWorld, int aMeta)
/* 256:    */   {
/* 257:176 */     return new GT_TileEntity_Ores();
/* 258:    */   }
/* 259:    */   
/* 260:    */   @SideOnly(Side.CLIENT)
/* 261:    */   public void getSubBlocks(Item aItem, CreativeTabs aTab, List aList)
/* 262:    */   {
/* 263:182 */     for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++)
/* 264:    */     {
/* 265:183 */       Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
/* 266:184 */       if ((tMaterial != null) && ((tMaterial.mTypes & 0x8) != 0))
/* 267:    */       {
/* 268:185 */         aList.add(new ItemStack(aItem, 1, i));
/* 269:186 */         aList.add(new ItemStack(aItem, 1, i + 1000));
/* 270:187 */         aList.add(new ItemStack(aItem, 1, i + 2000));
/* 271:188 */         aList.add(new ItemStack(aItem, 1, i + 3000));
/* 272:189 */         aList.add(new ItemStack(aItem, 1, i + 4000));
/* 273:190 */         aList.add(new ItemStack(aItem, 1, i + 16000));
/* 274:191 */         aList.add(new ItemStack(aItem, 1, i + 17000));
/* 275:192 */         aList.add(new ItemStack(aItem, 1, i + 18000));
/* 276:193 */         aList.add(new ItemStack(aItem, 1, i + 19000));
/* 277:194 */         aList.add(new ItemStack(aItem, 1, i + 20000));
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Ores
 * JD-Core Version:    0.7.0.1
 */