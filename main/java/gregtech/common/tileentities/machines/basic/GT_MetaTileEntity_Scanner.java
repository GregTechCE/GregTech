/*   1:    */ package gregtech.common.tileentities.machines.basic;
/*   2:    */ 
/*   3:    */ import forestry.api.genetics.AlleleManager;
/*   4:    */ import forestry.api.genetics.IAlleleRegistry;
/*   5:    */ import forestry.api.genetics.IIndividual;
import gregtech.GT_Mod;
/*   6:    */ import gregtech.api.GregTech_API;
/*   7:    */ import gregtech.api.enums.Element;
/*   8:    */ import gregtech.api.enums.GT_Values;
/*   9:    */ import gregtech.api.enums.ItemList;
/*  10:    */ import gregtech.api.enums.Materials;
/*  11:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*  12:    */ import gregtech.api.enums.Textures.BlockIcons;
/*  13:    */ import gregtech.api.interfaces.ITexture;
/*  14:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  15:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*  16:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/*  17:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  18:    */ import gregtech.api.objects.ItemData;
/*  19:    */ import gregtech.api.objects.MaterialStack;
/*  20:    */ import gregtech.api.util.GT_Log;
/*  21:    */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
/*  22:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  23:    */ import gregtech.api.util.GT_Utility;
/*  24:    */ import gregtech.common.items.behaviors.Behaviour_DataOrb;

/*  25:    */ import java.util.Map;

