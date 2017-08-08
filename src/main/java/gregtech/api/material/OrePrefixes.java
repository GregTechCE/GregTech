package gregtech.api.material;

import com.google.common.base.Preconditions;
import gregtech.api.interfaces.ICondition;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.material.type.Material;
import gregtech.api.objects.MaterialStack;
import gregtech.api.objects.SimpleItemStack;
import gregtech.api.objects.UnificationEntry;
import gregtech.api.util.GT_Log;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static gregtech.api.GT_Values.B;
import static gregtech.api.GT_Values.M;

public enum OrePrefixes {

    oreBlackgranite("Black Granite Ores", -1, "Granite %s Ore", MaterialIconType.ore,  ENABLE_UNIFICATION | DISALLOW_RECYCLING), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreRedgranite("Red Granite Ores", -1, "Granite %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreMarble("Marble Ores", -1, "Marble %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreBasalt("Basalt Ores", -1, "Basalt %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreSand("Sand Ores", -1, "Sand %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an Sand-Ores Mod. Ore -> Material is a Oneway Operation!
    oreGravel("Gravel Ores", -1, "Sand %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an Gravel-Ores Mod. Ore -> Material is a Oneway Operation!

    oreNetherrack("Netherrack Ores", -1, "Nether %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreNether("Nether Ores", -1, "Nether %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!

    oreDense("Dense Ores", -1, "Dense %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Prefix of the Dense-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreRich("Rich Ores", -1, "Rich %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Prefix of TFC
    oreNormal("Normal Ores", -1, "Normal %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Prefix of TFC
    oreSmall("Small Ores", -1, "Small %s Ore", MaterialIconType.oreSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3], -1, 64, 67), // Prefix of Railcraft.
    orePoor("Poor Ores", -1, "Poor %s Ore", MaterialIconType.oreSmall, ENABLE_UNIFICATION |  DISALLOW_RECYCLING B[3]), // Prefix of Railcraft.

    oreEndstone("Endstone Ores", -1, "End %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreEnd("End Ores", -1, "End %s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    ore("Ores", -1, "%s Ore", MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3], -1, 64, 68), // Regular Ore Prefix. Ore -> Material is a Oneway Operation! Introduced by Eloraam

    crushedCentrifuged("Centrifuged Ores", -1, "Centrifuged %s Ore", MaterialIconType.crushedCentrifuged, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    crushedPurified("Purified Ores", -1, "Purified %s Ore", MaterialIconType.crushedPurified, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    crushed("Crushed Ores", -1, "Crushed %s Ore", MaterialIconType.crushed, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),

    shard("Crystallised Shards", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Introduced by Mekanism
    clump("Clumps", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    reduced("Reduced Gravels", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    crystalline("Crystallised Metals", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),

    cleanGravel("Clean Gravels", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    dirtyGravel("Dirty Gravels", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),

    ingotQuintuple("5x Ingots", M * 5, "Quintuple %s Ingot", MaterialIconType.ingotQuintuple, ENABLE_UNIFICATION B[1]), // A quintuple Ingot.
    ingotQuadruple("4x Ingots", M * 4, "Quadruple %s Ingot", MaterialIconType.ingotQuadruple, ENABLE_UNIFICATION B[1]), // A quadruple Ingot.
    ingotTriple("3x Ingots", M * 3, "Triple %s Ingot", MaterialIconType.ingotTriple, ENABLE_UNIFICATION B[1]), // A triple Ingot.
    ingotDouble("2x Ingots", M * 2, "Double %s Ingot", MaterialIconType.ingotDouble, ENABLE_UNIFICATION B[1]), // A double Ingot. Introduced by TerraFirmaCraft
    ingotHot("Hot Ingots", M, "Hot %s Ingot", MaterialIconType.ingotHot, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[1]), // A hot Ingot, which has to be cooled down by a Vacuum Freezer.
    ingot("Ingots", M, "%s Ingot", MaterialIconType.ingot, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[1]), // A regular Ingot. Introduced by Eloraam

    gemChipped("Chipped Gemstones", M / 4, "Chipped %s", MaterialIconType.gemChipped, ENABLE_UNIFICATION | SELF_REFERENCING B[2]), // A regular Gem worth one small Dust. Introduced by TerraFirmaCraft
    gemFlawed("Flawed Gemstones", M / 2, "Flawed %s", MaterialIconType.gemFlawed, ENABLE_UNIFICATION | SELF_REFERENCING B[2]), // A regular Gem worth two small Dusts. Introduced by TerraFirmaCraft
    gemFlawless("Flawless Gemstones", M * 2, "Flawless %s", MaterialIconType.gemFlawless, ENABLE_UNIFICATION | SELF_REFERENCING B[2]), // A regular Gem worth two Dusts. Introduced by TerraFirmaCraft
    gemExquisite("Exquisite Gemstones", M * 4, "Exquisite %s", MaterialIconType.gemExquisite, ENABLE_UNIFICATION | SELF_REFERENCING B[2]), // A regular Gem worth four Dusts. Introduced by TerraFirmaCraft
    gem("Gemstones", M, "%s", MaterialIconType.gem, ENABLE_UNIFICATION | SELF_REFERENCING B[2]), // A regular Gem worth one Dust. Introduced by Eloraam

    dustTiny("Tiny Dusts", M / 9, "Tiny Pile of %s Dust", MaterialIconType.dustTiny, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[0] | B[1] | B[2] | B[3]), // 1/9th of a Dust.
    dustSmall("Small Dusts", M / 4, "Small Pile of %s Dust", MaterialIconType.dustSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[0] | B[1] | B[2] | B[3]), // 1/4th of a Dust.
    dustImpure("Impure Dusts", M, "Impure Pile of %s Dust", MaterialIconType.dustImpure, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]), // Dust with impurities. 1 Unit of Main Material and 1/9 - 1/4 Unit of secondary Material
    dustRefined("Refined Dusts", M, "Refined Pile of %s Dust", MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    dustPure("Purified Dusts", M, "Purified Pile of %s Dust", MaterialIconType.dustPure, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[3]),
    dust("Dusts", M, "%s Dust", MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[0] | B[1] | B[2] | B[3]), // Pure Dust worth of one Ingot or Gem. Introduced by Alblaka.

    nugget("Nuggets", M / 9, "%s Nugget", MaterialIconType.nugget, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[1]), // A Nugget. Introduced by Eloraam

    plateAlloy("Alloy Plates", -1, "%s", MaterialIconType.plate, ENABLE_UNIFICATION | DISALLOW_RECYCLING B[1]), // Special Alloys have this prefix.
    plateDense("Dense Plates", M * 9, "Dense %s Plate", MaterialIconType.plateDense, ENABLE_UNIFICATION B[1]), // 9 Plates combined in one Item.
    plateQuintuple("5x Plates", M * 5, "Quintuple %s Plate", MaterialIconType.plateQuintuple, ENABLE_UNIFICATION B[1]),
    plateQuadruple("4x Plates", M * 4, "Quadruple %s Plate", MaterialIconType.plateQuadruple, ENABLE_UNIFICATION B[1]),
    plateTriple("3x Plates", M * 3, "Triple %s Plate", MaterialIconType.plateTriple, ENABLE_UNIFICATION B[1]),
    plateDouble("2x Plates", M * 2, "Double %s Plate", MaterialIconType.plateDouble, ENABLE_UNIFICATION B[1]),
    plate("Plates", M, "%s Plate", MaterialIconType.plate, ENABLE_UNIFICATION B[1] | B[2]), // Regular Plate made of one Ingot/Dust. Introduced by Calclavia

    foil("Foils", M / 4, "%s Foil", MaterialIconType.foil, ENABLE_UNIFICATION B[1]), // Foil made of 1/4 Ingot/Dust.

    stickLong("Long Sticks/Rods", M, "Long %s Rod", MaterialIconType.stickLong, ENABLE_UNIFICATION B[1] | B[2]), // Stick made of an Ingot.
    stick("Sticks/Rods", M / 2, "%s Rod", MaterialIconType.stick, ENABLE_UNIFICATION B[1] | B[2]), // Stick made of half an Ingot. Introduced by Eloraam

    round("Rounds", M / 9, "%s Round", MaterialIconType.round, ENABLE_UNIFICATION B[1]), // consisting out of one Nugget.

    bolt("Bolts", M / 8, "%s Bolt", MaterialIconType.bolt, ENABLE_UNIFICATION B[1] | B[2]), // consisting out of 1/8 Ingot or 1/4 Stick.

    comb("Combs", M, "%s Comb", null DISALLOW_RECYCLING B[1] | B[2]), // contain dusts

    screw("Screws", M / 9, "%s Screw", MaterialIconType.screw, ENABLE_UNIFICATION B[1] | B[2]), // consisting out of a Bolt.

    ring("Rings", M / 4, "%s Ring", MaterialIconType.ring, ENABLE_UNIFICATION B[1]), // consisting out of 1/2 Stick.

    springSmall("Small Springs", M / 4, "Small %s Spring", MaterialIconType.springSmall, ENABLE_UNIFICATION B[1]), // consisting out of 1 Fine Wire.

    spring("Springs", M, "%s Spring", MaterialIconType.spring, ENABLE_UNIFICATION B[1]), // consisting out of 2 Sticks.

    wireFine("Fine Wires", M / 8, "Fine %s Wire", MaterialIconType.wireFine, ENABLE_UNIFICATION B[1]), // consisting out of 1/8 Ingot or 1/4 Wire.

    rotor("Rotors", M * 4 + M / 4, "%s Rotor", MaterialIconType.rotor, ENABLE_UNIFICATION B[7]), // consisting out of 4 Plates, 1 Ring and 1 Screw.

    gearGtSmall("Small Gears", M, "Small %s Gear", MaterialIconType.gearGtSmall, ENABLE_UNIFICATION B[7]),
    gearGt("Gears", M * 4, "%s Gear", MaterialIconType.gearGt, ENABLE_UNIFICATION B[7]), // Introduced by me because BuildCraft has ruined the gear Prefix...

    lens("Lenses", (M * 3) / 4, "%s Lens", MaterialIconType.lens, ENABLE_UNIFICATION B[2]), // 3/4 of a Plate or Gem used to shape a Lense. Normally only used on Transparent Materials.

    cellPlasma("Cells of Plasma", M, "%s Plasma Cell", MaterialIconType.cellPlasma, ENABLE_UNIFICATION | SELF_REFERENCING, FLUID_CONTAINER | DISALLOW_RECYCLING B[5]), // Hot Cell full of Plasma, which can be used in the Plasma Generator.
    cell("Cells", M, "%s Cell", MaterialIconType.cell, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER B[4] | B[8]), // Regular Gas/Fluid Cell. Introduced by Calclavia

    bucket("Buckets", M, "%s Bucket", null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER B[4] | B[8]), // A vanilla Iron Bucket filled with the Material.
    bottle("Bottles", -1, "%s Bottle", null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING B[4] | B[8]), // Glass Bottle containing a Fluid.
    capsule("Capsules", M, "%s Capsule", null SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING B[4] | B[8]),

    crystal("Crystals", M, "%s Crystal", null B[2]),

    bulletGtSmall("Small Bullets", M / 9, "Small %s Bullet", null, ENABLE_UNIFICATION B[6] | B[8]),
    bulletGtMedium("Medium Bullets", M / 6, "Medium %s Bullet", null, ENABLE_UNIFICATION B[6] | B[8]),
    bulletGtLarge("Large Bullets", M / 3, "Large %s Bullet", null, ENABLE_UNIFICATION B[6] | B[8]),

    arrowGtWood("Regular Arrows", M / 4, "%s Arrow", MaterialIconType.arrow, ENABLE_UNIFICATION B[6]), // Arrow made of 1/4 Ingot/Dust + Wooden Stick.
    arrowGtPlastic("Light Arrows", M / 4, "Light %s Arrow", MaterialIconType.arrow, ENABLE_UNIFICATION B[6]), // Arrow made of 1/4 Ingot/Dust + Plastic Stick.
    arrow("Arrows", -1, "%s", MaterialIconType.arrow SELF_REFERENCING | DISALLOW_RECYCLING B[6]),

    toolHeadArrow("Arrow Heads", M / 4, "%s Arrow Head", MaterialIconType.toolHeadArrow, ENABLE_UNIFICATION B[6]), // consisting out of 1/4 Ingot.
    toolHeadSword("Sword Blades", M * 2, "%s Sword Blade", MaterialIconType.toolHeadSword, ENABLE_UNIFICATION B[6]), // consisting out of 2 Ingots.
    toolHeadPickaxe("Pickaxe Heads", M * 3, "%s Pickaxe Head" MaterialIconType.toolHeadPickaxe, ENABLE_UNIFICATION  B[6]), // consisting out of 3 Ingots.
    toolHeadShovel("Shovel Heads", M, "%s Shovel Head", MaterialIconType.toolHeadShovel, ENABLE_UNIFICATION B[6]), // consisting out of 1 Ingots.
    toolHeadUniversalSpade("Universal Spade Heads", M, "%s Universal Spade Head", MaterialIconType.toolHeadUniversalSpade, ENABLE_UNIFICATION B[6]), // consisting out of 1 Ingots.
    toolHeadAxe("Axe Heads", M * 3, "%s Axe Head", MaterialIconType.toolHeadAxe, ENABLE_UNIFICATION B[6]), // consisting out of 3 Ingots.
    toolHeadHoe("Hoe Heads", M * 2, "%s Hoe Head", MaterialIconType.toolHeadHoe, ENABLE_UNIFICATION B[6]), // consisting out of 2 Ingots.
    toolHeadSense("Sense Blades", M * 3, "%s Sense Blade", MaterialIconType.toolHeadSense, ENABLE_UNIFICATION B[6]), // consisting out of 3 Ingots.
    toolHeadFile("File Heads", M * 2, "%s File Head", MaterialIconType.toolHeadFile, ENABLE_UNIFICATION ,B[6]), // consisting out of 2 Ingots.
    toolHeadHammer("Hammer Heads", M * 6, "%s Hammer Head", MaterialIconType.toolHeadHammer, ENABLE_UNIFICATION B[6]), // consisting out of 6 Ingots.
    toolHeadPlow("Plow Heads", M * 4, "%s Plow Head", MaterialIconType.toolHeadPlow, ENABLE_UNIFICATION), // consisting out of 4 Ingots.
    toolHeadSaw("Saw Blades", M * 2, "%s Saw Blade", MaterialIconType.toolHeadSaw, ENABLE_UNIFICATION B[6]), // consisting out of 2 Ingots.
    toolHeadBuzzSaw("Buzzsaw Blades", M * 4, "%s Buzzsaw Blade", MaterialIconType.toolHeadBuzzSaw, ENABLE_UNIFICATION B[6]), // consisting out of 4 Ingots.
    toolHeadScrewdriver("Screwdriver Tips", M, "%s Screwdriver Tip", MaterialIconType.toolHeadScrewdriver, ENABLE_UNIFICATION B[6]), // consisting out of 1 Ingots.
    toolHeadDrill("Drill Tips", M * 4, "%s Drill Tip", MaterialIconType.toolHeadDrill, ENABLE_UNIFICATION B[6]), // consisting out of 4 Ingots.
    toolHeadChainsaw("Chainsaw Tips", M * 2, "%s Chainsaw Tip", MaterialIconType.toolHeadChainsaw, ENABLE_UNIFICATION B[6]), // consisting out of 2 Ingots.
    toolHeadWrench("Wrench Tips", M * 4, "%s Wrench Tip", MaterialIconType.toolHeadWrench, ENABLE_UNIFICATION B[6]), // consisting out of 4 Ingots.

    turbineBlade("Turbine Blades", M * 6, "%s Turbine Blade", MaterialIconType.turbineBlade, ENABLE_UNIFICATION B[6]), // consisting out of 6 Ingots.

    toolSword("Swords", M * 2, "%s", null), // vanilly Sword
    toolPickaxe("Pickaxes", M * 3, "%s", null), // vanilly Pickaxe
    toolShovel("Shovels", M, "%s", null), // vanilly Shovel
    toolAxe("Axes", M * 3, "%s", null), // vanilly Axe
    toolHoe("Hoes", M * 2, "%s", null), // vanilly Hoe
    toolShears("Shears", M * 2, "%s", null), // vanilly Shears
    tool("Tools", -1, "%s", null, DISALLOW_RECYCLING), // toolPot, toolSkillet, toolSaucepan, toolBakeware, toolCuttingboard, toolMortarandpestle, toolMixingbowl, toolJuicer

    compressedCobblestone("9^X Compressed Cobblestones", -1, "%s", null DISALLOW_RECYCLING, ),
    compressedStone("9^X Compressed Stones", -1, "%s", null DISALLOW_RECYCLING ),
    compressedDirt("9^X Compressed Dirt", -1, "%s", null DISALLOW_RECYCLING ),
    compressedGravel("9^X Compressed Gravel",  -1,"%s", null DISALLOW_RECYCLING ),
    compressedSand("9^X Compressed Sand", -1, "%s", null DISALLOW_RECYCLING ),
    compressed("Compressed Materials", M * 2, "Compressed %s", null, ENABLE_UNIFICATION ), // Compressed Material, worth 1 Unit. Introduced by Galacticraft

    glass("Glasses", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),
    paneGlass("Glass Panes", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),
    blockGlass("Glass Blocks", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),

    blockWool("Wool Blocks", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),

    block_("Random Blocks", -1, "%s", MaterialIconType.block DISALLOW_RECYCLING ), // IGNORE
    block("Storage Blocks", M * 9, "Block of %s", MaterialIconType.block, ENABLE_UNIFICATION ), // Storage Block consisting out of 9 Ingots/Gems/Dusts. Introduced by CovertJaguar

    craftingTool("Crafting Tools", -1, "%s", null DISALLOW_RECYCLING ), // Special Prefix used mainly for the Crafting Handler.
    crafting("Crafting Ingredients", -1, "%s", null DISALLOW_RECYCLING ), // Special Prefix used mainly for the Crafting Handler.
    craft("Crafting Stuff?", -1, "%s", null DISALLOW_RECYCLING ), // Special Prefix used mainly for the Crafting Handler.

    log("Logs", -1, "%s", null DISALLOW_RECYCLING ), // Prefix used for Logs. Usually as "logWood". Introduced by Eloraam
    slab("Slabs", -1, "%s", null DISALLOW_RECYCLING ), // Prefix used for Slabs. Usually as "slabWood" or "slabStone". Introduced by SirSengir
    stair("Stairs", -1, "%s", null DISALLOW_RECYCLING ), // Prefix used for Stairs. Usually as "stairWood" or "stairStone". Introduced by SirSengir
    fence("Fences", -1, "%s", null DISALLOW_RECYCLING ), // Prefix used for Fences. Usually as "fenceWood". Introduced by Forge
    plank("Planks", -1, "%s", null DISALLOW_RECYCLING ), // Prefix for Planks. Usually "plankWood". Introduced by Eloraam
    treeSapling("Saplings", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Prefix for Saplings.
    treeLeaves("Leaves", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Prefix for Leaves.
    tree("Tree Parts", -1, "%s", null DISALLOW_RECYCLING ), // Prefix for Tree Parts.
    stoneCobble("Cobblestones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Cobblestone Prefix for all Cobblestones.
    stoneSmooth("Smoothstones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Smoothstone Prefix.
    stoneMossyBricks("mossy Stone Bricks", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Mossy Stone Bricks.
    stoneMossy("Mossy Stones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Mossy Cobble.
    stoneBricks("Stone Bricks", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Stone Bricks.
    stoneCracked("Cracked Stones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Cracked Bricks.
    stoneChiseled("Chiseled Stones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Chiseled Stone.
    stone("Stones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Prefix to determine which kind of Rock this is.
    cobblestone("Cobblestones", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),
    rock("Rocks", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Prefix to determine which kind of Rock this is.
    record("Records", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ),
    rubble("Rubbles", -1, "%s", null, ENABLE_UNIFICATION | SELF_REFERENCING | DISALLOW_RECYCLING ),
    scraps("Scraps", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING ),
    scrap("Scraps", -1, "%s", null DISALLOW_RECYCLING ),

    book("Books", -1, "%s", null DISALLOW_RECYCLING ), // Used for Books of any kind.
    paper("Papers", -1, "%s", null DISALLOW_RECYCLING ), // Used for Papers of any kind.
    dye("Dyes", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Used for the 16 dyes. Introduced by Eloraam
    stainedClay("Stained Clays", -1, "%s", null SELF_REFERENCING | DISALLOW_RECYCLING ), // Used for the 16 colors of Stained Clay. Introduced by Forge
    armorHelmet("Helmets", M * 5, "%s", null B[6]), // vanilly Helmet
    armorChestplate("Chestplates", M * 8, "%s", null B[6]), // vanilly Chestplate
    armorLeggings("Leggings", M * 7, "%s", null B[6]), // vanilly Pants
    armorBoots("Boots", M * 4, "%s", null B[6]), // vanilly Boots
    armor("Armor Parts", -1, "%s", null DISALLOW_RECYCLING B[6]),
    frameGt("Frame Boxes", M * 2, "%s", null, ENABLE_UNIFICATION ),
    pipeTiny("Tiny Pipes", M / 2, "Tiny %s Pipe", MaterialIconType.pipeTiny, ENABLE_UNIFICATION ),
    pipeSmall("Small Pipes", M, "Small %s Pipe", MaterialIconType.pipeSmall, ENABLE_UNIFICATION ),
    pipeMedium("Medium Pipes", M * 3, "Medium %s Pipe", MaterialIconType.pipeMedium, ENABLE_UNIFICATION ),
    pipeLarge("Large pipes", M * 6, "Large %s Pipe", MaterialIconType.pipeLarge, ENABLE_UNIFICATION ),
    pipeHuge("Huge Pipes", M * 12, "Huge %s Pipe", MaterialIconType.pipeHuge, ENABLE_UNIFICATION ),
    pipeRestrictiveTiny("Tiny Restrictive Pipes", M / 2, "Tiny Restrictive %s Pipe", null, ENABLE_UNIFICATION ),
    pipeRestrictiveSmall("Small Restrictive Pipes", M, "Small Restrictive %s Pipe", null, ENABLE_UNIFICATION ),
    pipeRestrictiveMedium("Medium Restrictive Pipes", M * 3, "Medium Restrictive %s Pipe", null, ENABLE_UNIFICATION ),
    pipeRestrictiveLarge("Large Restrictive Pipes", M * 6, "Large Restrictive %s Pipe", null, ENABLE_UNIFICATION ),
    pipeRestrictiveHuge("Huge Restrictive Pipes", M * 12, "Huge Restrictive %s Pipe", null, ENABLE_UNIFICATION ),
    pipe("Pipes", -1, "%s Pipe", null DISALLOW_RECYCLING ),

    wireGt16("16x Wires", M * 8, "16x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION ),
    wireGt12("12x Wires", M * 6, "12x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION ),
    wireGt08("8x Wires", M * 4, "8x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION  , ),
    wireGt04("4x Wires", M * 2, "4x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION ),
    wireGt02("2x Wires", M, "2x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION ),
    wireGt01("1x Wires", M / 2, "1x %s Wire", MaterialIconType.wire, ENABLE_UNIFICATION ),
    cableGt12("12x Cables", M * 6, "12x %s Cable", null, ENABLE_UNIFICATION ),
    cableGt08("8x Cables", M * 4, "8x %s Cable", null, ENABLE_UNIFICATION ),
    cableGt04("4x Cables", M * 2, "4x %s Cable", null, ENABLE_UNIFICATION ),
    cableGt02("2x Cables", M, "2x %s Cable", null, ENABLE_UNIFICATION ),
    cableGt01("1x Cables", M / 2, "1x %s Cable", null, ENABLE_UNIFICATION  ),

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
    batterySingleUse("Single Use Batteries", -1, "%s", null DISALLOW_RECYCLING ),
    battery("Reusable Batteries", -1, "%s", null DISALLOW_RECYCLING ), // Introduced by Calclavia
    circuit("Circuits", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING ), // Introduced by Calclavia
    chipset("Chipsets", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING ), // Introduced by Buildcraft
    computer("Computers", -1, "%s", null, ENABLE_UNIFICATION | DISALLOW_RECYCLING ); // A whole Computer. "computerMaster" = ComputerCube


    static {
        ingotHot.heatDamage = 3.0F;
        cellPlasma.heatDamage = 6.0F;

        ingotQuintuple.defaultStackSize = 12;
        ingotQuadruple.defaultStackSize = 16;
        ingotTriple.defaultStackSize = 21;
        ingotDouble.defaultStackSize = 32;
        ingotHot.defaultStackSize = 16;

        gemFlawless.defaultStackSize = 32;
        gemExquisite.defaultStackSize = 16;

        plateDense.defaultStackSize = 8;
        plateQuintuple.defaultStackSize = 12;
        plateQuadruple.defaultStackSize = 16;
        plateTriple.defaultStackSize = 21;
        plateDouble.defaultStackSize = 32;

        rotor.defaultStackSize = 16;
        gearGt.defaultStackSize = 16;

        bucket.defaultStackSize = 16;
        bottle.defaultStackSize = 16;
        capsule.defaultStackSize = 16;

        toolHeadSword.defaultStackSize = 16;
        toolHeadPickaxe.defaultStackSize = 16;
        toolHeadShovel.defaultStackSize = 16;
        toolHeadUniversalSpade.defaultStackSize = 16;
        toolHeadAxe.defaultStackSize = 16;
        toolHeadHoe.defaultStackSize = 16;
        toolHeadSense.defaultStackSize = 16;
        toolHeadFile.defaultStackSize = 16;
        toolHeadHammer.defaultStackSize = 16;
        toolHeadPlow.defaultStackSize = 16;
        toolHeadSaw.defaultStackSize = 16;
        toolHeadBuzzSaw.defaultStackSize = 16;
        toolHeadScrewdriver.defaultStackSize = 16;
        toolHeadDrill.defaultStackSize = 16;
        toolHeadChainsaw.defaultStackSize = 16;
        toolHeadWrench.defaultStackSize = 16;

        toolSword.defaultStackSize = 1;
        toolPickaxe.defaultStackSize = 1;
        toolShovel.defaultStackSize = 1;
        toolAxe.defaultStackSize = 1;
        toolHoe.defaultStackSize = 1;
        toolShears.defaultStackSize = 1;
        tool.defaultStackSize = 1;

        record.defaultStackSize = 1;

        armorHelmet.defaultStackSize = 1;
        armorChestplate.defaultStackSize = 1;
        armorLeggings.defaultStackSize = 1;
        armorBoots.defaultStackSize = 1;
        armor.defaultStackSize = 1;

        block.ignoreMaterials(Materials.Ice, Materials.Snow, Materials.Concrete, Materials.Glass, Materials.Glowstone, Materials.Marble, Materials.Quartz, Materials.CertusQuartz);

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

        bucket.mNotGeneratedItems.add(Materials.Lava);
        bucket.mNotGeneratedItems.add(Materials.Milk);
        bucket.mNotGeneratedItems.add(Materials.Water);
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

        toolHeadFile.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        toolHeadSaw.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        toolHeadDrill.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        toolHeadChainsaw.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        toolHeadWrench.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        toolHeadBuzzSaw.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));
        turbineBlade.generationCondition = new ICondition.And<ISubTagContainer>(new ICondition.Not<>(SubTag.NO_SMASHING), new ICondition.Not<>(SubTag.BOUNCY));

        rotor.generationCondition = new ICondition.Nor<ISubTagContainer>(SubTag.CRYSTAL, SubTag.STONE, SubTag.BOUNCY);

        spring.generationCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));
        springSmall.generationCondition = new ICondition.Or<ISubTagContainer>(SubTag.STRETCHY, SubTag.BOUNCY, new ICondition.Not<ISubTagContainer>(SubTag.NO_SMASHING));

        gemChipped.generationCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<>(SubTag.QUARTZ), new ICondition.Not<>(SubTag.PEARL), new ICondition.Not<>(SubTag.MAGICAL));
        gemFlawed.generationCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<>(SubTag.QUARTZ), new ICondition.Not<>(SubTag.PEARL), new ICondition.Not<>(SubTag.MAGICAL));
        gemFlawless.generationCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<>(SubTag.QUARTZ), new ICondition.Not<>(SubTag.PEARL), new ICondition.Not<>(SubTag.MAGICAL));
        gemExquisite.generationCondition = new ICondition.And<>(SubTag.TRANSPARENT, SubTag.CRYSTAL, new ICondition.Not<>(SubTag.QUARTZ), new ICondition.Not<>(SubTag.PEARL), new ICondition.Not<>(SubTag.MAGICAL));

        lens.generationCondition = new ICondition.Or<>(SubTag.MAGICAL, new ICondition.And<>(SubTag.TRANSPARENT, SubTag.HAS_COLOR));

        plateDouble.generationCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateTriple.generationCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateQuadruple.generationCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));
        plateQuintuple.generationCondition = new ICondition.Or<>(SubTag.PAPER, new ICondition.Not<>(SubTag.NO_SMASHING));

        plateDense.generationCondition = new ICondition.Not<>(SubTag.NO_SMASHING);

        ingotDouble.generationCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotTriple.generationCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotQuadruple.generationCondition = new ICondition.Not<>(SubTag.NO_SMASHING);
        ingotQuintuple.generationCondition = new ICondition.Not<>(SubTag.NO_SMASHING);

        wireFine.generationCondition = SubTag.METAL;

        //-----

        pipeRestrictiveTiny.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount);
        pipeRestrictiveSmall.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount * 2);
        pipeRestrictiveMedium.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount * 3);
        pipeRestrictiveLarge.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount * 4);
        pipeRestrictiveHuge.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount * 5);

        cableGt12.secondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.materialAmount * 4);
        cableGt08.secondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.materialAmount * 3);
        cableGt04.secondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.materialAmount * 2);
        cableGt02.secondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.materialAmount);
        cableGt01.secondaryMaterial = new MaterialStack(Materials.Ash, dustSmall.materialAmount);

        bucket.secondaryMaterial = new MaterialStack(Materials.Iron, ingot.materialAmount * 3);
        cell.secondaryMaterial = new MaterialStack(Materials.Tin, plate.materialAmount * 2);
        cellPlasma.secondaryMaterial = new MaterialStack(Materials.Tin, plate.materialAmount * 2);

        oreRedgranite.secondaryMaterial = new MaterialStack(Materials.GraniteRed, dust.materialAmount);
        oreBlackgranite.secondaryMaterial = new MaterialStack(Materials.GraniteBlack, dust.materialAmount);

        oreBasalt.secondaryMaterial = new MaterialStack(Materials.Basalt, dust.materialAmount);
        oreMarble.secondaryMaterial = new MaterialStack(Materials.Marble, dust.materialAmount);

        oreSand.secondaryMaterial = new MaterialStack(Materials.SiliconDioxide, dustTiny.materialAmount);
        oreGravel.secondaryMaterial = new MaterialStack(Materials.Flint, dustTiny.materialAmount);

        oreNetherrack.secondaryMaterial = new MaterialStack(Materials.Netherrack, dust.materialAmount);
        oreNether.secondaryMaterial = new MaterialStack(Materials.Netherrack, dust.materialAmount);

        oreEndstone.secondaryMaterial = new MaterialStack(Materials.Endstone, dust.materialAmount);
        oreEnd.secondaryMaterial = new MaterialStack(Materials.Endstone, dust.materialAmount);

        oreMarble.secondaryMaterial = new MaterialStack(Materials.Marble, dust.materialAmount);
        oreBasalt.secondaryMaterial = new MaterialStack(Materials.Basalt, dust.materialAmount);

        oreDense.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount);
        orePoor.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount * 2);
        oreSmall.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount * 2);
        oreNormal.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount * 2);
        oreRich.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount * 2);
        ore.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount);

        crushed.secondaryMaterial = new MaterialStack(Materials.Stone, dust.materialAmount);

        toolHeadDrill.secondaryMaterial = new MaterialStack(Materials.Steel, plate.materialAmount * 4);
        toolHeadChainsaw.secondaryMaterial = new MaterialStack(Materials.Steel, plate.materialAmount * 4 + ring.materialAmount * 2);
        toolHeadWrench.secondaryMaterial = new MaterialStack(Materials.Steel, ring.materialAmount + screw.materialAmount * 2);

        arrowGtWood.secondaryMaterial = new MaterialStack(Materials.Wood, stick.materialAmount);
        arrowGtPlastic.secondaryMaterial = new MaterialStack(Materials.Plastic, stick.materialAmount);

        bulletGtSmall.secondaryMaterial = new MaterialStack(Materials.Brass, ingot.materialAmount / 9);
        bulletGtMedium.secondaryMaterial = new MaterialStack(Materials.Brass, ingot.materialAmount / 6);
        bulletGtLarge.secondaryMaterial = new MaterialStack(Materials.Brass, ingot.materialAmount / 3);
    }

    private static ICondition<Material> hasFlag(int generationFlags) {
        return (material) -> material.hasFlag(generationFlags);
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