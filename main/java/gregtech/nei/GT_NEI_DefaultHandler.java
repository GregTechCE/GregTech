/*   1:    */ package gregtech.nei;
/*   2:    */ 
/*   3:    */ import codechicken.lib.gui.GuiDraw;
/*   4:    */ import codechicken.nei.PositionedStack;
/*   5:    */ import codechicken.nei.guihook.GuiContainerManager;
/*   6:    */ import codechicken.nei.guihook.IContainerInputHandler;
/*   7:    */ import codechicken.nei.guihook.IContainerTooltipHandler;
/*   8:    */ import codechicken.nei.recipe.GuiCraftingRecipe;
/*   9:    */ import codechicken.nei.recipe.GuiRecipe;
/*  10:    */ import codechicken.nei.recipe.GuiUsageRecipe;
/*  11:    */ import codechicken.nei.recipe.TemplateRecipeHandler;
/*  12:    */ import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
/*  13:    */ import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
/*  14:    */ import com.google.common.collect.ListMultimap;
/*  15:    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*  16:    */ import gregtech.api.enums.GT_Values;
/*  17:    */ import gregtech.api.enums.OrePrefixes;
/*  18:    */ import gregtech.api.gui.GT_GUIContainer_BasicMachine;
/*  19:    */ import gregtech.api.objects.ItemData;
/*  20:    */ import gregtech.api.objects.MaterialStack;
/*  21:    */ import gregtech.api.util.GT_LanguageManager;
/*  22:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  23:    */ import gregtech.api.util.GT_Recipe;
/*  24:    */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/*  25:    */ import gregtech.api.util.GT_Utility;
/*  26:    */ import java.awt.Point;
/*  27:    */ import java.awt.Rectangle;
/*  28:    */ import java.util.ArrayList;
/*  29:    */ import java.util.Collection;
/*  30:    */ import java.util.Iterator;
/*  31:    */ import java.util.LinkedList;
/*  32:    */ import java.util.List;
/*  33:    */ import net.minecraft.client.Minecraft;
/*  34:    */ import net.minecraft.client.gui.FontRenderer;
/*  35:    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*  36:    */ import net.minecraft.init.Blocks;
/*  37:    */ import net.minecraft.item.ItemStack;
/*  38:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  39:    */ import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
/*  40:    */ import net.minecraftforge.fluids.FluidStack;
/*  41:    */ import org.lwjgl.opengl.GL11;
/*  42:    */ 
/*  43:    */ public class GT_NEI_DefaultHandler
/*  44:    */   extends TemplateRecipeHandler
/*  45:    */ {
/*  46:    */   protected final GT_Recipe.GT_Recipe_Map mRecipeMap;
/*  47:    */   public static final int sOffsetX = 5;
/*  48:    */   public static final int sOffsetY = 11;
/*  49:    */   
/*  50:    */   public GT_NEI_DefaultHandler(GT_Recipe.GT_Recipe_Map aRecipeMap)
/*  51:    */   {
/*  52: 43 */     this.mRecipeMap = aRecipeMap;
/*  53: 44 */     this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(65, 13, 36, 18), getOverlayIdentifier(), new Object[0]));
/*  54: 46 */     if (!NEI_GT_Config.sIsAdded)
/*  55:    */     {
/*  56: 47 */       FMLInterModComms.sendRuntimeMessage(GT_Values.GT, "NEIPlugins", "register-crafting-handler", "gregtech@" + getRecipeName() + "@" + getOverlayIdentifier());
/*  57: 48 */       GuiCraftingRecipe.craftinghandlers.add(this);
/*  58: 49 */       GuiUsageRecipe.usagehandlers.add(this);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public TemplateRecipeHandler newInstance()
/*  63:    */   {
/*  64: 55 */     return new GT_NEI_DefaultHandler(this.mRecipeMap);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public class FixedPositionedStack
/*  68:    */     extends PositionedStack
/*  69:    */   {
/*  70: 59 */     public boolean permutated = false;
/*  71:    */     public final int mChance;
/*  72:    */     
/*  73:    */     public FixedPositionedStack(Object object, int x, int y)
/*  74:    */     {
/*  75: 63 */       this(object, x, y, 0);
/*  76:    */     }
/*  77:    */     
/*  78:    */     public FixedPositionedStack(Object object, int x, int y, int aChance)
/*  79:    */     {
/*  80: 67 */       super(object,x, y, true);
/*  81: 68 */       this.mChance = aChance;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public void generatePermutations()
/*  85:    */     {
/*  86: 73 */       if (this.permutated) {
/*  87: 73 */         return;
/*  88:    */       }
/*  89: 75 */       ArrayList<ItemStack> tDisplayStacks = new ArrayList();
/*  90: 76 */       for (ItemStack tStack : this.items) {
/*  91: 76 */         if (GT_Utility.isStackValid(tStack)) {
/*  92: 77 */           if (tStack.getItemDamage() == 32767)
/*  93:    */           {
/*  94: 78 */             List<ItemStack> permutations = codechicken.nei.ItemList.itemMap.get(tStack.getItem());
/*  95: 79 */             if (!permutations.isEmpty())
/*  96:    */             {
/*  97:    */               ItemStack stack;
/*  98: 80 */               for (Iterator i$ = permutations.iterator(); i$.hasNext(); tDisplayStacks.add(GT_Utility.copyAmount(tStack.stackSize, new Object[] { stack }))) {
/*  99: 80 */                 stack = (ItemStack)i$.next();
/* 100:    */               }
/* 101:    */             }
/* 102:    */             else
/* 103:    */             {
/* 104: 82 */               ItemStack base = new ItemStack(tStack.getItem(), tStack.stackSize);
/* 105: 83 */               base.stackTagCompound = tStack.stackTagCompound;
/* 106: 84 */               tDisplayStacks.add(base);
/* 107:    */             }
/* 108:    */           }
/* 109:    */           else
/* 110:    */           {
/* 111: 87 */             tDisplayStacks.add(GT_Utility.copy(new Object[] { tStack }));
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115: 91 */       this.items = ((ItemStack[])tDisplayStacks.toArray(new ItemStack[0]));
/* 116: 92 */       if (this.items.length == 0) {
/* 117: 92 */         this.items = new ItemStack[] { new ItemStack(Blocks.fire) };
/* 118:    */       }
/* 119: 93 */       this.permutated = true;
/* 120: 94 */       setPermutationToRender(0);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public class CachedDefaultRecipe
/* 125:    */     extends TemplateRecipeHandler.CachedRecipe
/* 126:    */   {
/* 127:    */     public final GT_Recipe mRecipe;
/* 128:101 */     public final List<PositionedStack> mOutputs = new ArrayList();
/* 129:102 */     public final List<PositionedStack> mInputs = new ArrayList();
/* 130:    */     
/* 131:    */     public List<PositionedStack> getIngredients()
/* 132:    */     {
/* 133:106 */       return getCycledIngredients(GT_NEI_DefaultHandler.this.cycleticks / 10, this.mInputs);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public PositionedStack getResult()
/* 137:    */     {
/* 138:111 */       return null;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public List<PositionedStack> getOtherStacks()
/* 142:    */     {
/* 143:116 */       return this.mOutputs;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public CachedDefaultRecipe(GT_Recipe aRecipe)
/* 147:    */     {
/* 148:119 */       super();
/* 149:120 */       this.mRecipe = aRecipe;
/* 150:    */       
/* 151:122 */       int tStartIndex = 0;
/* 152:124 */       switch (GT_NEI_DefaultHandler.this.mRecipeMap.mUsualInputCount)
/* 153:    */       {
/* 154:    */       case 0: 
/* 155:    */         break;
/* 156:    */       case 1: 
/* 157:128 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 158:128 */           this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 159:    */         }
/* 160:128 */         tStartIndex++;
/* 161:129 */         break;
/* 162:    */       case 2: 
/* 163:131 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 164:131 */           this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
/* 165:    */         }
/* 166:131 */         tStartIndex++;
/* 167:132 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 168:132 */           this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 169:    */         }
/* 170:132 */         tStartIndex++;
/* 171:133 */         break;
/* 172:    */       case 3: 
/* 173:135 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 174:135 */           this.mInputs.add(new FixedPositionedStack(aRecipe.getRepresentativeInput(tStartIndex), 12, 14));
/* 175:    */         }
/* 176:135 */         tStartIndex++;
/* 177:136 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 178:136 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
/* 179:    */         }
/* 180:136 */         tStartIndex++;
/* 181:137 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 182:137 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 183:    */         }
/* 184:137 */         tStartIndex++;
/* 185:138 */         break;
/* 186:    */       case 4: 
/* 187:140 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 188:140 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 5));
/* 189:    */         }
/* 190:140 */         tStartIndex++;
/* 191:141 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 192:141 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 5));
/* 193:    */         }
/* 194:141 */         tStartIndex++;
/* 195:142 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 196:142 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 23));
/* 197:    */         }
/* 198:142 */         tStartIndex++;
/* 199:143 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 200:143 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 23));
/* 201:    */         }
/* 202:143 */         tStartIndex++;
/* 203:144 */         break;
/* 204:    */       case 5: 
/* 205:146 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 206:146 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 5));
/* 207:    */         }
/* 208:146 */         tStartIndex++;
/* 209:147 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 210:147 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 5));
/* 211:    */         }
/* 212:147 */         tStartIndex++;
/* 213:148 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 214:148 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 5));
/* 215:    */         }
/* 216:148 */         tStartIndex++;
/* 217:149 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 218:149 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 23));
/* 219:    */         }
/* 220:149 */         tStartIndex++;
/* 221:150 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 222:150 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 23));
/* 223:    */         }
/* 224:150 */         tStartIndex++;
/* 225:151 */         break;
/* 226:    */       case 6: 
/* 227:153 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 228:153 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 5));
/* 229:    */         }
/* 230:153 */         tStartIndex++;
/* 231:154 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 232:154 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 5));
/* 233:    */         }
/* 234:154 */         tStartIndex++;
/* 235:155 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 236:155 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 5));
/* 237:    */         }
/* 238:155 */         tStartIndex++;
/* 239:156 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 240:156 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 23));
/* 241:    */         }
/* 242:156 */         tStartIndex++;
/* 243:157 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 244:157 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 23));
/* 245:    */         }
/* 246:157 */         tStartIndex++;
/* 247:158 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 248:158 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 23));
/* 249:    */         }
/* 250:158 */         tStartIndex++;
/* 251:159 */         break;
/* 252:    */       case 7: 
/* 253:161 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 254:161 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, -4));
/* 255:    */         }
/* 256:161 */         tStartIndex++;
/* 257:162 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 258:162 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, -4));
/* 259:    */         }
/* 260:162 */         tStartIndex++;
/* 261:163 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 262:163 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, -4));
/* 263:    */         }
/* 264:163 */         tStartIndex++;
/* 265:164 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 266:164 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 14));
/* 267:    */         }
/* 268:164 */         tStartIndex++;
/* 269:165 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 270:165 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
/* 271:    */         }
/* 272:165 */         tStartIndex++;
/* 273:166 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 274:166 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 275:    */         }
/* 276:166 */         tStartIndex++;
/* 277:167 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 278:167 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 32));
/* 279:    */         }
/* 280:167 */         tStartIndex++;
/* 281:168 */         break;
/* 282:    */       case 8: 
/* 283:170 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 284:170 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, -4));
/* 285:    */         }
/* 286:170 */         tStartIndex++;
/* 287:171 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 288:171 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, -4));
/* 289:    */         }
/* 290:171 */         tStartIndex++;
/* 291:172 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 292:172 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, -4));
/* 293:    */         }
/* 294:172 */         tStartIndex++;
/* 295:173 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 296:173 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 14));
/* 297:    */         }
/* 298:173 */         tStartIndex++;
/* 299:174 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 300:174 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
/* 301:    */         }
/* 302:174 */         tStartIndex++;
/* 303:175 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 304:175 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 305:    */         }
/* 306:175 */         tStartIndex++;
/* 307:176 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 308:176 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 32));
/* 309:    */         }
/* 310:176 */         tStartIndex++;
/* 311:177 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 312:177 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 32));
/* 313:    */         }
/* 314:177 */         tStartIndex++;
/* 315:178 */         break;
/* 316:    */       default: 
/* 317:180 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 318:180 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, -4));
/* 319:    */         }
/* 320:180 */         tStartIndex++;
/* 321:181 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 322:181 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, -4));
/* 323:    */         }
/* 324:181 */         tStartIndex++;
/* 325:182 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 326:182 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, -4));
/* 327:    */         }
/* 328:182 */         tStartIndex++;
/* 329:183 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 330:183 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 14));
/* 331:    */         }
/* 332:183 */         tStartIndex++;
/* 333:184 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 334:184 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 14));
/* 335:    */         }
/* 336:184 */         tStartIndex++;
/* 337:185 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 338:185 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 14));
/* 339:    */         }
/* 340:185 */         tStartIndex++;
/* 341:186 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 342:186 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 12, 32));
/* 343:    */         }
/* 344:186 */         tStartIndex++;
/* 345:187 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 346:187 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 30, 32));
/* 347:    */         }
/* 348:187 */         tStartIndex++;
/* 349:188 */         if (aRecipe.getRepresentativeInput(tStartIndex) != null) {
/* 350:188 */           this.mInputs.add(new FixedPositionedStack( aRecipe.getRepresentativeInput(tStartIndex), 48, 32));
/* 351:    */         }
/* 352:188 */         tStartIndex++;
/* 353:    */       }
/* 354:192 */       if (aRecipe.mSpecialItems != null) {
/* 355:192 */         this.mInputs.add(new FixedPositionedStack( aRecipe.mSpecialItems, 120, 52));
/* 356:    */       }
/* 357:194 */       tStartIndex = 0;
/* 358:196 */       switch (GT_NEI_DefaultHandler.this.mRecipeMap.mUsualOutputCount)
/* 359:    */       {
/* 360:    */       case 0: 
/* 361:    */         break;
/* 362:    */       case 1: 
/* 363:200 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 364:200 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 365:    */         }
/* 366:200 */         tStartIndex++;
/* 367:201 */         break;
/* 368:    */       case 2: 
/* 369:203 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 370:203 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 371:    */         }
/* 372:203 */         tStartIndex++;
/* 373:204 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 374:204 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
/* 375:    */         }
/* 376:204 */         tStartIndex++;
/* 377:205 */         break;
/* 378:    */       case 3: 
/* 379:207 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 380:207 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 381:    */         }
/* 382:207 */         tStartIndex++;
/* 383:208 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 384:208 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
/* 385:    */         }
/* 386:208 */         tStartIndex++;
/* 387:209 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 388:209 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 14, aRecipe.getOutputChance(tStartIndex)));
/* 389:    */         }
/* 390:209 */         tStartIndex++;
/* 391:210 */         break;
/* 392:    */       case 4: 
/* 393:212 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 394:212 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 5, aRecipe.getOutputChance(tStartIndex)));
/* 395:    */         }
/* 396:212 */         tStartIndex++;
/* 397:213 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 398:213 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 5, aRecipe.getOutputChance(tStartIndex)));
/* 399:    */         }
/* 400:213 */         tStartIndex++;
/* 401:214 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 402:214 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 23, aRecipe.getOutputChance(tStartIndex)));
/* 403:    */         }
/* 404:214 */         tStartIndex++;
/* 405:215 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 406:215 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 23, aRecipe.getOutputChance(tStartIndex)));
/* 407:    */         }
/* 408:215 */         tStartIndex++;
/* 409:216 */         break;
/* 410:    */       case 5: 
/* 411:218 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 412:218 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 5, aRecipe.getOutputChance(tStartIndex)));
/* 413:    */         }
/* 414:218 */         tStartIndex++;
/* 415:219 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 416:219 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 5, aRecipe.getOutputChance(tStartIndex)));
/* 417:    */         }
/* 418:219 */         tStartIndex++;
/* 419:220 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 420:220 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 5, aRecipe.getOutputChance(tStartIndex)));
/* 421:    */         }
/* 422:220 */         tStartIndex++;
/* 423:221 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 424:221 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 23, aRecipe.getOutputChance(tStartIndex)));
/* 425:    */         }
/* 426:221 */         tStartIndex++;
/* 427:222 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 428:222 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 23, aRecipe.getOutputChance(tStartIndex)));
/* 429:    */         }
/* 430:222 */         tStartIndex++;
/* 431:223 */         break;
/* 432:    */       case 6: 
/* 433:225 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 434:225 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 5, aRecipe.getOutputChance(tStartIndex)));
/* 435:    */         }
/* 436:225 */         tStartIndex++;
/* 437:226 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 438:226 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 5, aRecipe.getOutputChance(tStartIndex)));
/* 439:    */         }
/* 440:226 */         tStartIndex++;
/* 441:227 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 442:227 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 5, aRecipe.getOutputChance(tStartIndex)));
/* 443:    */         }
/* 444:227 */         tStartIndex++;
/* 445:228 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 446:228 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 23, aRecipe.getOutputChance(tStartIndex)));
/* 447:    */         }
/* 448:228 */         tStartIndex++;
/* 449:229 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 450:229 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 23, aRecipe.getOutputChance(tStartIndex)));
/* 451:    */         }
/* 452:229 */         tStartIndex++;
/* 453:230 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 454:230 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 23, aRecipe.getOutputChance(tStartIndex)));
/* 455:    */         }
/* 456:230 */         tStartIndex++;
/* 457:231 */         break;
/* 458:    */       case 7: 
/* 459:233 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 460:233 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, -4, aRecipe.getOutputChance(tStartIndex)));
/* 461:    */         }
/* 462:233 */         tStartIndex++;
/* 463:234 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 464:234 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, -4, aRecipe.getOutputChance(tStartIndex)));
/* 465:    */         }
/* 466:234 */         tStartIndex++;
/* 467:235 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 468:235 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, -4, aRecipe.getOutputChance(tStartIndex)));
/* 469:    */         }
/* 470:235 */         tStartIndex++;
/* 471:236 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 472:236 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 473:    */         }
/* 474:236 */         tStartIndex++;
/* 475:237 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 476:237 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
/* 477:    */         }
/* 478:237 */         tStartIndex++;
/* 479:238 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 480:238 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 14, aRecipe.getOutputChance(tStartIndex)));
/* 481:    */         }
/* 482:238 */         tStartIndex++;
/* 483:239 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 484:239 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 32, aRecipe.getOutputChance(tStartIndex)));
/* 485:    */         }
/* 486:239 */         tStartIndex++;
/* 487:240 */         break;
/* 488:    */       case 8: 
/* 489:242 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 490:242 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, -4, aRecipe.getOutputChance(tStartIndex)));
/* 491:    */         }
/* 492:242 */         tStartIndex++;
/* 493:243 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 494:243 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, -4, aRecipe.getOutputChance(tStartIndex)));
/* 495:    */         }
/* 496:243 */         tStartIndex++;
/* 497:244 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 498:244 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, -4, aRecipe.getOutputChance(tStartIndex)));
/* 499:    */         }
/* 500:244 */         tStartIndex++;
/* 501:245 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 502:245 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 503:    */         }
/* 504:245 */         tStartIndex++;
/* 505:246 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 506:246 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
/* 507:    */         }
/* 508:246 */         tStartIndex++;
/* 509:247 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 510:247 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 14, aRecipe.getOutputChance(tStartIndex)));
/* 511:    */         }
/* 512:247 */         tStartIndex++;
/* 513:248 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 514:248 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 32, aRecipe.getOutputChance(tStartIndex)));
/* 515:    */         }
/* 516:248 */         tStartIndex++;
/* 517:249 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 518:249 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 32, aRecipe.getOutputChance(tStartIndex)));
/* 519:    */         }
/* 520:249 */         tStartIndex++;
/* 521:250 */         break;
/* 522:    */       default: 
/* 523:252 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 524:252 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, -4, aRecipe.getOutputChance(tStartIndex)));
/* 525:    */         }
/* 526:252 */         tStartIndex++;
/* 527:253 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 528:253 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, -4, aRecipe.getOutputChance(tStartIndex)));
/* 529:    */         }
/* 530:253 */         tStartIndex++;
/* 531:254 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 532:254 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, -4, aRecipe.getOutputChance(tStartIndex)));
/* 533:    */         }
/* 534:254 */         tStartIndex++;
/* 535:255 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 536:255 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 14, aRecipe.getOutputChance(tStartIndex)));
/* 537:    */         }
/* 538:255 */         tStartIndex++;
/* 539:256 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 540:256 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 14, aRecipe.getOutputChance(tStartIndex)));
/* 541:    */         }
/* 542:256 */         tStartIndex++;
/* 543:257 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 544:257 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 14, aRecipe.getOutputChance(tStartIndex)));
/* 545:    */         }
/* 546:257 */         tStartIndex++;
/* 547:258 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 548:258 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 102, 32, aRecipe.getOutputChance(tStartIndex)));
/* 549:    */         }
/* 550:258 */         tStartIndex++;
/* 551:259 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 552:259 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 120, 32, aRecipe.getOutputChance(tStartIndex)));
/* 553:    */         }
/* 554:259 */         tStartIndex++;
/* 555:260 */         if (aRecipe.getOutput(tStartIndex) != null) {
/* 556:260 */           this.mOutputs.add(new FixedPositionedStack( aRecipe.getOutput(tStartIndex), 138, 32, aRecipe.getOutputChance(tStartIndex)));
/* 557:    */         }
/* 558:260 */         tStartIndex++;
/* 559:    */       }
/* 560:264 */       if ((aRecipe.mFluidInputs.length > 0) && (aRecipe.mFluidInputs[0] != null) && (aRecipe.mFluidInputs[0].getFluid() != null)) {
/* 561:264 */         this.mInputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidInputs[0], true), 48, 52));
					if((aRecipe.mFluidInputs.length > 1) && (aRecipe.mFluidInputs[1] != null) && (aRecipe.mFluidInputs[1].getFluid() != null)){
						this.mInputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidInputs[1], true), 30, 52));
					}
