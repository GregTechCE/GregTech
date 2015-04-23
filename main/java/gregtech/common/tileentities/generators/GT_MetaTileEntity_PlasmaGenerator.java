package gregtech.common.tileentities.generators;

/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
/*  8:   */ import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
/*  9:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 10:   */ 
/* 11:   */ public class GT_MetaTileEntity_PlasmaGenerator
/* 12:   */   extends GT_MetaTileEntity_BasicGenerator
/* 13:   */ {

	public int mEfficiency;
/* 14:   */   public boolean isOutputFacing(byte aSide)
/* 15:   */   {
/* 16:12 */     return aSide == getBaseMetaTileEntity().getFrontFacing();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public GT_MetaTileEntity_PlasmaGenerator(int aID, String aName, String aNameRegional, int aTier)
/* 20:   */   {
/* 21:15 */     super(aID, aName, aNameRegional, aTier, "Plasma into energy", new ITexture[0]);
onConfigLoad();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public GT_MetaTileEntity_PlasmaGenerator(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/* 25:   */   {
/* 26:19 */     super(aName, aTier, aDescription, aTextures);
onConfigLoad();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 30:   */   {
/* 31:24 */     return new GT_MetaTileEntity_PlasmaGenerator(this.mName, this.mTier, this.mDescription, this.mTextures);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public GT_Recipe.GT_Recipe_Map getRecipes()
/* 35:   */   {
/* 36:29 */     return GT_Recipe.GT_Recipe_Map.sPlasmaFuels;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getCapacity()
/* 40:   */   {
/* 41:34 */     return 16000;
/* 42:   */   }

public void onConfigLoad()
/* 39:   */   {
				this.mEfficiency =GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "PlasmaGenerator.efficiency.tier."+this.mTier, (50 + ((this.mTier-5) * 25)));
}

/* 43:   */   
/* 44:   */   public int getEfficiency()
/* 45:   */   {
/* 46:39 */     return this.mEfficiency;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public ITexture[] getFront(byte aColor)
/* 50:   */   {
/* 51:42 */     return new ITexture[] { super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 52:   */   }
/* 53:   */   
/* 54:   */   public ITexture[] getBack(byte aColor)
/* 55:   */   {
/* 56:43 */     return new ITexture[] { super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS) };
/* 57:   */   }
/* 58:   */   
/* 59:   */   public ITexture[] getBottom(byte aColor)
/* 60:   */   {
/* 61:44 */     return new ITexture[] { super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS) };
/* 62:   */   }
/* 63:   */   
/* 64:   */   public ITexture[] getTop(byte aColor)
/* 65:   */   {
/* 66:45 */     return new ITexture[] { super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS) };
/* 67:   */   }
/* 68:   */   
/* 69:   */   public ITexture[] getSides(byte aColor)
/* 70:   */   {
/* 71:46 */     return new ITexture[] { super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS) };
/* 72:   */   }
/* 73:   */   
/* 74:   */   public ITexture[] getFrontActive(byte aColor)
/* 75:   */   {
/* 76:47 */     return new ITexture[] { super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 77:   */   }
/* 78:   */   
/* 79:   */   public ITexture[] getBackActive(byte aColor)
/* 80:   */   {
/* 81:48 */     return new ITexture[] { super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW) };
/* 82:   */   }
/* 83:   */   
/* 84:   */   public ITexture[] getBottomActive(byte aColor)
/* 85:   */   {
/* 86:49 */     return new ITexture[] { super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW) };
/* 87:   */   }
/* 88:   */   
/* 89:   */   public ITexture[] getTopActive(byte aColor)
/* 90:   */   {
/* 91:50 */     return new ITexture[] { super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW) };
/* 92:   */   }
/* 93:   */   
/* 94:   */   public ITexture[] getSidesActive(byte aColor)
/* 95:   */   {
/* 96:51 */     return new ITexture[] { super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW) };
/* 97:   */   }
/* 98:   */ }