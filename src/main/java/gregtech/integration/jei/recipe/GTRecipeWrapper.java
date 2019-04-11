package gregtech.integration.jei.recipe;

import codechicken.lib.util.ItemNBTUtils;
import gnu.trove.map.TObjectIntMap;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GTRecipeWrapper implements IRecipeWrapper {

    private final RecipeMap<?> recipeMap;
    private final Recipe recipe;

    public GTRecipeWrapper(RecipeMap<?> recipeMap, Recipe recipe) {
        this.recipeMap = recipeMap;
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        if (!recipe.getInputs().isEmpty()) {
            List<CountableIngredient> recipeInputs = recipe.getInputs();
            List<List<ItemStack>> matchingInputs = new ArrayList<>(recipeInputs.size());
            for (CountableIngredient ingredient : recipeInputs) {
                List<ItemStack> ingredientValues = Arrays.stream(ingredient.getIngredient().getMatchingStacks())
                    .map(ItemStack::copy)
                    .sorted(OreDictUnifier.getItemStackComparator())
                    .collect(Collectors.toList());
                ingredientValues.forEach(stack -> {
                    if (ingredient.getCount() == 0) {
                        ItemNBTUtils.setBoolean(stack, "not_consumed", true);
                        stack.setCount(1);
                    } else stack.setCount(ingredient.getCount());
                });
                matchingInputs.add(ingredientValues);
            }
            ingredients.setInputLists(ItemStack.class, matchingInputs);
        }
        if (!recipe.getFluidInputs().isEmpty()) {
            List<FluidStack> recipeInputs = recipe.getFluidInputs()
                .stream().map(FluidStack::copy)
                .collect(Collectors.toList());
            recipeInputs.forEach(stack -> {
                if (stack.amount == 0) {
                    if (stack.tag == null)
                        stack.tag = new NBTTagCompound();
                    stack.tag.setBoolean("not_consumed", true);
                    stack.amount = 1;
                }
            });
            ingredients.setInputs(FluidStack.class, recipeInputs);
        }
        if (!recipe.getOutputs().isEmpty() || !recipe.getChancedOutputs().isEmpty()) {
            List<ItemStack> recipeOutputs = recipe.getOutputs()
                .stream().map(ItemStack::copy).collect(Collectors.toList());
            TObjectIntMap<ItemStack> chancedOutputs = recipe.getChancedOutputs();
            for (ItemStack chancedStack : chancedOutputs.keySet()) {
                int outputChance = chancedOutputs.get(chancedStack);
                chancedStack = chancedStack.copy();
                ItemNBTUtils.setInteger(chancedStack, "chance", outputChance);
                recipeOutputs.add(chancedStack);
            }
            recipeOutputs.sort(Comparator.comparing(stack -> ItemNBTUtils.getInteger(stack, "chance")));
            ingredients.setOutputs(ItemStack.class, recipeOutputs);
        }
        if (!recipe.getFluidOutputs().isEmpty()) {
            List<FluidStack> recipeOutputs = recipe.getFluidOutputs()
                .stream().map(FluidStack::copy).collect(Collectors.toList());
            ingredients.setOutputs(FluidStack.class, recipeOutputs);
        }
    }

    public void addTooltip(int slotIndex, boolean input, Object ingredient, List<String> tooltip) {
        NBTTagCompound tagCompound;
        if (ingredient instanceof ItemStack) {
            tagCompound = ((ItemStack) ingredient).getTagCompound();
        } else if (ingredient instanceof FluidStack) {
            tagCompound = ((FluidStack) ingredient).tag;
        } else {
            throw new IllegalArgumentException("Unknown ingredient type: " + ingredient.getClass());
        }
        if (tagCompound != null && tagCompound.hasKey("chance")) {
            String chanceString = Recipe.formatChanceValue(tagCompound.getInteger("chance"));
            tooltip.add(I18n.format("gregtech.recipe.chance", chanceString));
        } else if (tagCompound != null && tagCompound.hasKey("not_consumed")) {
            tooltip.add(I18n.format("gregtech.recipe.not_consumed"));
        }
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.total", Math.abs(recipe.getEUt()) * recipeMap.getAmperage() * recipe.getDuration()), 0, 70, 0x111111);
        minecraft.fontRenderer.drawString(I18n.format(recipe.getEUt() >= 0 ? "gregtech.recipe.eu" : "gregtech.recipe.eu_inverted", Math.abs(recipe.getEUt())), 0, 80, 0x111111);
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.duration", recipe.getDuration() / 20f), 0, 90, 0x111111);
        minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe.amperage", recipeMap.getAmperage()), 0, 100, 0x111111);
        int baseYPosition = 110;
        for (String propertyKey : recipe.getPropertyKeys()) {
            minecraft.fontRenderer.drawString(I18n.format("gregtech.recipe." + propertyKey,
                recipe.<Object>getProperty(propertyKey)), 0, baseYPosition, 0x111111);
            baseYPosition += 10;
        }
    }
}
