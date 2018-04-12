package gregtech.api.cable;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.cable.net.EnergyNet;
import gregtech.api.cable.net.WorldENet;
import gregtech.api.cable.tile.TileEntityCable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCable extends Block implements ITileEntityProvider {

    public static final PropertyEnum<Insulation> INSULATION = PropertyEnum.create("insulation", Insulation.class);

    public final WireProperties baseProps;
    private WireProperties[] insulatedPropsCache;

    public BlockCable(WireProperties cableProperties) {
        super(Material.IRON);
        this.baseProps = cableProperties;
        setUnlocalizedName("cable");
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHarvestLevel("cutter", 1);
        setHardness(2.0f);
        setResistance(3.0f);
    }

    protected void initPropsCache() {
        Insulation[] insulationArray = Insulation.values();
        this.insulatedPropsCache = new WireProperties[insulationArray.length];
        for(int i = 0; i < insulationArray.length; i++) {
            Insulation insulation = insulationArray[i];
            int totalAmperage = baseProps.amperage * insulation.amperage;
            int totalLossPerBlock = baseProps.lossPerBlock * insulation.amperage * insulation.lossMultiplier;
            this.insulatedPropsCache[i] = new WireProperties(baseProps.material, baseProps.voltage,
                totalAmperage, totalLossPerBlock);
        }
    }

    public ItemStack getItem(Insulation insulation) {
        return new ItemStack(this, 1, insulation.ordinal());
    }

    public static Insulation getInsulation(ItemStack itemStack) {
        return Insulation.values()[itemStack.getMetadata()];
    }

    public WireProperties getProperties(Insulation insulation) {
        if(insulatedPropsCache == null) {
            initPropsCache();
        }
        return insulatedPropsCache[insulation.ordinal()];
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, INSULATION);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(INSULATION, Insulation.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for(Insulation insulation : Insulation.values()) {
            items.add(getItem(insulation));
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        WorldENet worldENet = WorldENet.getWorldENet(worldIn);
        EnergyNet energyNet = worldENet.getNetFromPos(pos);
        if(energyNet != null) {
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
                    energyNet.addNode(pos, getProperties(state.getValue(INSULATION)));
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
            if(energyContainer != null)
                return true;
        }
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(getItem(state.getValue(INSULATION)));
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
