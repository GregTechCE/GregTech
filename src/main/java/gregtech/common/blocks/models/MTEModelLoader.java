package gregtech.common.blocks.models;

import com.google.common.collect.ImmutableMap;
import gregtech.api.GTValues;
import gregtech.api.util.GTResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.LinkedHashMap;
import java.util.Map;

public enum MTEModelLoader implements ICustomModelLoader {
    INSTANCE;

    private Map<ResourceLocation, IModel> modelMap;

    MTEModelLoader() {
        this.modelMap = new LinkedHashMap<>();
    }

    public void addMTE2ModelMapping(ResourceLocation mteModelLocation, IModel model) {
        modelMap.put(mteModelLocation, model);
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(GTValues.MODID) &&
            modelMap.containsKey(new GTResourceLocation(modelLocation.getResourcePath()));
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return modelMap.get(new GTResourceLocation(modelLocation.getResourcePath()));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }
}
