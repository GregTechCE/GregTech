package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;

public class ProcessingArrows implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingArrows() {
        for (OrePrefixes tPrefix : OrePrefixes.values())
            if (tPrefix.name().startsWith("arrowGt")) {
                tPrefix.add(this);
            }
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        ItemStack tOutput = GT_Utility.copyAmount(1L, aStack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.SMITE, EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, tOutput) + 3);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, aStack), Materials.HolyWater.getFluid(25L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, aStack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.FIRE_ASPECT, EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, tOutput) + 3);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, aStack), Materials.FierySteel.getFluid(25L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, aStack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.FIRE_ASPECT, EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, aStack), Materials.Blaze.getMolten(18L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, aStack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantments.KNOCKBACK, EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, aStack), Materials.Rubber.getMolten(18L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, aStack);
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE, EnchantmentHelper.getEnchantmentLevel(gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, aStack), Materials.Mercury.getFluid(25L), tOutput, null, null, null, 100, 2);
    }
}