/*  26:    */ import net.minecraft.init.Items;
/*  27:    */ import net.minecraft.item.ItemStack;
/*  28:    */ import net.minecraft.nbt.NBTTagCompound;
/*  29:    */ import net.minecraftforge.fluids.FluidStack;
/*  30:    */ 
/*  31:    */ public class GT_MetaTileEntity_Scanner
/*  32:    */   extends GT_MetaTileEntity_BasicMachine
/*  33:    */ {
/*  34:    */   public GT_MetaTileEntity_Scanner(int aID, String aName, String aNameRegional, int aTier)
/*  35:    */   {
/*  36: 29 */     super(aID, aName, aNameRegional, aTier, 1, "Scans Crops and other things.", 1, 1, "Scanner.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER) });
/*  37:    */   }
/*  38:    */   
/*  39:    */   public GT_MetaTileEntity_Scanner(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/*  40:    */   {
/*  41: 33 */     super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  45:    */   {
/*  46: 38 */     return new GT_MetaTileEntity_Scanner(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int checkRecipe()
/*  50:    */   {
/*  51: 43 */     ItemStack aStack = getInputAt(0);
/*  52: 44 */     if (getOutputAt(0) != null)
/*  53:    */     {
/*  54: 45 */       this.mOutputBlocked += 1;
/*  55:    */     }
/*  56: 46 */     else if ((GT_Utility.isStackValid(aStack)) && (aStack.stackSize > 0))
/*  57:    */     {
/*  58: 47 */       if ((getFillableStack() != null) && (getFillableStack().containsFluid(Materials.Honey.getFluid(100L)))) {
/*  59:    */         try
/*  60:    */         {
/*  61: 49 */           Object tIndividual = AlleleManager.alleleRegistry.getIndividual(aStack);
/*  62: 50 */           if (tIndividual != null)
/*  63:    */           {
/*  64: 51 */             if (((IIndividual)tIndividual).analyze())
/*  65:    */             {
/*  66: 52 */               getFillableStack().amount -= 100;
/*  67: 53 */               this.mOutputItems[0] = GT_Utility.copy(new Object[] { aStack });
/*  68: 54 */               aStack.stackSize = 0;
/*  69: 55 */               NBTTagCompound tNBT = new NBTTagCompound();
/*  70: 56 */               ((IIndividual)tIndividual).writeToNBT(tNBT);
/*  71: 57 */               this.mOutputItems[0].setTagCompound(tNBT);
/*  72: 58 */               this.mMaxProgresstime = (500 / (1 << this.mTier - 1));
/*  73: 59 */               this.mEUt = (2 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/*  74: 60 */               return 2;
/*  75:    */             }
/*  76: 62 */             this.mOutputItems[0] = GT_Utility.copy(new Object[] { aStack });
/*  77: 63 */             aStack.stackSize = 0;
/*  78: 64 */             this.mMaxProgresstime = 1;
/*  79: 65 */             this.mEUt = 1;
/*  80: 66 */             return 2;
/*  81:    */           }
/*  82:    */         }
/*  83:    */         catch (Throwable e)
/*  84:    */         {
/*  85: 69 */           if (GT_Values.D1) {
/*  86: 69 */             e.printStackTrace(GT_Log.err);
/*  87:    */           }
/*  88:    */         }
/*  89:    */       }
/*  90: 72 */       if (ItemList.IC2_Crop_Seeds.isStackEqual(aStack, true, true))
/*  91:    */       {
/*  92: 73 */         NBTTagCompound tNBT = aStack.getTagCompound();
/*  93: 74 */         if (tNBT == null) {
/*  94: 74 */           tNBT = new NBTTagCompound();
/*  95:    */         }
/*  96: 75 */         if (tNBT.getByte("scan") < 4)
/*  97:    */         {
/*  98: 76 */           tNBT.setByte("scan", (byte)4);
/*  99: 77 */           this.mMaxProgresstime = (160 / (1 << this.mTier - 1));
/* 100: 78 */           this.mEUt = (8 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 101:    */         }
/* 102:    */         else
/* 103:    */         {
/* 104: 80 */           this.mMaxProgresstime = 1;
/* 105: 81 */           this.mEUt = 1;
/* 106:    */         }
/* 107: 83 */         aStack.stackSize -= 1;
/* 108: 84 */         this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { aStack });
/* 109: 85 */         this.mOutputItems[0].setTagCompound(tNBT);
/* 110: 86 */         return 2;
/* 111:    */       }
/* 112: 88 */       if (ItemList.Tool_DataOrb.isStackEqual(getSpecialSlot(), false, true))
/* 113:    */       {
/* 114: 89 */         if (ItemList.Tool_DataOrb.isStackEqual(aStack, false, true))
/* 115:    */         {
/* 116: 90 */           aStack.stackSize -= 1;
/* 117: 91 */           this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { getSpecialSlot() });
/* 118: 92 */           this.mMaxProgresstime = (512 / (1 << this.mTier - 1));
/* 119: 93 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 120: 94 */           return 2;
/* 121:    */         }
/* 122: 96 */         ItemData tData = GT_OreDictUnificator.getAssociation(aStack);
/* 123: 97 */         if ((tData != null) && ((tData.mPrefix == OrePrefixes.dust) || (tData.mPrefix == OrePrefixes.cell)) && (tData.mMaterial.mMaterial.mElement != null) && (!tData.mMaterial.mMaterial.mElement.mIsIsotope) && (tData.mMaterial.mMaterial != Materials.Magic) && (tData.mMaterial.mMaterial.getMass() > 0L))
/* 124:    */         {
/* 125: 98 */           getSpecialSlot().stackSize -= 1;
/* 126: 99 */           aStack.stackSize -= 1;
/* 127:    */           
/* 128:101 */           this.mOutputItems[0] = ItemList.Tool_DataOrb.get(1L, new Object[0]);
/* 129:102 */           Behaviour_DataOrb.setDataTitle(this.mOutputItems[0], "Elemental-Scan");
/* 130:103 */           Behaviour_DataOrb.setDataName(this.mOutputItems[0], tData.mMaterial.mMaterial.mElement.name());
/* 132:105 */           this.mMaxProgresstime = ((int)(tData.mMaterial.mMaterial.getMass() * 8192L / (1 << this.mTier - 1)));
/* 133:106 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 134:107 */           return 2;
/* 135:    */         }
/* 136:    */       }
/* 137:110 */       if (ItemList.Tool_DataStick.isStackEqual(getSpecialSlot(), false, true))
/* 138:    */       {
/* 139:111 */         if (ItemList.Tool_DataStick.isStackEqual(aStack, false, true))
/* 140:    */         {
/* 141:112 */           aStack.stackSize -= 1;
/* 142:113 */           this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { getSpecialSlot() });
/* 143:114 */           this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 144:115 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 145:116 */           return 2;
/* 146:    */         }
/* 147:118 */         if (aStack.getItem() == Items.written_book)
/* 148:    */         {
/* 149:119 */           getSpecialSlot().stackSize -= 1;
/* 150:120 */           aStack.stackSize -= 1;
/* 151:    */           
/* 152:122 */           this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { getSpecialSlot() });
/* 153:123 */           this.mOutputItems[0].setTagCompound(aStack.getTagCompound());
/* 154:124 */           this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 155:125 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 156:126 */           return 2;
/* 157:    */         }
/* 158:128 */         if (aStack.getItem() == Items.filled_map)
/* 159:    */         {
/* 160:129 */           getSpecialSlot().stackSize -= 1;
/* 161:130 */           aStack.stackSize -= 1;
/* 162:    */           
/* 163:132 */           this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { getSpecialSlot() });
/* 164:133 */           this.mOutputItems[0].setTagCompound(GT_Utility.getNBTContainingShort(new NBTTagCompound(), "map_id", (short)aStack.getItemDamage()));
/* 165:134 */           this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 166:135 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 167:136 */           return 2;
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:140 */     return 0;
/* 172:    */   }

@Override
public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
	if (mProgresstime>=(mMaxProgresstime-1)) {try{
		if(this.mOutputItems[0].getUnlocalizedName().equals("gt.metaitem.01.32707")){
		GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "scanning");}}catch (Exception e){}
	}
	super.onPostTick(aBaseMetaTileEntity, aTick);
}

/* 173:    */   
/* 174:    */   public GT_Recipe.GT_Recipe_Map getRecipeList()
/* 175:    */   {
/* 176:145 */     return GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getCapacity()
/* 180:    */   {
/* 181:150 */     return 1000;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 185:    */   {
/* 186:155 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
/* 190:    */   {
/* 191:160 */     super.startSoundLoop(aIndex, aX, aY, aZ);
/* 192:161 */     if (aIndex == 1) {
/* 193:161 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(212)), 10, 1.0F, aX, aY, aZ);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void startProcess()
/* 198:    */   {
/* 199:166 */     sendLoopStart((byte)1);
/* 200:    */   }
/* 201:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Scanner
 * JD-Core Version:    0.7.0.1
 */