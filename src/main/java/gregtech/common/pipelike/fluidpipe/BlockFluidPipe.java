package gregtech.common.pipelike.fluidpipe;

import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.type.Material;
import gregtech.common.pipelike.fluidpipe.net.WorldFluidPipeNet;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipe;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockFluidPipe extends BlockPipe<FluidPipeType, FluidPipeProperties, WorldFluidPipeNet> {

    public BlockFluidPipe(Material material, FluidPipeProperties baseProperties) {
        super(material, baseProperties);
        setHarvestLevel(ModHandler.isMaterialWood(material) ? "axe" : "wrench", 1);
    }

    @Override
    public Class<FluidPipeType> getPipeTypeClass() {
        return FluidPipeType.class;
    }

    @Override
    public WorldFluidPipeNet getWorldPipeNet(World world) {
        return WorldFluidPipeNet.getWorldPipeNet(world);
    }

    @Override
    //because fluid pipe networks are monolithic - pipes of different kinds just don't connect to each other
    protected boolean canPipesConnect(IPipeTile<FluidPipeType, FluidPipeProperties> selfTile, EnumFacing side, IPipeTile<FluidPipeType, FluidPipeProperties> sideTile) {
        return selfTile.getNodeData().equals(sideTile.getNodeData());
    }

    @Override
    public int getActiveNodeConnections(IBlockAccess world, BlockPos nodePos) {
        int activeNodeConnections = 0;
        for(EnumFacing side : EnumFacing.VALUES) {
            BlockPos offsetPos = nodePos.offset(side);
            TileEntity tileEntity = world.getTileEntity(offsetPos);
            //do not connect to null cables and ignore cables
            if(tileEntity == null || getPipeTileEntity(tileEntity) != null) continue;
            EnumFacing opposite = side.getOpposite();
            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, opposite);
            if(fluidHandler != null) {
                activeNodeConnections |= 1 << side.getIndex();
            }
        }
        return activeNodeConnections;
    }

    @Override
    protected void onActiveModeChange(World world, BlockPos pos, boolean isActiveNow, boolean isInitialChange) {
        TileEntityFluidPipe newTileEntity = isActiveNow ?
            new TileEntityFluidPipeActive() : new TileEntityFluidPipe();
        if(!isInitialChange) {
            TileEntity oldTileEntity = world.getTileEntity(pos);
            if(oldTileEntity instanceof TileEntityFluidPipe) {
                newTileEntity.transferDataFrom((TileEntityFluidPipe) oldTileEntity);
            }
        }
        world.setTileEntity(pos, newTileEntity);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFluidPipe();
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return FluidPipeRenderer.BLOCK_RENDER_TYPE;
    }
}
