package gregtech.common.render;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

/**
 * Util for easy rendering quads based on side and with custom size
 */
@SideOnly(Side.CLIENT)
public class RenderUtil {

    private static FaceBakery FACE_BAKERY = new FaceBakery();
    private static BlockFaceUV DEFAULT_UV = new BlockFaceUV(new float[] {0F, 0F, 16F, 16F}, 0);

    public static BakedQuad renderSide(TextureAtlasSprite sprite, EnumFacing side, float offset, int color) {
        switch (side) {
            case DOWN:
                return renderQuadCustom(0F, 0F - offset, 0F, 16F, 16F, sprite, side, color);
            case UP:
                return renderQuadCustom(0F, 16F + offset, 0F, 16F, 16F, sprite, side, color);
            case WEST:
                return renderQuadCustom(0F - offset, 0F, 0F, 16F, 16F, sprite, side, color);
            case EAST:
                return renderQuadCustom(16F + offset, 0F, 0F, 16F, 16F, sprite, side, color);
            case NORTH:
                return renderQuadCustom(0F, 0F, 0F - offset, 16F, 16F, sprite, side, color);
            case SOUTH:
                return renderQuadCustom(0F, 0F, 16F + offset, 16F, 16F, sprite, side, color);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BakedQuad renderQuadCustom(float x, float y, float z, float width, float height, TextureAtlasSprite sprite, EnumFacing side, int color) {
        Vector3f from = new Vector3f(x, y, z);
        Vector3f to;
        switch (side) {
            case DOWN:
            case UP:
                to = new Vector3f(x + width, y, z + height);
                break;
            case WEST:
            case EAST:
                to = new Vector3f(x, y + width, z + height);
                break;
            case NORTH:
            case SOUTH:
                to = new Vector3f(x + width, y + height, z);
                break;
            default:
                to = new Vector3f(x, y, z);
        }

        return FACE_BAKERY.makeBakedQuad(from, to,
                new BlockPartFace(side, color, sprite.getIconName(), DEFAULT_UV),
                sprite, side, ModelRotation.X0_Y0, null, false, true);
    }


}
