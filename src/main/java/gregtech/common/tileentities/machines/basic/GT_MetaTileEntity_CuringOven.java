package gregtech.common.tileentities.machines.basic;


import java.util.Iterator;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_CuringOven
        extends GT_MetaTileEntity_BasicMachine {
    private static int sHeaviestElementMass = 0;

    public GT_MetaTileEntity_CuringOven(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Producing Elemental Matter", 1, 1, "Replicator.png", "", new ITexture[]{new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_SIDE_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_SIDE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_FRONT")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_TOP_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_TOP")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_BOTTOM_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_BOTTOM"))});
    }

    public GT_MetaTileEntity_CuringOven(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Replicator(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public int checkRecipe() {
    	return 0;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (ItemList.Cell_Empty.isStackEqual(aStack));
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }
}
