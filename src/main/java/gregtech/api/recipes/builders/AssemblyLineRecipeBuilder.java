package gregtech.api.recipes.builders;

import gregtech.api.recipes.recipes.AssemblyLineRecipe;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssemblyLineRecipeBuilder {

    private ItemStack researchItem;
    private int researchTime;

    private List<ItemStack> inputs = new ArrayList<>();
    private List<FluidStack> fluidInputs = new ArrayList<>();
    private ItemStack output;

    private int duration;
    private int EUt;

    private AssemblyLineRecipeBuilder() {
    }

    public static AssemblyLineRecipeBuilder start() {
        return new AssemblyLineRecipeBuilder();
    }

    public AssemblyLineRecipeBuilder researchItem(ItemStack researchItem) {
        this.researchItem = researchItem;
        return this;
    }

    public AssemblyLineRecipeBuilder researchTime(int researchTime) {
        this.researchTime = researchTime;
        return this;
    }

    public AssemblyLineRecipeBuilder inputs(@Nonnull ItemStack... inputs) {
        Collections.addAll(this.inputs, inputs);
        return this;
    }

    public AssemblyLineRecipeBuilder fluidInputs(@Nonnull FluidStack... inputs) {
        Collections.addAll(this.fluidInputs, inputs);
        return this;
    }

    public AssemblyLineRecipeBuilder output(ItemStack output) {
        this.output = output;
        return this;
    }

    public AssemblyLineRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public AssemblyLineRecipeBuilder EUt(int EUt) {
        this.EUt = EUt;
        return this;
    }

    public ValidationResult<AssemblyLineRecipe> build() {
        return ValidationResult.newResult(validate(),
            new AssemblyLineRecipe(researchItem, researchTime, inputs, fluidInputs, output, duration, EUt));
    }

    protected EnumValidationResult validate() {
        EnumValidationResult result = EnumValidationResult.VALID;

        if (inputs.contains(null)) {
            GTLog.logger.error("Input cannot contain null ItemStacks", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }
        if (fluidInputs.contains(null)) {
            GTLog.logger.error("Fluid input cannot contain null FluidStacks", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }

        if (output == null || output.isEmpty()) {
            GTLog.logger.error("Output ItemStack cannot be null or empty", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }
        if (researchItem == null || output.isEmpty()) {
            GTLog.logger.error("Research ItemStack cannot be null or empty", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }

        if (researchTime <= 0) {
            GTLog.logger.error("Research Time cannot be less or equal to 0", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }
        if (duration <= 0) {
            GTLog.logger.error("Duration cannot be less or equal to 0", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }
        if (EUt <= 0) {
            GTLog.logger.error("EUt cannot be less or equal to 0", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }

        if (!GTUtility.isBetweenInclusive(4, 16, inputs.size())) {
            GTLog.logger.error("Invalid amount of recipe inputs. Should be between {} and {} inclusive", 4, 16);
            GTLog.logger.error("", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }
        if (!GTUtility.isBetweenInclusive(0, 4, fluidInputs.size())) {
            GTLog.logger.error("Invalid amount of recipe fluid inputs. Should be between {} and {} inclusive", 0, 4);
            GTLog.logger.error("", new IllegalArgumentException());
            result = EnumValidationResult.INVALID;
        }

        return result;
    }

    public void buildAndRegister() {
        ValidationResult<AssemblyLineRecipe> result = build();

        if (result.getType() == EnumValidationResult.VALID) {
            //RecipeMap.ASSEMBLYLINE_RECIPES.add(result.getResult());
        }
    }
}
