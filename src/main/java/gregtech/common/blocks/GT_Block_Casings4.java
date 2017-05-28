package gregtech.common.blocks;

import gregtech.api.enums.ItemList;
//import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
//import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.itemblocks.GT_Item_Casings4;
import gregtech.common.blocks.materials.GT_Material_Casings;
//import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeTurbine;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings4 extends GT_Block_Casings_Abstract {

    public static final PropertyEnum<EnumCasingVariant> CASING_VARIANT = PropertyEnum.create("casing_variant", EnumCasingVariant.class);

    public static boolean mConnectedMachineTextures = true;

    public GT_Block_Casings4() {
        super("blockcasings4", GT_Item_Casings4.class, GT_Material_Casings.INSTANCE);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(CASING_VARIANT, EnumCasingVariant.ROBUST_TSTEEL));

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Robust Tungstensteel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Clean Stainless Steel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Stable Titanium Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Titanium Firebox Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");// not used
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Fusion Casing");// not used
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
        switch (meta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL;
            case 1:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL;
            case 2:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM;
            case 3:
                return side.getIndex() > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TITANIUM : Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM;
            case 4:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW;
            case 5:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS;
            case 6:
                return Textures.BlockIcons.MACHINE_CASING_FUSION;
            case 7:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_COIL;
            case 8:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_2;
            case 9:
                return Textures.BlockIcons.MACHINE_CASING_TURBINE;
            case 10:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL;
            case 11:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM;
            case 12:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL;
            case 13:
                return Textures.BlockIcons.MACHINE_CASING_ENGINE_INTAKE;
            case 14:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL;
            case 15:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL;
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL;
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTurbineCasing(int meta, int iconIndex, boolean active, EnumFacing side) {
        switch (meta) {
            case 9:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getIcon() : Textures.BlockIcons.TURBINE[iconIndex].getIcon();
            case 10:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE1[iconIndex].getIcon() : Textures.BlockIcons.TURBINE1[iconIndex].getIcon();
            case 11:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE2[iconIndex].getIcon() : Textures.BlockIcons.TURBINE2[iconIndex].getIcon();
            case 12:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE3[iconIndex].getIcon() : Textures.BlockIcons.TURBINE3[iconIndex].getIcon();
            default:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getIcon() : Textures.BlockIcons.TURBINE[iconIndex].getIcon();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite getWorldIcon(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        int meta = state.getValue(METADATA);
        if(pos == null) {
            return getIcon(side, meta);
        }
        if ((meta != 6) && (meta != 8) && (meta != 9) && (meta != 10) && (meta != 11) && (meta != 12) || (!mConnectedMachineTextures)) {
            return getIcon(side, meta);
        }
        int startIndex = meta == 6 ? 1 : 13;
        if ((meta == 9) || (meta == 10) || (meta == 11) || (meta == 12)) {
            if ((side == EnumFacing.NORTH) || (side == EnumFacing.SOUTH)) {
                TileEntity tileEntity;
                IMetaTileEntity metaTileEntity;
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.SOUTH ? 1 : -1, -1, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 0, true, side);
                    }
                    return getTurbineCasing(meta, 0, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.SOUTH ? 1 : -1, 0, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 3, true, side);
                    }
                    return getTurbineCasing(meta, 3, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.SOUTH ? 1 : -1, 1, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 6, true, side);
                    }
                    return getTurbineCasing(meta, 6, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.down()))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 1, true, side);
                    }
                    return getTurbineCasing(meta, 1, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.up()))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 7, true, side);
                    }
                    return getTurbineCasing(meta, 7, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.NORTH ? 1 : -1, 1, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 8, true, side);
                    }
                    return getTurbineCasing(meta, 8, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.NORTH ? 1 : -1, 0, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 5, true, side);
                    }
                    return getTurbineCasing(meta, 5, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(side == EnumFacing.NORTH ? 1 : -1, -1, 0)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 2, true, side);
                    }
                    return getTurbineCasing(meta, 2, false, side);
                }
            } else if ((side == EnumFacing.WEST) || (side == EnumFacing.EAST)) {
                TileEntity tileEntity;
                Object metaTileEntity;
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, -1, side == EnumFacing.WEST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 0, true, side);
                    }
                    return getTurbineCasing(meta, 0, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, 0, side == EnumFacing.WEST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 3, true, side);
                    }
                    return getTurbineCasing(meta, 3, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, 1, side == EnumFacing.WEST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 6, true, side);
                    }
                    return getTurbineCasing(meta, 6, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.down()))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 1, true, side);
                    }
                    return getTurbineCasing(meta, 1, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.up()))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 7, true, side);
                    }
                    return getTurbineCasing(meta, 7, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, 1, side == EnumFacing.EAST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 8, true, side);
                    }
                    return getTurbineCasing(meta, 8, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, 0, side == EnumFacing.EAST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 5, true, side);
                    }
                    return getTurbineCasing(meta, 5, false, side);
                }
                if ((null != (tileEntity = world.getTileEntity(pos.add(0, -1, side == EnumFacing.EAST ? 1 : -1)))) && ((tileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tileEntity).getFrontFacing() == side.getIndex()) && (null != (metaTileEntity = ((IGregTechTileEntity) tileEntity).getMetaTileEntity())) && ((metaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tileEntity).isActive()) {
                        return getTurbineCasing(meta, 2, true, side);
                    }
                    return getTurbineCasing(meta, 2, false, side);
                }
            }
            switch (meta) {
                case 9:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
                case 10:
                    return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getIcon();
                case 11:
                    return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
                case 12:
                    return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
                default:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
            }
        }
        boolean[] connectedSides = {
                stateAndMetaEquals(world, pos.down(), meta),
                stateAndMetaEquals(world, pos.up(), meta),
                stateAndMetaEquals(world, pos.north(), meta),
                stateAndMetaEquals(world, pos.south(), meta),
                stateAndMetaEquals(world, pos.west(), meta),
                stateAndMetaEquals(world, pos.east(), meta)};
        switch (side) {
            case DOWN:
                if (connectedSides[0]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((connectedSides[4]) && (!connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (!connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((!connectedSides[4]) && (!connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((connectedSides[4]) && (!connectedSides[5]) && (!connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (!connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((!connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((!connectedSides[4]) && (!connectedSides[5]) && (!connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[4]) && (!connectedSides[2])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
                if ((!connectedSides[5]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
            case UP:
                if (connectedSides[1]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((connectedSides[4]) && (!connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (!connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((!connectedSides[4]) && (!connectedSides[5]) && (connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((connectedSides[4]) && (!connectedSides[5]) && (!connectedSides[2]) && (connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((connectedSides[4]) && (connectedSides[5]) && (!connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((!connectedSides[4]) && (connectedSides[5]) && (connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((!connectedSides[4]) && (!connectedSides[5]) && (!connectedSides[2]) && (!connectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
                if ((!connectedSides[3]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
            case NORTH:
                if (connectedSides[5]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((connectedSides[2]) && (!connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (!connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((connectedSides[2]) && (!connectedSides[0]) && (!connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (!connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((!connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[0]) && (!connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
            case SOUTH:
                if (connectedSides[3]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((connectedSides[2]) && (!connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (!connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[0]) && (connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((connectedSides[2]) && (!connectedSides[0]) && (!connectedSides[4]) && (connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((connectedSides[2]) && (connectedSides[0]) && (!connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((!connectedSides[2]) && (connectedSides[0]) && (connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[0]) && (!connectedSides[4]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[2]) && (!connectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
            case WEST:
                if (connectedSides[4]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((connectedSides[0]) && (!connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (!connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((connectedSides[0]) && (!connectedSides[3]) && (!connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (!connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((!connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[3]) && (!connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
                if ((!connectedSides[3]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
            case EAST:
                if (connectedSides[2]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 6)].getIcon();
                }
                if ((!connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 5)].getIcon();
                }
                if ((connectedSides[0]) && (!connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 2)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (!connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 3)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 4)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[3]) && (connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 11)].getIcon();
                }
                if ((connectedSides[0]) && (!connectedSides[3]) && (!connectedSides[1]) && (connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 8)].getIcon();
                }
                if ((connectedSides[0]) && (connectedSides[3]) && (!connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 9)].getIcon();
                }
                if ((!connectedSides[0]) && (connectedSides[3]) && (connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 10)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[3]) && (!connectedSides[1]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
                }
                if ((!connectedSides[0]) && (!connectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex)].getIcon();
                }
                if ((!connectedSides[3]) && (!connectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 1)].getIcon();
                }
                break;
        }
        return Textures.BlockIcons.CONNECTED_HULLS[(startIndex + 7)].getIcon();
    }

    public boolean stateAndMetaEquals(IBlockAccess access, BlockPos blockPos, int meta) {
        IBlockState state = access.getBlockState(blockPos);
        return state.getBlock() == this && state.getValue(METADATA) == meta;
    }

    public enum EnumCasingVariant implements IStringSerializable {
        ROBUST_TSTEEL("robust_tsteel"),
        CLEAN_SSTEEL("clean_ssteel"),
        STABLE_TITANIUM("stable_titanium"),
        FIREBOX_TITANIUM("titanium_firebox"),
        FUSION("fusion_casing"),
        _FUSION("fusion_casing2"),// TODO delete not used metas
        __FUSION("fusion_casing3"),// TODO delete not used metas
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
