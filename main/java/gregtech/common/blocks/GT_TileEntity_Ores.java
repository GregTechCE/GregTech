/*   1:    */ package gregtech.common.blocks;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.GT_Values;
/*   5:    */ import gregtech.api.enums.Materials;
/*   6:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*   7:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   8:    */ import gregtech.api.interfaces.ITexture;
/*   9:    */ import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
/*  10:    */ import gregtech.api.net.IGT_NetworkHandler;
/*  11:    */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  12:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  13:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  14:    */ import gregtech.api.util.GT_Utility;

/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Random;

/*  17:    */ import net.minecraft.block.Block;
/*  18:    */ import net.minecraft.init.Blocks;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ import net.minecraft.nbt.NBTTagCompound;
/*  21:    */ import net.minecraft.network.Packet;
/*  22:    */ import net.minecraft.tileentity.TileEntity;
/*  23:    */ import net.minecraft.world.World;
/*  24:    */ 
/*  25:    */ public class GT_TileEntity_Ores
/*  26:    */   extends TileEntity
/*  27:    */   implements ITexturedTileEntity
/*  28:    */ {
/*  29: 28 */   private static final ITexture[] mStoneTextures = { new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.netherrack, 0, 0), new GT_CopiedBlockTexture(Blocks.end_stone, 0, 0), new GT_RenderedTexture(Textures.BlockIcons.GRANITE_BLACK_STONE), new GT_RenderedTexture(Textures.BlockIcons.GRANITE_RED_STONE), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_CopiedBlockTexture(Blocks.stone, 0, 0) };
/*  30: 47 */   public short mMetaData = 0;
/*  31: 48 */   public boolean mNatural = false;
/*  32: 48 */   public boolean mBlocked = true;
/*  33:    */   
/*  34:    */   public void readFromNBT(NBTTagCompound aNBT)
/*  35:    */   {
/*  36: 52 */     super.readFromNBT(aNBT);
/*  37: 53 */     this.mMetaData = aNBT.getShort("m");
/*  38: 54 */     this.mNatural = aNBT.getBoolean("n");
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void writeToNBT(NBTTagCompound aNBT)
/*  42:    */   {
/*  43: 59 */     super.writeToNBT(aNBT);
/*  44: 60 */     aNBT.setShort("m", this.mMetaData);
/*  45: 61 */     aNBT.setBoolean("n", this.mNatural);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void onUpdated()
/*  49:    */   {
/*  50: 65 */     if ((!this.worldObj.isRemote) && (this.mBlocked))
/*  51:    */     {
/*  52: 66 */       this.mBlocked = false;
/*  53: 67 */       GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(this.xCoord, (short)this.yCoord, this.zCoord, this.mMetaData), this.xCoord, this.zCoord);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Packet getDescriptionPacket()
/*  58:    */   {
/*  59: 73 */     if (!this.worldObj.isRemote) {
/*  60: 73 */       if ((this.mBlocked == (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1)) ? 1 : 0) == 0) {
/*  61: 74 */         GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(this.xCoord, (short)this.yCoord, this.zCoord, this.mMetaData), this.xCoord, this.zCoord);
/*  62:    */       }
/*  63:    */     }
/*  64: 76 */     return null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static byte getHarvestData(short aMetaData)
/*  68:    */   {
/*  69: 86 */     Materials aMaterial = GregTech_API.sGeneratedMaterials[(aMetaData % 1000)];
/*  70: 87 */     return aMaterial == null ? 0 : (byte)Math.max((aMetaData % 16000 / 1000 == 3) || (aMetaData % 16000 / 1000 == 4) ? 3 : 0, Math.min(7, aMaterial.mToolQuality - (aMetaData < 16000 ? 0 : 1)));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void overrideOreBlockMaterial(Block aOverridingStoneBlock, byte aOverridingStoneMeta)
/*  74:    */   {
/*  75: 91 */     this.mMetaData = ((short)(int)(this.mMetaData % 1000L + this.mMetaData / 16000L * 16000L));
/*  76: 92 */     if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.netherrack)) {
/*  77: 93 */       this.mMetaData = ((short)(this.mMetaData + 1000));
/*  78: 94 */     } else if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.end_stone)) {
/*  79: 95 */       this.mMetaData = ((short)(this.mMetaData + 2000));
/*  80: 96 */     } else if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, GregTech_API.sBlockGranites)) {
/*  81: 97 */       if (aOverridingStoneBlock == GregTech_API.sBlockGranites)
/*  82:    */       {
/*  83: 98 */         if (aOverridingStoneMeta < 8) {
/*  84: 99 */           this.mMetaData = ((short)(this.mMetaData + 3000));
/*  85:    */         } else {
/*  86:101 */           this.mMetaData = ((short)(this.mMetaData + 4000));
/*  87:    */         }
/*  88:    */       }
/*  89:    */       else {
/*  90:104 */         this.mMetaData = ((short)(this.mMetaData + 3000));
/*  91:    */       }
/*  92:    */     }
/*  93:107 */     this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, getHarvestData(this.mMetaData), 0);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData)
/*  97:    */   {
/*  98:111 */     aY = Math.min(aWorld.getActualHeight(), Math.max(aY, 1));
/*  99:112 */     Block tBlock = aWorld.getBlock(aX, aY, aZ);
/* 100:114 */     if ((aMetaData > 0) && (tBlock != Blocks.air))
/* 101:    */     {
/* 102:115 */       if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.netherrack)) {
/* 103:116 */         aMetaData += 1000;
/* 104:117 */       } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.end_stone)) {
/* 105:118 */         aMetaData += 2000;
/* 106:119 */       } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTech_API.sBlockGranites))
/* 107:    */       {
/* 108:120 */         if (tBlock == GregTech_API.sBlockGranites)
/* 109:    */         {
/* 110:121 */           if (aWorld.getBlockMetadata(aX, aY, aZ) < 8) {
/* 111:122 */             aMetaData += 3000;
/* 112:    */           } else {
/* 113:124 */             aMetaData += 4000;
/* 114:    */           }
/* 115:    */         }
/* 116:    */         else {
/* 117:127 */           aMetaData += 3000;
/* 118:    */         }
/* 119:    */       }
/* 120:129 */       else if (!tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.stone)) {
/* 121:129 */         return false;
/* 122:    */       }
/* 123:130 */       aWorld.setBlock(aX, aY, aZ, GregTech_API.sBlockOres1, getHarvestData((short)aMetaData), 0);
/* 124:131 */       TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 125:132 */       if ((tTileEntity instanceof GT_TileEntity_Ores))
/* 126:    */       {
/* 127:133 */         ((GT_TileEntity_Ores)tTileEntity).mMetaData = ((short)aMetaData);
/* 128:134 */         ((GT_TileEntity_Ores)tTileEntity).mNatural = true;
/* 129:    */       }
/* 130:136 */       return true;
/* 131:    */     }
/* 132:138 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public short getMetaData()
/* 136:    */   {
/* 137:145 */     return this.mMetaData;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean canUpdate()
/* 141:    */   {
/* 142:150 */     return false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public ArrayList<ItemStack> getDrops(int aFortune)
/* 146:    */   {
/* 147:157 */     ArrayList<ItemStack> rList = new ArrayList();
/* 148:158 */     if (this.mMetaData <= 0)
/* 149:    */     {
/* 150:159 */       rList.add(new ItemStack(Blocks.iron_ore, 1, 0));
/* 151:160 */       return rList;
/* 152:    */     }
/* 153:162 */     if (this.mMetaData < 16000)
/* 154:    */     {
/* 155:163 */       rList.add(new ItemStack(GregTech_API.sBlockOres1, 1, this.mMetaData));
/* 156:164 */       return rList;
/* 157:    */     }
/* 158:166 */     Materials aMaterial = GregTech_API.sGeneratedMaterials[(this.mMetaData % 1000)];
/* 159:167 */     if (!this.mNatural) {
/* 160:167 */       aFortune = 0;
/* 161:    */     }
/* 162:168 */     if (aMaterial != null)
/* 163:    */     {
/* 164:169 */       Random tRandom = new Random(this.xCoord ^ this.yCoord ^ this.zCoord);
/* 165:170 */       ArrayList<ItemStack> tSelector = new ArrayList();
/* 166:    */       
/* 167:    */ 
/* 168:173 */       ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
/* 169:174 */       if (tStack != null) {
/* 170:174 */         for (int i = 0; i < 1; i++) {
/* 171:174 */           tSelector.add(tStack);
/* 172:    */         }
/* 173:    */       }
/* 174:175 */       tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
/* 175:176 */       if (tStack != null) {
/* 176:176 */         for (int i = 0; i < 2; i++) {
/* 177:176 */           tSelector.add(tStack);
/* 178:    */         }
/* 179:    */       }
/* 180:177 */       tStack = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
/* 181:178 */       if (tStack != null) {
/* 182:178 */         for (int i = 0; i < 12; i++) {
/* 183:178 */           tSelector.add(tStack);
/* 184:    */         }
/* 185:    */       }
/* 186:180 */       tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L), 1L);
/* 187:181 */       if (tStack != null) {
/* 188:181 */         for (int i = 0; i < 5; i++) {
/* 189:181 */           tSelector.add(tStack);
/* 190:    */         }
/* 191:    */       }
/* 192:182 */       tStack = GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L);
/* 193:183 */       if (tStack != null) {
/* 194:183 */         for (int i = 0; i < 10; i++) {
/* 195:183 */           tSelector.add(tStack);
/* 196:    */         }
/* 197:    */       }
/* 198:185 */       tStack = GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), 1L);
/* 199:186 */       if (tStack != null) {
/* 200:186 */         for (int i = 0; i < 5; i++) {
/* 201:186 */           tSelector.add(tStack);
/* 202:    */         }
/* 203:    */       }
/* 204:187 */       tStack = GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L);
/* 205:188 */       if (tStack != null) {
/* 206:188 */         for (int i = 0; i < 10; i++) {
/* 207:188 */           tSelector.add(tStack);
/* 208:    */         }
/* 209:    */       }
/* 210:190 */       if (tSelector.size() > 0)
/* 211:    */       {
/* 212:191 */         int i = 0;
/* 213:191 */         for (int j = Math.max(1, aMaterial.mOreMultiplier + (aFortune > 0 ? tRandom.nextInt(1 + aFortune * aMaterial.mOreMultiplier) : 0) / 2); i < j; i++) {
/* 214:192 */           rList.add(GT_Utility.copyAmount(1L, new Object[] { tSelector.get(tRandom.nextInt(tSelector.size())) }));
/* 215:    */         }
/* 216:    */       }
/* 217:195 */       if (tRandom.nextInt(3 + aFortune) > 1) {
/* 218:196 */         switch (this.mMetaData / 1000 % 16)
/* 219:    */         {
/* 220:    */         case 0: 
/* 221:197 */           rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, Materials.Stone, 1L)); break;
/* 222:    */         case 1: 
/* 223:198 */           rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, Materials.Netherrack, 1L)); break;
/* 224:    */         case 2: 
/* 225:199 */           rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, Materials.Endstone, 1L)); break;
/* 226:    */         case 3: 
/* 227:200 */           rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, Materials.GraniteBlack, 1L)); break;
/* 228:    */         case 4: 
/* 229:201 */           rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, Materials.GraniteRed, 1L));
/* 230:    */         }
/* 231:    */       }
/* 232:    */     }
/* 233:205 */     return rList;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public ITexture[] getTexture(byte aSide)
/* 237:    */   {
/* 238:210 */     Materials aMaterial = GregTech_API.sGeneratedMaterials[(this.mMetaData % 1000)];
/* 239:211 */     if ((aMaterial != null) && (this.mMetaData < 32000)) {
/* 240:211 */       return new ITexture[] { mStoneTextures[(this.mMetaData / 1000 % 16)], new GT_RenderedTexture(aMaterial.mIconSet.mTextures[OrePrefixes.oreSmall.mTextureIndex], aMaterial.mRGBa) };
/* 241:    */     }
/* 242:212 */     return new ITexture[] { mStoneTextures[0], new GT_RenderedTexture(gregtech.api.enums.TextureSet.SET_NONE.mTextures[OrePrefixes.ore.mTextureIndex]) };
/* 243:    */   }
/* 244:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_TileEntity_Ores
 * JD-Core Version:    0.7.0.1
 */