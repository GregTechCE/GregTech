/*   1:    */ package gregtech.common;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
/*   4:    */ import gregtech.api.GregTech_API;
/*   5:    */ import gregtech.api.enums.ItemList;
/*   6:    */ import gregtech.api.enums.Materials;
/*   7:    */ import gregtech.api.enums.OrePrefixes;
/*   8:    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*   9:    */ import gregtech.api.util.GT_Config;
/*  10:    */ import gregtech.api.util.GT_ModHandler;
/*  11:    */ import gregtech.api.util.GT_Recipe;
/*  12:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraftforge.fluids.Fluid;
/*  16:    */ import net.minecraftforge.fluids.FluidStack;
/*  17:    */ 
/*  18:    */ public class GT_RecipeAdder
/*  19:    */   implements IGT_RecipeAdder
/*  20:    */ {
/*  21:    */   public boolean addFusionReactorRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt, int aStartEU)
/*  22:    */   {
/*  23: 26 */     return false;
/*  31:    */   }

			@Override
			public boolean addFusionReactorRecipe(FluidStack aInput1, FluidStack aInput2,FluidStack aOutput1, int aDuration, int aEUt, int aStartEU) {
				if(aInput1==null||aInput2==null||aOutput1==null||aDuration<1||aEUt<1||aStartEU<1){
					return false;
				}
				GT_Recipe.GT_Recipe_Map.sFusionRecipes.addRecipe( null, new FluidStack[] {aInput1, aInput2},new FluidStack[]{aOutput1}, aDuration, aEUt, aStartEU);
				return true;
			}
