package gregtech.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GT_Entity_Arrow extends EntityArrow {

    private ItemStack arrowStack;

    public GT_Entity_Arrow(World worldIn) {
        super(worldIn);
    }

    public GT_Entity_Arrow(World worldIn, double x, double y, double z, ItemStack arrowStack) {
        super(worldIn, x, y, z);
        this.arrowStack = arrowStack;
    }

    public GT_Entity_Arrow(World worldIn, EntityLivingBase shooter, ItemStack arrowStack) {
        super(worldIn, shooter);
        this.arrowStack = arrowStack;
    }

    /* TODO
    public void onUpdate() {
        onEntityUpdate();
        if ((this.mArrow == null) && (!this.worldObj.isRemote)) {
            setDead();
            return;
        }
        Entity tShootingEntity = this.shootingEntity;
        if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
            this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / 3.141592653589793D));
        }
        if (this.mTicksAlive++ == 3000) {
            setDead();
        }
        Block tBlock = this.worldObj.getBlock(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
        if (tBlock.getMaterial() != Material.air) {
            tBlock.setBlockBoundsBasedOnState(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
            AxisAlignedBB axisalignedbb = tBlock.getCollisionBoundingBoxFromPool(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
            if ((axisalignedbb != null) && (axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            this.arrowShake -= 1;
        }
        if (this.inGround) {
            int j = this.worldObj.getBlockMetadata(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
            if ((tBlock != this.mHitBlock) || (j != this.mHitBlockMeta)) {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2F;
                this.motionY *= this.rand.nextFloat() * 0.2F;
                this.motionZ *= this.rand.nextFloat() * 0.2F;
                this.mTicksAlive = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.ticksInAir += 1;
            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition tVector = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (tVector != null) {
                vec3 = Vec3.createVectorHelper(tVector.hitVec.xCoord, tVector.hitVec.yCoord, tVector.hitVec.zCoord);
            }
            Entity tHitEntity = null;
            List tAllPotentiallyHitEntities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double tLargestDistance = 1.7976931348623157E+308D;
            for (int i = 0; i < tAllPotentiallyHitEntities.size(); i++) {
                Entity entity1 = (Entity) tAllPotentiallyHitEntities.get(i);
                if ((entity1.canBeCollidedWith()) && ((entity1 != tShootingEntity) || (this.ticksInAir >= 5))) {
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(0.3D, 0.3D, 0.3D);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
                    if (movingobjectposition1 != null) {
                        double tDistance = vec31.distanceTo(movingobjectposition1.hitVec);
                        if (tDistance < tLargestDistance) {
                            tHitEntity = entity1;
                            tLargestDistance = tDistance;
                        }
                    }
                }
            }
            if (tHitEntity != null) {
                tVector = new MovingObjectPosition(tHitEntity);
            }
            if ((tVector != null) && ((tVector.entityHit instanceof EntityPlayer))) {
                EntityPlayer entityplayer = (EntityPlayer) tVector.entityHit;
                if ((entityplayer.capabilities.disableDamage) || (((tShootingEntity instanceof EntityPlayer)) && (!((EntityPlayer) tShootingEntity).canAttackPlayer(entityplayer)))) {
                    tVector = null;
                }
            }
            if (tVector != null) {
                if (tVector.entityHit != null) {
                    ItemData tData = GT_OreDictUnificator.getItemData(this.mArrow);


                    float tMagicDamage = (tVector.entityHit instanceof EntityLivingBase) ? EnchantmentHelper.func_152377_a(this.mArrow, ((EntityLivingBase) tVector.entityHit).getCreatureAttribute()) : 0.0F;
                    float tDamage = MathHelper.ceiling_double_int(MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * (getDamage() + ((tData != null) && (tData.mMaterial != null) && (tData.mMaterial.mMaterial != null) ? tData.mMaterial.mMaterial.mToolQuality / 2.0F - 1.0F : 0.0F)));
                    if (getIsCritical()) {
                        tDamage += this.rand.nextInt((int) (tDamage / 2.0D + 2.0D));
                    }
                    int tFireDamage = (isBurning() ? 5 : 0) + 4 * EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow);
                    int tKnockback = this.mKnockback + EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, this.mArrow);
                    int tHitTimer = -1;

                    int[] tDamages = onHitEntity(tVector.entityHit, tShootingEntity == null ? this : tShootingEntity, this.mArrow == null ? new ItemStack(Items.arrow, 1) : this.mArrow, (int) (tDamage * 2.0F), (int) (tMagicDamage * 2.0F), tKnockback, tFireDamage, tHitTimer);
                    if (tDamages != null) {
                        tDamage = tDamages[0] / 2.0F;
                        tMagicDamage = tDamages[1] / 2.0F;
                        tKnockback = tDamages[2];
                        tFireDamage = tDamages[3];
                        tHitTimer = tDamages[4];
                        if ((tFireDamage > 0) && (!(tVector.entityHit instanceof EntityEnderman))) {
                            tVector.entityHit.setFire(tFireDamage);
                        }
                        if ((!(tHitEntity instanceof EntityPlayer)) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, this.mArrow) > 0)) {
                            EntityPlayer tPlayer = null;
                            if ((this.worldObj instanceof WorldServer)) {
                                tPlayer = FakePlayerFactory.get((WorldServer) this.worldObj, new GameProfile(new UUID(0L, 0L), (tShootingEntity instanceof EntityLivingBase) ? ((EntityLivingBase) tShootingEntity).getCommandSenderName() : "Arrow"));
                            }
                            if (tPlayer != null) {
                                tPlayer.inventory.currentItem = 0;
                                tPlayer.inventory.setInventorySlotContents(0, getArrowItem());
                                tShootingEntity = tPlayer;
                                tPlayer.setDead();
                            }
                        }
                        DamageSource tDamageSource = DamageSource.causeArrowDamage(this, tShootingEntity == null ? this : tShootingEntity);
                        if ((tDamage + tMagicDamage > 0.0F) && (tVector.entityHit.attackEntityFrom(tDamageSource, tDamage + tMagicDamage))) {
                            if ((tVector.entityHit instanceof EntityLivingBase)) {
                                if (tHitTimer >= 0) {
                                    tVector.entityHit.hurtResistantTime = tHitTimer;
                                }
                                if (((tVector.entityHit instanceof EntityCreeper)) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow) > 0)) {
                                    ((EntityCreeper) tVector.entityHit).func_146079_cb();
                                }
                                EntityLivingBase tHitLivingEntity = (EntityLivingBase) tVector.entityHit;
                                if (!this.worldObj.isRemote) {
                                    tHitLivingEntity.setArrowCountInEntity(tHitLivingEntity.getArrowCountInEntity() + 1);
                                }
                                if (tKnockback > 0) {
                                    float tKnockbackDivider = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                    if (tKnockbackDivider > 0.0F) {
                                        tHitLivingEntity.addVelocity(this.motionX * tKnockback * 0.6000000238418579D / tKnockbackDivider, 0.1D, this.motionZ * tKnockback * 0.6000000238418579D / tKnockbackDivider);
                                    }
                                }
                                GT_Utility.GT_EnchantmentHelper.applyBullshitA(tHitLivingEntity, tShootingEntity == null ? this : tShootingEntity, this.mArrow);
                                GT_Utility.GT_EnchantmentHelper.applyBullshitB((tShootingEntity instanceof EntityLivingBase) ? (EntityLivingBase) tShootingEntity : null, tHitLivingEntity, this.mArrow);
                                if ((tShootingEntity != null) && (tHitLivingEntity != tShootingEntity) && ((tHitLivingEntity instanceof EntityPlayer)) && ((tShootingEntity instanceof EntityPlayerMP))) {
                                    ((EntityPlayerMP) tShootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                                }
                            }
                            if (((tShootingEntity instanceof EntityPlayer)) && (tMagicDamage > 0.0F)) {
                                ((EntityPlayer) tShootingEntity).onEnchantmentCritical(tVector.entityHit);
                            }
                            if ((!(tVector.entityHit instanceof EntityEnderman)) || (((EntityEnderman) tVector.entityHit).getActivePotionEffect(Potion.weakness) != null)) {
                                if (tFireDamage > 0) {
                                    tVector.entityHit.setFire(tFireDamage);
                                }
                                playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                                setDead();
                            }
                        } else {
                            this.motionX *= -0.1000000014901161D;
                            this.motionY *= -0.1000000014901161D;
                            this.motionZ *= -0.1000000014901161D;
                            this.rotationYaw += 180.0F;
                            this.prevRotationYaw += 180.0F;
                            this.ticksInAir = 0;
                        }
                    }
                } else {
                    this.mHitBlockX = tVector.blockX;
                    this.mHitBlockY = tVector.blockY;
                    this.mHitBlockZ = tVector.blockZ;
                    this.mHitBlock = this.worldObj.getBlock(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
                    this.mHitBlockMeta = this.worldObj.getBlockMetadata(this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ);
                    this.motionX = ((float) (tVector.hitVec.xCoord - this.posX));
                    this.motionY = ((float) (tVector.hitVec.yCoord - this.posY));
                    this.motionZ = ((float) (tVector.hitVec.zCoord - this.posZ));
                    float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / f2 * 0.0500000007450581D;
                    this.posY -= this.motionY / f2 * 0.0500000007450581D;
                    this.posZ -= this.motionZ / f2 * 0.0500000007450581D;
                    playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    setIsCritical(false);
                    if (this.mHitBlock.getMaterial() != Material.air) {
                        this.mHitBlock.onEntityCollidedWithBlock(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ, this);
                    }
                    if ((!this.worldObj.isRemote) && (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, this.mArrow) > 2)) {
                        GT_Utility.setCoordsOnFire(this.worldObj, this.mHitBlockX, this.mHitBlockY, this.mHitBlockZ, true);
                    }
                    if (breaksOnImpact()) {
                        setDead();
                    }
                }
            }
            if (getIsCritical()) {
                for (int i = 0; i < 4; i++) {
                    this.worldObj.spawnParticle("crit", this.posX + this.motionX * i / 4.0D, this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;

            this.rotationYaw = ((float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
            for (this.rotationPitch = ((float) (Math.atan2(this.motionY, MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
                this.prevRotationPitch += 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
                this.prevRotationYaw -= 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
                this.prevRotationYaw += 360.0F;
            }
            this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
            this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
            float tFrictionMultiplier = 0.99F;
            if (isInWater()) {
                for (int l = 0; l < 4; l++) {
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                }
                tFrictionMultiplier = 0.8F;
            }
            if (isWet()) {
                extinguish();
            }
            this.motionX *= tFrictionMultiplier;
            this.motionY *= tFrictionMultiplier;
            this.motionZ *= tFrictionMultiplier;
            this.motionY -= 0.0500000007450581D;
            setPosition(this.posX, this.posY, this.posZ);
            func_145775_I();
        }
    }*/

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if(arrowStack != null) {
            compound.setTag("Item", arrowStack.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("Item")) {
            arrowStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item"));
        }
    }

    public void setPickup(PickupStatus pickup) {
        pickupStatus = pickup;
    }

    public void setArrowStack(ItemStack arrowStack) {
        this.arrowStack = arrowStack;
    }

    @Override
    protected ItemStack getArrowStack() {
        return arrowStack;
    }

}