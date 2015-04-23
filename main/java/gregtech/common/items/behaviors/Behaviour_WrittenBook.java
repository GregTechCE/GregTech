/*  1:   */ package gregtech.common.items.behaviors;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.relauncher.Side;
/*  4:   */ import cpw.mods.fml.relauncher.SideOnly;
/*  5:   */ import gregtech.api.items.GT_MetaBase_Item;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import gregtech.api.util.GT_Utility.ItemNBT;
/*  8:   */ import java.util.List;
/*  9:   */ import net.minecraft.client.Minecraft;
/* 10:   */ import net.minecraft.client.entity.EntityPlayerSP;
/* 11:   */ import net.minecraft.client.gui.GuiScreenBook;
/* 12:   */ import net.minecraft.entity.player.EntityPlayer;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.world.World;
/* 15:   */ 
/* 16:   */ public class Behaviour_WrittenBook
/* 17:   */   extends Behaviour_None
/* 18:   */ {
/* 19:   */   @SideOnly(Side.CLIENT)
/* 20:   */   public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/* 21:   */   {
/* 22:25 */     if ((GT_Utility.isStringValid(GT_Utility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
/* 23:25 */       Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
/* 24:   */     }
/* 25:26 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 29:   */   {
/* 30:31 */     String tTitle = GT_Utility.ItemNBT.getBookTitle(aStack);
/* 31:32 */     if (GT_Utility.isStringValid(tTitle))
/* 32:   */     {
/* 33:33 */       aList.add(tTitle);
/* 34:34 */       aList.add("by " + GT_Utility.ItemNBT.getBookAuthor(aStack));
/* 35:   */     }
/* 36:36 */     return aList;
/* 37:   */   }
/* 38:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_WrittenBook
 * JD-Core Version:    0.7.0.1
 */