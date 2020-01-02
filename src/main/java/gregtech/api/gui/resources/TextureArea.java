package gregtech.api.gui.resources;

import gregtech.api.GTValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Represents a texture area of image
 * This representation doesn't take image size in account, so all image variables are
 * 0.0 - 1.0 bounds
 */
public class TextureArea {

    public final ResourceLocation imageLocation;

    public final double offsetX;
    public final double offsetY;

    public final double imageWidth;
    public final double imageHeight;

    public TextureArea(ResourceLocation imageLocation, double offsetX, double offsetY, double width, double height) {
        this.imageLocation = imageLocation;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.imageWidth = width;
        this.imageHeight = height;
    }

    public static TextureArea fullImage(String imageLocation) {
        return new TextureArea(new ResourceLocation(GTValues.MODID, imageLocation), 0.0, 0.0, 1.0, 1.0);
    }

    public static TextureArea areaOfImage(String imageLocation, int imageSizeX, int imageSizeY, int u, int v, int width, int height) {
        return new TextureArea(new ResourceLocation(imageLocation),
            u / (imageSizeX * 1.0),
            v / (imageSizeY * 1.0),
            (u + width) / (imageSizeX * 1.0),
            (v + height) / (imageSizeY * 1.0));
    }

    public TextureArea getSubArea(double offsetX, double offsetY, double width, double height) {
        return new TextureArea(imageLocation,
            this.offsetX + (imageWidth * offsetX),
            this.offsetY + (imageHeight * offsetY),
            this.imageWidth * width,
            this.imageHeight * height);
    }

    @SideOnly(Side.CLIENT)
    public void draw(int x, int y, int width, int height) {
        Minecraft.getMinecraft().renderEngine.bindTexture(imageLocation);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0D).tex(offsetX, offsetY + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0D).tex(offsetX + imageWidth, offsetY + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y, 0.0D).tex(offsetX + imageWidth, offsetY).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(offsetX, offsetY).endVertex();
        tessellator.draw();
    }

    @SideOnly(Side.CLIENT)
    public void drawSubArea(int x, int y, int width, int height, double drawnU, double drawnV, double drawnWidth, double drawnHeight) {
        //sub area is just different width and height
        double imageU = this.offsetX + (this.imageWidth * drawnU);
        double imageV = this.offsetY + (this.imageHeight * drawnV);
        double imageWidth = this.imageWidth * drawnWidth;
        double imageHeight = this.imageHeight * drawnHeight;
        Minecraft.getMinecraft().renderEngine.bindTexture(imageLocation);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0D).tex(imageU, imageV + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0D).tex(imageU + imageWidth, imageV + imageHeight).endVertex();
        bufferbuilder.pos(x + width, y, 0.0D).tex(imageU + imageWidth, imageV).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex(imageU, imageV).endVertex();
        tessellator.draw();
    }

}
