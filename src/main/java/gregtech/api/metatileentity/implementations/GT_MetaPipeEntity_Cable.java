package gregtech.api.metatileentity.implementations;

import cofh.api.energy.IEnergyReceiver;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.metatileentity.IMetaTileEntityCable;
import gregtech.api.interfaces.tileentity.IColoredTileEntity;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.MetaPipeEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;

import static gregtech.api.enums.GT_Values.VN;

public class GT_MetaPipeEntity_Cable extends MetaPipeEntity implements IMetaTileEntityCable {
    public final float mThickNess;
    public final Materials mMaterial;
    public final long mCableLossPerMeter, mAmperage, mVoltage;
    public final boolean mInsulated, mCanShock;
    public long mTransferredAmperage = 0, mTransferredAmperageLast20 = 0, mTransferredVoltageLast20 = 0;
    public long mRestRF;
    public short mOverheat;

    public GT_MetaPipeEntity_Cable(int aID, String aName, String aNameRegional, float aThickNess, Materials aMaterial, long aCableLossPerMeter, long aAmperage, long aVoltage, boolean aInsulated, boolean aCanShock) {
        super(aID, aName, aNameRegional, 0);
        mThickNess = aThickNess;
        mMaterial = aMaterial;
        mAmperage = aAmperage;
        mVoltage = aVoltage;
        mInsulated = aInsulated;
        mCanShock = aCanShock;
        mCableLossPerMeter = aCableLossPerMeter;
    }

    public GT_MetaPipeEntity_Cable(String aName, float aThickNess, Materials aMaterial, long aCableLossPerMeter, long aAmperage, long aVoltage, boolean aInsulated, boolean aCanShock) {
        super(aName, 0);
        mThickNess = aThickNess;
        mMaterial = aMaterial;
        mAmperage = aAmperage;
        mVoltage = aVoltage;
        mInsulated = aInsulated;
        mCanShock = aCanShock;
        mCableLossPerMeter = aCableLossPerMeter;
    }

