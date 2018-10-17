package gregtech.common.pipelike.fluidpipe;

import gregtech.api.damagesources.DamageSources;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.pipelike.fluidpipe.net.FluidPipeNet;
import gregtech.common.pipelike.fluidpipe.net.WorldFluidPipeNet;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipe;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.Random;

public class BlockFluidPipe extends BlockPipe<FluidPipeType, FluidPipeProperties, WorldFluidPipeNet> {

    public static final PropertyBool IS_GAS_LEAKING = PropertyBool.create("gas_leaking");
    public static final PropertyBool IS_MELTING = PropertyBool.create("melting");

    public BlockFluidPipe(Material material, FluidPipeProperties baseProperties) {
        super(material, baseProperties);
        setDefaultState(getDefaultState()
            .withProperty(IS_GAS_LEAKING, false)
            .withProperty(IS_MELTING, false));
        setHarvestLevel(ModHandler.isMaterialWood(material) ? "axe" : "wrench", 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        super.createBlockState();
        return new BlockStateContainer(this, pipeVariantProperty, IS_MELTING, IS_GAS_LEAKING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
            .withProperty(pipeVariantProperty, getPipeTypeClass().getEnumConstants()[meta % 8 % 4])
            .withProperty(IS_MELTING, meta % 8 / 4 == 1)
            .withProperty(IS_GAS_LEAKING, meta / 8 == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(pipeVariantProperty).ordinal() +
            (state.getValue(IS_MELTING) ? 4 : 0) +
            (state.getValue(IS_GAS_LEAKING) ? 8 : 0);
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
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(stateIn.getValue(IS_GAS_LEAKING)) {
            EnumFacing direction = EnumFacing.VALUES[rand.nextInt(6)];
            int particleCount = 5 + rand.nextInt(6);
            spawnParticles(worldIn, pos, direction, EnumParticleTypes.CLOUD, particleCount, rand);
        } else if(stateIn.getValue(IS_MELTING)) {
            EnumFacing direction = EnumFacing.VALUES[rand.nextInt(6)];
            int particleCount = 7 + rand.nextInt(6);
            spawnParticles(worldIn, pos, direction, EnumParticleTypes.FLAME, particleCount, rand);
        }
    }

    public static void spawnParticles(World worldIn, BlockPos pos, EnumFacing direction, EnumParticleTypes particleType, int particleCount, Random rand) {
        for(int i = 0; i < particleCount; i++) {
            worldIn.spawnParticle(particleType,
                pos.getX() + 0.5 - direction.getFrontOffsetX() / 1.8,
                pos.getY() + 0.5 - direction.getFrontOffsetY() / 1.8,
                pos.getZ() + 0.5 - direction.getFrontOffsetZ() / 1.8,
                direction.getFrontOffsetX() * 0.2 + rand.nextDouble() * 0.1,
                direction.getFrontOffsetY() * 0.2 + rand.nextDouble() * 0.1,
                direction.getFrontOffsetZ() * 0.2 + rand.nextDouble() * 0.1);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(state.getValue(IS_GAS_LEAKING)) {
            if (rand.nextInt(100) <= 15) {
                //with small chance, explode non-gas-proof pipes
                worldIn.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.0f + rand.nextFloat(), true);
            } else {
                //otherwise, just remove gas leaking animation
                worldIn.setBlockState(pos, state.withProperty(IS_GAS_LEAKING, false));
            }
        }
        if(state.getValue(IS_MELTING)) {
            //this pipe is melting, replace it with fire block
            worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
        }
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
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(entityIn instanceof EntityLivingBase) {
            EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
            FluidPipeNet pipeNet = getWorldPipeNet(worldIn).getNetFromPos(pos);
            if (pipeNet != null) {
                FluidStack fluidStack = pipeNet.getFluidNetTank().getFluid();
                if (fluidStack == null) {
                    return; //pipe network is empty
                }
                int fluidTemperature = fluidStack.getFluid().getTemperature(fluidStack);
                if (fluidTemperature >= 373) {
                    //100C, temperature of boiling water
                    if (!GTUtility.isWearingFullHeatHazmat(entityLiving)) {
                        float damageAmount = (fluidTemperature - 363) / 2.0f;
                        entityLiving.attackEntityFrom(DamageSources.getHeatDamage(), damageAmount);
                    }
                } else if(fluidTemperature <= 183) {
                    //-90C, temperature of freezing of many gases
                    if(!GTUtility.isWearingFullFrostHazmat(entityLiving)) {
                        float damageAmount = fluidTemperature / 2.0f;
                        entityLiving.attackEntityFrom(DamageSources.getFrostDamage(), damageAmount);
                    }
                }
            }
        }
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
