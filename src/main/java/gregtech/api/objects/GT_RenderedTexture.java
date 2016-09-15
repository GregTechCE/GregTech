package gregtech.api.objects;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.newblocks.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GT_RenderedTexture implements ITexture {

    private final IIconContainer mIconContainer;
    public int mRGBa = -1;

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa) {
        mIconContainer = aIcon;
        if(aRGBa != null) {
            this.mRGBa = makeColor(aRGBa);
        }
    }

    public GT_RenderedTexture(String spriteName, short[] aRGBa) {
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(spriteName);
        this.mIconContainer = GT_Utility.sprite2Container(sprite);
        if(aRGBa != null) {
            this.mRGBa = makeColor(aRGBa);
        }
    }

    private int makeColor(short[] rgba) {
        try {
            for(int i = 0; i < 4; i++)
                rgba[i] = (short) Math.max(0, rgba[i]);
            return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
        } catch (IllegalArgumentException err) {
            return Color.WHITE.getRGB();
        }
    }

    public GT_RenderedTexture(IIconContainer aIcon, int aRGBa) {
        mIconContainer = aIcon;
        this.mRGBa = aRGBa;
    }

    public GT_RenderedTexture(IIconContainer aIcon) {
        this.mIconContainer = aIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset) {
        ArrayList<BakedQuad> quads = new ArrayList<>();
        TextureAtlasSprite sprite = mIconContainer.getIcon();
        TextureAtlasSprite overlay = mIconContainer.getOverlayIcon();
        if(sprite != null) {
            quads.add(RenderUtil.renderSide(DefaultVertexFormats.BLOCK, sprite, side, -1, offset, mRGBa, false));
        }
        if(overlay != null) {
            quads.add(RenderUtil.renderSide(DefaultVertexFormats.BLOCK, overlay, side, -1, offset + 0.01F, mRGBa, false));
        }
        return quads;
    }



    @Override
    public boolean isValidTexture() {
        return mIconContainer != null;
    }

}