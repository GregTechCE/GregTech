package gregtech.loaders.preload;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import ic2.core.block.wiring.CableType;
import ic2.core.item.block.ItemCable;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.IngotResourceType;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import static gregtech.api.GTValues.W;
import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public class OreDictionaryLoader implements Runnable {
    
    public void run() {
        GTLog.logger.info("Registering OreDict entries.");

        //Simulating oredict registration for vanilla items.
        //Because forge registers event handlers after registering oredicts for vanilla items
        Multimap<String, Object> oreDicts = LinkedListMultimap.create();

        // tree- and wood-related things
        oreDicts.put("logWood",     new ItemStack(Blocks.LOG, 1, WILDCARD_VALUE));
        oreDicts.put("logWood",     new ItemStack(Blocks.LOG2, 1, WILDCARD_VALUE));
        oreDicts.put("plankWood",   new ItemStack(Blocks.PLANKS, 1, WILDCARD_VALUE));
        oreDicts.put("slabWood",    new ItemStack(Blocks.WOODEN_SLAB, 1, WILDCARD_VALUE));
        oreDicts.put("stairWood",   Blocks.OAK_STAIRS);
        oreDicts.put("stairWood",   Blocks.SPRUCE_STAIRS);
        oreDicts.put("stairWood",   Blocks.BIRCH_STAIRS);
        oreDicts.put("stairWood",   Blocks.JUNGLE_STAIRS);
        oreDicts.put("stairWood",   Blocks.ACACIA_STAIRS);
        oreDicts.put("stairWood",   Blocks.DARK_OAK_STAIRS);
        oreDicts.put("stickWood",   Items.STICK);
        oreDicts.put("treeSapling", new ItemStack(Blocks.SAPLING, 1, WILDCARD_VALUE));
        oreDicts.put("treeLeaves",  new ItemStack(Blocks.LEAVES, 1, WILDCARD_VALUE));
        oreDicts.put("treeLeaves",  new ItemStack(Blocks.LEAVES2, 1, WILDCARD_VALUE));
        oreDicts.put("vine",        Blocks.VINE);

        // Ores
        oreDicts.put("oreGold",     Blocks.GOLD_ORE);
        oreDicts.put("oreIron",     Blocks.IRON_ORE);
        oreDicts.put("oreLapis",    Blocks.LAPIS_ORE);
        oreDicts.put("oreDiamond",  Blocks.DIAMOND_ORE);
        oreDicts.put("oreRedstone", Blocks.REDSTONE_ORE);
        oreDicts.put("oreEmerald",  Blocks.EMERALD_ORE);
        oreDicts.put("oreQuartz",   Blocks.QUARTZ_ORE);
        oreDicts.put("oreCoal",     Blocks.COAL_ORE);

        // ingots/nuggets
        oreDicts.put("ingotIron",     Items.IRON_INGOT);
        oreDicts.put("ingotGold",     Items.GOLD_INGOT);
        oreDicts.put("ingotBrick",    Items.BRICK);
        oreDicts.put("ingotBrickNether", Items.NETHERBRICK);
        oreDicts.put("nuggetGold",  Items.GOLD_NUGGET);
		oreDicts.put("nuggetIron",  Items.IRON_NUGGET);

        // gems and dusts
        oreDicts.put("gemDiamond",  Items.DIAMOND);
        oreDicts.put("gemEmerald",  Items.EMERALD);
        oreDicts.put("gemQuartz",   Items.QUARTZ);
        oreDicts.put("gemPrismarine", Items.PRISMARINE_SHARD);
        oreDicts.put("dustPrismarine", Items.PRISMARINE_CRYSTALS);
        oreDicts.put("dustRedstone",  Items.REDSTONE);
        oreDicts.put("dustGlowstone", Items.GLOWSTONE_DUST);
        oreDicts.put("gemLapis",    new ItemStack(Items.DYE, 1, 4));

        // storage blocks
        oreDicts.put("blockGold",     Blocks.GOLD_BLOCK);
        oreDicts.put("blockIron",     Blocks.IRON_BLOCK);
        oreDicts.put("blockLapis",    Blocks.LAPIS_BLOCK);
        oreDicts.put("blockDiamond",  Blocks.DIAMOND_BLOCK);
        oreDicts.put("blockRedstone", Blocks.REDSTONE_BLOCK);
        oreDicts.put("blockEmerald",  Blocks.EMERALD_BLOCK);
        oreDicts.put("blockQuartz",   Blocks.QUARTZ_BLOCK);
        oreDicts.put("blockCoal",     Blocks.COAL_BLOCK);

        // crops
        oreDicts.put("cropWheat",   Items.WHEAT);
        oreDicts.put("cropPotato",  Items.POTATO);
        oreDicts.put("cropCarrot",  Items.CARROT);
        oreDicts.put("cropNetherWart", Items.NETHER_WART);
        oreDicts.put("sugarcane",   Items.REEDS);
        oreDicts.put("blockCactus", Blocks.CACTUS);

        // misc materials
        oreDicts.put("dye",         new ItemStack(Items.DYE, 1, WILDCARD_VALUE));
        oreDicts.put("paper",       new ItemStack(Items.PAPER));

        // mob drops
        oreDicts.put("slimeball",   Items.SLIME_BALL);
        oreDicts.put("enderpearl",  Items.ENDER_PEARL);
        oreDicts.put("bone",        Items.BONE);
        oreDicts.put("gunpowder",   Items.GUNPOWDER);
        oreDicts.put("string",      Items.STRING);
        oreDicts.put("netherStar",  Items.NETHER_STAR);
        oreDicts.put("leather",     Items.LEATHER);
        oreDicts.put("feather",     Items.FEATHER);
        oreDicts.put("egg",         Items.EGG);

        // records
        oreDicts.put("record",      Items.RECORD_13);
        oreDicts.put("record",      Items.RECORD_CAT);
        oreDicts.put("record",      Items.RECORD_BLOCKS);
        oreDicts.put("record",      Items.RECORD_CHIRP);
        oreDicts.put("record",      Items.RECORD_FAR);
        oreDicts.put("record",      Items.RECORD_MALL);
        oreDicts.put("record",      Items.RECORD_MELLOHI);
        oreDicts.put("record",      Items.RECORD_STAL);
        oreDicts.put("record",      Items.RECORD_STRAD);
        oreDicts.put("record",      Items.RECORD_WARD);
        oreDicts.put("record",      Items.RECORD_11);
        oreDicts.put("record",      Items.RECORD_WAIT);

        // blocks
        oreDicts.put("dirt",        Blocks.DIRT);
        oreDicts.put("grass",       Blocks.GRASS);
        oreDicts.put("stone",       Blocks.STONE);
        oreDicts.put("cobblestone", Blocks.COBBLESTONE);
        oreDicts.put("gravel",      Blocks.GRAVEL);
        oreDicts.put("sand",        new ItemStack(Blocks.SAND, 1, WILDCARD_VALUE));
        oreDicts.put("sandstone",   new ItemStack(Blocks.SANDSTONE, 1, WILDCARD_VALUE));
        oreDicts.put("sandstone",   new ItemStack(Blocks.RED_SANDSTONE, 1, WILDCARD_VALUE));
        oreDicts.put("netherrack",  Blocks.NETHERRACK);
        oreDicts.put("obsidian",    Blocks.OBSIDIAN);
        oreDicts.put("glowstone",   Blocks.GLOWSTONE);
        oreDicts.put("endstone",    Blocks.END_STONE);
        oreDicts.put("torch",       Blocks.TORCH);
        oreDicts.put("workbench",   Blocks.CRAFTING_TABLE);
        oreDicts.put("blockSlime",    Blocks.SLIME_BLOCK);
        oreDicts.put("blockPrismarine", new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.EnumType.ROUGH.getMetadata()));
        oreDicts.put("blockPrismarineBrick", new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.EnumType.BRICKS.getMetadata()));
        oreDicts.put("blockPrismarineDark", new ItemStack(Blocks.PRISMARINE, 1, BlockPrismarine.EnumType.DARK.getMetadata()));
        oreDicts.put("stoneGranite",          new ItemStack(Blocks.STONE, 1, 1));
        oreDicts.put("stoneGranitePolished",  new ItemStack(Blocks.STONE, 1, 2));
        oreDicts.put("stoneDiorite",          new ItemStack(Blocks.STONE, 1, 3));
        oreDicts.put("stoneDioritePolished",  new ItemStack(Blocks.STONE, 1, 4));
        oreDicts.put("stoneAndesite",         new ItemStack(Blocks.STONE, 1, 5));
        oreDicts.put("stoneAndesitePolished", new ItemStack(Blocks.STONE, 1, 6));
        oreDicts.put("blockGlassColorless", Blocks.GLASS);
        oreDicts.put("blockGlass",    Blocks.GLASS);
        oreDicts.put("blockGlass",    new ItemStack(Blocks.STAINED_GLASS, 1, WILDCARD_VALUE));
        //blockGlass{Color} is added below with dyes
        oreDicts.put("paneGlassColorless", Blocks.GLASS_PANE);
        oreDicts.put("paneGlass",     Blocks.GLASS_PANE);
        oreDicts.put("paneGlass",     new ItemStack(Blocks.STAINED_GLASS_PANE, 1, WILDCARD_VALUE));
        //paneGlass{Color} is added below with dyes

        // chests
        oreDicts.put("chest",        Blocks.CHEST);
        oreDicts.put("chest",        Blocks.ENDER_CHEST);
        oreDicts.put("chest",        Blocks.TRAPPED_CHEST);
        oreDicts.put("chestWood",    Blocks.CHEST);
        oreDicts.put("chestEnder",   Blocks.ENDER_CHEST);
        oreDicts.put("chestTrapped", Blocks.TRAPPED_CHEST);

        // Register dyes
        String[] dyes =
            {
                "Black",
                "Red",
                "Green",
                "Brown",
                "Blue",
                "Purple",
                "Cyan",
                "LightGray",
                "Gray",
                "Pink",
                "Lime",
                "Yellow",
                "LightBlue",
                "Magenta",
                "Orange",
                "White"
            };

        for(int i = 0; i < 16; i++)
        {
            ItemStack dye = new ItemStack(Items.DYE, 1, i);
            ItemStack block = new ItemStack(Blocks.STAINED_GLASS, 1, 15 - i);
            ItemStack pane = new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 15 - i);

            oreDicts.put("dye" + dyes[i], dye);
            oreDicts.put("blockGlass" + dyes[i], block);
            oreDicts.put("paneGlass"  + dyes[i], pane);
        }

        Multimaps.asMap(oreDicts).forEach((ore, list) -> list.forEach(o -> {
			if (o instanceof ItemStack) {
				OreDictUnifier.onItemRegistration(new OreDictionary.OreRegisterEvent(ore, (ItemStack) o));
			} else if (o instanceof Item) {
				OreDictUnifier.onItemRegistration(new OreDictionary.OreRegisterEvent(ore, new ItemStack((Item) o)));
			} else if (o instanceof Block) {
				OreDictUnifier.onItemRegistration(new OreDictionary.OreRegisterEvent(ore, new ItemStack((Block) o)));
			}
		}));

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, 1), OrePrefix.cell, MarkerMaterials.Empty);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, FluidRegistry.LAVA.getName(), 1), OrePrefix.cell, Materials.Lava);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, FluidRegistry.WATER.getName(), 1), OrePrefix.cell, Materials.Water);
        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "fluid.creosote.cell", 1), OrePrefix.cell, Materials.Creosote);

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, FluidName.uu_matter, 1), OrePrefix.cell, Materials.UUMatter);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.fluid_cell, FluidName.construction_foam, 1), OrePrefix.cell, Materials.ConstructionFoam);

        OreDictUnifier.registerOre(new ItemStack(Items.BUCKET, 1, 0), OrePrefix.bucket, MarkerMaterials.Empty);
        OreDictUnifier.registerOre(new ItemStack(Items.WATER_BUCKET, 1, 0), OrePrefix.bucket, Materials.Water);
        OreDictUnifier.registerOre(new ItemStack(Items.LAVA_BUCKET, 1, 0), OrePrefix.bucket, Materials.Lava);
        OreDictUnifier.registerOre(new ItemStack(Items.MILK_BUCKET, 1, 0), OrePrefix.bucket, Materials.Milk);

        OreDictUnifier.registerOre(new ItemStack(Items.GLASS_BOTTLE, 1, 0), OrePrefix.bottle, MarkerMaterials.Empty);
        OreDictUnifier.registerOre(new ItemStack(Items.POTIONITEM, 1, 0), OrePrefix.bottle, Materials.Water);

        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_ORE, 1), OrePrefix.ore, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_ORE, 1), OrePrefix.ore, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_ORE, 1), OrePrefix.ore, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_ORE, 1), OrePrefix.ore, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_ORE, 1), OrePrefix.ore, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_ORE, 1), OrePrefix.ore, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_ORE, 1), OrePrefix.ore, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_ORE, 1), OrePrefix.ore, Materials.NetherQuartz);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.ingot, IngotResourceType.copper, 1), OrePrefix.ingot, Materials.Copper);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.ingot, IngotResourceType.tin, 1), OrePrefix.ingot, Materials.Tin);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.ingot, IngotResourceType.lead, 1), OrePrefix.ingot, Materials.Lead);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.ingot, IngotResourceType.bronze, 1), OrePrefix.ingot, Materials.Bronze);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.ingot, IngotResourceType.silver, 1), OrePrefix.ingot, Materials.Silver);
        OreDictUnifier.registerOre(new ItemStack(Items.DYE, 1, 4), OrePrefix.gem, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_EYE, 1), OrePrefix.gem, Materials.EnderEye);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_PEARL, 1), OrePrefix.gem, Materials.EnderPearl);
        OreDictUnifier.registerOre(new ItemStack(Items.DIAMOND, 1), OrePrefix.gem, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Items.EMERALD, 1), OrePrefix.gem, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 0), OrePrefix.gem, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 1), OrePrefix.gem, Materials.Charcoal);
        OreDictUnifier.registerOre(new ItemStack(Items.QUARTZ, 1), OrePrefix.gem, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Items.NETHER_STAR, 1), OrePrefix.gem, Materials.NetherStar);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_NUGGET, 1), OrePrefix.nugget, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_INGOT, 1), OrePrefix.ingot, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.IRON_INGOT, 1), OrePrefix.ingot, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1), OrePrefix.plate, Materials.Paper);
        OreDictUnifier.registerOre(new ItemStack(Items.SUGAR, 1), OrePrefix.dust, Materials.Sugar);
        OreDictUnifier.registerOre(new ItemStack(Items.STICK, 1), OrePrefix.stick, Materials.Wood);
        OreDictUnifier.registerOre(new ItemStack(Items.REDSTONE, 1), OrePrefix.dust, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Items.GUNPOWDER, 1), OrePrefix.dust, Materials.Gunpowder);
        OreDictUnifier.registerOre(new ItemStack(Items.GLOWSTONE_DUST, 1), OrePrefix.dust, Materials.Glowstone);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_POWDER, 1), OrePrefix.dust, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_ROD, 1), OrePrefix.stick, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_BLOCK, 1, 0), OrePrefix.block, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_BLOCK, 1, 0), OrePrefix.block, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_BLOCK, 1, 0), OrePrefix.block, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_BLOCK, 1, 0), OrePrefix.block, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_BLOCK, 1, 0), OrePrefix.block, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_BLOCK, 1, 0), OrePrefix.block, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0), OrePrefix.block, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 1), OrePrefix.stone, Materials.GraniteBlack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 2), OrePrefix.stone, Materials.GraniteBlack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 5), OrePrefix.stone, Materials.Andesite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 6), OrePrefix.stone, Materials.Andesite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 3), OrePrefix.stone, Materials.Diorite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 4), OrePrefix.stone, Materials.Diorite);

        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL, 1), "craftingAnvil");
        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1, 0), "craftingAnvil");
        OreDictUnifier.registerOre(ModHandler.getModItem("ThermalExpansion", "sawdust", 1), OrePrefix.dust, Materials.Wood);

        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 6), OrePrefix.stone, Materials.Basalt);
        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 7), OrePrefix.stone, Materials.Marble);
        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "tile.railcraft.brick.abyssal", 1, W), OrePrefix.stone, Materials.Basalt);
        OreDictUnifier.registerOre(ModHandler.getModItem("Railcraft", "tile.railcraft.brick.quarried", 1, W), OrePrefix.stone, Materials.Marble);
        OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN, 1, W), OrePrefix.stone, Materials.Obsidian);
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, W), "stoneSmooth");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, W), "stoneBricks");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 1), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 2), "stoneCracked");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 3), "stoneChiseled");
        OreDictUnifier.registerOre(new ItemStack(Blocks.NETHERRACK, 1, W), OrePrefix.stone, Materials.Netherrack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.END_STONE, 1, W), OrePrefix.stone, Materials.Endstone);

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.re_battery, 1), OrePrefix.battery, MarkerMaterials.Tier.Basic);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.re_battery, 1, W), OrePrefix.battery, MarkerMaterials.Tier.Basic);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.advanced_re_battery, 1, W), OrePrefix.battery, MarkerMaterials.Tier.Advanced);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.energy_crystal, 1, W), OrePrefix.battery, MarkerMaterials.Tier.Elite);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.lapotron_crystal, 1, W), OrePrefix.battery, MarkerMaterials.Tier.Master);
        OreDictUnifier.registerOre(ItemCable.getCable(CableType.copper, 1), "craftingWireCopper");
        OreDictUnifier.registerOre(ItemCable.getCable(CableType.gold, 1), "craftingWireGold");
        OreDictUnifier.registerOre(ItemCable.getCable(CableType.iron, 1), "craftingWireIron");
        OreDictUnifier.registerOre(ItemCable.getCable(CableType.tin, 1), "craftingWireTin");

        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_TORCH, 1, W), "craftingRedstoneTorch");

