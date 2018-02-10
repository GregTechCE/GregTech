package gregtech.common.metatileentities;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.factory.MetaTileEntityFactory;
import gregtech.api.metatileentity.factory.TieredMetaTileEntityFactory;
import gregtech.api.metatileentity.factory.WorkableSteamMetaTileEntityFactory;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTResourceLocation;
import gregtech.common.blocks.machines.BlockMachine;
import gregtech.common.blocks.tileentity.TileEntityCableEmitter;
import gregtech.common.metatileentities.steam.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaTileEntities {

    public static void init() {
        GTLog.logger.info("Registering MetaTileEntities");

        GameRegistry.registerTileEntity(GregtechTileEntity.class, "gregtech_tile_entity");
        GameRegistry.registerTileEntity(TileEntityCableEmitter.class, "gregtech_cable_emitter");

        GregTechAPI.METATILEENTITY_REGISTRY.register(1, "steam_extractor", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamExtractor.class, new GTResourceLocation("steam_extractor"), RecipeMap.EXTRACTOR_RECIPES));
        GregTechAPI.METATILEENTITY_REGISTRY.register(2, "steam_macerator", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamMacerator.class, new GTResourceLocation("steam_macerator"), RecipeMap.MACERATOR_RECIPES));
        GregTechAPI.METATILEENTITY_REGISTRY.register(3, "steam_compressor", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamCompressor.class, new GTResourceLocation("steam_compressor"), RecipeMap.COMPRESSOR_RECIPES));
        GregTechAPI.METATILEENTITY_REGISTRY.register(4, "steam_alloy_smelter", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamAlloySmelter.class, new GTResourceLocation("steam_alloy_smelter"), RecipeMap.ALLOY_SMELTER_RECIPES));
        GregTechAPI.METATILEENTITY_REGISTRY.register(5, "steam_hammer", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamHammer.class, new GTResourceLocation("steam_hammer"), RecipeMap.HAMMER_RECIPES));
        GregTechAPI.METATILEENTITY_REGISTRY.register(6, "steam_furnace", new WorkableSteamMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamFurnace.class, new GTResourceLocation("steam_furnace"), RecipeMap.FURNACE_RECIPES));

        GregTechAPI.METATILEENTITY_REGISTRY.register(7, "steam_boiler", new MetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], SteamBoiler.class, new GTResourceLocation("steam_boiler")));

        GregTechAPI.METATILEENTITY_REGISTRY.register(8, "mte_test_generator", new TieredMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[0], TestGeneratorMTE.class, new GTResourceLocation("mte_test_generator"), 1));
    }
}
