package gregtech.api.metatileentity.factory;

import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.BlockMachine;
import net.minecraft.util.ResourceLocation;

public class WorkableMetaTileEntityFactory<T extends WorkableMetaTileEntity> extends TieredMetaTileEntityFactory<T> {

    protected final RecipeMap<Recipe, ?> recipeMap;

    public WorkableMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, int tier, RecipeMap<Recipe, ?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation, tier);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(WorkableMetaTileEntityFactory.class).newInstance(this);
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }

    public RecipeMap<Recipe, ?> getRecipeMap() {
        return recipeMap;
    }
}
