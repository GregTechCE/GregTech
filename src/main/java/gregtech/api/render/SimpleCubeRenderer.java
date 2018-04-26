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

public class SimpleCubeRenderer implements ICubeRenderer {

    private final String basePath;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite sprite;

    public SimpleCubeRenderer(String basePath) {
        this.basePath = basePath;
        Textures.iconRegisters.add(this::registerIcons);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.sprite = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/" + basePath));
    }

    public void renderSided(EnumFacing side, Cuboid6 bounds, CCRenderState renderState, IVertexOperation[] pipeline) {
        MetaTileEntity.renderFace(renderState, side, bounds, sprite, pipeline);
    }

    public void renderSided(EnumFacing side, CCRenderState renderState, IVertexOperation[] pipeline) {
        renderSided(side, Cuboid6.full, renderState, pipeline);
    }

    @Override
    public void render(CCRenderState renderState, IVertexOperation[] pipeline, Cuboid6 bounds) {
        for(EnumFacing side : EnumFacing.values()) {
            renderSided(side, bounds, renderState, pipeline);
        }
    }
}
