package gregtech.api.capability;

import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IPaintable {

    @CapabilityInject(IPaintable.class)
    public static final Capability<IPaintable> CAPABILITY_PAINTABLE = null;

    EnumDyeColor getColor();

    void setColor(EnumDyeColor color);

}
