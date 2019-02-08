package gregtech.api.util;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class ShapedOreEnergyTransferRecipe extends ShapedOreRecipe {

    public ShapedOreEnergyTransferRecipe(ResourceLocation group, @Nonnull ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }

    public ShapedOreEnergyTransferRecipe(ResourceLocation group, @Nonnull ItemStack result, ShapedPrimer primer) {
        super(group, result, primer);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventoryCrafting) {
        ItemStack resultStack = super.getCraftingResult(inventoryCrafting);
        chargeStackFromComponents(resultStack, inventoryCrafting);
        return resultStack;
    }

    public static void chargeStackFromComponents(ItemStack toolStack, IInventory ingredients) {
        IElectricItem electricItem = toolStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem != null && electricItem.getMaxCharge() > 0L) {
            long maxCharge = electricItem.charge(Long.MAX_VALUE, Integer.MAX_VALUE, true, true);
            for (int slotIndex = 0; slotIndex < ingredients.getSizeInventory(); slotIndex++) {
                ItemStack stackInSlot = ingredients.getStackInSlot(slotIndex);
                IElectricItem batteryItem = stackInSlot.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
                if (batteryItem != null && batteryItem.canProvideChargeExternally() && maxCharge > 0L) {
                    long discharged = batteryItem.discharge(maxCharge, Integer.MAX_VALUE, true, true, false);
                    maxCharge -= electricItem.charge(discharged, Integer.MAX_VALUE, true, false);
                    if (discharged > 0L) ingredients.setInventorySlotContents(slotIndex, stackInSlot);
                    if (maxCharge == 0L) break;
                }
            }
        }
    }
}
