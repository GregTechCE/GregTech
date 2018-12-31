package gregtech.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.FuelRecipeMapWorkableHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipes.FuelRecipe;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.SimpleCubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockFireboxCasing;
import gregtech.common.blocks.BlockFireboxCasing.FireboxCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetaTileEntityLargeBoiler extends MultiblockWithDisplayBase {

    private static final int CONSUMPTION_MULTIPLIER = 100;

    public enum BoilerType {
        BRONZE(600, 1.0f, 500,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.BRONZE_BRICKS),
            MetaBlocks.BOILER_FIREBOX_CASING.getState(FireboxCasingType.BRONZE_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.BRONZE_PIPE),
            Textures.BRONZE_PLATED_BRICKS,
            Textures.BRONZE_FIREBOX, Textures.BRONZE_FIREBOX_ACTIVE),

        STEEL(900, 1.2f, 1000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID),
            MetaBlocks.BOILER_FIREBOX_CASING.getState(FireboxCasingType.STEEL_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE),
            Textures.SOLID_STEEL_CASING,
            Textures.STEEL_FIREBOX, Textures.STEEL_FIREBOX_ACTIVE),

        TITANIUM(1400, 1.6f, 2000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.TITANIUM_STABLE),
            MetaBlocks.BOILER_FIREBOX_CASING.getState(FireboxCasingType.TITANIUM_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.TITANIUM_PIPE),
            Textures.STABLE_TITANIUM_CASING,
            Textures.TITANIUM_FIREBOX, Textures.TITANIUM_FIREBOX_ACTIVE),

        TUNGSTENSTEEL(2400, 2.4f, 4000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.TUNGSTENSTEEL_ROBUST),
            MetaBlocks.BOILER_FIREBOX_CASING.getState(FireboxCasingType.TUNGSTENSTEEL_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.TUNGSTENSTEEL_PIPE),
            Textures.ROBUST_TUNGSTENSTEEL_CASING,
            Textures.TUNGSTENSTEEL_FIREBOX, Textures.TUNGSTENSTEEL_FIREBOX_ACTIVE);

        public final int baseSteamOutput;
        public final float fuelConsumptionMultiplier;
        public final int maxTemperature;
        public final IBlockState casingState;
        public final IBlockState fireboxState;
        public final IBlockState pipeState;
        public final ICubeRenderer solidCasingRenderer;
        public final SimpleCubeRenderer fireboxIdleRenderer;
        public final SimpleCubeRenderer firefoxActiveRenderer;

        BoilerType(int baseSteamOutput, float fuelConsumptionMultiplier, int maxTemperature, IBlockState casingState, IBlockState fireboxState, IBlockState pipeState,
                   ICubeRenderer solidCasingRenderer, SimpleCubeRenderer fireboxIdleRenderer, SimpleCubeRenderer firefoxActiveRenderer) {
            this.baseSteamOutput = baseSteamOutput;
            this.fuelConsumptionMultiplier = fuelConsumptionMultiplier;
            this.maxTemperature = maxTemperature;
            this.casingState = casingState;
            this.fireboxState = fireboxState;
            this.pipeState = pipeState;
            this.solidCasingRenderer = solidCasingRenderer;
            this.fireboxIdleRenderer = fireboxIdleRenderer;
            this.firefoxActiveRenderer = firefoxActiveRenderer;
        }
    }

    public final BoilerType boilerType;

    private int currentTemperature;
    private int fuelBurnTicksLeft;
    private boolean isActive;
    private boolean wasActiveAndNeedsUpdate;
    private boolean hasNoWater;

    private FluidTankList fluidImportInventory;
    private ItemHandlerList itemImportInventory;
    private FluidTankList steamOutputTank;

    public MetaTileEntityLargeBoiler(String metaTileEntityId, BoilerType boilerType) {
        super(metaTileEntityId);
        this.boilerType = boilerType;
        reinitializeStructurePattern();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityLargeBoiler(metaTileEntityId, boilerType);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.fluidImportInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.itemImportInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.steamOutputTank = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.fluidImportInventory = new FluidTankList(true);
        this.itemImportInventory = new ItemHandlerList(Collections.emptyList());
        this.steamOutputTank = new FluidTankList(true);
        this.currentTemperature = 0; //reset temperature
        this.fuelBurnTicksLeft = 0;
        this.hasNoWater = false;
        this.isActive = false;
        replaceFireboxAsActive(false);
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
        if(!getWorld().isRemote && isStructureFormed()) {
            replaceFireboxAsActive(false);
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.large_boiler.temperature",
                currentTemperature, boilerType.maxTemperature));
            int steamOutput = 0;
            if(currentTemperature >= 100) {
                double outputMultiplier = currentTemperature / (boilerType.maxTemperature * 1.0);
                steamOutput = (int) (boilerType.baseSteamOutput * outputMultiplier);
                if(fluidImportInventory.drain(ModHandler.getWater(1), false) == null &&
                    fluidImportInventory.drain(ModHandler.getDistilledWater(1), false) == null) {
                    steamOutput = 0;
                }
            }
            textList.add(new TextComponentTranslation("gregtech.multiblock.large_boiler.steam_output",
                steamOutput, boilerType.baseSteamOutput));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void updateFormedValid() {
        if(fuelBurnTicksLeft > 0) {
            --this.fuelBurnTicksLeft;
            if(this.currentTemperature < boilerType.maxTemperature && getTimer() % 20 == 0) {
                this.currentTemperature++;
            }
            if(fuelBurnTicksLeft == 0) {
                this.wasActiveAndNeedsUpdate = true;
            }
        } else if(currentTemperature > 0 && getTimer() % 20 == 0) {
            --this.currentTemperature;
        }

        if(currentTemperature >= 100) {
            boolean doWaterDrain = getTimer() % 20 == 0;
            FluidStack drainedWater = fluidImportInventory.drain(ModHandler.getWater(1), doWaterDrain);
            if(drainedWater == null || drainedWater.amount == 0) {
                drainedWater = fluidImportInventory.drain(ModHandler.getDistilledWater(1), doWaterDrain);
            }
            if(drainedWater != null && drainedWater.amount > 0) {
                if(currentTemperature > 100 && hasNoWater) {
                    float explosionPower = currentTemperature / 100 * 2.0f;
                    getWorld().setBlockToAir(getPos());
                    getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5,
                        explosionPower, true);
                }
                this.hasNoWater = false;
                if(currentTemperature >= 100) {
                    double outputMultiplier = currentTemperature / (boilerType.maxTemperature * 1.0);
                    FluidStack steamStack = ModHandler.getSteam((int) (boilerType.baseSteamOutput * outputMultiplier));
                    steamOutputTank.fill(steamStack, true);
                }
            } else {
                this.hasNoWater = true;
            }
        }

        if(fuelBurnTicksLeft == 0) {
            int fuelMaxBurnTime = setupRecipeAndConsumeInputs();
            if(fuelMaxBurnTime > 0) {
                this.fuelBurnTicksLeft = fuelMaxBurnTime;
                if(wasActiveAndNeedsUpdate) {
                    this.wasActiveAndNeedsUpdate = false;
                } else setActive(true);
                markDirty();
            }
        }

        if (wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
            setActive(false);
        }
    }

    private int setupRecipeAndConsumeInputs() {
        for(IFluidTank fluidTank : fluidImportInventory.getFluidTanks()) {
            FluidStack fuelStack = fluidTank.drain(Integer.MAX_VALUE, false);
            if(fuelStack == null || fuelStack.getFluid() == FluidRegistry.WATER)
                continue; //ignore empty tanks and water
            FuelRecipe dieselRecipe = RecipeMaps.DIESEL_GENERATOR_FUELS.findRecipe(GTValues.V[9], fuelStack);
            if(dieselRecipe != null) {
                int fuelAmountToConsume = (int) (dieselRecipe.getRecipeFluid().amount * CONSUMPTION_MULTIPLIER * boilerType.fuelConsumptionMultiplier);
                if(fuelStack.amount >= fuelAmountToConsume) {
                    fluidTank.drain(fuelAmountToConsume, true);
                    long recipeVoltage = FuelRecipeMapWorkableHandler.getTieredVoltage(dieselRecipe.getMinVoltage());
                    int voltageMultiplier = (int) Math.max(1L, recipeVoltage / GTValues.V[GTValues.LV]);
                    return (int) Math.floor(dieselRecipe.getDuration() * CONSUMPTION_MULTIPLIER / 4 * voltageMultiplier);
                } else continue;
            }
            FuelRecipe denseFuelRecipe = RecipeMaps.SEMI_FLUID_GENERATOR_FUELS.findRecipe(GTValues.V[9], fuelStack);
            if(denseFuelRecipe != null) {
                int fuelAmountToConsume = (int) (denseFuelRecipe.getRecipeFluid().amount * CONSUMPTION_MULTIPLIER * boilerType.fuelConsumptionMultiplier);
                if(fuelStack.amount >= fuelAmountToConsume) {
                    fluidTank.drain(fuelAmountToConsume, true);
                    long recipeVoltage = FuelRecipeMapWorkableHandler.getTieredVoltage(denseFuelRecipe.getMinVoltage());
                    int voltageMultiplier = (int) Math.max(1L, recipeVoltage / GTValues.V[GTValues.LV]);
                    return (int) Math.floor(denseFuelRecipe.getDuration() * CONSUMPTION_MULTIPLIER * 2 * voltageMultiplier);
                }
            }
        }
        for(int slotIndex = 0; slotIndex < itemImportInventory.getSlots(); slotIndex++) {
            ItemStack itemStack = itemImportInventory.getStackInSlot(slotIndex);
            int fuelBurnValue = (int) (TileEntityFurnace.getItemBurnTime(itemStack) / (60 * boilerType.fuelConsumptionMultiplier));
            if(fuelBurnValue > 0) {
                itemStack.shrink(1);
                itemImportInventory.setStackInSlot(slotIndex, itemStack);
                return fuelBurnValue;
            }
        }
        return 0;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("CurrentTemperature", currentTemperature);
        data.setInteger("FuelBurnTicksLeft", fuelBurnTicksLeft);
        data.setBoolean("HasNoWater", hasNoWater);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.currentTemperature = data.getInteger("CurrentTemperature");
        this.fuelBurnTicksLeft = data.getInteger("FuelBurnTicksLeft");
        this.hasNoWater = data.getBoolean("HasNoWater");
        this.isActive = fuelBurnTicksLeft > 0;
    }

    private void setActive(boolean active) {
        this.isActive = active;
        if(!getWorld().isRemote) {
            if(isStructureFormed()) {
                replaceFireboxAsActive(active);
            }
            writeCustomData(100, buf -> buf.writeBoolean(isActive));
            markDirty();
        }
    }

    private void replaceFireboxAsActive(boolean isActive) {
        BlockPos centerPos = getPos().offset(getFrontFacing().getOpposite()).down();
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                BlockPos blockPos = centerPos.add(x, 0, z);
                IBlockState blockState = getWorld().getBlockState(blockPos);
                if(blockState.getBlock() instanceof BlockFireboxCasing) {
                    blockState = blockState.withProperty(BlockFireboxCasing.ACTIVE, isActive);
                    getWorld().setBlockState(blockPos, blockState);
                }
            }
        }
    }

    @Override
    public int getLightValue(IMultiblockPart sourcePart) {
        return sourcePart == null ? 0 : (isActive ? 15 : 0);
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
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == 100) {
            this.isActive = buf.readBoolean();
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return boilerType == null ? null : FactoryBlockPattern.start()
            .aisle("XXX", "CCC", "CCC", "CCC")
            .aisle("XXX", "CPC", "CPC", "CCC")
            .aisle("XXX", "CSC", "CCC", "CCC")
            .setAmountAtLeast('X', 4)
            .setAmountAtLeast('C', 20)
            .where('S', selfPredicate())
            .where('P', statePredicate(boilerType.pipeState))
            .where('X', state -> statePredicate(GTUtility.getAllPropertyValues(boilerType.fireboxState, BlockFireboxCasing.ACTIVE))
                .or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.IMPORT_ITEMS)).test(state))
            .where('C', statePredicate(boilerType.casingState).or(abilityPartPredicate(
                MultiblockAbility.EXPORT_FLUIDS)))
            .build();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), isActive);
    }

    @Override
    protected boolean checkStructureComponents(Set<IMultiblockPart> parts, Map<MultiblockAbility<Object>, List<Object>> abilities) {
        //noinspection SuspiciousMethodCalls
        int importFluidsSize = abilities.getOrDefault(MultiblockAbility.IMPORT_FLUIDS, Collections.emptyList()).size();
        //noinspection SuspiciousMethodCalls
        return importFluidsSize >= 1 && (importFluidsSize >= 2 ||
            abilities.containsKey(MultiblockAbility.IMPORT_ITEMS)) &&
                abilities.containsKey(MultiblockAbility.EXPORT_FLUIDS);
    }

    private boolean isFireboxPart(IMultiblockPart sourcePart) {
        return isStructureFormed() && (((MetaTileEntity) sourcePart).getPos().getY() < getPos().getY());
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if(sourcePart != null && isFireboxPart(sourcePart)) {
            return isActive ? boilerType.firefoxActiveRenderer : boilerType.fireboxIdleRenderer;
        }
        return boilerType.solidCasingRenderer;
    }

    @Override
    public boolean shouldRenderOverlay(IMultiblockPart sourcePart) {
        return sourcePart == null || !isFireboxPart(sourcePart);
    }
}
