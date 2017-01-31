package gregtech.common.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.item.CCRenderItem;
import codechicken.lib.render.item.IItemRenderer;
import gregtech.api.GregTech_API;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.items.GT_Generic_Item;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class ItemRenderer {

    public static final ItemRenderer INSTANCE = new ItemRenderer();

    private ModelResourceLocation LOCATION_NORMAL = new ModelResourceLocation("gt/item/normal", "inventory");
    private ModelResourceLocation LOCATION_HANDHELD = new ModelResourceLocation("gt/item/handheld", "inventory");
    private ModelResourceLocation LOCATION_BLOCK = new ModelResourceLocation("gt/item/block", "inventory");

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(LOCATION_NORMAL, new BakedModelItemRendererWrapper(ModelUtil.DEFAULT_TRANSFORMS));
        event.getModelRegistry().putObject(LOCATION_HANDHELD, new BakedModelItemRendererWrapper(ModelUtil.HANDHELD_TRANSFORMS));
        event.getModelRegistry().putObject(LOCATION_BLOCK, new BakedModelItemRendererWrapper(ModelUtil.BLOCK_TRANSFORMS));
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.getMap();
        System.out.println("GT: Started Iconload Phase");
        for (Block block : Block.REGISTRY) {
            if (block instanceof GT_Generic_Block) {
                ((GT_Generic_Block) block).registerIcons(map);
            }
        }

        for (Item item : Item.REGISTRY) {
            if (item instanceof GT_Generic_Item) {
                ((GT_Generic_Item) item).registerIcons(map);
            }
        }

        GregTech_API.sBlockIcons = map;
        for(Runnable runnable : GregTech_API.sGTBlockIconload) {
            runnable.run();
        }
        for(Runnable runnable : GregTech_API.sGTItemIconload) {
            runnable.run();
        }
        System.out.println("GT: Finished Iconload Phase");
    }

    public void init() {
        CCRenderItem.init();
        MinecraftForge.EVENT_BUS.register(this);
        ItemModelMesher mesher = CCRenderItem.instance().getItemModelMesher();

        ItemMeshDefinition definition = new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if(stack.getItem() instanceof ItemBlock) {
                    return LOCATION_BLOCK;
                }
                GT_Generic_Item generic_item = (GT_Generic_Item) stack.getItem();
                if(generic_item.isHandheld(stack)) {
                    return LOCATION_HANDHELD;
                }
                return LOCATION_NORMAL;
            }
        };

        for(Item item : Item.REGISTRY) {
            if(item instanceof GT_Generic_Item || (item instanceof ItemBlock && ((ItemBlock) item).block instanceof GT_Generic_Block)) {
                mesher.register(item, definition);
            }
        }

    }

    public void renderItem(ItemStack p_78443_2_, int p_78443_3_) {
        Minecraft mc = Minecraft.getMinecraft();
        TextureManager texturemanager = mc.getTextureManager();
        Item item = p_78443_2_.getItem();
        CCRenderState ccrs = CCRenderState.instance();

        if (item instanceof ItemBlock) {
            Block block = ((ItemBlock) item).block;
            EnumBlockRenderType layer = block.getRenderType(block.getDefaultState());
            ccrs.reset();
            ccrs.pullLightmap();
            if(layer == RenderBlocks.INSTANCE.renderType) {
                RenderBlocks.INSTANCE.renderBlockAsItem(ccrs, p_78443_2_);
            }
            if(layer == GT_Renderer_Block.INSTANCE.renderType) {
                GT_Renderer_Block.INSTANCE.renderInventoryBlock(ccrs, p_78443_2_);
            }
            if(layer == RenderGeneratedOres.INSTANCE.renderType) {
                RenderGeneratedOres.INSTANCE.renderBlockAsItem(ccrs, p_78443_2_);
            }
        }
        else
        {
            GL11.glPushMatrix();
            GT_Generic_Item generic_item = (GT_Generic_Item) item;
            TextureAtlasSprite iicon = generic_item.getIcon(p_78443_2_, p_78443_3_);
            int color = generic_item.getColorFromItemStack(p_78443_2_, p_78443_3_);
            int[] colors = new int[] { (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, 255};

            if (iicon == null)
            {
                GL11.glPopMatrix();
                return;
            }

            texturemanager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            //TextureUtil.func_152777_a(false, false, 1.0F);
            Tessellator tessellator = Tessellator.getInstance();
            float f = iicon.getMinU();
            float f1 = iicon.getMaxU();
            float f2 = iicon.getMinV();
            float f3 = iicon.getMaxV();
            //float f4 = 0.0F;
            //float f5 = 0.3F;
            //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            //GL11.glTranslatef(-f4, -f5, 0.0F);
            //float f6 = 1.5F;
            //GL11.glScalef(f6, f6, f6);
            //GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
            //GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            //GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            GL11.glRotatef(180f, 0f, 1f, 0f);
            GL11.glTranslatef(-1f, 0f, -0.5f);


            renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F, colors);

            /*if (p_78443_2_.hasEffect())
            {
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glDisable(GL11.GL_LIGHTING);
                texturemanager.bindTexture(RES_ITEM_GLINT);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(768, 1, 1, 0);
                float f7 = 0.76F;
                GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float f8 = 0.125F;
                GL11.glScalef(f8, f8, f8);
                float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                GL11.glTranslatef(f9, 0.0F, 0.0F);
                GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F, colors);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(f8, f8, f8);
                f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                GL11.glTranslatef(-f9, 0.0F, 0.0F);
                GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F, colors);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
            }*/

            //GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            //texturemanager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            //TextureUtil.func_147945_b();
            GL11.glPopMatrix();
        }
    }

    /**
     * Renders an item held in hand as a 2D texture with thickness
     */
    public static void renderItemIn2D(Tessellator tes, float p_78439_1_, float p_78439_2_, float p_78439_3_, float p_78439_4_, int p_78439_5_, int p_78439_6_, float p_78439_7_, int[] color)
    {
        VertexBuffer buf = tes.getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        
        buf.pos(0.0D, 0.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)p_78439_4_).normal(0.0f, 0.0f, 1.0f).endVertex();
        buf.pos(1.0D, 0.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)p_78439_4_).normal(0.0f, 0.0f, 1.0f).endVertex();
        buf.pos(1.0D, 1.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)p_78439_2_).normal(0.0f, 0.0f, 1.0f).endVertex();
        buf.pos(0.0D, 1.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)p_78439_2_).normal(0.0f, 0.0f, 1.0f).endVertex();
        tes.draw();
        
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        buf.pos(0.0D, 1.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)p_78439_2_).normal(0.0f, 0.0f, -1.0f).endVertex();
        buf.pos(1.0D, 1.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)p_78439_2_).normal(0.0f, 0.0f, -1.0f).endVertex();
        buf.pos(1.0D, 0.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)p_78439_4_).normal(0.0f, 0.0f, -1.0f).endVertex();
        buf.pos(0.0D, 0.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)p_78439_4_).normal(0.0f, 0.0f, -1.0f).endVertex();
        tes.draw();
        
        float f5 = 0.5F * (p_78439_1_ - p_78439_3_) / (float)p_78439_5_;
        float f6 = 0.5F * (p_78439_4_ - p_78439_2_) / (float)p_78439_6_;
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        int k;
        float f7;
        float f8;

        for (k = 0; k < p_78439_5_; ++k)
        {
            f7 = (float)k / (float)p_78439_5_;
            f8 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * f7 - f5;
            buf.pos((double)f7, 0.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_4_).normal(-1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f7, 0.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_4_).normal(-1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f7, 1.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_2_).normal(-1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f7, 1.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_2_).normal(-1.0F, 0.0F, 0.0F).endVertex();
        }

        tes.draw();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        float f9;

        for (k = 0; k < p_78439_5_; ++k)
        {
            f7 = (float)k / (float)p_78439_5_;
            f8 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * f7 - f5;
            f9 = f7 + 1.0F / (float)p_78439_5_;
            buf.pos((double)f9, 1.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_2_).normal(1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f9, 1.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_2_).normal(1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f9, 0.0D, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_4_).normal(1.0F, 0.0F, 0.0F).endVertex();
            buf.pos((double)f9, 0.0D, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)f8, (double)p_78439_4_).normal(1.0F, 0.0F, 0.0F).endVertex();
        }

        tes.draw();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);

        for (k = 0; k < p_78439_6_; ++k)
        {
            f7 = (float)k / (float)p_78439_6_;
            f8 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * f7 - f6;
            f9 = f7 + 1.0F / (float)p_78439_6_;
            buf.pos(0.0D, (double)f9, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)f8).normal(0.0F, 1.0F, 0.0F).endVertex();
            buf.pos(1.0D, (double)f9, 0.0).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)f8).normal(0.0F, 1.0F, 0.0F).endVertex();
            buf.pos(1.0D, (double)f9, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)f8).normal(0.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.0D, (double)f9, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)f8).normal(0.0F, 1.0F, 0.0F).endVertex();
        }

        tes.draw();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);

        for (k = 0; k < p_78439_6_; ++k)
        {
            f7 = (float)k / (float)p_78439_6_;
            f8 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * f7 - f6;
            buf.pos(1.0D, (double)f7, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)f8).normal(0.0F, -1.0F, 0.0F).endVertex();
            buf.pos(0.0D, (double)f7, 0.0D).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)f8).normal(0.0F, -1.0F, 0.0F).endVertex();
            buf.pos(0.0D, (double)f7, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_1_, (double)f8).normal(0.0F, -1.0F, 0.0F).endVertex();
            buf.pos(1.0D, (double)f7, (double)(0.0F - p_78439_7_)).color(color[0], color[1], color[2], color[3]).tex((double)p_78439_3_, (double)f8).normal(0.0F, -1.0F, 0.0F).endVertex();
        }

        tes.draw();
    }

    public void renderItem(ItemStack item) {
        if(item.getItem() instanceof ItemBlock) {
            renderItem(item, 0);
        } else if (item.getItem() instanceof GT_Generic_Item) {
            GT_Generic_Item generic_item = (GT_Generic_Item) item.getItem();
            for(int i = 0; i < generic_item.getRenderPasses(item); i++) {
                renderItem(item, i);
            }
        } else {
            return;
        }
    }

    private final class BakedModelItemRendererWrapper implements IBakedModel, IPerspectiveAwareModel, IItemRenderer {

        private ItemCameraTransforms transforms;

        public BakedModelItemRendererWrapper(ItemCameraTransforms transforms) {
            this.transforms = transforms;
        }

        @Override
        public void renderItem(ItemStack item) {
            ItemRenderer.this.renderItem(item);
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return Collections.EMPTY_LIST;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return true;
        }

        @Override
        public boolean isGui3d() {
            return true;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return true;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return null;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return transforms;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return ItemOverrideList.NONE;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            return ImmutablePair.of(this, new TRSRTransformation(getItemCameraTransforms().getTransform(cameraTransformType)).getMatrix());
        }

    }

}
