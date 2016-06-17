package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;

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
        return new String[]{"Assembly line",
                "Size: 3x(2-16)x4, variable length",
                "Bottom: Steel Casing(or Maintenance or Input Hatch), Input Bus(Last Output Bus), Steel Casing",
                "Middle: Reinforced Glass, Assembling Line, Reinforced Glass",
                "UpMiddle: Grate Casing(or Controller), Assembling Casing, Grate Casing",
                "Top: Steel Casing(or Energy Hatch)",
                "Up to 16 repeating slices, last is Output Bus"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_IMPLOSION_COMPRESSOR_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_IMPLOSION_COMPRESSOR)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "ImplosionCompressor.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sImplosionRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<ItemStack> tInputList = getStoredInputs();
        for (int i = 0; i < tInputList.size() - 1; i++) {
            for (int j = i + 1; j < tInputList.size(); j++) {
                if (GT_Utility.areStacksEqual((ItemStack) tInputList.get(i), (ItemStack) tInputList.get(j))) {
                    if (((ItemStack) tInputList.get(i)).stackSize >= ((ItemStack) tInputList.get(j)).stackSize) {
                        tInputList.remove(j--);
                    } else {
                        tInputList.remove(i--);
                        break;
                    }
                }
            }
        }
        ItemStack[] tInputs = (ItemStack[]) Arrays.copyOfRange(tInputList.toArray(new ItemStack[tInputList.size()]), 0, 2);

        ArrayList<FluidStack> tFluidList = getStoredFluids();
        for (int i = 0; i < tFluidList.size() - 1; i++) {
            for (int j = i + 1; j < tFluidList.size(); j++) {
                if (GT_Utility.areFluidsEqual((FluidStack) tFluidList.get(i), (FluidStack) tFluidList.get(j))) {
                    if (((FluidStack) tFluidList.get(i)).amount >= ((FluidStack) tFluidList.get(j)).amount) {
                        tFluidList.remove(j--);
                    } else {
                        tFluidList.remove(i--);
                        break;
                    }
                }
            }
        }
        FluidStack[] tFluids = (FluidStack[]) Arrays.copyOfRange(tFluidList.toArray(new FluidStack[tInputList.size()]), 0, 1);
        if (tInputList.size() > 0) {
            long tVoltage = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sAssemblylineRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], tFluids, tInputs);
            if ((tRecipe != null) && (tRecipe.isRecipeInputEqual(true, tFluids, tInputs))) {
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;
                if (tRecipe.mEUt <= 16) {
                    this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
                    this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
                } else {
                    this.mEUt = tRecipe.mEUt;
                    this.mMaxProgresstime = tRecipe.mDuration;
                    while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                        this.mEUt *= 4;
                        this.mMaxProgresstime /= 2;
                    }
                }
                if (this.mEUt > 0) {
                    this.mEUt = (-this.mEUt);
                }
                this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                this.mOutputItems = new ItemStack[]{tRecipe.getOutput(0), tRecipe.getOutput(1)};
                updateSlots();
                return true;
            }
        }
        return false;
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
                    if (!addOutputToMachineList(tTileEntity, 16)){;return false;
                    }else{if(r>0){return true;}else{return false;}}
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
                    }else{if(r>0){return true;}else{return false;}}
                }
            }
        }
        return false;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 1000;
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