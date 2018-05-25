package gregtech.api.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Optional;

@SideOnly(Side.CLIENT)
public abstract class AbstractBakedModel implements IBakedModel {

	private VertexFormat format;

	public AbstractBakedModel(VertexFormat format) {
		this.format = format;
	}

	protected void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, TextureAtlasSprite sprite, double x, double y, double z, float u, float v, int rgbaColor) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
				case POSITION:
					builder.put(e, (float) x, (float) y, (float) z, 1.0f);
					break;
				case COLOR:
					float red = ((rgbaColor >> 16) & 0xFF) / 255.0f;
					float green = ((rgbaColor >> 8) & 0xFF) / 255.0f;
					float blue = ((rgbaColor) & 0xFF) / 255.0f;
					builder.put(e, red, green, blue, 1.0f);
					break;
				case UV:
					if (format.getElement(e).getIndex() == 0) {
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}

	protected BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, EnumFacing orientation, int rgbaColor) {
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();
        if (sprite == null) {
            sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		putVertex(builder, normal, sprite, v1.x, v1.y, v1.z, 0, 0, rgbaColor);
		putVertex(builder, normal, sprite, v2.x, v2.y, v2.z, 0, 16, rgbaColor);
		putVertex(builder, normal, sprite, v3.x, v3.y, v3.z, 16, 16, rgbaColor);
		putVertex(builder, normal, sprite, v4.x, v4.y, v4.z, 16, 0, rgbaColor);
		builder.setQuadOrientation(orientation);
		return builder.build();
	}

}
