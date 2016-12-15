package gregtech.common.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class GT_RenderUtil {

    public static void renderItemIcon(TextureAtlasSprite icon, double size, double z, float nx, float ny, float nz) {
        renderItemIcon(icon, 0.0D, 0.0D, size, size, z, nx, ny, nz);
    }

    public static void renderItemIcon(TextureAtlasSprite icon, double xStart, double yStart, double xEnd, double yEnd, double z, float nx, float ny, float nz) {
        if (icon == null) {
            return;
        }
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if (nz > 0.0F) {
            addVertexWithUV(buffer, nx, ny, nz, xStart, yStart, z, icon.getMinU(), icon.getMinV());
            addVertexWithUV(buffer, nx, ny, nz, xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            addVertexWithUV(buffer, nx, ny, nz, xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            addVertexWithUV(buffer, nx, ny, nz, xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
        } else {
            addVertexWithUV(buffer, nx, ny, nz, xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
            addVertexWithUV(buffer, nx, ny, nz, xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            addVertexWithUV(buffer, nx, ny, nz, xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            addVertexWithUV(buffer, nx, ny, nz, xStart, yStart, z, icon.getMinU(), icon.getMinV());
        }
        tessellator.draw();
    }
    
    private static void addVertexWithUV(VertexBuffer buffer, double nX, double nY, double nZ, double x, double y, double z, double u, double v) {
        buffer.pos(x, y, z).color(1.0f, 1.0f, 1.0f, 1.0f).tex(u, v).normal((float)nX, (float)nY, (float)nZ).endVertex();
    }
    
}
