package gregtech.loaders.recipe;

import gregtech.api.util.GTUtility;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Predicate;

public class ContainerRecipeWrapper extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    private final IRecipe delegate;
    private final Predicate<ItemStack> itemsToReturn;

    public ContainerRecipeWrapper(IRecipe delegate, Predicate<ItemStack> itemsToReturn) {
        this.delegate = delegate;
        this.itemsToReturn = itemsToReturn;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> remainingItems = delegate.getRemainingItems(inv);
        for(int i = 0; i < remainingItems.size(); i++) {
            if(!remainingItems.get(i).isEmpty()) continue;
            ItemStack stackInSlot = inv.getStackInSlot(i);
            //if specified item should be returned back, copy it with amount 1 and add to remaining items
            if(itemsToReturn.test(stackInSlot)) {
                ItemStack remainingItem = GTUtility.copyAmount(1, stackInSlot);
                remainingItems.set(i, remainingItem);
            }
        }
        return remainingItems;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return delegate.matches(inv, worldIn);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return delegate.getCraftingResult(inv);
    }

    @Override
    public boolean canFit(int width, int height) {
        return delegate.canFit(width, height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return delegate.getRecipeOutput();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return delegate.getIngredients();
    }

    @Override
    public boolean isDynamic() {
        return delegate.isDynamic();
    }

    @Override
    public String getGroup() {
        return delegate.getGroup();
    }
}
