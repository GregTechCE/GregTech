package gregtech.common.metatileentities.multi.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeMapWorkable;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.BlockWorldState;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class MetaTileEntityDieselEngine extends RecipeMapMultiblockController {

    private int currentCycle = 0;

    public MetaTileEntityDieselEngine(String metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.DIESEL_GENERATOR_FUELS);
        this.recipeMapWorkable = new DieselEngineWorkableHandler(this, recipeMap, this::checkRecipe);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityDieselEngine(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(isStructureFormed()) {
            FluidStack lubricantStack = importFluids.drain(Materials.Lubricant.getFluid(Integer.MAX_VALUE), false);
            int lubricantAmount = lubricantStack == null ? 0 : lubricantStack.amount;
            Fluid currentRecipeFluid = ((DieselEngineWorkableHandler) recipeMapWorkable).getCurrentInputFluid();
            FluidStack fuelStack = importFluids.drain(new FluidStack(currentRecipeFluid, Integer.MAX_VALUE), false);
            boolean isBoostingEU = ((DieselEngineWorkableHandler) recipeMapWorkable).isBoostingEU();

            textList.add(new TextComponentTranslation("gregtech.multiblock.lubricant_amount", lubricantAmount));

            if(fuelStack != null) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.fuel_amount", fuelStack.amount,
                    new TextComponentTranslation(fuelStack.getUnlocalizedName())));
            }

            if(isBoostingEU) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.oxygen_boosted"));
            } else {
                textList.add(new TextComponentTranslation("gregtech.multiblock.supply_oxygen_to_boost"));
            }
        }
        super.addDisplayText(textList);
    }

    @Override
    protected boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        int nextCycleIndex = currentCycle + 1;
        if(nextCycleIndex >= 5) {
            FluidStack lubricantStack = Materials.Lubricant.getFluid(2);
            FluidStack drainStack = importFluids.drain(lubricantStack, false);
            if(drainStack != null && drainStack.amount == lubricantStack.amount) {
                if(consumeIfSuccess) {
                    importFluids.drain(lubricantStack, true);
                    this.currentCycle = 0;
                    markDirty();
                }
                return true;
            }
            return false;
        }
        if(consumeIfSuccess) {
            this.currentCycle++;
            markDirty();
        }
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("CurrentCycle", currentCycle);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.currentCycle = data.getInteger("CurrentCycle");
    }

    @Override
    protected boolean shouldUseEnergyOutputs() {
        return true;
    }

    @Override
    protected Vec3d getCenterOffset() {
        return new Vec3d(2, -1, 0);
    }

    protected Predicate<BlockWorldState> intakeCasingPredicate() {
        IBlockState blockState = MetaBlocks.MUTLIBLOCK_CASING.getState(MultiblockCasingType.ENGINE_INTAKE_CASING);
        return blockWorldState -> {
            if(blockWorldState.getBlockState() != blockState)
                return false;
            IBlockState offsetState = blockWorldState.getOffsetState(getFrontFacing());
            return offsetState.getBlock() == Blocks.AIR;
        };
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("XXX", "XDX", "XXX")
            .aisle("XHX", "HGH", "XHX")
            .aisle("XHX", "HGH", "XHX")
            .aisle("XHX", "HGH", "XHX")
            .aisle("AAA", "AYA", "AAA")
            .where('X', statePredicate(getCasingState()))
            .where('G', statePredicate(MetaBlocks.TURBINE_CASING.getState(TurbineCasingType.TITANIUM_GEARBOX)))
            .where('H', statePredicate(getCasingState()).or(abilityPartPredicate(MultiblockAbility.IMPORT_FLUIDS)))
            .where('D', abilityPartPredicate(MultiblockAbility.OUTPUT_ENERGY))
            .where('A', intakeCasingPredicate())
            .where('Y', selfPredicate())
            .build();
    }

    public IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.TITANIUM_STABLE);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return Textures.STABLE_TITANIUM_CASING;
    }

    protected static class DieselEngineWorkableHandler extends MultiblockRecipeMapWorkable {

        private boolean isBoostingEU = false;
        private Fluid currentInputFluid = null;

        public DieselEngineWorkableHandler(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, BiFunction<Recipe, Boolean, Boolean> recipeChecker) {
            super(tileEntity, recipeMap, recipeChecker);
        }

        public boolean isBoostingEU() {
            return isBoostingEU;
        }

        public Fluid getCurrentInputFluid() {
            return currentInputFluid;
        }

        @Override
        protected long getMaxVoltage() {
            return GTValues.V[4];
        }

        @Override
        protected void setupRecipe(Recipe recipe) {
            super.setupRecipe(recipe);
            this.currentInputFluid = recipe.getFluidInputs().get(0).getFluid();
        }

        @Override
        protected void completeRecipe() {
            super.completeRecipe();
            this.isBoostingEU = false;
            this.currentInputFluid = null;
        }

        @Override
        protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
            Recipe recipe = recipeMap.findRecipe(maxVoltage, inputs, fluidInputs);
            if(recipe == null)
                return recipe;
            FluidStack inputFluid = recipe.getFluidInputs().get(0);
            int fuelValue = recipe.getEUt() * recipe.getDuration() / inputFluid.amount;

            return recipeMap.recipeBuilder()
                .fluidInputs(GTUtility.copyAmount(4096 / fuelValue, inputFluid))
                .EUt(2048).duration(2) //2 to allow dividing by 2 below without re-finding recipe
                .build().getResult();
        }

        @Override
        protected int[] calculateOverclock(int EUt, long voltage, long amperage, int duration, boolean consumeInputs) {
            int[] overclock = super.calculateOverclock(EUt, voltage, amperage, duration, consumeInputs);
            FluidStack oxygenStack = Materials.Oxygen.getFluid(2 * overclock[1]);
            IMultipleTankHandler importFluids = getImportFluidsInventory();
            FluidStack drainStack = importFluids.drain(oxygenStack, false);
            if(drainStack != null && drainStack.amount == oxygenStack.amount) {
                overclock[0] *= 3; //resultEUt *= 3
                overclock[1] /= 2; //duration /= 2
                if(consumeInputs) {
                    importFluids.drain(oxygenStack, true);
                    this.isBoostingEU = true;
                }
            }
            return overclock;
        }
    }

}
