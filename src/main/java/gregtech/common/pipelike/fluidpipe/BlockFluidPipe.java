package gregtech.common.pipelike.fluidpipe;

import com.google.common.base.Preconditions;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTUtility;
import gregtech.common.pipelike.fluidpipe.net.FluidPipeNet;
import gregtech.common.pipelike.fluidpipe.net.WorldFluidPipeNet;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipe;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class BlockFluidPipe extends BlockPipe<FluidPipeType, FluidPipeProperties, WorldFluidPipeNet> {

    private final SortedMap<Material, FluidPipeProperties> enabledMaterials = new TreeMap<>();

    public BlockFluidPipe() {
        setHarvestLevel("pickaxe", 1);
    }

    public void addPipeMaterial(Material material, FluidPipeProperties fluidPipeProperties) {
        Preconditions.checkNotNull(material, "material");
        Preconditions.checkNotNull(fluidPipeProperties, "fluidPipeProperties");
        Preconditions.checkArgument(Material.MATERIAL_REGISTRY.getNameForObject(material) != null,"material is not registered");
        this.enabledMaterials.put(material, fluidPipeProperties);
    }

    public Collection<Material> getEnabledMaterials() {
        return Collections.unmodifiableSet(enabledMaterials.keySet());
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
    protected FluidPipeProperties createProperties(FluidPipeType fluidPipeType, Material material) {
        return fluidPipeType.modifyProperties(enabledMaterials.get(material));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (Material material : enabledMaterials.keySet()) {
            for (FluidPipeType fluidPipeType : FluidPipeType.values()) {
                items.add(getItem(fluidPipeType, material));
            }
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
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos offsetPos = nodePos.offset(side);
            TileEntity tileEntity = world.getTileEntity(offsetPos);
            //do not connect to null cables and ignore cables
            if (tileEntity == null || getPipeTileEntity(tileEntity) != null) continue;
            EnumFacing opposite = side.getOpposite();
            IFluidHandler fluidHandler = tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, opposite);
            if (fluidHandler != null) {
                activeNodeConnections |= 1 << side.getIndex();
            }
        }
        return activeNodeConnections;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
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
                } else if (fluidTemperature <= 183) {
                    //-90C, temperature of freezing of many gases
                    if (!GTUtility.isWearingFullFrostHazmat(entityLiving)) {
                        float damageAmount = fluidTemperature / 2.0f;
                        entityLiving.attackEntityFrom(DamageSources.getFrostDamage(), damageAmount);
                    }
                }
            }
        }
    }

    @Override
    public TileEntityPipeBase<FluidPipeType, FluidPipeProperties> createNewTileEntity(boolean supportsTicking) {
        return supportsTicking ? new TileEntityFluidPipeActive() : new TileEntityFluidPipe();
    }

    @Override
    protected void onActiveModeChange(World world, BlockPos pos, boolean isActiveNow, boolean isInitialChange) {
        TileEntityFluidPipe oldTileEntity = (TileEntityFluidPipe) world.getTileEntity(pos);
        if (!(oldTileEntity instanceof TileEntityFluidPipeActive) && isActiveNow) {
            TileEntityFluidPipeActive newTileEntity = new TileEntityFluidPipeActive();
            newTileEntity.transferDataFrom(oldTileEntity);
            newTileEntity.setActive(true);
            world.setTileEntity(pos, newTileEntity);
        } else if (oldTileEntity instanceof TileEntityFluidPipeActive) {
            ((TileEntityFluidPipeActive) oldTileEntity).setActive(isActiveNow);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return FluidPipeRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
