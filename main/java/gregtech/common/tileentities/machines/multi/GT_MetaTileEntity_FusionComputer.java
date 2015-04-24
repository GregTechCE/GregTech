/*   1:    */ package gregtech.common.tileentities.machines.multi;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
import java.util.Arrays;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_MultiMachine;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
/*   4:    */ import gregtech.api.interfaces.ITexture;
/*   5:    */ import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
/*   6:    */ import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Maintenance;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
/*   7:    */ import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.metatileentity.implementations.*;
import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
/*  12:    */ 
/*  13:    */ public abstract class GT_MetaTileEntity_FusionComputer
/*  14:    */   extends GT_MetaTileEntity_MultiBlockBase
/*  15:    */ {
	
				public GT_Recipe mLastRecipe;
				public int mEUStore;
/*  16:    */   public GT_MetaTileEntity_FusionComputer(int aID, String aName, String aNameRegional, int tier)
/*  17:    */   {
/*  18: 16 */     super(aID, aName, aNameRegional);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public GT_MetaTileEntity_FusionComputer(String aName)
/*  22:    */   {
/*  23: 20 */     super(aName);
/*  24:    */   }
/*  30:    */   public abstract int tier();
/*  40:    */   
/*  41:    */   public abstract long maxEUStore();
/*  50:    */   
/*  51:    */   public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer)
/*  52:    */   {
/*  53: 31 */     getBaseMetaTileEntity().openGUI(aPlayer, 143);
/*  54: 32 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public abstract MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);
/*  61:    */   
/*  62:    */   public boolean allowCoverOnSide(byte aSide, GT_ItemStack aStack)
/*  63:    */   {
/*  64: 42 */     return aSide != getBaseMetaTileEntity().getFrontFacing();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void saveNBTData(NBTTagCompound aNBT) {
					super.saveNBTData(aNBT);
				}
/*  68:    */   
/*  69:    */   public void loadNBTData(NBTTagCompound aNBT) {
					super.loadNBTData(aNBT);
				}
/*  70:    */   
/*  71:    */   @Override
					public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity,ItemStack aStack) {
/*  73: 57 */     int xCenter = getBaseMetaTileEntity().getXCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetX * 5;int yCenter = getBaseMetaTileEntity().getYCoord();int zCenter = getBaseMetaTileEntity().getZCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetZ * 5;
/*  74: 59 */     if (((isAdvancedMachineCasing(xCenter + 5, yCenter, zCenter)) || (xCenter + 5 == getBaseMetaTileEntity().getXCoord())) && 
/*  75: 60 */       ((isAdvancedMachineCasing(xCenter - 5, yCenter, zCenter)) || (xCenter - 5 == getBaseMetaTileEntity().getXCoord())) && 
/*  76: 61 */       ((isAdvancedMachineCasing(xCenter, yCenter, zCenter + 5)) || (zCenter + 5 == getBaseMetaTileEntity().getZCoord())) && 
/*  77: 62 */       ((isAdvancedMachineCasing(xCenter, yCenter, zCenter - 5)) || (zCenter - 5 == getBaseMetaTileEntity().getZCoord())) && 
/*  78: 63 */       (checkCoils(xCenter, yCenter, zCenter)) && 
/*  79: 64 */       (checkHulls(xCenter, yCenter, zCenter)) && 
/*  80: 65 */       (checkUpperOrLowerHulls(xCenter, yCenter + 1, zCenter)) && 
/*  81: 66 */       (checkUpperOrLowerHulls(xCenter, yCenter - 1, zCenter)) && 
/*  82: 67 */       (addIfEnergyInjector(xCenter + 4, yCenter, zCenter + 3,aBaseMetaTileEntity)) && 
/*  83: 68 */       (addIfEnergyInjector(xCenter + 4, yCenter, zCenter - 3,aBaseMetaTileEntity)) && 
/*  84: 69 */       (addIfEnergyInjector(xCenter + 4, yCenter, zCenter + 5,aBaseMetaTileEntity)) && 
/*  85: 70 */       (addIfEnergyInjector(xCenter + 4, yCenter, zCenter - 5,aBaseMetaTileEntity)) && 
/*  86: 71 */       (addIfEnergyInjector(xCenter - 4, yCenter, zCenter + 3,aBaseMetaTileEntity)) && 
/*  87: 72 */       (addIfEnergyInjector(xCenter - 4, yCenter, zCenter - 3,aBaseMetaTileEntity)) && 
/*  88: 73 */       (addIfEnergyInjector(xCenter - 4, yCenter, zCenter + 5,aBaseMetaTileEntity)) && 
/*  89: 74 */       (addIfEnergyInjector(xCenter - 4, yCenter, zCenter - 5,aBaseMetaTileEntity)) && 
/*  90: 75 */       (addIfEnergyInjector(xCenter + 3, yCenter, zCenter + 4,aBaseMetaTileEntity)) && 
/*  91: 76 */       (addIfEnergyInjector(xCenter - 3, yCenter, zCenter + 4,aBaseMetaTileEntity)) && 
/*  92: 77 */       (addIfEnergyInjector(xCenter + 5, yCenter, zCenter + 4,aBaseMetaTileEntity)) && 
/*  93: 78 */       (addIfEnergyInjector(xCenter - 5, yCenter, zCenter + 4,aBaseMetaTileEntity)) && 
/*  94: 79 */       (addIfEnergyInjector(xCenter + 3, yCenter, zCenter - 4,aBaseMetaTileEntity)) && 
/*  95: 80 */       (addIfEnergyInjector(xCenter - 3, yCenter, zCenter - 4,aBaseMetaTileEntity)) && 
/*  96: 81 */       (addIfEnergyInjector(xCenter + 5, yCenter, zCenter - 4,aBaseMetaTileEntity)) && 
/*  97: 82 */       (addIfEnergyInjector(xCenter - 5, yCenter, zCenter - 4,aBaseMetaTileEntity)) && 
/*  98: 83 */       (addIfExtractor(xCenter + 1, yCenter, zCenter - 5,aBaseMetaTileEntity)) && 
/*  99: 84 */       (addIfExtractor(xCenter + 1, yCenter, zCenter + 5,aBaseMetaTileEntity)) && 
/* 100: 85 */       (addIfExtractor(xCenter - 1, yCenter, zCenter - 5,aBaseMetaTileEntity)) && 
/* 101: 86 */       (addIfExtractor(xCenter - 1, yCenter, zCenter + 5,aBaseMetaTileEntity)) && 
/* 102: 87 */       (addIfExtractor(xCenter + 1, yCenter, zCenter - 7,aBaseMetaTileEntity)) && 
/* 103: 88 */       (addIfExtractor(xCenter + 1, yCenter, zCenter + 7,aBaseMetaTileEntity)) && 
/* 104: 89 */       (addIfExtractor(xCenter - 1, yCenter, zCenter - 7,aBaseMetaTileEntity)) && 
/* 105: 90 */       (addIfExtractor(xCenter - 1, yCenter, zCenter + 7,aBaseMetaTileEntity)) && 
/* 106: 91 */       (addIfExtractor(xCenter + 5, yCenter, zCenter - 1,aBaseMetaTileEntity)) && 
/* 107: 92 */       (addIfExtractor(xCenter + 5, yCenter, zCenter + 1,aBaseMetaTileEntity)) && 
/* 108: 93 */       (addIfExtractor(xCenter - 5, yCenter, zCenter - 1,aBaseMetaTileEntity)) && 
/* 109: 94 */       (addIfExtractor(xCenter - 5, yCenter, zCenter + 1,aBaseMetaTileEntity)) && 
/* 110: 95 */       (addIfExtractor(xCenter + 7, yCenter, zCenter - 1,aBaseMetaTileEntity)) && 
/* 111: 96 */       (addIfExtractor(xCenter + 7, yCenter, zCenter + 1,aBaseMetaTileEntity)) && 
/* 112: 97 */       (addIfExtractor(xCenter - 7, yCenter, zCenter - 1,aBaseMetaTileEntity)) && 
/* 113: 98 */       (addIfExtractor(xCenter - 7, yCenter, zCenter + 1,aBaseMetaTileEntity)) && 
/* 114: 99 */       (addIfInjector(xCenter + 1, yCenter + 1, zCenter - 6,aBaseMetaTileEntity)) && 
/* 115:100 */       (addIfInjector(xCenter + 1, yCenter + 1, zCenter + 6,aBaseMetaTileEntity)) && 
/* 116:101 */       (addIfInjector(xCenter - 1, yCenter + 1, zCenter - 6,aBaseMetaTileEntity)) && 
/* 117:102 */       (addIfInjector(xCenter - 1, yCenter + 1, zCenter + 6,aBaseMetaTileEntity)) && 
/* 118:103 */       (addIfInjector(xCenter - 6, yCenter + 1, zCenter + 1,aBaseMetaTileEntity)) && 
/* 119:104 */       (addIfInjector(xCenter + 6, yCenter + 1, zCenter + 1,aBaseMetaTileEntity)) && 
/* 120:105 */       (addIfInjector(xCenter - 6, yCenter + 1, zCenter - 1,aBaseMetaTileEntity)) && 
/* 121:106 */       (addIfInjector(xCenter + 6, yCenter + 1, zCenter - 1,aBaseMetaTileEntity)) && 
/* 122:107 */       (addIfInjector(xCenter + 1, yCenter - 1, zCenter - 6,aBaseMetaTileEntity)) && 
/* 123:108 */       (addIfInjector(xCenter + 1, yCenter - 1, zCenter + 6,aBaseMetaTileEntity)) && 
/* 124:109 */       (addIfInjector(xCenter - 1, yCenter - 1, zCenter - 6,aBaseMetaTileEntity)) && 
/* 125:110 */       (addIfInjector(xCenter - 1, yCenter - 1, zCenter + 6,aBaseMetaTileEntity)) && 
/* 126:111 */       (addIfInjector(xCenter - 6, yCenter - 1, zCenter + 1,aBaseMetaTileEntity)) && 
/* 127:112 */       (addIfInjector(xCenter + 6, yCenter - 1, zCenter + 1,aBaseMetaTileEntity)) && 
/* 128:113 */       (addIfInjector(xCenter - 6, yCenter - 1, zCenter - 1,aBaseMetaTileEntity)) && 
/* 129:114 */       (addIfInjector(xCenter + 6, yCenter - 1, zCenter - 1,aBaseMetaTileEntity)) &&
					(this.mEnergyHatches.size()>=1)&&
					(this.mOutputHatches.size()>=1)&&
					(this.mInputHatches.size()>=2)) {
	if(this.mEnergyHatches!=null){
		for(int i = 0;i < this.mEnergyHatches.size();i++){
			if(this.mEnergyHatches.get(i).mTier<tier())return false;}
	}if(this.mOutputHatches!=null){
		for(int i = 0;i < this.mOutputHatches.size();i++){
			if(this.mOutputHatches.get(i).mTier<tier())return false;}
	}if(this.mInputHatches!=null){
		for(int i = 0;i < this.mInputHatches.size();i++){
			if(this.mInputHatches.get(i).mTier<tier())return false;}
	}
	mWrench = true;mScrewdriver = true;	mSoftHammer = true;	 mHardHammer = true;	mSolderingTool = true;	mCrowbar = true;
/* 130:115 */       return true;
/* 131:    */     }
/* 132:117 */     return false;
/* 133:    */   }

				private boolean checkTier(byte tier,ArrayList<GT_MetaTileEntity_Hatch> list){
					if(list!=null){
						for(int i = 0;i < list.size();i++){
							if(list.get(i).mTier<tier){
								return false;
							}
						}
					}
					return true;
				}
/* 134:    */   
/* 135:    */   private boolean checkCoils(int aX, int aY, int aZ)
/* 136:    */   {
/* 137:121 */     return (isFusionCoil(aX + 6, aY, aZ - 1)) && (isFusionCoil(aX + 6, aY, aZ)) && (isFusionCoil(aX + 6, aY, aZ + 1)) && (isFusionCoil(aX + 5, aY, aZ - 3)) && (isFusionCoil(aX + 5, aY, aZ - 2)) && (isFusionCoil(aX + 5, aY, aZ + 2)) && (isFusionCoil(aX + 5, aY, aZ + 3)) && (isFusionCoil(aX + 4, aY, aZ - 4)) && (isFusionCoil(aX + 4, aY, aZ + 4)) && (isFusionCoil(aX + 3, aY, aZ - 5)) && (isFusionCoil(aX + 3, aY, aZ + 5)) && (isFusionCoil(aX + 2, aY, aZ - 5)) && (isFusionCoil(aX + 2, aY, aZ + 5)) && (isFusionCoil(aX + 1, aY, aZ - 6)) && (isFusionCoil(aX + 1, aY, aZ + 6)) && (isFusionCoil(aX, aY, aZ - 6)) && (isFusionCoil(aX, aY, aZ + 6)) && (isFusionCoil(aX - 1, aY, aZ - 6)) && (isFusionCoil(aX - 1, aY, aZ + 6)) && (isFusionCoil(aX - 2, aY, aZ - 5)) && (isFusionCoil(aX - 2, aY, aZ + 5)) && (isFusionCoil(aX - 3, aY, aZ - 5)) && (isFusionCoil(aX - 3, aY, aZ + 5)) && (isFusionCoil(aX - 4, aY, aZ - 4)) && (isFusionCoil(aX - 4, aY, aZ + 4)) && (isFusionCoil(aX - 5, aY, aZ - 3)) && (isFusionCoil(aX - 5, aY, aZ - 2)) && (isFusionCoil(aX - 5, aY, aZ + 2)) && (isFusionCoil(aX - 5, aY, aZ + 3)) && (isFusionCoil(aX - 6, aY, aZ - 1)) && (isFusionCoil(aX - 6, aY, aZ)) && (isFusionCoil(aX - 6, aY, aZ + 1));
/* 138:    */   }
/* 139:    */   
/* 140:    */   private boolean checkUpperOrLowerHulls(int aX, int aY, int aZ)
/* 141:    */   {
/* 142:168 */     return (isAdvancedMachineCasing(aX + 6, aY, aZ)) && (isAdvancedMachineCasing(aX + 5, aY, aZ - 3)) && (isAdvancedMachineCasing(aX + 5, aY, aZ - 2)) && (isAdvancedMachineCasing(aX + 5, aY, aZ + 2)) && (isAdvancedMachineCasing(aX + 5, aY, aZ + 3)) && (isAdvancedMachineCasing(aX + 4, aY, aZ - 4)) && (isAdvancedMachineCasing(aX + 4, aY, aZ + 4)) && (isAdvancedMachineCasing(aX + 3, aY, aZ - 5)) && (isAdvancedMachineCasing(aX + 3, aY, aZ + 5)) && (isAdvancedMachineCasing(aX + 2, aY, aZ - 5)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 5)) && (isAdvancedMachineCasing(aX, aY, aZ - 6)) && (isAdvancedMachineCasing(aX, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 5)) && (isAdvancedMachineCasing(aX - 2, aY, aZ + 5)) && (isAdvancedMachineCasing(aX - 3, aY, aZ - 5)) && (isAdvancedMachineCasing(aX - 3, aY, aZ + 5)) && (isAdvancedMachineCasing(aX - 4, aY, aZ - 4)) && (isAdvancedMachineCasing(aX - 4, aY, aZ + 4)) && (isAdvancedMachineCasing(aX - 5, aY, aZ - 3)) && (isAdvancedMachineCasing(aX - 5, aY, aZ - 2)) && (isAdvancedMachineCasing(aX - 5, aY, aZ + 2)) && (isAdvancedMachineCasing(aX - 5, aY, aZ + 3)) && (isAdvancedMachineCasing(aX - 6, aY, aZ));
/* 143:    */   }
/* 144:    */   
/* 145:    */   private boolean checkHulls(int aX, int aY, int aZ)
/* 146:    */   {
/* 147:205 */     return (isAdvancedMachineCasing(aX + 6, aY, aZ - 3)) && (isAdvancedMachineCasing(aX + 6, aY, aZ - 2)) && (isAdvancedMachineCasing(aX + 6, aY, aZ + 2)) && (isAdvancedMachineCasing(aX + 6, aY, aZ + 3)) && (isAdvancedMachineCasing(aX + 3, aY, aZ - 6)) && (isAdvancedMachineCasing(aX + 3, aY, aZ + 6)) && (isAdvancedMachineCasing(aX + 2, aY, aZ - 6)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 6)) && (isAdvancedMachineCasing(aX - 2, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 3, aY, aZ - 6)) && (isAdvancedMachineCasing(aX - 3, aY, aZ + 6)) && (isAdvancedMachineCasing(aX - 7, aY, aZ)) && (isAdvancedMachineCasing(aX + 7, aY, aZ)) && (isAdvancedMachineCasing(aX, aY, aZ - 7)) && (isAdvancedMachineCasing(aX, aY, aZ + 7)) && (isAdvancedMachineCasing(aX - 6, aY, aZ - 3)) && (isAdvancedMachineCasing(aX - 6, aY, aZ - 2)) && (isAdvancedMachineCasing(aX - 6, aY, aZ + 2)) && (isAdvancedMachineCasing(aX - 6, aY, aZ + 3)) && (isAdvancedMachineCasing(aX - 4, aY, aZ - 2)) && (isAdvancedMachineCasing(aX - 4, aY, aZ + 2)) && (isAdvancedMachineCasing(aX + 4, aY, aZ - 2)) && (isAdvancedMachineCasing(aX + 4, aY, aZ + 2)) && (isAdvancedMachineCasing(aX - 2, aY, aZ - 4)) && (isAdvancedMachineCasing(aX - 2, aY, aZ + 4)) && (isAdvancedMachineCasing(aX + 2, aY, aZ - 4)) && (isAdvancedMachineCasing(aX + 2, aY, aZ + 4));
/* 148:    */   }
/* 149:    */   
/* 150:    */   private boolean addIfEnergyInjector(int aX, int aY, int aZ,IGregTechTileEntity aBaseMetaTileEntity)
/* 151:    */   {
					if(addEnergyInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntity(aX,aY,aZ), 53)){
						return true;
					}
