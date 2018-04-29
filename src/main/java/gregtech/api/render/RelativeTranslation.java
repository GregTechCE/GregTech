package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import net.minecraft.util.math.Vec3i;

/**
 * Well, basically, translation with different op id
 */
public class RelativeTranslation extends Translation {

    private static final int operationIndex = CCRenderState.registerOperation();

    public RelativeTranslation(Vector3 vec) {
        super(vec);
    }

    public RelativeTranslation(Vec3i vec) {
        super(vec);
    }

    public RelativeTranslation(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public int operationID() {
        return operationIndex;
    }
}
