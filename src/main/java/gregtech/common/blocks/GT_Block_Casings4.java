package gregtech.common.blocks;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeTurbine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GT_Block_Casings4 extends GT_Block_Casings_Abstract {

    public static boolean mConnectedMachineTextures = true;

    public GT_Block_Casings4() {
        super(GT_Item_Casings4.class, "gt.blockcasings4", GT_Material_Casings.INSTANCE);
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            Textures.BlockIcons.CASING_BLOCKS[(i + 48)] = new GT_CopiedBlockTexture(this, 6, i);
        }
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Robust Tungstensteel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Clean Stainless Steel Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Stable Titanium Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Titanium Firebox Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");
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
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ImmutableList<BakedQuad> getIcon(EnumFacing aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getQuads(aSide);
            case 1:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getQuads(aSide);
            case 2:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getQuads(aSide);
            case 3:
                return aSide.getIndex() > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TITANIUM.getQuads(aSide) : Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getQuads(aSide);
            case 4:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW.getQuads(aSide);
            case 5:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS.getQuads(aSide);
            case 6:
                return Textures.BlockIcons.MACHINE_CASING_FUSION.getQuads(aSide);
            case 7:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_COIL.getQuads(aSide);
            case 8:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_2.getQuads(aSide);
            case 9:
                return Textures.BlockIcons.MACHINE_CASING_TURBINE.getQuads(aSide);
            case 10:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getQuads(aSide);
            case 11:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getQuads(aSide);
            case 12:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getQuads(aSide);
            case 13:
                return Textures.BlockIcons.MACHINE_CASING_ENGINE_INTAKE.getQuads(aSide);
            case 14:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getQuads(aSide);
            case 15:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getQuads(aSide);
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getQuads(aSide);
    }

    @SideOnly(Side.CLIENT)
    public ImmutableList<BakedQuad> getTurbineCasing(int meta, int iconIndex, boolean active, EnumFacing aSide) {
        switch (meta) {
            case 9:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getQuads(aSide) : Textures.BlockIcons.TURBINE[iconIndex].getQuads(aSide);
            case 10:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE1[iconIndex].getQuads(aSide) : Textures.BlockIcons.TURBINE1[iconIndex].getQuads(aSide);
            case 11:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE2[iconIndex].getQuads(aSide) : Textures.BlockIcons.TURBINE2[iconIndex].getQuads(aSide);
            case 12:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE3[iconIndex].getQuads(aSide) : Textures.BlockIcons.TURBINE3[iconIndex].getQuads(aSide);
            default:
                return active ? Textures.BlockIcons.TURBINE_ACTIVE[iconIndex].getQuads(aSide) : Textures.BlockIcons.TURBINE[iconIndex].getQuads(aSide);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ImmutableList<BakedQuad> getIcon(IBlockAccess aWorld, BlockPos pos, EnumFacing aSide, int tMeta) {
        if(pos == null) {
            return getIcon(aSide, tMeta);
        }
        if ((tMeta != 6) && (tMeta != 8) && (tMeta != 9) && (tMeta != 10) && (tMeta != 11) && (tMeta != 12) || (!mConnectedMachineTextures)) {
            return getIcon(aSide, tMeta);
        }
        int tStartIndex = tMeta == 6 ? 1 : 13;
        if ((tMeta == 9) || (tMeta == 10) || (tMeta == 11) || (tMeta == 12)) {
            if ((aSide == EnumFacing.NORTH) || (aSide == EnumFacing.SOUTH)) {
                TileEntity tTileEntity;
                IMetaTileEntity tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.SOUTH ? 1 : -1, -1, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 0, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.SOUTH ? 1 : -1, 0, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 3, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.SOUTH ? 1 : -1, 1, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 6, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.down()))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 1, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.up()))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 7, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.NORTH ? 1 : -1, 1, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 8, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.NORTH ? 1 : -1, 0, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 5, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(aSide == EnumFacing.NORTH ? 1 : -1, -1, 0)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 2, false, aSide);
                }
            } else if ((aSide == EnumFacing.WEST) || (aSide == EnumFacing.EAST)) {
                TileEntity tTileEntity;
                Object tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, -1, aSide == EnumFacing.WEST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 0, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, 0, aSide == EnumFacing.WEST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 3, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, 1, aSide == EnumFacing.WEST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 6, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.down()))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 1, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.up()))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 7, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, 1, aSide == EnumFacing.EAST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 8, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, 0, aSide == EnumFacing.EAST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 5, false, aSide);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(pos.add(0, -1, aSide == EnumFacing.EAST ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide.getIndex()) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true, aSide);
                    }
                    return getTurbineCasing(tMeta, 2, false, aSide);
                }
            }
            switch (tMeta) {
                case 9:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getQuads(aSide);
                case 10:
                    return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getQuads(aSide);
                case 11:
                    return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getQuads(aSide);
                case 12:
                    return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getQuads(aSide);
                default:
                    return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getQuads(aSide);
            }
        }
        boolean[] tConnectedSides = {
                stateAndMetaEquals(aWorld, pos.down(), tMeta),
                stateAndMetaEquals(aWorld, pos.up(), tMeta),
                stateAndMetaEquals(aWorld, pos.north(), tMeta),
                stateAndMetaEquals(aWorld, pos.south(), tMeta),
                stateAndMetaEquals(aWorld, pos.west(), tMeta),
                stateAndMetaEquals(aWorld, pos.east(), tMeta)};
        switch (aSide) {
            case DOWN:
                if (tConnectedSides[0]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[2])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
                if ((!tConnectedSides[5]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
            case UP:
                if (tConnectedSides[1]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
            case NORTH:
                if (tConnectedSides[5]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
            case SOUTH:
                if (tConnectedSides[3]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
            case WEST:
                if (tConnectedSides[4]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
            case EAST:
                if (tConnectedSides[2]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getQuads(aSide);
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex)].getQuads(aSide);
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getQuads(aSide);
                }
                break;
        }
        return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getQuads(aSide);
    }

    public boolean stateAndMetaEquals(IBlockAccess access, BlockPos blockPos, int meta) {
        IBlockState state = access.getBlockState(blockPos);
        return state.getBlock() == this && state.getValue(METADATA) == meta;
    }

}
