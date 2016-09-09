package gregtech.api.objects;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
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

    public int mRGBa;

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta, short[] aRGBa) {
        if (aRGBa.length != 4) throw new IllegalArgumentException("RGBa doesn't have 4 Values @ GT_CopiedBlockTexture");
        mBlock = aBlock;
        mRGBa = makeColor(aRGBa);
        mSide = (byte) aSide;
        mMeta = (byte) aMeta;
    }

    private int makeColor(short[] rgba) {
        short[] nullRGBA = Materials._NULL.getRGBA();
        short red = rgba[0] > 0 && 255 > rgba[0] ? rgba[0] : nullRGBA[0];
        short green = rgba[1] > 0 && 255 > rgba[1] ? rgba[1] : nullRGBA[1];
        short blue = rgba[2] > 0 && 255 > rgba[2] ? rgba[2] : nullRGBA[2];
        short alpha = rgba[3] > 0 && 255 > rgba[3] ? rgba[3] : nullRGBA[3];
        return new Color(red, green, blue, alpha).getRGB();
    }

    public GT_CopiedBlockTexture(Block aBlock, int aSide, int aMeta) {
        this(aBlock, aSide, aMeta, Dyes._NULL.mRGBa);
    }

    public List<BakedQuad> getSideQuads(Block aBlock, int aMeta, EnumFacing side) {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            IBlockState blockState = aBlock.getStateFromMeta(aMeta);
            IBakedModel model = mc.getBlockRendererDispatcher().getModelForState(blockState);
            return model.getQuads(blockState, side, mc.theWorld.rand.nextLong());
        } catch (Throwable error) {
            System.out.println("Failed to gen side quads of " + aBlock.getRegistryName() + " " + mMeta);
            error.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOffset) {
        List<BakedQuad> quads = getSideQuads(mBlock, mMeta, EnumFacing.VALUES[mSide > 0 ? mSide - 1 : 0]);
        for (BakedQuad bakedQuad : quads) {
            ObfuscationReflectionHelper.setPrivateValue(BakedQuad.class, bakedQuad, tintOffset, 1);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isValidTexture() {
        return mBlock != null;
    }

}