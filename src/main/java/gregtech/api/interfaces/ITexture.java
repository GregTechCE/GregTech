package gregtech.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface ITexture {

    @SideOnly(Side.CLIENT)
    public List<BakedQuad> getQuads(Block aBlock, BlockPos blockPos, EnumFacing side, float offset);

    public boolean isValidTexture();
}