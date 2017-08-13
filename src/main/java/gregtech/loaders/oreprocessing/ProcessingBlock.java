package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingBlock implements IOreRegistrationHandler {
    public ProcessingBlock() {
        OrePrefix.block.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 9L), null, (int) Math.max(aMaterial.getMass() * 10L, 1L), 30);

        ItemStack tStack1 = OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L);
        ItemStack tStack2 = OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 1L);
        ItemStack tStack3 = OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1L);

        GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, new Object[]{aStack})});

        if (tStack1 != null)
            GT_ModHandler.removeRecipe(new ItemStack[]{tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1});
        if (tStack2 != null)
            GT_ModHandler.removeRecipe(new ItemStack[]{tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2});
        if (tStack3 != null) {
            GT_ModHandler.removeRecipe(new ItemStack[]{tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3});
        }
        if (aMaterial.mStandardMoltenFluid != null) {
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L, new Object[0]), aMaterial.getMolten(1296L), OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L), 288, 8);
        }
        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, OrePrefix.block.get(aMaterial).toString(), false)) {
            if ((tStack1 == null) && (tStack2 == null) && (tStack3 != null))
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefix.dust.get(aMaterial)});
            if (tStack2 != null)
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefix.gem.get(aMaterial)});
            if (tStack1 != null) {
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefix.ingot.get(aMaterial)});
            }
        }
        if (tStack1 != null) tStack1.stackSize = 9;
        if (tStack2 != null) tStack2.stackSize = 9;
        if (tStack3 != null) {
            tStack3.stackSize = 9;
        }
        GT_Values.RA.addForgeHammerRecipe(aStack, tStack2, 100, 24);

        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockdecrafting, OrePrefix.block.get(aMaterial).toString(), tStack2 != null)) {
            if (tStack3 != null)
                GT_ModHandler.addShapelessCraftingRecipe(tStack3, new Object[]{OrePrefix.block.get(aMaterial)});
            if (tStack2 != null)
                GT_ModHandler.addShapelessCraftingRecipe(tStack2, new Object[]{OrePrefix.block.get(aMaterial)});
            if (tStack1 != null) {
                GT_ModHandler.addShapelessCraftingRecipe(tStack1, new Object[]{OrePrefix.block.get(aMaterial)});
            }
        }
        if (!OrePrefix.block.isIgnored(aMaterial))
            GT_ModHandler.addCompressionRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 9L), OreDictionaryUnifier.get(OrePrefix.block, aMaterial, 1L));
        switch (aMaterial.mName) {
            case "Mercury":
                System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
                break;
            case "Iron":
            case "WroughtIron":
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftIron.get(1L, new Object[0]), 640, 120);
                GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
                break;
            case "Steel":
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftSteel.get(1L, new Object[0]), 1280, 120);
                GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
        }
    }
}
