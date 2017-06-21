package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.ItemCasings4;
import gregtech.common.blocks.materials.MaterialCasings;
import gregtech.common.blocks.models.CasingModel;
import gregtech.common.blocks.models.TurbineModel;
import gregtech.common.blocks.properties.UnlistedBlockAccess;
import gregtech.common.blocks.properties.UnlistedBlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BlockCasings4 extends BlockCasingsAbstract {

    public static final PropertyEnum<EnumCasingVariant> CASING_VARIANT = PropertyEnum.create("casing_variant", EnumCasingVariant.class);

    public static boolean connectedMachineTextures = true;

    public BlockCasings4() {
        super("blockcasings4", ItemCasings4.class, MaterialCasings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.ROBUST_TSTEEL));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Robust Tungstensteel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Clean Stainless Steel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Stable Titanium Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Titanium Firebox Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");// not used
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");// not used
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Fusion Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Fusion Coil");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Fusion Casing MK II");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Turbine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Stainless Steel Turbine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Titanium Turbine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Tungstensteel Turbine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Engine Intake Casing");

        ItemList.Casing_RobustTungstenSteel.set(new ItemStack(this, 1, 0));
        ItemList.Casing_CleanStainlessSteel.set(new ItemStack(this, 1, 1));
        ItemList.Casing_StableTitanium.set(new ItemStack(this, 1, 2));
        ItemList.Casing_Firebox_Titanium.set(new ItemStack(this, 1, 3));
        ItemList.Casing_Fusion.set(new ItemStack(this, 1, 6));
        ItemList.Casing_Fusion_Coil.set(new ItemStack(this, 1, 7));
        ItemList.Casing_Fusion2.set(new ItemStack(this, 1, 8));
        ItemList.Casing_Turbine.set(new ItemStack(this, 1, 9));
        ItemList.Casing_Turbine1.set(new ItemStack(this, 1, 10));
        ItemList.Casing_Turbine2.set(new ItemStack(this, 1, 11));
        ItemList.Casing_Turbine3.set(new ItemStack(this, 1, 12));
        ItemList.Casing_EngineIntake.set(new ItemStack(this, 1, 13));

        invokeOnClient(() -> {
            StateMapperBase stateMapper = new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    switch (state.getValue(CASING_VARIANT)) {
                        case __FUSION:
                            return CasingModel.BAKED_CASING_MODEL;
                        case FUSION2:
                            return CasingModel.BAKED_CASING_MK2_MODEL;
                        case TURBINE:
                            return TurbineModel.BAKED_TURBINE_MODEL;
                        case TURBINE1:
                            return TurbineModel.BAKED_SSTEEL_TURBINE_MODEL;
                        case TURBINE2:
                            return TurbineModel.BAKED_TITANIUM_TURBINE_MODEL;
                        case TURBINE3:
                            return TurbineModel.BAKED_TSTEEL_TURBINE_MODEL;
                    }

                    return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), this.getPropertyString(state.getProperties()));
                }
            };
            ModelLoader.setCustomStateMapper(this, stateMapper);
        });
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this)
                .add(CASING_VARIANT)
                .add(UnlistedBlockAccess.BLOCKACCESS, UnlistedBlockPos.POS)
                .build();
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        return extendedBlockState
                .withProperty(UnlistedBlockAccess.BLOCKACCESS, world)
                .withProperty(UnlistedBlockPos.POS, pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.byMetadata(meta & 0b1111));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(CASING_VARIANT).getMetadata();
        return meta;
    }

    public enum EnumCasingVariant implements IStringSerializable {
        ROBUST_TSTEEL("robust_tsteel"),
        CLEAN_SSTEEL("clean_ssteel"),
        STABLE_TITANIUM("stable_titanium"),
        FIREBOX_TITANIUM("titanium_firebox"),
        FUSION("fusion_casing"),// TODO delete not used metas
        _FUSION("fusion_casing2"),// TODO delete not used metas
        __FUSION("fusion_casing3"),
        FUSION_COIL("fusion_coil"),
        FUSION2("fusion_casing_mk2"),
        TURBINE("turbine"),
        TURBINE1("ssteel_turbine"),
        TURBINE2("titanium_turbine"),
        TURBINE3("tsteel_turbine"),
        ENGINE_INTAKE("engine_intake");

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
        public String getName() {
            return this.name;
        }

        static {
            for (EnumCasingVariant casingVariant : values()) {
                META_LOOKUP[casingVariant.getMetadata()] = casingVariant;
            }
        }
    }
}
