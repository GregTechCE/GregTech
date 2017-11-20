package gregtech.loaders.preload;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;

public class ItemBlockFluidLoader implements Runnable {

    public void run() {
//        Materials.Water.mFluid = (Materials.Ice.mFluid = GT_ModHandler.getWater(1000L).getFluid());
//        Materials.Lava.mFluid = GT_ModHandler.getLava(1000L).getFluid();


//
//        Item tItem = (Item) GTUtility.callConstructor("gregtech.common.items.GT_SensorCard_Item", 0, null, false, "sensorcard", "GregTech Sensor Card");
//        ItemList.NC_SensorCard.set(tItem == null ? new GenericItem("sensorcard", "GregTech Sensor Card", "Nuclear Control not installed", false) : tItem);
//
//        ItemList.Neutron_Reflector.set(new GT_NeutronReflector_Item("neutronreflector", "Iridium Neutron Reflector", 0));
//        GT_ModHandler.addCraftingRecipe(ItemList.Neutron_Reflector.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RRR", "RPR", "RRR", 'R', GT_ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1), 'P', OrePrefix.plateAlloy.get(Materials.Iridium)});
//        GT_ModHandler.addCraftingRecipe(ItemList.Neutron_Reflector.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RBR", "RPR", "RBR", 'R', GT_ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1), 'P', OrePrefix.plateAlloy.get(Materials.Iridium),'B', OrePrefix.plate.get(Materials.TungstenCarbide)});
//
//        ItemList.Reactor_Coolant_He_1.set(GregTechAPI.constructCoolantCellItem("60k_Helium_Coolantcell", "60k He Coolant Cell", 60000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_1.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" P ", "PCP", " P ", 'C', OrePrefix.cell.get(Materials.Helium), 'P', OrePrefix.plate.get(Materials.Tin)});
//
//        ItemList.Reactor_Coolant_He_3.set(GregTechAPI.constructCoolantCellItem("180k_Helium_Coolantcell", "180k He Coolant Cell", 180000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_3.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PCP", "PCP", 'C', ItemList.Reactor_Coolant_He_1, 'P', OrePrefix.plate.get(Materials.Tin)});
//
//        ItemList.Reactor_Coolant_He_6.set(GregTechAPI.constructCoolantCellItem("360k_Helium_Coolantcell", "360k He Coolant Cell", 360000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_6.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PDP", "PCP", 'C', ItemList.Reactor_Coolant_He_3, 'P', OrePrefix.plate.get(Materials.Tin), 'D', OrePrefix.plateDense.get(Materials.Copper)});
//
//        ItemList.Reactor_Coolant_NaK_1.set(GregTechAPI.constructCoolantCellItem("60k_NaK_Coolantcell", "60k NaK Coolantcell", 60000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_1.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TST", "PCP", "TST", 'C', GT_ModHandler.getIC2Item(ItemName.heat_vent, 1), 'T', OrePrefix.plate.get(Materials.Tin), 'S', OrePrefix.dust.get(Materials.Sodium), 'P', OrePrefix.dust.get(Materials.Potassium)});
//
//        ItemList.Reactor_Coolant_NaK_3.set(GregTechAPI.constructCoolantCellItem("180k_NaK_Coolantcell", "180k NaK Coolantcell", 180000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_3.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PCP", "PCP", 'C', ItemList.Reactor_Coolant_NaK_1, 'P', OrePrefix.plate.get(Materials.Tin)});
//
//        ItemList.Reactor_Coolant_NaK_6.set(GregTechAPI.constructCoolantCellItem("360k_NaK_Coolantcell", "360k NaK Coolantcell", 360000));
//        GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_6.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PDP", "PCP", 'C', ItemList.Reactor_Coolant_NaK_3, 'P', OrePrefix.plate.get(Materials.Tin), 'D', OrePrefix.plateDense.get(Materials.Copper)});
//
//        if (!GregTechAPI.mIC2Classic) {
//            ItemList.Depleted_Thorium_1.set(new GT_DepletetCell_Item("ThoriumcellDep", "Fuel Rod (Depleted Thorium)", 1));
//            ItemList.Depleted_Thorium_2.set(new GT_DepletetCell_Item("Double_ThoriumcellDep", "Dual Fuel Rod (Depleted Thorium)", 1));
//            ItemList.Depleted_Thorium_4.set(new GT_DepletetCell_Item("Quad_ThoriumcellDep", "Quad Fuel Rod (Depleted Thorium)", 1));
//
//            ItemList.ThoriumCell_1.set(new GT_RadioactiveCellIC_Item("Thoriumcell", "Fuel Rod (Thorium)", 1, 50000, 0.4F, 0, 0.25F, ItemList.Depleted_Thorium_1.get(1), false));
//
//            ItemList.ThoriumCell_2.set(new GT_RadioactiveCellIC_Item("Double_Thoriumcell", "Double Fuel Rod (Thorium)", 2, 50000, 0.4F, 0, 0.25F, ItemList.Depleted_Thorium_2.get(1), false));
//            GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_2.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.ThoriumCell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
//
//            ItemList.ThoriumCell_4.set(new GT_RadioactiveCellIC_Item("Quad_Thoriumcell", "Quad Fuel Rod (Thorium)", 4, 50000, 0.4F, 0, 0.25F, ItemList.Depleted_Thorium_4.get(1), false));
//            GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_4.get(1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.ThoriumCell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});
//
//            ItemList.Depleted_Naquadah_1.set(new GT_DepletetCell_Item("NaquadahcellDep", "Fuel Rod (Depleted Naquadah)", 1));
//            ItemList.Depleted_Naquadah_2.set(new GT_DepletetCell_Item("Double_NaquadahcellDep", "Dual Fuel Rod (Depleted Naquadah)", 1));
//            ItemList.Depleted_Naquadah_4.set(new GT_DepletetCell_Item("Quad_NaquadahcellDep", "Quad Fuel Rod (Depleted Naquadah)", 1));
//
//            ItemList.NaquadahCell_1.set(new GT_RadioactiveCellIC_Item("Naquadahcell", "Fuel Rod (Naquadah)", 1, 100000, 2F, 1, 1F, ItemList.Depleted_Naquadah_1.get(1, new Object[0]),true));
//
//            ItemList.NaquadahCell_2.set(new GT_RadioactiveCellIC_Item("Double_Naquadahcell", "Double Fuel Rod (Naquadah)", 2, 100000, 2F, 1, 1F, ItemList.Depleted_Naquadah_2.get(1, new Object[0]),true));
//            GT_ModHandler.addCraftingRecipe(ItemList.NaquadahCell_2.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.NaquadahCell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
//
//            ItemList.NaquadahCell_4.set(new GT_RadioactiveCellIC_Item("Quad_Naquadahcell", "Quad Fuel Rod (Naquadah)", 4, 100000, 2F, 1, 1F, ItemList.Depleted_Naquadah_4.get(1, new Object[0]),true));
//            GT_ModHandler.addCraftingRecipe(ItemList.NaquadahCell_4.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.NaquadahCell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});
//
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Naquadah_1.get(1, new Object[0]), 5000, new Object[]{OreDictUnifier.get(OrePrefix.dust, Materials.Naquadah, 1L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Naquadria, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1L)});
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Naquadah_2.get(1, new Object[0]), 5000, new Object[]{OreDictUnifier.get(OrePrefix.dust, Materials.Naquadah, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Naquadria, 1L), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 3L)});
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Naquadah_4.get(1, new Object[0]), 5000, new Object[]{OreDictUnifier.get(OrePrefix.dust, Materials.Naquadah, 4L), OreDictUnifier.get(OrePrefix.dust, Materials.Naquadria, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 6L)});
//
//            ItemList.Uraniumcell_1.set(new GT_RadioactiveCellIC_Item("reactorUraniumSimple", "Fuel Rod (Uranium)", 1, 20000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1), false));
//            ItemList.Uraniumcell_2.set(new GT_RadioactiveCellIC_Item("reactorUraniumDual", "Dual Fuel Rod (Uranium)", 2, 20000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.dual_uranium_fuel_rod, 1), false));
//            ItemList.Uraniumcell_4.set(new GT_RadioactiveCellIC_Item("reactorUraniumQuad", "Quad Fuel Rod (Uranium)", 4, 20000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.quad_uranium_fuel_rod, 1), false));
//            ItemList.Moxcell_1.set(new GT_RadioactiveCellIC_Item("reactorMOXSimple", "Fuel Rod (Mox)", 1, 10000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.mox_fuel_rod, 1), true));
//            ItemList.Moxcell_2.set(new GT_RadioactiveCellIC_Item("reactorMOXDual", "Dual Fuel Rod (Mox)", 2, 10000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.dual_mox_fuel_rod, 1), true));
//            ItemList.Moxcell_4.set(new GT_RadioactiveCellIC_Item("reactorMOXQuad", "Quad Fuel Rod (Mox)", 4, 10000, 2F, 1, 1F, GT_ModHandler.getIC2Item(ItemName.quad_mox_fuel_rod, 1), true));
//
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_1.get(1), 5000, OreDictUnifier.get(OrePrefix.dustSmall, Materials.Lutetium, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1));
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_2.get(1), 5000, OreDictUnifier.get(OrePrefix.dust, Materials.Lutetium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 3L));
//            GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_4.get(1), 5000, OreDictUnifier.get(OrePrefix.dust, Materials.Lutetium, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 4L), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 6L));
//        }
//
//
//        if (!GregTechAPI.mIC2Classic) {
//            GTLog.logger.info("GregTechMod: Registering Fluids.");
//            Materials.ConstructionFoam.mFluid = FluidName.construction_foam.getInstance();
//            Materials.UUMatter.mFluid = FluidName.uu_matter.getInstance();
//        }
//
//
//        //manually register fluid containers for vanilla liquids
//        GregTechMod.gregtechproxy.registerFluidContainer(ItemList.Cell_Water.get(1), ItemList.Cell_Empty.get(1), FluidRegistry.WATER, 1000);
//        GregTechMod.gregtechproxy.registerFluidContainer(ItemList.Cell_Lava.get(1), ItemList.Cell_Empty.get(1), FluidRegistry.LAVA, 1000);
//
//        G
//
//        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 32767), new ItemStack(Blocks.SAND, 1), null, 0, false);
//        //GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.stone, 1, 32767), new ItemStack(Blocks.cobblestone, 1), null, 0, false);
//        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.GRAVEL, 1, 32767), new ItemStack(Items.FLINT, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Flint, 1), 10, false);
//        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.FURNACE, 1, 32767), new ItemStack(Blocks.SAND, 6), null, 0, false);
//
//
//        if (GregTechAPI.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateIron", true)) {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Iron, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 0));
//        } else {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Iron, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 0), false, false);
//        }
//
//        if (GregTechAPI.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateSteel", true)) {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Steel, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 1));
//        } else {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Steel, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 1), false, false);
//        }
//
//        if (GregTechAPI.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateTinAlloy", true)) {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.TinAlloy, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 2));
//        } else {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.TinAlloy, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 2), false, false);
//        }
//
//        if (GregTechAPI.sUnification.get(ConfigCategories.specialunificationtargets + "." + "railcraft", "plateCopper", true)) {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Copper, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 3));
//        } else {
//            OreDictionaryUnifier.set(OrePrefix.plate, Materials.Copper, GT_ModHandler.getModItem("Railcraft", "part.plate", 1, 3), false, false);
//        }
//
//        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Cocoa, GT_ModHandler.getModItem("harvestcraft", "cocoapowderItem", 1, 0));
//        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Coffee, ItemList.IC2_CoffeePowder.get(1));
//
//        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Naquadah.getMolten(1000L), OreDictUnifier.get(OrePrefix.cell, Materials.Naquadah, 1L), OreDictUnifier.get(OrePrefix.cell, Materials.Empty, 1L)));
//        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.NaquadahEnriched.getMolten(1000L), OreDictUnifier.get(OrePrefix.cell, Materials.NaquadahEnriched, 1L), OreDictUnifier.get(OrePrefix.cell, Materials.Empty, 1L)));
//        FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Naquadria.getMolten(1000L), OreDictUnifier.get(OrePrefix.cell, Materials.Naquadria, 1L), OreDictUnifier.get(OrePrefix.cell, Materials.Empty, 1L)));
    }
}