package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.events.EssentiaHandler;

public class MetaTileEntityMagicEnergyConverter extends TieredMetaTileEntity {

    private int essentiaLeft = 0;
    private boolean isActive = false;

    public MetaTileEntityMagicEnergyConverter(String metaTileEntityId) {
        super(metaTileEntityId, GTValues.MV);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityMagicEnergyConverter(metaTileEntityId);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return (isActive ? Textures.MAGIC_ENERGY_CONVERTER_ACTIVE : Textures.MAGIC_ENERGY_CONVERTER).getParticleSprite();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA(getPaintingColorForRendering())));
        ICubeRenderer cubeRenderer = isActive ? Textures.MAGIC_ENERGY_CONVERTER_ACTIVE : Textures.MAGIC_ENERGY_CONVERTER;
        cubeRenderer.render(renderState, translation, colouredPipeline);
    }

    @Override
    public void update() {
        super.update();
        if (getWorld().isRemote)
            return;
        int energyToAdd = (int) GTValues.V[GTValues.MV];
        if (energyContainer.getEnergyCanBeInserted() >= energyToAdd) {
            if (essentiaLeft == 0) {
                boolean drainedEssentia = EssentiaHandler.drainEssentia(getHolder(), Aspect.ENERGY, null, 16, 10);
                if (drainedEssentia) {
                    this.essentiaLeft += 20;
                } else {
                    setActive(false);
                }
            } else if (essentiaLeft > 0) {
                --this.essentiaLeft;
                energyContainer.addEnergy(energyToAdd);
                setActive(true);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("EssentiaStored", essentiaLeft);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.essentiaLeft = data.getInteger("EssentiaStored");
    }

    private void setActive(boolean isActive) {
        if(this.isActive != isActive) {
            this.isActive = isActive;
            if(!getWorld().isRemote) {
                writeCustomData(-100, w -> w.writeBoolean(isActive));
            }
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            this.isActive = buf.readBoolean();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isActive);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isActive = buf.readBoolean();
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

}
