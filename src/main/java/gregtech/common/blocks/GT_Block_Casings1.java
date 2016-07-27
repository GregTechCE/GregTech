package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GT_Block_Casings1
        extends GT_Block_Casings_Abstract {
    public GT_Block_Casings1() {
        super(GT_Item_Casings1.class, "gt.blockcasings", GT_Material_Casings.INSTANCE);
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            Textures.BlockIcons.CASING_BLOCKS[i] = new GT_CopiedBlockTexture(this, 6, i);
        }
        Textures.BlockIcons.CASING_BLOCKS[120] = new GT_CopiedBlockTexture(this, 6, 0);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "ULV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "LV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "MV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "HV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "EV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "IV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "LuV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "ZPM Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "UV Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "MAX Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Bronze Plated Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Heat Proof Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cupronickel Coil Block (Deprecated)");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Kanthal Coil Block (Deprecated)");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Nichrome Coil Block (Deprecated)");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Superconducting Coil Block");
        ItemList.Casing_ULV.set(new ItemStack(this, 1, 0));
        ItemList.Casing_LV.set(new ItemStack(this, 1, 1));
        ItemList.Casing_MV.set(new ItemStack(this, 1, 2));
        ItemList.Casing_HV.set(new ItemStack(this, 1, 3));
        ItemList.Casing_EV.set(new ItemStack(this, 1, 4));
        ItemList.Casing_IV.set(new ItemStack(this, 1, 5));
        ItemList.Casing_LuV.set(new ItemStack(this, 1, 6));
        ItemList.Casing_ZPM.set(new ItemStack(this, 1, 7));
        ItemList.Casing_UV.set(new ItemStack(this, 1, 8));
        ItemList.Casing_MAX.set(new ItemStack(this, 1, 9));
        ItemList.Casing_BronzePlatedBricks.set(new ItemStack(this, 1, 10));
        ItemList.Casing_HeatProof.set(new ItemStack(this, 1, 11));
        ItemList.Casing_Coil_Cupronickel_Deprecated.set(new ItemStack(this, 1, 12));
        ItemList.Casing_Coil_Kanthal_Deprecated.set(new ItemStack(this, 1, 13));
        ItemList.Casing_Coil_Nichrome_Deprecated.set(new ItemStack(this, 1, 14));
        ItemList.Casing_Coil_Superconductor.set(new ItemStack(this, 1, 15));
    }

    public IIcon getIcon(int aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            switch (aMeta) {
                case 10:
                    return Textures.BlockIcons.MACHINE_BRONZEPLATEDBRICKS.getIcon();
                case 11:
                    return Textures.BlockIcons.MACHINE_HEATPROOFCASING.getIcon();
                case 12:
                    return Textures.BlockIcons.RENDERING_ERROR.getIcon();
                case 13:
                    return Textures.BlockIcons.RENDERING_ERROR.getIcon();
                case 14:
                    return Textures.BlockIcons.RENDERING_ERROR.getIcon();
                case 15:
                    return Textures.BlockIcons.MACHINE_COIL_SUPERCONDUCTOR.getIcon();
            }
            if (aSide == 0) {
                return Textures.BlockIcons.MACHINECASINGS_BOTTOM[aMeta].getIcon();
            }
            if (aSide == 1) {
                return Textures.BlockIcons.MACHINECASINGS_TOP[aMeta].getIcon();
            }
            return Textures.BlockIcons.MACHINECASINGS_SIDE[aMeta].getIcon();
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }

    public int colorMultiplier(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return aWorld.getBlockMetadata(aX, aY, aZ) > 9 ? super.colorMultiplier(aWorld, aX, aY, aZ) : gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[0] << 16 | gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[1] << 8 | gregtech.api.enums.Dyes.MACHINE_METAL.mRGBa[2];
    }
}
