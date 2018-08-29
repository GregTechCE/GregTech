package gregtech.common.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.IRandomDisplayTickPart;
import codechicken.multipart.TileMultipart;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.common.pipelike.fluidpipe.LeakableFluidPipeTile;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.FluidPipeProperties;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.pipelike.fluidpipe.tile.FluidPipeFluidHandler;
import gregtech.common.render.FluidPipeRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class FluidPipeMultiPart extends PipeMultiPart<FluidPipeType, FluidPipeProperties> implements ICapabilityProvider, IRandomDisplayTickPart, LeakableFluidPipeTile {

    private FluidPipeFluidHandler fluidHandler;

    private boolean isBurning = false;
    private boolean isLeaking = false;
    private long minimumPipeLifeTime = 0L;

    FluidPipeMultiPart() {}

    public FluidPipeMultiPart(PipeMultiPart<FluidPipeType, FluidPipeProperties> sourceTile) {
        super(sourceTile);
        this.isLeaking = ((FluidPipeMultiPart) sourceTile).isLeaking;
        this.isBurning = ((FluidPipeMultiPart) sourceTile).isBurning;
    }

    public FluidPipeMultiPart(IBlockState blockState, TileEntity tile) {
        super(blockState, tile);
    }


    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.FLUID_PIPE_PART_KEY;
    }

    @Override
    public void randomDisplayTick(Random random) {
        if (isLeaking) {
            EnumFacing direction = EnumFacing.VALUES[random.nextInt(6)];
            int particleCount = 5 + random.nextInt(6);
            BlockFluidPipe.spawnParticles(world(), pos(), direction, EnumParticleTypes.CLOUD, particleCount, random);
        } else if (isBurning) {
            EnumFacing direction = EnumFacing.VALUES[random.nextInt(6)];
            int particleCount = 7 + random.nextInt(6);
            BlockFluidPipe.spawnParticles(world(), pos(), direction, EnumParticleTypes.FLAME, particleCount, random);
        }
    }

    private void onDestroyPipeTick(Random random) {
        if(this.isLeaking) {
            if (random.nextInt(100) <= 15) {
                //with small chance, explode non-gas-proof pipes
                world().createExplosion(null, pos().getX() + 0.5, pos().getY() + 0.5, pos().getZ() + 0.5, 2.0f + world().rand.nextFloat(), true);
            } else {
                //otherwise, just remove gas leaking animation
                this.isLeaking = false;
                sendDescUpdate();
            }
        }
        if(this.isBurning) {
            //for multipart tiles, just remove part
            tile().remPart(this);
        }
    }

    @Override
    public void scheduledTick() {
        super.scheduledTick();
        if(minimumPipeLifeTime > 0L && world().getTotalWorldTime() >= minimumPipeLifeTime) {
            this.minimumPipeLifeTime = 0L;
            onDestroyPipeTick(world().rand);
        }
    }

    @Override
    public void markAsBurning() {
        this.isBurning = true;
        scheduleDestroyTick(60 + world().rand.nextInt(80));
        sendDescUpdate();
    }

    @Override
    public void markAsLeaking() {
        this.isLeaking = true;
        scheduleDestroyTick(80 + world().rand.nextInt(120));
        sendDescUpdate();
    }

    private void scheduleDestroyTick(int ticks) {
        this.minimumPipeLifeTime = world().getTotalWorldTime() + ticks;
        scheduleTick(ticks);
    }

    @Override
    public void writeDesc(MCDataOutput packet) {
        super.writeDesc(packet);
        packet.writeBoolean(isBurning);
        packet.writeBoolean(isLeaking);
    }

    @Override
    public void readDesc(MCDataInput packet) {
        super.readDesc(packet);
        this.isBurning = packet.readBoolean();
        this.isLeaking = packet.readBoolean();
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

    @Override
    public void save(NBTTagCompound tag) {
        super.save(tag);
        if(minimumPipeLifeTime > 0L) {
            tag.setLong("MinPipeLifeTime", minimumPipeLifeTime);
            tag.setBoolean("Burning", isBurning);
            tag.setBoolean("Leaking", isLeaking);
        }
    }

    @Override
    public void load(NBTTagCompound tag) {
        super.load(tag);
        this.minimumPipeLifeTime = tag.getLong("MinPipeLifeTime");
        if(minimumPipeLifeTime > 0L) {
            this.isBurning = tag.getBoolean("Burning");
            this.isLeaking = tag.getBoolean("Leaking");
        }
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
