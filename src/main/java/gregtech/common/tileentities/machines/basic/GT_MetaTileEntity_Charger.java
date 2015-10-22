package gregtech.common.tileentities.machines.basic;

import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicBatteryBuffer;
import gregtech.api.util.GT_ModHandler;

import static gregtech.api.enums.GT_Values.V;

public class GT_MetaTileEntity_Charger extends GT_MetaTileEntity_BasicBatteryBuffer {

    public GT_MetaTileEntity_Charger(int aID, String aName, String aNameRegional, int aTier, String aDescription, int aSlotCount) {
        super(aID, aName, aNameRegional, aTier, aDescription, aSlotCount);
    }

    public GT_MetaTileEntity_Charger(String aName, int aTier, String aDescription, ITexture[][][] aTextures, int aSlotCount) {
        super(aName, aTier, aDescription, aTextures, aSlotCount);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Charger(mName, mTier, mDescription, mTextures, mInventory.length);
    }

    @Override
    public long getMinimumStoredEU() {
        return V[mTier] * 64 * mInventory.length;
    }

    @Override
    public long maxEUStore() {
        return V[mTier] * 256 * mInventory.length;
    }

    @Override
    public long maxAmperesIn() {
        return mChargeableCount * 8;
    }

    @Override
    public long maxAmperesOut() {
        return mBatteryCount * 4;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            super.onPostTick(aBaseMetaTileEntity, aTick);
            if (this.getBaseMetaTileEntity() instanceof BaseMetaTileEntity) {
                BaseMetaTileEntity mBaseMetaTileEntity = (BaseMetaTileEntity) getBaseMetaTileEntity();
                if (mBaseMetaTileEntity.getMetaTileEntity() instanceof MetaTileEntity) {
                    MetaTileEntity mMetaTileEntity = (MetaTileEntity) mBaseMetaTileEntity.getMetaTileEntity();
                    //for (int t = 0; t < 6; t++) {
                    if (mMetaTileEntity.dechargerSlotCount() > 0 && mBaseMetaTileEntity.getStoredEU() < mBaseMetaTileEntity.getEUCapacity()) {
                        for (int i = mMetaTileEntity.dechargerSlotStartIndex(), k = mMetaTileEntity.dechargerSlotCount() + i; i < k; i++) {
                            if (mMetaTileEntity.mInventory[i] != null && mBaseMetaTileEntity.getStoredEU() < mBaseMetaTileEntity.getEUCapacity()) {
                                mBaseMetaTileEntity.increaseStoredEnergyUnits(GT_ModHandler.dischargeElectricItem(mMetaTileEntity.mInventory[i], (int) Math.min(V[mTier] * 15, mBaseMetaTileEntity.getEUCapacity() - mBaseMetaTileEntity.getStoredEU()), (int) Math.min(Integer.MAX_VALUE, mMetaTileEntity.getInputTier()), true, false, false), true);
                                if (mMetaTileEntity.mInventory[i].stackSize <= 0)
                                    mMetaTileEntity.mInventory[i] = null;
                            }
                        }
                    }
                    if (mMetaTileEntity.rechargerSlotCount() > 0 && mBaseMetaTileEntity.getStoredEU() > 0) {
                        for (int i = mMetaTileEntity.rechargerSlotStartIndex(), k = mMetaTileEntity.rechargerSlotCount() + i; i < k; i++) {
                            if (mBaseMetaTileEntity.getStoredEU() > 0 && mMetaTileEntity.mInventory[i] != null) {
                                mBaseMetaTileEntity.decreaseStoredEU(GT_ModHandler.chargeElectricItem(mMetaTileEntity.mInventory[i], (int) Math.min(V[this.mTier] * 15, mBaseMetaTileEntity.getStoredEU()), (int) Math.min(Integer.MAX_VALUE, mMetaTileEntity.getOutputTier()), true, false), true);
                                if (mMetaTileEntity.mInventory[i].stackSize <= 0)
                                    mMetaTileEntity.mInventory[i] = null;
                            }
                        }
                        //}
                    }
                }
            }
        }
    }
}
