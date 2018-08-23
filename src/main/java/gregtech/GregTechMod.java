package gregtech;

import codechicken.lib.CodeChickenLib;
import crafttweaker.CraftTweakerAPI;
import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.items.gui.PlayerInventoryUIFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.multipart.PipeMultipartFactory;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.CommonProxy;
import gregtech.common.ConfigHolder;
import gregtech.common.MetaEntities;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockFrameFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.command.GregTechCommand;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.worldgen.WorldGenRubberTree;
import gregtech.integration.theoneprobe.TheOneProbeCompatibility;
import gregtech.loaders.postload.DungeonLootLoader;
import gregtech.loaders.preload.PipeLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     acceptedMinecraftVersions = "[1.12,1.13)",
     dependencies = "required:forge@[14.23.3.2702,);" + CodeChickenLib.MOD_VERSION_DEP + "after:forestry;after:forgemultipartcbe;after:jei@[4.8.6,);after:crafttweaker;")
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

        NetworkHandler.init();
        MetaTileEntityUIFactory.INSTANCE.init();
        PlayerInventoryUIFactory.INSTANCE.init();
        SimpleCapabilityManager.init();
        OreDictUnifier.init();
        Materials.register();

        if(Loader.isModLoaded(GTValues.MODID_CT)) {
            GTLog.logger.info("Running early CraftTweaker initialization scripts...");
            runEarlyCraftTweakerScripts();
        }

        //freeze material registry before processing items, blocks and fluids
        Material.freezeRegistry();

        PipeLoader.init();
        MetaBlocks.init();
        MetaItems.init();
        MetaFluids.init();
        MetaTileEntities.init();
        MetaEntities.init();
        MinecraftForge.EVENT_BUS.register(WorldPipeNet.class);

        gregtechproxy.onPreLoad();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        gregtechproxy.onLoad();

        if (RecipeMap.isFoundInvalidRecipe()) {
            GTLog.logger.fatal("Seems like invalid recipe was found. Loading will not continue.");
            throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
        }

        if(Loader.isModLoaded(GTValues.MODID_FMP)) {
            GTLog.logger.info("ForgeMultiPart found. Enabling integration...");
            PipeMultipartFactory.registerMultipartFactory();
        }

        if(Loader.isModLoaded(GTValues.MODID_TOP)) {
            GTLog.logger.info("TheOneProbe found. Enabling integration...");
            TheOneProbeCompatibility.registerCompatibility();
        }

        WorldGenRegistry.INSTANCE.initializeRegistry();
        if(!ConfigHolder.disableRubberTreeGeneration) {
            GameRegistry.registerWorldGenerator(new WorldGenRubberTree(), 10000);
        }

        DungeonLootLoader.init();
    }

    @Method(modid = GTValues.MODID_CT)
    private void runEarlyCraftTweakerScripts() {
        CraftTweakerAPI.tweaker.loadScript(false, "gregtech");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        gregtechproxy.onPostLoad();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new GregTechCommand());
    }
}