package gregtech.common.metatileentities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.factory.MetaTileEntityFactory;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.BlockMachine;
import gregtech.common.blocks.properties.PropertyString;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MetaTileEntities {

    public static void init() {
//        GTLog.logger.info("Registering TileEntities.");

        GameRegistry.registerTileEntity(GregtechTileEntity.class, "gregtech_tile_entity");
        MetaTileEntityFactory<TestMTE> mteFactory = new MetaTileEntityFactory<>(BlockMachine.ToolClass.AXE, 1, new String[]{"test_desc"}, TestMTE.class, new ResourceLocation(GTValues.MODID, "block/basic_mte"), null);
        GregTechAPI.METATILEENTITY_REGISTRY.register(0, "mte_test", mteFactory);

        BlockMachine.META_TYPE = PropertyString.create("meta_type", GregTechAPI.METATILEENTITY_REGISTRY.getKeys());
    }
}
