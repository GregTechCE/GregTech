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
    private MultiblockControllerBase controller;
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
            if(controller == null && controllerPos != null) {
                this.controller = (MultiblockControllerBase) BlockMachine.getMetaTileEntity(getWorld(), controllerPos);
            }
        }
        return controller;
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
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
            this.controller = null;
            this.shouldRenderOverlay = buf.readBoolean();
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            if(buf.readBoolean()) {
                this.controllerPos = buf.readBlockPos();
                this.controller = null;
                this.shouldRenderOverlay = buf.readBoolean();
            } else {
                this.controllerPos = null;
                this.controller = null;
                this.shouldRenderOverlay = true;
            }
        }
    }

    private void setController(MultiblockControllerBase controller1, boolean shouldHideOverlay) {
        this.controller = controller1;
        this.shouldRenderOverlay = controller1 == null || !shouldHideOverlay;
        if(!getWorld().isRemote) {
            writeCustomData(-100, writer -> {
                writer.writeBoolean(controller != null);
                if(controller != null) {
                    writer.writeBlockPos(controller.getPos());
                    writer.writeBoolean(shouldRenderOverlay);
                }
            });
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
}
