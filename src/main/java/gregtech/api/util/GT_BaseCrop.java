package gregtech.api.util;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import ic2.api.crops.CropCard;
import ic2.api.crops.CropProperties;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static gregtech.api.enums.GT_Values.E;

public class GT_BaseCrop extends CropCard {

    public static ArrayList<GT_BaseCrop> sCropList = new ArrayList<GT_BaseCrop>();
    private String mName = E, mDiscoveredBy = "Gregorius Techneticies";
    private int mTier = 0, mMaxSize = 0, mAfterHarvestSize = 0, mHarvestSize = 0, mStats[] = new int[5], mGrowthSpeed = 0;
    private String[] mAttributes = new String[0];
    private ItemStack mDrop = null, mSpecialDrops[] = null;
    private Materials mBlock = null;

    /**
     * To create new Crops
     *
     * @param aID           Default ID
     * @param aCropName     Name of the Crop
     * @param aDiscoveredBy The one who discovered the Crop
     * @param aDrop         The Item which is dropped by the Crop. must be != null
     * @param aBaseSeed     Baseseed to plant this Crop. null == crossbreed only
     * @param aTier         tier of the Crop. forced to be >= 1
     * @param aMaxSize      maximum Size of the Crop. forced to be >= 3
     * @param aGrowthSpeed  how fast the Crop grows. if < 0 then its set to Tier*300
     * @param aHarvestSize  the size the Crop needs to be harvested. forced to be between 2 and max size
     */
    public GT_BaseCrop(int aID, String aCropName, String aDiscoveredBy, ItemStack aBaseSeed, int aTier, int aMaxSize, int aGrowthSpeed, int aAfterHarvestSize, int aHarvestSize, int aStatChemical, int aStatFood, int aStatDefensive, int aStatColor, int aStatWeed, String[] aAttributes, ItemStack aDrop, ItemStack[] aSpecialDrops) {
        new GT_BaseCrop(aID, aCropName, aDiscoveredBy, aBaseSeed, aTier, aMaxSize, aGrowthSpeed, aAfterHarvestSize, aHarvestSize, aStatChemical, aStatFood, aStatDefensive, aStatColor, aStatWeed, aAttributes, null, aDrop, aSpecialDrops);
    }

