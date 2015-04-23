/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Materials;
/*   5:    */ import gregtech.api.enums.OrePrefixes;
/*   6:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   7:    */ import gregtech.api.items.GT_MetaGenerated_Tool;
/*   8:    */ import gregtech.api.objects.ItemData;
/*   9:    */ import gregtech.api.objects.MaterialStack;
/*  10:    */ import gregtech.api.util.GT_LanguageManager;
/*  11:    */ import gregtech.api.util.GT_ModHandler;
/*  12:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  13:    */ import gregtech.api.util.GT_Utility;
/*  14:    */ import gregtech.common.blocks.GT_Block_Ores;
/*  15:    */ import gregtech.common.blocks.GT_TileEntity_Ores;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Random;
/*  19:    */ import net.minecraft.block.Block;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.init.Blocks;
/*  22:    */ import net.minecraft.item.ItemStack;
/*  23:    */ import net.minecraft.tileentity.TileEntity;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  26:    */ import net.minecraftforge.fluids.IFluidBlock;
/*  27:    */ 
/*  28:    */ public class Behaviour_Prospecting
/*  29:    */   extends Behaviour_None
/*  30:    */ {
/*  31:    */   private final int mVanillaCosts;
/*  32:    */   private final int mEUCosts;
/*  33:    */   
/*  34:    */   public Behaviour_Prospecting(int aVanillaCosts, int aEUCosts)
/*  35:    */   {
/*  36: 31 */     this.mVanillaCosts = aVanillaCosts;
/*  37: 32 */     this.mEUCosts = aEUCosts;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  41:    */   {
/*  42: 37 */     if (aWorld.isRemote) {
/*  43: 38 */       return false;
/*  44:    */     }
/*  45: 40 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/*  46: 41 */     if (aBlock == null) {
/*  47: 41 */       return false;
/*  48:    */     }
/*  49: 42 */     byte aMeta = (byte)aWorld.getBlockMetadata(aX, aY, aZ);
/*  50:    */     
/*  51:    */ 
/*  52: 45 */     ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(aBlock, 1, aMeta));
/*  53: 46 */     if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore")))
/*  54:    */     {
/*  55: 47 */       GT_Utility.sendChatToPlayer(aPlayer, "This is " + tAssotiation.mMaterial.mMaterial.mDefaultLocalName + " Ore.");
/*  56: 48 */       GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(1)), 1.0F, -1.0F, aX, aY, aZ);
/*  57: 49 */       return true;
/*  58:    */     }
/*  59: 52 */     if ((aBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.stone)) || (aBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTech_API.sBlockGranites)) || (aBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.netherrack)) || (aBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.end_stone)))
/*  60:    */     {
/*  61: 53 */       if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer))
/*  62:    */       {
/*  63: 54 */         GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(1)), 1.0F, -1.0F, aX, aY, aZ);
/*  64: 55 */         int tX = aX;int tY = aY;int tZ = aZ;int tMetaID = 0;int tQuality = (aItem instanceof GT_MetaGenerated_Tool) ? ((GT_MetaGenerated_Tool)aItem).getHarvestLevel(aStack, "") : 0;
/*  65:    */         
/*  66: 57 */         int i = 0;
/*  67: 57 */         for (int j = 6 + tQuality; i < j; i++)
/*  68:    */         {
/*  69: 58 */           tX -= ForgeDirection.getOrientation(aSide).offsetX;
/*  70: 59 */           tY -= ForgeDirection.getOrientation(aSide).offsetY;
/*  71: 60 */           tZ -= ForgeDirection.getOrientation(aSide).offsetZ;
/*  72:    */           
/*  73: 62 */           Block tBlock = aWorld.getBlock(tX, tY, tZ);
/*  74: 63 */           if ((tBlock == Blocks.lava) || (tBlock == Blocks.flowing_lava))
/*  75:    */           {
/*  76: 64 */             GT_Utility.sendChatToPlayer(aPlayer, "There is Lava behind this Rock.");
/*  77: 65 */             break;
/*  78:    */           }
/*  79: 67 */           if ((tBlock == Blocks.water) || (tBlock == Blocks.flowing_water) || ((tBlock instanceof IFluidBlock)))
/*  80:    */           {
/*  81: 68 */             GT_Utility.sendChatToPlayer(aPlayer, "There is a Liquid behind this Rock.");
/*  82: 69 */             break;
/*  83:    */           }
/*  84: 71 */           if ((tBlock == Blocks.monster_egg) || (!GT_Utility.hasBlockHitBox(aWorld, tX, tY, tZ)))
/*  85:    */           {
/*  86: 72 */             GT_Utility.sendChatToPlayer(aPlayer, "There is an Air Pocket behind this Rock.");
/*  87: 73 */             break;
/*  88:    */           }
/*  89: 75 */           if (tBlock != aBlock)
/*  90:    */           {
/*  91: 76 */             if (i >= 4) {
/*  92:    */               break;
/*  93:    */             }
/*  94: 76 */             GT_Utility.sendChatToPlayer(aPlayer, "Material is changing behind this Rock."); break;
/*  95:    */           }
/*  96:    */         }
/*  97: 81 */         Random tRandom = new Random(aX ^ aY ^ aZ ^ aSide);
/*  98: 82 */         i = 0;
/*  99: 82 */         for (int j = 9 + 2 * tQuality; i < j; i++)
/* 100:    */         {
/* 101: 83 */           tX = aX - 4 - tQuality + tRandom.nextInt(j);
/* 102: 84 */           tY = aY - 4 - tQuality + tRandom.nextInt(j);
/* 103: 85 */           tZ = aZ - 4 - tQuality + tRandom.nextInt(j);
/* 104: 86 */           Block tBlock = aWorld.getBlock(tX, tY, tZ);
/* 105: 87 */           if ((tBlock instanceof GT_Block_Ores))
/* 106:    */           {
/* 107: 88 */             TileEntity tTileEntity = aWorld.getTileEntity(tX, tY, tZ);
/* 108: 89 */             if ((tTileEntity instanceof GT_TileEntity_Ores))
/* 109:    */             {
/* 110: 90 */               Materials tMaterial = GregTech_API.sGeneratedMaterials[(((GT_TileEntity_Ores)tTileEntity).mMetaData % 1000)];
/* 111: 91 */               if ((tMaterial != null) && (tMaterial != Materials._NULL))
/* 112:    */               {
/* 113: 92 */                 GT_Utility.sendChatToPlayer(aPlayer, "Found traces of " + tMaterial.mDefaultLocalName + " Ore.");
/* 114: 93 */                 return true;
/* 115:    */               }
/* 116:    */             }
/* 117:    */           }
/* 118:    */           else
/* 119:    */           {
/* 120: 97 */             tMetaID = aWorld.getBlockMetadata(tX, tY, tZ);
/* 121: 98 */             tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
/* 122: 99 */             if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore")))
/* 123:    */             {
/* 124:100 */               GT_Utility.sendChatToPlayer(aPlayer, "Found traces of " + tAssotiation.mMaterial.mMaterial.mDefaultLocalName + " Ore.");
/* 125:101 */               return true;
/* 126:    */             }
/* 127:    */           }
/* 128:    */         }
/* 129:105 */         GT_Utility.sendChatToPlayer(aPlayer, "No Ores found.");
/* 130:    */       }
/* 131:107 */       return true;
/* 132:    */     }
/* 133:109 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:112 */   private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.prospecting", "Usable for Prospecting");
/* 137:    */   
/* 138:    */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 139:    */   {
/* 140:116 */     aList.add(this.mTooltip);
/* 141:117 */     return aList;
/* 142:    */   }
/* 143:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Prospecting
 * JD-Core Version:    0.7.0.1
 */