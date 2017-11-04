package gregtech.common.metatileentities;

import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.factory.MetaTileEntityFactory;
import gregtech.api.metatileentity.factory.WorkableMetaTileEntityFactory;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTResourceLocation;
import gregtech.common.blocks.BlockMachine;
import gregtech.common.blocks.properties.PropertyString;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaTileEntities {

    public static void init() {
//        GTLog.logger.info("Registering TileEntities.");

        GameRegistry.registerTileEntity(GregtechTileEntity.class, "gregtech_tile_entity");
        GregTechAPI.METATILEENTITY_REGISTRY.register(0, "mte_test", new WorkableMetaTileEntityFactory<>(BlockMachine.ToolClass.WRENCH, 1, new String[]{"test_desc"}, TestMTE.class, new GTResourceLocation("mte_test"), 1, RecipeMap.FURNACE_RECIPES));

        BlockMachine.META_TYPE = PropertyString.create("meta_type", GregTechAPI.METATILEENTITY_REGISTRY.getKeys());
    }
}
