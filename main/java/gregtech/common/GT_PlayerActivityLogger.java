/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
/*  4:   */ import gregtech.api.util.GT_Log;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ 
/*  8:   */ public class GT_PlayerActivityLogger
/*  9:   */   implements Runnable
/* 10:   */ {
/* 11:   */   public void run()
/* 12:   */   {
/* 13:   */     try
/* 14:   */     {
/* 15:   */       for (;;)
/* 16:   */       {
/* 17:13 */         if (GT_Log.pal == null) {
/* 18:13 */           return;
/* 19:   */         }
/* 20:14 */         ArrayList<String> tList = GT_Mod.gregtechproxy.mBufferedPlayerActivity;
/* 21:15 */         GT_Mod.gregtechproxy.mBufferedPlayerActivity = new ArrayList();
/* 22:16 */         String tLastOutput = "";
/* 23:17 */         int i = 0;
/* 24:17 */         for (int j = tList.size(); i < j; i++)
/* 25:   */         {
/* 26:18 */           if (!tLastOutput.equals(tList.get(i))) {
/* 27:18 */             GT_Log.pal.println((String)tList.get(i));
/* 28:   */           }
/* 29:19 */           tLastOutput = (String)tList.get(i);
/* 30:   */         }
/* 31:21 */         Thread.sleep(10000L);
/* 32:   */       }
/* 33:   */     }
/* 34:   */     catch (Throwable e) {}
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_PlayerActivityLogger
 * JD-Core Version:    0.7.0.1
 */