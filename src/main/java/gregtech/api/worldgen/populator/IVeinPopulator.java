package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;

public interface IVeinPopulator {

    void loadFromConfig(JsonObject object);
}
