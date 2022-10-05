package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.stream.Collectors;

@ZenClass("mods.gregtech.recipe.RecipeBuilder")
@ZenRegister
public class CTRecipeBuilder {

    private final RecipeBuilder<?> backingBuilder;

    public CTRecipeBuilder(RecipeBuilder<?> backingBuilder) {
        this.backingBuilder = backingBuilder;
    }

    @ZenMethod
    public CTRecipeBuilder duration(int duration) {
        this.backingBuilder.duration(duration);
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder EUt(int EUt) {
        this.backingBuilder.EUt(EUt);
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder hidden() {
        this.backingBuilder.hidden();
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder inputs(IIngredient... ingredients) {
        this.backingBuilder.inputsIngredients(Arrays.stream(ingredients)
            .map(s -> new CountableIngredient(new CraftTweakerIngredientWrapper(s), s.getAmount()))
            .collect(Collectors.toList()));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder notConsumable(IIngredient ingredient) {
        this.backingBuilder.notConsumable(new CraftTweakerIngredientWrapper(ingredient));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder notConsumable(ILiquidStack ingredient) {
        this.backingBuilder.notConsumable(CraftTweakerMC.getLiquidStack(ingredient));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder circuit(int num) {
        this.backingBuilder.notConsumable(CraftTweakerIngredientWrapper.fromStacks(IntCircuitIngredient.getIntegratedCircuit(num)));
        return this;
    }

    //note that fluid input predicates are not supported
    @ZenMethod
    public CTRecipeBuilder fluidInputs(ILiquidStack... ingredients) {
        this.backingBuilder.fluidInputs(Arrays.stream(ingredients)
            .map(CraftTweakerMC::getLiquidStack)
            .collect(Collectors.toList()));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder outputs(IItemStack... ingredients) {
        this.backingBuilder.outputs(Arrays.stream(ingredients)
            .map(CraftTweakerMC::getItemStack)
            .collect(Collectors.toList()));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder chancedOutput(IItemStack outputStack, int chanceValue, int tierChanceBoost) {
        this.backingBuilder.chancedOutput(CraftTweakerMC.getItemStack(outputStack), chanceValue, tierChanceBoost);
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder fluidOutputs(ILiquidStack... ingredients) {
        this.backingBuilder.fluidOutputs(Arrays.stream(ingredients)
            .map(CraftTweakerMC::getLiquidStack)
            .collect(Collectors.toList()));
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder property(String key, int value) {
        boolean applied = this.backingBuilder.applyProperty(key, value);
        if (!applied) {
            throw new IllegalArgumentException("Property " +
                key + " cannot be applied to recipe type " +
                backingBuilder.getClass().getSimpleName());
        }
        return this;
    }

    @ZenMethod
    public CTRecipeBuilder property(String key, IItemStack item) {
        boolean applied = this.backingBuilder.applyProperty(key, CraftTweakerMC.getItemStack(item));
        if (!applied) {
            throw new IllegalArgumentException("Property " +
                key + " cannot be applied to recipe type " +
                backingBuilder.getClass().getSimpleName() + " for Item " + CraftTweakerMC.getItemStack(item).getDisplayName());
        }
        return this;
    }

    @ZenMethod
    public void buildAndRegister() {
        this.backingBuilder.buildAndRegister();
    }

    @ZenMethod
    @Override
    public String toString() {
        return this.backingBuilder.toString();
    }

    public static class CraftTweakerIngredientWrapper extends Ingredient {

        private final IIngredient ingredient;

        public CraftTweakerIngredientWrapper(IIngredient ingredient) {
            super(ingredient.getItems().stream()
                .map(CraftTweakerMC::getItemStack)
                .toArray(ItemStack[]::new));
            this.ingredient = ingredient;
        }

        @Override
        public boolean apply(@Nullable ItemStack itemStack) {
            if (itemStack == null) {
                // Avoiding NPE
                itemStack = ItemStack.EMPTY;
            }

            //because CT is dump enough to compare stack sizes by default...
            // Setting stack size to 1 to avoid problem with non-consumable ingredients (count - 0)
            IItemStack stack = CraftTweakerMC.getIItemStack(itemStack).amount(1);
            return ingredient.amount(1)
                    .matches(stack);
        }
    }

}
