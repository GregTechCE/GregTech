/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
/*  6:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  7:   */ import gregtech.api.interfaces.ITexture;
/*  8:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  9:   */ import gregtech.api.metatileentity.MetaTileEntity;
/* 10:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/* 11:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 12:   */ import gregtech.api.util.GT_Config;
/* 13:   */ import net.minecraftforge.fluids.FluidStack;
/* 14:   */ 
/* 15:   */ public class GT_MetaTileEntity_Massfabricator
/* 16:   */   extends GT_MetaTileEntity_BasicMachine
/* 17:   */ {
/* 18:17 */   public static int sUUAperUUM = 1;
/* 19:17 */   public static int sUUASpeedBonus = 4;
/* 20:17 */   public static int sDurationMultiplier = 3215;
/* 21:18 */   public static boolean sRequiresUUA = false;
/* 22:   */   
/* 23:   */   public GT_MetaTileEntity_Massfabricator(int aID, String aName, String aNameRegional, int aTier)
/* 24:   */   {
/* 25:21 */     super(aID, aName, aNameRegional, aTier, 1, "UUM = Matter * Fabrication Squared", 1, 1, "Massfabricator.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_MASSFAB_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_MASSFAB), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_MASSFAB_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_MASSFAB), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_MASSFAB_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_MASSFAB), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_MASSFAB_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_MASSFAB) });
/* 26:   */   }
/* 27:   */   
/* 28:   */   public GT_MetaTileEntity_Massfabricator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 29:   */   {
/* 30:25 */     super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 34:   */   {
/* 35:30 */     return new GT_MetaTileEntity_Massfabricator(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void onConfigLoad(GT_Config aConfig)
/* 39:   */   {
/* 40:35 */     super.onConfigLoad(aConfig);
/* 41:36 */     sDurationMultiplier = aConfig.get(ConfigCategories.machineconfig, "Massfabricator.UUM_Duration_Multiplier", sDurationMultiplier);
/* 42:37 */     sUUAperUUM = aConfig.get(ConfigCategories.machineconfig, "Massfabricator.UUA_per_UUM", sUUAperUUM);
/* 43:38 */     sUUASpeedBonus = aConfig.get(ConfigCategories.machineconfig, "Massfabricator.UUA_Speed_Bonus", sUUASpeedBonus);
/* 44:39 */     sRequiresUUA = aConfig.get(ConfigCategories.machineconfig, "Massfabricator.UUA_Requirement", sRequiresUUA);
/* 45:40 */     Materials.UUAmplifier.mChemicalFormula = ("Mass Fabricator Eff/Speed Bonus: x" + sUUASpeedBonus);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int checkRecipe()
/* 49:   */   {
/* 50:45 */     FluidStack tFluid = getDrainableStack();
/* 51:46 */     if ((tFluid == null) || (tFluid.amount < getCapacity()))
/* 52:   */     {
/* 53:47 */       this.mOutputFluid = Materials.UUMatter.getFluid(1L);
/* 54:48 */       this.mEUt = ((int)gregtech.api.enums.GT_Values.V[this.mTier]);
/* 55:49 */       this.mMaxProgresstime = (sDurationMultiplier / (1 << this.mTier - 1));
/* 56:51 */       if (((tFluid = getFillableStack()) != null) && (tFluid.amount >= sUUAperUUM) && (tFluid.isFluidEqual(Materials.UUAmplifier.getFluid(1L))))
/* 57:   */       {
/* 58:52 */         tFluid.amount -= sUUAperUUM;
/* 59:53 */         this.mMaxProgresstime /= sUUASpeedBonus;
/* 60:54 */         return 2;
/* 61:   */       }
/* 62:56 */       return (sRequiresUUA) || (ItemList.Circuit_Integrated.isStackEqual(getInputAt(0), true, true)) ? 1 : 2;
/* 63:   */     }
/* 64:58 */     return 0;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public boolean isFluidInputAllowed(FluidStack aFluid)
/* 68:   */   {
/* 69:63 */     return aFluid.isFluidEqual(Materials.UUAmplifier.getFluid(1L));
/* 70:   */   }
/* 71:   */   
/* 72:   */   public int getCapacity()
/* 73:   */   {
/* 74:68 */     return Math.max(sUUAperUUM, 1000);
/* 75:   */   }
/* 76:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Massfabricator
 * JD-Core Version:    0.7.0.1
 */