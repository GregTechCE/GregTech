package gregtech.api.enums;

import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.interfaces.ICondition;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static gregtech.api.enums.GT_Values.*;

public enum OrePrefixes {
    @Deprecated pulp("Pulps", "", "", false, false, false, false, false, false, false, false, false, false, B[0] | B[1] | B[2] | B[3], -1, 64, -1),
    @Deprecated leaves("Leaves", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    @Deprecated sapling("Saplings", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    @Deprecated itemDust("Dusts", "", "", false, false, false, false, false, false, false, false, false, false, B[0] | B[1] | B[2] | B[3], -1, 64, -1),
    oreBlackgranite("Black Granite Ores", "Granite ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreRedgranite("Red Granite Ores", "Granite ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreMarble("Marble Ores", "Marble ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreBasalt("Basalt Ores", "Basalt ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreNetherrack("Netherrack Ores", "Nether ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreNether("Nether Ores", "Nether ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    @Deprecated denseore("Dense Ores", "", "", false, false, false, false, false, true, false, false, false, true, B[3], -1, 64, -1),
    oreDense("Dense Ores", "Dense ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Dense-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreRich("Rich Ores", "Rich ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of TFC
    oreNormal("Normal Ores", "Normal ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of TFC
    oreSmall("Small Ores", "Small ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, 67), // Prefix of Railcraft.
    orePoor("Poor Ores", "Poor ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of Railcraft.
    oreEndstone("Endstone Ores", "End ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreEnd("End Ores", "End ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    @Deprecated oreGem("Ores", "", "", false, false, false, false, false, true, false, false, false, true, B[3], -1, 64, -1),
    ore("Ores", "", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, 68), // Regular Ore Prefix. Ore -> Material is a Oneway Operation! Introduced by Eloraam
    crushedCentrifuged("Centrifuged Ores", "Centrifuged ", " Ore", true, true, false, false, false, false, false, true, false, true, B[3], -1, 64, 7),
    crushedPurified("Purified Ores", "Purified ", " Ore", true, true, false, false, false, false, false, true, false, true, B[3], -1, 64, 6),
    crushed("Crushed Ores", "Crushed ", " Ore", true, true, false, false, false, false, false, true, false, true, B[3], -1, 64, 5),
    shard("Crystallised Shards", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1), // Introduced by Mekanism
    clump("Clumps", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1),
    reduced("Reduced Gravels", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1),
    crystalline("Crystallised Metals", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1),
    cleanGravel("Clean Gravels", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1),
    dirtyGravel("Dirty Gravels", "", "", true, true, false, false, false, false, false, false, false, true, B[3], -1, 64, -1),
    ingotQuintuple("5x Ingots", "Quintuple ", " Ingot", true, true, false, false, false, false, true, true, false, false, B[1], M * 5, 12, 16), // A quintuple Ingot.
    ingotQuadruple("4x Ingots", "Quadruple ", " Ingot", true, true, false, false, false, false, true, true, false, false, B[1], M * 4, 16, 15), // A quadruple Ingot.
    @Deprecated ingotQuad("4x Ingots", "Quadruple ", " Ingot", false, false, false, false, false, false, false, false, false, false, B[1], -1, 16, 15),
    ingotTriple("3x Ingots", "Triple ", " Ingot", true, true, false, false, false, false, true, false, false, false, B[1], M * 3, 21, 14), // A triple Ingot.
    ingotDouble("2x Ingots", "Double ", " Ingot", true, true, false, false, false, false, true, true, false, false, B[1], M * 2, 32, 13), // A double Ingot. Introduced by TerraFirmaCraft
    ingotHot("Hot Ingots", "Hot ", " Ingot", true, true, false, false, false, false, false, true, false, false, B[1], M * 1, 16, 12), // A hot Ingot, which has to be cooled down by a Vacuum Freezer.
    ingot("Ingots", "", " Ingot", true, true, false, false, false, false, false, true, false, false, B[1], M * 1, 64, 11), // A regular Ingot. Introduced by Eloraam
    gemChipped("Chipped Gemstones", "Chipped ", "", true, true, true, false, false, false, true, true, false, false, B[2], M / 4, 64, 59), // A regular Gem worth one small Dust. Introduced by TerraFirmaCraft
    gemFlawed("Flawed Gemstones", "Flawed ", "", true, true, true, false, false, false, true, true, false, false, B[2], M / 2, 64, 60), // A regular Gem worth two small Dusts. Introduced by TerraFirmaCraft
    gemFlawless("Flawless Gemstones", "Flawless ", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 2, 32, 61), // A regular Gem worth two Dusts. Introduced by TerraFirmaCraft
    gemExquisite("Exquisite Gemstones", "Exquisite ", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 4, 16, 62), // A regular Gem worth four Dusts. Introduced by TerraFirmaCraft
    gem("Gemstones", "", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 1, 64, 8), // A regular Gem worth one Dust. Introduced by Eloraam
    @Deprecated dustDirty("Impure Dusts", "", "", false, false, false, false, false, false, false, false, false, true, B[3], -1, 64, 3),
    dustTiny("Tiny Dusts", "Tiny Pile of ", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M / 9, 64, 0), // 1/9th of a Dust.
    dustSmall("Small Dusts", "Small Pile of ", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M / 4, 64, 1), // 1/4th of a Dust.
    dustImpure("Impure Dusts", "Impure Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 3), // Dust with impurities. 1 Unit of Main Material and 1/9 - 1/4 Unit of secondary Material
    dustRefined("Refined Dusts", "Refined Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 2),
    dustPure("Purified Dusts", "Purified Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 4),
    dust("Dusts", "", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M * 1, 64, 2), // Pure Dust worth of one Ingot or Gem. Introduced by Alblaka.
    nugget("Nuggets", "", " Nugget", true, true, false, false, false, false, false, true, false, false, B[1], M / 9, 64, 9), // A Nugget. Introduced by Eloraam
    plateAlloy("Alloy Plates", "", "", true, false, false, false, false, false, false, false, false, false, B[1], -1, 64, 17), // Special Alloys have this prefix.
    plateSteamcraft("Steamcraft Plates", "", "", false, false, false, false, false, false, false, false, false, false, B[1], -1, 64, 17),
    plateDense("Dense Plates", "Dense ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 9, 8, 22), // 9 Plates combined in one Item.
    plateQuintuple("5x Plates", "Quintuple ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 5, 12, 21),
    plateQuadruple("4x Plates", "Quadruple ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 4, 16, 20),
    @Deprecated plateQuad("4x Plates", "", "", false, false, false, false, false, false, false, false, false, false, B[1], -1, 16, 20),
    plateTriple("3x Plates", "Triple ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 3, 21, 19),
    plateDouble("2x Plates", "Double ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 2, 32, 18),
    plate("Plates", "", " Plate", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M * 1, 64, 17), // Regular Plate made of one Ingot/Dust. Introduced by Calclavia
    foil("Foils", "", " Foil", true, true, false, false, false, false, true, true, false, false, B[1], M / 4, 64, 29), // Foil made of 1/4 Ingot/Dust.
    stickLong("Long Sticks/Rods", "Long ", " Rod", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M * 1, 64, 54), // Stick made of an Ingot.
    stick("Sticks/Rods", "", " Rod", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M / 2, 64, 23), // Stick made of half an Ingot. Introduced by Eloraam
    round("Rounds", "", " Round", true, true, false, false, false, false, true, true, false, false, B[1], M / 9, 64, 25), // consisting out of one Nugget.
    bolt("Bolts", "", " Bolt", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M / 8, 64, 26), // consisting out of 1/8 Ingot or 1/4 Stick.
    comb("Combs", "", " Comb", false, false, false, false, false, false, false, true, false, false, B[1] | B[2], M, 64, 101), // contain dusts
    screw("Screws", "", " Screw", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M / 9, 64, 27), // consisting out of a Bolt.
    ring("Rings", "", " Ring", true, true, false, false, false, false, true, true, false, false, B[1], M / 4, 64, 28), // consisting out of 1/2 Stick.
    springSmall("Small Springs", "Small ", " Spring", true, true, false, false, false, false, true, true, false, false, B[1], M / 4, 64, 55), // consisting out of 1 Fine Wire.
    spring("Springs", "", " Spring", true, true, false, false, false, false, true, true, false, false, B[1], M * 1, 64, 56), // consisting out of 2 Sticks.
    wireFine("Fine Wires", "Fine ", " Wire", true, true, false, false, false, false, true, true, false, false, B[1], M / 8, 64, 51), // consisting out of 1/8 Ingot or 1/4 Wire.
    rotor("Rotors", "", " Rotor", true, true, false, false, false, false, true, true, false, false, B[7], M * 4 + M / 4, 16, 53), // consisting out of 4 Plates, 1 Ring and 1 Screw.
    gearGtSmall("Small Gears", "Small ", " Gear", true, true, false, false, false, false, true, true, false, false, B[7], M * 1, 64, 52),
    gearGt("Gears", "", " Gear", true, true, false, false, false, false, true, true, false, false, B[7], M * 4, 16, 63), // Introduced by me because BuildCraft has ruined the gear Prefix...
    lens("Lenses", "", " Lens", true, true, false, false, false, false, true, true, false, false, B[2], (M * 3) / 4, 64, 24), // 3/4 of a Plate or Gem used to shape a Lense. Normally only used on Transparent Materials.
    crateGtDust("Crates of Dust", "Crate of ", " Dust", true, true, false, true, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], -1, 64, 96), // consisting out of 16 Dusts.
    crateGtPlate("Crates of Plates", "Crate of ", " Plate", true, true, false, true, false, false, false, true, false, false, B[1] | B[2], -1, 64, 99), // consisting out of 16 Plates.
    crateGtIngot("Crates of Ingots", "Crate of ", " Ingot", true, true, false, true, false, false, false, true, false, false, B[1], -1, 64, 97), // consisting out of 16 Ingots.
    crateGtGem("Crates of Gems", "Crate of ", " Gem", true, true, false, true, false, false, false, true, false, false, B[2], -1, 64, 98), // consisting out of 16 Gems.
    cellPlasma("Cells of Plasma", "", " Plasma Cell", true, true, true, true, false, false, false, true, false, false, B[5], M * 1, 64, 31), // Hot Cell full of Plasma, which can be used in the Plasma Generator.
    cell("Cells", "", " Cell", true, true, true, true, false, false, true, true, false, false, B[4] | B[8], M * 1, 64, 30), // Regular Gas/Fluid Cell. Introduced by Calclavia
    bucket("Buckets", "", " Bucket", true, true, true, true, false, false, true, false, false, false, B[4] | B[8], M * 1, 16, -1), // A vanilla Iron Bucket filled with the Material.
    bottle("Bottles", "", " Bottle", true, true, true, true, false, false, false, false, false, false, B[4] | B[8], -1, 16, -1), // Glass Bottle containing a Fluid.
    capsule("Capsules", "", " Capsule", false, true, true, true, false, false, false, false, false, false, B[4] | B[8], M * 1, 16, -1),
    crystal("Crystals", "", " Crystal", false, true, false, false, false, false, true, false, false, false, B[2], M * 1, 64, -1),
    bulletGtSmall("Small Bullets", "Small ", " Bullet", true, true, false, false, true, false, true, false, true, false, B[6] | B[8], M / 9, 64, -1),
    bulletGtMedium("Medium Bullets", "Medium ", " Bullet", true, true, false, false, true, false, true, false, true, false, B[6] | B[8], M / 6, 64, -1),
    bulletGtLarge("Large Bullets", "Large ", " Bullet", true, true, false, false, true, false, true, false, true, false, B[6] | B[8], M / 3, 64, -1),
    arrowGtWood("Regular Arrows", "", " Arrow", true, true, false, false, true, false, true, false, true, false, B[6], M / 4, 64, 57), // Arrow made of 1/4 Ingot/Dust + Wooden Stick.
    arrowGtPlastic("Light Arrows", "Light ", " Arrow", true, true, false, false, true, false, true, false, true, false, B[6], M / 4, 64, 58), // Arrow made of 1/4 Ingot/Dust + Plastic Stick.
    arrow("Arrows", "", "", false, false, true, false, false, false, false, false, true, false, B[6], -1, 64, 57),
    toolHeadArrow("Arrow Heads", "", " Arrow Head", true, true, false, false, false, false, true, true, false, false, B[6], M / 4, 64, 46), // consisting out of 1/4 Ingot.
    toolHeadSword("Sword Blades", "", " Sword Blade", true, true, false, false, false, false, true, true, false, false, B[6], M * 2, 16, 32), // consisting out of 2 Ingots.
    toolHeadPickaxe("Pickaxe Heads", "", " Pickaxe Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 3, 16, 33), // consisting out of 3 Ingots.
    toolHeadShovel("Shovel Heads", "", " Shovel Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 1, 16, 34), // consisting out of 1 Ingots.
    toolHeadUniversalSpade("Universal Spade Heads", "", " Universal Spade Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 1, 16, 43), // consisting out of 1 Ingots.
    toolHeadAxe("Axe Heads", "", " Axe Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 3, 16, 35), // consisting out of 3 Ingots.
    toolHeadHoe("Hoe Heads", "", " Hoe Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 2, 16, 36), // consisting out of 2 Ingots.
    toolHeadSense("Sense Blades", "", " Sense Blade", true, true, false, false, false, false, true, true, false, false, B[6], M * 3, 16, 44), // consisting out of 3 Ingots.
    toolHeadFile("File Heads", "", " File Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 2, 16, 38), // consisting out of 2 Ingots.
    toolHeadHammer("Hammer Heads", "", " Hammer Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 6, 16, 37), // consisting out of 6 Ingots.
    toolHeadPlow("Plow Heads", "", " Plow Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 4, 16, 45), // consisting out of 4 Ingots.
    toolHeadSaw("Saw Blades", "", " Saw Blade", true, true, false, false, false, false, true, true, false, false, B[6], M * 2, 16, 39), // consisting out of 2 Ingots.
    toolHeadBuzzSaw("Buzzsaw Blades", "", " Buzzsaw Blade", true, true, false, false, false, false, true, true, false, false, B[6], M * 4, 16, 48), // consisting out of 4 Ingots.
    toolHeadScrewdriver("Screwdriver Tips", "", " Screwdriver Tip", true, true, false, false, false, false, true, false, false, false, B[6], M * 1, 16, 47), // consisting out of 1 Ingots.
    toolHeadDrill("Drill Tips", "", " Drill Tip", true, true, false, false, false, false, true, true, false, false, B[6], M * 4, 16, 40), // consisting out of 4 Ingots.
    toolHeadChainsaw("Chainsaw Tips", "", " Chainsaw Tip", true, true, false, false, false, false, true, true, false, false, B[6], M * 2, 16, 41), // consisting out of 2 Ingots.
    toolHeadWrench("Wrench Tips", "", " Wrench Tip", true, true, false, false, false, false, true, true, false, false, B[6], M * 4, 16, 42), // consisting out of 4 Ingots.
    turbineBlade("Turbine Blades", "", " Turbine Blade", true, true, false, false, false, false, true, true, false, false, B[6], M * 6, 64, 100), // consisting out of 6 Ingots.
    toolSword("Swords", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 2, 1, -1), // vanilly Sword
    toolPickaxe("Pickaxes", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 3, 1, -1), // vanilly Pickaxe
    toolShovel("Shovels", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 1, 1, -1), // vanilly Shovel
    toolAxe("Axes", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 3, 1, -1), // vanilly Axe
    toolHoe("Hoes", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 2, 1, -1), // vanilly Hoe
    toolShears("Shears", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 2, 1, -1), // vanilly Shears
    tool("Tools", "", "", false, false, false, false, false, false, false, false, true, false, B[6], -1, 1, -1), // toolPot, toolSkillet, toolSaucepan, toolBakeware, toolCuttingboard, toolMortarandpestle, toolMixingbowl, toolJuicer
    compressedCobblestone("9^X Compressed Cobblestones", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    compressedStone("9^X Compressed Stones", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    compressedDirt("9^X Compressed Dirt", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    compressedGravel("9^X Compressed Gravel", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    compressedSand("9^X Compressed Sand", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    compressed("Compressed Materials", "Compressed ", "", true, true, false, false, false, false, true, false, false, false, 0, M * 2, 64, -1), // Compressed Material, worth 1 Unit. Introduced by Galacticraft
    glass("Glasses", "", "", false, false, true, false, true, false, false, false, false, false, 0, -1, 64, -1),
    paneGlass("Glass Panes", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    blockGlass("Glass Blocks", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    blockWool("Wool Blocks", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    block_("Random Blocks", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1), // IGNORE
    block("Storage Blocks", "Block of ", "", true, true, false, false, false, true, true, false, false, false, 0, M * 9, 64, 71), // Storage Block consisting out of 9 Ingots/Gems/Dusts. Introduced by CovertJaguar
    craftingTool("Crafting Tools", "", "", false, false, false, false, false, false, false, false, true, false, 0, -1, 64, -1), // Special Prefix used mainly for the Crafting Handler.
    crafting("Crafting Ingredients", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Special Prefix used mainly for the Crafting Handler.
    craft("Crafting Stuff?", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Special Prefix used mainly for the Crafting Handler.
    log("Logs", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix used for Logs. Usually as "logWood". Introduced by Eloraam
    slab("Slabs", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix used for Slabs. Usually as "slabWood" or "slabStone". Introduced by SirSengir
    stair("Stairs", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix used for Stairs. Usually as "stairWood" or "stairStone". Introduced by SirSengir
    fence("Fences", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Prefix used for Fences. Usually as "fenceWood". Introduced by Forge
    plank("Planks", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix for Planks. Usually "plankWood". Introduced by Eloraam
    treeSapling("Saplings", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix for Saplings.
    treeLeaves("Leaves", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Prefix for Leaves.
    tree("Tree Parts", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Prefix for Tree Parts.
    stoneCobble("Cobblestones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Cobblestone Prefix for all Cobblestones.
    stoneSmooth("Smoothstones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Smoothstone Prefix.
    stoneMossyBricks("mossy Stone Bricks", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Mossy Stone Bricks.
    stoneMossy("Mossy Stones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Mossy Cobble.
    @Deprecated stoneBricksMossy("Mossy Stone Bricks", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1),
    stoneBricks("Stone Bricks", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Stone Bricks.
    @Deprecated stoneBrick("Stone Bricks", "", "", false, false, false, false, false, true, false, false, false, false, 0, -1, 64, -1),
    stoneCracked("Cracked Stones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Cracked Bricks.
    stoneChiseled("Chiseled Stones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Chiseled Stone.
    stone("Stones", "", "", false, true, true, false, true, true, false, false, false, false, 0, -1, 64, -1), // Prefix to determine which kind of Rock this is.
    cobblestone("Cobblestones", "", "", false, true, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    rock("Rocks", "", "", false, true, true, false, true, true, false, false, false, false, 0, -1, 64, -1), // Prefix to determine which kind of Rock this is.
    record("Records", "", "", false, false, true, false, false, false, false, false, false, false, 0, -1, 1, -1),
    rubble("Rubbles", "", "", true, true, true, false, false, false, false, false, false, false, 0, -1, 64, -1),
    scraps("Scraps", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    scrap("Scraps", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    item_("Items", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // IGNORE
    item("Items", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Random Item. Introduced by Alblaka
    book("Books", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Used for Books of any kind.
    paper("Papers", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Used for Papers of any kind.
    dye("Dyes", "", "", false, false, true, false, false, false, false, false, false, false, 0, -1, 64, -1), // Used for the 16 dyes. Introduced by Eloraam
    stainedClay("Stained Clays", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Used for the 16 colors of Stained Clay. Introduced by Forge
    armorHelmet("Helmets", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 5, 1, -1), // vanilly Helmet
    armorChestplate("Chestplates", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 8, 1, -1), // vanilly Chestplate
    armorLeggings("Leggings", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 7, 1, -1), // vanilly Pants
    armorBoots("Boots", "", "", false, true, false, false, false, false, true, false, true, false, B[6], M * 4, 1, -1), // vanilly Boots
    armor("Armor Parts", "", "", false, false, false, false, false, false, false, false, true, false, B[6], -1, 1, -1),
    frameGt("Frame Boxes", "", "", true, true, false, false, true, false, true, false, false, false, 0, M * 2, 64, 83),
    pipeTiny("Tiny Pipes", "Tiny ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M / 2, 64, 78),
    pipeSmall("Small Pipes", "Small ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 1, 64, 79),
    pipeMedium("Medium Pipes", "Medium ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 3, 64, 80),
    pipeLarge("Large pipes", "Large ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 6, 64, 81),
    pipeHuge("Huge Pipes", "Huge ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 12, 64, 82),
    pipeRestrictiveTiny("Tiny Restrictive Pipes", "Tiny Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M / 2, 64, 78),
    pipeRestrictiveSmall("Small Restrictive Pipes", "Small Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 1, 64, 79),
    pipeRestrictiveMedium("Medium Restrictive Pipes", "Medium Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 3, 64, 80),
    pipeRestrictiveLarge("Large Restrictive Pipes", "Large Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 6, 64, 81),
    pipeRestrictiveHuge("Huge Restrictive Pipes", "Huge Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 12, 64, 82),
    pipe("Pipes", "", " Pipe", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, 77),
    wireGt16("16x Wires", "16x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 8, 64, -1),
    wireGt12("12x Wires", "12x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 6, 64, -1),
    wireGt08("8x Wires", "8x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 4, 64, -1),
    wireGt04("4x Wires", "4x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 2, 64, -1),
    wireGt02("2x Wires", "2x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 1, 64, -1),
    wireGt01("1x Wires", "1x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M / 2, 64, -1),
    cableGt12("12x Cables", "12x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 6, 64, -1),
    cableGt08("8x Cables", "8x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 4, 64, -1),
    cableGt04("4x Cables", "4x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 2, 64, -1),
    cableGt02("2x Cables", "2x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 1, 64, -1),
    cableGt01("1x Cables", "1x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M / 2, 64, -1),

    /* Electric Components.
     *
	 * usual Materials for this are:
	 * Primitive (Tier 1)
	 * Basic (Tier 2) as used by UE as well : IC2 Circuit and RE-Battery
	 * Good (Tier 3)
	 * Advanced (Tier 4) as used by UE as well : Advanced Circuit, Advanced Battery and Lithium Battery
	 * Data (Tier 5) : Data Storage Circuit
	 * Elite (Tier 6) as used by UE as well : Energy Crystal and Data Control Circuit
	 * Master (Tier 7) : Energy Flow Circuit and Lapotron Crystal
	 * Ultimate (Tier 8) : Data Orb and Lapotronic Energy Orb
	 * Infinite (Cheaty)
	 */
    batterySingleuse("Single Use Batteries", "", "", false, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    battery("Reusable Batteries", "", "", false, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Calclavia
    circuit("Circuits", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Calclavia
    chipset("Chipsets", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Buildcraft
    computer("Computers", "", "", true, true, false, false, true, false, false, false, false, false, 0, -1, 64, -1), // A whole Computer. "computerMaster" = ComputerCube

    // random known prefixes without special abilities.
    skull("Skulls", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    plating("Platings", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    dinosaur("Dinosaurs", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    travelgear("Travel Gear", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    bauble("Baubles", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    cluster("Clusters", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    grafter("Grafters", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    scoop("Scoops", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    frame("Frames", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    tome("Tomes", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    junk("Junk", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    bee("Bees", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    rod("Rods", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    dirt("Dirts", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    sand("Sands", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    grass("Grasses", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    gravel("Gravels", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    mushroom("Mushrooms", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    wood("Woods", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Eloraam
    drop("Drops", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    fuel("Fuels", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    panel("Panels", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    brick("Bricks", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    chunk("Chunks", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    wire("Wires", "", "", false, false, false, false, true, false, false, false, false, false, 0, -1, 64, -1),
    seed("Seeds", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    reed("Reeds", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    sheetDouble("2x Sheets", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    sheet("Sheets", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    crop("Crops", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    plant("Plants", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    coin("Coins", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    lumar("Lumars", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    ground("Grounded Stuff", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    cable("Cables", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    component("Components", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    wax("Waxes", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    wall("Walls", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    tube("Tubes", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    list("Lists", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    food("Foods", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    gear("Gears", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by SirSengir
    coral("Corals", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    flower("Flowers", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    storage("Storages", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    material("Materials", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    plasma("Plasmas", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    element("Elements", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    molecule("Molecules", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    wafer("Wafers", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    orb("Orbs", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    handle("Handles", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    blade("Blades", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    head("Heads", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    motor("Motors", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    bit("Bits", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    shears("Shears", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    turbine("Turbines", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    fertilizer("Fertilizers", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    chest("Chests", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    raw("Raw Things", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    stainedGlass("Stained Glasses", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    mystic("Mystic Stuff", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    mana("Mana Stuff", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    rune("Runes", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    petal("Petals", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    pearl("Pearls", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    powder("Powders", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    soulsand("Soulsands", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    obsidian("Obsidians", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    glowstone("Glowstones", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    beans("Beans", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    br("br", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    essence("Essences", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    alloy("Alloys", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    cooking("Cooked Things", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    elven("Elven Stuff", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    reactor("Reactors", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    mffs("MFFS", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    projred("Project Red", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    ganys("Ganys Stuff", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    liquid("Liquids", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    bars("Bars", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    bar("Bars", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
	toolHeadMallet("Mallet Heads", "", " Mallet Head", true, true, false, false, false, false, true, true, false, false, B[6], M * 6, 16, 127), // Reverse Head consisting out of 6 Ingots.
	handleMallet("Mallet Handle", "", " Handle", true, true, false, false, false, false, true, true, false, false, B[1] | B[2], M / 2, 64, 126); // Reverse Stick made of half an Ingot. Introduced by Eloraam
	
    public static volatile int VERSION = 509;

    static {
        pulp.mPrefixInto = dust;
        oreGem.mPrefixInto = ore;
        leaves.mPrefixInto = treeLeaves;
        sapling.mPrefixInto = treeSapling;
        itemDust.mPrefixInto = dust;
        dustDirty.mPrefixInto = dustImpure;
        denseore.mPrefixInto = oreDense;
        ingotQuad.mPrefixInto = ingotQuadruple;
        plateQuad.mPrefixInto = plateQuadruple;
        stoneBrick.mPrefixInto = stoneBricks;
        stoneBricksMossy.mPrefixInto = stoneMossyBricks;

        ingotHot.mHeatDamage = 3.0F;
        cellPlasma.mHeatDamage = 6.0F;

        block.ignoreMaterials(Materials.Ice, Materials.Snow, Materials.Concrete, Materials.Glass, Materials.Glowstone, Materials.DarkIron, Materials.Marble, Materials.Quartz, Materials.CertusQuartz, Materials.Limestone);
        ingot.ignoreMaterials(Materials.Brick, Materials.NetherBrick);

        dust.addFamiliarPrefix(dustTiny);
        dust.addFamiliarPrefix(dustSmall);
        dustTiny.addFamiliarPrefix(dust);
        dustTiny.addFamiliarPrefix(dustSmall);
        dustSmall.addFamiliarPrefix(dust);
        dustSmall.addFamiliarPrefix(dustTiny);

        ingot.addFamiliarPrefix(nugget);
        nugget.addFamiliarPrefix(ingot);

        for (OrePrefixes tPrefix1 : values())
            if (tPrefix1.name().startsWith("ore")) for (OrePrefixes tPrefix2 : values())
                if (tPrefix2.name().startsWith("ore")) tPrefix1.addFamiliarPrefix(tPrefix2);
        for (OrePrefixes tPrefix1 : values())
            if (tPrefix1.name().startsWith("pipe")) for (OrePrefixes tPrefix2 : values())
                if (tPrefix2.name().startsWith("pipe")) tPrefix1.addFamiliarPrefix(tPrefix2);
        for (OrePrefixes tPrefix1 : values())
            if (tPrefix1.name().startsWith("wireGt")) for (OrePrefixes tPrefix2 : values())
                if (tPrefix2.name().startsWith("wireGt")) tPrefix1.addFamiliarPrefix(tPrefix2);
        for (OrePrefixes tPrefix1 : values())
            if (tPrefix1.name().startsWith("cableGt")) for (OrePrefixes tPrefix2 : values())
                if (tPrefix2.name().startsWith("cableGt")) tPrefix1.addFamiliarPrefix(tPrefix2);

        // These are only the important ones.
        gem.mNotGeneratedItems.add(Materials.Coal);
        gem.mNotGeneratedItems.add(Materials.Charcoal);
        gem.mNotGeneratedItems.add(Materials.NetherStar);
        gem.mNotGeneratedItems.add(Materials.Diamond);
        gem.mNotGeneratedItems.add(Materials.Emerald);
        gem.mNotGeneratedItems.add(Materials.NetherQuartz);
        gem.mNotGeneratedItems.add(Materials.EnderPearl);
        gem.mNotGeneratedItems.add(Materials.EnderEye);
        gem.mNotGeneratedItems.add(Materials.Flint);
        gem.mNotGeneratedItems.add(Materials.Lapis);
        dust.mNotGeneratedItems.add(Materials.Bone);
        dust.mNotGeneratedItems.add(Materials.Redstone);
        dust.mNotGeneratedItems.add(Materials.Glowstone);
        dust.mNotGeneratedItems.add(Materials.Gunpowder);
        dust.mNotGeneratedItems.add(Materials.Sugar);
        dust.mNotGeneratedItems.add(Materials.Blaze);
        stick.mNotGeneratedItems.add(Materials.Wood);
        stick.mNotGeneratedItems.add(Materials.Bone);
        stick.mNotGeneratedItems.add(Materials.Blaze);
        ingot.mNotGeneratedItems.add(Materials.Iron);
        ingot.mNotGeneratedItems.add(Materials.Gold);
        ingot.mNotGeneratedItems.add(Materials.Brick);
        ingot.mNotGeneratedItems.add(Materials.BrickNether);
        ingot.mNotGeneratedItems.add(Materials.WoodSealed);
        ingot.mNotGeneratedItems.add(Materials.Wood);
        nugget.mNotGeneratedItems.add(Materials.Gold);
        plate.mNotGeneratedItems.add(Materials.Paper);
        cell.mNotGeneratedItems.add(Materials.Empty);
        cell.mNotGeneratedItems.add(Materials.Water);
        cell.mNotGeneratedItems.add(Materials.Lava);
        cell.mNotGeneratedItems.add(Materials.ConstructionFoam);
        cell.mNotGeneratedItems.add(Materials.UUMatter);
        cell.mNotGeneratedItems.add(Materials.BioFuel);
        cell.mNotGeneratedItems.add(Materials.CoalFuel);
        bucket.mNotGeneratedItems.add(Materials.Empty);
        bucket.mNotGeneratedItems.add(Materials.Lava);
        bucket.mNotGeneratedItems.add(Materials.Milk);
        bucket.mNotGeneratedItems.add(Materials.Water);
        bottle.mNotGeneratedItems.add(Materials.Empty);
        bottle.mNotGeneratedItems.add(Materials.Water);
        bottle.mNotGeneratedItems.add(Materials.Milk);
        block.mNotGeneratedItems.add(Materials.Iron);
        block.mNotGeneratedItems.add(Materials.Gold);
        block.mNotGeneratedItems.add(Materials.Lapis);
        block.mNotGeneratedItems.add(Materials.Emerald);
        block.mNotGeneratedItems.add(Materials.Redstone);
        block.mNotGeneratedItems.add(Materials.Diamond);
        block.mNotGeneratedItems.add(Materials.Coal);
        toolHeadArrow.mNotGeneratedItems.add(Materials.Glass);

        //-----

        dustImpure.mGeneratedItems.add(Materials.GraniteRed);
        dustImpure.mGeneratedItems.add(Materials.GraniteBlack);
        dustImpure.mGeneratedItems.add(Materials.Quartzite);
        dustImpure.mGeneratedItems.add(Materials.Flint);
        dustImpure.mGeneratedItems.add(Materials.Redrock);
        dustImpure.mGeneratedItems.add(Materials.Basalt);
        dustImpure.mGeneratedItems.add(Materials.Marble);
        dustImpure.mGeneratedItems.add(Materials.Netherrack);
        dustImpure.mGeneratedItems.add(Materials.Endstone);
        dustImpure.mGeneratedItems.add(Materials.Stone);

        plate.mGeneratedItems.add(Materials.Redstone);
        plate.mGeneratedItems.add(Materials.Concrete);
        plate.mGeneratedItems.add(Materials.GraniteRed);
        plate.mGeneratedItems.add(Materials.GraniteBlack);
        plate.mGeneratedItems.add(Materials.Glowstone);
        plate.mGeneratedItems.add(Materials.Nikolite);
        plate.mGeneratedItems.add(Materials.Obsidian);

        plate.mGeneratedItems.add(Materials.Paper);
        plateDouble.mGeneratedItems.add(Materials.Paper);
        plateTriple.mGeneratedItems.add(Materials.Paper);
        plateQuadruple.mGeneratedItems.add(Materials.Paper);
        plateQuintuple.mGeneratedItems.add(Materials.Paper);

        lens.mGeneratedItems.add(Materials.EnderPearl);
        lens.mGeneratedItems.add(Materials.EnderEye);

        stickLong.mGeneratedItems.add(Materials.Blaze);

        //-----

        dust.mGeneratedItems.addAll(dustPure.mGeneratedItems);
        dust.mGeneratedItems.addAll(dustImpure.mGeneratedItems);
        dust.mGeneratedItems.addAll(dustRefined.mGeneratedItems);
        dustTiny.mGeneratedItems.addAll(dust.mGeneratedItems);
        dustSmall.mGeneratedItems.addAll(dust.mGeneratedItems);
        crateGtDust.mGeneratedItems.addAll(dust.mGeneratedItems);
        crateGtIngot.mGeneratedItems.addAll(ingot.mGeneratedItems);
        crateGtGem.mGeneratedItems.addAll(gem.mGeneratedItems);
        crateGtPlate.mGeneratedItems.addAll(plate.mGeneratedItems);

        //-----

        toolHeadFile.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));
        toolHeadSaw.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));
        toolHeadDrill.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));
        toolHeadChainsaw.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));
        toolHeadWrench.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));
        toolHeadBuzzSaw.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));

        rotor.mCondition = new ICondition.Nor<ISubTagContainer>(SubTag.CRYSTAL, SubTag.STONE, SubTag.BOUNCY);

        spring.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        springSmall.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));

        gemChipped.mCondition = new ICondition.And<ISubTagContainer>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemFlawed.mCondition = new ICondition.And<ISubTagContainer>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemFlawless.mCondition = new ICondition.And<ISubTagContainer>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemExquisite.mCondition = new ICondition.And<ISubTagContainer>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));

        lens.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.MAGICAL, new ICondition.And<ISubTagContainer>(SubTag.TRANSPARENT, SubTag.HAS_COLOR));

        plateDouble.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.PAPER, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        plateTriple.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.PAPER, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        plateQuadruple.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.PAPER, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        plateQuintuple.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.PAPER, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));

        plateDense.mCondition = new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING);

        ingotDouble.mCondition = new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING);
        ingotTriple.mCondition = new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING);
        ingotQuadruple.mCondition = new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING);
        ingotQuintuple.mCondition = new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING);

        wireFine.mCondition = SubTag.METAL;

        //-----

        pipeRestrictiveTiny.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount);
        pipeRestrictiveSmall.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount * 2);
        pipeRestrictiveMedium.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount * 3);
        pipeRestrictiveLarge.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount * 4);
        pipeRestrictiveHuge.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount * 5);
        cableGt12.mSecondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.mMaterialAmount * 4);
        cableGt08.mSecondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.mMaterialAmount * 3);
        cableGt04.mSecondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.mMaterialAmount * 2);
        cableGt02.mSecondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.mMaterialAmount);
        cableGt01.mSecondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.mMaterialAmount);
        bucket.mSecondaryMaterial = new MaterialStack(Materials.Iron, ingot.mMaterialAmount * 3);
        cell.mSecondaryMaterial = new MaterialStack(Materials.Tin, plate.mMaterialAmount * 2);
        cellPlasma.mSecondaryMaterial = new MaterialStack(Materials.Tin, plate.mMaterialAmount * 2);
        oreRedgranite.mSecondaryMaterial = new MaterialStack(Materials.GraniteRed, dust.mMaterialAmount);
        oreBlackgranite.mSecondaryMaterial = new MaterialStack(Materials.GraniteBlack, dust.mMaterialAmount);
        oreNetherrack.mSecondaryMaterial = new MaterialStack(Materials.Netherrack, dust.mMaterialAmount);
        oreNether.mSecondaryMaterial = new MaterialStack(Materials.Netherrack, dust.mMaterialAmount);
        oreEndstone.mSecondaryMaterial = new MaterialStack(Materials.Endstone, dust.mMaterialAmount);
        oreEnd.mSecondaryMaterial = new MaterialStack(Materials.Endstone, dust.mMaterialAmount);
        oreDense.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount);
        orePoor.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount * 2);
        oreSmall.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount * 2);
        oreNormal.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount * 2);
        oreRich.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount * 2);
        ore.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount);
        crushed.mSecondaryMaterial = new MaterialStack(Materials.Stone, dust.mMaterialAmount);
        toolHeadDrill.mSecondaryMaterial = new MaterialStack(Materials.Steel, plate.mMaterialAmount * 4);
        toolHeadChainsaw.mSecondaryMaterial = new MaterialStack(Materials.Steel, plate.mMaterialAmount * 4 + ring.mMaterialAmount * 2);
        toolHeadWrench.mSecondaryMaterial = new MaterialStack(Materials.Steel, ring.mMaterialAmount + screw.mMaterialAmount * 2);
        arrowGtWood.mSecondaryMaterial = new MaterialStack(Materials.Wood, stick.mMaterialAmount);
        arrowGtPlastic.mSecondaryMaterial = new MaterialStack(Materials.Plastic, stick.mMaterialAmount);
        bulletGtSmall.mSecondaryMaterial = new MaterialStack(Materials.Brass, ingot.mMaterialAmount / 9);
        bulletGtMedium.mSecondaryMaterial = new MaterialStack(Materials.Brass, ingot.mMaterialAmount / 6);
        bulletGtLarge.mSecondaryMaterial = new MaterialStack(Materials.Brass, ingot.mMaterialAmount / 3);
    }

    public final ArrayList<ItemStack> mPrefixedItems = new ArrayList<ItemStack>();
    public final short mTextureIndex;
    public final String mRegularLocalName, mLocalizedMaterialPre, mLocalizedMaterialPost;
    public final boolean mIsUsedForOreProcessing, mIsEnchantable, mIsUnificatable, mIsMaterialBased, mIsSelfReferencing, mIsContainer, mDontUnificateActively, mIsUsedForBlocks, mAllowNormalRecycling, mGenerateDefaultItem;
    public final List<TC_AspectStack> mAspects = new ArrayList<TC_AspectStack>();
    public final Collection<OrePrefixes> mFamiliarPrefixes = new HashSet<OrePrefixes>();
    /**
     * Used to determine the amount of Material this Prefix contains.
     * Multiply or Divide GregTech_API.MATERIAL_UNIT to get the Amounts in comparision to one Ingot.
     * 0 = Null
     * Negative = Undefined Amount
     */
    public final long mMaterialAmount;
    private final Collection<Materials> mNotGeneratedItems = new HashSet<Materials>(), mIgnoredMaterials = new HashSet<Materials>(), mGeneratedItems = new HashSet<Materials>();
    private final ArrayList<IOreRecipeRegistrator> mOreProcessing = new ArrayList<IOreRecipeRegistrator>();
    public ItemStack mContainerItem = null;
    public ICondition<ISubTagContainer> mCondition = null;
    public byte mDefaultStackSize = 64;
    public MaterialStack mSecondaryMaterial = null;
    public OrePrefixes mPrefixInto = this;
    public float mHeatDamage = 0.0F; // Negative for Frost Damage
    /**
     * Yes this Value can be changed to add Bits for the MetaGenerated-Item-Check.
     */
    public int mMaterialGenerationBits = 0;
    private OrePrefixes(String aRegularLocalName, String aLocalizedMaterialPre, String aLocalizedMaterialPost, boolean aIsUnificatable, boolean aIsMaterialBased, boolean aIsSelfReferencing, boolean aIsContainer, boolean aDontUnificateActively, boolean aIsUsedForBlocks, boolean aAllowNormalRecycling, boolean aGenerateDefaultItem, boolean aIsEnchantable, boolean aIsUsedForOreProcessing, int aMaterialGenerationBits, long aMaterialAmount, int aDefaultStackSize, int aTextureindex) {
        mIsUnificatable = aIsUnificatable;
        mIsMaterialBased = aIsMaterialBased;
        mIsSelfReferencing = aIsSelfReferencing;
        mIsContainer = aIsContainer;
        mDontUnificateActively = aDontUnificateActively;
        mIsUsedForBlocks = aIsUsedForBlocks;
        mAllowNormalRecycling = aAllowNormalRecycling;
        mGenerateDefaultItem = aGenerateDefaultItem;
        mIsEnchantable = aIsEnchantable;
        mIsUsedForOreProcessing = aIsUsedForOreProcessing;
        mMaterialGenerationBits = aMaterialGenerationBits;
        mMaterialAmount = aMaterialAmount;
        mRegularLocalName = aRegularLocalName;
        mLocalizedMaterialPre = aLocalizedMaterialPre;
        mLocalizedMaterialPost = aLocalizedMaterialPost;
        mDefaultStackSize = (byte) aDefaultStackSize;
        mTextureIndex = (short) aTextureindex;

        if (name().startsWith("ore")) {
            new TC_AspectStack(TC_Aspects.TERRA, 1).addToAspectList(mAspects);
        } else if (name().startsWith("wire") || name().startsWith("cable")) {
            new TC_AspectStack(TC_Aspects.ELECTRUM, 1).addToAspectList(mAspects);
        } else if (name().startsWith("dust")) {
            new TC_AspectStack(TC_Aspects.PERDITIO, 1).addToAspectList(mAspects);
        } else if (name().startsWith("crushed")) {
            new TC_AspectStack(TC_Aspects.PERFODIO, 1).addToAspectList(mAspects);
        } else if (name().startsWith("ingot") || name().startsWith("nugget")) {
            new TC_AspectStack(TC_Aspects.METALLUM, 1).addToAspectList(mAspects);
        } else if (name().startsWith("armor")) {
            new TC_AspectStack(TC_Aspects.TUTAMEN, 1).addToAspectList(mAspects);
        } else if (name().startsWith("stone")) {
            new TC_AspectStack(TC_Aspects.TERRA, 1).addToAspectList(mAspects);
        } else if (name().startsWith("pipe")) {
            new TC_AspectStack(TC_Aspects.ITER, 1).addToAspectList(mAspects);
        } else if (name().startsWith("gear")) {
            new TC_AspectStack(TC_Aspects.MOTUS, 1).addToAspectList(mAspects);
            new TC_AspectStack(TC_Aspects.MACHINA, 1).addToAspectList(mAspects);
        } else if (name().startsWith("frame") || name().startsWith("plate")) {
            new TC_AspectStack(TC_Aspects.FABRICO, 1).addToAspectList(mAspects);
        } else if (name().startsWith("tool")) {
            new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2).addToAspectList(mAspects);
        } else if (name().startsWith("gem") || name().startsWith("crystal") || name().startsWith("lens")) {
            new TC_AspectStack(TC_Aspects.VITREUS, 1).addToAspectList(mAspects);
        } else if (name().startsWith("crate")) {
            new TC_AspectStack(TC_Aspects.ITER, 2).addToAspectList(mAspects);
        } else if (name().startsWith("circuit")) {
            new TC_AspectStack(TC_Aspects.COGNITIO, 1).addToAspectList(mAspects);
        } else if (name().startsWith("computer")) {
            new TC_AspectStack(TC_Aspects.COGNITIO, 4).addToAspectList(mAspects);
        } else if (name().startsWith("battery")) {
            new TC_AspectStack(TC_Aspects.ELECTRUM, 1).addToAspectList(mAspects);
        }
    }

    public static OrePrefixes getOrePrefix(String aOre) {
        for (OrePrefixes tPrefix : values())
            if (aOre.startsWith(tPrefix.toString())) {
                if (tPrefix == oreNether && aOre.equals("oreNetherQuartz")) return ore;
                return tPrefix;
            }
        return null;
    }

    public static String stripPrefix(String aOre) {
        for (OrePrefixes tPrefix : values()) {
            if (aOre.startsWith(tPrefix.toString())) {
                return aOre.replaceFirst(tPrefix.toString(), "");
            }
        }
        return aOre;
    }

    public static String replacePrefix(String aOre, OrePrefixes aPrefix) {
        for (OrePrefixes tPrefix : values()) {
            if (aOre.startsWith(tPrefix.toString())) {
                return aOre.replaceFirst(tPrefix.toString(), aPrefix.toString());
            }
        }
        return "";
    }

    public static OrePrefixes getPrefix(String aPrefixName) {
        return getPrefix(aPrefixName, null);
    }

    public static OrePrefixes getPrefix(String aPrefixName, OrePrefixes aReplacement) {
        Object tObject = GT_Utility.getFieldContent(OrePrefixes.class, aPrefixName, false, false);
        if (tObject instanceof OrePrefixes) return (OrePrefixes) tObject;
        return aReplacement;
    }

    public static Materials getMaterial(String aOre) {
        return Materials.get(stripPrefix(aOre));
    }

    public static Materials getMaterial(String aOre, OrePrefixes aPrefix) {
        return Materials.get(aOre.replaceFirst(aPrefix.toString(), ""));
    }

    public static Materials getRealMaterial(String aOre, OrePrefixes aPrefix) {
        return Materials.getRealMaterial(aOre.replaceFirst(aPrefix.toString(), ""));
    }

    public static boolean isInstanceOf(String aName, OrePrefixes aPrefix) {
        return aName == null ? false : aName.startsWith(aPrefix.toString());
    }

    public boolean add(ItemStack aStack) {
        if (aStack == null) return false;
        if (!contains(aStack)) mPrefixedItems.add(aStack);
        while (mPrefixedItems.contains(null)) mPrefixedItems.remove(null);
        return true;
    }

    public boolean contains(ItemStack aStack) {
        if (aStack == null) return false;
        for (ItemStack tStack : mPrefixedItems)
            if (GT_Utility.areStacksEqual(aStack, tStack, !tStack.hasTagCompound())) return true;
        return false;
    }

    public boolean doGenerateItem(Materials aMaterial) {
        return aMaterial != null && aMaterial != Materials._NULL && ((aMaterial.mTypes & mMaterialGenerationBits) != 0 || mGeneratedItems.contains(aMaterial)) && !mNotGeneratedItems.contains(aMaterial) && (mCondition == null || mCondition.isTrue(aMaterial));
    }

    public boolean ignoreMaterials(Materials... aMaterials) {
        for (Materials tMaterial : aMaterials) if (tMaterial != null) mIgnoredMaterials.add(tMaterial);
        return true;
    }

    public boolean isIgnored(Materials aMaterial) {
        if (aMaterial != null && (!aMaterial.mUnificatable || aMaterial != aMaterial.mMaterialInto)) return true;
        return mIgnoredMaterials.contains(aMaterial);
    }

    public boolean addFamiliarPrefix(OrePrefixes aPrefix) {
        if (aPrefix == null || mFamiliarPrefixes.contains(aPrefix) || aPrefix == this) return false;
        return mFamiliarPrefixes.add(aPrefix);
    }

    public boolean add(IOreRecipeRegistrator aRegistrator) {
        if (aRegistrator == null) return false;
        return mOreProcessing.add(aRegistrator);
    }

    public void processOre(Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial != null && (aMaterial != Materials._NULL || mIsSelfReferencing || !mIsMaterialBased) && GT_Utility.isStackValid(aStack))
            for (IOreRecipeRegistrator tRegistrator : mOreProcessing) {
                if (D2)
                    GT_Log.ore.println("Processing '" + aOreDictName + "' with the Prefix '" + name() + "' and the Material '" + aMaterial.name() + "' at " + GT_Utility.getClassName(tRegistrator));
                tRegistrator.registerOre(this, aMaterial, aOreDictName, aModName, GT_Utility.copyAmount(1, aStack));
            }
    }

    public Object get(Object aMaterial) {
        if (aMaterial instanceof Materials) return new ItemData(this, (Materials) aMaterial);
        return name() + aMaterial;
    }

    @SuppressWarnings("incomplete-switch")
    public String getDefaultLocalNameForItem(Materials aMaterial) {
        // Certain Materials have slightly different Localizations.
        switch (this) {
            case crateGtDust:
                return mLocalizedMaterialPre + OrePrefixes.dust.getDefaultLocalNameForItem(aMaterial);
            case crateGtIngot:
                return mLocalizedMaterialPre + OrePrefixes.ingot.getDefaultLocalNameForItem(aMaterial);
            case crateGtGem:
                return mLocalizedMaterialPre + OrePrefixes.gem.getDefaultLocalNameForItem(aMaterial);
            case crateGtPlate:
                return mLocalizedMaterialPre + OrePrefixes.plate.getDefaultLocalNameForItem(aMaterial);
        }
        switch (aMaterial) {
            case Glass:
                if (name().startsWith("gem")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystal";
                if (name().startsWith("plate")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Pane";
                break;
            case InfusedAir:
            case InfusedDull:
            case InfusedEarth:
            case InfusedEntropy:
            case InfusedFire:
            case InfusedOrder:
            case InfusedVis:
            case InfusedWater:
                if (name().startsWith("gem")) return mLocalizedMaterialPre + "Shard of " + aMaterial.mDefaultLocalName;
                if (name().startsWith("crystal"))
                    return mLocalizedMaterialPre + "Shard of " + aMaterial.mDefaultLocalName;
                if (name().startsWith("plate"))
                    return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystal Plate";
                if (name().startsWith("dust"))
                    return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystal Powder";
                if (this == OrePrefixes.crushedCentrifuged)
                    return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystals";
                if (this == OrePrefixes.crushedPurified)
                    return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystals";
                if (this == OrePrefixes.crushed)
                    return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Crystals";
                break;
            case Wheat:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Flour";
                break;
            case Ice:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Crushed Ice";
                break;
            case Wood:
            case WoodSealed:
                if (name().startsWith("bolt")) return "Short " + aMaterial.mDefaultLocalName + " Stick";
                if (name().startsWith("stick")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Stick";
                if (name().startsWith("dust")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Pulp";
                if (name().startsWith("nugget")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Chip";
                if (name().startsWith("plate")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Plank";
                break;
            case Plastic:
            case Rubber:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Pulp";
                if (name().startsWith("plate")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Sheet";
                if (name().startsWith("ingot")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Bar";
                if (name().startsWith("nugget")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Chip";
                if (name().startsWith("foil")) return "Thin " + aMaterial.mDefaultLocalName + " Sheet";
                break;
            case FierySteel:
                if (mIsContainer) return mLocalizedMaterialPre + "Fiery Blood" + mLocalizedMaterialPost;
                break;
            case Steeleaf:
                if (name().startsWith("ingot")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName;
                break;
            case Bone:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Bone Meal";
                break;
            case Blaze:
            case Milk:
            case Cocoa:
            case Chocolate:
            case Coffee:
            case Chili:
            case Cheese:
            case Snow:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + " Powder";
                break;
            case Paper:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Chad";
                if (this == OrePrefixes.plate) return "Sheet of Paper";
                if (this == OrePrefixes.plateDouble) return "Paperboard";
                if (this == OrePrefixes.plateTriple) return "Carton";
                if (this == OrePrefixes.plateQuadruple) return "Cardboard";
                if (this == OrePrefixes.plateQuintuple) return "Thick Cardboard";
                if (this == OrePrefixes.plateDense) return "Strong Cardboard";
                break;
            case MeatRaw:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Mince Meat";
                break;
            case MeatCooked:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + "Cooked Mince Meat";
                break;
            case Ash:
            case DarkAsh:
            case Gunpowder:
            case Sugar:
            case Salt:
            case RockSalt:
            case VolcanicAsh:
            case RareEarth:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName;
                break;
            case Vermiculite:
            case Bentonite:
            case Kaolinite:
            case Talc:
            case BasalticMineralSand:
            case GraniticMineralSand:
            case GlauconiteSand:
            case CassiteriteSand:
            case GarnetSand:
            case QuartzSand:
            case Pitchblende:
            case FullersEarth:
                if (name().startsWith("dust")) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName;
                if (this == OrePrefixes.crushedCentrifuged) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName;
                if (this == OrePrefixes.crushedPurified) return mLocalizedMaterialPre + aMaterial.mDefaultLocalName;
                if (this == OrePrefixes.crushed) return "Ground " + aMaterial.mDefaultLocalName;
                break;
        }
        // Use Standard Localization
        return mLocalizedMaterialPre + aMaterial.mDefaultLocalName + mLocalizedMaterialPost;
    }
}