package gregtech.common.items.potions;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class GlassBottleFluidHandler implements IFluidHandlerItem, ICapabilityProvider {

    private ItemStack itemStack;
    private Collection<ICapabilityProvider> rawCapabilityProviders;
    private IFluidHandlerItem nextHandlerInChain;

    public GlassBottleFluidHandler(ItemStack itemStack, Collection<ICapabilityProvider> rawCapabilityProviders) {
        this.itemStack = itemStack;
        this.rawCapabilityProviders = rawCapabilityProviders;
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
        return itemStack;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        IFluidHandlerItem nextInChain = getNextInChain();
        IFluidTankProperties[] properties = nextInChain == null ? new IFluidTankProperties[0] : nextInChain.getTankProperties();
        IFluidTankProperties ownProperties = new FluidTankProperties(null, PotionFluids.POTION_ITEM_FLUID_AMOUNT, true, false);
        return ArrayUtils.add(properties, ownProperties);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource == null || resource.amount <= 0) {
            return 0;
        }
        int myOwnResult = fillImpl(resource, doFill);
        if(myOwnResult == 0) {
            IFluidHandlerItem nextInChain = getNextInChain();
            return nextInChain == null ? 0 : nextInChain.fill(resource, doFill);
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        IFluidHandlerItem nextInChain = getNextInChain();
        return nextInChain == null ? null : nextInChain.drain(resource, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        IFluidHandlerItem nextInChain = getNextInChain();
        return nextInChain == null ? null : nextInChain.drain(maxDrain, doDrain);
    }

    private int fillImpl(FluidStack resource, boolean doFill) {
        PotionType potionType = PotionFluids.getPotionForFluid(resource.getFluid());
        if (potionType != null && potionType != PotionTypes.EMPTY && resource.amount >= PotionFluids.POTION_ITEM_FLUID_AMOUNT) {
            if(doFill) {
                this.itemStack = new ItemStack(Items.POTIONITEM);
                PotionUtils.addPotionToItemStack(itemStack, potionType);
            }
            return PotionFluids.POTION_ITEM_FLUID_AMOUNT;
        }
        return 0;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return null;
    }

    private IFluidHandlerItem getNextInChain() {
        if(rawCapabilityProviders != null) {
            boolean foundMyself = false;
            for(ICapabilityProvider provider : rawCapabilityProviders) {
                IFluidHandlerItem fluidHandlerItem = provider.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if(fluidHandlerItem != null) {
                    if(fluidHandlerItem == this) {
                        foundMyself = true;
                    } else if(foundMyself) {
                        this.nextHandlerInChain = fluidHandlerItem;
                        break;
                    }
                }
            }
            this.rawCapabilityProviders = null;
        }
        return nextHandlerInChain;
    }
}
