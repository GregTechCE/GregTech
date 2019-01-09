package gregtech.api.cover;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.function.Consumer;

public interface ICoverable {


    World getWorld();
    BlockPos getPos();

    long getTimer();

    void markDirty();
    boolean isValid();

    <T> T getCapability(Capability<T> capability, EnumFacing side);

    boolean placeCoverOnSide(EnumFacing side, ItemStack itemStack, CoverDefinition definition);

    boolean removeCover(EnumFacing side);

    boolean canPlaceCoverOnSide(EnumFacing side);

    CoverBehavior getCoverAtSide(EnumFacing side);

    void writeCoverData(CoverBehavior behavior, int id, Consumer<PacketBuffer> writer);
}
