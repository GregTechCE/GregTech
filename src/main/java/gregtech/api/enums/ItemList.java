package gregtech.api.enums;

import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import static gregtech.api.enums.GT_Values.W;

/**
 * Class containing all non-OreDict Items of GregTech.
 */
public enum ItemList implements IItemContainer {
    Display_ITS_FREE,
    Display_Fluid,
    TE_Slag,
    TE_Slag_Rich,
    TE_Rockwool,
    TE_Hardened_Glass,
    FR_Lemon,
    FR_Mulch,
    FR_Fertilizer,
    FR_Compost,
    FR_Silk,
    FR_Wax,
    FR_RefractoryWax,
    FR_WaxCapsule,
    FR_RefractoryCapsule,
    FR_Stick,
    FR_Casing_Impregnated,
    FR_Casing_Sturdy,
    FR_Casing_Hardened,
    FR_Bee_Drone,
    FR_Bee_Princess,
    FR_Bee_Queen,
    FR_Tree_Sapling,
    FR_Butterfly,
    FR_Larvae,
    FR_Serum,
    FR_Caterpillar,
    FR_PollenFertile,
    TF_LiveRoot,
    TF_Vial_FieryBlood,
    TF_Vial_FieryTears,
    RC_ShuntingWire,
    RC_ShuntingWireFrame,
    RC_Rail_Reinforced,
    RC_Rail_Electric,
    RC_Rail_Standard,
    RC_Rail_Wooden,
    RC_Rail_Adv,
    RC_Rail_HS,
    RC_Tie_Wood,
    RC_Tie_Stone,
    RC_Bed_Wood,
    RC_Bed_Stone,
    RC_Rebar,
    IC2_Item_Casing_Tin,
    IC2_Item_Casing_Copper,
    IC2_Item_Casing_Iron,
    IC2_Item_Casing_Steel,
    IC2_Item_Casing_Lead,
    IC2_Item_Casing_Bronze,
    IC2_Item_Casing_Gold,
    IC2_Spray_WeedEx,
    IC2_Scrap,
    IC2_Scrapbox,
    IC2_Fertilizer,
    IC2_Mixed_Metal_Ingot,
    IC2_Hops,
    IC2_Resin,
    IC2_Plantball,
    IC2_PlantballCompressed,
    IC2_CoffeeBeans,
    IC2_CoffeePowder,
    IC2_Crop_Seeds,
    IC2_Grin_Powder,
    IC2_Energium_Dust,
    IC2_Compressed_Coal_Ball,
    IC2_Compressed_Coal_Chunk,
    IC2_Fuel_Rod_Empty,
    IC2_Fuel_Can_Empty,
    IC2_Fuel_Can_Filled,
    IC2_Food_Can_Empty,
    IC2_Food_Can_Filled,
    IC2_Food_Can_Spoiled,
    IC2_ShaftIron,
    IC2_ShaftSteel,
    IC2_Industrial_Diamond,
    IC2_ForgeHammer,
    IC2_WireCutter,
    IC2_SuBattery,
    IC2_ReBattery,
    IC2_AdvBattery,
    IC2_EnergyCrystal,
    IC2_LapotronCrystal,
    Arrow_Head_Glass_Emtpy,
    Arrow_Head_Glass_Poison,
    Arrow_Head_Glass_Poison_Long,
    Arrow_Head_Glass_Poison_Strong,
    Arrow_Head_Glass_Slowness,
    Arrow_Head_Glass_Slowness_Long,
    Arrow_Head_Glass_Weakness,
    Arrow_Head_Glass_Weakness_Long,
    Arrow_Head_Glass_Holy_Water,
    Arrow_Wooden_Glass_Emtpy,
    Arrow_Wooden_Glass_Poison,
    Arrow_Wooden_Glass_Poison_Long,
    Arrow_Wooden_Glass_Poison_Strong,
    Arrow_Wooden_Glass_Slowness,
    Arrow_Wooden_Glass_Slowness_Long,
    Arrow_Wooden_Glass_Weakness,
    Arrow_Wooden_Glass_Weakness_Long,
    Arrow_Wooden_Glass_Holy_Water,
    Arrow_Plastic_Glass_Emtpy,
    Arrow_Plastic_Glass_Poison,
    Arrow_Plastic_Glass_Poison_Long,
    Arrow_Plastic_Glass_Poison_Strong,
    Arrow_Plastic_Glass_Slowness,
    Arrow_Plastic_Glass_Slowness_Long,
    Arrow_Plastic_Glass_Weakness,
    Arrow_Plastic_Glass_Weakness_Long,
    Arrow_Plastic_Glass_Holy_Water,
    Shape_Empty,
    Shape_Mold_Bottle,
    Shape_Mold_Plate,
    Shape_Mold_Ingot,
    Shape_Mold_Casing,
    Shape_Mold_Gear,
    Shape_Mold_Gear_Small,
    Shape_Mold_Credit,
    Shape_Mold_Nugget,
    Shape_Mold_Block,
    Shape_Mold_Ball,
    Shape_Mold_Bun,
    Shape_Mold_Bread,
    Shape_Mold_Baguette,
    Shape_Mold_Cylinder,
    Shape_Mold_Anvil,
    Shape_Mold_Arrow,
    Shape_Mold_Name,
    Shape_Slicer_Flat,
    Shape_Slicer_Stripes,
    Shape_Extruder_Bottle,
    Shape_Extruder_Plate,
    Shape_Extruder_Cell,
    Shape_Extruder_Ring,
    Shape_Extruder_Rod,
    Shape_Extruder_Bolt,
    Shape_Extruder_Ingot,
    Shape_Extruder_Wire,
    Shape_Extruder_Casing,
    Shape_Extruder_Pipe_Tiny,
    Shape_Extruder_Pipe_Small,
    Shape_Extruder_Pipe_Medium,
    Shape_Extruder_Pipe_Large,
    Shape_Extruder_Pipe_Huge,
    Shape_Extruder_Block,
    Shape_Extruder_Sword,
    Shape_Extruder_Pickaxe,
    Shape_Extruder_Shovel,
    Shape_Extruder_Axe,
    Shape_Extruder_Hoe,
    Shape_Extruder_Hammer,
    Shape_Extruder_File,
    Shape_Extruder_Saw,
    Shape_Extruder_Gear,
    Crate_Empty,
    Credit_Copper,
    Credit_Iron,
    Credit_Silver,
    Credit_Gold,
    Credit_Platinum,
    Credit_Osmium,
    Credit_Greg_Copper,
    Credit_Greg_Cupronickel,
    Credit_Greg_Silver,
    Credit_Greg_Gold,
    Credit_Greg_Platinum,
    Credit_Greg_Osmium,
    Credit_Greg_Naquadah,
    Credit_Greg_Neutronium,
    Coin_Gold_Ancient,
    Coin_Doge,
    Coin_Chocolate,
    Cell_Universal_Fluid,
    Cell_Empty,
    Cell_Water,
    Cell_Lava,
    Cell_Air,
    Large_Fluid_Cell_Steel,
    Large_Fluid_Cell_TungstenSteel,
    ThermosCan_Empty,
    ThermosCan_Dark_Coffee,
    ThermosCan_Dark_Cafe_au_lait,
    ThermosCan_Coffee,
    ThermosCan_Cafe_au_lait,
    ThermosCan_Lait_au_cafe,
    ThermosCan_Dark_Chocolate_Milk,
    ThermosCan_Chocolate_Milk,
    ThermosCan_Tea,
    ThermosCan_Sweet_Tea,
    ThermosCan_Ice_Tea,
    Bottle_Empty,
    Bottle_Milk,
    Bottle_Holy_Water,
    Bottle_Purple_Drink,
    Bottle_Grape_Juice,
    Bottle_Wine,
    Bottle_Vinegar,
    Bottle_Potato_Juice,
    Bottle_Vodka,
    Bottle_Leninade,
    Bottle_Mineral_Water,
    Bottle_Salty_Water,
    Bottle_Reed_Water,
    Bottle_Rum,
    Bottle_Pirate_Brew,
    Bottle_Hops_Juice,
    Bottle_Dark_Beer,
    Bottle_Dragon_Blood,
    Bottle_Wheaty_Juice,
    Bottle_Scotch,
    Bottle_Glen_McKenner,
    Bottle_Wheaty_Hops_Juice,
    Bottle_Beer,
    Bottle_Chilly_Sauce,
    Bottle_Hot_Sauce,
    Bottle_Diabolo_Sauce,
    Bottle_Diablo_Sauce,
    Bottle_Snitches_Glitch_Sauce,
    Bottle_Apple_Juice,
    Bottle_Cider,
    Bottle_Golden_Apple_Juice,
    Bottle_Golden_Cider,
    Bottle_Iduns_Apple_Juice,
    Bottle_Notches_Brew,
    Bottle_Lemon_Juice,
    Bottle_Limoncello,
    Bottle_Lemonade,
    Bottle_Alcopops,
    Bottle_Cave_Johnsons_Grenade_Juice,
    Food_Potato_On_Stick,
    Food_Potato_On_Stick_Roasted,
    Food_Fries,
    Food_ChiliChips,
    Food_PotatoChips,
    Food_Baked_Potato,
    Food_Poisonous_Potato,
    Food_Cheese,
    Food_Chum,
    Food_Chum_On_Stick,
    Food_Dough,
    Food_Dough_Sugar,
    Food_Dough_Chocolate,
    Food_Raw_Cookie,
    Food_Flat_Dough,
    Food_Burger_Veggie,
    Food_Burger_Cheese,
    Food_Burger_Meat,
    Food_Burger_Chum,
    Food_Sandwich_Veggie,
    Food_Sandwich_Cheese,
    Food_Sandwich_Bacon,
    Food_Sandwich_Steak,
    Food_Large_Sandwich_Veggie,
    Food_Large_Sandwich_Cheese,
    Food_Large_Sandwich_Bacon,
    Food_Large_Sandwich_Steak,
    Food_Sliced_Lemon,
    Food_Sliced_Tomato,
    Food_Sliced_Onion,
    Food_Sliced_Cucumber,
    Food_Sliced_Cheese,
    Food_Sliced_Bread,
    Food_Sliced_Bun,
    Food_Sliced_Baguette,
    Food_Sliced_Breads,
    Food_Sliced_Buns,
    Food_Sliced_Baguettes,
    Food_Packaged_Fries,
    Food_Packaged_PotatoChips,
    Food_Packaged_ChiliChips,
    Food_Raw_Potato,
    Food_Raw_Fries,
    Food_Raw_PotatoChips,
    Food_Raw_Bread,
    Food_Raw_Bun,
    Food_Raw_Baguette,
    Food_Raw_Cake,
    Food_Raw_Pizza_Veggie,
    Food_Raw_Pizza_Cheese,
    Food_Raw_Pizza_Meat,
    Food_Baked_Bread,
    Food_Baked_Bun,
    Food_Baked_Baguette,
    Food_Baked_Cake,
    Food_Baked_Pizza_Veggie,
    Food_Baked_Pizza_Cheese,
    Food_Baked_Pizza_Meat,
    Crop_Drop_Argentia,
    Crop_Drop_Plumbilia,
    Crop_Drop_Indigo,
    Crop_Drop_Ferru,
    Crop_Drop_Aurelia,
    Crop_Drop_OilBerry,
    Crop_Drop_MilkWart,
    Crop_Drop_BobsYerUncleRanks,
    Crop_Drop_Coppon,
    Crop_Drop_Tine,
    Crop_Drop_Chilly,
    Crop_Drop_Lemon,
    Crop_Drop_Onion,
    Crop_Drop_Tomato,
    Crop_Drop_MTomato,
    Crop_Drop_Grapes,
    Crop_Drop_TeaLeaf,
    Crop_Drop_Cucumber,
    Schematic,
    Schematic_Crafting,
    Schematic_1by1,
    Schematic_2by2,
    Schematic_3by3,
    Schematic_Dust,
    Circuit_Integrated,
    Circuit_Board_Basic,
    Circuit_Board_Advanced,
    Circuit_Board_Elite,
    Circuit_Parts_Advanced,
    Circuit_Parts_Wiring_Basic,
    Circuit_Parts_Wiring_Advanced,
    Circuit_Parts_Wiring_Elite,
    Circuit_Parts_Crystal_Chip_Elite,
    Circuit_Parts_Crystal_Chip_Master,
    Circuit_Primitive,
    Circuit_Basic,
    Circuit_Good,
    Circuit_Advanced,
    Circuit_Data,
    Circuit_Elite,
    Circuit_Master,
    Circuit_Ultimate,
    Rotor_LV, Rotor_MV, Rotor_HV, Rotor_EV, Rotor_IV, Rotor_LuV, Rotor_ZPM, Rotor_UV,
    Electric_Motor_LV, Electric_Motor_MV, Electric_Motor_HV, Electric_Motor_EV, Electric_Motor_IV, Electric_Motor_LuV, Electric_Motor_ZPM, Electric_Motor_UV,
    Electric_Pump_LV, Electric_Pump_MV, Electric_Pump_HV, Electric_Pump_EV, Electric_Pump_IV, Electric_Pump_LuV, Electric_Pump_ZPM, Electric_Pump_UV,
    Conveyor_Module_LV, Conveyor_Module_MV, Conveyor_Module_HV, Conveyor_Module_EV, Conveyor_Module_IV, Conveyor_Module_LuV, Conveyor_Module_ZPM, Conveyor_Module_UV,
    Electric_Piston_LV, Electric_Piston_MV, Electric_Piston_HV, Electric_Piston_EV, Electric_Piston_IV, Electric_Piston_LuV, Electric_Piston_ZPM, Electric_Piston_UV,
    Field_Generator_LV, Field_Generator_MV, Field_Generator_HV, Field_Generator_EV, Field_Generator_IV, Field_Generator_LuV, Field_Generator_ZPM, Field_Generator_UV,
    Robot_Arm_LV, Robot_Arm_MV, Robot_Arm_HV, Robot_Arm_EV, Robot_Arm_IV, Robot_Arm_LuV, Robot_Arm_ZPM, Robot_Arm_UV,
    Emitter_LV, Emitter_MV, Emitter_HV, Emitter_EV, Emitter_IV, Emitter_LuV, Emitter_ZPM, Emitter_UV,
    Sensor_LV, Sensor_MV, Sensor_HV, Sensor_EV, Sensor_IV, Sensor_LuV, Sensor_ZPM, Sensor_UV,
    Battery_Hull_LV, Battery_Hull_MV, Battery_Hull_HV,
    Battery_SU_LV_SulfuricAcid,
    Battery_SU_LV_Mercury,
    Battery_SU_MV_SulfuricAcid,
    Battery_SU_MV_Mercury,
    Battery_SU_HV_SulfuricAcid,
    Battery_SU_HV_Mercury,
    Battery_RE_ULV_Tantalum,
    Battery_RE_LV_Cadmium,
    Battery_RE_LV_Lithium,
    Battery_RE_LV_Sodium,
    Battery_RE_MV_Cadmium,
    Battery_RE_MV_Lithium,
    Battery_RE_MV_Sodium,
    Battery_RE_HV_Cadmium,
    Battery_RE_HV_Lithium,
    Battery_RE_HV_Sodium,
    ZPM,
    Fuel_Can_Plastic_Empty,
    Fuel_Can_Plastic_Filled,
    Upgrade_Battery,
    Upgrade_Overclocker,
    Upgrade_Muffler,
    Upgrade_SteamEngine,
    Upgrade_Lock,
    Cover_Controller,
    Cover_ActivityDetector,
    Cover_FluidDetector,
    Cover_ItemDetector,
    Cover_EnergyDetector,
    Cover_Drain,
    Cover_Shutter,
    Cover_Crafting,
    Cover_Screen,
    Cover_SolarPanel,
    Cover_SolarPanel_8V,
    Cover_SolarPanel_LV,
    Cover_SolarPanel_MV,
    Cover_SolarPanel_HV,
    Cover_SolarPanel_EV,
    Cover_SolarPanel_IV,
    Cover_SolarPanel_LuV,
    Cover_SolarPanel_ZPM,
    Cover_SolarPanel_UV,
    Ingot_IridiumAlloy,
    Plank_Oak,
    Plank_Spruce,
    Plank_Birch,
    Plank_Jungle,
    Plank_Acacia,
    Plank_DarkOak,
    Plank_Larch,
    Plank_Teak,
    Plank_Acacia_Green,
    Plank_Lime,
    Plank_Chestnut,
    Plank_Wenge,
    Plank_Baobab,
    Plank_Sequoia,
    Plank_Kapok,
    Plank_Ebony,
    Plank_Mahagony,
    Plank_Balsa,
    Plank_Willow,
    Plank_Walnut,
    Plank_Greenheart,
    Plank_Cherry,
    Plank_Mahoe,
    Plank_Poplar,
    Plank_Palm,
    Plank_Papaya,
    Plank_Pine,
    Plank_Plum,
    Plank_Maple,
    Plank_Citrus,
    Dye_Indigo,
    Dye_SquidInk,
    Dye_Bonemeal,
    Dye_Cocoa,
    Duct_Tape,
    Book_Written_00,
    Book_Written_01,
    Book_Written_02,
    Book_Written_03,
    Paper_Printed_Pages,
    Paper_Magic_Empty,
    Paper_Magic_Page,
    Paper_Magic_Pages,
    Paper_Punch_Card_Empty,
    Paper_Punch_Card_Encoded,
    McGuffium_239,
    NC_SensorCard,
    NC_SensorKit,
    Tool_Matches,
    Tool_MatchBox_Used,
    Tool_MatchBox_Full,
    Tool_Lighter_Invar_Empty,
    Tool_Lighter_Invar_Used,
    Tool_Lighter_Invar_Full,
    Tool_Lighter_Platinum_Empty,
    Tool_Lighter_Platinum_Used,
    Tool_Lighter_Platinum_Full,
    Tool_Cheat,
    Tool_Scanner,
    Tool_DataOrb,
    Tool_DataStick,
    Tool_Sonictron,
    Tool_Sword_Bronze,
    Tool_Pickaxe_Bronze,
    Tool_Shovel_Bronze,
    Tool_Axe_Bronze,
    Tool_Hoe_Bronze,
    Tool_Sword_Steel,
    Tool_Pickaxe_Steel,
    Tool_Shovel_Steel,
    Tool_Axe_Steel,
    Tool_Hoe_Steel,

