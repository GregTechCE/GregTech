package gregtech.api.gui;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import org.lwjgl.opengl.GL11;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The GUI-Container I use for all my MetaTileEntities
 */
public class GT_GUIContainerMetaTile_Machine extends GT_GUIContainer {

    public final GT_ContainerMetaTile_Machine mContainer;

    public GT_GUIContainerMetaTile_Machine(GT_ContainerMetaTile_Machine aContainer, String aGUIbackground) {
        super(aContainer, aGUIbackground);
        mContainer = aContainer;
    }

    public GT_GUIContainerMetaTile_Machine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aGUIbackground) {
        this(new GT_ContainerMetaTile_Machine(aInventoryPlayer, aTileEntity), aGUIbackground);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        if (GregTech_API.sColoredGUI && mContainer != null && mContainer.mTileEntity != null) {
            int tColor = mContainer.mTileEntity.getColorization() & 15;
            if (tColor < ItemDye.field_150922_c.length) {
                tColor = ItemDye.field_150922_c[tColor];
                GL11.glColor4f(((tColor >> 16) & 255) / 255.0F, ((tColor >> 8) & 255) / 255.0F, (tColor & 255) / 255.0F, 1.0F);
            } else GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        } else GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}