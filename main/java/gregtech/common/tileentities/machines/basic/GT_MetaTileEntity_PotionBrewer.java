/*   1:    */ package gregtech.common.tileentities.machines.basic;
/*   2:    */ 
/*   3:    */ import gregtech.api.enums.Materials;
/*   4:    */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*   5:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   6:    */ import gregtech.api.interfaces.ITexture;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*   9:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import gregtech.api.util.GT_Utility;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraftforge.fluids.Fluid;
/*  17:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  18:    */ import net.minecraftforge.fluids.FluidStack;
/*  19:    */ 
/*  20:    */ public class GT_MetaTileEntity_PotionBrewer
/*  21:    */   extends GT_MetaTileEntity_BasicMachine
/*  22:    */ {
/*  23:    */   public GT_MetaTileEntity_PotionBrewer(int aID, String aName, String aNameRegional, int aTier)
/*  24:    */   {
/*  25: 21 */     super(aID, aName, aNameRegional, aTier, 1, "Brewing your Drinks", 1, 0, "PotionBrewer.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_POTIONBREWER) });
/*  26:    */   }
/*  27:    */   
/*  28:    */   public GT_MetaTileEntity_PotionBrewer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/*  29:    */   {
/*  30: 25 */     super(aName, aTier, 1, aDescription, aTextures, 1, 0, aGUIName, aNEIName);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  34:    */   {
/*  35: 30 */     return new GT_MetaTileEntity_PotionBrewer(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public GT_Recipe.GT_Recipe_Map getRecipeList()
/*  39:    */   {
/*  40: 35 */     return GT_Recipe.GT_Recipe_Map.sBrewingRecipes;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int checkRecipe()
/*  44:    */   {
/*  45: 40 */     int tCheck = super.checkRecipe();
/*  46: 41 */     if (tCheck != 0) {
/*  47: 41 */       return tCheck;
/*  48:    */     }
/*  49: 43 */     FluidStack aFluid = getFillableStack();
/*  50: 44 */     if ((getDrainableStack() == null) && (aFluid != null) && (getInputAt(0) != null))
/*  51:    */     {
/*  52: 45 */       String tInputName = aFluid.getFluid().getName();
/*  53: 46 */       if (tInputName.startsWith("potion."))
/*  54:    */       {
/*  55: 47 */         tInputName = tInputName.replaceFirst("potion.", "");
/*  56: 48 */         int tFirstDot = tInputName.indexOf('.') + 1;
/*  57: 49 */         String tModifier = tFirstDot <= 0 ? "" : tInputName.substring(tFirstDot);
/*  58: 50 */         if (!tModifier.isEmpty()) {
/*  59: 50 */           tInputName = tInputName.replaceFirst("." + tModifier, "");
/*  60:    */         }
/*  61: 52 */         if (GT_Utility.areStacksEqual(new ItemStack(Items.fermented_spider_eye, 1, 0), getInputAt(0)))
/*  62:    */         {
/*  63: 53 */           if (tInputName.equals("poison")) {
/*  64: 53 */             return setOutput("potion.damage" + tModifier);
/*  65:    */           }
/*  66: 54 */           if (tInputName.equals("health")) {
/*  67: 54 */             return setOutput("potion.damage" + tModifier);
/*  68:    */           }
/*  69: 55 */           if (tInputName.equals("waterbreathing")) {
/*  70: 55 */             return setOutput("potion.damage" + tModifier);
/*  71:    */           }
/*  72: 56 */           if (tInputName.equals("nightvision")) {
/*  73: 56 */             return setOutput("potion.invisibility" + tModifier);
/*  74:    */           }
/*  75: 57 */           if (tInputName.equals("fireresistance")) {
/*  76: 57 */             return setOutput("potion.slowness" + tModifier);
/*  77:    */           }
/*  78: 58 */           if (tInputName.equals("speed")) {
/*  79: 58 */             return setOutput("potion.slowness" + tModifier);
/*  80:    */           }
/*  81: 59 */           if (tInputName.equals("strength")) {
/*  82: 59 */             return setOutput("potion.weakness" + tModifier);
/*  83:    */           }
/*  84: 60 */           if (tInputName.equals("regen")) {
/*  85: 60 */             return setOutput("potion.poison" + tModifier);
/*  86:    */           }
/*  87: 61 */           return setOutput("potion.weakness");
/*  88:    */         }
/*  89: 63 */         if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L), getInputAt(0)))
/*  90:    */         {
/*  91: 64 */           if (!tModifier.startsWith("strong")) {
/*  92: 64 */             return setOutput("potion." + tInputName + ".strong" + (tModifier.isEmpty() ? "" : new StringBuilder().append(".").append(tModifier).toString()));
/*  93:    */           }
/*  94: 65 */           if (tModifier.startsWith("long")) {
/*  95: 65 */             return setOutput("potion." + tInputName + tModifier.replaceFirst("long", ""));
/*  96:    */           }
/*  97: 66 */           return setOutput("potion.thick");
/*  98:    */         }
/*  99: 68 */         if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), getInputAt(0)))
/* 100:    */         {
/* 101: 69 */           if (!tModifier.startsWith("long")) {
/* 102: 69 */             return setOutput("potion." + tInputName + ".long" + (tModifier.isEmpty() ? "" : new StringBuilder().append(".").append(tModifier).toString()));
/* 103:    */           }
/* 104: 70 */           if (tModifier.startsWith("strong")) {
/* 105: 70 */             return setOutput("potion." + tInputName + tModifier.replaceFirst("strong", ""));
/* 106:    */           }
/* 107: 71 */           return setOutput("potion.mundane");
/* 108:    */         }
/* 109: 73 */         if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L), getInputAt(0)))
/* 110:    */         {
/* 111: 74 */           if (!tInputName.endsWith(".splash")) {
/* 112: 74 */             return setOutput("potion." + tInputName + ".splash");
/* 113:    */           }
/* 114: 75 */           return setOutput("potion.mundane");
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118: 79 */     return 0;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private final int setOutput(String aFluidName)
/* 122:    */   {
/* 123: 83 */     this.mOutputFluid = FluidRegistry.getFluidStack(aFluidName, 750);
/* 124: 84 */     if (this.mOutputFluid == null)
/* 125:    */     {
/* 126: 85 */       this.mOutputFluid = FluidRegistry.getFluidStack("potion.mundane", getFillableStack().amount);
/* 127: 86 */       getInputAt(0).stackSize -= 1;
/* 128: 87 */       getFillableStack().amount = 0;
/* 129: 88 */       this.mEUt = (4 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 130: 89 */       this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 131: 90 */       return 2;
/* 132:    */     }
/* 133: 92 */     if (getFillableStack().amount < 750) {
/* 134: 92 */       return 0;
/* 135:    */     }
/* 136: 93 */     getInputAt(0).stackSize -= 1;
/* 137: 94 */     getFillableStack().amount -= 750;
/* 138: 95 */     this.mEUt = (4 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 139: 96 */     this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 140: 97 */     return 2;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 144:    */   {
/* 145:102 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isFluidInputAllowed(FluidStack aFluid)
/* 149:    */   {
/* 150:107 */     return (aFluid.getFluid().getName().startsWith("potion.")) || (super.isFluidInputAllowed(aFluid));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getCapacity()
/* 154:    */   {
/* 155:112 */     return 750;
/* 156:    */   }
/* 157:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_PotionBrewer
 * JD-Core Version:    0.7.0.1
 */