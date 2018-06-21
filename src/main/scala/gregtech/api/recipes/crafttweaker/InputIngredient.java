package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import gregtech.api.recipes.CountableIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ZenClass("mods.gregtech.recipe.InputIngredient")
@ZenRegister
public class InputIngredient {

    private final CountableIngredient backingIngredient;

    public InputIngredient(CountableIngredient backingIngredient) {
        this.backingIngredient = backingIngredient;
    }

    @ZenGetter("amount")
    public int getAmount() {
        return backingIngredient.getCount();
    }

    @ZenGetter("matchingItems")
    public List<IItemStack> getMatchingItems() {
        return Arrays.stream(backingIngredient.getIngredient().getMatchingStacks())
            .map(MCItemStack::new)
            .collect(Collectors.toList());
    }

    @ZenMethod
    public boolean matches(IItemStack ingredient) {
        return backingIngredient.getIngredient().apply(CraftTweakerMC.getItemStack(ingredient));
    }

}
