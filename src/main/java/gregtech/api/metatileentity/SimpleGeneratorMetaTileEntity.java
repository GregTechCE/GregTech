package gregtech.api.metatileentity;

import gregtech.api.gui.ModularUI;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.render.OrientedOverlayRenderer;
import net.minecraft.entity.player.EntityPlayer;

public class SimpleGeneratorMetaTileEntity extends WorkableTieredMetaTileEntity {

    public SimpleGeneratorMetaTileEntity(String metaTileEntityId, RecipeMap<?> recipeMap, OrientedOverlayRenderer renderer, int tier) {
        super(metaTileEntityId, recipeMap, renderer, tier);
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new SimpleGeneratorMetaTileEntity(metaTileEntityId, workable.recipeMap, renderer, getTier());
    }

    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        return workable.recipeMap.createUITemplate(workable::getProgressPercent, importItems, exportItems, importFluids, exportFluids)
            .label(6, 6, getMetaName())
            .bindPlayerInventory(player.inventory);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createGuiTemplate(entityPlayer).build(getHolder(), entityPlayer);
    }
}
