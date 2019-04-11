package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.worldgen.config.OreConfigUtils;
import net.minecraft.util.math.Vec3i;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Random;

@ZenClass("mods.gregtech.ore.generator.EllipsoidGenerator")
@ZenRegister
public class EllipsoidGenerator extends ShapeGenerator {

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
        int[] data = OreConfigUtils.getIntRange(object.get("radius"));
        this.radiusMin = data[0];
        this.radiusMax = data[1];
    }

    @Override
    public Vec3i getMaxSize() {
        return new Vec3i(radiusMax * 2, radiusMax, radiusMax * 2);
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess blockAccess) {
        int a = radiusMin == radiusMax ? radiusMax : (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        int b = radiusMin == radiusMax ? radiusMax / 2 : (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin) / 2;
        int c = radiusMin == radiusMax ? radiusMax : (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        int ab2 = a * a * b * b, ac2 = a * a * c * c, bc2 = b * b * c * c, abc2 = ab2 * c * c;

        int max = Math.max(a, Math.max(b, c));
        for (int x = -max; x <= max; x++) {
            int xr = bc2 * x * x;
            if (xr > abc2) continue;
            for (int y = -max; y <= max; y++) {
                int yr = xr + ac2 * y * y + ab2;
                if (yr > abc2) continue;
                for (int z = -max; z <= max; z++) {
                    int zr = yr + ab2 * z * z;
                    if (zr > abc2) continue;
                    blockAccess.generateBlock(x, y, z);
                }
            }
        }
    }

}
