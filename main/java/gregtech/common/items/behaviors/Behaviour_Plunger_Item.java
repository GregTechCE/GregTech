/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  5:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntityItemPipe;
/*  6:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntityItemPipe.Util;
/*  7:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  8:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  9:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/* 10:   */ import gregtech.api.util.GT_LanguageManager;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import java.util.HashMap;
/* 13:   */ import java.util.LinkedHashMap;
/* 14:   */ import java.util.List;
/* 15:   */ import java.util.Map;
/* 16:   */ import net.minecraft.entity.item.EntityItem;
/* 17:   */ import net.minecraft.entity.player.EntityPlayer;
/* 18:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 19:   */ import net.minecraft.item.ItemStack;
/* 20:   */ import net.minecraft.tileentity.TileEntity;
/* 21:   */ import net.minecraft.world.World;
/* 22:   */ 
/* 23:   */ public class Behaviour_Plunger_Item
/* 24:   */   extends Behaviour_None
/* 25:   */ {
/* 26:   */   private final int mCosts;
/* 27:   */   
/* 28:   */   public Behaviour_Plunger_Item(int aCosts)
/* 29:   */   {
/* 30:25 */     this.mCosts = aCosts;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 34:   */   {
/* 35:30 */     if (aWorld.isRemote) {
/* 36:30 */       return false;
/* 37:   */     }
/* 38:31 */     TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 39:32 */     if ((aTileEntity instanceof IGregTechTileEntity))
/* 40:   */     {
/* 41:33 */       IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity)aTileEntity).getMetaTileEntity();
/* 42:34 */       if ((tMetaTileEntity instanceof IMetaTileEntityItemPipe)) {
/* 43:35 */         for (IMetaTileEntityItemPipe tTileEntity : GT_Utility.sortMapByValuesAcending(IMetaTileEntityItemPipe.Util.scanPipes((IMetaTileEntityItemPipe)tMetaTileEntity, new HashMap(), 0L, false, true)).keySet())
/* 44:   */         {
/* 45:36 */           int i = 0;
/* 46:36 */           for (int j = tTileEntity.getSizeInventory(); i < j; i++) {
/* 47:36 */             if (tTileEntity.isValidSlot(i)) {
/* 48:37 */               if ((tTileEntity.getStackInSlot(i) != null) && (
/* 49:38 */                 (aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts))))
/* 50:   */               {
/* 51:39 */                 ItemStack tStack = tTileEntity.decrStackSize(i, 64);
/* 52:40 */                 if (tStack != null)
/* 53:   */                 {
/* 54:41 */                   EntityItem tEntity = new EntityItem(aWorld, ((IGregTechTileEntity)aTileEntity).getOffsetX((byte)aSide, 1) + 0.5D, ((IGregTechTileEntity)aTileEntity).getOffsetY((byte)aSide, 1) + 0.5D, ((IGregTechTileEntity)aTileEntity).getOffsetZ((byte)aSide, 1) + 0.5D, tStack);
/* 55:42 */                   tEntity.motionX = 0.0D;tEntity.motionY = 0.0D;tEntity.motionZ = 0.0D;
/* 56:43 */                   aWorld.spawnEntityInWorld(tEntity);
/* 57:44 */                   GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/* 58:   */                 }
/* 59:46 */                 return true;
/* 60:   */               }
/* 61:   */             }
/* 62:   */           }
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:53 */     return false;
/* 67:   */   }
/* 68:   */   
/* 69:56 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.plunger.item", "Clears Items from Pipes");
/* 70:   */   
/* 71:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 72:   */   {
/* 73:60 */     aList.add(this.mTooltip);
/* 74:61 */     return aList;
/* 75:   */   }
/* 76:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Plunger_Item
 * JD-Core Version:    0.7.0.1
 */