package gregtech.api.metatileentity.factory;

import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.WorkableSteamMetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.BlockMachine;
import net.minecraft.util.ResourceLocation;

public class WorkableSteamMetaTileEntityFactory<T extends WorkableSteamMetaTileEntity> extends MetaTileEntityFactory<T>  {

    protected final RecipeMap<Recipe, ?> recipeMap;

    public WorkableSteamMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, RecipeMap<Recipe, ?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(WorkableSteamMetaTileEntityFactory.class).newInstance(this);
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    public RecipeMap<Recipe, ?> getRecipeMap() {
        return recipeMap;
    }
}
