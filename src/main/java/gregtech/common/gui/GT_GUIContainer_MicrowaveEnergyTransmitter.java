package gregtech.common.gui;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GT_GUIContainer_MicrowaveEnergyTransmitter
        extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_MicrowaveEnergyTransmitter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_MicrowaveEnergyTransmitter(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "Teleporter.png");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString("Teleporter", 46, 8, 16448255);
        if (this.mContainer != null) {
            this.fontRendererObj.drawString("X: " + GT_Utility.parseNumberToString(((GT_Container_MicrowaveEnergyTransmitter) this.mContainer).mTargetX), 46, 16, 16448255);
            this.fontRendererObj.drawString("Y: " + GT_Utility.parseNumberToString(((GT_Container_MicrowaveEnergyTransmitter) this.mContainer).mTargetY), 46, 24, 16448255);
            this.fontRendererObj.drawString("Z: " + GT_Utility.parseNumberToString(((GT_Container_MicrowaveEnergyTransmitter) this.mContainer).mTargetZ), 46, 32, 16448255);
            if (((GT_Container_MicrowaveEnergyTransmitter) this.mContainer).mEgg > 0) {
                this.fontRendererObj.drawString("Dim: " + GT_Utility.parseNumberToString(((GT_Container_MicrowaveEnergyTransmitter) this.mContainer).mTargetD), 46, 40, 16448255);
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