    /**
     * To create new Crops
     *
     * @param aID           Default ID
     * @param aCropName     Name of the Crop
     * @param aDiscoveredBy The one who discovered the Crop
     * @param aDrop         The Item which is dropped by the Crop. must be != null
     * @param aBaseSeed     Baseseed to plant this Crop. null == crossbreed only
     * @param aTier         tier of the Crop. forced to be >= 1
     * @param aMaxSize      maximum Size of the Crop. forced to be >= 3
     * @param aGrowthSpeed  how fast the Crop grows. if < 0 then its set to Tier*300
     * @param aHarvestSize  the size the Crop needs to be harvested. forced to be between 2 and max size
     * @param aBlock        the block below needed for crop to grow. If null no block needed
     */
    public GT_BaseCrop(int aID, String aCropName, String aDiscoveredBy, ItemStack aBaseSeed, int aTier, int aMaxSize, int aGrowthSpeed, int aAfterHarvestSize, int aHarvestSize, int aStatChemical, int aStatFood, int aStatDefensive, int aStatColor, int aStatWeed, String[] aAttributes, Materials aBlock, ItemStack aDrop, ItemStack[] aSpecialDrops) {
        mName = aCropName;
        aID = GT_Config.addIDConfig(ConfigCategories.IDs.crops, mName.replaceAll(" ", "_"), aID);
        if (aDiscoveredBy != null && !aDiscoveredBy.equals(E)) mDiscoveredBy = aDiscoveredBy;
        if (aDrop != null && aID > 0 && aID < 256) {
            mDrop = GT_Utility.copy(aDrop);
            mSpecialDrops = aSpecialDrops;
            mTier = Math.max(1, aTier);
            mMaxSize = Math.max(3, aMaxSize);
            mHarvestSize = Math.min(Math.max(aHarvestSize, 2), mMaxSize);
            mAfterHarvestSize = Math.min(Math.max(aAfterHarvestSize, 1), mMaxSize - 1);
            mStats[0] = aStatChemical;
            mStats[1] = aStatFood;
            mStats[2] = aStatDefensive;
            mStats[3] = aStatColor;
            mStats[4] = aStatWeed;
            mGrowthSpeed = aGrowthSpeed;
            mAttributes = aAttributes;
            mBlock = aBlock;
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.crops, aCropName, true)) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName(), aCropName);
                Crops.instance.registerCrop(this);
                if (aBaseSeed != null) Crops.instance.registerBaseSeed(aBaseSeed, this, 1, 1, 1, 1);
                sCropList.add(this);
            }
        }
    }

    @Override
    public int getSizeAfterHarvest(ICropTile crop) {
        return (byte) mAfterHarvestSize;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (mGrowthSpeed < 200) return super.getGrowthDuration(cropTile);
        return getTier() * mGrowthSpeed;
    }

    @Override
    public int getRootsLength(ICropTile cropTile) {
        return 5;
    }

    @Override
    public final boolean canGrow(ICropTile aCrop) {
        if (mBlock != null && aCrop.getCurrentSize() == mMaxSize - 1) {
            return isBlockBelow(aCrop);
        }
        return aCrop.getCurrentSize() < getMaxSize();
    }

    @Override
    public final boolean canBeHarvested(ICropTile aCrop) {
        return aCrop.getCurrentSize() >= mHarvestSize;
    }

    @Override
    public boolean canCross(ICropTile aCrop) {
        return aCrop.getCurrentSize() + 2 > getMaxSize();
    }

    public int getTier() {
        return mTier;
    }

    @Override
    public int getMaxSize() {
        return mMaxSize;
    }

    @Override
    public String getId() {
        return mName;
    }

    @Override
    public String getDiscoveredBy() {
        return mDiscoveredBy;
    }

    @Override
    public String getOwner() {
        return "gregtech";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(getTier(), mStats[0], mStats[1], mStats[2], mStats[3], mStats[4]);
    }

    @Override
    public String[] getAttributes() {
        return mAttributes;
    }

    /*@Override
    public String getCropInformation() {
        if(mBlock != null) {
            return String.format("Requires %s Ore or Block of %s as soil block to reach full growth.", mBlock.mName, mBlock.mName);
        }
        return "";
    }*/

    @Override
    public ItemStack getGain(ICropTile aCrop) {
        int tDrop;
        if (mSpecialDrops != null && (tDrop = aCrop.getWorldObj().rand.nextInt((mSpecialDrops.length*2) + 2)) < mSpecialDrops.length && mSpecialDrops[tDrop] != null) {
            return GT_Utility.copy(mSpecialDrops[tDrop]);
        }
        return GT_Utility.copy(mDrop);
    }

    @Override
    public boolean onRightClick(ICropTile cropTile, EntityPlayer player) {
        return canBeHarvested(cropTile) && super.onRightClick(cropTile, player);
    }
    
    @Override
    public List<ResourceLocation> getTexturesLocation() {
        List<ResourceLocation> list = new ArrayList<>();
        for (int size = 1; size <= getMaxSize(); size++) {
            list.add(new ResourceLocation(getOwner(), "blocks/crop/blockCrop." + getId() + "." + size));
        }
        return list;
    }


    public boolean isBlockBelow(ICropTile aCrop) {
        if (aCrop == null) {
            return false;
        }
        for (int i = 1; i < this.getRootsLength(aCrop); i++) {
            IBlockState downState = aCrop.getWorldObj().getBlockState(aCrop.getPosition().down(i));
            int tMetaID = downState.getBlock().getMetaFromState(downState);
            ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(downState.getBlock(), 1, tMetaID));
            if (tAssotiation != null && tAssotiation.mMaterial.mMaterial == mBlock) {
                if (tAssotiation.mPrefix.toString().startsWith("ore") || tAssotiation.mPrefix == OrePrefixes.block) {
                    return true;
                }
            }
        }
        return false;
    }

}
