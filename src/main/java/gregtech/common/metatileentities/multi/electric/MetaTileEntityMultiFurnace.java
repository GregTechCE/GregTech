package gregtech.common.metatileentities.multi.electric;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.multiblock.PatternMatchContext;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityMultiFurnace extends RecipeMapMultiblockController {

    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
        MultiblockAbility.IMPORT_ITEMS, MultiblockAbility.EXPORT_ITEMS, MultiblockAbility.INPUT_ENERGY
    };

    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;

    public MetaTileEntityMultiFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FURNACE_RECIPES);
        this.recipeMapWorkable = new MultiFurnaceWorkable(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityMultiFurnace(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gregtech.multiblock.multi_furnace.heating_coil_level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gregtech.multiblock.multi_furnace.heating_coil_discount", heatingCoilDiscount));
        }
        super.addDisplayText(textList);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        CoilType coilType = context.getOrDefault("CoilType", CoilType.CUPRONICKEL);
        this.heatingCoilLevel = coilType.getLevel();
        this.heatingCoilDiscount = coilType.getEnergyDiscount();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.heatingCoilLevel = 0;
        this.heatingCoilDiscount = 0;
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("XXX", "CCC", "XXX")
            .aisle("XXX", "C#C", "XXX")
            .aisle("XSX", "CCC", "XXX")
            .setAmountAtLeast('L', 9)
            .where('S', selfPredicate())
            .where('L', statePredicate(getCasingState()))
            .where('X', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
            .where('C', MetaTileEntityElectricBlastFurnace.heatingCoilPredicate())
            .where('#', isAirPredicate())
            .build();
    }

    public IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(MetalCasingType.INVAR_HEATPROOF);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    protected class MultiFurnaceWorkable extends MultiblockRecipeLogic {

        public MultiFurnaceWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
            int currentItemsEngaged = 0;
            int maxItemsLimit = 16 * heatingCoilLevel;
            ArrayList<CountableIngredient> recipeInputs = new ArrayList<>();
            ArrayList<ItemStack> recipeOutputs = new ArrayList<>();
            for (int index = 0; index < inputs.getSlots(); index++) {
                ItemStack stackInSlot = inputs.getStackInSlot(index);
                if (stackInSlot.isEmpty())
                    continue;
                Recipe matchingRecipe = recipeMap.findRecipe(maxVoltage,
                    Collections.singletonList(stackInSlot), Collections.emptyList());
                CountableIngredient inputIngredient = matchingRecipe == null ? null : matchingRecipe.getInputs().get(0);
                if (inputIngredient != null && (maxItemsLimit - currentItemsEngaged) >= inputIngredient.getCount()) {
                    ItemStack outputStack = matchingRecipe.getOutputs().get(0).copy();
                    int overclockAmount = Math.min(stackInSlot.getCount() / inputIngredient.getCount(),
                        (maxItemsLimit - currentItemsEngaged) / inputIngredient.getCount());
                    recipeInputs.add(new CountableIngredient(inputIngredient.getIngredient(),
                        inputIngredient.getCount() * overclockAmount));
                    if (!outputStack.isEmpty()) {
                        outputStack.setCount(outputStack.getCount() * overclockAmount);
                        recipeOutputs.add(outputStack);
                    }
                    currentItemsEngaged += inputIngredient.getCount() * overclockAmount;
                }

                if (currentItemsEngaged >= maxItemsLimit) break;
            }
            return recipeInputs.isEmpty() ? null : recipeMap.recipeBuilder()
                .inputsIngredients(recipeInputs)
                .outputs(recipeOutputs)
                .EUt(Math.max(1, 4 * heatingCoilLevel / heatingCoilDiscount))
                .duration(512)
                .cannotBeBuffered()
                .build().getResult();
        }
    }

}
