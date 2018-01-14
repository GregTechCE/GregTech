package gregtech.api.metatileentity.factory;

import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.WorkableSteamMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.BlockMachine;
import net.minecraft.util.ResourceLocation;

public class WorkableSteamMetaTileEntityFactory<T extends WorkableSteamMetaTileEntity<?>> extends MetaTileEntityFactory<T>  {

    protected final RecipeMap<?, ?> recipeMap;

    public WorkableSteamMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, RecipeMap<?, ?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(IMetaTileEntityFactory.class, RecipeMap.class).newInstance(this, recipeMap);
        } catch (Throwable exception) {
            throw new RuntimeException(exception);
        }
    }
}
