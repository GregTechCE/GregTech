package gregtech.common.render.tesr;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Rotation;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockCrusherBlade;
import gregtech.common.blocks.tileentity.TileEntityCrusherBlade;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class TileEntityCrusherBladeRenderer extends TileEntityRendererBase<TileEntityCrusherBlade> {

    @Override
    protected void draw(TileEntityCrusherBlade tileEntity, CCRenderState renderState, Matrix4 translation, float partialTicks) {
        translation.translate(0.5, 0.5, 0.5);

        IBlockState blockState = tileEntity.getBlockState();
        switch (blockState.getValue(BlockCrusherBlade.AXIS)) {
            case Y: break;
            case X: translation.rotate(Math.toRadians(90.0), Rotation.axes[3]); break;
            case Z: translation.rotate(Math.toRadians(90.0), Rotation.axes[5]); break;
        }

        if(blockState.getValue(BlockCrusherBlade.ACTIVE)) {
            long currentWorldTime = tileEntity.hasWorld() ? tileEntity.getWorld().getTotalWorldTime() : 0;
            translation.rotate(Math.toRadians(currentWorldTime * 12.0 % 180), Rotation.axes[1]);
        }

        translation.translate(-0.5, -0.5, -0.5);

        TextureAtlasSprite ironBlockTexture = TextureUtils.getBlockTexture("iron_block");
        IVertexOperation[] operations = {};

        for(Cuboid6 cuboid6 : BlockCrusherBlade.basicModel) {
            for(EnumFacing renderSide : EnumFacing.VALUES) {
                Textures.renderFace(renderState, translation, operations, renderSide, cuboid6, ironBlockTexture);
            }
        }
    }

}
