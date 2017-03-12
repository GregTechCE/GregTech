package gregtech.jei;

import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import mezz.jei.Internal;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class JEIGregtechRecipe implements IRecipeWrapper {

    private IGuiHelper jeiHelper;

    protected final GT_Recipe.GT_Recipe_Map mRecipeMap;
    protected final GT_Recipe mRecipe;

    public JEIGregtechRecipe(GT_Recipe.GT_Recipe_Map mRecipeMap, GT_Recipe mRecipe) {
        this.mRecipe = mRecipe;
        this.mRecipeMap = mRecipeMap;
        this.jeiHelper = Internal.getHelpers().getGuiHelper();
        jeiHelper.getSlotDrawable();
    }

    public void init(IRecipeLayout layout) {
        int tStartIndex = 0;
        IGuiItemStackGroup mInputs = layout.getItemStacks();
        IGuiFluidStackGroup mFluids = layout.getFluidStacks();

        switch (mRecipeMap.mUsualInputCount) {
            case 0:
                break;
            case 1:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 2:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));;
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 3:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 4:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 5:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 6:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 7:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            case 8:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                break;
            default:
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 12 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 30 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
                if (mRecipe.getNEIAdaptedInput(tStartIndex) != null) {
                    mInputs.init(tStartIndex, true, 48 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getNEIAdaptedInput(tStartIndex));
                }
                tStartIndex++;
        }
        if (mRecipe.mSpecialItems != null) {
            mInputs.init(100, false, 120 + 1, 52);
            if(mRecipe.mSpecialItems instanceof Collection)
                mInputs.set(100, (Collection<ItemStack>) mRecipe.mSpecialItems);
            else if(mRecipe.mSpecialItems instanceof ItemStack)
                mInputs.set(100, (ItemStack) mRecipe.mSpecialItems);
        }
        tStartIndex = 1000;
        switch (mRecipeMap.mUsualOutputCount) {
            case 0:
                break;
            case 1:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 2:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 3:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 4:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 5:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 6:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 5);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 23);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 7:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            case 8:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 24);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                break;
            default:
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, -4);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 14);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 102 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 120 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
                if (mRecipe.getOutput(tStartIndex - 1000) != null) {
                    mInputs.init(tStartIndex, false, 138 + 1, 32);
                    mInputs.set(tStartIndex, mRecipe.getOutput(tStartIndex - 1000));
                }
                tStartIndex++;
        }



        if ((mRecipe.mFluidInputs.length > 0) && (mRecipe.mFluidInputs[0] != null) && (mRecipe.mFluidInputs[0].getFluid() != null)) {
            mFluids.init(0, true, 49 + 1, 52 + 1, 16, 16, mRecipe.mFluidInputs[0].amount, true, null);
            mFluids.set(0, mRecipe.mFluidInputs[0]);
            if ((mRecipe.mFluidInputs.length > 1) && (mRecipe.mFluidInputs[1] != null) && (mRecipe.mFluidInputs[1].getFluid() != null)) {
                mFluids.init(1, true, 30 + 2, 52 + 1, 16, 16, mRecipe.mFluidInputs[1].amount, true, null);
                mFluids.set(1, mRecipe.mFluidInputs[1]);
            }
        }
        if (mRecipe.mFluidOutputs.length > 1) {
            if (mRecipe.mFluidOutputs[0] != null && (mRecipe.mFluidOutputs[0].getFluid() != null)) {
                mFluids.init(2, false, 120 + 2, 5 + 1, 16, 16, mRecipe.mFluidOutputs[0].amount, true, null);
                mFluids.set(2, mRecipe.mFluidOutputs[0]);
            }
            if (mRecipe.mFluidOutputs[1] != null && (mRecipe.mFluidOutputs[1].getFluid() != null)) {
                mFluids.init(3, false, 138 + 2, 5 + 1, 16, 16, mRecipe.mFluidOutputs[1].amount, true, null);
                mFluids.set(3, mRecipe.mFluidOutputs[1]);
            }
            if (mRecipe.mFluidOutputs.length > 2 && mRecipe.mFluidOutputs[2] != null && (mRecipe.mFluidOutputs[2].getFluid() != null)) {
                mFluids.init(4, false, 102 + 2, 23 + 1, 16, 16, mRecipe.mFluidOutputs[2].amount, true, null);
                mFluids.set(4, mRecipe.mFluidOutputs[2]);
            }
            if (mRecipe.mFluidOutputs.length > 3 && mRecipe.mFluidOutputs[3] != null && (mRecipe.mFluidOutputs[3].getFluid() != null)) {
                mFluids.init(5, false, 120 + 2, 23 + 1, 16, 16, mRecipe.mFluidOutputs[3].amount, true, null);
                mFluids.set(5, mRecipe.mFluidOutputs[3]);
            }
            if (mRecipe.mFluidOutputs.length > 4 && mRecipe.mFluidOutputs[4] != null && (mRecipe.mFluidOutputs[4].getFluid() != null)) {
                mFluids.init(6, false, 138 + 2, 23 + 1, 16, 16, mRecipe.mFluidOutputs[4].amount, true, null);
                mFluids.set(6, mRecipe.mFluidOutputs[4]);
            }
        } else if ((mRecipe.mFluidOutputs.length > 0) && (mRecipe.mFluidOutputs[0] != null) && (mRecipe.mFluidOutputs[0].getFluid() != null)) {
            mFluids.init(7, false, 102 + 2, 52 + 1, 16, 16, mRecipe.mFluidOutputs[0].amount, true, null);
            mFluids.set(7, mRecipe.mFluidOutputs[0]);
        }

        mInputs.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            int slotType = slotIndex / 1000;
            int inputIndex = slotIndex % 1000;
            switch (slotType) {
                case 1:
                    //it's output
                    int outputChance = mRecipe.getOutputChance(inputIndex);
                    if(outputChance != 10000) {
                        float floatChance = (outputChance * 1F / 10000F) * 100F;
                        String cutChance = String.format("%.2f", floatChance);
                        tooltip.add("Chance: " + cutChance + "%");
                    }
                    break;
                case 0:
                    //it's input
                    if(mRecipe.getRepresentativeInput(inputIndex).stackSize == 0) {
                        tooltip.add("Does not get consumed in the process");
                    }
                    break;
            }
        });

        mFluids.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            tooltip.add(TextFormatting.BLUE + "Amount: " + ingredient.amount + TextFormatting.GRAY);
            tooltip.add(TextFormatting.RED + "Temperature: " + ingredient.getFluid().getTemperature() + " K" + TextFormatting.GRAY);
            tooltip.add(TextFormatting.GREEN + "State: " + (ingredient.getFluid().isGaseous() ? "Gas" : "Liquid") + TextFormatting.GRAY);
        });

    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, getInputs());
        ingredients.setInputs(FluidStack.class, getFluidInputs());

        ingredients.setOutputs(ItemStack.class, getOutputs());
        ingredients.setOutputs(FluidStack.class, getFluidOutputs());
    }

    @Override
    public List<ItemStack> getInputs() {
        return Arrays.asList(mRecipe.mInputs);
    }

    @Override
    public List<ItemStack> getOutputs() {
        return Arrays.asList(mRecipe.mOutputs);
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return Arrays.asList(mRecipe.mFluidInputs);
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return Arrays.asList(mRecipe.mFluidOutputs);
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int tEUt = mRecipe.mEUt;
        int tDuration = mRecipe.mDuration;
        if (tEUt != 0) {
            drawText(10, 73, "Total: " + tDuration * tEUt + " EU", -16777216);
            drawText(10, 83, "Usage: " + tEUt + " EU/t", -16777216);
            if (this.mRecipeMap.mShowVoltageAmperageInNEI) {
                drawText(10, 93, "Voltage: " + tEUt / this.mRecipeMap.mAmperage + " EU", -16777216);
                drawText(10, 103, "Amperage: " + this.mRecipeMap.mAmperage, -16777216);
            } else {
                drawText(10, 93, "Voltage: unspecified", -16777216);
                drawText(10, 103, "Amperage: unspecified", -16777216);
            }
        }
        if (tDuration > 0) {
            drawText(10, 113, "Time: " + (tDuration < 20 ? "< 1" : Integer.valueOf(tDuration / 20)) + " secs", -16777216);
        }
        if ((GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePre)) || (GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePost))) {
            drawText(10, 123, this.mRecipeMap.mNEISpecialValuePre + mRecipe.mSpecialValue * this.mRecipeMap.mNEISpecialValueMultiplier + this.mRecipeMap.mNEISpecialValuePost, -16777216);
        }

    }

    public static void drawText(int aX, int aY, String aString, int aColor) {
        Minecraft.getMinecraft().fontRendererObj.drawString(aString, aX, aY, aColor);
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

}
