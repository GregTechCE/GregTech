package gregtech.api.recipes.ingredients;

import gregtech.api.recipes.ModHandler;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class IntCircuitIngredient extends Ingredient {

    public static ItemStack getIntegratedCircuit(int configuration) {
        ItemStack stack = MetaItems.INTEGRATED_CIRCUIT.getStackForm();
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("Configuration", configuration);
        stack.setTagCompound(tagCompound);
        return stack;
    }

    public static int getCircuitConfiguration(ItemStack itemStack) {
        if(!MetaItems.INTEGRATED_CIRCUIT.isItemEqual(itemStack) ||
            !itemStack.hasTagCompound()) return 0;
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        return tagCompound.getInteger("Configuration");
    }

    private static ItemStack[] gatherMatchingCircuits(int... matchingConfigurations) {
        ItemStack[] resultItems = new ItemStack[matchingConfigurations.length];
        for(int i = 0; i < resultItems.length; i++) {
            resultItems[i] = getIntegratedCircuit(matchingConfigurations[i]);
        }
        return resultItems;
    }

    private final int[] matchingConfigurations;

    public IntCircuitIngredient(int... matchingConfigurations) {
        super(gatherMatchingCircuits(matchingConfigurations));
        this.matchingConfigurations = matchingConfigurations;
    }

    @Override
    public boolean apply(@Nullable ItemStack itemStack) {
        return itemStack != null && MetaItems.INTEGRATED_CIRCUIT.isItemEqual(itemStack) &&
            ArrayUtils.contains(matchingConfigurations, getCircuitConfiguration(itemStack));
    }

    @Override
    public boolean isSimple() {
        return false;
    }

}
