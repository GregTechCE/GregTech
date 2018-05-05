package gregtech.common.metatileentities.multi;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.SimpleCubeRenderer;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MetaTileEntityLargeBoiler extends MultiblockWithDisplayBase {

    public enum BoilerType {
        BRONZE(800, 2.0f, 500,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.BRONZE_BRICKS),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.BRONZE_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.BRONZE_PIPE),
            Textures.BRONZE_PLATED_BRICKS,
            Textures.BRONZE_FIREBOX, Textures.BRONZE_FIREBOX_ACTIVE),

        STEEL(1600, 1.5f, 1000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.STEEL_PIPE),
            Textures.SOLID_STEEL_CASING,
            Textures.STEEL_FIREBOX, Textures.STEEL_FIREBOX_ACTIVE),

        TITANIUM(3800, 1.0f, 2000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.TITANIUM_STABLE),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.TITANIUM_FIREBOX),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.TITANIUM_PIPE),
            Textures.STABLE_TITANIUM_CASING,
            Textures.TITANIUM_FIREBOX, Textures.TITANIUM_FIREBOX_ACTIVE),

        TUNGSTENSTEEL(7600, 0.5f, 4000,
            MetaBlocks.METAL_CASING.getState(MetalCasingType.TUNGSTENSTEEL_ROBUST),
            MetaBlocks.BOILER_CASING.getState(BoilerCasingType.TUNGSTENSTEEL_FIREBOX),
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

    private final BoilerType boilerType;

    private int currentTemperature;
    private int fuelBurnTicksLeft;
    private boolean isActive;
    private boolean wasActiveAndNeedsUpdate;

    private FluidTankList fluidImportInventory;
    private ItemHandlerList itemImportInventory;
    private FluidTankList steamOutputTank;

    public MetaTileEntityLargeBoiler(String metaTileEntityId, BoilerType boilerType) {
        super(metaTileEntityId);
        this.boilerType = boilerType;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityLargeBoiler(metaTileEntityId, boilerType);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(isStructureFormed()) {
            double outputMultiplier = currentTemperature / (boilerType.maxTemperature * 1.0);
            int steamOutput = (int) (boilerType.baseSteamOutput * outputMultiplier);
            textList.add(new TextComponentTranslation("gregtech.multiblock.large_boiler.temperature",
                currentTemperature, boilerType.maxTemperature));
            textList.add(new TextComponentTranslation("gregtech.multiblock.large_boiler.steam_output",
                steamOutput, boilerType.baseSteamOutput));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void updateFormedValid() {
        if(fuelBurnTicksLeft > 0) {
            --this.fuelBurnTicksLeft;
            if(this.currentTemperature < boilerType.maxTemperature)
                this.currentTemperature++;
            double outputMultiplier = currentTemperature / (boilerType.maxTemperature * 1.0);
            FluidStack steamStack = ModHandler.getSteam((int) (boilerType.baseSteamOutput * outputMultiplier));
            steamOutputTank.fill(steamStack, true);
            if(fuelBurnTicksLeft == 0)
                this.wasActiveAndNeedsUpdate = true;
        } else if(currentTemperature > 0) {
            --this.currentTemperature;
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

    //TODO implement recipe caching for this thing
    private int setupRecipeAndConsumeInputs() {
        Recipe dieselRecipe = RecipeMaps.DIESEL_GENERATOR_FUELS.findRecipe(GTValues.V[9],
            itemImportInventory, fluidImportInventory);
        if(dieselRecipe != null && dieselRecipe.matches(true, false,
            itemImportInventory, fluidImportInventory)) {
            int fuelValue = dieselRecipe.getEUt() * dieselRecipe.getDuration() / 2;
            return (int) (fuelValue * boilerType.fuelConsumptionMultiplier);
        }
        Recipe denseFuelRecipe = RecipeMaps.SEMI_FLUID_GENERATOR_FUELS.findRecipe(GTValues.V[9],
            itemImportInventory, fluidImportInventory);
        if(denseFuelRecipe != null && denseFuelRecipe.matches(true, false,
            itemImportInventory, fluidImportInventory)) {
            int fuelValue = denseFuelRecipe.getEUt() * denseFuelRecipe.getDuration() * 2;
            return (int) (fuelValue * boilerType.fuelConsumptionMultiplier);
        }
        for(int slotIndex = 0; slotIndex < itemImportInventory.getSlots(); slotIndex++) {
            ItemStack itemStack = itemImportInventory.getStackInSlot(slotIndex);
            int fuelBurnValue = TileEntityFurnace.getItemBurnTime(itemStack) / 80;
            if(fuelBurnValue > 0) {
                itemStack.shrink(1);
                itemImportInventory.setStackInSlot(slotIndex, itemStack);
                return fuelBurnValue;
            }
        }
        return 0;
    }

    private void setActive(boolean active) {
        this.isActive = active;
        if(!getWorld().isRemote) {
            writeCustomData(-100, buf -> buf.writeBoolean(isActive));
            markDirty();
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
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == -100) {
            this.isActive = buf.readBoolean();
        }
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("XXX", "CCC", "CCC", "CCC")
            .aisle("XXX", "SPC", "CPC", "CCC")
            .aisle("XXX", "CCC", "CCC", "CCC")
            .where('S', selfPredicate())
            .where('P', statePredicate(boilerType.pipeState))
            .where('X', statePredicate(boilerType.fireboxState).or(abilityPartPredicate(
                MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.IMPORT_ITEMS)))
            .where('C', statePredicate(boilerType.casingState).or(abilityPartPredicate(
                MultiblockAbility.EXPORT_FLUIDS)))
            .build();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MULTIBLOCK_WORKABLE_OVERLAY.render(renderState, translation, pipeline, getFrontFacing(), isActive);
        if(isStructureFormed()) {
            BlockPos cornerPos = getPos().offset(getFrontFacing().getOpposite()).add(-1, -1, -1);
            SimpleCubeRenderer renderer = isActive ? boilerType.fireboxIdleRenderer : boilerType.firefoxActiveRenderer;
            for(int x = 0; x < 3; x++) {
                for(int z = 0; z < 3; z++) {
                    Matrix4 matrix = translation.copy().translate(x, 0, z);
                    if(x == 0) renderer.renderSided(EnumFacing.WEST, matrix, Cuboid6.full, renderState, pipeline);
                    if(x == 2) renderer.renderSided(EnumFacing.EAST, matrix, Cuboid6.full, renderState, pipeline);
                    if(z == 0) renderer.renderSided(EnumFacing.NORTH, matrix, Cuboid6.full, renderState, pipeline);
                    if(z == 2) renderer.renderSided(EnumFacing.SOUTH, matrix, Cuboid6.full, renderState, pipeline);
                }
            }
        }
    }



    @Override
    public ICubeRenderer getBaseTexture() {
        return boilerType.solidCasingRenderer;
    }

}
