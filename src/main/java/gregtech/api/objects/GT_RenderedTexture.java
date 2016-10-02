package gregtech.api.objects;

import com.google.common.collect.ImmutableList;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class GT_RenderedTexture implements ITexture {

    private final IIconContainer mIconContainer;
    public int mRGBa = -1;

    private HashMap<Integer, EnumMap<EnumFacing, ImmutableList<BakedQuad>>> cache = new HashMap<>();

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa) {
        mIconContainer = aIcon;
        if(aRGBa != null) {
            this.mRGBa = makeColor(aRGBa);
        }
    }

    public GT_RenderedTexture(String spriteName, short[] aRGBa) {
        this(new LazyCopiedIconContainer(spriteName), aRGBa);
    }

    public static int makeColor(short[] rgba) {
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

    public static int offset2key(float offset) {
        //precision: x.xx
        return (int) (offset * 100);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset) {
        int offsetKey = offset2key(offset);
        EnumMap<EnumFacing, ImmutableList<BakedQuad>> offsetCache = cache.get(offsetKey);
        if(offsetCache == null) {
            offsetCache = new EnumMap<>(EnumFacing.class);
            cache.put(offsetKey, offsetCache);
        }
        ImmutableList<BakedQuad> quads = offsetCache.get(side);
        if(quads == null) {
            quads = generate9(side, offset);
            offsetCache.put(side, quads);
        }
        return quads;
    }

    private ImmutableList<BakedQuad> generate9(EnumFacing side, float offset) {
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
        TextureAtlasSprite sprite = mIconContainer.getIcon();
        TextureAtlasSprite overlay = mIconContainer.getOverlayIcon();
        if(sprite != null) {
            quads.add(RenderUtil.renderSide(sprite, side, offset, mRGBa));
        }
        if(overlay != null) {
            quads.add(RenderUtil.renderSide(overlay, side, offset + 0.01F, mRGBa));
        }
        return quads.build();
    }



    @Override
    public boolean isValidTexture() {
        return mIconContainer != null;
    }

}