package gregtech.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface ITexture {

    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, int tintOffset);

    public int applyColor(int tint);

    public boolean isValidTexture();
}