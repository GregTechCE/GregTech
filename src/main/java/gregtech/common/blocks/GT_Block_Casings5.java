package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.GT_Item_Casings5;
import gregtech.common.blocks.materials.GT_Material_Casings;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings5 extends GT_Block_Casings_Abstract {

    public static final PropertyEnum<EnumCoilVariant> COIL_VARIANT = PropertyEnum.create("coil_variant", EnumCoilVariant.class);

    public GT_Block_Casings5() {
        super("blockcasings5", GT_Item_Casings5.class, GT_Material_Casings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(COIL_VARIANT, EnumCoilVariant.CUPRONICKEL));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Cupronickel Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Kanthal Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Nichrome Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Tungstensteel Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "HSS-G Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Naquadah Coil Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Naquadah Alloy Coil Block");

        ItemList.Casing_Coil_Cupronickel.set(new ItemStack(this, 1, 0));
        ItemList.Casing_Coil_Kanthal.set(new ItemStack(this, 1, 1));
        ItemList.Casing_Coil_Nichrome.set(new ItemStack(this, 1, 2));
        ItemList.Casing_Coil_TungstenSteel.set(new ItemStack(this, 1, 3));
        ItemList.Casing_Coil_HSSG.set(new ItemStack(this, 1, 4));
        ItemList.Casing_Coil_Naquadah.set(new ItemStack(this, 1, 5));
        ItemList.Casing_Coil_NaquadahAlloy.set(new ItemStack(this, 1, 6));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COIL_VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(COIL_VARIANT, EnumCoilVariant.byMetadata(meta & 0b1111));
    }

    /**
     * @see Block#getMetaFromState(IBlockState)
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(COIL_VARIANT).getMetadata();
        return meta;
    }

//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public TextureAtlasSprite getIcon(EnumFacing aSide, int aMeta) {
//        return getIconContainer(aSide, aMeta).getIcon();
//    }
//
//    public static IIconContainer getIconContainer(EnumFacing aSide, int aMeta) {
//        switch (aMeta) {
//            case 0:
//                return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL;
//            case 1:
//                return Textures.BlockIcons.MACHINE_COIL_KANTHAL;
//            case 2:
//                return Textures.BlockIcons.MACHINE_COIL_NICHROME;
//            case 3:
//                return Textures.BlockIcons.MACHINE_COIL_TUNGSTENSTEEL;
//            case 4:
//                return Textures.BlockIcons.MACHINE_COIL_HSSG;
//            case 5:
//                return Textures.BlockIcons.MACHINE_COIL_NAQUADAH;
//            case 6:
//                return Textures.BlockIcons.MACHINE_COIL_NAQUADAHALLOY;
//        }
//        return Textures.BlockIcons.MACHINE_COIL_CUPRONICKEL;
//    }

    public static enum EnumCoilVariant implements IStringSerializable {
        CUPRONICKEL("cupronickel"),
        KANTHAL("kanthal"),
        NICHROME("nichrome"),
        TUNGSTENSTEEL("tungstensteel"),
        HSSG("hssg"),
        NAQUADAH("naquadah"),
        NAQUADAH_ALLOY("naquadah_alloy");

        private final int meta = ordinal();
        private final String name;

        EnumCoilVariant(String name) {
            this.name = name;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static EnumCoilVariant byMetadata(int meta) {
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }

            return values()[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
