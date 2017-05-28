package gregtech.common.blocks;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.GT_Item_Casings1;
import gregtech.common.blocks.materials.GT_Material_Casings;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings1 extends GT_Block_Casings_Abstract {

    public static final PropertyEnum<EnumCasingVariant> CASING_VARIANT = PropertyEnum.create("casing_variant", EnumCasingVariant.class);

    public GT_Block_Casings1() {
        super("blockcasings", GT_Item_Casings1.class, GT_Material_Casings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.ULV));

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
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cupronickel Coil Block (Deprecated)");// not used
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Kanthal Coil Block (Deprecated)");// not used
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Nichrome Coil Block (Deprecated)");// not used
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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CASING_VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.byMetadata(meta & 0b1111));
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(CASING_VARIANT).getMetadata();
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing side, int meta) {
        return getIconContainer(side, meta).getIcon();
    }

    public static IIconContainer getIconContainer(EnumFacing side, int meta) {
        if ((meta >= 0) && (meta < 16)) {
            switch (meta) {
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
            if (side == EnumFacing.DOWN) {
                return Textures.BlockIcons.MACHINECASINGS_BOTTOM[meta];
            }
            if (side == EnumFacing.UP) {
                return Textures.BlockIcons.MACHINECASINGS_TOP[meta];
            }
            return Textures.BlockIcons.MACHINECASINGS_SIDE[meta];
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL;
    }

    @Override
    public int getColorMultiplier(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        int metadata = state.getValue(CASING_VARIANT).getMetadata();
        return metadata > 9 ? Dyes._NULL.getRGBAInt() : Dyes.MACHINE_METAL.getRGBAInt();
    }

    public enum EnumCasingVariant implements IStringSerializable {
        ULV("ulv"),
        LV("lv"),
        MV("mv"),
        HV("hv"),
        EV("ev"),
        IV("iv"),
        LUV("luv"),
        ZPM("zpm"),
        UV("uv"),
        MAX("max"),
        BRONZE_PLATED_BRICKS("bplated_bricks"),
        HEAT_PROOF("heat_proof"),
        _DEPR_CASING("depr"),// TODO delete not used metas
        __DEPR_CASING("depr2"),// TODO delete not used metas
        ___DEPR_CASING("depr3"),// TODO delete not used metas
        SUPERCONDUCTING("superconducting");

        private static final EnumCasingVariant[] META_LOOKUP = new EnumCasingVariant[values().length];

        private final int meta = ordinal();
        private final String name;

        EnumCasingVariant(String name) {
            this.name = name;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static EnumCasingVariant byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        static {
            for (EnumCasingVariant casingVariant : values()) {
                META_LOOKUP[casingVariant.getMetadata()] = casingVariant;
            }
        }
    }
}
