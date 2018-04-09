package gregtech.loaders.postload;

import net.minecraftforge.common.config.Configuration;

import java.util.Random;

public interface IShapeGenerator {
    void loadFromConfig(Configuration config, String category);
    void generate(Random gridRandom);
    ShapeType getShapeType();
}
