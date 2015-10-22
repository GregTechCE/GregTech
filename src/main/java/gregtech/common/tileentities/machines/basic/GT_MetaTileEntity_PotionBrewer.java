package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_PotionBrewer
        extends GT_MetaTileEntity_BasicMachine {
    public GT_MetaTileEntity_PotionBrewer(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Brewing your Drinks", 1, 0, "PotionBrewer.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_POTIONBREWER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_POTIONBREWER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_POTIONBREWER)});
    }

    public GT_MetaTileEntity_PotionBrewer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 0, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PotionBrewer(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public GT_Recipe.GT_Recipe_Map getRecipeList() {
        return GT_Recipe.GT_Recipe_Map.sBrewingRecipes;
    }

    public int checkRecipe() {
        int tCheck = super.checkRecipe();
        if (tCheck != 0) {
            return tCheck;
        }
        FluidStack aFluid = getFillableStack();
        if ((getDrainableStack() == null) && (aFluid != null) && (getInputAt(0) != null)) {
            String tInputName = aFluid.getFluid().getName();
            if (tInputName.startsWith("potion.")) {
                tInputName = tInputName.replaceFirst("potion.", "");
                int tFirstDot = tInputName.indexOf('.') + 1;
                String tModifier = tFirstDot <= 0 ? "" : tInputName.substring(tFirstDot);
                if (!tModifier.isEmpty()) {
                    tInputName = tInputName.replaceFirst("." + tModifier, "");
                }
                if (GT_Utility.areStacksEqual(new ItemStack(Items.fermented_spider_eye, 1, 0), getInputAt(0))) {
                    if (tInputName.equals("poison")) {
                        return setOutput("potion.damage" + tModifier);
                    }
                    if (tInputName.equals("health")) {
                        return setOutput("potion.damage" + tModifier);
                    }
                    if (tInputName.equals("waterbreathing")) {
                        return setOutput("potion.damage" + tModifier);
                    }
                    if (tInputName.equals("nightvision")) {
                        return setOutput("potion.invisibility" + tModifier);
                    }
                    if (tInputName.equals("fireresistance")) {
                        return setOutput("potion.slowness" + tModifier);
                    }
                    if (tInputName.equals("speed")) {
                        return setOutput("potion.slowness" + tModifier);
                    }
                    if (tInputName.equals("strength")) {
                        return setOutput("potion.weakness" + tModifier);
                    }
                    if (tInputName.equals("regen")) {
                        return setOutput("potion.poison" + tModifier);
                    }
                    return setOutput("potion.weakness");
                }
                if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L), getInputAt(0))) {
                    if (!tModifier.startsWith("strong")) {
                        return setOutput("potion." + tInputName + ".strong" + (tModifier.isEmpty() ? "" : new StringBuilder().append(".").append(tModifier).toString()));
                    }
                    if (tModifier.startsWith("long")) {
                        return setOutput("potion." + tInputName + tModifier.replaceFirst("long", ""));
                    }
                    return setOutput("potion.thick");
                }
                if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), getInputAt(0))) {
                    if (!tModifier.startsWith("long")) {
                        return setOutput("potion." + tInputName + ".long" + (tModifier.isEmpty() ? "" : new StringBuilder().append(".").append(tModifier).toString()));
                    }
                    if (tModifier.startsWith("strong")) {
                        return setOutput("potion." + tInputName + tModifier.replaceFirst("strong", ""));
                    }
                    return setOutput("potion.mundane");
                }
                if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L), getInputAt(0))) {
                    if (!tInputName.endsWith(".splash")) {
                        return setOutput("potion." + tInputName + ".splash");
                    }
                    return setOutput("potion.mundane");
                }
            }
        }
        return 0;
    }

    private final int setOutput(String aFluidName) {
        this.mOutputFluid = FluidRegistry.getFluidStack(aFluidName, 750);
        if (this.mOutputFluid == null) {
            this.mOutputFluid = FluidRegistry.getFluidStack("potion.mundane", getFillableStack().amount);
            getInputAt(0).stackSize -= 1;
            getFillableStack().amount = 0;
            this.mEUt = (4 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
            this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
            return 2;
        }
        if (getFillableStack().amount < 750) {
            return 0;
        }
        getInputAt(0).stackSize -= 1;
        getFillableStack().amount -= 750;
        this.mEUt = (4 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
        this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
        return 2;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return (aFluid.getFluid().getName().startsWith("potion.")) || (super.isFluidInputAllowed(aFluid));
    }

    public int getCapacity() {
        return 750;
    }
}
