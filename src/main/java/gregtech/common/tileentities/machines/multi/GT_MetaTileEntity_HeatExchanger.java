package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_HeatExchanger extends GT_MetaTileEntity_MultiBlockBase {

    private static boolean controller;
    public GT_MetaTileEntity_Hatch_Input mInputHotFluidHatch;
    public GT_MetaTileEntity_Hatch_Output mOutputColdFluidHatch;
    public boolean superheated = false;
    private float water;

    public GT_MetaTileEntity_HeatExchanger(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_HeatExchanger(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Heat Exchanger",
                "Size(WxHxD): 3x4x3, Controller (Front middle at bottom)",
                "3x3x4 of Stable Titanium Casing (hollow, Min 24!)",
                "2 Titanium Pipe Casing Blocks (Inside the Hollow Casing)",
                "1x Distillated Water Input (Any casing)",
                "min 1 Steam Output (Any casing)",
                "1x Hot Fluid Input (Bottom center)",
                "1x Cold Fluid Output (Top Center)",
                "1x Maintenance Hatch (Any casing)"};
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        superheated = aNBT.getBoolean("superheated");
        super.loadNBTData(aNBT);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setBoolean("superheated", superheated);
        super.saveNBTData(aNBT);
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeHeatExchanger.png");
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        if (mInputHotFluidHatch.getFluid() == null)
            return true;

        int fluidAmountToConsume = mInputHotFluidHatch.getFluidAmount(); // how much fluid is in hatch

        int superheated_threshold = 4000;   // default: must have 4000L per second to generate superheated steam
        float efficiency = 1f;              // default: operate at 100% efficiency with no integrated circuitry
        float penalty_per_config = 0.015f;  // penalize 1.5% efficiency per circuitry level (1-25)
        int shs_reduction_per_config = 150; // reduce threshold 150L/s per circuitry level (1-25)
        float steam_output_multiplier = 4f; // default: multiply output by 4
        float penalty = 0.0f;               // penalty to apply to output based on circuitry level (1-25).
        boolean do_lava = false;

        // Do we have an integrated circuit with a valid configuration?
        if (mInventory[1] != null && mInventory[1].getUnlocalizedName().startsWith("gt.integrated_circuit")) {
            int circuit_config = mInventory[1].getItemDamage();
            if (circuit_config >= 1 && circuit_config <= 25) {
                // If so, apply the penalty and reduced threshold.
                penalty = (circuit_config - 1) * penalty_per_config;
                superheated_threshold -= (shs_reduction_per_config * (circuit_config - 1));
            }
        }
        efficiency -= penalty;

        // If we're working with lava, adjust the threshold and multipliers accordingly.
        if (GT_ModHandler.isLava(mInputHotFluidHatch.getFluid())) {
            superheated_threshold /= 4;
            do_lava = true;
        } else if (mInputHotFluidHatch.getFluid().isFluidEqual(FluidRegistry.getFluidStack("ic2hotcoolant", 1))) {
            steam_output_multiplier = 2f;
        } else {
            // If we're working with neither, fail out
            return false;
        }

        superheated = fluidAmountToConsume >= superheated_threshold; // set the internal superheated flag if we have enough hot fluid.  Used in the onRunningTick method.
        fluidAmountToConsume = Math.min(fluidAmountToConsume, superheated_threshold * 2); // Don't consume too much hot fluid per second
        mInputHotFluidHatch.drain(fluidAmountToConsume, true);
        this.mMaxProgresstime = 20;
        this.mEUt = (int) (fluidAmountToConsume * steam_output_multiplier * efficiency);
        if (do_lava) {
            mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2pahoehoelava", fluidAmountToConsume), true);
            this.mEfficiencyIncrease = 80;
        } else {
            mOutputColdFluidHatch.fill(FluidRegistry.getFluidStack("ic2coolant", fluidAmountToConsume), true);
            this.mEfficiencyIncrease = 80;
        }
        return true;
    }

    private int useWater(float input) {
        water = water + input;
        int usage = (int) water;
        water = water - (int) usage;
        return usage;
    }

    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEUt > 0) {
            int tGeneratedEU = (int) (this.mEUt * 2L * this.mEfficiency / 10000L); // APPROXIMATELY how much steam to generate.
            if (tGeneratedEU > 0) {

                if (superheated)
                    tGeneratedEU /= 2; // We produce half as much superheated steam if necessary

                int distilledConsumed = useWater(tGeneratedEU / 160f); // how much distilled water to consume
                //tGeneratedEU = distilledConsumed * 160; // EXACTLY how much steam to generate, producing a perfect 1:160 ratio with distilled water consumption

                FluidStack distilledStack = GT_ModHandler.getDistilledWater(distilledConsumed);
                if (depleteInput(distilledStack)) // Consume the distilled water
                {
                    if (superheated) {
                        addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", tGeneratedEU)); // Generate superheated steam
                    } else {
                        addOutput(GT_ModHandler.getSteam(tGeneratedEU)); // Generate regular steam
                    }
                } else {
                    explodeMultiblock(); // Generate crater
                }
            }
            return true;
        }
        return true;
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;

        int tCasingAmount = 0;
        int tFireboxAmount = 0;
        controller = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i != 0) || (j != 0)) {
                    for (int k = 0; k <= 3; k++) {
                        if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50) && !addInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50) && !addMaintenanceToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), 50) && !ignoreController(aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j))) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getCasingBlock()) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getCasingMeta()) {
                                return false;
                            }
                            tCasingAmount++;
                        }
                    }
                } else {
                    if (!addHotFluidInputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j), 50)) {
                        return false;
                    }
                    if (!addColdFluidOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 3, zDir + j), 50)) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 1, zDir + j) != getPipeBlock()) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 1, zDir + j) != getPipeMeta()) {
                        return false;
                    }

                    if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 2, zDir + j) != getPipeBlock()) {
                        return false;
                    }
                    if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 2, zDir + j) != getPipeMeta()) {
                        return false;
                    }
                }
            }
        }
        return (tCasingAmount >= 24);
    }

    public boolean ignoreController(Block tTileEntity) {
        if (!controller && tTileEntity == GregTech_API.sBlockMachines) {
            return true;
        }
        return false;
    }

    public boolean addColdFluidOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Output) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).mMachineBlock = (byte) aBaseCasingIndex;
            mOutputColdFluidHatch = (GT_MetaTileEntity_Hatch_Output) aMetaTileEntity;
            return true;
        }
        return false;
    }

    public boolean addHotFluidInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).mMachineBlock = (byte) aBaseCasingIndex;
            ((GT_MetaTileEntity_Hatch_Input) aMetaTileEntity).mRecipeMap = getRecipeMap();
            mInputHotFluidHatch = (GT_MetaTileEntity_Hatch_Input) aMetaTileEntity;
            return true;
        }
        return false;
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getCasingMeta() {
        return 2;
    }

    public byte getCasingTextureIndex() {
        return 50;
    }

    public Block getPipeBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getPipeMeta() {
        return 14;
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
        return 1;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_HeatExchanger(this.mName);
    }
}