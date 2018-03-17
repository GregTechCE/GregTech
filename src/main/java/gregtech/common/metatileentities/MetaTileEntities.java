package gregtech.common.metatileentities;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.tileentity.TileEntityCableEmitter;
import gregtech.common.metatileentities.steam.*;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaTileEntities {

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        GameRegistry.registerTileEntity(TileEntityCableEmitter.class, "gregtech_cable_emitter");

        TileEntityChestRenderer

        BlockMachine.registerMetaTileEntity("steam_boiler_bronze", SteamBoiler.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_boiler_steel", SteamBoiler.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_extractor_bronze", SteamExtractor.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_extractor_steel", SteamExtractor.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_compressor_bronze", SteamCompressor.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_compressor_steel", SteamCompressor.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_macerator_bronze", SteamMacerator.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_macerator_steel", SteamMacerator.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_hammer_bronze", SteamHammer.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_hammer_steel", SteamHammer.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_furnace_bronze", SteamFurnace.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_furnace_steel", SteamFurnace.Steel.class);

        BlockMachine.registerMetaTileEntity("steam_alloy_smelter_bronze", SteamAlloySmelter.Bronze.class);
        BlockMachine.registerMetaTileEntity("steam_alloy_smelter_steel", SteamAlloySmelter.Steel.class);
    }
}
