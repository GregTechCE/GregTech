package gregtech.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderUtil {

    public static void setGlColorFromInt(int colorValue) {
        int i = (colorValue & 16711680) >> 16;
        int j = (colorValue & 65280) >> 8;
        int k = (colorValue & 255);
        GlStateManager.color(i / 255.0f, j / 255.0f, k / 255.0f);
    }

    private static void guiVertex(BufferBuilder bb, TextureAtlasSprite sprite, double x, double y, double u, double v) {
        float ru = sprite.getInterpolatedU(u);
        float rv = sprite.getInterpolatedV(v);
        bb.pos(x, y, 0);
        bb.tex(ru, rv);
        bb.endVertex();
    }
    public static void drawFluidForGui(FluidStack fluid, double startX, double startY, double endX, double endY) {
        ResourceLocation stillTexture = fluid.getFluid().getStill(fluid);
        int colorValue = fluid.getFluid().getColor(fluid);
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(stillTexture.toString());
        if (sprite == null)
            sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        drawCutSprite(sprite, colorValue, startX, startY, endX, endY);
    }

    public static void drawCutSprite(TextureAtlasSprite sprite, int colorValue, double startX, double startY, double endX, double endY) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderUtil.setGlColorFromInt(colorValue);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bb = tess.getBuffer();
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        // draw all the full sprites

        double diffX = endX - startX;
        double diffY = endY - startY;

        int stepX = diffX > 0 ? 16 : -16;
        int stepY = diffY > 0 ? 16 : -16;

        int loopCountX = (int) Math.abs(diffX / 16);
        int loopCountY = (int) Math.abs(diffY / 16);

        double x = startX;
        for (int xc = 0; xc < loopCountX; xc++) {
            double y = startY;
            for (int yc = 0; yc < loopCountY; yc++) {
                guiVertex(bb, sprite, x, y, 0, 0);
                guiVertex(bb, sprite, x + stepX, y, 16, 0);
                guiVertex(bb, sprite, x + stepX, y + stepY, 16, 16);
                guiVertex(bb, sprite, x, y + stepY, 0, 16);
                y += stepY;
            }
            x += stepX;
        }

        if (diffX % 16 != 0) {
            double additionalWidth = diffX % 16;
            x = endX - additionalWidth;
            double xTex = Math.abs(additionalWidth);
            double y = startY;
            for (int yc = 0; yc < loopCountY; y++) {
                guiVertex(bb, sprite, x, y, 0, 0);
                guiVertex(bb, sprite, endX, y, xTex, 0);
                guiVertex(bb, sprite, endX, y + stepY, xTex, 16);
                guiVertex(bb, sprite, x, y + stepY, 0, 16);
                y += stepY;
            }
        }

        if (diffY % 16 != 0) {
            double additionalHeight = diffY % 16;
            double y = endY - additionalHeight;
            double yTex = Math.abs(additionalHeight);
            x = startX;
            for (int xc = 0; xc < loopCountX; xc++) {
                guiVertex(bb, sprite, x, y, 0, 0);
                guiVertex(bb, sprite, x + stepX, y, 16, 0);
                guiVertex(bb, sprite, x + stepX, endY, 16, yTex);
                guiVertex(bb, sprite, x, endY, 0, yTex);
                x += stepX;
            }
        }

        if (diffX % 16 != 0 && diffY % 16 != 0) {
            double w = diffX % 16;
            double h = diffY % 16;
            x = endX - w;
            double y = endY - h;
            double tx = w < 0 ? -w : w;
            double ty = h < 0 ? -h : h;
            guiVertex(bb, sprite, x, y, 0, 0);
            guiVertex(bb, sprite, endX, y, tx, 0);
            guiVertex(bb, sprite, endX, endY, tx, ty);
            guiVertex(bb, sprite, x, endY, 0, ty);
        }

        tess.draw();
        GlStateManager.color(1, 1, 1);
    }

}
