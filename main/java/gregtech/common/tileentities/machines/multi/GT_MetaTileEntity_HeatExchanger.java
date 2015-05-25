package gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import forestry.core.fluids.Fluids;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

public class GT_MetaTileEntity_HeatExchanger extends GT_MetaTileEntity_MultiBlockBase{
	/*  25:    */   public GT_MetaTileEntity_HeatExchanger(int aID, String aName, String aNameRegional)
	/*  26:    */   {
	/*  27: 25 */     super(aID, aName, aNameRegional);
	/*  28:    */   }
	/*  29:    */   
	/*  30:    */   public GT_MetaTileEntity_HeatExchanger(String aName)
	/*  31:    */   {
	/*  32: 29 */     super(aName);
	/*  33:    */   }
	/*  34:    */   
	/*  35:    */   public String[] getDescription()
	/*  36:    */   {
	/*  37: 34 */     return new String[] { "Controller Block for the Heat Exchanger", "Size: 3x3x4", "Controller (front middle at bottom)", "3x3x4 of Stable Titanium Casing (hollow, Min 24!)", "2 Titanium Pipe Casing Blocks inside the Hollow Casing", "1x Distillated Water Input (one of the Casings)","min 1 Steam Output (one of the Casings)", "1x Maintenance Hatch (one of the Casings)", "1x Hot Fluid Input (botton Center)", "1x Cold Fluid Output (top Center)" };
	/*  38:    */   }
	
