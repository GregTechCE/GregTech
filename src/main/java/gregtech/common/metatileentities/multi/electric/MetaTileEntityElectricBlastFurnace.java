package gregtech.common.metatileentities.multi.electric;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.BlockWorldState;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class MetaTileEntityElectricBlastFurnace extends RecipeMapMultiblockController {

    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
        MultiblockAbility.IMPORT_ITEMS, MultiblockAbility.IMPORT_FLUIDS,
        MultiblockAbility.EXPORT_ITEMS, MultiblockAbility.EXPORT_FLUIDS,
        MultiblockAbility.INPUT_ENERGY
    };

    private int blastFurnaceTemperature;
    private int bonusTemperature;

    public MetaTileEntityElectricBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable = new ElectricBlastFurnaceWorkable(this);
        reinitializeStructurePattern();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityElectricBlastFurnace(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            if (ConfigHolder.machineSpecific.useVoltageTieredHeatBonus) {
                textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.bonus_temperature", bonusTemperature));
            }
            textList.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature", blastFurnaceTemperature));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.blastFurnaceTemperature = context.getOrDefault("CoilType", CoilType.CUPRONICKEL).getCoilTemperature();

        if (ConfigHolder.machineSpecific.useVoltageTieredHeatBonus) {
            int energyTier = GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage());
            this.bonusTemperature = ConfigHolder.machineSpecific.VoltageTieredHeatBonusBase * (energyTier - 2);
            this.blastFurnaceTemperature += bonusTemperature;
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public boolean checkRecipe(Recipe recipe, boolean consumeIfSuccess) {
        int recipeRequiredTemp = recipe.getIntegerProperty("blast_furnace_temperature");
        return this.blastFurnaceTemperature >= recipeRequiredTemp;
    }

    public static Predicate<BlockWorldState> heatingCoilPredicate() {
        return blockWorldState -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (!(blockState.getBlock() instanceof BlockWireCoil))
                return false;
            BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
            CoilType coilType = blockWireCoil.getState(blockState);
            CoilType currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
            return currentCoilType.getName().equals(coilType.getName());
        };
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("XXX", "CCC", "CCC", "XXX")
            .aisle("XXX", "C#C", "C#C", "XXX")
            .aisle("XSX", "CCC", "CCC", "XXX")
            .setAmountAtLeast('L', 10)
            .where('S', selfPredicate())
            .where('L', statePredicate(getCasingState()))
            .where('X', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
            .where('C', heatingCoilPredicate())
            .where('#', isAirPredicate())
            .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.INVAR_HEATPROOF);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (ConfigHolder.machineSpecific.ebfCoilBonuses) {
            double coilDiscount = Math.abs(100 - ConfigHolder.machineSpecific.coilBonusEUtDiscount * 100);
            double durationDiscount = Math.abs(100 - ConfigHolder.machineSpecific.coilBonusHighTemperatureDurationDiscount * 100);

            tooltip.add(I18n.format("gregtech.multiblock.electric_blast_furnace.tooltip1", ConfigHolder.machineSpecific.coilBonusTemperature, coilDiscount));
            tooltip.add(I18n.format("gregtech.multiblock.electric_blast_furnace.tooltip2", ConfigHolder.machineSpecific.coilBonusTemperature*2, durationDiscount));
        }
        if (ConfigHolder.machineSpecific.useVoltageTieredHeatBonus) {
            tooltip.add(I18n.format("gregtech.multiblock.electric_blast_furnace.tooltip3", ConfigHolder.machineSpecific.VoltageTieredHeatBonusBase));
        }
    }

    public class ElectricBlastFurnaceWorkable extends MultiblockRecipeLogic {

        public ElectricBlastFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void setupRecipe(Recipe recipe) {
            int[] resultOverclock = calculateOverclock(recipe.getEUt(), getMaxVoltage(), recipe.getDuration(), recipe);
            this.progressTime = 1;
            setMaxProgress(resultOverclock[1]);
            this.recipeEUt = resultOverclock[0];
            this.fluidOutputs = GTUtility.copyFluidList(recipe.getFluidOutputs());
            int tier = getMachineTierForRecipe(recipe);
            this.itemOutputs = GTUtility.copyStackList(recipe.getResultItemOutputs(getOutputInventory().getSlots(), random, tier));
            if (this.wasActiveAndNeedsUpdate) {
                this.wasActiveAndNeedsUpdate = false;
            } else {
                this.setActive(true);
            }
        }

        protected int[] calculateOverclock(int EUt, long voltage, int duration, Recipe recipe) {
            if (!ConfigHolder.machineSpecific.ebfCoilBonuses) {
                return super.calculateOverclock(EUt, voltage, duration);
            }

            if(!allowOverclocking) {
                return new int[] {EUt, duration};
            }
            boolean negativeEU = EUt < 0;
            int tier = getOverclockingTier(voltage);
            if (GTValues.V[tier] <= EUt || tier == 0)
                return new int[]{EUt, duration};
            if (negativeEU)
                EUt = -EUt;
            if (EUt <= 16) {
                int multiplier = EUt <= 8 ? tier : tier - 1;
                int resultEUt = EUt * (1 << multiplier) * (1 << multiplier);
                int resultDuration = duration / (1 << multiplier);
                return new int[]{negativeEU ? -resultEUt : resultEUt, resultDuration};
            } else {
                int resultEUt = EUt;
                double resultDuration = duration;

                // Improved Overclocks - do not overclock further if duration is too small
                int recipeTemp = recipe.getIntegerProperty("blast_furnace_temperature");
                int tempOverBase = Math.max(1, blastFurnaceTemperature - recipeTemp);
                int amountEUtBonus = tempOverBase / ConfigHolder.machineSpecific.coilBonusTemperature;

                for (int i = amountEUtBonus; resultEUt <= GTValues.V[tier - 1] && resultDuration >= 3 && i > 0; i--) {
                    if (i % 2 == 0) {
                        resultEUt *= 4;
                        resultDuration -= (resultDuration * ConfigHolder.machineSpecific.coilBonusHighTemperatureDurationDiscount);
                    }
                }

                // Regular Overclocks - do not overclock further if duration is already too small
                while (resultDuration >= 3 && resultEUt <= GTValues.V[tier - 1]) {
                    resultEUt *= 4;
                    resultDuration /= 2.8;
                }

                // Coil Energy Discount
                resultEUt *= (Math.pow(ConfigHolder.machineSpecific.coilBonusEUtDiscount, amountEUtBonus));

                return new int[]{negativeEU ? -resultEUt : resultEUt, (int) Math.ceil(resultDuration)};
            }
        }
    }
}
