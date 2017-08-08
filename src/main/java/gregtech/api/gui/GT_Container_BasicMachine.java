package gregtech.api.gui;

import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MetaTileEntityBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The Container I use for all my Basic Machines
 */
public class GT_Container_BasicMachine extends GT_Container_BasicTank {

    public boolean mFluidTransfer = false, mItemTransfer = false, mStuttering = false;

    public GT_Container_BasicMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(tileEntity, 0, 8, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(tileEntity, 0, 26, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Render(tileEntity, 2, 107, 63));

        int tStartIndex = ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).getInputSlot();

        switch (((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mInputSlotCount) {
            case 0:
                break;
            case 1:
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 25));
                break;
            case 2:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 25));
                break;
            case 3:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 25));
                break;
            case 4:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 34));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 34));
                break;
            case 5:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 34));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 34));
                break;
            case 6:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 16));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 34));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 34));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 34));
                break;
            case 7:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 17, 43));
                break;
            case 8:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 43));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 35, 43));
                break;
            default:
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 7));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 53, 25));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 17, 43));
                addSlotToContainer(new Slot(tileEntity, tStartIndex++, 35, 43));
                addSlotToContainer(new Slot(tileEntity, tStartIndex, 53, 43));
                break;
        }

        tStartIndex = ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).getOutputSlot();

        switch (((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mOutputItems.length) {
            case 0:
                break;
            case 1:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                break;
            case 2:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 25));
                break;
            case 3:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 25));
                break;
            case 4:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 34));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 34));
                break;
            case 5:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 34));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 34));
                break;
            case 6:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 16));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 34));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 34));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 34));
                break;
            case 7:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 43));
                break;
            case 8:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 43));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 43));
                break;
            default:
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 7));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 25));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 107, 43));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 125, 43));
                addSlotToContainer(new SlotOutput(tileEntity, tStartIndex++, 143, 43));
                break;
        }

        addSlotToContainer(new Slot(tileEntity, 1, 80, 63));
        addSlotToContainer(new Slot(tileEntity, 3, 125, 63));
        addSlotToContainer(new GT_Slot_Render(tileEntity, tStartIndex++, 53, 63));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, ClickType aShifthold, EntityPlayer aPlayer) {
        switch (aSlotIndex) {
            case 0:
                if (tileEntity.getMetaTileEntity() == null) return null;
                ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mFluidTransfer = !((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mFluidTransfer;
                return null;
            case 1:
                if (tileEntity.getMetaTileEntity() == null) return null;
                ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mItemTransfer = !((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mItemTransfer;
                return null;
            default:
                return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (tileEntity.isClientSide() || tileEntity.getMetaTileEntity() == null) return;

        mFluidTransfer = ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mFluidTransfer;
        mItemTransfer = ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mItemTransfer;
        mStuttering = ((MetaTileEntityBasicMachine) tileEntity.getMetaTileEntity()).mStuttering;

        Iterator<IContainerListener> var2 = this.listeners.iterator();
        while (var2.hasNext()) {
            IContainerListener var1 = var2.next();
            var1.sendProgressBarUpdate(this, 102, mFluidTransfer ? 1 : 0);
            var1.sendProgressBarUpdate(this, 103, mItemTransfer ? 1 : 0);
            var1.sendProgressBarUpdate(this, 104, mStuttering ? 1 : 0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 102:
                mFluidTransfer = par2 != 0;
                break;
            case 103:
                mItemTransfer = par2 != 0;
                break;
            case 104:
                mStuttering = par2 != 0;
                break;
        }
    }

}
