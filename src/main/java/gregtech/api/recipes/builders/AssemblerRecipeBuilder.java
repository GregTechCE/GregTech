package gregtech.api.recipes.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.ImmutableMap;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.capability.impl.ElectricItem;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;

public class AssemblerRecipeBuilder extends RecipeBuilder<AssemblerRecipeBuilder> {
	
	protected int circuitMeta = -1;
	protected long maxEnergy = 0L;
	
	public AssemblerRecipeBuilder() {
    }

    public AssemblerRecipeBuilder(Recipe recipe, RecipeMap<AssemblerRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public AssemblerRecipeBuilder(RecipeBuilder<AssemblerRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }
    
    @Override
    public boolean applyProperty(String key, Object value) {
        if (key.equals("circuit") && value instanceof Number) {
            this.circuitMeta = ((Number) value).intValue();
            return true;
        }
        return false;
    }
	
    @Override
    public AssemblerRecipeBuilder copy() {
        return new AssemblerRecipeBuilder(this);
    }
    
    public AssemblerRecipeBuilder circuitMeta(int circuitMeta) {
        if (!GTUtility.isBetweenInclusive(0, 32, circuitMeta)) {
            GTLog.logger.error("Integrated Circuit Metadata cannot be less than 0 and more than 32", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.circuitMeta = circuitMeta;
        return this;
    }
    
    public AssemblerRecipeBuilder energyInputs(ItemStack... stacks) {
    	return energyInputs(Arrays.asList(stacks));
    }
    
    public AssemblerRecipeBuilder energyInputs(Collection<ItemStack> batteries) {
    	batteries = new ArrayList<>(batteries);
    	batteries.removeIf(stack -> stack == null || stack.isEmpty());
    	Iterator<ItemStack> iter = batteries.iterator();
    	while(iter.hasNext()) {
    		ItemStack stack = iter.next();
    		IElectricItem container = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
    		if (container == null) {
    			GTLog.logger.error("One of itemStacks have not energy container, use inputs() instead.", new IllegalArgumentException());
    			recipeStatus = EnumValidationResult.INVALID;
    			break;
    		}
    		
    		this.maxEnergy += container.getMaxCharge() * stack.getCount();
    		this.inputs.add(CountableIngredient.from(stack));
    	}
    	
    	return this;
    }
    
	public AssemblerRecipeBuilder energyOutput(ItemStack energyStack) {
		IElectricItem container = energyStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
    	if (container != null && container instanceof ElectricItem) {
    		((ElectricItem) container).setMaxChargeOverride(this.maxEnergy);
    		this.outputs.add(energyStack);
    	} else {
    		GTLog.logger.error("No energy container on output itemStack! Use ouputs() instead.", new IllegalArgumentException());
			recipeStatus = EnumValidationResult.INVALID;
    	}
    	
    	return this;
    }
    
    
    @Override
    protected EnumValidationResult finalizeAndValidate() {
        if (circuitMeta >= 0) {
            inputs.add(new CountableIngredient(new IntCircuitIngredient(circuitMeta), 0));
        }
        return super.finalizeAndValidate();
    }
    
    @Override
    public void buildAndRegister() {
        if (fluidInputs.size() == 1 && fluidInputs.get(0).getFluid() == Materials.SolderingAlloy.getMaterialFluid()) {
            int amount = fluidInputs.get(0).amount;
            fluidInputs.clear();
            recipeMap.addRecipe(this.copy().fluidInputs(Materials.SolderingAlloy.getFluid(amount)).build());
            recipeMap.addRecipe(this.copy().fluidInputs(Materials.Tin.getFluid((int) (amount * 1.5))).build());
            recipeMap.addRecipe(this.copy().fluidInputs(Materials.Lead.getFluid(amount * 2)).build());
        } else {
            recipeMap.addRecipe(build());
        }
    }
    
    public ValidationResult<Recipe> build() {
        return ValidationResult.newResult(finalizeAndValidate(),
            new Recipe(inputs, outputs, chancedOutputs, fluidInputs, fluidOutputs,
                ImmutableMap.of(), duration, EUt, hidden));
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("circuitMeta", circuitMeta)
            .toString();
    }
}
