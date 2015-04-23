/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.OrePrefixes;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
/*  6:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
/*  9:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 10:   */ import gregtech.api.util.GT_Utility;

/* 11:   */ import java.util.ArrayList;

/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class GT_MetaTileEntity_Printer
/* 15:   */   extends GT_MetaTileEntity_BasicMachine
/* 16:   */ {
/* 17:   */   public GT_MetaTileEntity_Printer(int aID, String aName, String aNameRegional, int aTier)
/* 18:   */   {
/* 19:19 */     super(aID, aName, aNameRegional, aTier, 1, "It can copy Books and paint Stuff", 1, 1, "Printer.png", GT_Recipe.GT_Recipe_Map.sPrinterRecipes.mNEIName, new ITexture[0]);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public GT_MetaTileEntity_Printer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 23:   */   {
/* 24:23 */     super(aName, aTier, 1, aDescription, aTextures, 2, 2, aGUIName, aNEIName);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int checkRecipe()
/* 28:   */   {
/* 29:28 */     if (getOutputAt(0) != null)
/* 30:   */     {
/* 31:29 */       this.mOutputBlocked += 1;
/* 32:   */     }
/* 33:30 */     else if ((GT_Utility.isStackValid(getInputAt(0))) && (getInputAt(0).stackSize > 0) && 
/* 34:31 */       (GT_Utility.isStackInvalid(getSpecialSlot())) && 
/* 35:32 */       (OrePrefixes.block.contains(getInputAt(0))))
/* 36:   */     {
/* 37:33 */       ArrayList<ItemStack> tList = GT_OreDictUnificator.getOres(GT_OreDictUnificator.getAssociation(getInputAt(0)));
/* 38:34 */       if (tList.size() > 1)
/* 39:   */       {
/* 40:35 */         tList.add(tList.get(0));
/* 41:36 */         int i = 0;
/* 42:36 */         for (int j = tList.size() - 1; i < j; i++) {
/* 43:37 */           if (GT_Utility.areStacksEqual(getInputAt(0), (ItemStack)tList.get(i)))
/* 44:   */           {
/* 45:38 */             this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[] { tList.get(i + 1) });
/* 46:39 */             this.mEUt = (1 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 47:40 */             this.mMaxProgresstime = (32 / (1 << this.mTier - 1));
/* 48:41 */             getInputAt(0).stackSize -= 1;
/* 49:42 */             return 2;
/* 50:   */           }
/* 51:   */         }
/* 52:   */       }
/* 53:   */     }
/* 54:49 */     this.mMaxProgresstime = 0;
/* 55:50 */     return 0;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 59:   */   {
/* 60:56 */     return null;
/* 61:   */   }
/* 62:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Printer
 * JD-Core Version:    0.7.0.1
 */