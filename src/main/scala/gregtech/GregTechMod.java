package gregtech;

import codechicken.lib.CodeChickenLib;
import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.items.MetaItemUIFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.CommonProxy;
import gregtech.common.ConfigHolder;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockFrameFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.command.GregTechCommand;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.multipart.GTMultipartFactory;
import gregtech.common.worldgen.WorldGenRubberTree;
import gregtech.loaders.load.FuelLoader;
import gregtech.loaders.load.MetaTileEntityLoader;
import gregtech.loaders.load.OreDictionaryLoader;
import gregtech.loaders.oreprocessing.OreProcessingHandler;
import gregtech.loaders.postload.CraftingRecipeLoader;
import gregtech.loaders.postload.DungeonLootLoader;
import gregtech.loaders.postload.MachineRecipeLoader;
import gregtech.loaders.preload.MaterialInfoLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     acceptedMinecraftVersions = "[1.12,1.13)",
     dependencies = CodeChickenLib.MOD_VERSION_DEP + "after:forestry;after:forgemultipartcbe;after:jei@[4.8.6,);")
public class GregTechMod {

    static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            ResourcePackHook.init();
            BlockOreFactory.init();
            BlockCompressedFactory.init();
            BlockFrameFactory.init();
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
        MetaItemUIFactory.INSTANCE.init();
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
        OreProcessingHandler.initializeMetaItems();
        gregtechproxy.onLoad();

        if(Loader.isModLoaded(GTValues.MODID_FMP)) {
            registerForgeMultipartCompat();
        }

        if (RecipeMap.foundInvalidRecipe) {
            throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
        }

        Material.init();
        GTLog.logger.info("Init-Phase finished!");
    }

    private void registerForgeMultipartCompat() {
        GTMultipartFactory.registerFactory();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        GTLog.logger.info("PostInit-Phase started!");

        OrePrefix.runMaterialHandlers();
        FuelLoader.registerFuels();
        MetaItems.registerRecipes();
        MachineRecipeLoader.init();
        CraftingRecipeLoader.init();
        MetaTileEntityLoader.init();

        WorldGenRegistry.INSTANCE.initializeRegistry();
        if(!ConfigHolder.disableRubberTreeGeneration) {
            GameRegistry.registerWorldGenerator(new WorldGenRubberTree(), 10000);
        }
        gregtechproxy.onPostLoad();

        DungeonLootLoader.init();
        GTLog.logger.info("PostInit-Phase finished!");
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new GregTechCommand());
    }
}