package gregtech.common.metatileentities.electric.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityLargeTurbine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class MetaTileEntityRotorHolder extends MetaTileEntityMultiblockPart {

    private static final int NORMAL_MAXIMUM_SPEED = 6000;

    private final int maxRotorSpeed;
    private int currentRotorSpeed;

    private boolean isRotorLooping;
    private boolean hasRotor;

    public MetaTileEntityRotorHolder(String metaTileEntityId, int tier, int maxSpeed) {
        super(metaTileEntityId, tier);
        this.maxRotorSpeed = maxSpeed;
        this.currentRotorSpeed = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityRotorHolder(metaTileEntityId, getTier(), maxRotorSpeed);
    }

    @Override
    public void update() {
        super.update();
        MetaTileEntityLargeTurbine controller = (MetaTileEntityLargeTurbine) getController();
        if(controller != null && !getWorld().isRemote) {
            if(currentRotorSpeed < maxRotorSpeed && controller.isActive()) {
                incrementSpeed(1);
            } else if(currentRotorSpeed > 0 && !controller.isActive()) {
                incrementSpeed(-1);
            }
        }
    }

    /**
     * @return true if current rotor is still looping, i.e it's current speed > 0
     * this can return true even if multiblock is not formed
     */
    public boolean isRotorLooping() {
        return isRotorLooping;
    }

    public void incrementSpeed(int incrementSpeed) {
       boolean lastIsLooping = currentRotorSpeed > 0;
       this.currentRotorSpeed = MathHelper.clamp(currentRotorSpeed + incrementSpeed, 0, maxRotorSpeed);
       this.isRotorLooping = currentRotorSpeed > 0;
       if(isRotorLooping != lastIsLooping && !getWorld().isRemote) {
           writeCustomData(-200, writer -> writer.writeBoolean(isRotorLooping));
           markDirty();
       }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        if(getController() != null) {
            Textures.LARGE_TURBINE_ROTOR_RENDERER.renderSided(renderState, pipeline, getFrontFacing(), false, false);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return ModularUI.defaultBuilder()
            .bindPlayerInventory(entityPlayer.inventory)
            .build(getHolder(), entityPlayer);
    }
}
