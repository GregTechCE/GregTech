package gregtech.api.world;

import gregtech.api.GregTech_API;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GT_Worldgen_Ore extends GT_Worldgen {
    public final int mBlockMeta, mAmount, mSize, mMinY, mMaxY, mProbability, mDimensionType;
    public final Block mBlock;
    public final Collection<String> mBiomeList;
    public final boolean mAllowToGenerateinVoid;
    private final String aTextWorldgen = "worldgen.";

    public GT_Worldgen_Ore(String aName, boolean aDefault, Block aBlock, int aBlockMeta, int aDimensionType, int aAmount, int aSize, int aProbability, int aMinY, int aMaxY, Collection<String> aBiomeList, boolean aAllowToGenerateinVoid) {
        super(aName, GregTech_API.sWorldgenList, aDefault);
        mDimensionType = aDimensionType;
        mBlock = aBlock;
        mBlockMeta = Math.min(Math.max(aBlockMeta, 0), 15);
        mProbability = GregTech_API.sWorldgenFile.get(aTextWorldgen + mWorldGenName, "Probability", aProbability);
        mAmount = GregTech_API.sWorldgenFile.get(aTextWorldgen + mWorldGenName, "Amount", aAmount);
        mSize = GregTech_API.sWorldgenFile.get(aTextWorldgen + mWorldGenName, "Size", aSize);
        mMinY = GregTech_API.sWorldgenFile.get(aTextWorldgen + mWorldGenName, "MinHeight", aMinY);
        mMaxY = GregTech_API.sWorldgenFile.get(aTextWorldgen + mWorldGenName, "MaxHeight", aMaxY);
        if (aBiomeList == null) mBiomeList = new ArrayList<String>();
        else mBiomeList = aBiomeList;
        mAllowToGenerateinVoid = aAllowToGenerateinVoid;
    }
}