package gregtech.api.render;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.client.model.pipeline.LightUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MetaTileEntityRenderer extends FastTESR<TileEntity> {

    public static final MetaTileEntityRenderer INSTANCE = new MetaTileEntityRenderer();
    public static final DummyTileEntity DUMMY_TILE_ENTITY = new DummyTileEntity();
    public static class DummyTileEntity extends TileEntity {}

    private final TextureAtlasSprite fullMapSprite;
    private ItemStack renderingForItemStack;

    private MetaTileEntityRenderer() {
        this.fullMapSprite = new TextureAtlasSprite("missingno") {};
        this.fullMapSprite.setIconHeight(1);
        this.fullMapSprite.setIconWidth(1);
        this.fullMapSprite.initSprite(1, 1, 0, 0, false);
        ClientRegistry.bindTileEntitySpecialRenderer(DummyTileEntity.class, this);
    }

    public boolean isRenderingItemStack() {
        return renderingForItemStack != null;
    }

    public ItemStack getRenderingItemStack() {
        return renderingForItemStack;
    }

    public void setRenderingForItemStack(ItemStack renderingForItemStack) {
        this.renderingForItemStack = renderingForItemStack;
    }

    private TextureAtlasSprite overrideSprite;
    private BufferBuilder buffer;
    private int lightmap;
    private double x, y, z;
    
    @Override
    public void renderTileEntityFast(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        //this.buffer = buffer;
        //this.x = x;
        //this.y = y;
        //this.z = z;

        buffer.pos(x, y, z).color(1, 1, 1, 0).tex(0, 0).lightmap(15, 15).endVertex();
        buffer.pos(x, y + 1, z).color(1, 1, 1, 0).tex(0, 1).lightmap(15, 15).endVertex();
        buffer.pos(x, y + 1, z + 1).color(1, 1, 1, 0).tex(1, 1).lightmap(15, 15).endVertex();
        buffer.pos(x, y, z + 1).color(1, 1, 1, 0).tex(1, 0).lightmap(15, 15).endVertex();

        /*if(destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            this.overrideSprite = fullMapSprite;
        } else this.overrideSprite = null;

        if(te.getWorld() != null) {
            IBlockState blockState = te.getWorld().getBlockState(te.getPos());
            this.lightmap = blockState.getPackedLightmapCoords(te.getWorld(), te.getPos());
        } else this.lightmap = 15 << 20; //max skylight and zero blocklight



        if(te instanceof DummyTileEntity) {
            BlockMachine<?> blockMachine = (BlockMachine<?>) ((ItemBlock) renderingForItemStack.getItem()).getBlock();
            blockMachine.getRenderer().renderMetaTileEntity(this, blockMachine.getSampleMetaTileEntity(), renderingForItemStack);
        } else if(te instanceof MetaTileEntity) {
            BlockMachine<?> blockMachine = (BlockMachine<?>) te.getBlockType();
            blockMachine.getRenderer().renderMetaTileEntity(this, (MetaTileEntity) te, null);
        }*/
    }

    public void renderSide(EnumFacing side, TextureAtlasSprite sprite, double x, double y, double z, double width, double height, double depth, int color) {
        if(side == EnumFacing.DOWN) {
            renderYFaceNeg(sprite, x, y, z, width, height, color);
        } else if(side == EnumFacing.UP) {
            renderYFacePos(sprite, x, y + depth, z, width, height, color);
        } else if(side == EnumFacing.WEST) {
            renderXFaceNeg(sprite, x, y, z, width, height, color);
        } else if(side == EnumFacing.EAST) {
            renderXFacePos(sprite, x + depth, y, z, width, height, color);
        } else if(side == EnumFacing.NORTH) {
            renderZFaceNeg(sprite, x, y, z, width, height, color);
        } else if(side == EnumFacing.SOUTH) {
            renderZFacePos(sprite, x, y, z + depth, width, height, color);
        }
    }
    
    public void renderXFacePos(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color) {
        renderXFace(sprite, x, y, z, width, height, color, true);
    }

    public void renderXFaceNeg(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color) {
        renderXFace(sprite, x, y, z, width, height, color, false);
    }

    public void renderZFacePos(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color) {
        renderZFace(sprite, x, y, z, width, height, color, true);
    }

    public void renderZFaceNeg(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color) {
        renderZFace(sprite, x, y, z, width, height, color, false);
    }

    public void renderYFacePos(TextureAtlasSprite sprite, double x, double y, double z, double width, double depth, int color) {
        renderYFace(sprite, x, y, z, width, depth, color, true);
    }

    public void renderYFaceNeg(TextureAtlasSprite sprite, double x, double y, double z, double width, double depth, int color) {
        renderYFace(sprite, x, y, z, width, depth, color, false);
    }
    
    public void renderYFace(TextureAtlasSprite sprite, double x, double y, double z, double width, double depth, int color, boolean inverse) {
        if(overrideSprite != null)
            sprite = overrideSprite;
        x += this.x;
        y += this.y;
        z += this.z;
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

        if(inverse) {
            buffer.pos(x, y, z + depth).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y, z + depth).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
        } else {
            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y, z + depth).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y, z + depth).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
        }
    }

    public void renderZFace(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color, boolean inverse) {
        if(overrideSprite != null)
            sprite = overrideSprite;
        x += this.x;
        y += this.y;
        z += this.z;
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

        if(inverse) {
            buffer.pos(x + width, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
        } else {
            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x + width, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
        }
    }

    public void renderXFace(TextureAtlasSprite sprite, double x, double y, double z, double width, double height, int color, boolean inverse) {
        if(overrideSprite != null)
            sprite = overrideSprite;
        x += this.x;
        y += this.y;
        z += this.z;
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

        if(inverse) {

            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z + width).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y, z + width).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
        } else {
            buffer.pos(x, y, z + width).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z + width).color(diffuse * r, diffuse * g, diffuse * b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y + height, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
            buffer.pos(x, y, z).color(diffuse * r, diffuse * g, diffuse * b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
        }
    }

}
