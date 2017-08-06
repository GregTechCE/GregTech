package gregtech.api.material;

import com.google.common.base.Preconditions;
import gregtech.api.material.type.DustMaterial;
import gregtech.api.material.type.GemMaterial;
import gregtech.api.material.type.Material;
import gregtech.api.material.type.MetalMaterial;
import gregtech.api.material.type.SolidMaterial;
import gregtech.api.interfaces.ICondition;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.objects.SimpleItemStack;
import gregtech.api.objects.UnificationEntry;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.GT_Values.*;
import static gregtech.api.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.material.type.MetalMaterial.MatFlags.*;
import static gregtech.api.material.type.SolidMaterial.MatFlags.GENERATE_GEAR;
import static gregtech.api.material.type.SolidMaterial.MatFlags.GENERATE_LONG_ROD;
import static gregtech.api.material.type.SolidMaterial.MatFlags.GENERATE_ROD;

public enum OrePrefixes {

    oreBlackgranite("Black Granite Ores", "Granite ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreRedgranite("Red Granite Ores", "Granite ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreMarble("Marble Ores", "Marble ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreBasalt("Basalt Ores", "Basalt ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreSand("Sand Ores", "Sand ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an Sand-Ores Mod. Ore -> Material is a Oneway Operation!
    oreGravel("Gravel Ores", "Sand ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an Gravel-Ores Mod. Ore -> Material is a Oneway Operation!

    oreNetherrack("Netherrack Ores", "Nether ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreNether("Nether Ores", "Nether ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!

    oreDense("Dense Ores", "Dense ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of the Dense-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreRich("Rich Ores", "Rich ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of TFC
    oreNormal("Normal Ores", "Normal ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of TFC
    oreSmall("Small Ores", "Small ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, 67), // Prefix of Railcraft.
    orePoor("Poor Ores", "Poor ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // Prefix of Railcraft.

    oreEndstone("Endstone Ores", "End ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreEnd("End Ores", "End ", " Ore", true, true, false, false, false, true, false, false, false, true, B[3], -1, 64, -1), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

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
    ingotTriple("3x Ingots", "Triple ", " Ingot", true, true, false, false, false, false, true, false, false, false, B[1], M * 3, 21, 14), // A triple Ingot.
    ingotDouble("2x Ingots", "Double ", " Ingot", true, true, false, false, false, false, true, true, false, false, B[1], M * 2, 32, 13), // A double Ingot. Introduced by TerraFirmaCraft
    ingotHot("Hot Ingots", "Hot ", " Ingot", true, true, false, false, false, false, false, true, false, false, B[1], M * 1, 16, 12), // A hot Ingot, which has to be cooled down by a Vacuum Freezer.
    ingot("Ingots", "", " Ingot", true, true, false, false, false, false, false, true, false, false, B[1], M * 1, 64, 11), // A regular Ingot. Introduced by Eloraam

    gemChipped("Chipped Gemstones", "Chipped ", "", true, true, true, false, false, false, true, true, false, false, B[2], M / 4, 64, 59), // A regular Gem worth one small Dust. Introduced by TerraFirmaCraft
    gemFlawed("Flawed Gemstones", "Flawed ", "", true, true, true, false, false, false, true, true, false, false, B[2], M / 2, 64, 60), // A regular Gem worth two small Dusts. Introduced by TerraFirmaCraft
    gemFlawless("Flawless Gemstones", "Flawless ", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 2, 32, 61), // A regular Gem worth two Dusts. Introduced by TerraFirmaCraft
    gemExquisite("Exquisite Gemstones", "Exquisite ", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 4, 16, 62), // A regular Gem worth four Dusts. Introduced by TerraFirmaCraft
    gem("Gemstones", "", "", true, true, true, false, false, false, true, true, false, false, B[2], M * 1, 64, 8), // A regular Gem worth one Dust. Introduced by Eloraam

    dustTiny("Tiny Dusts", "Tiny Pile of ", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M / 9, 64, 0), // 1/9th of a Dust.
    dustSmall("Small Dusts", "Small Pile of ", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M / 4, 64, 1), // 1/4th of a Dust.
    dustImpure("Impure Dusts", "Impure Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 3), // Dust with impurities. 1 Unit of Main Material and 1/9 - 1/4 Unit of secondary Material
    dustRefined("Refined Dusts", "Refined Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 2),
    dustPure("Purified Dusts", "Purified Pile of ", " Dust", true, true, false, false, false, false, false, true, false, true, B[3], M * 1, 64, 4),
    dust("Dusts", "", " Dust", true, true, false, false, false, false, false, true, false, false, B[0] | B[1] | B[2] | B[3], M * 1, 64, 2), // Pure Dust worth of one Ingot or Gem. Introduced by Alblaka.

    nugget("Nuggets", "", " Nugget", true, true, false, false, false, false, false, true, false, false, B[1], M / 9, 64, 9), // A Nugget. Introduced by Eloraam

    plateAlloy("Alloy Plates", "", "", true, false, false, false, false, false, false, false, false, false, B[1], -1, 64, 17), // Special Alloys have this prefix.
    plateDense("Dense Plates", "Dense ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 9, 8, 22), // 9 Plates combined in one Item.
    plateQuintuple("5x Plates", "Quintuple ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 5, 12, 21),
    plateQuadruple("4x Plates", "Quadruple ", " Plate", true, true, false, false, false, false, true, true, false, false, B[1], M * 4, 16, 20),
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
    stoneBricks("Stone Bricks", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Stone Bricks.
    stoneCracked("Cracked Stones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Cracked Bricks.
    stoneChiseled("Chiseled Stones", "", "", false, false, true, false, false, true, false, false, false, false, 0, -1, 64, -1), // Chiseled Stone.
    stone("Stones", "", "", false, true, true, false, true, true, false, false, false, false, 0, -1, 64, -1), // Prefix to determine which kind of Rock this is.
    cobblestone("Cobblestones", "", "", false, true, true, false, false, true, false, false, false, false, 0, -1, 64, -1),
    rock("Rocks", "", "", false, true, true, false, true, true, false, false, false, false, 0, -1, 64, -1), // Prefix to determine which kind of Rock this is.
    record("Records", "", "", false, false, true, false, false, false, false, false, false, false, 0, -1, 1, -1),
    rubble("Rubbles", "", "", true, true, true, false, false, false, false, false, false, false, 0, -1, 64, -1),
    scraps("Scraps", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    scrap("Scraps", "", "", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, -1),

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
    pipeRestrictiveSmall("Small Restrictive Pipes", "Small Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M, 64, 79),
    pipeRestrictiveMedium("Medium Restrictive Pipes", "Medium Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 3, 64, 80),
    pipeRestrictiveLarge("Large Restrictive Pipes", "Large Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 6, 64, 81),
    pipeRestrictiveHuge("Huge Restrictive Pipes", "Huge Restrictive ", " Pipe", true, true, false, false, true, false, true, false, false, false, 0, M * 12, 64, 82),
    pipe("Pipes", "", " Pipe", false, false, false, false, false, false, false, false, false, false, 0, -1, 64, 77),

    wireGt16("16x Wires", "16x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 8, 64, -1),
    wireGt12("12x Wires", "12x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 6, 64, -1),
    wireGt08("8x Wires", "8x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 4, 64, -1),
    wireGt04("4x Wires", "4x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M * 2, 64, -1),
    wireGt02("2x Wires", "2x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M, 64, -1),
    wireGt01("1x Wires", "1x ", " Wire", true, true, false, false, false, false, true, false, false, false, 0, M / 2, 64, -1),
    cableGt12("12x Cables", "12x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 6, 64, -1),
    cableGt08("8x Cables", "8x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 4, 64, -1),
    cableGt04("4x Cables", "4x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M * 2, 64, -1),
    cableGt02("2x Cables", "2x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M, 64, -1),
    cableGt01("1x Cables", "1x ", " Cable", true, true, false, false, false, false, true, false, false, false, 0, M / 2, 64, -1),

    /* Electric Components.
     *
	 * usual Materials for this are:
	 * Primitive (Tier 1)
	 * Basic (Tier 2)
	 * Good (Tier 3)
	 * Advanced (Tier 4)
	 * Data (Tier 5)
	 * Elite (Tier 6)
	 * Master (Tier 7)
	 * Ultimate (Tier 8)
	 * Infinite
	 */
    batterySingleUse("Single Use Batteries", "", "", false, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1),
    battery("Reusable Batteries", "", "", false, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Calclavia
    circuit("Circuits", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Calclavia
    chipset("Chipsets", "", "", true, true, false, false, false, false, false, false, false, false, 0, -1, 64, -1), // Introduced by Buildcraft
    computer("Computers", "", "", true, true, false, false, true, false, false, false, false, false, 0, -1, 64, -1); // A whole Computer. "computerMaster" = ComputerCube


    static {
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

        /*cell.mNotGeneratedItems.add(Materials.Empty);
        cell.mNotGeneratedItems.add(Materials.Water);
        cell.mNotGeneratedItems.add(Materials.Lava);
        cell.mNotGeneratedItems.add(Materials.ConstructionFoam);
        cell.mNotGeneratedItems.add(Materials.UUMatter);
        cell.mNotGeneratedItems.add(Materials.BioFuel);
        cell.mNotGeneratedItems.add(Materials.CoalFuel);*/
        //Now all of these need to be generated
        //because IC2 removed normal cells

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
        dustImpure.mGeneratedItems.add(Materials.Andesite);
        dustImpure.mGeneratedItems.add(Materials.Diorite);

        plate.mGeneratedItems.add(Materials.Redstone);
        plate.mGeneratedItems.add(Materials.Concrete);
        plate.mGeneratedItems.add(Materials.GraniteRed);
        plate.mGeneratedItems.add(Materials.GraniteBlack);
        plate.mGeneratedItems.add(Materials.Glowstone);
        plate.mGeneratedItems.add(Materials.Nikolite);
        plate.mGeneratedItems.add(Materials.Obsidian);
        plate.mGeneratedItems.add(Materials.Andesite);
        plate.mGeneratedItems.add(Materials.Diorite);

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
        turbineBlade.mCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING), new ICondition.Not<ISubTagContainer>(SubTag.BOUNCY));

        rotor.mCondition = new ICondition.Nor<ISubTagContainer>(SubTag.CRYSTAL, SubTag.STONE, SubTag.BOUNCY);

        spring.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        springSmall.mCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));

        gemChipped.mCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemFlawed.mCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemFlawless.mCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));
        gemExquisite.mCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<ISubTagContainer>(SubTag.QUARTZ), new ICondition.Not<ISubTagContainer>(SubTag.PEARL), new ICondition.Not<ISubTagContainer>(SubTag.MAGICAL));

        lens.mCondition = new ICondition.Or<>(SubTag.MAGICAL, new ICondition.And<>(SubTag.TRANSPARENT, SubTag.HAS_COLOR));

        plateDouble.mCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateTriple.mCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateQuadruple.mCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateQuintuple.mCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));

        plateDense.mCondition = new ICondition.Not<>(SubTag.NO_SMASHING);

        ingotDouble.mCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotTriple.mCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotQuadruple.mCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotQuintuple.mCondition = new ICondition.Not<>(SubTag.NO_SMASHING);

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

        oreBasalt.mSecondaryMaterial = new MaterialStack(Materials.Basalt, dust.mMaterialAmount);
        oreMarble.mSecondaryMaterial = new MaterialStack(Materials.Marble, dust.mMaterialAmount);

        oreSand.mSecondaryMaterial = new MaterialStack(Materials.SiliconDioxide, dustTiny.mMaterialAmount);
        oreGravel.mSecondaryMaterial = new MaterialStack(Materials.Flint, dustTiny.mMaterialAmount);

        oreNetherrack.mSecondaryMaterial = new MaterialStack(Materials.Netherrack, dust.mMaterialAmount);
        oreNether.mSecondaryMaterial = new MaterialStack(Materials.Netherrack, dust.mMaterialAmount);

        oreEndstone.mSecondaryMaterial = new MaterialStack(Materials.Endstone, dust.mMaterialAmount);
        oreEnd.mSecondaryMaterial = new MaterialStack(Materials.Endstone, dust.mMaterialAmount);

        oreMarble.mSecondaryMaterial = new MaterialStack(Materials.Marble, dust.mMaterialAmount);
        oreBasalt.mSecondaryMaterial = new MaterialStack(Materials.Basalt, dust.mMaterialAmount);

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

    public static final int ENABLE_UNIFICATION = Material.MatFlags.createFlag(0);
    public static final int SELF_REFERENCING = Material.MatFlags.createFlag(1);
    public static final int DISALLOW_RECYCLING = Material.MatFlags.createFlag(2);
    public static final int FLUID_CONTAINER = Material.MatFlags.createFlag(3);

    public final String categoryName;

    public final boolean isSelfReferencing;
    private final @Nullable String materialNameLocale;
    public final @Nullable ICondition<Material> generationCondition;
    public final @Nullable MaterialIconType materialIconType;

    public final long materialAmount;

    private final ArrayList<IOreRecipeRegistrator> oreProcessingHandlers = new ArrayList<>();

    public @Nullable ItemStack containerItem = null;
    public byte defaultStackSize = 64;
    public @Nullable MaterialStack secondaryMaterial = null;
    public float heatDamage = 0.0F; // Negative for Frost Damage

    OrePrefixes(String categoryName, long materialAmount) {
        this.categoryName = categoryName;
        this.materialAmount = materialAmount;
        this.isSelfReferencing = true;
        this.materialNameLocale = null;
        this.generationCondition = null;
        this.materialIconType = null;
    }

    OrePrefixes(String categoryName, long materialAmount, String materialNameLocale, MaterialIconType materialIconType, ICondition<Material> condition) {
        this.categoryName = categoryName;
        this.materialAmount = materialAmount;
        this.isSelfReferencing = false;
        this.materialNameLocale = materialNameLocale;
        this.materialIconType = materialIconType;
        this.generationCondition = condition;
    }

    public static OrePrefixes getPrefix(String prefixName) {
        return getPrefix(prefixName, null);
    }

    public static OrePrefixes getPrefix(String prefixName, @Nullable OrePrefixes replacement) {
        try {
            return Enum.valueOf(OrePrefixes.class, prefixName);
        } catch (IllegalArgumentException invalidPrefixName) {
            return replacement;
        }
    }

    public boolean doGenerateItem(Material material) {
        return !isSelfReferencing && generationCondition != null && materialIconType != null && generationCondition.isTrue(material);
    }

    public boolean addProcessingHandler(IOreRecipeRegistrator processingHandler) {
        Preconditions.checkNotNull(processingHandler);
        return oreProcessingHandlers.add(processingHandler);
    }

    public void processOreRegistration(Material material, String modName, SimpleItemStack itemStack) {
        //tracking bad oredict registrations
        if(isSelfReferencing && material != null) {
            GT_Log.logger.warn("Mod %s attempted to register %s with prefix %s and material %s, but prefix is self resolving!", modName, itemStack, this, material.toString());
            material = null; //truncate material
        }
        if(!isSelfReferencing && material == null) {
            GT_Log.logger.warn("Mod %s attempted to register %s with prefix %s, but prefix is not self resolving!", modName, itemStack, this);
            return; //there is nothing we can do -- return to avoid further problems with invalid registration
        }
        UnificationEntry unificationEntry = new UnificationEntry(this, material);
        for(IOreRecipeRegistrator handler : oreProcessingHandlers) {
            handler.registerOre(unificationEntry, modName, itemStack);
        }
    }

    public String getDefaultLocalNameForItem(Material material) {
        return String.format(materialNameLocale, material.defaultLocalName);
    }

}