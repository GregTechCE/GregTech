package gregtech.api.items.toolitem;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.tool.IConfiguratorItem;
import gregtech.api.items.metaitem.stats.IItemCapabilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ConfiguratorItemStat implements IItemCapabilityProvider {

    @Override
    public ICapabilityProvider createProvider(ItemStack itemStack) {
        return new CapabilityProvider(itemStack);
    }

    private static class CapabilityProvider extends AbstractToolItemCapabilityProvider<IConfiguratorItem> implements IConfiguratorItem {

        public CapabilityProvider(final ItemStack itemStack) {
            super(itemStack);
        }

        @Override
        protected Capability<IConfiguratorItem> getCapability() {
            return GregtechCapabilities.CAPABILITY_CONFIGURATOR;
        }
    }
}
