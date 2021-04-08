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

public class CTCubeRenderer implements IIconRegister {


    private final String basePath;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] ctSprites;

    public CTCubeRenderer(String basePath) {
        this.basePath = basePath;
        Textures.iconRegisters.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.ctSprites = new TextureAtlasSprite[16];
        for (int i = 0; i < 16; i++) {
            this.ctSprites[i] = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/" + basePath + "_" + i));
        }
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return ctSprites[ctSprites.length - 1];
    }

    @SideOnly(Side.CLIENT)
    public void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, int connectionMask) {
        for (EnumFacing renderSide : EnumFacing.VALUES) {
            if (hasFaceBit(connectionMask, renderSide)) {
                continue; //do not render faces occluded by connections
            }
            int resultTextureMask = 0b1111;
            if (renderSide.getAxis().isHorizontal()) {
                if (hasFaceBit(connectionMask, EnumFacing.UP)) resultTextureMask &= ~TextureDirection.TOP;
                if (hasFaceBit(connectionMask, EnumFacing.DOWN)) resultTextureMask &= ~TextureDirection.BOTTOM;
                EnumFacing leftFacing = renderSide.rotateY();
                EnumFacing rightFacing = renderSide.rotateYCCW();
                if (hasFaceBit(connectionMask, leftFacing)) resultTextureMask &= ~TextureDirection.LEFT;
                if (hasFaceBit(connectionMask, rightFacing)) resultTextureMask &= ~TextureDirection.RIGHT;
            } else {
                if (hasFaceBit(connectionMask, EnumFacing.NORTH)) resultTextureMask &= ~TextureDirection.TOP;
                if (hasFaceBit(connectionMask, EnumFacing.SOUTH)) resultTextureMask &= ~TextureDirection.BOTTOM;
                if (hasFaceBit(connectionMask, EnumFacing.WEST)) resultTextureMask &= ~TextureDirection.LEFT;
                if (hasFaceBit(connectionMask, EnumFacing.EAST)) resultTextureMask &= ~TextureDirection.RIGHT;
            }
            TextureAtlasSprite sideSprite = ctSprites[resultTextureMask];
            Textures.renderFace(renderState, translation, pipeline, renderSide, Cuboid6.full, sideSprite);
            Matrix4 backTranslation = translation.copy();
            backTranslation.translate(renderSide.getXOffset(), renderSide.getYOffset() * 0.999, renderSide.getZOffset());
            int backFaceTextureMask;
            if (renderSide.getAxis().isHorizontal()) {
                backFaceTextureMask = resultTextureMask & ~(TextureDirection.RIGHT | TextureDirection.LEFT);
                if ((resultTextureMask & TextureDirection.RIGHT) > 0)
                    backFaceTextureMask |= TextureDirection.LEFT;
                if ((resultTextureMask & TextureDirection.LEFT) > 0)
                    backFaceTextureMask |= TextureDirection.RIGHT;
            } else {
                backFaceTextureMask = resultTextureMask;
            }
            TextureAtlasSprite backSideSprite = ctSprites[backFaceTextureMask];
            Textures.renderFace(renderState, backTranslation, pipeline, renderSide.getOpposite(), Cuboid6.full, backSideSprite);
        }
    }

    private static class TextureDirection {
        private static final int TOP = 1;
        private static final int BOTTOM = 2;
        private static final int LEFT = 4;
        private static final int RIGHT = 8;
    }

    public static boolean hasFaceBit(int mask, EnumFacing side) {
        return (mask & 1 << side.getIndex()) > 0;
    }
}
