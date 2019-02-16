package gregtech.api.worldgen.shape;

import com.google.gson.JsonElement;
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
    private int heightMin;
    private int heightMax;

    public EllipsoidGenerator() {
    }



    public EllipsoidGenerator(int radiusMin, int radiusMax) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        int[] radiusData = OreConfigUtils.getIntRange(object.get("radius"));
        this.radiusMin = radiusData[0];
        this.radiusMax = radiusData[1];

        JsonElement heightJson = object.get("height");
        if (heightJson != null) {
            int[] heightData = OreConfigUtils.getIntRange(heightJson);
            if (heightData.length == 2) {
                this.heightMin = heightData[0];
                this.heightMax = heightData[1];
            }
        }
    }

    @Override
    public Vec3i getMaxSize() {
        return new Vec3i(radiusMax * 2, heightMax == 0 ? radiusMax : heightMax * 2, radiusMax * 2);
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess blockAccess) {
        int a = radiusMin >= radiusMax ? radiusMin : (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin);
        int b;
        if (heightMin == 0) {
            // default if no height prop provided (also: originally coded behavior)
            b = radiusMin >= radiusMax ? radiusMin / 2 : (gridRandom.nextInt(radiusMax - radiusMin) + radiusMin) / 2;
        } else {
            b = heightMin >= heightMax ? heightMin : (gridRandom.nextInt(heightMax - heightMin) + heightMin);
        }
        int c = a; // somewhat pointless, but establishes pattern for other (non-symmetrical) shapes.  Ellipsoids will always be circular as viewed from above.

        int ab2 = a * a * b * b, ac2 = a * a * c * c, bc2 = b * b * c * c, abc2 = ab2 * c * c;

        for (int x = -a; x <= a; x++) {
            int xr = bc2 * x * x;

            for (int y = -b; y <= b; y++) {
                int yr = xr + ac2 * y * y + ab2;
                if(yr > abc2) continue;

                for (int z = -c; z <= c; z++) {
                    int zr = yr + ab2 * z * z;
                    if (zr > abc2) continue;
                    blockAccess.generateBlock(x, y, z);
                }
            }
        }
    }

}