/*  32:    */   
/*  33:    */   public boolean addCentrifugeRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int aDuration)
/*  34:    */   {
/*  35: 34 */     return addCentrifugeRecipe(aInput1, aInput2 < 0 ? ItemList.IC2_Fuel_Can_Empty.get(-aInput2, new Object[0]) : aInput2 > 0 ? ItemList.Cell_Empty.get(aInput2, new Object[0]) : null, null, null, aOutput1, aOutput2, aOutput3, aOutput4, aOutput5, aOutput6, null, aDuration, 5);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean addCentrifugeRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int[] aChances, int aDuration, int aEUt)
/*  39:    */   {
/*  40: 39 */     if (((aInput1 == null) && (aFluidInput == null)) || ((aOutput1 == null) && (aFluidOutput == null))) {
/*  41: 39 */       return false;
/*  42:    */     }
/*  43: 40 */     if ((aInput1 != null) && ((aDuration = GregTech_API.sRecipeFile.get("centrifuge", aInput1, aDuration)) <= 0)) {
/*  44: 40 */       return false;
/*  45:    */     }
/*  46: 41 */     if ((aFluidInput != null) && ((aDuration = GregTech_API.sRecipeFile.get("centrifuge", aFluidInput.getFluid().getName(), aDuration)) <= 0)) {
/*  47: 41 */       return false;
/*  48:    */     }
/*  49: 42 */     GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2 }, new ItemStack[] { aOutput1, aOutput2, aOutput3, aOutput4, aOutput5, aOutput6 }, null, aChances, new FluidStack[] { aFluidInput }, new FluidStack[] { aFluidOutput }, aDuration, aEUt, 0);
/*  50: 43 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean addElectrolyzerRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int aDuration, int aEUt)
/*  54:    */   {
/*  55: 48 */     return addElectrolyzerRecipe(aInput1, aInput2 < 0 ? ItemList.IC2_Fuel_Can_Empty.get(-aInput2, new Object[0]) : aInput2 > 0 ? ItemList.Cell_Empty.get(aInput2, new Object[0]) : null, null, null, aOutput1, aOutput2, aOutput3, aOutput4, aOutput5, aOutput6, null, aDuration, aEUt);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean addElectrolyzerRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, ItemStack aOutput5, ItemStack aOutput6, int[] aChances, int aDuration, int aEUt)
/*  59:    */   {
/*  60: 53 */     if (((aInput1 == null) && (aFluidInput == null)) || ((aOutput1 == null) && (aFluidOutput == null))) {
/*  61: 53 */       return false;
/*  62:    */     }
/*  63: 54 */     if ((aInput1 != null) && ((aDuration = GregTech_API.sRecipeFile.get("electrolyzer", aInput1, aDuration)) <= 0)) {
/*  64: 54 */       return false;
/*  65:    */     }
/*  66: 55 */     if ((aFluidInput != null) && ((aDuration = GregTech_API.sRecipeFile.get("electrolyzer", aFluidInput.getFluid().getName(), aDuration)) <= 0)) {
/*  67: 55 */       return false;
/*  68:    */     }
/*  69: 56 */     GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2 }, new ItemStack[] { aOutput1, aOutput2, aOutput3, aOutput4, aOutput5, aOutput6 }, null, aChances, new FluidStack[] { aFluidInput }, new FluidStack[] { aFluidOutput }, aDuration, aEUt, 0);
/*  70: 57 */     return true;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput, int aDuration)
/*  74:    */   {
/*  75: 62 */     return addChemicalRecipe(aInput1, aInput2, null, null, aOutput, aDuration);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration)
/*  79:    */   {
/*  80: 67 */     if (((aInput1 == null) && (aFluidInput == null)) || ((aOutput == null) && (aFluidOutput == null))) {
/*  81: 67 */       return false;
/*  82:    */     }
/*  83: 68 */     if ((aOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("chemicalreactor", aOutput, aDuration)) <= 0)) {
/*  84: 68 */       return false;
/*  85:    */     }
/*  86: 69 */     if ((aFluidOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("chemicalreactor", aFluidOutput.getFluid().getName(), aDuration)) <= 0)) {
/*  87: 69 */       return false;
/*  88:    */     }
/*  89: 70 */     GT_Recipe.GT_Recipe_Map.sChemicalRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2 }, new ItemStack[] { aOutput }, null, null, new FluidStack[] { aFluidInput }, new FluidStack[] { aFluidOutput }, aDuration, 30, 0);
/*  90: 71 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel)
/*  94:    */   {
/*  95: 76 */     return addBlastRecipe(aInput1, aInput2, null, null, aOutput1, aOutput2, aDuration, aEUt, aLevel);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean addBlastRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel)
/*  99:    */   {
/* 100: 81 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 101: 81 */       return false;
/* 102:    */     }
/* 103: 82 */     if ((aDuration = GregTech_API.sRecipeFile.get("blastfurnace", aInput1, aDuration)) <= 0) {
/* 104: 82 */       return false;
/* 105:    */     }
/* 106: 83 */     GT_Recipe.GT_Recipe_Map.sBlastRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2 }, new ItemStack[] { aOutput1, aOutput2 }, null, null, new FluidStack[] { aFluidInput }, null, aDuration, aEUt, aLevel);
/* 107: 84 */     return true;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean addCannerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt)
/* 111:    */   {
/* 112: 89 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 113: 89 */       return false;
/* 114:    */     }
/* 115: 90 */     if ((aDuration = GregTech_API.sRecipeFile.get("canning", aInput1, aDuration)) <= 0) {
/* 116: 90 */       return false;
/* 117:    */     }
/* 118: 91 */     new GT_Recipe(aInput1, aEUt, aInput2, aDuration, aOutput1, aOutput2);
/* 119: 92 */     return true;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt)
/* 123:    */   {
/* 124: 97 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 125: 97 */       return false;
/* 126:    */     }
/* 127: 98 */     if ((aInput2 == null) && ((OrePrefixes.ingot.contains(aInput1)) || (OrePrefixes.dust.contains(aInput1)) || (OrePrefixes.gem.contains(aInput1)))) {
/* 128: 98 */       return false;
/* 129:    */     }
/* 130: 99 */     if ((aDuration = GregTech_API.sRecipeFile.get("alloysmelting", aInput2 == null ? aInput1 : aOutput1, aDuration)) <= 0) {
/* 131: 99 */       return false;
/* 132:    */     }
/* 133:100 */     new GT_Recipe(aInput1, aInput2, aEUt, aDuration, aOutput1);
/* 134:101 */     return true;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean addCNCRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt)
/* 138:    */   {
/* 139:106 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 140:106 */       return false;
/* 141:    */     }
/* 142:107 */     if ((aDuration = GregTech_API.sRecipeFile.get("cnc", aOutput1, aDuration)) <= 0) {
/* 143:107 */       return false;
/* 144:    */     }
/* 145:109 */     return true;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean addLatheRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt)
/* 149:    */   {
/* 150:114 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 151:114 */       return false;
/* 152:    */     }
/* 153:115 */     if ((aDuration = GregTech_API.sRecipeFile.get("lathe", aInput1, aDuration)) <= 0) {
/* 154:115 */       return false;
/* 155:    */     }
/* 156:116 */     new GT_Recipe(aInput1, aOutput1, aOutput2, aDuration, aEUt);
/* 157:117 */     return true;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean addCutterRecipe(ItemStack aInput, FluidStack aLubricant, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt)
/* 161:    */   {
/* 162:122 */     if ((aInput == null) || (aLubricant == null) || (aOutput1 == null)) {
/* 163:122 */       return false;
/* 164:    */     }
/* 165:123 */     if ((aDuration = GregTech_API.sRecipeFile.get("cutting", aInput, aDuration)) <= 0) {
/* 166:123 */       return false;
/* 167:    */     }
/* 168:124 */     GT_Recipe.GT_Recipe_Map.sCutterRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2 }, null, new FluidStack[] { aLubricant }, null, aDuration, aEUt, 0);
/* 169:125 */     return true;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean addCutterRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt)
/* 173:    */   {
/* 174:130 */     if ((aInput == null) || (aOutput1 == null)) {
/* 175:130 */       return false;
/* 176:    */     }
/* 177:131 */     if ((aDuration = GregTech_API.sRecipeFile.get("cutting", aInput, aDuration)) <= 0) {
/* 178:131 */       return false;
/* 179:    */     }
/* 180:132 */     GT_Recipe.GT_Recipe_Map.sCutterRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2 }, null, new FluidStack[] { Materials.Water.getFluid(Math.max(4, Math.min(1000, aDuration * aEUt / 320))) }, null, aDuration * 2, aEUt, 0);
/* 181:133 */     GT_Recipe.GT_Recipe_Map.sCutterRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2 }, null, new FluidStack[] { GT_ModHandler.getDistilledWater(Math.max(3, Math.min(750, aDuration * aEUt / 426))) }, null, aDuration * 2, aEUt, 0);
/* 182:134 */     GT_Recipe.GT_Recipe_Map.sCutterRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2 }, null, new FluidStack[] { Materials.Lubricant.getFluid(Math.max(1, Math.min(250, aDuration * aEUt / 1280))) }, null, aDuration, aEUt, 0);
/* 183:135 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */    public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt)
  {
    if ((aInput1 == null) || (aOutput1 == null)) {
      return false;
    }
    if ((aDuration = GregTech_API.sRecipeFile.get("assembling", aOutput1, aDuration)) <= 0) {
      return false;
    }
    GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2 == null ?  aInput1  : aInput2 }, new ItemStack[] { aOutput1 }, null, null, null, aDuration, aEUt, 0);
    return true;
  }
