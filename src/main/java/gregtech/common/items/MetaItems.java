package gregtech.common.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.NotImplementedException;

@SuppressWarnings("WeakerAccess")
public final class MetaItems {

	private MetaItems() {}

	public static MetaItem1 META_ITEM_FIRST;
	public static MetaItem2 META_ITEM_SECOND;
	public static MetaTool META_TOOL;

	public static MetaItem<?>.MetaValueItem CREDIT_COPPER;
	public static MetaItem<?>.MetaValueItem CREDIT_CUPRONICKEL;
	public static MetaItem<?>.MetaValueItem CREDIT_SILVER;
	public static MetaItem<?>.MetaValueItem CREDIT_GOLD;
	public static MetaItem<?>.MetaValueItem CREDIT_PLATINUM;
	public static MetaItem<?>.MetaValueItem CREDIT_OSMIUM;
	public static MetaItem<?>.MetaValueItem CREDIT_NAQUADAH;
	public static MetaItem<?>.MetaValueItem CREDIT_DARMSTADTIUM;

	public static MetaItem<?>.MetaValueItem COIN_GOLD_ANCIENT;
	public static MetaItem<?>.MetaValueItem COIN_DOGE;
	public static MetaItem<?>.MetaValueItem COIN_CHOCOLATE;

	public static MetaItem<?>.MetaValueItem MINECART_WHEELS_IRON;
	public static MetaItem<?>.MetaValueItem MINECART_WHEELS_STEEL;

	public static MetaItem<?>.MetaValueItem SHAPE_EMPTY;

	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_PLATE;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_CASING;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_GEAR;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_CREDIT;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BOTTLE;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_INGOT;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BALL;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BLOCK;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_NUGGET;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BUN;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BREAD;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_BAGUETTE;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_CYLINDER;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_ANVIL;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_NAME;
	public static MetaItem<?>.MetaValueItem SHAPE_MOLD_GEAR_SMALL;

	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PLATE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_ROD;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_BOLT;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_RING;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_CELL;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_INGOT;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_WIRE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_CASING;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PIPE_TINY;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PIPE_SMALL;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PIPE_MEDIUM;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PIPE_LARGE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PIPE_HUGE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_BLOCK;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SWORD;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PICKAXE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SHOVEL;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_AXE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_HOE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_HAMMER;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_FILE;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SAW;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_GEAR;
	public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_BOTTLE;

	public static MetaItem<?>.MetaValueItem SHAPE_SLICER_FLAT;
	public static MetaItem<?>.MetaValueItem SHAPE_SLICER_STRIPES;

	public static MetaItem<?>.MetaValueItem FUEL_CAN_PLASTIC_EMPTY;
	public static MetaItem<?>.MetaValueItem FUEL_CAN_PLASTIC_FILLED;

	public static MetaItem<?>.MetaValueItem SPRAY_EMPTY;

	public static MetaItem<?>.MetaValueItem THERMOS_CAN_EMPTY;

	public static MetaItem<?>.MetaValueItem LARGE_FLUID_CELL_STEEL;
	public static MetaItem<?>.MetaValueItem LARGE_FLUID_CELL_TUNGSTENSTEEL;

	public static MetaItem<?>.MetaValueItem TOOL_MATCHES;
	public static MetaItem<?>.MetaValueItem TOOL_MATCHBOX_USED;
	public static MetaItem<?>.MetaValueItem TOOL_MATCHBOX_FULL;

	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_INVAR_EMPTY;
	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_INVAR_USED;
	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_INVAR_FULL;

	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_PLATINUM_EMPTY;
	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_PLATINUM_USED;
	public static MetaItem<?>.MetaValueItem TOOL_LIGHTER_PLATINUM_FULL;

	public static MetaItem<?>.MetaValueItem INGOT_IRIDIUM_ALLOY;

