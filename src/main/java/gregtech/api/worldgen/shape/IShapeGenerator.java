package gregtech.api.worldgen.shape;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gregtech.api.worldgen.generator.IBlockGeneratorAccess;

import java.util.Random;

public interface IShapeGenerator {

    void loadFromConfig(JsonObject object);

    void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess);

    static int[] getIntRange(JsonElement element) {
        if(element.isJsonArray()) {
            JsonArray dataArray = element.getAsJsonArray();
            int min = dataArray.get(0).getAsInt();
            int max = dataArray.get(1).getAsInt();
            return new int[] {min, max};
        } else if(element.isJsonObject()) {
            JsonObject dataObject = element.getAsJsonObject();
            int min = dataObject.get("min").getAsInt();
            int max = dataObject.get("max").getAsInt();
            return new int[] {min, max};
        } else if(element.isJsonPrimitive()) {
            int size = element.getAsInt();
            return new int[] {size, size};
        } else {
            throw new IllegalArgumentException("size range not defined");
        }
    }

}
