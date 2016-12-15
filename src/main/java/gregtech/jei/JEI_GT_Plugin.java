package gregtech.jei;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.util.GT_Recipe;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@JEIPlugin
public class JEI_GT_Plugin implements IModPlugin {

    private static IJeiHelpers jeiHelpers;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {}

    @Override
    public void register(@Nonnull IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeHandlers(new JEIGregtechRecipeHandler());
        for(GT_Recipe.GT_Recipe_Map recipe_map : GT_Recipe.GT_Recipe_Map.sMappings) {
            if(recipe_map.mNEIAllowed) {
                registry.addRecipeCategories(new JEIGregtehRecipeCategory(recipe_map));
                registry.addRecipes(recipe_map.mRecipeList.stream()
                        .map(recipe -> new JEIGregtechRecipe(recipe_map, recipe))
                        .collect(Collectors.toList()));
            }
        }
        for(int i = 0; i < GregTech_API.METATILEENTITIES.length; i++) {
            IMetaTileEntity metaTileEntity = GregTech_API.METATILEENTITIES[i];
            if(metaTileEntity instanceof GT_MetaTileEntity_BasicMachine) {
                GT_MetaTileEntity_BasicMachine basicMachine = (GT_MetaTileEntity_BasicMachine) metaTileEntity;
                GT_Recipe.GT_Recipe_Map recipe_map = basicMachine.getRecipeList();
                if(recipe_map != null && recipe_map.mNEIAllowed) {
                    ItemStack rStack = new ItemStack(GregTech_API.sBlockMachines, 1, i);
                    registry.addRecipeCategoryCraftingItem(rStack, recipe_map.mUnlocalizedName);
                }
            }
        }
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}

    public static IJeiHelpers getJeiHelpers() {
        return jeiHelpers;
    }
}
