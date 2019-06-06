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
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.api.cover.ICoverable.CoverSideData;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.AttachmentType;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.PipeCoverableImplementation;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.ParticleHandlerUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class PipeMultiPart<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, IRedstonePart, IPipeTile<PipeType, NodeDataType>, ICapabilityProvider {

    private BlockPipe<PipeType, NodeDataType, ?> pipeBlock;
    private PipeType pipeType;
    private Material material;
    protected int insulationColor = DEFAULT_INSULATION_COLOR;
    private NodeDataType cachedNodeData;
    private final PipeCoverableImplementation coverableImplementation = new PipeCoverableImplementation(this);

    private TIntIntMap blockedConnectionsMap = new TIntIntHashMap();
    private int blockedConnections = 0;

    protected int activeConnections;
    protected Cuboid6 centerBox;
    protected List<Cuboid6> sidedConnections = new ArrayList<>();

    //set to true when part is being replaced with modified version of self
    //used to skip pipe net adding and removing code while replacing part
    protected boolean isBeingReplaced = false;

    protected PipeMultiPart() {
    }

    public PipeMultiPart(IPipeTile<PipeType, NodeDataType> sourceTile) {
        //this constructor is used for data transfer
        //when some nodes becomes active and changes itself to become tickable
        //good example is fluid pipes
        transferDataFrom(sourceTile);
    }

    protected abstract PipeMultiPart<PipeType, NodeDataType> toTickablePart();

    @Override
    public void transferDataFrom(IPipeTile<PipeType, NodeDataType> sourceTile) {
        this.insulationColor = sourceTile.getInsulationColor();
        this.pipeBlock = sourceTile.getPipeBlock();
        this.pipeType = sourceTile.getPipeType();
        this.material = sourceTile.getPipeMaterial();
        this.blockedConnectionsMap = sourceTile.getBlockedConnectionsMap();
        sourceTile.getCoverableImplementation().transferDataTo(getCoverableImplementation());
        //transfer other part-related data only from multi parts
        if (sourceTile instanceof PipeMultiPart) {
            PipeMultiPart<PipeType, NodeDataType> sourcePart = (PipeMultiPart<PipeType, NodeDataType>) sourceTile;
            this.activeConnections = sourcePart.activeConnections;
            this.centerBox = sourcePart.centerBox;
            this.sidedConnections = sourcePart.sidedConnections;
        }
        recomputeBlockedConnections();
        reinitializeShape();
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
    public PipeCoverableImplementation getCoverableImplementation() {
        return coverableImplementation;
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
    public Material getPipeMaterial() {
        return material;
    }

    @Override
    public TIntIntMap getBlockedConnectionsMap() {
        return new TIntIntHashMap(blockedConnectionsMap);
    }

    @Override
    public boolean isConnectionBlocked(AttachmentType type, EnumFacing side) {
        int blockedConnections = blockedConnectionsMap.get(type.ordinal());
        return (blockedConnections & 1 << side.getIndex()) > 0;
    }

    @Override
    public void setConnectionBlocked(AttachmentType attachmentType, EnumFacing side, boolean blocked) {
        int blockedConnections = blockedConnectionsMap.get(attachmentType.ordinal());
        this.blockedConnectionsMap.put(attachmentType.ordinal(), withSideConnectionBlocked(blockedConnections, side, blocked));
        recomputeBlockedConnections();
        updateActualConnections();
        if (!getPipeWorld().isRemote) {
            updateSideBlockedConnection(side);
            MCDataOutput writeStream = getWriteStream();
            writeStream.writeByte(3);
            writeStream.writeVarInt(this.blockedConnections);
            markAsDirty();
        }
    }

    private void recomputeBlockedConnections() {
        int resultBlockedConnections = 0;
        for(int blockedConnections : blockedConnectionsMap.values()) {
            resultBlockedConnections |= blockedConnections;
        }
        this.blockedConnections = resultBlockedConnections;
    }

    private void updateSideBlockedConnection(EnumFacing side) {
        WorldPipeNet<?, ?> worldPipeNet = getPipeBlock().getWorldPipeNet(getPipeWorld());
        boolean isSideBlocked = false;
        int sideIndex = 1 << side.getIndex();
        for(int blockedConnections : blockedConnectionsMap.values()) {
            isSideBlocked |= (blockedConnections & sideIndex) > 0;
        }
        worldPipeNet.updateBlockedConnections(getPipePos(), side, isSideBlocked);
    }

    private int withSideConnectionBlocked(int blockedConnections, EnumFacing side, boolean blocked) {
        int index = 1 << side.getIndex();
        if(blocked) {
            return blockedConnections | index;
        } else {
            return blockedConnections & ~index;
        }
    }

    @Override
    public IPipeTile<PipeType, NodeDataType> setSupportsTicking() {
        if (supportsTicking()) {
            return this;
        }
        PipeMultiPart<PipeType, NodeDataType> newPart = toTickablePart();
        //mark parts for replacement
        this.isBeingReplaced = true;
        newPart.isBeingReplaced = true;
        //and then remove old part and add new one
        TileMultipart tileMultipart = tile();
        tileMultipart.remPart(this);
        TileMultipart.addPart(tileMultipart.getWorld(), tileMultipart.getPos(), newPart);
        return newPart;
    }

    @Override
    public boolean canPlaceCoverOnSide(EnumFacing side) {
        Cuboid6 plateBox = ICoverable.getCoverPlateBox(side, getCoverableImplementation().getCoverPlateThickness(), true);
        NormallyOccludedPart part = new NormallyOccludedPart(plateBox);
        return tile().canReplacePart(this, part);
    }

    @Override
    public void setInsulationColor(int color) {
        this.insulationColor = color;
        if (!world().isRemote) {
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
        if (cachedNodeData == null) {
            this.cachedNodeData = getPipeBlock().createProperties(this);
        }
        return cachedNodeData;
    }

    @Override
    public <T> T getCapabilityInternal(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_COVERABLE) {
            return GregtechTileCapabilities.CAPABILITY_COVERABLE.cast(getCoverableImplementation());
        }
        return null;
    }

    @Nullable
    @Override
    public final <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        boolean isCoverable = capability == GregtechTileCapabilities.CAPABILITY_COVERABLE;
        CoverBehavior coverBehavior = facing == null ? null : getCoverableImplementation().getCoverAtSide(facing);
        T defaultValue = getCapabilityInternal(capability, facing);
        if(isCoverable) {
            return defaultValue;
        }
        //if there is no cover, only provide capability if facing is not blocked
        if(coverBehavior == null && facing != null) {
            boolean isBlocked = (blockedConnections & 1 << facing.getIndex()) > 0;
            return isBlocked ? null : defaultValue;
        }
        if(coverBehavior != null) {
            return coverBehavior.getCapability(capability, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public final boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return getCapability(capability, facing) != null;
    }

    @Override
    public void onWorldJoin() {
        super.onWorldJoin();
        coverableImplementation.updateInputRedstoneSignals();
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
        if (hit.cuboid6.data instanceof CoverSideData) {
            EnumFacing coverSide = ((CoverSideData) hit.cuboid6.data).side;
            CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(coverSide);
            return coverBehavior == null ? ItemStack.EMPTY : coverBehavior.getCoverDefinition().getDropItemStack();
        }
        return getDropStack();
    }

    private ItemStack getDropStack() {
        return getPipeBlock().getDropItem(this);
    }

    private void reinitializeShape() {
        this.centerBox = BlockPipe.getSideBox(null, getPipeType().getThickness());
        updateSidedConnections(false);
    }

    private void updateSidedConnections(boolean notify) {
        float thickness = getPipeType().getThickness();
        this.sidedConnections.clear();
        for (EnumFacing side : EnumFacing.VALUES) {
            int sideIndex = side.getIndex();
            if ((activeConnections & (1 << sideIndex)) > 0 &&
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

    private boolean isMultipartConnectionBlocked(EnumFacing side) {
        int blockedConnections = blockedConnectionsMap.get(AttachmentType.MULTIPART.ordinal());
        return (blockedConnections & 1 << side.getIndex()) > 0;
    }

    private void updateMultipartBlockedConnections() {
        TileMultipart tileMultipart = tile();
        if (tileMultipart == null || tileMultipart.isInvalid())
            return;
        float thickness = getPipeType().getThickness();

        for (EnumFacing cableSide : EnumFacing.VALUES) {
            Cuboid6 sideBox = BlockPipe.getSideBox(cableSide, thickness);
            NormallyOccludedPart part = new NormallyOccludedPart(sideBox);
            boolean isConnectionBlocked = !tileMultipart.canReplacePart(this, part);
            boolean wasConnectionBlocked = isMultipartConnectionBlocked(cableSide);
            if(isConnectionBlocked != wasConnectionBlocked) {
                setConnectionBlocked(AttachmentType.MULTIPART, cableSide, isConnectionBlocked);
            }
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
        if (!this.isBeingReplaced) {
            this.updateMultipartBlockedConnections();
            this.updateActualConnections();
            this.isBeingReplaced = false;
            if (!world().isRemote) {
                getPipeBlock().getWorldPipeNet(world()).addNode(pos(), getNodeData(), getMark(), getBlockedConnections(), pipeBlock.getActiveNodeConnections(world(), pos(), this) > 0);
            }
        }
        reinitializeShape();
    }

    @Override
    public boolean activate(EntityPlayer player, CuboidRayTraceResult hit, ItemStack item, EnumHand hand) {
        return getPipeBlock().onPipeActivated(player, hand, hit, this);
    }

    @Override
    public void click(EntityPlayer player, CuboidRayTraceResult hit, ItemStack item) {
        EnumFacing coverSide = ICoverable.traceCoverSide(hit);
        if (coverSide == null) {
            return;
        }
        CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(coverSide);
        if (coverBehavior == null) {
            return;
        }
        coverBehavior.onLeftClick(player, hit);
    }

    @Override
    public int strongPowerLevel(int side) {
        return 0;
    }

    @Override
    public int weakPowerLevel(int side) {
        CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(EnumFacing.VALUES[side].getOpposite());
        return coverBehavior == null ? 0 : coverBehavior.getRedstoneSignalOutput();
    }

    @Override
    public boolean canConnectRedstone(int side) {
        CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(EnumFacing.VALUES[side]);
        return coverBehavior != null && coverBehavior.canConnectRedstone();
    }

    @Override
    public void onRemoved() {
        if (!this.isBeingReplaced && !world().isRemote) {
            pipeBlock.getWorldPipeNet(world()).removeNode(pos());
            getCoverableImplementation().dropAllCovers();
        }
    }

    @Override
    public void onPartChanged(TMultiPart part) {
        if (part != this) {
            scheduleTick(1);
            coverableImplementation.updateInputRedstoneSignals();
        }
    }

    @Override
    public void onNeighborChanged() {
        scheduleTick(1);
        coverableImplementation.updateInputRedstoneSignals();
    }

    @Override
    public void scheduledTick() {
        updateMultipartBlockedConnections();
        updateActualConnections();
        if (!world().isRemote) {
            getWriteStream().writeByte(1);
            updateActiveNodeStatus();
        }
    }

    public void updateActiveNodeStatus() {
        int activeConnections = pipeBlock.getActiveNodeConnections(world(), pos(), this);
        activeConnections &= ~getBlockedConnections();
        boolean isActiveNode = activeConnections > 0;
        PipeNet<NodeDataType> pipeNet = pipeBlock.getWorldPipeNet(world()).getNetFromPos(pos());
        if (pipeNet != null) {
            boolean changed = pipeNet.markNodeAsActive(pos(), isActiveNode);
            if (changed) {
                onModeChange(isActiveNode);
            }
        }
    }

    protected void onModeChange(boolean isActiveNow) {
    }

    @Override
    public void sendDescUpdate() {
        MCDataOutput writeStream = getWriteStream();
        writeStream.writeByte(0);
        this.writeDesc(writeStream);
    }

    @Override
    public void save(NBTTagCompound tag) {
        tag.setString("PipeBlock", Block.REGISTRY.getNameForObject(pipeBlock).toString());
        tag.setInteger("PipeType", pipeType.ordinal());
        tag.setString("PipeMaterial", material.toString());
        tag.setInteger("InsulationColor", insulationColor);
        tag.setInteger("ActiveConnections", activeConnections);
        NBTTagCompound blockedConnectionsTag = new NBTTagCompound();
        for(int attachmentType : blockedConnectionsMap.keys()) {
            int blockedConnections = blockedConnectionsMap.get(attachmentType);
            blockedConnectionsTag.setInteger(Integer.toString(attachmentType), blockedConnections);
        }
        tag.setTag("BlockedConnectionsMap", blockedConnectionsTag);
        getCoverableImplementation().readFromNBT(tag);
    }

    @Override
    public void load(NBTTagCompound tag) {
        ResourceLocation pipeBlockName = new ResourceLocation(tag.getString("PipeBlock"));
        //noinspection unchecked
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) Block.REGISTRY.getObject(pipeBlockName);
        this.pipeType = pipeBlock.getPipeTypeClass().getEnumConstants()[tag.getInteger("PipeType")];
        this.material = Material.MATERIAL_REGISTRY.getObject(tag.getString("PipeMaterial"));
        this.insulationColor = tag.getInteger("InsulationColor");
        this.activeConnections = tag.getInteger("ActiveConnections");
        this.blockedConnectionsMap.clear();
        NBTTagCompound blockedConnectionsTag = tag.getCompoundTag("BlockedConnectionsMap");
        for(String attachmentTypeKey : blockedConnectionsTag.getKeySet()) {
            int attachmentType = Integer.parseInt(attachmentTypeKey);
            int blockedConnections = blockedConnectionsTag.getInteger(attachmentTypeKey);
            this.blockedConnectionsMap.put(attachmentType, blockedConnections);
        }
        if(tag.hasKey("BlockedConnections")) {
            int blockedConnections = tag.getInteger("BlockedConnections");
            this.blockedConnectionsMap.put(AttachmentType.MULTIPART.ordinal(), blockedConnections);
        }
        recomputeBlockedConnections();
        getCoverableImplementation().writeToNBT(tag);
        reinitializeShape();
    }

    @Override
    public void writeDesc(MCDataOutput packet) {
        packet.writeVarInt(Block.REGISTRY.getIDForObject(pipeBlock));
        packet.writeEnum(pipeType);
        packet.writeVarInt(Material.MATERIAL_REGISTRY.getIDForObject(material));
        packet.writeInt(insulationColor);
        packet.writeVarInt(activeConnections);
        packet.writeVarInt(blockedConnections);
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        getCoverableImplementation().writeInitialSyncData(packetBuffer);
        byte[] dataArray = packetBuffer.array();
        packet.writeVarInt(dataArray.length);
        packet.writeArray(dataArray);
    }

    @Override
    public void readDesc(MCDataInput packet) {
        //noinspection unchecked
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) Block.REGISTRY.getObjectById(packet.readVarInt());
        this.pipeType = packet.readEnum(pipeBlock.getPipeTypeClass());
        this.material = Material.MATERIAL_REGISTRY.getObjectById(packet.readVarInt());
        this.insulationColor = packet.readInt();
        this.activeConnections = packet.readVarInt();
        this.blockedConnections = packet.readVarInt();
        byte[] dataArray = packet.readArray(packet.readVarInt());
        getCoverableImplementation().readInitialSyncData(new PacketBuffer(Unpooled.wrappedBuffer(dataArray)));
        this.reinitializeShape();
    }

    @Override
    public void read(MCDataInput packet) {
        byte packetType = packet.readByte();
        if (packetType == 0) {
            readDesc(packet);
        } else if (packetType == 1) {
            updateActualConnections();
        } else if (packetType == 2) {
            int dataId = packet.readVarInt();
            byte[] payload = packet.readArray(packet.readVarInt());
            PacketBuffer buffer = new PacketBuffer(Unpooled.wrappedBuffer(payload));
            getCoverableImplementation().readCustomData(dataId, buffer);
        } else if (packetType == 3) {
            this.blockedConnections = packet.readVarInt();
        }
        tile().markRender();
    }

    @Override
    public void writeCoverCustomData(int id, Consumer<PacketBuffer> writer) {
        MCDataOutput writeStream = getWriteStream();
        writeStream.writeByte(2);
        writeStream.writeVarInt(id);
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        writer.accept(packetBuffer);
        byte[] dataArray = packetBuffer.array();
        writeStream.writeVarInt(dataArray.length);
        writeStream.writeArray(dataArray);
    }

    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        ArrayList<IndexedCuboid6> result = new ArrayList<>();
        result.add(new IndexedCuboid6(null, centerBox));
        for (Cuboid6 sidedBox : sidedConnections) {
            result.add(new IndexedCuboid6(null, sidedBox));
        }
        getCoverableImplementation().addCoverCollisionBoundingBox(result, false);
        return result;
    }

    @Override
    public Iterable<Cuboid6> getCollisionBoxes() {
        ArrayList<Cuboid6> result = new ArrayList<>();
        result.add(centerBox);
        result.addAll(sidedConnections);
        getCoverableImplementation().addCoverCollisionBoundingBox(result, false);
        return result;
    }

    @Override
    public Iterable<Cuboid6> getOcclusionBoxes() {
        ArrayList<Cuboid6> result = new ArrayList<>();
        result.add(centerBox);
        getCoverableImplementation().addCoverCollisionBoundingBox(result, true);
        return result;
    }

    public Iterable<Cuboid6> getPartialOcclusionBoxes() {
        ArrayList<Cuboid6> result = new ArrayList<>(sidedConnections);
        getCoverableImplementation().addCoverCollisionBoundingBox(result, false);
        return result;
    }

    public boolean allowCompleteOcclusion() {
        return true;
    }

    @Override
    public void notifyBlockUpdate() {
        tile().notifyTileChange();
        updateActiveNodeStatus();
        updateActualConnections();
        if (!world().isRemote) {
            getWriteStream().writeByte(1);
            updateActiveNodeStatus();
        }
    }

    @Override
    public void scheduleChunkForRenderUpdate() {
        tile().markRender();
    }

    @Override
    public boolean isValidTile() {
        return tile() != null && !tile().isInvalid();
    }

    @Override
    public void markAsDirty() {
        tile().markDirty();
    }

    @SideOnly(Side.CLIENT)
    public void renderBreaking(Vector3 pos, TextureAtlasSprite texture, final CCRenderState ccrs) {
        ccrs.setPipeline(pos.translation(), new IconTransformation(texture));
        BlockRenderer.renderCuboid(ccrs, centerBox, 0);
        for (Cuboid6 sidedConnection : sidedConnections) {
            BlockRenderer.renderCuboid(ccrs, sidedConnection, 0);
        }
    }

    @SideOnly(Side.CLIENT)
    public abstract Pair<TextureAtlasSprite, Integer> getParticleTexture();

    @Override
    public void addHitEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        Pair<TextureAtlasSprite, Integer> atlasSprite = getParticleTexture();
        ParticleHandlerUtil.addHitEffects(world(), hit, atlasSprite.getLeft(), atlasSprite.getRight(), manager);
    }

    @Override
    public void addDestroyEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        Pair<TextureAtlasSprite, Integer> atlasSprite = getParticleTexture();
        ParticleHandlerUtil.addBlockDestroyEffects(world(), hit, atlasSprite.getLeft(), atlasSprite.getRight(), manager);
    }
}
