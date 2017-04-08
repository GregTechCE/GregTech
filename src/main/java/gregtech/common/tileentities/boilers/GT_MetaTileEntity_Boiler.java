package gregtech.common.tileentities.boilers;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public abstract class GT_MetaTileEntity_Boiler extends GT_MetaTileEntity_BasicTank {

    public int mTemperature = 20;
    public int mProcessingEnergy = 0;
    public int mLossTimer = 0;
    public FluidStack mSteam = null;
    public boolean mHadNoWater = false;

    public GT_MetaTileEntity_Boiler(int aID, String aName, String aNameRegional, String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, 0, 4, aDescription, aTextures);
    }

    public GT_MetaTileEntity_Boiler(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 4, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        ITexture[] tmp = mTextures[aSide >= 2 ? aSide != aFacing ? 2 : ((byte) (aActive ? 4 : 3)) : aSide][aColorIndex + 1];
        //mTextures[(aSide==aFacing?(aActive?4:3):aSide==GT_Utility.getOppositeSide(aFacing)?2:aSide==0?0:aSide==1?1:2)][aColorIndex+1];
        if (aSide != aFacing && tmp.length == 2) {
            tmp = new ITexture[]{tmp[0]};
        }
        return tmp;
    }

    @Override
    public boolean isElectric() {
        return false;
    }

    @Override
    public boolean isPneumatic() {
        return false;
    }

    @Override
    public boolean isSteampowered() {
        return false;
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return true;
    }

    @Override
    public int getProgresstime() {
        return this.mTemperature;
    }

    @Override
    public int maxProgresstime() {
        return 500;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer, EnumHand hand) {
        if (aBaseMetaTileEntity.isClientSide()) {
            return true;
        }
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    @Override
    public boolean doesFillContainers() {
        return true;
    }

    @Override
    public boolean doesEmptyContainers() {
        return true;
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
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return GT_ModHandler.isWater(aFluid);
    }

    @Override
    public FluidStack getDrainableStack() {
        return this.mSteam;
    }

    @Override
    public FluidStack setDrainableStack(FluidStack aFluid) {
        this.mSteam = aFluid;
        return this.mSteam;
    }

    @Override
    public boolean allowCoverOnSide(byte aSide, GT_ItemStack aCover) {
        return GregTech_API.getCoverBehavior(aCover.toStack()).isSimpleCover();
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mLossTimer", this.mLossTimer);
        aNBT.setInteger("mTemperature", this.mTemperature);
        aNBT.setInteger("mProcessingEnergy", this.mProcessingEnergy);
        if (this.mSteam != null) {
            aNBT.setTag("mSteam", this.mSteam.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.mLossTimer = aNBT.getInteger("mLossTimer");
        this.mTemperature = aNBT.getInteger("mTemperature");
        this.mProcessingEnergy = aNBT.getInteger("mProcessingEnergy");
        this.mSteam = FluidStack.loadFluidStackFromNBT(aNBT.getCompoundTag("mSteam"));
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if ((aBaseMetaTileEntity.isServerSide()) && (aTick > 20L)) {
            if (this.mTemperature <= 20) {
                this.mTemperature = 20;
                this.mLossTimer = 0;
            }
            if (++this.mLossTimer > 40) {
                this.mTemperature -= 1;
                this.mLossTimer = 0;
            }
            if (getFluidAmount() != 0) {
                for (EnumFacing side : EnumFacing.VALUES) {
                    if (side.getIndex() != aBaseMetaTileEntity.getFrontFacing() && side != EnumFacing.DOWN) {
                        int drain = GT_Utility.fillFluidTank(
                                aBaseMetaTileEntity.getWorldObj(),
                                aBaseMetaTileEntity.getWorldPos(), side,
                                getDrainableStack());
                        if (drain != 0) {
                            drain(side, drain, true);
                            if (getFluidAmount() == 0)
                                break;
                        }
                    }
                }
            }
            if (aTick % 10L == 0L) {
                if (this.mTemperature > 100) {
                    if ((this.mFluid == null) || (!GT_ModHandler.isWater(this.mFluid)) || (this.mFluid.amount <= 0)) {
                        this.mHadNoWater = true;
                    } else {
                        if (this.mHadNoWater) {
                            aBaseMetaTileEntity.doExplosion(2048L);
                            return;
                        }
                        this.mFluid.amount -= 1;
                        if (this.mSteam == null) {
                            this.mSteam = GT_ModHandler.getSteam(150L);
                        } else if (GT_ModHandler.isSteam(this.mSteam)) {
                            this.mSteam.amount += 150;
                        } else {
                            this.mSteam = GT_ModHandler.getSteam(150L);
                        }
                    }
                } else {
                    this.mHadNoWater = false;
                }
            }
            if ((this.mSteam != null) &&
                    (this.mSteam.amount > 32000)) {
                sendSound((byte) 1);
                this.mSteam.amount = 24000;
            }
            if ((this.mProcessingEnergy <= 0) && (aBaseMetaTileEntity.isAllowedToWork()) &&
                    (this.mInventory[2] != null)) {
                if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dust.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dustImpure.get(Materials.Coal))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.crushed.get(Materials.Coal)))) {
                    this.mProcessingEnergy += 160;
                    aBaseMetaTileEntity.decrStackSize(2, 1);
                    if (aBaseMetaTileEntity.getRandomNumber(3) == 0) {
                        aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L));
                    }
                } else if (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Charcoal))) {
                    this.mProcessingEnergy += 160;
                    aBaseMetaTileEntity.decrStackSize(2, 1);
                    if (aBaseMetaTileEntity.getRandomNumber(3) == 0) {
                        aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
                    }
                } else if (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], "fuelCoke")) {
                    this.mProcessingEnergy += 640;
                    aBaseMetaTileEntity.decrStackSize(2, 1);
                    if (aBaseMetaTileEntity.getRandomNumber(2) == 0) {
                        aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Ash, 1L));
                    }
                } else if ((GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.gem.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dust.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.dustImpure.get(Materials.Lignite))) || (GT_OreDictUnificator.isItemStackInstanceOf(this.mInventory[2], OrePrefixes.crushed.get(Materials.Lignite)))) {
                    this.mProcessingEnergy += 120;
                    aBaseMetaTileEntity.decrStackSize(2, 1);
                    if (aBaseMetaTileEntity.getRandomNumber(8) == 0) {
                        aBaseMetaTileEntity.addStackToSlot(3, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L));
                    }
                }
            }
            if ((this.mTemperature < 1000) && (this.mProcessingEnergy > 0) && (aTick % 12L == 0L)) {
                this.mProcessingEnergy -= 2;
                this.mTemperature += 1;
            }
            aBaseMetaTileEntity.setActive(this.mProcessingEnergy > 0);
        }
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return false;
    }

    @Override
    public void doSound(byte aIndex, double aX, double aY, double aZ) {
        if (aIndex == 1) {
            GT_Utility.doSoundAtClient(GregTech_API.sSoundList.get(4), 2, 1.0F, aX, aY, aZ);
            for (int l = 0; l < 8; l++) {
                getBaseMetaTileEntity().getWorldObj().spawnParticle(EnumParticleTypes.SMOKE_LARGE, aX - 0.5D + (new XSTR()).nextFloat(), aY, aZ - 0.5D + (new XSTR()).nextFloat(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public int getCapacity() {
        return 16000;
    }
}
