package gregtech.common.crafting;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MetaItemShapelessRecipeFactory implements IRecipeFactory {
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        String group = JsonUtils.getString(json, "group", "");

        NonNullList<Ingredient> ings = NonNullList.create();
        for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
            ings.add(CraftingHelper.getIngredient(ele, context));

        if (ings.isEmpty())
            throw new JsonParseException("No ingredients for shapeless recipe");

        JsonObject result = JsonUtils.getJsonObject(json, "result");
        String name = JsonUtils.getString(result, "name");
        int amount = JsonUtils.getInt(result, "amount", 1);
        ItemStack stack = ItemStack.EMPTY;
        for (MetaItem<?> item : MetaItems.ITEMS) {
            MetaItem<?>.MetaValueItem value = item.getItem(name);
            if (value != null) {
                stack = value.getStackForm(amount);
            }
        }
        return new ShapelessOreRecipe(group.isEmpty() ? null : new ResourceLocation(group), ings, stack);
    }
}