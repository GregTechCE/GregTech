package gregtech.loaders.postload;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.worldgen.GTWorldGen_Asteroid;
import gregtech.common.worldgen.GTWorldGen_OreSmall;
import gregtech.common.worldgen.GTWorldGen_OreVein;
import gregtech.common.worldgen.GTWorldGen_Stone;
import gregtech.common.worldgen.WorldGenerator;

public class WorldgenLoader implements Runnable {

    public void run() {
        
        GTLog.logger.info("GregTechMod: Loading world generators");
        
        //TODO Custom worldgen from configs
        //boolean pfaa = (GregTechAPI.sWorldgenFile.get(ConfigCategories.general, "AutoDetectPFAA", true)) && (Loader.isModLoaded("PFAAGeologica"));
        
        new WorldGenerator();
        
        //new GTWorldGen_Stone("debug_stone", true, 20, 80, 4, 24, 30, 1, MetaBlocks.GRANITE.withVariant(BlockGranite.GraniteVariant.BLACK_GRANITE, StoneBlock.ChiselingVariant.NORMAL) , false, new String[]{"overworld"}, new String[]{"all"});
        //new GTWorldGen_Asteroid("debug_asteroid", true, 180, 240, 4, 24, 30, 1, MetaBlocks.MINERAL.withVariant(BlockMineral.MineralVariant.MARBLE, StoneBlock.ChiselingVariant.NORMAL), new String[]{"overworld"}, new String[]{"all"});
        //new GTWorldGen_OreVein("debug_orevein", true, 60, 40, 80, 32, 7, 8, Materials.Aluminium, Materials.Bauxite, Materials.Ilmenite, Materials.Titanium, new String[]{"overworld"}, new String[]{"all"}, new String[]{"debug_asteroid"});
        
        String[] overworld = {"overworld"};
        String[] allBiome = {"all"};
        String[] allDims = {"overworld", "the_nether", "the_end", "?moon", "?mars", "?asteroid"};
        String[] noAst = {"overworld", "the_nether", "the_end", "?moon", "?mars"};
        String[] noEndMoon = {"overworld", "the_nether", "?mars", "?asteroid"};
        String[] nether = {"the_nether"};
        String[] none = new String[0];
        
        new GTWorldGen_Stone("tiny.blackgranite",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("small.blackgranite",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("medium.blackgranite", true, 0, 120, 16, 24, 144, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("large.blackgranite",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("huge.blackgranite",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.BLACK_GRANITE, false, overworld, allBiome);
        
        new GTWorldGen_Stone("tiny.redgranite",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("small.redgranite",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("medium.redgranite", true, 0, 120, 16, 24, 144, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("large.redgranite",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        new GTWorldGen_Stone("huge.redgranite",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.RED_GRANITE, false, overworld, allBiome);
        
        new GTWorldGen_Stone("tiny.basalt",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGen_Stone("small.basalt",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGen_Stone("medium.basalt", true, 0, 120, 16, 24, 144, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGen_Stone("large.basalt",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        new GTWorldGen_Stone("huge.basalt",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.BASALT, false, overworld, allBiome);
        
        new GTWorldGen_Stone("tiny.marble",   true, 0, 120,  4,  8,  48, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGen_Stone("small.marble",  true, 0, 120,  8, 16,  96, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGen_Stone("medium.marble", true, 0, 120, 16, 24, 144, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGen_Stone("large.marble",  true, 0, 120, 24, 32, 192, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        new GTWorldGen_Stone("huge.marble",   true, 0, 120, 32, 40, 240, 1, MetaBlocks.MARBLE, false, overworld, allBiome);
        
        /*IBlockState blockState = GregTechAPI.sBlockGranites.getDefaultState()
                .withProperty(BlockStonesAbstract.STONE_VARIANT, BlockStonesAbstract.EnumStoneVariant.NORMAL)
                .withProperty(GregTechAPI.sBlockGranites.getMaterialProperty(), Materials.GraniteBlack);
        new GT_Worldgen_Stone("overworld.stone.blackgranite.tiny", true, blockState, 0, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.blackgranite.small", true, blockState, 0, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.blackgranite.medium", true, blockState, 0, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.blackgranite.large", true, blockState, 0, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.blackgranite.huge", true, blockState, 0, 1, 400, 240, 0, 120, null, false);
		blockState = blockState.withProperty(GregTechAPI.sBlockGranites.getMaterialProperty(), Materials.GraniteRed);
        new GT_Worldgen_Stone("overworld.stone.redgranite.tiny", true, blockState, 0, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.redgranite.small", true, blockState, 0, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.redgranite.medium", true, blockState, 0, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.redgranite.large", true, blockState, 0, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.redgranite.huge", true, blockState, 0, 1, 400, 240, 0, 120, null, false);

		blockState = blockState.withProperty(GregTechAPI.sBlockGranites.getMaterialProperty(), Materials.GraniteBlack);
		new GT_Worldgen_Stone("nether.stone.blackgranite.tiny", false, blockState, -1, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.blackgranite.small", false, blockState, -1, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.blackgranite.medium", false, blockState, -1, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.blackgranite.large", false, blockState, -1, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.blackgranite.huge", false, blockState, -1, 1, 400, 240, 0, 120, null, false);
		blockState = blockState.withProperty(GregTechAPI.sBlockGranites.getMaterialProperty(), Materials.GraniteRed);
		new GT_Worldgen_Stone("nether.stone.redgranite.tiny", false, blockState, -1, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.redgranite.small", false, blockState, -1, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.redgranite.medium", false, blockState, -1, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.redgranite.large", false, blockState, -1, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.redgranite.huge", false, blockState, -1, 1, 400, 240, 0, 120, null, false);

		blockState = GregTechAPI.sBlockStones.getDefaultState();
        new GT_Worldgen_Stone("overworld.stone.marble.tiny", true, blockState, 0, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.marble.small", true, blockState, 0, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.marble.medium", true, blockState, 0, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.marble.large", true, blockState, 0, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.marble.huge", true, blockState, 0, 1, 400, 240, 0, 120, null, false);
		blockState = blockState.withProperty(GregTechAPI.sBlockStones.getMaterialProperty(), Materials.Basalt);
        new GT_Worldgen_Stone("overworld.stone.basalt.tiny", true, blockState, 0, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.basalt.small", true, blockState, 0, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.basalt.medium", true, blockState, 0, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.basalt.large", true, blockState, 0, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("overworld.stone.basalt.huge", true, blockState, 0, 1, 400, 240, 0, 120, null, false);

		blockState = blockState.withProperty(GregTechAPI.sBlockStones.getMaterialProperty(), Materials.Marble);
        new GT_Worldgen_Stone("nether.stone.marble.tiny", false, blockState, -1, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.marble.small", false, blockState, -1, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.marble.medium", false, blockState, -1, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.marble.large", false, blockState, -1, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.marble.huge", false, blockState, -1, 1, 400, 240, 0, 120, null, false);
		blockState = blockState.withProperty(GregTechAPI.sBlockStones.getMaterialProperty(), Materials.Basalt);
        new GT_Worldgen_Stone("nether.stone.basalt.tiny", false,blockState, -1, 1, 50, 48, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.basalt.small", false, blockState, -1, 1, 100, 96, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.basalt.medium", false, blockState, -1, 1, 200, 144, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.basalt.large", false, blockState, -1, 1, 300, 192, 0, 120, null, false);
        new GT_Worldgen_Stone("nether.stone.basalt.huge", false, blockState, -1, 1, 400, 240, 0, 120, null, false);*/
        
        new GTWorldGen_OreSmall("copper", true, 60, 120, 32, Materials.Copper, noAst, allBiome);
        new GTWorldGen_OreSmall("tin", true, 60, 120, 32, Materials.Tin, allDims, allBiome);
        new GTWorldGen_OreSmall("bismuth", true, 80, 120, 8, Materials.Bismuth, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome);
        new GTWorldGen_OreSmall("coal", true, 60, 100, 24, Materials.Coal, overworld, allBiome);
        new GTWorldGen_OreSmall("iron", true, 40, 80, 16, Materials.Iron, noAst, allBiome);
        new GTWorldGen_OreSmall("lead", true, 40, 80, 16, Materials.Lead, allDims, allBiome);
        new GTWorldGen_OreSmall("zinc", true, 30, 60, 12, Materials.Zinc, noAst, allBiome);
        new GTWorldGen_OreSmall("gold", true, 20, 40, 8, Materials.Gold, allDims, allBiome);
        new GTWorldGen_OreSmall("lapis", true, 20, 40, 4, Materials.Lapis, new String[]{"overworld", "?moon", "?asteroid"}, allBiome);
        new GTWorldGen_OreSmall("diamond", true, 5, 10, 2, Materials.Diamond, new String[]{"overworld", "the_nether", "?moon", "?mars", "?asteroid"}, allBiome);
        new GTWorldGen_OreSmall("emerald", true, 5, 250, 1, Materials.Emerald, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("ruby", true, 5, 250, 1, Materials.Ruby, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("sapphire", true, 5, 250, 1, Materials.Sapphire, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("greensapphire", true, 5, 250, 1, Materials.GreenSapphire, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("olivine", true, 5, 250, 1, Materials.Olivine, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("topaz", true, 5, 250, 1, Materials.Topaz, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("tanzanite", true, 5, 250, 1, Materials.Tanzanite, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("amethyst", true, 5, 250, 1, Materials.Amethyst, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("opal", true, 5, 250, 1, Materials.Opal, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("jasper", true, 5, 250, 1, Materials.Jasper, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("bluetopaz", true, 5, 250, 1, Materials.BlueTopaz, noEndMoon, allBiome);
        //new GTWorldGen_OreSmall("amber", true, 5, 250, 1, Materials.Amber, noEndMoon, allBiome); TODO Missing Amber
        //new GTWorldGen_OreSmall("foolsruby", true, 5, 250, 1, Materials.FoolsRuby, noEndMoon, allBiome); TODO Missing Fool's Ruby
        new GTWorldGen_OreSmall("garnetred", true, 5, 250, 1, Materials.GarnetRed, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("garnetyellow", true, 5, 250, 1, Materials.GarnetYellow, noEndMoon, allBiome);
        new GTWorldGen_OreSmall("redstone", true, 5, 20, 8, Materials.Redstone, new String[]{"overworld", "the_nether", "?moon", "?mars", "?asteroid"}, allBiome);
        new GTWorldGen_OreSmall("platinum", true, 20, 40, 8, Materials.Platinum, new String[]{"the_end", "?mars", "?asteroid"}, allBiome);
        new GTWorldGen_OreSmall("iridium", true, 20, 40, 8, Materials.Iridium, new String[]{"the_end", "?mars", "?asteroid"}, allBiome);
        new GTWorldGen_OreSmall("netherquartz", true, 30, 120, 64, Materials.NetherQuartz, nether, allBiome);
        new GTWorldGen_OreSmall("saltpeter", true, 10, 60, 8, Materials.Saltpeter, nether, allBiome);
        new GTWorldGen_OreSmall("sulfur_n", true, 10, 60, 32, Materials.Sulfur, nether, allBiome);
        new GTWorldGen_OreSmall("sulfur_o", true, 5, 15, 8, Materials.Sulfur, overworld, allBiome);
        
        /*new GT_Worldgen_GT_Ore_SmallPieces("ore.small.copper", true, 60, 120, 32,     !tPFAA, true, true, true, true, false, Materials.Copper);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.tin", true, 60, 120, 32,          !tPFAA, true, true, true, true, true, Materials.Tin);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.bismuth", true, 80, 120, 8,       !tPFAA, true, false, true, true, false, Materials.Bismuth);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.coal", true, 60, 100, 24,         !tPFAA, false, false, false, false, false, Materials.Coal);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.iron", true, 40, 80, 16,          !tPFAA, true, true, true, true, false, Materials.Iron);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.lead", true, 40, 80, 16,          !tPFAA, true, true, true, true, true, Materials.Lead);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.zinc", true, 30, 60, 12,          !tPFAA, true, true, true, true, false, Materials.Zinc);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.gold", true, 20, 40, 8,           !tPFAA, true, true, true, true, true, Materials.Gold);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.silver", true, 20, 40, 8,         !tPFAA, true, true, true, true, true, Materials.Silver);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.nickel", true, 20, 40, 8,         !tPFAA, true, true, true, true, true, Materials.Nickel);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.lapis", true, 20, 40, 4,          !tPFAA, false, false, true, false, true, Materials.Lapis);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.diamond", true, 5, 10, 2,         !tPFAA, true, false, true, true, true, Materials.Diamond);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.emerald", true, 5, 250, 1,        !tPFAA, true, false, false, true, true, Materials.Emerald);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.ruby", true, 5, 250, 1,           !tPFAA, true, false, false, true, true, Materials.Ruby);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sapphire", true, 5, 250, 1,       !tPFAA, true, false, false, true, true, Materials.Sapphire);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.greensapphire", true, 5, 250, 1,  !tPFAA, true, false, false, true, true, Materials.GreenSapphire);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.olivine", true, 5, 250, 1,        !tPFAA, true, false, false, true, true, Materials.Olivine);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.topaz", true, 5, 250, 1,          !tPFAA, true, false, false, true, true, Materials.Topaz);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.tanzanite", true, 5, 250, 1,      !tPFAA, true, false, false, true, true, Materials.Tanzanite);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.amethyst", true, 5, 250, 1,       !tPFAA, true, false, false, true, true, Materials.Amethyst);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.opal", true, 5, 250, 1,           !tPFAA, true, false, false, true, true, Materials.Opal);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.jasper", true, 5, 250, 1,         !tPFAA, true, false, false, true, true, Materials.Jasper);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.bluetopaz", true, 5, 250, 1,      !tPFAA, true, false, false, true, true, Materials.BlueTopaz);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.amber", true, 5, 250, 1,          !tPFAA, true, false, false, true, true, Materials.Amber);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.foolsruby", true, 5, 250, 1,      !tPFAA, true, false, false, true, true, Materials.FoolsRuby);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.garnetred", true, 5, 250, 1,      !tPFAA, true, false, false, true, true, Materials.GarnetRed);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.garnetyellow", true, 5, 250, 1,   !tPFAA, true, false, false, true, true, Materials.GarnetYellow);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.redstone", true, 5, 20, 8,        !tPFAA, true, false, true, true, true, Materials.Redstone);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.platinum", true, 20, 40, 8,       false, false, true, false, true, true, Materials.Platinum);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.iridium", true, 20, 40, 8,        false, false, true, false, true, true, Materials.Iridium);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.netherquartz", true, 30, 120, 64, false, true, false, false, false, false, Materials.NetherQuartz);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.saltpeter", true, 10, 60, 8,      false, true, false, false, false, false, Materials.Saltpeter);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sulfur_n", true, 10, 60, 32,      false, true, false, false, false, false, Materials.Sulfur);
        new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sulfur_o", true, 5, 15, 8,        !tPFAA, false, false, false, false, false, Materials.Sulfur);*/
        
        new GTWorldGen_Asteroid("endstone", true, 50, 200, 6, 30, 300, 1, StoneTypes.ENDSTONE, new String[]{"the_end"}, allBiome);
        new GTWorldGen_Asteroid("blackgranite", true, 50, 200, 12, 50, 150, 1, MetaBlocks.BLACK_GRANITE, new String[]{"?asteroid"}, allBiome);
        new GTWorldGen_Asteroid("redgranite", true, 50, 200, 12, 50, 150, 1, MetaBlocks.RED_GRANITE, new String[]{"?asteroid"}, allBiome);
        
        new GTWorldGen_OreVein("naquadah", true, 10, 10, 60, 32, 7, 5, Materials.Naquadah, Materials.Naquadah, Materials.Naquadah, Materials.NaquadahEnriched, new String[]{"the_end", "?mars"}, allBiome, new String[]{"endstone", "blackgranite"});
        new GTWorldGen_OreVein("lignite", true, 160, 50, 130, 32, 7, 8, Materials.Lignite, Materials.Lignite, Materials.Lignite, Materials.Coal, overworld, allBiome, none);
        new GTWorldGen_OreVein("coal", true, 80, 50, 80, 32, 7, 6, Materials.Coal, Materials.Coal, Materials.Coal, Materials.Lignite, overworld, allBiome, none);
        new GTWorldGen_OreVein("magnetite", true, 160, 50, 120, 32, 7, 3, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, none);
        new GTWorldGen_OreVein("gold", true, 160, 60, 80, 32, 7, 3, Materials.Magnetite, Materials.Magnetite, Materials.VanadiumMagnetite, Materials.Gold, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("iron", true, 120, 10, 40, 24, 7, 4, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome, none);
        new GTWorldGen_OreVein("cassiterite", true, 50, 40, 120, 24, 7, 5, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "redgranite"});
        new GTWorldGen_OreVein("tetrahedrite", true, 70, 80, 120, 24, 7, 4, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome, new String[]{"redgranite"});
        new GTWorldGen_OreVein("netherquratz", true, 80, 40, 80, 24, 7, 5, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, nether, allBiome, none);
        new GTWorldGen_OreVein("sulfur", true, 100, 5, 20, 24, 7, 5, Materials.Sulfur, Materials.Sulfur, Materials.Pyrite, Materials.Sphalerite, new String[]{"the_nether", "?mars"}, allBiome, none);
        new GTWorldGen_OreVein("copper", true, 80, 10, 30, 24, 7, 4, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome, none);
        new GTWorldGen_OreVein("bauxite", true, 80, 50, 90, 24, 7, 4, Materials.Bauxite, Materials.Bauxite, Materials.Aluminium, Materials.Ilmenite, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("salts", true, 50, 50, 60, 24, 7, 3, Materials.RockSalt, Materials.Salt, Materials.Lepidolite, Materials.Spodumene, new String[]{"overworld", "?moon"}, allBiome, none);
        new GTWorldGen_OreVein("redstone", true, 60, 10, 40, 24, 7, 3, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar, new String[]{"overworld", "the_nether", "?moon", "?mars"}, allBiome, new String[]{"redgranite"});
        new GTWorldGen_OreVein("soapstone", true, 40, 10, 40, 16, 7, 3, Materials.Soapstone, Materials.Talc, Materials.Glauconite, Materials.Pentlandite, new String[]{"overworld", "?moon", "?mars"}, allBiome, none);
        new GTWorldGen_OreVein("nickel", true, 40, 10, 40, 16, 7, 3, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite, new String[]{"overworld", "the_nether", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "blackgranite"});
        new GTWorldGen_OreVein("platinum", true, 5, 40, 50, 16, 7, 3, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium, new String[]{"overworld", "the_end", "?mars"}, allBiome, new String[]{"endstone", "blackgranite"});
        new GTWorldGen_OreVein("pitchblende", true, 40, 10, 40, 16, 7, 3, Materials.Pitchblende, Materials.Pitchblende, Materials.Uraninite, Materials.Uraninite, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("uranium", true, 20, 20, 30, 16, 7, 3, Materials.Uraninite, Materials.Uraninite, Materials.Uranium, Materials.Uranium,  new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("monazite", true, 30, 20, 40, 16, 7, 3, Materials.Bastnasite, Materials.Bastnasite, Materials.Monazite, Materials.Neodymium, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite", "redgranite"});
        new GTWorldGen_OreVein("molybdenum", true, 5, 20, 50, 16, 7, 3, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "redgranite"});
        new GTWorldGen_OreVein("tungstate", true, 10, 20, 50, 16, 7, 3, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "redgranite"});
        new GTWorldGen_OreVein("sapphire", true, 60, 10, 40, 16, 7, 3, Materials.Almandine, Materials.Pyrope, Materials.Sapphire, Materials.GreenSapphire, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"redgranite"});
        new GTWorldGen_OreVein("manganese", true, 20, 20, 30, 16, 7, 3, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite, new String[]{"overworld", "the_end", "?moon"}, allBiome, new String[]{"endstone", "blackgranite"});
        new GTWorldGen_OreVein("quartz", true, 60, 40, 80, 16, 7, 3, Materials.Quartzite, Materials.Barite, Materials.CertusQuartz, Materials.CertusQuartz, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("diamond", true, 40, 5, 20, 16, 7, 2, Materials.Graphite, Materials.Graphite, Materials.Diamond, Materials.Coal, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"redgranite"});
        new GTWorldGen_OreVein("olivine", true, 60, 10, 40, 16, 7, 3, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "redgranite"});
        new GTWorldGen_OreVein("galena", true, 40, 30, 60, 16, 7, 5, Materials.Galena, Materials.Galena, Materials.Silver, Materials.Lead, new String[]{"overworld", "?moon", "?mars"}, allBiome, new String[]{"blackgranite"});
        new GTWorldGen_OreVein("lapis", true, 40, 20, 50, 16, 7, 5, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone","redgranite"});
        new GTWorldGen_OreVein("beryllium", true, 30, 5, 30, 16, 7, 3, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium, new String[]{"overworld", "the_end", "?moon", "?mars"}, allBiome, new String[]{"endstone", "blackgranite"});
        new GTWorldGen_OreVein("oilsand", true, 80, 50, 80, 32, 7, 6, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, overworld, allBiome, none);
        new GTWorldGen_OreVein("apatite", true, 60, 40, 60, 16, 7, 3, Materials.Apatite, Materials.Apatite, Materials.Phosphor, Materials.Phosphate, overworld, allBiome, none);//TODO Missing Pyrochlore.
        
        /*new GT_Worldgen_GT_Ore_Layer("ore.mix.naquadah", true, 10, 60, 10, 5, 32, false, false, true, false, true, true, Materials.Naquadah, Materials.Naquadah, Materials.Naquadah, Materials.NaquadahEnriched);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.lignite", true, 50, 130, 160, 8, 32, !tPFAA, false, false, false, false, false, Materials.Lignite, Materials.Lignite, Materials.Lignite, Materials.Coal);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.coal", true, 50, 80, 80, 6, 32, !tPFAA, false, false, false, false, false, Materials.Coal, Materials.Coal, Materials.Coal, Materials.Lignite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.magnetite", true, 50, 120, 160, 3, 32, !tPFAA, true, false, true, true, false, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.gold", true, 60, 80, 160, 3, 32, !tPFAA, false, false, true, true, true, Materials.Magnetite, Materials.Magnetite, Materials.VanadiumMagnetite, Materials.Gold);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.iron", true, 10, 40, 120, 4, 24, !tPFAA, true, false, true, true, false, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.cassiterite", true, 40, 120, 50, 5, 24, !tPFAA, false, true, true, true, true, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.tetrahedrite", true, 80, 120, 70, 4, 24, !tPFAA, true, false, true, true, true, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.netherquartz", true, 40, 80, 80, 5, 24, false, true, false, false, false, false, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.sulfur", true, 5, 20, 100, 5, 24, false, true, false, false, true, false, Materials.Sulfur, Materials.Sulfur, Materials.Pyrite, Materials.Sphalerite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.copper", true, 10, 30, 80, 4, 24, !tPFAA, true, false, true, true, false, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.bauxite", true, 50, 90, 80, 4, 24, !tPFAA, tPFAA, false, true, true, true, Materials.Bauxite, Materials.Bauxite, Materials.Aluminium, Materials.Ilmenite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.salts", true, 50, 60, 50, 3, 24, !tPFAA, false, false, true, false, false, Materials.RockSalt, Materials.Salt, Materials.Lepidolite, Materials.Spodumene);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.redstone", true, 10, 40, 60, 3, 24, !tPFAA, true, false, true, true, true, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.soapstone", true, 10, 40, 40, 3, 16, !tPFAA, false, false, true, true, false, Materials.Soapstone, Materials.Talc, Materials.Glauconite, Materials.Pentlandite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.nickel", true, 10, 40, 40, 3, 16, !tPFAA, true, true, true, true, true, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.platinum", true, 40, 50, 5, 3, 16, !tPFAA, false, true, false, true, true, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.pitchblende", true, 10, 40, 40, 3, 16, !tPFAA, false, false, true, true, true, Materials.Pitchblende, Materials.Pitchblende, Materials.Uraninite, Materials.Uraninite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.uranium", true, 20, 30, 20, 3, 16, !tPFAA, false, false, true, true, true, Materials.Uraninite, Materials.Uraninite, Materials.Uranium, Materials.Uranium);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.monazite", true, 20, 40, 30, 3, 16, !tPFAA, tPFAA, false, true, true, true, Materials.Bastnasite, Materials.Bastnasite, Materials.Monazite, Materials.Neodymium);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.molybdenum", true, 20, 50, 5, 3, 16, !tPFAA, false, true, true, true, true, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.tungstate", true, 20, 50, 10, 3, 16, !tPFAA, false, true, true, true, true, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.sapphire", true, 10, 40, 60, 3, 16, !tPFAA, tPFAA, tPFAA, true, true, true, Materials.Almandine, Materials.Pyrope, Materials.Sapphire, Materials.GreenSapphire);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.manganese", true, 20, 30, 20, 3, 16, !tPFAA, false, true, true, false, true, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.quartz", true, 40, 80, 60, 3, 16, !tPFAA, tPFAA, false, true, true, true, Materials.Quartzite, Materials.Barite, Materials.CertusQuartz, Materials.CertusQuartz);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.diamond", true, 5, 20, 40, 2, 16, !tPFAA, false, false, true, true, true, Materials.Graphite, Materials.Graphite, Materials.Diamond, Materials.Coal);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.olivine", true, 10, 40, 60, 3, 16, !tPFAA, false, true, true, true, true, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.apatite", true, 40, 60, 60, 3, 16, !tPFAA, false, false, false, false, false, Materials.Apatite, Materials.Apatite, Materials.Phosphor, Materials.Phosphate);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.galena", true, 30, 60, 40, 5, 16, !tPFAA, false, false, true, true, true, Materials.Galena, Materials.Galena, Materials.Silver, Materials.Lead);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.lapis", true, 20, 50, 40, 5, 16, !tPFAA, false, true, true, true, true, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.beryllium", true, 5, 30, 30, 3, 16, !tPFAA, false, true, true, true, true, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium);
        new GT_Worldgen_GT_Ore_Layer("ore.mix.oilsand", true, 50, 80, 80, 6, 32, !tPFAA, false, false, false, false, false, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands, Materials.Oilsands);
        */
    }
}