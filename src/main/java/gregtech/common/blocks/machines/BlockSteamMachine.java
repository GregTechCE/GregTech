package gregtech.common.blocks.machines;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.Collection;

public class BlockSteamMachine extends BlockMachine {

    public static final PropertyDirection VENTING_FACE = PropertyDirection.create("venting_face", Arrays.asList(EnumFacing.VALUES));

    public BlockSteamMachine(Collection<String> mteTypeCollection) {
        super(mteTypeCollection);
    }

    @Override
    protected BlockStateContainer createStateContainer() {
        return new BlockStateContainer(this, ACTIVE, FACING, META_TYPE, VENTING_FACE);
    }

    @Override
    protected String getBlockStateSubFolder() {
        return "steam/";
    }
}
