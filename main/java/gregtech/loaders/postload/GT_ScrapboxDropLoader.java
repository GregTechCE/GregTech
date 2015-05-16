/*  1:   */ package gregtech.loaders.postload;
/*  2:   */ 
/*  3:   */ import gregtech.GT_Mod;
import gregtech.api.enums.ItemList;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.util.GT_Log;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;

/*  9:   */ import java.io.PrintStream;

/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.init.Items;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ 
/* 14:   */ public class GT_ScrapboxDropLoader
/* 15:   */   implements Runnable
/* 16:   */ {
/* 17:   */   public void run()
/* 18:   */   {
/* 19:16 */     GT_Log.out.println("GT_Mod: (re-)adding Scrapbox Drops.");
/* 20:   */     
/* 21:18 */     GT_ModHandler.addScrapboxDrop(9.5F, new ItemStack(Items.wooden_hoe));
/* 22:19 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_axe));
/* 23:20 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_sword));
/* 24:21 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_shovel));
/* 25:22 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.wooden_pickaxe));
/* 26:23 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Items.sign));
/* 27:24 */     GT_ModHandler.addScrapboxDrop(9.5F, new ItemStack(Items.stick));
/* 28:25 */     GT_ModHandler.addScrapboxDrop(5.0F, new ItemStack(Blocks.dirt));
/* 29:26 */     GT_ModHandler.addScrapboxDrop(3.0F, new ItemStack(Blocks.grass));
/* 30:27 */     GT_ModHandler.addScrapboxDrop(3.0F, new ItemStack(Blocks.gravel));
/* 31:28 */     GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Blocks.pumpkin));
/* 32:29 */     GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Blocks.soul_sand));
/* 33:30 */     GT_ModHandler.addScrapboxDrop(2.0F, new ItemStack(Blocks.netherrack));
/* 34:31 */     GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.bone));
/* 35:32 */     GT_ModHandler.addScrapboxDrop(9.0F, new ItemStack(Items.rotten_flesh));
/* 36:33 */     GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_porkchop));
/* 37:34 */     GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_beef));
/* 38:35 */     GT_ModHandler.addScrapboxDrop(0.4F, new ItemStack(Items.cooked_chicken));
/* 39:36 */     GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Items.apple));
/* 40:37 */     GT_ModHandler.addScrapboxDrop(0.5F, new ItemStack(Items.bread));
/* 41:38 */     GT_ModHandler.addScrapboxDrop(0.1F, new ItemStack(Items.cake));
/* 42:39 */     GT_ModHandler.addScrapboxDrop(1.0F, ItemList.IC2_Food_Can_Filled.get(1L, new Object[0]));
/* 43:40 */     GT_ModHandler.addScrapboxDrop(2.0F, ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]));
/* 44:41 */     GT_ModHandler.addScrapboxDrop(0.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Silicon, 1L));
/* 45:42 */     GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Water, 1L));
/* 46:43 */     GT_ModHandler.addScrapboxDrop(2.0F, ItemList.Cell_Empty.get(1L, new Object[0]));
/* 47:44 */     GT_ModHandler.addScrapboxDrop(5.0F, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L));
/* 48:45 */     GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.leather));
/* 49:46 */     GT_ModHandler.addScrapboxDrop(1.0F, new ItemStack(Items.feather));
/* 50:47 */     GT_ModHandler.addScrapboxDrop(0.7F, GT_ModHandler.getIC2Item("plantBall", 1L));
/* 51:48 */     GT_ModHandler.addScrapboxDrop(3.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L));
/* 52:49 */     GT_ModHandler.addScrapboxDrop(0.6F, new ItemStack(Items.slime_ball));
/* 53:50 */     GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Rubber, 1L));
/* 54:51 */     GT_ModHandler.addScrapboxDrop(2.7F, GT_ModHandler.getIC2Item("suBattery", 1L));
/* 55:52 */     GT_ModHandler.addScrapboxDrop(3.6F, ItemList.Circuit_Primitive.get(1L, new Object[0]));
/* 56:53 */     GT_ModHandler.addScrapboxDrop(0.8F, ItemList.Circuit_Parts_Advanced.get(1L, new Object[0]));
/* 57:54 */     GT_ModHandler.addScrapboxDrop(1.8F, ItemList.Circuit_Board_Basic.get(1L, new Object[0]));
/* 58:55 */     GT_ModHandler.addScrapboxDrop(0.4F, ItemList.Circuit_Board_Advanced.get(1L, new Object[0]));
/* 59:56 */     GT_ModHandler.addScrapboxDrop(0.2F, ItemList.Circuit_Board_Elite.get(1L, new Object[0]));
				if(!GT_Mod.gregtechproxy.mDisableIC2Cables){
/* 60:57 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1L));
/* 61:58 */     GT_ModHandler.addScrapboxDrop(0.4F, GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1L));}
/* 62:59 */     GT_ModHandler.addScrapboxDrop(0.9F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L));
/* 63:60 */     GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glowstone, 1L));
/* 64:61 */     GT_ModHandler.addScrapboxDrop(0.8F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L));
/* 65:62 */     GT_ModHandler.addScrapboxDrop(2.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Charcoal, 1L));
/* 66:63 */     GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1L));
/* 67:64 */     GT_ModHandler.addScrapboxDrop(1.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1L));
/* 68:65 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Silver, 1L));
/* 69:66 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 1L));
/* 70:67 */     GT_ModHandler.addScrapboxDrop(1.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tin, 1L));
/* 71:68 */     GT_ModHandler.addScrapboxDrop(1.2F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Copper, 1L));
/* 72:69 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bauxite, 1L));
/* 73:70 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Aluminium, 1L));
/* 74:71 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lead, 1L));
/* 75:72 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nickel, 1L));
/* 76:73 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Zinc, 1L));
/* 77:74 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 1L));
/* 78:75 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 1L));
/* 79:76 */     GT_ModHandler.addScrapboxDrop(1.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Obsidian, 1L));
/* 80:77 */     GT_ModHandler.addScrapboxDrop(1.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1L));
/* 81:78 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 1L));
/* 82:79 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lazurite, 1L));
/* 83:80 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Pyrite, 1L));
/* 84:81 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Calcite, 1L));
/* 85:82 */     GT_ModHandler.addScrapboxDrop(2.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sodalite, 1L));
/* 86:83 */     GT_ModHandler.addScrapboxDrop(4.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Netherrack, 1L));
/* 87:84 */     GT_ModHandler.addScrapboxDrop(4.0F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Flint, 1L));
/* 88:85 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Platinum, 1L));
/* 89:86 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Tungsten, 1L));
/* 90:87 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L));
/* 91:88 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Titanium, 1L));
/* 92:89 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Magnesium, 1L));
/* 93:90 */     GT_ModHandler.addScrapboxDrop(0.03F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Endstone, 1L));
/* 94:91 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GarnetRed, 1L));
/* 95:92 */     GT_ModHandler.addScrapboxDrop(0.5F, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.GarnetYellow, 1L));
/* 96:93 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L));
/* 97:94 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L));
/* 98:95 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L));
/* 99:96 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L));
/* :0:97 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L));
/* :1:98 */     GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Diamond, 1L));
/* :2:   */   }
/* :3:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_ScrapboxDropLoader
 * JD-Core Version:    0.7.0.1
 */