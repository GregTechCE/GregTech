/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Textures;
import gregtech.api.enums.Textures.BlockIcons;
/*  4:   */ import gregtech.api.interfaces.ITexture;
/*  5:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*  6:   */ import gregtech.api.metatileentity.MetaTileEntity;
/*  7:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/*  8:   */ import gregtech.api.objects.GT_RenderedTexture;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ import net.minecraft.nbt.NBTTagCompound;
/* 12:   */ 
/* 13:   */ public class GT_MetaTileEntity_Disassembler
/* 14:   */   extends GT_MetaTileEntity_BasicMachine
/* 15:   */ {
/* 16:   */   public GT_MetaTileEntity_Disassembler(int aID, String aName, String aNameRegional, int aTier)
/* 17:   */   {
/* 18:15 */     super(aID, aName, aNameRegional, aTier, 1, "Disassembles Machines at " + (50 + 10 * aTier) + "% Efficiency", 1, 9, "Disassembler.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_DISASSEMBLER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_DISASSEMBLER) });
/* 19:   */   }
/* 20:   */   
/* 21:   */   public GT_MetaTileEntity_Disassembler(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 22:   */   {
/* 23:19 */     super(aName, aTier, 1, aDescription, aTextures, 1, 9, aGUIName, aNEIName);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 27:   */   {
/* 28:24 */     return new GT_MetaTileEntity_Disassembler(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int checkRecipe()
/* 32:   */   {
/* 33:29 */     if ((getInputAt(0) != null) && (isOutputEmpty()))
/* 34:   */     {
/* 35:30 */       NBTTagCompound tNBT = getInputAt(0).getTagCompound();
/* 36:31 */       if (tNBT != null)
/* 37:   */       {
/* 38:32 */         tNBT = tNBT.getCompoundTag("GT.CraftingComponents");
/* 39:33 */         if (tNBT != null)
/* 40:   */         {
/* 41:34 */           getInputAt(0).stackSize -= 1;
/* 42:35 */           this.mEUt = (16 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
/* 43:36 */           this.mMaxProgresstime = 160;
/* 44:37 */           for (int i = 0; i < this.mOutputItems.length; i++) {
/* 45:37 */             if (getBaseMetaTileEntity().getRandomNumber(100) < 50 + 10 * this.mTier)
/* 46:   */             {
/* 47:38 */               this.mOutputItems[i] = GT_Utility.loadItem(tNBT, "Ingredient." + i);
/* 48:39 */               if (this.mOutputItems[i] != null) {
/* 49:39 */                 this.mMaxProgresstime *= 2;
/* 50:   */               }
/* 51:   */             }
/* 52:   */           }
/* 53:41 */           return 2;
/* 54:   */         }
/* 55:   */       }
/* 56:   */     }
/* 57:45 */     return 0;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 61:   */   {
/* 62:50 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (aStack.getTagCompound() != null) && (aStack.getTagCompound().getCompoundTag("GT.CraftingComponents") != null);
/* 63:   */   }
/* 64:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Disassembler
 * JD-Core Version:    0.7.0.1
 */