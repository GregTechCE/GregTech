package gregtech.common.blocks.models;


import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

public abstract class AbstractBakedModel implements IBakedModel {

	private VertexFormat format;

	public AbstractBakedModel(VertexFormat format) {
		this.format = format;
	}

	private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, TextureAtlasSprite sprite, double x, double y, double z, float u, float v) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
				case POSITION:
					builder.put(e, (float) x, (float) y, (float) z, 1.0f);
					break;
				case COLOR:
					builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
					break;
				case UV:
					if (format.getElement(e).getIndex() == 0) {
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, (float) normal.xCoord, (float) normal.yCoord, (float) normal.zCoord, 0f);
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}

	protected BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		putVertex(builder, normal, sprite, v1.xCoord, v1.yCoord, v1.zCoord, 0, 0);
		putVertex(builder, normal, sprite, v2.xCoord, v2.yCoord, v2.zCoord, 0, 16);
		putVertex(builder, normal, sprite, v3.xCoord, v3.yCoord, v3.zCoord, 16, 16);
		putVertex(builder, normal, sprite, v4.xCoord, v4.yCoord, v4.zCoord, 16, 0);
		return builder.build();
	}
}
