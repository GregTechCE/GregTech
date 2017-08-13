package gregtech.api.metatileentity.factory;

import com.google.common.base.Throwables;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.metatileentity.IMetaTileEntityFactory;
import gregtech.api.metatileentity.WorkableMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class WorkableMetaTileEntityFactory<T extends WorkableMetaTileEntity> extends TieredMetaTileEntityFactory<T> {

    protected RecipeMap<?, ?> recipeMap;

    public WorkableMetaTileEntityFactory(byte baseTileEntityType, Class<T> metaTileEntityClass,
                                         ResourceLocation modelLocation, IBlockState defaultState,
                                         int tier, RecipeMap<?, ?> recipeMap, String... description) {
        super(baseTileEntityType, metaTileEntityClass, modelLocation, defaultState, tier, description);
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
