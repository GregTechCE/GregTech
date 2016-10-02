package gregtech.api.objects;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.common.render.blocks.IBlockIconProvider;
import gregtech.common.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GT_CopiedBlockTexture implements ITexture {
    private final Block mBlock;
    private final byte mSide, mMeta;

    public int mRGBa;

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta, short[] aRGBa) {
        mBlock = aBlock;
        mRGBa = aRGBa == null ? -1 : makeColor(aRGBa);
        mSide = (byte) (aSide - 1);
        mMeta = (byte) aMeta;
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

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta) {
        this(aBlock, aSide, aMeta, null);
    }

    @SideOnly(Side.CLIENT)
    public static TextureAtlasSprite getSide(Block aBlock, int aMeta, EnumFacing side) {
        if (aBlock instanceof IBlockIconProvider) {
            IBlockIconProvider iconProvider = ((IBlockIconProvider) aBlock);
            return iconProvider.getIcon(FMLClientHandler.instance().getWorldClient(), null, side, aMeta);
        }
        System.out.println("Failed to copy texture of " + aBlock.getRegistryName() + " " + aMeta);
        return Textures.BlockIcons.RENDERING_ERROR.getIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset) {
        if(side != null) {
            TextureAtlasSprite sprite = getSide(mBlock, mMeta, EnumFacing.VALUES[mSide]);
            if(sprite != null) {
                BakedQuad quad = RenderUtil.renderSide(sprite, side, offset, mRGBa);
                return Collections.singletonList(quad);
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isValidTexture() {
        return mBlock != null;
    }

}