/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  5:   */ import net.minecraft.client.gui.FontRenderer;
/*  6:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  7:   */ 
/*  8:   */ public class GT_GUIContainer_Regulator
/*  9:   */   extends GT_GUIContainerMetaTile_Machine
/* 10:   */ {
/* 11:   */   public GT_GUIContainer_Regulator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 12:   */   {
/* 13:11 */     super(new GT_Container_Regulator(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/Regulator.png");
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/* 17:   */   {
/* 18:16 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[0], 120, 9, 16448255);
/* 19:17 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[1], 137, 9, 16448255);
/* 20:18 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[2], 155, 9, 16448255);
/* 21:19 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[3], 120, 26, 16448255);
/* 22:20 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[4], 137, 26, 16448255);
/* 23:21 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[5], 155, 26, 16448255);
/* 24:22 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[6], 120, 43, 16448255);
/* 25:23 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[7], 137, 43, 16448255);
/* 26:24 */     this.fontRendererObj.drawString("" + ((GT_Container_Regulator)this.mContainer).mTargetSlots[8], 155, 43, 16448255);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/* 30:   */   {
/* 31:29 */     super.drawGuiContainerBackgroundLayer(par1, par2, par3);
/* 32:30 */     int x = (this.width - this.xSize) / 2;
/* 33:31 */     int y = (this.height - this.ySize) / 2;
/* 34:32 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_GUIContainer_Regulator
 * JD-Core Version:    0.7.0.1
 */