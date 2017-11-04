package gregtech.api.metatileentity.factory;

import com.google.common.base.Throwables;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.common.blocks.BlockMachine;
import net.minecraft.util.ResourceLocation;

public class WorkableMetaTileEntityFactory<T extends WorkableMetaTileEntity<?>> extends TieredMetaTileEntityFactory<T> {

    protected RecipeMap<?, ?> recipeMap;

    public WorkableMetaTileEntityFactory(BlockMachine.ToolClass toolClass, int harvestLevel, String[] description, Class<T> metaTileEntityClass, ResourceLocation modelLocation, int tier, RecipeMap<?, ?> recipeMap) {
        super(toolClass, harvestLevel, description, metaTileEntityClass, modelLocation, tier);
        this.recipeMap = recipeMap;
    }

    @Override
    public IMetaTileEntity constructMetaTileEntity() {
        try {
            return metaTileEntityClass.getConstructor(IMetaTileEntityFactory.class, int.class, RecipeMap.class).newInstance(this, tier, recipeMap);
        } catch (Throwable exception) {
            throw Throwables.propagate(exception);
        }
    }

}
