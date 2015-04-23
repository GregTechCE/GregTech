/*   1:    */ package gregtech.common.items.behaviors;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
/*   4:    */ import gregtech.api.enums.GT_Values;
/*   5:    */ import gregtech.api.interfaces.IItemBehaviour;
/*   6:    */ import gregtech.api.interfaces.internal.IGT_Mod;
/*   7:    */ import gregtech.api.items.GT_MetaBase_Item;
/*   8:    */ import gregtech.api.util.GT_Utility;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.nbt.NBTTagList;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class Behaviour_Sonictron
/*  17:    */   extends Behaviour_None
/*  18:    */ {
/*  19: 16 */   public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Sonictron();
/*  20:    */   
/*  21:    */   public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ)
/*  22:    */   {
/*  23: 20 */     if ((!aWorld.isRemote) && (aWorld.getBlock(aX, aY, aZ) == GregTech_API.sBlockMachines) && (aWorld.getBlockMetadata(aX, aY, aZ) == 6)) {}
/*  24: 36 */     setCurrentIndex(aStack, -1);
/*  25: 37 */     return false;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ItemStack onItemRightClick(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, EntityPlayer aPlayer)
/*  29:    */   {
/*  30: 42 */     setCurrentIndex(aStack, 0);
/*  31: 43 */     return aStack;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void onUpdate(GT_MetaBase_Item aItem, ItemStack aStack, World aWorld, Entity aPlayer, int aTimer, boolean aIsInHand)
/*  35:    */   {
/*  36: 48 */     int tTickTimer = getTickTimer(aStack);
/*  37: 49 */     int tCurrentIndex = getCurrentIndex(aStack);
/*  38: 51 */     if ((tTickTimer++ % 2 == 0) && (tCurrentIndex > -1))
/*  39:    */     {
/*  40: 52 */       ItemStack[] tInventory = getNBTInventory(aStack);
/*  41: 53 */       GT_Values.GT.doSonictronSound(tInventory[tCurrentIndex], aPlayer.worldObj, aPlayer.posX, aPlayer.posY, aPlayer.posZ);
/*  42: 54 */       tCurrentIndex++;
/*  43: 54 */       if (tCurrentIndex > 63) {
/*  44: 54 */         tCurrentIndex = -1;
/*  45:    */       }
/*  46:    */     }
/*  47: 57 */     setTickTimer(aStack, tTickTimer);
/*  48: 58 */     setCurrentIndex(aStack, tCurrentIndex);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static int getCurrentIndex(ItemStack aStack)
/*  52:    */   {
/*  53: 62 */     NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
/*  54: 63 */     if (tNBTTagCompound == null) {
/*  55: 63 */       tNBTTagCompound = new NBTTagCompound();
/*  56:    */     }
/*  57: 64 */     return tNBTTagCompound.getInteger("mCurrentIndex");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static int getTickTimer(ItemStack aStack)
/*  61:    */   {
/*  62: 68 */     NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
/*  63: 69 */     if (tNBTTagCompound == null) {
/*  64: 69 */       tNBTTagCompound = new NBTTagCompound();
/*  65:    */     }
/*  66: 70 */     return tNBTTagCompound.getInteger("mTickTimer");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static NBTTagCompound setCurrentIndex(ItemStack aStack, int aIndex)
/*  70:    */   {
/*  71: 74 */     NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
/*  72: 75 */     if (tNBTTagCompound == null) {
/*  73: 75 */       tNBTTagCompound = new NBTTagCompound();
/*  74:    */     }
/*  75: 76 */     tNBTTagCompound.setInteger("mCurrentIndex", aIndex);
/*  76: 77 */     return tNBTTagCompound;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static NBTTagCompound setTickTimer(ItemStack aStack, int aTime)
/*  80:    */   {
/*  81: 81 */     NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
/*  82: 82 */     if (tNBTTagCompound == null) {
/*  83: 82 */       tNBTTagCompound = new NBTTagCompound();
/*  84:    */     }
/*  85: 83 */     tNBTTagCompound.setInteger("mTickTimer", aTime);
/*  86: 84 */     return tNBTTagCompound;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static ItemStack[] getNBTInventory(ItemStack aStack)
/*  90:    */   {
/*  91: 88 */     ItemStack[] tInventory = new ItemStack[64];
/*  92: 89 */     NBTTagCompound tNBT = aStack.getTagCompound();
/*  93: 90 */     if (tNBT == null) {
/*  94: 90 */       return tInventory;
/*  95:    */     }
/*  96: 92 */     NBTTagList tNBT_ItemList = tNBT.getTagList("Inventory", 10);
/*  97: 93 */     for (int i = 0; i < tNBT_ItemList.tagCount(); i++)
/*  98:    */     {
/*  99: 94 */       NBTTagCompound tag = tNBT_ItemList.getCompoundTagAt(i);
/* 100: 95 */       byte slot = tag.getByte("Slot");
/* 101: 96 */       if ((slot >= 0) && (slot < tInventory.length)) {
/* 102: 97 */         tInventory[slot] = GT_Utility.loadItem(tag);
/* 103:    */       }
/* 104:    */     }
/* 105:100 */     return tInventory;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static NBTTagCompound setNBTInventory(ItemStack aStack, ItemStack[] aInventory)
/* 109:    */   {
/* 110:104 */     NBTTagCompound tNBT = aStack.getTagCompound();
/* 111:105 */     if (tNBT == null) {
/* 112:105 */       tNBT = new NBTTagCompound();
/* 113:    */     }
/* 114:107 */     NBTTagList tNBT_ItemList = new NBTTagList();
/* 115:108 */     for (int i = 0; i < aInventory.length; i++)
/* 116:    */     {
/* 117:109 */       ItemStack stack = aInventory[i];
/* 118:110 */       if (stack != null)
/* 119:    */       {
/* 120:111 */         NBTTagCompound tag = new NBTTagCompound();
/* 121:112 */         tag.setByte("Slot", (byte)i);
/* 122:113 */         stack.writeToNBT(tag);
/* 123:114 */         tNBT_ItemList.appendTag(tag);
/* 124:    */       }
/* 125:    */     }
/* 126:117 */     tNBT.setTag("Inventory", tNBT_ItemList);
/* 127:118 */     aStack.setTagCompound(tNBT);
/* 128:119 */     return tNBT;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static void copyInventory(ItemStack[] aInventory, ItemStack[] aNewContent, int aIndexlength)
/* 132:    */   {
/* 133:123 */     for (int i = 0; i < aIndexlength; i++) {
/* 134:124 */       if (aNewContent[i] == null) {
/* 135:125 */         aInventory[i] = null;
/* 136:    */       } else {
/* 137:127 */         aInventory[i] = GT_Utility.copy(new Object[] { aNewContent[i] });
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.behaviors.Behaviour_Sonictron
 * JD-Core Version:    0.7.0.1
 */