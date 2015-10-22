package gregtech.loaders.oreprocessing;

import appeng.api.config.TunnelType;
import appeng.core.Api;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWire08 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingWire08() {
        OrePrefixes.wireGt08.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 3L), GT_OreDictUnificator.get(OrePrefixes.cableGt08, aMaterial, 1L), 300, 8);
        GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt08, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 3L), 300, 8);
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt08, aMaterial, 1L), new Object[]{aOreDictName, OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber), OrePrefixes.plate.get(Materials.Rubber)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, aMaterial, 8L), new Object[]{aOreDictName});
        GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefixes.wireGt04.get(aMaterial), OrePrefixes.wireGt04.get(aMaterial)});

        if (GT_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
            Api.INSTANCE.registries().p2pTunnel().addNewAttunement(GT_OreDictUnificator.get(OrePrefixes.cableGt08, aMaterial, 1L), TunnelType.IC2_POWER);
        }
    }
}
