package gregtech.integration.jei.multiblock.infos;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityLargeTurbine;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class LargeTurbineInfo extends MultiblockInfoPage {

    public final MetaTileEntityLargeTurbine turbine;

    public LargeTurbineInfo(MetaTileEntityLargeTurbine turbine) {
        this.turbine = turbine;
    }

    @Override
    public MultiblockControllerBase getController() {
        return turbine;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        MultiblockShapeInfo.Builder shapeInfo = MultiblockShapeInfo.builder()
                .aisle("CCCC", "CIOC", "CCCC")
                .aisle("CCCC", "R##D", "CCCC")
                .aisle("CCCC", "CSCC", "CCCC")
                .where('S', turbine, EnumFacing.SOUTH)
                .where('C', turbine.turbineType.casingState)
                .where('R', MetaTileEntities.ROTOR_HOLDER[GTValues.EV], EnumFacing.WEST)
                .where('D', MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.EV], EnumFacing.EAST)
                .where('#', Blocks.AIR.getDefaultState())
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.HV], EnumFacing.NORTH);
        if(turbine.recipeMap.getMaxFluidOutputs() > 0) {
            shapeInfo.where('O', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.EV], EnumFacing.NORTH);
        } else {
            shapeInfo.where('O', turbine.turbineType.casingState);
        }
        return Lists.newArrayList(shapeInfo.build());
    }

    @Override
    public String[] getDescription() {
        return new String[] {I18n.format("gregtech.multiblock.large_turbine.description")};
    }

}
