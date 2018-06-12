package gregtech.integration.jei;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.RecipeMapWorkableHandler;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.items.MetaItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class GTJeiPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        MetaItemSubtype metaItemSubtype = new MetaItemSubtype();
        for(MetaItem<?> metaItem : MetaItems.ITEMS) {
            subtypeRegistry.registerSubtypeInterpreter(metaItem, metaItemSubtype);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        for(RecipeMap<?> recipeMap : RecipeMap.RECIPE_MAPS) {
            registry.addRecipeCategories(new RecipeMapCategory(recipeMap, registry.getJeiHelpers().getGuiHelper()));
        }
    }

    @Override
    public void register(IModRegistry registry) {
        for(RecipeMap<?> recipeMap : RecipeMap.RECIPE_MAPS) {
            List<GTRecipeWrapper> recipesList = recipeMap.getRecipeList()
                .stream().filter(recipe -> !recipe.isHidden())
                .map(r -> new GTRecipeWrapper(recipeMap, r))
                .collect(Collectors.toList());
            registry.addRecipes(recipesList, GTValues.MODID + ":" + recipeMap.unlocalizedName);
        }
        for(String metaTileEntityId : GregTechAPI.META_TILE_ENTITY_REGISTRY.getKeys()) {
            MetaTileEntity metaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityId);
            if(metaTileEntity.hasCapability(IWorkable.CAPABILITY_WORKABLE, null)) {
                IWorkable workableCapability = metaTileEntity.getCapability(IWorkable.CAPABILITY_WORKABLE, null);
                if(workableCapability instanceof RecipeMapWorkableHandler) {
                    RecipeMap<?> recipeMap = ((RecipeMapWorkableHandler) workableCapability).recipeMap;
                    registry.addRecipeCatalyst(metaTileEntity.getStackForm(1), GTValues.MODID + ":" + recipeMap.unlocalizedName);
                }
            }
        }
    }
}
