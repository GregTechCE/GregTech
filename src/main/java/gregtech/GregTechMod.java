package gregtech;

import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.common.CommonProxy;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.loaders.load.FuelLoader;
import gregtech.loaders.oreprocessing.OreProcessingHandler;
import gregtech.loaders.postload.DungeonLootLoader;
import gregtech.loaders.preload.MaterialInfoLoader;
import gregtech.loaders.preload.OreDictionaryLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     version = "@VERSION@",
     acceptedMinecraftVersions = "[1.12,1.13)",
     dependencies = "required:codechickenlib;before:forestry")
public class GregTechMod {

    static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            ResourcePackHook.init();
            BlockOreFactory.init();
            BlockCompressedFactory.init();
        }
    }

    @Mod.Instance(GTValues.MODID)
    public static GregTechMod instance;

    @SidedProxy(modId = GTValues.MODID, clientSide = "gregtech.common.ClientProxy", serverSide = "gregtech.common.CommonProxy")
    public static CommonProxy gregtechproxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        GTLog.init(event.getModLog());

        GTLog.logger.info("PreInit-Phase started!");

        NetworkHandler.init();
        MetaTileEntityUIFactory.INSTANCE.init();
        SimpleCapabilityManager.init();

        OreDictUnifier.init();
        new OreProcessingHandler().registerProcessing();

        MetaBlocks.init();
        MetaItems.init();
        MetaFluids.init();
        MetaTileEntities.init();

        gregtechproxy.onPreLoad();

        GTLog.logger.info("PreInit-Phase finished!");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GTLog.logger.info("Init-Phase started!");

        OreDictionaryLoader.init();
        MetaItems.registerOreDict();
        MetaBlocks.registerOreDict();
        MaterialInfoLoader.init();
        OrePrefix.runMaterialHandlers();
        MetaItems.registerRecipes();

        gregtechproxy.onLoad();

        if (RecipeMap.foundInvalidRecipe) {
            throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
        }

        Material.init();
        GTLog.logger.info("Init-Phase finished!");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        GTLog.logger.info("PostInit-Phase started!");

        gregtechproxy.onPostLoad();

        FuelLoader.registerFuels();
        DungeonLootLoader.init();
        GTLog.logger.info("PostInit-Phase finished!");
    }
}