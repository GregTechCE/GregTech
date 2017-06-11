package gregtech.api.interfaces.metaitem;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

public interface INuclearStats extends IMetaItemStats {

    /**
     * This is to determine if the given Item can be put into a reactor.<br/>
     * Items already in the reactor won't get spilled out. This only gets called,
     * when placing the item in.
     * @param stack The given reactor component.
     * @param reactor The specific reactor to place the item in.
     * @return If the item is accepted in the reactor.
     */
    boolean canBePlacedIn(ItemStack stack, IReactor reactor);

    /**
     * Called by reactor upon iterating through it's inventory (every cycle).
     * Perform all necessary calculation/interaction here
     *
     * @param stack Reference to the specific instance of called ItemStack
     * @param reactor Reference to the Reactor
     * @param x X-coordinate of the stack in the grid
     * @param y Y-coordinate of the stack in the grid
     * @param heatrun every Stack will cycle 2 time (true, false) first run for heat, sec for Eu calculation
     */
    void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun);

    /**
     * Can be called by Uranium-Components who attempt to generate energy by pulsing to other components.
     * Uranium-Uranium interaction (f.e.) uses this method.
     * @param stack Reference to the specific instance of called ItemStack
     * @param reactor Reference to the Reactor
     * @param pulsingStack Reference to the specific instance of pulsing ItemStack
     * @param youX X-coordinate of your stack in the grid
     * @param youY Y-coordinate of your stack in the grid
     * @param pulseX X-coordinate of pulsing stack in the grid
     * @param pulseY Y-coordinate of pulsing stack in the grid
     * @param heatrun true for only create heat not EU, false for only EU  not heat
     * @return true if this component reacts to the pulse (and pulse is therefore meant to produce heat)
     */
    boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun);

    /**
     * Called by components to determine whether your component can be heated.
     * @param stack Reference to the specific instance of iterated ItemStack
     * @param reactor Reference to the Reactor
     * @param x X-coordinate of the stack in the grid
     * @param y Y-coordinate of the stack in the grid
     * @return true if your component can take heat
     */
    boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y);

    /**
     * Called by heat-switches to determine how much heat to distribute into which direction.
     * Please return the maximum capacity of your heat-containing component here.
     * @param stack Reference to the specific instance of iterated ItemStack
     * @param reactor Reference to the Reactor
     * @param x X-coordinate of the stack in the grid
     * @param y Y-coordinate of the stack in the grid
     * @return Maximum heat
     */
    int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y);

    /**
     * Called by heat-switches to determine how much heat to distribute into which direction.
     * Please return the current amount of heat stored in this component
     * @param stack Reference to the specific instance of iterated ItemStack
     * @param reactor Reference to the Reactor
     * @param x X-coordinate of the stack in the grid
     * @param y Y-coordinate of the stack in the grid
     * @return Current Heat
     */
    int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y);

    /**
     * Called by components to distribute heat to your component.
     * Perform heating-calculations and increase your heat (dmg) level accordingly.
     * This method will as well be called to REDUCE heat, by providing a negative amount.
     *
     * @param stack Reference to the specific instance of iterated ItemStack
     * @param reactor Reference to the Reactor
     * @param x X-coordinate of the stack in the grid
     * @param y Y-coordinate of the stack in the grid
     * @param heat Amount of heat to be added (may be negative to subtract heat)
     * @return 0 if the 'order' was accepted, return >0 to indicate the 'remaining' heat which couldn't be absorbed (and vice versa for <0)
     */
    int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat);

    /**
     * Called upon reactor explosion
     * Alter the explosion size.
     * Returning a float 0 < f < 1 will be counted as multiplier.
     * Anything else will be counted as a flat addition (in case of <0 = reduction).
     *
     * @param stack Reference to the specific instance of iterated ItemStack
     * @param reactor Reference to the Reactor
     * @return your explosion modifier
     */
    float influenceExplosion(ItemStack stack, IReactor reactor);

}
