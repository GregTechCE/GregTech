package gregtech.common.metatileentities.multi.electric;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.ICubeRenderer;
import net.minecraft.util.math.Vec3i;

public class MetaTileEntityTurbine extends RecipeMapMultiblockController {

    public enum TurbineType {
        ;

       // public final RecipeMap<?> recipeMap;
        //public final

    }

    public MetaTileEntityTurbine(String metaTileEntityId, RecipeMap<?> recipeMap) {
        super(metaTileEntityId, recipeMap);
    }

    @Override
    protected Vec3i getCenterOffset() {
        return null;//new Vec3i(1, -1);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return null;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return null;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return null;
    }
}
