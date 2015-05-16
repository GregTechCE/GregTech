/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   5:    */ import gregtech.api.interfaces.IIconContainer;
/*   6:    */ import gregtech.api.util.GT_Recipe;
/*   7:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*   8:    */ import gregtech.api.util.GT_Utility;
/*   9:    */ import gregtech.common.GT_Proxy;

/*  10:    */ import java.util.List;

/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.entity.EntityLivingBase;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.util.ChatComponentText;
/*  17:    */ import net.minecraft.util.EnumChatFormatting;
/*  18:    */ import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.world.BlockEvent;
/*  19:    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*  20:    */ 
/*  21:    */ public class GT_Tool_JackHammer
/*  22:    */   extends GT_Tool_Drill_LV
/*  23:    */ {
/*  24:    */   public int getToolDamagePerBlockBreak()
/*  25:    */   {
/*  26: 25 */     return GT_Mod.gregtechproxy.mHardRock ? 200 : 400;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getToolDamagePerDropConversion()
/*  30:    */   {
/*  31: 30 */     return 400;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getToolDamagePerContainerCraft()
/*  35:    */   {
/*  36: 35 */     return 3200;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getToolDamagePerEntityAttack()
/*  40:    */   {
/*  41: 40 */     return 800;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getBaseQuality()
/*  45:    */   {
/*  46: 45 */     return 1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getBaseDamage()
/*  50:    */   {
/*  51: 50 */     return 3.0F;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public float getSpeedMultiplier()
/*  55:    */   {
/*  56: 55 */     return 12.0F;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public float getMaxDurabilityMultiplier()
/*  60:    */   {
/*  61: 60 */     return 2.0F;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/*  65:    */   {
/*  66: 65 */     String tTool = aBlock.getHarvestTool(aMetaData);
/*  67: 66 */     return ((tTool != null) && (tTool.equals("pickaxe"))) || (aBlock.getMaterial() == Material.rock) || (aBlock.getMaterial() == Material.glass) || (aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/*  71:    */   {
/*  72: 71 */     int rConversions = 0;
/*  73: 72 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[] { new ItemStack(aBlock, 1, aMetaData) });
/*  74: 73 */     if ((tRecipe == null) || (aBlock.hasTileEntity(aMetaData)))
/*  75:    */     {
/*  76: 74 */       for (ItemStack tDrop : aDrops)
/*  77:    */       {
/*  78: 75 */         tRecipe = GT_Recipe.GT_Recipe_Map.sHammerRecipes.findRecipe(null, true, 2147483647L, null, new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { tDrop }) });
/*  79: 76 */         if (tRecipe != null)
/*  80:    */         {
/*  81: 77 */           ItemStack tHammeringOutput = tRecipe.getOutput(0);
/*  82: 78 */           if (tHammeringOutput != null)
/*  83:    */           {
/*  84: 79 */             rConversions += tDrop.stackSize;
/*  85: 80 */             tDrop.stackSize *= tHammeringOutput.stackSize;
/*  86: 81 */             tHammeringOutput.stackSize = tDrop.stackSize;
/*  87: 82 */             GT_Utility.setStack(tDrop, tHammeringOutput);
/*  88:    */           }
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94: 87 */       aDrops.clear();
/*  95: 88 */       aDrops.add(tRecipe.getOutput(0));
/*  96: 89 */       rConversions++;
/*  97:    */     }
/*  98: 91 */     return rConversions;
/*  99:    */   }

					public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer)
/* 117:    */   {
/* 118:117 */     super.onToolCrafted(aStack, aPlayer);
  					GT_Mod.achievements.issueAchievement(aPlayer, "hammertime");
/* 121:    */   }

/* 100:    */   
/* 101:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 102:    */   {
/* 103: 96 */     return aIsToolHead ? Textures.ItemIcons.JACKHAMMER : null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 107:    */   {
/* 108:101 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " has been jackhammered into pieces by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 109:    */   }
/* 110:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_JackHammer
 * JD-Core Version:    0.7.0.1
 */