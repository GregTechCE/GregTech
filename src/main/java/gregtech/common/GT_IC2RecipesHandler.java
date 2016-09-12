package gregtech.common;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class GT_IC2RecipesHandler implements IMachineRecipeManager {

    private ArrayList<RecipeIoContainer> recipes = new ArrayList<>();

    @Override
    public boolean addRecipe(IRecipeInput iRecipeInput, NBTTagCompound metadata, boolean replace, ItemStack... itemStacks) {
        RecipeIoContainer ioContainer = new RecipeIoContainer(iRecipeInput, new RecipeOutput(metadata, itemStacks));
        recipes.add(ioContainer);
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        for(RecipeIoContainer ioContainer : recipes) {
            if (ioContainer.input.matches(input)) {
                if (adjustInput) {
                    if (input.getItem().hasContainerItem(input)) {
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
        }
        return null;
    }

    @Override
    public Iterable<RecipeIoContainer> getRecipes() {
        return recipes;
    }

    @Override
    public boolean isIterable() {
        return true;
    }

}
