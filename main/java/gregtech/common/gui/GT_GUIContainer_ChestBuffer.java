/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  5:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  6:   */ 
/*  7:   */ public class GT_GUIContainer_ChestBuffer
/*  8:   */   extends GT_GUIContainerMetaTile_Machine
/*  9:   */ {
/* 10:   */   public GT_GUIContainer_ChestBuffer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 11:   */   {
/* 12:11 */     super(new GT_Container_ChestBuffer(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/ChestBuffer.png");
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/* 16:   */   {
/* 17:16 */     super.drawGuiContainerBackgroundLayer(par1, par2, par3);
/* 18:17 */     int x = (this.width - this.xSize) / 2;
/* 19:18 */     int y = (this.height - this.ySize) / 2;
/* 20:19 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/* 21:   */   }
/* 22:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_GUIContainer_ChestBuffer
 * JD-Core Version:    0.7.0.1
 */