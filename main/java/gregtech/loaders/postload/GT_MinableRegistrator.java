/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.api.util.GT_Log;
/*  4:   */ import gregtech.api.util.GT_ModHandler;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ 
/*  8:   */ public class GT_MinableRegistrator
/*  9:   */   implements Runnable
/* 10:   */ {
/* 11:   */   public void run()
/* 12:   */   {
/* 13:10 */     GT_Log.out.println("GT_Mod: Adding Blocks to the Miners Valuable List.");
/* 14:11 */     GT_ModHandler.addValuableOre(Blocks.glowstone, 0, 1);
/* 15:12 */     GT_ModHandler.addValuableOre(Blocks.soul_sand, 0, 1);
/* 16:   */   }
/* 17:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_MinableRegistrator
 * JD-Core Version:    0.7.0.1
 */