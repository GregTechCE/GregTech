package gregtech.common.items;

import gregtech.api.items.OreDictNames;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.RandomPotionEffect;
//import gregtech.common.items.behaviors.*;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static gregtech.common.items.MetaItems.*;

public class MetaItem1 extends MaterialMetaItem {

	private final static String emptyRow = "   ";
	private final static String textShape = " P ";

	public MetaItem1() {
		super(OrePrefix.dustTiny, OrePrefix.dustSmall, OrePrefix.dust, OrePrefix.dustImpure, OrePrefix.dustPure,
				OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged, OrePrefix.gem, OrePrefix.nugget,
				OrePrefix.ingot, OrePrefix.ingotHot, OrePrefix.plate, OrePrefix.plateDense, OrePrefix.stick, OrePrefix.lens,
				OrePrefix.round, OrePrefix.bolt, OrePrefix.screw, OrePrefix.ring, OrePrefix.foil, OrePrefix.cell,
				OrePrefix.cellPlasma, null, null, null, null, null, null, null, null, null);
	}

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
            .addStats(new FoodStats(1, 0.1F, false, true, OreDictUnifier.get(OrePrefix.foil, Materials.Gold, 1), new RandomPotionEffect(MobEffects.SPEED, 200, 1, 10)));

        MINECART_WHEELS_IRON = addItem(100, "minecart.wheels.iron");
        MINECART_WHEELS_STEEL = addItem(101, "minecart.wheels.steel");

        SHAPE_EMPTY = addItem(300, "shape.empty");

        SHAPE_MOLD_PLATE = addItem(301, "shape.mold.plate");
        SHAPE_MOLD_CASING = addItem(302, "shape.mold.casing");
        SHAPE_MOLD_GEAR = addItem(303, "shape.mold.gear");
        SHAPE_MOLD_CREDIT = addItem(304, "shape.mold.credit");
        SHAPE_MOLD_BOTTLE = addItem(305, "shape.mold.bottle");
        SHAPE_MOLD_INGOT = addItem(306, "shape.mold.ingot");
        SHAPE_MOLD_BALL = addItem(307, "shape.mold.ball");
        SHAPE_MOLD_BLOCK = addItem(308, "shape.mold.block");
        SHAPE_MOLD_NUGGET = addItem(309, "shape.mold.nugget");
        SHAPE_MOLD_BUN = addItem(310, "shape.mold.bun");
        SHAPE_MOLD_BREAD = addItem(311, "shape.mold.bread");
        SHAPE_MOLD_BAGUETTE = addItem(312, "shape.mold.baguette");
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
        SHAPE_EXTRUDER_CASING = addItem(357, "shape.extruder.casing");
        SHAPE_EXTRUDER_PIPE_TINY = addItem(358, "shape.extruder.pipe.tiny");
        SHAPE_EXTRUDER_PIPE_SMALL = addItem(359, "shape.extruder.pipe.small");
        SHAPE_EXTRUDER_PIPE_MEDIUM = addItem(360, "shape.extruder.pipe.medium");
        SHAPE_EXTRUDER_PIPE_LARGE = addItem(361, "shape.extruder.pipe.large");
        SHAPE_EXTRUDER_PIPE_HUGE = addItem(362, "shape.extruder.pipe.huge");
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

        SHAPE_SLICER_FLAT = addItem(398, "shape.slicer.flat");
        SHAPE_SLICER_STRIPES = addItem(399, "shape.slicer.stripes");

        FUEL_CAN_PLASTIC_EMPTY = addItem(400, "fuel.can.plastic.empty")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)));
        FUEL_CAN_PLASTIC_FILLED = addItem(401, "fuel.can.plastic.filled")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)));

        SPRAY_EMPTY = addItem(402, "spray.empty")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Tin, OrePrefix.plate.materialAmount * 2L), new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount)));

        THERMOS_CAN_EMPTY = addItem(404, "thermos_can.empty")
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Aluminium, OrePrefix.plate.materialAmount + 2L * OrePrefix.ring.materialAmount)));

        LARGE_FLUID_CELL_STEEL = addItem(405, "large.fluid.cell.steel")
            .addStats(new FluidStats(16000, Integer.MAX_VALUE, Integer.MAX_VALUE))
            .setMaxStackSize(16)
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        LARGE_FLUID_CELL_TUNGSTENSTEEL = addItem(406, "large.fluid.cell.tungstensteel")
            .addStats(new FluidStats(64000, Integer.MAX_VALUE, Integer.MAX_VALUE))
            .setMaxStackSize(16)
            .setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.TungstenSteel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)));

        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
//			IItemBehaviour behaviour = new Behaviour_Spray_Color(SPRAY_EMPTY.getStackForm(), SPRAY_CAN_DYES_USED[i].getStackForm(), SPRAY_CAN_DYES[i].getStackForm(), 512L, i);
            SPRAY_CAN_DYES[i] = addItem(430 + 2 * i, "spray.can.dyes." + EnumDyeColor.byDyeDamage(i).getName());//.addStats(behaviour);
            SPRAY_CAN_DYES_USED[i] = addItem(431 + 2 * i, "spray.can.dyes.used." + EnumDyeColor.byDyeDamage(i).getName());//.addStats(behaviour);
        }

//		IItemBehaviour behaviour = new Behaviour_Lighter(null, TOOL_MATCHES.getStackForm(), TOOL_MATCHES.getStackForm(), 1L);
        TOOL_MATCHES = addItem(471, "tool.matches");//.addStats(behaviour);

