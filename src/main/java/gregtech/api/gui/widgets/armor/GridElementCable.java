package gregtech.api.gui.widgets.armor;

import com.google.common.collect.ImmutableMap;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Size;

import java.util.Map;

public class GridElementCable extends PipeGridElementWidget {

    public GridElementCable(Size elementSize, int slotSize) {
        super(elementSize, slotSize, ConnectionType.POWER);
    }

    @Override
    protected Map<TextureSprite, TextureArea> createSpriteMap() {
        return ImmutableMap.of(
            TextureSprite.STRAIGHT, TextureArea.fullImage("textures/gui/armor/component/cable_power/str8.png"),
            TextureSprite.EDGE, TextureArea.fullImage("textures/gui/armor/component/cable_power/edge.png"),
            TextureSprite.T_JOINT, TextureArea.fullImage("textures/gui/armor/component/cable_power/t_joint.png"),
            TextureSprite.CROSS, TextureArea.fullImage("textures/gui/armor/component/cable_power/cross.png")
        );
    }
}
