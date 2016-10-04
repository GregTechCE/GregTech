package gregtech.common.blocks;

import cpw.mods.fml.common.Loader;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GT_Block_Metal extends GT_Block_Storage {
    public Materials[] mMats;
    public OrePrefixes mPrefix;
    public IIconContainer[] mBlockIcons;
    public boolean mHideBlocks;

    public GT_Block_Metal(String aName, Materials[] aMats, OrePrefixes aPrefix, IIconContainer[] aBlockIcons) {
        super(GT_Item_Storage.class, aName, Material.iron);
        mMats = aMats;
        mPrefix = aPrefix;
        mBlockIcons = aBlockIcons;
        mHideBlocks = Loader.isModLoaded("NotEnoughItems");

        for (int i = 0; i < aMats.length; i++) {
            if (aMats[i].mMetaItemSubID > 0 && aMats[i].mHasParentMod) {
                GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", "Block of " + aMats[i].mDefaultLocalName);
                GT_OreDictUnificator.registerOre(aPrefix, aMats[i], new ItemStack(this, 1, i));
            }
        }
        if (aMats.length<16 && Loader.isModLoaded("NotEnoughItems")) {
            for (int i = aMats.length; i < 16; i++) codechicken.nei.api.API.hideItem(new ItemStack(this, 1, i));
        }
    }

    public IIcon getIcon(int aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16) && aMeta < mMats.length) {
            return mBlockIcons[aMeta].getIcon();
        }
        return null;
    }
}
