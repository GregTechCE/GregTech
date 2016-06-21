package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.tileentities.machines.multi.GT_MetaTileEntity_LargeTurbine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class GT_Block_Casings4
        extends GT_Block_Casings_Abstract {
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
//    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Fusion Casing");
//    GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Fusion Casing");
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

    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
            case 1:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getIcon();
            case 2:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
            case 3:
                return aSide > 1 ? Textures.BlockIcons.MACHINE_CASING_FIREBOX_TITANIUM.getIcon() : Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
            case 4:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW.getIcon();
            case 5:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS.getIcon();
            case 6:
                return Textures.BlockIcons.MACHINE_CASING_FUSION.getIcon();
            case 7:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_COIL.getIcon();
            case 8:
                return Textures.BlockIcons.MACHINE_CASING_FUSION_2.getIcon();
            case 9:
                return Textures.BlockIcons.MACHINE_CASING_TURBINE.getIcon();
            case 10:
                return Textures.BlockIcons.MACHINE_CASING_CLEAN_STAINLESSSTEEL.getIcon();
            case 11:
                return Textures.BlockIcons.MACHINE_CASING_STABLE_TITANIUM.getIcon();
            case 12:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
            case 13:
                return Textures.BlockIcons.MACHINE_CASING_ENGINE_INTAKE.getIcon();
            case 14:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
            case 15:
                return Textures.BlockIcons.MACHINE_CASING_ROBUST_TUNGSTENSTEEL.getIcon();
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }

    public IIcon getTurbineCasing(int meta, int iconIndex, boolean active) {
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
    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        if ((tMeta != 6) && (tMeta != 8) && (tMeta != 9) && (tMeta != 10) && (tMeta != 11) && (tMeta != 12) || (!mConnectedMachineTextures)) {
            return getIcon(aSide, tMeta);
        }
        int tStartIndex = tMeta == 6 ? 1 : 13;
        if ((tMeta == 9) || (tMeta == 10) || (tMeta == 11) || (tMeta == 12)) {
            if ((aSide == 2) || (aSide == 3)) {
                TileEntity tTileEntity;
                IMetaTileEntity tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true);
                    }
                    return getTurbineCasing(tMeta, 0, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true);
                    }
                    return getTurbineCasing(tMeta, 3, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 3 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true);
                    }
                    return getTurbineCasing(tMeta, 6, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true);
                    }
                    return getTurbineCasing(tMeta, 1, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true);
                    }
                    return getTurbineCasing(tMeta, 7, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true);
                    }
                    return getTurbineCasing(tMeta, 8, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true);
                    }
                    return getTurbineCasing(tMeta, 5, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord + (aSide == 2 ? 1 : -1), yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true);
                    }
                    return getTurbineCasing(tMeta, 2, false);
                }
            } else if ((aSide == 4) || (aSide == 5)) {
                TileEntity tTileEntity;
                Object tMetaTileEntity;
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 0, true);
                    }
                    return getTurbineCasing(tMeta, 0, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 3, true);
                    }
                    return getTurbineCasing(tMeta, 3, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 4 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 6, true);
                    }
                    return getTurbineCasing(tMeta, 6, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 1, true);
                    }
                    return getTurbineCasing(tMeta, 1, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 7, true);
                    }
                    return getTurbineCasing(tMeta, 7, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord + 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 8, true);
                    }
                    return getTurbineCasing(tMeta, 8, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 5, true);
                    }
                    return getTurbineCasing(tMeta, 5, false);
                }
                if ((null != (tTileEntity = aWorld.getTileEntity(xCoord, yCoord - 1, zCoord + (aSide == 5 ? 1 : -1)))) && ((tTileEntity instanceof IGregTechTileEntity)) && (((IGregTechTileEntity) tTileEntity).getFrontFacing() == aSide) && (null != (tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity())) && ((tMetaTileEntity instanceof GT_MetaTileEntity_LargeTurbine))) {
                    if (((IGregTechTileEntity) tTileEntity).isActive()) {
                        return getTurbineCasing(tMeta, 2, true);
                    }
                    return getTurbineCasing(tMeta, 2, false);
                }
            }
            switch (tMeta) {
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
        boolean[] tConnectedSides = {(aWorld.getBlock(xCoord, yCoord - 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord - 1, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord + 1, zCoord) == this) && (aWorld.getBlockMetadata(xCoord, yCoord + 1, zCoord) == tMeta), (aWorld.getBlock(xCoord + 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord + 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord + 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord + 1) == tMeta), (aWorld.getBlock(xCoord - 1, yCoord, zCoord) == this) && (aWorld.getBlockMetadata(xCoord - 1, yCoord, zCoord) == tMeta), (aWorld.getBlock(xCoord, yCoord, zCoord - 1) == this) && (aWorld.getBlockMetadata(xCoord, yCoord, zCoord - 1) == tMeta)};
        switch (aSide) {
            case 0:
                if (tConnectedSides[0]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[2])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
                if ((!tConnectedSides[5]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
            case 1:
                if (tConnectedSides[1]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((tConnectedSides[4]) && (tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((!tConnectedSides[4]) && (tConnectedSides[5]) && (tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((!tConnectedSides[4]) && (!tConnectedSides[5]) && (!tConnectedSides[2]) && (!tConnectedSides[3])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
            case 2:
                if (tConnectedSides[5]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
            case 3:
                if (tConnectedSides[3]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((tConnectedSides[2]) && (tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((!tConnectedSides[2]) && (tConnectedSides[0]) && (tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[0]) && (!tConnectedSides[4]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[2]) && (!tConnectedSides[4])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
            case 4:
                if (tConnectedSides[4]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
            case 5:
                if (tConnectedSides[2]) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 6)].getIcon();
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 5)].getIcon();
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 2)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 3)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 4)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 11)].getIcon();
                }
                if ((tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 8)].getIcon();
                }
                if ((tConnectedSides[0]) && (tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 9)].getIcon();
                }
                if ((!tConnectedSides[0]) && (tConnectedSides[3]) && (tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 10)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[3]) && (!tConnectedSides[1]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
                }
                if ((!tConnectedSides[0]) && (!tConnectedSides[1])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 0)].getIcon();
                }
                if ((!tConnectedSides[3]) && (!tConnectedSides[5])) {
                    return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 1)].getIcon();
                }
                break;
        }
        return Textures.BlockIcons.CONNECTED_HULLS[(tStartIndex + 7)].getIcon();
    }
}