package gregtech.common.render.tesr;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Matrix4;
import gregtech.common.blocks.tileentity.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.animation.FastTESR;

public abstract class TileEntityRendererBase<T extends TileEntityBase> extends FastTESR<T> {

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        CCRenderState renderState = CCRenderState.instance();
        renderState.reset();
        renderState.bind(buffer);
        if (te.getWorld() != null) {
            renderState.setBrightness(te.getWorld(), te.getPos());
        }
        Matrix4 translation = new Matrix4().translate(x, y, z);
        draw(te, renderState, translation, partialTicks);
    }

    protected abstract void draw(T tileEntity, CCRenderState renderState, Matrix4 translation, float partialTicks);

    public static class TileEntityRenderBaseItem<T extends TileEntityBase> extends TileEntityItemStackRenderer {

        private final T tileEntityInstance;

        public TileEntityRenderBaseItem(Class<T> tileEntityClass) {
            try {
                this.tileEntityInstance = tileEntityClass.newInstance();
            } catch (InstantiationException | IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void renderByItem(ItemStack stack, float partialTicks) {
            IBlockState renderState = Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata());
            tileEntityInstance.setBlockState(renderState);
            TileEntityRendererDispatcher.instance.render(tileEntityInstance, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
        }

    }
}
