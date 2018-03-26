package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

public class SimpleSidedRenderer {

    public enum RenderSide {
        TOP, BOTTOM, SIDE;

        public static RenderSide bySide(EnumFacing side) {
            if(side == EnumFacing.UP) {
                return TOP;
            } else if(side == EnumFacing.DOWN) {
                return BOTTOM;
            } else return SIDE;
        }
    }

    private final String basePath;

    @SideOnly(Side.CLIENT)
    private Map<RenderSide, TextureAtlasSprite> sprites;

    public SimpleSidedRenderer(String basePath) {
        this.basePath = basePath;
        Textures.iconRegisters.add(this::registerSprites);
    }

    @SideOnly(Side.CLIENT)
    public void registerSprites(TextureMap textureMap) {
        this.sprites = new HashMap<>();
        for(RenderSide overlayFace : RenderSide.values()) {
            String faceName = overlayFace.name().toLowerCase();
            ResourceLocation resourceLocation = new ResourceLocation(GTValues.MODID, String.format("blocks/%s/%s", basePath, faceName));
            sprites.put(overlayFace, textureMap.registerSprite(resourceLocation));
        }
    }

    public void render(CCRenderState renderState, IVertexOperation[] pipeline, Cuboid6 bounds) {
        for(EnumFacing renderSide : EnumFacing.VALUES) {
            RenderSide overlayFace = RenderSide.bySide(renderSide);
            TextureAtlasSprite renderSprite = sprites.get(overlayFace);
            MetaTileEntity.renderFace(renderState, renderSide, bounds, renderSprite, pipeline);
        }
    }

    public void render(CCRenderState renderState, IVertexOperation[] pipeline) {
        render(renderState, pipeline, Cuboid6.full);
    }


}