    Spray_Empty, Spray_Bug, Spray_Ice, Spray_Hardener, Spray_CFoam, Spray_Pepper, Spray_Hydration,
    Color_00, Color_01, Color_02, Color_03, Color_04, Color_05, Color_06, Color_07, Color_08, Color_09, Color_10, Color_11, Color_12, Color_13, Color_14, Color_15,
    Spray_Color_00, Spray_Color_01, Spray_Color_02, Spray_Color_03, Spray_Color_04, Spray_Color_05, Spray_Color_06, Spray_Color_07, Spray_Color_08, Spray_Color_09, Spray_Color_10, Spray_Color_11, Spray_Color_12, Spray_Color_13, Spray_Color_14, Spray_Color_15,
    Spray_Color_Used_00, Spray_Color_Used_01, Spray_Color_Used_02, Spray_Color_Used_03, Spray_Color_Used_04, Spray_Color_Used_05, Spray_Color_Used_06, Spray_Color_Used_07, Spray_Color_Used_08, Spray_Color_Used_09, Spray_Color_Used_10, Spray_Color_Used_11, Spray_Color_Used_12, Spray_Color_Used_13, Spray_Color_Used_14, Spray_Color_Used_15,

    Armor_Cheat,
    Armor_Cloaking,
    Armor_Lamp,
    Armor_LithiumPack,
    Armor_LapotronicPack,
    Armor_ForceField,
    Energy_LapotronicOrb,
    Reactor_NeutronReflector,
    Component_Turbine_Bronze,
    Component_Turbine_Steel,
    Component_Turbine_Magnalium,
    Component_Turbine_TungstenSteel,
    Component_Turbine_Carbon,
    Component_LavaFilter,
    Component_Sawblade_Diamond,
    Component_Grinder_Diamond,
    Component_Grinder_Tungsten,
    Component_Filter,
    Component_Minecart_Wheels_Iron,
    Component_Minecart_Wheels_Steel,