/* 152:242 */     return isAdvancedMachineCasing(aX, aY, aZ);
/* 153:    */   }
/* 154:    */   
/* 155:    */   private boolean addIfInjector(int aX, int aY, int aZ,IGregTechTileEntity aTileEntity)
/* 156:    */   {
						if(addInputToMachineList(aTileEntity.getIGregTechTileEntity(aX,aY,aZ), 53)){
							return true;
						}
/* 157:246 */     return isAdvancedMachineCasing(aX, aY, aZ);
/* 158:    */   }
/* 159:    */   
/* 160:    */   private boolean addIfExtractor(int aX, int aY, int aZ,IGregTechTileEntity aTileEntity)
/* 161:    */   {
						if(addOutputToMachineList(aTileEntity.getIGregTechTileEntity(aX,aY,aZ), 53)){
							return true;
						}
/* 162:250 */     return isAdvancedMachineCasing(aX, aY, aZ);
/* 163:    */   }
/* 164:    */   
/* 165:    */   private boolean isAdvancedMachineCasing(int aX, int aY, int aZ)
/* 166:    */   {
/* 167:254 */     return (getBaseMetaTileEntity().getBlock(aX, aY, aZ) == getCasing()) && (getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == getCasingMeta());
/* 168:    */   }
				public abstract Block getCasing();
				public abstract int getCasingMeta();
