/*   1:    */ package gregtech.loaders.postload;
/*   2:    */ 
/*   3:    */ import gregtech.GT_Mod;
/*   4:    */ import gregtech.api.enums.ItemList;
/*   5:    */ import gregtech.api.enums.Materials;
/*   6:    */ import gregtech.api.enums.OrePrefixes;
/*   7:    */ import gregtech.api.util.GT_Log;
/*   8:    */ import gregtech.api.util.GT_OreDictUnificator;
/*   9:    */ import gregtech.common.GT_Proxy;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.WeightedRandomChestContent;
/*  14:    */ import net.minecraftforge.common.ChestGenHooks;
/*  15:    */ 
/*  16:    */ public class GT_BookAndLootLoader
/*  17:    */   implements Runnable
/*  18:    */ {
/*  19:    */   public void run()
/*  20:    */   {
/*  21: 18 */     GT_Log.out.println("GT_Mod: Adding worldgenerated Chest Content.");
/*  22: 20 */     if (GT_Mod.gregtechproxy.mIncreaseDungeonLoot)
/*  23:    */     {
/*  24: 22 */       ChestGenHooks tChest = ChestGenHooks.getInfo("bonusChest");tChest.setMax(tChest.getMax() + 8);tChest.setMin(tChest.getMin() + 4);
/*  25: 23 */       tChest = ChestGenHooks.getInfo("dungeonChest");tChest.setMax(tChest.getMax() + 6);tChest.setMin(tChest.getMin() + 3);
/*  26: 24 */       tChest = ChestGenHooks.getInfo("pyramidDesertyChest");tChest.setMax(tChest.getMax() + 8);tChest.setMin(tChest.getMin() + 4);
/*  27: 25 */       tChest = ChestGenHooks.getInfo("pyramidJungleChest");tChest.setMax(tChest.getMax() + 16);tChest.setMin(tChest.getMin() + 8);
/*  28: 26 */       tChest = ChestGenHooks.getInfo("pyramidJungleDispenser");tChest.setMax(tChest.getMax() + 2);tChest.setMin(tChest.getMin() + 1);
/*  29: 27 */       tChest = ChestGenHooks.getInfo("mineshaftCorridor");tChest.setMax(tChest.getMax() + 4);tChest.setMin(tChest.getMin() + 2);
/*  30: 28 */       tChest = ChestGenHooks.getInfo("villageBlacksmith");tChest.setMax(tChest.getMax() + 12);tChest.setMin(tChest.getMin() + 6);
/*  31: 29 */       tChest = ChestGenHooks.getInfo("strongholdCrossing");tChest.setMax(tChest.getMax() + 8);tChest.setMin(tChest.getMin() + 4);
/*  32: 30 */       tChest = ChestGenHooks.getInfo("strongholdCorridor");tChest.setMax(tChest.getMax() + 6);tChest.setMin(tChest.getMin() + 3);
/*  33: 31 */       tChest = ChestGenHooks.getInfo("strongholdLibrary");tChest.setMax(tChest.getMax() + 16);tChest.setMin(tChest.getMin() + 8);
/*  34:    */     }
/*  35:396 */     ChestGenHooks.addItem("bonusChest", new WeightedRandomChestContent(ItemList.Bottle_Purple_Drink.get(1L, new Object[0]), 8, 16, 2));
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39:400 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 20));
/*  40:401 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(ItemList.Bottle_Purple_Drink.get(1L, new Object[0]), 8, 16, 80));
/*  41:402 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 6, 120));
/*  42:403 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 6, 30));
/*  43:404 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 6, 60));
/*  44:405 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 6, 60));
/*  45:406 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 1, 6, 60));
/*  46:407 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 1, 6, 10));
/*  47:408 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 6, 20));
/*  48:409 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 6, 20));
/*  49:410 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 6, 20));
/*  50:411 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 6, 20));
/*  51:412 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 6, 20));
/*  52:413 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 6, 40));
/*  53:414 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 6, 40));
/*  54:415 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 1, 6, 40));
/*  55:416 */     ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 3, 40));
/*  56:    */     
/*  57:418 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 2));
/*  58:419 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 4, 16, 12));
/*  59:420 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Platinum, 1L), 2, 8, 4));
/*  60:421 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 2));
/*  61:422 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 2));
/*  62:423 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 2));
/*  63:424 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 2));
/*  64:425 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 4));
/*  65:426 */     ChestGenHooks.addItem("pyramidDesertyChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 4));
/*  66:    */     
/*  67:428 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(ItemList.Coin_Gold_Ancient.get(1L, new Object[0]), 16, 64, 10));
/*  68:429 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(ItemList.ZPM.getWithCharge(1L, 2147483647, new Object[0]), 1, 1, 1));
/*  69:430 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 16, 12));
/*  70:431 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 2, 8, 2));
/*  71:432 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 2, 8, 2));
/*  72:433 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 2, 8, 2));
/*  73:434 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 2, 8, 2));
/*  74:435 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 2, 8, 4));
/*  75:436 */     ChestGenHooks.addItem("pyramidJungleChest", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 2, 8, 4));
/*  76:    */     
/*  77:438 */     ChestGenHooks.addItem("pyramidJungleDispenser", new WeightedRandomChestContent(new ItemStack(Items.fire_charge, 1), 2, 8, 30));
/*  78:439 */     ChestGenHooks.addItem("pyramidJungleDispenser", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 8, 16, 20));
/*  79:    */     
/*  80:    */ 
/*  81:442 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Silver, 1L), 1, 4, 12));
/*  82:443 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Lead, 1L), 1, 4, 3));
/*  83:444 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 1, 4, 6));
/*  84:445 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 1, 4, 6));
/*  85:446 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L), 1, 4, 2));
/*  86:447 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GreenSapphire, 1L), 1, 4, 2));
/*  87:448 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L), 1, 4, 2));
/*  88:449 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetRed, 1L), 1, 4, 4));
/*  89:450 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.GarnetYellow, 1L), 1, 4, 4));
/*  90:451 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Ruby, 1L), 1, 4, 2));
/*  91:452 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Emerald, 1L), 1, 4, 2));
/*  92:453 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadPickaxe, Materials.DamascusSteel, 1L), 1, 4, 1));
/*  93:454 */     ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadShovel, Materials.DamascusSteel, 1L), 1, 4, 1));
/*  94:    */     
/*  95:456 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(ItemList.McGuffium_239.get(1L, new Object[0]), 1, 1, 1));
/*  96:457 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Chrome, 1L), 1, 4, 6));
/*  97:458 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Neodymium, 1L), 2, 8, 6));
/*  98:459 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Manganese, 1L), 2, 8, 12));
/*  99:460 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), 4, 12, 12));
/* 100:461 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), 4, 12, 12));
/* 101:462 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Brass, 1L), 4, 12, 12));
/* 102:463 */     ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.DamascusSteel, 1L), 4, 12, 1));
/* 103:    */     
/* 104:465 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(ItemList.Bottle_Holy_Water.get(1L, new Object[0]), 4, 8, 6));
/* 105:466 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(ItemList.McGuffium_239.get(1L, new Object[0]), 1, 1, 10));
/* 106:    */     
/* 107:468 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.DamascusSteel, 1L), 4, 8, 6));
/* 108:469 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Steel, 1L), 8, 16, 12));
/* 109:470 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Bronze, 1L), 8, 16, 12));
/* 110:471 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtIngot, Materials.Manganese, 1L), 4, 8, 12));
/* 111:472 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Neodymium, 1L), 4, 8, 6));
/* 112:473 */     ChestGenHooks.addItem("strongholdCrossing", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.crateGtDust, Materials.Chrome, 1L), 2, 4, 6));
/* 113:    */     
/* 114:475 */     ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadSword, Materials.DamascusSteel, 1L), 1, 4, 6));
/* 115:476 */     ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.toolHeadAxe, Materials.DamascusSteel, 1L), 1, 4, 6));
/* 116:477 */     ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.DamascusSteel, 1L), 16, 48, 6));
/* 117:478 */     ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(GT_OreDictUnificator.get(OrePrefixes.arrowGtWood, Materials.SterlingSilver, 1L), 8, 24, 6));
/* 118:    */   }
/* 119:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.postload.GT_BookAndLootLoader
 * JD-Core Version:    0.7.0.1
 */