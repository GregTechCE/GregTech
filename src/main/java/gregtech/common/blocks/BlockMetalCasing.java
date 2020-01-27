package gregtech.common.blocks;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils.IIconRegister;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.SolidMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class BlockMetalCasing extends Block  implements ICubeRenderer, IIconRegister {

    private final SolidMaterial metalCasingMaterial;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite sprite;

    public BlockMetalCasing(SolidMaterial metalCasingMaterial) {
        super(Material.IRON);
        this.metalCasingMaterial = metalCasingMaterial;
        setUnlocalizedName("metal_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        Textures.iconRegisters.add(this);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }

    public SolidMaterial getMetalCasingMaterial() {
        return metalCasingMaterial;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        this.sprite = textureMap.registerSprite(MaterialIconType.metalCasing.getBlockPath(metalCasingMaterial.materialIconSet));
    }

    @SideOnly(Side.CLIENT)
    public void renderSided(EnumFacing side, Matrix4 translation, Cuboid6 bounds, CCRenderState renderState, IVertexOperation[] pipeline) {
        Textures.renderFace(renderState, translation, pipeline, side, bounds, sprite);
    }

    @SideOnly(Side.CLIENT)
    public void renderSided(EnumFacing side, CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        renderSided(side, translation, Cuboid6.full, renderState, pipeline);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite() {
        return sprite;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 bounds) {
        renderState.baseColour = metalCasingMaterial.materialRGB << 8;
        renderState.alphaOverride = 0xFF;
        for (EnumFacing side : EnumFacing.values()) {
            renderSided(side, translation, bounds, renderState, pipeline);
        }
    }
}
