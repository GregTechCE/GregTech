package gregtech.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTLog;
import gregtech.common.metatileentities.electric.*;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityEnergyHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOven;
import gregtech.common.metatileentities.multi.MetaTileEntityCokeOvenHatch;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler.BoilerType;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;
import gregtech.common.metatileentities.multi.electric.*;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityDieselEngine;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine;
import gregtech.common.metatileentities.multi.electric.generator.MetaTileEntityLargeTurbine.TurbineType;
import gregtech.common.metatileentities.steam.*;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import gregtech.common.metatileentities.storage.MetaTileEntityChest;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumChest;
import gregtech.common.metatileentities.storage.MetaTileEntityQuantumTank;
import gregtech.common.metatileentities.storage.MetaTileEntityTank;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("WeakerAccess")
public class MetaTileEntities {

    //HULLS
    public static MetaTileEntityHull[] HULL = new MetaTileEntityHull[GTValues.V.length];
    public static MetaTileEntityTransformer[] TRANSFORMER = new MetaTileEntityTransformer[GTValues.V.length - 1];
    public static MetaTileEntityBatteryBuffer[][] BATTERY_BUFFER = new MetaTileEntityBatteryBuffer[GTValues.V.length][];
    public static MetaTileEntityCharger[] CHARGER = new MetaTileEntityCharger[GTValues.V.length];

    //BRONZE MACHINES SECTION
    public static SteamCoalBoiler STEAM_BOILER_COAL_BRONZE;
    public static SteamCoalBoiler STEAM_BOILER_COAL_STEEL;
    public static SteamSolarBoiler STEAM_BOILER_SOLAR_BRONZE;
    public static SteamLavaBoiler STEAM_BOILER_LAVA_BRONZE;
    public static SteamLavaBoiler STEAM_BOILER_LAVA_STEEL;
    public static SteamExtractor STEAM_EXTRACTOR_BRONZE;
    public static SteamExtractor STEAM_EXTRACTOR_STEEL;
    public static SteamMacerator STEAM_MACERATOR_BRONZE;
    public static SteamMacerator STEAM_MACERATOR_STEEL;
    public static SteamCompressor STEAM_COMPRESSOR_BRONZE;
    public static SteamCompressor STEAM_COMPRESSOR_STEEL;
    public static SteamHammer STEAM_HAMMER_BRONZE;
    public static SteamHammer STEAM_HAMMER_STEEL;
    public static SteamFurnace STEAM_FURNACE_BRONZE;
    public static SteamFurnace STEAM_FURNACE_STEEL;
    public static SteamAlloySmelter STEAM_ALLOY_SMELTER_BRONZE;
    public static SteamAlloySmelter STEAM_ALLOY_SMELTER_STEEL;

