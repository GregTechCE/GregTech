package gregtech.common.blocks;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings1 extends GT_Block_Casings_Abstract {
    public GT_Block_Casings1() {
        super(GT_Item_Casings1.class, "gt.blockcasings", GT_Material_Casings.INSTANCE);
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

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aMeta) {
        return getIconContainer(aSide, aMeta).getIcon();
    }
    
    public static IIconContainer getIconContainer(EnumFacing aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            switch (aMeta) {
                case 10:
                    return Textures.BlockIcons.MACHINE_BRONZEPLATEDBRICKS;
                case 11:
                    return Textures.BlockIcons.MACHINE_HEATPROOFCASING;
                case 12:
                    return Textures.BlockIcons.RENDERING_ERROR;
                case 13:
                    return Textures.BlockIcons.RENDERING_ERROR;
                case 14:
                    return Textures.BlockIcons.RENDERING_ERROR;
                case 15:
                    return Textures.BlockIcons.MACHINE_COIL_SUPERCONDUCTOR;
            }
            if (aSide == EnumFacing.DOWN) {
                return Textures.BlockIcons.MACHINECASINGS_BOTTOM[aMeta];
            }
            if (aSide == EnumFacing.UP) {
                return Textures.BlockIcons.MACHINECASINGS_TOP[aMeta];
            }
            return Textures.BlockIcons.MACHINECASINGS_SIDE[aMeta];
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL;
    }



    @Override
    public int getColorMultiplier(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
       int metadata = state.getValue(METADATA);
       return metadata > 9 ? Dyes._NULL.getRGBAInt() : Dyes.MACHINE_METAL.getRGBAInt();
    }

}
