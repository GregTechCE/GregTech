package gregtech.common.blocks.models;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import gregtech.api.GT_Values;
import gregtech.common.blocks.BlockCasings4;
import gregtech.common.blocks.properties.UnlistedBlockAccess;
import gregtech.common.blocks.properties.UnlistedBlockPos;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CasingModel implements IModel {

	public static final ModelResourceLocation BAKED_CASING_MODEL = new ModelResourceLocation(new ResourceLocation(GT_Values.MODID, "fusion_casing"), "normal");
	public static final ModelResourceLocation BAKED_CASING_MK2_MODEL = new ModelResourceLocation(new ResourceLocation(GT_Values.MODID, "fusion_casing_mk2"), "normal");

	public static final IModel CASING_MODEL = create("blocks/iconsets/FUSIONI_", 12);
	public static final IModel CASING_MK2_MODEL = create("blocks/iconsets/FUSIONII_", 12);

	private static IModel create(String prefix, int amount) {
		List<ResourceLocation> textures = new ArrayList<>(amount);
		for (int i = 0; i < amount; i++) {
			textures.add(i, new ResourceLocation(GT_Values.MODID, prefix + (i + 1)));
		}
		return new CasingModel(textures);
	}

	private final List<ResourceLocation> connectedTextures;

	public CasingModel(List<ResourceLocation> connectedTextures) {
		this.connectedTextures = connectedTextures;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		List<TextureAtlasSprite> textures = new ArrayList<>(12);
		for (ResourceLocation resourceLocation : connectedTextures) {
			textures.add(bakedTextureGetter.apply(resourceLocation));
		}
		return new CasingBakedModel(state, format, textures);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableSet.copyOf(connectedTextures);
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	public enum CasingBakedModelLoader implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(GT_Values.MODID)
					&& ("fusion_casing".equals(modelLocation.getResourcePath())
					|| "fusion_casing_mk2".equals(modelLocation.getResourcePath()));
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws Exception {
			switch (modelLocation.getResourcePath()) {
				case "fusion_casing":
					return CASING_MODEL;
				case "fusion_casing_mk2":
					return CASING_MK2_MODEL;
			}
			return null;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {
		}
	}

	public static class CasingBakedModel extends AbstractBakedModel {

		private List<TextureAtlasSprite> connectedTextures;

		public CasingBakedModel(IModelState state, VertexFormat format, List<TextureAtlasSprite> connectedTextures) {
			super(format);
			this.connectedTextures = connectedTextures;
		}

		private static <T extends Comparable<T>> boolean equalsListedProperty(IBlockState state, IBlockAccess blockAccess, BlockPos pos, IProperty<T> property) {
			IBlockState newState = blockAccess.getBlockState(pos);
			if (state.getBlock() != newState.getBlock()) return false;
			return newState.getValue(property) == state.getValue(property);
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

			if (side != null) {
				return Collections.emptyList();
			}

			IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

			TextureAtlasSprite[] sides = new TextureAtlasSprite[6];
			TextureAtlasSprite[] connectedHulls = connectedTextures.toArray(new TextureAtlasSprite[12]);

			int startIndex = 0;

			if (extendedBlockState != null) {
				IBlockAccess blockAccess = extendedBlockState.getValue(UnlistedBlockAccess.BLOCKACCESS);
				BlockPos pos = extendedBlockState.getValue(UnlistedBlockPos.POS);

				boolean[] connectedSides = {
						equalsListedProperty(state, blockAccess, pos.down(), BlockCasings4.CASING_VARIANT),
						equalsListedProperty(state, blockAccess, pos.up(), BlockCasings4.CASING_VARIANT),
						equalsListedProperty(state, blockAccess, pos.north(), BlockCasings4.CASING_VARIANT),
						equalsListedProperty(state, blockAccess, pos.south(), BlockCasings4.CASING_VARIANT),
						equalsListedProperty(state, blockAccess, pos.west(), BlockCasings4.CASING_VARIANT),
						equalsListedProperty(state, blockAccess, pos.east(), BlockCasings4.CASING_VARIANT),
				};

				if (connectedSides[0]) {
					sides[0] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 5];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 3];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 2];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 4];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[0] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && !connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[0] = connectedHulls[startIndex];
				}
				else if (connectedSides[1]) {
					sides[0] = connectedHulls[startIndex + 6];
				}

				if (connectedSides[1]) {
					sides[1] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 3];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 5];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 2];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 4];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[4] && !connectedSides[5]) {
					sides[1] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && !connectedSides[3] && !connectedSides[4] && connectedSides[5]) {
					sides[1] = connectedHulls[startIndex];
				}
				else if (connectedSides[0]) {
					sides[1] = connectedHulls[startIndex + 6];
				}

				if (connectedSides[2]) {
					sides[2] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[0] && connectedSides[1] && connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[0] && connectedSides[1] && connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 5];
				}
				else if (connectedSides[0] && !connectedSides[1] && connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 3];
				}
				else if (connectedSides[0] && connectedSides[1] && !connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 4];
				}
				else if (connectedSides[0] && connectedSides[1] && connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 2];
				}
				else if (!connectedSides[0] && !connectedSides[1] && connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex];
				}
				else if (!connectedSides[0] && connectedSides[1] && !connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[0] && !connectedSides[1] && !connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[0] && !connectedSides[1] && connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[0] && connectedSides[1] && !connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[0] && connectedSides[1] && connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[0] && !connectedSides[1] && !connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[0] && connectedSides[1] && !connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[0] && !connectedSides[1] && connectedSides[4] && !connectedSides[5]) {
					sides[2] = connectedHulls[startIndex];
				}
				else if (!connectedSides[0] && !connectedSides[1] && !connectedSides[4] && connectedSides[5]) {
					sides[2] = connectedHulls[startIndex];
				}
				else if (connectedSides[3]) {
					sides[2] = connectedHulls[startIndex + 6];
				}

				if (connectedSides[3]) {
					sides[3] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[1] && connectedSides[0] && connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[1] && connectedSides[0] && connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 3];
				}
				else if (connectedSides[1] && !connectedSides[0] && connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 5];
				}
				else if (connectedSides[1] && connectedSides[0] && !connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 2];
				}
				else if (connectedSides[1] && connectedSides[0] && connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 4];
				}
				else if (!connectedSides[1] && !connectedSides[0] && connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex];
				}
				else if (!connectedSides[1] && connectedSides[0] && !connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[1] && !connectedSides[0] && !connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[1] && !connectedSides[0] && connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[1] && connectedSides[0] && !connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[1] && connectedSides[0] && connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[1] && !connectedSides[0] && !connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[1] && connectedSides[0] && !connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[1] && !connectedSides[0] && connectedSides[4] && !connectedSides[5]) {
					sides[3] = connectedHulls[startIndex];
				}
				else if (!connectedSides[1] && !connectedSides[0] && !connectedSides[4] && connectedSides[5]) {
					sides[3] = connectedHulls[startIndex];
				}
				else if (connectedSides[2]) {
					sides[3] = connectedHulls[startIndex + 6];
				}

				if (connectedSides[4]) {
					sides[4] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 2];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 4];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 5];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 3];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[0] && !connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && !connectedSides[3] && !connectedSides[0] && connectedSides[1]) {
					sides[4] = connectedHulls[startIndex + 1];
				}
				else if (connectedSides[5]) {
					sides[4] = connectedHulls[startIndex + 6];
				}

				if (connectedSides[5]) {
					sides[5] = connectedHulls[startIndex + 7];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 6];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 4];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 2];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 3];
				}
				else if (connectedSides[2] && connectedSides[3] && connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 5];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 9];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 8];
				}
				else if (connectedSides[2] && !connectedSides[3] && connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 11];
				}
				else if (connectedSides[2] && connectedSides[3] && !connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 10];
				}
				else if (connectedSides[2] && !connectedSides[3] && !connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && connectedSides[3] && !connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex];
				}
				else if (!connectedSides[2] && !connectedSides[3] && connectedSides[1] && !connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 1];
				}
				else if (!connectedSides[2] && !connectedSides[3] && !connectedSides[1] && connectedSides[0]) {
					sides[5] = connectedHulls[startIndex + 1];
				}
				else if (connectedSides[4]) {
					sides[5] = connectedHulls[startIndex + 6];
				}
			}

			for (int i = 0; i < sides.length; i++) {
				if (sides[i] == null) {
					sides[i] = connectedHulls[startIndex + 7];
				}
			}

			List<BakedQuad> quads = new ArrayList<>();
			//down
			quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sides[0]));
			// up
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), sides[1]));
			//north
			quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sides[2]));
			//south
			quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sides[3]));
			//west
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sides[4]));
			//east
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), sides[5]));

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
			return connectedTextures.get(7);
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms() {
			return ItemCameraTransforms.DEFAULT;
		}
	}
}
