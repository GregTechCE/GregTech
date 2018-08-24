package gregtech.common.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import codechicken.multipart.*;
import com.google.common.collect.Lists;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.cable.BlockCable;
import gregtech.common.cable.ICableTile;
import gregtech.common.cable.Insulation;
import gregtech.common.cable.WireProperties;
import gregtech.common.cable.tile.CableEnergyContainer;
import gregtech.common.cable.tile.TileEntityCable;
import gregtech.common.render.CableRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class CableMultiPart extends TMultiPart implements TNormalOcclusionPart, TPartialOcclusionPart, ICableTile, ICapabilityProvider {

    private BlockCable cableBlock;
    private Insulation insulation;
    private int insulationColor = TileEntityCable.DEFAULT_INSULATION_COLOR;
    private int blockedConnections;
    private int activeConnections;
    private Cuboid6 centerBox;
    private List<Cuboid6> sidedConnections = new ArrayList<>();
    private CableEnergyContainer energyContainer;


    CableMultiPart() {}

    public CableMultiPart(IBlockState blockState, TileEntity tile) {
        this.cableBlock = (BlockCable) blockState.getBlock();
        this.insulation = blockState.getValue(BlockCable.INSULATION);
        if(tile instanceof TileEntityCable) {
            this.insulationColor = ((TileEntityCable) tile).getInsulationColor();
        }
        this.reinitializeShape();
    }

    public CableEnergyContainer getEnergyContainer() {
        if (energyContainer == null) {
            this.energyContainer = new CableEnergyContainer(this);
        }
        return energyContainer;
    }

    @Override
    public World getCableWorld() {
        return this.tile().getWorld();
    }

    @Override
    public BlockPos getCablePos() {
        return this.tile().getPos();
    }

    @Override
    public int getBlockedConnections() {
        return blockedConnections;
    }

    public void setInsulationColor(int color) {
        this.insulationColor = color;
        this.sendDescUpdate();
        BlockCable.updateCableConnections(this, this.tile().getWorld(), this.tile().getPos());
    }

    @Override
    public int getInsulationColor() {
        return insulationColor;
    }

    @Override
    public Insulation getInsulation() {
        return insulation;
    }

    @Override
    public WireProperties getWireProperties() {
        return cableBlock.getProperties(insulation);
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
        return cableBlock.getItem(insulation);
    }

    @Override
    public boolean hasCapability(Capability capability, EnumFacing facing) {
        return capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == IEnergyContainer.CAPABILITY_ENERGY_CONTAINER) {
            return (T) getEnergyContainer();
        }
        return null;
    }

    private void reinitializeShape() {
        this.centerBox = BlockCable.getSideBox(null, insulation.thickness);
        updateSidedConnections(false);
    }

    private void updateSidedConnections(boolean notify) {
        float thickness = insulation.thickness;
        this.sidedConnections.clear();
        for(EnumFacing side : EnumFacing.VALUES) {
            int sideIndex = side.getIndex();
            if((activeConnections & (1 << sideIndex)) > 0 &&
                (blockedConnections & (1 << sideIndex)) == 0) {
                this.sidedConnections.add(BlockCable.getSideBox(side, thickness));
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
        float thickness = insulation.thickness;
        int lastBlockedConnections = blockedConnections;

        this.blockedConnections = 0;
        for(EnumFacing cableSide : EnumFacing.VALUES) {
            Cuboid6 sideBox = BlockCable.getSideBox(cableSide, thickness);
            NormallyOccludedPart part = new NormallyOccludedPart(sideBox);
            if (!tileMultipart.canReplacePart(this, part)) {
                this.blockedConnections |= 1 << cableSide.getIndex();
            }
        }

        if (lastBlockedConnections != blockedConnections) {
            BlockCable.updateCableConnections(this, tileMultipart.getWorld(), tileMultipart.getPos());
            updateActualConnections();
        }
    }

    private void updateActualConnections() {
        int lastActualConnections = activeConnections;

        TileMultipart tileMultipart = tile();
        if (tileMultipart != null && !tileMultipart.isInvalid()) {
            this.activeConnections = BlockCable.getActualConnections(this, tileMultipart.getWorld(), tileMultipart.getPos());
        }

        if (lastActualConnections != activeConnections) {
            updateSidedConnections(true);
        }
    }

    @Override
    public ResourceLocation getType() {
        return GTMultipartFactory.CABLE_PART_KEY;
    }

    @Override
    public void onAdded() {
        this.updateBlockedConnections();
        this.updateActualConnections();
        BlockCable.attachNoNearbyNetwork(getCableWorld(), getCablePos(), this);
    }

    @Override
    public void onRemoved() {
        BlockCable.detachFromNetwork(getCableWorld(), getCablePos());
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
        tag.setString("CableMaterial", cableBlock.material.toString());
        tag.setInteger("Insulation", insulation.ordinal());
        tag.setInteger("InsulationColor", insulationColor);
        tag.setInteger("ActiveConnections", activeConnections);
        tag.setInteger("BlockedConnections", blockedConnections);
    }

    @Override
    public void load(NBTTagCompound tag) {
        String materialName = tag.getString("CableMaterial");
        this.cableBlock = MetaBlocks.CABLES.get(Material.MATERIAL_REGISTRY.getObject(materialName));
        this.insulation = Insulation.values()[tag.getInteger("Insulation")];
        this.insulationColor = tag.getInteger("InsulationColor");
        this.activeConnections = tag.getInteger("ActiveConnections");
        this.blockedConnections = tag.getInteger("BlockedConnections");
        reinitializeShape();
    }

    @Override
    public void writeDesc(MCDataOutput packet) {
        packet.writeString(cableBlock.material.toString());
        packet.writeEnum(insulation);
        packet.writeInt(insulationColor);
        packet.writeInt(activeConnections);
        packet.writeInt(blockedConnections);
    }

    @Override
    public void readDesc(MCDataInput packet) {
        String materialName = packet.readString();
        this.cableBlock = MetaBlocks.CABLES.get(Material.MATERIAL_REGISTRY.getObject(materialName));
        this.insulation = packet.readEnum(Insulation.class);
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
    public boolean renderStatic(Vector3 pos, BlockRenderLayer layer, CCRenderState ccrs) {
        TileMultipart tileMultipart = tile();
        ccrs.setBrightness(tileMultipart.getWorld(), tileMultipart.getPos());
        CableRenderer.INSTANCE.renderCableBlock(cableBlock.material, insulation, insulationColor, ccrs,
            new IVertexOperation[] {new Translation(tileMultipart.getPos())},
            activeConnections & ~blockedConnections);
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
