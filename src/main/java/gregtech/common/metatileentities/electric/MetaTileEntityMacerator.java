package gregtech.common.metatileentities.electric;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.OrientedOverlayRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MetaTileEntityMacerator extends SimpleMachineMetaTileEntity {

    private int outputAmount;

    public MetaTileEntityMacerator(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, int outputAmount, OrientedOverlayRenderer renderer, int tier) {
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