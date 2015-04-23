/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.material.MapColor;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ 
/*  6:   */ public class GT_Material_Machines
/*  7:   */   extends Material
/*  8:   */ {
/*  9:   */   public GT_Material_Machines()
/* 10:   */   {
/* 11: 8 */     super(MapColor.ironColor);
/* 12: 9 */     setRequiresTool();
/* 13:10 */     setImmovableMobility();
/* 14:11 */     setAdventureModeExempt();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isOpaque()
/* 18:   */   {
/* 19:16 */     return false;
/* 20:   */   }
/* 21:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Material_Machines
 * JD-Core Version:    0.7.0.1
 */