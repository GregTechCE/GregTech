package gregtech.integration.jei;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.FuelRecipeLogic;
import gregtech.api.gui.impl.ModularUIGuiHandler;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.machines.FuelRecipeMap;
import gregtech.api.recipes.machines.RecipeMapFurnace;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoCategory;
import gregtech.integration.jei.recipe.FacadeRegistryPlugin;
import gregtech.integration.jei.recipe.GTRecipeWrapper;
import gregtech.integration.jei.recipe.RecipeMapCategory;
import gregtech.integration.jei.recipe.fuel.FuelRecipeMapCategory;
import gregtech.integration.jei.recipe.fuel.GTFuelRecipeWrapper;
import gregtech.integration.jei.recipe.primitive.CokeOvenRecipeCategory;
import gregtech.integration.jei.recipe.primitive.CokeOvenRecipeWrapper;
import gregtech.integration.jei.recipe.primitive.PrimitiveBlastRecipeCategory;
import gregtech.integration.jei.recipe.primitive.PrimitiveBlastRecipeWrapper;
import gregtech.integration.jei.utils.CustomItemReturnRecipeWrapper;
import gregtech.integration.jei.utils.MachineSubtypeHandler;
import gregtech.integration.jei.utils.MetaItemSubtypeHandler;
import gregtech.loaders.recipe.CustomItemReturnShapedOreRecipeRecipe;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class GTJeiPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        MetaItemSubtypeHandler subtype = new MetaItemSubtypeHandler();
        for (MetaItem<?> metaItem : MetaItems.ITEMS) {
            subtypeRegistry.registerSubtypeInterpreter(metaItem, subtype);
        }
        subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(MetaBlocks.MACHINE), new MachineSubtypeHandler());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new MultiblockInfoCategory(registry.getJeiHelpers()));
        for (RecipeMap<?> recipeMap : RecipeMap.getRecipeMaps()) {
            registry.addRecipeCategories(new RecipeMapCategory(recipeMap, registry.getJeiHelpers().getGuiHelper()));
        }
        for (FuelRecipeMap fuelRecipeMap : FuelRecipeMap.getRecipeMaps()) {
            registry.addRecipeCategories(new FuelRecipeMapCategory(fuelRecipeMap, registry.getJeiHelpers().getGuiHelper()));
        }
        registry.addRecipeCategories(new PrimitiveBlastRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CokeOvenRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        MultiblockInfoCategory.registerRecipes(registry);
        registry.handleRecipes(CustomItemReturnShapedOreRecipeRecipe.class, recipe -> new CustomItemReturnRecipeWrapper(jeiHelpers, recipe), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeRegistryPlugin(new FacadeRegistryPlugin());

        ModularUIGuiHandler modularUIGuiHandler = new ModularUIGuiHandler();
        registry.addAdvancedGuiHandlers(modularUIGuiHandler);
        registry.addGhostIngredientHandler(modularUIGuiHandler.getGuiContainerClass(), modularUIGuiHandler);

        for (RecipeMap<?> recipeMap : RecipeMap.getRecipeMaps()) {
            List<GTRecipeWrapper> recipesList = recipeMap.getRecipeList()
                .stream().filter(recipe -> !recipe.isHidden() && recipe.hasValidInputsForDisplay())
                .map(r -> new GTRecipeWrapper(recipeMap, r))
                .collect(Collectors.toList());
            registry.addRecipes(recipesList, GTValues.MODID + ":" + recipeMap.unlocalizedName);
        }

        for (FuelRecipeMap fuelRecipeMap : FuelRecipeMap.getRecipeMaps()) {
            List<GTFuelRecipeWrapper> recipeList = fuelRecipeMap.getRecipeList().stream()
                .map(GTFuelRecipeWrapper::new)
                .collect(Collectors.toList());
            registry.addRecipes(recipeList, GTValues.MODID + ":" + fuelRecipeMap.unlocalizedName);
        }

        for (ResourceLocation metaTileEntityId : GregTechAPI.META_TILE_ENTITY_REGISTRY.getKeys()) {
            MetaTileEntity metaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityId);
            //noinspection ConstantConditions
            if (metaTileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null) != null) {
                IControllable workableCapability = metaTileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);

                if (workableCapability instanceof AbstractRecipeLogic) {
                    RecipeMap<?> recipeMap = ((AbstractRecipeLogic) workableCapability).recipeMap;
                    registry.addRecipeCatalyst(metaTileEntity.getStackForm(), GTValues.MODID + ":" + recipeMap.unlocalizedName);
                    if(recipeMap instanceof RecipeMapFurnace) {
                        registry.addRecipeCatalyst(metaTileEntity.getStackForm(), VanillaRecipeCategoryUid.SMELTING);
                    }
                } else if (workableCapability instanceof FuelRecipeLogic) {
                    FuelRecipeMap recipeMap = ((FuelRecipeLogic) workableCapability).recipeMap;
                    registry.addRecipeCatalyst(metaTileEntity.getStackForm(), GTValues.MODID + ":" + recipeMap.unlocalizedName);
                }
            }
        }

        for (MetaTileEntity breweryTile : MetaTileEntities.BREWERY) {
            registry.addRecipeCatalyst(breweryTile.getStackForm(), VanillaRecipeCategoryUid.BREWING);
        }

        String semiFluidMapId = GTValues.MODID + ":" + RecipeMaps.SEMI_FLUID_GENERATOR_FUELS.getUnlocalizedName();
        registry.addRecipeCatalyst(MetaTileEntities.LARGE_BRONZE_BOILER.getStackForm(), semiFluidMapId);
        registry.addRecipeCatalyst(MetaTileEntities.LARGE_STEEL_BOILER.getStackForm(), semiFluidMapId);
        registry.addRecipeCatalyst(MetaTileEntities.LARGE_TITANIUM_BOILER.getStackForm(), semiFluidMapId);
        registry.addRecipeCatalyst(MetaTileEntities.LARGE_TUNGSTENSTEEL_BOILER.getStackForm(), semiFluidMapId);
        
        FluidStack air = Materials.Air.getFluid(1000);
        registry.addIngredientInfo(air, air.getClass(), I18n.format("gregtech.machine.air_collector.jei_description"));
        
        String primitiveBlastId = GTValues.MODID + ":" + "primitive_blast_furnace";
        registry.addRecipes(RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.stream()
            .map(PrimitiveBlastRecipeWrapper::new)
            .collect(Collectors.toList()), primitiveBlastId);
        registry.addRecipeCatalyst(MetaTileEntities.PRIMITIVE_BLAST_FURNACE.getStackForm(), primitiveBlastId);
        
        String cokeOvenId = GTValues.MODID + ":" + "coke_oven";
        registry.addRecipes(RecipeMaps.COKE_OVEN_RECIPES.stream()
            .map(CokeOvenRecipeWrapper::new)
            .collect(Collectors.toList()), cokeOvenId);
        registry.addRecipeCatalyst(MetaTileEntities.COKE_OVEN.getStackForm(), cokeOvenId);
    }
}