	public static MetaItem<?>.MetaValueItem PAPER_PRINTED_PAGES;
	public static MetaItem<?>.MetaValueItem PAPER_MAGIC_EMPTY;
	public static MetaItem<?>.MetaValueItem PAPER_MAGIC_PAGE;
	public static MetaItem<?>.MetaValueItem PAPER_MAGIC_PAGES;
	public static MetaItem<?>.MetaValueItem PAPER_PUNCH_CARD_EMPTY;
	public static MetaItem<?>.MetaValueItem PAPER_PUNCH_CARD_ENCODED;

	public static MetaItem<?>.MetaValueItem SCHEMATIC;
	public static MetaItem<?>.MetaValueItem SCHEMATIC_CRAFTING;
	public static MetaItem<?>.MetaValueItem SCHEMATIC_1X1;
	public static MetaItem<?>.MetaValueItem SCHEMATIC_2X2;
	public static MetaItem<?>.MetaValueItem SCHEMATIC_3X3;
	public static MetaItem<?>.MetaValueItem SCHEMATIC_DUST;

	public static MetaItem<?>.MetaValueItem BATTERY_HULL_LV;
	public static MetaItem<?>.MetaValueItem BATTERY_HULL_MV;
	public static MetaItem<?>.MetaValueItem BATTERY_HULL_HV;

	public static MetaItem<?>.MetaValueItem BATTERY_RE_ULV_TANTALUM;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_LV_SULFURICACID;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_LV_MERCURY;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_LV_CADMIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_LV_LITHIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_LV_SODIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_MV_SULFURICACID;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_MV_MERCURY;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_MV_CADMIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_MV_LITHIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_MV_SODIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_HV_SULFURICACID;
	public static MetaItem<?>.MetaValueItem BATTERY_SU_HV_MERCURY;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_HV_CADMIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_HV_LITHIUM;
	public static MetaItem<?>.MetaValueItem BATTERY_RE_HV_SODIUM;

	public static MetaItem<?>.MetaValueItem ENERGY_LAPOTRONICORB;
	public static MetaItem<?>.MetaValueItem ENERGY_LAPOTRONICORB2;
	public static MetaItem<?>.MetaValueItem ZPM;
	public static MetaItem<?>.MetaValueItem ZPM2;

	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_LV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_MV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_HV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_EV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_IV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_LUV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_ZPM;
	public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_UV;

	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_LV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_MV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_HV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_EV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_IV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_LUV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_ZPM;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_UV;

	public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_LV;
	public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_MV;
	public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_HV;
	public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_EV;
	public static MetaItem<?>.MetaValueItem FLUID_REGULATOR_IV;

	public static MetaItem<?>.MetaValueItem FLUID_FILTER;

	public static MetaItem<?>.MetaValueItem ROTOR_LV;
	public static MetaItem<?>.MetaValueItem ROTOR_MV;
	public static MetaItem<?>.MetaValueItem ROTOR_HV;
	public static MetaItem<?>.MetaValueItem ROTOR_EV;
	public static MetaItem<?>.MetaValueItem ROTOR_IV;

	public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_LV;
	public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_MV;
	public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_HV;
	public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_EV;
	public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_IV;

	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_LV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_MV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_HV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_EV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_IV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_LUV;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_ZPM;
	public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_UV;

	public static MetaItem<?>.MetaValueItem ROBOT_ARM_LV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_MV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_HV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_EV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_IV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_LUV;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_ZPM;
	public static MetaItem<?>.MetaValueItem ROBOT_ARM_UV;

	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_LV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_MV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_HV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_EV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_IV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_LUV;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_ZPM;
	public static MetaItem<?>.MetaValueItem FIELD_GENERATOR_UV;

	public static MetaItem<?>.MetaValueItem EMITTER_LV;
	public static MetaItem<?>.MetaValueItem EMITTER_MV;
	public static MetaItem<?>.MetaValueItem EMITTER_HV;
	public static MetaItem<?>.MetaValueItem EMITTER_EV;
	public static MetaItem<?>.MetaValueItem EMITTER_IV;
	public static MetaItem<?>.MetaValueItem EMITTER_LUV;
	public static MetaItem<?>.MetaValueItem EMITTER_ZPM;
	public static MetaItem<?>.MetaValueItem EMITTER_UV;

