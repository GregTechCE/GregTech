package gregtech.integration.jei.multiblock.infos;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.integration.jei.multiblock.MultiblockInfoPage;
import gregtech.integration.jei.multiblock.MultiblockShapeInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class DistillationTowerInfo extends MultiblockInfoPage {

    @Override
    public MultiblockControllerBase getController() {
        return MetaTileEntities.DISTILLATION_TOWER;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        MultiblockShapeInfo shapeInfo = MultiblockShapeInfo.builder()
            .aisle("XSX", "XXX", "XXX", "XXX", "XXX", "XXX")
            .aisle("XFB", "X#X", "X#X", "X#X", "X#X" ,"XXX")
            .aisle("XEX", "XXX", "XHX", "XXX", "XXX", "XXX")
            .where('X', MetaBlocks.METAL_CASING.getState(MetalCasingType.STAINLESS_CLEAN))
            .where('S', MetaTileEntities.DISTILLATION_TOWER, EnumFacing.NORTH)
            .where('#', Blocks.AIR.getDefaultState())
            .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.EV], EnumFacing.DOWN)
            .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.EV], EnumFacing.SOUTH)
            .where('H', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.EV], EnumFacing.SOUTH)
            .where('B', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.EV], EnumFacing.EAST)
            .build();
        return Lists.newArrayList(shapeInfo);
    }

    @Override
    public String[] getDescription() {
        return new String[] {I18n.format("gregtech.multiblock.distillation_tower.description")};
    }

}
