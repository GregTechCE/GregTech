/*  1:   */ package gregtech.common.tileentities.generators;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
/*  8:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
/* 10:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 11:   */ import net.minecraftforge.fluids.FluidStack;
/* 12:   */ 
/* 13:   */ public class GT_MetaTileEntity_SteamTurbine
/* 14:   */   extends GT_MetaTileEntity_BasicGenerator
/* 15:   */ {

	public int mEfficiency;
/* 16:   */   public boolean isOutputFacing(byte aSide)
/* 17:   */   {
/* 18:14 */     return aSide == getBaseMetaTileEntity().getFrontFacing();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public GT_MetaTileEntity_SteamTurbine(int aID, String aName, String aNameRegional, int aTier)
/* 22:   */   {
/* 23:17 */     super(aID, aName, aNameRegional, aTier, "Requires Steam to run", new ITexture[0]);
onConfigLoad();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public GT_MetaTileEntity_SteamTurbine(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/* 27:   */   {
/* 28:21 */     super(aName, aTier, aDescription, aTextures);
onConfigLoad();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 32:   */   {
/* 33:26 */     return new GT_MetaTileEntity_SteamTurbine(this.mName, this.mTier, this.mDescription, this.mTextures);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public GT_Recipe.GT_Recipe_Map getRecipes()
/* 37:   */   {
/* 38:31 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getCapacity()
/* 42:   */   {
/* 43:36 */     return 24000 * this.mTier;
/* 44:   */   }

public void onConfigLoad()
/* 39:   */   {
				this.mEfficiency =GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "SteamTurbine.efficiency.tier."+this.mTier, (200 / consumedFluidPerOperation(GT_ModHandler.getSteam(1L))));
}
/* 45:   */   
/* 46:   */   public int getEfficiency()
/* 47:   */   {
/* 48:41 */     return this.mEfficiency;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getFuelValue(FluidStack aLiquid)
/* 52:   */   {
/* 53:46 */     return GT_ModHandler.isSteam(aLiquid) ? 1 : 0;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int consumedFluidPerOperation(FluidStack aLiquid)
/* 57:   */   {
/* 58:51 */     return 2 + this.mTier;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public ITexture[] getFront(byte aColor)
/* 62:   */   {
/* 63:54 */     return new ITexture[] { super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_FRONT), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 64:   */   }
/* 65:   */   
/* 66:   */   public ITexture[] getBack(byte aColor)
/* 67:   */   {
/* 68:55 */     return new ITexture[] { super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BACK) };
/* 69:   */   }
/* 70:   */   
/* 71:   */   public ITexture[] getBottom(byte aColor)
/* 72:   */   {
/* 73:56 */     return new ITexture[] { super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BOTTOM) };
/* 74:   */   }
/* 75:   */   
/* 76:   */   public ITexture[] getTop(byte aColor)
/* 77:   */   {
/* 78:57 */     return new ITexture[] { super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_TOP) };
/* 79:   */   }
/* 80:   */   
/* 81:   */   public ITexture[] getSides(byte aColor)
/* 82:   */   {
/* 83:58 */     return new ITexture[] { super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_SIDE) };
/* 84:   */   }
/* 85:   */   
/* 86:   */   public ITexture[] getFrontActive(byte aColor)
/* 87:   */   {
/* 88:59 */     return new ITexture[] { super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_FRONT_ACTIVE), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 89:   */   }
/* 90:   */   
/* 91:   */   public ITexture[] getBackActive(byte aColor)
/* 92:   */   {
/* 93:60 */     return new ITexture[] { super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BACK_ACTIVE) };
/* 94:   */   }
/* 95:   */   
/* 96:   */   public ITexture[] getBottomActive(byte aColor)
/* 97:   */   {
/* 98:61 */     return new ITexture[] { super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BOTTOM_ACTIVE) };
/* 99:   */   }
/* :0:   */   
/* :1:   */   public ITexture[] getTopActive(byte aColor)
/* :2:   */   {
/* :3:62 */     return new ITexture[] { super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_TOP_ACTIVE) };
/* :4:   */   }
/* :5:   */   
/* :6:   */   public ITexture[] getSidesActive(byte aColor)
/* :7:   */   {
/* :8:63 */     return new ITexture[] { super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_SIDE_ACTIVE) };
/* :9:   */   }
/* ;0:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.generators.GT_MetaTileEntity_SteamTurbine
 * JD-Core Version:    0.7.0.1
 */