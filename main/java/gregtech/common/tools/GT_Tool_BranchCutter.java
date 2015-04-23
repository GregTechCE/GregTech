/*  1:   */ package gregtech.common.tools;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.ItemIcons;
/*  5:   */ import gregtech.api.interfaces.IIconContainer;
/*  6:   */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import gregtech.api.util.GT_Utility;

/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Random;

/* 11:   */ import net.minecraft.block.Block;
/* 12:   */ import net.minecraft.block.material.Material;
/* 13:   */ import net.minecraft.entity.EntityLivingBase;
/* 14:   */ import net.minecraft.entity.player.EntityPlayer;
/* 15:   */ import net.minecraft.init.Blocks;
/* 16:   */ import net.minecraft.init.Items;
/* 17:   */ import net.minecraft.item.Item;
/* 18:   */ import net.minecraft.item.ItemStack;
/* 19:   */ import net.minecraft.util.ChatComponentText;
/* 20:   */ import net.minecraft.util.EnumChatFormatting;
/* 21:   */ import net.minecraft.util.IChatComponent;
/* 22:   */ import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
/* 23:   */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/* 24:   */ 
/* 25:   */ public class GT_Tool_BranchCutter
/* 26:   */   extends GT_Tool
/* 27:   */ {
/* 28:   */   public float getBaseDamage()
/* 29:   */   {
/* 30:26 */     return 2.5F;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public float getSpeedMultiplier()
/* 34:   */   {
/* 35:31 */     return 0.25F;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public float getMaxDurabilityMultiplier()
/* 39:   */   {
/* 40:36 */     return 0.25F;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean isGrafter()
/* 44:   */   {
/* 45:41 */     return true;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/* 49:   */   {
/* 50:46 */     if (aBlock.getMaterial() == Material.leaves)
/* 51:   */     {
/* 52:47 */       aEvent.dropChance = Math.min(1.0F, Math.max(aEvent.dropChance, (aStack.getItem().getHarvestLevel(aStack, "") + 1) * 0.2F));
/* 53:48 */       if (aBlock == Blocks.leaves)
/* 54:   */       {
/* 55:49 */         aDrops.clear();
/* 56:50 */         if (((aMetaData & 0x3) == 0) && (aPlayer.worldObj.rand.nextInt(9) <= aFortune * 2)) {
/* 57:50 */           aDrops.add(new ItemStack(Items.apple, 1, 0));
/* 58:   */         } else {
/* 59:50 */           aDrops.add(new ItemStack(Blocks.sapling, 1, aMetaData & 0x3));
/* 60:   */         }
/* 61:   */       }
/* 62:51 */       else if (aBlock == Blocks.leaves2)
/* 63:   */       {
/* 64:52 */         aDrops.clear();
/* 65:53 */         aDrops.add(new ItemStack(Blocks.sapling, 1, (aMetaData & 0x3) + 4));
/* 66:   */       }
/* 67:54 */       else if (aBlock == GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("rubberLeaves", 1L)))
/* 68:   */       {
/* 69:55 */         aDrops.clear();
/* 70:56 */         aDrops.add(GT_ModHandler.getIC2Item("rubberSapling", 1L));
/* 71:   */       }
/* 72:   */     }
/* 73:59 */     return 0;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 77:   */   {
/* 78:64 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 79:65 */     return ((tTool != null) && (tTool.equals("grafter"))) || (aBlock.getMaterial() == Material.leaves);
/* 80:   */   }
/* 81:   */   
/* 82:   */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 83:   */   {
/* 84:70 */     return aIsToolHead ? Textures.ItemIcons.GRAFTER : null;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 88:   */   {
/* 89:75 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 90:   */   }
/* 91:   */   
/* 92:   */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 93:   */   {
/* 94:80 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been trimmed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 95:   */   }
/* 96:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_BranchCutter
 * JD-Core Version:    0.7.0.1
 */