    Generator_Diesel_LV,
    Generator_Diesel_MV,
    Generator_Diesel_HV,
    Generator_Gas_Turbine_LV,
    Generator_Gas_Turbine_MV,
    Generator_Gas_Turbine_HV,
    Generator_Steam_Turbine_LV,
    Generator_Steam_Turbine_MV,
    Generator_Steam_Turbine_HV,
    Generator_Naquadah_Mark_I,
    Generator_Naquadah_Mark_II,
    Generator_Naquadah_Fluid,

    Machine_Bronze_Boiler,
    Machine_Bronze_Boiler_Solar,
    Machine_Bronze_CraftingTable,
    Machine_Bronze_Furnace,
    Machine_Bronze_Macerator,
    Machine_Bronze_Extractor,
    Machine_Bronze_Hammer,
    Machine_Bronze_Compressor,
    Machine_Bronze_AlloySmelter,
    Machine_Bronze_BlastFurnace,
    Machine_Steel_Boiler_Lava,
    Machine_Steel_Boiler,
    Machine_Steel_Furnace,
    Machine_Steel_Macerator,
    Machine_Steel_Extractor,
    Machine_Steel_Hammer,
    Machine_Steel_Compressor,
    Machine_Steel_AlloySmelter,

    Hull_Bronze, Hull_Steel, Hull_Bronze_Bricks, Hull_Steel_Bricks,

