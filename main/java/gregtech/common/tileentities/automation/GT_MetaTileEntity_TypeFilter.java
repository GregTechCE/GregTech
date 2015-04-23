/*   1:    */ package gregtech.common.tileentities.automation;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.interfaces.ITexture;
/*   6:    */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Buffer;
/*   9:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  10:    */ import gregtech.api.util.GT_Utility;
/*  11:    */ import gregtech.common.gui.GT_Container_TypeFilter;
/*  12:    */ import gregtech.common.gui.GT_GUIContainer_TypeFilter;

/*  13:    */ import java.util.ArrayList;

/*  14:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ 
/*  18:    */ public class GT_MetaTileEntity_TypeFilter
/*  19:    */   extends GT_MetaTileEntity_Buffer
/*  20:    */ {
/*  21: 19 */   public boolean bNBTAllowed = false;
/*  22: 19 */   public boolean bInvertFilter = false;
/*  23: 20 */   public int mRotationIndex = 0;
/*  24: 21 */   public OrePrefixes mPrefix = OrePrefixes.ore;
/*  25:    */   
/*  26:    */   public GT_MetaTileEntity_TypeFilter(int aID, String aName, String aNameRegional, int aTier)
/*  27:    */   {
/*  28: 24 */     super(aID, aName, aNameRegional, aTier, 11, "Filtering incoming Items by Type");
/*  29:    */   }
/*  30:    */   
/*  31:    */   public GT_MetaTileEntity_TypeFilter(String aName, int aTier, int aInvSlotCount, String aDescription, ITexture[][][] aTextures)
/*  32:    */   {
/*  33: 28 */     super(aName, aTier, aInvSlotCount, aDescription, aTextures);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  37:    */   {
/*  38: 33 */     return new GT_MetaTileEntity_TypeFilter(this.mName, this.mTier, this.mInventory.length, this.mDescription, this.mTextures);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ITexture getOverlayIcon()
/*  42:    */   {
/*  43: 38 */     return new GT_RenderedTexture(Textures.BlockIcons.AUTOMATION_TYPEFILTER);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isValidSlot(int aIndex)
/*  47:    */   {
/*  48: 41 */     return aIndex < 9;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  52:    */   {
/*  53: 45 */     return new GT_Container_TypeFilter(aPlayerInventory, aBaseMetaTileEntity);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  57:    */   {
/*  58: 50 */     return new GT_GUIContainer_TypeFilter(aPlayerInventory, aBaseMetaTileEntity);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void clickTypeIcon(boolean aRightClick)
/*  62:    */   {
/*  63: 54 */     if (getBaseMetaTileEntity().isServerSide())
/*  64:    */     {
/*  65: 55 */       for (int i = 0; i < OrePrefixes.values().length; i++) {
/*  66: 56 */         if (this.mPrefix == OrePrefixes.values()[i]) {
/*  67: 57 */           for (this.mPrefix = null; this.mPrefix == null; this.mPrefix = OrePrefixes.values()[i])
/*  68:    */           {
/*  70: 59 */             if (aRightClick)
/*  71:    */             {
/*  72: 60 */               i--;
/*  73: 60 */               if (i < 0) {
/*  74: 60 */                 i = OrePrefixes.values().length - 1;
/*  75:    */               }
/*  76:    */             }
/*  77:    */             else
/*  78:    */             {
/*  79: 62 */               i++;
/*  80: 62 */               if (i >= OrePrefixes.values().length) {
/*  81: 62 */                 i = 0;
/*  82:    */               }
/*  83:    */             }
/*  84: 64 */             if ((OrePrefixes.values()[i].mPrefixedItems.isEmpty()) || (OrePrefixes.values()[i].mPrefixInto != OrePrefixes.values()[i])) {
/*  85:    */           
/*  86:    */             }
/*  87:    */           }
/*  88:    */         }
/*  89:    */       }
/*  90: 69 */       this.mRotationIndex = 0;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick)
/*  95:    */   {
/*  96: 75 */     super.onPreTick(aBaseMetaTileEntity, aTick);
/*  97: 76 */     if ((getBaseMetaTileEntity().isServerSide()) && (aTick % 8L == 0L)) {
/*  98: 77 */       if (this.mPrefix.mPrefixedItems.isEmpty())
/*  99:    */       {
/* 100: 78 */         this.mInventory[9] = null;
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104: 80 */         Object[] tmp63_60 = new Object[1]; int tmp90_89 = ((this.mRotationIndex + 1) % this.mPrefix.mPrefixedItems.size());this.mRotationIndex = tmp90_89;tmp63_60[0] = this.mPrefix.mPrefixedItems.get(tmp90_89);this.mInventory[9] = GT_Utility.copyAmount(1L, tmp63_60);
/* 105: 81 */         if (this.mInventory[9].getItemDamage() == 32767) {
/* 106: 81 */           this.mInventory[9].setItemDamage(0);
/* 107:    */         }
/* 108: 82 */         this.mInventory[9].setStackDisplayName(this.mPrefix.toString());
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void saveNBTData(NBTTagCompound aNBT)
/* 114:    */   {
/* 115: 89 */     super.saveNBTData(aNBT);
/* 116: 90 */     aNBT.setString("mPrefix", this.mPrefix.toString());
/* 117: 91 */     aNBT.setBoolean("bInvertFilter", this.bInvertFilter);
/* 118: 92 */     aNBT.setBoolean("bNBTAllowed", this.bNBTAllowed);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void loadNBTData(NBTTagCompound aNBT)
/* 122:    */   {
/* 123: 97 */     super.loadNBTData(aNBT);
/* 124: 98 */     this.mPrefix = OrePrefixes.getPrefix(aNBT.getString("mPrefix"), this.mPrefix);
/* 125: 99 */     this.bInvertFilter = aNBT.getBoolean("bInvertFilter");
/* 126:100 */     this.bNBTAllowed = aNBT.getBoolean("bNBTAllowed");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 130:    */   {
/* 131:105 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && ((this.bNBTAllowed) || (!aStack.hasTagCompound())) && (this.mPrefix.contains(aStack) != this.bInvertFilter);
/* 132:    */   }
/* 133:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.automation.GT_MetaTileEntity_TypeFilter
 * JD-Core Version:    0.7.0.1
 */