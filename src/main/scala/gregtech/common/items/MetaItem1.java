package gregtech.common.items;

import gregtech.api.items.OreDictNames;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.RandomPotionEffect;
import gregtech.common.items.behaviors.ColorSprayBehaviour;
import gregtech.common.items.behaviors.IntCircuitBehaviour;
import gregtech.common.items.behaviors.LighterBehaviour;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.L;
import static gregtech.common.items.MetaItems.*;

public class MetaItem1 extends MaterialMetaItem {

    public final Map<OrePrefix, OrePrefix> purifyMap = new HashMap<>();

    public MetaItem1() {
        super(OrePrefix.dustTiny, OrePrefix.dustSmall, OrePrefix.dust, OrePrefix.dustImpure, OrePrefix.dustPure,
            OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged, OrePrefix.gem, OrePrefix.nugget,
            OrePrefix.ingot, OrePrefix.ingotHot, OrePrefix.plate, OrePrefix.plateDense, OrePrefix.stick, OrePrefix.lens,
            OrePrefix.bolt, OrePrefix.screw, OrePrefix.ring, OrePrefix.foil,
            null, null, null, null, null, null, null, null, null, null, null, null);
        registerPurifyRecipes();
    }

    private void registerPurifyRecipes() {
        purifyMap.put(OrePrefix.crushed, OrePrefix.crushedCentrifuged);
        purifyMap.put(OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged);
        purifyMap.put(OrePrefix.dustImpure, OrePrefix.dust);
        purifyMap.put(OrePrefix.dustPure, OrePrefix.dust);
    }