    Transformer_LV_ULV, Transformer_MV_LV, Transformer_HV_MV, Transformer_EV_HV, Transformer_IV_EV, Transformer_LuV_IV, Transformer_ZPM_LuV, Transformer_UV_ZPM, Transformer_MAX_UV,

    Casing_ULV, Casing_LV, Casing_MV, Casing_HV, Casing_EV, Casing_IV, Casing_LuV, Casing_ZPM, Casing_UV, Casing_MAX, Casing_BronzePlatedBricks, Casing_HeatProof, Casing_Coil_Cupronickel, Casing_Coil_Kanthal, Casing_Coil_Nichrome, Casing_Coil_Superconductor,
    Casing_SolidSteel, Casing_FrostProof, Casing_Gearbox_Bronze, Casing_Gearbox_Steel, Casing_Gearbox_Titanium, Casing_Gearbox_TungstenSteel, Casing_Processor, Casing_DataDrive, Casing_ContainmentField, Casing_Assembler, Casing_Pump, Casing_Motor, Casing_Pipe_Bronze, Casing_Pipe_Steel, Casing_Pipe_Titanium, Casing_Pipe_TungstenSteel,
    Casing_Stripes_A, Casing_Stripes_B, Casing_RadioactiveHazard, Casing_BioHazard, Casing_ExplosionHazard, Casing_FireHazard, Casing_AcidHazard, Casing_MagicHazard, Casing_FrostHazard, Casing_NoiseHazard, Casing_Grate, Casing_Vent, Casing_RadiationProof, Casing_Firebox_Bronze, Casing_Firebox_Steel, Casing_Firebox_TungstenSteel,
    Casing_RobustTungstenSteel, Casing_CleanStainlessSteel, Casing_StableTitanium, Casing_Firebox_Titanium,
    Hull_ULV, Hull_LV, Hull_MV, Hull_HV, Hull_EV, Hull_IV, Hull_LuV, Hull_ZPM, Hull_UV, Hull_MAX,

    Automation_Filter_ULV, Automation_Filter_LV, Automation_Filter_MV, Automation_Filter_HV, Automation_Filter_EV, Automation_Filter_IV, Automation_Filter_LuV, Automation_Filter_ZPM, Automation_Filter_UV, Automation_Filter_MAX,
    Automation_TypeFilter_ULV, Automation_TypeFilter_LV, Automation_TypeFilter_MV, Automation_TypeFilter_HV, Automation_TypeFilter_EV, Automation_TypeFilter_IV, Automation_TypeFilter_LuV, Automation_TypeFilter_ZPM, Automation_TypeFilter_UV, Automation_TypeFilter_MAX,
    Automation_ChestBuffer_ULV, Automation_ChestBuffer_LV, Automation_ChestBuffer_MV, Automation_ChestBuffer_HV, Automation_ChestBuffer_EV, Automation_ChestBuffer_IV, Automation_ChestBuffer_LuV, Automation_ChestBuffer_ZPM, Automation_ChestBuffer_UV, Automation_ChestBuffer_MAX,
    Automation_SuperBuffer_ULV, Automation_SuperBuffer_LV, Automation_SuperBuffer_MV, Automation_SuperBuffer_HV, Automation_SuperBuffer_EV, Automation_SuperBuffer_IV, Automation_SuperBuffer_LuV, Automation_SuperBuffer_ZPM, Automation_SuperBuffer_UV, Automation_SuperBuffer_MAX,
    Automation_Regulator_ULV, Automation_Regulator_LV, Automation_Regulator_MV, Automation_Regulator_HV, Automation_Regulator_EV, Automation_Regulator_IV, Automation_Regulator_LuV, Automation_Regulator_ZPM, Automation_Regulator_UV, Automation_Regulator_MAX,

