package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingWire implements IOreRegistrationHandler {
    public ProcessingWire() {
        OrePrefix.wireGt01.addProcessingHandler(this);
        OrePrefix.wireGt02.addProcessingHandler(this);
        OrePrefix.wireGt04.addProcessingHandler(this);
        OrePrefix.wireGt08.addProcessingHandler(this);
        OrePrefix.wireGt12.addProcessingHandler(this);
        OrePrefix.wireGt16.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case wireGt01:
                switch (uEntry.material.mName){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                if (!uEntry.material.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
                    GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.springSmall, uEntry.material, 2L), 100, 8);
                    GT_Values.RA.addWiremillRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.wireFine, uEntry.material, 4L), 200, 8);
                    GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1L), GT_Utility.copy(new Object[]{GT_Utility.copyAmount(2L, stack), OreDictionaryUnifier.get(OrePrefix.wireFine, uEntry.material, 8L)}), 100, 4);
                    GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1L), GT_Utility.copy(new Object[]{stack, OreDictionaryUnifier.get(OrePrefix.wireFine, uEntry.material, 4L)}), 50, 4);
                }
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING))
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 1L), GT_Proxy.tBits, new Object[]{"Xx", Character.valueOf('X'), OrePrefix.plate.get(uEntry.material)});
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[]{stack}), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt02, uEntry.material, 1L), 150, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(4L, new Object[]{stack}), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt04, uEntry.material, 1L), 200, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{stack}), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt08, uEntry.material, 1L), 300, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(12L, new Object[]{stack}), ItemList.Circuit_Integrated.getWithDamage(0L, 12L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt12, uEntry.material, 1L), 400, 8);
                GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(16L, new Object[]{stack}), ItemList.Circuit_Integrated.getWithDamage(0L, 16L, new Object[0]), OreDictionaryUnifier.get(OrePrefix.wireGt16, uEntry.material, 1L), 500, 8);

//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt01, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt02:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 1, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 1L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 2L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt01.get(uEntry.material), OrePrefix.wireGt01.get(uEntry.material)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt02, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt04:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 2, 15), OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 2, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 2L), OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 2L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(288L), OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 2L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 4L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt02.get(uEntry.material), OrePrefix.wireGt02.get(uEntry.material)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt04, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt08:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 3, 15), OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 3, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 3L), OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 3L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(432L), OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 3L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 8L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt04.get(uEntry.material), OrePrefix.wireGt04.get(uEntry.material)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt08, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt12:
                switch (uEntry.material.toString()){
                    case "Cobalt": case "Lead": case "Tin": case "Zinc":case "SolderingAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), new Object[]{aOreDictName, new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Blocks.CARPET, 1, 15), new ItemStack(Items.STRING, 1)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 4, 15), OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), new ItemStack(Blocks.CARPET, 4, 15), 100, 8);
                        break;
                    case "RedAlloy":
                        ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), new Object[]{aOreDictName, OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper), OrePrefix.plate.get(Materials.Paper)});
                        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 4L), OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 4L), 100, 8);
                        break;
                    default:
                        GT_Values.RA.addAssemblerRecipe(stack, ItemList.Circuit_Integrated.getWithDamage(0L, 24L, new Object[0]), Materials.Rubber.getMolten(576L), OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), 100, 8);
                        GT_Values.RA.addUnboxingRecipe(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Rubber, 4L), 100, 8);
                }
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 12L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt08.get(uEntry.material), OrePrefix.wireGt04.get(uEntry.material)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(OreDictionaryUnifier.get(OrePrefix.cableGt12, uEntry.material, 1L), TunnelType.IC2_POWER);
//                }
                break;
            case wireGt16:
                ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, uEntry.material, 16L), new Object[]{aOreDictName});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt08.get(uEntry.material), OrePrefix.wireGt08.get(uEntry.material)});
                ModHandler.addShapelessCraftingRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), new Object[]{OrePrefix.wireGt12.get(uEntry.material), OrePrefix.wireGt04.get(uEntry.material)});
//                TODO
//                if (GT_Mod.gregtechproxy.mAE2Integration) {
//                    Api.INSTANCE.registries().p2pTunnel().addNewAttunement(stack, TunnelType.IC2_POWER);
//                }
                break;
        }
    }
}
