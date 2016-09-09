package gregtech.common.render.newitems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.io.InputStreamReader;

@SideOnly(Side.CLIENT)
public class ModelUtil {

    public static ItemCameraTransforms DEFAULT_TRANSFORMS = getTransformsFromModel("models/item/generated.json");
    public static ItemCameraTransforms HANDHELD_TRANSFORMS = getTransformsFromModel("models/item/handheld.json");
    public static ItemCameraTransforms BLOCK_TRANSFORMS = getTransformsFromModel("models/block/block.json");

    public static ItemCameraTransforms getTransformsFromModel(String path) {
        try {
            ResourceLocation location = new ResourceLocation(path);
            IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(location);
            try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                ModelBlock modelBlock = ModelBlock.deserialize(reader);
                return modelBlock.getAllTransforms();
            }
        } catch (IOException notFound) {}
        return ItemCameraTransforms.DEFAULT;
    }

}