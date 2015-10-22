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

public class ProcessingWire12 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire12() {
        OrePrefixes.wireGt12.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Cobalt || aMaterial == Materials.Lead || aMaterial == Materials.Tin || aMaterial == Materials.Zinc || aMaterial == Materials.SolderingAlloy) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.carpet, 1, 15), new ItemStack(Blocks.carpet, 1, 15), new ItemStack(Blocks.carpet, 1, 15), new ItemStack(Blocks.carpet, 1, 15), new ItemStack(Items.string, 1)});
            GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.carpet, 4, 15), GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.carpet, 4, 15), 100, 8);
        } else if (aMaterial == Materials.RedAlloy) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), new Object[]{aOreDictName, OrePrefixes.plate.get(Materials.Paper), OrePrefixes.plate.get(Materials.Paper), OrePrefixes.plate.get(Materials.Paper), OrePrefixes.plate.get(Materials.Paper)});
            GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L), GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 4L), 100, 8);

        } else {
            GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(576L), GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), 100, 8);
            GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 4L), 100, 8);
        }
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 12L), new Object[]{aOreDictName});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefixes.wireGt08.get(aMaterial), OrePrefixes.wireGt04.get(aMaterial)});

        if (GT_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(GT_OreDictUnificator.get(OrePrefixes.cableGt12, aMaterial, 1L), TunnelType.IC2_POWER);
        }
    }
}