	public static MetaItem<?>.MetaValueItem SENSOR_LV;
	public static MetaItem<?>.MetaValueItem SENSOR_MV;
	public static MetaItem<?>.MetaValueItem SENSOR_HV;
	public static MetaItem<?>.MetaValueItem SENSOR_EV;
	public static MetaItem<?>.MetaValueItem SENSOR_IV;
	public static MetaItem<?>.MetaValueItem SENSOR_LUV;
	public static MetaItem<?>.MetaValueItem SENSOR_ZPM;
	public static MetaItem<?>.MetaValueItem SENSOR_UV;

	public static MetaItem<?>.MetaValueItem TOOL_DATASTICK;
	public static MetaItem<?>.MetaValueItem TOOL_DATAORB;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PRIMITIVE;
	public static MetaItem<?>.MetaValueItem CIRCUIT_BASIC;
	public static MetaItem<?>.MetaValueItem CIRCUIT_GOOD;
	public static MetaItem<?>.MetaValueItem CIRCUIT_ADVANCED;
	public static MetaItem<?>.MetaValueItem CIRCUIT_DATA;
	public static MetaItem<?>.MetaValueItem CIRCUIT_ELITE;
	public static MetaItem<?>.MetaValueItem CIRCUIT_MASTER;
	public static MetaItem<?>.MetaValueItem CIRCUIT_ULTIMATE;

	public static MetaItem<?>.MetaValueItem CIRCUIT_BOARD_BASIC;
	public static MetaItem<?>.MetaValueItem CIRCUIT_BOARD_ADVANCED;
	public static MetaItem<?>.MetaValueItem CIRCUIT_BOARD_ELITE;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_CRYSTAL_CHIP_ELITE;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_CRYSTAL_CHIP_MASTER;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_ADVANCED;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_WIRING_BASIC;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_WIRING_ADVANCED;
	public static MetaItem<?>.MetaValueItem CIRCUIT_PARTS_WIRING_ELITE;

	public static MetaItem<?>.MetaValueItem EMPTY_BOARD_BASIC;
	public static MetaItem<?>.MetaValueItem EMPTY_BOARD_ELITE;

	public static MetaItem<?>.MetaValueItem COMPONENT_SAWBLADE_DIAMOND;
	public static MetaItem<?>.MetaValueItem COMPONENT_GRINDER_DIAMOND;
	public static MetaItem<?>.MetaValueItem COMPONENT_GRINDER_TUNGSTEN;

	public static MetaItem<?>.MetaValueItem QUANTUMEYE;
	public static MetaItem<?>.MetaValueItem QUANTUMSTAR;
	public static MetaItem<?>.MetaValueItem GRAVISTAR;

	public static MetaItem<?>.MetaValueItem UPGRADE_MUFFLER;
	public static MetaItem<?>.MetaValueItem UPGRADE_LOCK;

	public static MetaItem<?>.MetaValueItem COMPONENT_FILTER;

    public static MetaItem<?>.MetaValueItem COVER_CONTROLLER;
    public static MetaItem<?>.MetaValueItem COVER_ACTIVITY_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_FLUID_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_ITEM_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_ENERGY_DETECTOR;
    public static MetaItem<?>.MetaValueItem COVER_PLAYER_DETECTOR;

    public static MetaItem<?>.MetaValueItem COVER_SCREEN;
    public static MetaItem<?>.MetaValueItem COVER_CRAFTING;
    public static MetaItem<?>.MetaValueItem COVER_DRAIN;

    public static MetaItem<?>.MetaValueItem COVER_SHUTTER;

    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_8V;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_LV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_MV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_HV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_EV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_IV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_LUV;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_ZPM;
    public static MetaItem<?>.MetaValueItem COVER_SOLARPANEL_UV;

