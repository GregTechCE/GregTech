/*   1:    */ package gregtech.common.tileentities.machines.steam;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
/*   4:    */ import gregtech.api.enums.Textures.BlockIcons;
/*   5:    */ import gregtech.api.gui.GT_GUIContainer_BasicMachine;
/*   6:    */ import gregtech.api.interfaces.ITexture;
/*   7:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   8:    */ import gregtech.api.metatileentity.MetaTileEntity;
/*   9:    */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_Bronze;
/*  10:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  11:    */ import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import gregtech.api.util.GT_Utility;

/*  14:    */ import java.util.Map;

/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ 
/*  17:    */ public class GT_MetaTileEntity_AlloySmelter_Bronze
/*  18:    */   extends GT_MetaTileEntity_BasicMachine_Bronze
/*  19:    */ {
/*  20:    */   public GT_MetaTileEntity_AlloySmelter_Bronze(int aID, String aName, String aNameRegional)
/*  21:    */   {
/*  22: 19 */     super(aID, aName, aNameRegional, "Combination Smelter", 2, 1, true);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public GT_MetaTileEntity_AlloySmelter_Bronze(String aName, String aDescription, ITexture[][][] aTextures)
/*  26:    */   {
/*  27: 23 */     super(aName, aDescription, aTextures, 2, 1, true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/*  31:    */   {
/*  32: 28 */     return new GT_MetaTileEntity_AlloySmelter_Bronze(this.mName, this.mDescription, this.mTextures);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
/*  36:    */   {
/*  37: 33 */     return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "BronzeAlloySmelter.png", GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes.mUnlocalizedName);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int checkRecipe()
/*  41:    */   {
/*  42: 38 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[1], null, getAllInputs());
/*  43: 39 */     if ((tRecipe != null) && (canOutput(tRecipe.mOutputs)) && (tRecipe.isRecipeInputEqual(true, null, getAllInputs())))
/*  44:    */     {
/*  45: 40 */       this.mOutputItems[0] = tRecipe.getOutput(0);
/*  46: 41 */       if (tRecipe.mEUt <= 16)
/*  47:    */       {
/*  48: 42 */         this.mEUt = tRecipe.mEUt;
/*  49: 43 */         this.mMaxProgresstime = (tRecipe.mDuration * 2);
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53: 45 */         this.mEUt = tRecipe.mEUt;
/*  54: 46 */         this.mMaxProgresstime = (tRecipe.mDuration * 2);
/*  55:    */       }
/*  56: 48 */       return 2;
/*  57:    */     }
/*  58: 50 */     return 0;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void startSoundLoop(byte aIndex, double aX, double aY, double aZ)
/*  62:    */   {
/*  63: 55 */     super.startSoundLoop(aIndex, aX, aY, aZ);
/*  64: 56 */     if (aIndex == 1) {
/*  65: 56 */       GT_Utility.doSoundAtClient((String)GregTech_API.sSoundList.get(Integer.valueOf(208)), 10, 1.0F, aX, aY, aZ);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void startProcess()
/*  70:    */   {
/*  71: 61 */     sendLoopStart((byte)1);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ITexture[] getSideFacingActive(byte aColor)
/*  75:    */   {
/*  76: 66 */     return new ITexture[] { super.getSideFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_ALLOY_SMELTER_ACTIVE) };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public ITexture[] getSideFacingInactive(byte aColor)
/*  80:    */   {
/*  81: 71 */     return new ITexture[] { super.getSideFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_STEAM_ALLOY_SMELTER) };
/*  82:    */   }
/*  83:    */   
/*  84:    */   public ITexture[] getFrontFacingActive(byte aColor)
/*  85:    */   {
/*  86: 76 */     return new ITexture[] { super.getFrontFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_ALLOY_SMELTER_ACTIVE) };
/*  87:    */   }
/*  88:    */   
/*  89:    */   public ITexture[] getFrontFacingInactive(byte aColor)
/*  90:    */   {
/*  91: 81 */     return new ITexture[] { super.getFrontFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_STEAM_ALLOY_SMELTER) };
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ITexture[] getTopFacingActive(byte aColor)
/*  95:    */   {
/*  96: 86 */     return new ITexture[] { super.getTopFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_ALLOY_SMELTER_ACTIVE) };
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ITexture[] getTopFacingInactive(byte aColor)
/* 100:    */   {
/* 101: 91 */     return new ITexture[] { super.getTopFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_STEAM_ALLOY_SMELTER) };
/* 102:    */   }
/* 103:    */   
/* 104:    */   public ITexture[] getBottomFacingActive(byte aColor)
/* 105:    */   {
/* 106: 96 */     return new ITexture[] { super.getBottomFacingActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_ALLOY_SMELTER_ACTIVE) };
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ITexture[] getBottomFacingInactive(byte aColor)
/* 110:    */   {
/* 111:101 */     return new ITexture[] { super.getBottomFacingInactive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_ALLOY_SMELTER) };
/* 112:    */   }
/* 113:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.steam.GT_MetaTileEntity_AlloySmelter_Bronze
 * JD-Core Version:    0.7.0.1
 */