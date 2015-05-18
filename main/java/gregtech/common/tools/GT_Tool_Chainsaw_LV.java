/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  6:   */ import gregtech.api.interfaces.IIconContainer;
/*  7:   */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*  8:   */ import java.util.Map;

/*  9:   */ import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.util.ChatComponentText;
/* 12:   */ import net.minecraft.util.EnumChatFormatting;
/* 13:   */ import net.minecraft.util.IChatComponent;
/* 14:   */ 
/* 15:   */ public class GT_Tool_Chainsaw_LV
/* 16:   */   extends GT_Tool_Saw
/* 17:   */ {
/* 18:   */   public int getToolDamagePerBlockBreak()
/* 19:   */   {
/* 20:17 */     return 50;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getToolDamagePerDropConversion()
/* 24:   */   {
/* 25:22 */     return 100;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getToolDamagePerContainerCraft()
/* 29:   */   {
/* 30:27 */     return 800;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getToolDamagePerEntityAttack()
/* 34:   */   {
/* 35:32 */     return 200;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getBaseQuality()
/* 39:   */   {
/* 40:37 */     return 0;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public float getBaseDamage()
/* 44:   */   {
/* 45:42 */     return 3.0F;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public float getSpeedMultiplier()
/* 49:   */   {
/* 50:47 */     return 2.0F;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public float getMaxDurabilityMultiplier()
/* 54:   */   {
/* 55:52 */     return 1.0F;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String getCraftingSound()
/* 59:   */   {
/* 60:57 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String getEntityHitSound()
/* 64:   */   {
/* 65:62 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(105));
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getBreakingSound()
/* 69:   */   {
/* 70:67 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String getMiningSound()
/* 74:   */   {
/* 75:72 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(104));
/* 76:   */   }
/* 77:   */   
/* 78:   */   public boolean canBlock()
/* 79:   */   {
/* 80:77 */     return false;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isWeapon()
/* 84:   */   {
/* 85:82 */     return true;
/* 86:   */   }

					public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 117:    */   {
/* 118:117 */     super.onToolCrafted(aStack, aPlayer);
  					try{GT_Mod.instance.achievements.issueAchievement(aPlayer, "brrrr");}catch(Exception e){}
/* 121:    */   }

/* 87:   */   
/* 88:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 89:   */   {
/* 90:87 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 94:   */   {
/* 95:92 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 99:   */   {
/* :0:97 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was massacred by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* :1:   */   }
/* :2:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Chainsaw_LV
 * JD-Core Version:    0.7.0.1
 */