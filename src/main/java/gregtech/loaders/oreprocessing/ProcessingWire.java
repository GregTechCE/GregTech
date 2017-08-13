package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingWire implements IOreRegistrationHandler {
    public ProcessingWire() {
        OrePrefix.wireGt01.add(this);
        OrePrefix.wireGt02.add(this);
        OrePrefix.wireGt04.add(this);
        OrePrefix.wireGt08.add(this);
        OrePrefix.wireGt12.add(this);
        OrePrefix.wireGt16.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aPrefix) {
            case wireGt01:
                switch (aMaterial.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 1, 15), OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.springSmall, aMaterial, 2L), 100, 8);
                    GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.wireFine, aMaterial, 4L), 200, 8);
                    GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L), GT_Utility.copy(new Object[]{GT_Utility.copyAmount(2L, aStack), OreDictionaryUnifier.get(OrePrefix.wireFine, aMaterial, 8L)}), 100, 4);
                    GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1L), GT_Utility.copy(new Object[]{aStack, OreDictionaryUnifier.get(OrePrefix.wireFine, aMaterial, 4L)}), 50, 4);
                }
                if (aMaterial.mUnificatable && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_WORKING))
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"Xx", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt02, aMaterial, 1L), 150, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt04, aMaterial, 1L), 200, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt08, aMaterial, 1L), 300, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(12L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 12L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt12, aMaterial, 1L), 400, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(16L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 16L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt16, aMaterial, 1L), 500, 8);

//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt01, aMaterial, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt02:
                switch (aMaterial.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 1, 15), OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 2L), new Object[]{aOreDictName});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt01.get(aMaterial), OrePrefix.wireGt01.get(aMaterial)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt02, aMaterial, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt04:
                switch (aMaterial.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 2, 15), OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 2, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 2L), OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 2L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(288L), OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 2L), 100, 8);
                }
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 4L), new Object[]{aOreDictName});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt02.get(aMaterial), OrePrefix.wireGt02.get(aMaterial)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt04, aMaterial, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt08:
                switch (aMaterial.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 3, 15), OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 3, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 3L), OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 3L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(432L), OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 3L), 100, 8);
                }
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 8L), new Object[]{aOreDictName});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt04.get(aMaterial), OrePrefix.wireGt04.get(aMaterial)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt08, aMaterial, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt12:
                switch (aMaterial.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 4, 15), OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.CARPET, 4, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 4L), OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 4L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(aStack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(576L), OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 4L), 100, 8);
                }
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 12L), new Object[]{aOreDictName});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt08.get(aMaterial), OrePrefix.wireGt04.get(aMaterial)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt12, aMaterial, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt16:
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 16L), new Object[]{aOreDictName});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt08.get(aMaterial), OrePrefix.wireGt08.get(aMaterial)});
                GT_ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), new Object[]{OrePrefix.wireGt12.get(aMaterial), OrePrefix.wireGt04.get(aMaterial)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(aStack, TunnelType.IC2_POWER);
//                }
                break;
        }
    }
}
