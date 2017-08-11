package gregtech.api.gui;

import gregtech.api.capability.internal.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMetaTileEntity extends ContainerGTTileEntity {

    public boolean isActive;
    public int maxProgressTime;
    public int progressTime;
    public long energyStored;
    public long energyStorage;
    public long inputVoltage;
    public long outputVoltage;
    public int displayErrorCode;

    public ContainerMetaTileEntity(InventoryPlayer playerInventory, IGregTechTileEntity tileEntity) {
        super(playerInventory, tileEntity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        boolean newIsActive = tileEntity.isActive();
        int newMaxProgress = tileEntity.getMaxProgress();
        int newCurrentProgress = tileEntity.getProgress();
        long newEnergyStored = tileEntity.getUniversalEnergyStored();
        long newEnergyStorage = tileEntity.getUniversalEnergyCapacity();
        long newInputVoltage = tileEntity.getInputVoltage();
        long newOutputVoltage = tileEntity.getOutputVoltage();
        int newErrorDisplayCode = tileEntity.getErrorDisplayID();

        for(IContainerListener containerListener : listeners) {
            if(isActive != newIsActive) {
                containerListener.sendProgressBarUpdate(this, 0, newIsActive ? 1 : 0);
            }
            if(maxProgressTime != newMaxProgress) {
                containerListener.sendProgressBarUpdate(this, 1, newMaxProgress);
            }
            if(progressTime != newCurrentProgress) {
                containerListener.sendProgressBarUpdate(this, 2, newCurrentProgress);
            }
            if(displayErrorCode != newErrorDisplayCode) {
                containerListener.sendProgressBarUpdate(this, 3, newErrorDisplayCode);
            }
            if(energyStored != newEnergyStored) {
                sendLongUpdate(containerListener, 4, newEnergyStored);
            }
            if(energyStorage != newEnergyStorage) {
                sendLongUpdate(containerListener, 6, newEnergyStorage);
            }
            if(inputVoltage != newInputVoltage) {
                sendLongUpdate(containerListener, 8, newInputVoltage);
            }
            if(outputVoltage != newOutputVoltage) {
                sendLongUpdate(containerListener, 10, newOutputVoltage);
            }
        }

        this.isActive = newIsActive;
        this.maxProgressTime = newMaxProgress;
        this.progressTime = newCurrentProgress;
        this.energyStored = newEnergyStored;
        this.energyStorage = newEnergyStorage;
        this.inputVoltage = newInputVoltage;
        this.outputVoltage = newOutputVoltage;

    }

    protected void sendLongUpdate(IContainerListener listener, int varBaseIndex, long value) {
        listener.sendProgressBarUpdate(this, varBaseIndex, (int) (value >> 32));
        listener.sendProgressBarUpdate(this, varBaseIndex + 1, (int) value);
    }

    //adds bits to long
    protected long ladd(long value, int bits) {
        return value << 32 | bits & 0xFFFFFFFFL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int varIndex, int value) {
        switch (varIndex) {
            case 0: this.isActive = value == 1; break;
            case 1: this.maxProgressTime = value; break;
            case 2: this.progressTime = value; break;
            case 3: this.displayErrorCode = value; break;
            case 4: this.energyStored = value; break;
            case 5: this.energyStored = ladd(this.energyStored, value); break;
            case 6: this.energyStorage = value; break;
            case 7: this.energyStorage = ladd(this.energyStorage, value); break;
            case 8: this.inputVoltage = value; break;
            case 9: this.inputVoltage = ladd(this.inputVoltage, value); break;
            case 10: this.outputVoltage = value; break;
            case 11: this.outputVoltage = ladd(this.outputVoltage, value); break;
        }
    }

}