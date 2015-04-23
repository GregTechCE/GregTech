/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
/*  4:   */ import gregtech.api.interfaces.tileentity.IGregTechDeviceInformation;
/*  5:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import gregtech.api.util.GT_Utility;
/*  8:   */ import java.util.List;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 11:   */ import net.minecraft.inventory.IInventory;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraft.nbt.NBTTagCompound;
/* 14:   */ import net.minecraft.tileentity.TileEntity;
/* 15:   */ import net.minecraft.world.World;
/* 16:   */ 
/* 17:   */ public class Behaviour_SensorKit
/* 18:   */   extends Behaviour_None
/* 19:   */ {
/* 20:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 21:   */   {
/* 22:22 */     if ((aPlayer instanceof EntityPlayerMP))
/* 23:   */     {
/* 24:23 */       TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 25:24 */       if (((tTileEntity instanceof IInventory)) && (!((IInventory)tTileEntity).isUseableByPlayer(aPlayer))) {
/* 26:24 */         return false;
/* 27:   */       }
/* 28:25 */       if (((tTileEntity instanceof IGregTechDeviceInformation)) && (((IGregTechDeviceInformation)tTileEntity).isGivingInformation()))
/* 29:   */       {
/* 30:26 */         GT_Utility.setStack(aStack, ItemList.NC_SensorCard.get(aStack.stackSize, new Object[0]));
/* 31:27 */         NBTTagCompound tNBT = aStack.getTagCompound();
/* 32:28 */         if (tNBT == null) {
/* 33:28 */           tNBT = new NBTTagCompound();
/* 34:   */         }
/* 35:29 */         tNBT.setInteger("x", aX);
/* 36:30 */         tNBT.setInteger("y", aY);
/* 37:31 */         tNBT.setInteger("z", aZ);
/* 38:32 */         aStack.setTagCompound(tNBT);
/* 39:   */       }
/* 40:34 */       return true;
/* 41:   */     }
/* 42:36 */     return false;
/* 43:   */   }
/* 44:   */   
/* 45:39 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.sensorkit.tooltip", "Used to display Information using the Mod Nuclear Control");
/* 46:   */   
/* 47:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 48:   */   {
/* 49:43 */     aList.add(this.mTooltip);
/* 50:44 */     return aList;
/* 51:   */   }
/* 52:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_SensorKit
 * JD-Core Version:    0.7.0.1
 */