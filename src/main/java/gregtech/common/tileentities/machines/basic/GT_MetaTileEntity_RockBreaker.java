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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GT_MetaTileEntity_RockBreaker
        extends GT_MetaTileEntity_BasicMachine {
    public GT_MetaTileEntity_RockBreaker(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Put Lava and Water adjacent", 1, 1, "RockBreaker.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER)});
    }

    public GT_MetaTileEntity_RockBreaker(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_RockBreaker(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public GT_Recipe.GT_Recipe_Map getRecipeList() {
        return GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes;
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (getRecipeList().containsInput(aStack));
    }

    public int checkRecipe() {
        IGregTechTileEntity aBaseMetaTileEntity = getBaseMetaTileEntity();
        if ((aBaseMetaTileEntity.getBlockOffset(0, 0, 1) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(0, 0, -1) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(-1, 0, 0) == Blocks.water) || (aBaseMetaTileEntity.getBlockOffset(1, 0, 0) == Blocks.water)) {
            ItemStack tOutput = null;
            if (aBaseMetaTileEntity.getBlockOffset(0, 1, 0) == Blocks.lava) {
                tOutput = new ItemStack(Blocks.stone, 1);
            } else if ((aBaseMetaTileEntity.getBlockOffset(0, 0, 1) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(0, 0, -1) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(-1, 0, 0) == Blocks.lava) || (aBaseMetaTileEntity.getBlockOffset(1, 0, 0) == Blocks.lava)) {
                tOutput = new ItemStack(Blocks.cobblestone, 1);
            }
            if (tOutput != null) {
                if (GT_Utility.areStacksEqual(getInputAt(0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L))) {
                    tOutput = new ItemStack(Blocks.obsidian, 1);
                    if (canOutput(new ItemStack[]{tOutput})) {
                        getInputAt(0).stackSize -= 1;
                        this.mOutputItems[0] = tOutput;
                        this.mMaxProgresstime = (128 / (1 << this.mTier - 1));
                        this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                        return 2;
                    }
                } else if (canOutput(new ItemStack[]{tOutput})) {
                    this.mOutputItems[0] = tOutput;
                    this.mMaxProgresstime = (16 / (1 << this.mTier - 1));
                    this.mEUt = (32 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                    return 2;
                }
            }
        }
        return 0;
    }
}
