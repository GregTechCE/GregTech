/*  1:   */ package gregtech.common.render;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*  4:   */ import net.minecraft.client.renderer.entity.RenderArrow;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.util.ResourceLocation;
/*  7:   */ 
/*  8:   */ public class GT_Renderer_Entity_Arrow
/*  9:   */   extends RenderArrow
/* 10:   */ {
/* 11:   */   private final ResourceLocation mTexture;
/* 12:   */   
/* 13:   */   public GT_Renderer_Entity_Arrow(Class aArrowClass, String aTextureName)
/* 14:   */   {
/* 15:13 */     this.mTexture = new ResourceLocation("gregtech:textures/entity/" + aTextureName + ".png");
/* 16:14 */     RenderingRegistry.registerEntityRenderingHandler(aArrowClass, this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected ResourceLocation getEntityTexture(Entity p_110775_1_)
/* 20:   */   {
/* 21:19 */     return this.mTexture;
/* 22:   */   }
/* 23:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_Renderer_Entity_Arrow
 * JD-Core Version:    0.7.0.1
 */