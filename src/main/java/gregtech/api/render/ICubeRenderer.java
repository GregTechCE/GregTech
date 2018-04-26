package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;

public interface ICubeRenderer {

    default void render(CCRenderState renderState, IVertexOperation[] pipeline) {
        render(renderState, pipeline, Cuboid6.full);
    }

    void render(CCRenderState renderState, IVertexOperation[] pipeline, Cuboid6 bounds);

}
