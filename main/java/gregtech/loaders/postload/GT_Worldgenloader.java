/*   1:    */ package gregtech.loaders.postload;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.common.Loader;
/*   4:    */ import gregtech.api.GregTech_API;
/*   5:    */ import gregtech.api.enums.ConfigCategories;
/*   6:    */ import gregtech.api.enums.Materials;
/*   7:    */ import gregtech.api.util.GT_Config;
/*   8:    */ import gregtech.common.GT_Worldgen_GT_Ore_Layer;
/*   9:    */ import gregtech.common.GT_Worldgen_GT_Ore_SmallPieces;
/*  10:    */ import gregtech.common.GT_Worldgen_Stone;
/*  11:    */ import gregtech.common.GT_Worldgenerator;
/*  12:    */ 
/*  13:    */ public class GT_Worldgenloader
/*  14:    */   implements Runnable
/*  15:    */ {
/*  16:    */   public void run()
/*  17:    */   {
/*  18: 16 */     boolean tPFAA = (GregTech_API.sWorldgenFile.get(ConfigCategories.general, "AutoDetectPFAA", true)) && (Loader.isModLoaded("PFAAGeologica"));
/*  19:    */     
/*  20: 18 */     new GT_Worldgenerator();
/*  21:    */     
/*  22: 20 */     new GT_Worldgen_Stone("overworld.stone.blackgranite.tiny", true, GregTech_API.sBlockGranites, 0, 0, 1, 50, 48, 0, 120, null, false);
/*  23: 21 */     new GT_Worldgen_Stone("overworld.stone.blackgranite.small", true, GregTech_API.sBlockGranites, 0, 0, 1, 100, 96, 0, 120, null, false);
/*  24: 22 */     new GT_Worldgen_Stone("overworld.stone.blackgranite.medium", true, GregTech_API.sBlockGranites, 0, 0, 1, 200, 144, 0, 120, null, false);
/*  25: 23 */     new GT_Worldgen_Stone("overworld.stone.blackgranite.large", true, GregTech_API.sBlockGranites, 0, 0, 1, 300, 192, 0, 120, null, false);
/*  26: 24 */     new GT_Worldgen_Stone("overworld.stone.blackgranite.huge", true, GregTech_API.sBlockGranites, 0, 0, 1, 400, 240, 0, 120, null, false);
/*  27: 25 */     new GT_Worldgen_Stone("overworld.stone.redgranite.tiny", true, GregTech_API.sBlockGranites, 8, 0, 1, 50, 48, 0, 120, null, false);
/*  28: 26 */     new GT_Worldgen_Stone("overworld.stone.redgranite.small", true, GregTech_API.sBlockGranites, 8, 0, 1, 100, 96, 0, 120, null, false);
/*  29: 27 */     new GT_Worldgen_Stone("overworld.stone.redgranite.medium", true, GregTech_API.sBlockGranites, 8, 0, 1, 200, 144, 0, 120, null, false);
/*  30: 28 */     new GT_Worldgen_Stone("overworld.stone.redgranite.large", true, GregTech_API.sBlockGranites, 8, 0, 1, 300, 192, 0, 120, null, false);
/*  31: 29 */     new GT_Worldgen_Stone("overworld.stone.redgranite.huge", true, GregTech_API.sBlockGranites, 8, 0, 1, 400, 240, 0, 120, null, false);
/*  32:    */     
/*  33: 31 */     new GT_Worldgen_Stone("nether.stone.blackgranite.tiny", false, GregTech_API.sBlockGranites, 0, -1, 1, 50, 48, 0, 120, null, false);
/*  34: 32 */     new GT_Worldgen_Stone("nether.stone.blackgranite.small", false, GregTech_API.sBlockGranites, 0, -1, 1, 100, 96, 0, 120, null, false);
/*  35: 33 */     new GT_Worldgen_Stone("nether.stone.blackgranite.medium", false, GregTech_API.sBlockGranites, 0, -1, 1, 200, 144, 0, 120, null, false);
/*  36: 34 */     new GT_Worldgen_Stone("nether.stone.blackgranite.large", false, GregTech_API.sBlockGranites, 0, -1, 1, 300, 192, 0, 120, null, false);
/*  37: 35 */     new GT_Worldgen_Stone("nether.stone.blackgranite.huge", false, GregTech_API.sBlockGranites, 0, -1, 1, 400, 240, 0, 120, null, false);
/*  38: 36 */     new GT_Worldgen_Stone("nether.stone.redgranite.tiny", false, GregTech_API.sBlockGranites, 8, -1, 1, 50, 48, 0, 120, null, false);
/*  39: 37 */     new GT_Worldgen_Stone("nether.stone.redgranite.small", false, GregTech_API.sBlockGranites, 8, -1, 1, 100, 96, 0, 120, null, false);
/*  40: 38 */     new GT_Worldgen_Stone("nether.stone.redgranite.medium", false, GregTech_API.sBlockGranites, 8, -1, 1, 200, 144, 0, 120, null, false);
/*  41: 39 */     new GT_Worldgen_Stone("nether.stone.redgranite.large", false, GregTech_API.sBlockGranites, 8, -1, 1, 300, 192, 0, 120, null, false);
/*  42: 40 */     new GT_Worldgen_Stone("nether.stone.redgranite.huge", false, GregTech_API.sBlockGranites, 8, -1, 1, 400, 240, 0, 120, null, false);
/*  43:    */     
/*  44: 42 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.copper", true, 60, 120, 32, !tPFAA, true, true, Materials.Copper);
/*  45: 43 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.tin", true, 60, 120, 32, !tPFAA, true, true, Materials.Tin);
/*  46: 44 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.bismuth", true, 80, 120, 8, !tPFAA, true, false, Materials.Bismuth);
/*  47: 45 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.coal", true, 60, 100, 24, !tPFAA, false, false, Materials.Coal);
/*  48: 46 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.iron", true, 40, 80, 16, !tPFAA, true, true, Materials.Iron);
/*  49: 47 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.lead", true, 40, 80, 16, !tPFAA, true, true, Materials.Lead);
/*  50: 48 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.zinc", true, 30, 60, 12, !tPFAA, true, true, Materials.Zinc);
/*  51: 49 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.gold", true, 20, 40, 8, !tPFAA, true, true, Materials.Gold);
/*  52: 50 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.silver", true, 20, 40, 8, !tPFAA, true, true, Materials.Silver);
/*  53: 51 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.nickel", true, 20, 40, 8, !tPFAA, true, true, Materials.Nickel);
/*  54: 52 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.lapis", true, 20, 40, 4, !tPFAA, false, false, Materials.Lapis);
/*  55: 53 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.diamond", true, 5, 10, 2, !tPFAA, true, false, Materials.Diamond);
/*  56: 54 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.emerald", true, 5, 250, 1, !tPFAA, true, false, Materials.Emerald);
/*  57: 55 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.ruby", true, 5, 250, 1, !tPFAA, true, false, Materials.Ruby);
/*  58: 56 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sapphire", true, 5, 250, 1, !tPFAA, true, false, Materials.Sapphire);
/*  59: 57 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.greensapphire", true, 5, 250, 1, !tPFAA, true, false, Materials.GreenSapphire);
/*  60: 58 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.olivine", true, 5, 250, 1, !tPFAA, true, false, Materials.Olivine);
/*  61: 59 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.topaz", true, 5, 250, 1, !tPFAA, true, false, Materials.Topaz);
/*  62: 60 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.tanzanite", true, 5, 250, 1, !tPFAA, true, false, Materials.Tanzanite);
/*  63: 61 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.amethyst", true, 5, 250, 1, !tPFAA, true, false, Materials.Amethyst);
/*  64: 62 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.opal", true, 5, 250, 1, !tPFAA, true, false, Materials.Opal);
/*  65: 63 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.jasper", true, 5, 250, 1, !tPFAA, true, false, Materials.Jasper);
/*  66: 64 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.bluetopaz", true, 5, 250, 1, !tPFAA, true, false, Materials.BlueTopaz);
/*  67: 65 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.amber", true, 5, 250, 1, !tPFAA, true, false, Materials.Amber);
/*  68: 66 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.foolsruby", true, 5, 250, 1, !tPFAA, true, false, Materials.FoolsRuby);
/*  69: 67 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.garnetred", true, 5, 250, 1, !tPFAA, true, false, Materials.GarnetRed);
/*  70: 68 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.garnetyellow", true, 5, 250, 1, !tPFAA, true, false, Materials.GarnetYellow);
/*  71: 69 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.redstone", true, 5, 20, 8, !tPFAA, true, false, Materials.Redstone);
/*  72: 70 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.platinum", true, 20, 40, 8, false, false, true, Materials.Platinum);
/*  73: 71 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.iridium", true, 20, 40, 8, false, false, true, Materials.Iridium);
/*  74: 72 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.netherquartz", true, 30, 120, 64, false, true, false, Materials.NetherQuartz);
/*  75: 73 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.saltpeter", true, 10, 60, 8, false, true, false, Materials.Saltpeter);
/*  76: 74 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sulfur_n", true, 10, 60, 32, false, true, false, Materials.Sulfur);
/*  77: 75 */     new GT_Worldgen_GT_Ore_SmallPieces("ore.small.sulfur_o", true, 5, 15, 8, !tPFAA, false, false, Materials.Sulfur);
/*  78:    */     
/*  79: 77 */     int i = 0;
/*  80: 77 */     for (int j = GregTech_API.sWorldgenFile.get("worldgen", "AmountOfCustomSmallOreSlots", 16); i < j; i++) {
/*  81: 78 */       new GT_Worldgen_GT_Ore_SmallPieces("ore.small.custom." + (i < 10 ? "0" : "") + i, false, 0, 0, 0, false, false, false, Materials._NULL);
/*  82:    */     }
/*  83: 81 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.naquadah", false, 10, 60, 10, 5, 32, false, false, true, Materials.Naquadah, Materials.Naquadah, Materials.Naquadah, Materials.NaquadahEnriched);
/*  84: 82 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.lignite", true, 50, 130, 160, 8, 32, !tPFAA, false, false, Materials.Lignite, Materials.Lignite, Materials.Lignite, Materials.Coal);
/*  85: 83 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.coal", true, 50, 80, 80, 6, 32, !tPFAA, false, false, Materials.Coal, Materials.Coal, Materials.Coal, Materials.Lignite);
/*  86: 84 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.magnetite", true, 50, 120, 160, 3, 32, !tPFAA, true, false, Materials.Magnetite, Materials.Magnetite, Materials.Iron, Materials.VanadiumMagnetite);
/*  87: 85 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.gold", true, 60, 80, 160, 3, 32, !tPFAA, false, false, Materials.Magnetite, Materials.Magnetite, Materials.VanadiumMagnetite, Materials.Gold);
/*  88: 86 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.iron", true, 10, 40, 120, 4, 24, !tPFAA, true, false, Materials.BrownLimonite, Materials.YellowLimonite, Materials.BandedIron, Materials.Malachite);
/*  89: 87 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.cassiterite", true, 40, 120, 50, 5, 24, !tPFAA, false, true, Materials.Tin, Materials.Tin, Materials.Cassiterite, Materials.Tin);
/*  90: 88 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.tetrahedrite", true, 80, 120, 70, 4, 24, !tPFAA, true, false, Materials.Tetrahedrite, Materials.Tetrahedrite, Materials.Copper, Materials.Stibnite);
/*  91: 89 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.netherquartz", true, 40, 80, 80, 5, 24, false, true, false, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz, Materials.NetherQuartz);
/*  92: 90 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.sulfur", true, 5, 20, 100, 5, 24, false, true, false, Materials.Sulfur, Materials.Sulfur, Materials.Pyrite, Materials.Sphalerite);
/*  93: 91 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.copper", true, 10, 30, 80, 4, 24, !tPFAA, true, false, Materials.Chalcopyrite, Materials.Iron, Materials.Pyrite, Materials.Copper);
/*  94: 92 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.bauxite", true, 50, 90, 80, 4, 24, !tPFAA, tPFAA, false, Materials.Bauxite, Materials.Bauxite, Materials.Aluminium, Materials.Ilmenite);
/*  95: 93 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.salts", true, 50, 60, 50, 3, 24, !tPFAA, false, false, Materials.RockSalt, Materials.Salt, Materials.Lepidolite, Materials.Spodumene);
/*  96: 94 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.redstone", true, 10, 40, 60, 3, 24, !tPFAA, true, false, Materials.Redstone, Materials.Redstone, Materials.Ruby, Materials.Cinnabar);
/*  97: 95 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.soapstone", true, 10, 40, 40, 3, 16, !tPFAA, false, false, Materials.Soapstone, Materials.Talc, Materials.Glauconite, Materials.Pentlandite);
/*  98: 96 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.nickel", true, 10, 40, 40, 3, 16, !tPFAA, true, true, Materials.Garnierite, Materials.Nickel, Materials.Cobaltite, Materials.Pentlandite);
/*  99: 97 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.platinum", true, 40, 50, 5, 3, 16, !tPFAA, false, true, Materials.Cooperite, Materials.Palladium, Materials.Platinum, Materials.Iridium);
/* 100: 98 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.pitchblende", true, 10, 40, 40, 3, 16, !tPFAA, false, false, Materials.Pitchblende, Materials.Pitchblende, Materials.Uranium, Materials.Uraninite);
/* 101: 99 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.plutonium", true, 20, 30, 10, 3, 16, !tPFAA, false, false, Materials.Uraninite, Materials.Uraninite, Materials.Plutonium, Materials.Uranium);
/* 102:100 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.monazite", true, 20, 40, 30, 3, 16, !tPFAA, tPFAA, false, Materials.Bastnasite, Materials.Bastnasite, Materials.Monazite, Materials.Neodymium);
/* 103:101 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.molybdenum", true, 20, 50, 5, 3, 16, !tPFAA, false, true, Materials.Wulfenite, Materials.Molybdenite, Materials.Molybdenum, Materials.Powellite);
/* 104:102 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.tungstate", true, 20, 50, 10, 3, 16, !tPFAA, false, true, Materials.Scheelite, Materials.Scheelite, Materials.Tungstate, Materials.Lithium);
/* 105:103 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.sapphire", true, 10, 40, 60, 3, 16, !tPFAA, tPFAA, tPFAA, Materials.Almandine, Materials.Pyrope, Materials.Sapphire, Materials.GreenSapphire);
/* 106:104 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.manganese", true, 20, 30, 20, 3, 16, !tPFAA, false, true, Materials.Grossular, Materials.Spessartine, Materials.Pyrolusite, Materials.Tantalite);
/* 107:105 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.quartz", true, 40, 80, 60, 3, 16, !tPFAA, tPFAA, false, Materials.Quartzite, Materials.Barite, Materials.CertusQuartz, Materials.CertusQuartz);
/* 108:106 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.diamond", true, 5, 20, 40, 2, 16, !tPFAA, false, false, Materials.Graphite, Materials.Graphite, Materials.Diamond, Materials.Coal);
/* 109:107 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.olivine", true, 10, 40, 60, 3, 16, !tPFAA, false, true, Materials.Bentonite, Materials.Magnesite, Materials.Olivine, Materials.Glauconite);
/* 110:108 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.apatite", true, 40, 60, 60, 3, 16, !tPFAA, false, false, Materials.Apatite, Materials.Apatite, Materials.Phosphorus, Materials.Phosphate);
/* 111:109 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.galena", true, 30, 60, 40, 5, 16, !tPFAA, false, false, Materials.Galena, Materials.Galena, Materials.Silver, Materials.Lead);
/* 112:110 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.lapis", true, 20, 50, 40, 5, 16, !tPFAA, false, true, Materials.Lazurite, Materials.Sodalite, Materials.Lapis, Materials.Calcite);
/* 113:111 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.beryllium", true, 5, 30, 30, 3, 16, !tPFAA, false, true, Materials.Beryllium, Materials.Beryllium, Materials.Emerald, Materials.Thorium);
/* 114:    */     
/* 115:113 */     i = 0;
/* 116:113 */     for (int j = GregTech_API.sWorldgenFile.get("worldgen", "AmountOfCustomLargeVeinSlots", 16); i < j; i++) {
/* 117:114 */       new GT_Worldgen_GT_Ore_Layer("ore.mix.custom." + (i < 10 ? "0" : "") + i, false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 118:    */     }
/* 119:117 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.00", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 120:118 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.01", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 121:119 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.02", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 122:120 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.03", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 123:121 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.04", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 124:122 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.05", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 125:123 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.06", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 126:124 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.07", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 127:125 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.08", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 128:126 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.09", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 129:127 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.10", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 130:128 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.11", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 131:129 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.12", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 132:130 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.13", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 133:131 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.14", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 134:132 */     new GT_Worldgen_GT_Ore_Layer("ore.mix.custom.15", false, 0, 0, 0, 0, 0, false, false, false, Materials._NULL, Materials._NULL, Materials._NULL, Materials._NULL);
/* 135:    */   }
/* 136:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_Worldgenloader
 * JD-Core Version:    0.7.0.1
 */