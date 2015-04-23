/*  1:   */ package gregtech.common.tileentities.machines.basic;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
/*  4:   */ import gregtech.api.enums.Element;
/*  5:   */ import gregtech.api.enums.ItemList;
/*  6:   */ import gregtech.api.enums.Materials;
/*  7:   */ import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
/*  8:   */ import gregtech.api.enums.Textures.BlockIcons;
/*  9:   */ import gregtech.api.interfaces.ITexture;
/* 10:   */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/* 11:   */ import gregtech.api.metatileentity.MetaTileEntity;
/* 12:   */ import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
/* 13:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 14:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 15:   */ import gregtech.api.util.GT_Utility;
/* 16:   */ import gregtech.common.items.behaviors.Behaviour_DataOrb;

/* 17:   */ import java.util.ArrayList;
/* 18:   */ import java.util.Collection;
/* 19:   */ import java.util.Iterator;

/* 20:   */ import net.minecraft.item.ItemStack;
/* 21:   */ import net.minecraftforge.fluids.FluidStack;
/* 22:   */ 
/* 23:   */ public class GT_MetaTileEntity_Replicator
/* 24:   */   extends GT_MetaTileEntity_BasicMachine
/* 25:   */ {
/* 26:18 */   private static int sHeaviestElementMass = 0;
/* 27:   */   
/* 28:   */   public GT_MetaTileEntity_Replicator(int aID, String aName, String aNameRegional, int aTier)
/* 29:   */   {
/* 30:21 */     super(aID, aName, aNameRegional, aTier, 1, "Producing Elemental Matter", 1, 1, "Replicator.png", "", new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_REPLICATOR) });
/* 31:   */   }
/* 32:   */   
/* 33:   */   public GT_MetaTileEntity_Replicator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName)
/* 34:   */   {
/* 35:25 */     super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
/* 39:   */   {
/* 40:30 */     return new GT_MetaTileEntity_Replicator(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int checkRecipe()
/* 44:   */   {
/* 45:35 */     FluidStack tFluid = getFillableStack();
/* 46:36 */     if ((tFluid != null) && (tFluid.isFluidEqual(Materials.UUMatter.getFluid(1L))))
/* 47:   */     {
/* 48:37 */       ItemStack tDataOrb = getSpecialSlot();
/* 49:38 */       if ((ItemList.Tool_DataOrb.isStackEqual(tDataOrb, false, true)) && (Behaviour_DataOrb.getDataTitle(tDataOrb).equals("Elemental-Scan")))
/* 50:   */       {
/* 51:39 */         Materials tMaterial = (Materials)Element.get(Behaviour_DataOrb.getDataName(tDataOrb)).mLinkedMaterials.get(0);
/* 52:40 */         long tMass = tMaterial.getMass();
/* 53:41 */         if ((tFluid.amount >= tMass) && (tMass > 0L))
/* 54:   */         {
/* 55:42 */           this.mEUt = ((int)gregtech.api.enums.GT_Values.V[this.mTier]);
/* 56:43 */           this.mMaxProgresstime = ((int)(tMass * 512L / (1 << this.mTier - 1)));
/* 57:44 */           if ((this.mOutputItems[0] = GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial, 1L)) == null)
/* 58:   */           {
/* 59:45 */             if ((this.mOutputItems[0] = GT_OreDictUnificator.get(OrePrefixes.cell, tMaterial, 1L)) != null) {
/* 60:46 */               if ((this.mOutputFluid = GT_Utility.getFluidForFilledItem(this.mOutputItems[0], true)) == null)
/* 61:   */               {
/* 62:47 */                 if (ItemList.Cell_Empty.isStackEqual(getInputAt(0))) {
/* 63:48 */                   if (canOutput(new ItemStack[] { this.mOutputItems[0] }))
/* 64:   */                   {
/* 65:49 */                     getInputAt(0).stackSize -= 1; FluidStack 
/* 66:50 */                       tmp231_230 = tFluid;tmp231_230.amount = ((int)(tmp231_230.amount - tMass));
/* 67:51 */                     return 2;
/* 68:   */                   }
/* 69:   */                 }
/* 70:   */               }
/* 71:   */               else
/* 72:   */               {
/* 73:55 */                 this.mOutputItems[0] = null;
/* 74:56 */                 if ((getDrainableStack() == null) || ((getDrainableStack().isFluidEqual(this.mOutputFluid)) && (getDrainableStack().amount < 16000)))
/* 75:   */                 {
/* 76:57 */                   FluidStack tmp287_286 = tFluid;tmp287_286.amount = ((int)(tmp287_286.amount - tMass));
/* 77:58 */                   return 2;
/* 78:   */                 }
/* 79:   */               }
/* 80:   */             }
/* 81:   */           }
/* 82:62 */           else if (canOutput(new ItemStack[] { this.mOutputItems[0] }))
/* 83:   */           {
/* 84:63 */             FluidStack tmp322_321 = tFluid;tmp322_321.amount = ((int)(tmp322_321.amount - tMass));
/* 85:64 */             return 2;
/* 86:   */           }
/* 87:   */         }
/* 88:   */       }
/* 89:   */     }
/* 90:69 */     return 0;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack)
/* 94:   */   {
/* 95:74 */     return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (ItemList.Cell_Empty.isStackEqual(aStack));
/* 96:   */   }
/* 97:   */   
/* 98:   */   public boolean isFluidInputAllowed(FluidStack aFluid)
/* 99:   */   {
/* :0:79 */     return aFluid.isFluidEqual(Materials.UUMatter.getFluid(1L));
/* :1:   */   }
/* :2:   */   
/* :3:   */   public int getCapacity()
/* :4:   */   {
/* :5:84 */     if ((sHeaviestElementMass == 0) && (GregTech_API.sPostloadFinished))
/* :6:   */     {
/* :7:   */       Materials tMaterial;
/* :8:84 */       for (Iterator i$ = Materials.VALUES.iterator(); i$.hasNext(); sHeaviestElementMass = Math.max(sHeaviestElementMass, (int)tMaterial.getMass()))
/* :9:   */       {
/* ;0:84 */         tMaterial = (Materials)i$.next();
/* ;1:84 */         if ((tMaterial.mElement == null) || (tMaterial.mElement.mIsIsotope)) {}
/* ;2:   */       }
/* ;3:   */     }
/* ;4:85 */     return sHeaviestElementMass;
/* ;5:   */   }
/* ;6:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_Replicator
 * JD-Core Version:    0.7.0.1
 */