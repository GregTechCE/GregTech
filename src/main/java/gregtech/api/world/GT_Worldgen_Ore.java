package gregtech.api.world;

import gregtech.api.GregTechAPI;
import net.minecraft.block.state.IBlockState;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GT_Worldgen_Ore extends GT_Worldgen {

    public final int mAmount, mSize, mMinY, mMaxY, mProbability, mDimensionType;
    public final IBlockState mBlockState;

    public final Collection<String> mBiomeList;

    public final boolean mAllowToGenerateinVoid;

    public GT_Worldgen_Ore(String aName, boolean aDefault, IBlockState blockState, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, GregTechAPI.sWorldgenList, aDefault);
        mDimensionType = aDimensionType;
        mBlockState = blockState;
        mProbability = GregTechAPI.sWorldgenFile.get("worldgen." + mWorldGenName, "Probability", aProbability);
        mAmount = GregTechAPI.sWorldgenFile.get("worldgen." + mWorldGenName, "Amount", aAmount);
        mSize = GregTechAPI.sWorldgenFile.get("worldgen." + mWorldGenName, "Size", aSize);
        mMinY = GregTechAPI.sWorldgenFile.get("worldgen." + mWorldGenName, "MinHeight", aMinY);
        mMaxY = GregTechAPI.sWorldgenFile.get("worldgen." + mWorldGenName, "MaxHeight", aMaxY);
        if (aBiomeList == null) mBiomeList = new ArrayList<>();
        else mBiomeList = aBiomeList;
        mAllowToGenerateinVoid = aAllowToGenerateinVoid;
    }
}