/*   1:    */ package gregtech.common.items;
/*   2:    */ 
/*   3:    */ import cpw.mods.fml.relauncher.Side;
/*   4:    */ import cpw.mods.fml.relauncher.SideOnly;
/*   5:    */ import gregtech.api.GregTech_API;
/*   6:    */ import gregtech.api.enums.ItemList;
/*   7:    */ import gregtech.api.enums.Materials;
/*   8:    */ import gregtech.api.enums.OrePrefixes;
/*   9:    */ import gregtech.api.items.GT_Generic_Item;
/*  10:    */ import gregtech.api.util.GT_LanguageManager;
/*  11:    */ import gregtech.api.util.GT_Log;
/*  12:    */ import gregtech.api.util.GT_ModHandler;
/*  13:    */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.util.List;
/*  16:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  17:    */ import net.minecraft.creativetab.CreativeTabs;
/*  18:    */ import net.minecraft.item.Item;
/*  19:    */ import net.minecraft.item.ItemStack;
/*  20:    */ 
/*  21:    */ public class GT_IntegratedCircuit_Item
/*  22:    */   extends GT_Generic_Item
/*  23:    */ {
/*  24:    */   public GT_IntegratedCircuit_Item()
/*  25:    */   {
/*  26: 23 */     super("integrated_circuit", "Integrated Circuit", "");
/*  27: 24 */     setHasSubtypes(true);
/*  28: 25 */     setMaxDamage(0);
/*  29:    */     
/*  30: 27 */     ItemList.Circuit_Integrated.set(this);
/*  31:    */     
/*  32:    */ 
/*  33: 30 */     GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 0L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { OrePrefixes.circuit.get(Materials.Basic) });
/*  34:    */     
/*  35: 32 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "d  ", " P ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  36: 33 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " d ", " P ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  37: 34 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 3L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  d", " P ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  38: 35 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", " Pd", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  39: 36 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 5L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", " P ", "  d", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  40: 37 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 6L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", " P ", " d ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  41: 38 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 7L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", " P ", "d  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  42: 39 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 8L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", "dP ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  43:    */     
/*  44: 41 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 9L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "P d", "   ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  45: 42 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 10L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "P  ", "  d", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  46: 43 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 11L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "P  ", "   ", "  d", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  47: 44 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 12L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "P  ", "   ", " d ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  48: 45 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 13L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  P", "   ", "  d", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  49: 46 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 14L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  P", "   ", " d ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  50: 47 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 15L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  P", "   ", "d  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  51: 48 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 16L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  P", "d  ", "   ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  52: 49 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 17L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", "   ", "d P", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  53: 50 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 18L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", "d  ", "  P", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  54: 51 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 19L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "d  ", "   ", "  P", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  55: 52 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 20L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " d ", "   ", "  P", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  56: 53 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 21L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "d  ", "   ", "P  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  57: 54 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 22L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { " d ", "   ", "P  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  58: 55 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 23L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "  d", "   ", "P  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  59: 56 */     GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 24L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[] { "   ", "  d", "P  ", Character.valueOf('P'), ItemList.Circuit_Integrated.getWildcard(1L, new Object[0]) });
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void addAdditionalToolTips(List aList, ItemStack aStack)
/*  63:    */   {
/*  64: 61 */     super.addAdditionalToolTips(aList, aStack);
/*  65: 62 */     aList.add(GT_LanguageManager.addStringLocalization(new StringBuilder().append(getUnlocalizedName()).append(".configuration").toString(), "Configuration: ") + getConfigurationString(getDamage(aStack)));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getUnlocalizedName(ItemStack aStack)
/*  69:    */   {
/*  70: 67 */     return getUnlocalizedName();
/*  71:    */   }
/*  72:    */   
/*  73:    */   @SideOnly(Side.CLIENT)
/*  74:    */   public final void getSubItems(Item var1, CreativeTabs aCreativeTab, List aList)
/*  75:    */   {
/*  76: 73 */     aList.add(new ItemStack(this, 1, 0));
/*  77:    */   }
/*  78:    */   
/*  79:    */   @SideOnly(Side.CLIENT)
/*  80:    */   public void registerIcons(IIconRegister aIconRegister)
/*  81:    */   {
/*  82: 79 */     super.registerIcons(aIconRegister);
/*  83: 80 */     if (GregTech_API.sPostloadFinished)
/*  84:    */     {
/*  85: 81 */       GT_Log.out.println("GT_Mod: Starting Item Icon Load Phase");
/*  86: 82 */       System.out.println("GT_Mod: Starting Item Icon Load Phase");
/*  87: 83 */       GregTech_API.sItemIcons = aIconRegister;
/*  88: 84 */       for (Runnable tRunnable : GregTech_API.sGTItemIconload) {
/*  89:    */         try
/*  90:    */         {
/*  91: 86 */           tRunnable.run();
/*  92:    */         }
/*  93:    */         catch (Throwable e)
/*  94:    */         {
/*  95: 88 */           e.printStackTrace(GT_Log.err);
/*  96:    */         }
/*  97:    */       }
/*  98: 91 */       GT_Log.out.println("GT_Mod: Finished Item Icon Load Phase");
/*  99: 92 */       System.out.println("GT_Mod: Finished Item Icon Load Phase");
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static String getModeString(int aMetaData)
/* 104:    */   {
/* 105: 97 */     switch ((byte)(aMetaData >>> 8))
/* 106:    */     {
/* 107:    */     case 0: 
/* 108: 98 */       return "==";
/* 109:    */     case 1: 
/* 110: 99 */       return "<=";
/* 111:    */     case 2: 
/* 112:100 */       return ">=";
/* 113:    */     case 3: 
/* 114:101 */       return "<";
/* 115:    */     case 4: 
/* 116:102 */       return ">";
/* 117:    */     }
/* 118:103 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static String getConfigurationString(int aMetaData)
/* 122:    */   {
/* 123:108 */     return getModeString(aMetaData) + " " + (byte)(aMetaData & 0xFF);
/* 124:    */   }
/* 125:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.items.GT_IntegratedCircuit_Item
 * JD-Core Version:    0.7.0.1
 */