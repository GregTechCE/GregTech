package gregtech.common.metatileentities.electric.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.render.Textures;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class MetaTileEntityMultiblockPart extends MetaTileEntity implements IMultiblockPart {

    private final int tier;
    private MultiblockControllerBase controller;

    public MetaTileEntityMultiblockPart(String metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        initializeInventory();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        if(controller != null) {
            controller.getBaseTexture().render(renderState, pipeline);
        } else {
            Textures.VOLTAGE_CASINGS[tier].render(renderState, pipeline);
        }
    }

    public int getTier() {
        return tier;
    }

    public MultiblockControllerBase getController() {
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
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        if(buf.readBoolean()) {
            BlockPos controllerPos = buf.readBlockPos();
            this.controller = (MultiblockControllerBase) BlockMachine.getMetaTileEntity(getWorld(), controllerPos);
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            if(buf.readBoolean()) {
                BlockPos controllerPos = buf.readBlockPos();
                this.controller = (MultiblockControllerBase) BlockMachine.getMetaTileEntity(getWorld(), controllerPos);
            } else {
                this.controller = null;
            }
        }
    }

    private void setController(MultiblockControllerBase controller1) {
        this.controller = controller1;
        if(!getWorld().isRemote) {
            writeCustomData(-100, writer -> {
                writer.writeBoolean(controller != null);
                if(controller != null) {
                    writer.writeBlockPos(controller.getPos());
                }
            });
        }
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        setController(controllerBase);
    }

    @Override
    public void removeFromMultiblock(MultiblockControllerBase controllerBase) {
        setController(null);
    }
}
