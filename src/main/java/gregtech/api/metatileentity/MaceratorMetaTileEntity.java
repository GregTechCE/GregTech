package gregtech.api.metatileentity;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.OrientedOverlayRenderer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MaceratorMetaTileEntity extends SimpleMachineMetaTileEntity {

    private int outputAmount;

    public MaceratorMetaTileEntity(String metaTileEntityId, RecipeMap<?> recipeMap, int outputAmount, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, recipeMap, renderer, tier);
        this.outputAmount = outputAmount;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SimpleMachineMetaTileEntity(metaTileEntityId, workable.recipeMap, renderer, getTier()) {
            @Override
            protected IItemHandlerModifiable createExportItemHandler() {
                return new ItemStackHandler(outputAmount);
            }
        };
    }
}