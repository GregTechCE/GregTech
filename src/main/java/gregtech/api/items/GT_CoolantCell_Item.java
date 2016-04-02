package gregtech.api.items;

import gregtech.api.GregTech_API;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class GT_CoolantCell_Item
        extends GT_Generic_Item {
    protected int heatStorage;

    public GT_CoolantCell_Item(String aUnlocalized, String aEnglish, int aMaxStore) {
        super(aUnlocalized, aEnglish, null);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        setNoRepair();
        this.heatStorage = aMaxStore;
        this.setCreativeTab(GregTech_API.TAB_GREGTECH);
    }

    protected static int getHeatOfStack(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
            aStack.setTagCompound(tNBT);
        }
        return tNBT.getInteger("heat");
    }

    protected void setHeatForStack(ItemStack aStack, int aHeat) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
            aStack.setTagCompound(tNBT);
        }
        tNBT.setInteger("heat", aHeat);
        if (this.heatStorage > 0) {
            double var4 = (double) aHeat / (double) this.heatStorage;
            int var6 = (int) (aStack.getMaxDamage() * var4);
            if (var6 >= aStack.getMaxDamage()) {
                var6 = aStack.getMaxDamage() - 1;
            }
            aStack.setItemDamage(var6);
        }
    }

    public void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        aList.add("Stored Heat: " + getHeatOfStack(aStack));
        switch (getControlTagOfStack(aStack)) {
            case 1:
                aList.add(StatCollector.translateToLocal("ic2.reactoritem.heatwarning.line1"));
                aList.add(StatCollector.translateToLocal("ic2.reactoritem.heatwarning.line2"));
        }
    }

    public int getControlTagOfStack(ItemStack stack) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        return nbtData.getInteger("tag");
    }

    public void setControlTagOfStack(ItemStack stack, int tag) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        nbtData.setInteger("tag", tag);
    }
}
