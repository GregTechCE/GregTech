package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GT_RenderedTexture implements ITexture {

    private static final FaceBakery FACE_BAKERY = new FaceBakery();

    private final IIconContainer mIconContainer;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public int mRGBa;

    public GT_RenderedTexture(IIconContainer aIcon, short[] aRGBa) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        mIconContainer = aIcon;
        mRGBa = new Color(aRGBa[0], aRGBa[1], aRGBa[2], aRGBa[3]).getRGB();
    }
    public GT_RenderedTexture(IIconContainer aIcon) {
        this(aIcon, Dyes._NULL.mRGBa);
    }


    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOffset) {
        ArrayList<BakedQuad> quads = new ArrayList<>();
        TextureAtlasSprite sprite = mIconContainer.getIcon();

        return quads;
    }



    @Override
    public int applyColor(int tint) {
        return mRGBa;
    }

    @Override
    public boolean isValidTexture() {
        return mIconContainer != null;
    }

}