/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*   6:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   7:    */ import gregtech.api.interfaces.IDebugableBlock;
/*   8:    */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*   9:    */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  10:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  11:    */ import gregtech.api.items.GT_Generic_Block;
/*  12:    */ import gregtech.api.metatileentity.BaseMetaPipeEntity;
/*  13:    */ import gregtech.api.metatileentity.BaseMetaTileEntity;
/*  14:    */ import gregtech.api.metatileentity.BaseTileEntity;
/*  15:    */ import gregtech.api.util.GT_BaseCrop;
/*  16:    */ import gregtech.api.util.GT_Log;
/*  17:    */ import gregtech.api.util.GT_Utility;
/*  18:    */ import gregtech.common.render.GT_Renderer_Block;

/*  19:    */ import java.io.PrintStream;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Random;

/*  23:    */ import net.minecraft.block.Block;
/*  24:    */ import net.minecraft.block.ITileEntityProvider;
/*  25:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  26:    */ import net.minecraft.creativetab.CreativeTabs;
/*  27:    */ import net.minecraft.entity.Entity;
/*  28:    */ import net.minecraft.entity.EntityLivingBase;
/*  29:    */ import net.minecraft.entity.EnumCreatureType;
/*  30:    */ import net.minecraft.entity.item.EntityItem;
/*  31:    */ import net.minecraft.entity.player.EntityPlayer;
/*  32:    */ import net.minecraft.item.Item;
/*  33:    */ import net.minecraft.item.ItemStack;
/*  34:    */ import net.minecraft.nbt.NBTTagCompound;
/*  35:    */ import net.minecraft.tileentity.TileEntity;
/*  36:    */ import net.minecraft.util.AxisAlignedBB;
/*  37:    */ import net.minecraft.util.IIcon;
/*  38:    */ import net.minecraft.util.MathHelper;
/*  39:    */ import net.minecraft.util.StatCollector;
/*  40:    */ import net.minecraft.world.Explosion;
/*  41:    */ import net.minecraft.world.IBlockAccess;
/*  42:    */ import net.minecraft.world.World;
/*  43:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  44:    */ 
/*  45:    */ public class GT_Block_Machines
/*  46:    */   extends GT_Generic_Block
/*  47:    */   implements IDebugableBlock, ITileEntityProvider
/*  48:    */ {
/*  49: 47 */   public static ThreadLocal<IGregTechTileEntity> mTemporaryTileEntity = new ThreadLocal();
/*  50:    */   
/*  51:    */   public GT_Block_Machines()
/*  52:    */   {
/*  53: 50 */     super(GT_Item_Machines.class, "gt.blockmachines", new GT_Material_Machines());
/*  54: 51 */     GregTech_API.registerMachineBlock(this, -1);
/*  55: 52 */     setHardness(1.0F);
/*  56: 53 */     setResistance(10.0F);
/*  57: 54 */     setStepSound(soundTypeMetal);
/*  58: 55 */     setCreativeTab(GregTech_API.TAB_GREGTECH);
/*  59: 56 */     this.isBlockContainer = true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getHarvestTool(int aMeta)
/*  63:    */   {
/*  64: 61 */     switch (aMeta / 4)
/*  65:    */     {
/*  66:    */     case 0: 
/*  67: 62 */       return "wrench";
/*  68:    */     case 1: 
/*  69: 63 */       return "wrench";
/*  70:    */     case 2: 
/*  71: 64 */       return "cutter";
/*  72:    */     case 3: 
/*  73: 65 */       return "axe";
/*  74:    */     }
/*  75: 66 */     return "wrench";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getHarvestLevel(int aMeta)
/*  79:    */   {
/*  80: 72 */     return aMeta % 4;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected boolean canSilkHarvest()
/*  84:    */   {
/*  85: 77 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void onNeighborChange(IBlockAccess aWorld, int aX, int aY, int aZ, int aTileX, int aTileY, int aTileZ)
/*  89:    */   {
/*  90: 80 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/*  91: 80 */     if ((tTileEntity instanceof BaseTileEntity)) {
/*  92: 80 */       ((BaseTileEntity)tTileEntity).onAdjacentBlockChange(aTileX, aTileY, aTileZ);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onBlockAdded(World aWorld, int aX, int aY, int aZ)
/*  97:    */   {
/*  98: 81 */     super.onBlockAdded(aWorld, aX, aY, aZ);
/*  99: 81 */     if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
/* 100: 81 */       GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getUnlocalizedName()
/* 105:    */   {
/* 106: 82 */     return "gt.blockmachines";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getLocalizedName()
/* 110:    */   {
/* 111: 83 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face)
/* 115:    */   {
/* 116: 84 */     return 0;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face)
/* 120:    */   {
/* 121: 85 */     return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0) ? 100 : 0;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getRenderType()
/* 125:    */   {
/* 126: 86 */     if (GT_Renderer_Block.INSTANCE == null) {
/* 127: 86 */       return super.getRenderType();
/* 128:    */     }
/* 129: 86 */     return GT_Renderer_Block.INSTANCE.mRenderID;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isFireSource(World aWorld, int aX, int aY, int aZ, ForgeDirection side)
/* 133:    */   {
/* 134: 87 */     return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face)
/* 138:    */   {
/* 139: 88 */     return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess aWorld, int aX, int aY, int aZ)
/* 143:    */   {
/* 144: 89 */     return false;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean canConnectRedstone(IBlockAccess var1, int var2, int var3, int var4, int var5)
/* 148:    */   {
/* 149: 90 */     return true;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 153:    */   {
/* 154: 91 */     return false;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 158:    */   {
/* 159: 92 */     return false;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean hasTileEntity(int aMeta)
/* 163:    */   {
/* 164: 93 */     return true;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean hasComparatorInputOverride()
/* 168:    */   {
/* 169: 94 */     return true;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean renderAsNormalBlock()
/* 173:    */   {
/* 174: 95 */     return false;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean canProvidePower()
/* 178:    */   {
/* 179: 96 */     return true;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isOpaqueCube()
/* 183:    */   {
/* 184: 97 */     return false;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public TileEntity createNewTileEntity(World aWorld, int aMeta)
/* 188:    */   {
/* 189: 98 */     return createTileEntity(aWorld, aMeta);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public IIcon getIcon(IBlockAccess aIBlockAccess, int aX, int aY, int aZ, int aSide)
/* 193:    */   {
/* 194: 99 */     return Textures.BlockIcons.MACHINE_LV_SIDE.getIcon();
/* 195:    */   }
/* 196:    */   
/* 197:    */   public IIcon getIcon(int aSide, int aMeta)
/* 198:    */   {
/* 199:100 */     return Textures.BlockIcons.MACHINE_LV_SIDE.getIcon();
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean onBlockEventReceived(World aWorld, int aX, int aY, int aZ, int aData1, int aData2)
/* 203:    */   {
/* 204:104 */     super.onBlockEventReceived(aWorld, aX, aY, aZ, aData1, aData2);
/* 205:105 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 206:106 */     return tTileEntity != null ? tTileEntity.receiveClientEvent(aData1, aData2) : false;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List outputAABB, Entity collider)
/* 210:    */   {
/* 211:111 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 212:112 */     if (((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getMetaTileEntity() != null))
/* 213:    */     {
/* 214:113 */       ((IGregTechTileEntity)tTileEntity).addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider);
/* 215:114 */       return;
/* 216:    */     }
/* 217:116 */     super.addCollisionBoxesToList(aWorld, aX, aY, aZ, inputAABB, outputAABB, collider);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ)
/* 221:    */   {
/* 222:121 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 223:122 */     if (((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getMetaTileEntity() != null)) {
/* 224:123 */       return ((IGregTechTileEntity)tTileEntity).getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
/* 225:    */     }
/* 226:125 */     return super.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider)
/* 230:    */   {
/* 231:130 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 232:131 */     if (((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity)tTileEntity).getMetaTileEntity() != null))
/* 233:    */     {
/* 234:132 */       ((IGregTechTileEntity)tTileEntity).onEntityCollidedWithBlock(aWorld, aX, aY, aZ, collider);
/* 235:133 */       return;
/* 236:    */     }
/* 237:135 */     super.onEntityCollidedWithBlock(aWorld, aX, aY, aZ, collider);
/* 238:    */   }
/* 239:    */   
/* 240:    */   @SideOnly(Side.CLIENT)
/* 241:    */   public void registerBlockIcons(IIconRegister aIconRegister)
/* 242:    */   {
/* 243:141 */     if (GregTech_API.sPostloadFinished)
/* 244:    */     {
/* 245:142 */       GT_Log.out.println("GT_Mod: Setting up Icon Register for Blocks");
/* 246:143 */       GregTech_API.sBlockIcons = aIconRegister;
/* 247:    */       
/* 248:145 */       GT_Log.out.println("GT_Mod: Registering MetaTileEntity specific Textures");
/* 249:146 */       for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
/* 250:    */         try
/* 251:    */         {
/* 252:148 */           if (tMetaTileEntity != null) {
/* 253:148 */             tMetaTileEntity.registerIcons(aIconRegister);
/* 254:    */           }
/* 255:    */         }
/* 256:    */         catch (Throwable e)
/* 257:    */         {
/* 258:150 */           e.printStackTrace(GT_Log.err);
/* 259:    */         }
/* 260:    */       }
/* 261:154 */       GT_Log.out.println("GT_Mod: Registering Crop specific Textures");
/* 262:    */       try
/* 263:    */       {
/* 264:156 */         for (GT_BaseCrop tCrop : GT_BaseCrop.sCropList) {
/* 265:157 */           tCrop.registerSprites(aIconRegister);
/* 266:    */         }
/* 267:    */       }
/* 268:    */       catch (Throwable e)
/* 269:    */       {
/* 270:160 */         e.printStackTrace(GT_Log.err);
/* 271:    */       }
/* 272:163 */       GT_Log.out.println("GT_Mod: Starting Block Icon Load Phase");
/* 273:164 */       System.out.println("GT_Mod: Starting Block Icon Load Phase");
/* 274:165 */       for (Runnable tRunnable : GregTech_API.sGTBlockIconload) {
/* 275:    */         try
/* 276:    */         {
/* 277:167 */           tRunnable.run();
/* 278:    */         }
/* 279:    */         catch (Throwable e)
/* 280:    */         {
/* 281:169 */           e.printStackTrace(GT_Log.err);
/* 282:    */         }
/* 283:    */       }
/* 284:172 */       GT_Log.out.println("GT_Mod: Finished Block Icon Load Phase");
/* 285:173 */       System.out.println("GT_Mod: Finished Block Icon Load Phase");
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public float getBlockHardness(World aWorld, int aX, int aY, int aZ)
/* 290:    */   {
/* 291:180 */     return super.getBlockHardness(aWorld, aX, aY, aZ);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public float getPlayerRelativeBlockHardness(EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ)
/* 295:    */   {
/* 296:185 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 297:186 */     if (((tTileEntity instanceof BaseMetaTileEntity)) && (((BaseMetaTileEntity)tTileEntity).privateAccess()) && (!((BaseMetaTileEntity)tTileEntity).playerOwnsThis(aPlayer, true))) {
/* 298:186 */       return -1.0F;
/* 299:    */     }
/* 300:187 */     return super.getPlayerRelativeBlockHardness(aPlayer, aWorld, aX, aY, aZ);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean onBlockActivated(World aWorld, int aX, int aY, int aZ, EntityPlayer aPlayer, int aSide, float par1, float par2, float par3)
/* 304:    */   {
/* 305:192 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 306:194 */     if ((tTileEntity == null) || (aPlayer.isSneaking())) {
/* 307:194 */       return false;
/* 308:    */     }
/* 309:196 */     if ((tTileEntity instanceof IGregTechTileEntity))
/* 310:    */     {
/* 311:197 */       if (((IGregTechTileEntity)tTileEntity).getTimer() < 50L) {
/* 312:197 */         return false;
/* 313:    */       }
/* 314:198 */       if ((!aWorld.isRemote) && (!((IGregTechTileEntity)tTileEntity).isUseableByPlayer(aPlayer))) {
/* 315:198 */         return true;
/* 316:    */       }
/* 317:199 */       return ((IGregTechTileEntity)tTileEntity).onRightclick(aPlayer, (byte)aSide, par1, par2, par3);
/* 318:    */     }
/* 319:202 */     return false;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void onBlockClicked(World aWorld, int aX, int aY, int aZ, EntityPlayer aPlayer)
/* 323:    */   {
/* 324:207 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 325:208 */     if ((tTileEntity != null) && 
/* 326:209 */       ((tTileEntity instanceof IGregTechTileEntity))) {
/* 327:210 */       ((IGregTechTileEntity)tTileEntity).onLeftclick(aPlayer);
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int getDamageValue(World aWorld, int aX, int aY, int aZ)
/* 332:    */   {
/* 333:217 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 334:218 */     if ((tTileEntity instanceof IGregTechTileEntity)) {
/* 335:219 */       return ((IGregTechTileEntity)tTileEntity).getMetaTileID();
/* 336:    */     }
/* 337:221 */     return 0;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void onBlockExploded(World aWorld, int aX, int aY, int aZ, Explosion aExplosion)
/* 341:    */   {
/* 342:226 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 343:227 */     if ((tTileEntity instanceof BaseMetaTileEntity)) {
/* 344:227 */       ((BaseMetaTileEntity)tTileEntity).doEnergyExplosion();
/* 345:    */     }
/* 346:228 */     super.onBlockExploded(aWorld, aX, aY, aZ, aExplosion);
/* 347:    */   }
/* 348:    */   
/* 349:    */   public void breakBlock(World aWorld, int aX, int aY, int aZ, Block par5, int par6)
/* 350:    */   {
/* 351:233 */     GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
/* 352:234 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 353:235 */     if ((tTileEntity instanceof IGregTechTileEntity))
/* 354:    */     {
/* 355:236 */       IGregTechTileEntity tGregTechTileEntity = (IGregTechTileEntity)tTileEntity;
/* 356:237 */       Random tRandom = new Random();
/* 357:238 */       mTemporaryTileEntity.set(tGregTechTileEntity);
/* 358:239 */       for (int i = 0; i < tGregTechTileEntity.getSizeInventory(); i++)
/* 359:    */       {
/* 360:240 */         ItemStack tItem = tGregTechTileEntity.getStackInSlot(i);
/* 361:241 */         if ((tItem != null) && (tItem.stackSize > 0) && (tGregTechTileEntity.isValidSlot(i)))
/* 362:    */         {
/* 363:242 */           EntityItem tItemEntity = new EntityItem(aWorld, aX + tRandom.nextFloat() * 0.8F + 0.1F, aY + tRandom.nextFloat() * 0.8F + 0.1F, aZ + tRandom.nextFloat() * 0.8F + 0.1F, new ItemStack(tItem.getItem(), tItem.stackSize, tItem.getItemDamage()));
/* 364:243 */           if (tItem.hasTagCompound()) {
/* 365:243 */             tItemEntity.getEntityItem().setTagCompound((NBTTagCompound)tItem.getTagCompound().copy());
/* 366:    */           }
/* 367:244 */           tItemEntity.motionX = (tRandom.nextGaussian() * 0.0500000007450581D);
/* 368:245 */           tItemEntity.motionY = (tRandom.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
/* 369:246 */           tItemEntity.motionZ = (tRandom.nextGaussian() * 0.0500000007450581D);
/* 370:247 */           aWorld.spawnEntityInWorld(tItemEntity);
/* 371:248 */           tItem.stackSize = 0;
/* 372:249 */           tGregTechTileEntity.setInventorySlotContents(i, null);
/* 373:    */         }
/* 374:    */       }
/* 375:    */     }
/* 376:253 */     super.breakBlock(aWorld, aX, aY, aZ, par5, par6);
/* 377:254 */     aWorld.removeTileEntity(aX, aY, aZ);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public ArrayList<ItemStack> getDrops(World aWorld, int aX, int aY, int aZ, int aMeta, int aFortune)
/* 381:    */   {
/* 382:259 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 383:260 */     if ((tTileEntity instanceof IGregTechTileEntity)) {
/* 384:260 */       return ((IGregTechTileEntity)tTileEntity).getDrops();
/* 385:    */     }
/* 386:261 */     return mTemporaryTileEntity.get() == null ? new ArrayList() : ((IGregTechTileEntity)mTemporaryTileEntity.get()).getDrops();
/* 387:    */   }
/* 388:    */   
/* 389:    */   public int getComparatorInputOverride(World aWorld, int aX, int aY, int aZ, int aSide)
/* 390:    */   {
/* 391:266 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 392:267 */     if ((tTileEntity != null) && ((tTileEntity instanceof IGregTechTileEntity))) {
/* 393:267 */       return ((IGregTechTileEntity)tTileEntity).getComparatorValue((byte)aSide);
/* 394:    */     }
/* 395:268 */     return 0;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public int isProvidingWeakPower(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide)
/* 399:    */   {
/* 400:273 */     if ((aSide < 0) || (aSide > 5)) {
/* 401:273 */       return 0;
/* 402:    */     }
/* 403:275 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 404:277 */     if ((tTileEntity != null) && ((tTileEntity instanceof IGregTechTileEntity))) {
/* 405:277 */       return ((IGregTechTileEntity)tTileEntity).getOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide));
/* 406:    */     }
/* 407:279 */     return 0;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public int isProvidingStrongPower(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide)
/* 411:    */   {
/* 412:284 */     if ((aSide < 0) || (aSide > 5)) {
/* 413:284 */       return 0;
/* 414:    */     }
/* 415:286 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 416:288 */     if ((tTileEntity != null) && ((tTileEntity instanceof IGregTechTileEntity))) {
/* 417:288 */       return ((IGregTechTileEntity)tTileEntity).getStrongOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide));
/* 418:    */     }
/* 419:290 */     return 0;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public void dropBlockAsItemWithChance(World aWorld, int aX, int aY, int aZ, int par5, float chance, int par7)
/* 423:    */   {
/* 424:295 */     if (!aWorld.isRemote)
/* 425:    */     {
/* 426:296 */       TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 427:298 */       if ((tTileEntity != null) && (chance < 1.0F))
/* 428:    */       {
/* 429:299 */         if (((tTileEntity instanceof BaseMetaTileEntity)) && (GregTech_API.sMachineNonWrenchExplosions)) {
/* 430:299 */           ((BaseMetaTileEntity)tTileEntity).doEnergyExplosion();
/* 431:    */         }
/* 432:    */       }
/* 433:    */       else {
/* 434:301 */         super.dropBlockAsItemWithChance(aWorld, aX, aY, aZ, par5, chance, par7);
/* 435:    */       }
/* 436:    */     }
/* 437:    */   }
/* 438:    */   
/* 439:    */   public boolean isSideSolid(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide)
/* 440:    */   {
/* 441:313 */     if (aWorld.getBlockMetadata(aX, aY, aZ) == 0) {
/* 442:313 */       return true;
/* 443:    */     }
/* 444:314 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 445:315 */     if (tTileEntity != null)
/* 446:    */     {
/* 447:316 */       if ((tTileEntity instanceof BaseMetaTileEntity)) {
/* 448:316 */         return true;
/* 449:    */       }
/* 450:317 */       if (((tTileEntity instanceof BaseMetaPipeEntity)) && ((((BaseMetaPipeEntity)tTileEntity).mConnections & 0xFFFFFFC0) != 0)) {
/* 451:317 */         return true;
/* 452:    */       }
/* 453:318 */       if (((tTileEntity instanceof ICoverable)) && (((ICoverable)tTileEntity).getCoverIDAtSide((byte)aSide.ordinal()) != 0)) {
/* 454:318 */         return true;
/* 455:    */       }
/* 456:    */     }
/* 457:320 */     return false;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public int getLightOpacity(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 461:    */   {
/* 462:325 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 463:326 */     if (tTileEntity == null) {
/* 464:326 */       return 0;
/* 465:    */     }
/* 466:327 */     if ((tTileEntity instanceof IGregTechTileEntity)) {
/* 467:327 */       return ((IGregTechTileEntity)tTileEntity).getLightOpacity();
/* 468:    */     }
/* 469:328 */     return aWorld.getBlockMetadata(aX, aY, aZ) == 0 ? 255 : 0;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public int getLightValue(IBlockAccess aWorld, int aX, int aY, int aZ)
/* 473:    */   {
/* 474:333 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 475:334 */     if ((tTileEntity instanceof BaseMetaTileEntity)) {
/* 476:334 */       return ((BaseMetaTileEntity)tTileEntity).getLightValue();
/* 477:    */     }
/* 478:335 */     return 0;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public TileEntity createTileEntity(World aWorld, int aMeta)
/* 482:    */   {
/* 483:340 */     if (aMeta < 4) {
/* 484:340 */       return GregTech_API.constructBaseMetaTileEntity();
/* 485:    */     }
/* 486:341 */     return new BaseMetaPipeEntity();
/* 487:    */   }
/* 488:    */   
/* 489:    */   public float getExplosionResistance(Entity par1Entity, World aWorld, int aX, int aY, int aZ, double explosionX, double explosionY, double explosionZ)
/* 490:    */   {
/* 491:346 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 492:347 */     if ((tTileEntity != null) && ((tTileEntity instanceof IGregTechTileEntity))) {
/* 493:347 */       return ((IGregTechTileEntity)tTileEntity).getBlastResistance((byte)6);
/* 494:    */     }
/* 495:348 */     return 10.0F;
/* 496:    */   }
/* 497:    */   
/* 498:    */   @SideOnly(Side.CLIENT)
/* 499:    */   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
/* 500:    */   {
/* 501:353 */     for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
/* 502:353 */       if (GregTech_API.METATILEENTITIES[i] != null) {
/* 503:353 */         par3List.add(new ItemStack(par1, 1, i));
/* 504:    */       }
/* 505:    */     }
/* 506:    */   }
/* 507:    */   
/* 508:    */   public void onBlockPlacedBy(World aWorld, int aX, int aY, int aZ, EntityLivingBase aPlayer, ItemStack aStack)
/* 509:    */   {
/* 510:358 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 511:359 */     if (tTileEntity == null) {
/* 512:359 */       return;
/* 513:    */     }
/* 514:360 */     if ((tTileEntity instanceof IGregTechTileEntity))
/* 515:    */     {
/* 516:361 */       IGregTechTileEntity var6 = (IGregTechTileEntity)tTileEntity;
/* 517:362 */       if (aPlayer == null)
/* 518:    */       {
/* 519:363 */         var6.setFrontFacing((byte)1);
/* 520:    */       }
/* 521:    */       else
/* 522:    */       {
/* 523:365 */         int var7 = MathHelper.floor_double(aPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 524:366 */         int var8 = Math.round(aPlayer.rotationPitch);
/* 525:367 */         if ((var8 >= 65) && (var6.isValidFacing((byte)1))) {
/* 526:368 */           var6.setFrontFacing((byte)1);
/* 527:369 */         } else if ((var8 <= -65) && (var6.isValidFacing((byte)0))) {
/* 528:370 */           var6.setFrontFacing((byte)0);
/* 529:    */         } else {
/* 530:372 */           switch (var7)
/* 531:    */           {
/* 532:    */           case 0: 
/* 533:373 */             var6.setFrontFacing((byte)2); break;
/* 534:    */           case 1: 
/* 535:374 */             var6.setFrontFacing((byte)5); break;
/* 536:    */           case 2: 
/* 537:375 */             var6.setFrontFacing((byte)3); break;
/* 538:    */           case 3: 
/* 539:376 */             var6.setFrontFacing((byte)4);
/* 540:    */           }
/* 541:    */         }
/* 542:    */       }
/* 543:    */     }
/* 544:    */   }
/* 545:    */   
/* 546:    */   public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aX, int aY, int aZ, int aLogLevel)
/* 547:    */   {
/* 548:385 */     TileEntity tTileEntity = aPlayer.worldObj.getTileEntity(aX, aY, aZ);
/* 549:386 */     if ((tTileEntity instanceof BaseMetaTileEntity)) {
/* 550:386 */       return ((BaseMetaTileEntity)tTileEntity).getDebugInfo(aPlayer, aLogLevel);
/* 551:    */     }
/* 552:387 */     if ((tTileEntity instanceof BaseMetaPipeEntity)) {
/* 553:387 */       return ((BaseMetaPipeEntity)tTileEntity).getDebugInfo(aPlayer, aLogLevel);
/* 554:    */     }
/* 555:388 */     return null;
/* 556:    */   }
/* 557:    */   
/* 558:    */   public boolean recolourBlock(World aWorld, int aX, int aY, int aZ, ForgeDirection aSide, int aColor)
/* 559:    */   {
/* 560:393 */     TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 561:394 */     if ((tTileEntity instanceof IGregTechTileEntity))
/* 562:    */     {
/* 563:395 */       if (((IGregTechTileEntity)tTileEntity).getColorization() == (byte)((aColor ^ 0xFFFFFFFF) & 0xF)) {
/* 564:395 */         return false;
/* 565:    */       }
/* 566:396 */       ((IGregTechTileEntity)tTileEntity).setColorization((byte)((aColor ^ 0xFFFFFFFF) & 0xF));
/* 567:397 */       return true;
/* 568:    */     }
/* 569:399 */     return false;
/* 570:    */   }
/* 571:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Block_Machines
 * JD-Core Version:    0.7.0.1
 */