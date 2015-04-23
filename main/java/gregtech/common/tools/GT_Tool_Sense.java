/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.interfaces.IIconContainer;
/*  5:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  6:   */ import gregtech.common.items.behaviors.Behaviour_Sense;

/*  7:   */ import java.util.List;

/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.block.material.Material;
/* 10:   */ import net.minecraft.entity.EntityLivingBase;
/* 11:   */ import net.minecraft.entity.player.EntityPlayer;
/* 12:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 13:   */ import net.minecraft.item.Item;
/* 14:   */ import net.minecraft.item.ItemStack;
/* 15:   */ import net.minecraft.server.management.ItemInWorldManager;
/* 16:   */ import net.minecraft.util.ChatComponentText;
/* 17:   */ import net.minecraft.util.EnumChatFormatting;
/* 18:   */ import net.minecraft.util.IChatComponent;
/* 19:   */ import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
/* 20:   */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/* 21:   */ 
/* 22:   */ public class GT_Tool_Sense
/* 23:   */   extends GT_Tool
/* 24:   */ {
/* 25:22 */   private ThreadLocal<Object> sIsHarvestingRightNow = new ThreadLocal();
/* 26:   */   
/* 27:   */   public float getBaseDamage()
/* 28:   */   {
/* 29:26 */     return 3.0F;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 33:   */   {
/* 34:31 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 35:32 */     return ((tTool != null) && ((tTool.equals("sense")) || (tTool.equals("scythe")))) || (aBlock.getMaterial() == Material.plants) || (aBlock.getMaterial() == Material.leaves);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 39:   */   {
/* 40:37 */     int rConversions = 0;
/* 41:38 */     if ((this.sIsHarvestingRightNow.get() == null) && ((aPlayer instanceof EntityPlayerMP)))
/* 42:   */     {
/* 43:39 */       this.sIsHarvestingRightNow.set(this);
/* 44:40 */       for (int i = -1; i < 2; i++) {
/* 45:40 */         for (int j = -1; j < 2; j++) {
/* 46:40 */           for (int k = -1; k < 2; k++) {
/* 47:40 */             if (((i != 0) || (j != 0) || (k != 0)) && (aStack.getItem().getDigSpeed(aStack, aPlayer.worldObj.getBlock(aX + i, aY + j, aZ + k), aPlayer.worldObj.getBlockMetadata(aX + i, aY + j, aZ + k)) > 0.0F) && (((EntityPlayerMP)aPlayer).theItemInWorldManager.tryHarvestBlock(aX + i, aY + j, aZ + k))) {
/* 48:40 */               rConversions++;
/* 49:   */             }
/* 50:   */           }
/* 51:   */         }
/* 52:   */       }
/* 53:41 */       this.sIsHarvestingRightNow.set(null);
/* 54:   */     }
/* 55:43 */     return rConversions;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 59:   */   {
/* 60:48 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSense.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 61:   */   }
/* 62:   */   
/* 63:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 64:   */   {
/* 65:53 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID)
/* 69:   */   {
/* 70:58 */     aItem.addItemBehavior(aID, new Behaviour_Sense(getToolDamagePerBlockBreak()));
/* 71:   */   }
/* 72:   */   
/* 73:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 74:   */   {
/* 75:63 */     return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " has taken the Soul of " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 76:   */   }
/* 77:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Sense
 * JD-Core Version:    0.7.0.1
 */