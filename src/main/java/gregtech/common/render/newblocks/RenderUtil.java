package gregtech.common.render.newblocks;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderUtil {

    public static BakedQuad renderSide(VertexFormat vertexFormat, TextureAtlasSprite sprite, EnumFacing side, int tint, float offset, int color, boolean hideSiding) {
        switch (side) {
            case NORTH:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.NORTH, sprite, tint,
                        0, 0, -offset, sprite.getMaxU(), sprite.getMaxV(),
                        0, 1, -offset, sprite.getMaxU(), sprite.getMinV(),
                        1, 1, -offset, sprite.getMinU(), sprite.getMinV(),
                        1, 0, -offset, sprite.getMinU(), sprite.getMaxV(),
                color);
            case SOUTH:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.SOUTH, sprite, tint,
                        1, 0, 1 + offset, sprite.getMinU(), sprite.getMaxV(),
                        1, 1, 1 + offset, sprite.getMinU(), sprite.getMinV(),
                        0, 1, 1 + offset, sprite.getMaxU(), sprite.getMinV(),
                        0, 0, 1 + offset, sprite.getMaxU(), sprite.getMaxV(),
                        color);
            case WEST:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.WEST, sprite, tint,
                        -offset, 0, 1, sprite.getMaxU(), sprite.getMaxV(),
                        -offset, 1, 1 , sprite.getMaxU(), sprite.getMinV(),
                        -offset, 1, 0, sprite.getMinU(), sprite.getMinV(),
                        -offset, 0, 0, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case EAST:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.EAST, sprite, tint,
                        1 + offset, 0, 0, sprite.getMaxU(), sprite.getMaxV(),
                        1 + offset, 1, 0 , sprite.getMaxU(), sprite.getMinV(),
                        1 + offset, 1, 1, sprite.getMinU(), sprite.getMinV(),
                        1 + offset, 0, 1, sprite.getMinU(), sprite.getMaxV(),
                color);
            case DOWN:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.DOWN, sprite, tint,
                        0, -offset, 0, sprite.getMinU(), sprite.getMinV(),
                        1, -offset, 0, sprite.getMaxU(), sprite.getMinV(),
                        1, -offset, 1, sprite.getMaxU(), sprite.getMaxV(),
                        0, -offset, 1, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case UP:
                return buildQuad(vertexFormat, hideSiding ? null : EnumFacing.UP, sprite, tint,
                        0, 1 + offset, 1, sprite.getMinU(), sprite.getMaxV(),
                        1, 1 + offset, 1, sprite.getMaxU(), sprite.getMaxV(),
                        1, 1 + offset, 0, sprite.getMaxU(), sprite.getMinV(),
                        0, 1 + offset, 0, sprite.getMinU(), sprite.getMinV(),
                color);

            default:
                System.out.println("Can't render side " + side);
                return null;
        }
    }

    public static BakedQuad renderQuadCustom(float x, float y, float z, float width, float height, VertexFormat vertexFormat, TextureAtlasSprite sprite, EnumFacing side, int tint, int color, boolean hide) {
        //float spriteCut = Math.min((width + height) / 2.0F, 1.0F); TODO
        switch (side) {
            case NORTH:
                return buildQuad(vertexFormat, EnumFacing.NORTH, sprite, tint,
                        x, y, z, sprite.getMaxU(), sprite.getMaxV(),
                        x, y + height, z, sprite.getMaxU(), sprite.getMinV(),
                        x + width, y + height, z, sprite.getMinU(), sprite.getMinV(),
                        x + width, y, z, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case SOUTH:
                return buildQuad(vertexFormat, EnumFacing.SOUTH, sprite, tint,
                        x + width, y, z, sprite.getMinU(), sprite.getMaxV(),
                        x + width, y + height, z, sprite.getMinU(), sprite.getMinV(),
                        x, y + height, z, sprite.getMaxU(), sprite.getMinV(),
                        x, y, z, sprite.getMaxU(), sprite.getMaxV(),
                        color);
            case WEST:
                return buildQuad(vertexFormat, EnumFacing.WEST, sprite, tint,
                        x, y, z + height, sprite.getMaxU(), sprite.getMaxV(),
                        x, y + width, z + height, sprite.getMaxU(), sprite.getMinV(),
                        x, y + width, z, sprite.getMinU(), sprite.getMinV(),
                        x, y, z, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case EAST:
                return buildQuad(vertexFormat, EnumFacing.EAST, sprite, tint,
                        x, y, z, sprite.getMaxU(), sprite.getMaxV(),
                        x, y + width, z, sprite.getMaxU(), sprite.getMinV(),
                        x, y + width, z + height, sprite.getMinU(), sprite.getMinV(),
                        x, y, z + height, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case DOWN:
                return buildQuad(vertexFormat, EnumFacing.DOWN, sprite, tint,
                        x, y, z, sprite.getMinU(), sprite.getMinV(),
                        x + width, y, z, sprite.getMaxU(), sprite.getMinV(),
                        x + width, y, z + height, sprite.getMaxU(), sprite.getMaxV(),
                        x, y, z + height, sprite.getMinU(), sprite.getMaxV(),
                        color);
            case UP:
                return buildQuad(vertexFormat, hide ? null : EnumFacing.UP, sprite, tint,
                        x, y, z + height, sprite.getMinU(), sprite.getMaxV(),
                        x + width, y, z + height, sprite.getMaxU(), sprite.getMaxV(),
                        x + width, y, z, sprite.getMaxU(), sprite.getMinV(),
                        x, y, z, sprite.getMinU(), sprite.getMinV(),
                        color);

            default:
                System.out.println("Can't render side " + side);
                return null;
        }
    }

    private static BakedQuad buildQuad(
            VertexFormat format, EnumFacing side, TextureAtlasSprite sprite, int tint,
            float x0, float y0, float z0, float u0, float v0,
            float x1, float y1, float z1, float u1, float v1,
            float x2, float y2, float z2, float u2, float v2,
            float x3, float y3, float z3, float u3, float v3, int color)
    {
        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setQuadTint(tint);
        builder.setQuadOrientation(side);
        builder.setTexture(sprite);

        putVertex(builder, format, side, x0, y0, z0, u0, v0, color);
        putVertex(builder, format, side, x1, y1, z1, u1, v1, color);
        putVertex(builder, format, side, x2, y2, z2, u2, v2, color);
        putVertex(builder, format, side, x3, y3, z3, u3, v3, color);
        return builder.build();
    }

    private static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, EnumFacing side, float x, float y, float z, float u, float v, int color)  {
        for(int e = 0; e < format.getElementCount(); e++)
        {
            switch(format.getElement(e).getUsage())
            {
                case POSITION:
                    builder.put(e, x, y, z, 1);
                    break;
                case COLOR:
                    if(color == -1) {
                        builder.put(e, 1f, 1f, 1f, 1f);
                    } else {
                        builder.put(e,
                                ((color >> 16) & 0xFF) / 255.0F,
                                ((color >> 8) & 0xFF) / 255.0F,
                                ((color) & 0xFF) / 255.0F, 1f);
                    }
                    break;
                case UV: if(format.getElement(e).getIndex() == 0)
                {
                    builder.put(e, u, v, 0f, 1f);
                    break;
                }
                case NORMAL:
                    builder.put(e, 0.0F, 0.0F, 1.0F, 0.0F);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

}
