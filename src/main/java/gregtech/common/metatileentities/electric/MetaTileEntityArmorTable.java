package gregtech.common.metatileentities.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.armor.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.util.PositionedRect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityArmorTable extends MetaTileEntity {

    public MetaTileEntityArmorTable(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityArmorTable(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
    }

    @Override
    public int getLightOpacity() {
        return 1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int horizontalGridSize = 12;
        int verticalGridSize = 7;
        ComponentGridWidget gridWidget = new ComponentGridWidget(11, 11, 20, 3, horizontalGridSize, verticalGridSize).setGridColor(0xFF5B5B5B);
        GridElementDef elementDef = new GridElementDef(4, 3,
            (elementSize, slotSize) -> {
                SimpleGridElementWidget elementWidget = new SimpleGridElementWidget(elementSize, slotSize, GuiTextures.COMPONENT_BATTERY, new PositionedRect(0, 5, 70, 50));
                elementWidget.addConnection(1, ConnectionType.POWER, ElementOrientation.RIGHT);
                return elementWidget;
            });
        gridWidget.placeWidgetAt(3, 2, elementDef, ElementOrientation.TOP);

        return ModularUI.builder(GuiTextures.BOXED_BACKGROUND, 28 + horizontalGridSize * 20, 28 + verticalGridSize * 20)
            .widget(gridWidget)
            .build(getHolder(), entityPlayer);
    }
}
