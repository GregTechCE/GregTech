/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.interfaces.IIconContainer;
/*  5:   */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*  6:   */ import java.util.List;

/*  7:   */ import net.minecraft.block.Block;
/*  8:   */ import net.minecraft.block.material.Material;
/*  9:   */ import net.minecraft.entity.Entity;
/* 10:   */ import net.minecraft.entity.EntityLivingBase;
/* 11:   */ import net.minecraft.entity.monster.EntitySnowman;
/* 12:   */ import net.minecraft.entity.player.EntityPlayer;
/* 13:   */ import net.minecraft.entity.player.EntityPlayerMP;
/* 14:   */ import net.minecraft.item.Item;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ import net.minecraft.server.management.ItemInWorldManager;
/* 17:   */ import net.minecraft.util.ChatComponentText;
/* 18:   */ import net.minecraft.util.EnumChatFormatting;
/* 19:   */ import net.minecraft.util.IChatComponent;
/* 20:   */ import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
/* 21:   */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/* 22:   */ 
/* 23:   */ public class GT_Tool_Plow
/* 24:   */   extends GT_Tool
/* 25:   */ {
/* 26:23 */   private ThreadLocal<Object> sIsHarvestingRightNow = new ThreadLocal();
/* 27:   */   
/* 28:   */   public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer)
/* 29:   */   {
/* 30:27 */     return (aEntity instanceof EntitySnowman) ? aOriginalDamage * 4.0F : aOriginalDamage;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public float getBaseDamage()
/* 34:   */   {
/* 35:32 */     return 1.0F;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 39:   */   {
/* 40:37 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 41:38 */     return ((tTool != null) && (tTool.equals("plow"))) || (aBlock.getMaterial() == Material.snow) || (aBlock.getMaterial() == Material.craftedSnow);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 45:   */   {
/* 46:43 */     int rConversions = 0;
/* 47:44 */     if ((this.sIsHarvestingRightNow.get() == null) && ((aPlayer instanceof EntityPlayerMP)))
/* 48:   */     {
/* 49:45 */       this.sIsHarvestingRightNow.set(this);
/* 50:46 */       for (int i = -1; i < 2; i++) {
/* 51:46 */         for (int j = -1; j < 2; j++) {
/* 52:46 */           for (int k = -1; k < 2; k++) {
/* 53:46 */             if (((i != 0) || (j != 0) || (k != 0)) && (aStack.getItem().getDigSpeed(aStack, aPlayer.worldObj.getBlock(aX + i, aY + j, aZ + k), aPlayer.worldObj.getBlockMetadata(aX + i, aY + j, aZ + k)) > 0.0F) && (((EntityPlayerMP)aPlayer).theItemInWorldManager.tryHarvestBlock(aX + i, aY + j, aZ + k))) {
/* 54:46 */               rConversions++;
/* 55:   */             }
/* 56:   */           }
/* 57:   */         }
/* 58:   */       }
/* 59:47 */       this.sIsHarvestingRightNow.set(null);
/* 60:   */     }
/* 61:49 */     return rConversions;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 65:   */   {
/* 66:54 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadPlow.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
/* 67:   */   }
/* 68:   */   
/* 69:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 70:   */   {
/* 71:59 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 75:   */   {
/* 76:64 */     return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " plew through the yard of " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 77:   */   }
/* 78:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Plow
 * JD-Core Version:    0.7.0.1
 */