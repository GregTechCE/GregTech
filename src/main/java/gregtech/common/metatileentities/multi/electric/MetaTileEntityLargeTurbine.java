package gregtech.common.metatileentities.multi.electric;

import gregtech.api.GTValues;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.DoubleCachedMultiblockWorkable;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;

public class MetaTileEntityLargeTurbine extends RecipeMapMultiblockController {

    public static final MultiblockAbility<MetaTileEntityRotorHolder> ABILITY_ROTOR_HOLDER = new MultiblockAbility<>();

    private static final int BASE_ROTOR_DAMAGE = 20;
    private static final int BASE_EU_OUTPUT = 1024;
    private static final int EU_OUTPUT_BONUS = 2048;
    private static final int MIN_DURABILITY_TO_WARN = 10;

    public enum TurbineType {

        STEAM(RecipeMaps.STEAM_TURBINE_FUELS, MetaBlocks.TURBINE_CASING.getState(TurbineCasingType.STEEL_TURBINE_CASING), Textures.SOLID_STEEL_CASING),
        GAS(RecipeMaps.GAS_TURBINE_FUELS, MetaBlocks.TURBINE_CASING.getState(TurbineCasingType.STAINLESS_TURBINE_CASING), Textures.CLEAN_STAINLESS_STEEL_CASING),
        PLASMA(RecipeMaps.PLASMA_GENERATOR_FUELS, MetaBlocks.TURBINE_CASING.getState(TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING), Textures.ROBUST_TUNGSTENSTEEL_CASING);

        public final RecipeMap<?> recipeMap;
        public final IBlockState casingState;
        public final ICubeRenderer casingRenderer;

        TurbineType(RecipeMap<?> recipeMap, IBlockState casingState, ICubeRenderer casingRenderer) {
            this.recipeMap = recipeMap;
            this.casingState = casingState;
            this.casingRenderer = casingRenderer;
        }
    }

    private final TurbineType turbineType;

    public MetaTileEntityLargeTurbine(String metaTileEntityId, TurbineType turbineType) {
        super(metaTileEntityId, turbineType.recipeMap);
        this.turbineType = turbineType;
        this.recipeMapWorkable = new LargeTurbineWorkableHandler(this);
        reinitializeStructurePattern();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityLargeTurbine(metaTileEntityId, turbineType);
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        MetaTileEntityRotorHolder rotorHolder = getAbilities(ABILITY_ROTOR_HOLDER).get(0);
        int damageToBeApplied = (int) (BASE_ROTOR_DAMAGE * rotorHolder.getRelativeRotorSpeed()) + 1;
        return rotorHolder.applyDamageToRotor(damageToBeApplied, !consumeIfSuccess);
    }

    @Override
    protected void updateFormedValid() {
        if(isTurbineFaceFree()) {
            recipeMapWorkable.updateWorkable();
        }
    }

    /**
     * @return true if structure formed, workable is active and front face is free
     */
    public boolean isActive() {
        return isTurbineFaceFree() && recipeMapWorkable.isActive();
    }

