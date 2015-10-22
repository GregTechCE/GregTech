package gregtech.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.gui.GT_Slot_Render;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.storage.GT_MetaTileEntity_QuantumChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import java.util.Iterator;

public class GT_Container_QuantumChest extends GT_ContainerMetaTile_Machine {

    public int mContent = 0;

    public GT_Container_QuantumChest(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity, 0, 80, 17));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 1, 80, 53));
        addSlotToContainer(new GT_Slot_Render(mTileEntity, 2, 59, 42));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
        if (mTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_QuantumChest) {
            mContent = ((GT_MetaTileEntity_QuantumChest) mTileEntity.getMetaTileEntity()).mItemCount;
        } else {
            mContent = 0;
        }

        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting) var2.next();
            var1.sendProgressBarUpdate(this, 100, mContent & 65535);
            var1.sendProgressBarUpdate(this, 101, mContent >>> 16);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 100:
                mContent = mContent & -65536 | par2;
                break;
            case 101:
                mContent = mContent & 65535 | par2 << 16;
                break;
        }
    }

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }
}
