package gregtech.common.items;

import com.google.gson.JsonObject;

import gregtech.api.GTValues;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class MetaItemCraftingHelper implements IIngredientFactory {
    public static void init() {
        CraftingHelper.register(new ResourceLocation(GTValues.MODID, "metaitem"), new MetaItemCraftingHelper());
    }

    @Override
    public Ingredient parse(JsonContext context, JsonObject json) {
        return null;
    }
}
