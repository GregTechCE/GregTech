package gregtech.common.gui;

import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainer_BronzeBlastFurnace
        extends GT_GUIContainerMetaTile_Machine {
    public GT_GUIContainer_BronzeBlastFurnace(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(new GT_Container_BronzeBlastFurnace(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/BronzeBlastFurnace.png");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString("Bronze Blast Furnace", 8, 4, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        if ((this.mContainer != null) &&
                (this.mContainer.mProgressTime > 0)) {
            drawTexturedModalRect(x + 58, y + 28, 176, 0, Math.max(0, Math.min(20, (1) + this.mContainer.mProgressTime * 20 / (this.mContainer.mMaxProgressTime < 1 ? 1 : this.mContainer.mMaxProgressTime))), 11);
        }
    }
}
