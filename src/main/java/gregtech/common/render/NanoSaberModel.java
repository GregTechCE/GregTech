package gregtech.common.render;

import codechicken.lib.model.DummyBakedModel;
import codechicken.lib.render.item.CCRenderItem;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Rotation;
import gregtech.common.items.behaviors.ToggleEnergyConsumerBehavior;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.model.IModelState;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.function.Function;

public class NanoSaberModel implements IItemRenderer {

    private IBakedModel originalModel;
    private DummyTransformModel transformModel;
    private IBakedModel activeBaseModel;
    private IBakedModel activeBladeModel;

    public NanoSaberModel(IBakedModel originalModel, Function<ModelResourceLocation, IBakedModel> modelGetter) {
        this.originalModel = originalModel;
        this.transformModel = new DummyTransformModel(originalModel);
        this.activeBaseModel = modelGetter.apply(new ModelResourceLocation("gregtech:metaitems/nano_saber/active_base#inventory"));
        this.activeBladeModel = modelGetter.apply(new ModelResourceLocation("gregtech:metaitems/nano_saber/active_blade#inventory"));
    }

    @Override
    public void renderItem(ItemStack stack, TransformType transformType) {
        this.transformModel.setItemStack(stack);
        //translate back before applying transforms
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        ForgeHooksClient.handleCameraTransforms(transformModel, transformType, false);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        this.transformModel.setItemStack(ItemStack.EMPTY);
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        if (ToggleEnergyConsumerBehavior.isItemActive(stack)) {
            renderItem.renderModel(activeBaseModel, stack);
            int brightness = (int) OpenGlHelper.lastBrightnessY << 16 | (int) OpenGlHelper.lastBrightnessX;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            renderItem.renderModel(activeBladeModel, stack);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightness & 0xFFFF, brightness >>> 16);
        } else {
            renderItem.renderModel(originalModel, stack);
        }
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return originalModel.getParticleTexture();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        CCRenderItem.notifyTransform(cameraTransformType);
        Matrix4f identityMatrix = new Matrix4f();
        identityMatrix.setIdentity();
        return Pair.of(this, identityMatrix);
    }

    @Override
    public IModelState getTransforms() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    private static class DummyTransformModel extends DummyBakedModel {

        private IBakedModel originalModel;
        private ItemStack itemStack;

        public DummyTransformModel(IBakedModel originalModel) {
            this.originalModel = originalModel;
        }

        public void setItemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
            if (itemStack.getSubCompound("Animation") != null) {
                Matrix4 result = new Matrix4();
                float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
                NBTTagCompound tag = itemStack.getSubCompound("Animation");
                float current = tag.getFloat("Cur");
                float old = tag.getFloat("Old");
                float resultRotate = old + (current - old) * partialTicks;

                if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
                    result.translate(-0.5, 0.4, 0.0);
                    result.rotate(Math.toRadians(180.0f), Rotation.axes[0]);
                    result.translate(-0.5, -0.5, -0.5);
                    result.rotate(Math.toRadians(resultRotate), Rotation.axes[2]);
                    result.translate(0.5, 0.5, 0.5);
                    return Pair.of(this, result.toMatrix4f());

                } else if (cameraTransformType == TransformType.FIRST_PERSON_LEFT_HAND) {
                    result.translate(-0.40, 0.4, 0.0);
                    result.rotate(Math.toRadians(180.0f), Rotation.axes[0]);
                    result.translate(-0.5, -0.5, -0.5);
                    result.rotate(Math.toRadians(-100.0f - resultRotate), Rotation.axes[2]);
                    result.translate(0.5, 0.5, 0.5);
                    return Pair.of(this, result.toMatrix4f());
                }
            }
            return Pair.of(this, originalModel.handlePerspective(cameraTransformType).getRight());
        }
    }
}
