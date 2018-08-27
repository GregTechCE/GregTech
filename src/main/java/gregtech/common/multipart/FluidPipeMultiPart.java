package gregtech.common.multipart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TileMultipart;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.pipelike.cable.BlockCable;
import gregtech.common.pipelike.cable.Insulation;
import gregtech.common.pipelike.cable.WireProperties;
import gregtech.common.pipelike.cable.tile.CableEnergyContainer;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.FluidPipeFluidHandler;
import gregtech.common.render.CableRenderer;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidPipeMultiPart extends PipeMultiPart<FluidPipeType, FluidPipeProperties> implements ICapabilityProvider {

    private FluidPipeFluidHandler fluidHandler;

    FluidPipeMultiPart() {}

    public FluidPipeMultiPart(PipeMultiPart<FluidPipeType, FluidPipeProperties> sourceTile) {
        super(sourceTile);
    }

    public FluidPipeMultiPart(IBlockState blockState, TileEntity tile) {
        super(blockState, tile);
    }


    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_PART_KEY;
    }

    @Override
    protected void onModeChange(boolean isActiveNow) {
        FluidPipeMultiPart part = isActiveNow ?
            new FluidPipeActiveMultiPart(this) :
            new FluidPipeMultiPart(this);
        //mark parts for replacement
        this.isBeingReplaced = true;
        part.isBeingReplaced = true;
        //and then remove old part and add new one
        TileMultipart tileMultipart = tile();
        tileMultipart.remPart(this);
        TileMultipart.addPart(tileMultipart.getWorld(),
            tileMultipart.getPos(), part);
    }

    public FluidPipeFluidHandler getFluidHandler() {
        if(fluidHandler == null) {
            this.fluidHandler = new FluidPipeFluidHandler(this);
        }
        return fluidHandler;
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        return capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getFluidHandler());
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public boolean renderStatic(Vector3 pos, BlockRenderLayer layer, CCRenderState ccrs) {
        TileMultipart tileMultipart = tile();
        ccrs.setBrightness(tileMultipart.getWorld(), tileMultipart.getPos());
        FluidPipeRenderer.INSTANCE.renderPipeBlock(getPipeBlock().material, getPipeType(), insulationColor, ccrs,
            new IVertexOperation[] {new Translation(tileMultipart.getPos())},
            activeConnections & ~getBlockedConnections());
        return true;
    }


}
