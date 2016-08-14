package gregtech.common.gui;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_Regulator
        extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_Regulator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_Regulator(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/Regulator.png");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[0]), 120, 9, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[1]), 137, 9, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[2]), 155, 9, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[3]), 120, 26, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[4]), 137, 26, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[5]), 155, 26, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[6]), 120, 43, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[7]), 137, 43, 16448255);
        this.fontRendererObj.drawString(String.valueOf(((GT_Container_Regulator) this.mContainer).mTargetSlots[8]), 155, 43, 16448255);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
