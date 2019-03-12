package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreDepositDefinition;

public interface IVeinPopulator {

    void loadFromConfig(JsonObject object);

    void initializeForVein(OreDepositDefinition definition);

}