	public static MetaItem<?>.MetaValueItem TOOL_CHEAT;
	public static MetaItem<?>.MetaValueItem TOOL_SCANNER;

	public static MetaItem<?>.MetaValueItem NC_SENSORKIT;
	public static MetaItem<?>.MetaValueItem DUCT_TAPE;
	public static MetaItem<?>.MetaValueItem MCGUFFIUM_239;

	public static MetaItem<?>.MetaValueItem INTEGRATED_CIRCUIT;

	public static MetaItem<?>.MetaValueItem FLUID_CELL;

	public static MetaItem<?>.MetaValueItem THERMOS_CAN_DARK_COFFEE;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_DARK_CAFE_AU_LAIT;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_COFFEE;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_CAFE_AU_LAIT;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_LAIT_AU_CAFE;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_DARK_CHOCOLATE_MILK;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_CHOCOLATE_MILK;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_TEA;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_SWEET_TEA;
	public static MetaItem<?>.MetaValueItem THERMOS_CAN_ICE_TEA;

	public static MetaItem<?>.MetaValueItem GELLED_TOLUENE;

	public static MetaItem<?>.MetaValueItem BOTTLE_PURPLE_DRINK;
	public static MetaItem<?>.MetaValueItem BOTTLE_GRAPE_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_WINE;
	public static MetaItem<?>.MetaValueItem BOTTLE_VINEGAR;
	public static MetaItem<?>.MetaValueItem BOTTLE_POTATO_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_VODKA;
	public static MetaItem<?>.MetaValueItem BOTTLE_LENINADE;
	public static MetaItem<?>.MetaValueItem BOTTLE_MINERAL_WATER;
	public static MetaItem<?>.MetaValueItem BOTTLE_SALTY_WATER;
	public static MetaItem<?>.MetaValueItem BOTTLE_REED_WATER;
	public static MetaItem<?>.MetaValueItem BOTTLE_RUM;
	public static MetaItem<?>.MetaValueItem BOTTLE_PIRATE_BREW;
	public static MetaItem<?>.MetaValueItem BOTTLE_HOPS_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_DARK_BEER;
	public static MetaItem<?>.MetaValueItem BOTTLE_DRAGON_BLOOD;
	public static MetaItem<?>.MetaValueItem BOTTLE_WHEATY_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_SCOTCH;
	public static MetaItem<?>.MetaValueItem BOTTLE_GLEN_MCKENNER;
	public static MetaItem<?>.MetaValueItem BOTTLE_WHEATY_HOPS_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_BEER;
	public static MetaItem<?>.MetaValueItem BOTTLE_CHILLY_SAUCE;
	public static MetaItem<?>.MetaValueItem BOTTLE_HOT_SAUCE;
	public static MetaItem<?>.MetaValueItem BOTTLE_DIABOLO_SAUCE;
	public static MetaItem<?>.MetaValueItem BOTTLE_DIABLO_SAUCE;
	public static MetaItem<?>.MetaValueItem BOTTLE_SNITCHES_GLITCH_SAUCE;
	public static MetaItem<?>.MetaValueItem BOTTLE_APPLE_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_CIDER;
	public static MetaItem<?>.MetaValueItem BOTTLE_GOLDEN_APPLE_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_GOLDEN_CIDER;
	public static MetaItem<?>.MetaValueItem BOTTLE_IDUNS_APPLE_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_NOTCHES_BREW;
	public static MetaItem<?>.MetaValueItem BOTTLE_LEMON_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_LIMONCELLO;
	public static MetaItem<?>.MetaValueItem BOTTLE_LEMONADE;
	public static MetaItem<?>.MetaValueItem BOTTLE_ALCOPOPS;
	public static MetaItem<?>.MetaValueItem BOTTLE_CAVE_JOHNSONS_GRENADE_JUICE;
	public static MetaItem<?>.MetaValueItem BOTTLE_MILK;
	public static MetaItem<?>.MetaValueItem BOTTLE_HOLY_WATER;