    Hatch_Dynamo_ULV, Hatch_Dynamo_LV, Hatch_Dynamo_MV, Hatch_Dynamo_HV, Hatch_Dynamo_EV, Hatch_Dynamo_IV, Hatch_Dynamo_LuV, Hatch_Dynamo_ZPM, Hatch_Dynamo_UV, Hatch_Dynamo_MAX,
    Hatch_Energy_ULV, Hatch_Energy_LV, Hatch_Energy_MV, Hatch_Energy_HV, Hatch_Energy_EV, Hatch_Energy_IV, Hatch_Energy_LuV, Hatch_Energy_ZPM, Hatch_Energy_UV, Hatch_Energy_MAX,
    Hatch_Input_ULV, Hatch_Input_LV, Hatch_Input_MV, Hatch_Input_HV, Hatch_Input_EV, Hatch_Input_IV, Hatch_Input_LuV, Hatch_Input_ZPM, Hatch_Input_UV, Hatch_Input_MAX,
    Hatch_Input_Bus_ULV, Hatch_Input_Bus_LV, Hatch_Input_Bus_MV, Hatch_Input_Bus_HV, Hatch_Input_Bus_EV, Hatch_Input_Bus_IV, Hatch_Input_Bus_LuV, Hatch_Input_Bus_ZPM, Hatch_Input_Bus_UV, Hatch_Input_Bus_MAX,
    Hatch_Output_ULV, Hatch_Output_LV, Hatch_Output_MV, Hatch_Output_HV, Hatch_Output_EV, Hatch_Output_IV, Hatch_Output_LuV, Hatch_Output_ZPM, Hatch_Output_UV, Hatch_Output_MAX,
    Hatch_Output_Bus_ULV, Hatch_Output_Bus_LV, Hatch_Output_Bus_MV, Hatch_Output_Bus_HV, Hatch_Output_Bus_EV, Hatch_Output_Bus_IV, Hatch_Output_Bus_LuV, Hatch_Output_Bus_ZPM, Hatch_Output_Bus_UV, Hatch_Output_Bus_MAX,
    Hatch_Muffler_LV, Hatch_Muffler_MV, Hatch_Muffler_HV, Hatch_Muffler_EV, Hatch_Muffler_IV, Hatch_Muffler_LuV, Hatch_Muffler_ZPM, Hatch_Muffler_UV, Hatch_Muffler_MAX,
    Hatch_Maintenance,

    Battery_Buffer_1by1_ULV, Battery_Buffer_1by1_LV, Battery_Buffer_1by1_MV, Battery_Buffer_1by1_HV, Battery_Buffer_1by1_EV, Battery_Buffer_1by1_IV, Battery_Buffer_1by1_LuV, Battery_Buffer_1by1_ZPM, Battery_Buffer_1by1_UV, Battery_Buffer_1by1_MAX,
    Battery_Buffer_2by2_ULV, Battery_Buffer_2by2_LV, Battery_Buffer_2by2_MV, Battery_Buffer_2by2_HV, Battery_Buffer_2by2_EV, Battery_Buffer_2by2_IV, Battery_Buffer_2by2_LuV, Battery_Buffer_2by2_ZPM, Battery_Buffer_2by2_UV, Battery_Buffer_2by2_MAX,
    Battery_Buffer_3by3_ULV, Battery_Buffer_3by3_LV, Battery_Buffer_3by3_MV, Battery_Buffer_3by3_HV, Battery_Buffer_3by3_EV, Battery_Buffer_3by3_IV, Battery_Buffer_3by3_LuV, Battery_Buffer_3by3_ZPM, Battery_Buffer_3by3_UV, Battery_Buffer_3by3_MAX,
    Battery_Buffer_4by4_ULV, Battery_Buffer_4by4_LV, Battery_Buffer_4by4_MV, Battery_Buffer_4by4_HV, Battery_Buffer_4by4_EV, Battery_Buffer_4by4_IV, Battery_Buffer_4by4_LuV, Battery_Buffer_4by4_ZPM, Battery_Buffer_4by4_UV, Battery_Buffer_4by4_MAX,

    Locker_ULV, Locker_LV, Locker_MV, Locker_HV, Locker_EV, Locker_IV, Locker_LuV, Locker_ZPM, Locker_UV, Locker_MAX,

