package gregtech.api.util;

import gregtech.api.interfaces.internal.IGT_CraftingRecipe;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class GT_Shaped_Recipe extends ShapedOreRecipe implements IGT_CraftingRecipe {
    public final boolean mDismantleable, mRemovableByGT, mKeepingNBT;
    private final Enchantment[] mEnchantmentsAdded;
    private final int[] mEnchantmentLevelsAdded;

    public GT_Shaped_Recipe(ItemStack aResult, boolean aDismantleAble, boolean aRemovableByGT, boolean aKeepingNBT, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object... aRecipe) {
        super(aResult, aRecipe);
        mEnchantmentsAdded = aEnchantmentsAdded;
        mEnchantmentLevelsAdded = aEnchantmentLevelsAdded;
        mRemovableByGT = aRemovableByGT;
        mKeepingNBT = aKeepingNBT;
        mDismantleable = aDismantleAble;
    }

    @Override
    public boolean matches(InventoryCrafting aGrid, World aWorld) {
        if (mKeepingNBT) {
            ItemStack tStack = null;
            for (int i = 0; i < aGrid.getSizeInventory(); i++) {
                if (aGrid.getStackInSlot(i) != null) {
                    if (tStack != null) {
                        if ((tStack.hasTagCompound() != aGrid.getStackInSlot(i).hasTagCompound()) || (tStack.hasTagCompound() && !tStack.getTagCompound().equals(aGrid.getStackInSlot(i).getTagCompound())))
                            return false;
                    }
                    tStack = aGrid.getStackInSlot(i);
                }
            }
        }
        return super.matches(aGrid, aWorld);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting aGrid) {
        ItemStack rStack = super.getCraftingResult(aGrid);
        if (rStack != null) {
            // Update the Stack
            GT_Utility.updateItemStack(rStack);

            // Keeping NBT
            if (mKeepingNBT) for (int i = 0; i < aGrid.getSizeInventory(); i++) {
                if (aGrid.getStackInSlot(i) != null && aGrid.getStackInSlot(i).hasTagCompound()) {
                    rStack.setTagCompound((NBTTagCompound) aGrid.getStackInSlot(i).getTagCompound().copy());
                    break;
                }
            }

            // Charge Values
            if (GT_ModHandler.isElectricItem(rStack)) {
                GT_ModHandler.dischargeElectricItem(rStack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
                int tCharge = 0;
                for (int i = 0; i < aGrid.getSizeInventory(); i++)
                    tCharge += GT_ModHandler.dischargeElectricItem(aGrid.getStackInSlot(i), Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, true);
                if (tCharge > 0) GT_ModHandler.chargeElectricItem(rStack, tCharge, Integer.MAX_VALUE, true, false);
            }

            // Saving Ingredients inside the Item.
            if (mDismantleable) {
                NBTTagCompound rNBT = rStack.getTagCompound(), tNBT = new NBTTagCompound();
                if (rNBT == null) rNBT = new NBTTagCompound();
                for (int i = 0; i < 9; i++) {
                    ItemStack tStack = aGrid.getStackInSlot(i);
                    if (tStack != null && GT_Utility.getContainerItem(tStack, true) == null && !(tStack.getItem() instanceof GT_MetaGenerated_Tool)) {
                        tStack = GT_Utility.copyAmount(1, tStack);
                        if(GT_Utility.isStackValid(tStack)){
                        GT_ModHandler.dischargeElectricItem(tStack, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false, true);
                        tNBT.setTag("Ingredient." + i, tStack.writeToNBT(new NBTTagCompound()));}
                    }
                }
                rNBT.setTag("GT.CraftingComponents", tNBT);
                rStack.setTagCompound(rNBT);
            }

            // Add Enchantments
            for (int i = 0; i < mEnchantmentsAdded.length; i++)
                GT_Utility.ItemNBT.addEnchantment(rStack, mEnchantmentsAdded[i], EnchantmentHelper.getEnchantmentLevel(mEnchantmentsAdded[i].effectId, rStack) + mEnchantmentLevelsAdded[i]);

            // Update the Stack again
            GT_Utility.updateItemStack(rStack);
        }
        return rStack;
    }

    @Override
    public boolean isRemovable() {
        return mRemovableByGT;
    }
}