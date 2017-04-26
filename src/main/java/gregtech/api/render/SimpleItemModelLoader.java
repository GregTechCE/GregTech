package gregtech.api.render;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.MaterialSet;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;

public class SimpleItemModelLoader implements ICustomModelLoader {

    public static ModelResourceLocation registerX32ModelForGeneration(Item item, MaterialSet materialSet, OrePrefixes orePrefix) {
        return registerModelForGeneration(item, "materialicons/" + materialSet.name() + "/" + orePrefix.name());
    }

    public static ModelResourceLocation registerModelForGeneration(Item item, String path) {
        ModelLoader.registerItemVariants(item, new ResourceLocation(GT_Values.MOD_ID + ":generated/" + path));
        return new ModelResourceLocation(GT_Values.MOD_ID + ":generated/" + path, "inventory");
        BlockRendererDispatcher
    }


    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(GT_Values.MOD_ID) &&
                modelLocation.getResourcePath().startsWith("models/item/generated/");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        String resourcePath = modelLocation.getResourcePath().replace("models/item/generated/", "");
        if(resourcePath.startsWith("materialicons/")) {
            return new ItemLayerModel(ImmutableList.of(
                    new ResourceLocation(modelLocation.getResourceDomain(), "items:" + resourcePath),
                    new ResourceLocation(modelLocation.getResourceDomain(), "items:" + resourcePath + "_OVERLAY")
            ));
        } else {
            return new ItemLayerModel(ImmutableList.of(
                    new ResourceLocation(modelLocation.getResourceDomain(), "items:" + resourcePath)
            ));
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

}
