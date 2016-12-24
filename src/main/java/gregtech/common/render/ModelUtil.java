package gregtech.common.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple util for gathering transforms from external model jsons
 */
@SideOnly(Side.CLIENT)
public class ModelUtil {

    public static ItemCameraTransforms DEFAULT_TRANSFORMS = getTransformsFromModel("models/item/generated.json");
    public static ItemCameraTransforms HANDHELD_TRANSFORMS = getTransformsFromModel("models/item/handheld.json");
    public static ItemCameraTransforms BLOCK_TRANSFORMS = getTransformsFromModel("models/block/block.json");

    static {
        ItemTransformVec3f vec = HANDHELD_TRANSFORMS.ground;
        ItemTransformVec3f newVec = new ItemTransformVec3f(vec.rotation, vec.translation, new Vector3f(0.5f, 0.5f, 0.5f));
        HANDHELD_TRANSFORMS = new ItemCameraTransforms(
                HANDHELD_TRANSFORMS.thirdperson_left,
                HANDHELD_TRANSFORMS.thirdperson_right,
                HANDHELD_TRANSFORMS.firstperson_left,
                HANDHELD_TRANSFORMS.firstperson_right,
                HANDHELD_TRANSFORMS.head,
                HANDHELD_TRANSFORMS.gui,
                newVec,
                HANDHELD_TRANSFORMS.fixed
        );
    }

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