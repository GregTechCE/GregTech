package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import codechicken.lib.vec.Cuboid6;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

public class LargeTurbineRenderer implements IIconRegister {

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] baseTexture;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] bladeTexture;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] activeBladeTexture;

    public LargeTurbineRenderer() {
        Textures.iconRegisters.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.baseTexture = new TextureAtlasSprite[9];
        this.bladeTexture = new TextureAtlasSprite[9];
        this.activeBladeTexture = new TextureAtlasSprite[9];
        for(int i = 0; i < 9; i++) {
            String basePath = "blocks/multiblock/large_turbine/%s/%d";
            this.baseTexture[i] = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, String.format(basePath, "base", i + 1)));
            this.bladeTexture[i] = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, String.format(basePath, "blade", i + 1)));
            this.activeBladeTexture[i] = textureMap.registerSprite(new ResourceLocation(GTValues.MODID, String.format(basePath, "blade_active", i + 1)));
        }
    }

    public void renderSided(CCRenderState renderState, IVertexOperation[] pipeline, EnumFacing side, boolean hasBase, boolean hasRotor, boolean isActive) {
        for(int i = 0; i < 9; i++) {
            int x = 1 - i % 3;
            int y = 1 - i / 3;
            RelativeTranslation translation;
            switch (side.getAxis()) {
                case X: translation = new RelativeTranslation(0, y, side.getAxisDirection() == AxisDirection.POSITIVE ? x : -x); break;
                case Y: translation = new RelativeTranslation(x, 0, y); break;
                case Z: translation = new RelativeTranslation(side.getAxisDirection() == AxisDirection.POSITIVE ? -x : x, y, 0); break;
                default: throw new IllegalArgumentException(side.toString());
            }
            IVertexOperation[] offset = ArrayUtils.add(pipeline, translation);
            if(hasBase) {
                MetaTileEntity.renderFace(renderState, side, Cuboid6.full, baseTexture[i], offset);
            }
            if(hasRotor) {
                if(!isActive) {
                    MetaTileEntity.renderFace(renderState, side, Cuboid6.full, bladeTexture[i], offset);
                } else {
                    MetaTileEntity.renderFace(renderState, side, Cuboid6.full, activeBladeTexture[i], offset);
                }
            }
        }

    }
}
