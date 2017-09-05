package gregtech.api.recipes;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.items.IDamagableItem;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.ISpecialElectricItem;
import ic2.api.recipe.Recipes;
import ic2.core.block.state.IIdProvider;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.DW;
import static gregtech.api.GTValues.V;

public class ModHandler {

    /**
     * Returns if that Liquid is Water or Distilled Water
     */
    public static boolean isWater(FluidStack fluid) {
        return new FluidStack(FluidRegistry.WATER, 1).isFluidEqual(fluid)
                || new FluidStack(FluidName.distilled_water.getInstance(), 1).isFluidEqual(fluid);
    }

    /**
     * Returns a Liquid Stack with given amount of Water.
     */
    public static FluidStack getWater(int amount) {
        return new FluidStack(FluidRegistry.WATER, amount);
    }

    /**
     * Returns a Liquid Stack with given amount of distilled Water.
     */
    public static FluidStack getDistilledWater(int amount) {
        return new FluidStack(FluidName.distilled_water.getInstance(), amount);
    }

    /**
     * Returns if that Liquid is Lava
     */
    public static boolean isLava(FluidStack fluid) {
        return new FluidStack(FluidRegistry.LAVA, 0).isFluidEqual(fluid);
    }

    /**
     * Returns a Liquid Stack with given amount of Lava.
     */
    public static FluidStack getLava(int amount) {
        return new FluidStack(FluidRegistry.LAVA, amount);
    }

    /**
     * Returns if that Liquid is Steam
     */
    public static boolean isSteam(FluidStack fluid) {
        return getSteam(1).isFluidEqual(fluid);
    }

    /**
     * Returns a Liquid Stack with given amount of Steam.
     */
    public static FluidStack getSteam(int amount) {
        return Materials.Steam.getFluid(amount);
    }

    /**
     * Returns if that Liquid is Milk
     */
    public static boolean isMilk(Fluid fluid) {
        return getMilk(1).getFluid() == fluid;
    }

    /**
     * Returns a Liquid Stack with given amount of Milk.
     */
    public static FluidStack getMilk(int amount) {
        return FluidRegistry.getFluidStack("milk", amount);
    }

    public static boolean isMaterialWood(Material material) {
        return material == Materials.Wood || material == Materials.WoodSealed;
    }

    /**
     * Gets an Item from mods
     */
    @Nullable
    public static ItemStack getModItem(String modID, String itemName, int amount) {
        return getModItem(modID, itemName, amount, 0);
    }

    /**
     * Gets an Item from mods, with metadata specified
     */
    @Nullable
    public static ItemStack getModItem(String modID, String itemName, int amount, int meta) {
        return GameRegistry.makeItemStack(modID + ":" + itemName, meta, amount, null);
    }

    ///////////////////////////////////////////////////
    //        Furnace Smelting Recipe Helpers        //
    ///////////////////////////////////////////////////
    /**
     * Just simple Furnace smelting
     */
    public static void addSmeltingRecipe(ItemStack input, ItemStack output) {
        output = OreDictUnifier.getUnificated(output);

        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(output, "Output cannot be null");

        GameRegistry.addSmelting(input, output.copy(), 0.0F);
    }

    /**
     * Adds to Furnace AND Alloysmelter AND Induction Smelter
     */
    public static void addSmeltingAndAlloySmeltingRecipe(ItemStack input, ItemStack output, boolean hidden) {
        Validate.notNull(input, "Input cannot be null");
        Validate.notNull(output, "Output cannot be null");

        if (input.stackSize == 1) {
            addSmeltingRecipe(input, output);
        }

        RecipeBuilder.NotConsumableInputRecipeBuilder recipeBuilder =
                RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
                        .inputs(input)
                        .outputs(output)
                        .duration(130)
                        .EUt(3);

        OrePrefix prefix = OreDictUnifier.getPrefix(output);
        if (prefix != null) {
            switch (prefix) {
                case ingot:
                    recipeBuilder.notConsumable(MetaItems.SHAPE_MOLD_INGOT);
                    break;
                case block:
                    recipeBuilder.notConsumable(MetaItems.SHAPE_MOLD_BLOCK);
                    break;
                case nugget:
                    recipeBuilder.notConsumable(MetaItems.SHAPE_MOLD_NUGGET);
                    break;
            }

            if (hidden) {
                recipeBuilder.hidden();
            }
            recipeBuilder.buildAndRegister();
        }

//        if (GregTechMod.gregtechproxy.mTEMachineRecipes) {
//            ThermalExpansion.addInductionSmelterRecipe(input, null, output, null, output.stackSize * 1600, 0);
//        }
    }

