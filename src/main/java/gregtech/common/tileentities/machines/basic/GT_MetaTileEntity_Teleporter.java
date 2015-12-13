package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Utility;
import gregtech.common.gui.GT_Container_Teleporter;
import gregtech.common.gui.GT_GUIContainer_Teleporter;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;
import static gregtech.api.enums.GT_Values.V;

public class GT_MetaTileEntity_Teleporter extends GT_MetaTileEntity_BasicTank {

    public static boolean sInterDimensionalTeleportAllowed = true;
    public int mTargetX = 0;
    public int mTargetY = 0;
    public int mTargetZ = 0;
    public int mTargetD = 0;
    public boolean mDebug = false;
    public boolean hasEgg = false;

    public GT_MetaTileEntity_Teleporter(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 3, "Teleport long distances with this little device.");
    }

    public GT_MetaTileEntity_Teleporter(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    private static float weightCalculation(Entity aEntity) {
        try {
            if ((aEntity instanceof EntityFX)) {
                return -1.0F;
            }
        } catch (Throwable e) {
        }
        if ((aEntity instanceof EntityFishHook)) {
            return -1.0F;
        }
        if ((aEntity instanceof EntityDragonPart)) {
            return -1.0F;
        }
        if ((aEntity instanceof EntityWeatherEffect)) {
            return -1.0F;
        }
        if ((aEntity instanceof EntityPlayer)) {
            EntityPlayer tPlayer = (EntityPlayer) aEntity;
            int tCount = 64;
            for (int i = 0; i < 36; i++) {
                if (tPlayer.inventory.getStackInSlot(i) != null) {
                    tCount += (tPlayer.inventory.getStackInSlot(i).getMaxStackSize() > 1 ? tPlayer.inventory.getStackInSlot(i).stackSize : 64);
                }
            }
            for (int i = 0; i < 4; i++) {
                if (tPlayer.inventory.armorInventory[i] != null) {
                    tCount += 256;
                }
            }
            return Math.min(5.0F, tCount / 666.6F);
        }
        if (GT_Utility.getClassName(aEntity).equals("EntityItnt")) {
            return 5.0F;
        }
        if (GT_Utility.getClassName(aEntity).equals("EntityNuke")) {
            return 50.0F;
        }
        if ((aEntity instanceof EntityArrow)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityBoat)) {
            return 0.1F;
        }
        if ((aEntity instanceof EntityEnderCrystal)) {
            return 2.0F;
        }
        if ((aEntity instanceof EntityEnderEye)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityFireball)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityFireworkRocket)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityHanging)) {
            return 0.005F;
        }
        if ((aEntity instanceof EntityItem)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityLiving)) {
            return 0.5F;
        }
        if ((aEntity instanceof EntityMinecart)) {
            return 0.1F;
        }
        if ((aEntity instanceof EntityThrowable)) {
            return 0.001F;
        }
        if ((aEntity instanceof EntityTNTPrimed)) {
            return 5.0F;
        }
        if ((aEntity instanceof EntityXPOrb)) {
            return 0.001F;
        }
        return -1.0F;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        this.hasEgg = checkForEgg();
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_Teleporter(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_Teleporter(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Teleporter(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
    

    public String[] getInfoData() {
        return new String[]{"Coordinates:", "X: " + this.mTargetX, "Y: " + this.mTargetY, "Z: " + this.mTargetZ, "Dimension: " + this.mTargetD};
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], (aSide != this.getBaseMetaTileEntity().getFrontFacing()) ? null : aActive ? new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER_ACTIVE) : new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TELEPORTER)};
    }

    public void saveNBTData(NBTTagCompound aNBT) {
        if (mFluid != null) aNBT.setTag("mFluid", mFluid.writeToNBT(new NBTTagCompound()));
        aNBT.setInteger("mTargetX", this.mTargetX);
        aNBT.setInteger("mTargetY", this.mTargetY);
        aNBT.setInteger("mTargetZ", this.mTargetZ);
        aNBT.setInteger("mTargetD", this.mTargetD);
        aNBT.setBoolean("mDebug", this.mDebug);
    }

    public void loadNBTData(NBTTagCompound aNBT) {
        mFluid = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mFluid"));
        this.mTargetX = aNBT.getInteger("mTargetX");
        this.mTargetY = aNBT.getInteger("mTargetY");
        this.mTargetZ = aNBT.getInteger("mTargetZ");
        this.mTargetD = aNBT.getInteger("mTargetD");
        this.mDebug = aNBT.getBoolean("mDebug");
    }

    public void onConfigLoad(GT_Config aConfig) {
        sInterDimensionalTeleportAllowed = aConfig.get(ConfigCategories.machineconfig, "Teleporter.Interdimensional", true);
    }

    public void onFirstTick() {
        if (getBaseMetaTileEntity().isServerSide()) {
            if ((this.mTargetX == 0) && (this.mTargetY == 0) && (this.mTargetZ == 0) && (this.mTargetD == 0)) {
                this.mTargetX = getBaseMetaTileEntity().getXCoord();
                this.mTargetY = getBaseMetaTileEntity().getYCoord();
                this.mTargetZ = getBaseMetaTileEntity().getZCoord();
                this.mTargetD = getBaseMetaTileEntity().getWorld().provider.dimensionId;
            }
            this.hasEgg = checkForEgg();
        }
    }

    public boolean checkForEgg() {
        for (byte i = -5; i <= 5; i = (byte) (i + 1)) {
            for (byte j = -5; j <= 5; j = (byte) (j + 1)) {
                for (byte k = -5; k <= 5; k = (byte) (k + 1)) {
                    if (getBaseMetaTileEntity().getBlockOffset(i, j, k) == Blocks.dragon_egg) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasDimensionalTeleportCapability() {
        return (this.mDebug) || (this.hasEgg) || (mFluid.isFluidEqual(Materials.Nitrogen.getPlasma(1)) && mFluid.amount >= 1000);
    }

    public boolean isDimensionalTeleportAvailable() {
        return (this.mDebug) || ((hasDimensionalTeleportCapability()) && (GT_Utility.isRealDimension(this.mTargetD)) && (GT_Utility.isRealDimension(getBaseMetaTileEntity().getWorld().provider.dimensionId)));
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mFluid == null) {
            mFluid = Materials.Nitrogen.getPlasma(0);
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (getBaseMetaTileEntity().isServerSide()) {
            if (getBaseMetaTileEntity().getTimer() % 100L == 50L) {
                this.hasEgg = checkForEgg();
            }
            if ((getBaseMetaTileEntity().isAllowedToWork()) && (getBaseMetaTileEntity().getRedstone())) {
                if (getBaseMetaTileEntity().decreaseStoredEnergyUnits(2048, false)) {
                    if (hasDimensionalTeleportCapability() && this.mTargetD != getBaseMetaTileEntity().getWorld().provider.dimensionId && (hasEgg || mFluid.isFluidEqual(Materials.Nitrogen.getPlasma(1)))) {
                        mFluid.amount--;
                        if (mFluid.amount < 1) {
                            mFluid = null;
                        }
                    }
                    int tDistance = distanceCalculation();
                    if (mInventory[0] != null) {
                        TileEntity tTile = null;
                        if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
                            tTile = getBaseMetaTileEntity().getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
                        } else {
                            World tWorld = DimensionManager.getWorld(this.mTargetD);
                            if (tWorld != null) {
                                tTile = tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
                            }
                        }
                        if (tTile != null && tTile instanceof IInventory) {
                            int tStacksize = mInventory[0].stackSize;
                            GT_Utility.moveOneItemStack(this, tTile, (byte) 0, (byte) 0, null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
                            if (mInventory[0] == null || mInventory[0].stackSize < tStacksize) {
                                getBaseMetaTileEntity().decreaseStoredEnergyUnits((int) (tDistance * tDistance * (tStacksize - (mInventory[0] == null ? 0 : mInventory[0].stackSize))), false);
                            }
                        }

                    }
                    for (Object tObject : getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(getBaseMetaTileEntity().getOffsetX(getBaseMetaTileEntity().getFrontFacing(), 2) - 1, getBaseMetaTileEntity().getOffsetY(getBaseMetaTileEntity().getFrontFacing(), 2) - 1, getBaseMetaTileEntity().getOffsetZ(getBaseMetaTileEntity().getFrontFacing(), 2) - 1, getBaseMetaTileEntity().getOffsetX(getBaseMetaTileEntity().getFrontFacing(), 2) + 2, getBaseMetaTileEntity().getOffsetY(getBaseMetaTileEntity().getFrontFacing(), 2) + 2, getBaseMetaTileEntity().getOffsetZ(getBaseMetaTileEntity().getFrontFacing(), 2) + 2))) {
                        if (((tObject instanceof Entity)) && (!((Entity) tObject).isDead)) {
                            Entity tEntity = (Entity) tObject;
                            if (getBaseMetaTileEntity().decreaseStoredEnergyUnits((int) (tDistance * tDistance * weightCalculation(tEntity)), false)) {
                                if (hasDimensionalTeleportCapability() && this.mTargetD != getBaseMetaTileEntity().getWorld().provider.dimensionId && (hasEgg || mFluid.isFluidEqual(Materials.Nitrogen.getPlasma(1)))) {
                                    mFluid.amount = mFluid.amount - ((int) Math.min(1000, (tDistance * tDistance * weightCalculation(tEntity) / 8192)));
                                    if (mFluid.amount < 1) {
                                        mFluid = null;
                                    }
                                }
                                if (tEntity.ridingEntity != null) {
                                    tEntity.mountEntity(null);
                                }
                                if (tEntity.riddenByEntity != null) {
                                    tEntity.riddenByEntity.mountEntity(null);
                                }
                                if ((this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) || (!isDimensionalTeleportAvailable()) || (!GT_Utility.moveEntityToDimensionAtCoords(tEntity, this.mTargetD, this.mTargetX + 0.5D, this.mTargetY + 0.5D, this.mTargetZ + 0.5D))) {
                                    if ((tEntity instanceof EntityLivingBase)) {
                                        ((EntityLivingBase) tEntity).setPositionAndUpdate(this.mTargetX + 0.5D, this.mTargetY + 0.5D, this.mTargetZ + 0.5D);
                                    } else {
                                        tEntity.setPosition(this.mTargetX + 0.5D, this.mTargetY + 0.5D, this.mTargetZ + 0.5D);
                                    }
                                }
                            }
                        }
                    }
                }
                getBaseMetaTileEntity().setActive(true);
            } else {
                getBaseMetaTileEntity().setActive(false);
            }
        }
    }

    private int distanceCalculation() {
        return Math.abs(((this.mTargetD != getBaseMetaTileEntity().getWorld().provider.dimensionId) && (isDimensionalTeleportAvailable()) ? 100 : 1) * (int) Math.sqrt(Math.pow(getBaseMetaTileEntity().getXCoord() - this.mTargetX, 2.0D) + Math.pow(getBaseMetaTileEntity().getYCoord() - this.mTargetY, 2.0D) + Math.pow(getBaseMetaTileEntity().getZCoord() - this.mTargetZ, 2.0D)));
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isOverclockerUpgradable() {
        return false;
    }

    @Override
    public boolean isTransformerUpgradable() {
        return false;
    }

    @Override
    public boolean isElectric() {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }

    @Override
    public boolean isEnetInput() {
        return true;
    }

    @Override
    public boolean isInputFacing(byte aSide) {
        return true;
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return false;
    }

    @Override
    public boolean isTeleporterCompatible() {
        return false;
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier] * 16;
    }

    @Override
    public long maxEUStore() {
        return 100000000;
    }

    @Override
    public long maxEUInput() {
        return V[mTier];
    }

    @Override
    public long maxSteamStore() {
        return maxEUStore();
    }

    @Override
    public long maxAmperesIn() {
        return 2;
    }

    @Override
    public int getStackDisplaySlot() {
        return 2;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public int getInputSlot() {
        return 0;
    }

    @Override
    public int getOutputSlot() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 64000;
    }

    @Override
    public boolean doesFillContainers() {
        return false;
    }

    @Override
    public boolean doesEmptyContainers() {
        return false;
    }

    @Override
    public boolean canTankBeFilled() {
        return true;
    }

    @Override
    public boolean canTankBeEmptied() {
        return true;
    }

    @Override
    public boolean displaysItemStack() {
        return false;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        return null;
    }
}
