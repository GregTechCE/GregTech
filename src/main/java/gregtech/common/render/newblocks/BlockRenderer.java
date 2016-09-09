package gregtech.common.render.newblocks;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.items.GT_Generic_Block;
import gregtech.common.render.newitems.ModelUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockRenderer {

    public static boolean shouldHook(IBlockState blockState) {
        if(blockState.getBlock() instanceof IBlockIconProvider) {
            return true;
        }
        if(blockState.getBlock() instanceof ITextureBlockIconProvider) {
            return true;
        }
        return false;
    }

    public static IBakedModel hook(IBlockState blockState) {
        if(blockState.getBlock() instanceof IBlockIconProvider) {
            return ICON_PROVIDER;
        }
        if(blockState.getBlock() instanceof ITextureBlockIconProvider) {
            return TEXTURE_PROVIDER;
        }
        return null;
    }

    private static final IconProviderModel ICON_PROVIDER = new IconProviderModel();
    private static final TextureProviderModel TEXTURE_PROVIDER = new TextureProviderModel();


    public static IBakedModel makeTextureProviderItemblock(ItemStack stack, EntityLivingBase holder) {
        return new ItemblockTextureProviderModel(stack, holder instanceof EntityPlayer ? (EntityPlayer) holder : null);
    }

    public static IBakedModel makeIconProviderItemblock(ItemStack stack, EntityLivingBase holder) {
        return new ItemblockIconProviderModel(stack, holder instanceof EntityPlayer ? (EntityPlayer) holder : null);
    }

    private static class ItemblockIconProviderModel extends AbstractModel {

        private final ItemStack itemStack;
        private final EntityPlayer holder;
        private final Block block;

        public ItemblockIconProviderModel(ItemStack itemStack, EntityPlayer holder) {
            this.itemStack = itemStack;
            this.holder = holder;
            this.block = Block.getBlockFromItem(itemStack.getItem());
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side != null) {
                TextureAtlasSprite sideIcon = getSideSprite(side);
                if (sideIcon != null) {
                    BakedQuad faceQuad = RenderUtil.renderSide(DefaultVertexFormats.BLOCK, sideIcon, side, side.getIndex(), 0.0F, 0xFFFFFF);
                    if (faceQuad != null) {
                        return Collections.singletonList(faceQuad);
                    }
                }
            }
            return Collections.emptyList();
        }

        public TextureAtlasSprite getSideSprite(EnumFacing side) {
            IBlockIconProvider provider = (IBlockIconProvider) block;
            return provider.getIcon(holder, itemStack, side);
        }

    }

    private static class IconProviderModel extends AbstractModel {

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side != null && state != null) {
                TextureAtlasSprite sideIcon = getSideSprite(side, state);
                if (sideIcon != null) {
                    BakedQuad faceQuad = RenderUtil.renderSide(DefaultVertexFormats.BLOCK, sideIcon, side, side.getIndex(), 0.0F, 0xFFFFFF);
                    if (faceQuad != null) {
                        return Collections.singletonList(faceQuad);
                    }
                }
            }
            return Collections.emptyList();
        }

        public TextureAtlasSprite getSideSprite(EnumFacing side, IBlockState blockState) {
            BlockPos pos = ((IExtendedBlockState) blockState).getValue(GT_Generic_Block.BLOCK_POS);
            IBlockIconProvider provider = (IBlockIconProvider) blockState.getBlock();
            return provider.getIcon(Minecraft.getMinecraft().theWorld, pos, side, blockState.getValue(GT_Generic_Block.METADATA));
        }

    }

    private static class TextureProviderModel extends AbstractModel {

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side != null && state != null) {
                ArrayList<BakedQuad> quads = new ArrayList<>();
                IExtendedBlockState blockState = (IExtendedBlockState) state;
                BlockPos pos = blockState.getValue(GT_Generic_Block.BLOCK_POS);
                ITextureBlockIconProvider provider = (ITextureBlockIconProvider) blockState.getBlock();
                ITexture[] textures = provider.getTexture(Minecraft.getMinecraft().theWorld, pos, blockState, side);
                for (ITexture texture : textures) {
                    if(texture != null) {
                        quads.addAll(texture.getQuads(state.getBlock(), pos, side, 0));
                    }
                }
                return quads;
            }
            return Collections.emptyList();
        }

    }

    private static class ItemblockTextureProviderModel extends AbstractModel {

        private final ItemStack itemStack;
        private final EntityPlayer holder;
        private final Block block;

        public ItemblockTextureProviderModel(ItemStack itemStack, EntityPlayer holder) {
            this.itemStack = itemStack;
            this.holder = holder;
            this.block = Block.getBlockFromItem(itemStack.getItem());
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            if(side != null) {
                ArrayList<BakedQuad> quads = new ArrayList<>();
                ITextureBlockIconProvider provider = (ITextureBlockIconProvider) block;
                ITexture[] textures = provider.getItemblockTexture(holder, itemStack, side);
                for (ITexture texture : textures) {
                    if(texture != null) {
                        quads.addAll(texture.getQuads(block, null, side, side.getIndex() * 1000));
                    }
                }
                return quads;
            }
            return Collections.emptyList();
        }

    }

    private static abstract class AbstractModel implements IBakedModel {

        @Override
        public boolean isAmbientOcclusion() {
            return false;
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
        public ItemCameraTransforms getItemCameraTransforms() {
            return ModelUtil.BLOCK_TRANSFORMS;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return null;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return Textures.BlockIcons.BLOCK_STEEL.getIcon();
        }

    }


}
