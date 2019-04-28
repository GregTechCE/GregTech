package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Matrix4;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFastRenderMetaTileEntity {

    int RENDER_PASS_NORMAL = 0;
    int RENDER_PASS_TRANSLUCENT = 1;

    @SideOnly(Side.CLIENT)
    void renderMetaTileEntityFast(CCRenderState renderState, Matrix4 translation, float partialTicks);

    AxisAlignedBB getRenderBoundingBox();

    default boolean shouldRenderInPass(int pass) {
        return pass == RENDER_PASS_NORMAL;
    }

    default boolean isGlobalRenderer() {
        return false;
    }
}
