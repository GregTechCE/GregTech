package gregtech.api.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

import static gregtech.api.enums.GT_Values.M;

/**
 * @author Gregorius Techneticies
 *         <p/>
 *         One Item for everything!
 *         <p/>
 *         This brilliant Item Class is used for automatically generating all possible variations of Material Items, like Dusts, Ingots, Gems, Plates and similar.
 *         It saves me a ton of work, when adding Items, because I always have to make a new Item SubType for each OreDict Prefix, when adding a new Material.
 *         <p/>
 *         As you can see, up to 32766 Items can be generated using this Class. And the last 766 Items can be custom defined, just to save space and MetaData.
 *         <p/>
 *         These Items can also have special RightClick abilities, electric Charge or even be set to become a Food alike Item.
 */
public abstract class GT_MetaGenerated_Item_X01 extends GT_MetaGenerated_Item {

    protected final OrePrefixes mPrefix;
    protected final int mIconSetIndex;

    /**
     * Creates the Item using these Parameters. This is for the new 1 Item = 1 Prefix System.
     *
     * @param aUnlocalized     The Unlocalized Name of this Item.
     * @param aGeneratedPrefix The OreDict Prefix you want to have generated.
     * @param aIconSetIndex    The TextureSet Index to be used. -1 for Defaulting to the Data contained in the Prefix. (this is only to be used for selecting the Icon in getIconContainer, nothing else)
     */
    public GT_MetaGenerated_Item_X01(String aUnlocalized, OrePrefixes aGeneratedPrefix, int aIconSetIndex) {
        super(aUnlocalized, (short) 32000, (short) 766);
        mPrefix = aGeneratedPrefix;
        mIconSetIndex = aIconSetIndex >= 0 ? aIconSetIndex : aGeneratedPrefix.mTextureIndex >= 0 ? aGeneratedPrefix.mTextureIndex : 0;

        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            OrePrefixes tPrefix = mPrefix;
            if (tPrefix == null) continue;
            Materials tMaterial = GregTech_API.sGeneratedMaterials[i];
            if (tMaterial == null) continue;
            if (mPrefix.doGenerateItem(tMaterial)) {
                ItemStack tStack = new ItemStack(this, 1, i);
                GT_LanguageManager.addStringLocalization(getUnlocalizedName(tStack) + ".name", getDefaultLocalization(tPrefix, tMaterial, i));
                GT_LanguageManager.addStringLocalization(getUnlocalizedName(tStack) + ".tooltip", tMaterial.getToolTip(tPrefix.mMaterialAmount / M));
                String tOreName = getOreDictString(tPrefix, tMaterial);
                tPrefix = OrePrefixes.getOrePrefix(tOreName);
                if (tPrefix != null && tPrefix.mIsUnificatable) {
                    GT_OreDictUnificator.set(tPrefix, OrePrefixes.getMaterial(tOreName, tPrefix), tStack);
                } else {
                    GT_OreDictUnificator.registerOre(tOreName, tStack);
                }
            }
        }
    }

	/* ---------- OVERRIDEABLE FUNCTIONS ---------- */

    /**
     * @param aPrefix   the OreDict Prefix
     * @param aMaterial the Material
     * @param aMetaData a Index from [0 - 31999]
     * @return the Localized Name when default LangFiles are used.
     */
    public String getDefaultLocalization(OrePrefixes aPrefix, Materials aMaterial, int aMetaData) {
        return aPrefix.getDefaultLocalNameForItem(aMaterial);
    }

    /**
     * @param aPrefix         always != null
     * @param aMaterial       always != null
     * @param aDoShowAllItems this is the Configuration Setting of the User, if he wants to see all the Stuff like Tiny Dusts or Crushed Ores as well.
     * @return if this Item should be visible in NEI or Creative
     */
    public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return true;
    }

    /**
     * @return the name of the Item to be registered at the OreDict.
     */
    public String getOreDictString(OrePrefixes aPrefix, Materials aMaterial) {
        return aPrefix.get(aMaterial).toString();
    }

    public IIconContainer getIconContainer(int aMetaData, Materials aMaterial) {
        return aMaterial.mIconSet.mTextures[mIconSetIndex];
    }
	
	/* ---------- INTERNAL OVERRIDES ---------- */

    @Override
    public ItemStack getContainerItem(ItemStack aStack) {
        int aMetaData = aStack.getItemDamage();
        if (aMetaData < GregTech_API.sGeneratedMaterials.length && aMetaData >= 0) {
            Materials aMaterial = GregTech_API.sGeneratedMaterials[aMetaData];
            if (aMaterial != null && aMaterial != Materials.Empty && aMaterial != Materials._NULL) {
                return GT_Utility.copyAmount(1, mPrefix.mContainerItem);
            }
        }
        return null;
    }

    @Override
    public short[] getRGBa(ItemStack aStack) {
        int aMetaData = getDamage(aStack);
        return aMetaData < GregTech_API.sGeneratedMaterials.length && GregTech_API.sGeneratedMaterials[aMetaData] != null ? GregTech_API.sGeneratedMaterials[aMetaData].mRGBa : Materials._NULL.mRGBa;
    }

    @Override
    public final IIconContainer getIconContainer(int aMetaData) {
        return aMetaData < GregTech_API.sGeneratedMaterials.length && GregTech_API.sGeneratedMaterials[aMetaData] != null ? getIconContainer(aMetaData, GregTech_API.sGeneratedMaterials[aMetaData]) : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubItems(Item var1, CreativeTabs aCreativeTab, List aList) {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++)
            if (mPrefix.doGenerateItem(GregTech_API.sGeneratedMaterials[i]) && doesShowInCreative(mPrefix, GregTech_API.sGeneratedMaterials[i], GregTech_API.sDoShowAllItemsInCreative)) {
                ItemStack tStack = new ItemStack(this, 1, i);
                isItemStackUsable(tStack);
                aList.add(tStack);
            }
        super.getSubItems(var1, aCreativeTab, aList);
    }

    @Override
    public final IIcon getIconFromDamage(int aMetaData) {
        if (aMetaData < 0) return null;
        if (aMetaData < GregTech_API.sGeneratedMaterials.length) {
            Materials tMaterial = GregTech_API.sGeneratedMaterials[aMetaData];
            if (tMaterial == null) return null;
            IIconContainer tIcon = getIconContainer(aMetaData, tMaterial);
            if (tIcon != null) return tIcon.getIcon();
            return null;
        }
        return aMetaData >= mOffset && aMetaData - mOffset < mIconList.length ? mIconList[aMetaData - mOffset][0] : null;
    }

    @Override
    public int getItemStackLimit(ItemStack aStack) {
        return getDamage(aStack) < mOffset ? Math.min(super.getItemStackLimit(aStack), mPrefix.mDefaultStackSize) : super.getItemStackLimit(aStack);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack aStack, ItemStack aBook) {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack aStack, ItemStack aMaterial) {
        return false;
    }
}