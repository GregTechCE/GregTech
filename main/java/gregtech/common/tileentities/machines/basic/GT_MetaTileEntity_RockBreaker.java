/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*  5:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  6:   */ import gregtech.api.interfaces.ITexture;
/*  7:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  8:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  9:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/* 10:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 11:   */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
/* 12:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 13:   */ import gregtech.api.util.GT_Utility;
/* 14:   */ import net.minecraft.init.Blocks;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ 
/* 17:   */ public class GT_MetaTileEntity_RockBreaker
/* 18:   */   extends GT_MetaTileEntity_BasicMachine
/* 19:   */ {
/* 20:   */   public GT_MetaTileEntity_RockBreaker(int aID, String aName, String aNameRegional, int aTier)
/* 21:   */   {
/* 22:19 */     super(aID, aName, aNameRegional, aTier, 1, "Put Lava and Water adjacent", 1, 1, "RockBreaker.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER) });
/* 23:   */   }
/* 24:   */   
/* 25:   */   public GT_MetaTileEntity_RockBreaker(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 26:   */   {
/* 27:23 */     super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 31:   */   {
/* 32:28 */     return new GT_MetaTileEntity_RockBreaker(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public GT_Recipe.GT_Recipe_Map getRecipeList()
/* 36:   */   {
/* 37:33 */     return GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 41:   */   {
/* 42:38 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int checkRecipe()
/* 46:   */   {
/* 47:43 */     IGregTechTileEntity aBaseMetaTileEntity = getBaseMetaTileEntity();
/* 48:44 */     if ((aBaseMetaTileEntity.getBlockOffset(0, 0, 1) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(0, 0, -1) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(-1, 0, 0) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(1, 0, 0) == Blocks.water))
/* 49:   */     {
/* 50:45 */       ItemStack tOutput = null;
/* 51:46 */       if (aBaseMetaTileEntity.getBlockOffset(0, 1, 0) == Blocks.lava) {
/* 52:47 */         tOutput = new ItemStack(Blocks.stone, 1);
/* 53:49 */       } else if ((aBaseMetaTileEntity.getBlockOffset(0, 0, 1) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(0, 0, -1) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(-1, 0, 0) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(1, 0, 0) == Blocks.lava)) {
/* 54:50 */         tOutput = new ItemStack(Blocks.cobblestone, 1);
/* 55:   */       }
/* 56:52 */       if (tOutput != null) {
/* 57:53 */         if (GT_Utility.areStacksEqual(getInputAt(0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L)))
/* 58:   */         {
/* 59:54 */           tOutput = new ItemStack(Blocks.obsidian, 1);
/* 60:55 */           if (canOutput(new ItemStack[] { tOutput }))
/* 61:   */           {
/* 62:56 */             getInputAt(0).stackSize -= 1;
/* 63:57 */             this.mOutputItems[0] = tOutput;
/* 64:58 */             this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
/* 65:59 */             this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 66:60 */             return 2;
/* 67:   */           }
/* 68:   */         }
/* 69:63 */         else if (canOutput(new ItemStack[] { tOutput }))
/* 70:   */         {
/* 71:64 */           this.mOutputItems[0] = tOutput;
/* 72:65 */           this.mMaxProgresstime = (16 / (1 << this.mTier - 1));
/* 73:66 */           this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 74:67 */           return 2;
/* 75:   */         }
/* 76:   */       }
/* 77:   */     }
/* 78:72 */     return 0;
/* 79:   */   }
/* 80:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_RockBreaker
 * JD-Core Version:    0.7.0.1
 */