    Machine_Multi_LargeBoiler_Bronze, Machine_Multi_LargeBoiler_Steel, Machine_Multi_LargeBoiler_Titanium, Machine_Multi_LargeBoiler_TungstenSteel, Machine_Multi_BlastFurnace, Machine_Multi_ImplosionCompressor, Machine_Multi_VacuumFreezer, Machine_Multi_Furnace,
    Machine_LV_AlloySmelter, Machine_MV_AlloySmelter, Machine_HV_AlloySmelter, Machine_EV_AlloySmelter, Machine_IV_AlloySmelter,
    Machine_LV_Assembler, Machine_MV_Assembler, Machine_HV_Assembler, Machine_EV_Assembler, Machine_IV_Assembler,
    Machine_LV_Bender, Machine_MV_Bender, Machine_HV_Bender, Machine_EV_Bender, Machine_IV_Bender,
    Machine_LV_Canner, Machine_MV_Canner, Machine_HV_Canner, Machine_EV_Canner, Machine_IV_Canner,
    Machine_LV_Compressor, Machine_MV_Compressor, Machine_HV_Compressor, Machine_EV_Compressor, Machine_IV_Compressor,
    Machine_LV_Cutter, Machine_MV_Cutter, Machine_HV_Cutter, Machine_EV_Cutter, Machine_IV_Cutter,
    Machine_LV_Slicer, Machine_MV_Slicer, Machine_HV_Slicer, Machine_EV_Slicer, Machine_IV_Slicer,
    Machine_LV_Sifter, Machine_MV_Sifter, Machine_HV_Sifter, Machine_EV_Sifter, Machine_IV_Sifter,
    Machine_LV_ArcFurnace, Machine_MV_ArcFurnace, Machine_HV_ArcFurnace, Machine_EV_ArcFurnace, Machine_IV_ArcFurnace,
    Machine_LV_PlasmaArcFurnace, Machine_MV_PlasmaArcFurnace, Machine_HV_PlasmaArcFurnace, Machine_EV_PlasmaArcFurnace, Machine_IV_PlasmaArcFurnace,
    Machine_LV_Oven, Machine_MV_Oven, Machine_HV_Oven, Machine_EV_Oven, Machine_IV_Oven,
    Machine_LV_E_Furnace, Machine_MV_E_Furnace, Machine_HV_E_Furnace, Machine_EV_E_Furnace, Machine_IV_E_Furnace,
    Machine_LV_Extractor, Machine_MV_Extractor, Machine_HV_Extractor, Machine_EV_Extractor, Machine_IV_Extractor,
    Machine_LV_Extruder, Machine_MV_Extruder, Machine_HV_Extruder, Machine_EV_Extruder, Machine_IV_Extruder,
    Machine_LV_Lathe, Machine_MV_Lathe, Machine_HV_Lathe, Machine_EV_Lathe, Machine_IV_Lathe,
    Machine_LV_Macerator, Machine_MV_Macerator, Machine_HV_Macerator, Machine_EV_Macerator, Machine_IV_Macerator,
    Machine_LV_Microwave, Machine_MV_Microwave, Machine_HV_Microwave, Machine_EV_Microwave, Machine_IV_Microwave,
    Machine_LV_Printer, Machine_MV_Printer, Machine_HV_Printer, Machine_EV_Printer, Machine_IV_Printer,
    Machine_LV_Recycler, Machine_MV_Recycler, Machine_HV_Recycler, Machine_EV_Recycler, Machine_IV_Recycler,
    Machine_LV_Scanner, Machine_MV_Scanner, Machine_HV_Scanner, Machine_EV_Scanner, Machine_IV_Scanner,
    Machine_LV_Wiremill, Machine_MV_Wiremill, Machine_HV_Wiremill, Machine_EV_Wiremill, Machine_IV_Wiremill,
    Machine_LV_Electrolyzer, Machine_MV_Electrolyzer, Machine_HV_Electrolyzer, Machine_EV_Electrolyzer, Machine_IV_Electrolyzer,
    Machine_LV_Centrifuge, Machine_MV_Centrifuge, Machine_HV_Centrifuge, Machine_EV_Centrifuge, Machine_IV_Centrifuge,
    Machine_LV_ThermalCentrifuge, Machine_MV_ThermalCentrifuge, Machine_HV_ThermalCentrifuge, Machine_EV_ThermalCentrifuge, Machine_IV_ThermalCentrifuge,
    Machine_LV_OreWasher, Machine_MV_OreWasher, Machine_HV_OreWasher, Machine_EV_OreWasher, Machine_IV_OreWasher,
    Machine_LV_RockBreaker, Machine_MV_RockBreaker, Machine_HV_RockBreaker, Machine_EV_RockBreaker, Machine_IV_RockBreaker,
    Machine_LV_Boxinator, Machine_MV_Boxinator, Machine_HV_Boxinator, Machine_EV_Boxinator, Machine_IV_Boxinator,
    Machine_LV_Unboxinator, Machine_MV_Unboxinator, Machine_HV_Unboxinator, Machine_EV_Unboxinator, Machine_IV_Unboxinator,
    Machine_LV_ChemicalReactor, Machine_MV_ChemicalReactor, Machine_HV_ChemicalReactor, Machine_EV_ChemicalReactor, Machine_IV_ChemicalReactor,
    Machine_LV_FluidCanner, Machine_MV_FluidCanner, Machine_HV_FluidCanner, Machine_EV_FluidCanner, Machine_IV_FluidCanner,
    Machine_LV_Disassembler, Machine_MV_Disassembler, Machine_HV_Disassembler, Machine_EV_Disassembler, Machine_IV_Disassembler,
    Machine_LV_Bundler, Machine_MV_Bundler, Machine_HV_Bundler, Machine_EV_Bundler, Machine_IV_Bundler,
    Machine_LV_Massfab, Machine_MV_Massfab, Machine_HV_Massfab, Machine_EV_Massfab, Machine_IV_Massfab,
    Machine_LV_Amplifab, Machine_MV_Amplifab, Machine_HV_Amplifab, Machine_EV_Amplifab, Machine_IV_Amplifab,
    Machine_LV_Replicator, Machine_MV_Replicator, Machine_HV_Replicator, Machine_EV_Replicator, Machine_IV_Replicator,
    Machine_LV_Brewery, Machine_MV_Brewery, Machine_HV_Brewery, Machine_EV_Brewery, Machine_IV_Brewery,
    Machine_LV_Fermenter, Machine_MV_Fermenter, Machine_HV_Fermenter, Machine_EV_Fermenter, Machine_IV_Fermenter,
    Machine_LV_FluidExtractor, Machine_MV_FluidExtractor, Machine_HV_FluidExtractor, Machine_EV_FluidExtractor, Machine_IV_FluidExtractor,
    Machine_LV_FluidSolidifier, Machine_MV_FluidSolidifier, Machine_HV_FluidSolidifier, Machine_EV_FluidSolidifier, Machine_IV_FluidSolidifier,
    Machine_LV_Distillery, Machine_MV_Distillery, Machine_HV_Distillery, Machine_EV_Distillery, Machine_IV_Distillery,
    Machine_LV_ChemicalBath, Machine_MV_ChemicalBath, Machine_HV_ChemicalBath, Machine_EV_ChemicalBath, Machine_IV_ChemicalBath,
    Machine_LV_Polarizer, Machine_MV_Polarizer, Machine_HV_Polarizer, Machine_EV_Polarizer, Machine_IV_Polarizer,
    Machine_LV_ElectromagneticSeparator, Machine_MV_ElectromagneticSeparator, Machine_HV_ElectromagneticSeparator, Machine_EV_ElectromagneticSeparator, Machine_IV_ElectromagneticSeparator,
    Machine_LV_Autoclave, Machine_MV_Autoclave, Machine_HV_Autoclave, Machine_EV_Autoclave, Machine_IV_Autoclave,
    Machine_LV_Mixer, Machine_MV_Mixer, Machine_HV_Mixer, Machine_EV_Mixer, Machine_IV_Mixer,
    Machine_LV_LaserEngraver, Machine_MV_LaserEngraver, Machine_HV_LaserEngraver, Machine_EV_LaserEngraver, Machine_IV_LaserEngraver,
    Machine_LV_Press, Machine_MV_Press, Machine_HV_Press, Machine_EV_Press, Machine_IV_Press,
    Machine_LV_Hammer, Machine_MV_Hammer, Machine_HV_Hammer, Machine_EV_Hammer, Machine_IV_Hammer,
    Machine_LV_FluidHeater, Machine_MV_FluidHeater, Machine_HV_FluidHeater, Machine_EV_FluidHeater, Machine_IV_FluidHeater,

