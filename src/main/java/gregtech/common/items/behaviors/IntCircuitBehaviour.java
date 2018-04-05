package gregtech.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class IntCircuitBehaviour implements IItemBehaviour {

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int configuration = IntCircuitIngredient.getCircuitConfiguration(itemStack);
        lines.add(I18n.format("metaitem.int_circuit.configuration", configuration));
    }
}
