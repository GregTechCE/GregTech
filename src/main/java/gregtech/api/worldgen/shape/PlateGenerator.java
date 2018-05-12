package gregtech.api.worldgen.shape;

import codechicken.lib.vec.Vector3;
import com.google.gson.JsonObject;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;

import java.util.Random;

public class PlateGenerator implements IShapeGenerator {

    private static final Vector3 zRotation = new Vector3(0, 0, 1);

    private int minLength;
    private int maxLength;
    private int minDepth;
    private int maxDepth;
    private int minHeight;
    private int maxHeight;
    private float floorSharpness;
    private float roofSharpness;

    public PlateGenerator() {
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        int[] length = IShapeGenerator.getIntRange(object.get("length"));
        int[] depth = IShapeGenerator.getIntRange(object.get("depth"));
        int[] height = IShapeGenerator.getIntRange(object.get("height"));
        this.minLength = length[0];
        this.maxLength = length[1];
        this.minDepth = depth[0];
        this.maxDepth = depth[1];
        this.minHeight = height[0];
        this.maxHeight = height[1];
        if(object.has("floor_sharpness")) {
            this.floorSharpness = object.get("floor_sharpness").getAsFloat();
        } else this.floorSharpness = 0.3f;
        if(object.has("roof_sharpness")) {
            this.roofSharpness = object.get("roof_sharpness").getAsFloat();
        } else this.roofSharpness = 0.7f;
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess) {
        int length = (minLength + minLength >= maxLength ? 0 : gridRandom.nextInt(maxLength - minLength)) / 2;
        int depth = (minDepth + minDepth >= maxDepth ? 0 : gridRandom.nextInt(maxDepth - minDepth)) / 2;
        int height = (minHeight + minHeight >= maxHeight ? 0 : gridRandom.nextInt(maxHeight - minHeight)) / 2;
        for(int x = -length; x <= length; x++) {
            for(int z = -depth; z <= depth; depth++) {
                boolean hasFloorSub = floorSharpness > gridRandom.nextFloat();
                boolean hasRoofSub = roofSharpness > gridRandom.nextFloat();
                for(int y = -height; y <= height; y++) {
                    if(hasRoofSub && (y == height || gridRandom.nextBoolean())) {
                        continue;
                    } else hasRoofSub = false;
                    if(hasFloorSub && y == -height)
                        continue;
                    relativeBlockAccess.generateBlock(x, y, z);
                }
            }
        }


    }
}
