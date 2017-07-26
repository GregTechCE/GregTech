package gregtech.api.recipes;

import gregtech.api.interfaces.internal.IRemovableRecipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class GTShapedRecipe extends ShapedOreRecipe implements IRemovableRecipe {
    public final boolean dismantleable, removableByGT, keepingNBT;
    private final Enchantment[] enchantmentsAdded;
    private final int[] mEnchantmentLevelsAdded;

    public GTShapedRecipe(ItemStack result, boolean dismantleAble, boolean removableByGT, boolean keepingNBT, Enchantment[] enchantmentsAdded, int[] enchantmentLevelsAdded, Object... recipe) {
        super(result, recipe);
        this.enchantmentsAdded = enchantmentsAdded;
        this.mEnchantmentLevelsAdded = enchantmentLevelsAdded;
        this.removableByGT = removableByGT;
        this.keepingNBT = keepingNBT;
        this.dismantleable = dismantleAble;
    }

    @Override
    public boolean matches(InventoryCrafting grid, World world) {
        if (keepingNBT) {
            ItemStack stack = null;
            for (int i = 0; i < grid.getSizeInventory(); i++) {
                if (grid.getStackInSlot(i) != null) {
                    if (stack != null) {
                        if ((stack.hasTagCompound() != grid.getStackInSlot(i).hasTagCompound()) || (stack.hasTagCompound() && !stack.getTagCompound().equals(grid.getStackInSlot(i).getTagCompound())))
                            return false;
                    }
                    stack = grid.getStackInSlot(i);
                }
            }
        }
        return super.matches(grid, world);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting grid) {
        ItemStack stack = super.getCraftingResult(grid);
        if (stack != null) {

            // Keeping NBT
            if (keepingNBT) for (int i = 0; i < grid.getSizeInventory(); i++) {
                if (grid.getStackInSlot(i) != null && grid.getStackInSlot(i).hasTagCompound()) {
                    stack.setTagCompound(grid.getStackInSlot(i).getTagCompound().copy());
                    break;
                }
            }

            // Charge Values
            if (ModHandler.isElectricItem(stack)) {
                ModHandler.dischargeElectricItem(stack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true);

                int charge = 0;
                for (int i = 0; i < grid.getSizeInventory(); i++)
                    charge += ModHandler.dischargeElectricItem(grid.getStackInSlot(i), Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, true);
                if (charge > 0) ModHandler.chargeElectricItem(stack, charge, Integer.MAX_VALUE, true, false);
            }

            // Saving Ingredients inside the Item.
            if (dismantleable) {
                NBTTagCompound tagCompound = stack.getTagCompound(), tag = new NBTTagCompound();
                if (tagCompound == null) tagCompound = new NBTTagCompound();
                for (int i = 0; i < 9; i++) {
                    ItemStack itemStack = grid.getStackInSlot(i);
                    if (itemStack != null && GT_Utility.getContainerItem(itemStack, true) == null && !(itemStack.getItem() instanceof GT_MetaGenerated_Tool)) {
                        itemStack = GT_Utility.copyAmount(1, itemStack);
                        if(GT_Utility.isStackValid(itemStack)){
                        ModHandler.dischargeElectricItem(itemStack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
                        tag.setTag("Ingredient." + i, itemStack.writeToNBT(new NBTTagCompound()));}
                    }
                }
                tagCompound.setTag("GT.CraftingComponents", tag);
                stack.setTagCompound(tagCompound);
            }

            // Add Enchantments
            for (int i = 0; i < enchantmentsAdded.length; i++)
                GT_Utility.ItemNBT.addEnchantment(stack, enchantmentsAdded[i], EnchantmentHelper.getEnchantmentLevel(enchantmentsAdded[i], stack) + mEnchantmentLevelsAdded[i]);

        }
        return stack;
    }

    @Override
    public boolean isRemovable() {
        return removableByGT;
    }
}