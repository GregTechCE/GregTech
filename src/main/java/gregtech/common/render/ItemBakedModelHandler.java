package gregtech.common.render;

import gregtech.api.GTValues;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = GTValues.MODID, value = Side.CLIENT)
public class ItemBakedModelHandler {

    private static class RegistrarEntry {
        private ModelResourceLocation resourceLocation;
        private BiFunction<IBakedModel, Function<ModelResourceLocation, IBakedModel>, IBakedModel> modelMaker;

        public RegistrarEntry(ModelResourceLocation resourceLocation, BiFunction<IBakedModel, Function<ModelResourceLocation, IBakedModel>, IBakedModel> modelMaker) {
            this.resourceLocation = resourceLocation;
            this.modelMaker = modelMaker;
        }
    }

    private static final List<RegistrarEntry> itemModelOverrides = new ArrayList<>();

    public static void addModelOverride(ModelResourceLocation resourceLocation, BiFunction<IBakedModel, Function<ModelResourceLocation, IBakedModel>, IBakedModel> modelCreator) {
        itemModelOverrides.add(new RegistrarEntry(resourceLocation, modelCreator));
    }

    @SubscribeEvent
    public static void onModelsBake(ModelBakeEvent event) {
        for (RegistrarEntry registrarEntry : itemModelOverrides) {
            ModelResourceLocation resourceLocation = registrarEntry.resourceLocation;
            IBakedModel bakedModel = event.getModelRegistry().getObject(resourceLocation);
            Function<ModelResourceLocation, IBakedModel> modelGetter = event.getModelRegistry()::getObject;
            event.getModelRegistry().putObject(resourceLocation, registrarEntry.modelMaker.apply(bakedModel, modelGetter));
        }
    }
}
