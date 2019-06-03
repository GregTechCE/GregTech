package gregtech.common.items.behaviors;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class PortableChargerBehavior implements IItemBehaviour {

    private final int voltage;

    public PortableChargerBehavior(int voltage) {
        this.voltage = voltage;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack charger = player.getHeldItem(hand);

        if (world.isRemote) {
            return new ActionResult<>(EnumActionResult.SUCCESS, charger);
        }

        IElectricItem chargerElectricItem = charger.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);

        for (ItemStack itemstack : player.inventory.mainInventory) {
            IElectricItem electricItem = itemstack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            if(electricItem != null && itemstack != charger) {
                long inputVoltage = Math.min(GTValues.V[voltage], chargerElectricItem.getCharge());
                long energyUsed = electricItem.charge(inputVoltage, voltage, false, false);
                if(energyUsed > 0L) {
                    chargerElectricItem.discharge(energyUsed, voltage, false, true, false);
                    break;
                }
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, charger);
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.addAll(Arrays.asList(I18n.format("behaviour.portable_recharger").split("/n")));
    }
}
