package gregtech.api.worldgen.shape;

import codechicken.lib.vec.Vector3;
import com.google.gson.JsonObject;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;

import java.util.Random;

public class EllipsoidGenerator implements IShapeGenerator {

    private static final Vector3 xRotation = new Vector3(1, 0, 0);
    private static final Vector3 yRotation = new Vector3(0, 1, 0);
    private static final Vector3 zRotation = new Vector3(0, 0, 1);

    private int radiusMin;
    private int radiusMax;

    public EllipsoidGenerator() {
    }

    public EllipsoidGenerator(int radiusMin, int radiusMax) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        int[] data = IShapeGenerator.getIntRange(object.get("radius"));
        this.radiusMin = data[0];
        this.radiusMax = data[1];
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess blockAccess) {
        long a = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long b = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin) / 2;
        long c = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long ab2 = a * a * b * b, ac2 = a * a * c * c, bc2 = b * b * c * c, abc2 = ab2 * c * c;

        float roll = (float) (gridRandom.nextFloat() * Math.PI);
        float pitch = (float) (gridRandom.nextFloat() * Math.PI);
        float yaw = (float) (gridRandom.nextFloat() * Math.PI);
        Vector3 point = new Vector3();
        int max = (int) Math.max(a, Math.max(b, c));
        for (int x = -max; x <= max; x++) {
            for (int y = -max; y <= max; y++) {
                for (int z = -max; z <= max; z++) {
                    point.set(x, y, z);
                    point.rotate(roll, xRotation);
                    point.rotate(pitch, yRotation);
                    point.rotate(yaw, zRotation);
                    if (bc2 * point.x * point.x + ac2 * point.y * point.y + ab2 * point.z * point.z > abc2) continue;
                    blockAccess.generateBlock((int) point.x, (int) point.y, (int) point.z);
                }
            }
        }
    }

}
