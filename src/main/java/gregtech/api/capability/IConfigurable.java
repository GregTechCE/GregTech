package gregtech.api.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 * For configurable things
 */
public interface IConfigurable {

    ResourceLocation getConfigurationID();

    NBTTagCompound copyConfiguration(EntityPlayer player);

    void pasteConfiguration(EntityPlayer player, NBTTagCompound configuration);
}