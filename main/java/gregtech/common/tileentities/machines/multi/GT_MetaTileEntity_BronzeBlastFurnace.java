/*   1:    */ package gregtech.common.tileentities.machines.multi;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*   6:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   7:    */ import gregtech.api.interfaces.ITexture;
/*   8:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   9:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*  10:    */ import gregtech.api.objects.GT_ItemStack;
/*  11:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  12:    */ import gregtech.api.util.GT_CoverBehavior;
/*  13:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  14:    */ import gregtech.api.util.GT_Utility;
/*  15:    */ import gregtech.common.gui.GT_Container_BronzeBlastFurnace;
/*  16:    */ import gregtech.common.gui.GT_GUIContainer_BronzeBlastFurnace;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.init.Blocks;
/*  20:    */ import net.minecraft.init.Items;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.nbt.NBTTagCompound;
/*  23:    */ import net.minecraft.world.World;
/*  24:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  25:    */ 
/*  26:    */ public class GT_MetaTileEntity_BronzeBlastFurnace
/*  27:    */   extends MetaTileEntity
/*  28:    */ {
/*  29: 26 */   public int mMaxProgresstime = 0;
/*  30: 26 */   public int mUpdate = 5;
/*  31: 26 */   public int mProgresstime = 0;
/*  32: 28 */   public boolean mMachine = false;
/*  33: 31 */   private static final ITexture[] FACING_SIDE = { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEPLATEDBRICKS) };
/*  34: 32 */   private static final ITexture[] FACING_FRONT = { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBLASTFURNACE) };
/*  35: 33 */   private static final ITexture[] FACING_ACTIVE = { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBLASTFURNACE_ACTIVE) };
/*  36:    */   public ItemStack mOutputItem1;
/*  37:    */   public ItemStack mOutputItem2;
/*  38:    */   
/*  39:    */   public GT_MetaTileEntity_BronzeBlastFurnace(int aID, String aName, String aNameRegional)
/*  40:    */   {
/*  41: 36 */     super(aID, aName, aNameRegional, 4);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public GT_MetaTileEntity_BronzeBlastFurnace(String aName)
/*  45:    */   {
/*  46: 40 */     super(aName, 4);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String[] getDescription()
/*  50:    */   {
/*  51: 45 */     return new String[] { "To get your first Steel", "Multiblock: 3x3x4 hollow with opening on top", "32 Bronze Plated Bricks required" };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/*  55:    */   {
/*  56: 50 */     if (aSide == aFacing) {
/*  57: 50 */       return aActive ? FACING_ACTIVE : FACING_FRONT;
/*  58:    */     }
/*  59: 51 */     return FACING_SIDE;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isSteampowered()
/*  63:    */   {
/*  64: 54 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isElectric()
/*  68:    */   {
/*  69: 55 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isPneumatic()
/*  73:    */   {
/*  74: 56 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isEnetInput()
/*  78:    */   {
/*  79: 57 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isEnetOutput()
/*  83:    */   {
/*  84: 58 */     return false;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isInputFacing(byte aSide)
/*  88:    */   {
/*  89: 59 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isOutputFacing(byte aSide)
/*  93:    */   {
/*  94: 60 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isTeleporterCompatible()
/*  98:    */   {
/*  99: 61 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean isFacingValid(byte aFacing)
/* 103:    */   {
/* 104: 62 */     return aFacing > 1;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isAccessAllowed(EntityPlayer aPlayer)
/* 108:    */   {
/* 109: 63 */     return true;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getProgresstime()
/* 113:    */   {
/* 114: 64 */     return this.mProgresstime;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int maxProgresstime()
/* 118:    */   {
/* 119: 65 */     return this.mMaxProgresstime;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int increaseProgress(int aProgress)
/* 123:    */   {
/* 124: 66 */     this.mProgresstime += aProgress;return this.mMaxProgresstime - this.mProgresstime;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean allowCoverOnSide(byte aSide, GT_ItemStack aCoverID)
/* 128:    */   {
/* 129: 70 */     return (GregTech_API.getCoverBehavior(aCoverID.toStack()).isSimpleCover()) && (super.allowCoverOnSide(aSide, aCoverID));
/* 130:    */   }
/* 131:    */   
/* 132:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 133:    */   {
/* 134: 75 */     return new GT_MetaTileEntity_BronzeBlastFurnace(this.mName);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void saveNBTData(NBTTagCompound aNBT)
/* 138:    */   {
/* 139: 80 */     aNBT.setInteger("mProgresstime", this.mProgresstime);
/* 140: 81 */     aNBT.setInteger("mMaxProgresstime", this.mMaxProgresstime);
/* 141: 82 */     if (this.mOutputItem1 != null)
/* 142:    */     {
/* 143: 83 */       NBTTagCompound tNBT = new NBTTagCompound();
/* 144: 84 */       this.mOutputItem1.writeToNBT(tNBT);
/* 145: 85 */       aNBT.setTag("mOutputItem1", tNBT);
/* 146:    */     }
/* 147: 87 */     if (this.mOutputItem2 != null)
/* 148:    */     {
/* 149: 88 */       NBTTagCompound tNBT = new NBTTagCompound();
/* 150: 89 */       this.mOutputItem2.writeToNBT(tNBT);
/* 151: 90 */       aNBT.setTag("mOutputItem2", tNBT);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void loadNBTData(NBTTagCompound aNBT)
/* 156:    */   {
/* 157: 96 */     this.mUpdate = 5;
/* 158: 97 */     this.mProgresstime = aNBT.getInteger("mProgresstime");
/* 159: 98 */     this.mMaxProgresstime = aNBT.getInteger("mMaxProgresstime");
/* 160: 99 */     this.mOutputItem1 = GT_Utility.loadItem(aNBT, "mOutputItem1");
/* 161:100 */     this.mOutputItem2 = GT_Utility.loadItem(aNBT, "mOutputItem2");
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer)
/* 165:    */   {
/* 166:105 */     if (aBaseMetaTileEntity.isClientSide()) {
/* 167:105 */       return true;
/* 168:    */     }
/* 169:106 */     aBaseMetaTileEntity.openGUI(aPlayer);
/* 170:107 */     return true;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 174:    */   {
/* 175:112 */     return new GT_Container_BronzeBlastFurnace(aPlayerInventory, aBaseMetaTileEntity);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/* 179:    */   {
/* 180:117 */     return new GT_GUIContainer_BronzeBlastFurnace(aPlayerInventory, aBaseMetaTileEntity);
/* 181:    */   }
/* 182:    */   
/* 183:    */   private boolean checkMachine()
/* 184:    */   {
/* 185:121 */     int xDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetZ;
/* 186:122 */     for (int i = -1; i < 2; i++) {
/* 187:122 */       for (int j = -1; j < 3; j++) {
/* 188:122 */         for (int k = -1; k < 2; k++) {
/* 189:122 */           if ((xDir + i != 0) || (j != 0) || (zDir + k != 0)) {
/* 190:123 */             if ((i != 0) || (j == -1) || (k != 0))
/* 191:    */             {
/* 192:124 */               if ((getBaseMetaTileEntity().getBlockOffset(xDir + i, j, zDir + k) != GregTech_API.sBlockCasings1) || (getBaseMetaTileEntity().getMetaIDOffset(xDir + i, j, zDir + k) != 10)) {
/* 193:124 */                 return false;
/* 194:    */               }
/* 195:    */             }
/* 196:126 */             else if ((!GT_Utility.arrayContains(getBaseMetaTileEntity().getBlockOffset(xDir + i, j, zDir + k), new Object[] { Blocks.lava, Blocks.flowing_lava, null })) && (!getBaseMetaTileEntity().getAirOffset(xDir + i, j, zDir + k))) {
/* 197:126 */               return false;
/* 198:    */             }
/* 199:    */           }
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:129 */     return true;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void onMachineBlockUpdate()
/* 207:    */   {
/* 208:134 */     this.mUpdate = 5;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTimer)
/* 212:    */   {
/* 213:139 */     if ((aBaseMetaTileEntity.isClientSide()) && 
/* 214:140 */       (aBaseMetaTileEntity.isActive())) {
/* 215:141 */       aBaseMetaTileEntity.getWorld().spawnParticle("largesmoke", aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1) + Math.random(), aBaseMetaTileEntity.getOffsetY(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1) + Math.random(), 0.0D, 0.3D, 0.0D);
/* 216:    */     }
/* 217:144 */     if (aBaseMetaTileEntity.isServerSide())
/* 218:    */     {
/* 219:145 */       if (this.mUpdate-- == 0) {
/* 220:146 */         this.mMachine = checkMachine();
/* 221:    */       }
/* 222:149 */       if (this.mMachine) {
/* 223:150 */         if (this.mMaxProgresstime > 0)
/* 224:    */         {
/* 225:151 */           if (++this.mProgresstime >= this.mMaxProgresstime)
/* 226:    */           {
						  addOutputProducts();
/* 228:153 */             this.mOutputItem1 = null;
/* 229:154 */             this.mOutputItem2 = null;
/* 230:155 */             this.mProgresstime = 0;
/* 231:156 */             this.mMaxProgresstime = 0;
/* 232:    */             try{GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "steel");}catch(Exception e){}
/* 227:152 */			}
/* 233:    */         }
/* 234:159 */         else if (aBaseMetaTileEntity.isAllowedToWork()) {
/* 235:159 */           checkRecipe();
/* 236:    */         }
/* 237:    */       }
/* 238:163 */       aBaseMetaTileEntity.setActive((this.mMaxProgresstime > 0) && (this.mMachine));
/* 239:165 */       if (aBaseMetaTileEntity.isActive())
/* 240:    */       {
/* 241:166 */         if (aBaseMetaTileEntity.getAir(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1)))
/* 242:    */         {
/* 243:167 */           aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1), Blocks.lava, 1, 2);
/* 244:168 */           this.mUpdate = 1;
/* 245:    */         }
/* 246:170 */         if (aBaseMetaTileEntity.getAir(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord() + 1, aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1)))
/* 247:    */         {
/* 248:171 */           aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord() + 1, aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1), Blocks.lava, 1, 2);
/* 249:172 */           this.mUpdate = 1;
/* 250:    */         }
/* 251:    */       }
/* 252:    */       else
/* 253:    */       {
/* 254:175 */         if (aBaseMetaTileEntity.getBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1)) == Blocks.lava)
/* 255:    */         {
/* 256:176 */           aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord(), aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1), Blocks.air, 0, 2);
/* 257:177 */           this.mUpdate = 1;
/* 258:    */         }
/* 259:179 */         if (aBaseMetaTileEntity.getBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord() + 1, aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1)) == Blocks.lava)
/* 260:    */         {
/* 261:180 */           aBaseMetaTileEntity.getWorld().setBlock(aBaseMetaTileEntity.getOffsetX(aBaseMetaTileEntity.getBackFacing(), 1), aBaseMetaTileEntity.getYCoord() + 1, aBaseMetaTileEntity.getOffsetZ(aBaseMetaTileEntity.getBackFacing(), 1), Blocks.air, 0, 2);
/* 262:181 */           this.mUpdate = 1;
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   private void addOutputProducts()
/* 269:    */   {
/* 270:188 */     if (this.mOutputItem1 != null) {
/* 271:189 */       if (this.mInventory[2] == null) {
/* 272:190 */         this.mInventory[2] = GT_Utility.copy(new Object[] { this.mOutputItem1 });
/* 273:191 */       } else if (GT_Utility.areStacksEqual(this.mInventory[2], this.mOutputItem1)) {
/* 274:192 */         this.mInventory[2].stackSize = Math.min(this.mOutputItem1.getMaxStackSize(), this.mOutputItem1.stackSize + this.mInventory[2].stackSize);
/* 275:    */       }
/* 276:    */     }
/* 277:194 */     if (this.mOutputItem2 != null) {
/* 278:195 */       if (this.mInventory[3] == null) {
/* 279:196 */         this.mInventory[3] = GT_Utility.copy(new Object[] { this.mOutputItem2 });
/* 280:197 */       } else if (GT_Utility.areStacksEqual(this.mInventory[3], this.mOutputItem2)) {
/* 281:198 */         this.mInventory[3].stackSize = Math.min(this.mOutputItem2.getMaxStackSize(), this.mOutputItem2.stackSize + this.mInventory[3].stackSize);
/* 282:    */       }
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   private boolean spaceForOutput(ItemStack aStack1, ItemStack aStack2)
/* 287:    */   {
/* 288:202 */     if (((this.mInventory[2] == null) || (aStack1 == null) || ((this.mInventory[2].stackSize + aStack1.stackSize <= this.mInventory[2].getMaxStackSize()) && (GT_Utility.areStacksEqual(this.mInventory[2], aStack1)))) && (
/* 289:203 */       (this.mInventory[3] == null) || (aStack2 == null) || ((this.mInventory[3].stackSize + aStack2.stackSize <= this.mInventory[3].getMaxStackSize()) && (GT_Utility.areStacksEqual(this.mInventory[3], aStack2))))) {
/* 290:204 */       return true;
/* 291:    */     }
/* 292:205 */     return false;
/* 293:    */   }
/* 294:    */   
/* 295:    */   private boolean checkRecipe()
/* 296:    */   {
/* 297:209 */     if (!this.mMachine) {
/* 298:209 */       return false;
/* 299:    */     }
/* 300:211 */     if ((this.mInventory[0] != null) && (this.mInventory[1] != null) && (this.mInventory[0].stackSize >= 1)) {
/* 301:212 */       if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[0], "dustIron")) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[0], "ingotIron")))
/* 302:    */       {
/* 303:213 */         if ((this.mInventory[1].getItem() == Items.coal) && (this.mInventory[1].stackSize >= 4) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 4L))))
/* 304:    */         {
/* 305:214 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 306:215 */           getBaseMetaTileEntity().decrStackSize(1, 4);
/* 307:216 */           this.mMaxProgresstime = 7200;
/* 308:217 */           return true;
/* 309:    */         }
/* 310:219 */         if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "fuelCoke")) && (this.mInventory[1].stackSize >= 2) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 4L))))
/* 311:    */         {
/* 312:220 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 313:221 */           getBaseMetaTileEntity().decrStackSize(1, 2);
/* 314:222 */           this.mMaxProgresstime = 4800;
/* 315:223 */           return true;
/* 316:    */         }
/* 317:225 */         if ((this.mInventory[0].stackSize >= 9) && ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCoal")) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCharcoal"))) && (this.mInventory[1].stackSize >= 4) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 9L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 4L))))
/* 318:    */         {
/* 319:226 */           getBaseMetaTileEntity().decrStackSize(0, 9);
/* 320:227 */           getBaseMetaTileEntity().decrStackSize(1, 4);
/* 321:228 */           this.mMaxProgresstime = 64800;
/* 322:229 */           return true;
/* 323:    */         }
/* 324:    */       }
/* 325:231 */       else if (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[0], "dustSteel"))
/* 326:    */       {
/* 327:232 */         if ((this.mInventory[1].getItem() == Items.coal) && (this.mInventory[1].stackSize >= 2) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 2L))))
/* 328:    */         {
/* 329:233 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 330:234 */           getBaseMetaTileEntity().decrStackSize(1, 2);
/* 331:235 */           this.mMaxProgresstime = 3600;
/* 332:236 */           return true;
/* 333:    */         }
/* 334:238 */         if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "fuelCoke")) && (this.mInventory[1].stackSize >= 1) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 2L))))
/* 335:    */         {
/* 336:239 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 337:240 */           getBaseMetaTileEntity().decrStackSize(1, 1);
/* 338:241 */           this.mMaxProgresstime = 2400;
/* 339:242 */           return true;
/* 340:    */         }
/* 341:244 */         if ((this.mInventory[0].stackSize >= 9) && ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCoal")) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCharcoal"))) && (this.mInventory[1].stackSize >= 2) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 9L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 2L))))
/* 342:    */         {
/* 343:245 */           getBaseMetaTileEntity().decrStackSize(0, 9);
/* 344:246 */           getBaseMetaTileEntity().decrStackSize(1, 2);
/* 345:247 */           this.mMaxProgresstime = 32400;
/* 346:248 */           return true;
/* 347:    */         }
/* 348:    */       }
/* 349:250 */       else if (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[0], "blockIron"))
/* 350:    */       {
/* 351:251 */         if ((this.mInventory[1].getItem() == Items.coal) && (this.mInventory[1].stackSize >= 36) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 9L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 4L))))
/* 352:    */         {
/* 353:252 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 354:253 */           getBaseMetaTileEntity().decrStackSize(1, 36);
/* 355:254 */           this.mMaxProgresstime = 64800;
/* 356:255 */           return true;
/* 357:    */         }
/* 358:257 */         if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "fuelCoke")) && (this.mInventory[1].stackSize >= 18) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 9L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 4L))))
/* 359:    */         {
/* 360:258 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 361:259 */           getBaseMetaTileEntity().decrStackSize(1, 18);
/* 362:260 */           this.mMaxProgresstime = 43200;
/* 363:261 */           return true;
/* 364:    */         }
/* 365:263 */         if (((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCoal")) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[1], "blockCharcoal"))) && (this.mInventory[1].stackSize >= 4) && (spaceForOutput(this.mOutputItem1 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 9L), this.mOutputItem2 = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.DarkAsh, 4L))))
/* 366:    */         {
/* 367:264 */           getBaseMetaTileEntity().decrStackSize(0, 1);
/* 368:265 */           getBaseMetaTileEntity().decrStackSize(1, 4);
/* 369:266 */           this.mMaxProgresstime = 64800;
/* 370:267 */           return true;
/* 371:    */         }
/* 372:    */       }
/* 373:    */     }
/* 374:272 */     this.mOutputItem1 = null;
/* 375:273 */     this.mOutputItem2 = null;
/* 376:274 */     return false;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean isGivingInformation()
/* 380:    */   {
/* 381:279 */     return false;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 385:    */   {
/* 386:284 */     return aIndex > 1;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 390:    */   {
/* 391:289 */     if (aIndex < 2) {}
/* 392:289 */     return !GT_Utility.areStacksEqual(aStack, this.mInventory[0]);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public byte getTileEntityBaseType()
/* 396:    */   {
/* 397:294 */     return 0;
/* 398:    */   }
/* 399:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_BronzeBlastFurnace
 * JD-Core Version:    0.7.0.1
 */