package gregtech.api.objects;

import com.google.common.collect.Lists;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.newblocks.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GT_PipeRenderedTexture implements ITexture {

    private float thickness;
    private boolean connected;
    private TextureAtlasSprite connectedSprite;
    private TextureAtlasSprite insulationSprite;
    private int rgbaConnection = -1;
    private int rgbaInsulation = -1;

    public GT_PipeRenderedTexture(float thickness, boolean connected, TextureAtlasSprite spriteConnected, TextureAtlasSprite spriteInsulation, short[] rgbaConnected, short[] rgbaInsulation) {
        this.thickness = thickness;
        this.connected = connected;
        this.connectedSprite = spriteConnected;
        this.insulationSprite = spriteInsulation;
        if(rgbaConnected != null) {
            this.rgbaConnection = GT_RenderedTexture.makeColor(rgbaConnected);
        }
        if(rgbaInsulation != null) {
            this.rgbaInsulation = GT_RenderedTexture.makeColor(rgbaInsulation);
        }
    }

    public GT_PipeRenderedTexture(float thickness, boolean connected, IIconContainer sprite, short[] rgba) {
        this(thickness, connected, sprite, rgba, sprite, rgba);
    }

    public GT_PipeRenderedTexture(float thickness, boolean connected, IIconContainer connection, short[] rgbaCon, IIconContainer insulation, short[] rgbaInsul) {
        this(thickness, connected, connection.getIcon(), insulation.getIcon(), rgbaCon, rgbaInsul);
    }

    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset) {
        float wireOffset = (1.0F - thickness) / 2f;

        switch (side) {
            case UP:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, wireOffset, thickness, thickness, EnumFacing.UP, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad north = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, wireOffset, thickness, wireOffset, EnumFacing.NORTH, rgbaInsulation);
                    BakedQuad south = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, wireOffset + thickness, thickness, wireOffset, EnumFacing.SOUTH, rgbaInsulation);

                    BakedQuad west = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, wireOffset, wireOffset, thickness, EnumFacing.WEST, rgbaInsulation);
                    BakedQuad east = makeQuad(insulationSprite, wireOffset + thickness, wireOffset + thickness, wireOffset, wireOffset, thickness, EnumFacing.EAST, rgbaInsulation);

                    BakedQuad up = makeQuad(connectedSprite, wireOffset, 1f, wireOffset, thickness, thickness, EnumFacing.UP, rgbaConnection);

                    return Lists.newArrayList(north, south, west, east, up);
                }
            case DOWN:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset, thickness, thickness, EnumFacing.DOWN, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad north = makeQuad(insulationSprite, wireOffset, 0f, wireOffset, thickness, wireOffset, EnumFacing.NORTH, rgbaInsulation);
                    BakedQuad south = makeQuad(insulationSprite, wireOffset, 0f, wireOffset + thickness, thickness, wireOffset, EnumFacing.SOUTH, rgbaInsulation);

                    BakedQuad west = makeQuad(insulationSprite, wireOffset, 0f, wireOffset, wireOffset, thickness, EnumFacing.WEST, rgbaInsulation);
                    BakedQuad east = makeQuad(insulationSprite, wireOffset + thickness, 0f, wireOffset, wireOffset, thickness, EnumFacing.EAST, rgbaInsulation);

                    BakedQuad down = makeQuad(connectedSprite, wireOffset, 0f, wireOffset, thickness, thickness, EnumFacing.DOWN, rgbaConnection);

                    return Lists.newArrayList(north, south, west, east, down);
                }
            case EAST:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset, thickness, thickness, EnumFacing.WEST, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad down = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, wireOffset, wireOffset, thickness, EnumFacing.DOWN, rgbaInsulation);
                    BakedQuad up = makeQuad(insulationSprite, wireOffset + thickness, wireOffset + thickness, wireOffset, wireOffset, thickness, EnumFacing.UP, rgbaInsulation);

                    BakedQuad north = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, wireOffset, wireOffset, thickness, EnumFacing.NORTH, rgbaInsulation);
                    BakedQuad south = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, wireOffset + thickness, wireOffset, thickness, EnumFacing.SOUTH, rgbaInsulation);

                    BakedQuad end = makeQuad(connectedSprite, 1f, wireOffset, wireOffset, thickness, thickness, EnumFacing.EAST, rgbaConnection);

                    return Lists.newArrayList(down, up, north, south, end);
                }
            case WEST:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, wireOffset, thickness, thickness, EnumFacing.EAST, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad down = makeQuad(insulationSprite, 0f, wireOffset, wireOffset, wireOffset, thickness, EnumFacing.DOWN, rgbaInsulation);
                    BakedQuad up = makeQuad(insulationSprite, 0f, wireOffset + thickness, wireOffset, wireOffset, thickness, EnumFacing.UP, rgbaInsulation);

                    BakedQuad north = makeQuad(insulationSprite, 0f, wireOffset, wireOffset, wireOffset, thickness, EnumFacing.NORTH, rgbaInsulation);
                    BakedQuad south = makeQuad(insulationSprite, 0f, wireOffset, wireOffset + thickness, wireOffset, thickness, EnumFacing.SOUTH, rgbaInsulation);

                    BakedQuad end = makeQuad(connectedSprite, 0f, wireOffset, wireOffset, thickness, thickness, EnumFacing.WEST, rgbaConnection);

                    return Lists.newArrayList(down, up, north, south, end);
                }
            case NORTH:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset, thickness, thickness, EnumFacing.NORTH, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad down = makeQuad(insulationSprite, wireOffset, wireOffset, 0f, thickness, wireOffset, EnumFacing.DOWN, rgbaInsulation);
                    BakedQuad up = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, 0f, thickness, wireOffset, EnumFacing.UP, rgbaInsulation);

                    BakedQuad east = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, 0f, thickness, wireOffset, EnumFacing.EAST, rgbaInsulation);
                    BakedQuad west = makeQuad(insulationSprite, wireOffset, wireOffset, 0f, thickness, wireOffset, EnumFacing.WEST, rgbaInsulation);

                    BakedQuad end = makeQuad(connectedSprite, wireOffset, wireOffset, 0f, thickness, thickness, EnumFacing.NORTH, rgbaConnection);

                    return Lists.newArrayList(down, up, end, east, west);
                }
            case SOUTH:
                if(!connected) {
                    BakedQuad quad = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset + thickness, thickness, thickness, EnumFacing.SOUTH, rgbaInsulation);
                    return Lists.newArrayList(quad);
                } else {
                    BakedQuad down = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset + thickness, thickness, wireOffset, EnumFacing.DOWN, rgbaInsulation);
                    BakedQuad up = makeQuad(insulationSprite, wireOffset, wireOffset + thickness, wireOffset + thickness, thickness, wireOffset, EnumFacing.UP, rgbaInsulation);

                    BakedQuad east = makeQuad(insulationSprite, wireOffset + thickness, wireOffset, wireOffset + thickness, thickness, wireOffset, EnumFacing.EAST, rgbaInsulation);
                    BakedQuad west = makeQuad(insulationSprite, wireOffset, wireOffset, wireOffset + thickness, thickness, wireOffset, EnumFacing.WEST, rgbaInsulation);

                    BakedQuad end = makeQuad(connectedSprite, wireOffset, wireOffset, 1f, thickness, thickness, EnumFacing.SOUTH, rgbaConnection);

                    return Lists.newArrayList(down, up, end, east, west);
                }
        }
        return Collections.emptyList();
    }

    public BakedQuad makeQuad(TextureAtlasSprite sprite, float x, float y, float z, float width, float height, EnumFacing side, int rgba) {
        return RenderUtil.renderQuadCustom(x, y, z, width, height, DefaultVertexFormats.BLOCK, sprite, side, -1, rgba, true);
    }

    @Override
    public boolean isValidTexture() {
        return thickness > 0.0F;
    }

}