/* 169:    */   
/* 170:    */   private boolean isFusionCoil(int aX, int aY, int aZ)
/* 171:    */   {
/* 172:258 */     return (getBaseMetaTileEntity().getBlock(aX, aY, aZ) == getFusionCoil() && (getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == getFusionCoilMeta()));
/* 173:    */   }
				public abstract Block getFusionCoil();
				public abstract int getFusionCoilMeta();

/* 190:    */   public abstract String[] getDescription();
/* 194:    */   
/* 195:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
/* 196:    */   {
					ITexture[] sTexture;
					if(aSide==aFacing){
						sTexture  = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa)),new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SCREEN) };
					}else{
						if(!aActive){
						sTexture  = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};}else{
							sTexture  = new ITexture[] { new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW, Dyes.getModulation(-1, Dyes._NULL.mRGBa))};
						}
					}
/* 197:283 */     return sTexture;
/* 198:    */   }

@Override
public boolean isCorrectMachinePart(ItemStack aStack) {return true;}

public int overclock(int mEUt){
	if(tierOverclock()==1){return 1;}
	if(tierOverclock()==2){return mEUt<32768? 2 : 1;}
	return mEUt<32768?4:mEUt<65536?2:1;
}

@Override
public boolean checkRecipe(ItemStack aStack) {
	if(this.mLastRecipe!=null){;
		for(GT_MetaTileEntity_Hatch_Input tInput : this.mInputHatches){
			if(tInput.mFluid!=null&& tInput.mFluid!=null&&tInput.mFluid.getFluid().getID()==this.mLastRecipe.mFluidInputs[0].getFluid().getID()&&tInput.mFluid.amount>=this.mLastRecipe.mFluidInputs[0].amount){
				for(GT_MetaTileEntity_Hatch_Input tInput2 : this.mInputHatches){
					if(tInput2.mFluid!=null&& tInput2.mFluid!=null&&tInput2.mFluid.getFluid().getID()==this.mLastRecipe.mFluidInputs[1].getFluid().getID()&&tInput2.mFluid.amount>=this.mLastRecipe.mFluidInputs[1].amount&&getMaxInputVoltage()>=this.mLastRecipe.mEUt){
						tInput.drain(this.mLastRecipe.mFluidInputs[0].amount, true);
						tInput2.drain(this.mLastRecipe.mFluidInputs[1].amount, true);
						
						this.mEUt = -(this.mLastRecipe.mEUt*overclock(this.mLastRecipe.mEUt));
						this.mMaxProgresstime = this.mLastRecipe.mDuration/overclock(this.mLastRecipe.mEUt);
						
						this.mEfficiencyIncrease = 10000;
						this.mOutputFluids = this.mLastRecipe.mFluidOutputs;
						turnCasingActive(true);
						return true;
					}
				}
			}
		}
	}
	this.mLastRecipe=null;
	turnCasingActive(false);
	for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sFusionRecipes.mRecipeList){
		FluidStack tFluid = tRecipe.mFluidInputs[0];
		if(tFluid!=null){
			for(GT_MetaTileEntity_Hatch_Input tInput : this.mInputHatches){
				if(tFluid.fluid !=null&& tInput.getFluid()!=null && tFluid.fluid.getID()==tInput.getFluid().getFluid().getID()&&tFluid.amount<=tInput.getFluid().amount){
					FluidStack tFluid2 = tRecipe.mFluidInputs[1];
						if(tFluid2!=null){
							for(GT_MetaTileEntity_Hatch_Input tInput2 : this.mInputHatches){
								if(tFluid2.fluid !=null&& tInput2.getFluid()!=null&&tFluid2.fluid.getID()==tInput2.getFluid().getFluid().getID()&&tFluid2.amount<=tInput2.getFluid().amount&&getMaxInputVoltage()>=tRecipe.mEUt&&this.mEUStore>=tRecipe.mSpecialValue){
									tInput.drain(tFluid.amount, true);
									tInput2.drain(tFluid2.amount, true);
									this.mLastRecipe=tRecipe;
									
									this.mEUt = -(tRecipe.mEUt*overclock(this.mLastRecipe.mEUt));
									this.mMaxProgresstime = tRecipe.mDuration/overclock(this.mLastRecipe.mEUt);
									
									this.mEfficiencyIncrease = 10000;
									this.mOutputFluids = tRecipe.mFluidOutputs;
									turnCasingActive(true);
									return true;	
							}
						}
					}
				}
			}
		}
	}
	return false;
}

