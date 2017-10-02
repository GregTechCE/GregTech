package gregtech.api.capability.internal;

import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;

public interface IPaintable {

    @Nullable EnumDyeColor getColor();

    void setColor(@Nullable EnumDyeColor color);

}
