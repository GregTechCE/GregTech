package gregtech.common.blocks;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTResourceLocation;
import gregtech.common.blocks.properties.PropertyString;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.common.blocks.properties.UnlistedBlockAccess.BLOCK_ACCESS;
import static gregtech.common.blocks.properties.UnlistedBlockPos.BLOCK_POS;

@SuppressWarnings("deprecation")
public class BlockMachine extends Block implements ITileEntityProvider {

    public static final PropertyInteger HARVEST_LEVEL = PropertyInteger.create("harvest_level", 0, 4);
    public static final PropertyEnum<ToolClass> HARVEST_TOOL = PropertyEnum.create("harvest_tool", ToolClass.class);

    public static PropertyString META_TYPE;

    // Instantiated after all MTEs are registered
    protected BlockMachine() {
        super(Material.IRON);
        setUnlocalizedName("machine");
        setHardness(6.0f);
        setResistance(8.0f);
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
    }

    @SideOnly(Side.CLIENT)
    public void registerStateMapper() {
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState blockState) {
                ResourceLocation modelLocation = GregTechAPI.METATILEENTITY_REGISTRY.getObject(blockState.getValue(META_TYPE)).getModelLocation();
                return new ModelResourceLocation(new GTResourceLocation(modelLocation.getResourcePath()), null);
            }
        });
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        ItemStack itemStack = placer.getHeldItem(hand);
        IMetaTileEntityFactory factory = ((MachineItemBlock) (itemStack).getItem()).getFactory(itemStack);
        return getDefaultState().withProperty(HARVEST_LEVEL, factory.getHarvestLevel()).withProperty(HARVEST_TOOL, factory.getHarvestTool());
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        ((IGregTechTileEntity) worldIn.getTileEntity(pos)).getMetaTileEntity().onEntityCollidedWithBlock(entityIn);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        IMetaTileEntity tileEntity = ((IGregTechTileEntity) worldIn.getTileEntity(pos)).getMetaTileEntity();
        if(tileEntity.isAccessAllowed(playerIn)) {
            if(heldItem.isEmpty()) {
                return tileEntity.onRightClick(side, playerIn, hand, hitX, hitY, hitZ);
            } else {
                SimpleItemStack stack = new SimpleItemStack(heldItem);
                if(GregTechAPI.screwdriverList.contains(stack)) {
                    return tileEntity.onScrewdriverRightClick(side, playerIn, hand, hitX, hitY, hitZ);
                } else if(GregTechAPI.wrenchList.contains(stack)) {
                    return tileEntity.onWrenchRightClick(side, side, playerIn, hand, hitX, hitY, hitZ);
                } else return tileEntity.onRightClick(side, playerIn, hand, hitX, hitY, hitZ);
            }
        }
        return false;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        IMetaTileEntity tileEntity = ((IGregTechTileEntity) worldIn.getTileEntity(pos)).getMetaTileEntity();
        if(tileEntity.isAccessAllowed(playerIn)) {
            tileEntity.onLeftClick(playerIn);
        }
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return ((IGregTechTileEntity) world.getTileEntity(pos)).getMetaTileEntity().canConnectRedstone(side);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return ((IGregTechTileEntity) blockAccess.getTileEntity(pos)).getMetaTileEntity().getOutputRedstoneSignal(side);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return ((IGregTechTileEntity) worldIn.getTileEntity(pos)).getMetaTileEntity().getComparatorValue();
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        IProperty<?>[] properties = {HARVEST_LEVEL, HARVEST_TOOL, META_TYPE};
        IUnlistedProperty<?>[] unlistedProperties = {BLOCK_ACCESS, BLOCK_POS};
        return new ExtendedBlockState(this, properties, unlistedProperties);
    }

    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        return extendedBlockState.withProperty(BLOCK_ACCESS, world).withProperty(BLOCK_POS, pos);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileentity = worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);

        if (!(tileentity instanceof GregtechTileEntity)) return state;

        IMetaTileEntity mte = ((GregtechTileEntity) tileentity).getMetaTileEntity();
        if (mte == null) return state;

        state = state.withProperty(META_TYPE, GregTechAPI.METATILEENTITY_REGISTRY.getNameForObject(mte.getFactory()));

        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int harvestLevel;
        int tool;
        if ((meta & 0b1100) == 0b1100) {
            harvestLevel = 4;
            tool = meta & 0b0011;
        } else {
            harvestLevel = meta & 0b0011;
            tool = meta & 0b1100;
        }
        return getDefaultState()
                .withProperty(HARVEST_LEVEL, harvestLevel)
                .withProperty(HARVEST_TOOL, ToolClass.values()[tool]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int harvestLevel = state.getValue(HARVEST_LEVEL);
        int tool = state.getValue(HARVEST_TOOL).ordinal();
        if (harvestLevel < 4) {
            tool <<= 2;
        } else {
            harvestLevel |= 1 << 4;
        }
        return harvestLevel & tool;
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return state.getValue(HARVEST_TOOL).getName();
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return state.getValue(HARVEST_LEVEL);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null; //handled by ItemBlock
    }

    public enum ToolClass implements IStringSerializable {

        WRENCH("wrench"),
        AXE("axe"),
        CUTTER("cutter");

        private final String name;

        ToolClass(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

}
