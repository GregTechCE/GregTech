package gregtech.common.render.newitems;

import com.google.common.base.Optional;
import gregtech.api.GregTech_API;
import gregtech.api.util.GT_Log;
import gregtech.common.render.IIconRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GT_IIconProvider_Item_Model implements IBakedModel {

    private static ModelResourceLocation RESOURCE_LOCATION = new ModelResourceLocation("gregtech", "IItemIconProvider");

    private HashMap<TextureAtlasSprite, List<BakedQuad>> iconsCache = new HashMap<>();
    private HashMap<Pair<TextureAtlasSprite, Integer>, List<BakedQuad>> overlaysCache = new HashMap<>();

    private ItemStack itemStack;
    private VertexFormat vertexFormat;

    public GT_IIconProvider_Item_Model() {
        MinecraftForge.EVENT_BUS.register(this);
        setupItemModels();
        this.vertexFormat = DefaultVertexFormats.ITEM;
    }

    @SubscribeEvent
    public void onTextureMapStitch(TextureStitchEvent.Pre pre) {
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        System.out.println("Texture map stitch");
        GT_Log.out.println("GT_Mod: Starting Item Icon Load Phase");
        System.out.println("GT_Mod: Starting Item Icon Load Phase");
        GregTech_API.sBlockIcons = pre.getMap();
        for (Runnable tRunnable : GregTech_API.sGTItemIconload) {
            try {
                tRunnable.run();
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
        }
        for(ResourceLocation itemId : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(itemId);
            if(item instanceof IIconRegister) {
                ((IIconRegister) item).registerIcons(pre.getMap());
            }
            if(item instanceof IItemColor) {
                itemColors.registerItemColorHandler((IItemColor) item, item);
            }
        }
        GT_Log.out.println("GT_Mod: Finished Item Icon Load Phase");
        System.out.println("GT_Mod: Finished Item Icon Load Phase");
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent bakeEvent) {
        System.out.println("Models bake");
        bakeEvent.getModelRegistry().putObject(RESOURCE_LOCATION, this);
    }

    public static void setupItemModels() {
        for(ResourceLocation itemId : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(itemId);
            if(item instanceof IItemIconProvider) {
                for (int i = 0; i < Short.MAX_VALUE; i++) {
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i, RESOURCE_LOCATION);
                }
            }
        }
    }

    //-----------------------------------------
    // IBakedModel
    //-----------------------------------------

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {

        if (side == null && itemStack != null) {
            IItemIconProvider iconProvider = (IItemIconProvider) itemStack.getItem();
            TextureAtlasSprite textureIcon = iconProvider.getIcon(itemStack, 0);
            //System.out.println(iconProvider.getClass() + " " + textureIcon);
            ArrayList<BakedQuad> resultQuads = new ArrayList<>();
            if (textureIcon != null) {
                //if (iconsCache.containsKey(textureIcon)) {
                //    resultQuads.addAll(iconsCache.get(textureIcon));
                //} else {
                List<BakedQuad> textureQuads = ItemLayerModel.getQuadsForSprite(0, textureIcon, vertexFormat, Optional.<TRSRTransformation>absent());
                //iconsCache.put(textureIcon, textureQuads);
                resultQuads.addAll(textureQuads);
                //}
            }
            if (iconProvider.getRenderPasses(itemStack) > 0) {
                for (int i = 0; i < iconProvider.getRenderPasses(itemStack); ++i) {
                    TextureAtlasSprite overlayIcon = iconProvider.getIcon(itemStack, i);
                    ImmutablePair<TextureAtlasSprite, Integer> iconPair = new ImmutablePair<>(overlayIcon, i);
                    if (overlayIcon != null) {
                        if (overlaysCache.containsKey(iconPair)) {
                            resultQuads.addAll(overlaysCache.get(iconPair));
                        } else {
                            List<BakedQuad> overlayQuads = getOverlayQuads(iconPair.getLeft(), i, 0.01F * i);
                            overlaysCache.put(iconPair, overlayQuads);
                            resultQuads.addAll(overlayQuads);
                        }
                    }
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
            IItemIconProvider iconProvider = (IItemIconProvider) itemStack.getItem();
            return iconProvider.getIcon(itemStack, 0);
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

    private List<BakedQuad> getOverlayQuads(TextureAtlasSprite sprite, int tint, float offset) {
        ArrayList<BakedQuad> builder = new ArrayList<>();
        // front
        builder.add(buildQuad(vertexFormat, EnumFacing.NORTH, sprite, tint,
                0, 0, 7.48f - offset / 16f, sprite.getMinU(), sprite.getMaxV(),
                0, 1, 7.48f - offset / 16f, sprite.getMinU(), sprite.getMinV(),
                1, 1, 7.48f - offset / 16f, sprite.getMaxU(), sprite.getMinV(),
                1, 0, 7.48f - offset / 16f, sprite.getMaxU(), sprite.getMaxV()
        ));
        // back
        builder.add(buildQuad(vertexFormat, EnumFacing.SOUTH, sprite, tint,
                0, 0, 8.52f + offset / 16f, sprite.getMinU(), sprite.getMaxV(),
                1, 0, 8.52f + offset / 16f, sprite.getMaxU(), sprite.getMaxV(),
                1, 1, 8.52f + offset / 16f, sprite.getMaxU(), sprite.getMinV(),
                0, 1, 8.52f + offset / 16f, sprite.getMinU(), sprite.getMinV()
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
