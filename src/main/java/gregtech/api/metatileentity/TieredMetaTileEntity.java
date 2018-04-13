package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.render.SimpleSidedRenderer;
import gregtech.api.render.SimpleSidedRenderer.RenderSide;
import gregtech.api.render.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

public abstract class TieredMetaTileEntity extends MetaTileEntity {

    private final int tier;
    protected EnergyContainerHandler energyContainer;

    public TieredMetaTileEntity(String metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        reinitializeEnergyContainer();
    }

    protected void reinitializeEnergyContainer() {
        long tierVoltage = GTValues.V[tier];
        if (isEnergyEmitter()) {
            this.energyContainer = addTrait(EnergyContainerHandler.emitterContainer(
                tierVoltage * 16L, tierVoltage, getMaxInputOutputAmperage()));
        } else this.energyContainer = addTrait(EnergyContainerHandler.receiverContainer(
            tierVoltage * 16L, tierVoltage, getMaxInputOutputAmperage()));
    }

    @SideOnly(Side.CLIENT)
    private SimpleSidedRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[tier];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return getBaseRenderer().getSpriteOnSide(RenderSide.TOP);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(getPaintingColorForRendering()));
        getBaseRenderer().render(renderState, colouredPipeline);
    }

    /**
     * Tier of machine determines it's input voltage, storage and generation rate
     * @return tier of this machine
     */
    public int getTier() {
        return tier;
    }

    /**
     * Determines max input or output amperage used by this meta tile entity
     * if emitter, it determines size of energy packets it will emit at once
     * if receiver, it determines max input energy per request
     * @return max amperage received or emitted by this machine
     */
    protected long getMaxInputOutputAmperage() {
        return 1L;
    }

    /**
     * Determines if this meta tile entity is in energy receiver or emitter mode
     * @return true if machine emits energy to network, false it it accepts energy from network
     */
    protected boolean isEnergyEmitter() {
        return false;
    }

}