    Neutron_Reflector,
    Reactor_Coolant_He_1, Reactor_Coolant_He_3, Reactor_Coolant_He_6, Reactor_Coolant_NaK_1, Reactor_Coolant_NaK_3, Reactor_Coolant_NaK_6,
    ThoriumCell_1, ThoriumCell_2, ThoriumCell_4,
    FusionComputer_LuV, FusionComputer_ZPMV, FusionComputer_UV,
    Casing_Fusion_Coil, Casing_Fusion, Casing_Fusion2,
    Generator_Plasma_IV, Generator_Plasma_LuV, Generator_Plasma_ZPMV,
    MagicEnergyConverter_LV, MagicEnergyConverter_MV, MagicEnergyConverter_HV,
    MagicEnergyAbsorber_LV, MagicEnergyAbsorber_MV, MagicEnergyAbsorber_HV, MagicEnergyAbsorber_EV,
    Depleted_Thorium_1, Depleted_Thorium_2, Depleted_Thorium_4,
    Processing_Array, Distillation_Tower, Energy_LapotronicOrb2,
    ZPM2, Quantum_Tank_LV, Quantum_Tank_MV, Quantum_Tank_HV, Quantum_Tank_EV, Quantum_Tank_IV, Quantum_Chest_LV, Quantum_Chest_MV, Quantum_Chest_HV, Quantum_Chest_EV, Quantum_Chest_IV,

    NULL, Cover_RedstoneTransmitterExternal, Cover_RedstoneTransmitterInternal, Cover_RedstoneReceiverExternal, Cover_RedstoneReceiverInternal,
    LargeSteamTurbine, LargeGasTurbine, LargeHPSteamTurbine, LargePlasmaTurbine, LargeDieselTurbine,
    Ingot_Heavy1, Ingot_Heavy2, Ingot_Heavy3,
    Pump_LV, Pump_MV, Pump_HV, Pump_EV, Pump_IV,
    Teleporter, Cover_NeedsMaintainance, Casing_Turbine, Casing_Turbine1, Casing_Turbine2, Casing_Turbine3,
    MobRep_LV, MobRep_MV, MobRep_HV, MobRep_EV, MobRep_IV, Cover_PlayerDetector, Machine_Multi_HeatExchanger,
    Block_BronzePlate, Block_IridiumTungstensteel, Block_Plascrete, Block_TungstenSteelReinforced,
    Honeycomb, Charcoal_Pile, Block_BrittleCharcoal, Seismic_Prospector, OilDrill, AdvancedMiner2, PyrolyseOven, OilCracker, Crop_Drop_UUMBerry, Crop_Drop_UUABerry, Empty_Board_Basic, Empty_Board_Elite,
    Battery_Charger_4by4_ULV, Battery_Charger_4by4_LV, Battery_Charger_4by4_MV, Battery_Charger_4by4_HV, Battery_Charger_4by4_EV, Battery_Charger_4by4_IV, Battery_Charger_4by4_LuV, Battery_Charger_4by4_ZPM, Battery_Charger_4by4_UV, Battery_Charger_4by4_MAX,
    MicroTransmitter_HV, MicroTransmitter_EV, MicroTransmitter_IV, MicroTransmitter_LUV, MicroTransmitter_ZPM, 
    Crop_Drop_Bauxite, Crop_Drop_Ilmenite, Crop_Drop_Pitchblende, Crop_Drop_Uraninite, Crop_Drop_Thorium, Crop_Drop_Nickel, Crop_Drop_Zinc, Crop_Drop_Manganese, Crop_Drop_Scheelite, Crop_Drop_Platinum, Crop_Drop_Iridium, Crop_Drop_Osmium, Crop_Drop_Naquadah, Uraniumcell_1, Uraniumcell_2, Uraniumcell_4, Moxcell_1, Moxcell_2, Moxcell_4, 
    ModularBasicHelmet, ModularBasicChestplate, ModularBasicLeggings, ModularBasicBoots, 
    ModularElectric1Helmet, ModularElectric1Chestplate, ModularElectric1Leggings, ModularElectric1Boots, 
    ModularElectric2Helmet, ModularElectric2Chestplate, ModularElectric2Leggings, ModularElectric2Boots, Block_Powderbarrel, GelledToluene, 
    FluidRegulator_LV, FluidRegulator_MV, FluidRegulator_HV, FluidRegulator_EV, FluidRegulator_IV, FluidFilter, CuringOven, Machine_Multi_Assemblyline;

