package gregtech.api.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import codechicken.lib.vec.Rotation;
import gregtech.api.GTValues;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class ChestRenderer implements IIconRegister {

    private static final Cuboid6 mainBox = new Cuboid6(1 / 16.0, 1 / 16.0, 1 / 16.0, 15 / 16.0, 10 / 16.0, 15 / 16.0);
    private static final Cuboid6 capBox = new Cuboid6(1 / 16.0, 9 / 16.0, 1 / 16.0, 15 / 16.0, 14 / 16.0, 15 / 16.0);
    private static final Cuboid6 lockBox = new Cuboid6(7 / 16.0, 8 / 16.0, 0 / 16.0, 9 / 16.0, 12 / 16.0, 1 / 16.0);
    private static final List<EnumFacing> rotations = Arrays.asList(EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.EAST);

    private final String basePath;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] textures;

    public ChestRenderer(String basePath) {
        this.basePath = basePath;
        Textures.iconRegisters.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        String formattedBase = GTValues.MODID + ":blocks/" + basePath;
        this.textures = new TextureAtlasSprite[7];
        this.textures[0] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/base_bottom"));
        this.textures[1] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/base_top"));
        this.textures[2] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/base_side"));
        this.textures[3] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/base_front"));
        this.textures[4] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/cap_bottom"));
        this.textures[5] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/cap_top"));
        this.textures[6] = textureMap.registerSprite(new ResourceLocation(formattedBase + "/lock"));
    }

    @SideOnly(Side.CLIENT)
    public void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, EnumFacing rotation, float capRotation) {
        translation.translate(0.5, 0.5, 0.5);
        translation.rotate(Math.toRadians(90.0 * rotations.indexOf(rotation)), Rotation.axes[1]);
        translation.translate(-0.5, -0.5, -0.5);

        for (EnumFacing renderSide : EnumFacing.VALUES) {
            TextureAtlasSprite baseSprite = renderSide.getAxis() == Axis.Y ? textures[renderSide.getIndex()] : renderSide == EnumFacing.NORTH ? textures[3] : textures[2];
            Textures.renderFace(renderState, translation, pipeline, renderSide, mainBox, baseSprite);
        }

        translation.translate(0.5, 9 / 16.0, 15 / 16.0);
        translation.rotate(Math.toRadians(capRotation), Rotation.axes[5]);
        translation.translate(-0.5, -9 / 16.0, -15 / 16.0);

        for (EnumFacing renderSide : EnumFacing.VALUES) {
            TextureAtlasSprite capSprite = renderSide.getAxis() == Axis.Y ? textures[4 + renderSide.getIndex()] : renderSide == EnumFacing.NORTH ? textures[3] : textures[2];
            TextureAtlasSprite lockSprite = textures[6];
            Textures.renderFace(renderState, translation, pipeline, renderSide, capBox, capSprite);
            Textures.renderFace(renderState, translation, pipeline, renderSide, lockBox, lockSprite);
        }
    }


    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return textures[0];
    }
}
