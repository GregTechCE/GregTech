package gregtech.common.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GT_Renderer_Entity_Arrow extends RenderArrow {

    private final ResourceLocation mTexture;

    public GT_Renderer_Entity_Arrow(Class<? extends Entity> aArrowClass, String aTextureName, RenderManager renderManager) {
        super(renderManager);
        this.mTexture = new ResourceLocation("gregtech:textures/entity/" + aTextureName + ".png");
        RenderingRegistry.registerEntityRenderingHandler(aArrowClass, this);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.mTexture;
    }

}
