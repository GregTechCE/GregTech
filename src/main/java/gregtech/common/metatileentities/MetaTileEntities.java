package gregtech.common.metatileentities;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.tileentity.TileEntityCableEmitter;
import gregtech.common.metatileentities.steam.*;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MetaTileEntities {

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        GameRegistry.registerTileEntity(MetaTileEntityHolder.class, "gregtech_machine");
        GameRegistry.registerTileEntity(TileEntityCableEmitter.class, "gregtech_cable_emitter");

        GregTechAPI.registerMetaTileEntity(1, new SteamCoalBoiler("steam_boiler_coal_bronze", false));
        GregTechAPI.registerMetaTileEntity(2, new SteamCoalBoiler("steam_boiler_coal_steel", true));

        GregTechAPI.registerMetaTileEntity(3, new SteamSolarBoiler("steam_boiler_solar_bronze", false));
        GregTechAPI.registerMetaTileEntity(4, new SteamSolarBoiler("steam_boiler_solar_steel", true));

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

        GregTechAPI.registerMetaTileEntity(20, new SimpleMachineMetaTileEntity("electric_furnace.lv", RecipeMaps.FURNACE_RECIPES, Textures.FURNACE_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(21, new SimpleMachineMetaTileEntity("electric_furnace.mv", RecipeMaps.FURNACE_RECIPES, Textures.FURNACE_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(22, new SimpleMachineMetaTileEntity("electric_furnace.hv", RecipeMaps.FURNACE_RECIPES, Textures.FURNACE_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(23, new SimpleMachineMetaTileEntity("electric_furnace.ev", RecipeMaps.FURNACE_RECIPES, Textures.FURNACE_OVERLAY, 4));

        GregTechAPI.registerMetaTileEntity(60, new SimpleMachineMetaTileEntity("macerator.lv", RecipeMaps.MACERATOR_RECIPES, Textures.MACERATOR_OVERLAY, 1) {
            @Override
            protected IItemHandlerModifiable createExportItemHandler() {
                return new ItemStackHandler(1);
            }
        });
        GregTechAPI.registerMetaTileEntity(61, new SimpleMachineMetaTileEntity("macerator.mv", RecipeMaps.MACERATOR_RECIPES, Textures.MACERATOR_OVERLAY, 2) {
            @Override
            protected IItemHandlerModifiable createExportItemHandler() {
                return new ItemStackHandler(1);
            }
        });
        GregTechAPI.registerMetaTileEntity(62, new SimpleMachineMetaTileEntity("macerator.hv", RecipeMaps.MACERATOR_RECIPES, Textures.MACERATOR_OVERLAY, 3) {
            @Override
            protected IItemHandlerModifiable createExportItemHandler() {
                return new ItemStackHandler(2);
            }
        });
        GregTechAPI.registerMetaTileEntity(63, new SimpleMachineMetaTileEntity("macerator.ev", RecipeMaps.MACERATOR_RECIPES, Textures.MACERATOR_OVERLAY, 4) {
            @Override
            protected IItemHandlerModifiable createExportItemHandler() {
                return new ItemStackHandler(3);
            }
        });

        GregTechAPI.registerMetaTileEntity(70, new SimpleMachineMetaTileEntity("alloy_smelter.lv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(71, new SimpleMachineMetaTileEntity("alloy_smelter.mv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(72, new SimpleMachineMetaTileEntity("alloy_smelter.hv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(73, new SimpleMachineMetaTileEntity("alloy_smelter.ev", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(74, new SimpleMachineMetaTileEntity("alloy_smelter.iv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(75, new SimpleMachineMetaTileEntity("alloy_smelter.luv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(76, new SimpleMachineMetaTileEntity("alloy_smelter.zpm", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(77, new SimpleMachineMetaTileEntity("alloy_smelter.uv", RecipeMaps.ALLOY_SMELTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(80, new SimpleMachineMetaTileEntity("amplifabricator.lv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(81, new SimpleMachineMetaTileEntity("amplifabricator.mv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(82, new SimpleMachineMetaTileEntity("amplifabricator.hv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(83, new SimpleMachineMetaTileEntity("amplifabricator.ev", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(84, new SimpleMachineMetaTileEntity("amplifabricator.iv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(85, new SimpleMachineMetaTileEntity("amplifabricator.luv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(86, new SimpleMachineMetaTileEntity("amplifabricator.zpm", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(87, new SimpleMachineMetaTileEntity("amplifabricator.uv", RecipeMaps.AMPLIFIERS, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(90, new SimpleMachineMetaTileEntity("arc_furnace.lv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(91, new SimpleMachineMetaTileEntity("arc_furnace.mv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(92, new SimpleMachineMetaTileEntity("arc_furnace.hv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(93, new SimpleMachineMetaTileEntity("arc_furnace.ev", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(94, new SimpleMachineMetaTileEntity("arc_furnace.iv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(95, new SimpleMachineMetaTileEntity("arc_furnace.luv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(96, new SimpleMachineMetaTileEntity("arc_furnace.zpm", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(97, new SimpleMachineMetaTileEntity("arc_furnace.uv", RecipeMaps.ARC_FURNACE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(100, new SimpleMachineMetaTileEntity("assembler.lv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(101, new SimpleMachineMetaTileEntity("assembler.mv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(102, new SimpleMachineMetaTileEntity("assembler.hv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(103, new SimpleMachineMetaTileEntity("assembler.ev", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(104, new SimpleMachineMetaTileEntity("assembler.iv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(105, new SimpleMachineMetaTileEntity("assembler.luv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(106, new SimpleMachineMetaTileEntity("assembler.zpm", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(107, new SimpleMachineMetaTileEntity("assembler.uv", RecipeMaps.ASSEMBLER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(110, new SimpleMachineMetaTileEntity("autoclave.lv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(111, new SimpleMachineMetaTileEntity("autoclave.mv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(112, new SimpleMachineMetaTileEntity("autoclave.hv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(113, new SimpleMachineMetaTileEntity("autoclave.ev", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(114, new SimpleMachineMetaTileEntity("autoclave.iv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(115, new SimpleMachineMetaTileEntity("autoclave.luv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(116, new SimpleMachineMetaTileEntity("autoclave.zpm", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(117, new SimpleMachineMetaTileEntity("autoclave.uv", RecipeMaps.AUTOCLAVE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(120, new SimpleMachineMetaTileEntity("bender.lv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(121, new SimpleMachineMetaTileEntity("bender.mv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(122, new SimpleMachineMetaTileEntity("bender.hv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(123, new SimpleMachineMetaTileEntity("bender.ev", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(124, new SimpleMachineMetaTileEntity("bender.iv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(125, new SimpleMachineMetaTileEntity("bender.luv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(126, new SimpleMachineMetaTileEntity("bender.zpm", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(127, new SimpleMachineMetaTileEntity("bender.uv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(130, new SimpleMachineMetaTileEntity("brewery.lv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(131, new SimpleMachineMetaTileEntity("brewery.mv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(132, new SimpleMachineMetaTileEntity("brewery.hv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(133, new SimpleMachineMetaTileEntity("brewery.ev", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(134, new SimpleMachineMetaTileEntity("brewery.iv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(135, new SimpleMachineMetaTileEntity("brewery.luv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(136, new SimpleMachineMetaTileEntity("brewery.zpm", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(137, new SimpleMachineMetaTileEntity("brewery.uv", RecipeMaps.BREWING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(140, new SimpleMachineMetaTileEntity("canner.lv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(141, new SimpleMachineMetaTileEntity("canner.mv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(142, new SimpleMachineMetaTileEntity("canner.hv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(143, new SimpleMachineMetaTileEntity("canner.ev", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(144, new SimpleMachineMetaTileEntity("canner.iv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(145, new SimpleMachineMetaTileEntity("canner.luv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(146, new SimpleMachineMetaTileEntity("canner.zpm", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(147, new SimpleMachineMetaTileEntity("canner.uv", RecipeMaps.CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(150, new SimpleMachineMetaTileEntity("centrifuge.lv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(151, new SimpleMachineMetaTileEntity("centrifuge.mv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(152, new SimpleMachineMetaTileEntity("centrifuge.hv", RecipeMaps.CENTRIFUGE_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));

        GregTechAPI.registerMetaTileEntity(160, new SimpleMachineMetaTileEntity("circuit_assembler.lv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1)); // todo recipemap
        GregTechAPI.registerMetaTileEntity(161, new SimpleMachineMetaTileEntity("circuit_assembler.mv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(162, new SimpleMachineMetaTileEntity("circuit_assembler.hv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(163, new SimpleMachineMetaTileEntity("circuit_assembler.ev", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(164, new SimpleMachineMetaTileEntity("circuit_assembler.iv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(165, new SimpleMachineMetaTileEntity("circuit_assembler.luv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(166, new SimpleMachineMetaTileEntity("circuit_assembler.zpm", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(167, new SimpleMachineMetaTileEntity("circuit_assembler.uv", RecipeMaps.BENDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(180, new SimpleMachineMetaTileEntity("chemical_bath.lv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(181, new SimpleMachineMetaTileEntity("chemical_bath.mv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(182, new SimpleMachineMetaTileEntity("chemical_bath.hv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(183, new SimpleMachineMetaTileEntity("chemical_bath.ev", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(184, new SimpleMachineMetaTileEntity("chemical_bath.iv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(185, new SimpleMachineMetaTileEntity("chemical_bath.luv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(186, new SimpleMachineMetaTileEntity("chemical_bath.zpm", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(187, new SimpleMachineMetaTileEntity("chemical_bath.uv", RecipeMaps.CHEMICAL_BATH_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(190, new SimpleMachineMetaTileEntity("chemical_reactor.lv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(191, new SimpleMachineMetaTileEntity("chemical_reactor.mv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(192, new SimpleMachineMetaTileEntity("chemical_reactor.hv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(193, new SimpleMachineMetaTileEntity("chemical_reactor.ev", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(194, new SimpleMachineMetaTileEntity("chemical_reactor.iv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(195, new SimpleMachineMetaTileEntity("chemical_reactor.luv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(196, new SimpleMachineMetaTileEntity("chemical_reactor.zpm", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(197, new SimpleMachineMetaTileEntity("chemical_reactor.uv", RecipeMaps.CHEMICAL_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(210, new SimpleMachineMetaTileEntity("compressor.lv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(211, new SimpleMachineMetaTileEntity("compressor.mv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(212, new SimpleMachineMetaTileEntity("compressor.hv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.ev", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.iv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.luv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.zpm", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(213, new SimpleMachineMetaTileEntity("compressor.uv", RecipeMaps.COMPRESSOR_RECIPES, Textures.COMPRESSOR_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(220, new SimpleMachineMetaTileEntity("cutter.lv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(221, new SimpleMachineMetaTileEntity("cutter.mv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(222, new SimpleMachineMetaTileEntity("cutter.hv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(223, new SimpleMachineMetaTileEntity("cutter.ev", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(224, new SimpleMachineMetaTileEntity("cutter.iv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(225, new SimpleMachineMetaTileEntity("cutter.luv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(226, new SimpleMachineMetaTileEntity("cutter.zpm", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(227, new SimpleMachineMetaTileEntity("cutter.uv", RecipeMaps.CUTTER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(230, new SimpleMachineMetaTileEntity("distillery.lv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(231, new SimpleMachineMetaTileEntity("distillery.mv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(232, new SimpleMachineMetaTileEntity("distillery.hv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(233, new SimpleMachineMetaTileEntity("distillery.ev", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(234, new SimpleMachineMetaTileEntity("distillery.iv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(235, new SimpleMachineMetaTileEntity("distillery.luv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(236, new SimpleMachineMetaTileEntity("distillery.zpm", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(237, new SimpleMachineMetaTileEntity("distillery.uv", RecipeMaps.DISTILLERY_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(240, new SimpleMachineMetaTileEntity("electrolyzer.lv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(241, new SimpleMachineMetaTileEntity("electrolyzer.mv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(242, new SimpleMachineMetaTileEntity("electrolyzer.hv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(243, new SimpleMachineMetaTileEntity("electrolyzer.ev", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(244, new SimpleMachineMetaTileEntity("electrolyzer.iv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(245, new SimpleMachineMetaTileEntity("electrolyzer.luv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(246, new SimpleMachineMetaTileEntity("electrolyzer.zpm", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(247, new SimpleMachineMetaTileEntity("electrolyzer.uv", RecipeMaps.ELECTROLYZER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(250, new SimpleMachineMetaTileEntity("electromagnetic_separator.lv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(251, new SimpleMachineMetaTileEntity("electromagnetic_separator.mv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(252, new SimpleMachineMetaTileEntity("electromagnetic_separator.hv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(253, new SimpleMachineMetaTileEntity("electromagnetic_separator.ev", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(254, new SimpleMachineMetaTileEntity("electromagnetic_separator.iv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(255, new SimpleMachineMetaTileEntity("electromagnetic_separator.luv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(256, new SimpleMachineMetaTileEntity("electromagnetic_separator.zpm", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(257, new SimpleMachineMetaTileEntity("electromagnetic_separator.uv", RecipeMaps.ELECTROMAGNETIC_SEPARATOR_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(260, new SimpleMachineMetaTileEntity("extractor.lv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(261, new SimpleMachineMetaTileEntity("extractor.mv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(262, new SimpleMachineMetaTileEntity("extractor.hv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(263, new SimpleMachineMetaTileEntity("extractor.ev", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(264, new SimpleMachineMetaTileEntity("extractor.iv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(265, new SimpleMachineMetaTileEntity("extractor.luv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(266, new SimpleMachineMetaTileEntity("extractor.zpm", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(267, new SimpleMachineMetaTileEntity("extractor.uv", RecipeMaps.EXTRACTOR_RECIPES, Textures.EXTRACTOR_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(270, new SimpleMachineMetaTileEntity("extruder.lv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(271, new SimpleMachineMetaTileEntity("extruder.mv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(272, new SimpleMachineMetaTileEntity("extruder.hv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(273, new SimpleMachineMetaTileEntity("extruder.ev", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(274, new SimpleMachineMetaTileEntity("extruder.iv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(275, new SimpleMachineMetaTileEntity("extruder.luv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(276, new SimpleMachineMetaTileEntity("extruder.zpm", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(277, new SimpleMachineMetaTileEntity("extruder.uv", RecipeMaps.EXTRUDER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(280, new SimpleMachineMetaTileEntity("fermenter.lv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(281, new SimpleMachineMetaTileEntity("fermenter.mv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(282, new SimpleMachineMetaTileEntity("fermenter.hv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(283, new SimpleMachineMetaTileEntity("fermenter.ev", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(284, new SimpleMachineMetaTileEntity("fermenter.iv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(285, new SimpleMachineMetaTileEntity("fermenter.luv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(286, new SimpleMachineMetaTileEntity("fermenter.zpm", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(287, new SimpleMachineMetaTileEntity("fermenter.uv", RecipeMaps.FERMENTING_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(290, new SimpleMachineMetaTileEntity("fluid_canner.lv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(291, new SimpleMachineMetaTileEntity("fluid_canner.mv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(292, new SimpleMachineMetaTileEntity("fluid_canner.hv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(293, new SimpleMachineMetaTileEntity("fluid_canner.ev", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(294, new SimpleMachineMetaTileEntity("fluid_canner.iv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(295, new SimpleMachineMetaTileEntity("fluid_canner.luv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(296, new SimpleMachineMetaTileEntity("fluid_canner.zpm", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(297, new SimpleMachineMetaTileEntity("fluid_canner.uv", RecipeMaps.FLUID_CANNER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(300, new SimpleMachineMetaTileEntity("fluid_extractor.lv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(301, new SimpleMachineMetaTileEntity("fluid_extractor.mv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(302, new SimpleMachineMetaTileEntity("fluid_extractor.hv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(303, new SimpleMachineMetaTileEntity("fluid_extractor.ev", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(304, new SimpleMachineMetaTileEntity("fluid_extractor.iv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(305, new SimpleMachineMetaTileEntity("fluid_extractor.luv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(306, new SimpleMachineMetaTileEntity("fluid_extractor.zpm", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(307, new SimpleMachineMetaTileEntity("fluid_extractor.uv", RecipeMaps.FLUID_EXTRACTION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(310, new SimpleMachineMetaTileEntity("fluid_heater.lv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(311, new SimpleMachineMetaTileEntity("fluid_heater.mv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(312, new SimpleMachineMetaTileEntity("fluid_heater.hv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(313, new SimpleMachineMetaTileEntity("fluid_heater.ev", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(314, new SimpleMachineMetaTileEntity("fluid_heater.iv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(315, new SimpleMachineMetaTileEntity("fluid_heater.luv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(316, new SimpleMachineMetaTileEntity("fluid_heater.zpm", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(317, new SimpleMachineMetaTileEntity("fluid_heater.uv", RecipeMaps.FLUID_HEATER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(320, new SimpleMachineMetaTileEntity("fluid_solidifier.lv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(321, new SimpleMachineMetaTileEntity("fluid_solidifier.mv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(322, new SimpleMachineMetaTileEntity("fluid_solidifier.hv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(323, new SimpleMachineMetaTileEntity("fluid_solidifier.ev", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(324, new SimpleMachineMetaTileEntity("fluid_solidifier.iv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(325, new SimpleMachineMetaTileEntity("fluid_solidifier.luv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(326, new SimpleMachineMetaTileEntity("fluid_solidifier.zpm", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(327, new SimpleMachineMetaTileEntity("fluid_solidifier.uv", RecipeMaps.FLUID_SOLIDFICATION_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        GregTechAPI.registerMetaTileEntity(330, new SimpleMachineMetaTileEntity("forge_hammer.lv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 1));
        GregTechAPI.registerMetaTileEntity(331, new SimpleMachineMetaTileEntity("forge_hammer.mv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 2));
        GregTechAPI.registerMetaTileEntity(332, new SimpleMachineMetaTileEntity("forge_hammer.hv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 3));
        GregTechAPI.registerMetaTileEntity(333, new SimpleMachineMetaTileEntity("forge_hammer.ev", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 4));
        GregTechAPI.registerMetaTileEntity(334, new SimpleMachineMetaTileEntity("forge_hammer.iv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 5));
        GregTechAPI.registerMetaTileEntity(335, new SimpleMachineMetaTileEntity("forge_hammer.luv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 6));
        GregTechAPI.registerMetaTileEntity(336, new SimpleMachineMetaTileEntity("forge_hammer.zpm", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 7));
        GregTechAPI.registerMetaTileEntity(337, new SimpleMachineMetaTileEntity("forge_hammer.uv", RecipeMaps.FORGE_HAMMER_RECIPES, Textures.ALLOY_SMELTER_OVERLAY, 8));

        // lv
        // mv
        // hv
        // ev
        // iv
        // luv
        // zpm
        // uv
    }
}
