package gregtech.api.recipes.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.gregtech.recipe.ChancedEntry")
@ZenRegister
public class ChancedEntry {

    private final IItemStack output;
    private final int chance;

    public ChancedEntry(IItemStack output, int chance) {
        this.output = output;
        this.chance = chance;
    }

    @ZenGetter("output")
    public IItemStack getOutput() {
        return output;
    }

    @ZenGetter("chance")
    public int getChance() {
        return chance;
    }
}
