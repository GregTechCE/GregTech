package gregtech.api.util;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapedOreIngredientAwareRecipe extends ShapedOreEnergyTransferRecipe {

    public ShapedOreIngredientAwareRecipe(ResourceLocation group, @Nonnull ItemStack result, Object... recipe) {
        super(group, result, recipe);
    }

    public ShapedOreIngredientAwareRecipe(ResourceLocation group, @Nonnull ItemStack result, ShapedPrimer primer) {
        super(group, result, primer);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inventoryCrafting) {
        ItemStack resultStack = super.getCraftingResult(inventoryCrafting);
        saveCraftingComponents(resultStack, inventoryCrafting);
        return resultStack;
    }

    public static void saveCraftingComponents(ItemStack toolStack, IInventory ingredients) {
        NBTTagList componentList = new NBTTagList();
        for(int slotIndex = 0; slotIndex < ingredients.getSizeInventory(); slotIndex++) {
            ItemStack stackInSlot = ingredients.getStackInSlot(slotIndex).copy();
            stackInSlot.setCount(1);
            //only save items that are not tools and don't have container items (to avoid dupes)
            if(!stackInSlot.isEmpty() && !(stackInSlot.getItem() instanceof ToolMetaItem<?>) &&
                stackInSlot.getItem().getContainerItem(stackInSlot).isEmpty()) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackInSlot.writeToNBT(stackTag);
                componentList.appendTag(stackTag);
            }
        }
        toolStack.setTagInfo("CraftingComponents", componentList);
    }

    public static List<ItemStack> getCraftingComponents(ItemStack toolStack) {
        NBTTagCompound tagCompound = toolStack.getTagCompound();
        if(tagCompound == null || !tagCompound.hasKey("CraftingComponents", NBT.TAG_LIST))
            return Collections.emptyList();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        NBTTagList componentList = tagCompound.getTagList("CraftingComponents", NBT.TAG_COMPOUND);
        for(int index = 0; index < componentList.tagCount(); index++) {
            NBTTagCompound stackTag = componentList.getCompoundTagAt(index);
            stacks.add(new ItemStack(stackTag));
        }
        return stacks;
    }
}
