package gregtech.common.covers;

import gregtech.api.GTValues;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTLog;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.CoverPlaceBehavior;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiFunction;

public class CoverBehaviors {

    public static void init() {
        GTLog.logger.info("Registering cover behaviors...");
        registerBehavior(new ResourceLocation(GTValues.MODID, "conveyor.lv"), MetaItems.CONVEYOR_MODULE_LV, (tile, side) -> new CoverConveyor(tile, side, GTValues.LV, 100));
    }

    public static void registerBehavior(ResourceLocation coverId, MetaValueItem placerItem, BiFunction<MetaTileEntity, EnumFacing, CoverBehavior> behaviorCreator) {
        CoverDefinition coverDefinition = new CoverDefinition(coverId, behaviorCreator, placerItem.getStackForm());
        CoverDefinition.registerCover(coverDefinition);
        placerItem.addStats(new CoverPlaceBehavior(coverDefinition));
    }
}
