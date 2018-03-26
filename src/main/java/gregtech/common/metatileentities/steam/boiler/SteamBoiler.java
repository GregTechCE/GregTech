package gregtech.common.metatileentities.steam.boiler;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.*;
import gregtech.api.gui.widgets.ProgressWidget.MoveType;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public abstract class SteamBoiler extends MetaTileEntity {

    private static final EnumFacing[] STEAM_PUSH_DIRECTIONS = ArrayUtils.add(EnumFacing.HORIZONTALS, EnumFacing.UP);
    //public static final int DEFAULT_TEMPERATURE = 20;

    public final TextureArea BRONZE_BACKGROUND_TEXTURE;
    public final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE;

    public final TextureArea SLOT_FURNACE_BACKGROUND;

    protected final boolean isHighPressure;
    protected final int baseSteamOutput;
    private final OrientedOverlayRenderer renderer;

    protected FluidTank waterFluidTank;
    protected FluidTank steamFluidTank;

    private int fuelBurnTimeLeft;
    private int fuelMaxBurnTime;
    private int currentTemperature;
    private boolean hasNoWater;
    private int timeBeforeCoolingDown;

    public SteamBoiler(String metaTileEntityId, boolean isHighPressure, OrientedOverlayRenderer renderer, int baseSteamOutput) {
        super(metaTileEntityId);
        this.renderer = renderer;
        this.isHighPressure = isHighPressure;
        this.baseSteamOutput = baseSteamOutput;
        BRONZE_BACKGROUND_TEXTURE = getGuiTexture("%s_gui");
        BRONZE_SLOT_BACKGROUND_TEXTURE = getGuiTexture("slot_%s");
        SLOT_FURNACE_BACKGROUND = getGuiTexture("slot_%s_furnace_background");
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline, new ColourMultiplier(paintingColor));
        if(isHighPressure) {
            Textures.STEAM_CASING_STEEL.render(renderState, colouredPipeline);
        } else Textures.STEAM_CASING_BRONZE.render(renderState, colouredPipeline);
        renderer.render(renderState, pipeline, getFrontFacing(), fuelBurnTimeLeft > 0);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("FuelBurnTimeLeft", fuelBurnTimeLeft);
        data.setInteger("FuelMaxBurnTime", fuelMaxBurnTime);
        data.setInteger("CurrentTemperature", currentTemperature);
        data.setBoolean("HasNoWater", hasNoWater);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.fuelBurnTimeLeft = data.getInteger("FuelBurnTimeLeft");
        this.fuelMaxBurnTime = data.getInteger("FuelMaxBurnTime");
        this.currentTemperature = data.getInteger("CurrentTemperature");
        this.hasNoWater = data.getBoolean("HasNoWater");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(fuelBurnTimeLeft);
        buf.writeInt(fuelMaxBurnTime);
        buf.writeInt(currentTemperature);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.fuelBurnTimeLeft = buf.readInt();
        this.fuelMaxBurnTime = buf.readInt();
        this.currentTemperature = buf.readInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == 100) {
            this.fuelMaxBurnTime = buf.readInt();
            this.fuelBurnTimeLeft = fuelMaxBurnTime;
            getHolder().scheduleChunkForRenderUpdate();
        }
    }

    public void setFuelMaxBurnTime(int fuelMaxBurnTime) {
        this.fuelMaxBurnTime = fuelMaxBurnTime;
        this.fuelBurnTimeLeft = fuelMaxBurnTime;
        if(!getWorld().isRemote) {
            writeCustomData(100, buffer -> buffer.writeInt(fuelMaxBurnTime));
            markDirty();
        }
    }

    @Override
    public void update() {
        super.update();
        //if(currentTemperature < DEFAULT_TEMPERATURE)
        //    currentTemperature = DEFAULT_TEMPERATURE;
        if(fuelMaxBurnTime > 0) {
            if(getTimer() % 12 == 0) {
                if(fuelBurnTimeLeft % 2 == 0 && currentTemperature < getMaxTemperate())
                    currentTemperature++;
                fuelBurnTimeLeft -= isHighPressure ? 2 : 1;
                if(fuelBurnTimeLeft == 0) {
                    this.fuelMaxBurnTime = 0;
                    this.fuelBurnTimeLeft = 0;
                    this.timeBeforeCoolingDown = 40;
                    getHolder().scheduleChunkForRenderUpdate();
                }
            }
        } else if(timeBeforeCoolingDown == 0) {
            if(currentTemperature > 0 /*DEFAULT_TEMPERATURE*/)
                currentTemperature--;
        } else --timeBeforeCoolingDown;

        if(!getWorld().isRemote) {
            if(getTimer() % 5 == 0) {
                fillInternalTankFromFluidContainer(0, 0);
                pushFluidsIntoNearbyHandlers(STEAM_PUSH_DIRECTIONS);
            }
            if(currentTemperature >= 100 && getTimer() % (isHighPressure ? 10 : 25) == 0) {
                float additionalTempBonus = (currentTemperature - 100) / (getMaxTemperate() - 100.0f);
                int fillAmount = baseSteamOutput + (int) (baseSteamOutput * additionalTempBonus);
                int filledSteam = steamFluidTank.fill(ModHandler.getSteam(fillAmount), true);
                boolean canDrainWater = waterFluidTank.drain(1, true) != null;
                if(this.hasNoWater && canDrainWater) {
                    getWorld().createExplosion(null,
                        getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,
                        1.0f + 1.5f * additionalTempBonus, true);
                } else this.hasNoWater = !canDrainWater;
                if(filledSteam == 0) {
                    //todo sound of steam pressure
                    steamFluidTank.drain(4000, true);
                }
            }
            if(fuelMaxBurnTime <= 0) {
                tryConsumeNewFuel();
            }
        }
    }

    protected abstract void tryConsumeNewFuel();

    public int getMaxTemperate() {
        return isHighPressure ? 1000 : 500;
    }

    public double getTemperaturePercent() {
        return currentTemperature / (getMaxTemperate() * 1.0);
    }

    public double getFuelLeftPercent() {
        return fuelMaxBurnTime == 0 ? 0.0 : fuelBurnTimeLeft / (fuelMaxBurnTime * 1.0);
    }

    @Override
    protected FluidTankHandler createImportFluidHandler() {
        this.waterFluidTank = new FilteredFluidHandler(16000)
            .setFillPredicate(ModHandler::isWater);
        return new FluidTankHandler(waterFluidTank);
    }

    @Override
    protected FluidTankHandler createExportFluidHandler() {
        this.steamFluidTank = new FluidTank(16000);
        return new FluidTankHandler(steamFluidTank);
    }

    protected TextureArea getGuiTexture(String pathTemplate) {
        String type = isHighPressure ? "steel" : "bronze";
        return TextureArea.fullImage(String.format("gregtech:textures/gui/steam/%s/%s.png",
            type, pathTemplate.replace("%s", type)));
    }

    public ModularUI.Builder<IUIHolder> createUITemplate(EntityPlayer player) {
        return ModularUI.builder(BRONZE_BACKGROUND_TEXTURE, 176, 166)
            .widget(0, new LabelWidget<>(6, 6, getMetaName()))

            .widget(1, new ProgressWidget<>(this::getTemperaturePercent, 95, 17, 11, 55)
                .setProgressBar(getGuiTexture("bar_%s_empty"),
                    getGuiTexture("bar_heat"),
                    MoveType.VERTICAL))

            .widget(2, new TankWidget<>(waterFluidTank, 83, 18, 10, 54)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty"), 1))
            .widget(3, new TankWidget<>(steamFluidTank, 70, 18, 10, 54)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty"), 1))

            .widget(4, new SlotWidget<>(this.importItems, 0, 43, 18)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_in")))
            .widget(5, new SlotWidget<>(this.exportItems, 0, 43, 54, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_out")))
            .widget(6, new ImageWidget<>(42, 35, 18, 18)
                .setImage(getGuiTexture("overlay_%s_fluid_container")))

            .widget(7, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getName())) // 166 - gui imageHeight, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 8, BRONZE_SLOT_BACKGROUND_TEXTURE);
    }

}
