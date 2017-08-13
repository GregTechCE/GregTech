package gregtech.loaders.load;

import gregtech.api.items.ItemList;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRegistryIterator implements Runnable {

    public void run() {
        for(ResourceLocation resourceLocation : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(resourceLocation);
            if(item instanceof ItemFood && item != ItemList.IC2_Food_Can_Filled.getItem() && item != ItemList.IC2_Food_Can_Spoiled.getItem()) {
                int foodValue = ((ItemFood) item).getHealAmount(new ItemStack(item, 1));
                if(foodValue > 0) {
                    RecipeMap.CANNER_RECIPES.recipeBuilder()
                            .inputs(ItemList.IC2_Food_Can_Empty.get(foodValue), new ItemStack(item, 1))
                            .outputs(ItemList.IC2_Food_Can_Filled.get(foodValue))
                            .buildAndRegister();
                }
            }
        }
    }
}