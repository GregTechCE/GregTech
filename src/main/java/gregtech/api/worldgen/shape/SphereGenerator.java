package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreConfigUtils;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;

import java.util.Random;

public class SphereGenerator implements IShapeGenerator {

    private int radiusMin;
    private int radiusMax;

    public SphereGenerator() {
    }

    public SphereGenerator(int radiusMin, int radiusMax) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        int[] data = OreConfigUtils.getIntRange(object.get("radius"));
        this.radiusMin = data[0];
        this.radiusMax = data[1];
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess) {
        int sphereRadius = radiusMin >= radiusMax ? radiusMin : gridRandom.nextInt(radiusMax - radiusMin);
        for(int x = -sphereRadius; x <= sphereRadius; x++) {
            for(int z = -sphereRadius; z <= sphereRadius; z++) {
                for(int y = -sphereRadius; y <= sphereRadius; y++) {
                    if(x * x + y * y + z * z > sphereRadius * sphereRadius)
                        continue;
                    relativeBlockAccess.generateBlock(x, y, z);
                }
            }
        }

    }
}
