package gregtech.common.items.behaviors;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.loaders.oreprocessing.ToolRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToolPartsBoxBehavior implements IItemBehaviour {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        List<ItemStack> components = ToolMetaItem.getToolComponents(itemStack);
        List<ItemStack> actualComponents = new ArrayList<>();
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        Random random = new Random(tagCompound == null ? 0L : tagCompound.getLong("RandomKey"));
        for(ItemStack componentStack : components) {
            int restoreChance = getItemRestoreChance(componentStack);
            if(random.nextInt(100) >= 100 - restoreChance) {
                actualComponents.add(componentStack);
            }
        }
        int emptyPlayerSlots = 0;
        NonNullList<ItemStack> allStacks = player.inventory.mainInventory;
        for(ItemStack inventoryStack : allStacks) {
            if(inventoryStack.isEmpty()) emptyPlayerSlots++;
        }
        if(actualComponents.size() > emptyPlayerSlots) {
            return ActionResult.newResult(EnumActionResult.PASS, itemStack);
        }
        for(ItemStack componentStack : actualComponents) {
            player.inventory.addItemStackToInventory(componentStack);
        }
        itemStack.shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    private static int getItemRestoreChance(ItemStack itemStack) {
        if(itemStack.hasCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null))
            return 100; //electric items are always restored
        if(itemStack.getItem() instanceof MetaItem<?>) {
            MetaItem<?> metaItem = (MetaItem<?>) itemStack.getItem();
            MetaValueItem metaValueItem = metaItem.getItem(itemStack);
            if(ArrayUtils.contains(ToolRecipeHandler.motorItems, metaValueItem))
                return 100; //always restore electrical components like motors
        }
        OrePrefix orePrefix = OreDictUnifier.getPrefix(itemStack);
        if(orePrefix != null) {
            String orePrefixName = orePrefix.name();
            if(orePrefix == OrePrefix.circuit)
                return 100; //always restore circuits of any tier
            else if(orePrefixName.startsWith("wireGt") || orePrefixName.startsWith("cableGt"))
                return 92; //almost always restore cables and wires
            else if(orePrefixName.startsWith("toolHead"))
                return 9; //restore tool heads only with 9% chance, because they will break almost always
        }
        return 37; //otherwise, restore with 37% chance



    }

}
