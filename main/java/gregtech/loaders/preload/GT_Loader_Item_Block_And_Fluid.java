/*   1:    */ package gregtech.loaders.preload;
/*   2:    */ 
/*   3:    */ import codechicken.nei.api.API;
/*   4:    */ import cpw.mods.fml.common.event.FMLInterModComms;
/*   5:    */ import cpw.mods.fml.common.registry.GameRegistry;
/*   6:    */ import gregtech.GT_Mod;
/*   7:    */ import gregtech.api.GregTech_API;
/*   8:    */ import gregtech.api.enums.Dyes;
/*   9:    */ import gregtech.api.enums.GT_Values;
/*  10:    */ import gregtech.api.enums.ItemList;
/*  11:    */ import gregtech.api.enums.Materials;
/*  12:    */ import gregtech.api.enums.OrePrefixes;
/*  13:    */ import gregtech.api.enums.SubTag;
/*  14:    */ import gregtech.api.items.GT_Generic_Item;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
/*  15:    */ import gregtech.api.metatileentity.BaseMetaPipeEntity;
/*  16:    */ import gregtech.api.metatileentity.BaseMetaTileEntity;
/*  17:    */ import gregtech.api.util.GT_Log;
/*  18:    */ import gregtech.api.util.GT_ModHandler;
/*  19:    */ import gregtech.api.util.GT_OreDictUnificator;
/*  20:    */ import gregtech.api.util.GT_Utility;
/*  21:    */ import gregtech.common.GT_Proxy;
/*  22:    */ import gregtech.common.blocks.GT_Block_Casings1;
/*  23:    */ import gregtech.common.blocks.GT_Block_Casings2;
/*  24:    */ import gregtech.common.blocks.GT_Block_Casings3;
/*  25:    */ import gregtech.common.blocks.GT_Block_Casings4;
/*  26:    */ import gregtech.common.blocks.GT_Block_Concretes;
/*  27:    */ import gregtech.common.blocks.GT_Block_Granites;
/*  28:    */ import gregtech.common.blocks.GT_Block_Machines;
/*  29:    */ import gregtech.common.blocks.GT_Block_Ores;
/*  30:    */ import gregtech.common.blocks.GT_TileEntity_Ores;
import gregtech.common.items.GT_DepletetCell_Item;
/*  31:    */ import gregtech.common.items.GT_FluidDisplayItem;
/*  32:    */ import gregtech.common.items.GT_IntegratedCircuit_Item;
/*  33:    */ import gregtech.common.items.GT_MetaGenerated_Item_01;
/*  34:    */ import gregtech.common.items.GT_MetaGenerated_Item_02;
/*  35:    */ import gregtech.common.items.GT_MetaGenerated_Item_03;
/*  36:    */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.items.GT_NeutronReflector_Item;
import ic2.core.Ic2Items;
import ic2.core.item.ItemRadioactive;

/*  37:    */ import java.io.PrintStream;

