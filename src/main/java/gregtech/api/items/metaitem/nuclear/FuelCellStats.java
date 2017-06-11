package gregtech.api.items.metaitem.nuclear;

import gregtech.api.interfaces.metaitem.IItemBehaviour;
import gregtech.api.interfaces.metaitem.IItemDurabilityManager;
import gregtech.api.interfaces.metaitem.INuclearStats;
import gregtech.api.util.GT_Utility;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class FuelCellStats implements INuclearStats, IItemDurabilityManager, IItemBehaviour {

    private final int maxDamage;
    private final int numberOfCells;
    private final ItemStack depletedStack;

    private final float breederMultiplier;

    public FuelCellStats(int maxDamage, int numberOfCells, ItemStack depletedStack, float breederMultiplier) {
        this.maxDamage = maxDamage;
        this.numberOfCells = numberOfCells;
        this.depletedStack = depletedStack;
        this.breederMultiplier = breederMultiplier;
    }

    private int getDamageOfStack(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.RodDamage", Constants.NBT.TAG_INT))
            return 0;
        return itemStack.getTagCompound().getInteger("GT.RodDamage");
    }

    private void setDamageForStack(ItemStack itemStack, int damage) {
        if(!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        tagCompound.setInteger("GT.RodDamage", damage);
    }

    @Override
    public boolean showsDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
        return true;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
        return heat;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
        if(reactor.produceEnergy()) {
            int basePulses = 1 + this.numberOfCells / 2;

            for(int iteration = 0; iteration < this.numberOfCells; ++iteration) {
                int pulses = basePulses +
                        checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) +
                        checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) +
                        checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) +
                        checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
                if(!heatRun) {
                    for(int pulse = 0; pulse < pulses; ++pulse) {
                        this.acceptUraniumPulse(stack, reactor, stack, x, y, x, y, heatRun);
                    }
                } else {
                    int heat = triangularNumber(pulses) * 4;
                    heat = this.getFinalHeat(reactor, heat);
                    Queue<ItemStackCoord> heatAcceptors = new ArrayDeque<>();
                    this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
                    this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
                    this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
                    this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);

                    while(!heatAcceptors.isEmpty() && heat > 0) {
                        int dheat = heat / heatAcceptors.size();
                        heat -= dheat;
                        ItemStackCoord acceptor = heatAcceptors.remove();
                        IReactorComponent acceptorComp = (IReactorComponent)acceptor.stack.getItem();
                        dheat = acceptorComp.alterHeat(acceptor.stack, reactor, acceptor.x, acceptor.y, dheat);
                        heat += dheat;
                    }

                    if(heat > 0) {
                        reactor.addHeat(heat);
                    }
                }
            }
            if(!heatRun) {
                int damageOfRod = getDamageOfStack(stack) + 1;
                setDamageForStack(stack, damageOfRod);
                if (damageOfRod > maxDamage) {
                    reactor.setItemAt(x, y, this.depletedStack.copy());
                }
            }
        }
    }

    private int getFinalHeat(IReactor reactor, int heat) {
        if(breederMultiplier != -1.0f && reactor.isFluidCooled()) {
            float breederEffectiveness = (float)reactor.getHeat() / (float)reactor.getMaxHeat();
            if(breederEffectiveness > 0.5f) {
                heat *= 2;
            }
        }
        return heat;
    }

    private static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
        ItemStack other = reactor.getItemAt(x, y);
        return other != null && other.getItem() instanceof IReactorComponent && ((IReactorComponent)other.getItem()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun)? 1 : 0;
    }

    private static int triangularNumber(int x) {
        return (x * x + x) / 2;
    }

    private void checkHeatAcceptor(IReactor reactor, int x, int y, Collection<ItemStackCoord> heatAcceptors) {
        ItemStack stack = reactor.getItemAt(x, y);
        if(stack != null && stack.getItem() instanceof IReactorComponent && ((IReactorComponent)stack.getItem()).canStoreHeat(stack, reactor, x, y)) {
            heatAcceptors.add(new ItemStackCoord(stack, x, y));
        }
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            if (this.breederMultiplier != -1.0f) {
                float breederEffectiveness = (float) reactor.getHeat() / (float) reactor.getMaxHeat();
                float reactorOutput = this.breederMultiplier * breederEffectiveness + 1.0F;
                reactor.addOutput(reactorOutput);
            } else {
                reactor.addOutput(1.0F);
            }
        }
        return true;
    }

    @Override
    public float influenceExplosion(ItemStack stack, IReactor reactor) {
        return (float)(2 * this.numberOfCells);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.translateToLocalFormatted("item.gt.fuel_cell.damage", getDamageOfStack(itemStack), maxDamage));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
        if(entity instanceof EntityLivingBase) {
            GT_Utility.applyRadioactivity((EntityLivingBase) entity, (int) (1 + (breederMultiplier != -1.0f ? breederMultiplier : 0)), numberOfCells);
        }
    }

    private static class ItemStackCoord {
        public final ItemStack stack;
        public final int x;
        public final int y;

        public ItemStackCoord(ItemStack stack, int x, int y) {
            this.stack = stack;
            this.x = x;
            this.y = y;
        }
    }
    
}
