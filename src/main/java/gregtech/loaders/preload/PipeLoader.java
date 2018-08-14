package gregtech.loaders.preload;

import gregtech.api.GTValues;
import gregtech.common.pipelike.CTCableAndPipeRegistry;
import gregtech.common.pipelike.cables.CableFactory;
import gregtech.common.pipelike.fluidpipes.FluidPipeFactory;
import gregtech.common.pipelike.fluidpipes.TypeFluidPipe;
import gregtech.common.pipelike.itempipes.ItemPipeFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static gregtech.api.GTValues.V;
import static gregtech.api.unification.material.MarkerMaterials.Tier;
import static gregtech.api.unification.material.Materials.*;

public class PipeLoader {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(PipeLoader.class);
        if(Loader.isModLoaded(GTValues.MODID_CT)){
            MinecraftForge.EVENT_BUS.register(CTCableAndPipeRegistry.class);
        }
    }

    @SubscribeEvent
    public static void registerDefaultCables(CableFactory.CableRegistryEvent event) {
        event.registerCable(RedAlloy, V[0], 1, 0);

        event.registerCable(Cobalt, V[1], 2, 2);
        event.registerCable(Lead, V[1], 2, 2);
        event.registerCable(Tin, V[1], 1, 1);
        event.registerCable(Zinc, V[1], 1, 1);
        event.registerCable(SolderingAlloy, V[1], 1, 1);

        event.registerCable(Iron, V[2], 2, 3);
        event.registerCable(Nickel, V[2], 3, 3);
        event.registerCable(Cupronickel, V[2], 2, 3);
        event.registerCable(Copper, V[2], 1, 2);
        event.registerCable(AnnealedCopper, V[2], 1, 1);

        event.registerCable(Kanthal, V[3], 4, 3);
        event.registerCable(Gold, V[3], 3, 2);
        event.registerCable(Electrum, V[3], 2, 2);
        event.registerCable(Silver, V[3], 1, 1);

        event.registerCable(Nichrome, V[4], 3, 4);
        event.registerCable(Steel, V[4], 2, 2);
        event.registerCable(BlackSteel, V[4], 3, 2);
        event.registerCable(Titanium, V[4], 4, 2);
        event.registerCable(Aluminium, V[4], 1, 1);

        event.registerCable(Graphene, V[5], 1, 1);
        event.setNotInsulable(Graphene);
        event.registerCable(Osmium, V[5], 4, 2);
        event.registerCable(Platinum, V[5], 2, 1);
        event.registerCable(TungstenSteel, V[5], 3, 2);
        event.registerCable(Tungsten, V[5], 2, 2);

        event.registerCable(HSSG, V[6], 4, 2);
        event.registerCable(NiobiumTitanium, V[6], 4, 2);
        event.registerCable(VanadiumGallium, V[6], 4, 2);
        event.registerCable(YttriumBariumCuprate, V[6], 4, 4);

        event.registerCable(Naquadah, V[7], 2, 4);
        event.registerCable(NaquadahEnriched, V[7], 4, 2);

        event.registerCable(NaquadahAlloy, V[8], 2, 4);
        event.registerCable(Duranium, V[8], 1, 8);

        event.registerCable(Tier.Superconductor, Integer.MAX_VALUE, 4, 0);
        event.setNotInsulable(Tier.Superconductor);
        event.specifyMaterialColor(Tier.Superconductor, 0xDCFAFA);
    }

    @SubscribeEvent
    public static void registerDefaultFluidPipes(FluidPipeFactory.FluidPipeRegistryEvent event) {
        event.registerFluidPipe(Wood, 100, 350, false);
        event.setOnlyMediumSized(Wood);

        event.registerFluidPipe(Copper, 200, 1000);
        event.registerFluidPipe(Bronze, 400, 2000);
        event.registerFluidPipe(Steel, 800, 2500);
        event.registerFluidPipe(StainlessSteel, 1200, 3000);
        event.registerFluidPipe(Titanium, 1600, 5000);
        event.registerFluidPipe(TungstenSteel, 2000, 7500);

        event.registerFluidPipe(Plastic, 1200, 350);
        event.registerFluidPipe(Polytetrafluoroethylene, 2000, 600);

        event.registerFluidPipe(Tier.Ultimate, 24000, 1500);
        event.setOnlyMediumSized(Tier.Ultimate);
        event.specifyMaterialColor(Tier.Ultimate, 0xC80000);

        event.registerFluidPipe(Tier.Superconductor, 800, 100000);
        event.setOnlyMediumSized(Tier.Superconductor);
        event.setIgnored(TypeFluidPipe.PIPE_SMALL, Tier.Superconductor);
        event.setIgnored(TypeFluidPipe.PIPE_LARGE, Tier.Superconductor);
        event.specifyMaterialColor(Tier.Superconductor, 0xFFFF00);
    }

    @SubscribeEvent
    public static void registerDefaultItemPipes(ItemPipeFactory.ItemPipeRegistryEvent event) {
        event.registerItemPipe(Brass, 1);
        event.registerItemPipe(WroughtIron, 1);
        event.registerItemPipe(Nickel, 1);
        event.registerItemPipe(Electrum, 2);
        event.registerItemPipe(Cobalt, 2);
        event.registerItemPipe(Aluminium, 2);
        event.registerItemPipe(Platinum, 4);
        event.registerItemPipe(Osmium, 8);
    }
}
