package gregtech.common.render.newrenderer;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nullable;
import javax.vecmath.Vector4f;
import java.util.*;

public class GT_IIconProvider_Item_Model implements ICustomModelLoader, IModel, IBakedModel {

    private static GT_IIconProvider_Item_Model INSTANCE = new GT_IIconProvider_Item_Model();
    private static ModelResourceLocation RESOURCE_LOCATION = new ModelResourceLocation("gregtech", "IIconProvider");

    private HashMap<TextureAtlasSprite, List<BakedQuad>> iconsCache = new HashMap<>();
    private HashMap<TextureAtlasSprite, List<BakedQuad>> overlaysCache = new HashMap<>();

    private ItemStack itemStack;
    private VertexFormat vertexFormat;

    //-----------------------------------------
    // IModel
    //-----------------------------------------

    public static void setupItemIcons() {
        TextureMap iconRegisterer = Minecraft.getMinecraft().getTextureMapBlocks();
        for(ResourceLocation itemId : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(itemId);
            if(item instanceof IIconRegister) {
                ((IIconRegister) item).registerIcons(iconRegisterer);
            }
        }
    }

    public static void setupItemModels() {
        ModelLoaderRegistry.registerLoader(INSTANCE);
        for(ResourceLocation itemId : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(itemId);
            if(item instanceof IIconProvider) {
                ModelLoader.registerItemVariants(item, RESOURCE_LOCATION);
                ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
                    @Override
                    public ModelResourceLocation getModelLocation(ItemStack stack) {
                        return RESOURCE_LOCATION;
                    }
                });
            }
        }
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.equals(RESOURCE_LOCATION);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if(modelLocation.equals(RESOURCE_LOCATION))
            return this;
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {}


    //-----------------------------------------
    // IModel
    //-----------------------------------------

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.emptyList();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        vertexFormat = format;
        return this;
    }

    @Override
    public IModelState getDefaultState() {
        return null;
    }



    //-----------------------------------------
    // IBakedModel
    //-----------------------------------------

    /*
    We hacks into ItemRenderer here
    We draw to VertexBuffer instead of wrapping around BakedQuads
     */
    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        //Only one call
        if(side == null && itemStack != null) {
            IIconProvider iconProvider = (IIconProvider) itemStack.getItem();
            IIconContainer iconContainer = iconProvider.getIconContainer(itemStack);
            TextureAtlasSprite textureIcon = iconContainer.getIcon();
            TextureAtlasSprite overlayIcon = iconContainer.getOverlayIcon();
            ArrayList<BakedQuad> resultQuads = new ArrayList<>();
            if(iconsCache.containsKey(textureIcon)) {
                resultQuads.addAll(iconsCache.get(textureIcon));
            } else {
                List<BakedQuad> textureQuads = ItemLayerModel.getQuadsForSprite(0, textureIcon, vertexFormat, Optional.<TRSRTransformation>absent());
                iconsCache.put(textureIcon, textureQuads);
                resultQuads.addAll(textureQuads);
            }
            if(overlayIcon != null) {
                if(overlaysCache.containsKey(overlayIcon)) {
                    resultQuads.addAll(overlaysCache.get(overlayIcon));
                } else {
                    List<BakedQuad> overlayQuads = getOverlayQuads(overlayIcon, 1);
                    overlaysCache.put(overlayIcon, overlayQuads);
                    resultQuads.addAll(overlayQuads);
                }
            }
            return resultQuads;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        if(itemStack != null) {
            IIconProvider iconProvider = (IIconProvider) itemStack.getItem();
            IIconContainer iconContainer = iconProvider.getIconContainer(itemStack);
            return iconContainer.getIcon();
        }
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList(Collections.<ItemOverride>emptyList()) {
            @Override
            public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
                GT_IIconProvider_Item_Model.this.itemStack = stack;
                return GT_IIconProvider_Item_Model.this;
            }
        };
    }

    private List<BakedQuad> getOverlayQuads(TextureAtlasSprite sprite, int tint) {
        ArrayList<BakedQuad> builder = new ArrayList<>();
        // front
        builder.add(buildQuad(vertexFormat, EnumFacing.NORTH, sprite, tint,
                0, 0, 7.48f / 16f, sprite.getMinU(), sprite.getMaxV(),
                0, 1, 7.48f / 16f, sprite.getMinU(), sprite.getMinV(),
                1, 1, 7.48f / 16f, sprite.getMaxU(), sprite.getMinV(),
                1, 0, 7.48f / 16f, sprite.getMaxU(), sprite.getMaxV()
        ));
        // back
        builder.add(buildQuad(vertexFormat, EnumFacing.SOUTH, sprite, tint,
                0, 0, 8.52f / 16f, sprite.getMinU(), sprite.getMaxV(),
                1, 0, 8.52f / 16f, sprite.getMaxU(), sprite.getMaxV(),
                1, 1, 8.52f / 16f, sprite.getMaxU(), sprite.getMinV(),
                0, 1, 8.52f / 16f, sprite.getMinU(), sprite.getMinV()
        ));
        return builder;
    }

    private static BakedQuad buildQuad(
            VertexFormat format, EnumFacing side, TextureAtlasSprite sprite, int tint,
            float x0, float y0, float z0, float u0, float v0,
            float x1, float y1, float z1, float u1, float v1,
            float x2, float y2, float z2, float u2, float v2,
            float x3, float y3, float z3, float u3, float v3)
    {
        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setQuadTint(tint);
        builder.setQuadOrientation(side);
        builder.setTexture(sprite);
        putVertex(builder, format, side, x0, y0, z0, u0, v0);
        putVertex(builder, format, side, x1, y1, z1, u1, v1);
        putVertex(builder, format, side, x2, y2, z2, u2, v2);
        putVertex(builder, format, side, x3, y3, z3, u3, v3);
        return builder.build();
    }

    private static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, EnumFacing side, float x, float y, float z, float u, float v)  {
        for(int e = 0; e < format.getElementCount(); e++)
        {
            switch(format.getElement(e).getUsage())
            {
                case POSITION:
                    builder.put(e, x, y, z, 1);
                    break;
                case COLOR:
                    builder.put(e, 1f, 1f, 1f, 1f);
                    break;
                case UV: if(format.getElement(e).getIndex() == 0)
                {
                    builder.put(e, u, v, 0f, 1f);
                    break;
                }
                case NORMAL:
                    builder.put(e, (float)side.getFrontOffsetX(), (float)side.getFrontOffsetY(), (float)side.getFrontOffsetZ(), 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }

}
