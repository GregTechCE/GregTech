package gregtech.common.render;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IColorMultiplier {

    @SideOnly(Side.CLIENT)
    int getColorFromItemstack(ItemStack stack, int tintIndex);

}
