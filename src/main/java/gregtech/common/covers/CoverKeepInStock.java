package gregtech.common.covers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IItemInfo;
import gregtech.api.capability.IStorageNetwork;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.CoverWithUI;
import gregtech.api.cover.ICoverable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.SimpleTextWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.CountableIngredient;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ItemStackKey;
import gregtech.common.covers.filter.ItemFilterWrapper;
import gregtech.common.covers.filter.SimpleItemFilter;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.electric.multiblockpart.MetaTileEntityMultiblockPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

/*
 * This is a cover for something that has the IStorageNetwork capability i.e. an inventory pipe
 * 
 *  It allows the specification of items to keep in stock which will be crafted using recipes from the adjacent machine.
 */
public class CoverKeepInStock extends CoverBehavior implements CoverWithUI, ITickable, IControllable {

    public final int tier;
    public final int maxItemTransferRate;
    protected int transferRate;
    protected int itemsLeftToTransferLastSecond;
    protected boolean isWorkingAllowed = true;
    protected final ItemFilterWrapper itemFilter;

    public CoverKeepInStock(ICoverable coverable, EnumFacing attachedSide, int tier, int itemsPerSecond) {
        super(coverable, attachedSide);
        this.tier = tier;
        this.maxItemTransferRate = itemsPerSecond;
        this.transferRate = maxItemTransferRate;
        this.itemsLeftToTransferLastSecond = transferRate;
        // Hack: reusing item filter gui for keep in stock config
        this.itemFilter = new ItemFilterWrapper(this);
        this.itemFilter.setItemFilter(new SimpleItemFilter());
        this.itemFilter.setMaxStackSize(tier*8);
    }

    protected void setTransferRate(int transferRate) {
        this.transferRate = transferRate;
        coverHolder.markDirty();
    }

    protected void adjustTransferRate(int amount) {
        setTransferRate(MathHelper.clamp(transferRate + amount, 1, maxItemTransferRate));
    }