    public static final ItemList[]
            DYE_ONLY_ITEMS = {Color_00, Color_01, Color_02, Color_03, Color_04, Color_05, Color_06, Color_07, Color_08, Color_09, Color_10, Color_11, Color_12, Color_13, Color_14, Color_15}, SPRAY_CAN_DYES = {Spray_Color_00, Spray_Color_01, Spray_Color_02, Spray_Color_03, Spray_Color_04, Spray_Color_05, Spray_Color_06, Spray_Color_07, Spray_Color_08, Spray_Color_09, Spray_Color_10, Spray_Color_11, Spray_Color_12, Spray_Color_13, Spray_Color_14, Spray_Color_15}, SPRAY_CAN_DYES_USED = {Spray_Color_Used_00, Spray_Color_Used_01, Spray_Color_Used_02, Spray_Color_Used_03, Spray_Color_Used_04, Spray_Color_Used_05, Spray_Color_Used_06, Spray_Color_Used_07, Spray_Color_Used_08, Spray_Color_Used_09, Spray_Color_Used_10, Spray_Color_Used_11, Spray_Color_Used_12, Spray_Color_Used_13, Spray_Color_Used_14, Spray_Color_Used_15}, TRANSFORMERS = {Transformer_LV_ULV, Transformer_MV_LV, Transformer_HV_MV, Transformer_EV_HV, Transformer_IV_EV, Transformer_LuV_IV, Transformer_ZPM_LuV, Transformer_UV_ZPM, Transformer_MAX_UV}, MACHINE_HULLS = {Hull_ULV, Hull_LV, Hull_MV, Hull_HV, Hull_EV, Hull_IV, Hull_LuV, Hull_ZPM, Hull_UV, Hull_MAX}, HATCHES_DYNAMO = {Hatch_Dynamo_ULV, Hatch_Dynamo_LV, Hatch_Dynamo_MV, Hatch_Dynamo_HV, Hatch_Dynamo_EV, Hatch_Dynamo_IV, Hatch_Dynamo_LuV, Hatch_Dynamo_ZPM, Hatch_Dynamo_UV, Hatch_Dynamo_MAX}, HATCHES_ENERGY = {Hatch_Energy_ULV, Hatch_Energy_LV, Hatch_Energy_MV, Hatch_Energy_HV, Hatch_Energy_EV, Hatch_Energy_IV, Hatch_Energy_LuV, Hatch_Energy_ZPM, Hatch_Energy_UV, Hatch_Energy_MAX}, HATCHES_INPUT = {Hatch_Input_ULV, Hatch_Input_LV, Hatch_Input_MV, Hatch_Input_HV, Hatch_Input_EV, Hatch_Input_IV, Hatch_Input_LuV, Hatch_Input_ZPM, Hatch_Input_UV, Hatch_Input_MAX}, HATCHES_INPUT_BUS = {Hatch_Input_Bus_ULV, Hatch_Input_Bus_LV, Hatch_Input_Bus_MV, Hatch_Input_Bus_HV, Hatch_Input_Bus_EV, Hatch_Input_Bus_IV, Hatch_Input_Bus_LuV, Hatch_Input_Bus_ZPM, Hatch_Input_Bus_UV, Hatch_Input_Bus_MAX}, HATCHES_OUTPUT = {Hatch_Output_ULV, Hatch_Output_LV, Hatch_Output_MV, Hatch_Output_HV, Hatch_Output_EV, Hatch_Output_IV, Hatch_Output_LuV, Hatch_Output_ZPM, Hatch_Output_UV, Hatch_Output_MAX}, HATCHES_OUTPUT_BUS = {Hatch_Output_Bus_ULV, Hatch_Output_Bus_LV, Hatch_Output_Bus_MV, Hatch_Output_Bus_HV, Hatch_Output_Bus_EV, Hatch_Output_Bus_IV, Hatch_Output_Bus_LuV, Hatch_Output_Bus_ZPM, Hatch_Output_Bus_UV, Hatch_Output_Bus_MAX}, HATCHES_MUFFLER = {Hatch_Muffler_LV, Hatch_Muffler_LV, Hatch_Muffler_MV, Hatch_Muffler_HV, Hatch_Muffler_EV, Hatch_Muffler_IV, Hatch_Muffler_LuV, Hatch_Muffler_ZPM, Hatch_Muffler_UV, Hatch_Muffler_MAX};
    public static Fluid sOilExtraHeavy, sEpichlorhydrin, sDrillingFluid, sNitricAcid, sBlueVitriol, sNickelSulfate, sToluene, sNitrationMixture;
    private ItemStack mStack;
    private boolean mHasNotBeenSet = true;

    @Override
    public IItemContainer set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) return this;
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = GT_Utility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public IItemContainer set(ItemStack aStack) {
        mHasNotBeenSet = false;
        mStack = GT_Utility.copyAmount(1, aStack);
        return this;
    }

    @Override
    public Item getItem() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return null;
        return mStack.getItem();
    }

    @Override
    public Block getBlock() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        return GT_Utility.getBlockFromStack(getItem());
    }

    @Override
    public final boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    @Override
    public boolean isStackEqual(Object aStack) {
        return isStackEqual(aStack, false, false);
    }

    @Override
    public boolean isStackEqual(Object aStack, boolean aWildcard, boolean aIgnoreNBT) {
        if (GT_Utility.isStackInvalid(aStack)) return false;
        return GT_Utility.areUnificationsEqual((ItemStack) aStack, aWildcard ? getWildcard(1) : get(1), aIgnoreNBT);
    }

    @Override
    public ItemStack get(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return GT_Utility.copyAmount(aAmount, aReplacements);
        return GT_Utility.copyAmount(aAmount, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWildcard(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return GT_Utility.copyAmount(aAmount, aReplacements);
        return GT_Utility.copyAmountAndMetaData(aAmount, W, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getUndamaged(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return GT_Utility.copyAmount(aAmount, aReplacements);
        return GT_Utility.copyAmountAndMetaData(aAmount, 0, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return GT_Utility.copyAmount(aAmount, aReplacements);
        return GT_Utility.copyAmountAndMetaData(aAmount, mStack.getMaxDamage() - 1, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GT_Utility.isStackInvalid(rStack)) return null;
        rStack.setStackDisplayName(aDisplayName);
        return GT_Utility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithCharge(long aAmount, int aEnergy, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GT_Utility.isStackInvalid(rStack)) return null;
        GT_ModHandler.chargeElectricItem(rStack, aEnergy, Integer.MAX_VALUE, true, false);
        return GT_Utility.copyAmount(aAmount, rStack);
    }

    @Override
    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (GT_Utility.isStackInvalid(mStack)) return GT_Utility.copyAmount(aAmount, aReplacements);
        return GT_Utility.copyAmountAndMetaData(aAmount, aMetaValue, GT_OreDictUnificator.get(mStack));
    }

    @Override
    public IItemContainer registerOre(Object... aOreNames) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) GT_OreDictUnificator.registerOre(tOreName, get(1));
        return this;
    }

    @Override
    public IItemContainer registerWildcardAsOre(Object... aOreNames) {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        for (Object tOreName : aOreNames) GT_OreDictUnificator.registerOre(tOreName, getWildcard(1));
        return this;
    }
}