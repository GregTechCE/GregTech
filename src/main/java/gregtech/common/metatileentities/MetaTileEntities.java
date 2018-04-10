package gregtech.common.metatileentities;

import gregtech.api.GregTechAPI;
import gregtech.api.cable.tile.TileEntityCable;
import gregtech.api.metatileentity.MaceratorMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SimpleGeneratorMetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.util.GTLog;
import gregtech.common.metatileentities.steam.*;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaTileEntities {

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        GameRegistry.registerTileEntity(MetaTileEntityHolder.class, "gregtech_machine");
        GameRegistry.registerTileEntity(TileEntityCable.class, "gregtech_cable");

        GregTechAPI.registerMetaTileEntity(1, new SteamCoalBoiler("steam_boiler_coal_bronze", false));
        GregTechAPI.registerMetaTileEntity(2, new SteamCoalBoiler("steam_boiler_coal_steel", true));

        GregTechAPI.registerMetaTileEntity(3, new SteamSolarBoiler("steam_boiler_solar_bronze", false));

        GregTechAPI.registerMetaTileEntity(5, new SteamLavaBoiler("steam_boiler_lava_bronze", false));
        GregTechAPI.registerMetaTileEntity(6, new SteamLavaBoiler("steam_boiler_lava_steel", true));

        GregTechAPI.registerMetaTileEntity(7, new SteamExtractor("steam_extractor_bronze", false));
        GregTechAPI.registerMetaTileEntity(8, new SteamExtractor("steam_extractor_steel", true));

        GregTechAPI.registerMetaTileEntity(9, new SteamMacerator("steam_macerator_bronze", false));
        GregTechAPI.registerMetaTileEntity(10, new SteamMacerator("steam_macerator_steel", true));

        GregTechAPI.registerMetaTileEntity(11, new SteamCompressor("steam_compressor_bronze", false));
        GregTechAPI.registerMetaTileEntity(12, new SteamCompressor("steam_compressor_steel", true));

        GregTechAPI.registerMetaTileEntity(13, new SteamHammer("steam_hammer_bronze", false));
        GregTechAPI.registerMetaTileEntity(14, new SteamHammer("steam_hammer_steel", true));

        GregTechAPI.registerMetaTileEntity(15, new SteamFurnace("steam_furnace_bronze", false));
        GregTechAPI.registerMetaTileEntity(16, new SteamFurnace("steam_furnace_steel", true));

        GregTechAPI.registerMetaTileEntity(17, new SteamAlloySmelter("steam_alloy_smelter_bronze", false));
        GregTechAPI.registerMetaTileEntity(18, new SteamAlloySmelter("steam_alloy_smelter_steel", true));

        GregTechAPI.registerMetaTileEntity(50, new SimpleMachineMetaTileEntity("electric_furnace.lv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(51, new SimpleMachineMetaTileEntity("electric_furnace.mv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(52, new SimpleMachineMetaTileEntity("electric_furnace.hv", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(53, new SimpleMachineMetaTileEntity("electric_furnace.ev", RecipeMaps.FURNACE_RECIPES, Textures.ELECTRIC_FURNACE_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(60, new MaceratorMetaTileEntity("macerator.lv", RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(61, new MaceratorMetaTileEntity("macerator.mv", RecipeMaps.MACERATOR_RECIPES, 1, Textures.MACERATOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(62, new MaceratorMetaTileEntity("macerator.hv", RecipeMaps.MACERATOR_RECIPES, 2, Textures.MACERATOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(63, new MaceratorMetaTileEntity("macerator.ev", RecipeMaps.MACERATOR_RECIPES, 3, Textures.MACERATOR_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(70, new SimpleMachineMetaTileEntity("alloy_smelter.lv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(71, new SimpleMachineMetaTileEntity("alloy_smelter.mv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(72, new SimpleMachineMetaTileEntity("alloy_smelter.hv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(73, new SimpleMachineMetaTileEntity("alloy_smelter.ev", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(74, new SimpleMachineMetaTileEntity("alloy_smelter.iv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(80, new SimpleMachineMetaTileEntity("amplifab.lv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(81, new SimpleMachineMetaTileEntity("amplifab.mv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(82, new SimpleMachineMetaTileEntity("amplifab.hv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(83, new SimpleMachineMetaTileEntity("amplifab.ev", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(84, new SimpleMachineMetaTileEntity("amplifab.iv", RecipeMaps.AMPLIFIERS, Textures.AMPLIFAB_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(90, new SimpleMachineMetaTileEntity("arc_furnace.lv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(91, new SimpleMachineMetaTileEntity("arc_furnace.mv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(92, new SimpleMachineMetaTileEntity("arc_furnace.hv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(93, new SimpleMachineMetaTileEntity("arc_furnace.ev", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(94, new SimpleMachineMetaTileEntity("arc_furnace.iv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ARC_FURNACE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(100, new SimpleMachineMetaTileEntity("assembler.lv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(101, new SimpleMachineMetaTileEntity("assembler.mv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(102, new SimpleMachineMetaTileEntity("assembler.hv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(103, new SimpleMachineMetaTileEntity("assembler.ev", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(104, new SimpleMachineMetaTileEntity("assembler.iv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ASSEMBLER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(110, new SimpleMachineMetaTileEntity("autoclave.lv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(111, new SimpleMachineMetaTileEntity("autoclave.mv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(112, new SimpleMachineMetaTileEntity("autoclave.hv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(113, new SimpleMachineMetaTileEntity("autoclave.ev", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(114, new SimpleMachineMetaTileEntity("autoclave.iv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.AUTOCLAVE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(120, new SimpleMachineMetaTileEntity("bender.lv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(121, new SimpleMachineMetaTileEntity("bender.mv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(122, new SimpleMachineMetaTileEntity("bender.hv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(123, new SimpleMachineMetaTileEntity("bender.ev", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(124, new SimpleMachineMetaTileEntity("bender.iv", RecipeMaps.BENDER_RECIPES, Textures.BENDER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(130, new SimpleMachineMetaTileEntity("brewery.lv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(131, new SimpleMachineMetaTileEntity("brewery.mv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(132, new SimpleMachineMetaTileEntity("brewery.hv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(133, new SimpleMachineMetaTileEntity("brewery.ev", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(134, new SimpleMachineMetaTileEntity("brewery.iv", RecipeMaps.BREWING_RECIPES, Textures.BREWERY_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(140, new SimpleMachineMetaTileEntity("canner.lv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(141, new SimpleMachineMetaTileEntity("canner.mv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(142, new SimpleMachineMetaTileEntity("canner.hv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(143, new SimpleMachineMetaTileEntity("canner.ev", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(144, new SimpleMachineMetaTileEntity("canner.iv", RecipeMaps.CANNER_RECIPES, Textures.CANNER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(150, new SimpleMachineMetaTileEntity("centrifuge.lv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(151, new SimpleMachineMetaTileEntity("centrifuge.mv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(152, new SimpleMachineMetaTileEntity("centrifuge.hv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.CENTRIFUGE_OVERLAY, 3));

//        GregTechAPI.registerMetaTileEntity(160, new SimpleMachineMetaTileEntity("circuit_assembler.lv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
//        GregTechAPI.registerMetaTileEntity(161, new SimpleMachineMetaTileEntity("circuit_assembler.mv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
//        GregTechAPI.registerMetaTileEntity(162, new SimpleMachineMetaTileEntity("circuit_assembler.hv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
//        GregTechAPI.registerMetaTileEntity(163, new SimpleMachineMetaTileEntity("circuit_assembler.ev", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
//        GregTechAPI.registerMetaTileEntity(164, new SimpleMachineMetaTileEntity("circuit_assembler.iv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(180, new SimpleMachineMetaTileEntity("chemical_bath.lv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(181, new SimpleMachineMetaTileEntity("chemical_bath.mv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(182, new SimpleMachineMetaTileEntity("chemical_bath.hv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(183, new SimpleMachineMetaTileEntity("chemical_bath.ev", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(184, new SimpleMachineMetaTileEntity("chemical_bath.iv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.CHEMICAL_BATH_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(190, new SimpleMachineMetaTileEntity("chemical_reactor.lv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(191, new SimpleMachineMetaTileEntity("chemical_reactor.mv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(192, new SimpleMachineMetaTileEntity("chemical_reactor.hv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(193, new SimpleMachineMetaTileEntity("chemical_reactor.ev", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(194, new SimpleMachineMetaTileEntity("chemical_reactor.iv", RecipeMaps.CHEMICAL_RECIPES, Textures.CHEMICAL_REACTOR_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(210, new SimpleMachineMetaTileEntity("compressor.lv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(211, new SimpleMachineMetaTileEntity("compressor.mv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(212, new SimpleMachineMetaTileEntity("compressor.hv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.ev", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.iv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(220, new SimpleMachineMetaTileEntity("cutter.lv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(221, new SimpleMachineMetaTileEntity("cutter.mv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(222, new SimpleMachineMetaTileEntity("cutter.hv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(223, new SimpleMachineMetaTileEntity("cutter.ev", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(224, new SimpleMachineMetaTileEntity("cutter.iv", RecipeMaps.CUTTER_RECIPES, Textures.CUTTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(230, new SimpleMachineMetaTileEntity("distillery.lv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(231, new SimpleMachineMetaTileEntity("distillery.mv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(232, new SimpleMachineMetaTileEntity("distillery.hv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(233, new SimpleMachineMetaTileEntity("distillery.ev", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(234, new SimpleMachineMetaTileEntity("distillery.iv", RecipeMaps.DISTILLERY_RECIPES, Textures.DISTILLERY_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(240, new SimpleMachineMetaTileEntity("electrolyzer.lv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(241, new SimpleMachineMetaTileEntity("electrolyzer.mv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(242, new SimpleMachineMetaTileEntity("electrolyzer.hv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(243, new SimpleMachineMetaTileEntity("electrolyzer.ev", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(244, new SimpleMachineMetaTileEntity("electrolyzer.iv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ELECTROLYZER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(250, new SimpleMachineMetaTileEntity("electromagnetic_separator.lv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(251, new SimpleMachineMetaTileEntity("electromagnetic_separator.mv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(252, new SimpleMachineMetaTileEntity("electromagnetic_separator.hv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(253, new SimpleMachineMetaTileEntity("electromagnetic_separator.ev", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(254, new SimpleMachineMetaTileEntity("electromagnetic_separator.iv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ELECTROMAGNETIC_SEPARATOR_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(260, new SimpleMachineMetaTileEntity("extractor.lv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(261, new SimpleMachineMetaTileEntity("extractor.mv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(262, new SimpleMachineMetaTileEntity("extractor.hv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(263, new SimpleMachineMetaTileEntity("extractor.ev", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(264, new SimpleMachineMetaTileEntity("extractor.iv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(270, new SimpleMachineMetaTileEntity("extruder.lv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(271, new SimpleMachineMetaTileEntity("extruder.mv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(272, new SimpleMachineMetaTileEntity("extruder.hv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(273, new SimpleMachineMetaTileEntity("extruder.ev", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(274, new SimpleMachineMetaTileEntity("extruder.iv", RecipeMaps.EXTRUDER_RECIPES, Textures.EXTRUDER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(280, new SimpleMachineMetaTileEntity("fermenter.lv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(281, new SimpleMachineMetaTileEntity("fermenter.mv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(282, new SimpleMachineMetaTileEntity("fermenter.hv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(283, new SimpleMachineMetaTileEntity("fermenter.ev", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(284, new SimpleMachineMetaTileEntity("fermenter.iv", RecipeMaps.FERMENTING_RECIPES, Textures.FERMENTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(290, new SimpleMachineMetaTileEntity("fluid_canner.lv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(291, new SimpleMachineMetaTileEntity("fluid_canner.mv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(292, new SimpleMachineMetaTileEntity("fluid_canner.hv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(293, new SimpleMachineMetaTileEntity("fluid_canner.ev", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(294, new SimpleMachineMetaTileEntity("fluid_canner.iv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.FLUID_CANNER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(300, new SimpleMachineMetaTileEntity("fluid_extractor.lv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(301, new SimpleMachineMetaTileEntity("fluid_extractor.mv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(302, new SimpleMachineMetaTileEntity("fluid_extractor.hv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(303, new SimpleMachineMetaTileEntity("fluid_extractor.ev", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(304, new SimpleMachineMetaTileEntity("fluid_extractor.iv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.FLUID_EXTRACTOR_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(310, new SimpleMachineMetaTileEntity("fluid_heater.lv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(311, new SimpleMachineMetaTileEntity("fluid_heater.mv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(312, new SimpleMachineMetaTileEntity("fluid_heater.hv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(313, new SimpleMachineMetaTileEntity("fluid_heater.ev", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(314, new SimpleMachineMetaTileEntity("fluid_heater.iv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.FLUID_HEATER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(320, new SimpleMachineMetaTileEntity("fluid_solidifier.lv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(321, new SimpleMachineMetaTileEntity("fluid_solidifier.mv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(322, new SimpleMachineMetaTileEntity("fluid_solidifier.hv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(323, new SimpleMachineMetaTileEntity("fluid_solidifier.ev", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(324, new SimpleMachineMetaTileEntity("fluid_solidifier.iv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.FLUID_SOLIDIFIER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(330, new SimpleMachineMetaTileEntity("forge_hammer.lv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(331, new SimpleMachineMetaTileEntity("forge_hammer.mv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(332, new SimpleMachineMetaTileEntity("forge_hammer.hv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(333, new SimpleMachineMetaTileEntity("forge_hammer.ev", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(334, new SimpleMachineMetaTileEntity("forge_hammer.iv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.FORGE_HAMMER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(340, new SimpleMachineMetaTileEntity("forming_press.lv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(341, new SimpleMachineMetaTileEntity("forming_press.mv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(342, new SimpleMachineMetaTileEntity("forming_press.hv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(343, new SimpleMachineMetaTileEntity("forming_press.ev", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(344, new SimpleMachineMetaTileEntity("forming_press.iv", RecipeMaps.FORMING_PRESS_RECIPES, Textures.FORMING_PRESS_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(350, new SimpleMachineMetaTileEntity("lathe.lv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(351, new SimpleMachineMetaTileEntity("lathe.mv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(352, new SimpleMachineMetaTileEntity("lathe.hv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(353, new SimpleMachineMetaTileEntity("lathe.ev", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(354, new SimpleMachineMetaTileEntity("lathe.iv", RecipeMaps.LATHE_RECIPES, Textures.LATHE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(360, new SimpleMachineMetaTileEntity("microwave.lv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(361, new SimpleMachineMetaTileEntity("microwave.mv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(362, new SimpleMachineMetaTileEntity("microwave.hv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(363, new SimpleMachineMetaTileEntity("microwave.ev", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(364, new SimpleMachineMetaTileEntity("microwave.iv", RecipeMaps.MICROWAVE_RECIPES, Textures.MICROWAVE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(370, new SimpleMachineMetaTileEntity("mixer.lv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(371, new SimpleMachineMetaTileEntity("mixer.mv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(372, new SimpleMachineMetaTileEntity("mixer.hv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(373, new SimpleMachineMetaTileEntity("mixer.ev", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(374, new SimpleMachineMetaTileEntity("mixer.iv", RecipeMaps.MIXER_RECIPES, Textures.MIXER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(380, new SimpleMachineMetaTileEntity("ore_washer.lv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(381, new SimpleMachineMetaTileEntity("ore_washer.mv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(382, new SimpleMachineMetaTileEntity("ore_washer.hv", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(383, new SimpleMachineMetaTileEntity("ore_washer.ev", RecipeMaps.ORE_WASHER_RECIPES, Textures.ORE_WASHER_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(390, new SimpleMachineMetaTileEntity("packer.lv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(391, new SimpleMachineMetaTileEntity("packer.mv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(392, new SimpleMachineMetaTileEntity("packer.hv", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(393, new SimpleMachineMetaTileEntity("packer.ev", RecipeMaps.PACKER_RECIPES, Textures.PACKER_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(400, new SimpleMachineMetaTileEntity("unpacker.lv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(401, new SimpleMachineMetaTileEntity("unpacker.mv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(402, new SimpleMachineMetaTileEntity("unpacker.hv", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(403, new SimpleMachineMetaTileEntity("unpacker.ev", RecipeMaps.UNPACKER_RECIPES, Textures.UNPACKER_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(410, new SimpleMachineMetaTileEntity("plasma_arc_furnace.lv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(411, new SimpleMachineMetaTileEntity("plasma_arc_furnace.mv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(412, new SimpleMachineMetaTileEntity("plasma_arc_furnace.hv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(413, new SimpleMachineMetaTileEntity("plasma_arc_furnace.ev", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(414, new SimpleMachineMetaTileEntity("plasma_arc_furnace.iv", RecipeMaps.PLASMA_ARC_FURNACE_RECIPES, Textures.PLASMA_ARC_FURNACE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(420, new SimpleMachineMetaTileEntity("polarizer.lv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(421, new SimpleMachineMetaTileEntity("polarizer.mv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(422, new SimpleMachineMetaTileEntity("polarizer.hv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(423, new SimpleMachineMetaTileEntity("polarizer.ev", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(424, new SimpleMachineMetaTileEntity("polarizer.iv", RecipeMaps.POLARIZER_RECIPES, Textures.POLARIZER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(430, new SimpleMachineMetaTileEntity("laser_engraver.lv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(431, new SimpleMachineMetaTileEntity("laser_engraver.mv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(432, new SimpleMachineMetaTileEntity("laser_engraver.hv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(433, new SimpleMachineMetaTileEntity("laser_engraver.ev", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(434, new SimpleMachineMetaTileEntity("laser_engraver.iv", RecipeMaps.LASER_ENGRAVER_RECIPES, Textures.LASER_ENGRAVER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(440, new SimpleMachineMetaTileEntity("printer.lv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(441, new SimpleMachineMetaTileEntity("printer.mv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(442, new SimpleMachineMetaTileEntity("printer.hv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(443, new SimpleMachineMetaTileEntity("printer.ev", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(444, new SimpleMachineMetaTileEntity("printer.iv", RecipeMaps.PRINTER_RECIPES, Textures.PRINTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(450, new SimpleMachineMetaTileEntity("sifter.lv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(451, new SimpleMachineMetaTileEntity("sifter.mv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(452, new SimpleMachineMetaTileEntity("sifter.hv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(453, new SimpleMachineMetaTileEntity("sifter.ev", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(454, new SimpleMachineMetaTileEntity("sifter.iv", RecipeMaps.SIFTER_RECIPES, Textures.SIFTER_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(460, new SimpleMachineMetaTileEntity("thermal_centrifuge.lv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(461, new SimpleMachineMetaTileEntity("thermal_centrifuge.mv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(462, new SimpleMachineMetaTileEntity("thermal_centrifuge.hv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(463, new SimpleMachineMetaTileEntity("thermal_centrifuge.ev", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(464, new SimpleMachineMetaTileEntity("thermal_centrifuge.iv", RecipeMaps.THERMAL_CENTRIFUGE_RECIPES, Textures.THERMAL_CENTRIFUGE_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(470, new SimpleMachineMetaTileEntity("wiremill.lv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(471, new SimpleMachineMetaTileEntity("wiremill.mv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(472, new SimpleMachineMetaTileEntity("wiremill.hv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(473, new SimpleMachineMetaTileEntity("wiremill.ev", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(474, new SimpleMachineMetaTileEntity("wiremill.iv", RecipeMaps.WIREMILL_RECIPES, Textures.WIREMILL_OVERLAY, 5));

        GregTechAPI.registerMetaTileEntity(475, new SimpleGeneratorMetaTileEntity("diesel_generator.lv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(476, new SimpleGeneratorMetaTileEntity("diesel_generator.mv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(477, new SimpleGeneratorMetaTileEntity("diesel_generator.hv", RecipeMaps.DIESEL_GENERATOR_FUELS, Textures.DIESEL_GENERATOR_OVERLAY, 3));
    }
}