/*  38:    */ import net.minecraft.init.Blocks;
/*  39:    */ import net.minecraft.init.Items;
/*  40:    */ import net.minecraft.item.Item;
/*  41:    */ import net.minecraft.item.ItemStack;
/*  42:    */ import net.minecraftforge.fluids.Fluid;
/*  43:    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*  44:    */ import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
/*  45:    */ import net.minecraftforge.fluids.FluidRegistry;
/*  46:    */ import net.minecraftforge.fluids.FluidStack;
/*  47:    */ 
/*  48:    */ public class GT_Loader_Item_Block_And_Fluid
/*  49:    */   implements Runnable
/*  50:    */ {
/*  51:    */   public void run()
/*  52:    */   {
/*  53: 54 */     Materials.Water.mFluid = (Materials.Ice.mFluid = GT_ModHandler.getWater(1000L).getFluid());
/*  54: 55 */     Materials.Lava.mFluid = GT_ModHandler.getLava(1000L).getFluid();
/*  55:    */     
/*  56: 57 */     GT_Log.out.println("GT_Mod: Register Books.");
/*  57:    */     
/*  58: 59 */     GT_Utility.getWrittenBook("Manual_Printer", "Printer Manual V2.0", "Gregorius Techneticies", new String[] { "This Manual explains the different Functionalities the GregTech Printing Factory has built in, which are not in NEI. Most got NEI Support now, but there is still some left without it.", "1. Coloring Items and Blocks: You know those Crafting Recipes, which have a dye surrounded by 8 Item to dye them? Or the ones which have just one Item and one Dye in the Grid? Those two Recipe Types can be cheaply automated using the Printer.", "The Colorization Functionality even optimizes the Recipes, which normally require 8 Items + 1 Dye to 1 Item and an 8th of the normally used Dye in Fluid Form, isn't that awesome?", "2. Copying Books: This Task got slightly harder. The first Step is putting the written and signed Book inside the Scanner with a Data Stick ready to receive the Data.", "Now insert the Stick into the Data Slot of the Printer and add 3 pieces of Paper together with 144 Liters of actual Ink Fluid. Water mixed and chemical Dyes won't work on Paper without messing things up!", "You got a stack of Pages for your new Book, just put them into the Assembler with some Glue and a piece of Leather for the Binding, and you receive an identical copy of the Book, which would stack together with the original.", "3. Renaming Items: This Functionality is no longer Part of the Printer. There is now a Name Mold for the Forming Press to imprint a Name into an Item, just rename the Mold in an Anvil and use it in the Forming Press on any Item.", "4. Crafting of Books, Maps, Nametags etc etc etc: Those Recipes moved to other Machines, just look them up in NEI." });
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69: 70 */     GT_Utility.getWrittenBook("Manual_Punch_Cards", "Punch Card Manual V0.0", "Gregorius Techneticies", new String[] { "This Manual will explain the Functionality of the Punch Cards, once they are fully implemented. And no, they won't be like the IRL Punch Cards. This is just a current Idea Collection.", "(i1&&i2)?o1=15:o1=0;=10", "ignore all Whitespace Characters, use Long for saving the Numbers", "&& || ^^ & | ^ ! ++ -- + - % / // * ** << >> >>> < > <= >= == !=  ~ ( ) ?: , ; ;= ;=X; = i0 i1 i2 i3 i4 i5 o0 o1 o2 o3 o4 o5 v0 v1 v2 v3 v4 v5 v6 v7 v8 v9 m0 m1 m2 m3 m4 m5 m6 m7 m8 m9 A B C D E F", "'0' = false, 'everything but 0' = true, '!' turns '0' into '1' and everything else into '0'", "',' is just a separator for multiple executed Codes in a row.", "';' means that the Program waits until the next tick before continuing. ';=10' and ';=10;' both mean that it will wait 10 Ticks instead of 1. And ';=0' or anything < 0 will default to 0.", "If the '=' Operator is used within Brackets, it returns the value the variable has been set to.", "The Program saves the Char Index of the current Task, the 10 Variables (which reset to 0 as soon as the Program Loop stops), the 10 Member Variables and the remaining waiting Time in its NBT.", "A = 10, B = 11, C = 12, D = 13, E = 14, F = 15, just for Hexadecimal Space saving, since Redstone has only 4 Bits.", "For implementing Loops you just need 1 Punch Card per Loop, these Cards can restart once they are finished, depending on how many other Cards there are in the Program Loop you inserted your Card into, since it will process them procedurally.", "A Punch Card Processor can run up to four Loops, each with the length of seven Punch Cards, parallel.", "Why does the Punch Card need Ink to be made, you ask? Because the empty one needs to have some lines on, and the for the punched one it prints the Code to execute in a human readable format on the Card." });
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85: 86 */     GT_Utility.getWrittenBook("Manual_Microwave", "Microwave Oven Manual", "Kitchen Industries", new String[] { "Congratulations, you inserted a random seemingly empty Book into the Microwave and these Letters appeared out of nowhere.", "You just got a Microwave Oven and asked yourself 'why do I even need it?'. It's simple, the Microwave can cook for just 128 EU and at an insane speed. Not even a normal E-furnace can do it that fast and cheap!", "This is the cheapest and fastest way to cook for you. That is why the Microwave Oven can be found in almost every Kitchen (see www.youwannabuyakitchen.ly).", "Long time exposure to Microwaves can cause Cancer, but we doubt Steve lives long enough to die because of that.", "Do not insert any Metals. It might result in an Explosion.", "Do not dry Animals with it. It will result in a Hot Dog, no matter which Animal you put into it.", "Do not insert inflammable Objects. The Oven will catch on Fire.", "Do not insert Explosives such as Eggs. Just don't." });
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96: 97 */     GT_Log.out.println("GT_Mod: Register Items.");
/*  97:    */     
/*  98: 99 */     new GT_IntegratedCircuit_Item();
/*  99:100 */     new GT_MetaGenerated_Item_01();
/* 100:101 */     new GT_MetaGenerated_Item_02();
/* 101:102 */     new GT_MetaGenerated_Item_03();
/* 102:103 */     new GT_MetaGenerated_Tool_01();
/* 103:    */     
/* 104:105 */     new GT_FluidDisplayItem();
/* 105:    */     
/* 106:107 */     Item tItem = (Item)GT_Utility.callConstructor("gregtech.common.items.GT_SensorCard_Item", 0, null, false, new Object[] { "sensorcard", "GregTech Sensor Card" });
/* 107:108 */     ItemList.NC_SensorCard.set(tItem == null ? new GT_Generic_Item("sensorcard", "GregTech Sensor Card", "Nuclear Control not installed", false) : tItem);

					ItemList.Neutron_Reflector.set(new GT_NeutronReflector_Item("neutronreflector", "Iridium Neutron Reflector", 0));
					GT_ModHandler.addCraftingRecipe(ItemList.Neutron_Reflector.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "RRR", "RPR", "RRR",'R', GT_ModHandler.getIC2Item("reactorReflectorThick", 1L),'P', OrePrefixes.plate.get(Materials.Iridium) });    
					
					ItemList.Reactor_Coolant_He_1.set(GregTech_API.constructCoolantCellItem("60k_Helium_Coolantcell", "60k He Coolant Cell", 60000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_1.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { " P ", "PCP", " P ",'C', OrePrefixes.cell.get(Materials.Helium),'P', OrePrefixes.plate.get(Materials.Tin) });    
					
					ItemList.Reactor_Coolant_He_3.set(GregTech_API.constructCoolantCellItem("180k_Helium_Coolantcell", "180k He Coolant Cell", 180000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_3.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "PCP", "PCP", "PCP",'C', ItemList.Reactor_Coolant_He_1,'P', OrePrefixes.plate.get(Materials.Tin)});    
					
					ItemList.Reactor_Coolant_He_6.set(GregTech_API.constructCoolantCellItem("360k_Helium_Coolantcell", "360k He Coolant Cell", 360000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_He_6.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "PCP", "PDP", "PCP",'C', ItemList.Reactor_Coolant_He_3,'P', OrePrefixes.plate.get(Materials.Tin),'D',OrePrefixes.plateDense.get(Materials.Copper) });    
					
					ItemList.Reactor_Coolant_NaK_1.set(GregTech_API.constructCoolantCellItem("60k_NaK_Coolantcell", "60k NaK Coolantcell", 60000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_1.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "TST", "PCP", "TST",'C', GT_ModHandler.getIC2Item("reactorCoolantSimple", 1L),'T', OrePrefixes.plate.get(Materials.Tin) ,'S', OrePrefixes.dust.get(Materials.Sodium),'P', OrePrefixes.dust.get(Materials.Potassium)});    
					
					ItemList.Reactor_Coolant_NaK_3.set(GregTech_API.constructCoolantCellItem("180k_NaK_Coolantcell", "180k NaK Coolantcell", 180000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_3.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "PCP", "PCP", "PCP",'C', ItemList.Reactor_Coolant_NaK_1,'P', OrePrefixes.plate.get(Materials.Tin)});    
					
					ItemList.Reactor_Coolant_NaK_6.set(GregTech_API.constructCoolantCellItem("360k_NaK_Coolantcell", "360k NaK Coolantcell", 360000));
					GT_ModHandler.addCraftingRecipe(ItemList.Reactor_Coolant_NaK_6.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "PCP", "PDP", "PCP",'C', ItemList.Reactor_Coolant_NaK_3,'P', OrePrefixes.plate.get(Materials.Tin),'D',OrePrefixes.plateDense.get(Materials.Copper) });    
					
					ItemList.ThoriumCell_1.set(new GT_RadioactiveCellIC_Item("Thoriumcell", "Fuel Rod (Thorium)", 1, 50000, 0.2D,0));
					GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_1.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "DR ", "   ", "   ",'R', Ic2Items.fuelRod ,'D', OrePrefixes.dust.get(Materials.Thorium) });    
					
					ItemList.ThoriumCell_2.set(new GT_RadioactiveCellIC_Item("Double_Thoriumcell", "Double Fuel Rod (Thorium)", 2, 50000, 0.2D,0));
					GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_2.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "RPR", "   ", "   ",'R', ItemList.ThoriumCell_1 ,'P', OrePrefixes.plate.get(Materials.Iron) });    
					
					ItemList.ThoriumCell_4.set(new GT_RadioactiveCellIC_Item("Quad_Thoriumcell", "Quad Fuel Rod (Thorium)", 4, 50000, 0.2D,0));
					GT_ModHandler.addCraftingRecipe(ItemList.ThoriumCell_4.get(1L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE , new Object[] { "RPR", "CPC", "RPR",'R', ItemList.ThoriumCell_1 ,'P', OrePrefixes.plate.get(Materials.Iron),'C', OrePrefixes.plate.get(Materials.Copper) });    
				
					ItemList.Depleted_Thorium_1.set(new GT_DepletetCell_Item("ThoriumcellDep","Fuel Rod (Depleted Thorium)",1));
					ItemList.Depleted_Thorium_2.set(new GT_DepletetCell_Item("Double_ThoriumcellDep","Dual Fuel Rod (Depleted Thorium)",2));
					ItemList.Depleted_Thorium_4.set(new GT_DepletetCell_Item("Quad_ThoriumcellDep","Quad Fuel Rod (Depleted Thorium)",4));

					GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_1.get(1, new Object[0]), 5000, new Object[]{ GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lutetium, 1L),GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Thorium, 2L),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 1L)});
					GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_2.get(1, new Object[0]), 5000, new Object[]{ GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lutetium, 2L),GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Thorium, 4L),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 3L)});
					GT_ModHandler.addThermalCentrifugeRecipe(ItemList.Depleted_Thorium_4.get(1, new Object[0]), 5000, new Object[]{ GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lutetium, 4L),GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Thorium, 8L),GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 6L)});

					
