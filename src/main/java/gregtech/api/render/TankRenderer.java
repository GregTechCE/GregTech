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
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TankRenderer extends CTCubeRenderer {

    public TankRenderer(String basePath) {
        super(basePath);
    }

    @SideOnly(Side.CLIENT)
    public void renderFluid(CCRenderState renderState, Matrix4 translation, int connectionMask, double fillPercent, FluidStack fluidStack) {
        if (fluidStack != null) {
            int fluidStackColor = fluidStack.getFluid().getColor(fluidStack);
            double fluidLevelOffset = (offset(EnumFacing.UP, connectionMask) + offset(EnumFacing.DOWN, connectionMask));
            double fluidLevel = fillPercent * (1.0 - fluidLevelOffset);

            Cuboid6 resultFluidCuboid = createFullOffsetCuboid(connectionMask);
            int resultFluidColor;
            if (fluidStack.getFluid().isGaseous(fluidStack)) {
                int opacity = (int) (fillPercent * 255);
                resultFluidColor = GTUtility.convertRGBtoRGBA_CL(fluidStackColor, opacity);
            } else {
                resultFluidCuboid.max.y = resultFluidCuboid.min.y + fluidLevel;
                resultFluidColor = GTUtility.convertRGBtoOpaqueRGBA_CL(fluidStackColor);
            }

            ColourMultiplier multiplier = new ColourMultiplier(resultFluidColor);
            IVertexOperation[] fluidPipeline = new IVertexOperation[]{multiplier};
            TextureAtlasSprite fluidSprite = TextureUtils.getTexture(fluidStack.getFluid().getStill(fluidStack));

            for (EnumFacing renderSide : EnumFacing.VALUES) {
                if (hasFaceBit(connectionMask, renderSide)) continue;
                Textures.renderFace(renderState, translation, fluidPipeline, renderSide, resultFluidCuboid, fluidSprite);
            }
        }
    }

    private static Cuboid6 createFullOffsetCuboid(int connectionMask) {
        Cuboid6 cuboid6 = new Cuboid6();
        for (EnumFacing side : EnumFacing.VALUES) {
            double offset = offset(side, connectionMask);
            double value = side.getAxisDirection() == AxisDirection.POSITIVE ? 1.0 - offset : offset;
            cuboid6.setSide(side, value);
        }
        return cuboid6;
    }

    private static double offset(EnumFacing side, int connectionMask) {
        return hasFaceBit(connectionMask, side) ? 0.0 : 0.003;
    }

}