    @Override
    public byte getTileEntityBaseType() {
        return (byte) (mInsulated ? 9 : 8);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaPipeEntity_Cable(mName, mThickNess, mMaterial, mCableLossPerMeter, mAmperage, mVoltage, mInsulated, mCanShock);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aConnections, byte aColorIndex, boolean aConnected, boolean aRedstone) {
        if (!mInsulated)
            return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa)};
        if (aConnected) {
            float tThickNess = getThickNess();
            if (tThickNess < 0.37F)
                return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa), new GT_RenderedTexture(Textures.BlockIcons.INSULATION_TINY, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
            if (tThickNess < 0.49F)
                return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa), new GT_RenderedTexture(Textures.BlockIcons.INSULATION_SMALL, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
            if (tThickNess < 0.74F)
                return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa), new GT_RenderedTexture(Textures.BlockIcons.INSULATION_MEDIUM, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
            if (tThickNess < 0.99F)
                return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa), new GT_RenderedTexture(Textures.BlockIcons.INSULATION_LARGE, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
            return new ITexture[]{new GT_RenderedTexture(mMaterial.mIconSet.mTextures[TextureSet.INDEX_wire], mMaterial.mRGBa), new GT_RenderedTexture(Textures.BlockIcons.INSULATION_HUGE, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
        }
        return new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.INSULATION_FULL, Dyes.getModulation(aColorIndex, Dyes.CABLE_INSULATION.mRGBa))};
    }

    @Override
    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity aEntity) {
        if (mCanShock && (((BaseMetaPipeEntity) getBaseMetaTileEntity()).mConnections & -128) == 0 && aEntity instanceof EntityLivingBase && !isCoverOnSide((BaseMetaPipeEntity) getBaseMetaTileEntity(), (EntityLivingBase) aEntity))
            GT_Utility.applyElectricityDamage((EntityLivingBase) aEntity, mTransferredVoltageLast20, mTransferredAmperageLast20);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        if (!mCanShock) return super.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
        return AxisAlignedBB.getBoundingBox(aX + 0.125D, aY + 0.125D, aZ + 0.125D, aX + 0.875D, aY + 0.875D, aZ + 0.875D);
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isFacingValid(byte aFacing) {
        return false;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return true;
    }

    @Override
    public final boolean renderInside(byte aSide) {
        return false;
    }

    @Override
    public int getProgresstime() {
        return (int) mTransferredAmperage * 64;
    }

    @Override
    public int maxProgresstime() {
        return (int) mAmperage * 64;
    }

    @Override
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
        if (!getBaseMetaTileEntity().getCoverBehaviorAtSide(aSide).letsEnergyIn(aSide, getBaseMetaTileEntity().getCoverIDAtSide(aSide), getBaseMetaTileEntity().getCoverDataAtSide(aSide), getBaseMetaTileEntity()))
            return 0;
        return transferElectricity(aSide, aVoltage, aAmperage, new ArrayList<TileEntity>(Arrays.asList((TileEntity) getBaseMetaTileEntity())));
    }

    @Override
    public long transferElectricity(byte aSide, long aVoltage, long aAmperage, ArrayList<TileEntity> aAlreadyPassedTileEntityList) {
        long rUsedAmperes = 0;
        aVoltage -= mCableLossPerMeter;
        if (aVoltage > 0) for (byte i = 0; i < 6 && aAmperage > rUsedAmperes; i++)
            if (i != aSide && (mConnections & (1 << i)) != 0 && getBaseMetaTileEntity().getCoverBehaviorAtSide(i).letsEnergyOut(i, getBaseMetaTileEntity().getCoverIDAtSide(i), getBaseMetaTileEntity().getCoverDataAtSide(i), getBaseMetaTileEntity())) {
                TileEntity tTileEntity = getBaseMetaTileEntity().getTileEntityAtSide(i);
                if (!aAlreadyPassedTileEntityList.contains(tTileEntity)) {
                    aAlreadyPassedTileEntityList.add(tTileEntity);
                    if (tTileEntity instanceof IEnergyConnected) {
                        if (getBaseMetaTileEntity().getColorization() >= 0) {
                            byte tColor = ((IEnergyConnected) tTileEntity).getColorization();
                            if (tColor >= 0 && tColor != getBaseMetaTileEntity().getColorization()) continue;
                        }
                        if (tTileEntity instanceof IGregTechTileEntity && ((IGregTechTileEntity) tTileEntity).getMetaTileEntity() instanceof IMetaTileEntityCable && ((IGregTechTileEntity) tTileEntity).getCoverBehaviorAtSide(GT_Utility.getOppositeSide(i)).letsEnergyIn(GT_Utility.getOppositeSide(i), ((IGregTechTileEntity) tTileEntity).getCoverIDAtSide(GT_Utility.getOppositeSide(i)), ((IGregTechTileEntity) tTileEntity).getCoverDataAtSide(GT_Utility.getOppositeSide(i)), ((IGregTechTileEntity) tTileEntity))) {
                            if (((IGregTechTileEntity) tTileEntity).getTimer() > 50)
                                rUsedAmperes += ((IMetaTileEntityCable) ((IGregTechTileEntity) tTileEntity).getMetaTileEntity()).transferElectricity(GT_Utility.getOppositeSide(i), aVoltage, aAmperage - rUsedAmperes, aAlreadyPassedTileEntityList);
                        } else {
                            rUsedAmperes += ((IEnergyConnected) tTileEntity).injectEnergyUnits(GT_Utility.getOppositeSide(i), aVoltage, aAmperage - rUsedAmperes);
                        }
//        		} else if (tTileEntity instanceof IEnergySink) {
//            		ForgeDirection tDirection = ForgeDirection.getOrientation(i).getOpposite();
//            		if (((IEnergySink)tTileEntity).acceptsEnergyFrom((TileEntity)getBaseMetaTileEntity(), tDirection)) {
//            			if (((IEnergySink)tTileEntity).demandedEnergyUnits() > 0 && ((IEnergySink)tTileEntity).injectEnergyUnits(tDirection, aVoltage) < aVoltage) rUsedAmperes++;
//            		}
                    } else if (tTileEntity instanceof IEnergySink) {
                        ForgeDirection tDirection = ForgeDirection.getOrientation(i).getOpposite();
                        if (((IEnergySink) tTileEntity).acceptsEnergyFrom((TileEntity) getBaseMetaTileEntity(), tDirection)) {
                            if (((IEnergySink) tTileEntity).getDemandedEnergy() > 0 && ((IEnergySink) tTileEntity).injectEnergy(tDirection, aVoltage, aVoltage) < aVoltage)
                                rUsedAmperes++;
                        }
                    } else if (GregTech_API.mOutputRF && tTileEntity instanceof IEnergyReceiver) {
                        ForgeDirection tDirection = ForgeDirection.getOrientation(i).getOpposite();
                        int rfOut = (int) (aVoltage * GregTech_API.mEUtoRF / 100);
                        if (((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, rfOut, true) == rfOut) {
                            ((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, rfOut, false);
                            rUsedAmperes++;
                        } else if (((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, rfOut, true) > 0) {
                            if (mRestRF == 0) {
                                int RFtrans = ((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, (int) rfOut, false);
                                rUsedAmperes++;
                                mRestRF = rfOut - RFtrans;
                            } else {
                                int RFtrans = ((IEnergyReceiver) tTileEntity).receiveEnergy(tDirection, (int) mRestRF, false);
                                mRestRF = mRestRF - RFtrans;
                            }
                        }
                        if (GregTech_API.mRFExplosions && ((IEnergyReceiver) tTileEntity).getMaxEnergyStored(tDirection) < rfOut * 600) {
                            if (rfOut > 32 * GregTech_API.mEUtoRF / 100) this.doExplosion(rfOut);
                        }
                    }
                }
            }
        mTransferredAmperage += rUsedAmperes;
        mTransferredVoltageLast20 = Math.max(mTransferredVoltageLast20, aVoltage);
        mTransferredAmperageLast20 = Math.max(mTransferredAmperageLast20, mTransferredAmperage);
        if (aVoltage > mVoltage || mTransferredAmperage > mAmperage) {
        	if(mOverheat>GT_Mod.gregtechproxy.mWireHeatingTicks * 100){
            getBaseMetaTileEntity().setToFire();}else{mOverheat +=100;}
            return aAmperage;
        }
        return rUsedAmperes;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            mTransferredAmperage = 0;
            if(mOverheat>0)mOverheat--;
            if (aTick % 20 == 0) {
                mTransferredVoltageLast20 = 0;
                mTransferredAmperageLast20 = 0;
                mConnections = 0;
                for (byte i = 0, j = 0; i < 6; i++) {
                    j = GT_Utility.getOppositeSide(i);
                    if (aBaseMetaTileEntity.getCoverBehaviorAtSide(i).alwaysLookConnected(i, aBaseMetaTileEntity.getCoverIDAtSide(i), aBaseMetaTileEntity.getCoverDataAtSide(i), aBaseMetaTileEntity) || aBaseMetaTileEntity.getCoverBehaviorAtSide(i).letsEnergyIn(i, aBaseMetaTileEntity.getCoverIDAtSide(i), aBaseMetaTileEntity.getCoverDataAtSide(i), aBaseMetaTileEntity) || aBaseMetaTileEntity.getCoverBehaviorAtSide(i).letsEnergyOut(i, aBaseMetaTileEntity.getCoverIDAtSide(i), aBaseMetaTileEntity.getCoverDataAtSide(i), aBaseMetaTileEntity)) {
                        TileEntity tTileEntity = aBaseMetaTileEntity.getTileEntityAtSide(i);
                        if (tTileEntity instanceof IColoredTileEntity) {
                            if (aBaseMetaTileEntity.getColorization() >= 0) {
                                byte tColor = ((IColoredTileEntity) tTileEntity).getColorization();
                                if (tColor >= 0 && tColor != aBaseMetaTileEntity.getColorization()) continue;
                            }
                        }
                        if (tTileEntity instanceof IEnergyConnected && (((IEnergyConnected) tTileEntity).inputEnergyFrom(j) || ((IEnergyConnected) tTileEntity).outputsEnergyTo(j))) {
                            mConnections |= (1 << i);
                            continue;
                        }
                        if (tTileEntity instanceof IGregTechTileEntity && ((IGregTechTileEntity) tTileEntity).getMetaTileEntity() instanceof IMetaTileEntityCable) {
                            if (((IGregTechTileEntity) tTileEntity).getCoverBehaviorAtSide(j).alwaysLookConnected(j, ((IGregTechTileEntity) tTileEntity).getCoverIDAtSide(j), ((IGregTechTileEntity) tTileEntity).getCoverDataAtSide(j), ((IGregTechTileEntity) tTileEntity)) || ((IGregTechTileEntity) tTileEntity).getCoverBehaviorAtSide(j).letsEnergyIn(j, ((IGregTechTileEntity) tTileEntity).getCoverIDAtSide(j), ((IGregTechTileEntity) tTileEntity).getCoverDataAtSide(j), ((IGregTechTileEntity) tTileEntity)) || ((IGregTechTileEntity) tTileEntity).getCoverBehaviorAtSide(j).letsEnergyOut(j, ((IGregTechTileEntity) tTileEntity).getCoverIDAtSide(j), ((IGregTechTileEntity) tTileEntity).getCoverDataAtSide(j), ((IGregTechTileEntity) tTileEntity))) {
                                mConnections |= (1 << i);
                                continue;
                            }
                        }
                        if (tTileEntity instanceof IEnergySink && ((IEnergySink) tTileEntity).acceptsEnergyFrom((TileEntity) aBaseMetaTileEntity, ForgeDirection.getOrientation(j))) {
                            mConnections |= (1 << i);
                            continue;
                        }
                        if (GregTech_API.mOutputRF && tTileEntity instanceof IEnergyReceiver && ((IEnergyReceiver) tTileEntity).canConnectEnergy(ForgeDirection.getOrientation(j))) {
                            mConnections |= (1 << i);
                            continue;
                        }
                        /*
					    if (tTileEntity instanceof IEnergyEmitter && ((IEnergyEmitter)tTileEntity).emitsEnergyTo((TileEntity)aBaseMetaTileEntity, ForgeDirection.getOrientation(j))) {
				    		mConnections |= (1<<i);
				    		continue;
					    }*/
                    }
                }
            }
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
    public String[] getDescription() {
        return new String[]{
                "Max Voltage: " + EnumChatFormatting.GREEN + mVoltage + " (" + VN[GT_Utility.getTier(mVoltage)] + ")" + EnumChatFormatting.GRAY,
                "Max Amperage: " + EnumChatFormatting.YELLOW + mAmperage + EnumChatFormatting.GRAY,
                "Loss/Meter/Ampere: " + EnumChatFormatting.RED + mCableLossPerMeter + EnumChatFormatting.GRAY + " EU-Volt"
        };
    }

    @Override
    public float getThickNess() {
        return mThickNess;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        //
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        //
    }
}