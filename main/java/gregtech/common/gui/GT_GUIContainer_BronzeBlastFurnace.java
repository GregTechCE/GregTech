/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import net.minecraft.client.gui.FontRenderer;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ 
/*  9:   */ public class GT_GUIContainer_BronzeBlastFurnace
/* 10:   */   extends GT_GUIContainerMetaTile_Machine
/* 11:   */ {
/* 12:   */   public GT_GUIContainer_BronzeBlastFurnace(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 13:   */   {
/* 14:11 */     super(new GT_Container_BronzeBlastFurnace(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/BronzeBlastFurnace.png");
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/* 18:   */   {
/* 19:16 */     this.fontRendererObj.drawString("Bronze Blast Furnace", 8, 4, 4210752);
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/* 23:   */   {
/* 24:21 */     super.drawGuiContainerBackgroundLayer(par1, par2, par3);
/* 25:22 */     int x = (this.width - this.xSize) / 2;
/* 26:23 */     int y = (this.height - this.ySize) / 2;
/* 27:24 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/* 28:26 */     if ((this.mContainer != null) && 
/* 29:27 */       (this.mContainer.mProgressTime > 0)) {
/* 30:27 */       drawTexturedModalRect(x + 58, y + 28, 176, 0, Math.max(0, Math.min(20, (this.mContainer.mProgressTime > 0 ? 1 : 0) + this.mContainer.mProgressTime * 20 / (this.mContainer.mMaxProgressTime < 1 ? 1 : this.mContainer.mMaxProgressTime))), 11);
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_GUIContainer_BronzeBlastFurnace
 * JD-Core Version:    0.7.0.1
 */