/*  1:   */ package gregtech.loaders.preload;
/*  2:   */ 
/*  3:   */ import gregtech.api.util.GT_Log;
/*  4:   */ import gregtech.common.redstonecircuits.GT_Circuit_BasicLogic;
/*  5:   */ import gregtech.common.redstonecircuits.GT_Circuit_BitAnd;
/*  6:   */ import gregtech.common.redstonecircuits.GT_Circuit_CombinationLock;
/*  7:   */ import gregtech.common.redstonecircuits.GT_Circuit_Equals;
/*  8:   */ import gregtech.common.redstonecircuits.GT_Circuit_Pulser;
/*  9:   */ import gregtech.common.redstonecircuits.GT_Circuit_Randomizer;
/* 10:   */ import gregtech.common.redstonecircuits.GT_Circuit_RedstoneMeter;
/* 11:   */ import gregtech.common.redstonecircuits.GT_Circuit_Repeater;
/* 12:   */ import gregtech.common.redstonecircuits.GT_Circuit_Timer;
/* 13:   */ import java.io.PrintStream;
/* 14:   */ 
/* 15:   */ public class GT_Loader_CircuitBehaviors
/* 16:   */   implements Runnable
/* 17:   */ {
/* 18:   */   public void run()
/* 19:   */   {
/* 20: 9 */     GT_Log.out.println("GT_Mod: Register Redstone Circuit behaviours.");
/* 21:10 */     new GT_Circuit_Timer(0);
/* 22:11 */     new GT_Circuit_BasicLogic(1);
/* 23:12 */     new GT_Circuit_Repeater(2);
/* 24:13 */     new GT_Circuit_Pulser(3);
/* 25:14 */     new GT_Circuit_RedstoneMeter(4);
/* 26:   */     
/* 27:16 */     new GT_Circuit_Randomizer(8);
/* 28:   */     
/* 29:18 */     new GT_Circuit_CombinationLock(16);
/* 30:19 */     new GT_Circuit_BitAnd(17);
/* 31:20 */     new GT_Circuit_Equals(18);
/* 32:   */   }
/* 33:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.preload.GT_Loader_CircuitBehaviors
 * JD-Core Version:    0.7.0.1
 */