	public static MetaItem<?>.MetaValueItem FOOD_POTATO_ON_STICK;
	public static MetaItem<?>.MetaValueItem FOOD_POTATO_ON_STICK_ROASTED;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_POTATO;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_FRIES;
	public static MetaItem<?>.MetaValueItem FOOD_FRIES;
	public static MetaItem<?>.MetaValueItem FOOD_PACKAGED_FRIES;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_POTATOCHIPS;
	public static MetaItem<?>.MetaValueItem FOOD_POTATOCHIPS;
	public static MetaItem<?>.MetaValueItem FOOD_CHILICHIPS;
	public static MetaItem<?>.MetaValueItem FOOD_PACKAGED_POTATOCHIPS;
	public static MetaItem<?>.MetaValueItem FOOD_PACKAGED_CHILICHIPS;
	public static MetaItem<?>.MetaValueItem FOOD_CHUM;
	public static MetaItem<?>.MetaValueItem FOOD_CHUM_ON_STICK;
	public static MetaItem<?>.MetaValueItem FOOD_DOUGH_SUGAR;
	public static MetaItem<?>.MetaValueItem FOOD_DOUGH_CHOCOLATE;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_COOKIE;

	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BUNS;
	public static MetaItem<?>.MetaValueItem FOOD_BURGER_VEGGIE;
	public static MetaItem<?>.MetaValueItem FOOD_BURGER_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_BURGER_MEAT;
	public static MetaItem<?>.MetaValueItem FOOD_BURGER_CHUM;

	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BREADS;
	public static MetaItem<?>.MetaValueItem FOOD_SANDWICH_VEGGIE;
	public static MetaItem<?>.MetaValueItem FOOD_SANDWICH_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_SANDWICH_BACON;
	public static MetaItem<?>.MetaValueItem FOOD_SANDWICH_STEAK;

	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BAGUETTES;
	public static MetaItem<?>.MetaValueItem FOOD_LARGE_SANDWICH_VEGGIE;
	public static MetaItem<?>.MetaValueItem FOOD_LARGE_SANDWICH_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_LARGE_SANDWICH_BACON;
	public static MetaItem<?>.MetaValueItem FOOD_LARGE_SANDWICH_STEAK;

	public static MetaItem<?>.MetaValueItem FOOD_RAW_PIZZA_VEGGIE;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_PIZZA_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_PIZZA_MEAT;

	public static MetaItem<?>.MetaValueItem FOOD_BAKED_PIZZA_VEGGIE;
	public static MetaItem<?>.MetaValueItem FOOD_BAKED_PIZZA_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_BAKED_PIZZA_MEAT;

	public static MetaItem<?>.MetaValueItem DYE_INDIGO;

	public static MetaItem<?>.MetaValueItem PLANK_OAK;
	public static MetaItem<?>.MetaValueItem PLANK_SPRUCE;
	public static MetaItem<?>.MetaValueItem PLANK_BIRCH;
	public static MetaItem<?>.MetaValueItem PLANK_JUNGLE;
	public static MetaItem<?>.MetaValueItem PLANK_ACACIA;
	public static MetaItem<?>.MetaValueItem PLANK_DARKOAK;

	public static MetaItem<?>.MetaValueItem SFMIXTURE;
	public static MetaItem<?>.MetaValueItem MSFMIXTURE;

	public static MetaItem<?>.MetaValueItem CROP_DROP_PLUMBILIA;
	public static MetaItem<?>.MetaValueItem CROP_DROP_ARGENTIA;
	public static MetaItem<?>.MetaValueItem CROP_DROP_INDIGO;
	public static MetaItem<?>.MetaValueItem CROP_DROP_FERRU;
	public static MetaItem<?>.MetaValueItem CROP_DROP_AURELIA;
	public static MetaItem<?>.MetaValueItem CROP_DROP_TEALEAF;

