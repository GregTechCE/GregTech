package gregtech.api.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import java.util.ArrayList;

@Cancelable
public class BlockScanningEvent extends WorldEvent {

    public final EntityPlayer player;
    public final int scanLevel;
    public final BlockPos blockPos;
    public final ArrayList<String> information;
    public final EnumFacing side;
    public final float clickX, clickY, clickZ;
    public final TileEntity tileEntity;
    public final IBlockState blockState;

    /**
     * used to determine the amount of energy this scan costs.
     */
    public int EUCost;

    public BlockScanningEvent(World world, EntityPlayer player, int scanLevel, BlockPos blockPos, ArrayList<String> information, EnumFacing side, float clickX, float clickY, float clickZ, TileEntity tileEntity, IBlockState blockState) {
        super(world);
        this.player = player;
        this.scanLevel = scanLevel;
        this.blockPos = blockPos;
        this.information = information;
        this.side = side;
        this.clickX = clickX;
        this.clickY = clickY;
        this.clickZ = clickZ;
        this.tileEntity = tileEntity;
        this.blockState = blockState;
        this.EUCost = 0;
    }

}