/* 562:    */       }
					if(aRecipe.mFluidOutputs.length>1){
						if(aRecipe.mFluidOutputs[0]!=null && (aRecipe.mFluidOutputs[0].getFluid() != null)){
							 this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[0], true), 120, 5));
						}
						if(aRecipe.mFluidOutputs[1]!=null && (aRecipe.mFluidOutputs[1].getFluid() != null)){
							 this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[1], true), 138, 5));
						}
						if(aRecipe.mFluidOutputs.length>2&&aRecipe.mFluidOutputs[2]!=null && (aRecipe.mFluidOutputs[2].getFluid() != null)){
							 this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[2], true), 102, 23));
						}
						if(aRecipe.mFluidOutputs.length>3&&aRecipe.mFluidOutputs[3]!=null && (aRecipe.mFluidOutputs[3].getFluid() != null)){
							 this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[3], true), 120, 23));
						}
						if(aRecipe.mFluidOutputs.length>4&&aRecipe.mFluidOutputs[4]!=null && (aRecipe.mFluidOutputs[4].getFluid() != null)){
							 this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[4], true), 138, 23));
						}
					}else if ((aRecipe.mFluidOutputs.length > 0) && (aRecipe.mFluidOutputs[0] != null) && (aRecipe.mFluidOutputs[0].getFluid() != null)) {
/* 564:265 */         this.mOutputs.add(new FixedPositionedStack( GT_Utility.getFluidDisplayStack(aRecipe.mFluidOutputs[0], true), 102, 52));
/* 565:    */       }
/* 566:    */     }
/* 567:    */   }
/* 568:    */   
/* 569:    */   static
/* 570:    */   {
/* 571:270 */     GuiContainerManager.addInputHandler(new GT_RectHandler());
/* 572:271 */     GuiContainerManager.addTooltipHandler(new GT_RectHandler());
/* 573:    */   }
/* 574:    */   
/* 575:    */   public static class GT_RectHandler
/* 576:    */     implements IContainerInputHandler, IContainerTooltipHandler
/* 577:    */   {
/* 578:    */     public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button)
/* 579:    */     {
/* 580:277 */       if (canHandle(gui))
/* 581:    */       {
/* 582:278 */         if (button == 0) {
/* 583:278 */           return transferRect(gui, false);
/* 584:    */         }
/* 585:279 */         if (button == 1) {
/* 586:279 */           return transferRect(gui, true);
/* 587:    */         }
/* 588:    */       }
/* 589:281 */       return false;
/* 590:    */     }
/* 591:    */     
/* 592:    */     public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyCode)
/* 593:    */     {
/* 594:286 */       return false;
/* 595:    */     }
/* 596:    */     
/* 597:    */     public boolean canHandle(GuiContainer gui)
/* 598:    */     {
/* 599:290 */       return ((gui instanceof GT_GUIContainer_BasicMachine)) && (GT_Utility.isStringValid(((GT_GUIContainer_BasicMachine)gui).mNEI));
/* 600:    */     }
/* 601:    */     
/* 602:    */     public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip)
/* 603:    */     {
/* 604:295 */       if ((canHandle(gui)) && (currenttip.isEmpty()) && (new Rectangle(65, 13, 36, 18).contains(new Point(GuiDraw.getMousePosition().x - ((GT_GUIContainer_BasicMachine)gui).getLeft() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[0], GuiDraw.getMousePosition().y - ((GT_GUIContainer_BasicMachine)gui).getTop() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[1])))) {
/* 605:295 */         currenttip.add("Recipes");
/* 606:    */       }
/* 607:296 */       return currenttip;
/* 608:    */     }
/* 609:    */     
/* 610:    */     private boolean transferRect(GuiContainer gui, boolean usage)
/* 611:    */     {
/* 612:300 */       return (canHandle(gui)) && (new Rectangle(65, 13, 36, 18).contains(new Point(GuiDraw.getMousePosition().x - ((GT_GUIContainer_BasicMachine)gui).getLeft() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[0], GuiDraw.getMousePosition().y - ((GT_GUIContainer_BasicMachine)gui).getTop() - codechicken.nei.recipe.RecipeInfo.getGuiOffset(gui)[1]))) && (usage ? GuiUsageRecipe.openRecipeGui(((GT_GUIContainer_BasicMachine)gui).mNEI, new Object[0]) : GuiCraftingRecipe.openRecipeGui(((GT_GUIContainer_BasicMachine)gui).mNEI, new Object[0]));
/* 613:    */     }
/* 614:    */     
/* 615:    */     public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip)
/* 616:    */     {
/* 617:305 */       return currenttip;
/* 618:    */     }
/* 619:    */     
/* 620:    */     public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip)
/* 621:    */     {
/* 622:310 */       return currenttip;
/* 623:    */     }
/* 624:    */     
/* 625:    */     public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode)
/* 626:    */     {
/* 627:315 */       return false;
/* 628:    */     }
/* 629:    */     
/* 630:    */     public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {}
/* 631:    */     
/* 632:    */     public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {}
/* 633:    */     
/* 634:    */     public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {}
/* 635:    */     
/* 636:    */     public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled)
/* 637:    */     {
/* 638:335 */       return false;
/* 639:    */     }
/* 640:    */     
/* 641:    */     public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {}
/* 642:    */     
/* 643:    */     public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {}
/* 644:    */   }
/* 645:    */   
/* 646:    */   public void loadCraftingRecipes(String outputId, Object... results)
/* 647:    */   {
/* 648:351 */     if (outputId.equals(getOverlayIdentifier())) {
/* 649:352 */       for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
/* 650:352 */         if (!tRecipe.mHidden) {
/* 651:352 */           this.arecipes.add(new CachedDefaultRecipe(tRecipe));
/* 652:    */         }
/* 653:    */       }
/* 654:    */     } else {
/* 655:354 */       super.loadCraftingRecipes(outputId, results);
/* 656:    */     }
/* 657:    */   }
/* 658:    */   
/* 659:    */   public void loadCraftingRecipes(ItemStack aResult)
/* 660:    */   {
/* 661:360 */     ItemData tPrefixMaterial = GT_OreDictUnificator.getAssociation(aResult);
/* 662:    */     
/* 663:362 */     ArrayList<ItemStack> tResults = new ArrayList();
/* 664:363 */     tResults.add(aResult);
/* 665:364 */     tResults.add(GT_OreDictUnificator.get(true, aResult));
/* 666:366 */     if ((tPrefixMaterial != null) && (!tPrefixMaterial.mBlackListed) && (!tPrefixMaterial.mPrefix.mFamiliarPrefixes.isEmpty())) {
/* 667:367 */       for (OrePrefixes tPrefix : tPrefixMaterial.mPrefix.mFamiliarPrefixes) {
/* 668:368 */         tResults.add(GT_OreDictUnificator.get(tPrefix, tPrefixMaterial.mMaterial.mMaterial, 1L));
/* 669:    */       }
/* 670:    */     }
/* 671:372 */     FluidStack tFluid = GT_Utility.getFluidForFilledItem(aResult, true);
/* 672:373 */     if (tFluid != null)
/* 673:    */     {
/* 674:374 */       tResults.add(GT_Utility.getFluidDisplayStack(tFluid, false));
/* 675:375 */       for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 676:376 */         if (tData.fluid.isFluidEqual(tFluid)) {
/* 677:376 */           tResults.add(GT_Utility.copy(new Object[] { tData.filledContainer }));
/* 678:    */         }
/* 679:    */       }
/* 680:    */     }
/* 681:380 */     for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
/* 682:380 */       if (!tRecipe.mHidden)
/* 683:    */       {
/* 684:381 */        CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(tRecipe);
/* 685:382 */         for (ItemStack tStack : tResults) {
/* 686:382 */           if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack))
/* 687:    */           {
/* 688:383 */             this.arecipes.add(tNEIRecipe);
/* 689:384 */             break;
/* 690:    */           }
/* 691:    */         }
/* 692:    */       }
/* 693:    */     }
/* 694:    */     CachedDefaultRecipe tNEIRecipe;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public void loadUsageRecipes(ItemStack aInput)
/* 698:    */   {
/* 699:391 */     ItemData tPrefixMaterial = GT_OreDictUnificator.getAssociation(aInput);
/* 700:    */     
/* 701:393 */     ArrayList<ItemStack> tInputs = new ArrayList();
/* 702:394 */     tInputs.add(aInput);
/* 703:395 */     tInputs.add(GT_OreDictUnificator.get(false, aInput));
/* 704:397 */     if ((tPrefixMaterial != null) && (!tPrefixMaterial.mPrefix.mFamiliarPrefixes.isEmpty())) {
/* 705:398 */       for (OrePrefixes tPrefix : tPrefixMaterial.mPrefix.mFamiliarPrefixes) {
/* 706:399 */         tInputs.add(GT_OreDictUnificator.get(tPrefix, tPrefixMaterial.mMaterial.mMaterial, 1L));
/* 707:    */       }
/* 708:    */     }
/* 709:403 */     FluidStack tFluid = GT_Utility.getFluidForFilledItem(aInput, true);
/* 710:404 */     if (tFluid != null)
/* 711:    */     {
/* 712:405 */       tInputs.add(GT_Utility.getFluidDisplayStack(tFluid, false));
/* 713:406 */       for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
/* 714:407 */         if (tData.fluid.isFluidEqual(tFluid)) {
/* 715:407 */           tInputs.add(GT_Utility.copy(new Object[] { tData.filledContainer }));
/* 716:    */         }
/* 717:    */       }
/* 718:    */     }
/* 719:411 */     for (GT_Recipe tRecipe : this.mRecipeMap.mRecipeList) {
/* 720:411 */       if (!tRecipe.mHidden)
/* 721:    */       {
/* 722:412 */         CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe(tRecipe);
/* 723:413 */         for (ItemStack tStack : tInputs) {
/* 724:413 */           if (tNEIRecipe.contains(tNEIRecipe.mInputs, tStack))
/* 725:    */           {
/* 726:414 */             this.arecipes.add(tNEIRecipe);
/* 727:415 */             break;
/* 728:    */           }
/* 729:    */         }
/* 730:    */       }
/* 731:    */     }
/* 732:    */     CachedDefaultRecipe tNEIRecipe;
/* 733:    */   }
/* 734:    */   
/* 735:    */   public String getOverlayIdentifier()
/* 736:    */   {
/* 737:422 */     return this.mRecipeMap.mNEIName;
/* 738:    */   }
/* 739:    */   
/* 740:    */   public void drawBackground(int recipe)
/* 741:    */   {
/* 742:427 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 743:428 */     GuiDraw.changeTexture(getGuiTexture());
/* 744:429 */     GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 78);
/* 745:    */   }
/* 746:    */   
/* 747:    */   public static void drawText(int aX, int aY, String aString, int aColor)
/* 748:    */   {
/* 749:433 */     Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
/* 750:    */   }
/* 751:    */   
/* 752:    */   public int recipiesPerPage()
/* 753:    */   {
/* 754:438 */     return 1;
/* 755:    */   }
/* 756:    */   
/* 757:    */   public String getRecipeName()
/* 758:    */   {
/* 759:443 */     return GT_LanguageManager.getTranslation(this.mRecipeMap.mUnlocalizedName);
/* 760:    */   }
/* 761:    */   
/* 762:    */   public String getGuiTexture()
/* 763:    */   {
///* 764:448 */     return "gregtech:textures/gui/" + this.mRecipeMap.mUnlocalizedName + ".png";
					return this.mRecipeMap.mNEIGUIPath;
/* 765:    */   }
/* 766:    */   
/* 767:    */   public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex)
/* 768:    */   {
/* 769:453 */     TemplateRecipeHandler.CachedRecipe tObject = (TemplateRecipeHandler.CachedRecipe)this.arecipes.get(aRecipeIndex);
/* 770:454 */     if ((tObject instanceof CachedDefaultRecipe))
/* 771:    */     {
/* 772:455 */       CachedDefaultRecipe tRecipe = (CachedDefaultRecipe)tObject;
/* 773:456 */       for (PositionedStack tStack : tRecipe.mOutputs) {
/* 774:456 */         if (aStack == tStack.item)
/* 775:    */         {
/* 776:457 */           if ((!(tStack instanceof FixedPositionedStack)) || (((FixedPositionedStack)tStack).mChance <= 0) || (((FixedPositionedStack)tStack).mChance == 10000)) {
/* 777:    */             break;
/* 778:    */           }
/* 779:458 */           currenttip.add("Chance: " + ((FixedPositionedStack)tStack).mChance / 100 + "." + (((FixedPositionedStack)tStack).mChance % 100 < 10 ? "0" + ((FixedPositionedStack)tStack).mChance % 100 : Integer.valueOf(((FixedPositionedStack)tStack).mChance % 100)) + "%"); break;
/* 780:    */         }
/* 781:    */       }
/* 782:462 */       for (PositionedStack tStack : tRecipe.mInputs) {
/* 783:462 */         if (aStack == tStack.item)
/* 784:    */         {
/* 785:463 */           if ((gregtech.api.enums.ItemList.Display_Fluid.isStackEqual(tStack.item, true, true)) || 
/* 786:464 */             (tStack.item.stackSize != 0)) {
/* 787:    */             break;
/* 788:    */           }
/* 789:464 */           currenttip.add("Does not get consumed in the process"); break;
/* 790:    */         }
/* 791:    */       }
/* 792:    */     }
/* 793:469 */     return currenttip;
/* 794:    */   }
/* 795:    */   
/* 796:    */   public void drawExtras(int aRecipeIndex)
/* 797:    */   {
/* 798:474 */     int tEUt = ((CachedDefaultRecipe)this.arecipes.get(aRecipeIndex)).mRecipe.mEUt;
/* 799:475 */     int tDuration = ((CachedDefaultRecipe)this.arecipes.get(aRecipeIndex)).mRecipe.mDuration;
/* 800:476 */     if (tEUt != 0)
/* 801:    */     {
/* 802:477 */       drawText(10, 73, "Total: " + tDuration * tEUt + " EU", -16777216);
/* 803:478 */       drawText(10, 83, "Usage: " + tEUt + " EU/t", -16777216);
/* 804:479 */       if (this.mRecipeMap.mShowVoltageAmperageInNEI)
/* 805:    */       {
/* 806:480 */         drawText(10, 93, "Voltage: " + tEUt / this.mRecipeMap.mAmperage + " EU", -16777216);
/* 807:481 */         drawText(10, 103, "Amperage: " + this.mRecipeMap.mAmperage, -16777216);
/* 808:    */       }
/* 809:    */       else
/* 810:    */       {
/* 811:483 */         drawText(10, 93, "Voltage: unspecified", -16777216);
/* 812:484 */         drawText(10, 103, "Amperage: unspecified", -16777216);
/* 813:    */       }
/* 814:    */     }
/* 815:487 */     if (tDuration > 0) {
/* 816:488 */       drawText(10, 113, "Time: " + (tDuration < 20 ? "< 1" : Integer.valueOf(tDuration / 20)) + " secs", -16777216);
/* 817:    */     }
/* 818:489 */     if ((GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePre)) || (GT_Utility.isStringValid(this.mRecipeMap.mNEISpecialValuePost))) {
/* 819:490 */       drawText(10, 123, this.mRecipeMap.mNEISpecialValuePre + ((CachedDefaultRecipe)this.arecipes.get(aRecipeIndex)).mRecipe.mSpecialValue * this.mRecipeMap.mNEISpecialValueMultiplier + this.mRecipeMap.mNEISpecialValuePost, -16777216);
/* 820:    */     }
/* 821:    */   }
/* 822:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.nei.GT_NEI_DefaultHandler
 * JD-Core Version:    0.7.0.1
 */