    @Override
    public void update() {
        long timer = coverHolder.getTimer();
        try
        {
            if (timer % 5 == 0 && isWorkingAllowed && itemsLeftToTransferLastSecond > 0) {
                TileEntity tileEntity = coverHolder.getWorld().getTileEntity(coverHolder.getPos().offset(attachedSide));
                if (tileEntity == null)
                    return;
                IItemHandler itemHandler = tileEntity == null ? null : tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, attachedSide.getOpposite());
                if (itemHandler == null)
                    return;
                TileEntity workableTileEntity = tileEntity;
                // For a multiblock, lets try to use its controller
                if (tileEntity instanceof MetaTileEntityHolder) {
                    MetaTileEntity metaTileEntity = ((MetaTileEntityHolder) tileEntity).getMetaTileEntity();
                    if (metaTileEntity != null && metaTileEntity instanceof MetaTileEntityMultiblockPart)
                    {
                        MultiblockControllerBase controller = ((MetaTileEntityMultiblockPart) metaTileEntity).getController();
                        if (controller != null && controller instanceof RecipeMapMultiblockController && controller.isStructureFormed())
                            workableTileEntity = (TileEntity) controller.getHolder();
                    }
                }
                IWorkable workable = workableTileEntity == null ? null : workableTileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_WORKABLE, attachedSide.getOpposite());
                if (workable == null)
                    return;
                if (workable instanceof AbstractRecipeLogic  == false)
                    return;
                AbstractRecipeLogic recipeLogic = (AbstractRecipeLogic) workable;
                IStorageNetwork myStorageNetwork = coverHolder.getCapability(GregtechCapabilities.CAPABILITY_STORAGE_NETWORK, attachedSide);
                if (myStorageNetwork == null)
                    return;

                // First cleanup the outputs
                this.itemsLeftToTransferLastSecond -= cleanUpOutputs(itemHandler, myStorageNetwork, itemsLeftToTransferLastSecond);
                // Have we finished for this cycle?
                if (this.itemsLeftToTransferLastSecond <= 0)
                    return;

                // TODO: Need to keep track of ongoing requests when doing keep in stock
                // For now, don't do keep in stock when the machine is busy
                if (workable.isActive())
                    return;
                
                // Or there is something in the inventory
                // Not including known nonconsumed items
                for (int slot=0; slot < itemHandler.getSlots(); ++slot) {
                    ItemStack stack = itemHandler.getStackInSlot(slot);
                    if (stack.isEmpty() || isIgnoredStack(stack))
                        continue;
                    return;
                }

                // Now go through the keep in stock
                ItemStackHandler slots = ((SimpleItemFilter) itemFilter.getItemFilter()).getItemFilterSlots();
                for (int i = 0; i < slots.getSlots(); ++i) {
                    ItemStack requested = slots.getStackInSlot(i);
                    if (requested == null || requested.isEmpty())
                        continue;
                    // Do we have enough in stock?
                    int inStock = 0;
                    IItemInfo itemInfo = myStorageNetwork.getItemInfo(new ItemStackKey(requested));
                    if (itemInfo != null)
                        inStock = itemInfo.getTotalItemAmount();
                    if (inStock < requested.getCount()) {
                        // Not enough in stock, find a recipe
                        RecipeMap<?> recipeMap = recipeLogic.recipeMap;
                        Collection<Recipe> recipeList = recipeMap.getRecipeList(); 
                        // Furnaces are special
                        if (recipeMap == RecipeMaps.FURNACE_RECIPES)
                            recipeList = getFurnaceRecipes();
                        for (Recipe recipe : recipeList) {
                            if (tryRecipe(requested, recipe, myStorageNetwork, itemHandler, itemsLeftToTransferLastSecond)) {
                                // Only do one keep in stock per cycle
                                return;
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (timer % 20 == 0) {
                this.itemsLeftToTransferLastSecond = transferRate;
            }
        }
    }

    protected boolean tryRecipe(ItemStack requested, Recipe recipe, IStorageNetwork sourceInventory, IItemHandler targetInventory, int maxTransferAmount) {
        NonNullList<ItemStack> outputs = recipe.getOutputs();
        for (ItemStack output : outputs) {
            if (equalsItemAndMetaData(requested, output)) {
                List<CountableIngredient> ingredients = recipe.getInputs();

                // First simulate the movement to make sure we can do it
                int transfered = tryIngredients(ingredients, sourceInventory, targetInventory, maxTransferAmount, true);
                // Seems to work so do it for real
                if (transfered > 0) {
                    // TODO don't recalculate again, use the ingredients we found when simulating
                    itemsLeftToTransferLastSecond -= tryIngredients(ingredients, sourceInventory, targetInventory, maxTransferAmount, false);
                    return true;
                }
            }
        }
        return false;
    }

    protected int tryIngredients(List<CountableIngredient> ingredients, IStorageNetwork sourceInventory, IItemHandler targetInventory, int maxTransferAmount, boolean simulate) {
        int itemsLeftToTransfer = maxTransferAmount;

        for (CountableIngredient ingredient : ingredients) {
            int amount = ingredient.getCount();
            // Ingredient is not consumed in recipe
            if (amount == 0)
                continue;
            // Can't do it this cycle
            if (amount > itemsLeftToTransfer)
                return 0;
            int transfered = tryMatchingStacks(ingredient.getIngredient().getMatchingStacks(), amount, sourceInventory, targetInventory, simulate);
            // Didn't work
            if (transfered <= 0)
                return 0;

            itemsLeftToTransfer -= transfered;
        }
        // All the ingredients worked, if there were any
        return maxTransferAmount - itemsLeftToTransfer;
    }

    protected int tryMatchingStacks(ItemStack[] matchingStacks, int amount, IStorageNetwork sourceInventory, IItemHandler targetInventory, boolean simulate) {
        for (ItemStack itemStack : matchingStacks) {
            ItemStackKey key = new ItemStackKey(itemStack);
            int extracted = sourceInventory.extractItem(key, amount, simulate);
            // Not enough of this ingredient
            if (extracted != amount)
                continue;
            ItemStack sourceStack = key.getItemStack();
            sourceStack.setCount(extracted);
            ItemStack remainder = ItemHandlerHelper.insertItemStacked(targetInventory, sourceStack, simulate);
            // Not accepting this ingredient
            if (remainder.getCount() != 0)
                continue;

            // This ingredient has enough and will go in the machine
            return extracted;
        }
        // No matching stack worked for this ingredient
        return 0;
    }

    protected int cleanUpOutputs(IItemHandler sourceInventory, IStorageNetwork targetInventory, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, itemsLeftToTransfer, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            ItemStackKey sourceStackKey = new ItemStackKey(sourceStack);
            int amountToInsert = targetInventory.insertItem(sourceStackKey, sourceStack.getCount(), true);

            if (amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                if (!sourceStack.isEmpty()) {
                    targetInventory.insertItem(sourceStackKey, sourceStack.getCount(), false);
                    itemsLeftToTransfer -= sourceStack.getCount();
                    if (itemsLeftToTransfer == 0) {
                        break;
                    }
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    static List<ItemStack> ignoredStacks = new ArrayList<>();
    
    {
        ignoredStacks.add(MetaItems.INTEGRATED_CIRCUIT.getStackForm());
        for (MetaValueItem mold : MetaItems.SHAPE_MOLDS) {
            ignoredStacks.add(mold.getStackForm());
        }
        for (MetaValueItem shape : MetaItems.SHAPE_EXTRUDERS) {
            ignoredStacks.add(shape.getStackForm());
        }
    }
    
    static boolean isIgnoredStack(ItemStack stack) {
        for (ItemStack ignored : ignoredStacks) {
            if (equalsItemAndMetaData(stack, ignored))
                return true;
        }
        return false;
    }
    
    static List<Recipe> furnaceRecipes = new ArrayList<Recipe>();
            
    static List<Recipe> getFurnaceRecipes() {
        if (!furnaceRecipes.isEmpty())
            return furnaceRecipes;
        for (Entry<ItemStack, ItemStack> furnaceRecipe : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            if (furnaceRecipe.getKey() != null && furnaceRecipe.getValue() != null) {
                Recipe recipe = RecipeMaps.FURNACE_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, furnaceRecipe.getKey()))
                        .outputs(furnaceRecipe.getValue())
                        .duration(128).EUt(4)
                        .build().getResult();
                furnaceRecipes.add(recipe);
            }
        }
        return furnaceRecipes;
    }
    
    // Utility method for ItemAndMetaData?
    public static boolean equalsItemAndMetaData(ItemStack one, ItemStack two) {
        if (one == two)
            return true;
        Item item1 = one.getItem();
        Item item2 = two.getItem();
        if (item1.equals(item2) == false)
            return false;
        int damage1 = GTUtility.getActualItemDamageFromStack(one);
        int damage2 = GTUtility.getActualItemDamageFromStack(two);
        return damage1 == damage2;
    }

    @Override
    public boolean canAttach() {
        return coverHolder.getCapability(GregtechCapabilities.CAPABILITY_STORAGE_NETWORK, attachedSide) != null;
    }
    
    @Override
    public boolean shouldCoverInteractWithOutputside() {
        return true;
    }

    @Override
    public void renderCover(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline, Cuboid6 plateBox, BlockRenderLayer layer) {
        Textures.CONVEYOR_OVERLAY.renderSided(attachedSide, plateBox, renderState, pipeline, translation);
    }

    @Override
    public EnumActionResult onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, CuboidRayTraceResult hitResult) {
        if (!coverHolder.getWorld().isRemote) {
            openUI((EntityPlayerMP) playerIn);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, T defaultValue) {
        if(capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return defaultValue;
    }

    protected String getUITitle() {
        return "cover.conveyor.title";
    }

    protected ModularUI buildUI(ModularUI.Builder builder, EntityPlayer player) {
        return builder.build(this, player);
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        WidgetGroup primaryGroup = new WidgetGroup();
        primaryGroup.addWidget(new LabelWidget(10, 5, getUITitle(), GTValues.VN[tier]));
        primaryGroup.addWidget(new ClickButtonWidget(10, 20, 20, 20, "-10", data -> adjustTransferRate(data.isShiftClick ? -100 : -10)));
        primaryGroup.addWidget(new ClickButtonWidget(146, 20, 20, 20, "+10", data -> adjustTransferRate(data.isShiftClick ? +100 : +10)));
        primaryGroup.addWidget(new ClickButtonWidget(30, 20, 20, 20, "-1", data -> adjustTransferRate(data.isShiftClick ? -5 : -1)));
        primaryGroup.addWidget(new ClickButtonWidget(126, 20, 20, 20, "+1", data -> adjustTransferRate(data.isShiftClick ? +5 : +1)));
        primaryGroup.addWidget(new ImageWidget(50, 20, 76, 20, GuiTextures.DISPLAY));
        primaryGroup.addWidget(new SimpleTextWidget(88, 30, "cover.conveyor.transfer_rate", 0xFFFFFF, () -> Integer.toString(transferRate)));

        this.itemFilter.initUI(70, primaryGroup::addWidget);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 190 + 82)
            .widget(primaryGroup)
            .bindPlayerInventory(player.inventory, GuiTextures.SLOT, 8, 190);
        return buildUI(builder, player);
    }

    @Override
    public boolean isWorkingEnabled() {
        return isWorkingAllowed;
    }

    @Override
    public void setWorkingEnabled(boolean isActivationAllowed) {
        this.isWorkingAllowed = isActivationAllowed;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("TransferRate", transferRate);
        tagCompound.setBoolean("WorkingAllowed", isWorkingAllowed);
        NBTTagCompound filterNBT = new NBTTagCompound();
        this.itemFilter.getItemFilter().writeToNBT(filterNBT);
        tagCompound.setTag("Filter", filterNBT);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.transferRate = tagCompound.getInteger("TransferRate");
        NBTTagCompound filterNBT = tagCompound.getCompoundTag("Filter");
        this.itemFilter.getItemFilter().readFromNBT(filterNBT);
    }
}
