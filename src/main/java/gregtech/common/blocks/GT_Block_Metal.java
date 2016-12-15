package gregtech.common.blocks;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Metal extends GT_Block_Storage {
    public Materials[] mMats;
    public OrePrefixes mPrefix;
    public Textures.BlockIcons[] mBlockIcons;

    public GT_Block_Metal(String aName, Materials[] aMats, OrePrefixes aPrefix, Textures.BlockIcons[] aBlockIcons) {
        super(GT_Item_Storage.class, aName, Material.IRON);
        mMats = aMats;
        mPrefix = aPrefix;
        mBlockIcons = aBlockIcons;
        for (int i = 0; i < aMats.length; i++) {
            GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + i + ".name", "Block of " + aMats[i].mDefaultLocalName);
            GT_OreDictUnificator.registerOre(aPrefix, aMats[i], new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aDamage) {
        if ((aDamage >= 0) && (aDamage < 16) && aDamage < mMats.length) {
            return mBlockIcons[aDamage].getIcon();
        }
        return mBlockIcons[0].getIcon();
    }

}
