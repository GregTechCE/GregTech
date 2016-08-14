package gregtech.common.tileentities.machines.basic;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Iterator;

public class GT_MetaTileEntity_Replicator
        extends GT_MetaTileEntity_BasicMachine {
    private static int sHeaviestElementMass = 0;

    public GT_MetaTileEntity_Replicator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Producing Elemental Matter", 1, 1, "Replicator.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_REPLICATOR), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_REPLICATOR_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_REPLICATOR)});
    }

    public GT_MetaTileEntity_Replicator(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Replicator(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public int checkRecipe() {
        FluidStack tFluid = getFillableStack();
        if ((tFluid != null) && (tFluid.isFluidEqual(Materials.UUMatter.getFluid(1L)))) {
            ItemStack tDataOrb = getSpecialSlot();
            if ((ItemList.Tool_DataOrb.isStackEqual(tDataOrb, false, true)) && (Behaviour_DataOrb.getDataTitle(tDataOrb).equals("Elemental-Scan"))) {
                Materials tMaterial = (Materials) Element.get(Behaviour_DataOrb.getDataName(tDataOrb)).mLinkedMaterials.get(0);
                long tMass = tMaterial.getMass();
                if ((tFluid.amount >= tMass) && (tMass > 0L)) {
                    this.mEUt = ((int) gregtech.api.enums.GT_Values.V[this.mTier]);
                    this.mMaxProgresstime = ((int) (tMass * 512L / (1 << this.mTier - 1)));
                    if ((this.mOutputItems[0] = GT_OreDictUnificator.get(OrePrefixes.dust, tMaterial, 1L)) == null) {
                        if ((this.mOutputItems[0] = GT_OreDictUnificator.get(OrePrefixes.cell, tMaterial, 1L)) != null) {
                            if ((this.mOutputFluid = GT_Utility.getFluidForFilledItem(this.mOutputItems[0], true)) == null) {
                                if (ItemList.Cell_Empty.isStackEqual(getInputAt(0))) {
                                    if (canOutput(new ItemStack[]{this.mOutputItems[0]})) {
                                        getInputAt(0).stackSize -= 1;
                                        FluidStack
                                                tmp231_230 = tFluid;
                                        tmp231_230.amount = ((int) (tmp231_230.amount - tMass));
                                        return 2;
                                    }
                                }
                            } else {
                                this.mOutputItems[0] = null;
                                if ((getDrainableStack() == null) || ((getDrainableStack().isFluidEqual(this.mOutputFluid)) && (getDrainableStack().amount < 16000))) {
                                    FluidStack tmp287_286 = tFluid;
                                    tmp287_286.amount = ((int) (tmp287_286.amount - tMass));
                                    return 2;
                                }
                            }
                        }
                    } else if (canOutput(new ItemStack[]{this.mOutputItems[0]})) {
                        FluidStack tmp322_321 = tFluid;
                        tmp322_321.amount = ((int) (tmp322_321.amount - tMass));
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (ItemList.Cell_Empty.isStackEqual(aStack));
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return aFluid.isFluidEqual(Materials.UUMatter.getFluid(1L));
    }

    public int getCapacity() {
        if ((sHeaviestElementMass == 0) && (GregTech_API.sPostloadFinished)) {
            Materials tMaterial;
            for (Iterator i$ = Materials.VALUES.iterator(); i$.hasNext(); sHeaviestElementMass = Math.max(sHeaviestElementMass, (int) tMaterial.getMass())) {
                tMaterial = (Materials) i$.next();
            }
        }
        return sHeaviestElementMass;
    }
}
