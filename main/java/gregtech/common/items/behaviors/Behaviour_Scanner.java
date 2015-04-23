/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.interfaces.IItemBehaviour;
/*  5:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  6:   */ import gregtech.api.util.GT_LanguageManager;
/*  7:   */ import gregtech.api.util.GT_Utility;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Map;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class Behaviour_Scanner
/* 17:   */   extends Behaviour_None
/* 18:   */ {
/* 19:18 */   public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Scanner();
/* 20:   */   
/* 21:   */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 22:   */   {
/* 23:22 */     if (((aPlayer instanceof EntityPlayerMP)) && (aItem.canUse(aStack, 20000.0D)))
/* 24:   */     {
/* 25:23 */       ArrayList<String> tList = new ArrayList();
/* 26:24 */       if (aItem.use(aStack, GT_Utility.getCoordinateScan(tList, aPlayer, aWorld, 1, aX, aY, aZ, aSide, hitX, hitY, hitZ), aPlayer)) {
/* 27:24 */         for (int i = 0; i < tList.size(); i++) {
/* 28:24 */           GT_Utility.sendChatToPlayer(aPlayer, (String)tList.get(i));
/* 29:   */         }
/* 30:   */       }
/* 31:25 */       return true;
/* 32:   */     }
/* 33:27 */     GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(108)), 1, 1.0F, aX, aY, aZ);
/* 34:28 */     return aPlayer instanceof EntityPlayerMP;
/* 35:   */   }
/* 36:   */   
/* 37:31 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.scanning", "Can scan Blocks in World");
/* 38:   */   
/* 39:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 40:   */   {
/* 41:35 */     aList.add(this.mTooltip);
/* 42:36 */     return aList;
/* 43:   */   }
/* 44:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Scanner
 * JD-Core Version:    0.7.0.1
 */