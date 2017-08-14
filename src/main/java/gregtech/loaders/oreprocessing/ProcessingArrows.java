package gregtech.loaders.oreprocessing;

import gregtech.api.enchants.EnchantmentEnderDamage;
import gregtech.api.items.OreDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ProcessingArrows implements IOreRegistrationHandler {
    public ProcessingArrows() {
        OrePrefix.arrowGtWood.add(this);
        OrePrefix.arrowGtPlastic.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ItemStack tOutput = GT_Utility.copyAmount(1, stack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.SMITE, EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, tOutput) + 3);
        RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).fluidInputs(Materials.HolyWater.getFluid(25)).outputs(tOutput).duration(100).EUt(2).buildAndRegister();

        //TODO Twilight
        //tOutput = GT_Utility.copyAmount(1, stack);
        //GT_Utility.updateItemStack(tOutput);
        //GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.FIRE_ASPECT, EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, tOutput) + 3);
        //RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).fluidInputs(Materials.FierySteel.getFluid(25)).outputs(tOutput).duration(100).EUt(2).buildAndRegister();

        tOutput = GT_Utility.copyAmount(1, stack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.FIRE_ASPECT, EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, tOutput) + 1);
        RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).fluidInputs(Materials.Blaze.getFluid(18)).outputs(tOutput).duration(100).EUt(2).buildAndRegister();

        tOutput = GT_Utility.copyAmount(1, stack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.KNOCKBACK, EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, tOutput) + 1);
        RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).fluidInputs(Materials.Rubber.getFluid(18)).outputs(tOutput).duration(100).EUt(2).buildAndRegister();

        tOutput = GT_Utility.copyAmount(1, stack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, EnchantmentEnderDamage.INSTANCE, EnchantmentHelper.getEnchantmentLevel(EnchantmentEnderDamage.INSTANCE, tOutput) + 1);
        RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder().inputs(GT_Utility.copyAmount(1, stack)).fluidInputs(Materials.Mercury.getFluid(25)).outputs(tOutput).duration(100).EUt(2).buildAndRegister();

        if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
            switch (uEntry.orePrefix) {
                case arrowGtWood:
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.arrowGtWood, uEntry.material, 1L), GT_Proxy.tBits, "  A", " S ", "F  ", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 1), Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('A'), OreDictionaryUnifier.get(OrePrefix.toolHeadArrow, uEntry.material, 1));
                case arrowGtPlastic:
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.arrowGtPlastic, uEntry.material, 1L), GT_Proxy.tBits, "  A", " S ", "F  ", Character.valueOf('S'), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Plastic, 1), Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('A'), OreDictionaryUnifier.get(OrePrefix.toolHeadArrow, uEntry.material, 1));
            }
        }
    }
}
