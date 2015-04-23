/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*  4:   */ import gregtech.api.gui.GT_Slot_Output;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  7:   */ import net.minecraft.inventory.Slot;
/*  8:   */ 
/*  9:   */ public class GT_Container_BronzeBlastFurnace
/* 10:   */   extends GT_ContainerMetaTile_Machine
/* 11:   */ {
/* 12:   */   public GT_Container_BronzeBlastFurnace(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity)
/* 13:   */   {
/* 14:12 */     super(aInventoryPlayer, aTileEntity);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void addSlots(InventoryPlayer aInventoryPlayer)
/* 18:   */   {
/* 19:17 */     addSlotToContainer(new Slot(this.mTileEntity, 0, 34, 16));
/* 20:18 */     addSlotToContainer(new Slot(this.mTileEntity, 1, 34, 34));
/* 21:19 */     addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 2, 86, 25));
/* 22:20 */     addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 3, 104, 25));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getSlotCount()
/* 26:   */   {
/* 27:25 */     return 4;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getShiftClickSlotCount()
/* 31:   */   {
/* 32:30 */     return 2;
/* 33:   */   }
/* 34:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_BronzeBlastFurnace
 * JD-Core Version:    0.7.0.1
 */