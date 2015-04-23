/*   1:    */ package gregtech.common.entities;
/*   2:    */ 
/*   3:    */ import com.mojang.authlib.GameProfile;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.objects.ItemData;
/*   6:    */ import gregtech.api.objects.MaterialStack;
/*   7:    */ import gregtech.api.util.GT_OreDictUnificator;
/*   8:    */ import gregtech.api.util.GT_Utility;
/*   9:    */ import gregtech.api.util.GT_Utility.GT_EnchantmentHelper;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Random;
/*  12:    */ import java.util.UUID;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.material.Material;
/*  15:    */ import net.minecraft.enchantment.Enchantment;
/*  16:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.EntityLivingBase;
/*  19:    */ import net.minecraft.entity.monster.EntityCreeper;
/*  20:    */ import net.minecraft.entity.monster.EntityEnderman;
/*  21:    */ import net.minecraft.entity.player.EntityPlayer;
/*  22:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  23:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  24:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  25:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  26:    */ import net.minecraft.init.Blocks;
/*  27:    */ import net.minecraft.init.Items;
/*  28:    */ import net.minecraft.item.ItemStack;
/*  29:    */ import net.minecraft.nbt.NBTTagCompound;
/*  30:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  31:    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*  32:    */ import net.minecraft.potion.Potion;
/*  33:    */ import net.minecraft.util.AxisAlignedBB;
/*  34:    */ import net.minecraft.util.DamageSource;
/*  35:    */ import net.minecraft.util.MathHelper;
/*  36:    */ import net.minecraft.util.MovingObjectPosition;
/*  37:    */ import net.minecraft.util.Vec3;
/*  38:    */ import net.minecraft.world.World;
/*  39:    */ import net.minecraft.world.WorldServer;
/*  40:    */ import net.minecraftforge.common.util.FakePlayerFactory;
/*  41:    */ 
/*  42:    */ public class GT_Entity_Arrow
/*  43:    */   extends EntityArrow
/*  44:    */ {
/*  45: 36 */   private int mHitBlockX = -1;
/*  46: 37 */   private int mHitBlockY = -1;
/*  47: 38 */   private int mHitBlockZ = -1;
/*  48: 39 */   private Block mHitBlock = Blocks.air;
/*  49: 40 */   private int mHitBlockMeta = 0;
/*  50: 41 */   private boolean inGround = false;
/*  51: 42 */   private int mTicksAlive = 0;
/*  52: 43 */   private int ticksInAir = 0;
/*  53: 44 */   private int mKnockback = 0;
/*  54: 46 */   private ItemStack mArrow = null;
/*  55:    */   
/*  56:    */   public GT_Entity_Arrow(World aWorld)
/*  57:    */   {
/*  58: 49 */     super(aWorld);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public GT_Entity_Arrow(World aWorld, double aX, double aY, double aZ)
/*  62:    */   {
/*  63: 53 */     super(aWorld, aX, aY, aZ);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public GT_Entity_Arrow(World aWorld, EntityLivingBase aEntity, float aSpeed)
/*  67:    */   {
/*  68: 57 */     super(aWorld, aEntity, aSpeed);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public GT_Entity_Arrow(EntityArrow aArrow, ItemStack aStack)
/*  72:    */   {
/*  73: 61 */     super(aArrow.worldObj);
/*  74: 62 */     NBTTagCompound tNBT = new NBTTagCompound();
/*  75: 63 */     aArrow.writeToNBT(tNBT);
/*  76: 64 */     readFromNBT(tNBT);
/*  77: 65 */     setArrowItem(aStack);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void onUpdate()
/*  81:    */   {
/*  82: 70 */     onEntityUpdate();
/*  83: 71 */     if ((this.mArrow == null) && (!this.worldObj.isRemote))
/*  84:    */     {
/*  85: 72 */       setDead();
/*  86: 73 */       return;
/*  87:    */     }
/*  88: 76 */     Entity tShootingEntity = this.shootingEntity;
/*  89: 78 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*  90:    */     {
/*  91: 79 */       float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  92: 80 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/*  93: 81 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / 3.141592653589793D));
/*  94:    */     }
/*  95: 84 */     if (this.mTicksAlive++ == 3000) {
/*  96: 84 */       setDead();
/*  97:    */     }
/*  98: 86 */     Block tBlock = this.worldObj.getBlock(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/*  99: 88 */     if (tBlock.getMaterial() != Material.air)
/* 100:    */     {
/* 101: 89 */       tBlock.setBlockBoundsBasedOnState(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/* 102: 90 */       AxisAlignedBB axisalignedbb = tBlock.getCollisionBoundingBoxFromPool(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/* 103: 91 */       if ((axisalignedbb != null) && (axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))) {
/* 104: 91 */         this.inGround = true;
/* 105:    */       }
/* 106:    */     }
/* 107: 94 */     if (this.arrowShake > 0) {
/* 108: 94 */       this.arrowShake -= 1;
/* 109:    */     }
/* 110: 96 */     if (this.inGround)
/* 111:    */     {
/* 112: 97 */       int j = this.worldObj.getBlockMetadata(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/* 113: 98 */       if ((tBlock != this.mHitBlock) || (j != this.mHitBlockMeta))
/* 114:    */       {
/* 115: 99 */         this.inGround = false;
/* 116:100 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 117:101 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 118:102 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 119:103 */         this.mTicksAlive = 0;
/* 120:104 */         this.ticksInAir = 0;
/* 121:    */       }
/* 122:    */     }
/* 123:    */     else
/* 124:    */     {
/* 125:107 */       this.ticksInAir += 1;
/* 126:108 */       Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
/* 127:109 */       Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 128:110 */       MovingObjectPosition tVector = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
/* 129:111 */       vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
/* 130:112 */       vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 131:114 */       if (tVector != null) {
/* 132:114 */         vec3 = Vec3.createVectorHelper(tVector.hitVec.xCoord, tVector.hitVec.yCoord, tVector.hitVec.zCoord);
/* 133:    */       }
/* 134:116 */       Entity tHitEntity = null;
/* 135:117 */       List tAllPotentiallyHitEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 136:118 */       double tLargestDistance = 1.7976931348623157E+308D;
/* 137:120 */       for (int i = 0; i < tAllPotentiallyHitEntities.size(); i++)
/* 138:    */       {
/* 139:121 */         Entity entity1 = (Entity)tAllPotentiallyHitEntities.get(i);
/* 140:123 */         if ((entity1.canBeCollidedWith()) && ((entity1 != tShootingEntity) || (this.ticksInAir >= 5)))
/* 141:    */         {
/* 142:124 */           AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(0.3D, 0.3D, 0.3D);
/* 143:125 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
/* 144:127 */           if (movingobjectposition1 != null)
/* 145:    */           {
/* 146:128 */             double tDistance = vec31.distanceTo(movingobjectposition1.hitVec);
/* 147:130 */             if (tDistance < tLargestDistance)
/* 148:    */             {
/* 149:131 */               tHitEntity = entity1;
/* 150:132 */               tLargestDistance = tDistance;
/* 151:    */             }
/* 152:    */           }
/* 153:    */         }
/* 154:    */       }
/* 155:138 */       if (tHitEntity != null) {
/* 156:138 */         tVector = new MovingObjectPosition(tHitEntity);
/* 157:    */       }
/* 158:140 */       if ((tVector != null) && (tVector.entityHit != null) && ((tVector.entityHit instanceof EntityPlayer)))
/* 159:    */       {
/* 160:141 */         EntityPlayer entityplayer = (EntityPlayer)tVector.entityHit;
/* 161:142 */         if ((entityplayer.capabilities.disableDamage) || (((tShootingEntity instanceof EntityPlayer)) && (!((EntityPlayer)tShootingEntity).canAttackPlayer(entityplayer)))) {
/* 162:142 */           tVector = null;
/* 163:    */         }
/* 164:    */       }
/* 165:145 */       if (tVector != null) {
/* 166:146 */         if (tVector.entityHit != null)
/* 167:    */         {
/* 168:147 */           ItemData tData = GT_OreDictUnificator.getItemData(this.mArrow);
/* 169:    */           
/* 170:    */ 
/* 171:150 */           float tMagicDamage = (tVector.entityHit instanceof EntityLivingBase) ? EnchantmentHelper.func_152377_a(this.mArrow, ((EntityLivingBase)tVector.entityHit).getCreatureAttribute()) : 0.0F;
/* 172:151 */           float tDamage = MathHelper.ceiling_double_int(MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * (getDamage() + ((tData != null) && (tData.mMaterial != null) && (tData.mMaterial.mMaterial != null) ? tData.mMaterial.mMaterial.mToolQuality / 2.0F - 1.0F : 0.0F)));
/* 173:153 */           if (getIsCritical()) {
/* 174:153 */             tDamage += this.rand.nextInt((int)(tDamage / 2.0D + 2.0D));
/* 175:    */           }
/* 176:156 */           int tFireDamage = (isBurning() ? 5 : 0) + 4 * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow);
/* 177:157 */           int tKnockback = this.mKnockback + EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, this.mArrow);
/* 178:158 */           int tHitTimer = -1;
/* 179:    */           
/* 180:160 */           int[] tDamages = onHitEntity(tVector.entityHit, tShootingEntity == null ? this : tShootingEntity, this.mArrow == null ? new ItemStack(Items.arrow, 1) : this.mArrow, (int)(tDamage * 2.0F), (int)(tMagicDamage * 2.0F), tKnockback, tFireDamage, tHitTimer);
/* 181:162 */           if (tDamages != null)
/* 182:    */           {
/* 183:163 */             tDamage = tDamages[0] / 2.0F;
/* 184:164 */             tMagicDamage = tDamages[1] / 2.0F;
/* 185:165 */             tKnockback = tDamages[2];
/* 186:166 */             tFireDamage = tDamages[3];
/* 187:167 */             tHitTimer = tDamages[4];
/* 188:169 */             if ((tFireDamage > 0) && (!(tVector.entityHit instanceof EntityEnderman))) {
/* 189:169 */               tVector.entityHit.setFire(tFireDamage);
/* 190:    */             }
/* 191:171 */             if ((!(tHitEntity instanceof EntityPlayer)) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, this.mArrow) > 0))
/* 192:    */             {
/* 193:172 */               EntityPlayer tPlayer = null;
/* 194:173 */               if ((this.worldObj instanceof WorldServer)) {
/* 195:173 */                 tPlayer = FakePlayerFactory.get((WorldServer)this.worldObj, new GameProfile(new UUID(0L, 0L), (tShootingEntity instanceof EntityLivingBase) ? ((EntityLivingBase)tShootingEntity).getCommandSenderName() : "Arrow"));
/* 196:    */               }
/* 197:174 */               if (tPlayer != null)
/* 198:    */               {
/* 199:175 */                 tPlayer.inventory.currentItem = 0;
/* 200:176 */                 tPlayer.inventory.setInventorySlotContents(0, getArrowItem());
/* 201:177 */                 tShootingEntity = tPlayer;
/* 202:178 */                 tPlayer.setDead();
/* 203:    */               }
/* 204:    */             }
/* 205:182 */             DamageSource tDamageSource = DamageSource.causeArrowDamage(this, tShootingEntity == null ? this : tShootingEntity);
/* 206:184 */             if ((tDamage + tMagicDamage > 0.0F) && (tVector.entityHit.attackEntityFrom(tDamageSource, tDamage + tMagicDamage)))
/* 207:    */             {
/* 208:185 */               if ((tVector.entityHit instanceof EntityLivingBase))
/* 209:    */               {
/* 210:186 */                 if (tHitTimer >= 0) {
/* 211:186 */                   tVector.entityHit.hurtResistantTime = tHitTimer;
/* 212:    */                 }
/* 213:188 */                 if (((tVector.entityHit instanceof EntityCreeper)) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow) > 0)) {
/* 214:188 */                   ((EntityCreeper)tVector.entityHit).func_146079_cb();
/* 215:    */                 }
/* 216:190 */                 EntityLivingBase tHitLivingEntity = (EntityLivingBase)tVector.entityHit;
/* 217:192 */                 if (!this.worldObj.isRemote) {
/* 218:192 */                   tHitLivingEntity.setArrowCountInEntity(tHitLivingEntity.getArrowCountInEntity() + 1);
/* 219:    */                 }
/* 220:194 */                 if (tKnockback > 0)
/* 221:    */                 {
/* 222:195 */                   float tKnockbackDivider = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 223:196 */                   if (tKnockbackDivider > 0.0F) {
/* 224:196 */                     tHitLivingEntity.addVelocity(this.motionX * tKnockback * 0.6000000238418579D / tKnockbackDivider, 0.1D, this.motionZ * tKnockback * 0.6000000238418579D / tKnockbackDivider);
/* 225:    */                   }
/* 226:    */                 }
/* 227:199 */                 GT_Utility.GT_EnchantmentHelper.applyBullshitA(tHitLivingEntity, tShootingEntity == null ? this : tShootingEntity, this.mArrow);
/* 228:200 */                 GT_Utility.GT_EnchantmentHelper.applyBullshitB((tShootingEntity instanceof EntityLivingBase) ? (EntityLivingBase)tShootingEntity : null, tHitLivingEntity, this.mArrow);
/* 229:202 */                 if ((tShootingEntity != null) && (tHitLivingEntity != tShootingEntity) && ((tHitLivingEntity instanceof EntityPlayer)) && ((tShootingEntity instanceof EntityPlayerMP))) {
/* 230:203 */                   ((EntityPlayerMP)tShootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
/* 231:    */                 }
/* 232:    */               }
/* 233:207 */               if (((tShootingEntity instanceof EntityPlayer)) && (tMagicDamage > 0.0F)) {
/* 234:207 */                 ((EntityPlayer)tShootingEntity).onEnchantmentCritical(tVector.entityHit);
/* 235:    */               }
/* 236:209 */               if ((!(tVector.entityHit instanceof EntityEnderman)) || (((EntityEnderman)tVector.entityHit).getActivePotionEffect(Potion.weakness) != null))
/* 237:    */               {
/* 238:210 */                 if (tFireDamage > 0) {
/* 239:210 */                   tVector.entityHit.setFire(tFireDamage);
/* 240:    */                 }
/* 241:211 */                 playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 242:212 */                 setDead();
/* 243:    */               }
/* 244:    */             }
/* 245:    */             else
/* 246:    */             {
/* 247:215 */               this.motionX *= -0.1000000014901161D;
/* 248:216 */               this.motionY *= -0.1000000014901161D;
/* 249:217 */               this.motionZ *= -0.1000000014901161D;
/* 250:218 */               this.rotationYaw += 180.0F;
/* 251:219 */               this.prevRotationYaw += 180.0F;
/* 252:220 */               this.ticksInAir = 0;
/* 253:    */             }
/* 254:    */           }
/* 255:    */         }
/* 256:    */         else
/* 257:    */         {
/* 258:224 */           this.mHitBlockX = tVector.blockX;
/* 259:225 */           this.mHitBlockY = tVector.blockY;
/* 260:226 */           this.mHitBlockZ = tVector.blockZ;
/* 261:227 */           this.mHitBlock = this.worldObj.getBlock(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/* 262:228 */           this.mHitBlockMeta = this.worldObj.getBlockMetadata(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
/* 263:229 */           this.motionX = ((float)(tVector.hitVec.xCoord - this.posX));
/* 264:230 */           this.motionY = ((float)(tVector.hitVec.yCoord - this.posY));
/* 265:231 */           this.motionZ = ((float)(tVector.hitVec.zCoord - this.posZ));
/* 266:232 */           float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 267:233 */           this.posX -= this.motionX / f2 * 0.0500000007450581D;
/* 268:234 */           this.posY -= this.motionY / f2 * 0.0500000007450581D;
/* 269:235 */           this.posZ -= this.motionZ / f2 * 0.0500000007450581D;
/* 270:236 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 271:237 */           this.inGround = true;
/* 272:238 */           this.arrowShake = 7;
/* 273:239 */           setIsCritical(false);
/* 274:241 */           if (this.mHitBlock.getMaterial() != Material.air) {
/* 275:241 */             this.mHitBlock.onEntityCollidedWithBlock(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ, this);
/* 276:    */           }
/* 277:243 */           if ((!this.worldObj.isRemote) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow) > 2)) {
/* 278:243 */             GT_Utility.setCoordsOnFire(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ, true);
/* 279:    */           }
/* 280:245 */           if (breaksOnImpact()) {
/* 281:245 */             setDead();
/* 282:    */           }
/* 283:    */         }
/* 284:    */       }
/* 285:249 */       if (getIsCritical()) {
/* 286:249 */         for (int i = 0; i < 4; i++) {
/* 287:249 */           this.worldObj.spawnParticle("crit", this.posX + this.motionX * i / 4.0D, this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
/* 288:    */         }
/* 289:    */       }
/* 290:251 */       this.posX += this.motionX;this.posY += this.motionY;this.posZ += this.motionZ;
/* 291:    */       
/* 292:253 */       this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
/* 293:255 */       for (this.rotationPitch = ((float)(Math.atan2(this.motionY, MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
/* 294:257 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 295:257 */         this.prevRotationPitch += 360.0F;
/* 296:    */       }
/* 297:258 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 298:258 */         this.prevRotationYaw -= 360.0F;
/* 299:    */       }
/* 300:259 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 301:259 */         this.prevRotationYaw += 360.0F;
/* 302:    */       }
/* 303:261 */       this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 304:262 */       this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 305:263 */       float tFrictionMultiplier = 0.99F;
/* 306:265 */       if (isInWater())
/* 307:    */       {
/* 308:266 */         for (int l = 0; l < 4; l++) {
/* 309:266 */           this.worldObj.spawnParticle("bubble", this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
/* 310:    */         }
/* 311:267 */         tFrictionMultiplier = 0.8F;
/* 312:    */       }
/* 313:270 */       if (isWet()) {
/* 314:270 */         extinguish();
/* 315:    */       }
/* 316:272 */       this.motionX *= tFrictionMultiplier;
/* 317:273 */       this.motionY *= tFrictionMultiplier;
/* 318:274 */       this.motionZ *= tFrictionMultiplier;
/* 319:275 */       this.motionY -= 0.0500000007450581D;
/* 320:276 */       setPosition(this.posX, this.posY, this.posZ);
/* 321:277 */       func_145775_I();
/* 322:    */     }
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void writeEntityToNBT(NBTTagCompound aNBT)
/* 326:    */   {
/* 327:283 */     super.writeEntityToNBT(aNBT);
/* 328:284 */     aNBT.setShort("xTile", (short)this.mHitBlockX);
/* 329:285 */     aNBT.setShort("yTile", (short)this.mHitBlockY);
/* 330:286 */     aNBT.setShort("zTile", (short)this.mHitBlockZ);
/* 331:287 */     aNBT.setShort("life", (short)this.mTicksAlive);
/* 332:288 */     aNBT.setByte("inTile", (byte)Block.getIdFromBlock(this.mHitBlock));
/* 333:289 */     aNBT.setByte("inData", (byte)this.mHitBlockMeta);
/* 334:290 */     aNBT.setByte("shake", (byte)this.arrowShake);
/* 335:291 */     aNBT.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 336:292 */     aNBT.setByte("pickup", (byte)this.canBePickedUp);
/* 337:293 */     aNBT.setDouble("damage", getDamage());
/* 338:294 */     aNBT.setTag("mArrow", this.mArrow == null ? null : this.mArrow.writeToNBT(new NBTTagCompound()));
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void readEntityFromNBT(NBTTagCompound aNBT)
/* 342:    */   {
/* 343:299 */     super.readEntityFromNBT(aNBT);
/* 344:300 */     this.mHitBlockX = aNBT.getShort("xTile");
/* 345:301 */     this.mHitBlockY = aNBT.getShort("yTile");
/* 346:302 */     this.mHitBlockZ = aNBT.getShort("zTile");
/* 347:303 */     this.mTicksAlive = aNBT.getShort("life");
/* 348:304 */     this.mHitBlock = Block.getBlockById(aNBT.getByte("inTile") & 0xFF);
/* 349:305 */     this.mHitBlockMeta = (aNBT.getByte("inData") & 0xFF);
/* 350:306 */     this.arrowShake = (aNBT.getByte("shake") & 0xFF);
/* 351:307 */     this.inGround = (aNBT.getByte("inGround") == 1);
/* 352:308 */     setDamage(aNBT.getDouble("damage"));
/* 353:309 */     this.canBePickedUp = aNBT.getByte("pickup");
/* 354:310 */     this.mArrow = GT_Utility.loadItem(aNBT, "mArrow");
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void onCollideWithPlayer(EntityPlayer aPlayer)
/* 358:    */   {
/* 359:315 */     if ((!this.worldObj.isRemote) && (this.inGround) && (this.arrowShake <= 0) && (this.canBePickedUp == 1) && (aPlayer.inventory.addItemStackToInventory(getArrowItem())))
/* 360:    */     {
/* 361:316 */       playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 362:317 */       aPlayer.onItemPickup(this, 1);
/* 363:318 */       setDead();
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public int[] onHitEntity(Entity aHitEntity, Entity aShootingEntity, ItemStack aArrow, int aRegularDamage, int aMagicDamage, int aKnockback, int aFireDamage, int aHitTimer)
/* 368:    */   {
/* 369:333 */     return new int[] { aRegularDamage, aMagicDamage, aKnockback, aFireDamage, aHitTimer };
/* 370:    */   }
/* 371:    */   
/* 372:    */   public void setArrowItem(ItemStack aStack)
/* 373:    */   {
/* 374:337 */     this.mArrow = GT_Utility.updateItemStack(GT_Utility.copyAmount(1L, new Object[] { aStack }));
/* 375:    */   }
/* 376:    */   
/* 377:    */   public ItemStack getArrowItem()
/* 378:    */   {
/* 379:341 */     return GT_Utility.copy(new Object[] { this.mArrow });
/* 380:    */   }
/* 381:    */   
/* 382:    */   public boolean breaksOnImpact()
/* 383:    */   {
/* 384:345 */     return false;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public void setKnockbackStrength(int aKnockback)
/* 388:    */   {
/* 389:350 */     this.mKnockback = aKnockback;
/* 390:    */   }
/* 391:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.entities.GT_Entity_Arrow
 * JD-Core Version:    0.7.0.1
 */