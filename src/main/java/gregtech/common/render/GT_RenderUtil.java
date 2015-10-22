package gregtech.common.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GT_RenderUtil {
    public static void renderItemIcon(IIcon icon, double size, double z, float nx, float ny, float nz) {
        renderItemIcon(icon, 0.0D, 0.0D, size, size, z, nx, ny, nz);
    }

    public static void renderItemIcon(IIcon icon, double xStart, double yStart, double xEnd, double yEnd, double z, float nx, float ny, float nz) {
        if (icon == null) {
            return;
        }
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(nx, ny, nz);
        if (nz > 0.0F) {
            Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
        } else {
            Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
        }
        Tessellator.instance.draw();
    }
}
