package gregtech.common.blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedBlockPosProperty implements IUnlistedProperty<BlockPos> {

    private final String name;

    public UnlistedBlockPosProperty(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(BlockPos value) {
        return true;
    }

    @Override
    public Class<BlockPos> getType() {
        return BlockPos.class;
    }

    @Override
    public String valueToString(BlockPos value) {
        return value.toString();
    }

}
