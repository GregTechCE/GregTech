package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.render.scene.WorldSceneRenderer;
import gregtech.api.util.BlockInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class SceneRenderWidget extends Widget {

    public final int posX;
    public final int posY;
    public final int width;
    public final int height;
    private final WorldSceneRenderer worldSceneRenderer;

    public SceneRenderWidget(int posX, int posY, int width, int height, Map<BlockPos, BlockInfo> renderedBlocks) {
        //we have to be drawn last because we don't restore modelview matrix completely -
        //we just revert it to identity state, while ContainerModularUI does translation to guiLeft; guiTop
        super();
        this.worldSceneRenderer = new WorldSceneRenderer(renderedBlocks);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int cornerX = (resolution.getScaledWidth() - gui.getWidth()) / 2;
        int cornerY = (resolution.getScaledHeight() - gui.getHeight()) / 2;
        worldSceneRenderer.render(cornerX + posX, cornerY + posY, width, height, 0xFFFFFF);
    }
}
