/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
/*  4:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  5:   */ import gregtech.api.interfaces.ITexture;
/*  6:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  7:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  8:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/*  9:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 10:   */ import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
/* 11:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 12:   */ import gregtech.api.util.GT_Utility;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class GT_MetaTileEntity_Boxinator
/* 16:   */   extends GT_MetaTileEntity_BasicMachine
/* 17:   */ {
/* 18:   */   public GT_MetaTileEntity_Boxinator(int aID, String aName, String aNameRegional, int aTier)
/* 19:   */   {
/* 20:18 */     super(aID, aName, aNameRegional, aTier, 1, "Puts things into Boxes", 2, 1, "Packager.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_BOXINATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_BOXINATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_BOXINATOR) });
/* 21:   */   }
/* 22:   */   
/* 23:   */   public GT_MetaTileEntity_Boxinator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 24:   */   {
/* 25:22 */     super(aName, aTier, 1, aDescription, aTextures, 2, 1, aGUIName, aNEIName);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 29:   */   {
/* 30:27 */     return new GT_MetaTileEntity_Boxinator(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public GT_Recipe.GT_Recipe_Map getRecipeList()
/* 34:   */   {
/* 35:32 */     return GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int checkRecipe()
/* 39:   */   {
/* 40:37 */     int tCheck = super.checkRecipe();
/* 41:38 */     if (tCheck != 0) {
/* 42:38 */       return tCheck;
/* 43:   */     }
/* 44:39 */     if ((GT_Utility.isStackValid(getInputAt(0))) && (GT_Utility.isStackValid(getInputAt(1))) && (GT_Utility.getContainerItem(getInputAt(0), true) == null))
/* 45:   */     {
/* 46:40 */       if ((ItemList.Schematic_1by1.isStackEqual(getInputAt(1))) && (getInputAt(0).stackSize >= 1))
/* 47:   */       {
/* 48:41 */         this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[] { getInputAt(0) });
/* 49:42 */         if (this.mOutputItems[0] != null) {
/* 50:42 */           if (canOutput(new ItemStack[] { this.mOutputItems[0] }))
/* 51:   */           {
/* 52:43 */             getInputAt(0).stackSize -= 1;
/* 53:44 */             this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 54:45 */             this.mMaxProgresstime = (16 / (1 << this.mTier - 1));
/* 55:46 */             return 2;
/* 56:   */           }
/* 57:   */         }
/* 58:48 */         return 0;
/* 59:   */       }
/* 60:50 */       if ((ItemList.Schematic_2by2.isStackEqual(getInputAt(1))) && (getInputAt(0).stackSize >= 4))
/* 61:   */       {
/* 62:51 */         this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[] { getInputAt(0), getInputAt(0), null, getInputAt(0), getInputAt(0) });
/* 63:52 */         if (this.mOutputItems[0] != null) {
/* 64:52 */           if (canOutput(new ItemStack[] { this.mOutputItems[0] }))
/* 65:   */           {
/* 66:53 */             getInputAt(0).stackSize -= 4;
/* 67:54 */             this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 68:55 */             this.mMaxProgresstime = (32 / (1 << this.mTier - 1));
/* 69:56 */             return 2;
/* 70:   */           }
/* 71:   */         }
/* 72:58 */         return 0;
/* 73:   */       }
/* 74:60 */       if ((ItemList.Schematic_3by3.isStackEqual(getInputAt(1))) && (getInputAt(0).stackSize >= 9))
/* 75:   */       {
/* 76:61 */         this.mOutputItems[0] = GT_ModHandler.getRecipeOutput(new ItemStack[] { getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0), getInputAt(0) });
/* 77:62 */         if (this.mOutputItems[0] != null) {
/* 78:62 */           if (canOutput(new ItemStack[] { this.mOutputItems[0] }))
/* 79:   */           {
/* 80:63 */             getInputAt(0).stackSize -= 9;
/* 81:64 */             this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 82:65 */             this.mMaxProgresstime = (64 / (1 << this.mTier - 1));
/* 83:66 */             return 2;
/* 84:   */           }
/* 85:   */         }
/* 86:68 */         return 0;
/* 87:   */       }
/* 88:   */     }
/* 89:71 */     return 0;
/* 90:   */   }
/* 91:   */   
/* 92:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 93:   */   {
/* 94:76 */     if (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack))
/* 95:   */     {
/* 96:76 */       if (((getInputSlot() == aIndex) || (!ItemList.Crate_Empty.isStackEqual(aStack))) && (!ItemList.Schematic_1by1.isStackEqual(getInputAt(1))) && (!ItemList.Schematic_2by2.isStackEqual(getInputAt(1))) && (!ItemList.Schematic_3by3.isStackEqual(getInputAt(1)))) {}
/* 97:76 */       return GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes.findRecipe(getBaseMetaTileEntity(), true, gregtech.api.enums.GT_Values.V[this.mTier], null, new ItemStack[] { GT_Utility.copyAmount(64L, new Object[] { aStack }), getInputAt(1) }) != null;
/* 98:   */     }
/* 99:77 */     return false;
/* :0:   */   }
/* :1:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Boxinator
 * JD-Core Version:    0.7.0.1
 */