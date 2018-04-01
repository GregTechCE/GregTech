package gregtech.api.metatileentity;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.capability.impl.EnergyRecipeMapWorkableHandler;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IUIHolder;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Set;

public class SimpleMachineMetaTileEntity extends TieredMetaTileEntity {

    protected final EnergyRecipeMapWorkableHandler workable;
    protected final OrientedOverlayRenderer renderer;

    private ItemStackHandler chargerInventory;
    private EnumFacing outputFacing;
    private boolean autoOutputItems;
    private boolean autoOutputFluids;

    public SimpleMachineMetaTileEntity(String metaTileEntityId, RecipeMap<?> recipeMap, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, tier);
        this.renderer = renderer;
        this.workable = addTrait(new EnergyRecipeMapWorkableHandler(energyContainer, recipeMap));
        this.chargerInventory = new ItemStackHandler(1);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, pipeline);
        renderer.render(renderState, pipeline, getFrontFacing(), workable.isActive());
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ChargerInventory", chargerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.chargerInventory.deserializeNBT(data.getCompoundTag("ChargerInventory"));
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(workable.recipeMap.getMaxInputs());
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(workable.recipeMap.getMaxOutputs());
    }

    @Override
    protected FluidTankHandler createImportFluidHandler() {
        FilteredFluidHandler[] fluidImports = new FilteredFluidHandler[workable.recipeMap.getMaxFluidInputs()];
        for(int i = 0; i < fluidImports.length; i++) {
            FilteredFluidHandler filteredFluidHandler = new FilteredFluidHandler(getInputTankCapacity(i));
            filteredFluidHandler.setFillPredicate(fluid -> canInputFluid(fluid.getFluid()));
            fluidImports[i] = filteredFluidHandler;
        }
        return new FluidTankHandler(fluidImports);
    }

    @Override
    protected FluidTankHandler createExportFluidHandler() {
        FluidTank[] fluidExports = new FluidTank[workable.recipeMap.getMaxFluidOutputs()];
        for(int i = 0; i < fluidExports.length; i++) {
            fluidExports[i] = new FluidTank(getOutputTankCapacity(i));
        }
        return new FluidTankHandler(fluidExports);
    }

    protected boolean canInputFluid(Fluid inputFluid) {
        RecipeMap<?> recipeMap = workable.recipeMap;
        Set<Recipe> matchingRecipes = null;
        for(IFluidTank fluidTank : importFluids) {
            Fluid fluidInTank = fluidTank.getFluid() == null ? null : fluidTank.getFluid().getFluid();
            if(fluidInTank != null) {
                if (matchingRecipes == null) {
                    //if we didn't have a list of recipes with any fluids, obtain it from first tank with fluid
                    matchingRecipes = new HashSet<>(recipeMap.getRecipesForFluid(fluidInTank));
                } else {
                    //else, remove recipes that don't contain fluid in this tank from list
                    matchingRecipes.removeIf(recipe -> !recipe.hasInputFluid(fluidInTank));
                }
            }
        }
        if(matchingRecipes == null) {
            //if all tanks are empty, generally fluid can be inserted if there are recipes for it
            return !recipeMap.getRecipesForFluid(inputFluid).isEmpty();
        } else {
            //otherwise, we can insert fluid only if one of recipes accept it as input
            return matchingRecipes.stream().anyMatch(recipe -> recipe.hasInputFluid(inputFluid));
        }
    }

    protected int getInputTankCapacity(int index) {
        return 16000;
    }

    protected int getOutputTankCapacity(int index) {
        return 16000;
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeByte(outputFacing.getIndex());
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

    protected ModularUI.Builder<IUIHolder> createGuiTemplate(EntityPlayer player) {
        return workable.recipeMap.createUITemplate(workable::getProgressPercent, importItems, exportItems, importFluids, exportFluids)
            .widget(0, new LabelWidget<>(6, 6, getMetaName()))
            .widget(1, new ButtonWidget(7, 62, 18, 18,
                GuiTextures.BUTTON_ITEM_OUTPUT, this::isAutoOutputItems, this::setAutoOutputItems))
            .widget(2, new ButtonWidget(25, 62, 18, 18,
                GuiTextures.BUTTON_FLUID_OUTPUT, this::isAutoOutputFluids, this::setAutoOutputFluids))
            .widget(3, new DischargerSlotWidget<>(chargerInventory, 0, 79, 62)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.CHARGER_OVERLAY))
            .widget(4, new ImageWidget<>(79, 42, 18, 18)
                .setPredicate(workable::isHasNotEnoughEnergy))
            .bindPlayerInventory(player.inventory, 5);
    }

    @Override
    protected ModularUI<IUIHolder> createUI(EntityPlayer entityPlayer) {
        return createGuiTemplate(entityPlayer).build(getHolder(), entityPlayer);
    }
}
