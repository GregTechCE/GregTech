package gregtech.common.gui;

import gregtech.api.gui.ContainerMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.boilers.GT_MetaTileEntity_Boiler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Container_Boiler
        extends ContainerMetaTileEntity {
    private int mSteamCapacity = 0;//FB: UR - UR_UNINIT_READ_CALLED_FROM_SUPER_CONSTRUCTOR
    public int mWaterAmount = 0;
    public int mSteamAmount = 0;
    public int mProcessingEnergy = 0;
    public int mTemperature = 2;
    public GT_Container_Boiler(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aSteamCapacity) {
        super(aInventoryPlayer, aTileEntity);
        this.mSteamCapacity = aSteamCapacity;
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(this.mTileEntity, 2, 116, 62));
        addSlotToContainer(new Slot(this.mTileEntity, 0, 44, 26));
        addSlotToContainer(new Slot(this.mTileEntity, 1, 44, 62));
        addSlotToContainer(new Slot(this.mTileEntity, 3, 116, 26));
    }

    @Override
    public int getSlotCount() {
        return 4;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
            return;
        }
        this.mTemperature = ((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mTemperature;
        this.mProcessingEnergy = ((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mProcessingEnergy;
        this.mSteamAmount = (((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mSteam == null ? 0 : ((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mSteam.amount);
        this.mWaterAmount = (((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mFluid == null ? 0 : ((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).mFluid.amount);

        this.mTemperature = Math.min(54, Math.max(0, this.mTemperature * 54 / (((GT_MetaTileEntity_Boiler) this.mTileEntity.getMetaTileEntity()).maxProgresstime() - 10)));
        this.mSteamAmount = Math.min(54, Math.max(0, this.mSteamAmount * 54 / (this.mSteamCapacity - 100)));
        this.mWaterAmount = Math.min(54, Math.max(0, this.mWaterAmount * 54 / 15900));
        this.mProcessingEnergy = Math.min(14, Math.max(this.mProcessingEnergy > 0 ? 1 : 0, this.mProcessingEnergy * 14 / 1000));

        for (IContainerListener var1 : this.listeners) {
            var1.sendProgressBarUpdate(this, 100, this.mTemperature);
            var1.sendProgressBarUpdate(this, 101, this.mProcessingEnergy);
            var1.sendProgressBarUpdate(this, 102, this.mSteamAmount);
            var1.sendProgressBarUpdate(this, 103, this.mWaterAmount);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                this.mTemperature = par2;
                break;
            case 101:
                this.mProcessingEnergy = par2;
                break;
            case 102:
                this.mSteamAmount = par2;
                break;
            case 103:
                this.mWaterAmount = par2;
        }
    }
}
