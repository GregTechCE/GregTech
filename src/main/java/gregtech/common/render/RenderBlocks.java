package gregtech.common.render;

import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.block.ICCBlockRenderer;
import gregtech.api.enums.Materials;
import gregtech.api.enums.StoneTypes;
import gregtech.api.enums.TextureSet;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
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
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.opengl.GL11;

public class RenderBlocks implements ICCBlockRenderer {

    public static RenderBlocks INSTANCE = new RenderBlocks();

    public float minX, minY, minZ, maxX, maxY, maxZ;
    public boolean flip = true;

    public EnumBlockRenderType renderType;
    private BlockPos.MutableBlockPos t = new BlockPos.MutableBlockPos();

    public void init() {
        renderType = BlockRenderingRegistry.createRenderType("GT_SIMPLE_BLOCK");
        BlockRenderingRegistry.registerRenderer(renderType, INSTANCE);
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void handleRenderBlockDamage(IBlockAccess world, BlockPos pos, IBlockState state, TextureAtlasSprite sprite, VertexBuffer buffer) {
        IBlockState stone = Blocks.STONE.getDefaultState();
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockDamage(stone, pos, sprite, world);
    }

    @Override
    public boolean renderBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();


        GT_Generic_Block aOres = (GT_Generic_Block) state.getBlock();
        aOres.setBlockBoundsBasedOnState(world, pos, state);
        setRenderBoundsFromBlock1(aOres);

        int lightmap;
        int color;
        TextureAtlasSprite sprite;

        for(EnumFacing side : EnumFacing.VALUES) {
            if(state.shouldSideBeRendered(world, pos, side)) {
                if((sprite = aOres.getWorldIcon(world, pos, state, side)) != null) {
                    color = aOres.getColorMultiplier(world, pos, state);
                    t.setPos(pos.getX(), pos.getY(), pos.getZ()).move(side);
                    lightmap = world.getBlockState(t).getPackedLightmapCoords(world, t);
                    renderFace(sprite, x, y, z, color, lightmap, buffer, side);
                }
            }
        }

        return true;
    }
    
    public void renderOresBlock(IBlockAccess world, BlockPos pos, IBlockState state, VertexBuffer buffer) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        GT_Block_GeneratedOres aOres = (GT_Block_GeneratedOres) state.getBlock();
        Materials mats = aOres.getMaterialSafe(state);
        boolean small = aOres.mSmall;
        int lightmap;

        int color = ITexture.color(mats.mRGBa, false);
        TextureAtlasSprite sprite1 = aOres.getStoneTypeSafe(state).mIconContainer.getIcon();
        TextureAtlasSprite sprite2 = mats.mIconSet.mTextures[small ? TextureSet.INDEX_oreSmall : TextureSet.INDEX_ore].getIcon();

