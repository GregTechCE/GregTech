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
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.WorldPipeNet;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.api.pipenet.tile.PipeCoverableImplementation;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ParticleHandlerUtil;
import gregtech.common.tools.DamageValues;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class PipeMultiPart<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, IPipeTile<PipeType, NodeDataType>, ICapabilityProvider {

    private BlockPipe<PipeType, NodeDataType, ?> pipeBlock;
    private PipeType pipeType;
    private Material material;
    protected int insulationColor = DEFAULT_INSULATION_COLOR;
    private int blockedConnections;
    private int coverBlockedConnections;
    private NodeDataType cachedNodeData;
    private final PipeCoverableImplementation coverableImplementation = new PipeCoverableImplementation(this);

    protected int activeConnections;
    protected Cuboid6 centerBox;
    protected List<Cuboid6> sidedConnections = new ArrayList<>();

    //set to true when part is being replaced with modified version of self
    //used to skip pipe net adding and removing code while replacing part
    protected boolean isBeingReplaced = false;

    protected PipeMultiPart() {
    }

    @SuppressWarnings("CopyConstructorMissesField")
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
        this.blockedConnections = sourceTile.getBlockedConnections();
        sourceTile.getCoverableImplementation().transferDataTo(getCoverableImplementation());
        //transfer other part-related data only from multi parts
        if (sourceTile instanceof PipeMultiPart) {
            PipeMultiPart<PipeType, NodeDataType> sourcePart = (PipeMultiPart<PipeType, NodeDataType>) sourceTile;
            this.activeConnections = sourcePart.activeConnections;
            this.centerBox = sourcePart.centerBox;
            this.sidedConnections = sourcePart.sidedConnections;
        }
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
    public void setPipeType(PipeType pipeType) {
        this.pipeType = pipeType;
    }

    @Override
    public void setPipeMaterial(Material pipeMaterial) {
        this.material = pipeMaterial;
    }

    public void setConnectionBlocked(EnumFacing side, boolean blocked) {
        if (blocked) {
            this.coverBlockedConnections |= 1 << side.getIndex();
        } else {
            this.coverBlockedConnections &= ~(1 << side.getIndex());
        }
        updateBlockedConnections();
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
        if (capability == GregtechCapabilities.CAPABILITY_COVERABLE) {
            return GregtechCapabilities.CAPABILITY_COVERABLE.cast(getCoverableImplementation());
        }
        return null;
    }

    @Nullable
    @Override
    public final <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        boolean isCoverable = capability == GregtechCapabilities.CAPABILITY_COVERABLE;
        CoverBehavior coverBehavior = facing == null ? null : getCoverableImplementation().getCoverAtSide(facing);
        T defaultValue = getCapabilityInternal(capability, facing);
        if (!isCoverable && facing != null) {
            if (coverBehavior != null) {
                return coverBehavior.getCapability(capability, defaultValue);
            } else if ((blockedConnections & (1 << facing.getIndex())) > 0) {
                return null;
            }
        }
        return defaultValue;
    }

    @Override
    public final boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return getCapability(capability, facing) != null;
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
        if (hit.cuboid6.data == null) {
            return getDropStack();
        } else if (hit.cuboid6.data instanceof EnumFacing) {
            EnumFacing coverSide = (EnumFacing) hit.cuboid6.data;
            CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(coverSide);
            return coverBehavior == null ? ItemStack.EMPTY : coverBehavior.getCoverDefinition().getDropItemStack();
        }
        return ItemStack.EMPTY;
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

    private void updateBlockedConnections() {
        TileMultipart tileMultipart = tile();
        if (tileMultipart == null || tileMultipart.isInvalid())
            return;
        float thickness = getPipeType().getThickness();
        int lastBlockedConnections = blockedConnections;

        this.blockedConnections = 0;
        for (EnumFacing cableSide : EnumFacing.VALUES) {
            if ((coverBlockedConnections & (1 << cableSide.getIndex())) > 0) {
                this.blockedConnections |= 1 << cableSide.getIndex();
                continue;
            }
            Cuboid6 sideBox = BlockPipe.getSideBox(cableSide, thickness);
            NormallyOccludedPart part = new NormallyOccludedPart(sideBox);
            if (!tileMultipart.canReplacePart(this, part)) {
                this.blockedConnections |= 1 << cableSide.getIndex();
            }
        }

        if (lastBlockedConnections != blockedConnections) {
            WorldPipeNet<NodeDataType, ?> worldPipeNet = pipeBlock.getWorldPipeNet(world());
            for (EnumFacing side : EnumFacing.VALUES) {
                boolean isBlockedCurrently = (blockedConnections & 1 << side.getIndex()) > 0;
                boolean wasBlocked = (lastBlockedConnections & 1 << side.getIndex()) > 0;
                if (isBlockedCurrently == wasBlocked) continue;
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
        if (!this.isBeingReplaced) {
            this.updateBlockedConnections();
            this.updateActualConnections();
            this.isBeingReplaced = false;
            if (!world().isRemote) {
                getPipeBlock().getWorldPipeNet(world()).addNode(pos(), getNodeData(), getMark(), getBlockedConnections(), pipeBlock.getActiveNodeConnections(world(), pos()) > 0);
            }
        }
        reinitializeShape();
    }

    @Override
    public boolean activate(EntityPlayer player, CuboidRayTraceResult hit, ItemStack item, EnumHand hand) {
        EnumFacing coverSide = ICoverable.traceCoverSide(hit);
        if (coverSide == null) {
            return false;
        }
        CoverBehavior coverBehavior = getCoverableImplementation().getCoverAtSide(coverSide);
        if (coverBehavior == null) {
            return false;
        }
        SimpleItemStack simpleItemStack = item.isEmpty() ? null : new SimpleItemStack(item);
        if (simpleItemStack != null && GregTechAPI.screwdriverList.contains(simpleItemStack)) {
            if (GTUtility.doDamageItem(item, DamageValues.DAMAGE_FOR_SCREWDRIVER, true) &&
                coverBehavior.onScrewdriverClick(player, hand, hit) == EnumActionResult.SUCCESS) {
                GTUtility.doDamageItem(item, DamageValues.DAMAGE_FOR_SCREWDRIVER, false);
                return true;
            } else {
                return false;
            }
        }
        return coverBehavior.onRightClick(player, hand, hit) == EnumActionResult.SUCCESS;
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
        }
    }

    @Override
    public void onNeighborChanged() {
        scheduleTick(1);
    }

    @Override
    public void scheduledTick() {
        updateBlockedConnections();
        updateActualConnections();
        if (!world().isRemote) {
            getWriteStream().writeByte(1);
            updateActiveNodeStatus();
        }
    }

    public void updateActiveNodeStatus() {
        int activeConnections = pipeBlock.getActiveNodeConnections(world(), pos());
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
        if (blockedConnections > 0) {
            tag.setInteger("BlockedConnections", blockedConnections);
        }
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
        this.blockedConnections = tag.getInteger("BlockedConnections");
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
            tile().markRender();
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
    public abstract TextureAtlasSprite getParticleTexture();

    @Override
    public void addHitEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        ParticleHandlerUtil.addHitEffects(world(), hit, getParticleTexture(), manager);
    }

    @Override
    public void addDestroyEffects(CuboidRayTraceResult hit, ParticleManager manager) {
        ParticleHandlerUtil.addBlockDestroyEffects(world(), hit, getParticleTexture(), manager);
    }
}