/* 108:    */     
/* 109:110 */     GT_Log.out.println("GT_Mod: Adding Blocks.");
/* 110:111 */     GregTech_API.sBlockMachines = new GT_Block_Machines();
/* 111:112 */     GregTech_API.sBlockCasings1 = new GT_Block_Casings1();
/* 112:113 */     GregTech_API.sBlockCasings2 = new GT_Block_Casings2();
/* 113:114 */     GregTech_API.sBlockCasings3 = new GT_Block_Casings3();
/* 114:115 */     GregTech_API.sBlockCasings4 = new GT_Block_Casings4();
/* 115:116 */     GregTech_API.sBlockGranites = new GT_Block_Granites();
/* 116:117 */     GregTech_API.sBlockConcretes = new GT_Block_Concretes();
/* 117:118 */     GregTech_API.sBlockOres1 = new GT_Block_Ores();
/* 118:    */     
/* 119:120 */     GT_Log.out.println("GT_Mod: Register TileEntities.");
/* 120:    */     
/* 121:    */ 
/* 122:123 */     BaseMetaTileEntity tBaseMetaTileEntity = GregTech_API.constructBaseMetaTileEntity();
/* 123:    */     
/* 124:125 */     GT_Log.out.println("GT_Mod: Testing BaseMetaTileEntity.");
/* 125:126 */     if (tBaseMetaTileEntity == null)
/* 126:    */     {
/* 127:127 */       GT_Log.out.println("GT_Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
/* 128:128 */       throw new RuntimeException("");
/* 129:    */     }
/* 130:131 */     GT_Log.out.println("GT_Mod: Registering the BaseMetaTileEntity.");
/* 131:132 */     GameRegistry.registerTileEntity(tBaseMetaTileEntity.getClass(), "BaseMetaTileEntity");
/* 132:133 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", tBaseMetaTileEntity.getClass().getName());
/* 133:    */     
/* 134:135 */     GT_Log.out.println("GT_Mod: Registering the BaseMetaPipeEntity.");
/* 135:136 */     GameRegistry.registerTileEntity(BaseMetaPipeEntity.class, "BaseMetaPipeEntity");
/* 136:137 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", BaseMetaPipeEntity.class.getName());
/* 137:    */     
/* 138:139 */     GT_Log.out.println("GT_Mod: Registering the Ore TileEntity.");
/* 139:140 */     GameRegistry.registerTileEntity(GT_TileEntity_Ores.class, "GT_TileEntity_Ores");
/* 140:141 */     FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", GT_TileEntity_Ores.class.getName());
/* 141:    */     
/* 142:143 */     GT_Log.out.println("GT_Mod: Registering Fluids.");
/* 143:144 */     Materials.ConstructionFoam.mFluid = GT_Utility.getFluidForFilledItem(GT_ModHandler.getIC2Item("CFCell", 1L), true).getFluid();
/* 144:145 */     Materials.UUMatter.mFluid = GT_Utility.getFluidForFilledItem(GT_ModHandler.getIC2Item("uuMatterCell", 1L), true).getFluid();
/* 145:    */     
///* 146:147 */     GT_Mod.gregtechproxy.addFluid("HeliumPlasma", "Helium Plasma", Materials.Helium, 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Helium, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
///* 147:148 */     GT_Mod.gregtechproxy.addFluid("NitrogenPlasma", "Nitrogen Plasma", Materials.Nitrogen, 3, 10000, GT_OreDictUnificator.get(OrePrefixes.cellPlasma, Materials.Nitrogen, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 148:    */     
/* 149:    */ 
/* 150:    */ 
/* 151:152 */     GT_Mod.gregtechproxy.addFluid("Air", "Air", Materials.Air, 2, 295, ItemList.Cell_Air.get(1L, new Object[0]), ItemList.Cell_Empty.get(1L, new Object[0]), 2000);
/* 152:153 */     GT_Mod.gregtechproxy.addFluid("Oxygen", "Oxygen", Materials.Oxygen, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oxygen, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 153:154 */     GT_Mod.gregtechproxy.addFluid("Hydrogen", "Hydrogen", Materials.Hydrogen, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Hydrogen, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 154:155 */     GT_Mod.gregtechproxy.addFluid("Deuterium", "Deuterium", Materials.Deuterium, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Deuterium, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 155:156 */     GT_Mod.gregtechproxy.addFluid("Tritium", "Tritium", Materials.Tritium, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Tritium, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 156:157 */     GT_Mod.gregtechproxy.addFluid("Helium", "Helium", Materials.Helium, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Helium, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 157:158 */     GT_Mod.gregtechproxy.addFluid("Helium-3", "Helium-3", Materials.Helium_3, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Helium_3, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 158:159 */     GT_Mod.gregtechproxy.addFluid("Methane", "Methane", Materials.Methane, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Methane, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 159:160 */     GT_Mod.gregtechproxy.addFluid("Nitrogen", "Nitrogen", Materials.Nitrogen, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Nitrogen, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 160:161 */     GT_Mod.gregtechproxy.addFluid("NitrogenDioxide", "Nitrogen Dioxide", Materials.NitrogenDioxide, 2, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NitrogenDioxide, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 161:162 */     GT_Mod.gregtechproxy.addFluid("Steam", "Steam", Materials.Water, 2, 375);
/* 162:163 */     Materials.Ice.mGas = Materials.Water.mGas;
/* 163:164 */     Materials.Water.mGas.setTemperature(375).setGaseous(true);
/* 164:    */     
/* 165:    */ 
/* 166:    */ 
/* 167:168 */     ItemList.sOilExtraHeavy = GT_Mod.gregtechproxy.addFluid("liquid_extra_heavy_oil", "Very Heavy Oil", null, 1, 295);
/* 168:169 */     ItemList.sOilHeavy = GT_Mod.gregtechproxy.addFluid("liquid_heavy_oil", "Heavy Oil", null, 1, 295);
/* 169:170 */     ItemList.sOilMedium = GT_Mod.gregtechproxy.addFluid("liquid_medium_oil", "Raw Oil", null, 1, 295);
/* 170:171 */     ItemList.sOilLight = GT_Mod.gregtechproxy.addFluid("liquid_light_oil", "Light Oil", null, 1, 295);
/* 171:172 */     ItemList.sNaturalGas = GT_Mod.gregtechproxy.addFluid("gas_natural_gas", "Natural Gas", null, 2, 295);
/* 172:    */     
/* 173:174 */     GT_Mod.gregtechproxy.addFluid("UUAmplifier", "UU Amplifier", Materials.UUAmplifier, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.UUAmplifier, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 174:175 */     GT_Mod.gregtechproxy.addFluid("Chlorine", "Chlorine", Materials.Chlorine, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Chlorine, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 175:176 */     GT_Mod.gregtechproxy.addFluid("Mercury", "Mercury", Materials.Mercury, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Mercury, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 176:177 */     GT_Mod.gregtechproxy.addFluid("NitroFuel", "Nitro Diesel", Materials.NitroFuel, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NitroFuel, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 177:178 */     GT_Mod.gregtechproxy.addFluid("SodiumPersulfate", "Sodium Persulfate", Materials.SodiumPersulfate, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.SodiumPersulfate, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 178:179 */     GT_Mod.gregtechproxy.addFluid("Glyceryl", "Glyceryl Trinitrate", Materials.Glyceryl, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Glyceryl, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 179:    */     
/* 180:181 */     GT_Mod.gregtechproxy.addFluid("lubricant", "Lubricant", Materials.Lubricant, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Lubricant, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 181:182 */     GT_Mod.gregtechproxy.addFluid("creosote", "Creosote Oil", Materials.Creosote, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Creosote, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 182:183 */     GT_Mod.gregtechproxy.addFluid("seedoil", "Seed Oil", Materials.SeedOil, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.SeedOil, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 183:184 */     GT_Mod.gregtechproxy.addFluid("fishoil", "Fish Oil", Materials.FishOil, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.FishOil, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 184:185 */     GT_Mod.gregtechproxy.addFluid("oil", "Oil", Materials.Oil, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Oil, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 185:186 */     GT_Mod.gregtechproxy.addFluid("fuel", "Diesel", Materials.Fuel, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Fuel, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 186:187 */     GT_Mod.gregtechproxy.addFluid("for.honey", "Honey", Materials.Honey, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Honey, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 187:188 */     GT_Mod.gregtechproxy.addFluid("biomass", "Biomass", Materials.Biomass, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Biomass, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 188:189 */     GT_Mod.gregtechproxy.addFluid("bioethanol", "Bio Ethanol", Materials.Ethanol, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Ethanol, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 189:190 */     GT_Mod.gregtechproxy.addFluid("sulfuricacid", "Sulfuric Acid", Materials.SulfuricAcid, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.SulfuricAcid, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 190:191 */     GT_Mod.gregtechproxy.addFluid("milk", "Milk", Materials.Milk, 1, 290, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Milk, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 191:192 */     GT_Mod.gregtechproxy.addFluid("mcguffium", "Mc Guffium 239", Materials.McGuffium239, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.McGuffium239, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 192:193 */     GT_Mod.gregtechproxy.addFluid("glue", "Glue", Materials.Glue, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Glue, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 193:194 */     GT_Mod.gregtechproxy.addFluid("hotfryingoil", "Hot Frying Oil", Materials.FryingOilHot, 1, 400, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.FryingOilHot, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 194:    */     
/* 195:196 */     GT_Mod.gregtechproxy.addFluid("fieryblood", "Fiery Blood", Materials.FierySteel, 1, 6400, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.FierySteel, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 196:197 */     GT_Mod.gregtechproxy.addFluid("holywater", "Holy Water", Materials.HolyWater, 1, 295, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HolyWater, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 197:199 */     if (ItemList.TF_Vial_FieryBlood.get(1L, new Object[0]) != null) {
/* 198:200 */       FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.FierySteel.getFluid(250L), ItemList.TF_Vial_FieryBlood.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0])));
/* 199:    */     }
/* 200:201 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Milk.getFluid(1000L), GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.Milk, 1L), GT_OreDictUnificator.get(OrePrefixes.bucket, Materials.Empty, 1L)));
/* 201:202 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Milk.getFluid(250L), ItemList.Bottle_Milk.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0])));
/* 202:203 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.HolyWater.getFluid(250L), ItemList.Bottle_Holy_Water.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0])));
/* 203:204 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.McGuffium239.getFluid(250L), ItemList.McGuffium_239.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0])));
/* 204:205 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Fuel.getFluid(100L), ItemList.Tool_Lighter_Invar_Full.get(1L, new Object[0]), ItemList.Tool_Lighter_Invar_Empty.get(1L, new Object[0])));
/* 205:206 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(Materials.Fuel.getFluid(1000L), ItemList.Tool_Lighter_Platinum_Full.get(1L, new Object[0]), ItemList.Tool_Lighter_Platinum_Empty.get(1L, new Object[0])));
/* 206:    */     
/* 207:208 */     Dyes.dyeBlack.addFluidDye(GT_Mod.gregtechproxy.addFluid("squidink", "Squid Ink", null, 1, 295));
/* 208:209 */     Dyes.dyeBlue.addFluidDye(GT_Mod.gregtechproxy.addFluid("indigo", "Indigo Dye", null, 1, 295));
/* 209:211 */     for (byte i = 0; i < Dyes.VALUES.length; i = (byte)(i + 1))
/* 210:    */     {
/* 211:212 */       Dyes tDye = Dyes.VALUES[i];
/* 212:    */       Fluid tFluid;
/* 213:214 */       tDye.addFluidDye(tFluid = GT_Mod.gregtechproxy.addFluid("dye.watermixed." + tDye.name().toLowerCase(), "dyes", "Water Mixed " + tDye.mName + " Dye", null, tDye.getRGBA(), 1, 295, null, null, 0));
/* 214:215 */       tDye.addFluidDye(tFluid = GT_Mod.gregtechproxy.addFluid("dye.chemical." + tDye.name().toLowerCase(), "dyes", "Chemical " + tDye.mName + " Dye", null, tDye.getRGBA(), 1, 295, null, null, 0));
/* 215:216 */       FluidContainerRegistry.registerFluidContainer(new FluidStack(tFluid, 2304), ItemList.SPRAY_CAN_DYES[i].get(1L, new Object[0]), ItemList.Spray_Empty.get(1L, new Object[0]));
/* 216:    */     }
/* 217:221 */     GT_Mod.gregtechproxy.addFluid("ice", "Crushed Ice", Materials.Ice, 0, 270, GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Ice, 1L), ItemList.Cell_Empty.get(1L, new Object[0]), 1000);
/* 218:222 */     Materials.Water.mSolid = Materials.Ice.mSolid;
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222:226 */     GT_Mod.gregtechproxy.addFluid("molten.glass", "Molten Glass", Materials.Glass, 4, 800);
/* 223:227 */     GT_Mod.gregtechproxy.addFluid("molten.redstone", "Molten Redstone", Materials.Redstone, 4, 500);
/* 224:228 */     GT_Mod.gregtechproxy.addFluid("molten.blaze", "Molten Blaze", Materials.Blaze, 4, 6400);
/* 225:229 */     GT_Mod.gregtechproxy.addFluid("molten.concrete", "Wet Concrete", Materials.Concrete, 4, 300);
/* 226:231 */     for (Materials tMaterial : Materials.VALUES) {
/* 227:232 */       if ((tMaterial.mStandardMoltenFluid == null) && (tMaterial.contains(SubTag.SMELTING_TO_FLUID)) && (!tMaterial.contains(SubTag.NO_SMELTING)))
/* 228:    */       {
/* 229:233 */         GT_Mod.gregtechproxy.addAutogeneratedMoltenFluid(tMaterial);
/* 230:234 */         if ((tMaterial.mSmeltInto != tMaterial) && (tMaterial.mSmeltInto.mStandardMoltenFluid == null)) {
/* 231:235 */           GT_Mod.gregtechproxy.addAutogeneratedMoltenFluid(tMaterial.mSmeltInto);
/* 232:    */         }
/* 233:    */       }
					if(tMaterial.mElement!=null){
						GT_Mod.gregtechproxy.addAutogeneratedPlasmaFluid(tMaterial);
					}
/* 234:    */     }
/* 235:242 */     GT_Mod.gregtechproxy.addFluid("potion.awkward", "Awkward Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 236:243 */     GT_Mod.gregtechproxy.addFluid("potion.thick", "Thick Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 32), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 237:244 */     GT_Mod.gregtechproxy.addFluid("potion.mundane", "Mundane Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 64), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 238:245 */     GT_Mod.gregtechproxy.addFluid("potion.damage", "Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8204), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 239:246 */     GT_Mod.gregtechproxy.addFluid("potion.damage.strong", "Strong Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8236), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 240:247 */     GT_Mod.gregtechproxy.addFluid("potion.damage.splash", "Splash Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16396), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 241:248 */     GT_Mod.gregtechproxy.addFluid("potion.damage.strong.splash", "Strong Splash Harming Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16428), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 242:249 */     GT_Mod.gregtechproxy.addFluid("potion.health", "Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8197), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 243:250 */     GT_Mod.gregtechproxy.addFluid("potion.health.strong", "Strong Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8229), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 244:251 */     GT_Mod.gregtechproxy.addFluid("potion.health.splash", "Splash Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16389), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 245:252 */     GT_Mod.gregtechproxy.addFluid("potion.health.strong.splash", "Strong Splash Healing Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16421), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 246:253 */     GT_Mod.gregtechproxy.addFluid("potion.speed", "Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8194), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 247:254 */     GT_Mod.gregtechproxy.addFluid("potion.speed.strong", "Strong Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8226), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 248:255 */     GT_Mod.gregtechproxy.addFluid("potion.speed.long", "Stretched Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8258), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 249:256 */     GT_Mod.gregtechproxy.addFluid("potion.speed.splash", "Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16386), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 250:257 */     GT_Mod.gregtechproxy.addFluid("potion.speed.strong.splash", "Strong Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16418), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 251:258 */     GT_Mod.gregtechproxy.addFluid("potion.speed.long.splash", "Stretched Splash Swiftness Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16450), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 252:259 */     GT_Mod.gregtechproxy.addFluid("potion.strength", "Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8201), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 253:260 */     GT_Mod.gregtechproxy.addFluid("potion.strength.strong", "Strong Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8233), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 254:261 */     GT_Mod.gregtechproxy.addFluid("potion.strength.long", "Stretched Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8265), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 255:262 */     GT_Mod.gregtechproxy.addFluid("potion.strength.splash", "Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16393), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 256:263 */     GT_Mod.gregtechproxy.addFluid("potion.strength.strong.splash", "Strong Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16425), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 257:264 */     GT_Mod.gregtechproxy.addFluid("potion.strength.long.splash", "Stretched Splash Strength Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16457), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 258:265 */     GT_Mod.gregtechproxy.addFluid("potion.regen", "Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8193), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 259:266 */     GT_Mod.gregtechproxy.addFluid("potion.regen.strong", "Strong Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8225), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 260:267 */     GT_Mod.gregtechproxy.addFluid("potion.regen.long", "Stretched Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8257), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 261:268 */     GT_Mod.gregtechproxy.addFluid("potion.regen.splash", "Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16385), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 262:269 */     GT_Mod.gregtechproxy.addFluid("potion.regen.strong.splash", "Strong Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16417), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 263:270 */     GT_Mod.gregtechproxy.addFluid("potion.regen.long.splash", "Stretched Splash Regenerating Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16449), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 264:271 */     GT_Mod.gregtechproxy.addFluid("potion.poison", "Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8196), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 265:272 */     GT_Mod.gregtechproxy.addFluid("potion.poison.strong", "Strong Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8228), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 266:273 */     GT_Mod.gregtechproxy.addFluid("potion.poison.long", "Stretched Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8260), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 267:274 */     GT_Mod.gregtechproxy.addFluid("potion.poison.splash", "Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16388), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 268:275 */     GT_Mod.gregtechproxy.addFluid("potion.poison.strong.splash", "Strong Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16420), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 269:276 */     GT_Mod.gregtechproxy.addFluid("potion.poison.long.splash", "Stretched Splash Poisonous Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16452), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 270:277 */     GT_Mod.gregtechproxy.addFluid("potion.fireresistance", "Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 8195), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 271:278 */     GT_Mod.gregtechproxy.addFluid("potion.fireresistance.long", "Stretched Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 8259), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 272:279 */     GT_Mod.gregtechproxy.addFluid("potion.fireresistance.splash", "Splash Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 16387), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 273:280 */     GT_Mod.gregtechproxy.addFluid("potion.fireresistance.long.splash", "Stretched Splash Fire Resistant Brew", null, 1, 375, new ItemStack(Items.potionitem, 1, 16451), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 274:281 */     GT_Mod.gregtechproxy.addFluid("potion.nightvision", "Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8198), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 275:282 */     GT_Mod.gregtechproxy.addFluid("potion.nightvision.long", "Stretched Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8262), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 276:283 */     GT_Mod.gregtechproxy.addFluid("potion.nightvision.splash", "Splash Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16390), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 277:284 */     GT_Mod.gregtechproxy.addFluid("potion.nightvision.long.splash", "Stretched Splash Night Vision Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16454), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 278:285 */     GT_Mod.gregtechproxy.addFluid("potion.weakness", "Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8200), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 279:286 */     GT_Mod.gregtechproxy.addFluid("potion.weakness.long", "Stretched Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8264), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 280:287 */     GT_Mod.gregtechproxy.addFluid("potion.weakness.splash", "Splash Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16392), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 281:288 */     GT_Mod.gregtechproxy.addFluid("potion.weakness.long.splash", "Stretched Splash Weakening Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16456), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 282:289 */     GT_Mod.gregtechproxy.addFluid("potion.slowness", "Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8202), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 283:290 */     GT_Mod.gregtechproxy.addFluid("potion.slowness.long", "Stretched Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8266), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 284:291 */     GT_Mod.gregtechproxy.addFluid("potion.slowness.splash", "Splash Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16394), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 285:292 */     GT_Mod.gregtechproxy.addFluid("potion.slowness.long.splash", "Stretched Splash Lame Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16458), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 286:293 */     GT_Mod.gregtechproxy.addFluid("potion.waterbreathing", "Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8205), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 287:294 */     GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.long", "Stretched Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8269), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 288:295 */     GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.splash", "Splash Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16397), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 289:296 */     GT_Mod.gregtechproxy.addFluid("potion.waterbreathing.long.splash", "Stretched Splash Fishy Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16461), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 290:297 */     GT_Mod.gregtechproxy.addFluid("potion.invisibility", "Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8206), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 291:298 */     GT_Mod.gregtechproxy.addFluid("potion.invisibility.long", "Stretched Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 8270), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 292:299 */     GT_Mod.gregtechproxy.addFluid("potion.invisibility.splash", "Splash Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16398), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 293:300 */     GT_Mod.gregtechproxy.addFluid("potion.invisibility.long.splash", "Stretched Splash Invisible Brew", null, 1, 295, new ItemStack(Items.potionitem, 1, 16462), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 294:    */     
/* 295:302 */     GT_Mod.gregtechproxy.addFluid("potion.purpledrink", "Purple Drink", null, 1, 275, ItemList.Bottle_Purple_Drink.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 296:303 */     GT_Mod.gregtechproxy.addFluid("potion.grapejuice", "Grape Juice", null, 1, 295, ItemList.Bottle_Grape_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 297:304 */     GT_Mod.gregtechproxy.addFluid("potion.wine", "Wine", null, 1, 295, ItemList.Bottle_Wine.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 298:305 */     GT_Mod.gregtechproxy.addFluid("potion.vinegar", "Vinegar", null, 1, 295, ItemList.Bottle_Vinegar.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 299:306 */     GT_Mod.gregtechproxy.addFluid("potion.potatojuice", "Potato Juice", null, 1, 295, ItemList.Bottle_Potato_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 300:307 */     GT_Mod.gregtechproxy.addFluid("potion.vodka", "Vodka", null, 1, 275, ItemList.Bottle_Vodka.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 301:308 */     GT_Mod.gregtechproxy.addFluid("potion.leninade", "Leninade", null, 1, 275, ItemList.Bottle_Leninade.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 302:309 */     GT_Mod.gregtechproxy.addFluid("potion.mineralwater", "Mineral Water", null, 1, 275, ItemList.Bottle_Mineral_Water.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 303:310 */     GT_Mod.gregtechproxy.addFluid("potion.saltywater", "Salty Water", null, 1, 275, ItemList.Bottle_Salty_Water.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 304:311 */     GT_Mod.gregtechproxy.addFluid("potion.reedwater", "Reed Water", null, 1, 295, ItemList.Bottle_Reed_Water.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 305:312 */     GT_Mod.gregtechproxy.addFluid("potion.rum", "Rum", null, 1, 295, ItemList.Bottle_Rum.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 306:313 */     GT_Mod.gregtechproxy.addFluid("potion.piratebrew", "Pirate Brew", null, 1, 295, ItemList.Bottle_Pirate_Brew.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 307:314 */     GT_Mod.gregtechproxy.addFluid("potion.hopsjuice", "Hops Juice", null, 1, 295, ItemList.Bottle_Hops_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 308:315 */     GT_Mod.gregtechproxy.addFluid("potion.darkbeer", "Dark Beer", null, 1, 275, ItemList.Bottle_Dark_Beer.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 309:316 */     GT_Mod.gregtechproxy.addFluid("potion.dragonblood", "Dragon Blood", null, 1, 375, ItemList.Bottle_Dragon_Blood.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 310:317 */     GT_Mod.gregtechproxy.addFluid("potion.wheatyjuice", "Wheaty Juice", null, 1, 295, ItemList.Bottle_Wheaty_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 311:318 */     GT_Mod.gregtechproxy.addFluid("potion.scotch", "Scotch", null, 1, 275, ItemList.Bottle_Scotch.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 312:319 */     GT_Mod.gregtechproxy.addFluid("potion.glenmckenner", "Glen McKenner", null, 1, 275, ItemList.Bottle_Glen_McKenner.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 313:320 */     GT_Mod.gregtechproxy.addFluid("potion.wheatyhopsjuice", "Wheaty Hops Juice", null, 1, 295, ItemList.Bottle_Wheaty_Hops_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 314:321 */     GT_Mod.gregtechproxy.addFluid("potion.beer", "Beer", null, 1, 275, ItemList.Bottle_Beer.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 315:322 */     GT_Mod.gregtechproxy.addFluid("potion.chillysauce", "Chilly Sauce", null, 1, 375, ItemList.Bottle_Chilly_Sauce.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 316:323 */     GT_Mod.gregtechproxy.addFluid("potion.hotsauce", "Hot Sauce", null, 1, 380, ItemList.Bottle_Hot_Sauce.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 317:324 */     GT_Mod.gregtechproxy.addFluid("potion.diabolosauce", "Diabolo Sauce", null, 1, 385, ItemList.Bottle_Diabolo_Sauce.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 318:325 */     GT_Mod.gregtechproxy.addFluid("potion.diablosauce", "Diablo Sauce", null, 1, 390, ItemList.Bottle_Diablo_Sauce.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 319:326 */     GT_Mod.gregtechproxy.addFluid("potion.diablosauce.strong", "Old Man Snitches glitched Diablo Sauce", null, 1, 999, ItemList.Bottle_Snitches_Glitch_Sauce.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 320:327 */     GT_Mod.gregtechproxy.addFluid("potion.applejuice", "Apple Juice", null, 1, 295, ItemList.Bottle_Apple_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 321:328 */     GT_Mod.gregtechproxy.addFluid("potion.cider", "Cider", null, 1, 295, ItemList.Bottle_Cider.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 322:329 */     GT_Mod.gregtechproxy.addFluid("potion.goldenapplejuice", "Golden Apple Juice", null, 1, 295, ItemList.Bottle_Golden_Apple_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 323:330 */     GT_Mod.gregtechproxy.addFluid("potion.goldencider", "Golden Cider", null, 1, 295, ItemList.Bottle_Golden_Cider.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 324:331 */     GT_Mod.gregtechproxy.addFluid("potion.idunsapplejuice", "Idun's Apple Juice", null, 1, 295, ItemList.Bottle_Iduns_Apple_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 325:332 */     GT_Mod.gregtechproxy.addFluid("potion.notchesbrew", "Notches Brew", null, 1, 295, ItemList.Bottle_Notches_Brew.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 326:333 */     GT_Mod.gregtechproxy.addFluid("potion.lemonjuice", "Lemon Juice", null, 1, 295, ItemList.Bottle_Lemon_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 327:334 */     GT_Mod.gregtechproxy.addFluid("potion.limoncello", "Limoncello", null, 1, 295, ItemList.Bottle_Limoncello.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 328:335 */     GT_Mod.gregtechproxy.addFluid("potion.lemonade", "Lemonade", null, 1, 275, ItemList.Bottle_Lemonade.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 329:336 */     GT_Mod.gregtechproxy.addFluid("potion.alcopops", "Alcopops", null, 1, 275, ItemList.Bottle_Alcopops.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 330:337 */     GT_Mod.gregtechproxy.addFluid("potion.cavejohnsonsgrenadejuice", "Cave Johnsons Grenade Juice", null, 1, 295, ItemList.Bottle_Cave_Johnsons_Grenade_Juice.get(1L, new Object[0]), ItemList.Bottle_Empty.get(1L, new Object[0]), 250);
/* 331:    */     
/* 332:339 */     GT_Mod.gregtechproxy.addFluid("potion.darkcoffee", "Dark Coffee", null, 1, 295, ItemList.ThermosCan_Dark_Coffee.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 333:340 */     GT_Mod.gregtechproxy.addFluid("potion.darkcafeaulait", "Dark Cafe au lait", null, 1, 295, ItemList.ThermosCan_Dark_Cafe_au_lait.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 334:341 */     GT_Mod.gregtechproxy.addFluid("potion.coffee", "Coffee", null, 1, 295, ItemList.ThermosCan_Coffee.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 335:342 */     GT_Mod.gregtechproxy.addFluid("potion.cafeaulait", "Cafe au lait", null, 1, 295, ItemList.ThermosCan_Cafe_au_lait.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 336:343 */     GT_Mod.gregtechproxy.addFluid("potion.laitaucafe", "Lait au cafe", null, 1, 295, ItemList.ThermosCan_Lait_au_cafe.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 337:344 */     GT_Mod.gregtechproxy.addFluid("potion.darkchocolatemilk", "Bitter Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Dark_Chocolate_Milk.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 338:345 */     GT_Mod.gregtechproxy.addFluid("potion.chocolatemilk", "Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Chocolate_Milk.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 339:346 */     GT_Mod.gregtechproxy.addFluid("potion.tea", "Tea", null, 1, 295, ItemList.ThermosCan_Tea.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 340:347 */     GT_Mod.gregtechproxy.addFluid("potion.sweettea", "Sweet Tea", null, 1, 295, ItemList.ThermosCan_Sweet_Tea.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 341:348 */     GT_Mod.gregtechproxy.addFluid("potion.icetea", "Ice Tea", null, 1, 255, ItemList.ThermosCan_Ice_Tea.get(1L, new Object[0]), ItemList.ThermosCan_Empty.get(1L, new Object[0]), 250);
/* 342:    */     
/* 343:350 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 750), ItemList.IC2_Spray_WeedEx.get(1L, new Object[0]), ItemList.Spray_Empty.get(1L, new Object[0])));
/* 344:    */     
/* 345:    */ 
/* 346:    */ 
/* 347:354 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Head_Glass_Poison.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 348:355 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Head_Glass_Poison_Long.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 349:356 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Head_Glass_Poison_Strong.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 350:357 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Head_Glass_Slowness.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 351:358 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Head_Glass_Slowness_Long.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 352:359 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Head_Glass_Weakness.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 353:360 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Head_Glass_Weakness_Long.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 354:361 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Head_Glass_Holy_Water.get(1L, new Object[0]), ItemList.Arrow_Head_Glass_Emtpy.get(1L, new Object[0])));
/* 355:    */     
/* 356:363 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Wooden_Glass_Poison.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 357:364 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Wooden_Glass_Poison_Long.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 358:365 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Wooden_Glass_Poison_Strong.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 359:366 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Wooden_Glass_Slowness.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 360:367 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Wooden_Glass_Slowness_Long.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 361:368 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Wooden_Glass_Weakness.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 362:369 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Wooden_Glass_Weakness_Long.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 363:370 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Wooden_Glass_Holy_Water.get(1L, new Object[0]), ItemList.Arrow_Wooden_Glass_Emtpy.get(1L, new Object[0])));
/* 364:    */     
/* 365:372 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison", 125), ItemList.Arrow_Plastic_Glass_Poison.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 366:373 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.long", 125), ItemList.Arrow_Plastic_Glass_Poison_Long.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 367:374 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.poison.strong", 125), ItemList.Arrow_Plastic_Glass_Poison_Strong.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 368:375 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness", 125), ItemList.Arrow_Plastic_Glass_Slowness.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 369:376 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.slowness.long", 125), ItemList.Arrow_Plastic_Glass_Slowness_Long.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 370:377 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness", 125), ItemList.Arrow_Plastic_Glass_Weakness.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 371:378 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("potion.weakness.long", 125), ItemList.Arrow_Plastic_Glass_Weakness_Long.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 372:379 */     FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(FluidRegistry.getFluidStack("holywater", 125), ItemList.Arrow_Plastic_Glass_Holy_Water.get(1L, new Object[0]), ItemList.Arrow_Plastic_Glass_Emtpy.get(1L, new Object[0])));
/* 373:394 */     if (!GT_Values.D1) {
/* 374:    */       try
/* 375:    */       {
/* 376:395 */         Class.forName("codechicken.nei.api.API");
/* 377:396 */         GT_Log.out.println("GT_Mod: Hiding certain Items from NEI.");
/* 378:397 */         API.hideItem(ItemList.Display_Fluid.getWildcard(1L, new Object[0]));
/* 379:    */       }
/* 380:    */       catch (Throwable e)
/* 381:    */       {
/* 382:398 */         if (GT_Values.D1) {
/* 383:398 */           e.printStackTrace(GT_Log.err);
/* 384:    */         }
/* 385:    */       }
/* 386:    */     }
/* 387:400 */     GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.cobblestone, 1, 32767), new ItemStack(Blocks.sand, 1), null, 0, false);
/* 388:401 */     GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.stone, 1, 32767), new ItemStack(Blocks.cobblestone, 1), null, 0, false);
/* 389:402 */     GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.gravel, 1, 32767), new ItemStack(Items.flint, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Flint, 1L), 10, false);
/* 390:403 */     GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.furnace, 1, 32767), new ItemStack(Blocks.sand, 6), null, 0, false);
/* 391:404 */     GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.lit_furnace, 1, 32767), new ItemStack(Blocks.sand, 6), null, 0, false);
/* 392:    */     
/* 393:406 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.FierySteel, GT_ModHandler.getModItem("TwilightForest", "item.fieryIngot", 1L, 0));
/* 394:407 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Knightmetal, GT_ModHandler.getModItem("TwilightForest", "item.knightMetal", 1L, 0));
/* 395:408 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Steeleaf, GT_ModHandler.getModItem("TwilightForest", "item.steeleafIngot", 1L, 0));
/* 396:409 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.IronWood, GT_ModHandler.getModItem("TwilightForest", "item.ironwoodIngot", 1L, 0));
/* 397:410 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedAir, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 0));
/* 398:411 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedFire, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 1));
/* 399:412 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedWater, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 2));
/* 400:413 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedEarth, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 3));
/* 401:414 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedOrder, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 4));
/* 402:415 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.InfusedEntropy, GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1L, 5));
/* 403:416 */     GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.Mercury, GT_ModHandler.getModItem("Thaumcraft", "ItemNugget", 1L, 5));
/* 404:417 */     GT_OreDictUnificator.set(OrePrefixes.nugget, Materials.Thaumium, GT_ModHandler.getModItem("Thaumcraft", "ItemNugget", 1L, 6));
/* 405:418 */     GT_OreDictUnificator.set(OrePrefixes.ingot, Materials.Thaumium, GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1L, 2));
/* 406:419 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Mercury, GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1L, 3));
/* 407:420 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Amber, GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1L, 6));
/* 408:421 */     GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Firestone, GT_ModHandler.getModItem("Railcraft", "firestone.raw", 1L));
/* 409:422 */     GT_OreDictUnificator.set(OrePrefixes.plate, Materials.Iron, GT_ModHandler.getModItem("Railcraft", "part.plate", 1L, 0));
/* 410:423 */     GT_OreDictUnificator.set(OrePrefixes.plate, Materials.Steel, GT_ModHandler.getModItem("Railcraft", "part.plate", 1L, 1));
/* 411:424 */     GT_OreDictUnificator.set(OrePrefixes.plate, Materials.TinAlloy, GT_ModHandler.getModItem("Railcraft", "part.plate", 1L, 2));
/* 412:425 */     GT_OreDictUnificator.set(OrePrefixes.plate, Materials.Copper, GT_ModHandler.getModItem("Railcraft", "part.plate", 1L, 3));
/* 413:426 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Cocoa, GT_ModHandler.getModItem("harvestcraft", "cocoapowderItem", 1L, 0));
/* 414:427 */     GT_OreDictUnificator.set(OrePrefixes.dust, Materials.Coffee, ItemList.IC2_CoffeePowder.get(1L, new Object[0]));
/* 415:    */   }
/* 416:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.preload.GT_Loader_Item_Block_And_Fluid
 * JD-Core Version:    0.7.0.1
 */