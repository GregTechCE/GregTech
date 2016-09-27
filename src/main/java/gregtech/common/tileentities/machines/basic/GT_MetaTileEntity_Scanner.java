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
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

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
            if (getSpecialSlot() == null && ItemList.Tool_DataStick.isStackEqual(aStack, false, true)) {
                if (GT_Utility.ItemNBT.getBookTitle(aStack).equals("Raw Prospection Data")) {
                    GT_Utility.ItemNBT.setBookTitle(aStack, "Analyzed Prospection Data");
                    GT_Utility.ItemNBT.convertProspectionData(aStack);
                    aStack.stackSize -= 1;

                    this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{aStack});
                    this.mMaxProgresstime = (1000 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;

                }
            }
            if(ItemList.Tool_DataStick.isStackEqual(getSpecialSlot(), false, true)&& aStack !=null){
            	for(GT_Recipe.GT_Recipe_AssemblyLine tRecipe:GT_Recipe.GT_Recipe_AssemblyLine.sAssemblylineRecipes){
            	if(GT_Utility.areStacksEqual(tRecipe.mResearchItem, aStack, true)){
            	
            	this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{getSpecialSlot()});
            	getSpecialSlot().stackSize -= 1;
                GT_Utility.ItemNBT.setBookTitle(this.mOutputItems[0], GT_LanguageManager.getTranslation(tRecipe.mOutput.getDisplayName())+" Construction Data");

                NBTTagCompound tNBT = this.mOutputItems[0].getTagCompound();
                if (tNBT == null) {
                    tNBT = new NBTTagCompound();
                }     
                tNBT.setTag("output", tRecipe.mOutput.writeToNBT(new NBTTagCompound()));
                tNBT.setInteger("time", tRecipe.mDuration);
                tNBT.setInteger("eu", tRecipe.mEUt);
                for(int i = 0 ; i < tRecipe.mInputs.length ; i++){
                	
                	tNBT.setTag(""+i, tRecipe.mInputs[i].writeToNBT(new NBTTagCompound()));
                }
                for(int i = 0 ; i < tRecipe.mFluidInputs.length ; i++){
                	
                	tNBT.setTag("f"+i, tRecipe.mFluidInputs[i].writeToNBT(new NBTTagCompound()));
                }
                
                tNBT.setString("author", "Assembly Line Recipe Generator");
                NBTTagList tNBTList = new NBTTagList();
                tNBTList.appendTag(new NBTTagString("Constructionplan for "+tRecipe.mOutput.stackSize+" "+GT_LanguageManager.getTranslation(tRecipe.mOutput.getDisplayName())+". Needed EU/t: "+tRecipe.mEUt+" Productiontime: "+(tRecipe.mDuration/20)));
                for(int i=0;i<tRecipe.mInputs.length;i++){
                	if(tRecipe.mInputs[i]!=null){
                		tNBTList.appendTag(new NBTTagString("Input Bus "+(i+1)+": "+tRecipe.mInputs[i].stackSize+" "+GT_LanguageManager.getTranslation(tRecipe.mInputs[i].getDisplayName())));
                	}
                }
                for(int i=0;i<tRecipe.mFluidInputs.length;i++){
                	if(tRecipe.mFluidInputs[i]!=null){
                		tNBTList.appendTag(new NBTTagString("Input Hatch "+(i+1)+": "+tRecipe.mFluidInputs[i].amount+"L "+GT_LanguageManager.getTranslation(tRecipe.mFluidInputs[i].getLocalizedName())));
                	}
                }
                tNBT.setTag("pages", tNBTList);
                
                this.mOutputItems[0].setTagCompound(tNBT);
                
                aStack.stackSize -= 1;
                this.mMaxProgresstime = (tRecipe.mResearchTime / (1 << this.mTier - 1));
                this.mEUt = (30 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
            	getSpecialSlot().stackSize -= 1;
            	return 2;
            	}
            	}
            }

        }
        return 0;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mProgresstime >= (mMaxProgresstime - 1)) {
            if ((this.mOutputItems[0] != null) && (this.mOutputItems[0].getUnlocalizedName().equals("gt.metaitem.01.32707"))) {
                GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "scanning");
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
