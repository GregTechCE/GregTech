package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;

import java.util.Random;

public interface IShapeGenerator {

    void loadFromConfig(JsonObject object);

    void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess);

}
