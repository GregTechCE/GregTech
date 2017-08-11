package gregtech.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nonnull;

public interface IDescribable {

    @CapabilityInject(IDescribable.class)
    public static final Capability<IDescribable> CAPABILITY_DESCRIBABLE = null;

    /**
     * Up to 8 Strings can be returned.
     * @return an array of information strings.
     */
    @Nonnull String[] getInfoData();

}