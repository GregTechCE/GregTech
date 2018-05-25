package gregtech.common.crafting;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.MetaItems;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

import com.google.gson.JsonObject;

public class MetaItemIngredientFactory implements IIngredientFactory {
    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {
        String name = JsonUtils.getString(json, "name");
        int amount = JsonUtils.getInt(json, "amount", 1);
        for (MetaItem<?> item : MetaItems.ITEMS) {
            MetaItem<?>.MetaValueItem value = item.getItem(name);
            if (value != null) {
                return Ingredient.fromStacks(value.getStackForm(amount));
            }
        }
        return Ingredient.EMPTY;
    }
}
