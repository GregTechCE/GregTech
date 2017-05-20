package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.GT_Item_Casings2;
import gregtech.common.blocks.materials.GT_Material_Casings;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings2 extends GT_Block_Casings_Abstract {

    public static final PropertyEnum<EnumCasingVariant> CASING_VARIANT = PropertyEnum.create("casing_variant", EnumCasingVariant.class);

    public GT_Block_Casings2() {
        super("blockcasings2", GT_Item_Casings2.class, GT_Material_Casings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.SOLIDSTEEL));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Solid Steel Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Frost Proof Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Bronze Gear Box Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Steel Gear Box Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Titanium Gear Box Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Assembling Line Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Processor Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Data Drive Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Containment Field Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Assembler Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Pump Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Motor Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Bronze Pipe Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Steel Pipe Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Titanium Pipe Machine Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Tungstensteel Pipe Machine Casing");

        ItemList.Casing_SolidSteel.set(new ItemStack(this, 1, 0));
        ItemList.Casing_FrostProof.set(new ItemStack(this, 1, 1));
        ItemList.Casing_Gearbox_Bronze.set(new ItemStack(this, 1, 2));
        ItemList.Casing_Gearbox_Steel.set(new ItemStack(this, 1, 3));
        ItemList.Casing_Gearbox_Titanium.set(new ItemStack(this, 1, 4));
        ItemList.Casing_Gearbox_TungstenSteel.set(new ItemStack(this, 1, 5));
        ItemList.Casing_Processor.set(new ItemStack(this, 1, 6));
        ItemList.Casing_DataDrive.set(new ItemStack(this, 1, 7));
        ItemList.Casing_ContainmentField.set(new ItemStack(this, 1, 8));
        ItemList.Casing_Assembler.set(new ItemStack(this, 1, 9));
        ItemList.Casing_Pump.set(new ItemStack(this, 1, 10));
        ItemList.Casing_Motor.set(new ItemStack(this, 1, 11));
        ItemList.Casing_Pipe_Bronze.set(new ItemStack(this, 1, 12));
        ItemList.Casing_Pipe_Steel.set(new ItemStack(this, 1, 13));
        ItemList.Casing_Pipe_Titanium.set(new ItemStack(this, 1, 14));
        ItemList.Casing_Pipe_TungstenSteel.set(new ItemStack(this, 1, 15));
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
//                return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL;
//            case 1:
//                return Textures.BlockIcons.MACHINE_CASING_FROST_PROOF;
//            case 2:
//                return Textures.BlockIcons.MACHINE_CASING_GEARBOX_BRONZE;
//            case 3:
//                return Textures.BlockIcons.MACHINE_CASING_GEARBOX_STEEL;
//            case 4:
//                return Textures.BlockIcons.MACHINE_CASING_GEARBOX_TITANIUM;
//            case 5:
//                return Textures.BlockIcons.MACHINE_CASING_GEARBOX_TUNGSTENSTEEL;
//            case 6:
//                return Textures.BlockIcons.MACHINE_CASING_PROCESSOR;
//            case 7:
//                return Textures.BlockIcons.MACHINE_CASING_DATA_DRIVE;
//            case 8:
//                return Textures.BlockIcons.MACHINE_CASING_CONTAINMENT_FIELD;
//            case 9:
//                return Textures.BlockIcons.MACHINE_CASING_ASSEMBLER;
//            case 10:
//                return Textures.BlockIcons.MACHINE_CASING_PUMP;
//            case 11:
//                return Textures.BlockIcons.MACHINE_CASING_MOTOR;
//            case 12:
//                return Textures.BlockIcons.MACHINE_CASING_PIPE_BRONZE;
//            case 13:
//                return Textures.BlockIcons.MACHINE_CASING_PIPE_STEEL;
//            case 14:
//                return Textures.BlockIcons.MACHINE_CASING_PIPE_TITANIUM;
//            case 15:
//                return Textures.BlockIcons.MACHINE_CASING_PIPE_TUNGSTENSTEEL;
//        }
//        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL;
//    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        return world.getBlockState(pos).getValue(CASING_VARIANT) == EnumCasingVariant.CONTAINMENTFIELD ?
                Blocks.BEDROCK.getExplosionResistance(world, pos, exploder, explosion) :
                super.getExplosionResistance(world, pos, exploder, explosion);
    }

    public static enum EnumCasingVariant implements IStringSerializable {
        SOLIDSTEEL("solidsteel"),
        FROSTPROOF("frostproof"),
        GEARBOX_BRONZE("gearbox_bronze"),
        GEARBOX_STEEL("gearbox_steel"),
        GEARBOX_TITANIUM("gearbox_titanium"),
        GEARBOX_TSTEEL("gearbox_tsteel"),
        PROCESSOR("processor"),
        DATADRIVE("datadrive"),
        CONTAINMENTFIELD("containmentfield"),
        ASSEMBLER("assembler"),
        PUMP("pump"),
        MOTOR("motor"),
        PIPE_BRONZE("pipe_bronze"),
        PIPE_STEEL("pipe_steel"),
        PIPE_TITANIUM("pipe_titanium"),
        PIPE_TSTEEL("pipe_tsteel");

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
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }

            return values()[meta];
        }

        @Override
        public String getName()
        {
            return this.name;
        }
    }
}
