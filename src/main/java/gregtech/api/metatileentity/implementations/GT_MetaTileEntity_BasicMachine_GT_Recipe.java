package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.*;
import gregtech.api.gui.GT_Container_BasicMachine;
import gregtech.api.gui.GT_GUIContainer_BasicMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_ModHandler.RecipeBits;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Locale;
import java.util.Random;

import static gregtech.api.enums.GT_Values.V;
import static gregtech.api.enums.GT_Values.W;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public class GT_MetaTileEntity_BasicMachine_GT_Recipe extends GT_MetaTileEntity_BasicMachine {
    private final GT_Recipe_Map mRecipes;
    private final int mTankCapacity, mSpecialEffect;
    private final String mSound;
    private final boolean mSharedTank, mRequiresFluidForFiltering;
    private final byte mGUIParameterA, mGUIParameterB;
    public GT_MetaTileEntity_BasicMachine_GT_Recipe(int aID, String aName, String aNameRegional, int aTier, String aDescription, GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int aTankCapacity, int aGUIParameterA, int aGUIParameterB, String aGUIName, String aSound, boolean aSharedTank, boolean aRequiresFluidForFiltering, int aSpecialEffect, String aOverlays, Object[] aRecipe) {
        super(aID, aName, aNameRegional, aTier, aRecipes.mAmperage, aDescription, aInputSlots, aOutputSlots, aGUIName, aRecipes.mNEIName, new ITexture[]{new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_SIDE_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_SIDE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_FRONT")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_TOP_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_TOP")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_BOTTOM_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/" + aOverlays.toLowerCase(Locale.ENGLISH) + "/OVERLAY_BOTTOM"))});
        mSharedTank = aSharedTank;
        mTankCapacity = aTankCapacity;
        mSpecialEffect = aSpecialEffect;
        mRequiresFluidForFiltering = aRequiresFluidForFiltering;
        mRecipes = aRecipes;
        mSound = aSound;
        mGUIParameterA = (byte) aGUIParameterA;
        mGUIParameterB = (byte) aGUIParameterB;

        if (aRecipe != null) {
            for (int i = 3; i < aRecipe.length; i++) {
                if (aRecipe[i] == X.CIRCUIT) {
                    aRecipe[i] = Tier.ELECTRIC[mTier].mManagingObject;
                    continue;
                }
                if (aRecipe[i] == X.BETTER_CIRCUIT) {
                    aRecipe[i] = Tier.ELECTRIC[mTier].mBetterManagingObject;
                    continue;
                }
                if (aRecipe[i] == X.HULL) {
                    aRecipe[i] = Tier.ELECTRIC[mTier].mHullObject;
                    continue;
                }
                if (aRecipe[i] == X.WIRE) {
                    aRecipe[i] = Tier.ELECTRIC[mTier].mConductingObject;
                    continue;
                }
                if (aRecipe[i] == X.WIRE4) {
                    aRecipe[i] = Tier.ELECTRIC[mTier].mLargerConductingObject;
                    continue;
                }

                if (aRecipe[i] == X.GLASS) {
                    switch (mTier) {
                        case 6:
                        case 7:
                        case 8:
                        	aRecipe[i] = Ic2Items.reinforcedGlass;
                        	break;
                        default:
                            aRecipe[i] = new ItemStack(Blocks.glass, 1, W);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.PLATE) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.Steel);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.Aluminium);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.StainlessSteel);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.Titanium);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.HSSE);
                            break;
                        case 8:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.Neutronium);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.plate.get(Materials.TungstenSteel);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.PIPE) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.Bronze);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.Steel);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.StainlessSteel);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.Titanium);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.pipeSmall.get(Materials.Ultimate);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.Ultimate);
                            break;
                        case 8:
                            aRecipe[i] = OrePrefixes.pipeLarge.get(Materials.Ultimate);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.pipeMedium.get(Materials.TungstenSteel);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.COIL_HEATING) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.AnyCopper);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.Cupronickel);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.Kanthal);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.Nichrome);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.Naquadah);
                            break;
                        case 8:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.NaquadahAlloy);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.wireGt08.get(Materials.Nichrome);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.COIL_HEATING_DOUBLE) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.AnyCopper);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.Cupronickel);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.Kanthal);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.Nichrome);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.HSSG);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.Naquadah);
                            break;
                        case 8:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.NaquadahAlloy);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.wireGt16.get(Materials.Nichrome);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.STICK_DISTILLATION) {
                    switch (mTier) {
                        default:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.Blaze);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.STICK_MAGNETIC) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.IronMagnetic);
                            break;
                        case 2:
                        case 3:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.SteelMagnetic);
                            break;
                        case 4:
                        case 5:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.NeodymiumMagnetic);
                            break;
                        case 6:
                        case 7:
                            aRecipe[i] = OrePrefixes.stickLong.get(Materials.NeodymiumMagnetic);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.block.get(Materials.NeodymiumMagnetic);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.STICK_ELECTROMAGNETIC) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.AnyIron);
                            break;
                        case 2:
                        case 3:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.Steel);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.Neodymium);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.stick.get(Materials.VanadiumGallium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.COIL_ELECTRIC) {
                    switch (mTier) {
                        case 0:
                            aRecipe[i] = OrePrefixes.wireGt01.get(Materials.Tin);
                            break;
                        case 1:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.Tin);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.wireGt02.get(Materials.AnyCopper);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.AnyCopper);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.wireGt08.get(Materials.AnnealedCopper);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.wireGt08.get(Materials.AnnealedCopper);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.wireGt04.get(Materials.YttriumBariumCuprate);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.wireGt08.get(Materials.Superconductor);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.wireGt16.get(Materials.Superconductor);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.ROBOT_ARM) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Robot_Arm_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Robot_Arm_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Robot_Arm_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Robot_Arm_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Robot_Arm_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Robot_Arm_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Robot_Arm_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Robot_Arm_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.PUMP) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Pump_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Pump_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Pump_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Pump_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Pump_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Pump_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Pump_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Pump_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.ROTOR) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Tin);
                            break;
                        case 2:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Bronze);
                            break;
                        case 3:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Steel);
                            break;
                        case 4:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.StainlessSteel);
                            break;
                        case 5:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.TungstenSteel);
                            break;
                        case 6:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Chrome);
                            break;
                        case 7:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Iridium);
                            break;
                        default:
                            aRecipe[i] = OrePrefixes.rotor.get(Materials.Osmium);
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.MOTOR) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Motor_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Motor_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Motor_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Motor_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Motor_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Motor_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Motor_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Motor_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.PISTON) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Electric_Piston_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Electric_Piston_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Electric_Piston_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Electric_Piston_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Electric_Piston_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Electric_Piston_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Electric_Piston_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Electric_Piston_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.CONVEYOR) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Conveyor_Module_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Conveyor_Module_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Conveyor_Module_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Conveyor_Module_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Conveyor_Module_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Conveyor_Module_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Conveyor_Module_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Conveyor_Module_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.EMITTER) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Emitter_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Emitter_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Emitter_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Emitter_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Emitter_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Emitter_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Emitter_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Emitter_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.SENSOR) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Sensor_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Sensor_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Sensor_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Sensor_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Sensor_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Sensor_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Sensor_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Sensor_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] == X.FIELD_GENERATOR) {
                    switch (mTier) {
                        case 0:
                        case 1:
                            aRecipe[i] = ItemList.Field_Generator_LV;
                            break;
                        case 2:
                            aRecipe[i] = ItemList.Field_Generator_MV;
                            break;
                        case 3:
                            aRecipe[i] = ItemList.Field_Generator_HV;
                            break;
                        case 4:
                            aRecipe[i] = ItemList.Field_Generator_EV;
                            break;
                        case 5:
                            aRecipe[i] = ItemList.Field_Generator_IV;
                            break;
                        case 6:
                            aRecipe[i] = ItemList.Field_Generator_LuV;
                            break;
                        case 7:
                            aRecipe[i] = ItemList.Field_Generator_ZPM;
                            break;
                        default:
                            aRecipe[i] = ItemList.Field_Generator_UV;
                            break;
                    }
                    continue;
                }

                if (aRecipe[i] instanceof X)
                    throw new IllegalArgumentException("MISSING TIER MAPPING FOR: " + aRecipe[i] + " AT TIER " + mTier);
            }

            if (!GT_ModHandler.addCraftingRecipe(getStackForm(1), RecipeBits.DISMANTLEABLE | RecipeBits.BUFFERED | RecipeBits.NOT_REMOVABLE | RecipeBits.REVERSIBLE, aRecipe)) {
                throw new IllegalArgumentException("INVALID CRAFTING RECIPE FOR: " + getStackForm(1).getDisplayName());
            }
        }
    }

    public GT_MetaTileEntity_BasicMachine_GT_Recipe(String aName, int aTier, String aDescription, GT_Recipe_Map aRecipes, int aInputSlots, int aOutputSlots, int aTankCapacity, int aAmperage, int aGUIParameterA, int aGUIParameterB, ITexture[][][] aTextures, String aGUIName, String aNEIName, String aSound, boolean aSharedTank, boolean aRequiresFluidForFiltering, int aSpecialEffect) {
        super(aName, aTier, aAmperage, aDescription, aTextures, aInputSlots, aOutputSlots, aGUIName, aNEIName);
        mSharedTank = aSharedTank;
        mTankCapacity = aTankCapacity;
        mSpecialEffect = aSpecialEffect;
        mRequiresFluidForFiltering = aRequiresFluidForFiltering;
        mRecipes = aRecipes;
        mSound = aSound;
        mGUIParameterA = (byte) aGUIParameterA;
        mGUIParameterB = (byte) aGUIParameterB;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_BasicMachine_GT_Recipe(mName, mTier, mDescription, mRecipes, mInputSlotCount, mOutputItems == null ? 0 : mOutputItems.length, mTankCapacity, mAmperage, mGUIParameterA, mGUIParameterB, mTextures, mGUIName, mNEIName, mSound, mSharedTank, mRequiresFluidForFiltering, mSpecialEffect);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_BasicMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_BasicMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), mGUIName, GT_Utility.isStringValid(mNEIName) ? mNEIName : getRecipeList() != null ? getRecipeList().mUnlocalizedName : "", mGUIParameterA, mGUIParameterB);
    }

	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		if (!super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) return false;
		if (mInventory[aIndex] != null) return true;
		switch (mInputSlotCount) {
		case  0: return false;
		case  1: return getFillableStack() == null ? !mRequiresFluidForFiltering && getRecipeList().containsInput(aStack) : null!=getRecipeList().findRecipe(getBaseMetaTileEntity(), mLastRecipe, true, V[mTier], new FluidStack[] {getFillableStack()}, getSpecialSlot(), new ItemStack[] {aStack});
		case  2: return (!mRequiresFluidForFiltering || getFillableStack() != null) && (((getInputAt(0)!=null&&getInputAt(1)!=null) || (getInputAt(0)==null&&getInputAt(1)==null?getRecipeList().containsInput(aStack):(getRecipeList().containsInput(aStack)&&null!=getRecipeList().findRecipe(getBaseMetaTileEntity(), mLastRecipe, true, V[mTier], new FluidStack[] {getFillableStack()}, getSpecialSlot(), aIndex == getInputSlot() ? new ItemStack[] {aStack, getInputAt(1)} : new ItemStack[] {getInputAt(0), aStack})))));
		default: return getRecipeList().containsInput(aStack);
		}
	}

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isClientSide() && aBaseMetaTileEntity.isActive()) {
            switch (mSpecialEffect) {
                case 0:
                    break;
                case 1:
                    if (aBaseMetaTileEntity.getFrontFacing() != 1 && aBaseMetaTileEntity.getCoverIDAtSide((byte) 1) == 0 && !aBaseMetaTileEntity.getOpacityAtSide((byte) 1)) {
                        Random tRandom = aBaseMetaTileEntity.getWorld().rand;
                        aBaseMetaTileEntity.getWorld().spawnParticle("smoke", aBaseMetaTileEntity.getXCoord() + 0.8F - tRandom.nextFloat() * 0.6F, aBaseMetaTileEntity.getYCoord() + 0.9F + tRandom.nextFloat() * 0.2F, aBaseMetaTileEntity.getZCoord() + 0.8F - tRandom.nextFloat() * 0.6F, 0.0D, 0.0D, 0.0D);
                    }
                    break;
            }
        }
    }

    @Override
    public GT_Recipe_Map getRecipeList() {
        return mRecipes;
    }

    @Override
    public int getCapacity() {
        return mTankCapacity;
    }

    @Override
    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 1 && GT_Utility.isStringValid(mSound)) GT_Utility.doSoundAtClient(mSound, 100, 1.0F, aX, aY, aZ);
    }

    @Override
    public void startProcess() {
        if (GT_Utility.isStringValid(mSound)) sendLoopStart((byte) 1);
    }

    @Override
    public FluidStack getFillableStack() {
        return mSharedTank ? getDrainableStack() : super.getFillableStack();
    }

    @Override
    public FluidStack setFillableStack(FluidStack aFluid) {
        return mSharedTank ? setDrainableStack(aFluid) : super.setFillableStack(aFluid);
    }

    @Override
    protected boolean displaysOutputFluid() {
        return !mSharedTank;
    }

    public static enum X {PUMP, WIRE, WIRE4, HULL, PIPE, GLASS, PLATE, MOTOR, ROTOR, SENSOR, PISTON, CIRCUIT, EMITTER, CONVEYOR, ROBOT_ARM, COIL_HEATING, COIL_ELECTRIC, STICK_MAGNETIC, STICK_DISTILLATION, BETTER_CIRCUIT, FIELD_GENERATOR, COIL_HEATING_DOUBLE, STICK_ELECTROMAGNETIC;}
}
