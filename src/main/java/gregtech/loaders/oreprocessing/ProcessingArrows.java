package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ProcessingArrows implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingArrows() {
        for (OrePrefixes tPrefix : OrePrefixes.values())
            if (tPrefix.name().startsWith("arrowGt")) {
                tPrefix.add(this);
            }
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        ItemStack tOutput = GT_Utility.copyAmount(1L, new Object[]{aStack});
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.smite, EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, tOutput) + 3);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.HolyWater.getFluid(25L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, new Object[]{aStack});
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 3);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.FierySteel.getFluid(25L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, new Object[]{aStack});
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.fireAspect, EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Blaze.getMolten(18L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, new Object[]{aStack});
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, Enchantment.knockback, EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Rubber.getMolten(18L), tOutput, null, null, null, 100, 2);

        tOutput = GT_Utility.copyAmount(1L, new Object[]{aStack});
        GT_Utility.updateItemStack(tOutput);
        GT_Utility.ItemNBT.addEnchantment(tOutput, gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE, EnchantmentHelper.getEnchantmentLevel(gregtech.api.enchants.Enchantment_EnderDamage.INSTANCE.effectId, tOutput) + 1);
        GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Mercury.getFluid(25L), tOutput, null, null, null, 100, 2);
    }
}