	public static MetaItem<?>.MetaValueItem CROP_DROP_OIL_BERRY;
	public static MetaItem<?>.MetaValueItem CROP_DROP_BOBS_YER_UNCLE_RANKS;
	public static MetaItem<?>.MetaValueItem CROP_DROP_UUM_BERRY;
	public static MetaItem<?>.MetaValueItem CROP_DROP_UUA_BERRY;

	public static MetaItem<?>.MetaValueItem CROP_DROP_MILK_WART;

	public static MetaItem<?>.MetaValueItem CROP_DROP_COPPON;
	public static MetaItem<?>.MetaValueItem CROP_DROP_TINE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_BAUXITE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_ILMENITE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_PITCHBLENDE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_URANINITE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_THORIUM;
	public static MetaItem<?>.MetaValueItem CROP_DROP_NICKEL;
	public static MetaItem<?>.MetaValueItem CROP_DROP_ZINC;
	public static MetaItem<?>.MetaValueItem CROP_DROP_MANGANESE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_SCHEELITE;
	public static MetaItem<?>.MetaValueItem CROP_DROP_PLATINUM;
	public static MetaItem<?>.MetaValueItem CROP_DROP_IRIDIUM;
	public static MetaItem<?>.MetaValueItem CROP_DROP_OSMIUM;
	public static MetaItem<?>.MetaValueItem CROP_DROP_NAQUADAH;

	public static MetaItem<?>.MetaValueItem CROP_DROP_CHILLY;
	public static MetaItem<?>.MetaValueItem CROP_DROP_LEMON;
	public static MetaItem<?>.MetaValueItem CROP_DROP_TOMATO;
	public static MetaItem<?>.MetaValueItem CROP_DROP_MTOMATO;
	public static MetaItem<?>.MetaValueItem CROP_DROP_GRAPES;
	public static MetaItem<?>.MetaValueItem CROP_DROP_ONION;
	public static MetaItem<?>.MetaValueItem CROP_DROP_CUCUMBER;

	public static MetaItem<?>.MetaValueItem FOOD_CHEESE;
	public static MetaItem<?>.MetaValueItem FOOD_DOUGH;
	public static MetaItem<?>.MetaValueItem FOOD_FLAT_DOUGH;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_BREAD;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_BUN;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_BAGUETTE;
	public static MetaItem<?>.MetaValueItem FOOD_BAKED_BUN;
	public static MetaItem<?>.MetaValueItem FOOD_BAKED_BAGUETTE;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BREAD;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BUN;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_BAGUETTE;
	public static MetaItem<?>.MetaValueItem FOOD_RAW_CAKE;
	public static MetaItem<?>.MetaValueItem FOOD_BAKED_CAKE;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_LEMON;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_TOMATO;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_ONION;
	public static MetaItem<?>.MetaValueItem FOOD_SLICED_CUCUMBER;

	public static MetaItem<?>.MetaValueItem FOOD_SLICED_CHEESE;

	public static MetaItem<?>.MetaValueItem[] DYE_ONLY_ITEMS = new MetaItem.MetaValueItem[16];

	public static MetaItem<?>.MetaValueItem[] SPRAY_CAN_DYES = new MetaItem.MetaValueItem[16];
	public static MetaItem<?>.MetaValueItem[] SPRAY_CAN_DYES_USED = new MetaItem.MetaValueItem[16];

