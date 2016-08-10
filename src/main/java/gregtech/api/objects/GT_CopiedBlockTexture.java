package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class GT_CopiedBlockTexture implements ITexture {
    private final Block mBlock;
    private final byte mSide, mMeta;
    /**
     * DO NOT MANIPULATE THE VALUES INSIDE THIS ARRAY!!!
     * <p/>
     * Just set this variable to another different Array instead.
     * Otherwise some colored things will get Problems.
     */
    public int mRGBa;

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta, short[] aRGBa) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_CopiedBlockTexture");
        mBlock = aBlock;
        mRGBa = new Color(aRGBa[0], aRGBa[1], aRGBa[2], aRGBa[3]).getRGB();
        mSide = (byte) aSide;
        mMeta = (byte) aMeta;
    }

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta) {
        this(aBlock, aSide, aMeta, Dyes._NULL.mRGBa);
    }
    public List<BakedQuad> getSideQuads(Block aBlock, int aMeta, EnumFacing side) {
        Minecraft mc = Minecraft.getMinecraft();
        IBlockState blockState = aBlock.getStateFromMeta(aMeta);
        IBakedModel model = mc.getBlockRendererDispatcher().getModelForState(blockState);
        return model.getQuads(blockState, side, mc.theWorld.rand.nextLong());
    }

    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOffset) {
        if(side.getIndex() == mSide) {
            List<BakedQuad> quads = getSideQuads(mBlock, mMeta, side);
            for(BakedQuad bakedQuad : quads) {
                ObfuscationReflectionHelper.setPrivateValue(BakedQuad.class, bakedQuad, tintOffset + side.getIndex(), 1);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public int applyColor(int tint) {
        if(tint == mSide) {
            return mRGBa;
        }
        return -1;
    }


    @Override
    public boolean isValidTexture() {
        return mBlock != null;
    }

}