package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

public class TankRenderer extends CTCubeRenderer {

    public TankRenderer(String basePath) {
        super(basePath);
    }

    public void renderFluid(CCRenderState renderState, Matrix4 translation, int connectionMask, double fillPercent, FluidStack fluidStack) {
        if (fluidStack != null) {
            double fluidLevelOffset = (offset(EnumFacing.UP, connectionMask) + offset(EnumFacing.DOWN, connectionMask));
            double fluidLevel = fillPercent * (1.0 - fluidLevelOffset);
            Cuboid6 fluidCuboid = new Cuboid6(
                offset(EnumFacing.WEST, connectionMask), 0.0,
                offset(EnumFacing.NORTH, connectionMask),
                1.0 - offset(EnumFacing.EAST, connectionMask), 1.0,
                1.0 - offset(EnumFacing.SOUTH, connectionMask));
            if (fluidStack.getFluid().isGaseous(fluidStack)) {
                double maxHeight = offset(EnumFacing.UP, connectionMask);
                fluidCuboid.min.y = 1.0 - maxHeight - fluidLevel;
                fluidCuboid.max.y = maxHeight;
            } else {
                double minHeight = offset(EnumFacing.DOWN, connectionMask);
                fluidCuboid.min.y = minHeight;
                fluidCuboid.max.y = minHeight + fluidLevel;
            }
            ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(fluidStack.getFluid().getColor(fluidStack)));
            IVertexOperation[] fluidPipeline = new IVertexOperation[]{multiplier};
            TextureAtlasSprite fluidSprite = TextureUtils.getTexture(fluidStack.getFluid().getStill(fluidStack));
            for (EnumFacing renderSide : EnumFacing.VALUES) {
                if (hasFaceBit(connectionMask, renderSide)) continue;
                Textures.renderFace(renderState, translation, fluidPipeline, renderSide, fluidCuboid, fluidSprite);
            }
        }
    }

    private static double offset(EnumFacing side, int connectionMask) {
        return hasFaceBit(connectionMask, side) ? 0.0 : 0.003;
    }

}
