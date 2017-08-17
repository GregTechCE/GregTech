package gregtech.api.unification.material.model;

import com.google.common.collect.ImmutableList;
import gregtech.api.model.SimpleCubeModel;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;

public class MaterialModelLoader implements ICustomModelLoader {

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(".mat");
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        String path = modelLocation.getResourcePath();
        path = path.substring(0, path.length() - 4); //remove .mat
        if(path.startsWith("models/")) path = path.substring(7); //remove models/
        String[] pathParts = path.split("/"); // icon_set/foil/iron
        MaterialIconType iconType = MaterialIconType.values.get(pathParts[1]);
        Material material = Material.MATERIAL_REGISTRY.getObject(pathParts[2]);
        if(iconType != null && material != null) {
            if(iconType.isBlock) {
                return new ItemLayerModel(ImmutableList.of(
                        iconType.getItemPath(material.materialIconSet),
                        iconType.getItemOverlayPath(material.materialIconSet)));
            } else {
                return new SimpleCubeModel(
                        iconType.getBlockPath(material.materialIconSet),
                        material.materialRGB, 0, iconType.ambientOcclusion);
            }
        } else {
            throw new NullPointerException(modelLocation.toString());
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
    }

}
