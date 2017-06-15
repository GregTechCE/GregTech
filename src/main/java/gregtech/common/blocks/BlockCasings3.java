package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemCasings3;
import gregtech.common.blocks.materials.MaterialCasings;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCasings3 extends BlockCasingsAbstract {

    public static final PropertyEnum<EnumCasingVariant> CASING_VARIANT = PropertyEnum.create("casing_variant", EnumCasingVariant.class);

    public BlockCasings3() {
        super("blockcasings3", ItemCasings3.class, MaterialCasings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.STRIPES_A));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Yellow Stripes Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Yellow Stripes Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Radioactive Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Bio Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Explosion Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fire Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Acid Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Magic Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Frost Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Noise Hazard Sign Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Grate Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Vent Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Radiation Proof Casing");// does not have a recipe
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Bronze Firebox Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Steel Firebox Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Tungstensteel Firebox Casing");

        ItemList.Casing_Stripes_A.set(new ItemStack(this, 1, 0));
        ItemList.Casing_Stripes_B.set(new ItemStack(this, 1, 1));
        ItemList.Casing_RadioactiveHazard.set(new ItemStack(this, 1, 2));
        ItemList.Casing_BioHazard.set(new ItemStack(this, 1, 3));
        ItemList.Casing_ExplosionHazard.set(new ItemStack(this, 1, 4));
        ItemList.Casing_FireHazard.set(new ItemStack(this, 1, 5));
        ItemList.Casing_AcidHazard.set(new ItemStack(this, 1, 6));
        ItemList.Casing_MagicHazard.set(new ItemStack(this, 1, 7));
        ItemList.Casing_FrostHazard.set(new ItemStack(this, 1, 8));
        ItemList.Casing_NoiseHazard.set(new ItemStack(this, 1, 9));
        ItemList.Casing_Grate.set(new ItemStack(this, 1, 10));
        ItemList.Casing_Vent.set(new ItemStack(this, 1, 11));
        ItemList.Casing_RadiationProof.set(new ItemStack(this, 1, 12));
        ItemList.Casing_Firebox_Bronze.set(new ItemStack(this, 1, 13));
        ItemList.Casing_Firebox_Steel.set(new ItemStack(this, 1, 14));
        ItemList.Casing_Firebox_TungstenSteel.set(new ItemStack(this, 1, 15));
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

    public enum EnumCasingVariant implements IStringSerializable {
        STRIPES_A("stripes_a"),
        STRIPES_B("stripes_b"),
        RADIOACTIVEHAZARD("radioactivehazard"),
        BIOHAZARD("biohazard"),
        EXPLOSIONHAZARD("explosionhazard"),
        FIREHAZARD("firehazard"),
        ACIDHAZARD("acidhazard"),
        MAGICHAZARD("magichazard"),
        FROSTHAZARD("frosthazard"),
        NOISEHAZARD("noisehazard"),
        GRATE("grate"),
        VENT("vent"),
        RADIATIONPROOF("radiationproof"),
        FIREBOX_BRONZE("firebox_bronze"),
        FIREBOX_STEEL("firebox_steel"),
        FIREBOX_TSTEEL("firebox_tsteel");
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
