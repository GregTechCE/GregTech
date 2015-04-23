/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  5:   */ import net.minecraft.client.gui.FontRenderer;
/*  6:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  7:   */ 
/*  8:   */ public class GT_GUIContainer_Boiler
/*  9:   */   extends GT_GUIContainerMetaTile_Machine
/* 10:   */ {
/* 11:   */   public GT_GUIContainer_Boiler(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aTextureName, int aSteamCapacity)
/* 12:   */   {
/* 13:10 */     super(new GT_Container_Boiler(aInventoryPlayer, aTileEntity, aSteamCapacity), "gregtech:textures/gui/" + aTextureName);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected void drawGuiContainerForegroundLayer(int par1, int par2)
/* 17:   */   {
/* 18:15 */     this.fontRendererObj.drawString("Boiler", 8, 4, 4210752);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
/* 22:   */   {
/* 23:20 */     super.drawGuiContainerBackgroundLayer(par1, par2, par3);
/* 24:21 */     int x = (this.width - this.xSize) / 2;
/* 25:22 */     int y = (this.height - this.ySize) / 2;
/* 26:23 */     drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
/* 27:25 */     if (this.mContainer != null)
/* 28:   */     {
/* 29:27 */       int tScale = ((GT_Container_Boiler)this.mContainer).mSteamAmount;
/* 30:28 */       if (tScale > 0) {
/* 31:28 */         drawTexturedModalRect(x + 70, y + 25 + 54 - tScale, 194, 54 - tScale, 10, tScale);
/* 32:   */       }
/* 33:29 */       tScale = ((GT_Container_Boiler)this.mContainer).mWaterAmount;
/* 34:30 */       if (tScale > 0) {
/* 35:30 */         drawTexturedModalRect(x + 83, y + 25 + 54 - tScale, 204, 54 - tScale, 10, tScale);
/* 36:   */       }
/* 37:31 */       tScale = ((GT_Container_Boiler)this.mContainer).mTemperature;
/* 38:32 */       if (tScale > 0) {
/* 39:32 */         drawTexturedModalRect(x + 96, y + 25 + 54 - tScale, 214, 54 - tScale, 10, tScale);
/* 40:   */       }
/* 41:33 */       tScale = ((GT_Container_Boiler)this.mContainer).mProcessingEnergy;
/* 42:34 */       if (tScale > 0) {
/* 43:34 */         drawTexturedModalRect(x + 117, y + 44 + 14 - tScale, 177, 14 - tScale, 15, tScale + 1);
/* 44:   */       }
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_GUIContainer_Boiler
 * JD-Core Version:    0.7.0.1
 */