package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.render.WorldSceneRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class SceneRenderWidget extends Widget {

    public final int posX;
    public final int posY;
    public final int width;
    public final int height;
    private final WorldSceneRenderer worldSceneRenderer;

    public SceneRenderWidget(int posX, int posY, int width, int height, Map<BlockPos, Tuple<IBlockState, TileEntity>> renderedBlocks) {
        //we have to be drawn last because we don't restore modelview matrix completely -
        //we just revert it to identity state, while ContainerModularUI does translation to guiLeft; guiTop
        super(Integer.MAX_VALUE);
        this.worldSceneRenderer = new WorldSceneRenderer(renderedBlocks);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int cornerX = (resolution.getScaledWidth() - gui.width) / 2;
        int cornerY = (resolution.getScaledHeight() - gui.height) / 2;
        worldSceneRenderer.render(cornerX + posX, cornerY + posY, width, height);
    }
}
