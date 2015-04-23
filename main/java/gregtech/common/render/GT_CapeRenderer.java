/*  1:   */ package gregtech.common.render;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.util.GT_Log;
/*  5:   */ import gregtech.api.util.GT_Utility;

/*  6:   */ import java.util.Collection;

/*  7:   */ import net.minecraft.client.entity.AbstractClientPlayer;
/*  8:   */ import net.minecraft.client.model.ModelBiped;
/*  9:   */ import net.minecraft.client.renderer.entity.RenderManager;
/* 10:   */ import net.minecraft.client.renderer.entity.RenderPlayer;
/* 11:   */ import net.minecraft.potion.Potion;
/* 12:   */ import net.minecraft.util.MathHelper;
/* 13:   */ import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
/* 14:   */ import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;

/* 15:   */ import org.lwjgl.opengl.GL11;
/* 16:   */ 
/* 17:   */ public class GT_CapeRenderer
/* 18:   */   extends RenderPlayer
/* 19:   */ {
/* 20:22 */   private final ResourceLocation[] mCapes = { new ResourceLocation("gregtech:textures/BrainTechCape.png"), new ResourceLocation("gregtech:textures/GregTechCape.png"), new ResourceLocation("gregtech:textures/MrBrainCape.png"), new ResourceLocation("gregtech:textures/GregoriusCape.png") };
/* 21:   */   private final Collection<String> mCapeList;
/* 22:   */   
/* 23:   */   public GT_CapeRenderer(Collection<String> aCapeList)
/* 24:   */   {
/* 25:26 */     this.mCapeList = aCapeList;
/* 26:27 */     setRenderManager(RenderManager.instance);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void receiveRenderSpecialsEvent(RenderPlayerEvent.Specials.Pre aEvent)
/* 30:   */   {
/* 31:31 */     AbstractClientPlayer aPlayer = (AbstractClientPlayer)aEvent.entityPlayer;
/* 32:32 */     if (GT_Utility.getFullInvisibility(aPlayer))
/* 33:   */     {
/* 34:32 */       aEvent.setCanceled(true);return;
/* 35:   */     }
/* 36:33 */     float aPartialTicks = aEvent.partialRenderTick;
/* 37:35 */     if (aPlayer.isInvisible()) {
/* 38:35 */       return;
/* 39:   */     }
/* 40:36 */     if (GT_Utility.getPotion(aPlayer, Integer.valueOf(Potion.invisibility.id).intValue())) {
/* 41:36 */       return;
/* 42:   */     }
/* 43:   */     try
/* 44:   */     {
/* 45:39 */       ResourceLocation tResource = null;
/* 46:41 */       if (aPlayer.getDisplayName().equalsIgnoreCase("Friedi4321")) {
/* 47:41 */         tResource = this.mCapes[0];
/* 48:   */       }
/* 49:42 */       if (this.mCapeList.contains(aPlayer.getDisplayName().toLowerCase())) {
/* 50:42 */         tResource = this.mCapes[1];
/* 51:   */       }
/* 52:43 */       if (aPlayer.getDisplayName().equalsIgnoreCase("Mr_Brain")) {
/* 53:43 */         tResource = this.mCapes[2];
/* 54:   */       }
/* 55:44 */       if (aPlayer.getDisplayName().equalsIgnoreCase("GregoriusT")) {
/* 56:44 */         tResource = this.mCapes[3];
/* 57:   */       }
/* 58:46 */       if ((tResource != null) && (!aPlayer.getHideCape()))
/* 59:   */       {
/* 60:47 */         bindTexture(tResource);
/* 61:48 */         GL11.glPushMatrix();
/* 62:49 */         GL11.glTranslatef(0.0F, 0.0F, 0.125F);
/* 63:50 */         double d0 = aPlayer.field_71091_bM + (aPlayer.field_71094_bP - aPlayer.field_71091_bM) * aPartialTicks - (aPlayer.prevPosX + (aPlayer.posX - aPlayer.prevPosX) * aPartialTicks);
/* 64:51 */         double d1 = aPlayer.field_71096_bN + (aPlayer.field_71095_bQ - aPlayer.field_71096_bN) * aPartialTicks - (aPlayer.prevPosY + (aPlayer.posY - aPlayer.prevPosY) * aPartialTicks);
/* 65:52 */         double d2 = aPlayer.field_71097_bO + (aPlayer.field_71085_bR - aPlayer.field_71097_bO) * aPartialTicks - (aPlayer.prevPosZ + (aPlayer.posZ - aPlayer.prevPosZ) * aPartialTicks);
/* 66:53 */         float f6 = aPlayer.prevRenderYawOffset + (aPlayer.renderYawOffset - aPlayer.prevRenderYawOffset) * aPartialTicks;
/* 67:54 */         double d3 = MathHelper.sin(f6 * 3.141593F / 180.0F);
/* 68:55 */         double d4 = -MathHelper.cos(f6 * 3.141593F / 180.0F);
/* 69:56 */         float f7 = (float)d1 * 10.0F;
/* 70:57 */         float f8 = (float)(d0 * d3 + d2 * d4) * 100.0F;
/* 71:58 */         float f9 = (float)(d0 * d4 - d2 * d3) * 100.0F;
/* 72:59 */         if (f7 < -6.0F) {
/* 73:59 */           f7 = -6.0F;
/* 74:   */         }
/* 75:60 */         if (f7 > 32.0F) {
/* 76:60 */           f7 = 32.0F;
/* 77:   */         }
/* 78:61 */         if (f8 < 0.0F) {
/* 79:61 */           f8 = 0.0F;
/* 80:   */         }
/* 81:62 */         float f10 = aPlayer.prevCameraYaw + (aPlayer.cameraYaw - aPlayer.prevCameraYaw) * aPartialTicks;
/* 82:63 */         f7 += MathHelper.sin((aPlayer.prevDistanceWalkedModified + (aPlayer.distanceWalkedModified - aPlayer.prevDistanceWalkedModified) * aPartialTicks) * 6.0F) * 32.0F * f10;
/* 83:64 */         if (aPlayer.isSneaking()) {
/* 84:65 */           f7 += 25.0F;
/* 85:   */         }
/* 86:67 */         GL11.glRotatef(6.0F + f8 / 2.0F + f7, 1.0F, 0.0F, 0.0F);
/* 87:68 */         GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 88:69 */         GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 89:70 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 90:71 */         ((ModelBiped)this.mainModel).renderCloak(0.0625F);
/* 91:72 */         GL11.glPopMatrix();
/* 92:   */       }
/* 93:   */     }
/* 94:   */     catch (Throwable e)
/* 95:   */     {
/* 96:75 */       if (GT_Values.D1) {
/* 97:75 */         e.printStackTrace(GT_Log.err);
/* 98:   */       }
/* 99:   */     }
/* :0:   */   }
/* :1:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.render.GT_CapeRenderer
 * JD-Core Version:    0.7.0.1
 */