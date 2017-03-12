package gregtech.common.render;

import codechicken.lib.colour.ColourARGB;
import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderBlocks implements ICCBlockRenderer {
    public static RenderBlocks INSTANCE = new RenderBlocks();
    private static final BlockRenderer.BlockFace face = new BlockRenderer.BlockFace();

    public EnumBlockRenderType renderType;

    private static Cuboid6 bounds;

    public void init() {
        renderType = BlockRenderingRegistry.createRenderType("GT_SIMPLE_BLOCK");
        BlockRenderingRegistry.registerRenderer(renderType, INSTANCE);
        setBlockBounds(Cuboid6.full);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, VertexBuffer buffer) {
        IBlockState stone = Blocks.STONE.getDefaultState();
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockDamage(stone, pos, sprite, world);
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        CCRenderState ccrs = CCRenderState.instance();
        GT_Generic_Block aOres = (GT_Generic_Block) state.getBlock();
        aOres.setBlockBoundsBasedOnState(world, pos, state);
        setRenderBoundsFromBlock(aOres);

        int color;
        TextureAtlasSprite sprite;

        ccrs.reset();
        ccrs.bind(buffer);
        ccrs.lightMatrix.locate(world, pos);

        for(EnumFacing side : EnumFacing.VALUES) {
            if(state.shouldSideBeRendered(world, pos, side)) {
                if((sprite = aOres.getWorldIcon(world, pos, state, side)) != null) {
                    color = aOres.getColorMultiplier(world, pos, state);
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;
                    renderFace(ccrs, side, new Translation(new Vector3().fromBlockPos(pos)), new ColourMultiplier(new ColourRGBA(r, g, b, 255).rgba()), new IconTransformation(sprite), ccrs.lightMatrix);
                }
            }
        }

        return true;
    }

    public void setBlockBounds(Cuboid6 bounds)
    {
        this.bounds = bounds;
    }

    public void setBlockBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        setBlockBounds(new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ));
    }

    public void setRenderBoundsFromBlock(GT_Generic_Block block) {
        setBlockBounds((double)block.minX, (double)block.minY, (double)block.minZ, (double)block.maxX, (double)block.maxY, (double)block.maxZ);
    }

    public Cuboid6 getBounds()
    {
        return bounds;
    }

    public static void renderFace(CCRenderState state, EnumFacing side, IVertexOperation... ops)
    {
        if (bounds != null) {
            face.loadCuboidFace(bounds, side.ordinal());
        }
        state.setPipeline(face, 0, face.verts.length, ops);
        state.render();
    }

    public void renderOresBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        CCRenderState ccrs = CCRenderState.instance();
        GT_Block_GeneratedOres aOres = (GT_Block_GeneratedOres) state.getBlock();
        Materials mats = aOres.getMaterialSafe(state);
        boolean small = aOres.mSmall;

        int color = ITexture.color(mats.mRGBa, false);
        TextureAtlasSprite sprite1 = aOres.getStoneTypeSafe(state).mIconContainer.getIcon();
        TextureAtlasSprite sprite2 = mats.mIconSet.mTextures[small ? TextureSet.INDEX_oreSmall : TextureSet.INDEX_ore].getIcon();

        ccrs.reset();
        ccrs.bind(buffer);
        ccrs.lightMatrix.locate(world, pos);

        for (EnumFacing side : EnumFacing.VALUES) {
            if (state.shouldSideBeRendered(world, pos, side)) {
                renderFace(ccrs, side, new Translation(new Vector3().fromBlockPos(pos)), new ColourMultiplier(-1), new IconTransformation(sprite1), ccrs.lightMatrix);
                renderFace(ccrs, side, new Translation(new Vector3().fromBlockPos(pos)), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(sprite2), ccrs.lightMatrix);
            }
        }
    }

    public void renderBlockAsItem(CCRenderState ccrs, ItemStack itemStack) {
        GT_Generic_Block aBlock = (GT_Generic_Block) ((ItemBlock) itemStack.getItem()).block;
        aBlock.setBlockBoundsForItemRender();
        setRenderBoundsFromBlock(aBlock);

        TextureAtlasSprite sprite;

        ccrs.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        for (EnumFacing side : EnumFacing.VALUES)
        {
            if((sprite = aBlock.getItemIcon(itemStack, side)) != null) {
                renderFace(ccrs, side, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(-1), new IconTransformation(sprite));
            }
        }
        ccrs.draw();
    }

    public void renderOresAsItem(CCRenderState ccrs, ItemStack stack) {
        GT_Block_GeneratedOres aOres = (GT_Block_GeneratedOres) ((ItemBlock) stack.getItem()).block;
        Materials mats = aOres.getMaterialSafe(stack);
        boolean small = aOres.mSmall;

        int color = ITexture.color(mats.mRGBa, false);
        TextureAtlasSprite sprite1 = aOres.getStoneTypeSafe(stack).mIconContainer.getIcon();
        TextureAtlasSprite sprite2 = mats.mIconSet.mTextures[small ? TextureSet.INDEX_oreSmall : TextureSet.INDEX_ore].getIcon();

        ccrs.startDrawing(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        for (EnumFacing side : EnumFacing.VALUES)
        {
            renderFace(ccrs, side, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(-1), new IconTransformation(sprite1));
            renderFace(ccrs, side, new Translation(0.0D, 0.0D, 0.0D), new ColourMultiplier(new ColourARGB(color).rgba()), new IconTransformation(sprite2));
        }
        ccrs.draw();
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
        CCRenderState ccrs = CCRenderState.instance();
        renderBlockAsItem(ccrs, new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
    }

    @Override
    public void registerTextures(TextureMap map) {
    }
}