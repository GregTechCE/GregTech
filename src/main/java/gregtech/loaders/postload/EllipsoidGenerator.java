package gregtech.loaders.postload;

import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;

import javax.vecmath.Matrix3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Random;

public class EllipsoidGenerator implements IShapeGenerator {
    public int radiusMin;
    public int radiusMax;

    public EllipsoidGenerator() {
    }

    public EllipsoidGenerator(int radiusMin, int radiusMax) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }

    @Override
    public void loadFromConfig(Configuration config, String category) {
        radiusMin = config.get(category, "radiusMin", radiusMin).getInt();
        radiusMax = config.get(category, "radiusMax", radiusMax).getInt();
    }

    @Override
    public void generate(Random gridRandom) {
        int depositY = gridRandom.nextInt(8) + 32;

        long a = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long b = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin) / 2; //todo Y ratio
        long c = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long ab2 = a * a * b * b, ac2 = a * a * c * c, bc2 = b * b * c * c, abc2 = ab2 * c * c;

        float roll = (float) (gridRandom.nextFloat() * Math.PI);
        float pitch = (float) (gridRandom.nextFloat() * Math.PI);
        float yaw = (float) (gridRandom.nextFloat() * Math.PI);

        Matrix3f matrix = getRotationMatrix(roll, pitch, yaw);

        int max = (int) Math.max(a, Math.max(b, c));
        for (int x = -max; x <= max; x++) {
            Vector3f xPart = new Vector3f(matrix.m00 * x, matrix.m10 * x, matrix.m20 * x);
            for (int y = -max; y <= max; y++) {
                Vector3f xyPart = new Vector3f(matrix.m01 * y, matrix.m11 * y, matrix.m21 * y);
                xyPart.add(xPart);
                for (int z = -max; z <= max; z++) {
                    Vector3f point = new Vector3f(matrix.m02 * z, matrix.m12 * z, matrix.m22 * z);
                    point.add(xyPart);
                    if (bc2 * point.x * point.x + ac2 * point.y * point.y + ab2 * point.z * point.z > abc2) continue;

//                    BlockPos pos = new BlockPos(x + depositX, y + depositY, z + depositZ);
//                    world.setBlockState(pos, MetaBlocks.RED_GRANITE.stone.get(), 18);
                }
            }
        }
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.ELLIPSOID;
    }

    private Vector3f multiply(Matrix3f m, Vector3f v) {
        return new Vector3f(
            v.x * m.m00 + v.y * m.m01 + v.z * m.m02,
            v.x * m.m10 + v.y * m.m11 + v.z * m.m12,
            v.x * m.m20 + v.y * m.m21 + v.z * m.m22);
    }

    /**
     * @return result of multiplication of three rotation matrix
     */
    private static Matrix3f getRotationMatrix(float roll, float pitch, float yaw) {
        float sr = MathHelper.sin(roll), cr = MathHelper.cos(roll);
        float sp = MathHelper.sin(pitch), cp = MathHelper.cos(pitch);
        float sy = MathHelper.sin(yaw), cy = MathHelper.cos(yaw);
        float ss = sr * sy, cs = cr * sy, sc = sr * cy, cc = cr * cy;
        return new Matrix3f(cp * cy, -cp * sy, sp,
            cs + sc * sp, cc - ss * sp, -sr * cp,
            ss - cc * sp, sc + cs * sp, cr * cp);
    }
}