//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1), "craftingWorkBench");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 16), "craftingWorkBench");

        OreDictUnifier.registerOre(new ItemStack(Blocks.PISTON, 1, W), "craftingPiston");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STICKY_PISTON, 1, W), "craftingPiston");

//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 45), "craftingSafe");
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.personal_chest, 1), "craftingSafe");

        OreDictUnifier.registerOre(new ItemStack(Blocks.CHEST, 1, W), "craftingChest");
        OreDictUnifier.registerOre(new ItemStack(Blocks.TRAPPED_CHEST, 1, W), "craftingChest");

        OreDictUnifier.registerOre(new ItemStack(Blocks.FURNACE, 1, W), "craftingFurnace");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.pump, 1), "craftingPump");
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.magnetizer, 1), "craftingElectromagnet");
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.teleporter, 1), "craftingTeleporter");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.macerator, 1), "craftingMacerator");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 50), "craftingMacerator");


        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.extractor, 1), "craftingExtractor");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 51), "craftingExtractor");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.compressor, 1), "craftingCompressor");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 52), "craftingCompressor");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.recycler, 1), "craftingRecycler");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 53), "craftingRecycler");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.iron_furnace, 1), "craftingIronFurnace");

//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 62), "craftingCentrifuge");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.induction_furnace, 1), "craftingInductionFurnace");


        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.electric_furnace, 1), "craftingElectricFurnace");
//        OreDictUnifier.registerOre(new ItemStack(MetaBlocks.MACHINE, 1, 54), "craftingElectricFurnace");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.generator, 1), "craftingGenerator");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2TEItem(TeBlock.geo_generator, 1), "craftingGeothermalGenerator");

        OreDictUnifier.registerOre(new ItemStack(Items.FEATHER, 1, W), "craftingFeather");

        OreDictUnifier.registerOre(new ItemStack(Items.WHEAT, 1, W), "itemWheat");
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1, W), "paperEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.FILLED_MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "bookEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "bookWritable");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "bookWritten");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "bookEnchanted");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "craftingBook");

        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.circuit, 1), OrePrefix.circuit, MarkerMaterials.Tier.Basic);
        OreDictUnifier.registerOre(ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.advanced_circuit, 1), OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
    }
}
