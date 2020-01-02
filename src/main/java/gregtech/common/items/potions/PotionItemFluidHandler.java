package gregtech.common.items.potions;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nonnull;

class PotionItemFluidHandler extends FluidHandlerItemStackSimple {

    public PotionItemFluidHandler(@Nonnull ItemStack container) {
        super(container, PotionFluids.POTION_ITEM_FLUID_AMOUNT);
    }

    @Override
    public FluidStack getFluid() {
        PotionType potionType = PotionUtils.getPotionFromItem(container);
        if (potionType == PotionTypes.EMPTY)
            return null;
        Fluid fluid = PotionFluids.getFluidForPotion(potionType);
        //because some mods are dumb enough to register potion types after block registry event
        if (fluid == null)
            return null;
        return new FluidStack(fluid, capacity);
    }

    @Override
    protected void setFluid(FluidStack fluid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return false; //we allow only emptying of potion containers
    }

    @Override
    protected void setContainerToEmpty() {
        this.container = new ItemStack(Items.GLASS_BOTTLE);
    }
}
