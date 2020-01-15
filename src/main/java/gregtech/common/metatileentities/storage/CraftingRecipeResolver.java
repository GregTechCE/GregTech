package gregtech.common.metatileentities.storage;

import gregtech.api.util.DummyContainer;
import gregtech.api.util.ItemStackKey;
import gregtech.common.pipelike.inventory.network.ItemSource;
import gregtech.common.pipelike.inventory.network.ItemSourceList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class CraftingRecipeResolver {

    private final World world;
    private final ItemSourceList itemSourceList;
    private ItemStackHandler craftingGrid;
    private InventoryCrafting inventoryCrafting = new InventoryCrafting(new DummyContainer(), 3, 3);
    private IRecipe cachedRecipe = null;
    private ItemStackHandler craftingResultInventory = new ItemStackHandler(1);
    private long timer = 0L;
    private ItemStackKey[] cachedMatchedIngredients = new ItemStackKey[9];

    public CraftingRecipeResolver(World world, ItemStackHandler craftingGrid) {
        this.world = world;
        this.craftingGrid = craftingGrid;
        this.itemSourceList = new ItemSourceList(world);
    }

    public ItemSourceList getItemSourceList() {
        return itemSourceList;
    }

    private boolean updateInventoryCrafting() {
        boolean craftingGridChanged = false;
        for (int i = 0; i < craftingGrid.getSlots(); i++) {
            ItemStack craftingGridStack = craftingGrid.getStackInSlot(i);
            ItemStack inventoryCraftingStack = inventoryCrafting.getStackInSlot(i);
            if (!ItemStack.areItemsEqual(craftingGridStack, inventoryCraftingStack) ||
                !ItemStack.areItemStackTagsEqual(craftingGridStack, inventoryCraftingStack)) {
                inventoryCrafting.setInventorySlotContents(i, craftingGridStack.copy());
                craftingGridChanged = true;
            }
        }
        return craftingGridChanged;
    }

    private void updateCurrentRecipe() {
        IRecipe newRecipe = CraftingManager.findMatchingRecipe(inventoryCrafting, world);
        if (cachedRecipe != newRecipe) {
            this.cachedRecipe = newRecipe;
            ItemStack resultStack = newRecipe.getCraftingResult(inventoryCrafting);
            craftingResultInventory.setStackInSlot(0, resultStack.copy());
        }
    }

    private boolean performCrafting(boolean simulate) {

    }

    public void update() {
        //update item sources every second, it is enough
        //if they are being modified, they will update themselves anyway
        if (timer % 20 == 0L) {
            this.itemSourceList.update();
        }
        //update crafting inventory state
        if (updateInventoryCrafting()) {
            updateCurrentRecipe();
        }
        this.timer++;
    }
}
