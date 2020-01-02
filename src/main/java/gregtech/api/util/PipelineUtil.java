package gregtech.api.util;

import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import org.apache.commons.lang3.ArrayUtils;

public class PipelineUtil {

    public static IVertexOperation[] color(IVertexOperation[] ops, int rgbColor) {
        return ArrayUtils.add(ops, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(rgbColor)));
    }

}
