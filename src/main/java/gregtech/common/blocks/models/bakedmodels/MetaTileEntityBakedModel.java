package gregtech.common.blocks.models.bakedmodels;

import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.model.AbstractBakedModel;
import gregtech.common.blocks.properties.UnlistedBlockAccess;
import gregtech.common.blocks.properties.UnlistedBlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MetaTileEntityBakedModel extends AbstractBakedModel {

    private Map<String, TextureAtlasSprite> textures;

    public MetaTileEntityBakedModel(IModelState state, VertexFormat format, Map<String, TextureAtlasSprite> textures) {
        super(format);
        this.textures = textures;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

        if (side != null) {
            return Collections.emptyList();
        }

        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        TextureAtlasSprite[] sides = new TextureAtlasSprite[6];

        sides[0] = textures.get("down");
        sides[1] = textures.get("up");
        sides[2] = textures.get("north");
        sides[3] = textures.get("south");
        sides[4] = textures.get("west");
        sides[5] = textures.get("east");

        if (extendedBlockState != null) {
            IBlockAccess blockAccess = extendedBlockState.getValue(UnlistedBlockAccess.BLOCK_ACCESS);
            BlockPos pos = extendedBlockState.getValue(UnlistedBlockPos.BLOCK_POS);
            TileEntity tileEntity = blockAccess.getTileEntity(pos);
            if (tileEntity instanceof GregtechTileEntity) {
                IMetaTileEntity metaTileEntity = ((GregtechTileEntity) tileEntity).getMetaTileEntity();

            }
        }

        List<BakedQuad> quads = new ArrayList<>();
        //down
        quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sides[0], EnumFacing.DOWN, 0xFFFFFF));
        // up
        quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), sides[1], EnumFacing.UP, 0xFFFFFF));
        //north
        quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sides[2], EnumFacing.NORTH, 0xFFFFFF));
        //south
        quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sides[3], EnumFacing.SOUTH, 0xFFFFFF));
        //west
        quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sides[4], EnumFacing.WEST, 0xFFFFFF));
        //east
        quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), sides[5], EnumFacing.EAST, 0xFFFFFF));

        return quads;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
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
        return Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
    }
}