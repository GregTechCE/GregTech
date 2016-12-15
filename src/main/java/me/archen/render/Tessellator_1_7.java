package me.archen.render;

import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.FaceBakery;

public class Tessellator_1_7 {

    public static final Tessellator_1_7 instance = new Tessellator_1_7(Tessellator.getInstance().getBuffer());
    private VertexBuffer handle;

    private float[] color = new float[] {1.0F, 1.0F, 1.0F};
    private double[] uv = new double[] {0.0f, 0.0f};
    private int[] lightmap = new int[] {255, 255};

    public Tessellator_1_7(VertexBuffer handle) {
        this.handle = Tessellator.getInstance().getBuffer();
    }

    public void setUV(double u, double v) {
        this.uv = new double[] {u, v};
    }

    public void setLightmap(int lightmapX, int lightmapY) {
        this.lightmap = new int[] {lightmapX, lightmapY};
    }

    public void setColor_F(float red, float green, float blue, float alpha) {
        handle.color(red, green, blue, alpha);
    }

    public void setColorOpaque_F(float red, float green, float blue) {
        handle.color(red, green, blue, 0.0F);
    }

    public void addVertex(double x, double y, double z) {
        handle.pos(x, y, z);
        handle.color(color[0], color[1], color[2], color[3]);
        handle.tex(uv[0], uv[1]);
        handle.lightmap(lightmap[0], lightmap[1]);
        handle.endVertex();
    }

    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        setUV(u, v);
        addVertex(x, y, z);
    }



}
