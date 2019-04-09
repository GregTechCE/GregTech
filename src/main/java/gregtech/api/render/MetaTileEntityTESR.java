package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.client.model.animation.FastTESR;

public class MetaTileEntityTESR extends FastTESR<MetaTileEntityHolder> {

    @Override
    public void renderTileEntityFast(MetaTileEntityHolder te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        MetaTileEntity metaTileEntity = te.getMetaTileEntity();
        if (metaTileEntity != null && metaTileEntity.requiresDynamicRendering()) {
            CCRenderState renderState = CCRenderState.instance();
            renderState.reset();
            renderState.bind(buffer);
            renderState.setBrightness(te.getWorld(), te.getPos());
            Matrix4 translation = new Matrix4().translate(x, y, z);
            metaTileEntity.renderMetaTileEntityDynamic(renderState, translation.copy(), new IVertexOperation[0], partialTicks);
        }
    }
}
