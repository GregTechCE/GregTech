package gregtech.api.capability.internal;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;

public interface IDescribable {

    /**
     * Up to 8 Strings can be returned.
     * @return an array of information strings.
     */
    @Nonnull String[] getInfoData();

}