    /**
     * @return true if turbine is formed and it's face is free and contains
     * only air blocks in front of rotor holder
     */
    public boolean isTurbineFaceFree() {
        return isStructureFormed() && getAbilities(ABILITY_ROTOR_HOLDER).get(0).isFrontFaceFree();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(isStructureFormed()) {
            MetaTileEntityRotorHolder rotorHolder = getAbilities(ABILITY_ROTOR_HOLDER).get(0);
            FluidStack fuelStack = ((LargeTurbineWorkableHandler) recipeMapWorkable).getFuelStack();
            int fuelAmount = fuelStack == null ? 0 : fuelStack.amount;

            ITextComponent fuelName = new TextComponentTranslation(fuelAmount == 0 ? "gregtech.fluid.empty" : fuelStack.getUnlocalizedName());
            textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.fuel_amount", fuelAmount, fuelName));

            if(rotorHolder.getRotorEfficiency() > 0.0) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_speed", rotorHolder.getCurrentRotorSpeed(), rotorHolder.getMaxRotorSpeed()));
                textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_efficiency", (int) (rotorHolder.getRotorEfficiency() * 100)));
                int rotorDurability = (int) (rotorHolder.getRotorDurability() * 100);
                if(rotorDurability > MIN_DURABILITY_TO_WARN) {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.rotor_durability", rotorDurability));
                } else {
                    textList.add(new TextComponentTranslation("gregtech.multiblock.turbine.low_rotor_durability",
                        MIN_DURABILITY_TO_WARN, rotorDurability).setStyle(new Style().setColor(TextFormatting.RED)));
                }
            }
        }
        super.addDisplayText(textList);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return turbineType == null ? null :
            FactoryBlockPattern.start()
            .aisle("CCCC", "CHHC", "CCCC")
            .aisle("CHHC", "R##D", "CHHC")
            .aisle("CCCC", "CSHC", "CCCC")
            .where('S', selfPredicate())
            .where('#', blockPredicate(Blocks.AIR))
            .where('C', statePredicate(getCasingState()))
            .where('H', statePredicate(getCasingState()).or(abilityPartPredicate(getAllowedAbilities())))
            .where('R', abilityPartPredicate(ABILITY_ROTOR_HOLDER))
            .where('D', abilityPartPredicate(MultiblockAbility.OUTPUT_ENERGY))
            .build();
    }

    public MultiblockAbility[] getAllowedAbilities() {
        return turbineType.recipeMap.getMaxFluidOutputs() > 0 ?
            new MultiblockAbility[] {MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_FLUIDS} :
            new MultiblockAbility[] {MultiblockAbility.IMPORT_FLUIDS};
    }

    public IBlockState getCasingState() {
        return turbineType.casingState;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return turbineType.casingRenderer;
    }

    @Override
    protected boolean shouldUseEnergyOutputs() {
        return true;
    }

    protected class LargeTurbineWorkableHandler extends DoubleCachedMultiblockWorkable {

        public LargeTurbineWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        public FluidStack getFuelStack() {
            if(doublePreviousRecipe == null)
                return null;
            FluidStack fuelStack = doublePreviousRecipe.getFluidInputs().get(0);
            return metaTileEntity.getImportFluids().drain(new FluidStack(fuelStack.getFluid(), Integer.MAX_VALUE), false);
        }

        @Override
        protected long getMaxVoltage() {
            return GTValues.V[4];
        }

        @Override
        protected boolean ignoreTooMuchEnergy() {
            return true;
        }

        @Override
        protected int[] calculateOverclock(int EUt, long voltage, long amperage, int duration, boolean consumeInputs) {
            return new int[] {EUt, duration};
        }

        @Override
        protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
            Recipe recipe = super.findRecipe(maxVoltage, inputs, fluidInputs);
            if(recipe == null)
                return null;

            MetaTileEntityRotorHolder rotorHolder = getAbilities(ABILITY_ROTOR_HOLDER).get(0);
            FluidStack inputFluid = recipe.getFluidInputs().get(0);

            double fuelValue = Math.abs(recipe.getEUt()) * recipe.getDuration() / inputFluid.amount;
            double relativeRotorSpeed = rotorHolder.getRelativeRotorSpeed();
            double rotorEfficiency = rotorHolder.getRotorEfficiency();

            int fuelToConsume = (int) Math.floor((2048 / fuelValue) * (relativeRotorSpeed * relativeRotorSpeed));
            int EUt = -(int) ((BASE_EU_OUTPUT + EU_OUTPUT_BONUS * rotorEfficiency) * (relativeRotorSpeed * relativeRotorSpeed));

            return recipeMap.recipeBuilder()
                .fluidInputs(GTUtility.copyAmount(fuelToConsume, inputFluid))
                .EUt(EUt + EUt * (int) (fuelValue / 2048)).duration(1) //so very dense fuels will get more EU generated
                .cannotBeBuffered()
                .build().getResult();
        }

    }

}
