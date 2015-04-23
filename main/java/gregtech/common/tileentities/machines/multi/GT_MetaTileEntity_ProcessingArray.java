package gregtech.common.tileentities.machines.multi;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine_GT_Recipe;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;

public class GT_MetaTileEntity_ProcessingArray extends GT_MetaTileEntity_MultiBlockBase{
	
				/*  22:    */   public GT_MetaTileEntity_ProcessingArray(int aID, String aName, String aNameRegional)
				/*  23:    */   {
				/*  24: 24 */     super(aID, aName, aNameRegional);
				/*  25:    */   }
				/*  26:    */   
				/*  27:    */   public GT_MetaTileEntity_ProcessingArray(String aName)
				/*  28:    */   {
				/*  29: 28 */     super(aName);
				/*  30:    */   }
				/*  31:    */   
				/*  32:    */   public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity)
				/*  33:    */   {
				/*  34: 33 */     return new GT_MetaTileEntity_ProcessingArray(this.mName);
				/*  35:    */   }
				/*  36:    */   
				/*  37:    */   public String[] getDescription()
				/*  38:    */   {
				/*  39: 38 */     return new String[] { "Controller Block for the Processing Array", "Size: 3x3x3 (Hollow)", "Controller (front centered)", "1x Input (anywhere)", "1x Output (anywhere)", "1x Energy Hatch (anywhere)", "1x Maintenance Hatch (anywhere)", "Robust Tungstensteel Casings for the rest (16 at least!)","Put up to 16 Basic Machines into the GUI Inventory" };
				/*  40:    */   }
				/*  41:    */   
				/*  42:    */   public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone)
				/*  43:    */   {
				/*  44: 43 */     if (aSide == aFacing) {
				/*  45: 43 */       return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[48], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER) };
				/*  46:    */     }
				/*  47: 44 */     return new ITexture[] { Textures.BlockIcons.CASING_BLOCKS[48] };
				/*  48:    */   }
				/*  49:    */   
				/*  50:    */   public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity)
				/*  51:    */   {
				/*  52: 49 */     return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "VacuumFreezer.png");
				/*  53:    */   }
				/*  54:    */   
				/*  55:    */   public GT_Recipe.GT_Recipe_Map getRecipeMap()
				/*  56:    */   {
									String tmp = mInventory[1].getUnlocalizedName().replaceAll("gt.blockmachines.basicmachine.", "");
										if(tmp.startsWith("centrifuge")){
											return GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes;
										}else if(tmp.equals("")){
											
										}
									
				/*  57: 54 */     return null;
				/*  58:    */   }
				/*  59:    */   
				/*  60:    */   public boolean isCorrectMachinePart(ItemStack aStack)
				/*  61:    */   {
										if(aStack!=null&&aStack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.")){
					/*  62: 59 */     return true;}
									return false;
				/*  63:    */   }
				/*  64:    */   
				/*  65:    */   public boolean isFacingValid(byte aFacing)
				/*  66:    */   {
				/*  67: 62 */     return aFacing > 1;
				/*  68:    */   }
				/*  69:    */   
								GT_Recipe mLastRecipe;
				/*  70:    */   public boolean checkRecipe(ItemStack aStack){
									if(!isCorrectMachinePart(mInventory[1])){return false;}
									GT_Recipe.GT_Recipe_Map map = getRecipeMap();
									if(map==null){return false;}
				/*  72: 66 */     ArrayList<ItemStack> tInputList = getStoredInputs();
				/*  75: 69 */       long tVoltage = getMaxInputVoltage();
				/*  76: 70 */       int tTier = 0;
									if(mInventory[1].getUnlocalizedName().endsWith("1")){tTier=1;}
									if(mInventory[1].getUnlocalizedName().endsWith("2")){tTier=2;}
									if(mInventory[1].getUnlocalizedName().endsWith("3")){tTier=3;}
									if(mInventory[1].getUnlocalizedName().endsWith("4")){tTier=4;}
									if(mInventory[1].getUnlocalizedName().endsWith("5")){tTier=5;}
									FluidStack fluidIn = null;
									if(mInputHatches !=null&&mInputHatches.size()>0){
										fluidIn = mInputHatches.get(0).mFluid;
									}
									ItemStack tInput =null;
									if(tInputList.size()>0){
									tInput = tInputList.get(0); }
				/*  78: 72 */       GT_Recipe tRecipe = map.findRecipe(getBaseMetaTileEntity(), mLastRecipe, false, gregtech.api.enums.GT_Values.V[tTier], new FluidStack[]{fluidIn}, new ItemStack[] { tInput });
				/*  79: 73 */       if (tRecipe != null) {
										mLastRecipe = tRecipe;
										int machines = Math.max(16,mInventory[1].stackSize);										
				/*  81:    */         if(tRecipe.mFluidOutputs.length>0&&tRecipe.mFluidInputs.length>0&&((mInputHatches.get(0).mFluid.amount)>(tRecipe.mFluidInputs[0].amount*machines))){
										mInputHatches.get(0).mFluid.amount -= tRecipe.mFluidInputs[0].amount*machines;
				/*  82: 74 */           this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
				/*  83: 75 */           this.mEfficiencyIncrease = 10000;
				/*  84: 77 */           if (tRecipe.mEUt <= 16)
				/*  85:    */           {
				/*  86: 78 */             this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1))*machines;
				/*  87: 79 */             this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
				/*  88:    */           }
				/*  89:    */           else
				/*  90:    */           {
				/*  91: 81 */             this.mEUt = tRecipe.mEUt*machines;
				/*  92: 82 */             this.mMaxProgresstime = tRecipe.mDuration;
				/*  93: 83 */             while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)])
				/*  94:    */             {
				/*  95: 84 */               this.mEUt *= 4;
				/*  96: 85 */               this.mMaxProgresstime /= 2;
				/*  97:    */             }
				/*  98:    */           }
				/*  99: 89 */           if (this.mEUt > 0) {
				/* 100: 89 */             this.mEUt = (-this.mEUt);
				/* 101:    */           }
				/* 102: 90 */           this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
				/* 103: 91 */           this.mOutputFluids = new FluidStack[] { tRecipe.getFluidOutput(0) };
										this.mOutputFluids[0].amount *=machines;
				/* 104: 92 */           updateSlots();}
				/* 105: 93 */           return true;
				/* 106:    */         
				/* 107:    */       }
				/* 109: 96 */     return false;
				/* 110:    */   }
				/* 111:    */   
				/* 112:    */   public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack)
				/* 113:    */   {
				/* 114:101 */     int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
				/* 115:103 */     if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
				/* 116:103 */       return false;
				/* 117:    */     }
				/* 118:105 */     int tAmount = 0;
				/* 119:107 */     for (int i = -1; i < 2; i++) {
				/* 120:107 */       for (int j = -1; j < 2; j++) {
				/* 121:107 */         for (int h = -1; h < 2; h++) {
				/* 122:107 */           if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0))))
				/* 123:    */           {
				/* 124:108 */             IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
				/* 125:109 */             if ((!addMaintenanceToMachineList(tTileEntity, 48)) && (!addInputToMachineList(tTileEntity, 48)) && (!addOutputToMachineList(tTileEntity, 48)) && (!addEnergyInputToMachineList(tTileEntity, 48)))
				/* 126:    */             {
				/* 127:110 */               if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings4) {
				/* 128:110 */                 return false;
				/* 129:    */               }
				/* 130:111 */               if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 0) {
				/* 131:111 */                 return false;
				/* 132:    */               }
				/* 133:112 */               tAmount++;
				/* 134:    */             }
				/* 135:    */           }
				/* 136:    */         }
				/* 137:    */       }
				/* 138:    */     }
				/* 139:116 */     return tAmount >= 16;
				/* 140:    */   }
				/* 141:    */   
				/* 142:    */   public int getMaxEfficiency(ItemStack aStack){return 10000;}
				/* 147:    */   public int getPollutionPerTick(ItemStack aStack){return 0;}
				/* 152:    */   public int getDamageToComponent(ItemStack aStack){return 0;}
				/* 157:    */   public int getAmountOfOutputs(){return 1;}
				/* 162:    */   public boolean explodesOnComponentBreak(ItemStack aStack){return false;}
				/* 166:    */ }