package gregtech.common.tileentities.machines.basic;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_Scanner
        extends GT_MetaTileEntity_BasicMachine {
    public GT_MetaTileEntity_Scanner(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Scans Crops and other things.", 1, 1, "Scanner.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_SCANNER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_SCANNER)});
    }

    public GT_MetaTileEntity_Scanner(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Scanner(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public int checkRecipe() {
        ItemStack aStack = getInputAt(0);
        if (getOutputAt(0) != null) {
            this.mOutputBlocked += 1;
        } else if ((GT_Utility.isStackValid(aStack)) && (aStack.stackSize > 0)) {
            if ((getFillableStack() != null) && (getFillableStack().containsFluid(Materials.Honey.getFluid(100L)))) {
                try {
                    Object tIndividual = AlleleManager.alleleRegistry.getIndividual(aStack);
                    if (tIndividual != null) {
                        if (((IIndividual) tIndividual).analyze()) {
                            getFillableStack().amount -= 100;
                            this.mOutputItems[0] = GT_Utility.copy(new Object[]{aStack});
                            aStack.stackSize = 0;
                            NBTTagCompound tNBT = new NBTTagCompound();
                            ((IIndividual) tIndividual).writeToNBT(tNBT);
                            this.mOutputItems[0].setTagCompound(tNBT);
                            this.mMaxProgresstime = (500 / (1 << this.mTier - 1));
                            this.mEUt = (2 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                            return 2;
                        }
                        this.mOutputItems[0] = GT_Utility.copy(new Object[]{aStack});
                        aStack.stackSize = 0;
                        this.mMaxProgresstime = 1;
                        this.mEUt = 1;
                        return 2;
                    }
                } catch (Throwable e) {
                    if (GT_Values.D1) {
                        e.printStackTrace(GT_Log.err);
                    }
                }
            }
            if (ItemList.IC2_Crop_Seeds.isStackEqual(aStack, true, true)) {
                NBTTagCompound tNBT = aStack.getTagCompound();
                if (tNBT == null) {
                    tNBT = new NBTTagCompound();
                }
                if (tNBT.getByte("scan") < 4) {
                    tNBT.setByte("scan", (byte) 4);
                    this.mMaxProgresstime = (160 / (1 << this.mTier - 1));
                    this.mEUt = (8 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                } else {
                    this.mMaxProgresstime = 1;
                    this.mEUt = 1;
                }
                aStack.stackSize -= 1;
                this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{aStack});
                this.mOutputItems[0].setTagCompound(tNBT);
                return 2;
            }
            if (ItemList.Tool_DataOrb.isStackEqual(getSpecialSlot(), false, true)) {
                if (ItemList.Tool_DataOrb.isStackEqual(aStack, false, true)) {
                    aStack.stackSize -= 1;
                    this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{getSpecialSlot()});
                    this.mMaxProgresstime = (512 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
                ItemData tData = GT_OreDictUnificator.getAssociation(aStack);
                if ((tData != null) && ((tData.mPrefix == OrePrefixes.dust) || (tData.mPrefix == OrePrefixes.cell)) && (tData.mMaterial.mMaterial.mElement != null) && (!tData.mMaterial.mMaterial.mElement.mIsIsotope) && (tData.mMaterial.mMaterial != Materials.Magic) && (tData.mMaterial.mMaterial.getMass() > 0L)) {
                    getSpecialSlot().stackSize -= 1;
                    aStack.stackSize -= 1;

                    this.mOutputItems[0] = ItemList.Tool_DataOrb.get(1L, new Object[0]);
                    Behaviour_DataOrb.setDataTitle(this.mOutputItems[0], "Elemental-Scan");
                    Behaviour_DataOrb.setDataName(this.mOutputItems[0], tData.mMaterial.mMaterial.mElement.name());
                    this.mMaxProgresstime = ((int) (tData.mMaterial.mMaterial.getMass() * 8192L / (1 << this.mTier - 1)));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
            }
            if (ItemList.Tool_DataStick.isStackEqual(getSpecialSlot(), false, true)) {
                if (ItemList.Tool_DataStick.isStackEqual(aStack, false, true)) {
                    aStack.stackSize -= 1;
                    this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{getSpecialSlot()});
                    this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
                if (aStack.getItem() == Items.written_book) {
                    getSpecialSlot().stackSize -= 1;
                    aStack.stackSize -= 1;

                    this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{getSpecialSlot()});
                    this.mOutputItems[0].setTagCompound(aStack.getTagCompound());
                    this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
                if (aStack.getItem() == Items.filled_map) {
                    getSpecialSlot().stackSize -= 1;
                    aStack.stackSize -= 1;

                    this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{getSpecialSlot()});
                    this.mOutputItems[0].setTagCompound(GT_Utility.getNBTContainingShort(new NBTTagCompound(), "map_id", (short) aStack.getItemDamage()));
                    this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
            }
        }
        return 0;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mProgresstime >= (mMaxProgresstime - 1)) {
            try {
                if (this.mOutputItems[0].getUnlocalizedName().equals("gt.metaitem.01.32707")) {
                    GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "scanning");
                }
            } catch (Exception e) {
            }
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }


    public GT_Recipe.GT_Recipe_Map getRecipeList() {
        return GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes;
    }

    public int getCapacity() {
        return 1000;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
    }

    public void startSoundLoop(byte aIndex, double aX, double aY, double aZ) {
        super.startSoundLoop(aIndex, aX, aY, aZ);
        if (aIndex == 1) {
            GT_Utility.doSoundAtClient((String) GregTech_API.sSoundList.get(Integer.valueOf(212)), 10, 1.0F, aX, aY, aZ);
        }
    }

    public void startProcess() {
        sendLoopStart((byte) 1);
    }
}
