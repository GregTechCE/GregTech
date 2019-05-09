package gregtech.api.metatileentity;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IRenderMetaTileEntity {

    int RENDER_PASS_NORMAL = 0;
    int RENDER_PASS_TRANSLUCENT = 1;

    @SideOnly(Side.CLIENT)
    void renderMetaTileEntityDynamic(double x, double y, double z, float partialTicks);

    AxisAlignedBB getRenderBoundingBox();

    default boolean shouldRenderInPass(int pass) {
        return pass == RENDER_PASS_NORMAL;
    }

    default boolean isGlobalRenderer() {
        return false;
    }
}
