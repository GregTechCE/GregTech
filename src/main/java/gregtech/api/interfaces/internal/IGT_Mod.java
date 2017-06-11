package gregtech.api.interfaces.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Interface used by the Mods Main Class to reference to internals.
 */
public interface IGT_Mod {

    /**
     * @return true if this is dedicated server side, false otherwise
     */
    boolean isServerSide();

    /**
     * @return true if this is client side, false otherwise
     */
    boolean isClientSide();

    /**
     * @return true if we are running on sponge server, false otherwise
     */
    boolean isSpongeSide();

    /**
     * @return player on clientside, null on serverside
     */
    EntityPlayer getThePlayer();

    //---------- Internal Usage Only ----------
    /**
     * Plays the Sonictron Sound for the ItemStack on the Client Side
     */
    void doSonictronSound(ItemStack stack, World world, double x, double y, double z);

}