/*  1:   */ package gregtech.common.tileentities.generators;
/*  2:   */ 
/*  3:   */ import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  6:   */ import gregtech.api.interfaces.ITexture;
/*  7:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  8:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  9:   */ import gregtech.api.metatileentity.implementations.*;
import gregtech.api.objects.GT_ArrayList;
/* 10:   */ import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Config;
/* 11:   */ import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
/* 12:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class GT_MetaTileEntity_DieselGenerator
/* 16:   */   extends GT_MetaTileEntity_BasicGenerator
/* 17:   */ {
	
				public int mEfficiency;
/* 18:   */   public boolean isOutputFacing(byte aSide)
/* 19:   */   {
/* 20:16 */     return aSide == getBaseMetaTileEntity().getFrontFacing();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public GT_MetaTileEntity_DieselGenerator(int aID, String aName, String aNameRegional, int aTier)
/* 24:   */   {
/* 25:19 */     super(aID, aName, aNameRegional, aTier, "Requires liquid Fuel", new ITexture[0]);
					onConfigLoad();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public GT_MetaTileEntity_DieselGenerator(String aName, int aTier, String aDescription, ITexture[][][] aTextures)
/* 29:   */   {
/* 30:23 */     super(aName, aTier, aDescription, aTextures);
				onConfigLoad();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 34:   */   {
/* 35:28 */     return new GT_MetaTileEntity_DieselGenerator(this.mName, this.mTier, this.mDescription, this.mTextures);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public GT_Recipe.GT_Recipe_Map getRecipes()
/* 39:   */   {
/* 40:33 */     return GT_Recipe.GT_Recipe_Map.sDieselFuels;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int getCapacity()
/* 44:   */   {
/* 45:38 */     return 16000;
/* 46:   */   }

/* 47:   */   public void onConfigLoad()
/* 39:   */   {
				this.mEfficiency =GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "DieselGenerator.efficiency.tier."+this.mTier, (100 - this.mTier * 10));
}

/* 48:   */   public int getEfficiency()
/* 49:   */   {
/* 50:43 */     return this.mEfficiency;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public int getFuelValue(ItemStack aStack)
/* 54:   */   {
/* 55:48 */     int rValue = Math.max(GT_ModHandler.getFuelCanValue(aStack) * 6 / 5, super.getFuelValue(aStack));
/* 56:49 */     if (ItemList.Fuel_Can_Plastic_Filled.isStackEqual(aStack, false, true)) {
/* 57:49 */       rValue = Math.max(rValue, GameRegistry.getFuelValue(aStack) * 3);
/* 58:   */     }
/* 59:50 */     return rValue;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public ITexture[] getFront(byte aColor)
/* 63:   */   {
/* 64:53 */     return new ITexture[] { super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 65:   */   }
/* 66:   */   
/* 67:   */   public ITexture[] getBack(byte aColor)
/* 68:   */   {
/* 69:54 */     return new ITexture[] { super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BACK) };
/* 70:   */   }
/* 71:   */   
/* 72:   */   public ITexture[] getBottom(byte aColor)
/* 73:   */   {
/* 74:55 */     return new ITexture[] { super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM) };
/* 75:   */   }
/* 76:   */   
/* 77:   */   public ITexture[] getTop(byte aColor)
/* 78:   */   {
/* 79:56 */     return new ITexture[] { super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_TOP) };
/* 80:   */   }
/* 81:   */   
/* 82:   */   public ITexture[] getSides(byte aColor)
/* 83:   */   {
/* 84:57 */     return new ITexture[] { super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_SIDE) };
/* 85:   */   }
/* 86:   */   
/* 87:   */   public ITexture[] getFrontActive(byte aColor)
/* 88:   */   {
/* 89:58 */     return new ITexture[] { super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT_ACTIVE), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier] };
/* 90:   */   }
/* 91:   */   
/* 92:   */   public ITexture[] getBackActive(byte aColor)
/* 93:   */   {
/* 94:59 */     return new ITexture[] { super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BACK_ACTIVE) };
/* 95:   */   }
/* 96:   */   
/* 97:   */   public ITexture[] getBottomActive(byte aColor)
/* 98:   */   {
/* 99:60 */     return new ITexture[] { super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM_ACTIVE) };
/* :0:   */   }
/* :1:   */   
/* :2:   */   public ITexture[] getTopActive(byte aColor)
/* :3:   */   {
/* :4:61 */     return new ITexture[] { super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_TOP_ACTIVE) };
/* :5:   */   }
/* :6:   */   
/* :7:   */   public ITexture[] getSidesActive(byte aColor)
/* :8:   */   {
/* :9:62 */     return new ITexture[] { super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_SIDE_ACTIVE) };
/* ;0:   */   }
/* ;1:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.generators.GT_MetaTileEntity_DieselGenerator
 * JD-Core Version:    0.7.0.1
 */