//		behaviour = new Behaviour_Lighter(null, TOOL_MATCHBOX_USED.getStackForm(), TOOL_MATCHBOX_FULL.getStackForm(), 16L);
        TOOL_MATCHBOX_USED = addItem(472, "tool.matchbox.used");//.addStats(behaviour);
        TOOL_MATCHBOX_FULL = addItem(473, "tool.matchbox.full");//.addStats(behaviour);

        TOOL_LIGHTER_INVAR_EMPTY = addItem(474, "tool.lighter.invar.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));

//		behaviour = new Behaviour_Lighter(TOOL_LIGHTER_INVAR_EMPTY.getStackForm(), TOOL_LIGHTER_INVAR_USED.getStackForm(), TOOL_LIGHTER_INVAR_FULL.getStackForm(), 100L);
        TOOL_LIGHTER_INVAR_USED = addItem(475, "tool.lighter.invar.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));//.addStats(behaviour);
        TOOL_LIGHTER_INVAR_FULL = addItem(476, "tool.lighter.invar.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)));//.addStats(behaviour);

        TOOL_LIGHTER_PLATINUM_EMPTY = addItem(477, "tool.lighter.platinum.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));

//		behaviour = new Behaviour_Lighter(TOOL_LIGHTER_PLATINUM_EMPTY.getStackForm(), TOOL_LIGHTER_PLATINUM_USED.getStackForm(), TOOL_LIGHTER_PLATINUM_FULL.getStackForm(), 1000L);
        TOOL_LIGHTER_PLATINUM_USED = addItem(478, "tool.lighter.platinum.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));//.addStats(behaviour);
        TOOL_LIGHTER_PLATINUM_FULL = addItem(479, "tool.lighter.platinum.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)));//.addStats(behaviour);

        INGOT_IRIDIUM_ALLOY = addItem(480, "ingot.iridiumalloy");

        PAPER_PRINTED_PAGES = addItem(481, "paper.printed.pages").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));//.addStats(new Behaviour_PrintedPages());
        PAPER_MAGIC_EMPTY = addItem(482, "paper.magic.empty").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L)));
        PAPER_MAGIC_PAGE = addItem(483, "paper.magic.page").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L)));
        PAPER_MAGIC_PAGES = addItem(484, "paper.magic.pages").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));
        PAPER_PUNCH_CARD_EMPTY = addItem(485, "paper.punch.card.empty").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L)));
        PAPER_PUNCH_CARD_ENCODED = addItem(486, "paper.punch.card.encoded").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L)));

        SCHEMATIC = addItem(490, "schematic").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));
        SCHEMATIC_CRAFTING = addItem(491, "schematic.crafting").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));
        SCHEMATIC_1X1 = addItem(495, "schematic.1by1").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));
        SCHEMATIC_2X2 = addItem(496, "schematic.2by2").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));
        SCHEMATIC_3X3 = addItem(497, "schematic.3by3").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));
        SCHEMATIC_DUST = addItem(498, "schematic.dust").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)));

        BATTERY_HULL_LV = addItem(500, "battery.hull.lv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount)));
        BATTERY_HULL_MV = addItem(501, "battery.hull.hv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 3L)));
        BATTERY_HULL_HV = addItem(502, "battery.hull.mv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 9L)));

        BATTERY_RE_ULV_TANTALUM = addItem(499, "battery.re.ulv.tantalum").addStats(new ElectricStats(1000, 0));

        BATTERY_SU_LV_SULFURICACID = addItem(510, "battery.su.lv.sulfuricacid").addStats(new ElectricStats(18000, 1, false));
        BATTERY_SU_LV_MERCURY = addItem(511, "battery.su.lv.mercury").addStats(new ElectricStats(32000, 1, false));

        BATTERY_RE_LV_CADMIUM = addItem(517, "battery.re.lv.cadmium").addStats(new ElectricStats(75000, 1));
        BATTERY_RE_LV_LITHIUM = addItem(518, "battery.re.lv.lithium").addStats(new ElectricStats(100000, 1));
        BATTERY_RE_LV_SODIUM = addItem(519, "battery.re.lv.sodium").addStats(new ElectricStats(50000, 1));

        BATTERY_SU_MV_SULFURICACID = addItem(520, "battery.su.mv.sulfuricacid").addStats(new ElectricStats(72000, 2, false));
        BATTERY_SU_MV_MERCURY = addItem(521, "battery.su.mv.mercury").addStats(new ElectricStats(128000, 2, false));

        BATTERY_RE_MV_CADMIUM = addItem(527, "battery.re.mv.cadmium").addStats(new ElectricStats(300000, 2));
        BATTERY_RE_MV_LITHIUM = addItem(528, "battery.re.mv.lithium").addStats(new ElectricStats(400000, 2));
        BATTERY_RE_MV_SODIUM = addItem(529, "battery.re.mv.sodium").addStats(new ElectricStats(200000, 2));

        BATTERY_SU_HV_SULFURICACID = addItem(530, "battery.su.hv.sulfuricacid").addStats(new ElectricStats(288000, 3, false));
        BATTERY_SU_HV_MERCURY = addItem(531, "battery.su.hv.mercury").addStats(new ElectricStats(512000, 3, false));

        BATTERY_RE_HV_CADMIUM = addItem(537, "battery.re.hv.cadmium").addStats(new ElectricStats(1200000, 3));
        BATTERY_RE_HV_LITHIUM = addItem(538, "battery.re.hv.lithium").addStats(new ElectricStats(1600000, 3));
        BATTERY_RE_HV_SODIUM = addItem(539, "battery.re.hv.sodium").addStats(new ElectricStats(800000, 3));

        ENERGY_LAPOTRONICORB = addItem(597, "energy.lapotronicorb").addStats(new ElectricStats(100000000, 5)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Ultimate);
        ENERGY_LAPOTRONICORB2 = addItem(598, "energy.lapotronicorb2").addStats(new ElectricStats(1000000000, 6)).setUnificationData(OrePrefix.battery, MarkerMaterials.Tier.Ultimate);

        ZPM = addItem(599, "zpm").addStats(new ElectricStats(2000000000000L, 7, false));
        ZPM2 = addItem(605, "zpm2").addStats(new ElectricStats(Long.MAX_VALUE, 8));

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

        FLUID_FILTER = addItem(635, "fluidfilter");

        ROTOR_LV = addItem(620, "rotor.lv").setUnificationData(OrePrefix.rotor, Materials.Tin);
        ROTOR_MV = addItem(621, "rotor.mv").setUnificationData(OrePrefix.rotor, Materials.Bronze);
        ROTOR_HV = addItem(622, "rotor.hv").setUnificationData(OrePrefix.rotor, Materials.Steel);
        ROTOR_EV = addItem(623, "rotor.ev").setUnificationData(OrePrefix.rotor, Materials.StainlessSteel);
        ROTOR_IV = addItem(624, "rotor.iv").setUnificationData(OrePrefix.rotor, Materials.TungstenSteel);

        CONVEYOR_MODULE_LV = addItem(630, "conveyor.module.lv");
        CONVEYOR_MODULE_MV = addItem(631, "conveyor.module.mv");
        CONVEYOR_MODULE_HV = addItem(632, "conveyor.module.hv");
        CONVEYOR_MODULE_EV = addItem(633, "conveyor.module.ev");
        CONVEYOR_MODULE_IV = addItem(634, "conveyor.module.iv");

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

        TOOL_DATASTICK = addItem(708, "tool.datastick").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Data);//.addStats(new Behaviour_DataStick());
        TOOL_DATAORB = addItem(707, "tool.dataorb").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);//.addStats(new Behaviour_DataOrb());

        CIRCUIT_PRIMITIVE = addItem(700, "circuit.primitive").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Primitive);
        CIRCUIT_BASIC = addItem(701, "circuit.basic").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
        CIRCUIT_GOOD = addItem(702, "circuit.good").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Good);
        CIRCUIT_ADVANCED = addItem(703, "circuit.advanced").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
        CIRCUIT_DATA = addItem(704, "circuit.data").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Data);
        CIRCUIT_ELITE = addItem(705, "circuit.elite").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
        CIRCUIT_MASTER = addItem(706, "circuit.master").setUnificationData(OrePrefix.circuit, MarkerMaterials.Tier.Master);
        CIRCUIT_ULTIMATE = TOOL_DATAORB;

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


        COMPONENT_SAWBLADE_DIAMOND = addItem(721, "component.sawblade.diamond").addOreDict(OreDictNames.craftingDiamondBlade);
        COMPONENT_GRINDER_DIAMOND = addItem(722, "component.grinder.diamond").addOreDict(OreDictNames.craftingGrinder);
        COMPONENT_GRINDER_TUNGSTEN = addItem(723, "component.grinder.tungsten").addOreDict(OreDictNames.craftingGrinder);

        QUANTUMEYE = addItem(724, "quantumeye");
        QUANTUMSTAR = addItem(725, "quantumstar");
        GRAVISTAR = addItem(726, "gravistar");

        UPGRADE_MUFFLER = addItem(727, "upgrade.muffler");
        UPGRADE_LOCK = addItem(728, "upgrade.lock");

        COMPONENT_FILTER = addItem(729, "component.filter").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Zinc, OrePrefix.foil.materialAmount * 16L))).addOreDict(OreDictNames.craftingFilter);

        COVER_CONTROLLER = addItem(730, "cover.controller");
        COVER_ACTIVITY_DETECTOR = addItem(731, "cover.activity.detector");
        COVER_FLUID_DETECTOR = addItem(732, "cover.fluid.detector");
        COVER_ITEM_DETECTOR = addItem(733, "cover.item.detector");
        COVER_ENERGY_DETECTOR = addItem(734, "cover.energy.detector");
        COVER_PLAYER_DETECTOR = addItem(735, "cover.player.detector");

        COVER_SCREEN = addItem(740, "cover.screen");
        COVER_CRAFTING = addItem(744, "cover.crafting");
        COVER_DRAIN = addItem(745, "cover.drain");

        COVER_SHUTTER = addItem(749, "cover.shutter");

        COVER_SOLARPANEL = addItem(750, "cover.solarpanel");
        COVER_SOLARPANEL_8V = addItem(751, "cover.solarpanel.8v");
        COVER_SOLARPANEL_LV = addItem(752, "cover.solarpanel.lv");
        COVER_SOLARPANEL_MV = addItem(753, "cover.solarpanel.mv");
        COVER_SOLARPANEL_HV = addItem(754, "cover.solarpanel.hv");
        COVER_SOLARPANEL_EV = addItem(755, "cover.solarpanel.ev");
        COVER_SOLARPANEL_IV = addItem(756, "cover.solarpanel.iv");
        COVER_SOLARPANEL_LUV = addItem(757, "cover.solarpanel.luv");
        COVER_SOLARPANEL_ZPM = addItem(758, "cover.solarpanel.zpm");
        COVER_SOLARPANEL_UV = addItem(759, "cover.solarpanel.uv");

        TOOL_CHEAT = addItem(761, "tool.cheat").addStats(/*new Behaviour_Scanner(), */new ElectricStats(-2000000000, -1));
        TOOL_SCANNER = addItem(762, "tool.scanner").addStats(/*new Behaviour_Scanner(),*/ new ElectricStats(400000, 2, true, false));

        NC_SENSORKIT = addItem(763, "nc.sensorkit");//.addStats(new Behaviour_SensorKit());
        DUCT_TAPE = addItem(764, "duct.tape").addOreDict(OreDictNames.craftingDuctTape);
        MCGUFFIUM_239 = addItem(765, "mcguffium.239");

        INTEGRATED_CIRCUIT = addItem(766, "circuit.integrated");

        FLUID_CELL = addItem(767, "cell.fluid").addStats(new FluidStats(16000, Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    public void registerRecipes() {

	    // Coin recipes
        ModHandler.addShapelessRecipe("coin_chocolate", COIN_CHOCOLATE.getStackForm(),
            new UnificationEntry(OrePrefix.dust, Materials.Cocoa),
            new UnificationEntry(OrePrefix.dust, Materials.Milk),
            new UnificationEntry(OrePrefix.dust, Materials.Sugar),
            new UnificationEntry(OrePrefix.foil, Materials.Gold));

        ModHandler.addShapelessRecipe("credit_copper", CREDIT_COPPER.getStackForm(8),
            CREDIT_CUPRONICKEL);

        ModHandler.addShapelessRecipe("credit_cupronickel", CREDIT_CUPRONICKEL.getStackForm(8),
            CREDIT_SILVER);

        ModHandler.addShapelessRecipe("credit_silver", CREDIT_SILVER.getStackForm(8),
            CREDIT_GOLD);

        ModHandler.addShapelessRecipe("credit_gold", CREDIT_GOLD.getStackForm(8),
            CREDIT_PLATINUM);

        ModHandler.addShapelessRecipe("credit_platinum", CREDIT_PLATINUM.getStackForm(8),
            CREDIT_OSMIUM);

        ModHandler.addShapelessRecipe("credit_osmium", CREDIT_OSMIUM.getStackForm(8),
            CREDIT_NAQUADAH);

        ModHandler.addShapelessRecipe("credit_naquadah", CREDIT_NAQUADAH.getStackForm(8),
            CREDIT_DARMSTADTIUM);


        ModHandler.addShapelessRecipe("credit_cupronickel", CREDIT_CUPRONICKEL.getStackForm(),
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER,
            CREDIT_COPPER);

        ModHandler.addShapelessRecipe("credit_silver", CREDIT_SILVER.getStackForm(),
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL,
            CREDIT_CUPRONICKEL);

        ModHandler.addShapelessRecipe("credit_gold", CREDIT_GOLD.getStackForm(),
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER,
            CREDIT_SILVER);

        ModHandler.addShapelessRecipe("credit_platinum", CREDIT_PLATINUM.getStackForm(),
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD,
            CREDIT_GOLD);

        ModHandler.addShapelessRecipe("credit_osmium", CREDIT_OSMIUM.getStackForm(),
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM,
            CREDIT_PLATINUM);

        ModHandler.addShapelessRecipe("credit_naquadah", CREDIT_NAQUADAH.getStackForm(),
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM,
            CREDIT_OSMIUM);

        ModHandler.addShapelessRecipe("credit_darmstadtium", CREDIT_DARMSTADTIUM.getStackForm(),
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH,
            CREDIT_NAQUADAH);

        // Minecart wheels recipes
        ModHandler.addShapedRecipe("minecart_wheels_iron", MINECART_WHEELS_IRON.getStackForm(),
            " h ",
            "RSR",
            " w ",
            'R', new UnificationEntry(OrePrefix.ring, Materials.Iron),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Iron));

        ModHandler.addShapedRecipe("minecart_wheels_steel", MINECART_WHEELS_STEEL.getStackForm(),
            " h ",
            "RSR",
            " w ",
            'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Steel));

        // Shape recipes
        ModHandler.addShapedRecipe("shape_empty", SHAPE_EMPTY.getStackForm(),// true,
            "hf",
            "PP",
            "PP",
            'P', new UnificationEntry(OrePrefix.plate, Materials.Steel));

        // Shape mold recipes
        ModHandler.removeRecipes(Items.GLASS_BOTTLE);

        ModHandler.addShapedRecipe("shape_mold_credit", SHAPE_MOLD_CREDIT.getStackForm(),
            "h  ",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_plate", SHAPE_MOLD_PLATE.getStackForm(),
            " h ",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_casing", SHAPE_MOLD_CASING.getStackForm(),
            "  h",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_gear", SHAPE_MOLD_GEAR.getStackForm(),
            emptyRow,
            " Ph",
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_bottle", SHAPE_MOLD_BOTTLE.getStackForm(),
            emptyRow,
            textShape,
            "  h",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_ingot", SHAPE_MOLD_INGOT.getStackForm(),
            emptyRow,
            textShape,
            " h ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_ball", SHAPE_MOLD_BALL.getStackForm(),
            emptyRow,
            textShape,
            "h  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_block", SHAPE_MOLD_BLOCK.getStackForm(),
            emptyRow,
            "hP ",
            emptyRow,
            'P', SHAPE_EMPTY);


        ModHandler.addShapedRecipe("shape_mold_nugget", SHAPE_MOLD_NUGGET.getStackForm(),
            "P h",
            emptyRow,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_bun", SHAPE_MOLD_BUN.getStackForm(),
            "P  ",
            "  h",
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_bread", SHAPE_MOLD_BREAD.getStackForm(),
            "P  ",
            emptyRow,
            "  h",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_baguette", SHAPE_MOLD_BAGUETTE.getStackForm(),
            "P  ",
            emptyRow,
            " h ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_cylinder", SHAPE_MOLD_CYLINDER.getStackForm(),
            "  P",
            emptyRow,
            "  h",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_anvil", SHAPE_MOLD_ANVIL.getStackForm(),
            "  P",
            emptyRow,
            " h ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_name", SHAPE_MOLD_NAME.getStackForm(),
            "  P",
            emptyRow,
            "h  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_mold_gear_small", SHAPE_MOLD_GEAR_SMALL.getStackForm(),
            emptyRow,
            emptyRow,
            "h P",
            'P', SHAPE_EMPTY);

        // Shape extruder recipes
        ModHandler.addShapedRecipe("shape_extruder_bolt", SHAPE_EXTRUDER_BOLT.getStackForm(),
            "x  ",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_cell", SHAPE_EXTRUDER_CELL.getStackForm(),
            " x ",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_ingot", SHAPE_EXTRUDER_INGOT.getStackForm(),
            "  x",
            textShape,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_ring", SHAPE_EXTRUDER_RING.getStackForm(),
            emptyRow,
            " Px",
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_rod", SHAPE_EXTRUDER_ROD.getStackForm(),
            emptyRow,
            textShape,
            "  x",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_wire", SHAPE_EXTRUDER_WIRE.getStackForm(),
            emptyRow,
            textShape,
            " x ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_casing", SHAPE_EXTRUDER_CASING.getStackForm(),
            emptyRow,
            textShape,
            "x  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_plate", SHAPE_EXTRUDER_PLATE.getStackForm(),
            emptyRow,
            "xP ",
            emptyRow,
            'P', SHAPE_EMPTY);


        ModHandler.addShapedRecipe("shape_extruder_block", SHAPE_EXTRUDER_BLOCK.getStackForm(),
            "P x",
            emptyRow,
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pipe_small", SHAPE_EXTRUDER_PIPE_SMALL.getStackForm(),
            "P  ",
            "  x",
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pipe_large", SHAPE_EXTRUDER_PIPE_LARGE.getStackForm(),
            "P  ",
            emptyRow,
            "  x",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pipe_medium", SHAPE_EXTRUDER_PIPE_MEDIUM.getStackForm(),
            "P  ",
            emptyRow,
            " x ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_sword", SHAPE_EXTRUDER_SWORD.getStackForm(),
            "  P",
            emptyRow,
            "  x",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pickaxe", SHAPE_EXTRUDER_PICKAXE.getStackForm(),
            "  P",
            emptyRow,
            " x ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_shovel", SHAPE_EXTRUDER_SHOVEL.getStackForm(),
            "  P",
            emptyRow,
            "x  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_axe", SHAPE_EXTRUDER_AXE.getStackForm(),
            "  P",
            "x  ",
            emptyRow,
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_hoe", SHAPE_EXTRUDER_HOE.getStackForm(),
            emptyRow,
            emptyRow,
            "x P",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_hammer", SHAPE_EXTRUDER_HAMMER.getStackForm(),
            emptyRow,
            "x  ",
            "  P",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_file", SHAPE_EXTRUDER_FILE.getStackForm(),
            "x  ",
            emptyRow,
            "  P",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_saw", SHAPE_EXTRUDER_SAW.getStackForm(),
            " x ",
            emptyRow,
            "  P",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_gear", SHAPE_EXTRUDER_GEAR.getStackForm(),
            "x  ",
            emptyRow,
            "P  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pipe_tiny", SHAPE_EXTRUDER_PIPE_TINY.getStackForm(),
            " x ",
            emptyRow,
            "P  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_pipe_huge", SHAPE_EXTRUDER_PIPE_HUGE.getStackForm(),
            "  x",
            emptyRow,
            "P  ",
            'P', SHAPE_EMPTY);

        ModHandler.addShapedRecipe("shape_extruder_bottle", SHAPE_EXTRUDER_BOTTLE.getStackForm(),
            emptyRow,
            "  x",
            "P  ",
            'P', SHAPE_EMPTY);

        // Shape slicer recipes
        ModHandler.addShapedRecipe("shape_slicer_flat", SHAPE_SLICER_FLAT.getStackForm(),
            "hXS",
            textShape,
            "fXd",
            'P', SHAPE_EXTRUDER_BLOCK,
            'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
            'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel));

        ModHandler.addShapedRecipe("shape_slicer_stripes", SHAPE_SLICER_STRIPES.getStackForm(),
            "hXS",
            "XPX",
            "fXd",
            'P', SHAPE_EXTRUDER_BLOCK,
            'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
            'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel));

        // Fluid container recipes
        ModHandler.addShapedRecipe("fuel_can_plastic_empty", FUEL_CAN_PLASTIC_EMPTY.getStackForm(7),
            " PP",
            "P P",
            "PPP",
            'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));

//        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
//            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone), OreDictUnifier.get(OrePrefix.cell, MarkerMaterials.Empty)) // TODO FLUID CONTAINERS
//            .outputs(SPRAY_EMPTY.getStackForm())
//            .duration(800)
//            .EUt(1)
//            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium), OreDictUnifier.get(OrePrefix.ring, Materials.Aluminium, 2))
            .outputs(THERMOS_CAN_EMPTY.getStackForm())
            .duration(800)
            .EUt(1)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Steel), OreDictUnifier.get(OrePrefix.ring, Materials.Steel, 2))
            .outputs(LARGE_FLUID_CELL_STEEL.getStackForm())
            .duration(100)
            .EUt(64)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel), OreDictUnifier.get(OrePrefix.ring, Materials.TungstenSteel, 2))
            .outputs(LARGE_FLUID_CELL_TUNGSTENSTEEL.getStackForm())
            .duration(200)
            .EUt(256)
            .buildAndRegister();

        // Matches/lighters recipes
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Phosphorus))
            .outputs(TOOL_MATCHES.getStackForm())
            .duration(16)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Phosphor))
            .outputs(TOOL_MATCHES.getStackForm())
            .duration(16)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphorus))
            .outputs(TOOL_MATCHES.getStackForm(4))
            .duration(64)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 4), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor))
            .outputs(TOOL_MATCHES.getStackForm(4))
            .duration(64)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
            .inputs(TOOL_MATCHES.getStackForm(16), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .outputs(TOOL_MATCHBOX_FULL.getStackForm())
            .duration(64)
            .EUt(16)
            .buildAndRegister();
        RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
            .inputs(TOOL_MATCHBOX_FULL.getStackForm())
            .outputs(TOOL_MATCHES.getStackForm(16))
            .duration(32)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Invar, 2), new ItemStack(Items.FLINT, 1))
            .outputs(TOOL_LIGHTER_INVAR_EMPTY.getStackForm())
            .duration(256)
            .EUt(16)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Platinum, 2), new ItemStack(Items.FLINT, 1))
            .outputs(TOOL_LIGHTER_PLATINUM_EMPTY.getStackForm())
            .duration(256)
            .EUt(256)
            .buildAndRegister();

        // Iridium alloy recipes
        ModHandler.addShapedRecipe("ingot_iridium_alloy", INGOT_IRIDIUM_ALLOY.getStackForm(),
            "IAI",
            "ADA",
            "IAI",
//				'D', GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : new UnificationEntry(OrePrefix.dust, Materials.Diamond),
            'D', new UnificationEntry(OrePrefix.dust, Materials.Diamond),
            'A', "plateAlloyAdvanced",
            'I', new UnificationEntry(OrePrefix.plate, Materials.Iridium));

        // Schematics recipes
        ModHandler.addShapedRecipe("schematic_1x1", SCHEMATIC_1X1.getStackForm(),
            "d  ", textShape, emptyRow,
            'P', SCHEMATIC);

        ModHandler.addShapedRecipe("schematic_2x2", SCHEMATIC_2X2.getStackForm(),
            " d ", textShape, emptyRow,
            'P', SCHEMATIC);

        ModHandler.addShapedRecipe("schematic_3x3", SCHEMATIC_3X3.getStackForm(),
            "  d", textShape, emptyRow,
            'P', SCHEMATIC);

        ModHandler.addShapedRecipe("schematic_dust", SCHEMATIC_DUST.getStackForm(),
            emptyRow, textShape, "  d",
            'P', SCHEMATIC);


        ModHandler.addShapelessRecipe("schematic_c", SCHEMATIC.getStackForm(),
            SCHEMATIC_CRAFTING);

        ModHandler.addShapelessRecipe("schematic_1", SCHEMATIC.getStackForm(),
            SCHEMATIC_1X1);

        ModHandler.addShapelessRecipe("schematic_2", SCHEMATIC.getStackForm(),
            SCHEMATIC_2X2);

        ModHandler.addShapelessRecipe("schematic_3", SCHEMATIC.getStackForm(),
            SCHEMATIC_3X3);

        ModHandler.addShapelessRecipe("schematic_d", SCHEMATIC.getStackForm(),
            SCHEMATIC_DUST);

        // Battery recipes
        ModHandler.addShapedRecipe("battery_hull_lv", BATTERY_HULL_LV.getStackForm(),
            "C",
            "P",
            "P",
            'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireTin);
        ModHandler.addShapedRecipe("battery_hull_mv", BATTERY_HULL_MV.getStackForm(),
            "C C",
            "PPP",
            "PPP",
            'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireCopper);

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_SULFURICACID.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_LV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_SULFURICACID.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_MV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_SULFURICACID.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_SU_HV_MERCURY.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_LV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_LV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_MV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_MV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_CADMIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_LITHIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(BATTERY_RE_HV_SODIUM.getStackForm())
            .outputs(BATTERY_HULL_HV.getStackForm())
            .buildAndRegister();

/*
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Cadmium, 2), BATTERY_HULL_LV.getStackForm())
            .outputs(BATTERY_RE_LV_CADMIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 2), BATTERY_HULL_LV.getStackForm())
            .outputs(BATTERY_RE_LV_LITHIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 2), BATTERY_HULL_LV.getStackForm())
            .outputs(BATTERY_RE_LV_SODIUM.getStackForm())
            .duration(100)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Cadmium, 8), BATTERY_HULL_MV.getStackForm())
            .outputs(BATTERY_RE_MV_CADMIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 8), BATTERY_HULL_MV.getStackForm())
            .outputs(BATTERY_RE_MV_LITHIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 8), BATTERY_HULL_MV.getStackForm())
            .outputs(BATTERY_RE_MV_SODIUM.getStackForm())
            .duration(400)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Cadmium, 32), BATTERY_HULL_HV.getStackForm())
            .outputs(BATTERY_RE_HV_CADMIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 32), BATTERY_HULL_HV.getStackForm())
            .outputs(BATTERY_RE_HV_LITHIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 32), BATTERY_HULL_HV.getStackForm())
            .outputs(BATTERY_RE_HV_SODIUM.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
*/
        // Electric motor recipes
        ModHandler.addShapedRecipe("electric_motor_lv_i", ELECTRIC_MOTOR_LV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.Iron),
            'W', new UnificationEntry(OrePrefix.wireGt01, Materials.Copper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

        ModHandler.addShapedRecipe("electric_motor_lv_s", ELECTRIC_MOTOR_LV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.Steel),
            'W', new UnificationEntry(OrePrefix.wireGt01, Materials.Copper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

        ModHandler.addShapedRecipe("electric_motor_mv", ELECTRIC_MOTOR_MV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
            'W', new UnificationEntry(OrePrefix.wireGt02, Materials.Copper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper));

        ModHandler.addShapedRecipe("electric_motor_hv", ELECTRIC_MOTOR_HV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
            'W', new UnificationEntry(OrePrefix.wireGt04, Materials.Copper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

        ModHandler.addShapedRecipe("electric_motor_ev", ELECTRIC_MOTOR_EV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
            'W', new UnificationEntry(OrePrefix.wireGt08, Materials.AnnealedCopper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

        ModHandler.addShapedRecipe("electric_motor_iv", ELECTRIC_MOTOR_IV.getStackForm(),
            "CWR",
            "WIW",
            "RWC",
            'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
            'R', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
            'W', new UnificationEntry(OrePrefix.wireGt16, Materials.AnnealedCopper),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));

        // Electric pump recipes
        ModHandler.addShapedRecipe("electric_pump_lv", ELECTRIC_PUMP_LV.getStackForm(),
            "SXO",
            "dPw",
            "OMW",
            'M', ELECTRIC_MOTOR_LV,
            'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
            'X', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
            'S', new UnificationEntry(OrePrefix.screw, Materials.Tin),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
            'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze));

        ModHandler.addShapedRecipe("electric_pump_mv", ELECTRIC_PUMP_MV.getStackForm(),
            "SXO",
            "dPw",
            "OMW",
            'M', ELECTRIC_MOTOR_MV,
            'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
            'X', new UnificationEntry(OrePrefix.rotor, Materials.Bronze),
            'S', new UnificationEntry(OrePrefix.screw, Materials.Bronze),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper),
            'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel));

        ModHandler.addShapedRecipe("electric_pump_hv", ELECTRIC_PUMP_HV.getStackForm(),
            "SXO",
            "dPw",
            "OMW",
            'M', ELECTRIC_MOTOR_HV,
            'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
            'X', new UnificationEntry(OrePrefix.rotor, Materials.Steel),
            'S', new UnificationEntry(OrePrefix.screw, Materials.Steel),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
            'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel));

        ModHandler.addShapedRecipe("electric_pump_ev", ELECTRIC_PUMP_EV.getStackForm(),
            "SXO",
            "dPw",
            "OMW",
            'M', ELECTRIC_MOTOR_EV,
            'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
            'X', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel),
            'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
            'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium));

        ModHandler.addShapedRecipe("electric_pump_iv", ELECTRIC_PUMP_IV.getStackForm(),
            "SXO",
            "dPw",
            "OMW",
            'M', ELECTRIC_MOTOR_IV,
            'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
            'X', new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel),
            'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
            'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel));

        // Conveyor module recipes
        ModHandler.addShapedRecipe("conveyor_module_lv", CONVEYOR_MODULE_LV.getStackForm(),
            "RRR",
            "MCM",
            "RRR",
            'M', ELECTRIC_MOTOR_LV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapedRecipe("conveyor_module_mv", CONVEYOR_MODULE_MV.getStackForm(),
            "RRR",
            "MCM",
            "RRR",
            'M', ELECTRIC_MOTOR_MV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper),
            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapedRecipe("conveyor_module_hv", CONVEYOR_MODULE_HV.getStackForm(),
            "RRR",
            "MCM",
            "RRR",
            'M', ELECTRIC_MOTOR_HV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapedRecipe("conveyor_module_ev", CONVEYOR_MODULE_EV.getStackForm(),
            "RRR",
            "MCM",
            "RRR",
            'M', ELECTRIC_MOTOR_EV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapedRecipe("conveyor_module_iv", CONVEYOR_MODULE_IV.getStackForm(),
            "RRR",
            "MCM",
            "RRR",
            'M', ELECTRIC_MOTOR_IV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
            'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        // Electric piston recipes
        ModHandler.addShapedRecipe("electric_piston_lv", ELECTRIC_PISTON_LV.getStackForm(),
            "PPP",
            "CSS",
            "CMG",
            'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
            'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Steel),
            'M', ELECTRIC_MOTOR_LV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

        ModHandler.addShapedRecipe("electric_piston_mv", ELECTRIC_PISTON_MV.getStackForm(),
            "PPP",
            "CSS",
            "CMG",
            'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
            'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Aluminium),
            'M', ELECTRIC_MOTOR_MV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper));

        ModHandler.addShapedRecipe("electric_piston_hv", ELECTRIC_PISTON_HV.getStackForm(),
            "PPP",
            "CSS",
            "CMG",
            'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
            'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
            'G', new UnificationEntry(OrePrefix.gearSmall, Materials.StainlessSteel),
            'M', ELECTRIC_MOTOR_HV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

        ModHandler.addShapedRecipe("electric_piston_ev", ELECTRIC_PISTON_EV.getStackForm(),
            "PPP",
            "CSS",
            "CMG",
            'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
            'G', new UnificationEntry(OrePrefix.gearSmall, Materials.Titanium),
            'M', ELECTRIC_MOTOR_EV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

        ModHandler.addShapedRecipe("electric_piston_iv", ELECTRIC_PISTON_IV.getStackForm(),
            "PPP",
            "CSS",
            "CMG",
            'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
            'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
            'G', new UnificationEntry(OrePrefix.gearSmall, Materials.TungstenSteel),
            'M', ELECTRIC_MOTOR_IV,
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));

        // Robot arm recipes
        ModHandler.addShapedRecipe("robot_arm_lv", ROBOT_ARM_LV.getStackForm(),
            "CCC",
            "MSM",
            "PES",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
            'M', ELECTRIC_MOTOR_LV,
            'P', ELECTRIC_PISTON_LV,
            'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

        ModHandler.addShapedRecipe("robot_arm_mv", ROBOT_ARM_MV.getStackForm(),
            "CCC",
            "MSM",
            "PES",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
            'M', ELECTRIC_MOTOR_MV,
            'P', ELECTRIC_PISTON_MV,
            'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper));

        ModHandler.addShapedRecipe("robot_arm_hv", ROBOT_ARM_HV.getStackForm(),
            "CCC",
            "MSM",
            "PES",
            'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
            'M', ELECTRIC_MOTOR_HV,
            'P', ELECTRIC_PISTON_HV,
            'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

        ModHandler.addShapedRecipe("robot_arm_ev", ROBOT_ARM_EV.getStackForm(),
            "CCC",
            "MSM",
            "PES",
            'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
            'M', ELECTRIC_MOTOR_EV,
            'P', ELECTRIC_PISTON_EV,
            'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

        ModHandler.addShapedRecipe("robot_arm_iv", ROBOT_ARM_IV.getStackForm(),
            "CCC",
            "MSM",
            "PES",
            'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
            'M', ELECTRIC_MOTOR_IV,
            'P', ELECTRIC_PISTON_IV,
            'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master),
            'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));

        // Field generator recipes
        ModHandler.addShapedRecipe("field_generator_lv", FIELD_GENERATOR_LV.getStackForm(),
            "WCW",
            "CGC",
            "WCW",
            'G', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic),
            'W', new UnificationEntry(OrePrefix.wireGt01, Materials.Osmium));

        ModHandler.addShapedRecipe("field_generator_mv", FIELD_GENERATOR_MV.getStackForm(),
            "WCW",
            "CGC",
            "WCW",
            'G', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good),
            'W', new UnificationEntry(OrePrefix.wireGt02, Materials.Osmium));

        // Emitter recipes
        ModHandler.addShapedRecipe("emitter_lv", EMITTER_LV.getStackForm(),
            "SSC",
            "WQS",
            "CWS",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

        ModHandler.addShapedRecipe("emitter_mv", EMITTER_MV.getStackForm(),
            "SSC",
            "WQS",
            "CWS",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Copper));

        ModHandler.addShapedRecipe("emitter_hv", EMITTER_HV.getStackForm(),
            "SSC",
            "WQS",
            "CWS",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

        ModHandler.addShapedRecipe("emitter_ev", EMITTER_EV.getStackForm(),
            "SSC",
            "WQS",
            "CWS",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

        ModHandler.addShapedRecipe("emitter_iv", EMITTER_IV.getStackForm(),
            "SSC",
            "WQS",
            "CWS",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master),
            'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));

        // Sensor recipes
        ModHandler.addShapedRecipe("sensor_lv", SENSOR_LV.getStackForm(),
            "P Q",
            "PS ",
            "CPP",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
            'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic));

        ModHandler.addShapedRecipe("sensor_mv", SENSOR_MV.getStackForm(),
            "P Q",
            "PS ",
            "CPP",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
            'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good));

        ModHandler.addShapedRecipe("sensor_hv", SENSOR_HV.getStackForm(),
            "P Q",
            "PS ",
            "CPP",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
            'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced));

        ModHandler.addShapedRecipe("sensor_ev", SENSOR_EV.getStackForm(),
            "P Q",
            "PS ",
            "CPP",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
            'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite));

        ModHandler.addShapedRecipe("sensor_iv", SENSOR_IV.getStackForm(),
            "P Q",
            "PS ",
            "CPP",
            'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
            'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
            'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master));

        // Special tools recipes
        ModHandler.addShapelessRecipe("tool_dataorb", TOOL_DATAORB.getStackForm(), TOOL_DATAORB);
        ModHandler.addShapelessRecipe("tool_datastick", TOOL_DATASTICK.getStackForm(), TOOL_DATASTICK);

        ModHandler.addShapedRecipe("tool_scanner", TOOL_SCANNER.getStackForm(),
            "EPR",
            "CSC",
            "PBP",
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced),
            'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
            'E', EMITTER_MV,
            'R', SENSOR_MV,
            'S', Items.DIAMOND,
            'B', BATTERY_RE_MV_LITHIUM);

        // Field generator recipes
        ModHandler.addShapedRecipe("field_generator_hv", FIELD_GENERATOR_HV.getStackForm(),
            "WCW",
            "CGC",
            "WCW",
            'G', QUANTUMEYE.getStackForm(),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced),
            'W', new UnificationEntry(OrePrefix.wireGt04, Materials.Osmium));

        ModHandler.addShapedRecipe("field_generator_ev", FIELD_GENERATOR_EV.getStackForm(),
            "WCW",
            "CGC",
            "WCW",
            'G', new UnificationEntry(OrePrefix.gem, Materials.NetherStar),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite),
            'W', new UnificationEntry(OrePrefix.wireGt08, Materials.Osmium));

        ModHandler.addShapedRecipe("field_generator_iv", FIELD_GENERATOR_IV.getStackForm(),
            "WCW",
            "CGC",
            "WCW",
            'G', QUANTUMSTAR.getStackForm(),
            'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master),
            'W', new UnificationEntry(OrePrefix.wireGt16, Materials.Osmium));


        ModHandler.addShapedRecipe("component_sawblade_diamond", COMPONENT_SAWBLADE_DIAMOND.getStackForm(),
            " D ",
            "DGD",
            " D ",
            'D', new UnificationEntry(OrePrefix.dustSmall, Materials.Diamond),
            'G', new UnificationEntry(OrePrefix.gear, Materials.CobaltBrass));

        ModHandler.addShapedRecipe("component_grinder_diamond", COMPONENT_GRINDER_DIAMOND.getStackForm(),
            "DSD",
            "SIS",
            "DSD",
            'I', OreDictNames.craftingIndustrialDiamond,
            'D', new UnificationEntry(OrePrefix.dust, Materials.Diamond),
            'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));

        ModHandler.addShapedRecipe("component_grinder_tungsten", COMPONENT_GRINDER_TUNGSTEN.getStackForm(),
            "TST",
            "SIS",
            "TST",
            'I', OreDictNames.craftingIndustrialDiamond,
            'T', new UnificationEntry(OrePrefix.plate, Materials.Tungsten),
            'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));

        // Upgrades recipes
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 2))
            .outputs(UPGRADE_MUFFLER.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
            .outputs(UPGRADE_LOCK.getStackForm())
            .duration(6400)
            .EUt(16)
            .buildAndRegister();

        // Misc
//        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
//            .inputs(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 4), OreDictUnifier.get(OrePrefix.foil, Materials.Zinc, 16))
//            .fluidInputs(Materials.Plastic.getFluid(144)) // TODO FLUIDS
//            .outputs(COMPONENT_FILTER.getStackForm())
//            .duration(1600)
//            .EUt(32)
//            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Good, 4), OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 2))
            .outputs(SCHEMATIC.getStackForm())
            .duration(3200)
            .EUt(4)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(SENSOR_LV.getStackForm(), EMITTER_LV.getStackForm())
            .outputs(NC_SENSORKIT.getStackForm())
            .duration(1600)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_LV.getStackForm(), OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Basic, 2))
            .outputs(FLUID_REGULATOR_LV.getStackForm())
            .duration(800)
            .EUt(4)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_MV.getStackForm(), OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Good, 2))
            .outputs(FLUID_REGULATOR_MV.getStackForm())
            .duration(800)
            .EUt(8)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_HV.getStackForm(), OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Advanced, 2))
            .outputs(FLUID_REGULATOR_HV.getStackForm())
            .duration(800)
            .EUt(16)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_EV.getStackForm(), OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Elite, 2))
            .outputs(FLUID_REGULATOR_EV.getStackForm())
            .duration(800)
            .EUt(32)
            .buildAndRegister();
        RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
            .inputs(ELECTRIC_PUMP_IV.getStackForm(), OreDictUnifier.get(OrePrefix.circuit, MarkerMaterials.Tier.Master, 2))
            .outputs(FLUID_REGULATOR_IV.getStackForm())
            .duration(800)
            .EUt(64)
            .buildAndRegister();
    }

	@Override
	public boolean onEntityItemUpdate(EntityItem itemEntity) {
		int damage = itemEntity.getItem().getMetadata();
		if (damage < this.metaItemOffset && damage >= 0 && !itemEntity.getEntityWorld().isRemote) {

			Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			if (material != null) {
				int posX = MathHelper.floor(itemEntity.posX);
				int posY = MathHelper.floor(itemEntity.posY);
				int posZ = MathHelper.floor(itemEntity.posZ);
				OrePrefix prefix = this.orePrefixes[(damage / 1000)];
				if (prefix == OrePrefix.dustImpure || prefix == OrePrefix.dustPure || prefix == OrePrefix.crushed || prefix == OrePrefix.dust) {

					IBlockState blockState = itemEntity.getEntityWorld().getBlockState(new BlockPos(posX, posY, posZ));
					if (blockState.getBlock() == Blocks.CAULDRON) {
						int waterLevel = blockState.getValue(BlockCauldron.LEVEL);
						if (waterLevel > 0) {
							if (prefix == OrePrefix.crushed) {
								itemEntity.setItem(OreDictUnifier.get(OrePrefix.crushedPurified, material, itemEntity.getItem().getCount()));
							} else if (prefix == OrePrefix.dust && material == Materials.Wheat) {
								itemEntity.setItem(FOOD_DOUGH.getStackForm(itemEntity.getItem().getCount()));
							} else {
								itemEntity.setItem(OreDictUnifier.get(OrePrefix.dust, material, itemEntity.getItem().getCount()));
							}
							itemEntity.getEntityWorld().setBlockState(new BlockPos(posX, posY, posZ), blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> lines, ITooltipFlag tooltipFlag) {
		super.addInformation(itemStack, world, lines, tooltipFlag);
		int damage = itemStack.getItemDamage();
		if (damage < this.metaItemOffset && damage >= 0) {
			Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			if (material != null) {
				OrePrefix prefix = this.orePrefixes[(damage / 1000)];
				if (prefix == OrePrefix.dustImpure || prefix == OrePrefix.dustPure) {
					lines.add(I18n.format("metaitem.dust.tooltip.purify"));
				}
			}
		}
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int damage = stack.getItemDamage();
		if (damage >= this.metaItemOffset + 430 && damage <= this.metaItemOffset + 461) {
			return SPRAY_EMPTY.getStackForm();
		}
		if (damage == this.metaItemOffset + 479 || damage == this.metaItemOffset + 476) {
			return new ItemStack(this, 1, damage - 2);
		}
		if (damage == this.metaItemOffset + 401) {
			return new ItemStack(this, 1, damage - 1);
		}
		return super.getContainerItem(stack);
	}
}