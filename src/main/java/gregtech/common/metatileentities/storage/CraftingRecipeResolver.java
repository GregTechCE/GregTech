package gregtech.common.metatileentities.storage;

import com.google.common.collect.Lists;
import gregtech.api.util.DummyContainer;
import gregtech.common.inventory.itemsource.ItemSourceList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CraftingRecipeResolver {

    private final World world;
    private final ItemSourceList itemSourceList;
    private ItemStackHandler craftingGrid;
    private InventoryCrafting inventoryCrafting = new InventoryCrafting(new DummyContainer(), 3, 3);
    private IRecipe cachedRecipe = null;
    private ItemStackHandler craftingResultInventory = new ItemStackHandler(1);
    private long timer = 0L;
    private CachedRecipeData cachedRecipeData = null;
    private int itemsCrafted = 0;

    public CraftingRecipeResolver(World world, ItemStackHandler craftingGrid) {
        this.world = world;
        this.craftingGrid = craftingGrid;
        this.itemSourceList = new ItemSourceList(world);
        this.itemSourceList.setItemListChangeCallback(this::notifyStoredItemsChanged);
    }

    public ItemSourceList getItemSourceList() {
        return itemSourceList;
    }

    public ItemStackHandler getCraftingResultInventory() {
        return craftingResultInventory;
    }

    public int getItemsCrafted() {
        return itemsCrafted;
    }

    public void setItemsCrafted(int itemsCrafted) {
        this.itemsCrafted = itemsCrafted;
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

    public void performRecipe(EntityPlayer player) {
        if (cachedRecipeData != null) {
            cachedRecipeData.performRecipe(player);
            //update items in the crafting grid to the actual equivalents used in crafting
            InvWrapper invWrapper = new InvWrapper(this.cachedRecipeData.inventory);
            copyInventoryItems(invWrapper, craftingGrid);
            //also update items in inventory crafting to avoid useless recipe re-caching
            copyInventoryItems(invWrapper, new InvWrapper(inventoryCrafting));
        }
    }

    public void handlePostItemCraft(ItemStack itemStack, EntityPlayer player) {
        itemStack.onCrafting(world, player, 1);
        FMLCommonHandler.instance().firePlayerCraftingEvent(player, itemStack, inventoryCrafting);
        if (cachedRecipe != null && !cachedRecipe.isDynamic()) {
            player.unlockRecipes(Lists.newArrayList(cachedRecipe));
        }
        this.itemsCrafted++;
    }

    public boolean checkRecipeValid() {
        return cachedRecipeData != null && cachedRecipeData.checkRecipeValid();
    }

    private void notifyStoredItemsChanged() {
        if (cachedRecipeData != null) {
            cachedRecipeData.attemptMatchRecipe();
        }
    }

    private void updateCurrentRecipe() {
        IRecipe newRecipe = CraftingManager.findMatchingRecipe(inventoryCrafting, world);
        if (cachedRecipe != newRecipe) {
            this.cachedRecipe = newRecipe;
            if (newRecipe != null) {
                ItemStack resultStack = newRecipe.getCraftingResult(inventoryCrafting);
                this.craftingResultInventory.setStackInSlot(0, resultStack.copy());
                this.cachedRecipeData = new CachedRecipeData(itemSourceList, newRecipe, resultStack.copy());
                copyInventoryItems(craftingGrid, new InvWrapper(this.cachedRecipeData.inventory));
                this.cachedRecipeData.attemptMatchRecipe();
            } else {
                this.craftingResultInventory.setStackInSlot(0, ItemStack.EMPTY);
                this.cachedRecipeData = null;
            }
        }
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

    private static void copyInventoryItems(IItemHandler src, IItemHandlerModifiable dest) {
        for (int i = 0; i < src.getSlots(); i++) {
            ItemStack itemStack = src.getStackInSlot(i);
            dest.setStackInSlot(i, itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.copy());
        }
    }
}
