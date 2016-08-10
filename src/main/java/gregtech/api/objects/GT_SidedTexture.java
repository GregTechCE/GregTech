package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GT_SidedTexture implements ITexture {
    private final IIconContainer[] mIconContainer;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public int mRGBa;

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5, short[] aRGBa) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_RenderedTexture");
        mIconContainer = new IIconContainer[]{aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5};
        mRGBa = new Color(aRGBa[0], aRGBa[1], aRGBa[2], aRGBa[3]).getRGB();
    }

    public GT_SidedTexture(IIconContainer aIcon0, IIconContainer aIcon1, IIconContainer aIcon2, IIconContainer aIcon3, IIconContainer aIcon4, IIconContainer aIcon5) {
        this(aIcon0, aIcon1, aIcon2, aIcon3, aIcon4, aIcon5, Dyes._NULL.mRGBa);
    }

    public GT_SidedTexture(IIconContainer aBottom, IIconContainer aTop, IIconContainer aSides, short[] aRGBa) {
        this(aBottom, aTop, aSides, aSides, aSides, aSides, aRGBa);
    }

    public GT_SidedTexture(IIconContainer aBottom, IIconContainer aTop, IIconContainer aSides) {
        this(aBottom, aTop, aSides, Dyes._NULL.mRGBa);
    }

    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOffset) {
        return Collections.emptyList();
    }

    @Override
    public int applyColor(int tint) {
        return mRGBa;
    }

    @Override
    public boolean isValidTexture() {
        return mIconContainer != null && mIconContainer[0] != null && mIconContainer[1] != null && mIconContainer[2] != null && mIconContainer[3] != null && mIconContainer[4] != null && mIconContainer[5] != null;
    }

}