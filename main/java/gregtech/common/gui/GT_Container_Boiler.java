/*  1:   */ package gregtech.common.gui;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import gregtech.api.gui.GT_ContainerMetaTile_Machine;
/*  6:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  7:   */ import gregtech.common.tileentities.boilers.GT_MetaTileEntity_Boiler;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import net.minecraft.entity.player.InventoryPlayer;
/* 11:   */ import net.minecraft.inventory.ICrafting;
/* 12:   */ import net.minecraft.inventory.Slot;
/* 13:   */ import net.minecraftforge.fluids.FluidStack;
/* 14:   */ 
/* 15:   */ public class GT_Container_Boiler
/* 16:   */   extends GT_ContainerMetaTile_Machine
/* 17:   */ {
/* 18:   */   public GT_Container_Boiler(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aSteamCapacity)
/* 19:   */   {
/* 20:20 */     super(aInventoryPlayer, aTileEntity);
/* 21:21 */     this.mSteamCapacity = aSteamCapacity;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addSlots(InventoryPlayer aInventoryPlayer)
/* 25:   */   {
/* 26:26 */     addSlotToContainer(new Slot(this.mTileEntity, 2, 116, 62));
/* 27:27 */     addSlotToContainer(new Slot(this.mTileEntity, 0, 44, 26));
/* 28:28 */     addSlotToContainer(new Slot(this.mTileEntity, 1, 44, 62));
/* 29:29 */     addSlotToContainer(new Slot(this.mTileEntity, 3, 116, 26));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getSlotCount()
/* 33:   */   {
/* 34:34 */     return 4;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getShiftClickSlotCount()
/* 38:   */   {
/* 39:39 */     return 1;
/* 40:   */   }
/* 41:   */   
/* 42:42 */   public int mWaterAmount = 0;
/* 43:42 */   public int mSteamAmount = 0;
/* 44:42 */   public int mProcessingEnergy = 0;
/* 45:42 */   public int mTemperature = 2;
/* 46:   */   private final int mSteamCapacity;
/* 47:   */   
/* 48:   */   public void detectAndSendChanges()
/* 49:   */   {
/* 50:46 */     super.detectAndSendChanges();
/* 51:47 */     if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
/* 52:47 */       return;
/* 53:   */     }
/* 54:49 */     this.mTemperature = ((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mTemperature;
/* 55:50 */     this.mProcessingEnergy = ((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mProcessingEnergy;
/* 56:51 */     this.mSteamAmount = (((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mSteam == null ? 0 : ((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mSteam.amount);
/* 57:52 */     this.mWaterAmount = (((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mFluid == null ? 0 : ((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).mFluid.amount);
/* 58:   */     
/* 59:54 */     this.mTemperature = Math.min(54, Math.max(0, this.mTemperature * 54 / (((GT_MetaTileEntity_Boiler)this.mTileEntity.getMetaTileEntity()).maxProgresstime() - 10)));
/* 60:55 */     this.mSteamAmount = Math.min(54, Math.max(0, this.mSteamAmount * 54 / (this.mSteamCapacity - 100)));
/* 61:56 */     this.mWaterAmount = Math.min(54, Math.max(0, this.mWaterAmount * 54 / 15900));
/* 62:57 */     this.mProcessingEnergy = Math.min(14, Math.max(this.mProcessingEnergy > 0 ? 1 : 0, this.mProcessingEnergy * 14 / 1000));
/* 63:   */     
/* 64:59 */     Iterator var2 = this.crafters.iterator();
/* 65:60 */     while (var2.hasNext())
/* 66:   */     {
/* 67:61 */       ICrafting var1 = (ICrafting)var2.next();
/* 68:62 */       var1.sendProgressBarUpdate(this, 100, this.mTemperature);
/* 69:63 */       var1.sendProgressBarUpdate(this, 101, this.mProcessingEnergy);
/* 70:64 */       var1.sendProgressBarUpdate(this, 102, this.mSteamAmount);
/* 71:65 */       var1.sendProgressBarUpdate(this, 103, this.mWaterAmount);
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   @SideOnly(Side.CLIENT)
/* 76:   */   public void updateProgressBar(int par1, int par2)
/* 77:   */   {
/* 78:72 */     super.updateProgressBar(par1, par2);
/* 79:73 */     switch (par1)
/* 80:   */     {
/* 81:   */     case 100: 
/* 82:74 */       this.mTemperature = par2; break;
/* 83:   */     case 101: 
/* 84:75 */       this.mProcessingEnergy = par2; break;
/* 85:   */     case 102: 
/* 86:76 */       this.mSteamAmount = par2; break;
/* 87:   */     case 103: 
/* 88:77 */       this.mWaterAmount = par2;
/* 89:   */     }
/* 90:   */   }
/* 91:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.gui.GT_Container_Boiler
 * JD-Core Version:    0.7.0.1
 */