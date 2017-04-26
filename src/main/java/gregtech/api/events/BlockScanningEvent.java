package gregtech.api.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

@net.minecraftforge.fml.common.eventhandler.Cancelable
public class BlockScanningEvent extends net.minecraftforge.event.world.WorldEvent {

    public final EntityPlayer mPlayer;
    public final int mScanLevel;
    public final BlockPos mPos;
    public final ArrayList<String> mList;
    public final EnumFacing mSide;
    public final float mClickX, mClickY, mClickZ;
    public final TileEntity mTileEntity;
    public final IBlockState mBlock;

    /**
     * used to determine the amount of Energy this Scan is costing.
     */
    public int mEUCost = 0;

    public BlockScanningEvent(World aWorld, EntityPlayer aPlayer, BlockPos aPos, EnumFacing aSide, int aScanLevel, IBlockState aBlock, TileEntity aTileEntity, ArrayList<String> aList, float aClickX, float aClickY, float aClickZ) {
        super(aWorld);
        mPlayer = aPlayer;
        mScanLevel = aScanLevel;
        mTileEntity = aTileEntity;
        mBlock = aBlock;
        mList = aList;
        mSide = aSide;
        mPos = aPos.toImmutable();
        mClickX = aClickX;
        mClickY = aClickY;
        mClickZ = aClickZ;
    }
}