/* 197:    */   
/* 198:    */   public boolean addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, FluidStack aFluidInput, ItemStack aOutput1, int aDuration, int aEUt)
/* 199:    */   {
/* 200:148 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 201:148 */       return false;
/* 202:    */     }
/* 203:149 */     if ((aDuration = GregTech_API.sRecipeFile.get("assembling", aOutput1, aDuration)) <= 0) {
/* 204:149 */       return false;
/* 205:    */     }
/* 206:150 */     GT_Recipe.GT_Recipe_Map.sAssemblerRecipes.addRecipe(true, new ItemStack[] { aInput1, (ItemStack) (aInput2 == null ? new ItemStack[] { aInput1 } : aInput2) }, new ItemStack[] { aOutput1 }, null, new FluidStack[] { aFluidInput == null ? null : aFluidInput }, null, aDuration, aEUt, 0);
/* 207:151 */     return true;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean addWiremillRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt)
/* 211:    */   {
/* 212:156 */     if ((aInput == null) || (aOutput == null)) {
/* 213:156 */       return false;
/* 214:    */     }
/* 215:157 */     if ((aDuration = GregTech_API.sRecipeFile.get("wiremill", aInput, aDuration)) <= 0) {
/* 216:157 */       return false;
/* 217:    */     }
/* 218:158 */     GT_Recipe.GT_Recipe_Map.sWiremillRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput }, null, null, null, aDuration, aEUt, 0);
/* 219:159 */     return true;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean addPolarizerRecipe(ItemStack aInput, ItemStack aOutput, int aDuration, int aEUt)
/* 223:    */   {
/* 224:164 */     if ((aInput == null) || (aOutput == null)) {
/* 225:164 */       return false;
/* 226:    */     }
/* 227:165 */     if ((aDuration = GregTech_API.sRecipeFile.get("polarizer", aInput, aDuration)) <= 0) {
/* 228:165 */       return false;
/* 229:    */     }
/* 230:166 */     GT_Recipe.GT_Recipe_Map.sPolarizerRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput }, null, null, null, aDuration, aEUt, 0);
/* 231:167 */     return true;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean addBenderRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt)
/* 235:    */   {
/* 236:172 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 237:172 */       return false;
/* 238:    */     }
/* 239:173 */     if ((aDuration = GregTech_API.sRecipeFile.get("bender", aInput1, aDuration)) <= 0) {
/* 240:173 */       return false;
/* 241:    */     }
/* 242:174 */     new GT_Recipe(aEUt, aDuration, aInput1, aOutput1);
/* 243:175 */     return true;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public boolean addExtruderRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt)
/* 247:    */   {
/* 248:180 */     if ((aInput == null) || (aShape == null) || (aOutput == null)) {
/* 249:180 */       return false;
/* 250:    */     }
/* 251:181 */     if ((aDuration = GregTech_API.sRecipeFile.get("extruder", aOutput, aDuration)) <= 0) {
/* 252:181 */       return false;
/* 253:    */     }
/* 254:182 */     GT_Recipe.GT_Recipe_Map.sExtruderRecipes.addRecipe(true, new ItemStack[] { aInput, aShape }, new ItemStack[] { aOutput }, null, null, null, aDuration, aEUt, 0);
/* 255:183 */     return true;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean addSlicerRecipe(ItemStack aInput, ItemStack aShape, ItemStack aOutput, int aDuration, int aEUt)
/* 259:    */   {
/* 260:188 */     if ((aInput == null) || (aShape == null) || (aOutput == null)) {
/* 261:188 */       return false;
/* 262:    */     }
/* 263:189 */     if ((aDuration = GregTech_API.sRecipeFile.get("slicer", aOutput, aDuration)) <= 0) {
/* 264:189 */       return false;
/* 265:    */     }
/* 266:190 */     GT_Recipe.GT_Recipe_Map.sSlicerRecipes.addRecipe(true, new ItemStack[] { aInput, aShape }, new ItemStack[] { aOutput }, null, null, null, aDuration, aEUt, 0);
/* 267:191 */     return true;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean addImplosionRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2)
/* 271:    */   {
/* 272:196 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 273:196 */       return false;
/* 274:    */     }
/* 275:197 */     if ((aInput2 = GregTech_API.sRecipeFile.get("implosion", aInput1, aInput2)) <= 0) {
/* 276:197 */       return false;
/* 277:    */     }
/* 278:198 */     new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2);
/* 279:199 */     return true;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public boolean addDistillationRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt)
/* 283:    */   {
/* 284:204 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 285:204 */       return false;
/* 286:    */     }
/* 287:205 */     if ((aDuration = GregTech_API.sRecipeFile.get("distillation", aInput1, aDuration)) <= 0) {
/* 288:205 */       return false;
/* 289:    */     }
/* 290:206 */     new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, aDuration, aEUt);
/* 291:207 */     return true;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean addVacuumFreezerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration)
/* 295:    */   {
/* 296:212 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 297:212 */       return false;
/* 298:    */     }
/* 299:213 */     if ((aDuration = GregTech_API.sRecipeFile.get("vacuumfreezer", aInput1, aDuration)) <= 0) {
/* 300:213 */       return false;
/* 301:    */     }
/* 302:214 */     new GT_Recipe(aInput1, aOutput1, aDuration);
/* 303:215 */     return true;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean addGrinderRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4)
/* 307:    */   {
/* 308:220 */     return false;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public boolean addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType)
/* 312:    */   {
/* 313:225 */     if (aInput1 == null) {
/* 314:225 */       return false;
/* 315:    */     }
/* 316:226 */     new GT_Recipe(aInput1, aOutput1, GregTech_API.sRecipeFile.get("fuel_" + aType, aInput1, aEU), aType);
/* 317:227 */     return true;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public boolean addSonictronSound(ItemStack aItemStack, String aSoundName)
/* 321:    */   {
/* 322:232 */     if ((aItemStack == null) || (aSoundName == null) || (aSoundName.equals(""))) {
/* 323:232 */       return false;
/* 324:    */     }
/* 325:233 */     GT_Mod.gregtechproxy.mSoundItems.add(aItemStack);
/* 326:234 */     GT_Mod.gregtechproxy.mSoundNames.add(aSoundName);
/* 327:235 */     if (aSoundName.startsWith("note.")) {
/* 328:236 */       GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(25));
/* 329:    */     } else {
/* 330:238 */       GT_Mod.gregtechproxy.mSoundCounts.add(Integer.valueOf(1));
/* 331:    */     }
/* 332:240 */     return true;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean addForgeHammerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt)
/* 336:    */   {
/* 337:245 */     if ((aInput1 == null) || (aOutput1 == null)) {
/* 338:245 */       return false;
/* 339:    */     }
/* 340:246 */     if (!GregTech_API.sRecipeFile.get("forgehammer", aOutput1, true)) {
/* 341:246 */       return false;
/* 342:    */     }
/* 343:247 */     GT_Recipe.GT_Recipe_Map.sHammerRecipes.addRecipe(true, new ItemStack[] { aInput1 }, new ItemStack[] { aOutput1 }, null, null, null, aDuration, aEUt, 0);
/* 344:248 */     return true;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public boolean addBoxingRecipe(ItemStack aContainedItem, ItemStack aEmptyBox, ItemStack aFullBox, int aDuration, int aEUt)
/* 348:    */   {
/* 349:253 */     if ((aContainedItem == null) || (aFullBox == null)) {
/* 350:253 */       return false;
/* 351:    */     }
/* 352:254 */     if (!GregTech_API.sRecipeFile.get("boxing", aFullBox, true)) {
/* 353:254 */       return false;
/* 354:    */     }
/* 355:255 */     GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes.addRecipe(true, new ItemStack[] { aContainedItem, aEmptyBox }, new ItemStack[] { aFullBox }, null, null, null, aDuration, aEUt, 0);
/* 356:256 */     return true;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean addUnboxingRecipe(ItemStack aFullBox, ItemStack aContainedItem, ItemStack aEmptyBox, int aDuration, int aEUt)
/* 360:    */   {
/* 361:261 */     if ((aFullBox == null) || (aContainedItem == null)) {
/* 362:261 */       return false;
/* 363:    */     }
/* 364:262 */     if (!GregTech_API.sRecipeFile.get("unboxing", aFullBox, true)) {
/* 365:262 */       return false;
/* 366:    */     }
/* 367:263 */     GT_Recipe.GT_Recipe_Map.sUnboxinatorRecipes.addRecipe(true, new ItemStack[] { aFullBox }, new ItemStack[] { aContainedItem, aEmptyBox }, null, null, null, aDuration, aEUt, 0);
/* 368:264 */     return true;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public boolean addAmplifier(ItemStack aAmplifierItem, int aDuration, int aAmplifierAmountOutputted)
/* 372:    */   {
/* 373:269 */     if ((aAmplifierItem == null) || (aAmplifierAmountOutputted <= 0)) {
/* 374:269 */       return false;
/* 375:    */     }
/* 376:270 */     if ((aDuration = GregTech_API.sRecipeFile.get("amplifier", aAmplifierItem, aDuration)) <= 0) {
/* 377:270 */       return false;
/* 378:    */     }
/* 379:271 */     GT_Recipe.GT_Recipe_Map.sAmplifiers.addRecipe(true, new ItemStack[] { aAmplifierItem }, null, null, null, new FluidStack[] { Materials.UUAmplifier.getFluid(aAmplifierAmountOutputted) }, aDuration, 32, 0);
/* 380:272 */     return true;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public boolean addBrewingRecipe(ItemStack aIngredient, Fluid aInput, Fluid aOutput, boolean aHidden)
/* 384:    */   {
/* 385:277 */     if ((aIngredient == null) || (aInput == null) || (aOutput == null)) {
/* 386:277 */       return false;
/* 387:    */     }
/* 388:278 */     if (!GregTech_API.sRecipeFile.get("brewing", aOutput.getUnlocalizedName(), true)) {
/* 389:278 */       return false;
/* 390:    */     }
/* 391:279 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sBrewingRecipes.addRecipe(false, new ItemStack[] { aIngredient }, null, null, new FluidStack[] { new FluidStack(aInput, 750) }, new FluidStack[] { new FluidStack(aOutput, 750) }, 128, 4, 0);
/* 392:280 */     if ((aHidden) && (tRecipe != null)) {
/* 393:280 */       tRecipe.mHidden = true;
/* 394:    */     }
/* 395:281 */     return true;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean addFermentingRecipe(FluidStack aInput, FluidStack aOutput, int aDuration, boolean aHidden)
/* 399:    */   {
/* 400:286 */     if ((aInput == null) || (aOutput == null)) {
/* 401:286 */       return false;
/* 402:    */     }
/* 403:287 */     if ((aDuration = GregTech_API.sRecipeFile.get("fermenting", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
/* 404:287 */       return false;
/* 405:    */     }
/* 406:288 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFermentingRecipes.addRecipe(false, null, null, null, new FluidStack[] { aInput }, new FluidStack[] { aOutput }, aDuration, 2, 0);
/* 407:289 */     if ((aHidden) && (tRecipe != null)) {
/* 408:289 */       tRecipe.mHidden = true;
/* 409:    */     }
/* 410:290 */     return true;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public boolean addDistilleryRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt, boolean aHidden)
/* 414:    */   {
/* 415:295 */     if ((aInput == null) || (aOutput == null)) {
/* 416:295 */       return false;
/* 417:    */     }
/* 418:296 */     if ((aDuration = GregTech_API.sRecipeFile.get("distillery", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
/* 419:296 */       return false;
/* 420:    */     }
/* 421:297 */     GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sDistilleryRecipes.addRecipe(true, new ItemStack[] { aCircuit }, null, null, new FluidStack[] { aInput }, new FluidStack[] { aOutput }, aDuration, aEUt, 0);
/* 422:298 */     if ((aHidden) && (tRecipe != null)) {
/* 423:298 */       tRecipe.mHidden = true;
/* 424:    */     }
/* 425:299 */     return true;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean addFluidSolidifierRecipe(ItemStack aMold, FluidStack aInput, ItemStack aOutput, int aDuration, int aEUt)
/* 429:    */   {
/* 430:304 */     if ((aMold == null) || (aInput == null) || (aOutput == null)) {
/* 431:304 */       return false;
/* 432:    */     }
/* 433:305 */     if ((aDuration = GregTech_API.sRecipeFile.get("fluidsolidifier", aOutput, aDuration)) <= 0) {
/* 434:305 */       return false;
/* 435:    */     }
/* 436:306 */     GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes.addRecipe(true, new ItemStack[] { aMold }, new ItemStack[] { aOutput }, null, new FluidStack[] { aInput }, null, aDuration, aEUt, 0);
/* 437:307 */     return true;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean addFluidSmelterRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt)
/* 441:    */   {
/* 442:312 */     if ((aInput == null) || (aOutput == null)) {
/* 443:312 */       return false;
/* 444:    */     }
/* 445:313 */     if ((aDuration = GregTech_API.sRecipeFile.get("fluidsmelter", aInput, aDuration)) <= 0) {
/* 446:313 */       return false;
/* 447:    */     }
/* 448:314 */     GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aRemains }, null, new int[] { aChance }, null, new FluidStack[] { aOutput }, aDuration, aEUt, 0);
/* 449:315 */     return true;
/* 450:    */   }
/* 451:    */   
/* 452:    */   public boolean addFluidExtractionRecipe(ItemStack aInput, ItemStack aRemains, FluidStack aOutput, int aChance, int aDuration, int aEUt)
/* 453:    */   {
/* 454:320 */     if ((aInput == null) || (aOutput == null)) {
/* 455:320 */       return false;
/* 456:    */     }
/* 457:321 */     if ((aDuration = GregTech_API.sRecipeFile.get("fluidextractor", aInput, aDuration)) <= 0) {
/* 458:321 */       return false;
/* 459:    */     }
/* 460:322 */     GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aRemains }, null, new int[] { aChance }, null, new FluidStack[] { aOutput }, aDuration, aEUt, 0);
/* 461:323 */     return true;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public boolean addFluidCannerRecipe(ItemStack aInput, ItemStack aOutput, FluidStack aFluidInput, FluidStack aFluidOutput)
/* 465:    */   {
/* 466:329 */     if ((aInput != null) && (aOutput != null))
/* 467:    */     {
/* 468:329 */       if ((aFluidInput == null ? 1 : 0) != (aFluidOutput == null ? 1 : 0)) {}
/* 469:    */     }
/* 470:    */     else {
/* 471:329 */       return false;
/* 472:    */     }
/* 473:330 */     if (!GregTech_API.sRecipeFile.get("fluidcanner", aOutput, true)) {
/* 474:330 */       return false;
/* 475:    */     }
/* 476:331 */     GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput }, null, new FluidStack[] { aFluidInput == null ? null : aFluidInput }, new FluidStack[] { aFluidOutput == null ? null : aFluidOutput }, aFluidOutput == null ? aFluidInput.amount / 62 : aFluidOutput.amount / 62, 1, 0);
/* 477:332 */     return true;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public boolean addChemicalBathRecipe(ItemStack aInput, FluidStack aBathingFluid, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt)
/* 481:    */   {
/* 482:337 */     if ((aInput == null) || (aBathingFluid == null) || (aOutput1 == null)) {
/* 483:337 */       return false;
/* 484:    */     }
/* 485:338 */     if ((aDuration = GregTech_API.sRecipeFile.get("chemicalbath", aInput, aDuration)) <= 0) {
/* 486:338 */       return false;
/* 487:    */     }
/* 488:339 */     GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2, aOutput3 }, null, aChances, new FluidStack[] { aBathingFluid }, null, aDuration, aEUt, 0);
/* 489:340 */     return true;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public boolean addElectromagneticSeparatorRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, int[] aChances, int aDuration, int aEUt)
/* 493:    */   {
/* 494:345 */     if ((aInput == null) || (aOutput1 == null)) {
/* 495:345 */       return false;
/* 496:    */     }
/* 497:346 */     if ((aDuration = GregTech_API.sRecipeFile.get("electromagneticseparator", aInput, aDuration)) <= 0) {
/* 498:346 */       return false;
/* 499:    */     }
/* 500:347 */     GT_Recipe.GT_Recipe_Map.sElectroMagneticSeparatorRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput1, aOutput2, aOutput3 }, null, aChances, null, null, aDuration, aEUt, 0);
/* 501:348 */     return true;
/* 502:    */   }
/* 503:    */   
/* 504:    */   public boolean addPrinterRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aSpecialSlot, ItemStack aOutput, int aDuration, int aEUt)
/* 505:    */   {
/* 506:353 */     if ((aInput == null) || (aFluid == null) || (aOutput == null)) {
/* 507:353 */       return false;
/* 508:    */     }
/* 509:354 */     if ((aDuration = GregTech_API.sRecipeFile.get("printer", aInput, aDuration)) <= 0) {
/* 510:354 */       return false;
/* 511:    */     }
/* 512:355 */     GT_Recipe.GT_Recipe_Map.sPrinterRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput }, aSpecialSlot, null, new FluidStack[] { aFluid }, null, aDuration, aEUt, 0);
/* 513:356 */     return true;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public boolean addAutoclaveRecipe(ItemStack aInput, FluidStack aFluid, ItemStack aOutput, int aChance, int aDuration, int aEUt)
/* 517:    */   {
/* 518:361 */     if ((aInput == null) || (aFluid == null) || (aOutput == null)) {
/* 519:361 */       return false;
/* 520:    */     }
/* 521:362 */     if ((aDuration = GregTech_API.sRecipeFile.get("autoclave", aInput, aDuration)) <= 0) {
/* 522:362 */       return false;
/* 523:    */     }
/* 524:363 */     GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes.addRecipe(true, new ItemStack[] { aInput }, new ItemStack[] { aOutput }, null, new int[] { aChance }, new FluidStack[] { aFluid }, null, aDuration, aEUt, 0);
/* 525:364 */     return true;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public boolean addMixerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aInput3, ItemStack aInput4, FluidStack aFluidInput, FluidStack aFluidOutput, ItemStack aOutput, int aDuration, int aEUt)
/* 529:    */   {
/* 530:369 */     if (((aInput1 == null) && (aFluidInput == null)) || ((aOutput == null) && (aFluidOutput == null))) {
/* 531:369 */       return false;
/* 532:    */     }
/* 533:370 */     if ((aOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("mixer", aOutput, aDuration)) <= 0)) {
/* 534:370 */       return false;
/* 535:    */     }
/* 536:371 */     if ((aFluidOutput != null) && ((aDuration = GregTech_API.sRecipeFile.get("mixer", aFluidOutput.getFluid().getName(), aDuration)) <= 0)) {
/* 537:371 */       return false;
/* 538:    */     }
/* 539:372 */     GT_Recipe.GT_Recipe_Map.sMixerRecipes.addRecipe(true, new ItemStack[] { aInput1, aInput2, aInput3, aInput4 }, new ItemStack[] { aOutput }, null, null, new FluidStack[] { aFluidInput }, new FluidStack[] { aFluidOutput }, aDuration, aEUt, 0);
/* 540:373 */     return true;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public boolean addLaserEngraverRecipe(ItemStack aItemToEngrave, ItemStack aLens, ItemStack aEngravedItem, int aDuration, int aEUt)
/* 544:    */   {
/* 545:378 */     if ((aItemToEngrave == null) || (aLens == null) || (aEngravedItem == null)) {
/* 546:378 */       return false;
/* 547:    */     }
/* 548:379 */     if ((aDuration = GregTech_API.sRecipeFile.get("laserengraving", aEngravedItem, aDuration)) <= 0) {
/* 549:379 */       return false;
/* 550:    */     }
/* 551:380 */     GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes.addRecipe(true, new ItemStack[] { aItemToEngrave, aLens }, new ItemStack[] { aEngravedItem }, null, null, null, aDuration, aEUt, 0);
/* 552:381 */     return true;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public boolean addFormingPressRecipe(ItemStack aItemToImprint, ItemStack aForm, ItemStack aImprintedItem, int aDuration, int aEUt)
/* 556:    */   {
/* 557:386 */     if ((aItemToImprint == null) || (aForm == null) || (aImprintedItem == null)) {
/* 558:386 */       return false;
/* 559:    */     }
/* 560:387 */     if ((aDuration = GregTech_API.sRecipeFile.get("press", aImprintedItem, aDuration)) <= 0) {
/* 561:387 */       return false;
/* 562:    */     }
/* 563:388 */     GT_Recipe.GT_Recipe_Map.sPressRecipes.addRecipe(true, new ItemStack[] { aItemToImprint, aForm }, new ItemStack[] { aImprintedItem }, null, null, null, aDuration, aEUt, 0);
/* 564:389 */     return true;
/* 565:    */   }
/* 566:    */   
/* 567:    */   public boolean addFluidHeaterRecipe(ItemStack aCircuit, FluidStack aInput, FluidStack aOutput, int aDuration, int aEUt)
/* 568:    */   {
/* 569:394 */     if ((aInput == null) || (aOutput == null)) {
/* 570:394 */       return false;
/* 571:    */     }
/* 572:395 */     if ((aDuration = GregTech_API.sRecipeFile.get("fluidheater", aOutput.getFluid().getUnlocalizedName(), aDuration)) <= 0) {
/* 573:395 */       return false;
/* 574:    */     }
/* 575:396 */     GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes.addRecipe(true, new ItemStack[] { aCircuit }, null, null, new FluidStack[] { aInput }, new FluidStack[] { aOutput }, aDuration, aEUt, 0);
/* 576:397 */     return true;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public boolean addSifterRecipe(ItemStack aItemToSift, ItemStack[] aSiftedItems, int[] aChances, int aDuration, int aEUt)
/* 580:    */   {
/* 581:402 */     if ((aItemToSift == null) || (aSiftedItems == null)) {
/* 582:402 */       return false;
/* 583:    */     }
/* 584:403 */     for (ItemStack tStack : aSiftedItems) {
/* 585:403 */       if (tStack != null)
/* 586:    */       {
/* 587:404 */         if ((aDuration = GregTech_API.sRecipeFile.get("sifter", aItemToSift, aDuration)) <= 0) {
/* 588:404 */           return false;
/* 589:    */         }
/* 590:405 */         GT_Recipe.GT_Recipe_Map.sSifterRecipes.addRecipe(true, new ItemStack[] { aItemToSift }, aSiftedItems, null, aChances, null, null, aDuration, aEUt, 0);
/* 591:406 */         return true;
/* 592:    */       }
/* 593:    */     }
/* 594:408 */     return false;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public boolean addArcFurnaceRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt)
/* 598:    */   {
/* 599:413 */     if ((aInput == null) || (aOutputs == null)) {
/* 600:413 */       return false;
/* 601:    */     }
/* 602:414 */     for (ItemStack tStack : aOutputs) {
/* 603:414 */       if (tStack != null)
/* 604:    */       {
/* 605:415 */         if ((aDuration = GregTech_API.sRecipeFile.get("arcfurnace", aInput, aDuration)) <= 0) {
/* 606:415 */           return false;
/* 607:    */         }
/* 608:416 */         GT_Recipe.GT_Recipe_Map.sArcFurnaceRecipes.addRecipe(true, new ItemStack[] { aInput }, aOutputs, null, aChances, new FluidStack[] { Materials.Oxygen.getGas(aDuration) }, null, Math.max(1, aDuration), Math.max(1, aEUt), 0);
/* 609:417 */         for (Materials tMaterial : new Materials[] { Materials.Argon, Materials.Nitrogen }) {
/* 610:417 */           if (tMaterial.mPlasma != null)
/* 611:    */           {
/* 612:418 */             int tPlasmaAmount = (int)Math.max(1L, aDuration / (tMaterial.getMass() * 16L));
/* 613:419 */             GT_Recipe.GT_Recipe_Map.sPlasmaArcFurnaceRecipes.addRecipe(true, new ItemStack[] { aInput }, aOutputs, null, aChances, new FluidStack[] { tMaterial.getPlasma(tPlasmaAmount) }, new FluidStack[] { tMaterial.getGas(tPlasmaAmount) }, Math.max(1, aDuration / 16), Math.max(1, aEUt / 3), 0);
/* 614:    */           }
/* 615:    */         }
/* 616:421 */         return true;
/* 617:    */       }
/* 618:    */     }
/* 619:423 */     return false;
/* 620:    */   }
/* 621:    */   
/* 622:    */   public boolean addPulveriserRecipe(ItemStack aInput, ItemStack[] aOutputs, int[] aChances, int aDuration, int aEUt)
/* 623:    */   {
/* 624:428 */     if ((aInput == null) || (aOutputs == null)) {
/* 625:428 */       return false;
/* 626:    */     }
/* 627:429 */     for (ItemStack tStack : aOutputs) {
/* 628:429 */       if (tStack != null)
/* 629:    */       {
/* 630:430 */         if ((aDuration = GregTech_API.sRecipeFile.get("pulveriser", aInput, aDuration)) <= 0) {
/* 631:430 */           return false;
/* 632:    */         }
/* 633:431 */         GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.addRecipe(true, new ItemStack[] { aInput }, aOutputs, null, aChances, null, null, aDuration, aEUt, 0);
/* 634:432 */         return true;
/* 635:    */       }
/* 636:    */     }
/* 637:434 */     return false;
/* 638:    */   }
/* 639:    */
 }