    ///////////////////////////////////////////////////
    //              Crafting Recipe Helpers          //
    ///////////////////////////////////////////////////

    /**
     * Adds Shaped Crafting Recipes.
     * <p/>
     * MetaValueItem's are converted to ItemStack via {@link MetaItem.MetaValueItem#getStackForm()} method.
     * <p/>
     * For Enums - {@link Enum#name()} is called.
     * <p/>
     * For UnificationEntry - {@link UnificationEntry#toString()} is called.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * <ul>
     * <li>'b' -  ToolDictNames.craftingToolBlade</li>
     * <li>'c' -  ToolDictNames.craftingToolCrowbar</li>
     * <li>'d' -  ToolDictNames.craftingToolScrewdriver</li>
     * <li>'f' -  ToolDictNames.craftingToolFile</li>
     * <li>'h' -  ToolDictNames.craftingToolHardHammer</li>
     * <li>'i' -  ToolDictNames.craftingToolSolderingIron</li>
     * <li>'j' -  ToolDictNames.craftingToolSolderingMetal</li>
     * <li>'k' -  ToolDictNames.craftingToolKnife</li>
     * <li>'m' -  ToolDictNames.craftingToolMortar</li>
     * <li>'p' -  ToolDictNames.craftingToolDrawplate</li>
     * <li>'r' -  ToolDictNames.craftingToolSoftHammer</li>
     * <li>'s' -  ToolDictNames.craftingToolSaw</li>
     * <li>'w' -  ToolDictNames.craftingToolWrench</li>
     * <li>'x' -  ToolDictNames.craftingToolWireCutter</li>
     * </ul>
     */
    public static void addMirroredShapedRecipe(ItemStack result, Object... recipe) {
        result = OreDictUnifier.getUnificated(result);
        Validate.notNull(result, "Result cannot be null");
        Validate.notNull(recipe, "Recipe cannot be null");
        Validate.isTrue(recipe.length > 0, "Recipe cannot be empty");
        Validate.noNullElements(recipe, "Recipe cannot contain null elements");

        GameRegistry.addRecipe(new ShapedOreRecipe(result, finalizeRecipeInput(recipe)).setMirrored(true));
    }

    /**
     * Adds Shaped Crafting Recipes.
     * <p/>
     * MetaValueItem's are converted to ItemStack via {@link MetaItem.MetaValueItem#getStackForm()} method.
     * <p/>
     * For Enums - {@link Enum#name()} is called.
     * <p/>
     * For UnificationEntry - {@link UnificationEntry#toString()} is called.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * <ul>
     * <li>'b' -  ToolDictNames.craftingToolBlade</li>
     * <li>'c' -  ToolDictNames.craftingToolCrowbar</li>
     * <li>'d' -  ToolDictNames.craftingToolScrewdriver</li>
     * <li>'f' -  ToolDictNames.craftingToolFile</li>
     * <li>'h' -  ToolDictNames.craftingToolHardHammer</li>
     * <li>'i' -  ToolDictNames.craftingToolSolderingIron</li>
     * <li>'j' -  ToolDictNames.craftingToolSolderingMetal</li>
     * <li>'k' -  ToolDictNames.craftingToolKnife</li>
     * <li>'m' -  ToolDictNames.craftingToolMortar</li>
     * <li>'p' -  ToolDictNames.craftingToolDrawplate</li>
     * <li>'r' -  ToolDictNames.craftingToolSoftHammer</li>
     * <li>'s' -  ToolDictNames.craftingToolSaw</li>
     * <li>'w' -  ToolDictNames.craftingToolWrench</li>
     * <li>'x' -  ToolDictNames.craftingToolWireCutter</li>
     * </ul>
     */
    public static void addShapedRecipe(ItemStack result, Object... recipe) {
        result = OreDictUnifier.getUnificated(result);
        Validate.notNull(result, "Result cannot be null");
        Validate.notNull(recipe, "Recipe cannot be null");
        Validate.isTrue(recipe.length > 0, "Recipe cannot be empty");
        Validate.noNullElements(recipe, "Recipe cannot contain null elements");

        GameRegistry.addRecipe(new ShapedOreRecipe(result, finalizeRecipeInput(recipe)));
    }

