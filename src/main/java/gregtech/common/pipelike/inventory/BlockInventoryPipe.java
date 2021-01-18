package gregtech.common.pipelike.inventory;

import gregtech.api.pipenet.block.simple.BlockSimplePipe;
import gregtech.api.pipenet.block.simple.EmptyNodeData;
import gregtech.api.pipenet.tickable.TickableWorldPipeNetEventHandler;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.common.pipelike.inventory.net.InventoryPipeNet;
import gregtech.common.pipelike.inventory.net.WorldInventoryPipeNet;
import gregtech.common.pipelike.inventory.tile.TileEntityInventoryPipe;
import gregtech.common.pipelike.inventory.tile.TileEntityInventoryPipeTickable;
import gregtech.common.render.InventoryPipeRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

public class BlockInventoryPipe extends BlockSimplePipe<InventoryPipeType, EmptyNodeData, WorldInventoryPipeNet> {

    static {
        TickableWorldPipeNetEventHandler.registerTickablePipeNet(WorldInventoryPipeNet::getWorldPipeNet);
    }

    public BlockInventoryPipe() {
        // Review: without this, the name is tile.pipe.name which doesn't seem correct?
        setUnlocalizedName("inventory_pipe");
    }

    @Override
    public TileEntityPipeBase<InventoryPipeType, EmptyNodeData> createNewTileEntity(boolean supportsTicking) {
        return supportsTicking ? new TileEntityInventoryPipeTickable() : new TileEntityInventoryPipe();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isRemote) {
            InventoryPipeNet inventoryPipeNet = getWorldPipeNet(worldIn).getNetFromPos(pos);
            if (inventoryPipeNet != null) {
                inventoryPipeNet.nodeNeighbourChanged(pos);
            }
        }
    }

    @Override
    public int getActiveNodeConnections(IBlockAccess world, BlockPos nodePos, IPipeTile<InventoryPipeType, EmptyNodeData> selfTileEntity) {
        int activeNodeConnections = 0;
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos offsetPos = nodePos.offset(side);
            TileEntity tileEntity = world.getTileEntity(offsetPos);
            if(tileEntity != null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())) {
                activeNodeConnections |= 1 << side.getIndex();
            }
        }
        return activeNodeConnections;
    }

    @Override
    protected EmptyNodeData createProperties(InventoryPipeType inventoryPipeType) {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    public Class<InventoryPipeType> getPipeTypeClass() {
        return InventoryPipeType.class;
    }

    @Override
    protected EmptyNodeData getFallbackType() {
        return EmptyNodeData.INSTANCE;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (InventoryPipeType pipeType : InventoryPipeType.values()) {
            items.add(new ItemStack(this, 1, pipeType.ordinal()));
        }
    }

    @Override
    public WorldInventoryPipeNet getWorldPipeNet(World world) {
        return WorldInventoryPipeNet.getWorldPipeNet(world);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return InventoryPipeRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected Pair<TextureAtlasSprite, Integer> getParticleTexture(World world, BlockPos blockPos) {
        return InventoryPipeRenderer.INSTANCE.getParticleTexture((TileEntityInventoryPipe) world.getTileEntity(blockPos));
    }
}
