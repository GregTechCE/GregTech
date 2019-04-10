package gregtech.common.multipart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TileMultipart;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.FluidPipeFluidHandler;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidPipeMultiPart extends PipeMultiPart<FluidPipeType, FluidPipeProperties> {

    private FluidPipeFluidHandler fluidHandler;

    FluidPipeMultiPart() {
    }

    public FluidPipeMultiPart(IPipeTile<FluidPipeType, FluidPipeProperties> sourceTile) {
        super(sourceTile);
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_PART_KEY;
    }

    @Override
    protected PipeMultiPart<FluidPipeType, FluidPipeProperties> toTickablePart() {
        return new FluidPipeActiveMultiPart(this);
    }

    @Override
    public boolean supportsTicking() {
        return false;
    }

    @Override
    protected void onModeChange(boolean isActiveNow) {
        if (!(this instanceof FluidPipeActiveMultiPart) && isActiveNow) {
            FluidPipeMultiPart part = new FluidPipeActiveMultiPart(this);
            ((FluidPipeActiveMultiPart) part).setActivePart(true);
            //mark parts for replacement
            this.isBeingReplaced = true;
            part.isBeingReplaced = true;
            //and then remove old part and add new one
            TileMultipart tileMultipart = tile();
            tileMultipart.remPart(this);
            TileMultipart.addPart(tileMultipart.getWorld(), tileMultipart.getPos(), part);
        } else if (this instanceof FluidPipeActiveMultiPart) {
            ((FluidPipeActiveMultiPart) this).setActivePart(isActiveNow);
        }
    }

    public FluidPipeFluidHandler getFluidHandler() {
        if (fluidHandler == null) {
            this.fluidHandler = new FluidPipeFluidHandler(this);
        }
        return fluidHandler;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapabilityInternal(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
        }
        return super.getCapabilityInternal(capability, facing);
    }

    @SideOnly(Side.CLIENT)
    public boolean renderStatic(Vector3 pos, BlockRenderLayer layer, CCRenderState ccrs) {
        if (MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
            FluidPipeRenderer.INSTANCE.renderPipeBlock(getPipeMaterial(), getPipeType(), getInsulationColor(), ccrs,
                new IVertexOperation[]{new Translation(pos)},
                activeConnections & ~getBlockedConnections());
            getCoverableImplementation().renderCovers(ccrs, new Matrix4().translate(pos));
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return FluidPipeRenderer.INSTANCE.getParticleTexture(this);
    }
}
