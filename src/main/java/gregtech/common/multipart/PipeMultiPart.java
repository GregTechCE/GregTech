package gregtech.common.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.multipart.*;
import com.google.common.collect.Lists;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.IPipeTile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class PipeMultiPart<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, IPipeTile<PipeType, NodeDataType> {

    private BlockPipe<PipeType, NodeDataType, ?> pipeBlock;
    private PipeType pipeType;
    protected int insulationColor = DEFAULT_INSULATION_COLOR;
    private int blockedConnections;
    protected int activeConnections;
    protected Cuboid6 centerBox;
    protected List<Cuboid6> sidedConnections = new ArrayList<>();

    //set to true when part is being replaced with modified version of self
    //used to skip pipe net adding and removing code while replacing part
    protected boolean isBeingReplaced = false;

    protected PipeMultiPart() {}

    public PipeMultiPart(PipeMultiPart<PipeType, NodeDataType> sourceTile) {
        //this constructor is used for data transfer
        //when some nodes becomes active and changes itself to become tickable
        //good example is fluid pipes
        transferDataFrom(sourceTile);
    }

    public PipeMultiPart(IBlockState blockState, TileEntity tile) {
        //noinspection unchecked
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) blockState.getBlock();
        this.pipeType = blockState.getValue(pipeBlock.pipeVariantProperty);
        if(tile instanceof IPipeTile) {
            //noinspection unchecked
            transferDataFrom((IPipeTile<PipeType, NodeDataType>) tile);
        }
        this.reinitializeShape();
    }

    @Override
    public void transferDataFrom(IPipeTile<PipeType, NodeDataType> sourceTile) {
        this.insulationColor = sourceTile.getInsulationColor();
        //transfer other part-related data only from multi parts
        if(sourceTile instanceof PipeMultiPart) {
            PipeMultiPart<PipeType, NodeDataType> sourcePart = (PipeMultiPart<PipeType, NodeDataType>) sourceTile;
            this.pipeBlock = sourcePart.pipeBlock;
            this.pipeType = sourcePart.pipeType;
            this.blockedConnections = sourcePart.blockedConnections;
            this.activeConnections = sourcePart.activeConnections;
            this.centerBox = sourcePart.centerBox;
            this.sidedConnections = sourcePart.sidedConnections;
        }
    }

    @Override
    public World getPipeWorld() {
        return this.tile().getWorld();
    }

    @Override
    public BlockPos getPipePos() {
        return this.tile().getPos();
    }

    @Override
    public int getBlockedConnections() {
        return blockedConnections;
    }

    private int getMark() {
        return insulationColor == IPipeTile.DEFAULT_INSULATION_COLOR ? 0 : insulationColor;
    }

    @Override
    public int getInsulationColor() {
        return insulationColor;
    }

    @Override
    public void setInsulationColor(int color) {
        this.insulationColor = color;
        if(!world().isRemote) {
            this.sendDescUpdate();
          pipeBlock.getWorldPipeNet(world()).updateMark(pos(), getMark());
        }
    }

    @Override
    public BlockPipe<PipeType, NodeDataType, ?> getPipeBlock() {
        return pipeBlock;
    }

    @Override
    public PipeType getPipeType() {
        return pipeType;
    }

    @Override
    public NodeDataType getNodeData() {
        return getPipeBlock().getProperties(getPipeType());
    }

    @Override
    public boolean occlusionTest(TMultiPart part) {
        return NormalOcclusionTest.apply(this, part);
    }

    @Override
    public Iterable<ItemStack> getDrops() {
        return Lists.newArrayList(getDropStack());
    }

    @Override
    public ItemStack pickItem(CuboidRayTraceResult hit) {
        return getDropStack();
    }

    private ItemStack getDropStack() {
        return getPipeBlock().getItem(getPipeType());
    }

    private void reinitializeShape() {
        this.centerBox = BlockPipe.getSideBox(null, getPipeType().getThickness());
        updateSidedConnections(false);
    }

    private void updateSidedConnections(boolean notify) {
        float thickness = getPipeType().getThickness();
        this.sidedConnections.clear();
        for(EnumFacing side : EnumFacing.VALUES) {
            int sideIndex = side.getIndex();
            if((activeConnections & (1 << sideIndex)) > 0 &&
                (blockedConnections & (1 << sideIndex)) == 0) {
                this.sidedConnections.add(BlockPipe.getSideBox(side, thickness));
            }
        }

        TileMultipart tileMultipart = tile();
        if (tileMultipart != null && notify) {
            tileMultipart.notifyPartChange(this);
            tileMultipart.markRender();
        }
    }

    private void updateBlockedConnections() {
        TileMultipart tileMultipart = tile();
        if(tileMultipart == null || tileMultipart.isInvalid())
            return;
        float thickness = getPipeType().getThickness();
        int lastBlockedConnections = blockedConnections;

        this.blockedConnections = 0;
        for(EnumFacing cableSide : EnumFacing.VALUES) {
            Cuboid6 sideBox = BlockPipe.getSideBox(cableSide, thickness);
            NormallyOccludedPart part = new NormallyOccludedPart(sideBox);
            if (!tileMultipart.canReplacePart(this, part)) {
                this.blockedConnections |= 1 << cableSide.getIndex();
            }
        }

        if (lastBlockedConnections != blockedConnections) {
            WorldPipeNet<NodeDataType, ?> worldPipeNet = pipeBlock.getWorldPipeNet(world());
            for(EnumFacing side : EnumFacing.VALUES) {
                boolean isBlockedCurrently = (blockedConnections & 1 << side.getIndex()) > 0;
                boolean wasBlocked = (lastBlockedConnections & 1 << side.getIndex()) > 0;
                if(isBlockedCurrently == wasBlocked) continue;
                worldPipeNet.updateBlockedConnections(pos(), side, isBlockedCurrently);
            }
            updateActualConnections();
        }
    }

    private void updateActualConnections() {
        int lastActualConnections = activeConnections;

        TileMultipart tileMultipart = tile();
        if (tileMultipart != null && !tileMultipart.isInvalid()) {
            this.activeConnections = pipeBlock.getActualConnections(this, tileMultipart.getWorld());
        }

        if (lastActualConnections != activeConnections) {
            updateSidedConnections(true);
        }
    }

    @Override
    public void onAdded() {
        if(!this.isBeingReplaced) {
            this.updateBlockedConnections();
            this.updateActualConnections();
            getPipeBlock().getWorldPipeNet(world()).addNode(pos(), getNodeData(), getMark(), getBlockedConnections(),
                pipeBlock.getActiveNodeConnections(world(), pos()) > 0);
            this.isBeingReplaced = false;
        }
    }

    @Override
    public void onRemoved() {
        if(!this.isBeingReplaced) {
            pipeBlock.getWorldPipeNet(world()).removeNode(pos());
            this.isBeingReplaced = false;
        }
    }

    @Override
    public void onPartChanged(TMultiPart part) {
        if(part != this) {
            this.scheduleTick(1);
        }
    }

    @Override
    public void scheduledTick() {
        this.updateBlockedConnections();
    }

    @Override
    public void onNeighborChanged() {
        updateActualConnections();
        getWriteStream().writeByte(1);
        scheduleTick(1);
        boolean isActiveNode = pipeBlock.getActiveNodeConnections(world(), pos()) > 0;
        PipeNet<NodeDataType> pipeNet = pipeBlock.getWorldPipeNet(world()).getNetFromPos(pos());
        if(pipeNet != null) {
            boolean changed = pipeNet.markNodeAsActive(pos(), isActiveNode);
            if(changed) {
                onModeChange(isActiveNode);
            }
        }
    }

    protected void onModeChange(boolean isActiveNow) {
    }

    @Override
    public void read(MCDataInput packet) {
        byte packetType = packet.readByte();
        if (packetType == 0) {
            readDesc(packet);
        } else if (packetType == 1) {
            updateActualConnections();
        }
        tile().markRender();
    }

    @Override
    public void sendDescUpdate() {
        this.getWriteStream().writeByte(0);
        this.writeDesc(this.getWriteStream());
    }

    @Override
    public void save(NBTTagCompound tag) {
        tag.setString("PipeBlock", Block.REGISTRY.getNameForObject(pipeBlock).toString());
        tag.setInteger("PipeType", pipeType.ordinal());
        tag.setInteger("InsulationColor", insulationColor);
        tag.setInteger("ActiveConnections", activeConnections);
        tag.setInteger("BlockedConnections", blockedConnections);
    }

    @Override
    public void load(NBTTagCompound tag) {
        ResourceLocation pipeBlockName = new ResourceLocation(tag.getString("PipeBlock"));
        //noinspection unchecked
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) Block.REGISTRY.getObject(pipeBlockName);
        this.pipeType = pipeBlock.getPipeTypeClass().getEnumConstants()[tag.getInteger("PipeType")];
        this.insulationColor = tag.getInteger("InsulationColor");
        this.activeConnections = tag.getInteger("ActiveConnections");
        this.blockedConnections = tag.getInteger("BlockedConnections");
        reinitializeShape();
    }

    @Override
    public void writeDesc(MCDataOutput packet) {
        packet.writeString(Block.REGISTRY.getNameForObject(pipeBlock).toString());
        packet.writeEnum(pipeType);
        packet.writeInt(insulationColor);
        packet.writeInt(activeConnections);
        packet.writeInt(blockedConnections);
    }

    @Override
    public void readDesc(MCDataInput packet) {
        ResourceLocation pipeBlockName = new ResourceLocation(packet.readString());
        //noinspection unchecked
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) Block.REGISTRY.getObject(pipeBlockName);
        this.pipeType = packet.readEnum(pipeBlock.getPipeTypeClass());
        this.insulationColor = packet.readInt();
        this.activeConnections = packet.readInt();
        this.blockedConnections = packet.readInt();
        this.reinitializeShape();
    }

    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        ArrayList<IndexedCuboid6> result = new ArrayList<>();
        result.add(new IndexedCuboid6(null, centerBox));
        for(Cuboid6 sidedBox : sidedConnections) {
            result.add(new IndexedCuboid6(null, sidedBox));
        }
        return result;
    }

    @Override
    public Iterable<Cuboid6> getCollisionBoxes() {
        ArrayList<Cuboid6> result = new ArrayList<>();
        result.add(centerBox);
        result.addAll(sidedConnections);
        return result;
    }

    @Override
    public Iterable<Cuboid6> getOcclusionBoxes() {
        return Lists.newArrayList(centerBox);
    }

    public Iterable<Cuboid6> getPartialOcclusionBoxes() {
        return sidedConnections;
    }

    public boolean allowCompleteOcclusion() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void renderBreaking(Vector3 pos, TextureAtlasSprite texture, final CCRenderState ccrs) {
        ccrs.setPipeline(pos.translation(), new IconTransformation(texture));
        BlockRenderer.renderCuboid(ccrs, centerBox, 0);
        for(Cuboid6 sidedConnection : sidedConnections) {
            BlockRenderer.renderCuboid(ccrs, sidedConnection, 0);
        }
    }

}