    private static Object[] finalizeRecipeInput(Object... recipe) {
        for (byte i = 0; i < recipe.length; i++) {
            if (recipe[i] instanceof MetaItem.MetaValueItem) {
                recipe[i] = ((MetaItem<?>.MetaValueItem) recipe[i]).getStackForm();
            } else if (recipe[i] instanceof Enum) {
                recipe[i] = ((Enum<?>) recipe[i]).name();
            } else if (recipe[i] instanceof UnificationEntry) {
                recipe[i] = recipe[i].toString();
            } else if (!(recipe[i] instanceof ItemStack
                    || recipe[i] instanceof Item
                    || recipe[i] instanceof Block
                    || recipe[i] instanceof String
                    || recipe[i] instanceof Character
                    || recipe[i] instanceof Boolean)) {
                throw new IllegalArgumentException(recipe.getClass().getSimpleName() + " type is not suitable for crafting input.");
            }
        }

        int idx = 0;
        ArrayList<Object> recipeList = new ArrayList<>(Arrays.asList(recipe));

        while (recipe[idx] instanceof String) {

            StringBuilder s = new StringBuilder((String) recipe[idx++]);

            while (s.length() < 3) s.append(" ");

            if (s.length() > 3) throw new IllegalArgumentException();

            for (char c : s.toString().toCharArray()) {
                switch (c) {
                    case 'b':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolBlade.name());
                        break;
                    case 'c':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolCrowbar.name());
                        break;
                    case 'd':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolScrewdriver.name());
                        break;
                    case 'f':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolFile.name());
                        break;
                    case 'h':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolHardHammer.name());
                        break;
                    case 'i':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolSolderingIron.name());
                        break;
                    case 'j':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolSolderingMetal.name());
                        break;
                    case 'k':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolKnife.name());
                        break;
                    case 'm':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolMortar.name());
                        break;
                    case 'p':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolDrawplate.name());
                        break;
                    case 'r':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolSoftHammer.name());
                        break;
                    case 's':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolSaw.name());
                        break;
                    case 'w':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolWrench.name());
                        break;
                    case 'x':
                        recipeList.add(c);
                        recipeList.add(ToolDictNames.craftingToolWireCutter.name());
                        break;
                }
            }
        }
        return recipe;
    }

    /**
     * Add Shapeless Crafting Recipes
     */
    public static void addShapelessRecipe(ItemStack result, Object... recipe) {
        result = OreDictUnifier.getUnificated(result);
        Validate.notNull(result, "Result cannot be null");
        Validate.notNull(recipe, "Recipe cannot be null");
        Validate.isTrue(recipe.length > 0, "Recipe cannot be empty");
        Validate.noNullElements(recipe, "Recipe cannot contain null elements");

        for (byte i = 0; i < recipe.length; i++) {
            if (recipe[i] instanceof MetaItem.MetaValueItem) {
                recipe[i] = ((MetaItem<?>.MetaValueItem) recipe[i]).getStackForm();
            } else if (recipe[i] instanceof Enum) {
                recipe[i] = ((Enum<?>) recipe[i]).name();
            } else if (recipe[i] instanceof UnificationEntry) {
                recipe[i] = recipe[i].toString();
            } else if (!(recipe[i] instanceof ItemStack
                    || recipe[i] instanceof Item
                    || recipe[i] instanceof Block
                    || recipe[i] instanceof String
                    || recipe[i] instanceof Character)) {
                throw new IllegalArgumentException(recipe.getClass().getSimpleName() + " type is not suitable for crafting input.");
            }
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(result.copy(), recipe));
    }

    ///////////////////////////////////////////////////
    //              Recipe Remove Helpers            //
    ///////////////////////////////////////////////////

    /**
     * Removes a Smelting Recipe
     */
    public static boolean removeFurnaceSmelting(ItemStack input) {
        Validate.notNull(input, "Cannot remove furnace recipe with null input.");
        for (ItemStack stack : FurnaceRecipes.instance().getSmeltingList().keySet()) {
            if (ItemStack.areItemStacksEqual(input, stack)) {
                FurnaceRecipes.instance().getSmeltingList().remove(stack);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a Crafting Recipe and gives you the former output of it.
     *
     * @param recipe The content of the Crafting Grid as ItemStackArray with length 9
     * @return the output of the old Recipe or null if there was nothing.
     */
    @Nullable
    public static ItemStack removeRecipe(ItemStack... recipe) {
        Validate.notNull(recipe, "Cannot remove null recipe.");

        //TODO CONFIG to not remove recipes

        recipe = GTUtility.copyStackArray(recipe);

        //At least one not null
        boolean temp = false;
        for (ItemStack stack : recipe) {
            if (stack != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return null;

        ItemStack returnStack = null;
        InventoryCrafting craftingGrid = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < recipe.length && i < 9; i++) craftingGrid.setInventorySlotContents(i, recipe[i]);

        List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
        int listSize = list.size();

        for (int i = 0; i < listSize; i++) {
            for (; i < listSize; i++) {
                if (list.get(i).matches(craftingGrid, DW)) {
                    returnStack = list.get(i).getCraftingResult(craftingGrid);
                    if (returnStack != null) list.remove(i--);
                    listSize = list.size();
                }
            }
        }
        return returnStack;
    }

    ///////////////////////////////////////////////////
    //            Get Recipe Output Helpers          //
    ///////////////////////////////////////////////////

    @Nullable
    public static ItemStack getRecipeOutput(World world, ItemStack... recipe) {
        if (recipe == null || recipe.length == 0) return null;

        if (world == null) world = DW;

        boolean temp = false;
        for (ItemStack stack : recipe) {
            if (stack != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return null;

        InventoryCrafting craftingGrid = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < 9 && i < recipe.length; i++) craftingGrid.setInventorySlotContents(i, recipe[i]);

        List<IRecipe> list = CraftingManager.getInstance().getRecipeList();

        for (IRecipe tmpRecipe : list) {
            if (tmpRecipe.matches(craftingGrid, world)) {
                return tmpRecipe.getCraftingResult(craftingGrid);
            }
        }

        return null;
    }

    @Nullable
    public static ItemStack getSmeltingOutput(ItemStack input) {
        if (input == null) return null;
        return OreDictUnifier.getUnificated(FurnaceRecipes.instance().getSmeltingResult(input));
    }

    /**
     * Used in my own Recycler.
     * <p/>
     * Only produces Scrap if scrapChance == 0. scrapChance is usually the random Number I give to the Function
     * If you directly insert 0 as scrapChance then you can check if its Recycler-Blacklisted or similar
     */
    public static ItemStack getRecyclerOutput(ItemStack input, int scrapChance) {
        if (input == null || scrapChance != 0) return null;

        if (Recipes.recyclerWhitelist.isEmpty())
            return Recipes.recyclerBlacklist.contains(input) ? null : IC2.getScrap(1);
        return Recipes.recyclerWhitelist.contains(input) ? IC2.getScrap(1) : null;
    }

    public static void addRCFurnaceRecipe(ItemStack input, ItemStack output, int duration) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(output);
        Preconditions.checkArgument(duration > 0, "Duration should be positive!");
        if(Loader.isModLoaded("railcraft")) {
            addRCFurnaceRecipeInternal(input, output, duration);
        }
    }

    @Optional.Method(modid = "railcraft")
    private static void addRCFurnaceRecipeInternal(ItemStack input, ItemStack output, int duration) {
        RailcraftCraftingManager.blastFurnace.addRecipe(input, true, false, duration, output);
    }

    ///////////////////////////////////////////////////
    //             Electric Items Helpers            //
    ///////////////////////////////////////////////////

    /**
     * Charges an Electric Item. Only if it's a valid Electric Item of course.
     * This forces the Usage of proper Voltages (so not the transfer limits defined by the Items) unless you ignore the Transfer Limit.
     * If tier is Integer.MAX_VALUE it will ignore Tier based Limitations.
     *
     * @return the actually used Energy.
     */
    public static int chargeElectricItem(ItemStack stack, int charge, int tier, boolean ignoreLimit, boolean simulate) {

        if (isElectricItem(stack)) {
            int tmpTier = ElectricItem.manager.getTier(stack);
            if (tmpTier < 0 || tmpTier == tier || tier == Integer.MAX_VALUE) {

                if (!ignoreLimit && tmpTier >= 0) {
                    charge = (int) Math.min(charge, V[Math.max(0, Math.min(V.length - 1, tmpTier))]);
                }

                if (charge > 0) {
                    int chargeTmp = (int) Math.max(0.0, ElectricItem.manager.charge(stack, charge, tmpTier, true, simulate));
                    return chargeTmp + (chargeTmp * 4 > tier ? tier : 0);
                }
            }
        }
        return 0;
    }

    /**
     * Discharges an Electric Item. Only if it's a valid Electric Item for that of course.
     * This forces the Usage of proper Voltages (so not the transfer limits defined by the Items) unless you ignore the Transfer Limit.
     * If tier is Integer.MAX_VALUE it will ignore Tier based Limitations.
     *
     * @return the Energy got from the Item.
     */
    public static double dischargeElectricItem(ItemStack stack, double charge, int tier, boolean ignoreLimit, boolean simulate, boolean ignoreDischargability) {

        if (isElectricItem(stack)) {
            int tmpTier = ElectricItem.manager.getTier(stack);
            if (tmpTier < 0 || tmpTier == tier || tier == Integer.MAX_VALUE) {

                if (!ignoreLimit && tmpTier >= 0) {
                    charge = Math.min(charge, V[Math.max(0, Math.min(V.length - 1, tmpTier))]);
                }

                if (charge > 0) {
                    double chargeTmp = Math.max(0, ElectricItem.manager.discharge(stack, charge + (charge * 4 > tier ? tier : 0), tmpTier, true, !ignoreDischargability, simulate));
                    return chargeTmp - (chargeTmp * 4 > tier ? tier : 0);
                }
            }
        }
        return 0;
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean canUseElectricItem(ItemStack stack, int charge) {
        return isElectricItem(stack) && ElectricItem.manager.canUse(stack, charge);
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean useElectricItem(ItemStack stack, int charge, EntityPlayer player) {
        return isElectricItem(stack) && ElectricItem.manager.use(stack, charge, player);
    }

    /**
     * Uses an Item. Tries to discharge in case of Electric Items
     */
    public static boolean damageOrDechargeItem(ItemStack stack, int damage, int decharge, EntityLivingBase player) {
        if (stack == null || (stack.getMaxStackSize() <= 1 && stack.stackSize > 1)) return false;

        if (player != null && player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isCreativeMode) {
            return true;
        }

        if (stack.getItem() instanceof IDamagableItem) {
            return ((IDamagableItem) stack.getItem()).doDamageToItem(stack, damage);
        } else if (ModHandler.isElectricItem(stack)) {

            if (canUseElectricItem(stack, decharge)) {
                if (player != null && player instanceof EntityPlayer) {
                    return ModHandler.useElectricItem(stack, decharge, (EntityPlayer) player);
                }
                return ModHandler.dischargeElectricItem(stack, decharge, Integer.MAX_VALUE, true, false, true) >= decharge;
            }
        } else if (stack.getItem().isDamageable()) {

            if (player == null) {
                stack.setItemDamage(stack.getItemDamage() + damage);
            } else {
                stack.damageItem(damage, player);
            }

            if (stack.getItemDamage() >= stack.getMaxDamage()) {
                stack.setItemDamage(stack.getMaxDamage() + 1);
//                ItemStack containerItem = GTUtility.getContainerItem(stack, true);
//                if (containerItem != null) {
//                    stack = containerItem.copy();
//                }
            }
            return true;
        }
        return false;
    }

    /**
     * Uses a Soldering Iron
     */
    public static boolean useSolderingIron(ItemStack stack, EntityLivingBase playerIn) {
        if (playerIn == null || stack == null) return false;

        if (GTUtility.isStackInList(stack, GregTechAPI.solderingToolList)) {
            if (playerIn instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) playerIn;
                if (player.capabilities.isCreativeMode) return true;

                if (isElectricItem(stack) && ElectricItem.manager.getCharge(stack) > 1000.0D) {

                    for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                        if (GTUtility.isStackInList(player.inventory.mainInventory[i], GregTechAPI.solderingMetalList)) {
                            if (player.inventory.mainInventory[i].stackSize < 1) return false;

                            if (player.inventory.mainInventory[i].stackSize == 1) {
                                player.inventory.mainInventory[i] = null;
                            } else {
                                player.inventory.mainInventory[i].stackSize--;
                            }

                            if (player.inventoryContainer != null) player.inventoryContainer.detectAndSendChanges();

                            if (canUseElectricItem(stack, 10000)) {
                                return ModHandler.useElectricItem(stack, 10000, (EntityPlayer) playerIn);
                            }

                            ModHandler.useElectricItem(stack, (int) ElectricItem.manager.getCharge(stack), (EntityPlayer) playerIn);
                            return false;
                        }
                    }
                }
            } else {
                damageOrDechargeItem(stack, 1, 1000, playerIn);
                return true;
            }
        }
        return false;
    }

    /**
     * Is this an electric Item, which can charge other Items?
     */
    public static boolean isChargerItem(ItemStack stack) {
        if (stack != null && isElectricItem(stack)) {
            if (stack.getItem() instanceof ISpecialElectricItem) {
                return true;
            } else if (stack.getItem() instanceof IElectricItem) {
                return ((IElectricItem) stack.getItem()).canProvideEnergy(stack);
            }
        }
        return false;
    }

    /**
     * Is this an electric Item?
     */
    public static boolean isElectricItem(ItemStack stack) {
        return stack != null && (stack.getItem() instanceof IElectricItem || stack.getItem() instanceof ISpecialElectricItem);
    }

    public static boolean isElectricItem(ItemStack stack, int tier) {
        return stack != null && isElectricItem(stack) && ElectricItem.manager.getTier(stack) == tier;
    }

    public static class IC2 {

        public static <T extends IIdProvider> IBlockState getIC2BlockState(BlockName blockName, T type) {
            return blockName.getBlockState(type);
        }

        public static ItemStack getIC2Item(ItemName itemName, int amount) {
            ItemStack stack = itemName.getItemStack();
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getIC2Item(ItemName itemName, int amount, boolean wildcard) {
            ItemStack stack = itemName.getItemStack();
            if (wildcard) stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getIC2Item(ItemName itemName, int amount, int explicitDamage) {
            ItemStack stack = itemName.getItemStack();
            stack.setItemDamage(explicitDamage);
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getIC2Item(BlockName itemName, int amount) {
            ItemStack stack = itemName.getItemStack();
            stack.stackSize = amount;
            return stack;
        }

        public static <T extends Enum<T> & IIdProvider> ItemStack getIC2Item(BlockName itemName, T variant, int amount) {
            ItemStack stack = itemName.getItemStack(variant);
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getIC2TEItem(TeBlock variant, int amount) {
            ItemStack stack = BlockName.te.getItemStack(variant);
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getIC2Item(ItemName itemName, String variant, int amount) {
            ItemStack stack = itemName.getItemStack(variant);
            stack.stackSize = amount;
            return stack;
        }

        public static <T extends Enum<T> & IIdProvider> ItemStack getIC2Item(ItemName itemName, T type, int amount) {
            ItemStack stack = itemName.getItemStack(type);
            stack.stackSize = amount;
            return stack;
        }

        public static ItemStack getScrapBox(int amount) {
            return ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.scrap_box, amount);
        }

        public static ItemStack getScrap(int amount) {
            return ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.scrap, amount);
        }

    }

    public static class ThermalExpansion {

        /**
         * LiquidTransposer Recipe for both directions
         */
        public static void addLiquidTransposerRecipe(ItemStack emptyContainer, FluidStack fluid, ItemStack fullContainer, int RF) {
            fullContainer = OreDictUnifier.getUnificated(fullContainer);
            Validate.notNull(emptyContainer, "Empty Container cannot be null");
            Validate.notNull(fullContainer, "Full Container cannot be null");
            Validate.notNull(fluid, "Fluid Container cannot be null");

//            if (!GregTechMod.gregtechproxy.mTEMachineRecipes &&
//                    !GregTechAPI.sRecipeFile.get(ConfigCategories.Machines.liquidtransposer, fullContainer, true))
//                return;

            try {
                ThermalExpansion.addTransposerFill(RF * 10, emptyContainer, fullContainer, fluid, true);
            } catch (Throwable e) {/*Do nothing*/}
        }

        /**
         * LiquidTransposer Recipe for filling Containers
         */
        public static void addLiquidTransposerFillRecipe(ItemStack emptyContainer, FluidStack fluid, ItemStack fullContainer, int MJ) {
            fullContainer = OreDictUnifier.getUnificated(fullContainer);
            Validate.notNull(emptyContainer, "Empty Container cannot be null");
            Validate.notNull(fullContainer, "Full Container cannot be null");
            Validate.notNull(fluid, "Fluid Container cannot be null");

//            if (!GregTechMod.gregtechproxy.mTEMachineRecipes &&
//                    !GregTechAPI.sRecipeFile.get(ConfigCategories.Machines.liquidtransposerfilling, fullContainer, true))
//                return;

            try {
                ThermalExpansion.addTransposerFill(MJ * 10, emptyContainer, fullContainer, fluid, false);
            } catch (Throwable e) {/*Do nothing*/}
        }

        /**
         * LiquidTransposer Recipe for emptying Containers
         */
        public static void addLiquidTransposerEmptyRecipe(ItemStack fullContainer, FluidStack fluid, ItemStack emptyContainer, int RF) {
            emptyContainer = OreDictUnifier.getUnificated(emptyContainer);
            Validate.notNull(emptyContainer, "Empty Container cannot be null");
            Validate.notNull(fullContainer, "Full Container cannot be null");
            Validate.notNull(fluid, "Fluid Container cannot be null");

//            if (!GregTechMod.gregtechproxy.mTEMachineRecipes &&
//                    !GregTechAPI.sRecipeFile.get(ConfigCategories.Machines.liquidtransposeremptying, fullContainer, true))
//                return;

            try {
                ThermalExpansion.addTransposerExtract(RF * 10, fullContainer, emptyContainer, fluid, 100, false);
            } catch (Throwable e) {/*Do nothing*/}
        }

        /**
         * Adds a Recipe to the Sawmills of GregTech and ThermalCraft
         */
        public static void addSawmillRecipe(ItemStack input1, ItemStack output1, ItemStack output2) {
            output2 = OreDictUnifier.getUnificated(output2);
            Validate.notNull(input1, "Input cannot be null");
            Validate.notNull(output1, "Output cannot be null");

//            if (!GregTechMod.gregtechproxy.mTEMachineRecipes &&
//                    !GregTechAPI.sRecipeFile.get(ConfigCategories.Machines.sawmill, input1, true))
//                return;

            try {
                ThermalExpansion.addSawmillRecipe(1600, input1, output1, output2, 100);
            } catch (Throwable e) {/*Do nothing*/}
        }

        /**
         * Induction Smelter Recipes for TE
         */
        public static void addInductionSmelterRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int energy, int chance) {
            output1 = OreDictUnifier.getUnificated(output1);
            output2 = OreDictUnifier.getUnificated(output2);
            Validate.notNull(input1, "Input cannot be null");
            Validate.notNull(output1, "Output cannot be null");
//            Validate.isTrue(GTUtility.getContainerItem(input1, false) != null, "Input item cannot have container item");

//            if (!GregTechMod.gregtechproxy.mTEMachineRecipes &&
//                    !GregTechAPI.sRecipeFile.get(ConfigCategories.Machines.inductionsmelter, input2 == null ? input1 : output1, true))
//                return;

            try {
                ThermalExpansion.addSmelterRecipe(energy * 10, GTUtility.copy(input1), input2 == null ? new ItemStack(Blocks.SAND, 1, 0) : input2, output1, output2, chance);
            } catch (Throwable e) {/*Do nothing*/}
        }

        public static void addFurnaceRecipe(int energy, ItemStack input, ItemStack output) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            FMLInterModComms.sendMessage("ThermalExpansion", "FurnaceRecipe", toSend);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
            addPulverizerRecipe(energy, input, primaryOutput, null, 0);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addPulverizerRecipe(energy, input, primaryOutput, secondaryOutput, 100);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (input == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
            addSawmillRecipe(energy, input, primaryOutput, null, 0);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addSawmillRecipe(energy, input, primaryOutput, secondaryOutput, 100);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (input == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "SawmillRecipe", toSend);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput) {
            addSmelterRecipe(energy, primaryInput, secondaryInput, primaryOutput, null, 0);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addSmelterRecipe(energy, primaryInput, secondaryInput, primaryOutput, secondaryOutput, 100);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (primaryInput == null || secondaryInput == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("primaryInput", new NBTTagCompound());
            toSend.setTag("secondaryInput", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
            secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
        }

        public static void addSmelterBlastOre(Material material) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("oreType", material.toString());
            FMLInterModComms.sendMessage("ThermalExpansion", "SmelterBlastOreType", toSend);
        }

        public static void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
            if (input == null || output == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
        }

        public static void addTransposerFill(int energy, ItemStack input, ItemStack output, FluidStack fluid, boolean reversible) {
            if (input == null || output == null || fluid == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            toSend.setTag("fluid", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            toSend.setBoolean("reversible", reversible);
            fluid.writeToNBT(toSend.getCompoundTag("fluid"));
            FMLInterModComms.sendMessage("ThermalExpansion", "TransposerFillRecipe", toSend);
        }

        public static void addTransposerExtract(int energy, ItemStack input, ItemStack output, FluidStack fluid, int chance, boolean reversible) {
            if (input == null || output == null || fluid == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            toSend.setTag("fluid", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            toSend.setBoolean("reversible", reversible);
            toSend.setInteger("chance", chance);
            fluid.writeToNBT(toSend.getCompoundTag("fluid"));
            FMLInterModComms.sendMessage("ThermalExpansion", "TransposerExtractRecipe", toSend);
        }

        public static void addMagmaticFuel(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "MagmaticFuel", toSend);
        }

        public static void addCompressionFuel(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "CompressionFuel", toSend);
        }

        public static void addCoolant(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "Coolant", toSend);
        }
    }

}
