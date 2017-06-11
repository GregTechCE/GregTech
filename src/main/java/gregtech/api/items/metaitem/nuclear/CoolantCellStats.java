package gregtech.api.items.metaitem.nuclear;

import gregtech.api.interfaces.metaitem.IItemBehaviour;
import gregtech.api.interfaces.metaitem.IItemDurabilityManager;
import gregtech.api.interfaces.metaitem.INuclearStats;
import ic2.api.reactor.IReactor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class CoolantCellStats implements INuclearStats, IItemDurabilityManager, IItemBehaviour {

    private final int heatStorage;

    public CoolantCellStats(int heatStorage) {
        this.heatStorage = heatStorage;
    }

    private int getHeatOfStack(ItemStack itemStack) {
        if(!itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey("GT.Heat", Constants.NBT.TAG_INT))
            return 0;
        return itemStack.getTagCompound().getInteger("GT.Heat");
    }

    private void setHeatForStack(ItemStack itemStack, int heat) {
        if(!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        tagCompound.setInteger("GT.Heat", heat);
    }

    @Override
    public boolean showsDurabilityBar(ItemStack itemStack) {
        return getHeatOfStack(itemStack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack itemStack) {
        return getHeatOfStack(itemStack) / (heatStorage * 1.0f);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack itemStack, List<String> lines) {
        int heatStored = getHeatOfStack(itemStack);
        lines.add(I18n.translateToLocalFormatted("item.gt.coolant_cell.heat", heatStored));
        if(heatStored > heatStorage / 10 * 9) {
            lines.add(I18n.translateToLocal("item.gt.coolant_cell.heat_warning"));
        }
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity player, int timer, boolean isInHand) {}

    @Override
    public void processChamber(ItemStack itemStack, IReactor reactor, int x, int y, boolean heatrun) {
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        return false;
    }

    @Override
    public boolean canStoreHeat(ItemStack itemStack, IReactor reactor, int x, int y) {
        return true;
    }

    @Override
    public int getMaxHeat(ItemStack itemStack, IReactor iReactor, int x, int y) {
        return heatStorage;
    }

    @Override
    public int getCurrentHeat(ItemStack itemStack, IReactor reactor, int x, int y) {
        return getHeatOfStack(itemStack);
    }

    @Override
    public int alterHeat(ItemStack itemStack, IReactor reactor, int x, int y, int heat) {
        int storedHeat = getHeatOfStack(itemStack);
        storedHeat += heat;
        if (storedHeat > heatStorage) {
            reactor.setItemAt(x, y, null);
            return storedHeat - heatStorage;
        } else {
            if (storedHeat < 0) {
                setHeatForStack(itemStack, 0);
                return storedHeat;
            }
            setHeatForStack(itemStack, storedHeat);
            return 0;
        }
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor reactor) {
        int canConsumeHeat = heatStorage - getHeatOfStack(itemStack);
        return -(canConsumeHeat / 1000f);
    }

    @Override
    public boolean canBePlacedIn(ItemStack itemStack, IReactor reactor) {
        return true;
    }

}
