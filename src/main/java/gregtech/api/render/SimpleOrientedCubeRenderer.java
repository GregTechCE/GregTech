package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SimpleOrientedCubeRenderer implements IIconRegister {

    private final String basePath;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] sprites;

    private enum CubeSide {
        FRONT, BACK, RIGHT, LEFT, TOP, BOTTOM
    }

    public SimpleOrientedCubeRenderer(String basePath) {
        this.basePath = basePath;
        Textures.iconRegisters.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.sprites = new TextureAtlasSprite[6];
        for (CubeSide cubeSide : CubeSide.values()) {
            String fullPath = String.format("blocks/%s/%s", basePath, cubeSide.name().toLowerCase());
            this.sprites[cubeSide.ordinal()] = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, fullPath));
        }
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite() {
        return sprites[CubeSide.FRONT.ordinal()];
    }

    @SideOnly(Side.CLIENT)
    public void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] ops, Cuboid6 bounds, EnumFacing frontFacing) {
        Textures.renderFace(renderState, translation, ops, EnumFacing.UP, bounds, sprites[CubeSide.TOP.ordinal()]);
        Textures.renderFace(renderState, translation, ops, EnumFacing.DOWN, bounds, sprites[CubeSide.BOTTOM.ordinal()]);

        Textures.renderFace(renderState, translation, ops, frontFacing, bounds, sprites[CubeSide.FRONT.ordinal()]);
        Textures.renderFace(renderState, translation, ops, frontFacing.getOpposite(), bounds, sprites[CubeSide.BACK.ordinal()]);

        Textures.renderFace(renderState, translation, ops, frontFacing.rotateY(), bounds, sprites[CubeSide.LEFT.ordinal()]);
        Textures.renderFace(renderState, translation, ops, frontFacing.rotateYCCW(), bounds, sprites[CubeSide.RIGHT.ordinal()]);
    }
}
