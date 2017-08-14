package gregtech.loaders.oreprocessing;

import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.items.MetaTool;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingToolOther implements IOreRegistrationHandler {
    public ProcessingToolOther() {
        OrePrefix.toolHeadHammer.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if ((uEntry.material != Materials.Stone) && (uEntry.material != Materials.Flint)) {
            if (uEntry.material != Materials.Rubber) {
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.PLUNGER, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"xRR", " SR", "S f", 'S', OrePrefix.stick.get(uEntry.material), 'R', OrePrefix.plate.get(Materials.Rubber)});
            }
            if ((!uEntry.material.contains(SubTag.WOOD)) && (!uEntry.material.contains(SubTag.BOUNCY)) && (!uEntry.material.contains(SubTag.NO_SMASHING))) {
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WRENCH, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"IhI", "III", " I ", 'I', OrePrefix.ingot.get(uEntry.material)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.CROWBAR, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"hDS", "DSD", "SDf", 'S', OrePrefix.stick.get(uEntry.material), 'D', Dyes.dyeBlue});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SCREWDRIVER, 1, uEntry.material, uEntry.material.mHandleMaterial, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{" fS", " Sh", "W  ", 'S', OrePrefix.stick.get(uEntry.material), 'W', OrePrefix.stick.get(uEntry.material.mHandleMaterial)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.WIRECUTTER, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PfP", "hPd", "STS", 'S', OrePrefix.stick.get(uEntry.material), 'P', OrePrefix.plate.get(uEntry.material), 'T', OrePrefix.screw.get(uEntry.material)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SCOOP, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"SWS", "SSS", "xSh", 'S', OrePrefix.stick.get(uEntry.material), 'W', new ItemStack(Blocks.WOOL, 1, 32767)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.BRANCHCUTTER, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PfP", "PdP", "STS", 'S', OrePrefix.stick.get(uEntry.material), 'P', OrePrefix.plate.get(uEntry.material), 'T', OrePrefix.screw.get(uEntry.material)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.KNIFE, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"fPh", " S ", 'S', OrePrefix.stick.get(uEntry.material), 'P', OrePrefix.plate.get(uEntry.material)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.BUTCHERYKNIFE, 1, uEntry.material, uEntry.material, null), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"PPf", "PP ", "Sh ", 'S', OrePrefix.stick.get(uEntry.material), 'P', OrePrefix.plate.get(uEntry.material)});
                ModHandler.addCraftingRecipe(MetaTool.INSTANCE.getToolWithStats(MetaTool.SOLDERING_IRON_LV, 1, uEntry.material, Materials.Rubber, new long[]{100000L, 32L, 1L, -1L}), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"LBf", "Sd ", "P  ", 'B', OrePrefix.bolt.get(uEntry.material), 'P', OrePrefix.plate.get(Materials.Rubber), 'S', OrePrefix.stick.get(Materials.Iron), 'L', ItemList.Battery_RE_LV_Lithium.get(1)});
            }
        }
    }
}
