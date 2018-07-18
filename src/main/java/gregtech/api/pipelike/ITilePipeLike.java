package gregtech.api.pipelike;

import gregtech.api.cover.ICoverableTile;
import gregtech.api.unification.material.type.Material;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITilePipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty> extends ICoverableTile {
    PipeFactory<Q, P, ?> getFactory();
    World getWorld();
    BlockPos getPos();
    Material getMaterial();
    Q getBaseProperty();
    P getTileProperty();
    int getColor();
    void setColor(int color);

    int MASK_OUTPUT_DISABLED = 1;
    int MASK_INPUT_DISABLED = 1 << 6;
    int MASK_FORMAL_CONNECTION = 1 << 12;
    int MASK_BLOCKED = 1 << 18;

    /**
     * @return bitmask for internal connection status, considering the affect of covers.
     *          =  (render mask, 6 bits)       << 18  * Prevent other tiles from connecting to this tile
     *           | (formal connection, 6 bits)  << 12  * Forced to show the side box of this tile for render and collision. Set by the covers.
     *           | (input disabled, 6 bits)      << 6  * Disable network actions pass-in to this tile from certain side
     *           | (output disabled, 6 bits)           * Disable network actions pass-out from this tile from certain side
     */
    int getInternalConnections();

    void updateInternalConnection();
}
