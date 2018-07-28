package gregtech.common.pipelike.itempipes;

import gregtech.api.worldentries.pipenet.PipeNet;
import gregtech.api.worldentries.pipenet.WorldPipeNet;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;

public class ItemPipeNet extends PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> {

    public ItemPipeNet(WorldPipeNet worldNet) {
        super(ItemPipeFactory.INSTANCE, worldNet);
    }

    @Override
    protected void transferNodeDataTo(Collection<? extends BlockPos> nodeToTransfer, PipeNet<TypeItemPipe, ItemPipeProperties, IItemHandler> toNet) {

    }

    @Override
    protected void removeData(BlockPos pos) {

    }
}
