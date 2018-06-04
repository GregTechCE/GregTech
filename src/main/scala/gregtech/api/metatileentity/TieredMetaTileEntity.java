package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.render.SimpleSidedCubeRenderer;
import gregtech.api.render.SimpleSidedCubeRenderer.RenderSide;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public abstract class TieredMetaTileEntity extends MetaTileEntity {

    private final int tier;
    protected IEnergyContainer energyContainer;

    public TieredMetaTileEntity(String metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        reinitializeEnergyContainer();
    }

    protected void reinitializeEnergyContainer() {
        long tierVoltage = GTValues.V[tier];
        if (isEnergyEmitter()) {
            this.energyContainer = EnergyContainerHandler.emitterContainer(this,
                tierVoltage * 32L, tierVoltage, getMaxInputOutputAmperage());
        } else this.energyContainer = EnergyContainerHandler.receiverContainer(this,
            tierVoltage * 32L, tierVoltage, getMaxInputOutputAmperage());
    }

    @SideOnly(Side.CLIENT)
    private SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.VOLTAGE_CASINGS[tier];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return getBaseRenderer().getSpriteOnSide(RenderSide.TOP);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
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

    /**
     * Returns tier less tooltip key
     * It is generated from getMetaName by removing last part (like: ".lv") and adding ".tooltip" part
     * @return tier less tooltip key
     */
    @Nonnull
    public final String getTierlessTooltipKey() {
        String metaName = getMetaName();
        int lastIndexOfDot = metaName.lastIndexOf('.');
        String subName = lastIndexOfDot == -1 ? metaName : metaName.substring(0, lastIndexOfDot);
        return subName + ".tooltip";
    }
}
