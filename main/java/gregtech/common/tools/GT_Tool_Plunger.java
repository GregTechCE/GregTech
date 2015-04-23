/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  6:   */ import gregtech.api.interfaces.IIconContainer;
/*  7:   */ import gregtech.api.interfaces.IItemBehaviour;
/*  8:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import gregtech.common.items.behaviors.Behaviour_Plunger_Fluid;
/* 11:   */ import gregtech.common.items.behaviors.Behaviour_Plunger_Item;

/* 12:   */ import java.util.Map;

/* 13:   */ import net.minecraft.block.Block;
/* 14:   */ import net.minecraft.entity.EntityLivingBase;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ import net.minecraft.util.ChatComponentText;
/* 17:   */ import net.minecraft.util.EnumChatFormatting;
/* 18:   */ import net.minecraft.util.IChatComponent;
/* 19:   */ 
/* 20:   */ public class GT_Tool_Plunger
/* 21:   */   extends GT_Tool
/* 22:   */ {
/* 23:   */   public float getBaseDamage()
/* 24:   */   {
/* 25:22 */     return 1.25F;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public float getMaxDurabilityMultiplier()
/* 29:   */   {
/* 30:27 */     return 0.25F;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getCraftingSound()
/* 34:   */   {
/* 35:32 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getEntityHitSound()
/* 39:   */   {
/* 40:37 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String getBreakingSound()
/* 44:   */   {
/* 45:42 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getMiningSound()
/* 49:   */   {
/* 50:47 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(101));
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 54:   */   {
/* 55:52 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 56:53 */     return (tTool != null) && (tTool.equals("plunger"));
/* 57:   */   }
/* 58:   */   
/* 59:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 60:   */   {
/* 61:58 */     return aIsToolHead ? Textures.ItemIcons.PLUNGER : null;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 65:   */   {
/* 66:63 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 70:   */   {
/* 71:68 */     aItem.addItemBehavior(aID, new Behaviour_Plunger_Item(getToolDamagePerDropConversion()));
/* 72:69 */     aItem.addItemBehavior(aID, new Behaviour_Plunger_Fluid(getToolDamagePerDropConversion()));
/* 73:   */     try
/* 74:   */     {
/* 75:71 */       Object tObject = GT_Utility.callConstructor("gregtech.common.items.behaviors.Behaviour_Plunger_Essentia", 0, null, false, new Object[] { Integer.valueOf(getToolDamagePerDropConversion()) });
/* 76:72 */       if ((tObject instanceof IItemBehaviour)) {
/* 77:72 */         aItem.addItemBehavior(aID, (IItemBehaviour)tObject);
/* 78:   */       }
/* 79:   */     }
/* 80:   */     catch (Throwable e) {}
/* 81:   */   }
/* 82:   */   
/* 83:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 84:   */   {
/* 85:78 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " got stuck trying to escape through a Pipe while fighting " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 86:   */   }
/* 87:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Plunger
 * JD-Core Version:    0.7.0.1
 */