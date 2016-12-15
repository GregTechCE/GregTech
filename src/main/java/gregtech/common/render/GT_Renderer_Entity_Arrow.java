package gregtech.common.render;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class GT_Renderer_Entity_Arrow<T extends EntityArrow> extends RenderArrow<T> {

    private final ResourceLocation mTexture;

    public GT_Renderer_Entity_Arrow(RenderManager manager, Class aArrowClass, String aTextureName) {
        super(manager);
        this.mTexture = new ResourceLocation("gregtech:textures/entity/" + aTextureName + ".png");
        RenderingRegistry.registerEntityRenderingHandler(aArrowClass, this);
    }

    protected ResourceLocation getEntityTexture(T p_110775_1_) {
        return this.mTexture;
    }

}
