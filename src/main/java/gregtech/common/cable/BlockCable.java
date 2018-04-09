package gregtech.common.cable;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.common.cable.data.Insulation;
import gregtech.common.cable.net.EnergyNet;
import gregtech.common.cable.net.WorldENet;
import gregtech.common.cable.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCable extends Block implements ITileEntityProvider {

    public static final PropertyEnum<Insulation> INSULATION = PropertyEnum.create("insulation", Insulation.class);
    public final MetalMaterial wireMaterial;


    public BlockCable(MetalMaterial wireMaterial) {
        super(Material.IRON);
        this.wireMaterial = wireMaterial;
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHarvestLevel("cutter", 1);
        setHardness(2.0f);
        setResistance(3.0f);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, INSULATION);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        WorldENet worldENet = WorldENet.getWorldENet(worldIn);
        EnergyNet energyNet = worldENet.getNetFromPos(pos);
        if(energyNet != null) {
            //this will cause network rebuilt
            energyNet.removeNode(pos);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        boolean hasCapability = hasEnergyCapabilities(worldIn, pos);
        WorldENet worldENet = WorldENet.getWorldENet(worldIn);
        EnergyNet energyNet = null;
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = pos.offset(facing);
            //only connect if offset block is same block type
            if(worldIn.getBlockState(offsetPos).getBlock() instanceof BlockCable) {
                EnergyNet offsetEnergyNet = worldENet.getNetFromPos(offsetPos);
                if(offsetEnergyNet == null)
                    continue;
                if(energyNet == null) {
                    energyNet = offsetEnergyNet;
                    energyNet.addNode(pos);
                } else if(energyNet != offsetEnergyNet) {
                    //if there is another e-net here, unite with it
                    energyNet.uniteNetworks(offsetEnergyNet);
                }
            }
        }
        if(energyNet == null) {
            energyNet = new EnergyNet(worldENet);
            worldENet.addEnergyNet(energyNet);
            worldENet.markDirty();
        }
        if(hasCapability) {
            TileEntity tileEntity = new TileEntityCable();
            worldIn.setTileEntity(pos, tileEntity);
            energyNet.markNodeAsActive(pos);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean hasTileEntity = worldIn.getTileEntity(pos) != null;
        boolean hasCapability = hasEnergyCapabilities(worldIn, pos);
        EnergyNet energyNet = WorldENet.getWorldENet(worldIn).getNetFromPos(pos);
        if(energyNet != null) {
            if(hasCapability && !hasTileEntity) {
                TileEntity tileEntity = new TileEntityCable();
                worldIn.setTileEntity(pos, tileEntity);
                energyNet.markNodeAsActive(pos);
            } else if(!hasCapability && hasTileEntity) {
                worldIn.removeTileEntity(pos);
                energyNet.markNodeAsInactive(pos);
            }
        }
    }

    private static boolean hasEnergyCapabilities(World worldIn, BlockPos pos) {
        for(EnumFacing facing : EnumFacing.VALUES) {
            BlockPos offsetPos = pos.offset(facing);
            TileEntity tileEntity = worldIn.getTileEntity(offsetPos);
            if(tileEntity == null || tileEntity instanceof TileEntityCable) continue;
            EnumFacing opposite = facing.getOpposite();
            IEnergyContainer energyContainer = tileEntity.getCapability(IEnergyContainer.CAPABILITY_ENERGY_CONTAINER, opposite);
            if(energyContainer != null && (energyContainer.inputsEnergy(opposite) ||
                    energyContainer.outputsEnergy(opposite)))
                return true;
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
