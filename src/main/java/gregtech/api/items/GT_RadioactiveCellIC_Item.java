package gregtech.api.items;


import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;


public class GT_RadioactiveCellIC_Item extends GT_RadioactiveCell_Item implements IReactorComponent {
    public final int numberOfCells;
    public final float sEnergy;
    public final int sRadiation;
    public final float sHeat;
    public final ItemStack sDepleted;
    public final boolean sMox;

    public GT_RadioactiveCellIC_Item(String aUnlocalized, String aEnglish, int aCellcount, int maxDamage, float aEnergy, int aRadiation, float aHeat, ItemStack aDepleted, boolean aMox) {
        super(aUnlocalized, aEnglish, aCellcount);
        setMaxStackSize(64);
        this.maxDmg = maxDamage;
        this.numberOfCells = aCellcount;
        this.sEnergy = aEnergy;
        this.sRadiation = aRadiation;
        this.sHeat = aHeat;
        this.sDepleted = aDepleted;
        this.sMox = aMox;

    }

    private static int checkPulseable(IReactor reactor, int x, int y, ItemStack me, int mex, int mey, boolean heatrun) {
        ItemStack other = reactor.getItemAt(x, y);
        if ((other != null) && ((other.getItem() instanceof IReactorComponent)) &&
                (((IReactorComponent) other.getItem()).acceptUraniumPulse(reactor, other, me, x, y, mex, mey, heatrun))) {
            return 1;
        }
        return 0;
    }

    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
        if (!reactor.produceEnergy()) {
            return;
        }
        for (int iteration = 0; iteration < this.numberOfCells; iteration++) {
            int pulses = 1 + this.numberOfCells / 2;
            if (!heatrun) {
                for (int i = 0; i < pulses; i++) {
                    acceptUraniumPulse(reactor, yourStack, yourStack, x, y, x, y, heatrun);
                }
                checkPulseable(reactor, x - 1, y, yourStack, x, y, heatrun);checkPulseable(reactor, x + 1, y, yourStack, x, y, heatrun);checkPulseable(reactor, x, y - 1, yourStack, x, y, heatrun);checkPulseable(reactor, x, y + 1, yourStack, x, y, heatrun);
            } else {
                pulses += checkPulseable(reactor, x - 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x + 1, y, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y - 1, yourStack, x, y, heatrun) + checkPulseable(reactor, x, y + 1, yourStack, x, y, heatrun);

//                int heat = sumUp(pulses) * 4;

                int heat = triangularNumber(pulses) * 4;
                
                heat = getFinalHeat(reactor, yourStack, x, y, heat);
                
                ArrayList<ItemStackCoord> heatAcceptors = new ArrayList();
                checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
                checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
                checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
                checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
                heat = Math.round(heat * sHeat);
                while ((heatAcceptors.size() > 0) && (heat > 0)) {

                    int dheat = heat / heatAcceptors.size();
                    heat -= dheat;
                    dheat = ((IReactorComponent) ((ItemStackCoord) heatAcceptors.get(0)).stack.getItem()).alterHeat(reactor, ((ItemStackCoord) heatAcceptors.get(0)).stack, ((ItemStackCoord) heatAcceptors.get(0)).x, ((ItemStackCoord) heatAcceptors.get(0)).y, dheat);
                    heat += dheat;
                    heatAcceptors.remove(0);
                }
                if (heat > 0) {
                    reactor.addHeat(heat);
                }
            }
        }
        if (getDamageOfStack(yourStack) >= getMaxDamageEx() - 1) {
        	reactor.setItemAt(x, y, sDepleted.copy());
        } else if (heatrun) {
            damageItemStack(yourStack, 1);
        }
    }
    
    protected int getFinalHeat(IReactor reactor, ItemStack stack, int x, int y, int heat)
    {
      if (sMox&&reactor.isFluidCooled())
      {
        float breedereffectiveness = (float)reactor.getHeat() / (float)reactor.getMaxHeat();
        if (breedereffectiveness > 0.5D) {
          heat *= 2;
        }
      }
      return heat;
    }

    private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemStackCoord> heatAcceptors) {
        ItemStack thing = reactor.getItemAt(x, y);
        if ((thing != null) && ((thing.getItem() instanceof IReactorComponent)) &&
                (((IReactorComponent) thing.getItem()).canStoreHeat(reactor, thing, x, y))) {
            heatAcceptors.add(new ItemStackCoord(thing, x, y));
        }
    }

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
    	if (!heatrun) {
        	if(sMox){
        	      float breedereffectiveness = (float)reactor.getHeat() / (float)reactor.getMaxHeat();
        	      float ReaktorOutput = 1.5F * breedereffectiveness + 1.0F;
        	      reactor.addOutput(ReaktorOutput * this.sEnergy);
        	}else{
            reactor.addOutput((float) (1.0F * this.sEnergy));}
        }
        return true;
    }

    public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return false;
    }

    public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return 0;
    }

    public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return 0;
    }

    public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
        return heat;
    }

    public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
        return 2 * this.numberOfCells;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
        if (this.sRadiation > 0 && (entity instanceof EntityLivingBase)) {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;
            if (!ItemArmorHazmat.hasCompleteHazmat(entityLiving)) {
                IC2Potion.radiation.applyTo(entityLiving, sRadiation * 20, sRadiation * 10);
            }
        }
    }

    private class ItemStackCoord {
        public ItemStack stack;
        public int x;
        public int y;

        public ItemStackCoord(ItemStack stack1, int x1, int y1) {
            this.stack = stack1;
            this.x = x1;
            this.y = y1;
        }
    }
}