    //SIMPLE MACHINES SECTION
    public static SimpleMachineMetaTileEntity[] ELECTRIC_FURNACE = new SimpleMachineMetaTileEntity[4];
    public static MetaTileEntityMacerator[] MACERATOR = new MetaTileEntityMacerator[4];
    public static SimpleMachineMetaTileEntity[] ALLOY_SMELTER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] AMPLIFABRICATOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] ARC_FURNACE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] ASSEMBLER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] AUTOCLAVE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] BENDER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] BREWERY = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] CANNER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] CENTRIFUGE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_BATH = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_REACTOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] COMPRESSOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] CUTTER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] DISTILLERY = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] ELECTROLYZER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] ELECTROMAGNETIC_SEPARATOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] EXTRACTOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] EXTRUDER = new SimpleMachineMetaTileEntity[3];
    public static SimpleMachineMetaTileEntity[] FERMENTER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FLUID_HEATER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FLUID_SOLIDIFIER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FORGE_HAMMER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] FORMING_PRESS = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] LATHE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] MICROWAVE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] MIXER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] ORE_WASHER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] PACKER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] UNPACKER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] PLASMA_ARC_FURNACE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] POLARIZER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] LASER_ENGRAVER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] SIFTER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] THERMAL_CENTRIFUGE = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] WIREMILL = new SimpleMachineMetaTileEntity[4];

    //GENERATORS SECTION
    public static SimpleGeneratorMetaTileEntity[] DIESEL_GENERATOR = new SimpleGeneratorMetaTileEntity[4];
    public static SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[4];
    public static MetaTileEntityMagicEnergyAbsorber MAGIC_ENERGY_ABSORBER;

    //MULTIBLOCK PARTS SECTION
    public static MetaTileEntityItemBus[] ITEM_IMPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityItemBus[] ITEM_EXPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_IMPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_EXPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_INPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_OUTPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[3]; //HV, LuV, MAX
    public static MetaTileEntityCokeOvenHatch COKE_OVEN_HATCH;

    //MULTIBLOCKS SECTION
    public static MetaTileEntityPrimitiveBlastFurnace PRIMITIVE_BLAST_FURNACE;
    public static MetaTileEntityCokeOven COKE_OVEN;
    public static MetaTileEntityElectricBlastFurnace ELECTRIC_BLAST_FURNACE;
    public static MetaTileEntityVacuumFreezer VACUUM_FREEZER;
    public static MetaTileEntityImplosionCompressor IMPLOSION_COMPRESSOR;
    public static MetaTileEntityPyrolyseOven PYROLYSE_OVEN;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
    public static MetaTileEntityCrackingUnit CRACKER;
    public static MetaTileEntityMultiFurnace MULTI_FURNACE;
    public static MetaTileEntityDieselEngine DIESEL_ENGINE;

    public static MetaTileEntityLargeTurbine LARGE_STEAM_TURBINE;
    public static MetaTileEntityLargeTurbine LARGE_GAS_TURBINE;
    public static MetaTileEntityLargeTurbine LARGE_PLASMA_TURBINE;

    public static MetaTileEntityLargeBoiler LARGE_BRONZE_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_STEEL_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_TITANIUM_BOILER;
    public static MetaTileEntityLargeBoiler LARGE_TUNGSTENSTEEL_BOILER;

    //STORAGE SECTION
    public static MetaTileEntityChest BRONZE_CHEST;
    public static MetaTileEntityChest STEEL_CHEST;
    public static MetaTileEntityChest STAINLESS_STEEL_CHEST;
    public static MetaTileEntityChest TITANIUM_CHEST;
    public static MetaTileEntityChest TUNGSTENSTEEL_CHEST;

    public static MetaTileEntityTank WOODEN_TANK;
    public static MetaTileEntityTank BRONZE_TANK;
    public static MetaTileEntityTank STEEL_TANK;
    public static MetaTileEntityTank STAINLESS_STEEL_TANK;
    public static MetaTileEntityTank TITANIUM_TANK;
    public static MetaTileEntityTank TUNGSTENSTEEL_TANK;

    //MISC MACHINES SECTION
    public static MetaTileEntityPump[] PUMP = new MetaTileEntityPump[4];
    public static MetaTileEntityAirCollector[] AIR_COLLECTOR = new MetaTileEntityAirCollector[4];
    public static MetaTileEntityItemCollector[] ITEM_COLLECTOR = new MetaTileEntityItemCollector[4];
    public static MetaTileEntityTeslaCoil TESLA_COIL;
    public static MetaTileEntityQuantumChest[] QUANTUM_CHEST = new MetaTileEntityQuantumChest[4];
    public static MetaTileEntityQuantumTank[] QUANTUM_TANK = new MetaTileEntityQuantumTank[4];

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        STEAM_BOILER_COAL_BRONZE = GregTechAPI.registerMetaTileEntity(1, new SteamCoalBoiler(gregtechId("steam_boiler_coal_bronze"), false));
        STEAM_BOILER_COAL_STEEL = GregTechAPI.registerMetaTileEntity(2, new SteamCoalBoiler(gregtechId("steam_boiler_coal_steel"), true));

        STEAM_BOILER_SOLAR_BRONZE = GregTechAPI.registerMetaTileEntity(3, new SteamSolarBoiler(gregtechId("steam_boiler_solar_bronze"), false));

        STEAM_BOILER_LAVA_BRONZE = GregTechAPI.registerMetaTileEntity(5, new SteamLavaBoiler(gregtechId("steam_boiler_lava_bronze"), false));
        STEAM_BOILER_LAVA_STEEL = GregTechAPI.registerMetaTileEntity(6, new SteamLavaBoiler(gregtechId("steam_boiler_lava_steel"), true));

        STEAM_EXTRACTOR_BRONZE = GregTechAPI.registerMetaTileEntity(7, new SteamExtractor(gregtechId("steam_extractor_bronze"), false));
        STEAM_EXTRACTOR_STEEL = GregTechAPI.registerMetaTileEntity(8, new SteamExtractor(gregtechId("steam_extractor_steel"), true));

        STEAM_MACERATOR_BRONZE = GregTechAPI.registerMetaTileEntity(9, new SteamMacerator(gregtechId("steam_macerator_bronze"), false));
        STEAM_MACERATOR_STEEL = GregTechAPI.registerMetaTileEntity(10, new SteamMacerator(gregtechId("steam_macerator_steel"), true));

        STEAM_COMPRESSOR_BRONZE = GregTechAPI.registerMetaTileEntity(11, new SteamCompressor(gregtechId("steam_compressor_bronze"), false));
        STEAM_COMPRESSOR_STEEL = GregTechAPI.registerMetaTileEntity(12, new SteamCompressor(gregtechId("steam_compressor_steel"), true));

        STEAM_HAMMER_BRONZE = GregTechAPI.registerMetaTileEntity(13, new SteamHammer(gregtechId("steam_hammer_bronze"), false));
        STEAM_HAMMER_STEEL = GregTechAPI.registerMetaTileEntity(14, new SteamHammer(gregtechId("steam_hammer_steel"), true));

        STEAM_FURNACE_BRONZE = GregTechAPI.registerMetaTileEntity(15, new SteamFurnace(gregtechId("steam_furnace_bronze"), false));
        STEAM_FURNACE_STEEL = GregTechAPI.registerMetaTileEntity(16, new SteamFurnace(gregtechId("steam_furnace_steel"), true));

        STEAM_ALLOY_SMELTER_BRONZE = GregTechAPI.registerMetaTileEntity(17, new SteamAlloySmelter(gregtechId("steam_alloy_smelter_bronze"), false));
        STEAM_ALLOY_SMELTER_STEEL = GregTechAPI.registerMetaTileEntity(18, new SteamAlloySmelter(gregtechId("steam_alloy_smelter_steel"), true));

        ELECTRIC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(50, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.lv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 1));
        ELECTRIC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(51, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.mv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 2));
        ELECTRIC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(52, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.hv"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 3));
        ELECTRIC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(53, new SimpleMachineMetaTileEntity(gregtechId("electric_furnace.ev"), RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 4));

        MACERATOR[0] = GregTechAPI.registerMetaTileEntity(60, new MetaTileEntityMacerator(gregtechId("macerator.lv"), RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 1));
        MACERATOR[1] = GregTechAPI.registerMetaTileEntity(61, new MetaTileEntityMacerator(gregtechId("macerator.mv"), RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 2));
        MACERATOR[2] = GregTechAPI.registerMetaTileEntity(62, new MetaTileEntityMacerator(gregtechId("macerator.hv"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 3));
        MACERATOR[3] = GregTechAPI.registerMetaTileEntity(63, new MetaTileEntityMacerator(gregtechId("macerator.ev"), RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 4));

        ALLOY_SMELTER[0] = GregTechAPI.registerMetaTileEntity(70, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.lv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        ALLOY_SMELTER[1] = GregTechAPI.registerMetaTileEntity(71, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.mv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        ALLOY_SMELTER[2] = GregTechAPI.registerMetaTileEntity(72, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.hv"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        ALLOY_SMELTER[3] = GregTechAPI.registerMetaTileEntity(73, new SimpleMachineMetaTileEntity(gregtechId("alloy_smelter.ev"), RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));

        AMPLIFABRICATOR[0] = GregTechAPI.registerMetaTileEntity(80, new SimpleMachineMetaTileEntity(gregtechId("amplifab.lv"), RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 1));
        AMPLIFABRICATOR[1] = GregTechAPI.registerMetaTileEntity(81, new SimpleMachineMetaTileEntity(gregtechId("amplifab.mv"), RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 2));
        AMPLIFABRICATOR[2] = GregTechAPI.registerMetaTileEntity(82, new SimpleMachineMetaTileEntity(gregtechId("amplifab.hv"), RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 3));
        AMPLIFABRICATOR[3] = GregTechAPI.registerMetaTileEntity(83, new SimpleMachineMetaTileEntity(gregtechId("amplifab.ev"), RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 4));

        ARC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(90, new SimpleMachineMetaTileEntity(gregtechId("arc_furnace.lv"), RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 1, false));
        ARC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(91, new SimpleMachineMetaTileEntity(gregtechId("arc_furnace.mv"), RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 2, false));
        ARC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(92, new SimpleMachineMetaTileEntity(gregtechId("arc_furnace.hv"), RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 3, false));
        ARC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(93, new SimpleMachineMetaTileEntity(gregtechId("arc_furnace.ev"), RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 4, false));

        ASSEMBLER[0] = GregTechAPI.registerMetaTileEntity(100, new SimpleMachineMetaTileEntity(gregtechId("assembler.lv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 1));
        ASSEMBLER[1] = GregTechAPI.registerMetaTileEntity(101, new SimpleMachineMetaTileEntity(gregtechId("assembler.mv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 2));
        ASSEMBLER[2] = GregTechAPI.registerMetaTileEntity(102, new SimpleMachineMetaTileEntity(gregtechId("assembler.hv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 3));
        ASSEMBLER[3] = GregTechAPI.registerMetaTileEntity(103, new SimpleMachineMetaTileEntity(gregtechId("assembler.ev"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 4));
        ASSEMBLER[4] = GregTechAPI.registerMetaTileEntity(104, new SimpleMachineMetaTileEntity(gregtechId("assembler.iv"), RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 5));

        AUTOCLAVE[0] = GregTechAPI.registerMetaTileEntity(110, new SimpleMachineMetaTileEntity(gregtechId("autoclave.lv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1, false));
        AUTOCLAVE[1] = GregTechAPI.registerMetaTileEntity(111, new SimpleMachineMetaTileEntity(gregtechId("autoclave.mv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2, false));
        AUTOCLAVE[2] = GregTechAPI.registerMetaTileEntity(112, new SimpleMachineMetaTileEntity(gregtechId("autoclave.hv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3, false));
        AUTOCLAVE[3] = GregTechAPI.registerMetaTileEntity(113, new SimpleMachineMetaTileEntity(gregtechId("autoclave.ev"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 4, false));
        AUTOCLAVE[4] = GregTechAPI.registerMetaTileEntity(114, new SimpleMachineMetaTileEntity(gregtechId("autoclave.iv"), RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 5, false));

        BENDER[0] = GregTechAPI.registerMetaTileEntity(120, new SimpleMachineMetaTileEntity(gregtechId("bender.lv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 1));
        BENDER[1] = GregTechAPI.registerMetaTileEntity(121, new SimpleMachineMetaTileEntity(gregtechId("bender.mv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 2));
        BENDER[2] = GregTechAPI.registerMetaTileEntity(122, new SimpleMachineMetaTileEntity(gregtechId("bender.hv"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 3));
        BENDER[3] = GregTechAPI.registerMetaTileEntity(123, new SimpleMachineMetaTileEntity(gregtechId("bender.ev"), RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 4));

        BREWERY[0] = GregTechAPI.registerMetaTileEntity(130, new SimpleMachineMetaTileEntity(gregtechId("brewery.lv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 1));
        BREWERY[1] = GregTechAPI.registerMetaTileEntity(131, new SimpleMachineMetaTileEntity(gregtechId("brewery.mv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 2));
        BREWERY[2] = GregTechAPI.registerMetaTileEntity(132, new SimpleMachineMetaTileEntity(gregtechId("brewery.hv"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 3));
        BREWERY[3] = GregTechAPI.registerMetaTileEntity(133, new SimpleMachineMetaTileEntity(gregtechId("brewery.ev"), RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 4));

        CANNER[0] = GregTechAPI.registerMetaTileEntity(140, new SimpleMachineMetaTileEntity(gregtechId("canner.lv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 1));
        CANNER[1] = GregTechAPI.registerMetaTileEntity(141, new SimpleMachineMetaTileEntity(gregtechId("canner.mv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 2));
        CANNER[2] = GregTechAPI.registerMetaTileEntity(142, new SimpleMachineMetaTileEntity(gregtechId("canner.hv"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 3));
        CANNER[3] = GregTechAPI.registerMetaTileEntity(143, new SimpleMachineMetaTileEntity(gregtechId("canner.ev"), RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 4));

        CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(150, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.lv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 1, false));
        CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(151, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.mv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 2, false));
        CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(152, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.hv"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 3, false));
        CENTRIFUGE[3] = GregTechAPI.registerMetaTileEntity(153, new SimpleMachineMetaTileEntity(gregtechId("centrifuge.ev"), RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 4, false));

        CHEMICAL_BATH[0] = GregTechAPI.registerMetaTileEntity(180, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.lv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 1));
        CHEMICAL_BATH[1] = GregTechAPI.registerMetaTileEntity(181, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.mv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 2));
        CHEMICAL_BATH[2] = GregTechAPI.registerMetaTileEntity(182, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.hv"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 3));
        CHEMICAL_BATH[3] = GregTechAPI.registerMetaTileEntity(183, new SimpleMachineMetaTileEntity(gregtechId("chemical_bath.ev"), RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 4));

        CHEMICAL_REACTOR[0] = GregTechAPI.registerMetaTileEntity(190, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.lv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 1));
        CHEMICAL_REACTOR[1] = GregTechAPI.registerMetaTileEntity(191, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.mv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2));
        CHEMICAL_REACTOR[2] = GregTechAPI.registerMetaTileEntity(192, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.hv"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3));
        CHEMICAL_REACTOR[3] = GregTechAPI.registerMetaTileEntity(193, new SimpleMachineMetaTileEntity(gregtechId("chemical_reactor.ev"), RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4));

        COMPRESSOR[0] = GregTechAPI.registerMetaTileEntity(210, new SimpleMachineMetaTileEntity(gregtechId("compressor.lv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 1));
        COMPRESSOR[1] = GregTechAPI.registerMetaTileEntity(211, new SimpleMachineMetaTileEntity(gregtechId("compressor.mv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 2));
        COMPRESSOR[2] = GregTechAPI.registerMetaTileEntity(212, new SimpleMachineMetaTileEntity(gregtechId("compressor.hv"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 3));
        COMPRESSOR[3] = GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity(gregtechId("compressor.ev"), RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 4));

        CUTTER[0] = GregTechAPI.registerMetaTileEntity(220, new SimpleMachineMetaTileEntity(gregtechId("cutter.lv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 1));
        CUTTER[1] = GregTechAPI.registerMetaTileEntity(221, new SimpleMachineMetaTileEntity(gregtechId("cutter.mv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 2));
        CUTTER[2] = GregTechAPI.registerMetaTileEntity(222, new SimpleMachineMetaTileEntity(gregtechId("cutter.hv"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 3));
        CUTTER[3] = GregTechAPI.registerMetaTileEntity(223, new SimpleMachineMetaTileEntity(gregtechId("cutter.ev"), RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 4));

        DISTILLERY[0] = GregTechAPI.registerMetaTileEntity(230, new SimpleMachineMetaTileEntity(gregtechId("distillery.lv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 1));
        DISTILLERY[1] = GregTechAPI.registerMetaTileEntity(231, new SimpleMachineMetaTileEntity(gregtechId("distillery.mv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 2));
        DISTILLERY[2] = GregTechAPI.registerMetaTileEntity(232, new SimpleMachineMetaTileEntity(gregtechId("distillery.hv"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 3));
        DISTILLERY[3] = GregTechAPI.registerMetaTileEntity(233, new SimpleMachineMetaTileEntity(gregtechId("distillery.ev"), RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 4));

        ELECTROLYZER[0] = GregTechAPI.registerMetaTileEntity(240, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.lv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 1, false));
        ELECTROLYZER[1] = GregTechAPI.registerMetaTileEntity(241, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.mv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 2, false));
        ELECTROLYZER[2] = GregTechAPI.registerMetaTileEntity(242, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.hv"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 3, false));
        ELECTROLYZER[3] = GregTechAPI.registerMetaTileEntity(243, new SimpleMachineMetaTileEntity(gregtechId("electrolyzer.ev"), RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 4, false));

        ELECTROMAGNETIC_SEPARATOR[0] = GregTechAPI.registerMetaTileEntity(250, new SimpleMachineMetaTileEntity(gregtechId("electromagnetic_separator.lv"), RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 1));
        ELECTROMAGNETIC_SEPARATOR[1] = GregTechAPI.registerMetaTileEntity(251, new SimpleMachineMetaTileEntity(gregtechId("electromagnetic_separator.mv"), RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 2));
        ELECTROMAGNETIC_SEPARATOR[2] = GregTechAPI.registerMetaTileEntity(252, new SimpleMachineMetaTileEntity(gregtechId("electromagnetic_separator.hv"), RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 3));
        ELECTROMAGNETIC_SEPARATOR[3] = GregTechAPI.registerMetaTileEntity(253, new SimpleMachineMetaTileEntity(gregtechId("electromagnetic_separator.ev"), RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 4));

        EXTRACTOR[0] = GregTechAPI.registerMetaTileEntity(260, new SimpleMachineMetaTileEntity(gregtechId("extractor.lv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 1));
        EXTRACTOR[1] = GregTechAPI.registerMetaTileEntity(261, new SimpleMachineMetaTileEntity(gregtechId("extractor.mv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 2));
        EXTRACTOR[2] = GregTechAPI.registerMetaTileEntity(262, new SimpleMachineMetaTileEntity(gregtechId("extractor.hv"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 3));
        EXTRACTOR[3] = GregTechAPI.registerMetaTileEntity(263, new SimpleMachineMetaTileEntity(gregtechId("extractor.ev"), RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 4));

        EXTRUDER[0] = GregTechAPI.registerMetaTileEntity(271, new SimpleMachineMetaTileEntity(gregtechId("extruder.mv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 2));
        EXTRUDER[1] = GregTechAPI.registerMetaTileEntity(272, new SimpleMachineMetaTileEntity(gregtechId("extruder.hv"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 3));
        EXTRUDER[2] = GregTechAPI.registerMetaTileEntity(273, new SimpleMachineMetaTileEntity(gregtechId("extruder.ev"), RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 4));

        FERMENTER[0] = GregTechAPI.registerMetaTileEntity(280, new SimpleMachineMetaTileEntity(gregtechId("fermenter.lv"), RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 1));
        FERMENTER[1] = GregTechAPI.registerMetaTileEntity(281, new SimpleMachineMetaTileEntity(gregtechId("fermenter.mv"), RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 2));
        FERMENTER[2] = GregTechAPI.registerMetaTileEntity(282, new SimpleMachineMetaTileEntity(gregtechId("fermenter.hv"), RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 3));
        FERMENTER[3] = GregTechAPI.registerMetaTileEntity(283, new SimpleMachineMetaTileEntity(gregtechId("fermenter.ev"), RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 4));

        FLUID_CANNER[0] = GregTechAPI.registerMetaTileEntity(290, new SimpleMachineMetaTileEntity(gregtechId("fluid_canner.lv"), RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 1));
        FLUID_CANNER[1] = GregTechAPI.registerMetaTileEntity(291, new SimpleMachineMetaTileEntity(gregtechId("fluid_canner.mv"), RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 2));
        FLUID_CANNER[2] = GregTechAPI.registerMetaTileEntity(292, new SimpleMachineMetaTileEntity(gregtechId("fluid_canner.hv"), RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 3));
        FLUID_CANNER[3] = GregTechAPI.registerMetaTileEntity(293, new SimpleMachineMetaTileEntity(gregtechId("fluid_canner.ev"), RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 4));

        FLUID_EXTRACTOR[0] = GregTechAPI.registerMetaTileEntity(300, new SimpleMachineMetaTileEntity(gregtechId("fluid_extractor.lv"), RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 1));
        FLUID_EXTRACTOR[1] = GregTechAPI.registerMetaTileEntity(301, new SimpleMachineMetaTileEntity(gregtechId("fluid_extractor.mv"), RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 2));
        FLUID_EXTRACTOR[2] = GregTechAPI.registerMetaTileEntity(302, new SimpleMachineMetaTileEntity(gregtechId("fluid_extractor.hv"), RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 3));
        FLUID_EXTRACTOR[3] = GregTechAPI.registerMetaTileEntity(303, new SimpleMachineMetaTileEntity(gregtechId("fluid_extractor.ev"), RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 4));

        FLUID_HEATER[0] = GregTechAPI.registerMetaTileEntity(310, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.lv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 1));
        FLUID_HEATER[1] = GregTechAPI.registerMetaTileEntity(311, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.mv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 2));
        FLUID_HEATER[2] = GregTechAPI.registerMetaTileEntity(312, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.hv"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 3));
        FLUID_HEATER[3] = GregTechAPI.registerMetaTileEntity(313, new SimpleMachineMetaTileEntity(gregtechId("fluid_heater.ev"), RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 4));

        FLUID_SOLIDIFIER[0] = GregTechAPI.registerMetaTileEntity(320, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.lv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 1));
        FLUID_SOLIDIFIER[1] = GregTechAPI.registerMetaTileEntity(321, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.mv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 2));
        FLUID_SOLIDIFIER[2] = GregTechAPI.registerMetaTileEntity(322, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.hv"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 3));
        FLUID_SOLIDIFIER[3] = GregTechAPI.registerMetaTileEntity(323, new SimpleMachineMetaTileEntity(gregtechId("fluid_solidifier.ev"), RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 4));

        FORGE_HAMMER[0] = GregTechAPI.registerMetaTileEntity(330, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.lv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 1));
        FORGE_HAMMER[1] = GregTechAPI.registerMetaTileEntity(331, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.mv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 2));
        FORGE_HAMMER[2] = GregTechAPI.registerMetaTileEntity(332, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.hv"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 3));
        FORGE_HAMMER[3] = GregTechAPI.registerMetaTileEntity(333, new SimpleMachineMetaTileEntity(gregtechId("forge_hammer.ev"), RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 4));

        FORMING_PRESS[0] = GregTechAPI.registerMetaTileEntity(340, new SimpleMachineMetaTileEntity(gregtechId("forming_press.lv"), RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 1));
        FORMING_PRESS[1] = GregTechAPI.registerMetaTileEntity(341, new SimpleMachineMetaTileEntity(gregtechId("forming_press.mv"), RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 2));
        FORMING_PRESS[2] = GregTechAPI.registerMetaTileEntity(342, new SimpleMachineMetaTileEntity(gregtechId("forming_press.hv"), RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 3));
        FORMING_PRESS[3] = GregTechAPI.registerMetaTileEntity(343, new SimpleMachineMetaTileEntity(gregtechId("forming_press.ev"), RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 4));

        LATHE[0] = GregTechAPI.registerMetaTileEntity(350, new SimpleMachineMetaTileEntity(gregtechId("lathe.lv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 1));
        LATHE[1] = GregTechAPI.registerMetaTileEntity(351, new SimpleMachineMetaTileEntity(gregtechId("lathe.mv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 2));
        LATHE[2] = GregTechAPI.registerMetaTileEntity(352, new SimpleMachineMetaTileEntity(gregtechId("lathe.hv"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 3));
        LATHE[3] = GregTechAPI.registerMetaTileEntity(353, new SimpleMachineMetaTileEntity(gregtechId("lathe.ev"), RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 4));

        MICROWAVE[0] = GregTechAPI.registerMetaTileEntity(360, new SimpleMachineMetaTileEntity(gregtechId("microwave.lv"), RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 1));
        MICROWAVE[1] = GregTechAPI.registerMetaTileEntity(361, new SimpleMachineMetaTileEntity(gregtechId("microwave.mv"), RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 2));
        MICROWAVE[2] = GregTechAPI.registerMetaTileEntity(362, new SimpleMachineMetaTileEntity(gregtechId("microwave.hv"), RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 3));
        MICROWAVE[3] = GregTechAPI.registerMetaTileEntity(363, new SimpleMachineMetaTileEntity(gregtechId("microwave.ev"), RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 4));

        MIXER[0] = GregTechAPI.registerMetaTileEntity(370, new SimpleMachineMetaTileEntity(gregtechId("mixer.lv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 1, false));
        MIXER[1] = GregTechAPI.registerMetaTileEntity(371, new SimpleMachineMetaTileEntity(gregtechId("mixer.mv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 2, false));
        MIXER[2] = GregTechAPI.registerMetaTileEntity(372, new SimpleMachineMetaTileEntity(gregtechId("mixer.hv"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 3, false));
        MIXER[3] = GregTechAPI.registerMetaTileEntity(373, new SimpleMachineMetaTileEntity(gregtechId("mixer.ev"), RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 4, false));

        ORE_WASHER[0] = GregTechAPI.registerMetaTileEntity(380, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.lv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 1));
        ORE_WASHER[1] = GregTechAPI.registerMetaTileEntity(381, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.mv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 2));
        ORE_WASHER[2] = GregTechAPI.registerMetaTileEntity(382, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.hv"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 3));
        ORE_WASHER[3] = GregTechAPI.registerMetaTileEntity(383, new SimpleMachineMetaTileEntity(gregtechId("ore_washer.ev"), RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 4));

        PACKER[0] = GregTechAPI.registerMetaTileEntity(390, new SimpleMachineMetaTileEntity(gregtechId("packer.lv"), RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 1));
        PACKER[1] = GregTechAPI.registerMetaTileEntity(391, new SimpleMachineMetaTileEntity(gregtechId("packer.mv"), RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 2));
        PACKER[2] = GregTechAPI.registerMetaTileEntity(392, new SimpleMachineMetaTileEntity(gregtechId("packer.hv"), RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 3));
        PACKER[3] = GregTechAPI.registerMetaTileEntity(393, new SimpleMachineMetaTileEntity(gregtechId("packer.ev"), RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 4));

        UNPACKER[0] = GregTechAPI.registerMetaTileEntity(400, new SimpleMachineMetaTileEntity(gregtechId("unpacker.lv"), RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 1));
        UNPACKER[1] = GregTechAPI.registerMetaTileEntity(401, new SimpleMachineMetaTileEntity(gregtechId("unpacker.mv"), RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 2));
        UNPACKER[2] = GregTechAPI.registerMetaTileEntity(402, new SimpleMachineMetaTileEntity(gregtechId("unpacker.hv"), RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 3));
        UNPACKER[3] = GregTechAPI.registerMetaTileEntity(403, new SimpleMachineMetaTileEntity(gregtechId("unpacker.ev"), RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 4));

        PLASMA_ARC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(410, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.lv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 1, false));
        PLASMA_ARC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(411, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.mv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 2, false));
        PLASMA_ARC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(412, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.hv"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 3, false));
        PLASMA_ARC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(413, new SimpleMachineMetaTileEntity(gregtechId("plasma_arc_furnace.ev"), RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 4, false));

        POLARIZER[0] = GregTechAPI.registerMetaTileEntity(420, new SimpleMachineMetaTileEntity(gregtechId("polarizer.lv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 1));
        POLARIZER[1] = GregTechAPI.registerMetaTileEntity(421, new SimpleMachineMetaTileEntity(gregtechId("polarizer.mv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 2));
        POLARIZER[2] = GregTechAPI.registerMetaTileEntity(422, new SimpleMachineMetaTileEntity(gregtechId("polarizer.hv"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 3));
        POLARIZER[3] = GregTechAPI.registerMetaTileEntity(423, new SimpleMachineMetaTileEntity(gregtechId("polarizer.ev"), RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 4));

        LASER_ENGRAVER[0] = GregTechAPI.registerMetaTileEntity(430, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.lv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 1));
        LASER_ENGRAVER[1] = GregTechAPI.registerMetaTileEntity(431, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.mv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 2));
        LASER_ENGRAVER[2] = GregTechAPI.registerMetaTileEntity(432, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.hv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 3));
        LASER_ENGRAVER[3] = GregTechAPI.registerMetaTileEntity(433, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.ev"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 4));
        LASER_ENGRAVER[4] = GregTechAPI.registerMetaTileEntity(434, new SimpleMachineMetaTileEntity(gregtechId("laser_engraver.iv"), RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 5));

        SIFTER[0] = GregTechAPI.registerMetaTileEntity(450, new SimpleMachineMetaTileEntity(gregtechId("sifter.lv"), RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 1));
        SIFTER[1] = GregTechAPI.registerMetaTileEntity(451, new SimpleMachineMetaTileEntity(gregtechId("sifter.mv"), RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 2));
        SIFTER[2] = GregTechAPI.registerMetaTileEntity(452, new SimpleMachineMetaTileEntity(gregtechId("sifter.hv"), RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 3));
        SIFTER[3] = GregTechAPI.registerMetaTileEntity(453, new SimpleMachineMetaTileEntity(gregtechId("sifter.ev"), RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 4));

        THERMAL_CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(460, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.lv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 1));
        THERMAL_CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(461, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.mv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 2));
        THERMAL_CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(462, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.hv"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 3));
        THERMAL_CENTRIFUGE[3] = GregTechAPI.registerMetaTileEntity(463, new SimpleMachineMetaTileEntity(gregtechId("thermal_centrifuge.ev"), RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 4));

        WIREMILL[0] = GregTechAPI.registerMetaTileEntity(470, new SimpleMachineMetaTileEntity(gregtechId("wiremill.lv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 1));
        WIREMILL[1] = GregTechAPI.registerMetaTileEntity(471, new SimpleMachineMetaTileEntity(gregtechId("wiremill.mv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 2));
        WIREMILL[2] = GregTechAPI.registerMetaTileEntity(472, new SimpleMachineMetaTileEntity(gregtechId("wiremill.hv"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 3));
        WIREMILL[3] = GregTechAPI.registerMetaTileEntity(473, new SimpleMachineMetaTileEntity(gregtechId("wiremill.ev"), RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 4));

        DIESEL_GENERATOR[0] = GregTechAPI.registerMetaTileEntity(480, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.lv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 1));
        DIESEL_GENERATOR[1] = GregTechAPI.registerMetaTileEntity(481, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.mv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 2));
        DIESEL_GENERATOR[2] = GregTechAPI.registerMetaTileEntity(482, new SimpleGeneratorMetaTileEntity(gregtechId("diesel_generator.hv"), RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 3));

        STEAM_TURBINE[0] = GregTechAPI.registerMetaTileEntity(485, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.lv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 1));
        STEAM_TURBINE[1] = GregTechAPI.registerMetaTileEntity(486, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.mv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 2));
        STEAM_TURBINE[2] = GregTechAPI.registerMetaTileEntity(487, new SimpleGeneratorMetaTileEntity(gregtechId("steam_turbine.hv"), RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 3));

        GAS_TURBINE[0] = GregTechAPI.registerMetaTileEntity(490, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.lv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 1));
        GAS_TURBINE[1] = GregTechAPI.registerMetaTileEntity(491, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.mv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 2));
        GAS_TURBINE[2] = GregTechAPI.registerMetaTileEntity(492, new SimpleGeneratorMetaTileEntity(gregtechId("gas_turbine.hv"), RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 3));

        MAGIC_ENERGY_ABSORBER = GregTechAPI.registerMetaTileEntity(493, new MetaTileEntityMagicEnergyAbsorber(gregtechId("magic_energy_absorber")));

        ITEM_COLLECTOR[0] = GregTechAPI.registerMetaTileEntity(494, new MetaTileEntityItemCollector(gregtechId("item_collector.lv"), 1, 8));
        ITEM_COLLECTOR[1] = GregTechAPI.registerMetaTileEntity(495, new MetaTileEntityItemCollector(gregtechId("item_collector.mv"), 2, 16));
        ITEM_COLLECTOR[2] = GregTechAPI.registerMetaTileEntity(496, new MetaTileEntityItemCollector(gregtechId("item_collector.hv"), 3, 32));
        ITEM_COLLECTOR[3] = GregTechAPI.registerMetaTileEntity(497, new MetaTileEntityItemCollector(gregtechId("item_collector.ev"), 4, 64));

        for (int i = 0; i < HULL.length; i++) {
            MetaTileEntityHull metaTileEntity = new MetaTileEntityHull(gregtechId("hull." + GTValues.VN[i].toLowerCase()), i);
            GregTechAPI.registerMetaTileEntity(500 + i, metaTileEntity);
            HULL[i] = metaTileEntity;
        }

        PRIMITIVE_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(510, new MetaTileEntityPrimitiveBlastFurnace(gregtechId("primitive_blast_furnace.bronze")));
        ELECTRIC_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(511, new MetaTileEntityElectricBlastFurnace(gregtechId("electric_blast_furnace")));
        VACUUM_FREEZER = GregTechAPI.registerMetaTileEntity(512, new MetaTileEntityVacuumFreezer(gregtechId("vacuum_freezer")));
        IMPLOSION_COMPRESSOR = GregTechAPI.registerMetaTileEntity(513, new MetaTileEntityImplosionCompressor(gregtechId("implosion_compressor")));
        PYROLYSE_OVEN = GregTechAPI.registerMetaTileEntity(514, new MetaTileEntityPyrolyseOven(gregtechId("pyrolyse_oven")));
        DISTILLATION_TOWER = GregTechAPI.registerMetaTileEntity(515, new MetaTileEntityDistillationTower(gregtechId("distillation_tower")));
        MULTI_FURNACE = GregTechAPI.registerMetaTileEntity(516, new MetaTileEntityMultiFurnace(gregtechId("multi_furnace")));
        DIESEL_ENGINE = GregTechAPI.registerMetaTileEntity(517, new MetaTileEntityDieselEngine(gregtechId("diesel_engine")));
        CRACKER = GregTechAPI.registerMetaTileEntity(525, new MetaTileEntityCrackingUnit(gregtechId("cracker")));

        LARGE_STEAM_TURBINE = GregTechAPI.registerMetaTileEntity(518, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.steam"), TurbineType.STEAM));
        LARGE_GAS_TURBINE = GregTechAPI.registerMetaTileEntity(519, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.gas"), TurbineType.GAS));
        LARGE_PLASMA_TURBINE = GregTechAPI.registerMetaTileEntity(520, new MetaTileEntityLargeTurbine(gregtechId("large_turbine.plasma"), TurbineType.PLASMA));

        LARGE_BRONZE_BOILER = GregTechAPI.registerMetaTileEntity(521, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.bronze"), BoilerType.BRONZE));
        LARGE_STEEL_BOILER = GregTechAPI.registerMetaTileEntity(522, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.steel"), BoilerType.STEEL));
        LARGE_TITANIUM_BOILER = GregTechAPI.registerMetaTileEntity(523, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.titanium"), BoilerType.TITANIUM));
        LARGE_TUNGSTENSTEEL_BOILER = GregTechAPI.registerMetaTileEntity(524, new MetaTileEntityLargeBoiler(gregtechId("large_boiler.tungstensteel"), BoilerType.TUNGSTENSTEEL));

        COKE_OVEN = GregTechAPI.registerMetaTileEntity(526, new MetaTileEntityCokeOven(gregtechId("coke_oven")));
        COKE_OVEN_HATCH = GregTechAPI.registerMetaTileEntity(527, new MetaTileEntityCokeOvenHatch(gregtechId("coke_oven_hatch")));

        int[] batteryBufferSlots = new int[]{1, 4, 9, 16};
        for (int i = 0; i < GTValues.V.length; i++) {
            if (i > 0) {
                MetaTileEntityTransformer transformer = new MetaTileEntityTransformer(gregtechId("transformer." + GTValues.VN[i].toLowerCase()), i);
                TRANSFORMER[i - 1] = GregTechAPI.registerMetaTileEntity(600 + (i - 1), transformer);
            }
            BATTERY_BUFFER[i] = new MetaTileEntityBatteryBuffer[batteryBufferSlots.length];
            for (int slot = 0; slot < batteryBufferSlots.length; slot++) {
                String transformerId = "battery_buffer." + GTValues.VN[i].toLowerCase() + "." + batteryBufferSlots[slot];
                MetaTileEntityBatteryBuffer batteryBuffer = new MetaTileEntityBatteryBuffer(gregtechId(transformerId), i, batteryBufferSlots[slot]);
                BATTERY_BUFFER[i][slot] = GregTechAPI.registerMetaTileEntity(610 + batteryBufferSlots.length * i + slot, batteryBuffer);
            }
            String chargerId = "charger." + GTValues.VN[i].toLowerCase();
            MetaTileEntityCharger charger = new MetaTileEntityCharger(gregtechId(chargerId), i, 4);
            CHARGER[i] = GregTechAPI.registerMetaTileEntity(680 + i, charger);
        }

        for (int i = 0; i < GTValues.V.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            ITEM_IMPORT_BUS[i] = new MetaTileEntityItemBus(gregtechId("item_bus.import." + voltageName), i, false);
            ITEM_EXPORT_BUS[i] = new MetaTileEntityItemBus(gregtechId("item_bus.export." + voltageName), i, true);
            FLUID_IMPORT_HATCH[i] = new MetaTileEntityFluidHatch(gregtechId("fluid_hatch.import." + voltageName), i, false);
            FLUID_EXPORT_HATCH[i] = new MetaTileEntityFluidHatch(gregtechId("fluid_hatch.export." + voltageName), i, true);
            ENERGY_INPUT_HATCH[i] = new MetaTileEntityEnergyHatch(gregtechId("energy_hatch.input." + voltageName), i, false);
            ENERGY_OUTPUT_HATCH[i] = new MetaTileEntityEnergyHatch(gregtechId("energy_hatch.output." + voltageName), i, true);

            GregTechAPI.registerMetaTileEntity(700 + 10 * i, ITEM_IMPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 1, ITEM_EXPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 2, FLUID_IMPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 3, FLUID_EXPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 4, ENERGY_INPUT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 5, ENERGY_OUTPUT_HATCH[i]);
        }

        ROTOR_HOLDER[0] = GregTechAPI.registerMetaTileEntity(817, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.hv"), GTValues.HV, 1.0f));
        ROTOR_HOLDER[1] = GregTechAPI.registerMetaTileEntity(818, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.luv"), GTValues.LuV, 1.15f));
        ROTOR_HOLDER[2] = GregTechAPI.registerMetaTileEntity(819, new MetaTileEntityRotorHolder(gregtechId("rotor_holder.max"), GTValues.MAX, 1.25f));

        BRONZE_CHEST = GregTechAPI.registerMetaTileEntity(802, new MetaTileEntityChest(gregtechId("bronze_chest"), Materials.Bronze, 9, 6));
        STEEL_CHEST = GregTechAPI.registerMetaTileEntity(803, new MetaTileEntityChest(gregtechId("steel_chest"), Materials.Steel, 9, 8));
        STAINLESS_STEEL_CHEST = GregTechAPI.registerMetaTileEntity(804, new MetaTileEntityChest(gregtechId("stainless_steel_chest"), Materials.StainlessSteel, 9, 10));
        TITANIUM_CHEST = GregTechAPI.registerMetaTileEntity(805, new MetaTileEntityChest(gregtechId("titanium_chest"), Materials.Titanium, 12, 10));
        TUNGSTENSTEEL_CHEST = GregTechAPI.registerMetaTileEntity(806, new MetaTileEntityChest(gregtechId("tungstensteel_chest"), Materials.TungstenSteel, 12, 14));

        WOODEN_TANK = GregTechAPI.registerMetaTileEntity(811, new MetaTileEntityTank(gregtechId("wooden_tank"), Materials.Wood, 4000));
        BRONZE_TANK = GregTechAPI.registerMetaTileEntity(812, new MetaTileEntityTank(gregtechId("bronze_tank"), Materials.Bronze, 8000));
        STEEL_TANK = GregTechAPI.registerMetaTileEntity(813, new MetaTileEntityTank(gregtechId("steel_tank"), Materials.Steel, 16000));
        STAINLESS_STEEL_TANK = GregTechAPI.registerMetaTileEntity(814, new MetaTileEntityTank(gregtechId("stainless_steel_tank"), Materials.StainlessSteel, 32000));
        TITANIUM_TANK = GregTechAPI.registerMetaTileEntity(815, new MetaTileEntityTank(gregtechId("titanium_tank"), Materials.Titanium, 48000));
        TUNGSTENSTEEL_TANK = GregTechAPI.registerMetaTileEntity(816, new MetaTileEntityTank(gregtechId("tungstensteel_tank"), Materials.TungstenSteel, 64000));

        for (int i = 1; i < 5; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            PUMP[i - 1] = new MetaTileEntityPump(gregtechId("pump." + voltageName), i);
            AIR_COLLECTOR[i - 1] = new MetaTileEntityAirCollector(gregtechId("air_collector." + voltageName), i);
            GregTechAPI.registerMetaTileEntity(900 + 10 * (i - 1), PUMP[i - 1]);
            GregTechAPI.registerMetaTileEntity(950 + 10 * (i - 1), AIR_COLLECTOR[i - 1]);
        }

        TESLA_COIL = new MetaTileEntityTeslaCoil(gregtechId("tesla_coil"));
        GregTechAPI.registerMetaTileEntity(1001, TESLA_COIL);

        for (int i = 2; i < 6; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            QUANTUM_CHEST[i - 2] = new MetaTileEntityQuantumChest(gregtechId("quantum_chest." + voltageName), i, 64 * 64000 * (i - 1));
            QUANTUM_TANK[i - 2] = new MetaTileEntityQuantumTank(gregtechId("quantum_tank." + voltageName), i, 1000 * 64000 * (i - 1));
            GregTechAPI.registerMetaTileEntity(1010 + (i - 2), QUANTUM_CHEST[i - 2]);
            GregTechAPI.registerMetaTileEntity(1020 + (i - 2), QUANTUM_TANK[i - 2]);
        }
    }

    private static ResourceLocation gregtechId(String name) {
        return new ResourceLocation(GTValues.MODID, name);
    }

}
