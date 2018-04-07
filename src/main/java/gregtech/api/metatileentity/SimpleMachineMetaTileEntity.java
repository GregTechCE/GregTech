package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.capability.impl.EnergyRecipeMapWorkableHandler;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ButtonWidget;
import gregtech.api.gui.widgets.DischargerSlotWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Set;

public class SimpleMachineMetaTileEntity extends WorkableTieredMetaTileEntity {

    private ItemStackHandler chargerInventory;
    private EnumFacing outputFacing;
    private boolean autoOutputItems;
    private boolean autoOutputFluids;

    public SimpleMachineMetaTileEntity(String metaTileEntityId, RecipeMap<?> recipeMap, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, recipeMap, renderer, tier);
        this.chargerInventory = new ItemStackHandler(1);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        if(outputFacing != null) {
            Textures.PIPE_OUT_OVERLAY.renderSided(outputFacing, renderState, pipeline);
            if(autoOutputItems) {
                Textures.ITEM_OUTPUT_OVERLAY.renderSided(outputFacing, renderState, pipeline);
            }
            if(autoOutputFluids) {
                Textures.FLUID_OUTPUT_OVERLAY.renderSided(outputFacing, renderState, pipeline);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        energyContainer.dischargeEnergyContainers(chargerInventory, 0);
        if(getTimer() % 5 == 0) {
            EnumFacing outputFacing = getOutputFacing();
            if(autoOutputFluids) {
                pushFluidsIntoNearbyHandlers(outputFacing);
            }
            if(autoOutputItems) {
                pushItemsIntoNearbyHandlers(outputFacing);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ChargerInventory", chargerInventory.serializeNBT());
        data.setInteger("OutputFacing", getOutputFacing().getIndex());
        data.setBoolean("AutoOutputItems", autoOutputItems);
        data.setBoolean("AutoOutputFluids", autoOutputFluids);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.chargerInventory.deserializeNBT(data.getCompoundTag("ChargerInventory"));
        this.outputFacing = EnumFacing.VALUES[data.getInteger("OutputFacing")];
        this.autoOutputItems = data.getBoolean("AutoOutputItems");
        this.autoOutputFluids = data.getBoolean("AutoOutputFluids");
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, chargerInventory);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(getOutputFacing().getIndex());
        buf.writeBoolean(autoOutputItems);
        buf.writeBoolean(autoOutputFluids);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.outputFacing = EnumFacing.VALUES[buf.readByte()];
        this.autoOutputItems = buf.readBoolean();
        this.autoOutputFluids = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == - 100) {
            this.outputFacing = EnumFacing.VALUES[buf.readByte()];
            getHolder().scheduleChunkForRenderUpdate();
        } else if(dataId == -101) {
            this.autoOutputItems = buf.readBoolean();
            getHolder().scheduleChunkForRenderUpdate();
        } else if(dataId == -102) {
            this.autoOutputFluids = buf.readBoolean();
            getHolder().scheduleChunkForRenderUpdate();
        }
    }

    public void setOutputFacing(EnumFacing outputFacing) {
        this.outputFacing = outputFacing;
        if(!getWorld().isRemote) {
            writeCustomData(-100, buf -> buf.writeByte(outputFacing.getIndex()));
            markDirty();
        }
    }

    public void setAutoOutputItems(boolean autoOutputItems) {
        this.autoOutputItems = autoOutputItems;
        if(!getWorld().isRemote) {
            writeCustomData(-101, buf -> buf.writeBoolean(autoOutputItems));
            markDirty();
        }
    }

    public void setAutoOutputFluids(boolean autoOutputFluids) {
        this.autoOutputFluids = autoOutputFluids;
        if(!getWorld().isRemote) {
            writeCustomData(-102, buf -> buf.writeBoolean(autoOutputFluids));
            markDirty();
        }
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if(this.outputFacing == null) {
            //set initial output facing as opposite to front
            setOutputFacing(frontFacing.getOpposite());
        }
    }

    public EnumFacing getOutputFacing() {
        return outputFacing == null ? EnumFacing.SOUTH : outputFacing;
    }

    public boolean isAutoOutputItems() {
        return autoOutputItems;
    }

    public boolean isAutoOutputFluids() {
        return autoOutputFluids;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SimpleMachineMetaTileEntity(metaTileEntityId, workable.recipeMap, renderer, getTier());
    }

    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        ModularUI.Builder builder = workable.recipeMap.createUITemplate(workable::getProgressPercent, importItems, exportItems, importFluids, exportFluids)
            .widget(0, new LabelWidget(6, 6, getMetaName()))
            .widget(3, new DischargerSlotWidget(chargerInventory, 0, 79, 62)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.CHARGER_OVERLAY))
            .widget(4, new ImageWidget(79, 42, 18, 18, GuiTextures.INDICATOR_NO_ENERGY)
                .setPredicate(workable::isHasNotEnoughEnergy))
            .bindPlayerInventory(player.inventory, 5);

        int buttonStartX = 7;
        if(exportItems.getSlots() > 0) {
            builder.widget(1, new ButtonWidget(buttonStartX, 62, 18, 18,
                GuiTextures.BUTTON_ITEM_OUTPUT, this::isAutoOutputItems, this::setAutoOutputItems));
            buttonStartX += 18;
        }
        if(exportFluids.getTanks() > 0) {
            builder.widget(2, new ButtonWidget(buttonStartX, 62, 18, 18,
                GuiTextures.BUTTON_FLUID_OUTPUT, this::isAutoOutputFluids, this::setAutoOutputFluids));
        }
        return builder;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createGuiTemplate(entityPlayer).build(getHolder(), entityPlayer);
    }
}
