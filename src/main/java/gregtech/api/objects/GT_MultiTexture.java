package gregtech.api.objects;

import gregtech.api.interfaces.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Lets Multiple ITextures Render overlay over each other.
 * <p/>
 * I should have done this much earlier...
 */
public class GT_MultiTexture implements ITexture {
    private final ITexture[] mTextures;

    public GT_MultiTexture(ITexture... aTextures) {
        mTextures = aTextures;
    }

    @Override
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset) {
        ArrayList<BakedQuad> quads = new ArrayList<>();
        for(int index = 0; index < mTextures.length; index++) {
            quads.addAll(mTextures[index].getQuads(aBlock, blockPos, side, offset + 0.001F * index));
        }
        return quads;
    }

    @Override
    public boolean isValidTexture() {
        return true;
    }

}