package gregtech.common.blocks.models;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SideOnly(Side.CLIENT)
public class MetaTileEntityModel implements IModel {

    @FunctionalInterface
    public interface BakedModelSupplier {
        IBakedModel get(IModelState s, VertexFormat v, Map<String, TextureAtlasSprite> m);
    }

    private final Map<String, ResourceLocation> textures;
    private final BakedModelSupplier bakedModelSupplier;

    public MetaTileEntityModel(Map<String, ResourceLocation> textures,
                               BakedModelSupplier bakedModelSupplier) {
        this.textures = ImmutableMap.copyOf(textures);
        this.bakedModelSupplier = bakedModelSupplier;
    }

    public MetaTileEntityModel(BakedModelSupplier bakedModelSupplier) {
        this(new HashMap<>(), bakedModelSupplier);
    }

    @Override
    public MetaTileEntityModel retexture(ImmutableMap<String, String> textures) {
        HashMap<String, ResourceLocation> textureMap = new HashMap<>(this.textures);

        textures.forEach((key, val) -> textureMap.replace(key, new ResourceLocation(val)));

        return new MetaTileEntityModel(textureMap, this.bakedModelSupplier);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableMap.Builder<String, TextureAtlasSprite> builder = ImmutableMap.builder();

        textures.forEach((s, location) -> builder.put(s, bakedTextureGetter.apply(location)));

        return bakedModelSupplier.get(state, format, builder.build());
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.copyOf(textures.values());
    }
}
