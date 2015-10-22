package gregtech.common.tileentities.machines.basic;

import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class GT_MetaTileEntity_Printer
        extends GT_MetaTileEntity_BasicMachine {
    public GT_MetaTileEntity_Printer(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "It can copy Books and paint Stuff", 1, 1, "Printer.png", GT_Recipe.GT_Recipe_Map.sPrinterRecipes.mNEIName, new ITexture[0]);
    }

    public GT_MetaTileEntity_Printer(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 2, 2, aGUIName, aNEIName);
    }

    public int checkRecipe() {
        if (getOutputAt(0) != null) {
            this.mOutputBlocked += 1;
        } else if ((GT_Utility.isStackValid(getInputAt(0))) && (getInputAt(0).stackSize > 0) &&
                (GT_Utility.isStackInvalid(getSpecialSlot())) &&
                (OrePrefixes.block.contains(getInputAt(0)))) {
            ArrayList<ItemStack> tList = GT_OreDictUnificator.getOres(GT_OreDictUnificator.getAssociation(getInputAt(0)));
            if (tList.size() > 1) {
                tList.add(tList.get(0));
                int i = 0;
                for (int j = tList.size() - 1; i < j; i++) {
                    if (GT_Utility.areStacksEqual(getInputAt(0), (ItemStack) tList.get(i))) {
                        this.mOutputItems[0] = GT_Utility.copyAmount(1L, new Object[]{tList.get(i + 1)});
                        this.mEUt = (1 * (1 << this.mTier - 1) * (1 << this.mTier - 1));
                        this.mMaxProgresstime = (32 / (1 << this.mTier - 1));
                        getInputAt(0).stackSize -= 1;
                        return 2;
                    }
                }
            }
        }
        this.mMaxProgresstime = 0;
        return 0;
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return null;
    }
}
