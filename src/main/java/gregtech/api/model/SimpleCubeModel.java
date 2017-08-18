package gregtech.api.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimpleCubeModel implements IModel, IRetexturableModel, IModelCustomData {

    private final ResourceLocation textureLocation;
    private final int rgbColor;
    private final boolean ambientOccasion;

    public SimpleCubeModel(ResourceLocation textureLocation, int rgbColor, boolean ambientOccasion) {
        this.textureLocation = textureLocation;
        this.rgbColor = rgbColor;
        this.ambientOccasion = ambientOccasion;
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures) {
        if(textures.containsKey("all") && !textures.get("all").isEmpty()) {
            return new SimpleCubeModel(new ResourceLocation(textures.get("all")), rgbColor, true);
        }
        return this;
    }

    @Override
    public IModel process(ImmutableMap<String, String> customData) {
        if(customData.containsKey("color")) {
            return new SimpleCubeModel(textureLocation, Integer.parseInt(customData.get("color")), ambientOccasion);
        }
        return this;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.of(textureLocation);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> map = IPerspectiveAwareModel.MapWrapper.getTransforms(state);
        TextureAtlasSprite sprite = bakedTextureGetter.apply(textureLocation);
        return new BakedSimpleCubeModel(format, sprite, rgbColor, ambientOccasion, map);
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

    private static class BakedSimpleCubeModel extends AbstractBakedModel implements IPerspectiveAwareModel {

        private final ImmutableMap<EnumFacing, ImmutableList<BakedQuad>> quads;
        private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
        private final boolean ambientOcclusion;
        private final TextureAtlasSprite textureAtlasSprite;

        public BakedSimpleCubeModel(VertexFormat vertexFormat, TextureAtlasSprite sprite, int rgbaColor, boolean ambientOcclusion, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
            super(vertexFormat);
            this.ambientOcclusion = ambientOcclusion;
            this.textureAtlasSprite = sprite;
            this.transforms = transforms;
            ImmutableMap.Builder<EnumFacing, ImmutableList<BakedQuad>> builder = ImmutableMap.builder();
            for(EnumFacing side : EnumFacing.VALUES) {
                ImmutableList.Builder<BakedQuad> sideQuads = ImmutableList.builder();
                switch (side) {
                    case WEST:
                    case EAST:
                        int x = side == EnumFacing.WEST ? 0 : 1;
                        sideQuads.add(createQuad(new Vec3d(x, 0, 0),
                                new Vec3d(x, 0, 1),
                                new Vec3d(x, 1, 1),
                                new Vec3d(x, 1, 0),
                                sprite, side, rgbaColor));
                        break;
                    case NORTH:
                    case SOUTH:
                        int z = side == EnumFacing.NORTH ? 0 : 1;
                        sideQuads.add(createQuad(new Vec3d(0, 0, z),
                                new Vec3d(1, 0, z),
                                new Vec3d(1, 1, z),
                                new Vec3d(0, 1, z),
                                sprite, side, rgbaColor));
                        break;
                    case DOWN:
                    case UP:
                        int y = side == EnumFacing.DOWN ? 0 : 1;
                        sideQuads.add(createQuad(new Vec3d(0, y, 0),
                                new Vec3d(1, y, 0),
                                new Vec3d(1, y, 1),
                                new Vec3d(0, y, 1),
                                sprite, side, rgbaColor));
                        break;
                }
                builder.put(side, sideQuads.build());
            }
            this.quads = builder.build();
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side == null) {
                return Collections.emptyList();
            }
            return quads.get(side);
        }

        @Override
        public boolean isAmbientOcclusion() {
            return ambientOcclusion;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return textureAtlasSprite;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return ItemOverrideList.NONE;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            return MapWrapper.handlePerspective(this, transforms, cameraTransformType);
        }

    }

}
