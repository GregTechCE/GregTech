package gregtech.api.capability;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IElectricItem {

    @CapabilityInject(IElectricItem.class)
    Capability<IElectricItem> CAPABILITY_ELECTRIC_ITEM = null;

    /**
     * Charge an item with a specified amount of energy.
     *
     * @param amount max amount of energy to charge in EU
     * @param chargerTier tier of the charging device, has to be at least as high as the item to charge
     * @param ignoreTransferLimit ignore any transfer limits, infinite charge rate
     * @param simulate don't actually change the item, just determine the return value
     * @return Energy transferred into the electric item
     */
    long charge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean simulate);

    /**
     * Discharge an item by a specified amount of energy
     *
     * The externally parameter is used to prevent non-battery-like items from providing power. For
     * example discharge slots set externally to true, but items using energy for themselves don't.
     * Special cases like the nano saber hitting armor will discharge with externally = false.
     *
     * @param amount max amount of energy to discharge in EU
     * @param dischargerTier tier of the discharging device, has to be at least as high as the item to discharge
     * @param ignoreTransferLimit ignore any transfer limits, infinite discharge rate
     * @param externally use the supplied item externally, i.e. to power something else as if it was a battery
     * @param simulate don't actually discharge the item, just determine the return value
     * @return Energy retrieved from the electric item
     */
    long discharge(long amount, int dischargerTier, boolean ignoreTransferLimit, boolean externally, boolean simulate);

    /**
     * Determine the charge level for the specified item.
     *
     * The item may not actually be chargeable to the returned level, e.g. if it is a
     * non-rechargeable single use battery.
     *
     * @return maximum charge level in EU
     */
    long getMaxCharge();

    /**
     * Determine if the specified electric item has at least a specific amount of EU.
     * BatPacks are not taken into account.
     *
     * @param amount minimum amount of energy required
     * @return true if there's enough energy
     */
    boolean canUse(long amount);

    /**
     * Try to retrieve a specific amount of energy from an Item, and if applicable, a BatPack.
     *
     * @param amount amount of energy to discharge in EU
     * @param entity entity holding the item
     * @return true if the operation succeeded
     */
    boolean use(long amount, EntityLivingBase entity);

    /**
     * Charge an item from the BatPack a player is wearing.
     * use() already contains this functionality.
     *
     * @param entity entity holding the item
     */
    void chargeFromArmor(EntityLivingBase entity);

    /**
     * Get the tier of the specified item.
     * @return The tier of the item.
     */
    int getTier();
}
