package gregtech.api.pipelike;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IPipeLikeTileProperty extends INBTSerializable<NBTTagCompound> {
    void addInformation(List<String> tooltip);
}
