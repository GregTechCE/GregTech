package gregtech.api.unification.ore;

import com.google.common.base.Preconditions;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.Condition;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import ic2.core.ref.ItemName;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.GENERATE_LENSE;
import static gregtech.api.unification.material.type.MetalMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_GEAR;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.GENERATE_ROD;
import static gregtech.api.unification.ore.OrePrefix.Conditions.*;
import static gregtech.api.unification.ore.OrePrefix.Flags.*;

public enum OrePrefix {

    oreBlackgranite("Black Granite Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreRedgranite("Red Granite Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreMarble("Marble Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreBasalt("Basalt Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    oreSand("Sand Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // In case of an Sand-Ores Mod. Ore -> Material is a Oneway Operation!
    oreGravel("Gravel Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // In case of an Gravel-Ores Mod. Ore -> Material is a Oneway Operation!

    oreNetherrack("Netherrack Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreNether("Nether Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of the Nether-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!

    oreDense("Dense Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of the Dense-Ores Mod. Causes Ores to double. Ore -> Material is a Oneway Operation!
    oreRich("Rich Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of TFC
    oreNormal("Normal Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of TFC
    oreSmall("Small Ores", -1, null, MaterialIconType.oreSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // Prefix of Railcraft.
    orePoor("Poor Ores", -1, null, MaterialIconType.oreSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Prefix of Railcraft.

    oreEndstone("Endstone Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!
    oreEnd("End Ores", -1,null,  MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // In case of an End-Ores Mod. Ore -> Material is a Oneway Operation!

    ore("Ores", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // Regular Ore Prefix. Ore -> Material is a Oneway Operation! Introduced by Eloraam

    crushedCentrifuged("Centrifuged Ores", -1, null, MaterialIconType.crushedCentrifuged, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)),
    crushedPurified("Purified Ores", -1, null, MaterialIconType.crushedPurified, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)),
    crushed("Crushed Ores", -1, null, MaterialIconType.crushed, ENABLE_UNIFICATION | DISALLOW_RECYCLING, (mat) -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)),

    shard("Crystallised Shards", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Introduced by Mekanism
    clump("Clumps", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    reduced("Reduced Gravels", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    crystalline("Crystallised Metals", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),

    cleanGravel("Clean Gravels", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    dirtyGravel("Dirty Gravels", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),

    ingotHot("Hot Ingots", M, null, MaterialIconType.ingotHot, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> (mat instanceof MetalMaterial) && ((MetalMaterial) mat).blastFurnaceTemperature > 1750), // A hot Ingot, which has to be cooled down by a Vacuum Freezer.
    ingot("Ingots", M, null, MaterialIconType.ingot, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof MetalMaterial), // A regular Ingot. Introduced by Eloraam

    gem("Gemstones", M, null, MaterialIconType.gem, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial), // A regular Gem worth one Dust. Introduced by Eloraam
    gemChipped("Chipped Gemstones", M / 4, null, MaterialIconType.gemChipped, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial && gem.doGenerateItem(mat)), // A regular Gem worth one small Dust. Introduced by TerraFirmaCraft
    gemFlawed("Flawed Gemstones", M / 2, null, MaterialIconType.gemFlawed, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial && gem.doGenerateItem(mat)), // A regular Gem worth two small Dusts. Introduced by TerraFirmaCraft
    gemFlawless("Flawless Gemstones", M * 2, null, MaterialIconType.gemFlawless, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial && gem.doGenerateItem(mat)), // A regular Gem worth two Dusts. Introduced by TerraFirmaCraft
    gemExquisite("Exquisite Gemstones", M * 4, null, MaterialIconType.gemExquisite, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial && gem.doGenerateItem(mat)), // A regular Gem worth four Dusts. Introduced by TerraFirmaCraft

    dustTiny("Tiny Dusts", M / 9, null, MaterialIconType.dustTiny, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // 1/9th of a Dust.
    dustSmall("Small Dusts", M / 4, null, MaterialIconType.dustSmall, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // 1/4th of a Dust.
    dustImpure("Impure Dusts", M, null, MaterialIconType.dustImpure, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)), // Dust with impurities. 1 Unit of Main Material and 1/9 - 1/4 Unit of secondary Material
    dustRefined("Refined Dusts", M, null, MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    dustPure("Purified Dusts", M, null, MaterialIconType.dustPure, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_ORE)),
    dust("Dusts", M, null, MaterialIconType.dust, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof DustMaterial), // Pure Dust worth of one Ingot or Gem. Introduced by Alblaka.

    nugget("Nuggets", M / 9, null, MaterialIconType.nugget, ENABLE_UNIFICATION | DISALLOW_RECYCLING, mat -> mat instanceof MetalMaterial), // A Nugget. Introduced by Eloraam

    plateDense("Dense Plates", M * 9, null, MaterialIconType.plateDense, ENABLE_UNIFICATION, and(mat -> mat instanceof DustMaterial, hasFlag(GENERATE_PLATE | GENERATE_DENSE), noFlag(NO_SMASHING))), // 9 Plates combined in one Item.
    plate("Plates", M, null, MaterialIconType.plate, ENABLE_UNIFICATION, mat -> mat instanceof DustMaterial && mat.hasFlag(GENERATE_PLATE)), // Regular Plate made of one Ingot/Dust. Introduced by Calclavia

    foil("Foils", M / 4, null, MaterialIconType.foil, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_FOIL)), // Foil made of 1/4 Ingot/Dust.

    stickLong("Long Sticks/Rods", M, null, MaterialIconType.stickLong, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_ROD)), // Stick made of an Ingot.
    stick("Sticks/Rods", M / 2, null, MaterialIconType.stick, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_ROD)), // Stick made of half an Ingot. Introduced by Eloraam

    round("Rounds", M / 9, null, MaterialIconType.round, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_ROUNDS)), // consisting out of one Nugget.

    bolt("Bolts", M / 8, null, MaterialIconType.bolt, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_BOLT_SCREW)), // consisting out of 1/8 Ingot or 1/4 Stick.

    comb("Combs", M, null, null, DISALLOW_RECYCLING, null), // contain dusts

    screw("Screws", M / 9, null, MaterialIconType.screw, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_BOLT_SCREW)), // consisting out of a Bolt.

    ring("Rings", M / 4, null, MaterialIconType.ring, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_RING)), // consisting out of 1/2 Stick.

    springSmall("Small Springs", M / 4, null, MaterialIconType.springSmall, ENABLE_UNIFICATION, and(mat -> mat instanceof MetalMaterial, hasFlag(GENERATE_SPRING_SMALL), noFlag(NO_SMASHING))), // consisting out of 1 Fine Wire.

    spring("Springs", M, null, MaterialIconType.spring, ENABLE_UNIFICATION, and(mat -> mat instanceof MetalMaterial, hasFlag(GENERATE_SPRING), noFlag(NO_SMASHING))), // consisting out of 2 Sticks.

    wireFine("Fine Wires", M / 8, null, MaterialIconType.wireFine, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_FINE_WIRE)), // consisting out of 1/8 Ingot or 1/4 Wire.

    rotor("Rotors", M * 4 + M / 4, null, MaterialIconType.rotor, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_ROTOR)), // consisting out of 4 Plates, 1 Ring and 1 Screw.

    gearSmall("Small Gears", M, null, MaterialIconType.gearSmall, ENABLE_UNIFICATION, mat -> mat instanceof MetalMaterial && mat.hasFlag(GENERATE_SMALL_GEAR)),
    gear("Gears", M * 4, null, MaterialIconType.gear, ENABLE_UNIFICATION, mat -> mat instanceof SolidMaterial && mat.hasFlag(GENERATE_GEAR)), // Introduced by me because BuildCraft has ruined the gear Prefix...

    lens("Lenses", (M * 3) / 4, null, MaterialIconType.lens, ENABLE_UNIFICATION, mat -> mat instanceof GemMaterial && mat.hasFlag(GENERATE_LENSE)), // 3/4 of a Plate or Gem used to shape a Lense. Normally only used on Transparent Materials.

    cellPlasma("Cells of Plasma", M, MarkerMaterials.Empty, MaterialIconType.cellPlasma, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING, mat -> mat instanceof FluidMaterial && ((FluidMaterial) mat).shouldGeneratePlasma()), // Hot Cell full of Plasma, which can be used in the Plasma Generator.
    cell("Cells", M, MarkerMaterials.Empty, MaterialIconType.cell, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER, null), // Regular Gas/Fluid Cell. Introduced by Calclavia

    bucket("Buckets", M, MarkerMaterials.Empty, null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER, null), // A vanilla Iron Bucket filled with the Material.
    bottle("Bottles", -1, MarkerMaterials.Empty, null, ENABLE_UNIFICATION | SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING, null), // Glass Bottle containing a Fluid.
    capsule("Capsules", M, MarkerMaterials.Empty, null, SELF_REFERENCING | FLUID_CONTAINER | DISALLOW_RECYCLING, null),

    crystal("Crystals", M, null, null, 0, null),

    toolHeadSword("Sword Blades", M * 2, null, MaterialIconType.toolHeadSword, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 2 Ingots.
    toolHeadPickaxe("Pickaxe Heads", M * 3, null, MaterialIconType.toolHeadPickaxe, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 3 Ingots.
    toolHeadShovel("Shovel Heads", M, null, MaterialIconType.toolHeadShovel, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 1 Ingots.
    toolHeadUniversalSpade("Universal Spade Heads", M, null, MaterialIconType.toolHeadUniversalSpade, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 1 Ingots.
    toolHeadAxe("Axe Heads", M * 3, null, MaterialIconType.toolHeadAxe, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 3 Ingots.
    toolHeadHoe("Hoe Heads", M * 2, null, MaterialIconType.toolHeadHoe, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 2 Ingots.
    toolHeadSense("Sense Blades", M * 3, null, MaterialIconType.toolHeadSense, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 3 Ingots.
    toolHeadFile("File Heads", M * 2, null, MaterialIconType.toolHeadFile, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 2 Ingots.
    toolHeadHammer("Hammer Heads", M * 6, null, MaterialIconType.toolHeadHammer, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 6 Ingots.
    toolHeadPlow("Plow Heads", M * 4, null, MaterialIconType.toolHeadPlow, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 4 Ingots.
    toolHeadSaw("Saw Blades", M * 2, null, MaterialIconType.toolHeadSaw, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 2 Ingots.
    toolHeadBuzzSaw("Buzzsaw Blades", M * 4, null, MaterialIconType.toolHeadBuzzSaw, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 4 Ingots.
    toolHeadScrewdriver("Screwdriver Tips", M, null, MaterialIconType.toolHeadScrewdriver, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 1 Ingots.
    toolHeadDrill("Drill Tips", M * 4, null, MaterialIconType.toolHeadDrill, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 4 Ingots.
    toolHeadChainsaw("Chainsaw Tips", M * 2, null, MaterialIconType.toolHeadChainsaw, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 2 Ingots.
    toolHeadWrench("Wrench Tips", M * 4, null, MaterialIconType.toolHeadWrench, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 4 Ingots.

    turbineBlade("Turbine Blades", M * 6, null, MaterialIconType.turbineBlade, ENABLE_UNIFICATION, and(isToolMaterial, noFlag(NO_SMASHING))), // consisting out of 6 Ingots.

    toolSword("Swords", M * 2, null, null, 0, null), // vanilly Sword
    toolPickaxe("Pickaxes", M * 3, null, null, 0, null), // vanilly Pickaxe
    toolShovel("Shovels", M, null, null, 0, null), // vanilly Shovel
    toolAxe("Axes", M * 3, null, null, 0, null), // vanilly Axe
    toolHoe("Hoes", M * 2, null, null, 0, null), // vanilly Hoe
    toolShears("Shears", M * 2, null, null, 0, null), // vanilly Shears
    tool("Tools", -1, null, null, DISALLOW_RECYCLING, null), // toolPot, toolSkillet, toolSaucepan, toolBakeware, toolCuttingboard, toolMortarandpestle, toolMixingbowl, toolJuicer

    compressedCobblestone("9^X Compressed Cobblestones", -1, null, null, DISALLOW_RECYCLING, null),
    compressedStone("9^X Compressed Stones", -1, null, null, DISALLOW_RECYCLING, null),
    compressedDirt("9^X Compressed Dirt", -1, null, null, DISALLOW_RECYCLING, null),
    compressedGravel("9^X Compressed Gravel", -1, null, null, DISALLOW_RECYCLING, null),
    compressedSand("9^X Compressed Sand", -1, null, null, DISALLOW_RECYCLING, null),
    compressed("Compressed Materials", M * 2, null, null, ENABLE_UNIFICATION, null), // Compressed Material, worth 1 Unit. Introduced by Galacticraft

    glass("Glasses", -1, Materials.Glass, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    paneGlass("Glass Panes", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    blockGlass("Glass Blocks", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),

    blockWool("Wool Blocks", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),

    block("Storage Blocks", M * 9, null, MaterialIconType.block, ENABLE_UNIFICATION, null), // Storage Block consisting out of 9 Ingots/Gems/Dusts. Introduced by CovertJaguar

    craftingTool("Crafting Tools", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.
    craftingLens("Crafting Ingredients", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.
    crafting("Crafting Ingredients", -1, null, null, DISALLOW_RECYCLING, null), // Special Prefix used mainly for the Crafting Handler.

    log("Logs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Logs. Usually as "logWood". Introduced by Eloraam
    slab("Slabs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Slabs. Usually as "slabWood" or "slabStone". Introduced by SirSengir
    stair("Stairs", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Stairs. Usually as "stairWood" or "stairStone". Introduced by SirSengir
    fence("Fences", -1, null, null, DISALLOW_RECYCLING, null), // Prefix used for Fences. Usually as "fenceWood". Introduced by Forge
    plank("Planks", -1, null, null, DISALLOW_RECYCLING, null), // Prefix for Planks. Usually "plankWood". Introduced by Eloraam
    treeSapling("Saplings", -1, Materials.Wood, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix for Saplings.
    treeLeaves("Leaves", -1, Materials.Wood, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix for Leaves.
    tree("Tree Parts", -1, Materials.Wood, null, DISALLOW_RECYCLING, null), // Prefix for Tree Parts.
    stoneCobble("Cobblestones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Cobblestone Prefix for all Cobblestones.
    stoneSmooth("Smoothstones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Smoothstone Prefix.
    stoneMossyBricks("mossy Stone Bricks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Mossy Stone Bricks.
    stoneMossy("Mossy Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Mossy Cobble.
    stoneBricks("Stone Bricks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Stone Bricks.
    stoneCracked("Cracked Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Cracked Bricks.
    stoneChiseled("Chiseled Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Chiseled Stone.
    stone("Stones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix to determine which kind of Rock this is.
    cobblestone("Cobblestones", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    rock("Rocks", -1, Materials.Stone, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Prefix to determine which kind of Rock this is.
    record("Records", -1, MarkerMaterials.Empty, null, SELF_REFERENCING | DISALLOW_RECYCLING, null),
    rubble("Rubbles", -1, Materials.Stone, null, ENABLE_UNIFICATION | SELF_REFERENCING | DISALLOW_RECYCLING, null),
    scraps("Scraps", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null),
    scrap("Scraps", -1, null, null, DISALLOW_RECYCLING, null),

    book("Books", -1, null, null, DISALLOW_RECYCLING, null), // Used for Books of any kind.
    paper("Papers", -1, null, null, DISALLOW_RECYCLING, null), // Used for Papers of any kind.
    dye("Dyes", -1, null, null, DISALLOW_RECYCLING, null), // Used for the 16 dyes. Introduced by Eloraam
    stainedClay("Stained Clays", -1, MarkerMaterials.Color.Colorless, null, SELF_REFERENCING | DISALLOW_RECYCLING, null), // Used for the 16 colors of Stained Clay. Introduced by Forge
    armorHelmet("Helmets", M * 5, null, null, 0, null), // vanilly Helmet
    armorChestplate("Chestplates", M * 8, null, null, 0, null), // vanilly Chestplate
    armorLeggings("Leggings", M * 7, null, null, 0, null), // vanilly Pants
    armorBoots("Boots", M * 4, null, null, 0, null), // vanilly Boots
    armor("Armor Parts", -1, null, null, DISALLOW_RECYCLING, null),
    frameGt("Frame Boxes", M * 2, null, null, ENABLE_UNIFICATION, null),

    pipeTiny("Tiny Pipes", M / 2, null, MaterialIconType.pipeTiny, ENABLE_UNIFICATION, null),
    pipeSmall("Small Pipes", M, null, MaterialIconType.pipeSmall, ENABLE_UNIFICATION, null),
    pipeMedium("Medium Pipes", M * 3, null, MaterialIconType.pipeMedium, ENABLE_UNIFICATION, null),
    pipeLarge("Large pipes", M * 6, null, MaterialIconType.pipeLarge, ENABLE_UNIFICATION, null),
    pipeHuge("Huge Pipes", M * 12, null, MaterialIconType.pipeHuge, ENABLE_UNIFICATION, null),
    pipeRestrictiveTiny("Tiny Restrictive Pipes", M / 2, null, null, ENABLE_UNIFICATION, null),
    pipeRestrictiveSmall("Small Restrictive Pipes", M, null, null, ENABLE_UNIFICATION, null),
    pipeRestrictiveMedium("Medium Restrictive Pipes", M * 3, null, null, ENABLE_UNIFICATION, null),
    pipeRestrictiveLarge("Large Restrictive Pipes", M * 6, null, null, ENABLE_UNIFICATION, null),
    pipeRestrictiveHuge("Huge Restrictive Pipes", M * 12, null, null, ENABLE_UNIFICATION, null),
    pipe("Pipes", -1, null, null, DISALLOW_RECYCLING, null),

    wireGt16("16x Wires", M * 8, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    wireGt12("12x Wires", M * 6, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    wireGt08("8x Wires", M * 4, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    wireGt04("4x Wires", M * 2, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    wireGt02("2x Wires", M, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    wireGt01("1x Wires", M / 2, null, MaterialIconType.wire, ENABLE_UNIFICATION, null),
    cableGt12("12x Cables", M * 6, null, null, ENABLE_UNIFICATION, null),
    cableGt08("8x Cables", M * 4, null, null, ENABLE_UNIFICATION, null),
    cableGt04("4x Cables", M * 2, null, null, ENABLE_UNIFICATION, null),
    cableGt02("2x Cables", M, null, null, ENABLE_UNIFICATION, null),
    cableGt01("1x Cables", M / 2, null, null, ENABLE_UNIFICATION, null),

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
    batterySingleUse("Single Use Batteries", -1, null, null, DISALLOW_RECYCLING, null),
    battery("Reusable Batteries", -1, null, null, DISALLOW_RECYCLING, null), // Introduced by Calclavia
    circuit("Circuits", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Introduced by Calclavia
    chipset("Chipsets", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null), // Introduced by Buildcraft
    computer("Computers", -1, null, null, ENABLE_UNIFICATION | DISALLOW_RECYCLING, null); // A whole Computer. "computerMaster" = ComputerCube

    public static class Flags {
        public static final long ENABLE_UNIFICATION = GTUtility.createFlag(0);
        public static final long SELF_REFERENCING = GTUtility.createFlag(1);
        public static final long FLUID_CONTAINER = GTUtility.createFlag(2);
        public static final long DISALLOW_RECYCLING = GTUtility.createFlag(3);
    }

	static class Conditions {
		static Condition<Material> isToolMaterial = mat -> mat instanceof SolidMaterial && ((SolidMaterial) mat).toolDurability > 0;
	}

    static {
        bottle.containerItem = new ItemStack(Items.GLASS_BOTTLE);
        bucket.containerItem = new ItemStack(Items.BUCKET);
        cell.containerItem = ModHandler.IC2.getIC2Item(ItemName.fluid_cell, 1);

        ingotHot.heatDamage = 3.0F;
        cellPlasma.heatDamage = 6.0F;

        ingotHot.defaultStackSize = 16;

        gemFlawless.defaultStackSize = 32;
        gemExquisite.defaultStackSize = 16;

        plateDense.defaultStackSize = 8;

        rotor.defaultStackSize = 16;
        gear.defaultStackSize = 16;

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

        gem.ignoredMaterials.add(Materials.Coal);
        gem.ignoredMaterials.add(Materials.Charcoal);
        gem.ignoredMaterials.add(Materials.NetherStar);
        gem.ignoredMaterials.add(Materials.Diamond);
        gem.ignoredMaterials.add(Materials.Emerald);
        gem.ignoredMaterials.add(Materials.NetherQuartz);
        gem.ignoredMaterials.add(Materials.EnderPearl);
        gem.ignoredMaterials.add(Materials.EnderEye);
        gem.ignoredMaterials.add(Materials.Flint);
        gem.ignoredMaterials.add(Materials.Lapis);
        dust.ignoredMaterials.add(Materials.Bone);
        dust.ignoredMaterials.add(Materials.Redstone);
        dust.ignoredMaterials.add(Materials.Glowstone);
        dust.ignoredMaterials.add(Materials.Gunpowder);
        dust.ignoredMaterials.add(Materials.Sugar);
        dust.ignoredMaterials.add(Materials.Blaze);
        stick.ignoredMaterials.add(Materials.Wood);
        stick.ignoredMaterials.add(Materials.Bone);
        stick.ignoredMaterials.add(Materials.Blaze);
        ingot.ignoredMaterials.add(Materials.Iron);
        ingot.ignoredMaterials.add(Materials.Gold);
        ingot.ignoredMaterials.add(Materials.WoodSealed);
        ingot.ignoredMaterials.add(Materials.Wood);
        nugget.ignoredMaterials.add(Materials.Gold);
        plate.ignoredMaterials.add(Materials.Paper);


        bucket.ignoredMaterials.add(Materials.Lava);
        bucket.ignoredMaterials.add(Materials.Milk);
        bucket.ignoredMaterials.add(Materials.Water);
        bottle.ignoredMaterials.add(Materials.Water);
        bottle.ignoredMaterials.add(Materials.Milk);
        block.ignoredMaterials.add(Materials.Iron);
        block.ignoredMaterials.add(Materials.Gold);
        block.ignoredMaterials.add(Materials.Lapis);
        block.ignoredMaterials.add(Materials.Emerald);
        block.ignoredMaterials.add(Materials.Redstone);
        block.ignoredMaterials.add(Materials.Diamond);
        block.ignoredMaterials.add(Materials.Coal);
        block.ignoredMaterials.add(Materials.Glass);
        block.ignoredMaterials.add(Materials.Marble);
        block.ignoredMaterials.add(Materials.GraniteRed);
        block.ignoredMaterials.add(Materials.Stone);
        block.ignoredMaterials.add(Materials.Glowstone);
        block.ignoredMaterials.add(Materials.Endstone);
        block.ignoredMaterials.add(Materials.Wheat);
        block.ignoredMaterials.add(Materials.Oilsands);

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

    }

    @SafeVarargs
    private static Condition<Material> and(Condition<Material>... conditions) {
        return new Condition.And<>(conditions);
    }

    private static Condition<Material> hasFlag(long generationFlags) {
        return (material) -> material.hasFlag(generationFlags);
    }

    private static Condition<Material> noFlag(long generationFlags) {
        return (material) -> !material.hasFlag(generationFlags);
    }

    public final String categoryName;

    public final boolean isUnificationEnabled;
    public final boolean isSelfReferencing;
    public final boolean isRecyclingDisallowed;
    public final boolean isFluidContainer;

    public final @Nullable Condition<Material> generationCondition;
    public final @Nullable MaterialIconType materialIconType;

    public final long materialAmount;

    /**
     * Contains a default material type for self-referencing OrePrefix
     * For self-referencing prefixes, it is always guaranteed for it to be not null
     *
     * NOTE: Ore registrations with self-referencing OrePrefix still can occur with other materials
     */
    public @Nullable Material materialType;

    private final ArrayList<IOreRegistrationHandler> oreProcessingHandlers = new ArrayList<>();
    private final ArrayList<Material> ignoredMaterials = new ArrayList<>();

    public @Nullable ItemStack containerItem = null;
    public byte defaultStackSize = 64;
    public @Nullable MaterialStack secondaryMaterial = null;
    public float heatDamage = 0.0F; // Negative for Frost Damage

    OrePrefix(String categoryName, long materialAmount, Material material, MaterialIconType materialIconType, long flags, Condition<Material> condition) {
        this.categoryName = categoryName;
        this.materialAmount = materialAmount;
        this.isSelfReferencing = (flags & SELF_REFERENCING) != 0;
        this.isUnificationEnabled = (flags & ENABLE_UNIFICATION) != 0;
        this.isRecyclingDisallowed = (flags & DISALLOW_RECYCLING) != 0;
        this.isFluidContainer = (flags & FLUID_CONTAINER) != 0;
        this.materialIconType = materialIconType;
        this.generationCondition = condition;
        if(isSelfReferencing) {
            Preconditions.checkArgument(material != null, "Material is null for self-referencing OrePrefix");
            this.materialType = material;
        }
    }

    public static OrePrefix getPrefix(String prefixName) {
        return getPrefix(prefixName, null);
    }

    public static OrePrefix getPrefix(String prefixName, @Nullable OrePrefix replacement) {
        try {
            return Enum.valueOf(OrePrefix.class, prefixName);
        } catch (IllegalArgumentException invalidPrefixName) {
            return replacement;
        }
    }

    public boolean doGenerateItem(Material material) {
        return !isSelfReferencing && generationCondition != null && materialIconType != null && !ignoredMaterials.contains(material) && generationCondition.isTrue(material);
    }

    public boolean addProcessingHandler(IOreRegistrationHandler processingHandler) {
        Preconditions.checkNotNull(processingHandler);
        return oreProcessingHandlers.add(processingHandler);
    }

    public void processOreRegistration(@Nullable Material material, String modName, SimpleItemStack itemStack) {
        if(isSelfReferencing) {
            if(material == null) {
                material = materialType; //append default material for self-referencing OrePrefix
            }
        } else if(material == null) {
            GTLog.logger.warn(
                    "Mod %s attempted to register %s with ore prefix %s without material, but OrePrefix is not self resolving!",
                    modName, itemStack, this);
            return;
        }
        
        UnificationEntry unificationEntry = new UnificationEntry(this, material);
        for(IOreRegistrationHandler handler : oreProcessingHandlers) {
            handler.registerOre(unificationEntry, modName, itemStack);
        }
    }

    @SideOnly(Side.CLIENT)
    public String getLocalNameForItem(Material material) {
        String unlocalized = "item.material.oreprefix." + this.name();
        String matLocalized = material.getLocalizedName();
        String formatted = I18n.format(unlocalized, matLocalized);
        return formatted.equals(unlocalized) ? matLocalized : formatted;
    }

    public boolean isIgnored(Material material) {
        return ignoredMaterials.contains(material);
    }

}