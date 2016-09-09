package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_MetaGenerated_Item;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.newblocks.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GT_RenderedTexture implements ITexture {

    private final IIconContainer mIconContainer;

    public int mRGBa;

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        mIconContainer = aIcon;
        mRGBa = makeColor(aRGBa);
    }

    public GT_RenderedTexture(String spriteName, short[] aRGBa) {
        this(GT_Utility.sprite2Container(
                Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName)),
                aRGBa == null ? new short[] {255, 255, 255, 255} : aRGBa);
    }

    private int makeColor(short[] rgba) {
        short[] nullRGBA = Materials._NULL.getRGBA();
        short red = rgba[0] > 0 && 255 > rgba[0] ? rgba[0] : nullRGBA[0];
        short green = rgba[1] > 0 && 255 > rgba[1] ? rgba[1] : nullRGBA[1];
        short blue = rgba[2] > 0 && 255 > rgba[2] ? rgba[2] : nullRGBA[2];
        short alpha = rgba[3] > 0 && 255 > rgba[3] ? rgba[3] : nullRGBA[3];
        return new Color(red, green, blue, alpha).getRGB();
    }

    public GT_RenderedTexture(IIconContainer aIcon, int aRGBa) {
        mIconContainer = aIcon;
        this.mRGBa = aRGBa;
    }

    public GT_RenderedTexture(IIconContainer aIcon) {
        this(aIcon, Dyes._NULL.mRGBa);
    }


    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOff) {
        ArrayList<BakedQuad> quads = new ArrayList<>();
        TextureAtlasSprite sprite = mIconContainer.getIcon();
        TextureAtlasSprite overlay = mIconContainer.getOverlayIcon();
        if(sprite != null) {
            quads.add(RenderUtil.renderSide(DefaultVertexFormats.BLOCK, sprite, side, tintOff, -0.003F, mRGBa));
        }
        if(overlay != null) {
            quads.add(RenderUtil.renderSide(DefaultVertexFormats.BLOCK, overlay, side, tintOff, -0.0023F, mRGBa));
        }
        return quads;
    }



    @Override
    public boolean isValidTexture() {
        return mIconContainer != null;
    }

}