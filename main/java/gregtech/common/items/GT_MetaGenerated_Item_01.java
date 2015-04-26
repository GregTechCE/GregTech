/*   1:    */ package gregtech.common.items;
/*   2:    */ 
/*   3:    */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*   4:    */ import gregtech.api.enums.ConfigCategories.Recipes;
/*   5:    */ import gregtech.api.enums.Dyes;
/*   6:    */ import gregtech.api.enums.GT_Values;
/*   7:    */ import gregtech.api.enums.ItemList;
/*   8:    */ import gregtech.api.enums.Materials;
/*   9:    */ import gregtech.api.enums.OreDictNames;
/*  10:    */ import gregtech.api.enums.OrePrefixes;
/*  11:    */ import gregtech.api.enums.SubTag;
/*  12:    */ import gregtech.api.enums.TC_Aspects;
/*  13:    */ import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.enums.Textures;
/*  14:    */ import gregtech.api.enums.Textures.BlockIcons;
/*  15:    */ import gregtech.api.interfaces.IItemBehaviour;
/*  16:    */ import gregtech.api.interfaces.ITexture;
/*  17:    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  18:    */ import gregtech.api.items.GT_MetaBase_Item;
/*  19:    */ import gregtech.api.items.GT_MetaGenerated_Item_X32;
import gregtech.api.objects.GT_ItemStack;
/*  20:    */ import gregtech.api.objects.GT_MultiTexture;
/*  21:    */ import gregtech.api.objects.GT_RenderedTexture;
/*  22:    */ import gregtech.api.objects.ItemData;
/*  23:    */ import gregtech.api.objects.MaterialStack;
/*  24:    */ import gregtech.api.util.GT_Config;
/*  25:    */ import gregtech.api.util.GT_FoodStat;
/*  26:    */ import gregtech.api.util.GT_LanguageManager;
/*  27:    */ import gregtech.api.util.GT_ModHandler;
/*  28:    */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  29:    */ import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
/*  30:    */ import gregtech.api.util.GT_Utility.ItemNBT;
/*  31:    */ import gregtech.common.covers.GT_Cover_Arm;
/*  32:    */ import gregtech.common.covers.GT_Cover_ControlsWork;
/*  33:    */ import gregtech.common.covers.GT_Cover_Conveyor;
/*  34:    */ import gregtech.common.covers.GT_Cover_Crafting;
/*  35:    */ import gregtech.common.covers.GT_Cover_DoesWork;
/*  36:    */ import gregtech.common.covers.GT_Cover_Drain;
/*  37:    */ import gregtech.common.covers.GT_Cover_EUMeter;
/*  38:    */ import gregtech.common.covers.GT_Cover_ItemMeter;
/*  39:    */ import gregtech.common.covers.GT_Cover_LiquidMeter;
/*  40:    */ import gregtech.common.covers.GT_Cover_Pump;
/*  41:    */ import gregtech.common.covers.GT_Cover_Screen;
/*  42:    */ import gregtech.common.covers.GT_Cover_Shutter;
/*  43:    */ import gregtech.common.covers.GT_Cover_SolarPanel;
/*  44:    */ import gregtech.common.items.behaviors.Behaviour_Arrow_Potion;
/*  45:    */ import gregtech.common.items.behaviors.Behaviour_DataOrb;
/*  46:    */ import gregtech.common.items.behaviors.Behaviour_DataStick;
/*  47:    */ import gregtech.common.items.behaviors.Behaviour_Lighter;
/*  48:    */ import gregtech.common.items.behaviors.Behaviour_PrintedPages;
/*  49:    */ import gregtech.common.items.behaviors.Behaviour_Scanner;
/*  50:    */ import gregtech.common.items.behaviors.Behaviour_SensorKit;
/*  51:    */ import gregtech.common.items.behaviors.Behaviour_Sonictron;
/*  52:    */ import gregtech.common.items.behaviors.Behaviour_Spray_Color;
/*  53:    */ import gregtech.common.items.behaviors.Behaviour_WrittenBook;

/*  54:    */ import java.util.List;

