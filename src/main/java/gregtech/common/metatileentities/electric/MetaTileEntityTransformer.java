package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.render.Textures;
import gregtech.api.unification.stack.SimpleItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

public class MetaTileEntityTransformer extends TieredMetaTileEntity {

    private boolean isTransformUp;

    public MetaTileEntityTransformer(String metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityTransformer(metaTileEntityId, getTier());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("Inverted", isTransformUp);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.isTransformUp = data.getBoolean("Inverted");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isTransformUp);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isTransformUp = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            this.isTransformUp = buf.readBoolean();
        }
    }

    public boolean isInverted() {
        return isTransformUp;
    }

    public void setTransformUp(boolean inverted) {
        isTransformUp = inverted;
        if(!getWorld().isRemote) {
            reinitializeEnergyContainer();
            writeCustomData(-100, b -> b.writeBoolean(isTransformUp));
            markDirty();
        }
    }

    @Override
    protected void reinitializeEnergyContainer() {
        long tierVoltage = GTValues.V[getTier()];
        if(isTransformUp) {
            //storage = 1 amp high; input = tier / 4; amperage = 4; output = tier; amperage = 1
            this.energyContainer = new EnergyContainerHandler(this, tierVoltage * 4L, tierVoltage / 4, 4, tierVoltage, 1);
        } else {
            //storage = 1 amp high; input = tier; amperage = 1; output = tier / 4; amperage = 4
            this.energyContainer = new EnergyContainerHandler(this, tierVoltage * 4L, tierVoltage, 1, tierVoltage / 4, 4);
        }
        ((EnergyContainerHandler) this.energyContainer).setSideInputCondition(s -> s == getFrontFacing());
        ((EnergyContainerHandler) this.energyContainer).setSideOutputCondition(s -> s == getFrontFacing().getOpposite());
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        if(isTransformUp) {
            Textures.ENERGY_OUT_MULTI.renderSided(getFrontFacing(), renderState, pipeline);
            Textures.ENERGY_IN.renderSided(getFrontFacing().getOpposite(), renderState, pipeline);
        } else {
            Textures.ENERGY_IN_MULTI.renderSided(getFrontFacing(), renderState, pipeline);
            Textures.ENERGY_OUT.renderSided(getFrontFacing().getOpposite(), renderState, pipeline);
        }
    }

    @Override
    public boolean isValidFrontFacing(EnumFacing facing) {
        return true;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = playerIn.getHeldItem(hand);
        if(!itemStack.isEmpty() && GregTechAPI.softHammerList.contains(new SimpleItemStack(itemStack))) {
            if(getWorld().isRemote)
                return true;
            if(isTransformUp) {
                playerIn.sendMessage(new TextComponentTranslation("machine.transformer.transform_down"));
                setTransformUp(false);
                return true;
            } else {
                playerIn.sendMessage(new TextComponentTranslation("machine.transformer.transform_up"));
                setTransformUp(true);
                return true;
            }
        }
        return false;
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
