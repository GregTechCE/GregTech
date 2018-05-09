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
        this.radiusMin = data[0] - 30;
        this.radiusMax = data[1] + 30;
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess blockAccess) {
        long a = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long b = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin) / 2;
        long c = (long) (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        long ab2 = a * a * b * b, ac2 = a * a * c * c, bc2 = b * b * c * c, abc2 = ab2 * c * c;

        int max = (int) Math.max(a, Math.max(b, c));
        for (int x = -max; x <= max; x++) {
            for (int y = -max; y <= max; y++) {
                for (int z = -max; z <= max; z++) {
                    if (bc2 * x * x + ac2 * y * y + ab2 * z * z > abc2) continue;
                    blockAccess.generateBlock(x, y, z);
                }
            }
        }
    }

}
