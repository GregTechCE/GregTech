/*  1:   */ package gregtech.common.render;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.renderer.Tessellator;
/*  4:   */ import net.minecraft.util.IIcon;
/*  5:   */ 
/*  6:   */ public class GT_RenderUtil
/*  7:   */ {
/*  8:   */   public static void renderItemIcon(IIcon icon, double size, double z, float nx, float ny, float nz)
/*  9:   */   {
/* 10: 8 */     renderItemIcon(icon, 0.0D, 0.0D, size, size, z, nx, ny, nz);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static void renderItemIcon(IIcon icon, double xStart, double yStart, double xEnd, double yEnd, double z, float nx, float ny, float nz)
/* 14:   */   {
/* 15:12 */     if (icon == null) {
/* 16:12 */       return;
/* 17:   */     }
/* 18:13 */     Tessellator.instance.startDrawingQuads();
/* 19:14 */     Tessellator.instance.setNormal(nx, ny, nz);
/* 20:15 */     if (nz > 0.0F)
/* 21:   */     {
/* 22:16 */       Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
/* 23:17 */       Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
/* 24:18 */       Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
/* 25:19 */       Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
/* 26:   */     }
/* 27:   */     else
/* 28:   */     {
/* 29:21 */       Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
/* 30:22 */       Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
/* 31:23 */       Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
/* 32:24 */       Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
/* 33:   */     }
/* 34:26 */     Tessellator.instance.draw();
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_RenderUtil
 * JD-Core Version:    0.7.0.1
 */