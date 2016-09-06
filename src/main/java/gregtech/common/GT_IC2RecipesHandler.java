package gregtech.common;

import com.google.common.collect.Lists;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.recipe.BasicMachineRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GT_IC2RecipesHandler implements IMachineRecipeManager {

    private HashMap<RecipeInputWrapper, RecipeIoContainer> recipes = new HashMap<>();

    private static class RecipeInputWrapper {

        private IRecipeInput recipeInput;

        public RecipeInputWrapper(IRecipeInput recipeInput) {
            this.recipeInput = recipeInput;
        }

        //For easy gets and replacements
        @Override
        public boolean equals(Object object) {
            if(object instanceof ItemStack) {
                ItemStack stack = (ItemStack) object;
                if(recipeInput.matches(stack)) {
                    if(stack.stackSize < recipeInput.getAmount() ||
                            stack.getItem().hasContainerItem(stack) &&
                            stack.stackSize != recipeInput.getAmount())
                        return false;
                    return true;
                }
                return false;
            } else if(object instanceof RecipeInputWrapper) {
                RecipeInputWrapper inputWrapper = (RecipeInputWrapper) object;
                return Arrays.equals(recipeInput.getInputs().toArray(),
                        inputWrapper.recipeInput.getInputs().toArray());
            }
            return false;
        }

        //for fast access
        @Override
        public int hashCode() {
            return Arrays.hashCode(recipeInput.getInputs().toArray());
        }

    }

    @Override
    public boolean addRecipe(IRecipeInput iRecipeInput, NBTTagCompound metadata, boolean replace, ItemStack... itemStacks) {
        RecipeIoContainer ioContainer = new RecipeIoContainer(iRecipeInput, new RecipeOutput(metadata, itemStacks));
        RecipeInputWrapper inputWrapper = new RecipeInputWrapper(iRecipeInput);
        RecipeIoContainer replaced = recipes.put(inputWrapper, ioContainer);
        if(replaced != null) {
            System.out.println("Replaced recipe " + replaced.input.getInputs() + " to " + iRecipeInput.getInputs());
        }
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        RecipeIoContainer ioContainer = recipes.get(input);
        if(ioContainer != null) {
            if(adjustInput) {
                if(input.getItem().hasContainerItem(input)) {
                    ItemStack container = input.getItem().getContainerItem(input);
                    input.setItem(container.getItem());
                    input.stackSize = container.stackSize;
                    input.setItemDamage(container.getItemDamage());
                    input.setTagCompound(container.getTagCompound());
                } else {
                    input.stackSize -= ioContainer.input.getAmount();
                }
            }
            return ioContainer.output;
        }

        return new RecipeOutput(new NBTTagCompound(), Lists.newArrayList());
    }

    @Override
    public Iterable<RecipeIoContainer> getRecipes() {
        return recipes.values();
    }

    @Override
    public boolean isIterable() {
        return true;
    }

}
