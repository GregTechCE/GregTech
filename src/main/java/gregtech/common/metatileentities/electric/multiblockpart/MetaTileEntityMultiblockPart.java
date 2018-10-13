package gregtech.common.metatileentities.electric.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.render.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class MetaTileEntityMultiblockPart extends MetaTileEntity implements IMultiblockPart {

    private final int tier;
    private BlockPos controllerPos;
    private MultiblockControllerBase controllerTile;
    protected boolean shouldRenderOverlay = true;

    public MetaTileEntityMultiblockPart(String metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        initializeInventory();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        MultiblockControllerBase controller = getController();
        if(controller != null) {
            return controller.getBaseTexture().getParticleSprite();
        } else {
            return Textures.VOLTAGE_CASINGS[tier].getParticleSprite();
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        MultiblockControllerBase controller = getController();
        if(controller != null) {
            controller.getBaseTexture().render(renderState, translation, pipeline);
        } else {
            Textures.VOLTAGE_CASINGS[tier].render(renderState, translation, pipeline);
        }
    }

    public int getTier() {
        return tier;
    }

    public MultiblockControllerBase getController() {
        if(getWorld() != null && getWorld().isRemote) { //check this only clientside
            if(controllerTile == null && controllerPos != null) {
                this.controllerTile = (MultiblockControllerBase) BlockMachine.getMetaTileEntity(getWorld(), controllerPos);
            }
        }
        if(controllerTile != null && (controllerTile.getHolder() == null ||
            controllerTile.getHolder().isInvalid() || !(getWorld().isRemote || controllerTile.getMultiblockParts().contains(this)))) {
            //tile can become invalid for many reasons, and can also forgot to remove us once we aren't in structure anymore
            //so check it here to prevent bugs with dangling controller reference and wrong texture
            this.controllerTile = null;
        }
        return controllerTile;
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        MultiblockControllerBase controller = getController();
        buf.writeBoolean(controller != null);
        if(controller != null) {
            buf.writeBlockPos(controller.getPos());
            buf.writeBoolean(shouldRenderOverlay);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        if(buf.readBoolean()) {
            this.controllerPos = buf.readBlockPos();
            this.controllerTile = null;
            this.shouldRenderOverlay = buf.readBoolean();
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            if(buf.readBoolean()) {
                this.controllerPos = buf.readBlockPos();
                this.controllerTile = null;
                this.shouldRenderOverlay = buf.readBoolean();
            } else {
                this.controllerPos = null;
                this.controllerTile = null;
                this.shouldRenderOverlay = true;
            }
        }
    }

    private void setController(MultiblockControllerBase controller1, boolean shouldHideOverlay) {
        this.controllerTile = controller1;
        this.shouldRenderOverlay = controller1 == null || !shouldHideOverlay;
        if(!getWorld().isRemote) {
            writeCustomData(-100, writer -> {
                writer.writeBoolean(controllerTile != null);
                if(controllerTile != null) {
                    writer.writeBlockPos(controllerTile.getPos());
                    writer.writeBoolean(shouldRenderOverlay);
                }
            });
        }
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        MultiblockControllerBase controller = getController();
        if(!getWorld().isRemote && controller != null) {
            controller.invalidateStructure();
        }
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase, Object attachmentData) {
        setController(controllerBase, attachmentData instanceof Boolean && (Boolean) attachmentData);
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        setController(null, false);
    }

    @Override
    public boolean isAttachedToMultiBlock() {
        return getController() != null;
    }
}
