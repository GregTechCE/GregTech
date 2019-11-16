package gregtech.integration.multipart;

import codechicken.multipart.TMultiPart;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.pipenet.block.material.TileEntityMaterialPipeBase;
import gregtech.api.pipenet.tile.AttachmentType;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public abstract class PipeMultiPart<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends TMultiPart implements ITickable {

    protected BlockPipe<PipeType, NodeDataType, ?> pipeBlock;
    private int pipeType;
    private Material material;
    protected int insulationColor = 0;
    private TIntIntMap blockedConnectionsMap = new TIntIntHashMap();

    protected PipeMultiPart() {
    }

    protected TileEntityMaterialPipeBase<PipeType, NodeDataType> createTileEntity() {
        return (TileEntityMaterialPipeBase<PipeType, NodeDataType>) pipeBlock.createNewTileEntity(false);
    }

    @Override
    public void update() {
        if (world().isRemote) return;
        world().setBlockState(pos(), pipeBlock.getDefaultState());
        TileEntityMaterialPipeBase<PipeType, NodeDataType> tileEntity = createTileEntity();
        world().setTileEntity(pos(), tileEntity);
        PipeType pipeType = pipeBlock.getPipeTypeClass().getEnumConstants()[this.pipeType];
        tileEntity.setDetachedConversionMode(true);
        try {
            tileEntity.setPipeData(pipeBlock, pipeType, material);
            tileEntity.setInsulationColor(insulationColor);
            int totalBlockedConnections = blockedConnectionsMap.get(AttachmentType.PIPE.ordinal());
            totalBlockedConnections |= blockedConnectionsMap.get(AttachmentType.MULTIPART.ordinal());
            //block connections that weren't blocked yet
            for (EnumFacing blockedSide : EnumFacing.VALUES) {
                if ((totalBlockedConnections & (1 << blockedSide.getIndex())) > 0) {
                    tileEntity.setConnectionBlocked(AttachmentType.PIPE, blockedSide, true);
                }
            }
        } finally {
            tileEntity.setDetachedConversionMode(false);
        }
    }

    @Override
    public void save(NBTTagCompound tag) {
        tag.setString("PipeBlock", Block.REGISTRY.getNameForObject(pipeBlock).toString());
        tag.setInteger("PipeType", pipeType);
        tag.setString("PipeMaterial", material.toString());
        tag.setInteger("InsulationColor", insulationColor);
        NBTTagCompound blockedConnectionsTag = new NBTTagCompound();
        for(int attachmentType : blockedConnectionsMap.keys()) {
            int blockedConnections = blockedConnectionsMap.get(attachmentType);
            blockedConnectionsTag.setInteger(Integer.toString(attachmentType), blockedConnections);
        }
        tag.setTag("BlockedConnectionsMap", blockedConnectionsTag);
    }

    @Override
    public void load(NBTTagCompound tag) {
        ResourceLocation pipeBlockName = new ResourceLocation(tag.getString("PipeBlock"));
        this.pipeBlock = (BlockPipe<PipeType, NodeDataType, ?>) Block.REGISTRY.getObject(pipeBlockName);
        this.pipeType = tag.getInteger("PipeType");
        this.material = Material.MATERIAL_REGISTRY.getObject(tag.getString("PipeMaterial"));
        this.insulationColor = tag.getInteger("InsulationColor");
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
    }
}
