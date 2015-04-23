/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  7:   */ import net.minecraft.entity.player.InventoryPlayer;
/*  8:   */ import net.minecraft.inventory.Container;
/*  9:   */ import net.minecraft.inventory.ContainerWorkbench;
/* 10:   */ import net.minecraft.network.NetHandlerPlayServer;
/* 11:   */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class GT_Cover_Crafting
/* 15:   */   extends GT_CoverBehavior
/* 16:   */ {
/* 17:   */   public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 18:   */   {
/* 19:13 */     if ((aPlayer instanceof EntityPlayerMP))
/* 20:   */     {
/* 21:14 */       ((EntityPlayerMP)aPlayer).getNextWindowId();
/* 22:15 */       ((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(((EntityPlayerMP)aPlayer).currentWindowId, 1, "Crafting", 9, true));
/* 23:16 */       ((EntityPlayerMP)aPlayer).openContainer = new ContainerWorkbench(((EntityPlayerMP)aPlayer).inventory, ((EntityPlayerMP)aPlayer).worldObj, aTileEntity.getXCoord(), aTileEntity.getYCoord(), aTileEntity.getZCoord())
/* 24:   */       {
/* 25:   */         public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/* 26:   */         {
/* 27:19 */           return true;
/* 28:   */         }
/* 29:21 */       };
/* 30:22 */       ((EntityPlayerMP)aPlayer).openContainer.windowId = ((EntityPlayerMP)aPlayer).currentWindowId;
/* 31:23 */       ((EntityPlayerMP)aPlayer).openContainer.addCraftingToCrafters((EntityPlayerMP)aPlayer);
/* 32:   */     }
/* 33:25 */     return true;
/* 34:   */   }
/* 35:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Crafting
 * JD-Core Version:    0.7.0.1
 */