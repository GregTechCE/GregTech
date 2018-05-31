package gregtech.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Materials;
import gregtech.common.cable.tile.TileEntityCable;
import gregtech.common.metatileentities.electric.*;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.util.GTLog;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityEnergyHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityFluidHatch;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityItemBus;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityRotorHolder;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler;
import gregtech.common.metatileentities.multi.MetaTileEntityLargeBoiler.BoilerType;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;
import gregtech.common.metatileentities.multi.electric.*;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityLargeTurbine.TurbineType;
import gregtech.common.metatileentities.steam.*;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import gregtech.common.metatileentities.storage.MetaTileEntityChest;
import gregtech.common.metatileentities.storage.MetaTileEntityTank;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
    public static SimpleMachineMetaTileEntity[] ALLOY_SMELTER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] AMPLIFABRICATOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ARC_FURNACE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ASSEMBLER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] AUTOCLAVE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] BENDER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] BREWERY = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] CANNER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] CENTRIFUGE = new SimpleMachineMetaTileEntity[3];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_BATH = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] CHEMICAL_REACTOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] COMPRESSOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] CUTTER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] DISTILLERY = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTROLYZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ELECTROMAGNETIC_SEPARATOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] EXTRACTOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] EXTRUDER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FERMENTER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FLUID_CANNER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FLUID_EXTRACTOR = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FLUID_HEATER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FLUID_SOLIDIFIER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FORGE_HAMMER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] FORMING_PRESS = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] LATHE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] MICROWAVE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] MIXER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] ORE_WASHER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] PACKER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] UNPACKER = new SimpleMachineMetaTileEntity[4];
    public static SimpleMachineMetaTileEntity[] PLASMA_ARC_FURNACE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] POLARIZER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] LASER_ENGRAVER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] PRINTER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] SIFTER = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] THERMAL_CENTRIFUGE = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] WIREMILL = new SimpleMachineMetaTileEntity[5];

    //GENERATORS SECTION
    public static SimpleGeneratorMetaTileEntity[] DIESEL_GENERATOR = new SimpleGeneratorMetaTileEntity[3];
    public static SimpleGeneratorMetaTileEntity[] STEAM_TURBINE = new SimpleGeneratorMetaTileEntity[3];
    public static SimpleGeneratorMetaTileEntity[] GAS_TURBINE = new SimpleGeneratorMetaTileEntity[3];

    //MULTIBLOCK PARTS SECTION
    public static MetaTileEntityItemBus[] ITEM_IMPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityItemBus[] ITEM_EXPORT_BUS = new MetaTileEntityItemBus[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_IMPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityFluidHatch[] FLUID_EXPORT_HATCH = new MetaTileEntityFluidHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_INPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityEnergyHatch[] ENERGY_OUTPUT_HATCH = new MetaTileEntityEnergyHatch[GTValues.V.length];
    public static MetaTileEntityRotorHolder[] ROTOR_HOLDER = new MetaTileEntityRotorHolder[GTValues.V.length];

    //MULTIBLOCKS SECTION
    public static MetaTileEntityPrimitiveBlastFurnace BRONZE_PRIMITIVE_BLAST_FURNACE;
    public static MetaTileEntityElectricBlastFurnace ELECTRIC_BLAST_FURNACE;
    public static MetaTileEntityVacuumFreezer VACUUM_FREEZER;
    public static MetaTileEntityImplosionCompressor IMPLOSION_COMPRESSOR;
    public static MetaTileEntityPyrolyseOven PYROLYSE_OVEN;
    public static MetaTileEntityDistillationTower DISTILLATION_TOWER;
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
    public static MetaTileEntityChest WOODEN_CHEST;
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
    public static MetaTileEntityPump[] PUMP = new MetaTileEntityPump[GTValues.V.length - 1];

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        GameRegistry.registerTileEntity(MetaTileEntityHolder.class, "gregtech_machine");
        GameRegistry.registerTileEntity(TileEntityCable.class, "gregtech_cable");

        STEAM_BOILER_COAL_BRONZE = GregTechAPI.registerMetaTileEntity(1, new SteamCoalBoiler("steam_boiler_coal_bronze", false));
        STEAM_BOILER_COAL_STEEL = GregTechAPI.registerMetaTileEntity(2, new SteamCoalBoiler("steam_boiler_coal_steel", true));

        STEAM_BOILER_SOLAR_BRONZE = GregTechAPI.registerMetaTileEntity(3, new SteamSolarBoiler("steam_boiler_solar_bronze", false));

        STEAM_BOILER_LAVA_BRONZE = GregTechAPI.registerMetaTileEntity(5, new SteamLavaBoiler("steam_boiler_lava_bronze", false));
        STEAM_BOILER_LAVA_STEEL = GregTechAPI.registerMetaTileEntity(6, new SteamLavaBoiler("steam_boiler_lava_steel", true));

        STEAM_EXTRACTOR_BRONZE = GregTechAPI.registerMetaTileEntity(7, new SteamExtractor("steam_extractor_bronze", false));
        STEAM_EXTRACTOR_STEEL = GregTechAPI.registerMetaTileEntity(8, new SteamExtractor("steam_extractor_steel", true));

        STEAM_MACERATOR_BRONZE = GregTechAPI.registerMetaTileEntity(9, new SteamMacerator("steam_macerator_bronze", false));
        STEAM_MACERATOR_STEEL = GregTechAPI.registerMetaTileEntity(10, new SteamMacerator("steam_macerator_steel", true));

        STEAM_COMPRESSOR_BRONZE = GregTechAPI.registerMetaTileEntity(11, new SteamCompressor("steam_compressor_bronze", false));
        STEAM_COMPRESSOR_STEEL = GregTechAPI.registerMetaTileEntity(12, new SteamCompressor("steam_compressor_steel", true));

        STEAM_HAMMER_BRONZE = GregTechAPI.registerMetaTileEntity(13, new SteamHammer("steam_hammer_bronze", false));
        STEAM_HAMMER_STEEL = GregTechAPI.registerMetaTileEntity(14, new SteamHammer("steam_hammer_steel", true));

        STEAM_FURNACE_BRONZE = GregTechAPI.registerMetaTileEntity(15, new SteamFurnace("steam_furnace_bronze", false));
        STEAM_FURNACE_STEEL = GregTechAPI.registerMetaTileEntity(16, new SteamFurnace("steam_furnace_steel", true));

        STEAM_ALLOY_SMELTER_BRONZE = GregTechAPI.registerMetaTileEntity(17, new SteamAlloySmelter("steam_alloy_smelter_bronze", false));
        STEAM_ALLOY_SMELTER_STEEL = GregTechAPI.registerMetaTileEntity(18, new SteamAlloySmelter("steam_alloy_smelter_steel", true));

        ELECTRIC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(50, new SimpleMachineMetaTileEntity("electric_furnace.lv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 1));
        ELECTRIC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(51, new SimpleMachineMetaTileEntity("electric_furnace.mv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 2));
        ELECTRIC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(52, new SimpleMachineMetaTileEntity("electric_furnace.hv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 3));
        ELECTRIC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(53, new SimpleMachineMetaTileEntity("electric_furnace.ev", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 4));

        MACERATOR[0] = GregTechAPI.registerMetaTileEntity(60, new MetaTileEntityMacerator("macerator.lv", RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 1));
        MACERATOR[1] = GregTechAPI.registerMetaTileEntity(61, new MetaTileEntityMacerator("macerator.mv", RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 2));
        MACERATOR[2] = GregTechAPI.registerMetaTileEntity(62, new MetaTileEntityMacerator("macerator.hv", RecipeMaps.MACERATOR_RECIPES, 2, Textures.MACERATOR_OVERLAY, 3));
        MACERATOR[3] = GregTechAPI.registerMetaTileEntity(63, new MetaTileEntityMacerator("macerator.ev", RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 4));

        ALLOY_SMELTER[0] = GregTechAPI.registerMetaTileEntity(70, new SimpleMachineMetaTileEntity("alloy_smelter.lv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        ALLOY_SMELTER[1] = GregTechAPI.registerMetaTileEntity(71, new SimpleMachineMetaTileEntity("alloy_smelter.mv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        ALLOY_SMELTER[2] = GregTechAPI.registerMetaTileEntity(72, new SimpleMachineMetaTileEntity("alloy_smelter.hv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        ALLOY_SMELTER[3] = GregTechAPI.registerMetaTileEntity(73, new SimpleMachineMetaTileEntity("alloy_smelter.ev", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        ALLOY_SMELTER[4] = GregTechAPI.registerMetaTileEntity(74, new SimpleMachineMetaTileEntity("alloy_smelter.iv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));

        AMPLIFABRICATOR[0] = GregTechAPI.registerMetaTileEntity(80, new SimpleMachineMetaTileEntity("amplifab.lv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 1));
        AMPLIFABRICATOR[1] = GregTechAPI.registerMetaTileEntity(81, new SimpleMachineMetaTileEntity("amplifab.mv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 2));
        AMPLIFABRICATOR[2] = GregTechAPI.registerMetaTileEntity(82, new SimpleMachineMetaTileEntity("amplifab.hv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 3));
        AMPLIFABRICATOR[3] = GregTechAPI.registerMetaTileEntity(83, new SimpleMachineMetaTileEntity("amplifab.ev", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 4));
        AMPLIFABRICATOR[4] = GregTechAPI.registerMetaTileEntity(84, new SimpleMachineMetaTileEntity("amplifab.iv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 5));

        ARC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(90, new SimpleMachineMetaTileEntity("arc_furnace.lv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 1));
        ARC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(91, new SimpleMachineMetaTileEntity("arc_furnace.mv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 2));
        ARC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(92, new SimpleMachineMetaTileEntity("arc_furnace.hv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 3));
        ARC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(93, new SimpleMachineMetaTileEntity("arc_furnace.ev", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 4));
        ARC_FURNACE[4] = GregTechAPI.registerMetaTileEntity(94, new SimpleMachineMetaTileEntity("arc_furnace.iv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 5));

        ASSEMBLER[0] = GregTechAPI.registerMetaTileEntity(100, new SimpleMachineMetaTileEntity("assembler.lv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 1));
        ASSEMBLER[1] = GregTechAPI.registerMetaTileEntity(101, new SimpleMachineMetaTileEntity("assembler.mv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 2));
        ASSEMBLER[2] = GregTechAPI.registerMetaTileEntity(102, new SimpleMachineMetaTileEntity("assembler.hv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 3));
        ASSEMBLER[3] = GregTechAPI.registerMetaTileEntity(103, new SimpleMachineMetaTileEntity("assembler.ev", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 4));
        ASSEMBLER[4] = GregTechAPI.registerMetaTileEntity(104, new SimpleMachineMetaTileEntity("assembler.iv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 5));

        AUTOCLAVE[0] = GregTechAPI.registerMetaTileEntity(110, new SimpleMachineMetaTileEntity("autoclave.lv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1));
        AUTOCLAVE[1] = GregTechAPI.registerMetaTileEntity(111, new SimpleMachineMetaTileEntity("autoclave.mv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2));
        AUTOCLAVE[2] = GregTechAPI.registerMetaTileEntity(112, new SimpleMachineMetaTileEntity("autoclave.hv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3));
        AUTOCLAVE[3] = GregTechAPI.registerMetaTileEntity(113, new SimpleMachineMetaTileEntity("autoclave.ev", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 4));
        AUTOCLAVE[4] = GregTechAPI.registerMetaTileEntity(114, new SimpleMachineMetaTileEntity("autoclave.iv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 5));

        BENDER[0] = GregTechAPI.registerMetaTileEntity(120, new SimpleMachineMetaTileEntity("bender.lv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 1));
        BENDER[1] = GregTechAPI.registerMetaTileEntity(121, new SimpleMachineMetaTileEntity("bender.mv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 2));
        BENDER[2] = GregTechAPI.registerMetaTileEntity(122, new SimpleMachineMetaTileEntity("bender.hv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 3));
        BENDER[3] = GregTechAPI.registerMetaTileEntity(123, new SimpleMachineMetaTileEntity("bender.ev", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 4));
        BENDER[4] = GregTechAPI.registerMetaTileEntity(124, new SimpleMachineMetaTileEntity("bender.iv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 5));

        BREWERY[0] = GregTechAPI.registerMetaTileEntity(130, new SimpleMachineMetaTileEntity("brewery.lv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 1));
        BREWERY[1] = GregTechAPI.registerMetaTileEntity(131, new SimpleMachineMetaTileEntity("brewery.mv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 2));
        BREWERY[2] = GregTechAPI.registerMetaTileEntity(132, new SimpleMachineMetaTileEntity("brewery.hv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 3));
        BREWERY[3] = GregTechAPI.registerMetaTileEntity(133, new SimpleMachineMetaTileEntity("brewery.ev", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 4));
        BREWERY[4] = GregTechAPI.registerMetaTileEntity(134, new SimpleMachineMetaTileEntity("brewery.iv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 5));

        CANNER[0] = GregTechAPI.registerMetaTileEntity(140, new SimpleMachineMetaTileEntity("canner.lv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 1));
        CANNER[1] = GregTechAPI.registerMetaTileEntity(141, new SimpleMachineMetaTileEntity("canner.mv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 2));
        CANNER[2] = GregTechAPI.registerMetaTileEntity(142, new SimpleMachineMetaTileEntity("canner.hv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 3));
        CANNER[3] = GregTechAPI.registerMetaTileEntity(143, new SimpleMachineMetaTileEntity("canner.ev", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 4));
        CANNER[4] = GregTechAPI.registerMetaTileEntity(144, new SimpleMachineMetaTileEntity("canner.iv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 5));

        CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(150, new SimpleMachineMetaTileEntity("centrifuge.lv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 1));
        CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(151, new SimpleMachineMetaTileEntity("centrifuge.mv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 2));
        CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(152, new SimpleMachineMetaTileEntity("centrifuge.hv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 3));

        CHEMICAL_BATH[0] = GregTechAPI.registerMetaTileEntity(180, new SimpleMachineMetaTileEntity("chemical_bath.lv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 1));
        CHEMICAL_BATH[1] = GregTechAPI.registerMetaTileEntity(181, new SimpleMachineMetaTileEntity("chemical_bath.mv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 2));
        CHEMICAL_BATH[2] = GregTechAPI.registerMetaTileEntity(182, new SimpleMachineMetaTileEntity("chemical_bath.hv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 3));
        CHEMICAL_BATH[3] = GregTechAPI.registerMetaTileEntity(183, new SimpleMachineMetaTileEntity("chemical_bath.ev", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 4));
        CHEMICAL_BATH[4] = GregTechAPI.registerMetaTileEntity(184, new SimpleMachineMetaTileEntity("chemical_bath.iv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 5));

        CHEMICAL_REACTOR[0] = GregTechAPI.registerMetaTileEntity(190, new SimpleMachineMetaTileEntity("chemical_reactor.lv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 1));
        CHEMICAL_REACTOR[1] = GregTechAPI.registerMetaTileEntity(191, new SimpleMachineMetaTileEntity("chemical_reactor.mv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2));
        CHEMICAL_REACTOR[2] = GregTechAPI.registerMetaTileEntity(192, new SimpleMachineMetaTileEntity("chemical_reactor.hv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3));
        CHEMICAL_REACTOR[3] = GregTechAPI.registerMetaTileEntity(193, new SimpleMachineMetaTileEntity("chemical_reactor.ev", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4));
        CHEMICAL_REACTOR[4] = GregTechAPI.registerMetaTileEntity(194, new SimpleMachineMetaTileEntity("chemical_reactor.iv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 5));

        COMPRESSOR[0] = GregTechAPI.registerMetaTileEntity(210, new SimpleMachineMetaTileEntity("compressor.lv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 1));
        COMPRESSOR[1] = GregTechAPI.registerMetaTileEntity(211, new SimpleMachineMetaTileEntity("compressor.mv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 2));
        COMPRESSOR[2] = GregTechAPI.registerMetaTileEntity(212, new SimpleMachineMetaTileEntity("compressor.hv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 3));
        COMPRESSOR[3] = GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.ev", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 4));
        COMPRESSOR[4] = GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.iv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 5));

        CUTTER[0] = GregTechAPI.registerMetaTileEntity(220, new SimpleMachineMetaTileEntity("cutter.lv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 1));
        CUTTER[1] = GregTechAPI.registerMetaTileEntity(221, new SimpleMachineMetaTileEntity("cutter.mv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 2));
        CUTTER[2] = GregTechAPI.registerMetaTileEntity(222, new SimpleMachineMetaTileEntity("cutter.hv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 3));
        CUTTER[3] = GregTechAPI.registerMetaTileEntity(223, new SimpleMachineMetaTileEntity("cutter.ev", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 4));
        CUTTER[4] = GregTechAPI.registerMetaTileEntity(224, new SimpleMachineMetaTileEntity("cutter.iv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 5));

        DISTILLERY[0] = GregTechAPI.registerMetaTileEntity(230, new SimpleMachineMetaTileEntity("distillery.lv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 1));
        DISTILLERY[1] = GregTechAPI.registerMetaTileEntity(231, new SimpleMachineMetaTileEntity("distillery.mv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 2));
        DISTILLERY[2] = GregTechAPI.registerMetaTileEntity(232, new SimpleMachineMetaTileEntity("distillery.hv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 3));
        DISTILLERY[3] = GregTechAPI.registerMetaTileEntity(233, new SimpleMachineMetaTileEntity("distillery.ev", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 4));
        DISTILLERY[4] = GregTechAPI.registerMetaTileEntity(234, new SimpleMachineMetaTileEntity("distillery.iv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 5));

        ELECTROLYZER[0] = GregTechAPI.registerMetaTileEntity(240, new SimpleMachineMetaTileEntity("electrolyzer.lv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 1));
        ELECTROLYZER[1] = GregTechAPI.registerMetaTileEntity(241, new SimpleMachineMetaTileEntity("electrolyzer.mv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 2));
        ELECTROLYZER[2] = GregTechAPI.registerMetaTileEntity(242, new SimpleMachineMetaTileEntity("electrolyzer.hv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 3));
        ELECTROLYZER[3] = GregTechAPI.registerMetaTileEntity(243, new SimpleMachineMetaTileEntity("electrolyzer.ev", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 4));
        ELECTROLYZER[4] = GregTechAPI.registerMetaTileEntity(244, new SimpleMachineMetaTileEntity("electrolyzer.iv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 5));

        ELECTROMAGNETIC_SEPARATOR[0] = GregTechAPI.registerMetaTileEntity(250, new SimpleMachineMetaTileEntity("electromagnetic_separator.lv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 1));
        ELECTROMAGNETIC_SEPARATOR[1] = GregTechAPI.registerMetaTileEntity(251, new SimpleMachineMetaTileEntity("electromagnetic_separator.mv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 2));
        ELECTROMAGNETIC_SEPARATOR[2] = GregTechAPI.registerMetaTileEntity(252, new SimpleMachineMetaTileEntity("electromagnetic_separator.hv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 3));
        ELECTROMAGNETIC_SEPARATOR[3] = GregTechAPI.registerMetaTileEntity(253, new SimpleMachineMetaTileEntity("electromagnetic_separator.ev", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 4));
        ELECTROMAGNETIC_SEPARATOR[4] = GregTechAPI.registerMetaTileEntity(254, new SimpleMachineMetaTileEntity("electromagnetic_separator.iv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 5));

        EXTRACTOR[0] = GregTechAPI.registerMetaTileEntity(260, new SimpleMachineMetaTileEntity("extractor.lv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 1));
        EXTRACTOR[1] = GregTechAPI.registerMetaTileEntity(261, new SimpleMachineMetaTileEntity("extractor.mv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 2));
        EXTRACTOR[2] = GregTechAPI.registerMetaTileEntity(262, new SimpleMachineMetaTileEntity("extractor.hv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 3));
        EXTRACTOR[3] = GregTechAPI.registerMetaTileEntity(263, new SimpleMachineMetaTileEntity("extractor.ev", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 4));
        EXTRACTOR[4] = GregTechAPI.registerMetaTileEntity(264, new SimpleMachineMetaTileEntity("extractor.iv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 5));

        EXTRUDER[0] = GregTechAPI.registerMetaTileEntity(270, new SimpleMachineMetaTileEntity("extruder.lv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 1));
        EXTRUDER[1] = GregTechAPI.registerMetaTileEntity(271, new SimpleMachineMetaTileEntity("extruder.mv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 2));
        EXTRUDER[2] = GregTechAPI.registerMetaTileEntity(272, new SimpleMachineMetaTileEntity("extruder.hv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 3));
        EXTRUDER[3] = GregTechAPI.registerMetaTileEntity(273, new SimpleMachineMetaTileEntity("extruder.ev", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 4));
        EXTRUDER[4] = GregTechAPI.registerMetaTileEntity(274, new SimpleMachineMetaTileEntity("extruder.iv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 5));

        FERMENTER[0] = GregTechAPI.registerMetaTileEntity(280, new SimpleMachineMetaTileEntity("fermenter.lv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 1));
        FERMENTER[1] = GregTechAPI.registerMetaTileEntity(281, new SimpleMachineMetaTileEntity("fermenter.mv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 2));
        FERMENTER[2] = GregTechAPI.registerMetaTileEntity(282, new SimpleMachineMetaTileEntity("fermenter.hv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 3));
        FERMENTER[3] = GregTechAPI.registerMetaTileEntity(283, new SimpleMachineMetaTileEntity("fermenter.ev", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 4));
        FERMENTER[4] = GregTechAPI.registerMetaTileEntity(284, new SimpleMachineMetaTileEntity("fermenter.iv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 5));

        FLUID_CANNER[0] = GregTechAPI.registerMetaTileEntity(290, new SimpleMachineMetaTileEntity("fluid_canner.lv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 1));
        FLUID_CANNER[1] = GregTechAPI.registerMetaTileEntity(291, new SimpleMachineMetaTileEntity("fluid_canner.mv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 2));
        FLUID_CANNER[2] = GregTechAPI.registerMetaTileEntity(292, new SimpleMachineMetaTileEntity("fluid_canner.hv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 3));
        FLUID_CANNER[3] = GregTechAPI.registerMetaTileEntity(293, new SimpleMachineMetaTileEntity("fluid_canner.ev", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 4));
        FLUID_CANNER[4] = GregTechAPI.registerMetaTileEntity(294, new SimpleMachineMetaTileEntity("fluid_canner.iv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 5));

        FLUID_EXTRACTOR[0] = GregTechAPI.registerMetaTileEntity(300, new SimpleMachineMetaTileEntity("fluid_extractor.lv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 1));
        FLUID_EXTRACTOR[1] = GregTechAPI.registerMetaTileEntity(301, new SimpleMachineMetaTileEntity("fluid_extractor.mv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 2));
        FLUID_EXTRACTOR[2] = GregTechAPI.registerMetaTileEntity(302, new SimpleMachineMetaTileEntity("fluid_extractor.hv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 3));
        FLUID_EXTRACTOR[3] = GregTechAPI.registerMetaTileEntity(303, new SimpleMachineMetaTileEntity("fluid_extractor.ev", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 4));
        FLUID_EXTRACTOR[4] = GregTechAPI.registerMetaTileEntity(304, new SimpleMachineMetaTileEntity("fluid_extractor.iv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 5));

        FLUID_HEATER[0] = GregTechAPI.registerMetaTileEntity(310, new SimpleMachineMetaTileEntity("fluid_heater.lv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 1));
        FLUID_HEATER[1] = GregTechAPI.registerMetaTileEntity(311, new SimpleMachineMetaTileEntity("fluid_heater.mv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 2));
        FLUID_HEATER[2] = GregTechAPI.registerMetaTileEntity(312, new SimpleMachineMetaTileEntity("fluid_heater.hv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 3));
        FLUID_HEATER[3] = GregTechAPI.registerMetaTileEntity(313, new SimpleMachineMetaTileEntity("fluid_heater.ev", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 4));
        FLUID_HEATER[4] = GregTechAPI.registerMetaTileEntity(314, new SimpleMachineMetaTileEntity("fluid_heater.iv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 5));

        FLUID_SOLIDIFIER[0] = GregTechAPI.registerMetaTileEntity(320, new SimpleMachineMetaTileEntity("fluid_solidifier.lv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 1));
        FLUID_SOLIDIFIER[1] = GregTechAPI.registerMetaTileEntity(321, new SimpleMachineMetaTileEntity("fluid_solidifier.mv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 2));
        FLUID_SOLIDIFIER[2] = GregTechAPI.registerMetaTileEntity(322, new SimpleMachineMetaTileEntity("fluid_solidifier.hv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 3));
        FLUID_SOLIDIFIER[3] = GregTechAPI.registerMetaTileEntity(323, new SimpleMachineMetaTileEntity("fluid_solidifier.ev", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 4));
        FLUID_SOLIDIFIER[4] = GregTechAPI.registerMetaTileEntity(324, new SimpleMachineMetaTileEntity("fluid_solidifier.iv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 5));

        FORGE_HAMMER[0] = GregTechAPI.registerMetaTileEntity(330, new SimpleMachineMetaTileEntity("forge_hammer.lv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 1));
        FORGE_HAMMER[1] = GregTechAPI.registerMetaTileEntity(331, new SimpleMachineMetaTileEntity("forge_hammer.mv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 2));
        FORGE_HAMMER[2] = GregTechAPI.registerMetaTileEntity(332, new SimpleMachineMetaTileEntity("forge_hammer.hv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 3));
        FORGE_HAMMER[3] = GregTechAPI.registerMetaTileEntity(333, new SimpleMachineMetaTileEntity("forge_hammer.ev", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 4));
        FORGE_HAMMER[4] = GregTechAPI.registerMetaTileEntity(334, new SimpleMachineMetaTileEntity("forge_hammer.iv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 5));

        FORMING_PRESS[0] = GregTechAPI.registerMetaTileEntity(340, new SimpleMachineMetaTileEntity("forming_press.lv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 1));
        FORMING_PRESS[1] = GregTechAPI.registerMetaTileEntity(341, new SimpleMachineMetaTileEntity("forming_press.mv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 2));
        FORMING_PRESS[2] = GregTechAPI.registerMetaTileEntity(342, new SimpleMachineMetaTileEntity("forming_press.hv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 3));
        FORMING_PRESS[3] = GregTechAPI.registerMetaTileEntity(343, new SimpleMachineMetaTileEntity("forming_press.ev", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 4));
        FORMING_PRESS[4] = GregTechAPI.registerMetaTileEntity(344, new SimpleMachineMetaTileEntity("forming_press.iv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 5));

        LATHE[0] = GregTechAPI.registerMetaTileEntity(350, new SimpleMachineMetaTileEntity("lathe.lv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 1));
        LATHE[1] = GregTechAPI.registerMetaTileEntity(351, new SimpleMachineMetaTileEntity("lathe.mv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 2));
        LATHE[2] = GregTechAPI.registerMetaTileEntity(352, new SimpleMachineMetaTileEntity("lathe.hv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 3));
        LATHE[3] = GregTechAPI.registerMetaTileEntity(353, new SimpleMachineMetaTileEntity("lathe.ev", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 4));
        LATHE[4] = GregTechAPI.registerMetaTileEntity(354, new SimpleMachineMetaTileEntity("lathe.iv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 5));

        MICROWAVE[0] = GregTechAPI.registerMetaTileEntity(360, new SimpleMachineMetaTileEntity("microwave.lv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 1));
        MICROWAVE[1] = GregTechAPI.registerMetaTileEntity(361, new SimpleMachineMetaTileEntity("microwave.mv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 2));
        MICROWAVE[2] = GregTechAPI.registerMetaTileEntity(362, new SimpleMachineMetaTileEntity("microwave.hv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 3));
        MICROWAVE[3] = GregTechAPI.registerMetaTileEntity(363, new SimpleMachineMetaTileEntity("microwave.ev", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 4));
        MICROWAVE[4] = GregTechAPI.registerMetaTileEntity(364, new SimpleMachineMetaTileEntity("microwave.iv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 5));

        MIXER[0] = GregTechAPI.registerMetaTileEntity(370, new SimpleMachineMetaTileEntity("mixer.lv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 1));
        MIXER[1] = GregTechAPI.registerMetaTileEntity(371, new SimpleMachineMetaTileEntity("mixer.mv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 2));
        MIXER[2] = GregTechAPI.registerMetaTileEntity(372, new SimpleMachineMetaTileEntity("mixer.hv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 3));
        MIXER[3] = GregTechAPI.registerMetaTileEntity(373, new SimpleMachineMetaTileEntity("mixer.ev", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 4));
        MIXER[4] = GregTechAPI.registerMetaTileEntity(374, new SimpleMachineMetaTileEntity("mixer.iv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 5));

        ORE_WASHER[0] = GregTechAPI.registerMetaTileEntity(380, new SimpleMachineMetaTileEntity("ore_washer.lv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 1));
        ORE_WASHER[1] = GregTechAPI.registerMetaTileEntity(381, new SimpleMachineMetaTileEntity("ore_washer.mv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 2));
        ORE_WASHER[2] = GregTechAPI.registerMetaTileEntity(382, new SimpleMachineMetaTileEntity("ore_washer.hv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 3));
        ORE_WASHER[3] = GregTechAPI.registerMetaTileEntity(383, new SimpleMachineMetaTileEntity("ore_washer.ev", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 4));

        PACKER[0] = GregTechAPI.registerMetaTileEntity(390, new SimpleMachineMetaTileEntity("packer.lv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 1));
        PACKER[1] = GregTechAPI.registerMetaTileEntity(391, new SimpleMachineMetaTileEntity("packer.mv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 2));
        PACKER[2] = GregTechAPI.registerMetaTileEntity(392, new SimpleMachineMetaTileEntity("packer.hv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 3));
        PACKER[3] = GregTechAPI.registerMetaTileEntity(393, new SimpleMachineMetaTileEntity("packer.ev", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 4));

        UNPACKER[0] = GregTechAPI.registerMetaTileEntity(400, new SimpleMachineMetaTileEntity("unpacker.lv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 1));
        UNPACKER[1] = GregTechAPI.registerMetaTileEntity(401, new SimpleMachineMetaTileEntity("unpacker.mv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 2));
        UNPACKER[2] = GregTechAPI.registerMetaTileEntity(402, new SimpleMachineMetaTileEntity("unpacker.hv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 3));
        UNPACKER[3] = GregTechAPI.registerMetaTileEntity(403, new SimpleMachineMetaTileEntity("unpacker.ev", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 4));

        PLASMA_ARC_FURNACE[0] = GregTechAPI.registerMetaTileEntity(410, new SimpleMachineMetaTileEntity("plasma_arc_furnace.lv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 1));
        PLASMA_ARC_FURNACE[1] = GregTechAPI.registerMetaTileEntity(411, new SimpleMachineMetaTileEntity("plasma_arc_furnace.mv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 2));
        PLASMA_ARC_FURNACE[2] = GregTechAPI.registerMetaTileEntity(412, new SimpleMachineMetaTileEntity("plasma_arc_furnace.hv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 3));
        PLASMA_ARC_FURNACE[3] = GregTechAPI.registerMetaTileEntity(413, new SimpleMachineMetaTileEntity("plasma_arc_furnace.ev", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 4));
        PLASMA_ARC_FURNACE[4] = GregTechAPI.registerMetaTileEntity(414, new SimpleMachineMetaTileEntity("plasma_arc_furnace.iv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 5));

        POLARIZER[0] = GregTechAPI.registerMetaTileEntity(420, new SimpleMachineMetaTileEntity("polarizer.lv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 1));
        POLARIZER[1] = GregTechAPI.registerMetaTileEntity(421, new SimpleMachineMetaTileEntity("polarizer.mv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 2));
        POLARIZER[2] = GregTechAPI.registerMetaTileEntity(422, new SimpleMachineMetaTileEntity("polarizer.hv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 3));
        POLARIZER[3] = GregTechAPI.registerMetaTileEntity(423, new SimpleMachineMetaTileEntity("polarizer.ev", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 4));
        POLARIZER[4] = GregTechAPI.registerMetaTileEntity(424, new SimpleMachineMetaTileEntity("polarizer.iv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 5));

        LASER_ENGRAVER[0] = GregTechAPI.registerMetaTileEntity(430, new SimpleMachineMetaTileEntity("laser_engraver.lv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 1));
        LASER_ENGRAVER[1] = GregTechAPI.registerMetaTileEntity(431, new SimpleMachineMetaTileEntity("laser_engraver.mv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 2));
        LASER_ENGRAVER[2] = GregTechAPI.registerMetaTileEntity(432, new SimpleMachineMetaTileEntity("laser_engraver.hv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 3));
        LASER_ENGRAVER[3] = GregTechAPI.registerMetaTileEntity(433, new SimpleMachineMetaTileEntity("laser_engraver.ev", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 4));
        LASER_ENGRAVER[4] = GregTechAPI.registerMetaTileEntity(434, new SimpleMachineMetaTileEntity("laser_engraver.iv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 5));

        PRINTER[0] = GregTechAPI.registerMetaTileEntity(440, new SimpleMachineMetaTileEntity("printer.lv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 1));
        PRINTER[1] = GregTechAPI.registerMetaTileEntity(441, new SimpleMachineMetaTileEntity("printer.mv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 2));
        PRINTER[2] = GregTechAPI.registerMetaTileEntity(442, new SimpleMachineMetaTileEntity("printer.hv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 3));
        PRINTER[3] = GregTechAPI.registerMetaTileEntity(443, new SimpleMachineMetaTileEntity("printer.ev", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 4));
        PRINTER[4] = GregTechAPI.registerMetaTileEntity(444, new SimpleMachineMetaTileEntity("printer.iv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 5));

        SIFTER[0] = GregTechAPI.registerMetaTileEntity(450, new SimpleMachineMetaTileEntity("sifter.lv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 1));
        SIFTER[1] = GregTechAPI.registerMetaTileEntity(451, new SimpleMachineMetaTileEntity("sifter.mv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 2));
        SIFTER[2] = GregTechAPI.registerMetaTileEntity(452, new SimpleMachineMetaTileEntity("sifter.hv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 3));
        SIFTER[3] = GregTechAPI.registerMetaTileEntity(453, new SimpleMachineMetaTileEntity("sifter.ev", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 4));
        SIFTER[4] = GregTechAPI.registerMetaTileEntity(454, new SimpleMachineMetaTileEntity("sifter.iv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 5));

        THERMAL_CENTRIFUGE[0] = GregTechAPI.registerMetaTileEntity(460, new SimpleMachineMetaTileEntity("thermal_centrifuge.lv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 1));
        THERMAL_CENTRIFUGE[1] = GregTechAPI.registerMetaTileEntity(461, new SimpleMachineMetaTileEntity("thermal_centrifuge.mv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 2));
        THERMAL_CENTRIFUGE[2] = GregTechAPI.registerMetaTileEntity(462, new SimpleMachineMetaTileEntity("thermal_centrifuge.hv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 3));
        THERMAL_CENTRIFUGE[3] = GregTechAPI.registerMetaTileEntity(463, new SimpleMachineMetaTileEntity("thermal_centrifuge.ev", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 4));
        THERMAL_CENTRIFUGE[4] = GregTechAPI.registerMetaTileEntity(464, new SimpleMachineMetaTileEntity("thermal_centrifuge.iv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 5));

        WIREMILL[0] = GregTechAPI.registerMetaTileEntity(470, new SimpleMachineMetaTileEntity("wiremill.lv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 1));
        WIREMILL[1] = GregTechAPI.registerMetaTileEntity(471, new SimpleMachineMetaTileEntity("wiremill.mv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 2));
        WIREMILL[2] = GregTechAPI.registerMetaTileEntity(472, new SimpleMachineMetaTileEntity("wiremill.hv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 3));
        WIREMILL[3] = GregTechAPI.registerMetaTileEntity(473, new SimpleMachineMetaTileEntity("wiremill.ev", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 4));
        WIREMILL[4] = GregTechAPI.registerMetaTileEntity(474, new SimpleMachineMetaTileEntity("wiremill.iv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 5));

        DIESEL_GENERATOR[0] = GregTechAPI.registerMetaTileEntity(480, new SimpleGeneratorMetaTileEntity("diesel_generator.lv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 1));
        DIESEL_GENERATOR[1] = GregTechAPI.registerMetaTileEntity(481, new SimpleGeneratorMetaTileEntity("diesel_generator.mv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 2));
        DIESEL_GENERATOR[2] = GregTechAPI.registerMetaTileEntity(482, new SimpleGeneratorMetaTileEntity("diesel_generator.hv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 3));

        STEAM_TURBINE[0] = GregTechAPI.registerMetaTileEntity(485, new SimpleGeneratorMetaTileEntity("steam_turbine.lv", RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 1));
        STEAM_TURBINE[1] = GregTechAPI.registerMetaTileEntity(486, new SimpleGeneratorMetaTileEntity("steam_turbine.mv", RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 2));
        STEAM_TURBINE[2] = GregTechAPI.registerMetaTileEntity(487, new SimpleGeneratorMetaTileEntity("steam_turbine.hv", RecipeMaps.STEAM_TURBINE_FUELS, Textures.STEAM_TURBINE_OVERLAY, 3));

        GAS_TURBINE[0] = GregTechAPI.registerMetaTileEntity(490, new SimpleGeneratorMetaTileEntity("gas_turbine.lv", RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 1));
        GAS_TURBINE[1] = GregTechAPI.registerMetaTileEntity(491, new SimpleGeneratorMetaTileEntity("gas_turbine.mv", RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 2));
        GAS_TURBINE[2] = GregTechAPI.registerMetaTileEntity(492, new SimpleGeneratorMetaTileEntity("gas_turbine.hv", RecipeMaps.GAS_TURBINE_FUELS, Textures.GAS_TURBINE_OVERLAY, 3));

        for(int i = 0; i < HULL.length; i++) {
            MetaTileEntityHull metaTileEntity = new MetaTileEntityHull("hull." + GTValues.VN[i].toLowerCase(), i);
            GregTechAPI.registerMetaTileEntity(500 + i, metaTileEntity);
            HULL[i] = metaTileEntity;
        }

        BRONZE_PRIMITIVE_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(510, new MetaTileEntityPrimitiveBlastFurnace("primitive_blast_furnace.bronze"));
        ELECTRIC_BLAST_FURNACE = GregTechAPI.registerMetaTileEntity(511, new MetaTileEntityElectricBlastFurnace("electric_blast_furnace"));
        VACUUM_FREEZER = GregTechAPI.registerMetaTileEntity(512, new MetaTileEntityVacuumFreezer("vacuum_freezer"));
        IMPLOSION_COMPRESSOR = GregTechAPI.registerMetaTileEntity(513, new MetaTileEntityImplosionCompressor("implosion_compressor"));
        PYROLYSE_OVEN = GregTechAPI.registerMetaTileEntity(514, new MetaTileEntityPyrolyseOven("pyrolyse_oven"));
        DISTILLATION_TOWER = GregTechAPI.registerMetaTileEntity(515, new MetaTileEntityDistillationTower("distillation_tower"));
        MULTI_FURNACE = GregTechAPI.registerMetaTileEntity(516, new MetaTileEntityMultiFurnace("multi_furnace"));
        DIESEL_ENGINE = GregTechAPI.registerMetaTileEntity(517, new MetaTileEntityDieselEngine("diesel_engine"));

        LARGE_STEAM_TURBINE = GregTechAPI.registerMetaTileEntity(518, new MetaTileEntityLargeTurbine("large_turbine.steam", TurbineType.STEAM));
        LARGE_GAS_TURBINE = GregTechAPI.registerMetaTileEntity(519, new MetaTileEntityLargeTurbine("large_turbine.gas", TurbineType.GAS));
        LARGE_PLASMA_TURBINE = GregTechAPI.registerMetaTileEntity(520, new MetaTileEntityLargeTurbine("large_turbine.plasma", TurbineType.PLASMA));

        LARGE_BRONZE_BOILER = GregTechAPI.registerMetaTileEntity(521, new MetaTileEntityLargeBoiler("large_boiler.bronze", BoilerType.BRONZE));
        LARGE_STEEL_BOILER = GregTechAPI.registerMetaTileEntity(522, new MetaTileEntityLargeBoiler("large_boiler.steel", BoilerType.STEEL));
        LARGE_TITANIUM_BOILER = GregTechAPI.registerMetaTileEntity(523, new MetaTileEntityLargeBoiler("large_boiler.titanium", BoilerType.TITANIUM));
        LARGE_TUNGSTENSTEEL_BOILER = GregTechAPI.registerMetaTileEntity(524, new MetaTileEntityLargeBoiler("large_boiler.tungstensteel", BoilerType.TUNGSTENSTEEL));

        int[] batteryBufferSlots = new int[] {1, 4, 9, 16};
        for(int i = 0; i < GTValues.V.length; i++) {
            if(i > 0) {
                MetaTileEntityTransformer transformer = new MetaTileEntityTransformer("transformer." + GTValues.VN[i].toLowerCase(), i);
                TRANSFORMER[i - 1] = GregTechAPI.registerMetaTileEntity(600 + (i - 1), transformer);
            }
            BATTERY_BUFFER[i] = new MetaTileEntityBatteryBuffer[batteryBufferSlots.length];
            for(int slot = 0; slot < batteryBufferSlots.length; slot++) {
                String transformerId = "battery_buffer." + GTValues.VN[i].toLowerCase() + "." + batteryBufferSlots[slot];
                MetaTileEntityBatteryBuffer batteryBuffer = new MetaTileEntityBatteryBuffer(transformerId, i, batteryBufferSlots[slot]);
                BATTERY_BUFFER[i][slot] = GregTechAPI.registerMetaTileEntity(610 + batteryBufferSlots.length * i + slot, batteryBuffer);
            }
            String chargerId = "charger." + GTValues.VN[i].toLowerCase();
            MetaTileEntityCharger charger = new MetaTileEntityCharger(chargerId, i, 4);
            CHARGER[i] = GregTechAPI.registerMetaTileEntity(680 + i, charger);
        }

        for(int i = 0; i < GTValues.V.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            ITEM_IMPORT_BUS[i] = new MetaTileEntityItemBus("item_bus.import." + voltageName, i, false);
            ITEM_EXPORT_BUS[i] = new MetaTileEntityItemBus("item_bus.export." + voltageName, i, true);
            FLUID_IMPORT_HATCH[i] = new MetaTileEntityFluidHatch("fluid_hatch.import." + voltageName, i, false);
            FLUID_EXPORT_HATCH[i] = new MetaTileEntityFluidHatch("fluid_hatch.export." + voltageName, i, true);
            ENERGY_INPUT_HATCH[i] = new MetaTileEntityEnergyHatch("energy_hatch.input." + voltageName, i, false);
            ENERGY_OUTPUT_HATCH[i] = new MetaTileEntityEnergyHatch("energy_hatch.output." + voltageName, i, true);
            ROTOR_HOLDER[i] = new MetaTileEntityRotorHolder("rotor_holder." + voltageName, i);

            GregTechAPI.registerMetaTileEntity(700 + 10 * i, ITEM_IMPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 1, ITEM_EXPORT_BUS[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 2, FLUID_IMPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 3, FLUID_EXPORT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 4, ENERGY_INPUT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 5, ENERGY_OUTPUT_HATCH[i]);
            GregTechAPI.registerMetaTileEntity(700 + 10 * i + 6, ROTOR_HOLDER[i]);
        }

        WOODEN_CHEST = GregTechAPI.registerMetaTileEntity(801, new MetaTileEntityChest("wooden_chest", Materials.Wood, 27));
        BRONZE_CHEST = GregTechAPI.registerMetaTileEntity(802, new MetaTileEntityChest("bronze_chest", Materials.Bronze, 36));
        STEEL_CHEST = GregTechAPI.registerMetaTileEntity(803, new MetaTileEntityChest("steel_chest", Materials.Steel, 45));
        STAINLESS_STEEL_CHEST = GregTechAPI.registerMetaTileEntity(804, new MetaTileEntityChest("stainless_steel_chest", Materials.StainlessSteel, 54));
        TITANIUM_CHEST = GregTechAPI.registerMetaTileEntity(805, new MetaTileEntityChest("titanium_chest", Materials.Titanium, 63));
        TUNGSTENSTEEL_CHEST = GregTechAPI.registerMetaTileEntity(806, new MetaTileEntityChest("tungstensteel_chest", Materials.TungstenSteel, 72));

        WOODEN_TANK = GregTechAPI.registerMetaTileEntity(811, new MetaTileEntityTank("wooden_tank", Materials.Wood, 4000));
        BRONZE_TANK = GregTechAPI.registerMetaTileEntity(812, new MetaTileEntityTank("bronze_tank", Materials.Bronze,8000));
        STEEL_TANK = GregTechAPI.registerMetaTileEntity(813, new MetaTileEntityTank("steel_tank", Materials.Steel, 16000));
        TUNGSTENSTEEL_TANK = GregTechAPI.registerMetaTileEntity(814, new MetaTileEntityTank("stainless_steel_tank", Materials.StainlessSteel, 24000));
        TITANIUM_TANK = GregTechAPI.registerMetaTileEntity(815, new MetaTileEntityTank("titanium_tank", Materials.Titanium, 32000));
        TUNGSTENSTEEL_TANK = GregTechAPI.registerMetaTileEntity(816, new MetaTileEntityTank("tungstensteel_tank", Materials.TungstenSteel, 48000));

        for(int i = 0; i < GTValues.V.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            if(i > 0) {
                PUMP[i - 1] = new MetaTileEntityPump("pump." + voltageName, i);
                GregTechAPI.registerMetaTileEntity(900 + 10 * (i - 1), PUMP[i - 1]);
            }
        }
    }

}
