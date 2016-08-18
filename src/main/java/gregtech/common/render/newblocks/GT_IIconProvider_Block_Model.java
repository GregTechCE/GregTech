package gregtech.common.render.newblocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.common.render.IIconRegister;
import gregtech.common.render.newitems.GT_IIconProvider_Item_Model;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

@SideOnly(Side.CLIENT)
public class GT_IIconProvider_Block_Model implements IBakedModel {

    private static ModelResourceLocation RESOURCE_LOCATION = new ModelResourceLocation("gregtech", "IBlockIconProvider");

    private VertexFormat vertexFormat;

    private EntityPlayer entityPlayer;
    private ItemStack itemStack;

    public GT_IIconProvider_Block_Model() {
        this.vertexFormat = DefaultVertexFormats.BLOCK;
        MinecraftForge.EVENT_BUS.register(this);
        setupBlockModels();
    }

    @SubscribeEvent
    public void onTextureMapStitch(TextureStitchEvent.Pre event) {
        for(ResourceLocation itemId : Block.REGISTRY.getKeys()) {
            Block block = Block.REGISTRY.getObject(itemId);
            if(block instanceof IIconRegister) {
                ((IIconRegister) block).registerIcons(event.getMap());
            }
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(RESOURCE_LOCATION, this);
    }

    private static void setupBlockModels() {
        for(ResourceLocation itemId : Block.REGISTRY.getKeys()) {
            Block block = Block.REGISTRY.getObject(itemId);
            if(block instanceof IBlockIconProvider || block instanceof ITextureBlockIconProvider) {
                Item itemBlock = Item.getItemFromBlock(block);
                if(itemBlock != null) {
                    ModelBakery.registerItemVariants(itemBlock, RESOURCE_LOCATION);
                    ModelLoader.setCustomMeshDefinition(itemBlock, stack -> RESOURCE_LOCATION);
                }
                ModelLoader.setCustomStateMapper(block, blockIn -> new FakeStateMap());
            }
        }
    }

    //-----------------------------------------
    // IBakedModel
    //-----------------------------------------


    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        Block renderBlock = state == null ? Block.getBlockFromItem(itemStack.getItem()) : state.getBlock();
        ArrayList<BakedQuad> builder = new ArrayList<>();
        if(side != null) {
            if (renderBlock instanceof IBlockIconProvider) {
                IBlockIconProvider blockIconProvider = (IBlockIconProvider) renderBlock;
                IExtendedBlockState extendedState = (IExtendedBlockState) state;
                TextureAtlasSprite sprite = null;
                if(extendedState == null) {
                    sprite = blockIconProvider.getIcon(itemStack, side);
                } else {
                    BlockPos blockPos = extendedState.getValue(GT_Generic_Block.BLOCK_POS);
                    sprite = blockIconProvider.getIcon(Minecraft.getMinecraft().theWorld, blockPos, side);
                }

                builder.add(buildQuad(vertexFormat, EnumFacing.NORTH, sprite, side.getIndex(),
                        0, 0, 7.48f / 16f, sprite.getMinU(), sprite.getMaxV(),
                        0, 1, 7.48f / 16f, sprite.getMinU(), sprite.getMinV(),
                        1, 1, 7.48f / 16f, sprite.getMaxU(), sprite.getMinV(),
                        1, 0, 7.48f / 16f, sprite.getMaxU(), sprite.getMaxV()
                ));
            }
            if(state.getBlock() instanceof ITextureBlockIconProvider) {
                ITextureBlockIconProvider blockIconProvider = (ITextureBlockIconProvider) renderBlock;
                if(state == null) {
                    ITexture[] textures = blockIconProvider.getItemblockTexture(entityPlayer, itemStack, side);
                    for(ITexture iTexture : textures) {
                        builder.addAll(iTexture.getQuads(renderBlock, BlockPos.ORIGIN, side, 0));
                    }
                } else {
                    IExtendedBlockState extendedState = (IExtendedBlockState) state;
                    BlockPos blockPos = extendedState.getValue(GT_Generic_Block.BLOCK_POS);
                    ITexture[] textures = blockIconProvider.getTexture(Minecraft.getMinecraft().theWorld, blockPos, extendedState, side);
                    for(ITexture iTexture : textures) {
                        builder.addAll(iTexture.getQuads(renderBlock, blockPos, side, 0));
                    }
                }

            }

        }
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
                GT_IIconProvider_Block_Model.this.itemStack = stack;
                if(entity instanceof EntityPlayer) {
                    GT_IIconProvider_Block_Model.this.entityPlayer = (EntityPlayer) entity;
                }
                return GT_IIconProvider_Block_Model.this;
            }
        };
    }

    private static class FakeStateMap implements Map<IBlockState, ModelResourceLocation> {

        @Override
        public int size() {
            return 16;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return true;
        }

        @Override
        public boolean containsValue(Object value) {
            return true;
        }

        @Override
        public ModelResourceLocation get(Object key) {
            return RESOURCE_LOCATION;
        }

        @Override
        public ModelResourceLocation put(IBlockState key, ModelResourceLocation value) {
            return RESOURCE_LOCATION;
        }

        @Override
        public ModelResourceLocation remove(Object key) {
            return RESOURCE_LOCATION;
        }

        @Override
        public void putAll(Map<? extends IBlockState, ? extends ModelResourceLocation> m) {
        }

        @Override
        public void clear() {
        }

        @Override
        public Set<IBlockState> keySet() {
            return ImmutableSet.of();
        }

        @Override
        public Collection<ModelResourceLocation> values() {
            return ImmutableList.of(RESOURCE_LOCATION);
        }

        @Override
        public Set<Entry<IBlockState, ModelResourceLocation>> entrySet() {
            return ImmutableSet.of();
        }

    }

}