    @Override
    public void registerSubItems() {
        CREDIT_COPPER = addItem(0, "credit.copper");
        CREDIT_CUPRONICKEL = addItem(1, "credit.cupronickel").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cupronickel, 907200L)));
        CREDIT_SILVER = addItem(2, "credit.silver");
        CREDIT_GOLD = addItem(3, "credit.gold");
        CREDIT_PLATINUM = addItem(4, "credit.platinum");
        CREDIT_OSMIUM = addItem(5, "credit.osmium");
        CREDIT_NAQUADAH = addItem(6, "credit.naquadah");
        CREDIT_DARMSTADTIUM = addItem(7, "credit.darmstadtium");

        COIN_GOLD_ANCIENT = addItem(8, "coin.gold.ancient").
            setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, 907200L)));
        COIN_DOGE = addItem(9, "coin.doge")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Brass, 907200L)));
        COIN_CHOCOLATE = addItem(10, "coin.chocolate")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.foil.materialAmount)))
            .addStats(new FoodStats(1, 0.1F, false, true, OreDictUnifier.get(OrePrefix.foil, Materials.Gold), new RandomPotionEffect(MobEffects.SPEED, 200, 1, 10)));

        MINECART_WHEELS_IRON = addItem(100, "minecart.wheels.iron");
        MINECART_WHEELS_STEEL = addItem(101, "minecart.wheels.steel");

        SHAPE_EMPTY = addItem(300, "shape.empty");

        SHAPE_MOLD_PLATE = addItem(301, "shape.mold.plate");
        SHAPE_MOLD_GEAR = addItem(303, "shape.mold.gear");
        SHAPE_MOLD_CREDIT = addItem(304, "shape.mold.credit");
        SHAPE_MOLD_BOTTLE = addItem(305, "shape.mold.bottle");
        SHAPE_MOLD_INGOT = addItem(306, "shape.mold.ingot");
        SHAPE_MOLD_BALL = addItem(307, "shape.mold.ball");
        SHAPE_MOLD_BLOCK = addItem(308, "shape.mold.block");
        SHAPE_MOLD_NUGGET = addItem(309, "shape.mold.nugget");
        SHAPE_MOLD_CYLINDER = addItem(313, "shape.mold.cylinder");
        SHAPE_MOLD_ANVIL = addItem(314, "shape.mold.anvil");
        SHAPE_MOLD_NAME = addItem(315, "shape.mold.name");
        SHAPE_MOLD_GEAR_SMALL = addItem(317, "shape.mold.gear.small");

        SHAPE_EXTRUDER_PLATE = addItem(350, "shape.extruder.plate");
        SHAPE_EXTRUDER_ROD = addItem(351, "shape.extruder.rod");
        SHAPE_EXTRUDER_BOLT = addItem(352, "shape.extruder.bolt");
        SHAPE_EXTRUDER_RING = addItem(353, "shape.extruder.ring");
        SHAPE_EXTRUDER_CELL = addItem(354, "shape.extruder.cell");
        SHAPE_EXTRUDER_INGOT = addItem(355, "shape.extruder.ingot");
        SHAPE_EXTRUDER_WIRE = addItem(356, "shape.extruder.wire");
        SHAPE_EXTRUDER_PIPE_TINY = addItem(358, "shape.extruder.pipe.tiny").setInvisible();
        SHAPE_EXTRUDER_PIPE_SMALL = addItem(359, "shape.extruder.pipe.small").setInvisible();
        SHAPE_EXTRUDER_PIPE_MEDIUM = addItem(360, "shape.extruder.pipe.medium").setInvisible();
        SHAPE_EXTRUDER_PIPE_LARGE = addItem(361, "shape.extruder.pipe.large").setInvisible();
        SHAPE_EXTRUDER_PIPE_HUGE = addItem(362, "shape.extruder.pipe.huge").setInvisible();
        SHAPE_EXTRUDER_BLOCK = addItem(363, "shape.extruder.block");
        SHAPE_EXTRUDER_SWORD = addItem(364, "shape.extruder.sword");
        SHAPE_EXTRUDER_PICKAXE = addItem(365, "shape.extruder.pickaxe");
        SHAPE_EXTRUDER_SHOVEL = addItem(366, "shape.extruder.shovel");
        SHAPE_EXTRUDER_AXE = addItem(367, "shape.extruder.axe");
        SHAPE_EXTRUDER_HOE = addItem(368, "shape.extruder.hoe");
        SHAPE_EXTRUDER_HAMMER = addItem(369, "shape.extruder.hammer");
        SHAPE_EXTRUDER_FILE = addItem(370, "shape.extruder.file");
        SHAPE_EXTRUDER_SAW = addItem(371, "shape.extruder.saw");
        SHAPE_EXTRUDER_GEAR = addItem(372, "shape.extruder.gear");
        SHAPE_EXTRUDER_BOTTLE = addItem(373, "shape.extruder.bottle");

        SHAPE_SLICER_FLAT = addItem(398, "shape.slicer.flat").setInvisible();
        SHAPE_SLICER_STRIPES = addItem(399, "shape.slicer.stripes").setInvisible();

        FUEL_CAN_PLASTIC_EMPTY = addItem(400, "fuel.can.plastic.empty")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)))
            .setInvisible();
        FUEL_CAN_PLASTIC_FILLED = addItem(401, "fuel.can.plastic.filled")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)))
            .setInvisible();

        SPRAY_EMPTY = addItem(402, "spray.empty")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Tin, OrePrefix.plate.materialAmount * 2L), new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount)));

        LARGE_FLUID_CELL_STEEL = addItem(405, "large_fluid_cell.steel")
            .addStats(new FluidStats(16000, Integer.MAX_VALUE, Integer.MAX_VALUE, true))
            .setMaxStackSize(16)
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        LARGE_FLUID_CELL_TUNGSTEN_STEEL = addItem(406, "large_fluid_cell.tungstensteel")
            .addStats(new FluidStats(64000, Integer.MAX_VALUE, Integer.MAX_VALUE, true))
            .setMaxStackSize(16)
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.TungstenSteel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            SPRAY_CAN_DYES[i] = addItem(430 + 2 * i, "spray.can.dyes." + EnumDyeColor.byMetadata(i).getName());
            SPRAY_CAN_DYES_USED[i] = addItem(431 + 2 * i, "spray.can.dyes.used." + EnumDyeColor.byMetadata(i).getName());

            ColorSprayBehaviour behaviour = new ColorSprayBehaviour(SPRAY_EMPTY.getStackForm(), SPRAY_CAN_DYES_USED[i].getStackForm(), SPRAY_CAN_DYES[i].getStackForm(), 512L, i);
            SPRAY_CAN_DYES[i].addStats(behaviour);
            SPRAY_CAN_DYES_USED[i].addStats(behaviour);
        }

        TOOL_MATCHES = addItem(471, "tool.matches");
        LighterBehaviour behaviour = new LighterBehaviour(null, TOOL_MATCHES.getStackForm(), TOOL_MATCHES.getStackForm(), 1L);
        TOOL_MATCHES.addStats(behaviour);

        TOOL_MATCHBOX_USED = addItem(472, "tool.matchbox.used");
        TOOL_MATCHBOX_FULL = addItem(473, "tool.matchbox.full");
        behaviour = new LighterBehaviour(null, TOOL_MATCHBOX_USED.getStackForm(), TOOL_MATCHBOX_FULL.getStackForm(), 16L);
        TOOL_MATCHBOX_USED.addStats(behaviour);
        TOOL_MATCHBOX_FULL.addStats(behaviour);

        TOOL_LIGHTER_INVAR_EMPTY = addItem(474, "tool.lighter.invar.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));

        TOOL_LIGHTER_INVAR_USED = addItem(475, "tool.lighter.invar.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));
        TOOL_LIGHTER_INVAR_FULL = addItem(476, "tool.lighter.invar.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));
        behaviour = new LighterBehaviour(TOOL_LIGHTER_INVAR_EMPTY.getStackForm(), TOOL_LIGHTER_INVAR_USED.getStackForm(), TOOL_LIGHTER_INVAR_FULL.getStackForm(), 100L);
        TOOL_LIGHTER_INVAR_USED.addStats(behaviour);
        TOOL_LIGHTER_INVAR_FULL.addStats(behaviour);

        TOOL_LIGHTER_PLATINUM_EMPTY = addItem(477, "tool.lighter.platinum.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));

        TOOL_LIGHTER_PLATINUM_USED = addItem(478, "tool.lighter.platinum.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));
        TOOL_LIGHTER_PLATINUM_FULL = addItem(479, "tool.lighter.platinum.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));
        behaviour = new LighterBehaviour(TOOL_LIGHTER_PLATINUM_EMPTY.getStackForm(), TOOL_LIGHTER_PLATINUM_USED.getStackForm(), TOOL_LIGHTER_PLATINUM_FULL.getStackForm(), 1000L);
        TOOL_LIGHTER_PLATINUM_USED.addStats(behaviour);
        TOOL_LIGHTER_PLATINUM_FULL.addStats(behaviour);

        INGOT_IRIDIUM_ALLOY = addItem(480, "ingot.iridiumalloy").setInvisible();

        SCHEMATIC = addItem(490, "schematic")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();
        SCHEMATIC_CRAFTING = addItem(491, "schematic.crafting")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();
        SCHEMATIC_1X1 = addItem(495, "schematic.1by1")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();
        SCHEMATIC_2X2 = addItem(496, "schematic.2by2")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();
        SCHEMATIC_3X3 = addItem(497, "schematic.3by3")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();
        SCHEMATIC_DUST = addItem(498, "schematic.dust")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))
            .setInvisible();

        BATTERY_HULL_LV = addItem(500, "battery.hull.lv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount)));
        BATTERY_HULL_MV = addItem(501, "battery.hull.hv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 3L)));
        BATTERY_HULL_HV = addItem(502, "battery.hull.mv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 9L)));

        BATTERY_RE_ULV_TANTALUM = addItem(499, "battery.re.ulv.tantalum").addStats(ElectricStats.createRechargeableBattery(1000, 0));

        BATTERY_SU_LV_SULFURIC_ACID = addItem(510, "battery.su.lv.sulfuricacid").addStats(ElectricStats.createBattery(18000, 1, false)).setModelAmount(8);
        BATTERY_SU_LV_MERCURY = addItem(511, "battery.su.lv.mercury").addStats(ElectricStats.createBattery(32000, 1, false)).setModelAmount(8);

        BATTERY_RE_LV_CADMIUM = addItem(517, "battery.re.lv.cadmium").addStats(ElectricStats.createRechargeableBattery(75000, 1)).setModelAmount(8);
        BATTERY_RE_LV_LITHIUM = addItem(518, "battery.re.lv.lithium").addStats(ElectricStats.createRechargeableBattery(100000, 1)).setModelAmount(8);
        BATTERY_RE_LV_SODIUM = addItem(519, "battery.re.lv.sodium").addStats(ElectricStats.createRechargeableBattery(50000, 1)).setModelAmount(8);

        BATTERY_SU_MV_SULFURIC_ACID = addItem(520, "battery.su.mv.sulfuricacid").addStats(ElectricStats.createBattery(72000, 2, false)).setModelAmount(8);
        BATTERY_SU_MV_MERCURY = addItem(521, "battery.su.mv.mercury").addStats(ElectricStats.createBattery(128000, 2, false)).setModelAmount(8);

        BATTERY_RE_MV_CADMIUM = addItem(527, "battery.re.mv.cadmium").addStats(ElectricStats.createRechargeableBattery(300000, 2)).setModelAmount(8);
        BATTERY_RE_MV_LITHIUM = addItem(528, "battery.re.mv.lithium").addStats(ElectricStats.createRechargeableBattery(400000, 2)).setModelAmount(8);
        BATTERY_RE_MV_SODIUM = addItem(529, "battery.re.mv.sodium").addStats(ElectricStats.createRechargeableBattery(200000, 2)).setModelAmount(8);

        BATTERY_SU_HV_SULFURIC_ACID = addItem(530, "battery.su.hv.sulfuricacid").addStats(ElectricStats.createBattery(288000, 3, false)).setModelAmount(8);
        BATTERY_SU_HV_MERCURY = addItem(531, "battery.su.hv.mercury").addStats(ElectricStats.createBattery(512000, 3, false)).setModelAmount(8);

        BATTERY_RE_HV_CADMIUM = addItem(537, "battery.re.hv.cadmium").addStats(ElectricStats.createRechargeableBattery(1200000, 3)).setModelAmount(8);
        BATTERY_RE_HV_LITHIUM = addItem(538, "battery.re.hv.lithium").addStats(ElectricStats.createRechargeableBattery(1600000, 3)).setModelAmount(8);
        BATTERY_RE_HV_SODIUM = addItem(539, "battery.re.hv.sodium").addStats(ElectricStats.createRechargeableBattery(800000, 3)).setModelAmount(8);

        ENERGY_LAPOTRONIC_ORB = addItem(597, "energy.lapotronicorb").addStats(ElectricStats.createRechargeableBattery(100000000, 5)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Ultimate).setModelAmount(8);
        ENERGY_LAPOTRONIC_ORB2 = addItem(598, "energy.lapotronicorb2").addStats(ElectricStats.createRechargeableBattery(1000000000, 6)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Ultimate).setModelAmount(8);

        ZPM = addItem(599, "zpm").addStats(ElectricStats.createBattery(2000000000000L, 7, false)).setModelAmount(8);
        ZPM2 = addItem(605, "zpm2").addStats(ElectricStats.createRechargeableBattery(Long.MAX_VALUE, 8)).setModelAmount(8);

        ELECTRIC_MOTOR_LV = addItem(600, "electric.motor.lv");
        ELECTRIC_MOTOR_MV = addItem(601, "electric.motor.mv");
        ELECTRIC_MOTOR_HV = addItem(602, "electric.motor.hv");
        ELECTRIC_MOTOR_EV = addItem(603, "electric.motor.ev");
        ELECTRIC_MOTOR_IV = addItem(604, "electric.motor.iv");
        ELECTRIC_MOTOR_LUV = addItem(606, "electric.motor.luv");
        ELECTRIC_MOTOR_ZPM = addItem(607, "electric.motor.zpm");
        ELECTRIC_MOTOR_UV = addItem(608, "electric.motor.uv");

        ELECTRIC_PUMP_LV = addItem(610, "electric.pump.lv");
        ELECTRIC_PUMP_MV = addItem(611, "electric.pump.mv");
        ELECTRIC_PUMP_HV = addItem(612, "electric.pump.hv");
        ELECTRIC_PUMP_EV = addItem(613, "electric.pump.ev");
        ELECTRIC_PUMP_IV = addItem(614, "electric.pump.iv");
        ELECTRIC_PUMP_LUV = addItem(620, "electric.pump.luv");
        ELECTRIC_PUMP_ZPM = addItem(621, "electric.pump.zpm");
        ELECTRIC_PUMP_UV = addItem(622, "electric.pump.uv");

        FLUID_REGULATOR_LV = addItem(615, "fluidregulator.lv");
        FLUID_REGULATOR_MV = addItem(616, "fluidregulator.mv");
        FLUID_REGULATOR_HV = addItem(617, "fluidregulator.hv");
        FLUID_REGULATOR_EV = addItem(618, "fluidregulator.ev");
        FLUID_REGULATOR_IV = addItem(619, "fluidregulator.iv");

        SMALL_BRONZE_PIPE = addItem(620, "pipe.small.bronze").setUnificationData(OrePrefix.pipeSmall, Materials.Bronze);
        SMALL_STEEL_PIPE = addItem(621, "pipe.small.steel").setUnificationData(OrePrefix.pipeSmall, Materials.Steel);

        RUBBER_DROP = addItem(622, "rubber_drop").setBurnValue(200);

        FLUID_FILTER = addItem(635, "fluidfilter").setInvisible();

        CONVEYOR_MODULE_LV = addItem(630, "conveyor.module.lv");
        CONVEYOR_MODULE_MV = addItem(631, "conveyor.module.mv");
        CONVEYOR_MODULE_HV = addItem(632, "conveyor.module.hv");
        CONVEYOR_MODULE_EV = addItem(633, "conveyor.module.ev");
        CONVEYOR_MODULE_IV = addItem(634, "conveyor.module.iv");
        CONVEYOR_MODULE_LUV = addItem(635, "conveyor.module.luv");
        CONVEYOR_MODULE_ZPM = addItem(636, "conveyor.module.zpm");
        CONVEYOR_MODULE_UV = addItem(637, "conveyor.module.uv");

        ELECTRIC_PISTON_LV = addItem(640, "electric.piston.lv");
        ELECTRIC_PISTON_MV = addItem(641, "electric.piston.mv");
        ELECTRIC_PISTON_HV = addItem(642, "electric.piston.hv");
        ELECTRIC_PISTON_EV = addItem(643, "electric.piston.ev");
        ELECTRIC_PISTON_IV = addItem(644, "electric.piston.iv");
        ELECTRIC_PISTON_LUV = addItem(645, "electric.piston.luv");
        ELECTRIC_PISTON_ZPM = addItem(646, "electric.piston.zpm");
        ELECTRIC_PISTON_UV = addItem(647, "electric.piston.uv");

        ROBOT_ARM_LV = addItem(650, "robot.arm.lv");
        ROBOT_ARM_MV = addItem(651, "robot.arm.mv");
        ROBOT_ARM_HV = addItem(652, "robot.arm.hv");
        ROBOT_ARM_EV = addItem(653, "robot.arm.ev");
        ROBOT_ARM_IV = addItem(654, "robot.arm.iv");
        ROBOT_ARM_LUV = addItem(655, "robot.arm.luv");
        ROBOT_ARM_ZPM = addItem(656, "robot.arm.zpm");
        ROBOT_ARM_UV = addItem(657, "robot.arm.uv");

        FIELD_GENERATOR_LV = addItem(670, "field.generator.lv");
        FIELD_GENERATOR_MV = addItem(671, "field.generator.mv");
        FIELD_GENERATOR_HV = addItem(672, "field.generator.hv");
        FIELD_GENERATOR_EV = addItem(673, "field.generator.ev");
        FIELD_GENERATOR_IV = addItem(674, "field.generator.iv");
        FIELD_GENERATOR_LUV = addItem(675, "field.generator.luv");
        FIELD_GENERATOR_ZPM = addItem(676, "field.generator.zpm");
        FIELD_GENERATOR_UV = addItem(677, "field.generator.uv");

        EMITTER_LV = addItem(680, "emitter.lv");
        EMITTER_MV = addItem(681, "emitter.mv");
        EMITTER_HV = addItem(682, "emitter.hv");
        EMITTER_EV = addItem(683, "emitter.ev");
        EMITTER_IV = addItem(684, "emitter.iv");
        EMITTER_LUV = addItem(685, "emitter.luv");
        EMITTER_ZPM = addItem(686, "emitter.zpm");
        EMITTER_UV = addItem(687, "emitter.uv");

        SENSOR_LV = addItem(690, "sensor.lv");
        SENSOR_MV = addItem(691, "sensor.mv");
        SENSOR_HV = addItem(692, "sensor.hv");
        SENSOR_EV = addItem(693, "sensor.ev");
        SENSOR_IV = addItem(694, "sensor.iv");
        SENSOR_LUV = addItem(695, "sensor.luv");
        SENSOR_ZPM = addItem(696, "sensor.zpm");
        SENSOR_UV = addItem(697, "sensor.uv");

        TOOL_DATA_STICK = addItem(708, "tool.datastick").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Data);
        TOOL_DATA_ORB = addItem(707, "tool.dataorb").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);

        CIRCUIT_PRIMITIVE = addItem(700, "circuit.primitive").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Primitive);
        CIRCUIT_BASIC = addItem(701, "circuit.basic").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
        CIRCUIT_GOOD = addItem(702, "circuit.good").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Good);
        CIRCUIT_ADVANCED = addItem(703, "circuit.advanced").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
        CIRCUIT_DATA = addItem(704, "circuit.data").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Data);
        CIRCUIT_ELITE = addItem(705, "circuit.elite").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
        CIRCUIT_MASTER = addItem(706, "circuit.master").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Master);
        CIRCUIT_ULTIMATE = TOOL_DATA_ORB;

        CIRCUIT_BOARD_BASIC = addItem(710, "circuit.board.basic");
        CIRCUIT_BOARD_ADVANCED = addItem(711, "circuit.board.advanced");
        CIRCUIT_BOARD_ELITE = addItem(712, "circuit.board.elite");
        CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE = addItem(713, "circuit.parts.crystal.chip.elite");
        CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER = addItem(714, "circuit.parts.crystal.chip.master");
        CIRCUIT_PARTS_ADVANCED = addItem(715, "circuit.parts.advanced");
        CIRCUIT_PARTS_WIRING_BASIC = addItem(716, "circuit.parts.wiring.basic");
        CIRCUIT_PARTS_WIRING_ADVANCED = addItem(717, "circuit.parts.wiring.advanced");
        CIRCUIT_PARTS_WIRING_ELITE = addItem(718, "circuit.parts.wiring.elite");
        EMPTY_BOARD_BASIC = addItem(719, "empty.board.basic");
        EMPTY_BOARD_ELITE = addItem(720, "empty.board.elite");

        COMPONENT_SAW_BLADE_DIAMOND = addItem(721, "component.sawblade.diamond").addOreDict(OreDictNames.craftingDiamondBlade);
        COMPONENT_GRINDER_DIAMOND = addItem(722, "component.grinder.diamond").addOreDict(OreDictNames.craftingGrinder);
        COMPONENT_GRINDER_TUNGSTEN = addItem(723, "component.grinder.tungsten").addOreDict(OreDictNames.craftingGrinder);

        QUANTUM_EYE = addItem(724, "quantumeye");
        QUANTUM_STAR = addItem(725, "quantumstar");
        GRAVI_STAR = addItem(726, "gravistar");

        UPGRADE_MUFFLER = addItem(727, "upgrade.muffler");
        UPGRADE_LOCK = addItem(728, "upgrade.lock");

        COMPONENT_FILTER = addItem(729, "component.filter").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Zinc, OrePrefix.foil.materialAmount * 16L))).addOreDict(OreDictNames.craftingFilter).setInvisible();

        COVER_CONTROLLER = addItem(730, "cover.controller").setInvisible();
        COVER_ACTIVITY_DETECTOR = addItem(731, "cover.activity.detector").setInvisible();
        COVER_FLUID_DETECTOR = addItem(732, "cover.fluid.detector").setInvisible();
        COVER_ITEM_DETECTOR = addItem(733, "cover.item.detector").setInvisible();
        COVER_ENERGY_DETECTOR = addItem(734, "cover.energy.detector").setInvisible();
        COVER_PLAYER_DETECTOR = addItem(735, "cover.player.detector").setInvisible();

        COVER_SCREEN = addItem(740, "cover.screen").setInvisible();
        COVER_CRAFTING = addItem(744, "cover.crafting").setInvisible();
        COVER_DRAIN = addItem(745, "cover.drain").setInvisible();

        COVER_SHUTTER = addItem(749, "cover.shutter").setInvisible();

        COVER_SOLARPANEL = addItem(750, "cover.solar.panel").setInvisible();
        COVER_SOLARPANEL_8V = addItem(751, "cover.solar.panel.ulv").setInvisible();
        COVER_SOLARPANEL_LV = addItem(752, "cover.solar.panel.lv").setInvisible();
        COVER_SOLARPANEL_MV = addItem(753, "cover.solar.panel.mv").setInvisible();
        COVER_SOLARPANEL_HV = addItem(754, "cover.solar.panel.hv").setInvisible();
        COVER_SOLARPANEL_EV = addItem(755, "cover.solar.panel.ev").setInvisible();
        COVER_SOLARPANEL_IV = addItem(756, "cover.solar.panel.iv").setInvisible();
        COVER_SOLARPANEL_LUV = addItem(757, "cover.solar.panel.luv").setInvisible();
        COVER_SOLARPANEL_ZPM = addItem(758, "cover.solar.panel.zpm").setInvisible();
        COVER_SOLARPANEL_UV = addItem(759, "cover.solar.panel.uv").setInvisible();

        //TOOL_CHEAT = addItem(761, "tool.cheat").addStats(new ElectricStats(-2000000000, -1));

        FLUID_CELL = addItem(762, "fluid_cell").addStats(new FluidStats(1000, Integer.MIN_VALUE, Integer.MAX_VALUE, false));

        DUCT_TAPE = addItem(764, "duct.tape").addOreDict(OreDictNames.craftingDuctTape).setInvisible();
        MCGUFFIUM_239 = addItem(765, "mcguffium.239");

        INTEGRATED_CIRCUIT = addItem(766, "circuit.integrated").addStats(new IntCircuitBehaviour());
    }

    public void registerRecipes() {
        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(RUBBER_DROP.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber))
            .duration(800).EUt(6)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.dust, Materials.Redstone).input(OrePrefix.plate, Materials.Tin, 2)
            .outputs(SPRAY_EMPTY.getStackForm())
            .duration(800).EUt(1)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Steel).input(OrePrefix.ring, Materials.Steel, 2)
            .outputs(LARGE_FLUID_CELL_STEEL.getStackForm())
            .duration(100)
            .EUt(64)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.TungstenSteel).input(OrePrefix.ring, Materials.TungstenSteel, 2)
            .outputs(LARGE_FLUID_CELL_TUNGSTEN_STEEL.getStackForm())
            .duration(200)
            .EUt(256)
            .buildAndRegister();

        // Matches/lighters recipes
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.stick, Materials.Wood).input(OrePrefix.dustSmall, Materials.Phosphorus)
            .outputs(TOOL_MATCHES.getStackForm())
            .duration(16)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.stick, Materials.Wood).input(OrePrefix.dustSmall, Materials.Phosphor)
            .outputs(TOOL_MATCHES.getStackForm())
            .duration(16)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.stick, Materials.Wood, 4).input(OrePrefix.dust, Materials.Phosphorus)
            .outputs(TOOL_MATCHES.getStackForm(4))
            .duration(64)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.stick, Materials.Wood, 4).input(OrePrefix.dust, Materials.Phosphor)
            .outputs(TOOL_MATCHES.getStackForm(4))
            .duration(64)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(TOOL_MATCHES.getStackForm(16)).input(OrePrefix.plate, Materials.Paper)
            .outputs(TOOL_MATCHBOX_FULL.getStackForm())
            .duration(64)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Invar, 2).inputs(new ItemStack(Items.FLINT, 1))
            .outputs(TOOL_LIGHTER_INVAR_EMPTY.getStackForm())
            .duration(256)
            .EUt(16)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Platinum, 2).inputs(new ItemStack(Items.FLINT, 1))
            .outputs(TOOL_LIGHTER_PLATINUM_EMPTY.getStackForm())
            .duration(256)
            .EUt(256)
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_SULFURIC_ACID.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_LV.getStackForm())
            .fluidInputs(Materials.Cadmium.getFluid(2 * L))
            .outputs(BATTERY_RE_LV_CADMIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_LV.getStackForm())
            .fluidInputs(Materials.Lithium.getFluid(2 * L))
            .outputs(BATTERY_RE_LV_LITHIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_LV.getStackForm())
            .fluidInputs(Materials.Sodium.getFluid(2 * L))
            .outputs(BATTERY_RE_LV_SODIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();

        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_MV.getStackForm())
            .fluidInputs(Materials.Cadmium.getFluid(8 * L))
            .outputs(BATTERY_RE_MV_CADMIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_MV.getStackForm())
            .fluidInputs(Materials.Lithium.getFluid(8 * L))
            .outputs(BATTERY_RE_MV_LITHIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_MV.getStackForm())
            .fluidInputs(Materials.Sodium.getFluid(8 * L))
            .outputs(BATTERY_RE_MV_SODIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_HV.getStackForm())
            .fluidInputs(Materials.Cadmium.getFluid(32 * L))
            .outputs(BATTERY_RE_HV_CADMIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_HV.getStackForm())
            .fluidInputs(Materials.Lithium.getFluid(32 * L))
            .outputs(BATTERY_RE_HV_LITHIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(BATTERY_HULL_HV.getStackForm())
            .fluidInputs(Materials.Sodium.getFluid(32 * L))
            .outputs(BATTERY_RE_HV_SODIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();

        // Upgrades recipes
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium).input(OrePrefix.dust, Materials.Plastic, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron).input(OrePrefix.dust, Materials.Plastic, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.WroughtIron).input(OrePrefix.dust, Materials.Plastic, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.WroughtIron).input(OrePrefix.dust, Materials.Wood, 2)
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium).input(OrePrefix.plate, Materials.Iridium)
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Iron).input(OrePrefix.plate, Materials.Iridium)
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.WroughtIron).input(OrePrefix.plate, Materials.Iridium)
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();

        // Misc
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.plate, Materials.Aluminium, 2)
            .input(OrePrefix.foil, Materials.Zinc, 16)
            .fluidInputs(Materials.Plastic.getFluid(144))
            .outputs(COMPONENT_FILTER.getStackForm())
            .duration(1600)
            .EUt(32)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .input(OrePrefix.circuit, MarkerMaterials.Tier.Good, 4).input(OrePrefix.plate, Materials.StainlessSteel, 2)
            .outputs(SCHEMATIC.getStackForm())
            .duration(3200)
            .EUt(4)
            .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_LV.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Basic, 2)
            .outputs(FLUID_REGULATOR_LV.getStackForm())
            .duration(800)
            .EUt(4)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_MV.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Good, 2)
            .outputs(FLUID_REGULATOR_MV.getStackForm())
            .duration(800)
            .EUt(8)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_HV.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 2)
            .outputs(FLUID_REGULATOR_HV.getStackForm())
            .duration(800)
            .EUt(16)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_EV.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 2)
            .outputs(FLUID_REGULATOR_EV.getStackForm())
            .duration(800)
            .EUt(32)
            .buildAndRegister();
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_IV.getStackForm()).input(OrePrefix.circuit, MarkerMaterials.Tier.Master, 2)
            .outputs(FLUID_REGULATOR_IV.getStackForm())
            .duration(800)
            .EUt(64)
            .buildAndRegister();
    }


    @Override
    public boolean onEntityItemUpdate(EntityItem itemEntity) {
        int damage = itemEntity.getItem().getMetadata();
        if (damage >= this.metaItemOffset || itemEntity.getEntityWorld().isRemote)
            return false;
        Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
        OrePrefix prefix = this.orePrefixes[(damage / 1000)];
        if (!purifyMap.containsKey(prefix))
            return false;
        int posX = MathHelper.floor(itemEntity.posX);
        int posY = MathHelper.floor(itemEntity.posY);
        int posZ = MathHelper.floor(itemEntity.posZ);
        BlockPos blockPos = new BlockPos(itemEntity);
        IBlockState blockState = itemEntity.getEntityWorld().getBlockState(blockPos);
        int waterLevel = blockState.getBlock() instanceof BlockCauldron ?
            blockState.getValue(BlockCauldron.LEVEL) : 0;
        if(waterLevel == 0)
            return false;
        itemEntity.getEntityWorld().setBlockState(blockPos,
            blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
        ItemStack replacementStack = OreDictUnifier.get(purifyMap.get(prefix), material,
            itemEntity.getItem().getCount());
        itemEntity.setItem(replacementStack);
        return false;
    }

    @Override
    protected void addMaterialTooltip(ItemStack itemStack, OrePrefix prefix, Material material, List<String> lines, ITooltipFlag tooltipFlag) {
        if (prefix == OrePrefix.dustImpure || prefix == OrePrefix.dustPure) {
            lines.add(I18n.format("metaitem.dust.tooltip.purify"));
        }
    }

}