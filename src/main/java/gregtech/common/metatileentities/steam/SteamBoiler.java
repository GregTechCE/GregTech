package gregtech.common.metatileentities.steam;

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
import gregtech.api.render.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;

public class SteamBoiler extends MetaTileEntity {

    public static final class Bronze extends SteamBoiler { public Bronze() { super(false); } }
    public static final class Steel extends SteamBoiler { public Steel() { super(true); } }

    public static final int DEFAULT_TEMPERATURE = 20;

    public final TextureArea BRONZE_BACKGROUND_TEXTURE;
    public final TextureArea BRONZE_SLOT_BACKGROUND_TEXTURE;

    public final TextureArea SLOT_FURNACE_BACKGROUND;

    private final boolean isHighPressure;

    private FluidTank waterFluidTank;
    private FluidTank steamFluidTank;

    private int fuelBurnTimeLeft;
    private int fuelMaxBurnTime;
    private int currentTemperature;
    private boolean hasNoWater;
    private int timeBeforeCoolingDown;

    public SteamBoiler(boolean isHighPressure) {
        this.isHighPressure = isHighPressure;
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
        }
    }

    public void setFuelMaxBurnTime(int fuelMaxBurnTime) {
        this.fuelMaxBurnTime = fuelMaxBurnTime;
        this.fuelBurnTimeLeft = fuelMaxBurnTime;
        if(!world.isRemote) {
            writeCustomData(1, buffer -> buffer.writeInt(fuelMaxBurnTime));
            markDirty();
        }
    }

    @Override
    public void update() {
        super.update();
        if(currentTemperature < DEFAULT_TEMPERATURE)
            currentTemperature = DEFAULT_TEMPERATURE;
        if(fuelMaxBurnTime > 0) {
            if(getTimer() % 12 == 0) {
                if(fuelBurnTimeLeft % 2 == 0 && currentTemperature < getMaxTemperate())
                    fuelBurnTimeLeft -= isHighPressure ? 1 : 2;
                if(fuelBurnTimeLeft == 0) {
                    this.fuelMaxBurnTime = 0;
                    this.fuelBurnTimeLeft = 0;
                    this.timeBeforeCoolingDown = 40;
                }
            }
        } else if(timeBeforeCoolingDown == 0) {
            if(currentTemperature > DEFAULT_TEMPERATURE)
                currentTemperature--;
        } else --timeBeforeCoolingDown;

        if(!world.isRemote) {
            if(getTimer() % 5 == 0) {
                fillInternalTankFromFluidContainer(0, 0);
                pushFluidsIntoNearbyHandlers();
            }
            if(currentTemperature >= 100 && getTimer() % (isHighPressure ? 10 : 25) == 0) {
                float additionalTempBonus = (currentTemperature - 100) / (getMaxTemperate() - 100.0f);
                int fillAmount = 150 + (int) (150 * additionalTempBonus);
                int filledSteam = steamFluidTank.fill(ModHandler.getSteam(fillAmount), true);
                boolean canDrainWater = waterFluidTank.drain(1, true) != null;
                if(this.hasNoWater && canDrainWater) {
                    world.createExplosion(null,
                        getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,
                        1.0f + 1.5f * additionalTempBonus, true);
                } else this.hasNoWater = !canDrainWater;
                if(filledSteam == 0) {
                    //todo sound of steam pressure
                    steamFluidTank.drain(4000, true);
                }
            }
            if(fuelMaxBurnTime == 0 && fuelBurnTimeLeft == 0) {
                ItemStack fuelInSlot = importItems.extractItem(0, 1, true);
                if(fuelInSlot.isEmpty()) return;
                int burnTime = ForgeEventFactory.getItemBurnTime(fuelInSlot);
                if(burnTime == 0) return;
                ItemStack remainderAsh = ModHandler.getBurningFuelRemainder(world.rand, fuelInSlot);
                if(!remainderAsh.isEmpty()) { //we don't care if we can't insert ash - it's chanced anyway
                    exportItems.insertItem(0, remainderAsh, false);
                }
                setFuelMaxBurnTime(burnTime);
            }
        }
    }

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
    public IItemHandlerModifiable createImportItemHandler() {
        return new ItemStackHandler(1) {
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(slot == 0 && ForgeEventFactory.getItemBurnTime(stack) == 0)
                    return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
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

    @Override
    public ModularUI<IUIHolder> createUI(EntityPlayer player) {
        return ModularUI.builder(BRONZE_SLOT_BACKGROUND_TEXTURE, 176, 166)
            .widget(0, new LabelWidget<>(6, 6, getMetaName()))

            .widget(1, new SlotWidget<>(this.importItems, 0, 115, 54)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, SLOT_FURNACE_BACKGROUND))
            .widget(3, new SlotWidget<>(this.exportItems, 0, 115, 18, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE))

            .widget(2, new ProgressWidget<>(this::getFuelLeftPercent, 114, 35, 18, 18)
                .setProgressBar(getGuiTexture("boiler_%s_fuel"),
                    getGuiTexture("boiler_%s_fuel_full"),
                    MoveType.VERTICAL))
            .widget(4, new ProgressWidget<>(this::getTemperaturePercent, 96, 18, 10, 54)
                .setProgressBar(getGuiTexture("bar_%s_empty"),
                    getGuiTexture("bar_heat"),
                    MoveType.VERTICAL))

            .widget(5, new TankWidget<>(waterFluidTank, 83, 18, 10, 54)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty"), 1))
            .widget(6, new TankWidget<>(steamFluidTank, 70, 18, 10, 54)
                .setBackgroundTexture(getGuiTexture("bar_%s_empty"), 1))

            .widget(7, new SlotWidget<>(this.importItems, 1, 43, 18)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_in")))
            .widget(8, new SlotWidget<>(this.exportItems, 1, 43, 54, true, false)
                .setBackgroundTexture(BRONZE_SLOT_BACKGROUND_TEXTURE, getGuiTexture("overlay_%s_out")))
            .widget(9, new ImageWidget<>(42, 35, 18, 18)
                .setImage(getGuiTexture("overlay_%s_fluid_container")))

            .widget(10, new LabelWidget<>(8, 166 - 96 + 2, player.inventory.getName())) // 166 - gui imageHeight, 96 + 2 - from vanilla code
            .bindPlayerInventory(player.inventory, 11, BRONZE_BACKGROUND_TEXTURE)
            .build(this, player);
    }
}