/*  55:    */ import net.minecraft.block.Block;
/*  56:    */ import net.minecraft.enchantment.Enchantment;
/*  57:    */ import net.minecraft.entity.item.EntityItem;
/*  58:    */ import net.minecraft.init.Blocks;
/*  59:    */ import net.minecraft.init.Items;
/*  60:    */ import net.minecraft.item.EnumAction;
/*  61:    */ import net.minecraft.item.ItemStack;
/*  62:    */ import net.minecraft.potion.Potion;
/*  63:    */ import net.minecraft.util.MathHelper;
/*  64:    */ import net.minecraft.world.World;
/*  65:    */ 
/*  66:    */ public class GT_MetaGenerated_Item_01
/*  67:    */   extends GT_MetaGenerated_Item_X32
/*  68:    */ {
/*  69:    */   public static GT_MetaGenerated_Item_01 INSTANCE;
/*  70: 36 */   private final String mToolTipPurify = GT_LanguageManager.addStringLocalization("metaitem.01.tooltip.purify", "Throw into Cauldron to get clean Dust");
/*  71:    */   
/*  72:    */   public GT_MetaGenerated_Item_01()
/*  73:    */   {
/*  74: 39 */     super("metaitem.01", new OrePrefixes[] { OrePrefixes.dustTiny, OrePrefixes.dustSmall, OrePrefixes.dust, OrePrefixes.dustImpure, OrePrefixes.dustPure, OrePrefixes.crushed, OrePrefixes.crushedPurified, OrePrefixes.crushedCentrifuged, OrePrefixes.gem, OrePrefixes.nugget, null, OrePrefixes.ingot, OrePrefixes.ingotHot, OrePrefixes.ingotDouble, OrePrefixes.ingotTriple, OrePrefixes.ingotQuadruple, OrePrefixes.ingotQuintuple, OrePrefixes.plate, OrePrefixes.plateDouble, OrePrefixes.plateTriple, OrePrefixes.plateQuadruple, OrePrefixes.plateQuintuple, OrePrefixes.plateDense, OrePrefixes.stick, OrePrefixes.lens, OrePrefixes.round, OrePrefixes.bolt, OrePrefixes.screw, OrePrefixes.ring, OrePrefixes.foil, OrePrefixes.cell, OrePrefixes.cellPlasma });
/*  75: 40 */     INSTANCE = this;
/*  76:    */     
/*  77: 42 */     int tLastID = 0;
/*  78:    */     
/*  79: 44 */     setBurnValue(17000 + Materials.Wood.mMetaItemSubID, 1600);
/*  80: 45 */     GT_OreDictUnificator.addToBlacklist(new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID));
/*  81: 46 */     GT_ModHandler.addCompressionRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 8L), new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID));
/*  82: 47 */     GregTech_API.registerCover(new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID), new GT_RenderedTexture(Textures.BlockIcons.COVER_WOOD_PLATE), null);
/*  83:    */     
/*  84: 49 */     ItemStack tStack = new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID);
/*  85: 50 */     tStack.setStackDisplayName("The holy Planks of Sengir");
/*  86: 51 */     GT_Utility.ItemNBT.addEnchantment(tStack, Enchantment.smite, 10);
/*  87: 52 */     GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "XXX", "XDX", "XXX", Character.valueOf('X'), OrePrefixes.gem.get(Materials.NetherStar), Character.valueOf('D'), new ItemStack(Blocks.dragon_egg, 1, 32767) });
/*  88:    */     
/*  89: 54 */     ItemList.Credit_Greg_Copper.set(addItem(tLastID = 0, "Copper GT Credit", "0.125 Credits", new Object[0]));
/*  90: 55 */     ItemList.Credit_Greg_Cupronickel.set(addItem(tLastID = 1, "Cupronickel GT Credit", "1 Credit", new Object[] { new ItemData(Materials.Cupronickel, 907200L, new MaterialStack[0]) }));
/*  91: 56 */     ItemList.Credit_Greg_Silver.set(addItem(tLastID = 2, "Silver GT Credit", "8 Credits", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L) }));
/*  92: 57 */     ItemList.Credit_Greg_Gold.set(addItem(tLastID = 3, "Gold GT Credit", "64 Credits", new Object[0]));
/*  93: 58 */     ItemList.Credit_Greg_Platinum.set(addItem(tLastID = 4, "Platinum GT Credit", "512 Credits", new Object[0]));
/*  94: 59 */     ItemList.Credit_Greg_Osmium.set(addItem(tLastID = 5, "Osmium GT Credit", "4096 Credits", new Object[0]));
/*  95: 60 */     ItemList.Credit_Greg_Naquadah.set(addItem(tLastID = 6, "Naquadah GT Credit", "32768 Credits", new Object[0]));
/*  96: 61 */     ItemList.Credit_Greg_Neutronium.set(addItem(tLastID = 7, "Neutronium GT Credit", "262144 Credits", new Object[0]));
/*  97: 62 */     ItemList.Coin_Gold_Ancient.set(addItem(tLastID = 8, "Ancient Gold Coin", "Found in ancient Ruins", new Object[] { new ItemData(Materials.Gold, 907200L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 8L) }));
/*  98: 63 */     ItemList.Coin_Doge.set(addItem(tLastID = 9, "Doge Coin", "wow much coin how money so crypto plz mine v rich very currency wow", new Object[] { new ItemData(Materials.Brass, 907200L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L) }));
/*  99: 64 */     ItemList.Coin_Chocolate.set(addItem(tLastID = 10, "Chocolate Coin", "Wrapped in Gold", new Object[] { new ItemData(Materials.Gold, OrePrefixes.foil.mMaterialAmount, new MaterialStack[0]), new GT_FoodStat(1, 0.1F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1L), true, false, false, new int[] { Potion.moveSpeed.id, 200, 1, 100 }) }));
/* 100: 65 */     ItemList.Credit_Copper.set(addItem(tLastID = 11, "Industrial Copper Credit", "0.125 Credits", new Object[0]));
/* 101:    */     
/* 102: 67 */     ItemList.Credit_Silver.set(addItem(tLastID = 13, "Industrial Silver Credit", "8 Credits", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L) }));
/* 103: 68 */     ItemList.Credit_Gold.set(addItem(tLastID = 14, "Industrial Gold Credit", "64 Credits", new Object[0]));
/* 104: 69 */     ItemList.Credit_Platinum.set(addItem(tLastID = 15, "Industrial Platinum Credit", "512 Credits", new Object[0]));
/* 105: 70 */     ItemList.Credit_Osmium.set(addItem(tLastID = 16, "Industrial Osmium Credit", "4096 Credits", new Object[0]));
/* 106:    */     
/* 107: 72 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Coin_Chocolate.get(1L, new Object[0]), new Object[] { OrePrefixes.dust.get(Materials.Cocoa), OrePrefixes.dust.get(Materials.Milk), OrePrefixes.dust.get(Materials.Sugar), OrePrefixes.foil.get(Materials.Gold) });
/* 108:    */     
/* 109: 74 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Copper.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Iron });
/* 110: 75 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Silver });
/* 111: 76 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Gold });
/* 112: 77 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Platinum });
/* 113: 78 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Osmium });
/* 114:    */     
/* 115: 80 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper });
/* 116: 81 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron });
/* 117: 82 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver });
/* 118: 83 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold });
/* 119: 84 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Osmium.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum });
/* 120:    */     
/* 121: 86 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Copper.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Cupronickel });
/* 122: 87 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Silver });
/* 123: 88 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Gold });
/* 124: 89 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Platinum });
/* 125: 90 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Osmium });
/* 126: 91 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Naquadah });
/* 127: 92 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(8L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Neutronium });
/* 128:    */     
/* 129: 94 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper });
/* 130: 95 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel });
/* 131: 96 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver });
/* 132: 97 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold });
/* 133: 98 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum });
/* 134: 99 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium });
/* 135:100 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Neutronium.get(1L, new Object[0]), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah });
/* 136:    */     
/* 137:102 */     ItemList.Component_Minecart_Wheels_Iron.set(addItem(tLastID = 100, "Iron Minecart Wheels", "To get things rolling", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L) }));
/* 138:103 */     ItemList.Component_Minecart_Wheels_Steel.set(addItem(tLastID = 101, "Steel Minecart Wheels", "To get things rolling", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L) }));
/* 139:    */     
/* 140:105 */     GT_ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Iron.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " h ", "RSR", " w ", Character.valueOf('R'), OrePrefixes.ring.get(Materials.AnyIron), Character.valueOf('S'), OrePrefixes.stick.get(Materials.AnyIron) });
/* 141:106 */     GT_ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Steel.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " h ", "RSR", " w ", Character.valueOf('R'), OrePrefixes.ring.get(Materials.Steel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Steel) });
/* 142:    */     
/* 143:108 */     ItemList.Arrow_Head_Glass_Emtpy.set(addItem(tLastID = 200, "Empty Glass Arrow Head", "Fill with Potions before use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 144:109 */     ItemList.Arrow_Head_Glass_Poison.set(addItem(tLastID = 201, "Poison Glass Arrow Head", "Glass Arrow filled with Poison", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 145:110 */     ItemList.Arrow_Head_Glass_Poison_Long.set(addItem(tLastID = 202, "Poison Glass Arrow Head", "Glass Arrow filled with stretched Poison", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 146:111 */     ItemList.Arrow_Head_Glass_Poison_Strong.set(addItem(tLastID = 203, "Poison Glass Arrow Head", "Glass Arrow filled with strong Poison", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 147:112 */     ItemList.Arrow_Head_Glass_Slowness.set(addItem(tLastID = 204, "Slowness Glass Arrow Head", "Glass Arrow filled with Laming Brew", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 148:113 */     ItemList.Arrow_Head_Glass_Slowness_Long.set(addItem(tLastID = 205, "Slowness Glass Arrow Head", "Glass Arrow filled with stretched Laming Brew", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 149:114 */     ItemList.Arrow_Head_Glass_Weakness.set(addItem(tLastID = 206, "Weakness Glass Arrow Head", "Glass Arrow filled with Weakening Brew", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 150:115 */     ItemList.Arrow_Head_Glass_Weakness_Long.set(addItem(tLastID = 207, "Weakness Glass Arrow Head", "Glass Arrow filled with stretched Weakening Brew", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 151:116 */     ItemList.Arrow_Head_Glass_Holy_Water.set(addItem(tLastID = 208, "Holy Water Glass Arrow Head", "Glass Arrow filled with Holy Water", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L) }));
/* 152:    */     
/* 153:118 */     ItemList.Arrow_Wooden_Glass_Emtpy.set(addItem(tLastID = 225, "Regular Glass Vial Arrow", "Empty Glass Arrow", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 154:119 */     ItemList.Arrow_Wooden_Glass_Poison.set(addItem(tLastID = 226, "Regular Poison Arrow", "Glass Arrow filled with Poison", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.poison.id, 450, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 155:120 */     ItemList.Arrow_Wooden_Glass_Poison_Long.set(addItem(tLastID = 227, "Regular Poison Arrow", "Glass Arrow filled with stretched Poison", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.poison.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 156:121 */     ItemList.Arrow_Wooden_Glass_Poison_Strong.set(addItem(tLastID = 228, "Regular Poison Arrow", "Glass Arrow filled with strong Poison", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.poison.id, 450, 1, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 157:122 */     ItemList.Arrow_Wooden_Glass_Slowness.set(addItem(tLastID = 229, "Regular Slowness Arrow", "Glass Arrow filled with Laming Brew", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.moveSlowdown.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 158:123 */     ItemList.Arrow_Wooden_Glass_Slowness_Long.set(addItem(tLastID = 230, "Regular Slowness Arrow", "Glass Arrow filled with stretched Laming Brew", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.moveSlowdown.id, 1800, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 159:124 */     ItemList.Arrow_Wooden_Glass_Weakness.set(addItem(tLastID = 231, "Regular Weakness Arrow", "Glass Arrow filled with Weakening Brew", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.weakness.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 160:125 */     ItemList.Arrow_Wooden_Glass_Weakness_Long.set(addItem(tLastID = 232, "Regular Weakness Arrow", "Glass Arrow filled with stretched Weakening Brew", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, new int[] { Potion.weakness.id, 1800, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 161:126 */     ItemList.Arrow_Wooden_Glass_Holy_Water.set(addItem(tLastID = 233, "Regular Holy Water Arrow", "Glass Arrow filled with Holy Water", new Object[] { new Behaviour_Arrow_Potion(1.0F, 6.0F, Enchantment.smite, 10, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L) }));
/* 162:    */     
/* 163:128 */     ItemList.Arrow_Plastic_Glass_Emtpy.set(addItem(tLastID = 250, "Light Glass Vial Arrow", "Empty Glass Arrow", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 164:129 */     ItemList.Arrow_Plastic_Glass_Poison.set(addItem(tLastID = 251, "Light Poison Arrow", "Glass Arrow filled with Poison", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.poison.id, 450, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 165:130 */     ItemList.Arrow_Plastic_Glass_Poison_Long.set(addItem(tLastID = 252, "Light Poison Arrow", "Glass Arrow filled with stretched Poison", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.poison.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 166:131 */     ItemList.Arrow_Plastic_Glass_Poison_Strong.set(addItem(tLastID = 253, "Light Poison Arrow", "Glass Arrow filled with strong Poison", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.poison.id, 450, 1, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 167:132 */     ItemList.Arrow_Plastic_Glass_Slowness.set(addItem(tLastID = 254, "Light Slowness Arrow", "Glass Arrow filled with Laming Brew", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.moveSlowdown.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 168:133 */     ItemList.Arrow_Plastic_Glass_Slowness_Long.set(addItem(tLastID = 255, "Light Slowness Arrow", "Glass Arrow filled with stretched Laming Brew", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.moveSlowdown.id, 1800, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 169:134 */     ItemList.Arrow_Plastic_Glass_Weakness.set(addItem(tLastID = 256, "Light Weakness Arrow", "Glass Arrow filled with Weakening Brew", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.weakness.id, 900, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 170:135 */     ItemList.Arrow_Plastic_Glass_Weakness_Long.set(addItem(tLastID = 257, "Light Weakness Arrow", "Glass Arrow filled with stretched Weakening Brew", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, new int[] { Potion.weakness.id, 1800, 0, 100 }), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L) }));
/* 171:136 */     ItemList.Arrow_Plastic_Glass_Holy_Water.set(addItem(tLastID = 258, "Light Holy Water Arrow", "Glass Arrow filled with Holy Water", new Object[] { new Behaviour_Arrow_Potion(1.5F, 6.0F, Enchantment.smite, 10, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L) }));
/* 172:    */     
/* 173:138 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Emtpy, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 174:139 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 175:140 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 176:141 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison_Strong.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison_Strong, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 177:142 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Slowness.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Slowness, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 178:143 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Slowness_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Slowness_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 179:144 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Weakness.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Weakness, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 180:145 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Weakness_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Weakness_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 181:146 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Holy_Water.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Holy_Water, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Wood) });
/* 182:    */     
/* 183:148 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Emtpy, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 184:149 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 185:150 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 186:151 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison_Strong.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Poison_Strong, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 187:152 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Slowness.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Slowness, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 188:153 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Slowness_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Slowness_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 189:154 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Weakness.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Weakness, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 190:155 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Weakness_Long.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Weakness_Long, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 191:156 */     GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Holy_Water.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  A", " S ", "F  ", Character.valueOf('A'), ItemList.Arrow_Head_Glass_Holy_Water, Character.valueOf('F'), OreDictNames.craftingFeather, Character.valueOf('S'), OrePrefixes.stick.get(Materials.Plastic) });
/* 192:    */     
/* 193:158 */     ItemList.Shape_Empty.set(addItem(tLastID = 300, "Empty Shape Plate", "Raw Plate to make Molds and Extruder Shapes", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L) }));
/* 194:    */     
/* 195:160 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Empty.get(1L, new Object[0]), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "hf", "PP", "PP", Character.valueOf('P'), OrePrefixes.plate.get(Materials.Steel) });
/* 196:    */     
/* 197:162 */     ItemList.Shape_Mold_Plate.set(addItem(tLastID = 301, "Mold (Plate)", "Mold for making Plates", new Object[0]));
/* 198:163 */     ItemList.Shape_Mold_Casing.set(addItem(tLastID = 302, "Mold (Casing)", "Mold for making Item Casings", new Object[0]));
/* 199:164 */     ItemList.Shape_Mold_Gear.set(addItem(tLastID = 303, "Mold (Gear)", "Mold for making Gears", new Object[0]));
/* 200:165 */     ItemList.Shape_Mold_Credit.set(addItem(tLastID = 304, "Mold (Coinage)", "Secure Mold for making Coins (Don't lose it!)", new Object[0]));
/* 201:166 */     ItemList.Shape_Mold_Bottle.set(addItem(tLastID = 305, "Mold (Bottle)", "Mold for making Bottles", new Object[0]));
/* 202:167 */     ItemList.Shape_Mold_Ingot.set(addItem(tLastID = 306, "Mold (Ingot)", "Mold for making Ingots", new Object[0]));
/* 203:168 */     ItemList.Shape_Mold_Ball.set(addItem(tLastID = 307, "Mold (Ball)", "Mold for making Balls", new Object[0]));
/* 204:169 */     ItemList.Shape_Mold_Block.set(addItem(tLastID = 308, "Mold (Block)", "Mold for making Blocks", new Object[0]));
/* 205:170 */     ItemList.Shape_Mold_Nugget.set(addItem(tLastID = 309, "Mold (Nuggets)", "Mold for making Nuggets", new Object[0]));
/* 206:171 */     ItemList.Shape_Mold_Bun.set(addItem(tLastID = 310, "Mold (Buns)", "Mold for shaping Buns", new Object[0]));
/* 207:172 */     ItemList.Shape_Mold_Bread.set(addItem(tLastID = 311, "Mold (Bread)", "Mold for shaping Breads", new Object[0]));
/* 208:173 */     ItemList.Shape_Mold_Baguette.set(addItem(tLastID = 312, "Mold (Baguette)", "Mold for shaping Baguettes", new Object[0]));
/* 209:174 */     ItemList.Shape_Mold_Cylinder.set(addItem(tLastID = 313, "Mold (Cylinder)", "Mold for shaping Cylinders", new Object[0]));
/* 210:175 */     ItemList.Shape_Mold_Anvil.set(addItem(tLastID = 314, "Mold (Anvil)", "Mold for shaping Anvils", new Object[0]));
/* 211:176 */     ItemList.Shape_Mold_Name.set(addItem(tLastID = 315, "Mold (Name)", "Mold for naming Items (rename Mold with Anvil)", new Object[0]));
/* 212:177 */     ItemList.Shape_Mold_Arrow.set(addItem(tLastID = 316, "Mold (Arrow Head)", "Mold for making Arrow Heads", new Object[0]));
/* 213:178 */     ItemList.Shape_Mold_Gear_Small.set(addItem(tLastID = 317, "Mold (Small Gear)", "Mold for making small Gears", new Object[0]));
/* 214:    */     
/* 215:180 */     GT_ModHandler.removeRecipe(new ItemStack[] { new ItemStack(Blocks.glass), null, new ItemStack(Blocks.glass), null, new ItemStack(Blocks.glass) });
/* 216:    */     
/* 217:182 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Credit.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "h  ", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 218:183 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Plate.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " h ", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 219:184 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Casing.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  h", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 220:185 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " Ph", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 221:186 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bottle.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", "  h", Character.valueOf('P'), ItemList.Shape_Empty });
/* 222:187 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ingot.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", " h ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 223:188 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ball.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", "h  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 224:189 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Block.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "hP ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 225:    */     
/* 226:191 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Nugget.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P h", "   ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 227:192 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bun.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "  h", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 228:193 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bread.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "   ", "  h", Character.valueOf('P'), ItemList.Shape_Empty });
/* 229:194 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Baguette.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "   ", " h ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 230:195 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Cylinder.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", "  h", Character.valueOf('P'), ItemList.Shape_Empty });
/* 231:196 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Anvil.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", " h ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 232:197 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Name.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", "h  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 233:198 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Arrow.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "h  ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 234:199 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear_Small.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "   ", "h P", Character.valueOf('P'), ItemList.Shape_Empty });
/* 235:    */     
/* 236:201 */     ItemList.Shape_Extruder_Plate.set(addItem(tLastID = 350, "Extruder Shape (Plate)", "Extruder Shape for making Plates", new Object[0]));
/* 237:202 */     ItemList.Shape_Extruder_Rod.set(addItem(tLastID = 351, "Extruder Shape (Rod)", "Extruder Shape for making Rods", new Object[0]));
/* 238:203 */     ItemList.Shape_Extruder_Bolt.set(addItem(tLastID = 352, "Extruder Shape (Bolt)", "Extruder Shape for making Bolts", new Object[0]));
/* 239:204 */     ItemList.Shape_Extruder_Ring.set(addItem(tLastID = 353, "Extruder Shape (Ring)", "Extruder Shape for making Rings", new Object[0]));
/* 240:205 */     ItemList.Shape_Extruder_Cell.set(addItem(tLastID = 354, "Extruder Shape (Cell)", "Extruder Shape for making Cells", new Object[0]));
/* 241:206 */     ItemList.Shape_Extruder_Ingot.set(addItem(tLastID = 355, "Extruder Shape (Ingot)", "Extruder Shape for, wait, can't we just use a Furnace?", new Object[0]));
/* 242:207 */     ItemList.Shape_Extruder_Wire.set(addItem(tLastID = 356, "Extruder Shape (Wire)", "Extruder Shape for making Wires", new Object[0]));
/* 243:208 */     ItemList.Shape_Extruder_Casing.set(addItem(tLastID = 357, "Extruder Shape (Casing)", "Extruder Shape for making Item Casings", new Object[0]));
/* 244:209 */     ItemList.Shape_Extruder_Pipe_Tiny.set(addItem(tLastID = 358, "Extruder Shape (Tiny Pipe)", "Extruder Shape for making tiny Pipes", new Object[0]));
/* 245:210 */     ItemList.Shape_Extruder_Pipe_Small.set(addItem(tLastID = 359, "Extruder Shape (Small Pipe)", "Extruder Shape for making small Pipes", new Object[0]));
/* 246:211 */     ItemList.Shape_Extruder_Pipe_Medium.set(addItem(tLastID = 360, "Extruder Shape (Normal Pipe)", "Extruder Shape for making Pipes", new Object[0]));
/* 247:212 */     ItemList.Shape_Extruder_Pipe_Large.set(addItem(tLastID = 361, "Extruder Shape (Large Pipe)", "Extruder Shape for making large Pipes", new Object[0]));
/* 248:213 */     ItemList.Shape_Extruder_Pipe_Huge.set(addItem(tLastID = 362, "Extruder Shape (Huge Pipe)", "Extruder Shape for making full Block Pipes", new Object[0]));
/* 249:214 */     ItemList.Shape_Extruder_Block.set(addItem(tLastID = 363, "Extruder Shape (Block)", "Extruder Shape for making Blocks", new Object[0]));
/* 250:215 */     ItemList.Shape_Extruder_Sword.set(addItem(tLastID = 364, "Extruder Shape (Sword Blade)", "Extruder Shape for making Swords", new Object[0]));
/* 251:216 */     ItemList.Shape_Extruder_Pickaxe.set(addItem(tLastID = 365, "Extruder Shape (Pickaxe Head)", "Extruder Shape for making Pickaxes", new Object[0]));
/* 252:217 */     ItemList.Shape_Extruder_Shovel.set(addItem(tLastID = 366, "Extruder Shape (Shovel Head)", "Extruder Shape for making Shovels", new Object[0]));
/* 253:218 */     ItemList.Shape_Extruder_Axe.set(addItem(tLastID = 367, "Extruder Shape (Axe Head)", "Extruder Shape for making Axes", new Object[0]));
/* 254:219 */     ItemList.Shape_Extruder_Hoe.set(addItem(tLastID = 368, "Extruder Shape (Hoe Head)", "Extruder Shape for making Hoes", new Object[0]));
/* 255:220 */     ItemList.Shape_Extruder_Hammer.set(addItem(tLastID = 369, "Extruder Shape (Hammer Head)", "Extruder Shape for making Hammers", new Object[0]));
/* 256:221 */     ItemList.Shape_Extruder_File.set(addItem(tLastID = 370, "Extruder Shape (File Head)", "Extruder Shape for making Files", new Object[0]));
/* 257:222 */     ItemList.Shape_Extruder_Saw.set(addItem(tLastID = 371, "Extruder Shape (Saw Blade)", "Extruder Shape for making Saws", new Object[0]));
/* 258:223 */     ItemList.Shape_Extruder_Gear.set(addItem(tLastID = 372, "Extruder Shape (Gear)", "Extruder Shape for making Gears", new Object[0]));
/* 259:224 */     ItemList.Shape_Extruder_Bottle.set(addItem(tLastID = 373, "Extruder Shape (Bottle)", "Extruder Shape for making Bottles", new Object[0]));
/* 260:    */     
/* 261:226 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bolt.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "x  ", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 262:227 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Cell.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " x ", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 263:228 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ingot.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  x", " P ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 264:229 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ring.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " Px", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 265:230 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Rod.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", "  x", Character.valueOf('P'), ItemList.Shape_Empty });
/* 266:231 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Wire.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", " x ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 267:232 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Casing.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", " P ", "x  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 268:233 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Plate.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "xP ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 269:    */     
/* 270:235 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Block.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P x", "   ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 271:236 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Small.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "  x", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 272:237 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Large.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "   ", "  x", Character.valueOf('P'), ItemList.Shape_Empty });
/* 273:238 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Medium.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P  ", "   ", " x ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 274:239 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Sword.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", "  x", Character.valueOf('P'), ItemList.Shape_Empty });
/* 275:240 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pickaxe.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", " x ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 276:241 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Shovel.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "   ", "x  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 277:242 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Axe.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  P", "x  ", "   ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 278:243 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hoe.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "   ", "x P", Character.valueOf('P'), ItemList.Shape_Empty });
/* 279:244 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hammer.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "x  ", "  P", Character.valueOf('P'), ItemList.Shape_Empty });
/* 280:245 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_File.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "x  ", "   ", "  P", Character.valueOf('P'), ItemList.Shape_Empty });
/* 281:246 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Saw.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " x ", "   ", "  P", Character.valueOf('P'), ItemList.Shape_Empty });
/* 282:247 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Gear.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "x  ", "   ", "P  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 283:248 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Tiny.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " x ", "   ", "P  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 284:249 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Huge.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "  x", "   ", "P  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 285:250 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bottle.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "   ", "  x", "P  ", Character.valueOf('P'), ItemList.Shape_Empty });
/* 286:    */     
/* 287:252 */     ItemList.Shape_Slicer_Flat.set(addItem(tLastID = 398, "Slicer Blade (Flat)", "Slicer Blade for cutting Flat", new Object[0]));
/* 288:253 */     ItemList.Shape_Slicer_Stripes.set(addItem(tLastID = 399, "Slicer Blade (Stripes)", "Slicer Blade for cutting Stripes", new Object[0]));
/* 289:    */     
/* 290:255 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Flat.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "hXS", " P ", "fXd", Character.valueOf('P'), ItemList.Shape_Extruder_Block, Character.valueOf('X'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel) });
/* 291:256 */     GT_ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Stripes.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "hXS", "XPX", "fXd", Character.valueOf('P'), ItemList.Shape_Extruder_Block, Character.valueOf('X'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel) });
/* 292:    */     
/* 293:258 */     ItemList.Fuel_Can_Plastic_Empty.set(addItem(tLastID = 400, "Empty Plastic Fuel Can", "Used to store Fuels", new Object[] { new ItemData(Materials.Plastic, OrePrefixes.plate.mMaterialAmount * 1L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L) }));
/* 294:259 */     ItemList.Fuel_Can_Plastic_Filled.set(addItem(tLastID = 401, "Plastic Fuel Can", "Burns well in Diesel Generators", new Object[] { new ItemData(Materials.Plastic, OrePrefixes.plate.mMaterialAmount * 1L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L) }));
/* 295:    */     
/* 296:261 */     GT_ModHandler.addCraftingRecipe(ItemList.Fuel_Can_Plastic_Empty.get(7L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " PP", "P P", "PPP", Character.valueOf('P'), OrePrefixes.plate.get(Materials.Plastic) });
/* 297:    */     
/* 298:263 */     ItemList.Spray_Empty.set(addItem(tLastID = 402, "Empty Spray Can", "Used for making Sprays", new Object[] { new ItemData(Materials.Tin, OrePrefixes.plate.mMaterialAmount * 2L, Materials.Redstone, OrePrefixes.dust.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L) }));
/* 299:    */     
/* 300:265 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Empty, 1L), ItemList.Spray_Empty.get(1L, new Object[0]), 800, 1);
/* 301:    */     
/* 302:267 */     ItemList.Crate_Empty.set(addItem(tLastID = 403, "Empty Crate", "To Package lots of Material", new Object[] { new ItemData(Materials.Wood, 3628800L, Materials.Iron, OrePrefixes.screw.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L) }));
/* 303:    */     
/* 304:269 */     GT_ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "SWS", "WdW", "SWS", Character.valueOf('W'), OrePrefixes.plank.get(Materials.Wood), Character.valueOf('S'), OrePrefixes.screw.get(Materials.AnyIron) });
/* 305:270 */     GT_ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "SWS", "WdW", "SWS", Character.valueOf('W'), OrePrefixes.plank.get(Materials.Wood), Character.valueOf('S'), OrePrefixes.screw.get(Materials.Steel) });
/* 306:    */     
/* 307:272 */     ItemList.ThermosCan_Empty.set(addItem(tLastID = 404, "Empty Thermos Can", "Keeping hot things hot and cold things cold", new Object[] { new ItemData(Materials.Aluminium, OrePrefixes.plate.mMaterialAmount * 1L + 2L * OrePrefixes.ring.mMaterialAmount, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L) }));
/* 308:    */     
/* 309:274 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Aluminium, 2L), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 800, 1);
/* 310:    */     
/* 311:276 */     ItemList.Large_Fluid_Cell_Steel.set(addItem(tLastID = 405, "Large Steel Fluid Cell", "", new Object[] { new ItemData(Materials.Steel, OrePrefixes.plate.mMaterialAmount * 8L + 4L * OrePrefixes.ring.mMaterialAmount, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L) }));
/* 312:277 */     setFluidContainerStats(32000 + tLastID, 16000L, 4L);
/* 313:    */     
/* 314:279 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 8L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, 4L), ItemList.Large_Fluid_Cell_Steel.get(1L, new Object[0]), 100, 64);
/* 315:    */     
/* 316:281 */     ItemList.Large_Fluid_Cell_TungstenSteel.set(addItem(tLastID = 406, "Large Tungstensteel Fluid Cell", "", new Object[] { new ItemData(Materials.TungstenSteel, OrePrefixes.plate.mMaterialAmount * 8L + 4L * OrePrefixes.ring.mMaterialAmount, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 6L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 3L) }));
/* 317:282 */     setFluidContainerStats(32000 + tLastID, 64000L, 4L);
/* 318:    */     
/* 319:284 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 8L), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.TungstenSteel, 4L), ItemList.Large_Fluid_Cell_TungstenSteel.get(1L, new Object[0]), 200, 256);
/* 320:288 */     for (byte i = 0; i < 16; i = (byte)(i + 1))
/* 321:    */     {
/* 322:289 */       ItemList.SPRAY_CAN_DYES[i].set(addItem(tLastID = 430 + 2 * i, "Spray Can (" + Dyes.get(i).mName + ")", "Full", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L) }));
/* 323:290 */       ItemList.SPRAY_CAN_DYES_USED[i].set(addItem(tLastID + 1, "Spray Can (" + Dyes.get(i).mName + ")", "Used", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 3L), SubTag.INVISIBLE }));
/* 324:291 */       IItemBehaviour<GT_MetaBase_Item> tBehaviour = new Behaviour_Spray_Color(ItemList.Spray_Empty.get(1L, new Object[0]), ItemList.SPRAY_CAN_DYES_USED[i].get(1L, new Object[0]), ItemList.SPRAY_CAN_DYES[i].get(1L, new Object[0]), 512L, i);
/* 325:292 */       addItemBehavior(32000 + tLastID, tBehaviour);
/* 326:293 */       addItemBehavior(32001 + tLastID, tBehaviour);
/* 327:    */     }
/* 328:296 */     ItemList.Tool_Matches.set(addItem(tLastID = 471, "Match", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 329:297 */     ItemList.Tool_MatchBox_Used.set(addItem(tLastID = 472, "Match Box", "This is not a Car", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE }));
/* 330:298 */     ItemList.Tool_MatchBox_Full.set(addItem(tLastID = 473, "Match Box (Full)", "This is not a Car", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 331:    */     
/* 332:300 */     IItemBehaviour<GT_MetaBase_Item> tBehaviour = new Behaviour_Lighter(null, ItemList.Tool_Matches.get(1L, new Object[0]), ItemList.Tool_Matches.get(1L, new Object[0]), 1L);
/* 333:301 */     addItemBehavior(32471, tBehaviour);
/* 334:302 */     tBehaviour = new Behaviour_Lighter(null, ItemList.Tool_MatchBox_Used.get(1L, new Object[0]), ItemList.Tool_MatchBox_Full.get(1L, new Object[0]), 16L);
/* 335:303 */     addItemBehavior(32472, tBehaviour);addItemBehavior(32473, tBehaviour);
/* 336:    */     
/* 337:305 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Wood, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Phosphor, 1L), ItemList.Tool_Matches.get(1L, new Object[0]), 16, 16);
/* 338:306 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Wood, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Phosphorus, 1L), ItemList.Tool_Matches.get(1L, new Object[0]), 16, 16);
/* 339:307 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphor, 1L), ItemList.Tool_Matches.get(4L, new Object[0]), 64, 16);
/* 340:308 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Wood, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphorus, 1L), ItemList.Tool_Matches.get(4L, new Object[0]), 64, 16);
/* 341:309 */     GT_Values.RA.addBoxingRecipe(ItemList.Tool_Matches.get(16L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Paper, 1L), ItemList.Tool_MatchBox_Full.get(1L, new Object[0]), 64, 16);
/* 342:310 */     GT_Values.RA.addUnboxingRecipe(ItemList.Tool_MatchBox_Full.get(1L, new Object[0]), ItemList.Tool_Matches.get(16L, new Object[0]), null, 32, 16);
/* 343:    */     
/* 344:312 */     ItemList.Tool_Lighter_Invar_Empty.set(addItem(tLastID = 474, "Lighter (Empty)", "", new Object[] { new ItemData(Materials.Invar, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 345:313 */     ItemList.Tool_Lighter_Invar_Used.set(addItem(tLastID = 475, "Lighter", "", new Object[] { new ItemData(Materials.Invar, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE }));
/* 346:314 */     ItemList.Tool_Lighter_Invar_Full.set(addItem(tLastID = 476, "Lighter (Full)", "", new Object[] { new ItemData(Materials.Invar, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 347:    */     
/* 348:316 */     tBehaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Invar_Empty.get(1L, new Object[0]), ItemList.Tool_Lighter_Invar_Used.get(1L, new Object[0]), ItemList.Tool_Lighter_Invar_Full.get(1L, new Object[0]), 100L);
/* 349:317 */     addItemBehavior(32475, tBehaviour);addItemBehavior(32476, tBehaviour);
/* 350:    */     
/* 351:319 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Invar, 2L), new ItemStack(Items.flint, 1), ItemList.Tool_Lighter_Invar_Empty.get(1L, new Object[0]), 256, 16);
/* 352:    */     
/* 353:321 */     ItemList.Tool_Lighter_Platinum_Empty.set(addItem(tLastID = 477, "Platinum Lighter (Empty)", "A known Prank Master is engraved on it", new Object[] { new ItemData(Materials.Platinum, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 354:322 */     ItemList.Tool_Lighter_Platinum_Used.set(addItem(tLastID = 478, "Platinum Lighter", "A known Prank Master is engraved on it", new Object[] { new ItemData(Materials.Platinum, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE }));
/* 355:323 */     ItemList.Tool_Lighter_Platinum_Full.set(addItem(tLastID = 479, "Platinum Lighter (Full)", "A known Prank Master is engraved on it", new Object[] { new ItemData(Materials.Platinum, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 356:    */     
/* 357:325 */     tBehaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Platinum_Empty.get(1L, new Object[0]), ItemList.Tool_Lighter_Platinum_Used.get(1L, new Object[0]), ItemList.Tool_Lighter_Platinum_Full.get(1L, new Object[0]), 1000L);
/* 358:326 */     addItemBehavior(32478, tBehaviour);addItemBehavior(32479, tBehaviour);
/* 359:    */     
/* 360:328 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Platinum, 2L), new ItemStack(Items.flint, 1), ItemList.Tool_Lighter_Platinum_Empty.get(1L, new Object[0]), 256, 256);
/* 361:    */     
/* 362:330 */     ItemList.Ingot_IridiumAlloy.set(addItem(tLastID = 480, "Iridium Alloy Ingot", "Used to make Iridium Plates", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L) }));
/* 363:    */     
/* 364:332 */     GT_ModHandler.addRollingMachineRecipe(ItemList.Ingot_IridiumAlloy.get(1L, new Object[0]), new Object[] { "IAI", "ADA", "IAI", Character.valueOf('D'), GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : OrePrefixes.dust.get(Materials.Diamond), Character.valueOf('A'), OrePrefixes.plateAlloy.get("Advanced"), Character.valueOf('I'), OrePrefixes.plate.get(Materials.Iridium) });
/* 365:333 */     GT_ModHandler.addCraftingRecipe(ItemList.Ingot_IridiumAlloy.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "IAI", "ADA", "IAI", Character.valueOf('D'), GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : OrePrefixes.dust.get(Materials.Diamond), Character.valueOf('A'), OrePrefixes.plateAlloy.get("Advanced"), Character.valueOf('I'), OrePrefixes.plate.get(Materials.Iridium) });
/* 366:334 */     GT_Values.RA.addImplosionRecipe(ItemList.Ingot_IridiumAlloy.get(1L, new Object[0]), 8, GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Iridium, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 4L));
/* 367:    */     
/* 368:336 */     ItemList.Paper_Printed_Pages.set(addItem(tLastID = 481, "Printed Pages", "Used to make written Books", new Object[] { new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]), new Behaviour_PrintedPages(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 369:337 */     ItemList.Paper_Magic_Empty.set(addItem(tLastID = 482, "Magic Paper", "", new Object[] { SubTag.INVISIBLE, new ItemData(Materials.Paper, 3628800L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTIO, 1L) }));
/* 370:338 */     ItemList.Paper_Magic_Page.set(addItem(tLastID = 483, "Enchanted Page", "", new Object[] { SubTag.INVISIBLE, new ItemData(Materials.Paper, 3628800L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTIO, 2L) }));
/* 371:339 */     ItemList.Paper_Magic_Pages.set(addItem(tLastID = 484, "Enchanted Pages", "", new Object[] { SubTag.INVISIBLE, new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTIO, 4L) }));
/* 372:340 */     ItemList.Paper_Punch_Card_Empty.set(addItem(tLastID = 485, "Punch Card", "", new Object[] { SubTag.INVISIBLE, new ItemData(Materials.Paper, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L) }));
/* 373:341 */     ItemList.Paper_Punch_Card_Encoded.set(addItem(tLastID = 486, "Punched Card", "", new Object[] { SubTag.INVISIBLE, new ItemData(Materials.Paper, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 374:342 */     ItemList.Book_Written_01.set(addItem(tLastID = 487, "Book", "", new Object[] { new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 375:343 */     ItemList.Book_Written_02.set(addItem(tLastID = 488, "Book", "", new Object[] { new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 376:344 */     ItemList.Book_Written_03.set(addItem(tLastID = 489, "Book", "", new Object[] { new ItemData(Materials.Paper, 10886400L, new MaterialStack[0]), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 377:    */     
/* 378:346 */     ItemList.Schematic.set(addItem(tLastID = 490, "Schematic", "EMPTY", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 1L) }));
/* 379:347 */     ItemList.Schematic_Crafting.set(addItem(tLastID = 491, "Schematic (Crafting)", "Crafts the Programmed Recipe", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }));
/* 380:348 */     ItemList.Schematic_1by1.set(addItem(tLastID = 495, "Schematic (1x1)", "Crafts 1 Items as 1x1 (use in Packager)", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }));
/* 381:349 */     ItemList.Schematic_2by2.set(addItem(tLastID = 496, "Schematic (2x2)", "Crafts 4 Items as 2x2 (use in Packager)", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }));
/* 382:350 */     ItemList.Schematic_3by3.set(addItem(tLastID = 497, "Schematic (3x3)", "Crafts 9 Items as 3x3 (use in Packager)", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }));
/* 383:351 */     ItemList.Schematic_Dust.set(addItem(tLastID = 498, "Schematic (Dusts)", "Combines Dusts (use in Packager)", new Object[] { new ItemData(Materials.StainlessSteel, 7257600L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L) }));
/* 384:    */     
/* 385:353 */     GT_ModHandler.addCraftingRecipe(ItemList.Schematic_1by1.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "d  ", " P ", "   ", Character.valueOf('P'), ItemList.Schematic });
/* 386:354 */     GT_ModHandler.addCraftingRecipe(ItemList.Schematic_2by2.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " d ", " P ", "   ", Character.valueOf('P'), ItemList.Schematic });
/* 387:355 */     GT_ModHandler.addCraftingRecipe(ItemList.Schematic_3by3.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  d", " P ", "   ", Character.valueOf('P'), ItemList.Schematic });
/* 388:356 */     GT_ModHandler.addCraftingRecipe(ItemList.Schematic_Dust.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", " P ", "  d", Character.valueOf('P'), ItemList.Schematic });
/* 389:    */     
/* 390:358 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Schematic_Crafting });
/* 391:359 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Schematic_1by1 });
/* 392:360 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Schematic_2by2 });
/* 393:361 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Schematic_3by3 });
/* 394:362 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Schematic_Dust });
/* 395:    */     
/* 396:364 */     ItemList.Battery_Hull_LV.set(addItem(tLastID = 500, "Small Battery Hull", "An empty LV Battery Hull", new Object[] { new ItemData(Materials.BatteryAlloy, OrePrefixes.plate.mMaterialAmount * 2L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 397:365 */     ItemList.Battery_Hull_MV.set(addItem(tLastID = 501, "Medium Battery Hull", "An empty MV Battery Hull", new Object[] { new ItemData(Materials.BatteryAlloy, OrePrefixes.plate.mMaterialAmount * 6L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 398:366 */     ItemList.Battery_Hull_HV.set(addItem(tLastID = 502, "Large Battery Hull", "An empty HV Battery Hull", new Object[] { new ItemData(Materials.BatteryAlloy, OrePrefixes.plate.mMaterialAmount * 18L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L) }));
/* 399:    */     
/* 400:368 */     GT_ModHandler.addCraftingRecipe(ItemList.Battery_Hull_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "C", "P", "P", Character.valueOf('P'), OrePrefixes.plate.get(Materials.BatteryAlloy), Character.valueOf('C'), OreDictNames.craftingWireTin });
/* 401:369 */     GT_ModHandler.addCraftingRecipe(ItemList.Battery_Hull_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "C C", "PPP", "PPP", Character.valueOf('P'), OrePrefixes.plate.get(Materials.BatteryAlloy), Character.valueOf('C'), OreDictNames.craftingWireCopper });
/* 402:    */     
/* 403:371 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BatteryAlloy, 2L), ItemList.Battery_Hull_LV.get(1L, new Object[0]), 800, 1);
/* 404:372 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BatteryAlloy, 6L), ItemList.Battery_Hull_MV.get(1L, new Object[0]), 1600, 2);
/* 405:373 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.AnnealedCopper, 2L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BatteryAlloy, 6L), ItemList.Battery_Hull_MV.get(1L, new Object[0]), 1600, 2);
/* 406:374 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BatteryAlloy, 18L), ItemList.Battery_Hull_HV.get(1L, new Object[0]), 3200, 4);
/* 407:    */     
/* 408:376 */     ItemList.Battery_RE_ULV_Tantalum.set(addItem(tLastID = 499, "Tantalum Capacitor", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L) }));
/* 409:377 */     setElectricStats(32000 + tLastID, 1000L, GT_Values.V[0], 0L, -3L, false);
/* 410:    */     
/* 411:379 */     ItemList.Battery_SU_LV_SulfuricAcid.set(addItem(tLastID = 510, "Small Acid Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 412:380 */     setElectricStats(32000 + tLastID, 12000L, GT_Values.V[1], 1L, -2L, true);
/* 413:381 */     ItemList.Battery_SU_LV_Mercury.set(addItem(tLastID = 511, "Small Mercury Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 414:382 */     setElectricStats(32000 + tLastID, 32000L, GT_Values.V[1], 1L, -2L, true);
/* 415:    */     
/* 416:384 */     ItemList.Battery_RE_LV_Cadmium.set(addItem(tLastID = 517, "Small Cadmium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY" }));
/* 417:385 */     setElectricStats(32000 + tLastID, 75000L, GT_Values.V[1], 1L, -3L, true);
/* 418:386 */     ItemList.Battery_RE_LV_Lithium.set(addItem(tLastID = 518, "Small Lithium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY" }));
/* 419:387 */     setElectricStats(32000 + tLastID, 100000L, GT_Values.V[1], 1L, -3L, true);
/* 420:388 */     ItemList.Battery_RE_LV_Sodium.set(addItem(tLastID = 519, "Small Sodium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY" }));
/* 421:389 */     setElectricStats(32000 + tLastID, 50000L, GT_Values.V[1], 1L, -3L, true);
/* 422:    */     
/* 423:391 */     ItemList.Battery_SU_MV_SulfuricAcid.set(addItem(tLastID = 520, "Medium Acid Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L) }));
/* 424:392 */     setElectricStats(32000 + tLastID, 48000L, GT_Values.V[2], 2L, -2L, true);
/* 425:393 */     ItemList.Battery_SU_MV_Mercury.set(addItem(tLastID = 521, "Medium Mercury Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L) }));
/* 426:394 */     setElectricStats(32000 + tLastID, 128000L, GT_Values.V[2], 2L, -2L, true);
/* 427:    */     
/* 428:396 */     ItemList.Battery_RE_MV_Cadmium.set(addItem(tLastID = 527, "Medium Cadmium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 429:397 */     setElectricStats(32000 + tLastID, 300000L, GT_Values.V[2], 2L, -3L, true);
/* 430:398 */     ItemList.Battery_RE_MV_Lithium.set(addItem(tLastID = 528, "Medium Lithium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 431:399 */     setElectricStats(32000 + tLastID, 400000L, GT_Values.V[2], 2L, -3L, true);
/* 432:400 */     ItemList.Battery_RE_MV_Sodium.set(addItem(tLastID = 529, "Medium Sodium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L) }));
/* 433:401 */     setElectricStats(32000 + tLastID, 200000L, GT_Values.V[2], 2L, -3L, true);
/* 434:    */     
/* 435:403 */     ItemList.Battery_SU_HV_SulfuricAcid.set(addItem(tLastID = 530, "Large Acid Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L) }));
/* 436:404 */     setElectricStats(32000 + tLastID, 192000L, GT_Values.V[3], 3L, -2L, true);
/* 437:405 */     ItemList.Battery_SU_HV_Mercury.set(addItem(tLastID = 531, "Large Mercury Battery", "Single Use", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L) }));
/* 438:406 */     setElectricStats(32000 + tLastID, 512000L, GT_Values.V[3], 3L, -2L, true);
/* 439:    */     
/* 440:408 */     ItemList.Battery_RE_HV_Cadmium.set(addItem(tLastID = 537, "Large Cadmium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L) }));
/* 441:409 */     setElectricStats(32000 + tLastID, 1200000L, GT_Values.V[3], 3L, -3L, true);
/* 442:410 */     ItemList.Battery_RE_HV_Lithium.set(addItem(tLastID = 538, "Large Lithium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L) }));
/* 443:411 */     setElectricStats(32000 + tLastID, 1600000L, GT_Values.V[3], 3L, -3L, true);
/* 444:412 */     ItemList.Battery_RE_HV_Sodium.set(addItem(tLastID = 539, "Large Sodium Battery", "Reusable", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L) }));
/* 445:413 */     setElectricStats(32000 + tLastID, 800000L, GT_Values.V[3], 3L, -3L, true);
/* 446:    */     
/* 447:415 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_SulfuricAcid.get(1L, new Object[0]), ItemList.Battery_Hull_LV.get(1L, new Object[0]));
/* 448:416 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_Mercury.get(1L, new Object[0]), ItemList.Battery_Hull_LV.get(1L, new Object[0]));
/* 449:417 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_SulfuricAcid.get(1L, new Object[0]), ItemList.Battery_Hull_MV.get(1L, new Object[0]));
/* 450:418 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_Mercury.get(1L, new Object[0]), ItemList.Battery_Hull_MV.get(1L, new Object[0]));
/* 451:419 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_SulfuricAcid.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(1L, new Object[0]));
/* 452:420 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_Mercury.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(1L, new Object[0]));
/* 453:421 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Cadmium.get(1L, new Object[0]), ItemList.Battery_Hull_LV.get(1L, new Object[0]));
/* 454:422 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Lithium.get(1L, new Object[0]), ItemList.Battery_Hull_LV.get(1L, new Object[0]));
/* 455:423 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Sodium.get(1L, new Object[0]), ItemList.Battery_Hull_LV.get(1L, new Object[0]));
/* 456:424 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Cadmium.get(1L, new Object[0]), ItemList.Battery_Hull_MV.get(1L, new Object[0]));
/* 457:425 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Lithium.get(1L, new Object[0]), ItemList.Battery_Hull_MV.get(1L, new Object[0]));
/* 458:426 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Sodium.get(1L, new Object[0]), ItemList.Battery_Hull_MV.get(1L, new Object[0]));
/* 459:427 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Cadmium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(1L, new Object[0]));
/* 460:428 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Lithium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(1L, new Object[0]));
/* 461:429 */     GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Sodium.get(1L, new Object[0]), ItemList.Battery_Hull_HV.get(1L, new Object[0]));
/* 462:    */     
/* 463:431 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cadmium, 2L), ItemList.Battery_Hull_LV.get(1L, new Object[0]), ItemList.Battery_RE_LV_Cadmium.get(1L, new Object[0]), null, 100, 2);
/* 464:432 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 2L), ItemList.Battery_Hull_LV.get(1L, new Object[0]), ItemList.Battery_RE_LV_Lithium.get(1L, new Object[0]), null, 100, 2);
/* 465:433 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 2L), ItemList.Battery_Hull_LV.get(1L, new Object[0]), ItemList.Battery_RE_LV_Sodium.get(1L, new Object[0]), null, 100, 2);
/* 466:434 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cadmium, 8L), ItemList.Battery_Hull_MV.get(1L, new Object[0]), ItemList.Battery_RE_MV_Cadmium.get(1L, new Object[0]), null, 400, 2);
/* 467:435 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 8L), ItemList.Battery_Hull_MV.get(1L, new Object[0]), ItemList.Battery_RE_MV_Lithium.get(1L, new Object[0]), null, 400, 2);
/* 468:436 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 8L), ItemList.Battery_Hull_MV.get(1L, new Object[0]), ItemList.Battery_RE_MV_Sodium.get(1L, new Object[0]), null, 400, 2);
/* 469:437 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cadmium, 32L), ItemList.Battery_Hull_HV.get(1L, new Object[0]), ItemList.Battery_RE_HV_Cadmium.get(1L, new Object[0]), null, 1600, 2);
/* 470:438 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lithium, 32L), ItemList.Battery_Hull_HV.get(1L, new Object[0]), ItemList.Battery_RE_HV_Lithium.get(1L, new Object[0]), null, 1600, 2);
/* 471:439 */     GT_Values.RA.addCannerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodium, 32L), ItemList.Battery_Hull_HV.get(1L, new Object[0]), ItemList.Battery_RE_HV_Sodium.get(1L, new Object[0]), null, 1600, 2);
/* 472:    */     
/* 473:441 */     ItemList.Energy_LapotronicOrb.set(addItem(tLastID = 597, "Lapotronic Energy Orb", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), OrePrefixes.battery.get(Materials.Ultimate) }));
/* 474:442 */     setElectricStats(32000 + tLastID, 100000000L, GT_Values.V[5], 5L, -3L, true);
/* 475:    */     
/* 476:444 */     ItemList.ZPM.set(addItem(tLastID = 598, "Zero Point Module", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L) }));
/* 477:445 */     setElectricStats(32000 + tLastID, 2000000000000L, GT_Values.V[7], 7L, -2L, true);
/* 478:    */     
				  ItemList.Energy_LapotronicOrb2.set(addItem(tLastID = 599, "Lapotronic Energy Orb Cluster", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), OrePrefixes.battery.get(Materials.Ultimate) }));
/* 474:442 */     setElectricStats(32000 + tLastID, 1000000000L, GT_Values.V[6], 6L, -3L, true);
/* 475:    */     
/* 476:444 */     ItemList.ZPM2.set(addItem(tLastID = 605, "Ultimate Battery", "Fill this to win minecraft", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L) }));
/* 477:445 */     setElectricStats(32000 + tLastID, Long.MAX_VALUE, GT_Values.V[8], 8L, -3L, true);
/* 478:    */     
/* 479:447 */     ItemList.Electric_Motor_LV.set(addItem(tLastID = 600, "Electric Motor (LV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L) }));
/* 480:448 */     ItemList.Electric_Motor_MV.set(addItem(tLastID = 601, "Electric Motor (MV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L) }));
/* 481:449 */     ItemList.Electric_Motor_HV.set(addItem(tLastID = 602, "Electric Motor (HV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L) }));
/* 482:450 */     ItemList.Electric_Motor_EV.set(addItem(tLastID = 603, "Electric Motor (EV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L) }));
/* 483:451 */     ItemList.Electric_Motor_IV.set(addItem(tLastID = 604, "Electric Motor (IV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L) }));
/* 484:452 */     ItemList.Electric_Motor_LuV.set(ItemList.Electric_Motor_IV.get(1L, new Object[0]));
/* 485:453 */     ItemList.Electric_Motor_ZPM.set(ItemList.Electric_Motor_LuV.get(1L, new Object[0]));
/* 486:454 */     ItemList.Electric_Motor_UV.set(ItemList.Electric_Motor_ZPM.get(1L, new Object[0]));
/* 487:    */     
/* 488:456 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.IronMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.AnyIron), Character.valueOf('W'), OrePrefixes.wireGt01.get(Materials.AnyCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tin) });
/* 489:457 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.SteelMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.Steel), Character.valueOf('W'), OrePrefixes.wireGt01.get(Materials.AnyCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tin) });
/* 490:458 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.SteelMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.Aluminium), Character.valueOf('W'), OrePrefixes.wireGt02.get(Materials.AnyCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.AnyCopper) });
/* 491:459 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.SteelMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.StainlessSteel), Character.valueOf('W'), OrePrefixes.wireGt04.get(Materials.AnyCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Gold) });
/* 492:460 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.NeodymiumMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.Titanium), Character.valueOf('W'), OrePrefixes.wireGt08.get(Materials.AnnealedCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Aluminium) });
/* 493:461 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CWR", "WIW", "RWC", Character.valueOf('I'), OrePrefixes.stick.get(Materials.NeodymiumMagnetic), Character.valueOf('R'), OrePrefixes.stick.get(Materials.TungstenSteel), Character.valueOf('W'), OrePrefixes.wireGt16.get(Materials.AnnealedCopper), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tungsten) });
/* 494:    */     
/* 495:463 */     ItemList.Electric_Pump_LV.set(addItem(tLastID = 610, "Electric Pump (LV)", "640 L/sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L) }));
/* 496:464 */     ItemList.Electric_Pump_MV.set(addItem(tLastID = 611, "Electric Pump (MV)", "2560 L/sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L) }));
/* 497:465 */     ItemList.Electric_Pump_HV.set(addItem(tLastID = 612, "Electric Pump (HV)", "10240 L/sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L) }));
/* 498:466 */     ItemList.Electric_Pump_EV.set(addItem(tLastID = 613, "Electric Pump (EV)", "40960 L/sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 8L) }));
/* 499:467 */     ItemList.Electric_Pump_IV.set(addItem(tLastID = 614, "Electric Pump (IV)", "163840 L/sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 16L) }));
/* 500:468 */     ItemList.Electric_Pump_LuV.set(ItemList.Electric_Pump_IV.get(1L, new Object[0]));
/* 501:469 */     ItemList.Electric_Pump_ZPM.set(ItemList.Electric_Pump_LuV.get(1L, new Object[0]));
/* 502:470 */     ItemList.Electric_Pump_UV.set(ItemList.Electric_Pump_ZPM.get(1L, new Object[0]));
/* 503:    */     
/* 504:472 */     GregTech_API.registerCover(ItemList.Electric_Pump_LV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP) }), new GT_Cover_Pump(32));
/* 505:473 */     GregTech_API.registerCover(ItemList.Electric_Pump_MV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP) }), new GT_Cover_Pump(128));
/* 506:474 */     GregTech_API.registerCover(ItemList.Electric_Pump_HV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP) }), new GT_Cover_Pump(512));
/* 507:475 */     GregTech_API.registerCover(ItemList.Electric_Pump_EV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP) }), new GT_Cover_Pump(2048));
/* 508:476 */     GregTech_API.registerCover(ItemList.Electric_Pump_IV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP) }), new GT_Cover_Pump(8192));
/* 509:    */     
/* 510:478 */     ItemList.Rotor_LV.set(addItem(tLastID = 620, "Tin Rotor", "", new Object[] { OrePrefixes.rotor.get(Materials.Tin), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L) }));
/* 511:479 */     ItemList.Rotor_MV.set(addItem(tLastID = 621, "Bronze Rotor", "", new Object[] { OrePrefixes.rotor.get(Materials.Bronze), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L) }));
/* 512:480 */     ItemList.Rotor_HV.set(addItem(tLastID = 622, "Steel Rotor", "", new Object[] { OrePrefixes.rotor.get(Materials.Steel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L) }));
/* 513:481 */     ItemList.Rotor_EV.set(addItem(tLastID = 623, "Stainless Steel Rotor", "", new Object[] { OrePrefixes.rotor.get(Materials.StainlessSteel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L) }));
/* 514:482 */     ItemList.Rotor_IV.set(addItem(tLastID = 624, "Tungstensteel Rotor", "", new Object[] { OrePrefixes.rotor.get(Materials.TungstenSteel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L) }));
/* 515:483 */     ItemList.Rotor_LuV.set(ItemList.Rotor_IV.get(1L, new Object[0]));
/* 516:484 */     ItemList.Rotor_ZPM.set(ItemList.Rotor_LuV.get(1L, new Object[0]));
/* 517:485 */     ItemList.Rotor_UV.set(ItemList.Rotor_ZPM.get(1L, new Object[0]));
/* 518:    */     
/* 519:487 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SXO", "dPw", "OMW", Character.valueOf('M'), ItemList.Electric_Motor_LV, Character.valueOf('O'), OrePrefixes.ring.get(Materials.Rubber), Character.valueOf('X'), OrePrefixes.rotor.get(Materials.Tin), Character.valueOf('S'), OrePrefixes.screw.get(Materials.Tin), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Tin), Character.valueOf('P'), OrePrefixes.pipeMedium.get(Materials.Bronze) });
/* 520:488 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SXO", "dPw", "OMW", Character.valueOf('M'), ItemList.Electric_Motor_MV, Character.valueOf('O'), OrePrefixes.ring.get(Materials.Rubber), Character.valueOf('X'), OrePrefixes.rotor.get(Materials.Bronze), Character.valueOf('S'), OrePrefixes.screw.get(Materials.Bronze), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.AnyCopper), Character.valueOf('P'), OrePrefixes.pipeMedium.get(Materials.Steel) });
/* 521:489 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SXO", "dPw", "OMW", Character.valueOf('M'), ItemList.Electric_Motor_HV, Character.valueOf('O'), OrePrefixes.ring.get(Materials.Rubber), Character.valueOf('X'), OrePrefixes.rotor.get(Materials.Steel), Character.valueOf('S'), OrePrefixes.screw.get(Materials.Steel), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Gold), Character.valueOf('P'), OrePrefixes.pipeMedium.get(Materials.StainlessSteel) });
/* 522:490 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SXO", "dPw", "OMW", Character.valueOf('M'), ItemList.Electric_Motor_EV, Character.valueOf('O'), OrePrefixes.ring.get(Materials.Rubber), Character.valueOf('X'), OrePrefixes.rotor.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.screw.get(Materials.StainlessSteel), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Aluminium), Character.valueOf('P'), OrePrefixes.pipeMedium.get(Materials.Titanium) });
/* 523:491 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SXO", "dPw", "OMW", Character.valueOf('M'), ItemList.Electric_Motor_IV, Character.valueOf('O'), OrePrefixes.ring.get(Materials.Rubber), Character.valueOf('X'), OrePrefixes.rotor.get(Materials.TungstenSteel), Character.valueOf('S'), OrePrefixes.screw.get(Materials.TungstenSteel), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Tungsten), Character.valueOf('P'), OrePrefixes.pipeMedium.get(Materials.TungstenSteel) });
/* 524:    */     
/* 525:493 */     ItemList.Conveyor_Module_LV.set(addItem(tLastID = 630, "Conveyor Module (LV)", "1 Stack every 20 secs (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L) }));
/* 526:494 */     ItemList.Conveyor_Module_MV.set(addItem(tLastID = 631, "Conveyor Module (MV)", "1 Stack every 5 secs (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L) }));
/* 527:495 */     ItemList.Conveyor_Module_HV.set(addItem(tLastID = 632, "Conveyor Module (HV)", "1 Stack every 1 sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 4L) }));
/* 528:496 */     ItemList.Conveyor_Module_EV.set(addItem(tLastID = 633, "Conveyor Module (EV)", "1 Stack every 1/5 sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 8L) }));
/* 529:497 */     ItemList.Conveyor_Module_IV.set(addItem(tLastID = 634, "Conveyor Module (IV)", "1 Stack every 1/20 sec (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L) }));
/* 530:498 */     ItemList.Conveyor_Module_LuV.set(ItemList.Conveyor_Module_IV.get(1L, new Object[0]));
/* 531:499 */     ItemList.Conveyor_Module_ZPM.set(ItemList.Conveyor_Module_LuV.get(1L, new Object[0]));
/* 532:500 */     ItemList.Conveyor_Module_UV.set(ItemList.Conveyor_Module_ZPM.get(1L, new Object[0]));
/* 533:    */     
/* 534:502 */     GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "MCM", "RRR", Character.valueOf('M'), ItemList.Electric_Motor_LV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tin), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 535:503 */     GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "MCM", "RRR", Character.valueOf('M'), ItemList.Electric_Motor_MV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.AnyCopper), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 536:504 */     GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "MCM", "RRR", Character.valueOf('M'), ItemList.Electric_Motor_HV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Gold), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 537:505 */     GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "MCM", "RRR", Character.valueOf('M'), ItemList.Electric_Motor_EV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Aluminium), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 538:506 */     GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "RRR", "MCM", "RRR", Character.valueOf('M'), ItemList.Electric_Motor_IV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tungsten), Character.valueOf('R'), OrePrefixes.plate.get(Materials.Rubber) });
/* 539:    */     
/* 540:508 */     GregTech_API.registerCover(ItemList.Conveyor_Module_LV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR) }), new GT_Cover_Conveyor(400));
/* 541:509 */     GregTech_API.registerCover(ItemList.Conveyor_Module_MV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR) }), new GT_Cover_Conveyor(100));
/* 542:510 */     GregTech_API.registerCover(ItemList.Conveyor_Module_HV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR) }), new GT_Cover_Conveyor(20));
/* 543:511 */     GregTech_API.registerCover(ItemList.Conveyor_Module_EV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR) }), new GT_Cover_Conveyor(4));
/* 544:512 */     GregTech_API.registerCover(ItemList.Conveyor_Module_IV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR) }), new GT_Cover_Conveyor(1));
/* 545:    */     
/* 546:514 */     ItemList.Electric_Piston_LV.set(addItem(tLastID = 640, "Electric Piston (LV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L) }));
/* 547:515 */     ItemList.Electric_Piston_MV.set(addItem(tLastID = 641, "Electric Piston (MV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L) }));
/* 548:516 */     ItemList.Electric_Piston_HV.set(addItem(tLastID = 642, "Electric Piston (HV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L) }));
/* 549:517 */     ItemList.Electric_Piston_EV.set(addItem(tLastID = 643, "Electric Piston (EV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L) }));
/* 550:518 */     ItemList.Electric_Piston_IV.set(addItem(tLastID = 644, "Electric Piston (IV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L) }));
/* 551:519 */     ItemList.Electric_Piston_LuV.set(ItemList.Electric_Piston_IV.get(1L, new Object[0]));
/* 552:520 */     ItemList.Electric_Piston_ZPM.set(ItemList.Electric_Piston_LuV.get(1L, new Object[0]));
/* 553:521 */     ItemList.Electric_Piston_UV.set(ItemList.Electric_Piston_ZPM.get(1L, new Object[0]));
/* 554:    */     
/* 555:523 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "PPP", "CSS", "CMG", Character.valueOf('P'), OrePrefixes.plate.get(Materials.Steel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Steel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.Steel), Character.valueOf('M'), ItemList.Electric_Motor_LV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tin) });
/* 556:524 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "PPP", "CSS", "CMG", Character.valueOf('P'), OrePrefixes.plate.get(Materials.Aluminium), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Aluminium), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.Aluminium), Character.valueOf('M'), ItemList.Electric_Motor_MV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.AnyCopper) });
/* 557:525 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "PPP", "CSS", "CMG", Character.valueOf('P'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.StainlessSteel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.StainlessSteel), Character.valueOf('M'), ItemList.Electric_Motor_HV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Gold) });
/* 558:526 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "PPP", "CSS", "CMG", Character.valueOf('P'), OrePrefixes.plate.get(Materials.Titanium), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Titanium), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.Titanium), Character.valueOf('M'), ItemList.Electric_Motor_EV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Aluminium) });
/* 559:527 */     GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "PPP", "CSS", "CMG", Character.valueOf('P'), OrePrefixes.plate.get(Materials.TungstenSteel), Character.valueOf('S'), OrePrefixes.stick.get(Materials.TungstenSteel), Character.valueOf('G'), OrePrefixes.gearGtSmall.get(Materials.TungstenSteel), Character.valueOf('M'), ItemList.Electric_Motor_IV, Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tungsten) });
/* 560:    */     
/* 561:529 */     ItemList.Robot_Arm_LV.set(addItem(tLastID = 650, "Robot Arm (LV)", "Inserts into specific Slots (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L) }));
/* 562:530 */     ItemList.Robot_Arm_MV.set(addItem(tLastID = 651, "Robot Arm (MV)", "Inserts into specific Slots (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L) }));
/* 563:531 */     ItemList.Robot_Arm_HV.set(addItem(tLastID = 652, "Robot Arm (HV)", "Inserts into specific Slots (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 4L) }));
/* 564:532 */     ItemList.Robot_Arm_EV.set(addItem(tLastID = 653, "Robot Arm (EV)", "Inserts into specific Slots (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 8L) }));
/* 565:533 */     ItemList.Robot_Arm_IV.set(addItem(tLastID = 654, "Robot Arm (IV)", "Inserts into specific Slots (as Cover)", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 16L) }));
/* 566:534 */     ItemList.Robot_Arm_LuV.set(ItemList.Robot_Arm_IV.get(1L, new Object[0]));
/* 567:535 */     ItemList.Robot_Arm_ZPM.set(ItemList.Robot_Arm_LuV.get(1L, new Object[0]));
/* 568:536 */     ItemList.Robot_Arm_UV.set(ItemList.Robot_Arm_ZPM.get(1L, new Object[0]));
/* 569:    */     
/* 570:538 */     GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CCC", "MSM", "PES", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Steel), Character.valueOf('M'), ItemList.Electric_Motor_LV, Character.valueOf('P'), ItemList.Electric_Piston_LV, Character.valueOf('E'), OrePrefixes.circuit.get(Materials.Basic), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tin) });
/* 571:539 */     GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CCC", "MSM", "PES", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Aluminium), Character.valueOf('M'), ItemList.Electric_Motor_MV, Character.valueOf('P'), ItemList.Electric_Piston_MV, Character.valueOf('E'), OrePrefixes.circuit.get(Materials.Good), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.AnyCopper) });
/* 572:540 */     GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CCC", "MSM", "PES", Character.valueOf('S'), OrePrefixes.stick.get(Materials.StainlessSteel), Character.valueOf('M'), ItemList.Electric_Motor_HV, Character.valueOf('P'), ItemList.Electric_Piston_HV, Character.valueOf('E'), OrePrefixes.circuit.get(Materials.Advanced), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Gold) });
/* 573:541 */     GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CCC", "MSM", "PES", Character.valueOf('S'), OrePrefixes.stick.get(Materials.Titanium), Character.valueOf('M'), ItemList.Electric_Motor_EV, Character.valueOf('P'), ItemList.Electric_Piston_EV, Character.valueOf('E'), OrePrefixes.circuit.get(Materials.Elite), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Aluminium) });
/* 574:542 */     GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "CCC", "MSM", "PES", Character.valueOf('S'), OrePrefixes.stick.get(Materials.TungstenSteel), Character.valueOf('M'), ItemList.Electric_Motor_IV, Character.valueOf('P'), ItemList.Electric_Piston_IV, Character.valueOf('E'), OrePrefixes.circuit.get(Materials.Master), Character.valueOf('C'), OrePrefixes.cableGt01.get(Materials.Tungsten) });
/* 575:    */     
/* 576:544 */     GregTech_API.registerCover(ItemList.Robot_Arm_LV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM) }), new GT_Cover_Arm(400));
/* 577:545 */     GregTech_API.registerCover(ItemList.Robot_Arm_MV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM) }), new GT_Cover_Arm(100));
/* 578:546 */     GregTech_API.registerCover(ItemList.Robot_Arm_HV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM) }), new GT_Cover_Arm(20));
/* 579:547 */     GregTech_API.registerCover(ItemList.Robot_Arm_EV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM) }), new GT_Cover_Arm(4));
/* 580:548 */     GregTech_API.registerCover(ItemList.Robot_Arm_IV.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM) }), new GT_Cover_Arm(1));
/* 581:    */     
/* 582:550 */     ItemList.Field_Generator_LV.set(addItem(tLastID = 670, "Field Generator (LV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 1L) }));
/* 583:551 */     ItemList.Field_Generator_MV.set(addItem(tLastID = 671, "Field Generator (MV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 2L) }));
/* 584:552 */     ItemList.Field_Generator_HV.set(addItem(tLastID = 672, "Field Generator (HV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L) }));
/* 585:553 */     ItemList.Field_Generator_EV.set(addItem(tLastID = 673, "Field Generator (EV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 8L) }));
/* 586:554 */     ItemList.Field_Generator_IV.set(addItem(tLastID = 674, "Field Generator (IV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 16L) }));
/* 587:555 */     ItemList.Field_Generator_LuV.set(ItemList.Field_Generator_IV.get(1L, new Object[0]));
/* 588:556 */     ItemList.Field_Generator_ZPM.set(ItemList.Field_Generator_LuV.get(1L, new Object[0]));
/* 589:557 */     ItemList.Field_Generator_UV.set(ItemList.Field_Generator_ZPM.get(1L, new Object[0]));
/* 590:    */     
/* 591:559 */     GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "WCW", "CGC", "WCW", Character.valueOf('G'), OrePrefixes.gem.get(Materials.EnderPearl), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic), Character.valueOf('W'), OrePrefixes.wireGt01.get(Materials.Osmium) });
/* 592:560 */     GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "WCW", "CGC", "WCW", Character.valueOf('G'), OrePrefixes.gem.get(Materials.EnderEye), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Good), Character.valueOf('W'), OrePrefixes.wireGt02.get(Materials.Osmium) });
/* 593:561 */     GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "WCW", "CGC", "WCW", Character.valueOf('G'), OrePrefixes.gem.get(Materials.NetherStar), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Advanced), Character.valueOf('W'), OrePrefixes.wireGt04.get(Materials.Osmium) });
/* 594:562 */     GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "WCW", "CGC", "WCW", Character.valueOf('G'), OrePrefixes.gem.get(Materials.NetherStar), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Elite), Character.valueOf('W'), OrePrefixes.wireGt08.get(Materials.Osmium) });
/* 595:563 */     GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "WCW", "CGC", "WCW", Character.valueOf('G'), OrePrefixes.gem.get(Materials.NetherStar), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Master), Character.valueOf('W'), OrePrefixes.wireGt16.get(Materials.Osmium) });
/* 596:    */     
/* 597:565 */     ItemList.Emitter_LV.set(addItem(tLastID = 680, "Emitter (LV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 1L) }));
/* 598:566 */     ItemList.Emitter_MV.set(addItem(tLastID = 681, "Emitter (MV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 2L) }));
/* 599:567 */     ItemList.Emitter_HV.set(addItem(tLastID = 682, "Emitter (HV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 4L) }));
/* 600:568 */     ItemList.Emitter_EV.set(addItem(tLastID = 683, "Emitter (EV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 8L) }));
/* 601:569 */     ItemList.Emitter_IV.set(addItem(tLastID = 684, "Emitter (IV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 16L) }));
/* 602:570 */     ItemList.Emitter_LuV.set(ItemList.Emitter_IV.get(1L, new Object[0]));
/* 603:571 */     ItemList.Emitter_ZPM.set(ItemList.Emitter_LuV.get(1L, new Object[0]));
/* 604:572 */     ItemList.Emitter_UV.set(ItemList.Emitter_ZPM.get(1L, new Object[0]));
/* 605:    */     
/* 606:574 */     GT_ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSC", "WQS", "CWS", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.Quartzite), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Brass), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Tin) });
/* 607:575 */     GT_ModHandler.addCraftingRecipe(ItemList.Emitter_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSC", "WQS", "CWS", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.NetherQuartz), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Electrum), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Good), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.AnyCopper) });
/* 608:576 */     GT_ModHandler.addCraftingRecipe(ItemList.Emitter_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSC", "WQS", "CWS", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.Emerald), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Chrome), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Advanced), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Gold) });
/* 609:577 */     GT_ModHandler.addCraftingRecipe(ItemList.Emitter_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSC", "WQS", "CWS", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.EnderPearl), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Platinum), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Elite), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Aluminium) });
/* 610:578 */     GT_ModHandler.addCraftingRecipe(ItemList.Emitter_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "SSC", "WQS", "CWS", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.EnderEye), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Osmium), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Master), Character.valueOf('W'), OrePrefixes.cableGt01.get(Materials.Tungsten) });
/* 611:    */     
/* 612:580 */     ItemList.Sensor_LV.set(addItem(tLastID = 690, "Sensor (LV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L) }));
/* 613:581 */     ItemList.Sensor_MV.set(addItem(tLastID = 691, "Sensor (MV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L) }));
/* 614:582 */     ItemList.Sensor_HV.set(addItem(tLastID = 692, "Sensor (HV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L) }));
/* 615:583 */     ItemList.Sensor_EV.set(addItem(tLastID = 693, "Sensor (EV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 8L) }));
/* 616:584 */     ItemList.Sensor_IV.set(addItem(tLastID = 694, "Sensor (IV)", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 16L) }));
/* 617:585 */     ItemList.Sensor_LuV.set(ItemList.Sensor_IV.get(1L, new Object[0]));
/* 618:586 */     ItemList.Sensor_ZPM.set(ItemList.Sensor_LuV.get(1L, new Object[0]));
/* 619:587 */     ItemList.Sensor_UV.set(ItemList.Sensor_ZPM.get(1L, new Object[0]));
/* 620:    */     
/* 621:589 */     GT_ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P Q", "PS ", "CPP", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.Quartzite), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Brass), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Steel), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Basic) });
/* 622:590 */     GT_ModHandler.addCraftingRecipe(ItemList.Sensor_MV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P Q", "PS ", "CPP", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.NetherQuartz), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Electrum), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Aluminium), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Good) });
/* 623:591 */     GT_ModHandler.addCraftingRecipe(ItemList.Sensor_HV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P Q", "PS ", "CPP", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.Emerald), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Chrome), Character.valueOf('P'), OrePrefixes.plate.get(Materials.StainlessSteel), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Advanced) });
/* 624:592 */     GT_ModHandler.addCraftingRecipe(ItemList.Sensor_EV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P Q", "PS ", "CPP", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.EnderPearl), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Platinum), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Titanium), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Elite) });
/* 625:593 */     GT_ModHandler.addCraftingRecipe(ItemList.Sensor_IV.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "P Q", "PS ", "CPP", Character.valueOf('Q'), OrePrefixes.gem.get(Materials.EnderEye), Character.valueOf('S'), OrePrefixes.stick.get(Materials.Osmium), Character.valueOf('P'), OrePrefixes.plate.get(Materials.TungstenSteel), Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Master) });
/* 626:    */     
/* 627:595 */     ItemList.Circuit_Primitive.set(addItem(tLastID = 700, "NAND Chip", "A very simple Circuit", new Object[] { OrePrefixes.circuit.get(Materials.Primitive) }));
/* 628:596 */     ItemList.Circuit_Basic.set(addItem(tLastID = 701, "Basic Electronic Circuit", "A basic Circuit", new Object[] { OrePrefixes.circuit.get(Materials.Basic) }));
/* 629:597 */     ItemList.Circuit_Good.set(addItem(tLastID = 702, "Good Electronic Circuit", "A good Circuit", new Object[] { OrePrefixes.circuit.get(Materials.Good) }));
/* 630:598 */     ItemList.Circuit_Advanced.set(addItem(tLastID = 703, "Advanced Circuit", "An advanced Circuit", new Object[] { OrePrefixes.circuit.get(Materials.Advanced) }));
/* 631:599 */     ItemList.Circuit_Data.set(addItem(tLastID = 704, "Data Storage Circuit", "A Data Storage Chip", new Object[] { OrePrefixes.circuit.get(Materials.Data) }));
/* 632:600 */     ItemList.Circuit_Elite.set(addItem(tLastID = 705, "Data Control Circuit", "A Processor", new Object[] { OrePrefixes.circuit.get(Materials.Elite) }));
/* 633:601 */     ItemList.Circuit_Master.set(addItem(tLastID = 706, "Energy Flow Circuit", "A High Voltage Processor", new Object[] { OrePrefixes.circuit.get(Materials.Master) }));
/* 634:602 */     ItemList.Tool_DataOrb.set(addItem(tLastID = 707, "Data Orb", "A High Capacity Data Storage", new Object[] { OrePrefixes.circuit.get(Materials.Ultimate), SubTag.NO_UNIFICATION, new Behaviour_DataOrb() }));ItemList.Circuit_Ultimate.set(ItemList.Tool_DataOrb.get(1L, new Object[0]));
/* 635:603 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataOrb.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Tool_DataOrb.get(1L, new Object[0]) });
/* 636:604 */     ItemList.Tool_DataStick.set(addItem(tLastID = 708, "Data Stick", "A Low Capacity Data Storage", new Object[] { OrePrefixes.circuit.get(Materials.Data), SubTag.NO_UNIFICATION, new Behaviour_DataStick() }));
/* 637:605 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataStick.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { ItemList.Tool_DataStick.get(1L, new Object[0]) });
/* 638:    */     

/* 639:607 */     ItemList.Circuit_Board_Basic.set(addItem(tLastID = 710, "Basic Circuit Board", "A basic Board", new Object[0]));
/* 640:608 */     ItemList.Circuit_Board_Advanced.set(addItem(tLastID = 711, "Advanced Circuit Board", "An advanced Board", new Object[0]));
/* 641:609 */     ItemList.Circuit_Board_Elite.set(addItem(tLastID = 712, "Processor Board", "A Processor Board", new Object[0]));
/* 642:610 */     ItemList.Circuit_Parts_Crystal_Chip_Elite.set(addItem(tLastID = 713, "Engraved Crystal Chip", "Needed for Circuits", new Object[0]));
/* 643:611 */     ItemList.Circuit_Parts_Crystal_Chip_Master.set(addItem(tLastID = 714, "Engraved Lapotron Chip", "Needed for Circuits", new Object[0]));
/* 644:612 */     ItemList.Circuit_Parts_Advanced.set(addItem(tLastID = 715, "Advanced Circuit Parts", "Advanced Circuit Parts", new Object[0]));
/* 645:613 */     ItemList.Circuit_Parts_Wiring_Basic.set(addItem(tLastID = 716, "Etched Medium Voltage Wiring", "Part of Circuit Boards", new Object[0]));
/* 646:614 */     ItemList.Circuit_Parts_Wiring_Advanced.set(addItem(tLastID = 717, "Etched High Voltage Wiring", "Part of Circuit Boards", new Object[0]));
/* 647:615 */     ItemList.Circuit_Parts_Wiring_Elite.set(addItem(tLastID = 718, "Etched Extreme Voltage Wiring", "Part of Circuit Boards", new Object[0]));
/* 648:    */     
/* 649:    */ 
/* 650:618 */     ItemList.Component_Sawblade_Diamond.set(addItem(tLastID = 721, "Diamond Sawblade", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L), OreDictNames.craftingDiamondBlade }));
/* 651:619 */     ItemList.Component_Grinder_Diamond.set(addItem(tLastID = 722, "Diamond Grinding Head", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 6L), OreDictNames.craftingGrinder }));
/* 652:620 */     ItemList.Component_Grinder_Tungsten.set(addItem(tLastID = 723, "Tungsten Grinding Head", "", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 6L), OreDictNames.craftingGrinder }));
/* 653:    */     
/* 654:622 */     GT_ModHandler.addCraftingRecipe(ItemList.Component_Sawblade_Diamond.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { " D ", "DGD", " D ", Character.valueOf('D'), OrePrefixes.dustSmall.get(Materials.Diamond), Character.valueOf('G'), OrePrefixes.gearGt.get(Materials.CobaltBrass) });
/* 655:623 */     GT_ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Diamond.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "DSD", "SIS", "DSD", Character.valueOf('I'), OreDictNames.craftingIndustrialDiamond, Character.valueOf('D'), OrePrefixes.dust.get(Materials.Diamond), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel) });
/* 656:624 */     GT_ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Tungsten.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "TST", "SIS", "TST", Character.valueOf('I'), OreDictNames.craftingIndustrialDiamond, Character.valueOf('T'), OrePrefixes.plate.get(Materials.Tungsten), Character.valueOf('S'), OrePrefixes.plate.get(Materials.Steel) });
/* 657:    */     
/* 658:626 */     ItemList.Upgrade_Muffler.set(addItem(tLastID = 727, "Muffler Upgrade", "Makes Machines silent", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L) }));
/* 659:627 */     ItemList.Upgrade_Lock.set(addItem(tLastID = 728, "Lock Upgrade", "Protects your Machines", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L) }));
/* 660:    */     
/* 661:629 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 662:630 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 663:631 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 664:632 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 665:633 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 666:634 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L, new Object[0]), 1600, 2);
/* 667:635 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L, new Object[0]), 6400, 16);
/* 668:636 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L, new Object[0]), 6400, 16);
/* 669:637 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L, new Object[0]), 6400, 16);
/* 670:    */     
/* 671:639 */     ItemList.Component_Filter.set(addItem(tLastID = 729, "Item Filter", "", new Object[] { new ItemData(Materials.Zinc, OrePrefixes.foil.mMaterialAmount * 16L, new MaterialStack[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L), OreDictNames.craftingFilter }));
/* 672:    */     
/* 673:641 */     GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item("carbonMesh", 4L), GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Zinc, 16L), ItemList.Component_Filter.get(1L, new Object[0]), 1600, 32);
/* 674:    */     
/* 675:643 */     ItemList.Cover_Controller.set(addItem(tLastID = 730, "Machine Controller", "Turns Machines ON/OFF", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L) }));
/* 676:644 */     ItemList.Cover_ActivityDetector.set(addItem(tLastID = 731, "Activity Detector", "Gives out Activity as Redstone", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L) }));
/* 677:645 */     ItemList.Cover_FluidDetector.set(addItem(tLastID = 732, "Fluid Detector", "Gives out Fluid Amount as Redstone", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L) }));
/* 678:646 */     ItemList.Cover_ItemDetector.set(addItem(tLastID = 733, "Item Detector", "Gives out Item Amount as Redstone", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TERRA, 1L) }));
/* 679:647 */     ItemList.Cover_EnergyDetector.set(addItem(tLastID = 734, "Energy Detector", "Gives out Energy Amount as Redstone", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L) }));
/* 680:    */     
/* 681:649 */     GregTech_API.registerCover(ItemList.Cover_Controller.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONTROLLER) }), new GT_Cover_ControlsWork());
/* 682:650 */     GregTech_API.registerCover(ItemList.Cover_ActivityDetector.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR) }), new GT_Cover_DoesWork());
/* 683:651 */     GregTech_API.registerCover(ItemList.Cover_FluidDetector.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR) }), new GT_Cover_LiquidMeter());
/* 684:652 */     GregTech_API.registerCover(ItemList.Cover_ItemDetector.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ITEMDETECTOR) }), new GT_Cover_ItemMeter());
/* 685:653 */     GregTech_API.registerCover(ItemList.Cover_EnergyDetector.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ENERGYDETECTOR) }), new GT_Cover_EUMeter());
/* 686:    */     
/* 687:655 */     ItemList.Cover_Screen.set(addItem(tLastID = 740, "Computer Monitor", "Displays Data", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L) }));
/* 688:656 */     ItemList.Cover_Crafting.set(addItem(tLastID = 744, "Crafting Table Cover", "Better than a wooden Workbench", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 4L) }));
/* 689:657 */     ItemList.Cover_Drain.set(addItem(tLastID = 745, "Drain", "Absorbs Fluids and collects Rain", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L) }));
/* 690:    */     
/* 691:659 */     ItemList.Cover_Shutter.set(addItem(tLastID = 749, "Shutter Module", "Blocks Inventory/Tank Side. Usage together with Machine Controller.", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L) }));
/* 692:    */     
/* 693:661 */     GT_ModHandler.addCraftingRecipe(ItemList.Cover_Screen.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "AGA", "RPB", "ALA", Character.valueOf('A'), OrePrefixes.plate.get(Materials.Aluminium), Character.valueOf('L'), OrePrefixes.dust.get(Materials.Glowstone), Character.valueOf('R'), Dyes.dyeRed, Character.valueOf('G'), Dyes.dyeLime, Character.valueOf('B'), Dyes.dyeBlue, Character.valueOf('P'), OrePrefixes.plate.get(Materials.Glass) });
/* 694:    */     
/* 695:663 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L), new ItemStack(Items.iron_door, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);
/* 696:664 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L), new ItemStack(Items.iron_door, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);
/* 697:665 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 2L), new ItemStack(Items.iron_door, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);
/* 698:666 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 2L), new ItemStack(Blocks.iron_bars, 2), ItemList.Cover_Drain.get(1L, new Object[0]), 800, 16);
/* 699:667 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 2L), new ItemStack(Blocks.iron_bars, 2), ItemList.Cover_Drain.get(1L, new Object[0]), 800, 16);
/* 700:668 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 2L), new ItemStack(Blocks.iron_bars, 2), ItemList.Cover_Drain.get(1L, new Object[0]), 800, 16);
/* 701:669 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 1L), new ItemStack(Blocks.crafting_table, 1), ItemList.Cover_Crafting.get(1L, new Object[0]), 800, 16);
/* 702:670 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L), new ItemStack(Blocks.crafting_table, 1), ItemList.Cover_Crafting.get(1L, new Object[0]), 800, 16);
/* 703:671 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1L), new ItemStack(Blocks.crafting_table, 1), ItemList.Cover_Crafting.get(1L, new Object[0]), 800, 16);
/* 704:    */     
/* 705:673 */     GregTech_API.registerCover(ItemList.Cover_Screen.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SCREEN) }), new GT_Cover_Screen());
/* 706:674 */     GregTech_API.registerCover(ItemList.Cover_Crafting.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CRAFTING) }), new GT_Cover_Crafting());
/* 707:675 */     GregTech_API.registerCover(ItemList.Cover_Drain.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[0][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_DRAIN) }), new GT_Cover_Drain());
/* 708:676 */     GregTech_API.registerCover(ItemList.Cover_Shutter.get(1L, new Object[0]), new GT_MultiTexture(new ITexture[] { Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SHUTTER) }), new GT_Cover_Shutter());
/* 709:    */     
/* 710:678 */     ItemList.Cover_SolarPanel.set(addItem(tLastID = 750, "Solar Panel", "May the Sun be with you", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 1L) }));
/* 711:679 */     ItemList.Cover_SolarPanel_8V.set(addItem(tLastID = 751, "Solar Panel (8V)", "8 Volt Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 2L) }));
/* 712:680 */     ItemList.Cover_SolarPanel_LV.set(addItem(tLastID = 752, "Solar Panel (LV)", "Low Voltage Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 4L) }));
/* 713:681 */     ItemList.Cover_SolarPanel_MV.set(addItem(tLastID = 753, "Solar Panel (MV)", "Medium Voltage Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 8L) }));
/* 714:682 */     ItemList.Cover_SolarPanel_HV.set(addItem(tLastID = 754, "Solar Panel (HV)", "High Voltage Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 16L) }));
/* 715:683 */     ItemList.Cover_SolarPanel_EV.set(addItem(tLastID = 755, "Solar Panel (EV)", "Extreme Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 32L) }));
/* 716:684 */     ItemList.Cover_SolarPanel_IV.set(addItem(tLastID = 756, "Solar Panel (IV)", "Insane Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L) }));
/* 717:685 */     ItemList.Cover_SolarPanel_LuV.set(addItem(tLastID = 757, "Solar Panel (LuV)", "Ludicrous Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L) }));
/* 718:686 */     ItemList.Cover_SolarPanel_ZPM.set(addItem(tLastID = 758, "Solar Panel (ZPM)", "ZPM Voltage Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L) }));
/* 719:687 */     ItemList.Cover_SolarPanel_UV.set(addItem(tLastID = 759, "Solar Panel (UV)", "Ultimate Solar Panel", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L) }));
/* 720:    */     
/* 721:689 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL), new GT_Cover_SolarPanel(1));
/* 722:690 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_8V.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_8V), new GT_Cover_SolarPanel(8));
/* 723:691 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_LV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_LV), new GT_Cover_SolarPanel(32));
/* 724:692 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_MV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_MV), new GT_Cover_SolarPanel(128));
/* 725:693 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_HV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_HV), new GT_Cover_SolarPanel(512));
/* 726:694 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_EV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_EV), new GT_Cover_SolarPanel(2048));
/* 727:695 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_IV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_IV), new GT_Cover_SolarPanel(8192));
/* 728:696 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_LuV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_LuV), new GT_Cover_SolarPanel(32768));
/* 729:697 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_ZPM.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_ZPM), new GT_Cover_SolarPanel(131072));
/* 730:698 */     GregTech_API.registerCover(ItemList.Cover_SolarPanel_UV.get(1L, new Object[0]), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_UV), new GT_Cover_SolarPanel(524288));
/* 731:    */     
/* 732:700 */     ItemList.Tool_Sonictron.set(addItem(tLastID = 760, "Sonictron", "Bring your Music with you", new Object[] { Behaviour_Sonictron.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L) }));
/* 733:701 */     ItemList.Tool_Cheat.set(addItem(tLastID = 761, "Debug Scanner", "Also an Infinite Energy Source", new Object[] { Behaviour_Scanner.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 64L) }));
/* 734:702 */     setElectricStats(32000 + tLastID, -2000000000L, 1000000000L, -1L, -3L, false);
/* 735:703 */     ItemList.Tool_Scanner.set(addItem(tLastID = 762, "Portable Scanner", "Tricorder", new Object[] { Behaviour_Scanner.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 6L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 6L) }));
/* 736:704 */     setElectricStats(32000 + tLastID, 400000L, GT_Values.V[2], 2L, -1L, false);
/* 737:705 */     GT_ModHandler.addCraftingRecipe(ItemList.Tool_Scanner.get(1L, new Object[0]), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[] { "EPR", "CSC", "PBP", Character.valueOf('C'), OrePrefixes.circuit.get(Materials.Advanced), Character.valueOf('P'), OrePrefixes.plate.get(Materials.Aluminium), Character.valueOf('E'), ItemList.Emitter_MV, Character.valueOf('R'), ItemList.Sensor_MV, Character.valueOf('S'), ItemList.Cover_Screen, Character.valueOf('B'), ItemList.Battery_RE_MV_Lithium });
/* 738:706 */     ItemList.NC_SensorKit.set(addItem(tLastID = 763, "GregTech Sensor Kit", "", new Object[] { new Behaviour_SensorKit() }));
/* 739:707 */     ItemList.Duct_Tape.set(addItem(tLastID = 764, "BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84", "If you can't fix it with this, use more of it!", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), OreDictNames.craftingDuctTape }));
/* 740:708 */     ItemList.McGuffium_239.set(addItem(tLastID = 765, "Mc Guffium 239", "42% better than Phlebotnium", new Object[] { new TC_Aspects.TC_AspectStack(TC_Aspects.ALIENIS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.SPIRITUS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITIUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.RADIO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MAGNETO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 8L) }));
/* 741:    */     
/* 742:710 */     GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4L), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 2L), ItemList.Schematic.get(1L, new Object[0]), 3200, 4);
/* 743:711 */     GT_Values.RA.addAssemblerRecipe(ItemList.Sensor_LV.get(1L, new Object[0]), ItemList.Emitter_LV.get(1L, new Object[0]), ItemList.NC_SensorKit.get(1L, new Object[0]), 1600, 2);
/* 744:    */   }
/* 745:    */   
/* 746:    */   public boolean onEntityItemUpdate(EntityItem aItemEntity)
/* 747:    */   {
/* 748:716 */     int aDamage = aItemEntity.getEntityItem().getItemDamage();
/* 749:717 */     if ((aDamage < 32000) && (aDamage >= 0) && (!aItemEntity.worldObj.isRemote))
/* 750:    */     {
/* 751:718 */       Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
/* 752:719 */       if ((aMaterial != null) && (aMaterial != Materials.Empty) && (aMaterial != Materials._NULL))
/* 753:    */       {
/* 754:720 */         int tX = MathHelper.floor_double(aItemEntity.posX);int tY = MathHelper.floor_double(aItemEntity.posY);int tZ = MathHelper.floor_double(aItemEntity.posZ);
/* 755:721 */         OrePrefixes aPrefix = this.mGeneratedPrefixList[(aDamage / 1000)];
/* 756:722 */         if ((aPrefix == OrePrefixes.dustImpure) || (aPrefix == OrePrefixes.dustPure))
/* 757:    */         {
/* 758:723 */           Block tBlock = aItemEntity.worldObj.getBlock(tX, tY, tZ);
/* 759:724 */           byte tMetaData = (byte)aItemEntity.worldObj.getBlockMetadata(tX, tY, tZ);
/* 760:725 */           if ((tBlock == Blocks.cauldron) && (tMetaData > 0))
/* 761:    */           {
/* 762:726 */             aItemEntity.setEntityItemStack(GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, aItemEntity.getEntityItem().stackSize));
/* 763:727 */             aItemEntity.worldObj.setBlockMetadataWithNotify(tX, tY, tZ, tMetaData - 1, 3);
/* 764:728 */             return true;
/* 765:    */           }
/* 766:    */         }
/* 767:731 */         if (aPrefix == OrePrefixes.crushed)
/* 768:    */         {
/* 769:732 */           Block tBlock = aItemEntity.worldObj.getBlock(tX, tY, tZ);
/* 770:733 */           byte tMetaData = (byte)aItemEntity.worldObj.getBlockMetadata(tX, tY, tZ);
/* 771:734 */           if ((tBlock == Blocks.cauldron) && (tMetaData > 0))
/* 772:    */           {
/* 773:735 */             aItemEntity.setEntityItemStack(GT_OreDictUnificator.get(OrePrefixes.crushedPurified, aMaterial, aItemEntity.getEntityItem().stackSize));
/* 774:736 */             aItemEntity.worldObj.setBlockMetadataWithNotify(tX, tY, tZ, tMetaData - 1, 3);
/* 775:737 */             return true;
/* 776:    */           }
/* 777:    */         }
/* 778:    */       }
/* 779:    */     }
/* 780:742 */     return false;
/* 781:    */   }
/* 782:    */   
/* 783:    */   protected void addAdditionalToolTips(List aList, ItemStack aStack)
/* 784:    */   {
/* 785:747 */     super.addAdditionalToolTips(aList, aStack);
/* 786:748 */     int aDamage = aStack.getItemDamage();
/* 787:749 */     if ((aDamage < 32000) && (aDamage >= 0))
/* 788:    */     {
/* 789:750 */       Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
/* 790:751 */       if ((aMaterial != null) && (aMaterial != Materials.Empty) && (aMaterial != Materials._NULL))
/* 791:    */       {
/* 792:752 */         OrePrefixes aPrefix = this.mGeneratedPrefixList[(aDamage / 1000)];
/* 793:753 */         if ((aPrefix == OrePrefixes.dustImpure) || (aPrefix == OrePrefixes.dustPure)) {
/* 794:754 */           aList.add(this.mToolTipPurify);
/* 795:    */         }
/* 796:    */       }
/* 797:    */     }
/* 798:    */   }
/* 799:    */   
/* 800:    */   public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems)
/* 801:    */   {
/* 802:762 */     return (aDoShowAllItems) || (((aPrefix != OrePrefixes.gem) || (!aMaterial.name().startsWith("Infused"))) && (aPrefix != OrePrefixes.nugget) && (aPrefix != OrePrefixes.dustTiny) && (aPrefix != OrePrefixes.dustSmall) && (aPrefix != OrePrefixes.dustImpure) && (aPrefix != OrePrefixes.dustPure) && (aPrefix != OrePrefixes.crushed) && (aPrefix != OrePrefixes.crushedPurified) && (aPrefix != OrePrefixes.crushedCentrifuged) && (aPrefix != OrePrefixes.ingotHot) && (aPrefix != OrePrefixes.cellPlasma));
/* 803:    */   }
/* 804:    */   
/* 805:    */   public ItemStack getContainerItem(ItemStack aStack)
/* 806:    */   {
/* 807:767 */     int aDamage = aStack.getItemDamage();
/* 808:768 */     if ((aDamage >= 32430) && (aDamage <= 32461)) {
/* 809:768 */       return ItemList.Spray_Empty.get(1L, new Object[0]);
/* 810:    */     }
/* 811:769 */     if ((aDamage == 32479) || (aDamage == 32476)) {
/* 812:769 */       return new ItemStack(this, 1, aDamage - 2);
/* 813:    */     }
/* 814:770 */     if (aDamage == 32401) {
/* 815:770 */       return new ItemStack(this, 1, aDamage - 1);
/* 816:    */     }
/* 817:771 */     return super.getContainerItem(aStack);
/* 818:    */   }
/* 819:    */   
/* 820:    */   public boolean doesMaterialAllowGeneration(OrePrefixes aPrefix, Materials aMaterial)
/* 821:    */   {
/* 822:776 */     return (super.doesMaterialAllowGeneration(aPrefix, aMaterial)) && ((aPrefix != OrePrefixes.ingotHot) || (aMaterial.mBlastFurnaceTemp > 1750));
/* 823:    */   }
/* 824:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_MetaGenerated_Item_01
 * JD-Core Version:    0.7.0.1
 */