	public static ToolMetaItem<?>.MetaToolValueItem SWORD;
	public static ToolMetaItem<?>.MetaToolValueItem PICKAXE;
	public static ToolMetaItem<?>.MetaToolValueItem SHOVEL;
	public static ToolMetaItem<?>.MetaToolValueItem AXE;
	public static ToolMetaItem<?>.MetaToolValueItem HOE;
	public static ToolMetaItem<?>.MetaToolValueItem SAW;
	public static ToolMetaItem<?>.MetaToolValueItem HARDHAMMER;
	public static ToolMetaItem<?>.MetaToolValueItem SOFTHAMMER;
	public static ToolMetaItem<?>.MetaToolValueItem WRENCH;
	public static ToolMetaItem<?>.MetaToolValueItem FILE;
	public static ToolMetaItem<?>.MetaToolValueItem CROWBAR;
	public static ToolMetaItem<?>.MetaToolValueItem SCREWDRIVER;
	public static ToolMetaItem<?>.MetaToolValueItem MORTAR;
	public static ToolMetaItem<?>.MetaToolValueItem WIRECUTTER;
	public static ToolMetaItem<?>.MetaToolValueItem SCOOP;
	public static ToolMetaItem<?>.MetaToolValueItem BRANCHCUTTER;
	public static ToolMetaItem<?>.MetaToolValueItem UNIVERSALSPADE;
	public static ToolMetaItem<?>.MetaToolValueItem KNIFE;
	public static ToolMetaItem<?>.MetaToolValueItem BUTCHERYKNIFE;
	public static ToolMetaItem<?>.MetaToolValueItem SENSE;
	public static ToolMetaItem<?>.MetaToolValueItem PLOW;
	public static ToolMetaItem<?>.MetaToolValueItem PLUNGER;
	public static ToolMetaItem<?>.MetaToolValueItem ROLLING_PIN;
	public static ToolMetaItem<?>.MetaToolValueItem DRILL_LV;
	public static ToolMetaItem<?>.MetaToolValueItem DRILL_MV;
	public static ToolMetaItem<?>.MetaToolValueItem DRILL_HV;
	public static ToolMetaItem<?>.MetaToolValueItem CHAINSAW_LV;
	public static ToolMetaItem<?>.MetaToolValueItem CHAINSAW_MV;
	public static ToolMetaItem<?>.MetaToolValueItem CHAINSAW_HV;
	public static ToolMetaItem<?>.MetaToolValueItem WRENCH_LV;
	public static ToolMetaItem<?>.MetaToolValueItem WRENCH_MV;
	public static ToolMetaItem<?>.MetaToolValueItem WRENCH_HV;
	public static ToolMetaItem<?>.MetaToolValueItem JACKHAMMER;
	public static ToolMetaItem<?>.MetaToolValueItem BUZZSAW;
	public static ToolMetaItem<?>.MetaToolValueItem SCREWDRIVER_LV;
	public static ToolMetaItem<?>.MetaToolValueItem SOLDERING_IRON_LV;
	public static ToolMetaItem<?>.MetaToolValueItem TURBINE_SMALL;
	public static ToolMetaItem<?>.MetaToolValueItem TURBINE_NORMAL;
	public static ToolMetaItem<?>.MetaToolValueItem TURBINE_LARGE;
	public static ToolMetaItem<?>.MetaToolValueItem TURBINE_HUGE;

	public static void init() {
        META_ITEM_FIRST = new MetaItem1();
        META_ITEM_FIRST.setRegistryName("meta_item_1");
        META_ITEM_SECOND = new MetaItem2();
        META_ITEM_SECOND.setRegistryName("meta_item_2");
        META_TOOL = new MetaTool();
        META_TOOL.setRegistryName("meta_tool");
	}

	public static void registerOreDict() {
        MetaItems.META_ITEM_FIRST.registerOreDict();
        MetaItems.META_ITEM_SECOND.registerOreDict();
    }

    public static void registerRecipes() {
        MetaItems.META_ITEM_FIRST.registerRecipes();
        MetaItems.META_ITEM_SECOND.registerRecipes();
        MetaItems.META_TOOL.registerRecipes();
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        META_ITEM_FIRST.registerModels();
        META_ITEM_SECOND.registerModels();
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        MetaItems.META_ITEM_FIRST.registerColor();
        MetaItems.META_ITEM_SECOND.registerColor();
        MetaItems.META_TOOL.registerColor();
    }

	public static ItemStack getIntegratedCircuit(int configuration) {
        ItemStack stack = INTEGRATED_CIRCUIT.getStackForm();
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("Configuration", configuration);
        stack.setTagCompound(tagCompound);
        return stack;
    }

}
