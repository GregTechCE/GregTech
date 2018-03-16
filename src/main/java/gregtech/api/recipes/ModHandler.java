package gregtech.api.recipes;

import com.google.common.base.Preconditions;
import gregtech.api.GTValues;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.ToolDictNames;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.common.MetaFluids;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.DW;

public class ModHandler {

    /**
     * Returns if that Liquid is Water or Distilled Water
     */
    public static boolean isWater(FluidStack fluid) {
        return new FluidStack(FluidRegistry.WATER, 1).isFluidEqual(fluid)
                || new FluidStack(MetaFluids.DISTILLED_WATER, 1).isFluidEqual(fluid);
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
        return new FluidStack(MetaFluids.DISTILLED_WATER, amount);
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
    public static ItemStack getModItem(String modID, String itemName, int amount) {
        return getModItem(modID, itemName, amount, 0);
    }

    /**
     * Gets an Item from mods, with metadata specified
     */
    public static ItemStack getModItem(String modID, String itemName, int amount, int meta) {
        return GameRegistry.makeItemStack(modID + ":" + itemName, meta, amount, null);
    }

    public static ItemStack getBurningFuelRemainder(Random random, ItemStack fuelStack) {
        float remainderChance;
        ItemStack remainder;
        if(OreDictUnifier.getOreDictionaryNames(fuelStack).contains("fuelCoke")) {
            remainder = OreDictUnifier.get(OrePrefix.dust, Materials.Ash);
            remainderChance = 0.5f;
        } else {
            MaterialStack materialStack = OreDictUnifier.getMaterial(fuelStack);
            if(materialStack == null)
                return ItemStack.EMPTY;
            else if(materialStack.material == Materials.Charcoal) {
                remainder = OreDictUnifier.get(OrePrefix.dust, Materials.Ash);
                remainderChance = 0.3f;
            } else if(materialStack.material == Materials.Coal) {
                remainder = OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh);
                remainderChance = 0.35f;
            } else if(materialStack.material == Materials.Lignite) {
                remainder = OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh);
                remainderChance = 0.35f;
            } else return ItemStack.EMPTY;
        }
        return random.nextFloat() <= remainderChance ? remainder : ItemStack.EMPTY;
    }

    ///////////////////////////////////////////////////
    //        Furnace Smelting Recipe Helpers        //
    ///////////////////////////////////////////////////
    /**
     * Just simple Furnace smelting
     */
    public static void addSmeltingRecipe(ItemStack input, ItemStack output) {
        output = OreDictUnifier.getUnificated(output);

        boolean skip = false;
        if (input.isEmpty()) {
            GTLog.logger.error("Input cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        if (output.isEmpty()) {
            GTLog.logger.error("Output cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        if (skip) return;


        GameRegistry.addSmelting(input, output.copy(), 0.0F);
    }

    /**
     * Adds to Furnace AND Alloysmelter AND Induction Smelter
     */
    public static void addSmeltingAndAlloySmeltingRecipe(ItemStack input, ItemStack output, boolean hidden) {
        boolean skip = false;
        if (input.isEmpty()) {
            GTLog.logger.error("Input cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        if (output.isEmpty()) {
            GTLog.logger.error("Output cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        if (skip) return;

        if (input.getCount() == 1) {
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
    public static void addMirroredShapedRecipe(String regName, ItemStack result, Object... recipe) {
        result = OreDictUnifier.getUnificated(result);
        boolean skip = false;
        if (result.isEmpty()) {
            GTLog.logger.error("Result cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        skip |= validateRecipe(recipe);
        if (skip) return;

        IRecipe shapedOreRecipe = new ShapedOreRecipe(new ResourceLocation(GTValues.MODID, "general"), result.copy(), finalizeRecipeInput(recipe))
            .setMirrored(true)
            .setRegistryName(regName);
        ForgeRegistries.RECIPES.register(shapedOreRecipe);
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
    public static void addShapedRecipe(String regName, ItemStack result, Object... recipe) {
        boolean skip = false;
        if (result.isEmpty()) {
            GTLog.logger.error("Result cannot be an empty ItemStack", new IllegalArgumentException());
            skip = true;
        }
        skip |= validateRecipe(recipe);
        if (skip) return;

        IRecipe shapedOreRecipe = new ShapedOreRecipe(null, result.copy(), finalizeRecipeInput(recipe))
            .setRegistryName(regName);
        ForgeRegistries.RECIPES.register(shapedOreRecipe);
    }

    private static boolean validateRecipe(Object... recipe) {
        boolean skip = false;
        if (recipe == null) {
            GTLog.logger.error("Recipe cannot be null", new IllegalArgumentException());
            skip = true;
        } else if (recipe.length == 0) {
            GTLog.logger.error("Recipe cannot be empty", new IllegalArgumentException());
            skip = true;
        } else if (Arrays.asList(recipe).contains(null) || Arrays.asList(recipe).contains(ItemStack.EMPTY)) {
            GTLog.logger.error("Recipe cannot contain null elements or Empty ItemStacks. Recipe: " + 
                Arrays.stream(recipe).map(o -> o == null ? "NULL" : o).map(o -> o == ItemStack.EMPTY ? "EMPTY STACK" : o)
                .map(Object::toString).map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")));
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            skip = true;
        }
        return skip;
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
                String toolName = getToolNameByCharacter(c);
                if(toolName != null) {
                    recipeList.add(c);
                    recipeList.add(toolName);
                }
            }
        }
        return recipeList.toArray();
    }

    /**
     * Add Shapeless Crafting Recipes
     */
    public static void addShapelessRecipe(String regName, ItemStack result, Object... recipe) {
        boolean skip = false;
        if (result.isEmpty()) {
            GTLog.logger.error("Result ItemStack cannot be empty", new IllegalArgumentException());
            skip = true;
        }
        skip |= validateRecipe(recipe);
        if (skip) return;

        for (byte i = 0; i < recipe.length; i++) {
            if (recipe[i] instanceof MetaItem.MetaValueItem) {
                recipe[i] = ((MetaItem<?>.MetaValueItem) recipe[i]).getStackForm();
            } else if (recipe[i] instanceof Enum) {
                recipe[i] = ((Enum<?>) recipe[i]).name();
            } else if (recipe[i] instanceof UnificationEntry) {
                recipe[i] = recipe[i].toString();
            } else if(recipe[i] instanceof Character) {
                String toolName = getToolNameByCharacter((char) recipe[i]);
                if(toolName == null) {
                    throw new IllegalArgumentException("Tool name is not found for char " + recipe[i]);
                }
                recipe[i] = toolName;
            } else if (!(recipe[i] instanceof ItemStack
                    || recipe[i] instanceof Item
                    || recipe[i] instanceof Block
                    || recipe[i] instanceof String)) {
                throw new IllegalArgumentException(recipe.getClass().getSimpleName() + " type is not suitable for crafting input.");
            }
        }

        IRecipe shapelessRecipe = new ShapelessOreRecipe(null, result.copy(), recipe)
            .setRegistryName(regName);
        ForgeRegistries.RECIPES.register(shapelessRecipe);
    }

    private @Nullable static String getToolNameByCharacter(char character) {
        switch (character) {
            case 'b': return ToolDictNames.craftingToolBlade.name();
            case 'c': return ToolDictNames.craftingToolCrowbar.name();
            case 'd': return ToolDictNames.craftingToolScrewdriver.name();
            case 'f': return ToolDictNames.craftingToolFile.name();
            case 'h': return ToolDictNames.craftingToolHardHammer.name();
            case 'i': return ToolDictNames.craftingToolSolderingIron.name();
            case 'j': return ToolDictNames.craftingToolSolderingMetal.name();
            case 'k': return ToolDictNames.craftingToolKnife.name();
            case 'm': return ToolDictNames.craftingToolMortar.name();
            case 'p': return ToolDictNames.craftingToolDrawplate.name();
            case 'r': return ToolDictNames.craftingToolSoftHammer.name();
            case 's': return ToolDictNames.craftingToolSaw.name();
            case 'w': return ToolDictNames.craftingToolWrench.name();
            case 'x': return ToolDictNames.craftingToolWireCutter.name();
            default: return null;
        }
    }

    ///////////////////////////////////////////////////
    //              Recipe Remove Helpers            //
    ///////////////////////////////////////////////////

    /**
     * Removes a Smelting Recipe
     */
    public static boolean removeFurnaceSmelting(ItemStack input) {
        Validate.isTrue(!input.isEmpty(), "Cannot remove furnace recipe with empty input.");
        for (ItemStack stack : FurnaceRecipes.instance().getSmeltingList().keySet()) {
            if (ItemStack.areItemStacksEqual(input, stack)) {
                FurnaceRecipes.instance().getSmeltingList().remove(stack);
                return true;
            }
        }
        return false;
    }

    public static int removeRecipes(Item output) {
        return removeRecipes(recipe -> {
            ItemStack recipeOutput = recipe.getRecipeOutput();
            return !recipeOutput.isEmpty() && recipeOutput.getItem() == output;
        });
    }

    public static int removeRecipes(ItemStack output) {
        return removeRecipes(recipe -> ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), output));
    }

    public static <R extends IRecipe> int removeRecipes(Class<R> recipeClass) {
        return removeRecipes(recipeClass::isInstance);
    }

    public static int removeRecipes(Predicate<IRecipe> predicate) {
        int recipesRemoved = 0;

        IForgeRegistry<IRecipe> registry = ForgeRegistries.RECIPES;
        List<IRecipe> toRemove = new ArrayList<>();

        for (IRecipe recipe : registry) {
            if (predicate.test(recipe)) {
                toRemove.add(recipe);
                recipesRemoved++;
            }
        }

        toRemove.forEach(recipe -> registry.register(new DummyRecipe().setRegistryName(recipe.getRegistryName())));

        return recipesRemoved;
    }

    public static void removeRecipeByName(ResourceLocation location) {
        ForgeRegistries.RECIPES.register(new DummyRecipe().setRegistryName(location));
    }

    ///////////////////////////////////////////////////
    //            Get Recipe Output Helpers          //
    ///////////////////////////////////////////////////

    public static ItemStack getRecipeOutput(World world, ItemStack... recipe) {
        if (recipe == null || recipe.length == 0) return ItemStack.EMPTY;

        if (world == null) world = DW;

        boolean temp = false;
        for (ItemStack stack : recipe) {
            if (!stack.isEmpty()) {
                temp = true;
                break;
            }
        }
        if (!temp) return ItemStack.EMPTY;

        InventoryCrafting craftingGrid = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < 9 && i < recipe.length; i++) craftingGrid.setInventorySlotContents(i, recipe[i]);

        for (IRecipe tmpRecipe : CraftingManager.REGISTRY) {
            if (tmpRecipe.matches(craftingGrid, world)) {
                return tmpRecipe.getCraftingResult(craftingGrid);
            }
        }

        return ItemStack.EMPTY;
    }

    public static ItemStack getSmeltingOutput(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;
        return OreDictUnifier.getUnificated(FurnaceRecipes.instance().getSmeltingResult(input));
    }

    public static void addRCFurnaceRecipe(ItemStack input, ItemStack output, int duration) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(output);
        Preconditions.checkArgument(duration > 0, "Duration should be positive!");
//        if(Loader.isModLoaded("railcraft")) {
//            addRCFurnaceRecipeInternal(input, output, duration);
//        }
    }

//    @Optional.Method(modid = "railcraft")
//    private static void addRCFurnaceRecipeInternal(ItemStack input, ItemStack output, int duration) {
//        RailcraftCraftingManager.blastFurnace.addRecipe(input, true, false, duration, output);
//    }

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
    public static long chargeElectricItem(ItemStack stack, long charge, int tier, boolean ignoreLimit, boolean simulate) {
        if (isElectricItem(stack)) {
            IElectricItem electricItem = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
            return electricItem.charge(charge, tier, ignoreLimit, simulate);
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
    public static long dischargeElectricItem(ItemStack stack, long charge, int tier, boolean ignoreLimit, boolean externally, boolean simulate) {
        if (isElectricItem(stack)) {
            IElectricItem electricItem = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
            return electricItem.discharge(charge, tier, ignoreLimit, externally, simulate);
        }
        return 0;
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean canUseElectricItem(ItemStack stack, long charge) {
        boolean electricItem = isElectricItem(stack);
        if (!electricItem) {
            return false;
        }
        IElectricItem item = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
        return item.canUse(charge);
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean useElectricItem(ItemStack stack, long charge, EntityPlayer player) {
        boolean electricItem = isElectricItem(stack);
        if (!electricItem) {
            return false;
        }
        IElectricItem item = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
        return item.use(charge, player);
    }


//    /**
//     * Uses a Soldering Iron
//     */
//    public static boolean useSolderingIron(ItemStack stack, EntityLivingBase playerIn) {
//        if (playerIn == null || stack == null) return false;
//
//        if (GTUtility.isStackInList(stack, GregTechAPI.solderingToolList)) {
//            if (playerIn instanceof EntityPlayer) {
//                EntityPlayer player = (EntityPlayer) playerIn;
//                if (player.capabilities.isCreativeMode) return true;
//
//                if (isElectricItem(stack) && ElectricItem.manager.getCharge(stack) > 1000.0D) {
//
//                    for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
//                        if (GTUtility.isStackInList(player.inventory.mainInventory.get(i), GregTechAPI.solderingMetalList)) {
//                            if (player.inventory.mainInventory.get(i).getCount() < 1) return false;
//
//                            if (player.inventory.mainInventory.get(i).getCount() == 1) {
//                                player.inventory.mainInventory.set(i, ItemStack.EMPTY);
//                            } else {
//                                player.inventory.mainInventory.get(i).shrink(1);
//                            }
//
//                            if (player.inventoryContainer != null) player.inventoryContainer.detectAndSendChanges();
//
//                            if (canUseElectricItem(stack, 10000)) {
//                                return ModHandler.useElectricItem(stack, 10000, (EntityPlayer) playerIn);
//                            }
//
//                            ModHandler.useElectricItem(stack, (int) ElectricItem.manager.getCharge(stack), (EntityPlayer) playerIn);
//                            return false;
//                        }
//                    }
//                }
//            } else {
//                damageOrDechargeItem(stack, 1, 1000, playerIn);
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Is this an electric Item?
     */
    public static boolean isElectricItem(ItemStack stack) {
        return !stack.isEmpty() && stack.hasCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
    }

    public static boolean isElectricItem(ItemStack stack, int tier) {
        if (isElectricItem(stack)) {
            IElectricItem capability = stack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
            return capability.getTier() == tier;
        }
        return false;
    }
}
