package gregtech.common.metatileentities;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.tileentity.TileEntityCableEmitter;
import gregtech.common.metatileentities.steam.*;
import gregtech.common.metatileentities.steam.boiler.SteamCoalBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamLavaBoiler;
import gregtech.common.metatileentities.steam.boiler.SteamSolarBoiler;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
    }
}