public abstract int tierOverclock();

public boolean turnCasingActive(boolean status){
	if(this.mEnergyHatches!=null){
		for(GT_MetaTileEntity_Hatch_Energy hatch: this.mEnergyHatches){
			hatch.mMachineBlock = status?(byte) 52:(byte) 53;
		}
	}if(this.mOutputHatches!=null){
		for(GT_MetaTileEntity_Hatch_Output hatch: this.mOutputHatches){
			hatch.mMachineBlock = status?(byte) 52:(byte) 53;	
		}
	}if(this.mInputHatches!=null){
		for(GT_MetaTileEntity_Hatch_Input hatch: this.mInputHatches){
			hatch.mMachineBlock = status?(byte) 52:(byte) 53;
		}
	}
	return true;
}
@Override
public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
	if (aBaseMetaTileEntity.isServerSide()) {
		if (mEfficiency < 0) mEfficiency = 0;
		if (--mUpdate==0 || --mStartUpCheck==0) {
			mInputHatches.clear();
			mInputBusses.clear();
			mOutputHatches.clear();
			mOutputBusses.clear();
			mDynamoHatches.clear();
			mEnergyHatches.clear();
			mMufflerHatches.clear();
			mMaintenanceHatches.clear();
			mMachine = checkMachine(aBaseMetaTileEntity, mInventory[1]);
		}
		if (mStartUpCheck < 0) {
			if (mMachine) {
				for (GT_MetaTileEntity_Hatch_Maintenance tHatch : mMaintenanceHatches) {
					if (isValidMetaTileEntity(tHatch)) {
						if (tHatch.mWrench) mWrench = true;
						if (tHatch.mScrewdriver) mScrewdriver = true;
						if (tHatch.mSoftHammer) mSoftHammer = true;
						if (tHatch.mHardHammer) mHardHammer = true;
						if (tHatch.mSolderingTool) mSolderingTool = true;
						if (tHatch.mCrowbar) mCrowbar = true;
						
						tHatch.mWrench = false;
						tHatch.mScrewdriver = false;
						tHatch.mSoftHammer = false;
						tHatch.mHardHammer = false;
						tHatch.mSolderingTool = false;
						tHatch.mCrowbar = false;
					}
				}
				if(this.mEnergyHatches!=null){
					for (GT_MetaTileEntity_Hatch_Energy tHatch : mEnergyHatches) if (isValidMetaTileEntity(tHatch)) {
						if (aBaseMetaTileEntity.getStoredEU()+(2048*tierOverclock())<maxEUStore()&&tHatch.getBaseMetaTileEntity().decreaseStoredEnergyUnits(2048*tierOverclock(), false)){
							aBaseMetaTileEntity.increaseStoredEnergyUnits(2048*tierOverclock(),true);
						}
					}
				}
				if (getRepairStatus() > 0) {
			    	if (mMaxProgresstime > 0 && doRandomMaintenanceDamage()) {
			    		if (aBaseMetaTileEntity.decreaseStoredEnergyUnits(mEUt, false)) {
				    		if (!polluteEnvironment(getPollutionPerTick(mInventory[1]))) {
				    			stopMachine();
				    		}
				    		
					    	if (mMaxProgresstime > 0 && ++mProgresstime>=mMaxProgresstime) {
					    		if (mOutputItems != null) for (ItemStack tStack : mOutputItems) if (tStack != null) addOutput(tStack);
					    		if (mOutputFluids != null) for (FluidStack tStack : mOutputFluids) if (tStack != null) addOutput(tStack);
					    		mEfficiency = Math.max(0, Math.min(mEfficiency + mEfficiencyIncrease, getMaxEfficiency(mInventory[1]) - ((getIdealStatus() - getRepairStatus()) * 1000)));
					    		mOutputItems = null;
					    		mProgresstime = 0;
					    		mMaxProgresstime = 0;
					    		mEfficiencyIncrease = 0;
					    		this.mEUStore=(int) aBaseMetaTileEntity.getStoredEU();
					    		if (aBaseMetaTileEntity.isAllowedToWork()) checkRecipe(mInventory[1]);
					    	}
			    		}
			    	} else {
			    		if (aTick % 100 == 0 || aBaseMetaTileEntity.hasWorkJustBeenEnabled() || aBaseMetaTileEntity.hasInventoryBeenModified()) {
			    			turnCasingActive(mMaxProgresstime>0);
				    		if (aBaseMetaTileEntity.isAllowedToWork()) {
				    			this.mEUStore=(int) aBaseMetaTileEntity.getStoredEU();
				    			if(checkRecipe(mInventory[1])&&aBaseMetaTileEntity.getStoredEU()>=this.mLastRecipe.mSpecialValue){
				    				aBaseMetaTileEntity.decreaseStoredEnergyUnits(this.mLastRecipe.mSpecialValue, true);
				    			}
				    			}
				    		if (mMaxProgresstime <= 0) mEfficiency = Math.max(0, mEfficiency - 1000);
			    		}
			    	}
				} else {
					stopMachine();
				}
			} else {
				turnCasingActive(false);
				stopMachine();
			}
		}
		aBaseMetaTileEntity.setErrorDisplayID((aBaseMetaTileEntity.getErrorDisplayID()&~127)|(mWrench?0:1)|(mScrewdriver?0:2)|(mSoftHammer?0:4)|(mHardHammer?0:8)|(mSolderingTool?0:16)|(mCrowbar?0:32)|(mMachine?0:64));
		aBaseMetaTileEntity.setActive(mMaxProgresstime>0);
	}
}

@Override
public boolean onRunningTick(ItemStack aStack) {
	if (mEUt < 0) {
		if (!drainEnergyInput(((long)-mEUt * 10000) / Math.max(1000, mEfficiency))) {
			stopMachine();
			return false;
		}
	}
	return true;
}

public boolean drainEnergyInput(long aEU) {
//	if (aEU <= this.mEUStore) {
//		this.mEUStore-=aEU;
//		return true;}
	return false;
}

@Override
public int getMaxEfficiency(ItemStack aStack) {
	return 10000;}
@Override
public int getPollutionPerTick(ItemStack aStack) {return 0;}
@Override
public int getDamageToComponent(ItemStack aStack) {return 0;}
@Override
public int getAmountOfOutputs() {return 0;}
@Override
public boolean explodesOnComponentBreak(ItemStack aStack) {return false;} }