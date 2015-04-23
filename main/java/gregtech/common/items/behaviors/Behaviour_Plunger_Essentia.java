/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  5:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import gregtech.api.util.GT_Utility;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import net.minecraft.entity.player.EntityPlayer;
/* 11:   */ import net.minecraft.entity.player.PlayerCapabilities;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraft.tileentity.TileEntity;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 16:   */ import thaumcraft.api.aspects.IEssentiaTransport;
/* 17:   */ 
/* 18:   */ public class Behaviour_Plunger_Essentia
/* 19:   */   extends Behaviour_None
/* 20:   */ {
/* 21:   */   private final int mCosts;
/* 22:   */   
/* 23:   */   public Behaviour_Plunger_Essentia(int aCosts)
/* 24:   */   {
/* 25:22 */     this.mCosts = aCosts;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 29:   */   {
/* 30:27 */     if (aWorld.isRemote) {
/* 31:27 */       return false;
/* 32:   */     }
/* 33:28 */     TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
/* 34:29 */     if (((aTileEntity instanceof IEssentiaTransport)) && (
/* 35:30 */       (aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts))))
/* 36:   */     {
/* 37:31 */       GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/* 38:32 */       for (ForgeDirection tDirection : ForgeDirection.VALID_DIRECTIONS) {
/* 39:32 */         ((IEssentiaTransport)aTileEntity).takeEssentia(((IEssentiaTransport)aTileEntity).getEssentiaType(tDirection), ((IEssentiaTransport)aTileEntity).getEssentiaAmount(tDirection), tDirection);
/* 40:   */       }
/* 41:33 */       return true;
/* 42:   */     }
/* 43:36 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:39 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.plunger.essentia", "Clears Essentia from Containers and Tubes");
/* 47:   */   
/* 48:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 49:   */   {
/* 50:43 */     aList.add(this.mTooltip);
/* 51:44 */     return aList;
/* 52:   */   }
/* 53:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Plunger_Essentia
 * JD-Core Version:    0.7.0.1
 */