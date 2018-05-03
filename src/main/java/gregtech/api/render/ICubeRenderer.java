package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICubeRenderer {

    @SideOnly(Side.CLIENT)
    default void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        render(renderState, translation, pipeline, Cuboid6.full);
    }

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getParticleSprite();

    @SideOnly(Side.CLIENT)
    void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] ops, Cuboid6 bounds);

}
