package gregtech.common.render;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class GT_Renderer_Entity_Arrow
  extends RenderArrow
{
  private final ResourceLocation mTexture;
  
  public GT_Renderer_Entity_Arrow(Class aArrowClass, String aTextureName)
  {
    this.mTexture = new ResourceLocation("gregtech:textures/entity/" + aTextureName + ".png");
    RenderingRegistry.registerEntityRenderingHandler(aArrowClass, this);
  }
  
  protected ResourceLocation getEntityTexture(Entity p_110775_1_)
  {
    return this.mTexture;
  }
}


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_Renderer_Entity_Arrow
 * JD-Core Version:    0.7.0.1
 */