/*  1:   */ package gregtech.loaders.misc;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*  6:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import net.minecraft.init.Blocks;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class GT_CoverLoader
/* 12:   */   implements Runnable
/* 13:   */ {
/* 14:   */   public void run()
/* 15:   */   {
/* 16:14 */     for (byte i = 0; i < 16; i = (byte)(i + 1)) {
/* 17:15 */       GregTech_API.registerCover(new ItemStack(Blocks.carpet, 1, i), new GT_CopiedBlockTexture(Blocks.wool, 0, i), null);
/* 18:   */     }
/* 19:18 */     GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVent", 1L), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), null);
/* 20:19 */     GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentCore", 1L), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), null);
/* 21:20 */     GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentGold", 1L), new GT_RenderedTexture(Textures.BlockIcons.VENT_ADVANCED), null);
/* 22:21 */     GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentSpread", 1L), new GT_RenderedTexture(Textures.BlockIcons.VENT_NORMAL), null);
/* 23:22 */     GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentDiamond", 1L), new GT_RenderedTexture(Textures.BlockIcons.VENT_ADVANCED), null);
/* 24:   */   }
/* 25:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.misc.GT_CoverLoader
 * JD-Core Version:    0.7.0.1
 */