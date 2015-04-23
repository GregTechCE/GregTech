/*   1:    */ package gregtech.loaders.load;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
/*   4:    */ import gregtech.api.util.GT_Log;
/*   5:    */ import gregtech.common.GT_Proxy;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ 
/*  12:    */ public class GT_SonictronLoader
/*  13:    */   implements Runnable
/*  14:    */ {
/*  15:    */   public void run()
/*  16:    */   {
/*  17: 12 */     GT_Log.out.println("GT_Mod: Loading Sonictron Sounds");
/*  18: 13 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.iron_block, 1));
/*  19: 14 */     GT_Mod.gregtechproxy.mSoundNames.add("note.harp");
/*  20: 15 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  21: 16 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.gold_block, 1));
/*  22: 17 */     GT_Mod.gregtechproxy.mSoundNames.add("note.pling");
/*  23: 18 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  24: 19 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.stone, 1));
/*  25: 20 */     GT_Mod.gregtechproxy.mSoundNames.add("note.bd");
/*  26: 21 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  27: 22 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.log, 1));
/*  28: 23 */     GT_Mod.gregtechproxy.mSoundNames.add("note.bassattack");
/*  29: 24 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  30: 25 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.planks, 1));
/*  31: 26 */     GT_Mod.gregtechproxy.mSoundNames.add("note.bass");
/*  32: 27 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  33: 28 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.glass, 1));
/*  34: 29 */     GT_Mod.gregtechproxy.mSoundNames.add("note.hat");
/*  35: 30 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  36: 31 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.sand, 1));
/*  37: 32 */     GT_Mod.gregtechproxy.mSoundNames.add("note.snare");
/*  38: 33 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/*  39: 34 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.record_cat, 1));
/*  40: 35 */     GT_Mod.gregtechproxy.mSoundNames.add("streaming.");
/*  41: 36 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(12));
/*  42: 37 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.tnt, 1));
/*  43: 38 */     GT_Mod.gregtechproxy.mSoundNames.add("random.explode");
/*  44: 39 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(3));
/*  45: 40 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.fire, 1));
/*  46: 41 */     GT_Mod.gregtechproxy.mSoundNames.add("fire.fire");
/*  47: 42 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  48: 43 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.flint_and_steel, 1));
/*  49: 44 */     GT_Mod.gregtechproxy.mSoundNames.add("fire.ignite");
/*  50: 45 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  51: 46 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.lava, 1));
/*  52: 47 */     GT_Mod.gregtechproxy.mSoundNames.add("liquid.lavapop");
/*  53: 48 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  54: 49 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.water, 1));
/*  55: 50 */     GT_Mod.gregtechproxy.mSoundNames.add("liquid.water");
/*  56: 51 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  57: 52 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.water_bucket, 1));
/*  58: 53 */     GT_Mod.gregtechproxy.mSoundNames.add("liquid.splash");
/*  59: 54 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  60: 55 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.lava_bucket, 1));
/*  61: 56 */     GT_Mod.gregtechproxy.mSoundNames.add("random.fizz");
/*  62: 57 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  63: 58 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.portal, 1));
/*  64: 59 */     GT_Mod.gregtechproxy.mSoundNames.add("portal.portal");
/*  65: 60 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  66: 61 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.end_portal, 1));
/*  67: 62 */     GT_Mod.gregtechproxy.mSoundNames.add("portal.travel");
/*  68: 63 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  69: 64 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.end_portal_frame, 1));
/*  70: 65 */     GT_Mod.gregtechproxy.mSoundNames.add("portal.trigger");
/*  71: 66 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  72: 67 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.glass_pane, 1));
/*  73: 68 */     GT_Mod.gregtechproxy.mSoundNames.add("random.glass");
/*  74: 69 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  75: 70 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.ender_pearl, 1));
/*  76: 71 */     GT_Mod.gregtechproxy.mSoundNames.add("random.orb");
/*  77: 72 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  78: 73 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.ender_eye, 1));
/*  79: 74 */     GT_Mod.gregtechproxy.mSoundNames.add("random.levelup");
/*  80: 75 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  81: 76 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.stone_button, 1));
/*  82: 77 */     GT_Mod.gregtechproxy.mSoundNames.add("random.click");
/*  83: 78 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  84: 79 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.cobblestone, 1));
/*  85: 80 */     GT_Mod.gregtechproxy.mSoundNames.add("damage.fallbig");
/*  86: 81 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  87: 82 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.dirt, 1));
/*  88: 83 */     GT_Mod.gregtechproxy.mSoundNames.add("damage.fallsmall");
/*  89: 84 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  90: 85 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.iron_sword, 1));
/*  91: 86 */     GT_Mod.gregtechproxy.mSoundNames.add("damage.hurtflesh");
/*  92: 87 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  93: 88 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.diamond_sword, 1));
/*  94: 89 */     GT_Mod.gregtechproxy.mSoundNames.add("random.hurt");
/*  95: 90 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  96: 91 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.bow, 1));
/*  97: 92 */     GT_Mod.gregtechproxy.mSoundNames.add("random.bow");
/*  98: 93 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/*  99: 94 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.arrow, 1));
/* 100: 95 */     GT_Mod.gregtechproxy.mSoundNames.add("random.drr");
/* 101: 96 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 102: 97 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.fishing_rod, 1));
/* 103: 98 */     GT_Mod.gregtechproxy.mSoundNames.add("random.bowhit");
/* 104: 99 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 105:100 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.iron_shovel, 1));
/* 106:101 */     GT_Mod.gregtechproxy.mSoundNames.add("random.break");
/* 107:102 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 108:103 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.bucket, 1));
/* 109:104 */     GT_Mod.gregtechproxy.mSoundNames.add("random.breath");
/* 110:105 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 111:106 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.potionitem, 1));
/* 112:107 */     GT_Mod.gregtechproxy.mSoundNames.add("random.drink");
/* 113:108 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 114:109 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.glass_bottle, 1));
/* 115:110 */     GT_Mod.gregtechproxy.mSoundNames.add("random.burp");
/* 116:111 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 117:112 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.ender_chest == null ? Blocks.obsidian : Blocks.ender_chest, 1));
/* 118:113 */     GT_Mod.gregtechproxy.mSoundNames.add("random.chestopen");
/* 119:114 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 120:115 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.chest, 1));
/* 121:116 */     GT_Mod.gregtechproxy.mSoundNames.add("random.chestclosed");
/* 122:117 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 123:118 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.iron_door, 1));
/* 124:119 */     GT_Mod.gregtechproxy.mSoundNames.add("random.door_open");
/* 125:120 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 126:121 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.wooden_door, 1));
/* 127:122 */     GT_Mod.gregtechproxy.mSoundNames.add("random.door_close");
/* 128:123 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 129:124 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Items.porkchop, 1));
/* 130:125 */     GT_Mod.gregtechproxy.mSoundNames.add("random.eat");
/* 131:126 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 132:127 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.wool, 1));
/* 133:128 */     GT_Mod.gregtechproxy.mSoundNames.add("step.cloth");
/* 134:129 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 135:130 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.grass, 1));
/* 136:131 */     GT_Mod.gregtechproxy.mSoundNames.add("step.grass");
/* 137:132 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 138:133 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.gravel, 1));
/* 139:134 */     GT_Mod.gregtechproxy.mSoundNames.add("step.gravel");
/* 140:135 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 141:136 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.snow, 1));
/* 142:137 */     GT_Mod.gregtechproxy.mSoundNames.add("step.snow");
/* 143:138 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 144:139 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.piston, 1));
/* 145:140 */     GT_Mod.gregtechproxy.mSoundNames.add("tile.piston.out");
/* 146:141 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 147:142 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.sticky_piston, 1));
/* 148:143 */     GT_Mod.gregtechproxy.mSoundNames.add("tile.piston.in");
/* 149:144 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 150:145 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.mossy_cobblestone, 1));
/* 151:146 */     GT_Mod.gregtechproxy.mSoundNames.add("ambient.cave.cave");
/* 152:147 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 153:148 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.lapis_block, 1));
/* 154:149 */     GT_Mod.gregtechproxy.mSoundNames.add("ambient.weather.rain");
/* 155:150 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 156:151 */     GT_Mod.gregtechproxy.mSoundItems.add(new ItemStack(Blocks.diamond_block, 1));
/* 157:152 */     GT_Mod.gregtechproxy.mSoundNames.add("ambient.weather.thunder");
/* 158:153 */     GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 159:    */   }
/* 160:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.load.GT_SonictronLoader
 * JD-Core Version:    0.7.0.1
 */