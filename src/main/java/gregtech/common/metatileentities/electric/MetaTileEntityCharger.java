package gregtech.common.metatileentities.electric;

import gregtech.api.capability.IElectricItem;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class MetaTileEntityCharger extends TieredMetaTileEntity {

    private final int inventorySize;

    public MetaTileEntityCharger(String metaTileEntityId, int tier, int inventorySize) {
        super(metaTileEntityId, tier);
        this.inventorySize = inventorySize;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityCharger(metaTileEntityId, getTier(), inventorySize);
    }

    @Override
    public void update() {
        super.update();
        if(!getWorld().isRemote && energyContainer.getEnergyStored() > 0) {
            long inputVoltage = Math.min(energyContainer.getInputVoltage(), energyContainer.getEnergyStored());
            long energyUsedUp = 0L;
            for(int i = 0; i < importItems.getSlots(); i++) {
                ItemStack batteryStack = importItems.getStackInSlot(i);
                IElectricItem electricItem = batteryStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
                if(electricItem != null && electricItem.charge(inputVoltage, getTier(), false, true) > 0) {
                    energyUsedUp += electricItem.charge(inputVoltage, getTier(), false, false);
                    importItems.setStackInSlot(i, batteryStack);
                    if(energyUsedUp >= energyContainer.getEnergyStored()) break;
                }
            }
            if(energyUsedUp > 0) {
                energyContainer.addEnergy(-energyUsedUp);
            }
        }
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(inventorySize) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                IElectricItem electricItem = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
                if(electricItem == null || electricItem.getTier() != getTier() ||
                    electricItem.charge(Long.MAX_VALUE, getTier(), false, true) == 0)
                    return stack; //why do i write these comments? because this line is too short while line above is long
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return getImportItems();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
            .label(6, 6, getMetaFullname())
            .squareOfSlots(importItems, 0, inventorySize, true, true, GuiTextures.SLOT, GuiTextures.CHARGER_OVERLAY)
            .bindPlayerInventory(entityPlayer.inventory)
            .build(getHolder(), entityPlayer);
    }
}
