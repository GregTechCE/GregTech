/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.Dyes;
/*   5:    */ import gregtech.api.enums.ItemList;
/*   6:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   7:    */ import gregtech.api.util.GT_LanguageManager;
/*   8:    */ import gregtech.api.util.GT_Utility;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import net.minecraft.block.Block;
/*  14:    */ import net.minecraft.block.BlockColored;
/*  15:    */ import net.minecraft.entity.player.EntityPlayer;
/*  16:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  17:    */ import net.minecraft.init.Blocks;
/*  18:    */ import net.minecraft.init.Items;
/*  19:    */ import net.minecraft.item.Item;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.nbt.NBTTagCompound;
/*  22:    */ import net.minecraft.world.World;
/*  23:    */ import net.minecraftforge.common.util.ForgeDirection;
/*  24:    */ 
/*  25:    */ public class Behaviour_Spray_Color
/*  26:    */   extends Behaviour_None
/*  27:    */ {
/*  28:    */   private final ItemStack mEmpty;
/*  29:    */   private final ItemStack mUsed;
/*  30:    */   private final ItemStack mFull;
/*  31:    */   private final long mUses;
/*  32:    */   private final byte mColor;
/*  33:    */   
/*  34:    */   public Behaviour_Spray_Color(ItemStack aEmpty, ItemStack aUsed, ItemStack aFull, long aUses, int aColor)
/*  35:    */   {
/*  36: 30 */     this.mEmpty = aEmpty;
/*  37: 31 */     this.mUsed = aUsed;
/*  38: 32 */     this.mFull = aFull;
/*  39: 33 */     this.mUses = aUses;
/*  40: 34 */     this.mColor = ((byte)aColor);
/*  41: 35 */     this.mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.paintspray." + this.mColor + ".tooltip", "Can Color things in " + Dyes.get(this.mColor).mName);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  45:    */   {
/*  46: 40 */     if ((aWorld.isRemote) || (aStack.stackSize != 1)) {
/*  47: 40 */       return false;
/*  48:    */     }
/*  49: 42 */     boolean rOutput = false;
/*  50: 44 */     if (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack)) {
/*  51: 44 */       return false;
/*  52:    */     }
/*  53: 46 */     NBTTagCompound tNBT = aStack.getTagCompound();
/*  54: 47 */     if (tNBT == null) {
/*  55: 47 */       tNBT = new NBTTagCompound();
/*  56:    */     }
/*  57: 48 */     long tUses = tNBT.getLong("GT.RemainingPaint");
/*  58: 50 */     if (GT_Utility.areStacksEqual(aStack, this.mFull, true))
/*  59:    */     {
/*  60: 51 */       aStack.func_150996_a(this.mUsed.getItem());
/*  61: 52 */       Items.feather.setDamage(aStack, Items.feather.getDamage(this.mUsed));
/*  62: 53 */       tUses = this.mUses;
/*  63:    */     }
/*  64: 55 */     if ((GT_Utility.areStacksEqual(aStack, this.mUsed, true)) && 
/*  65: 56 */       (colorize(aWorld, aX, aY, aZ, aSide)))
/*  66:    */     {
/*  67: 57 */       GT_Utility.sendSoundToPlayers(aWorld, (String)GregTech_API.sSoundList.get(Integer.valueOf(102)), 1.0F, 1.0F, aX, aY, aZ);
/*  68: 58 */       if (!aPlayer.capabilities.isCreativeMode) {
/*  69: 58 */         tUses -= 1L;
/*  70:    */       }
/*  71: 59 */       rOutput = true;
/*  72:    */     }
/*  73: 62 */     tNBT.removeTag("GT.RemainingPaint");
/*  74: 63 */     if (tUses > 0L) {
/*  75: 63 */       tNBT.setLong("GT.RemainingPaint", tUses);
/*  76:    */     }
/*  77: 64 */     if (tNBT.hasNoTags()) {
/*  78: 64 */       aStack.setTagCompound(null);
/*  79:    */     } else {
/*  80: 64 */       aStack.setTagCompound(tNBT);
/*  81:    */     }
/*  82: 66 */     if (tUses <= 0L) {
/*  83: 67 */       if (this.mEmpty == null)
/*  84:    */       {
/*  85: 68 */         aStack.stackSize -= 1;
/*  86:    */       }
/*  87:    */       else
/*  88:    */       {
/*  89: 70 */         aStack.func_150996_a(this.mEmpty.getItem());
/*  90: 71 */         Items.feather.setDamage(aStack, Items.feather.getDamage(this.mEmpty));
/*  91:    */       }
/*  92:    */     }
/*  93: 74 */     return rOutput;
/*  94:    */   }
/*  95:    */   
/*  96: 77 */   private final Collection<Block> mAllowedVanillaBlocks = Arrays.asList(new Block[] { Blocks.glass, Blocks.glass_pane, Blocks.stained_glass, Blocks.stained_glass_pane, Blocks.carpet, Blocks.hardened_clay, ItemList.TE_Rockwool.getBlock() });
/*  97:    */   private final String mTooltip;
/*  98:    */   
/*  99:    */   private boolean colorize(World aWorld, int aX, int aY, int aZ, int aSide)
/* 100:    */   {
/* 101: 80 */     Block aBlock = aWorld.getBlock(aX, aY, aZ);
/* 102: 81 */     if ((aBlock != Blocks.air) && ((this.mAllowedVanillaBlocks.contains(aBlock)) || ((aBlock instanceof BlockColored))))
/* 103:    */     {
/* 104: 82 */       if (aBlock == Blocks.hardened_clay)
/* 105:    */       {
/* 106: 82 */         aWorld.setBlock(aX, aY, aZ, Blocks.stained_hardened_clay, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);return true;
/* 107:    */       }
/* 108: 83 */       if (aBlock == Blocks.glass_pane)
/* 109:    */       {
/* 110: 83 */         aWorld.setBlock(aX, aY, aZ, Blocks.stained_glass_pane, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);return true;
/* 111:    */       }
/* 112: 84 */       if (aBlock == Blocks.glass)
/* 113:    */       {
/* 114: 84 */         aWorld.setBlock(aX, aY, aZ, Blocks.stained_glass, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);return true;
/* 115:    */       }
/* 116: 85 */       if (aWorld.getBlockMetadata(aX, aY, aZ) == ((this.mColor ^ 0xFFFFFFFF) & 0xF)) {
/* 117: 85 */         return false;
/* 118:    */       }
/* 119: 86 */       aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);
/* 120: 87 */       return true;
/* 121:    */     }
/* 122: 89 */     return aBlock.recolourBlock(aWorld, aX, aY, aZ, ForgeDirection.getOrientation(aSide), (this.mColor ^ 0xFFFFFFFF) & 0xF);
/* 123:    */   }
/* 124:    */   
/* 125: 92 */   private final String mTooltipUses = GT_LanguageManager.addStringLocalization("gt.behaviour.paintspray.uses", "Remaining Uses:");
/* 126: 93 */   private final String mTooltipUnstackable = GT_LanguageManager.addStringLocalization("gt.behaviour.unstackable", "Not usable when stacked!");
/* 127:    */   
/* 128:    */   public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack)
/* 129:    */   {
/* 130: 97 */     aList.add(this.mTooltip);
/* 131: 98 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 132: 99 */     long tRemainingPaint = tNBT == null ? 0L : GT_Utility.areStacksEqual(aStack, this.mFull, true) ? this.mUses : tNBT.getLong("GT.RemainingPaint");
/* 133:100 */     aList.add(this.mTooltipUses + " " + tRemainingPaint);
/* 134:101 */     aList.add(this.mTooltipUnstackable);
/* 135:102 */     return aList;
/* 136:    */   }
/* 137:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Spray_Color
 * JD-Core Version:    0.7.0.1
 */