/*   1:    */ package gregtech.common.tools;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.ItemIcons;
/*   6:    */ import gregtech.api.interfaces.IIconContainer;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;

/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;

/*  11:    */ import net.minecraft.block.Block;
/*  12:    */ import net.minecraft.block.material.Material;
/*  13:    */ import net.minecraft.entity.EntityLivingBase;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.init.Blocks;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.util.ChatComponentText;
/*  18:    */ import net.minecraft.util.EnumChatFormatting;
/*  19:    */ import net.minecraft.util.IChatComponent;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;
/*  22:    */ import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
/*  23:    */ 
/*  24:    */ public class GT_Tool_Saw
/*  25:    */   extends GT_Tool
/*  26:    */ {
/*  27:    */   public int getToolDamagePerBlockBreak()
/*  28:    */   {
/*  29: 27 */     return 50;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getToolDamagePerDropConversion()
/*  33:    */   {
/*  34: 32 */     return 100;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getToolDamagePerContainerCraft()
/*  38:    */   {
/*  39: 37 */     return 200;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getToolDamagePerEntityAttack()
/*  43:    */   {
/*  44: 42 */     return 200;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getBaseQuality()
/*  48:    */   {
/*  49: 47 */     return 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public float getBaseDamage()
/*  53:    */   {
/*  54: 52 */     return 1.75F;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public float getSpeedMultiplier()
/*  58:    */   {
/*  59: 57 */     return 1.0F;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public float getMaxDurabilityMultiplier()
/*  63:    */   {
/*  64: 62 */     return 1.0F;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getCraftingSound()
/*  68:    */   {
/*  69: 67 */     return null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getEntityHitSound()
/*  73:    */   {
/*  74: 72 */     return null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getBreakingSound()
/*  78:    */   {
/*  79: 77 */     return (String)GregTech_API.sSoundList.get(Integer.valueOf(0));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getMiningSound()
/*  83:    */   {
/*  84: 82 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent)
/*  88:    */   {
/*  89: 87 */     if ((aBlock.getMaterial() == Material.leaves) && ((aBlock instanceof IShearable)))
/*  90:    */     {
/*  91: 88 */       aPlayer.worldObj.setBlock(aX, aY, aZ, aBlock, aMetaData, 0);
/*  92: 89 */       if (((IShearable)aBlock).isShearable(aStack, aPlayer.worldObj, aX, aY, aZ))
/*  93:    */       {
/*  94: 90 */         ArrayList<ItemStack> tDrops = ((IShearable)aBlock).onSheared(aStack, aPlayer.worldObj, aX, aY, aZ, aFortune);
/*  95: 91 */         aDrops.clear();
/*  96: 92 */         aDrops.addAll(tDrops);
/*  97: 93 */         aEvent.dropChance = 1.0F;
/*  98:    */       }
/*  99: 95 */       aPlayer.worldObj.setBlock(aX, aY, aZ, Blocks.air, 0, 0);
/* 100:    */     }
/* 101: 96 */     else if (((aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce)) && (aDrops.isEmpty()))
/* 102:    */     {
/* 103: 97 */       aDrops.add(new ItemStack(aBlock, 1, aMetaData));
/* 104: 98 */       aPlayer.worldObj.setBlockToAir(aX, aY, aZ);
/* 105: 99 */       aEvent.dropChance = 1.0F;
/* 106:100 */       return 1;
/* 107:    */     }
/* 108:102 */     return 0;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isMinableBlock(Block aBlock, byte aMetaData)
/* 112:    */   {
/* 113:107 */     String tTool = aBlock.getHarvestTool(aMetaData);
/* 114:108 */     return ((tTool != null) && ((tTool.equals("axe")) || (tTool.equals("saw")))) || (aBlock.getMaterial() == Material.leaves) || (aBlock.getMaterial() == Material.vine) || (aBlock.getMaterial() == Material.wood) || (aBlock.getMaterial() == Material.cactus) || (aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ItemStack getBrokenItem(ItemStack aStack)
/* 118:    */   {
/* 119:113 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack)
/* 123:    */   {
/* 124:118 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_SAW;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public short[] getRGBa(boolean aIsToolHead, ItemStack aStack)
/* 128:    */   {
/* 129:123 */     return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {}
/* 133:    */   
/* 134:    */   public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity)
/* 135:    */   {
/* 136:133 */     return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was getting cut down by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
/* 137:    */   }
/* 138:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tools.GT_Tool_Saw
 * JD-Core Version:    0.7.0.1
 */