package gregtech.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.Material.MatFlags;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class MetaTileEntityTank extends MetaTileEntity {

    private final int tankSize;
    private final SolidMaterial material;
    private SyncFluidTank fluidTank;

    public MetaTileEntityTank(String metaTileEntityId, SolidMaterial material, int tankSize) {
        super(metaTileEntityId);
        this.tankSize = tankSize;
        this.material = material;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityTank(metaTileEntityId, material, tankSize);
    }

    @Override
    public String getHarvestTool() {
        return material.toString().contains("wood") ? "axe" : "pickaxe";
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.fluidTank = new SyncFluidTank(tankSize);
        this.fluidInventory = fluidTank;
    }

    @Override
    public void initFromItemStackData(NBTTagCompound itemStack) {
        super.initFromItemStackData(itemStack);
        if(itemStack.hasKey("Fluid", NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(itemStack.getCompoundTag("Fluid"));
            fluidTank.setFluid(fluidStack);
        }
    }

    @Override
    public void writeItemStackData(NBTTagCompound itemStack) {
        super.writeItemStackData(itemStack);
        FluidStack fluidStack = fluidTank.getFluid();
        if(fluidStack != null && fluidStack.amount > 0) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            itemStack.setTag("Fluid", tagCompound);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        FluidStack fluidStack = fluidTank.getFluid();
        buf.writeBoolean(fluidStack != null);
        if(fluidStack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            fluidStack.writeToNBT(tagCompound);
            buf.writeCompoundTag(tagCompound);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        FluidStack fluidStack = null;
        if(buf.readBoolean()) {
            try {
                NBTTagCompound tagCompound = buf.readCompoundTag();
                fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound);
            } catch (IOException ignored) {}
        }
        fluidTank.setFluid(fluidStack);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -200) {
            FluidStack fluidStack = null;
            if(buf.readBoolean()) {
                try {
                    NBTTagCompound tagCompound = buf.readCompoundTag();
                    fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound);
                } catch (IOException ignored) {}
            }
            fluidTank.setFluid(fluidStack);
        }
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
       return getWorld().isRemote || FluidUtil.interactWithFluidHandler(playerIn, hand, fluidTank);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return material.toString().contains("wood") ?
            Textures.WOODEN_TANK.getParticleTexture() :
            Textures.METAL_TANK.getParticleTexture();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        FluidStack fluidStack = getFluidForRendering();
        if(material.toString().contains("wood")) {
            Textures.WOODEN_TANK.render(renderState, translation, GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()), pipeline, tankSize, fluidStack);
        } else {
            int baseColor = ColourRGBA.multiply(
                GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB),
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
            Textures.METAL_TANK.render(renderState, translation, baseColor, pipeline, tankSize, fluidStack);
        }
    }

    @SideOnly(Side.CLIENT)
    private FluidStack getFluidForRendering() {
        if(getWorld() == null && renderContextStack != null) {
            NBTTagCompound tagCompound = renderContextStack.getTagCompound();
            if(tagCompound != null && tagCompound.hasKey("Fluid", NBT.TAG_COMPOUND)) {
                return FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid"));
            }
            return null;
        }
        return fluidTank.getFluid();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.fluid_storage_capacity", tankSize));

        NBTTagCompound tagCompound = stack.getTagCompound();
        if(tagCompound != null && tagCompound.hasKey("Fluid", NBT.TAG_COMPOUND)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag("Fluid"));
            if(fluidStack == null)
                return;
            tooltip.add(I18n.format("gregtech.machine.fluid_tank.fluid",
                fluidStack.amount, I18n.format(fluidStack.getUnlocalizedName())));
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("FluidInventory", ((FluidTank) fluidInventory).writeToNBT(new NBTTagCompound()));
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        ((FluidTank) this.fluidInventory).readFromNBT(data.getCompoundTag("FluidInventory"));
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //handled manually
    }

    private class SyncFluidTank extends FluidTank {

        public SyncFluidTank(int capacity) {
            super(capacity);
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return !material.toString().contains("wood") &&
                !material.hasFlag(MatFlags.FLAMMABLE) ||
                fluid.getFluid().getTemperature() <= 325;
        }

        @Override
        protected void onContentsChanged() {
            FluidStack newFluid = getFluid();
            writeCustomData(-200, buf -> {
                buf.writeBoolean(newFluid != null);
                if(newFluid != null) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    newFluid.writeToNBT(tagCompound);
                    buf.writeCompoundTag(tagCompound);
                }
            });
        }

    }
}
