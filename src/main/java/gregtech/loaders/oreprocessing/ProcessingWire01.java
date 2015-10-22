package gregtech.loaders.oreprocessing;

import appeng.api.config.TunnelType;
import appeng.core.Api;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingWire01 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire01() {
        OrePrefixes.wireGt01.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Cobalt || aMaterial == Materials.Lead || aMaterial == Materials.Tin || aMaterial == Materials.Zinc || aMaterial == Materials.SolderingAlloy) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.carpet, 1, 15), new ItemStack(Items.string, 1)});
            GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.carpet, 1, 15), GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.carpet, 1, 15), 100, 8);
        } else if (aMaterial == Materials.RedAlloy) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), new Object[]{aOreDictName, OrePrefixes.plate.get(Materials.Paper)});
            GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L), 100, 8);

        } else {
            GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 1L), 100, 8);
        }

        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.springSmall, aMaterial, 2L), 100, 8);
            GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.wireFine, aMaterial, 4L), 200, 8);
        }
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt02, aMaterial, 1L), 150, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt04, aMaterial, 1L), 200, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt08, aMaterial, 1L), 300, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(12L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 12L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt12, aMaterial, 1L), 400, 8);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 16L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.wireGt16, aMaterial, 1L), 500, 8);

        if (GT_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(GT_OreDictUnificator.get(OrePrefixes.cableGt01, aMaterial, 1L), TunnelType.IC2_POWER);
        }
    }
}
