package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_AssemblyLine
        extends GT_MetaTileEntity_MultiBlockBase {
    public GT_MetaTileEntity_AssemblyLine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_AssemblyLine(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_AssemblyLine(this.mName);
    }

    public String[] getDescription() {
        return new String[]{"Assembly Line",
                "Size: 3x(5-16)x4, variable length",
                "Bottom: Steel Casing(or Maintenance or Input Hatch),",
                "Input Bus(Last Output Bus), Steel Casing",
                "Middle: Reinforced Glass, Assembly Line, Reinforced Glass",
                "UpMiddle: Grate Casing, Assembling Casing,",
                "Grate Casing(or Controller)",
                "Top: Steel Casing(or Energy Hatch)",
                "Up to 16 repeating slices, last is Output Bus"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ASSEMBLY_LINE)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AssemblyLine.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
    	 if(!GT_Utility.isStackValid(mInventory[1]) && !ItemList.Tool_DataStick.isStackEqual(mInventory[1], false, true))return false;
    	NBTTagCompound tTag = mInventory[1].getTagCompound();
    	if(tTag==null)return false;
    	ItemStack tStack[] = new ItemStack[15];
    	for(int i = 0;i<15;i++){
    		if(tTag.hasKey(""+i)){
    		tStack[i] = GT_Utility.loadItem(tTag, ""+i);
    		if(tStack[i]!=null){
    			if(mInputBusses.get(i)==null)return false;
    			if(GT_Utility.areStacksEqual(tStack[i],mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0),true) && tStack[i].stackSize <= mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0).stackSize){
    			}else{return false;}
    		}}
    	}
    	FluidStack[] tFluids = new FluidStack[4];
    	for(int i = 0;i<4;i++){
    		if(tTag.hasKey("f"+i)){
    			tFluids[i] = GT_Utility.loadFluid(tTag, "f"+i);
    			if(tFluids[i]!=null){
    				if(mInputHatches.get(i)==null)return false;
    				if(mInputHatches.get(i).mFluid!=null && GT_Utility.areFluidsEqual(mInputHatches.get(i).mFluid, tFluids[i], true) && mInputHatches.get(i).mFluid.amount>=tFluids[i].amount){
    				}else{return false;}
    			}
    		}
    	}
    	if(tTag.hasKey("output")){
    		mOutputItems = new ItemStack[]{GT_Utility.loadItem(tTag, "output")};
    		if(mOutputItems==null||mOutputItems[0]==null||!GT_Utility.isStackValid(mOutputItems[0]))return false;
    	}else{return false;}
    	if(tTag.hasKey("time")){
    		mMaxProgresstime = tTag.getInteger("time");
    		if(mMaxProgresstime<=0)return false;
    	}else{return false;}
    	if(tTag.hasKey("eu")){
    		mEUt = tTag.getInteger("eu");
    	}else{return false;}
    	for(int i = 0;i<15;i++){
    		if(tStack[i]!=null){
    			mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0).stackSize -= tStack[i].stackSize;
    			if(mInputBusses.get(i).getBaseMetaTileEntity().getStackInSlot(0).stackSize <= 0){
    			}
    		}
    	}
    	
    	for(int i = 0;i<4;i++){
    		if(tFluids[i]!=null){
    			mInputHatches.get(i).mFluid.amount -= tFluids[i].amount;
    			if(mInputHatches.get(i).mFluid.amount<=0){
    				mInputHatches.get(i).mFluid = null;
    			}
    		}
    	}
    	byte tTier = (byte) Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        if (mEUt <= 16) {
            this.mEUt = (mEUt * (1 << tTier - 1) * (1 << tTier - 1));
            this.mMaxProgresstime = (mMaxProgresstime / (1 << tTier - 1));
        } else {
            while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                this.mEUt *= 4;
                this.mMaxProgresstime /= 2;
            }
        }
        this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
        updateSlots();
        return true;
    }

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 20) {
            GT_Utility.doSoundAtClient((String) GregTech_API.sSoundList.get(212), 10, 1.0F, aX, aY, aZ);
        }
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        if (xDir != 0) {
            for(int r = 0; r <= 16; r++){
                int i = r*xDir;

                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(0, 0, i)!=GregTech_API.sBlockCasings3&&aBaseMetaTileEntity.getMetaIDOffset(0, 0, i)!=10){return false;}
                if(!aBaseMetaTileEntity.getBlockOffset(0, -1, i).getUnlocalizedName().equals("blockAlloyGlass")){return false;}
                IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(0, -2, i);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))){
                    if (aBaseMetaTileEntity.getBlockOffset(0, -2, i) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(0, -2, i) != 0) {return false;}
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, 1, i);
                if (!addEnergyInputToMachineList(tTileEntity, 16)){
                    if (aBaseMetaTileEntity.getBlockOffset(xDir, 1, i) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir, 1, i) != 0) {return false;}
                }
                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(xDir, 0, i)!=GregTech_API.sBlockCasings2&&aBaseMetaTileEntity.getMetaIDOffset(xDir, 0, i)!=9){return false;}
                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(xDir,-1, i)!=GregTech_API.sBlockCasings2&&aBaseMetaTileEntity.getMetaIDOffset(xDir,-1, i)!=5){return false;}


                if(aBaseMetaTileEntity.getBlockOffset(xDir*2, 0, i)!=GregTech_API.sBlockCasings3&&aBaseMetaTileEntity.getMetaIDOffset(xDir*2, 0, i)!=10){return false;}
                if(!aBaseMetaTileEntity.getBlockOffset(xDir*2, -1, i).getUnlocalizedName().equals("blockAlloyGlass")){return false;}
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir*2, -2, i);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))){
                    if (aBaseMetaTileEntity.getBlockOffset(xDir*2, -2, i) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir*2, -2, i) != 0) {return false;}
                }
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir, -2, i);
                if (!addInputToMachineList(tTileEntity, 16)){
                    if (!addOutputToMachineList(tTileEntity, 16)){
                    }else{if(r>0){return mEnergyHatches.size()>0;}else{return false;}}
                }
            }
        }else{
            for(int r = 0; r <= 16; r++){
                int i = r*-zDir;

                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(i, 0, 0)!=GregTech_API.sBlockCasings3&&aBaseMetaTileEntity.getMetaIDOffset(i, 0, 0)!=10){return false;}
                if(!aBaseMetaTileEntity.getBlockOffset(i, -1, 0).getUnlocalizedName().equals("blockAlloyGlass")){return false;}
                IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, 0);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))){
                    if (aBaseMetaTileEntity.getBlockOffset(i, -2, 0) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(i, -2, 0) != 0) {return false;}
                }

                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, 1, zDir);
                if (!addEnergyInputToMachineList(tTileEntity, 16)){
                    if (aBaseMetaTileEntity.getBlockOffset(i, 1, zDir) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(i, 1, zDir) != 0) {return false;}
                }
                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(i, 0, zDir)!=GregTech_API.sBlockCasings2&&aBaseMetaTileEntity.getMetaIDOffset(i, 0, zDir)!=9){return false;}
                if(i!=0&&aBaseMetaTileEntity.getBlockOffset(i,-1, zDir)!=GregTech_API.sBlockCasings2&&aBaseMetaTileEntity.getMetaIDOffset(i,-1, zDir)!=5){return false;}


                if(aBaseMetaTileEntity.getBlockOffset(i, 0, zDir*2)!=GregTech_API.sBlockCasings3&&aBaseMetaTileEntity.getMetaIDOffset(i, 0, zDir*2)!=10){return false;}
                if(!aBaseMetaTileEntity.getBlockOffset(i, -1, zDir*2).getUnlocalizedName().equals("blockAlloyGlass")){return false;}
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, zDir*2);
                if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16))){
                    if (aBaseMetaTileEntity.getBlockOffset(i, -2, zDir*2) != GregTech_API.sBlockCasings2) {return false;}
                    if (aBaseMetaTileEntity.getMetaIDOffset(i, -2, zDir*2) != 0) {return false;}
                }
                tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(i, -2, zDir);
                if (!addInputToMachineList(tTileEntity, 16)){
                    if (!addOutputToMachineList(tTileEntity, 16)){
                    }else{if(r>0){return mEnergyHatches.size()>0;}else{return false;}}
                }
            }
        }
        return false;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public int getAmountOfOutputs() {
        return 2;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}