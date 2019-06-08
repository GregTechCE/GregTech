package gregtech.common.covers;

import gregtech.api.GTValues;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.ICoverable;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.render.Textures;
import gregtech.api.util.GTLog;
import gregtech.common.covers.filter.OreDictionaryItemFilter;
import gregtech.common.covers.filter.SimpleFluidFilter;
import gregtech.common.covers.filter.SimpleItemFilter;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.CoverPlaceBehavior;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiFunction;

public class CoverBehaviors {

    public static void init() {
        GTLog.logger.info("Registering cover behaviors...");
        registerBehavior(0, new ResourceLocation(GTValues.MODID, "conveyor.lv"), MetaItems.CONVEYOR_MODULE_LV, (tile, side) -> new CoverConveyor(tile, side, GTValues.LV, 8));
        registerBehavior(1, new ResourceLocation(GTValues.MODID, "conveyor.mv"), MetaItems.CONVEYOR_MODULE_MV, (tile, side) -> new CoverConveyor(tile, side, GTValues.MV, 32));
        registerBehavior(2, new ResourceLocation(GTValues.MODID, "conveyor.hv"), MetaItems.CONVEYOR_MODULE_HV, (tile, side) -> new CoverConveyor(tile, side, GTValues.HV, 64));
        registerBehavior(3, new ResourceLocation(GTValues.MODID, "conveyor.ev"), MetaItems.CONVEYOR_MODULE_EV, (tile, side) -> new CoverConveyor(tile, side, GTValues.EV, 3 * 64));
        registerBehavior(4, new ResourceLocation(GTValues.MODID, "conveyor.iv"), MetaItems.CONVEYOR_MODULE_IV, (tile, side) -> new CoverConveyor(tile, side, GTValues.IV, 8 * 64));
        registerBehavior(5, new ResourceLocation(GTValues.MODID, "conveyor.luv"), MetaItems.CONVEYOR_MODULE_LUV, (tile, side) -> new CoverConveyor(tile, side, GTValues.LuV, 16 * 64));
        registerBehavior(6, new ResourceLocation(GTValues.MODID, "conveyor.zpm"), MetaItems.CONVEYOR_MODULE_ZPM, (tile, side) -> new CoverConveyor(tile, side, GTValues.ZPM, 16 * 64));
        registerBehavior(7, new ResourceLocation(GTValues.MODID, "conveyor.uv"), MetaItems.CONVEYOR_MODULE_UV, (tile, side) -> new CoverConveyor(tile, side, GTValues.UV, 16 * 64));

        registerBehavior(10, new ResourceLocation(GTValues.MODID, "robotic_arm.lv"), MetaItems.ROBOT_ARM_LV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.LV, 8));
        registerBehavior(11, new ResourceLocation(GTValues.MODID, "robotic_arm.mv"), MetaItems.ROBOT_ARM_MV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.MV, 32));
        registerBehavior(12, new ResourceLocation(GTValues.MODID, "robotic_arm.hv"), MetaItems.ROBOT_ARM_HV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.HV, 64));
        registerBehavior(13, new ResourceLocation(GTValues.MODID, "robotic_arm.ev"), MetaItems.ROBOT_ARM_EV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.EV, 3 * 64));
        registerBehavior(14, new ResourceLocation(GTValues.MODID, "robotic_arm.iv"), MetaItems.ROBOT_ARM_IV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.IV, 8 * 64));
        registerBehavior(15, new ResourceLocation(GTValues.MODID, "robotic_arm.luv"), MetaItems.ROBOT_ARM_LUV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.LuV, 16 * 64));
        registerBehavior(16, new ResourceLocation(GTValues.MODID, "robotic_arm.zpm"), MetaItems.ROBOT_ARM_ZPM, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.ZPM, 16 * 64));
        registerBehavior(17, new ResourceLocation(GTValues.MODID, "robotic_arm.uv"), MetaItems.ROBOT_ARM_UV, (tile, side) -> new CoverRoboticArm(tile, side, GTValues.UV, 16 * 64));

        registerBehavior(20, new ResourceLocation(GTValues.MODID, "pump.lv"), MetaItems.ELECTRIC_PUMP_LV, (tile, side) -> new CoverPump(tile, side, GTValues.LV, 640));
        registerBehavior(21, new ResourceLocation(GTValues.MODID, "pump.mv"), MetaItems.ELECTRIC_PUMP_MV, (tile, side) -> new CoverPump(tile, side, GTValues.MV, 2560));
        registerBehavior(22, new ResourceLocation(GTValues.MODID, "pump.hv"), MetaItems.ELECTRIC_PUMP_HV, (tile, side) -> new CoverPump(tile, side, GTValues.HV, 10240));
        registerBehavior(23, new ResourceLocation(GTValues.MODID, "pump.ev"), MetaItems.ELECTRIC_PUMP_EV, (tile, side) -> new CoverPump(tile, side, GTValues.EV, 40490));
        registerBehavior(24, new ResourceLocation(GTValues.MODID, "pump.iv"), MetaItems.ELECTRIC_PUMP_IV, (tile, side) -> new CoverPump(tile, side, GTValues.IV, 163840));
        registerBehavior(25, new ResourceLocation(GTValues.MODID, "pump.luv"), MetaItems.ELECTRIC_PUMP_LUV, (tile, side) -> new CoverPump(tile, side, GTValues.LuV, 655360));
        registerBehavior(26, new ResourceLocation(GTValues.MODID, "pump.zpm"), MetaItems.ELECTRIC_PUMP_ZPM, (tile, side) -> new CoverPump(tile, side, GTValues.ZPM, 655360));
        registerBehavior(27, new ResourceLocation(GTValues.MODID, "pump.uv"), MetaItems.ELECTRIC_PUMP_UV, (tile, side) -> new CoverPump(tile, side, GTValues.UV, 655360));

        registerBehavior(30, new ResourceLocation(GTValues.MODID, "ore_dictionary_filter"), MetaItems.ORE_DICTIONARY_FILTER, (tile, side) -> new CoverItemFilter(tile, side, "cover.ore_dictionary_filter.title", Textures.ORE_DICTIONARY_FILTER_OVERLAY, new OreDictionaryItemFilter()));
        registerBehavior(31, new ResourceLocation(GTValues.MODID, "item_filter"), MetaItems.ITEM_FILTER, (tile, side) -> new CoverItemFilter(tile, side, "cover.item_filter.title", Textures.ITEM_FILTER_FILTER_OVERLAY, new SimpleItemFilter()));
        registerBehavior(32, new ResourceLocation(GTValues.MODID, "fluid_filter"), MetaItems.FLUID_FILTER, (tile, side) -> new CoverFluidFilter(tile, side, "cover.fluid_filter.title", Textures.FLUID_FILTER_OVERLAY, new SimpleFluidFilter()));
        registerBehavior(33, new ResourceLocation(GTValues.MODID, "shutter"), MetaItems.COVER_SHUTTER, CoverShutter::new);

        registerBehavior(34, new ResourceLocation(GTValues.MODID, "solar_panel.basic"), MetaItems.COVER_SOLAR_PANEL, (tile, side) -> new CoverSolarPanel(tile, side, 1));
        registerBehavior(35, new ResourceLocation(GTValues.MODID, "solar_panel.ulv"), MetaItems.COVER_SOLAR_PANEL_ULV, (tile, side) -> new CoverSolarPanel(tile, side, 8));
        registerBehavior(36, new ResourceLocation(GTValues.MODID, "solar_panel.lv"), MetaItems.COVER_SOLAR_PANEL_LV, (tile, side) -> new CoverSolarPanel(tile, side, 32));

        registerBehavior(37, new ResourceLocation(GTValues.MODID, "machine_controller"), MetaItems.COVER_MACHINE_CONTROLLER, CoverMachineController::new);
    }

    public static void registerBehavior(int coverNetworkId, ResourceLocation coverId, MetaValueItem placerItem, BiFunction<ICoverable, EnumFacing, CoverBehavior> behaviorCreator) {
        CoverDefinition coverDefinition = new CoverDefinition(coverId, behaviorCreator, placerItem.getStackForm());
        CoverDefinition.registerCover(coverNetworkId, coverDefinition);
        placerItem.addStats(new CoverPlaceBehavior(coverDefinition));
    }
}