        for (EnumFacing side : EnumFacing.VALUES) {
            if (state.shouldSideBeRendered(world, pos, side)) {
                t.setPos(pos.getX(), pos.getY(), pos.getZ()).move(side);
                lightmap = world.getBlockState(t).getPackedLightmapCoords(world, t);
                renderFace(sprite1, x, y, z, 0xFFFFFFFF, lightmap, buffer, side);
                renderFace(sprite2, x, y, z, color, lightmap, buffer, side);
            }
        }
    }

    public void renderBlockAsItem(ItemStack itemStack) {

        Tessellator tes = Tessellator.getInstance();
        VertexBuffer buf = tes.getBuffer();

        GT_Generic_Block aBlock = (GT_Generic_Block) ((ItemBlock) itemStack.getItem()).block;
        aBlock.setBlockBoundsForItemRender();
        setRenderBoundsFromBlock1(aBlock);

        TextureAtlasSprite sprite;

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.DOWN)) != null) {
            renderYNegItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.UP)) != null) {
            renderYPosItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.NORTH)) != null) {
            renderZNegItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.SOUTH)) != null) {
            renderZPosItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.EAST)) != null) {
            renderXPosItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        if((sprite = aBlock.getItemIcon(itemStack, EnumFacing.WEST)) != null) {
            renderXNegItem(sprite, 0, 0, 0, 0xFFFFFFFF, buf);
        }
        tes.draw();
    }

    public void renderOresAsItem(ItemStack stack) {
        Tessellator tes = Tessellator.getInstance();
        VertexBuffer buf = tes.getBuffer();

        GT_Block_GeneratedOres aOres = (GT_Block_GeneratedOres) ((ItemBlock) stack.getItem()).block;
        Materials mats = aOres.getMaterialSafe(stack);
        boolean small = aOres.mSmall;

        int color = ITexture.color(mats.mRGBa, false);
        TextureAtlasSprite sprite1 = aOres.getStoneTypeSafe(stack).mIconContainer.getIcon();
        TextureAtlasSprite sprite2 = mats.mIconSet.mTextures[small ? TextureSet.INDEX_oreSmall : TextureSet.INDEX_ore].getIcon();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderYNegItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderYNegItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderYPosItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderYPosItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderZNegItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderZNegItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderZPosItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderZPosItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderXPosItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderXPosItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();

        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
        renderXNegItem(sprite1, 0, 0, 0, 0xFFFFFFFF, buf);
        renderXNegItem(sprite2, 0, 0, 0, color, buf);
        tes.draw();
    }

    @Override
    public void renderBrightness(IBlockState state, float brightness) {
        renderBlockAsItem(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
    }

    @Override
    public void registerTextures(TextureMap map) {
    }

    public void setRenderBoundsFromBlock1(GT_Generic_Block block) {
        this.minX = block.minX;
        this.minY = block.minY;
        this.minZ = block.minZ;
        this.maxX = block.maxX;
        this.maxY = block.maxY;
        this.maxZ = block.maxZ;
    }

    public void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void renderYPosItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.UP);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).normal(0.0f, 1.0f, 0.0f).endVertex();

    }

    public void renderYNegItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.DOWN);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).normal(0.0f, -1.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).normal(0.0f, -1.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).normal(0.0f, -1.0f, 0.0f).endVertex();
        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).normal(0.0f, -1.0f, 0.0f).endVertex();
    }

    public void renderZNegItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.NORTH);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).normal(0.0f, 0.0f, -1.0f).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).normal(0.0f, 0.0f, -1.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).normal(0.0f, 0.0f, -1.0f).endVertex();
        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).normal(0.0f, 0.0f, -1.0f).endVertex();
    }

    public void renderZPosItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.SOUTH);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).normal(0.0f, 0.0f, 1.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).normal(0.0f, 0.0f, 1.0f).endVertex();
        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).normal(0.0f, 0.0f, 1.0f).endVertex();
        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).normal(0.0f, 0.0f, 1.0f).endVertex();
    }

    public void renderXNegItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.WEST);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).normal(-1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).normal(-1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).normal(-1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).normal(-1.0f, 0.0f, 0.0f).endVertex();
    }

    public void renderXPosItem(TextureAtlasSprite sprite, double x, double y, double z, int color, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.EAST);

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).normal(1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).normal(1.0f, 0.0f, 0.0f).endVertex();
        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).normal(1.0f, 0.0f, 0.0f).endVertex();
    }

    public void renderFace(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer, EnumFacing side) {
        switch (side) {
            case DOWN:
                renderYNeg(sprite, x, y, z, color, lightmap, buffer);
                break;
            case UP:
                renderYPos(sprite, x, y, z, color, lightmap, buffer);
                break;
            case NORTH:
                renderZNeg(sprite, x, y, z, color, lightmap, buffer);
                break;
            case SOUTH:
                renderZPos(sprite, x, y, z, color, lightmap, buffer);
                break;
            case WEST:
                renderXNeg(sprite, x, y, z, color, lightmap, buffer);
                break;
            case EAST:
                renderXPos(sprite, x, y, z, color, lightmap, buffer);
                break;
        }
    }

    public void renderYPos(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.UP);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();

    }

    public void renderYNeg(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.DOWN);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
    }

    public void renderZNeg(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.NORTH);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
    }

    public void renderZPos(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.SOUTH);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
    }

    public void renderXNeg(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.WEST);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + minX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + minX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
    }

    public void renderXPos(TextureAtlasSprite sprite, double x, double y, double z, int color, int lightmap, VertexBuffer buffer) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = ((color) & 0xFF) / 255.0f;
        float a = ((color >> 24) & 0xFF) / 255.0f;

        float diffuse = LightUtil.diffuseLight(EnumFacing.EAST);

        int light1 = lightmap >> 16 & 65535;
        int light2 = lightmap & 65535;

        double minU = sprite.getMinU();
        double minV = sprite.getMinV();
        double maxU = sprite.getMaxU();
        double maxV = sprite.getMaxV();

        buffer.pos(x + maxX, y + minY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + minZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? maxU : minU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + maxY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? minV : maxV).lightmap(light1, light2).endVertex();
        buffer.pos(x + maxX, y + minY, z + maxZ).color(diffuse * r, diffuse * g, diffuse * b, a).tex(flip ? minU : maxU, flip ? maxV : minV).lightmap(light1, light2).endVertex();
    }

}
