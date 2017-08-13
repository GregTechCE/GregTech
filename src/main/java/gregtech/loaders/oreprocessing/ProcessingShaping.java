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
import ic2.core.item.type.CasingResourceType;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingShaping implements IOreRegistrationHandler {
    public ProcessingShaping() {
        OrePrefix.ingot.add(this);
        OrePrefix.dust.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (((aMaterial == Materials.Glass) || (OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1L) != null)) && (!aMaterial.contains(SubTag.NO_SMELTING))) {
            long aMaterialMass = aMaterial.getMass();
            int tAmount = (int) (aPrefix.mMaterialAmount / 3628800L);
            if ((tAmount > 0) && (tAmount <= 64) && (aPrefix.mMaterialAmount % 3628800L == 0L)) {
                int tVoltageMultiplier = aMaterial.mBlastFurnaceTemp >= 2800 ? 64 : 16;

                if (aMaterial.contains(SubTag.NO_SMASHING)) {
                    tVoltageMultiplier /= 4;
                } else if (aPrefix.name().startsWith(OrePrefix.dust.name())) {
                    return;
                }

                if (!OrePrefix.block.isIgnored(aMaterial.mSmeltInto)) {
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(9L, aStack), ItemList.Shape_Extruder_Block.get(0L), OreDictionaryUnifier.get(OrePrefix.block, aMaterial.mSmeltInto, tAmount), 10 * tAmount, 8 * tVoltageMultiplier);
                    GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, aStack), ItemList.Shape_Mold_Block.get(0L), OreDictionaryUnifier.get(OrePrefix.block, aMaterial.mSmeltInto, tAmount), 5 * tAmount, 4 * tVoltageMultiplier);
                }
                if (((aPrefix != OrePrefix.ingot) || (aMaterial != aMaterial.mSmeltInto))) {
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Ingot.get(0L), OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial.mSmeltInto, tAmount), 10, 4 * tVoltageMultiplier);
                }

                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Pipe_Tiny.get(0L), OreDictionaryUnifier.get(OrePrefix.pipeTiny, aMaterial.mSmeltInto, tAmount * 2), 4 * tAmount, 8 * tVoltageMultiplier);
                if (!(aMaterial == Materials.Redstone || aMaterial == Materials.Glowstone)) {
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Pipe_Small.get(0L), OreDictionaryUnifier.get(OrePrefix.pipeSmall, aMaterial.mSmeltInto, tAmount), 8 * tAmount, 8 * tVoltageMultiplier);
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, aStack), ItemList.Shape_Extruder_Pipe_Medium.get(0L), OreDictionaryUnifier.get(OrePrefix.pipeMedium, aMaterial.mSmeltInto, tAmount), 24 * tAmount, 8 * tVoltageMultiplier);
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, aStack), ItemList.Shape_Extruder_Pipe_Large.get(0L), OreDictionaryUnifier.get(OrePrefix.pipeLarge, aMaterial.mSmeltInto, tAmount), 48 * tAmount, 8 * tVoltageMultiplier);
                }
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(12L, aStack), ItemList.Shape_Extruder_Pipe_Huge.get(0L), OreDictionaryUnifier.get(OrePrefix.pipeHuge, aMaterial.mSmeltInto, tAmount), 96 * tAmount, 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Plate.get(0L), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
                if (tAmount * 2 <= 64)
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Rod.get(0L), OreDictionaryUnifier.get(OrePrefix.stick, aMaterial.mSmeltInto, tAmount * 2), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
                if (tAmount * 2 <= 64)
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Wire.get(0L), OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial.mSmeltInto, tAmount * 2), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
                if (tAmount * 8 <= 64)
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Bolt.get(0L), OreDictionaryUnifier.get(OrePrefix.bolt, aMaterial.mSmeltInto, tAmount * 8), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
                if (tAmount * 4 <= 64) {
                    GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Ring.get(0L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial.mSmeltInto, tAmount * 4), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 6 * tVoltageMultiplier);
                    if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial) && !aMaterial.contains(SubTag.NO_SMASHING))
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"h ", " X", Character.valueOf('X'), OrePrefix.stick.get(aMaterial)});
                }
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Extruder_Sword.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadSword, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, aStack), ItemList.Shape_Extruder_Pickaxe.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadPickaxe, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Shovel.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadShovel, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 1L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(3L, aStack), ItemList.Shape_Extruder_Axe.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadAxe, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 3L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Extruder_Hoe.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadHoe, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(6L, aStack), ItemList.Shape_Extruder_Hammer.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadHammer, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 6L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Extruder_File.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadFile, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Extruder_Saw.get(0L), OreDictionaryUnifier.get(OrePrefix.toolHeadSaw, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 8 * tVoltageMultiplier);
                GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(4L, aStack), ItemList.Shape_Extruder_Gear.get(0L), OreDictionaryUnifier.get(OrePrefix.gearGt, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 5L * tAmount, tAmount), 8 * tVoltageMultiplier);

                GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Plate.get(0L), OreDictionaryUnifier.get(OrePrefix.plate, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 2L * tAmount, tAmount), 2 * tVoltageMultiplier);
                GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(8L, aStack), ItemList.Shape_Mold_Gear.get(0L), OreDictionaryUnifier.get(OrePrefix.gearGt, aMaterial.mSmeltInto, tAmount), (int) Math.max(aMaterialMass * 10L * tAmount, tAmount), 2 * tVoltageMultiplier);
                switch (aMaterial.mSmeltInto.mName) {
                    case "Glass":
                        GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Bottle.get(0L), new ItemStack(Items.GLASS_BOTTLE, 1), tAmount * 32, 16);
                        GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Mold_Bottle.get(0L), new ItemStack(Items.GLASS_BOTTLE, 1), tAmount * 64, 4);
                        break;
                    case "Steel":
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.steel, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                    case "Iron":
                    case "WroughtIron":
                        GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Cell.get(0L), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, tAmount), tAmount * 128, 32);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        if (tAmount * 31 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(31L, aStack), ItemList.Shape_Mold_Anvil.get(0L), new ItemStack(Blocks.ANVIL, 1, 0), tAmount * 512, 4 * tVoltageMultiplier);
                        break;
                    case "Tin":
                        GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Extruder_Cell.get(0L), ItemList.Cell_Empty.get(tAmount), tAmount * 128, 32);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                    case "Lead":
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.lead, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.lead, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                    case "Copper":
                    case "AnnealedCopper":
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.copper, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.copper, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                    case "Bronze":
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                    case "Gold":
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Shape_Extruder_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, tAmount * 2), tAmount * 32, 3 * tVoltageMultiplier);
                        if (tAmount * 2 <= 64)
                            GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Shape_Mold_Casing.get(0L), GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, tAmount * 3), tAmount * 128, 1 * tVoltageMultiplier);
                        break;
                }
            }
        }
    }
}
