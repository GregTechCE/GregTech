/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   5:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   6:    */ import gregtech.api.util.GT_LanguageManager;
/*   7:    */ import gregtech.api.util.GT_Utility;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class Behaviour_SoftHammer
/*  18:    */   extends Behaviour_None
/*  19:    */ {
/*  20:    */   private final int mCosts;
/*  21:    */   
/*  22:    */   public Behaviour_SoftHammer(int aCosts)
/*  23:    */   {
/*  24: 21 */     this.mCosts = aCosts;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  28:    */   {
/*  29: 26 */     if (aWorld.isRemote) {
/*  30: 27 */       return false;
/*  31:    */     }
/*  32: 29 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/*  33: 30 */     if (aBlock == null) {
/*  34: 30 */       return false;
/*  35:    */     }
/*  36: 31 */     byte aMeta = (byte)aWorld.getBlockMetadata(aX, aY, aZ);
/*  37: 34 */     if (aBlock == Blocks.lit_redstone_lamp)
/*  38:    */     {
/*  39: 35 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/*  40:    */       {
/*  41: 36 */         aWorld.isRemote = true;
/*  42: 37 */         aWorld.setBlock(aX, aY, aZ, Blocks.redstone_lamp, 0, 0);
/*  43: 38 */         aWorld.isRemote = false;
/*  44: 39 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/*  45:    */       }
/*  46: 41 */       return true;
/*  47:    */     }
/*  48: 43 */     if (aBlock == Blocks.redstone_lamp)
/*  49:    */     {
/*  50: 44 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/*  51:    */       {
/*  52: 45 */         aWorld.isRemote = true;
/*  53: 46 */         aWorld.setBlock(aX, aY, aZ, Blocks.lit_redstone_lamp, 0, 0);
/*  54: 47 */         aWorld.isRemote = false;
/*  55: 48 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/*  56:    */       }
/*  57: 50 */       return true;
/*  58:    */     }
/*  59: 52 */     if (aBlock == Blocks.golden_rail)
/*  60:    */     {
/*  61: 53 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/*  62:    */       {
/*  63: 54 */         aWorld.isRemote = true;
/*  64: 55 */         aWorld.setBlock(aX, aY, aZ, aBlock, (aMeta + 8) % 16, 0);
/*  65: 56 */         aWorld.isRemote = false;
/*  66: 57 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/*  67:    */       }
/*  68: 59 */       return true;
/*  69:    */     }
/*  70: 61 */     if (aBlock == Blocks.activator_rail)
/*  71:    */     {
/*  72: 62 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/*  73:    */       {
/*  74: 63 */         aWorld.isRemote = true;
/*  75: 64 */         aWorld.setBlock(aX, aY, aZ, aBlock, (aMeta + 8) % 16, 0);
/*  76: 65 */         aWorld.isRemote = false;
/*  77: 66 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/*  78:    */       }
/*  79: 68 */       return true;
/*  80:    */     }
/*  81: 70 */     if ((aBlock == Blocks.log) || (aBlock == Blocks.log2) || (aBlock == Blocks.hay_block))
/*  82:    */     {
/*  83: 71 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts))) {
/*  84: 72 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 4) % 12, 3);
/*  85:    */       }
/*  86: 74 */       return true;
/*  87:    */     }
/*  88: 76 */     if ((aBlock == Blocks.piston) || (aBlock == Blocks.sticky_piston) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper))
/*  89:    */     {
/*  90: 77 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/*  91:    */       {
/*  92: 78 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 1) % 6, 3);
/*  93: 79 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/*  94:    */       }
/*  95: 81 */       return true;
/*  96:    */     }
/*  97: 83 */     if ((aBlock == Blocks.pumpkin) || (aBlock == Blocks.lit_pumpkin) || (aBlock == Blocks.furnace) || (aBlock == Blocks.lit_furnace) || (aBlock == Blocks.chest) || (aBlock == Blocks.trapped_chest))
/*  98:    */     {
/*  99: 84 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/* 100:    */       {
/* 101: 85 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta - 1) % 4 + 2, 3);
/* 102: 86 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/* 103:    */       }
/* 104: 88 */       return true;
/* 105:    */     }
/* 106: 90 */     if (aBlock == Blocks.hopper)
/* 107:    */     {
/* 108: 91 */       if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool)aItem).doDamage(aStack, this.mCosts)))
/* 109:    */       {
/* 110: 92 */         aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 1) % 6 == 1 ? (aMeta + 1) % 6 : 2, 3);
/* 111: 93 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
/* 112:    */       }
/* 113: 95 */       return true;
/* 114:    */     }
/* 115: 97 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:100 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.softhammer", "Activates and Deactivates Machines");
/* 119:    */   
/* 120:    */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 121:    */   {
/* 122:104 */     aList.add(this.mTooltip);
/* 123:105 */     return aList;
/* 124:    */   }
/* 125:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_SoftHammer
 * JD-Core Version:    0.7.0.1
 */