	public GT_MetaTileEntity_Hatch_Input mInputHotFluidHatch;
	public GT_MetaTileEntity_Hatch_Output mOutputColdFluidHatch;
	public boolean superheated=false;
	/*  59:    */   
	/*  60:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
	/*  61:    */   {
	/*  62: 53 */     if (aSide == aFacing) {
	/*  63: 53 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER) };
	/*  64:    */     }
	/*  65: 54 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[50] };
	/*  66:    */   }
	/*  67:    */   
	/*  68:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
	/*  69:    */   {
	/*  70: 59 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeBoiler.png");
	/*  71:    */   }
	/*  72:    */   
	/*  73:    */   public boolean isCorrectMachinePart(ItemStack aStack)
	/*  74:    */   {
	/*  75: 64 */     return true;
	/*  76:    */   }
	/*  77:    */   
	/*  78:    */   public boolean isFacingValid(byte aFacing)
	/*  79:    */   {
	/*  80: 67 */     return aFacing > 1;
	/*  81:    */   }
	/*  82:    */   
	/*  83:    */   public boolean checkRecipe(ItemStack aStack)
	/*  84:    */   {
		if(GT_ModHandler.isLava(mInputHotFluidHatch.getFluid())){
			int fluidAmount = mInputHotFluidHatch.getFluidAmount();
			if(fluidAmount >= 1000){superheated=true;}else{superheated=false;}
			if(fluidAmount>2000){fluidAmount=2000;}
			mInputHotFluidHatch.drain(fluidAmount, true);
			mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2pahoehoelava", fluidAmount), true);
			
			
			           this.mMaxProgresstime = 20;
			           this.mEUt = fluidAmount*2;
			           this.mEfficiencyIncrease = 80;
		return true;	
		}
		
		if(mInputHotFluidHatch.getFluid().isFluidEqual(FluidRegistry.getFluidStack("ic2hotcoolant", 1))){
			int fluidAmount = mInputHotFluidHatch.getFluidAmount();
			if(fluidAmount >= 4000){superheated=true;}else{superheated=false;}
			if(fluidAmount>8000){fluidAmount=8000;}
			mInputHotFluidHatch.drain(fluidAmount, true);
			mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2coolant", fluidAmount), true);
			
			
			           this.mMaxProgresstime = 20;
			           this.mEUt = fluidAmount/2;
			           this.mEfficiencyIncrease = 20;
		return true;	
		}
		return false;}
	/* 133:    */   
	/* 134:    */   public boolean onRunningTick(ItemStack aStack)
	/* 135:    */   {
	/* 136:115 */     if (this.mEUt > 0)
	/* 137:    */     {System.out.println("EU: "+mEUt+" Eff: "+mEfficiency);
	/* 138:116 */       int tGeneratedEU = (int)(this.mEUt * 2L * this.mEfficiency / 10000L);
	/* 139:117 */       if (tGeneratedEU > 0) {
	/* 140:118 */         if (depleteInput(GT_ModHandler.getDistilledWater(((superheated ? tGeneratedEU/2 :tGeneratedEU) + 160) / 160))) {
							if(superheated){
								addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", tGeneratedEU/2));
							}else{
	/* 141:119 */           addOutput(GT_ModHandler.getSteam(tGeneratedEU));}
	/* 142:    */         } else {
	/* 143:121 */           explodeMultiblock();
	/* 144:    */         }
	/* 145:    */       }
	/* 146:124 */       return true;
	/* 147:    */     }
	/* 148:126 */     return true;
	/* 149:    */   }
	/* 150:    */   private static boolean controller;
	/* 151:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
	/* 152:    */   {
	/* 153:131 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
	/* 154:    */     
	/* 155:133 */     int tCasingAmount = 0;int tFireboxAmount = 0;controller=false;
	/* 156:135 */     for (int i = -1; i < 2; i++) {
	/* 157:135 */       for (int j = -1; j < 2; j++) {
	/* 158:136 */         if ((i != 0) || (j != 0))
	/* 159:    */         {
	/* 160:137 */           for (int k = 0; k <= 3; k++) {
	/* 161:138 */           if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50)&&!addInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50)&&!addMaintenanceToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50)&&!ignoreController(aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j)))
	/* 162:    */             {
	/* 163:139 */               if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getCasingBlock()) {
	/* 164:139 */                 return false;
	/* 165:    */               }
	/* 166:140 */               if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getCasingMeta()) {
	/* 167:140 */                 return false;
	/* 168:    */               }
	/* 169:141 */               tCasingAmount++;
	/* 170:    */             }
							}
	/* 171:    */           }else{
								if(!addHotFluidInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j), 50)){
									return false;
								}
								if(!addColdFluidOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 3, zDir + j), 50)){
									return false;
								}
		/* 177:146 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != getPipeBlock()) {
			/* 178:146 */               return false;
			/* 179:    */             }
			/* 180:147 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != getPipeMeta()) {
			/* 181:147 */               return false;
			/* 182:    */             }
		
			/* 177:146 */             if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 2, zDir + j) != getPipeBlock()) {
				/* 178:146 */               return false;
				/* 179:    */             }
				/* 180:147 */             if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 2, zDir + j) != getPipeMeta()) {
				/* 181:147 */               return false;
				/* 182:    */             }
	/* 172:    */         }
	/* 195:    */       }
	/* 196:    */     }
	/* 215:166 */     return (tCasingAmount >= 24);
	/* 216:    */   }
	
	public boolean ignoreController(Block tTileEntity){
		if(!controller&&tTileEntity == GregTech_API.sBlockMachines){return true;}
		return false;
	}
	
	public boolean addColdFluidOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) return false;
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output) {
			((GT_MetaTileEntity_Hatch)aMetaTileEntity).mMachineBlock = (byte)aBaseCasingIndex;
			mOutputColdFluidHatch = (GT_MetaTileEntity_Hatch_Output)aMetaTileEntity;
			return true;
		}
		return false;
	}
	
	public boolean addHotFluidInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) return false;
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
			((GT_MetaTileEntity_Hatch)aMetaTileEntity).mMachineBlock = (byte)aBaseCasingIndex;
			((GT_MetaTileEntity_Hatch_Input)aMetaTileEntity).mRecipeMap = getRecipeMap();
			mInputHotFluidHatch = (GT_MetaTileEntity_Hatch_Input)aMetaTileEntity;
			return true;
		}
		return false;
	}
	
	/* 26:   */   public Block getCasingBlock()
	/* 27:   */   {
	/* 28:22 */     return GregTech_API.sBlockCasings4;
	/* 29:   */   }
	/* 30:   */   
	/* 31:   */   public byte getCasingMeta()
	/* 32:   */   {
	/* 33:23 */     return 2;
	/* 34:   */   }
	/* 35:   */   
	/* 36:   */   public byte getCasingTextureIndex()
	/* 37:   */   {
	/* 38:24 */     return 50;
	/* 39:   */   }
	/* 40:   */   
	/* 41:   */   public Block getPipeBlock()
	/* 42:   */   {
	/* 43:26 */     return GregTech_API.sBlockCasings2;
	/* 44:   */   }
	/* 45:   */   
	/* 46:   */   public byte getPipeMeta()
	/* 47:   */   {
	/* 48:27 */     return 14;
	/* 49:   */   }
	/* 217:    */   
	/* 218:    */   public int getMaxEfficiency(ItemStack aStack)
	/* 219:    */   {
	/* 220:171 */     return 10000;
	/* 221:    */   }
	/* 222:    */   
	/* 223:    */   public int getPollutionPerTick(ItemStack aStack)
	/* 224:    */   {
	/* 225:176 */     return 10;
	/* 226:    */   }
	/* 227:    */   
	/* 228:    */   public int getDamageToComponent(ItemStack aStack)
	/* 229:    */   {
	/* 230:181 */     return 0;
	/* 231:    */   }
	/* 232:    */   
	/* 233:    */   public int getAmountOfOutputs()
	/* 234:    */   {
	/* 235:186 */     return 1;
	/* 236:    */   }
	/* 237:    */   
	/* 238:    */   public boolean explodesOnComponentBreak(ItemStack aStack)
	/* 239:    */   {
	/* 240:191 */     return false;
	/* 241:    */   }
	/* 242:    */
	/* 21:   */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
	/* 22:   */   {
	/* 23:19 */     return new GT_MetaTileEntity_HeatExchanger(this.mName);
	/* 24:   */   }
	}
