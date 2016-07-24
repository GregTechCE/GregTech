package gregtech.loaders.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.ThaumcraftApiHelper;

public class GT_Achievements {

    public static List<Materials> oreList = new ArrayList<Materials>();
    public static List<Integer[]> oreStats = new ArrayList<Integer[]>();
    public static int oreReg = -1;
    public HashMap<String, Achievement> achievementList;
    public HashMap<String, Boolean> issuedAchievements;
    public int adjX = 5;
    public int adjY = 9;

    public GT_Achievements() {
        this.achievementList = new HashMap();
        this.issuedAchievements = new HashMap();
        for (int i = 0; i < oreList.size(); i++) {
            if (GT_Values.D1 && this.achievementList.get(oreList.get(i).name()) == null) {
                GT_Log.out.println("achievement." + oreList.get(i).name() + "=Find " + oreList.get(i).name() + " Ore");

                StringBuilder dimensions = new StringBuilder();
                boolean isFirst = true;
                if(oreStats.get(i)[3] == 1) {
                    dimensions.append("Overworld");
                    isFirst = false;
                }
                if(oreStats.get(i)[4] == 1) {
                    if(!isFirst) dimensions.append("/");
                    dimensions.append("Nether");
                    isFirst = false;
                }
                if(oreStats.get(i)[5] == 1) {
                    if(!isFirst) dimensions.append("/");
                    dimensions.append("End");
                    isFirst = false;
                }
                GT_Log.out.println("achievement." + oreList.get(i).name() + ".desc=Height: " + (oreStats.get(i)[0]) + "-" + (oreStats.get(i)[1]) + ", Chance: " + (oreStats.get(i)[2]) + ", " + dimensions.toString());
            }
            registerOreAchievement(oreList.get(i));
        }
        registerAchievement("flintpick", 0, 0, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(2, 1, Materials.Flint, Materials.Wood, null), "", false);
        registerAchievement("crops", -4, 0, GT_ModHandler.getIC2Item("crop", 1L), "flintpick", false);
        registerAchievement("havestlead", -4, 2, ItemList.Crop_Drop_Plumbilia.get(1, new Object[]{}), "crops", false);
        registerAchievement("havestcopper", -2, 1, ItemList.Crop_Drop_Coppon.get(1, new Object[]{}), "crops", false);
        registerAchievement("havesttin", -2, -1, ItemList.Crop_Drop_Tine.get(1, new Object[]{}), "crops", false);
        registerAchievement("havestoil", -4, -4, ItemList.Crop_Drop_OilBerry.get(1, new Object[]{}), "crops", false);
        registerAchievement("havestiron", -2, -3, ItemList.Crop_Drop_Ferru.get(1, new Object[]{}), "crops", false);
        registerAchievement("havestgold", -2, -6, ItemList.Crop_Drop_Aurelia.get(1, new Object[]{}), "havestiron", false);
        registerAchievement("havestsilver", -4, -5, ItemList.Crop_Drop_Argentia.get(1, new Object[]{}), "havestiron", false);
        registerAchievement("havestemeralds", -2, -8, ItemList.Crop_Drop_BobsYerUncleRanks.get(1, new Object[]{}), "havestgold", false);

        registerAchievement("tools", 0, 4, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(12, 1, Materials.Iron, Materials.Wood, null), "flintpick", false);
        registerAchievement("driltime", 2, 4, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(100, 1, Materials.BlueSteel, Materials.StainlessSteel, null), "tools", false);
        registerAchievement("brrrr", 2, 6, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(110, 1, Materials.BlueSteel, Materials.StainlessSteel, null), "driltime", false);
        registerAchievement("highpowerdrill", 3, 5, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(104, 1, Materials.TungstenSteel, Materials.TungstenSteel, null), "driltime", false);
        registerAchievement("hammertime", 3, 7, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(130, 1, Materials.TungstenSteel, Materials.TungstenSteel, null), "highpowerdrill", false);
        registerAchievement("repair", 4, 5, ItemList.Machine_HV_Disassembler.get(1, new Object[]{}), "highpowerdrill", false);

        registerAchievement("unitool", -2, 4, GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(32, 1, Materials.Steel, Materials.Iron, null), "tools", false);
        registerAchievement("recycling", -4, 4, ItemList.Machine_LV_ArcFurnace.get(1, new Object[]{}), "unitool", false);

        registerAchievement("crushed", 0, 6, GT_OreDictUnificator.get(OrePrefixes.crushed, Materials.Tin, 1L), "tools", false);
        registerAchievement("cleandust", 0, 10, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), "crushed", false);
        registerAchievement("washing", -2, 6, GT_OreDictUnificator.get(OrePrefixes.crushedPurified, Materials.Iron, 1L), "crushed", false);
        registerAchievement("spinit", -4, 6, GT_OreDictUnificator.get(OrePrefixes.crushedCentrifuged, Materials.Redstone, 1L), "crushed", false);
        registerAchievement("newfuel", -4, 8, ItemList.ThoriumCell_4.get(1, new Object[]{}), "spinit", false);
        registerAchievement("newmetal", -4, 10, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Lutetium, 1L), "newfuel", false);
        registerAchievement("reflect", -2, 9, ItemList.Neutron_Reflector.get(1, new Object[]{}), "newfuel", false);

        registerAchievement("bronze", 2, 0, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 1L), "flintpick", false);
        registerAchievement("simplyeco", 2, 2, ItemList.Machine_Bronze_Boiler_Solar.get(1, new Object[]{}), "bronze", false);
        registerAchievement("firststeam", 2, -2, ItemList.Machine_Bronze_Boiler.get(1, new Object[]{}), "bronze", false);
        registerAchievement("alloysmelter", 2, -4, ItemList.Machine_Bronze_AlloySmelter.get(1, new Object[]{}), "firststeam", false);
        registerAchievement("macerator", 0, -2, ItemList.Machine_Bronze_Macerator.get(1, new Object[]{}), "firststeam", false);
        registerAchievement("extract", 0, -4, ItemList.Machine_Bronze_Extractor.get(1, new Object[]{}), "alloysmelter", false);

        registerAchievement("smallparts", 0, -8, ItemList.Circuit_Primitive.get(1, new Object[]{}), "extract", false);
        registerAchievement("bettercircuits", 0, -10, ItemList.Circuit_Good.get(1, new Object[]{}), "smallparts", false);
        registerAchievement("stepforward", -2, -10, ItemList.Circuit_Advanced.get(1, new Object[]{}), "bettercircuits", false);
        registerAchievement("energyflow", -4, -10, ItemList.Circuit_Master.get(1, new Object[]{}), "stepforward", false);
        registerAchievement("orbs", -6, -10, ItemList.Energy_LapotronicOrb.get(1, new Object[]{}), "energyflow", false);
        registerAchievement("thatspower", -8, -10, ItemList.Energy_LapotronicOrb2.get(1, new Object[]{}), "orbs", false);
        registerAchievement("datasaving", -2, -12, ItemList.Tool_DataOrb.get(1, new Object[]{}), "stepforward", false);
        registerAchievement("superbuffer", 0, -12, ItemList.Automation_SuperBuffer_LV.get(1, new Object[]{}), "datasaving", false);
        registerAchievement("newstorage", -2, -14, ItemList.Quantum_Chest_HV.get(1, new Object[]{}), "superbuffer", false);
        registerAchievement("whereistheocean", 2, -14, ItemList.Quantum_Tank_IV.get(1, new Object[]{}), "superbuffer", false);
        registerAchievement("luck", 2, -6, ItemList.ZPM.get(1, new Object[]{}), "", false);

        registerAchievement("steel", 4, 0, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), "bronze", false);
        registerAchievement("highpressure", 4, 2, ItemList.Machine_Steel_Boiler.get(1, new Object[]{}), "steel", false);
        registerAchievement("extremepressure", 4, 4, ItemList.Machine_Multi_LargeBoiler_Steel.get(1, new Object[]{}), "highpressure", false);
        registerAchievement("cheapermac", 6, 1, ItemList.Machine_LV_Hammer.get(1, new Object[]{}), "steel", false);
        registerAchievement("complexalloys", 6, 3, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.BlueSteel, 1L), "cheapermac", false);

        registerAchievement("magneticiron", 4, -2, GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L), "steel", false);
        registerAchievement("lvmotor", 4, -6, ItemList.Electric_Motor_LV.get(1, new Object[]{}), "magneticiron", false);
        registerAchievement("pumpcover", 2, -8, ItemList.Electric_Pump_LV.get(1, new Object[]{}), "lvmotor", false);
        registerAchievement("closeit", 2, -10, ItemList.Cover_Shutter.get(1, new Object[]{}), "pumpcover", false);
        registerAchievement("slurp", 2, -12, ItemList.Pump_HV.get(1, new Object[]{}), "closeit", false);
        registerAchievement("transport", 4, -10, ItemList.Conveyor_Module_LV.get(1, new Object[]{}), "lvmotor", false);
        registerAchievement("manipulation", 4, -12, ItemList.Cover_Controller.get(1, new Object[]{}), "transport", false);
        registerAchievement("buffer", 4, -14, ItemList.Automation_ChestBuffer_LV.get(1, new Object[]{}), "manipulation", false);
        registerAchievement("complexmachines", 6, -9, ItemList.Robot_Arm_LV.get(1, new Object[]{}), "lvmotor", false);
        registerAchievement("avengers", 8, -11, ItemList.Machine_LV_Assembler.get(1, new Object[]{}), "complexmachines", false);
        registerAchievement("filterregulate", 10, -11, ItemList.Component_Filter.get(1, new Object[]{}), "avengers", false);

        registerAchievement("steampower", 6, -6, ItemList.Generator_Steam_Turbine_LV.get(1, new Object[]{}), "lvmotor", false);
        registerAchievement("batterys", 6, -4, ItemList.Battery_Buffer_2by2_MV.get(1, new Object[]{}), "steampower", false);
        registerAchievement("badweather", 6, -8, ItemList.Casing_FireHazard.get(1, new Object[]{}), "steampower", false);
        registerAchievement("electricproblems", 7, -7, ItemList.Casing_ExplosionHazard.get(1, new Object[]{}), "steampower", false);
        registerAchievement("ebf", 8, -6, ItemList.Machine_Multi_BlastFurnace.get(1, new Object[]{}), "steampower", false);
        registerAchievement("energyhatch", 12, -6, ItemList.Hatch_Energy_LV.get(1, new Object[]{}), "ebf", false);

        registerAchievement("gtaluminium", 8, 0, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Aluminium, 1L), "steel", false);
        registerAchievement("highpowersmelt", 8, 2, ItemList.Machine_Multi_Furnace.get(1, new Object[]{}), "gtaluminium", false);
        registerAchievement("oilplant", 8, 4, ItemList.Distillation_Tower.get(1, new Object[]{}), "highpowersmelt", false);
        registerAchievement("factory", 8, 6, ItemList.Processing_Array.get(1, new Object[]{}), "oilplant", false);
        registerAchievement("upgradeebf", 8, -2, ItemList.Hatch_Energy_MV.get(1, new Object[]{}), "gtaluminium", false);
        registerAchievement("maintainance", 10, -2, ItemList.Hatch_Maintenance.get(1, new Object[]{}), "upgradeebf", false);

        registerAchievement("upgrade", 10, 0, ItemList.Casing_Coil_Kanthal.get(1, new Object[]{}), "gtaluminium", false);
        registerAchievement("titan", 14, 0, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Titanium, 1L), "upgrade", false);
        registerAchievement("magic", 14, 3, ItemList.MagicEnergyConverter_LV.get(1, new Object[]{}), "titan", false);
        registerAchievement("highmage", 10, 3, ItemList.MagicEnergyAbsorber_HV.get(1, new Object[]{}), "magic", false);
        registerAchievement("artificaldia", 12, 2, ItemList.IC2_Industrial_Diamond.get(1, new Object[]{}), "titan", false);
        registerAchievement("muchsteam", 13, 1, ItemList.LargeSteamTurbine.get(1, new Object[]{}), "titan", false);
        registerAchievement("efficientsteam", 11, 1, ItemList.LargeSteamTurbine.get(1, new Object[]{}), "muchsteam", false);

        registerAchievement("upgrade2", 16, 0, ItemList.Casing_Coil_Nichrome.get(1, new Object[]{}), "titan", false);
        registerAchievement("tungsten", 16, 2, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tungsten, 1L), "upgrade2", false);
        registerAchievement("osmium", 16, -2, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Osmium, 1L), "upgrade2", false);
        registerAchievement("hightech", 15, -3, ItemList.Field_Generator_LV.get(1, new Object[]{}), "osmium", false);
        registerAchievement("amplifier", 16, -5, ItemList.Machine_LV_Amplifab.get(1, new Object[]{}), "hightech", false);
        registerAchievement("scanning", 13, -3, ItemList.Machine_HV_Scanner.get(1, new Object[]{}), "hightech", false);
        registerAchievement("alienpower", 14, -5, ItemList.Generator_Naquadah_Mark_I.get(1, new Object[]{}), "hightech", false);
        registerAchievement("universal", 15, -6, ItemList.Machine_LV_Massfab.get(1, new Object[]{}), "hightech", false);
        registerAchievement("replication", 17, -6, ItemList.Machine_LV_Replicator.get(1, new Object[]{}), "universal", false);

        registerAchievement("tungstensteel", 16, 4, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.TungstenSteel, 1L), "tungsten", false);
        registerAchievement("upgrade3", 15, 5, ItemList.Casing_Coil_TungstenSteel.get(1, new Object[]{}), "tungstensteel", false);
        registerAchievement("hssg", 13, 5, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.HSSG, 1L), "upgrade3", false);
        registerAchievement("upgrade4", 11, 5, ItemList.Casing_Coil_HSSG.get(1, new Object[]{}), "hssg", false);
        registerAchievement("stargatematerial", 11, 7, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 1L), "upgrade4", false);
        registerAchievement("conducting", 14, 6, ItemList.Casing_Coil_Superconductor.get(1, new Object[]{}), "upgrade3", false);
        registerAchievement("fusion", 15, 7, ItemList.FusionComputer_LuV.get(1, new Object[]{}), "tungstensteel", false);
        registerAchievement("higherefficency", 15, 9, ItemList.Generator_Plasma_IV.get(1, new Object[]{}), "fusion", false);
        registerAchievement("advancing", 13, 7, ItemList.FusionComputer_ZPMV.get(1, new Object[]{}), "fusion", false);

        registerAchievement("stargateliquid", 11, 9, ItemList.Generator_Plasma_LuV.get(1, new Object[]{}), "advancing", false);
        registerAchievement("tothelimit", 13, 9, ItemList.Generator_Plasma_LuV.get(1, new Object[]{}), "advancing", false);
        registerAchievement("fullefficiency", 12, 10, ItemList.Generator_Plasma_ZPMV.get(1, new Object[]{}), "tothelimit", false);
        registerAchievement("upgrade5", 9, 9, ItemList.Casing_Coil_Naquadah.get(1, new Object[]{}), "stargateliquid", false);
        registerAchievement("alienmetallurgy", 9, 7, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahAlloy, 1L), "upgrade5", false);
        registerAchievement("over9000", 7, 7, ItemList.Casing_Coil_NaquadahAlloy.get(1, new Object[]{}), "alienmetallurgy", false);
        registerAchievement("finalpreparations", 7, 9, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadria, 1L), "over9000", false);
        registerAchievement("denseaspossible", 6, 10, ItemList.FusionComputer_UV.get(1, new Object[]{}), "finalpreparations", false);
        registerAchievement("whatnow", 4, 10, ItemList.ZPM2.get(1, new Object[]{}), "denseaspossible", false);

        if(Loader.isModLoaded("NotEnoughItems") && GT_Mod.gregtechproxy.mHideUnusedOres){
        for (int i = 1; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (GregTech_API.sGeneratedMaterials[i] != null) {
            	if(!oreList.contains(GregTech_API.sGeneratedMaterials[i])){
            		codechicken.nei.api.API.hideItem(GT_OreDictUnificator.get(OrePrefixes.ore, GregTech_API.sGeneratedMaterials[i], 1));
            	}
            }
        }
        }
        if (GT_Mod.gregtechproxy.mAchievements) {
            AchievementPage.registerAchievementPage(new AchievementPage("GregTech 5", (Achievement[]) this.achievementList.values().toArray(
                    new Achievement[this.achievementList.size()])));
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
        }
    }

    public static void registerOre(Materials aMaterial, int min, int max, int chance, boolean overworld, boolean nether, boolean end) {
        if (aMaterial != Materials._NULL) {
            oreList.add(aMaterial);
        }
        oreStats.add(new Integer[]{min, max, chance, overworld ? 1 : 0, nether ? 1 : 0, end ? 1 : 0});
    }

    public Achievement registerAchievement(String textId, int x, int y, ItemStack icon, Achievement requirement, boolean special) {
        if (!GT_Mod.gregtechproxy.mAchievements) {
            return null;
        }
        ;
        Achievement achievement = new Achievement(textId, textId, this.adjX + x, this.adjY + y, icon, requirement);
        if (special) {
            achievement.setSpecial();
        }
        achievement.registerStat();
        if (GT_Values.D2) {
            GT_Log.out.println("achievement." + textId + "=");
            GT_Log.out.println("achievement." + textId + ".desc=");
        }
        this.achievementList.put(textId, achievement);
        return achievement;
    }

    public Achievement registerAchievement(String textId, int x, int y, ItemStack icon, String requirement, boolean special) {
        if (!GT_Mod.gregtechproxy.mAchievements) {
            return null;
        }
        ;
        Achievement achievement = new Achievement(textId, textId, this.adjX + x, this.adjY + y, icon, getAchievement(requirement));
        if (special) {
            achievement.setSpecial();
        }
        achievement.registerStat();
        if (GT_Values.D2) {
            GT_Log.out.println("achievement." + textId + "=");
            GT_Log.out.println("achievement." + textId + ".desc=");
        }
        this.achievementList.put(textId, achievement);
        return achievement;
    }

    public Achievement registerOreAchievement(Materials aMaterial) {
        if (this.achievementList.get(aMaterial.name()) == null) {
            oreReg++;
            return registerAchievement(aMaterial.name(), -(6 + oreReg % 5), ((oreReg) / 5) - 8, new ItemStack(GregTech_API.sBlockOres1, 1,
                    aMaterial.mMetaItemSubID), AchievementList.openInventory, false);
        }
        return null;
    }

    public void issueAchievement(EntityPlayer entityplayer, String textId) {
        if (entityplayer == null || !GT_Mod.gregtechproxy.mAchievements) {
            return;
        }
//		if (this.achievementList.containsKey(textId)) {
//			if(this.issuedAchievements.containsKey((entityplayer.getDisplayName()+textId))){
//			return;
//			}else{
//			this.issuedAchievements.put((entityplayer.getDisplayName()+textId), true);
        entityplayer.triggerAchievement((StatBase) this.achievementList.get(textId));
//			}
//		}
    }

    public Achievement getAchievement(String textId) {
        if (this.achievementList.containsKey(textId)) {
            return (Achievement) this.achievementList.get(textId);
        }
        return null;
    }

    public void issueAchivementHatch(EntityPlayer player, ItemStack stack) {
        if (player == null || stack == null) {
            return;
        }
        ItemData data = GT_OreDictUnificator.getItemData(stack);

        if (data != null) {
            if (data.mPrefix == OrePrefixes.ingot) {
                if (data.mMaterial.mMaterial == Materials.Aluminium) {
                    issueAchievement(player, "gtaluminium");
                } else if (data.mMaterial.mMaterial == Materials.Titanium) {
                    issueAchievement(player, "titan");
                } else if (data.mMaterial.mMaterial == Materials.BlueSteel) {
                    issueAchievement(player, "complexalloys");
                } else if (data.mMaterial.mMaterial == Materials.Tungsten) {
                    issueAchievement(player, "tungsten");
                } else if (data.mMaterial.mMaterial == Materials.Osmium) {
                    issueAchievement(player, "osmium");
                } else if (data.mMaterial.mMaterial == Materials.TungstenSteel) {
                    issueAchievement(player, "tungstensteel");
                } else if (data.mMaterial.mMaterial == Materials.HSSG) {
                    issueAchievement(player, "hssg");
                } else if (data.mMaterial.mMaterial == Materials.Naquadah) {
                    issueAchievement(player, "stargatematerial");
                } else if (data.mMaterial.mMaterial == Materials.NaquadahAlloy) {
                    issueAchievement(player, "alienmetallurgy");
                } else if (data.mMaterial.mMaterial == Materials.Naquadria) {
                    issueAchievement(player, "finalpreparations");
                }
            }
        }
        if (stack.getUnlocalizedName().equals("ic2.itemPartIndustrialDiamond")) {
            issueAchievement(player, "artificaldia");
            issueAchievement(player, "buildCoalDiamond");

        }
    }

    public void issueAchivementHatchFluid(EntityPlayer player, FluidStack fluid) {
        if (player == null || fluid == null) {
            return;
        }
        if (fluid.getFluid().getUnlocalizedName().equals("fluid.plasma.helium")) {
            issueAchievement(player, "fusion");
        } else if (fluid.getFluid().getUnlocalizedName().equals("fluid.molten.europium")) {
            issueAchievement(player, "advancing");
        } else if (fluid.getFluid().getUnlocalizedName().equals("fluid.molten.naquadah")) {
            issueAchievement(player, "stargateliquid");
        } else if (fluid.getFluid().getUnlocalizedName().equals("fluid.molten.americium")) {
            issueAchievement(player, "tothelimit");
        } else if (fluid.getFluid().getUnlocalizedName().equals("fluid.molten.neutronium")) {
            issueAchievement(player, "denseaspossible");
        } else if (fluid.getFluid().getUnlocalizedName().equals("fluid.plasma.nitrogen")) {
            issueAchievement(player, "higherefficency");
        }
    }

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        ItemStack stack = event.crafting;
        if (player == null || stack == null) {
            return;
        }
        ItemData data = GT_OreDictUnificator.getItemData(stack);
        if (data != null) {
            if (data.mPrefix == OrePrefixes.dust && data.mMaterial.mMaterial == Materials.Bronze) {
                issueAchievement(player, "bronze");
            } else if (data.mPrefix == OrePrefixes.circuit && data.mMaterial.mMaterial == Materials.Advanced) {
                issueAchievement(player, "stepforward");
            }
        }
        if (stack.getUnlocalizedName().startsWith("gt.metaitem.")) {
            if (stack.getUnlocalizedName().equals("gt.metaitem.01.2300")) {
                issueAchievement(player, "bronze");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32700")) {
                issueAchievement(player, "smallparts");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.23354")) {
                issueAchievement(player, "magneticiron");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32600")) {
                issueAchievement(player, "lvmotor");
                issueAchievement(player, "buildCable");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32610")) {
                issueAchievement(player, "pumpcover");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32630")) {
                issueAchievement(player, "transport");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32650")) {
                issueAchievement(player, "complexmachines");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32670")) {
                issueAchievement(player, "hightech");
            }
        } else if (stack.getUnlocalizedName().equals("ic2.blockCrop")) {
            issueAchievement(player, "crops");
        } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.")) {
            if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.arcfurnace.tier.")) {
                issueAchievement(player, "recycling");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.disassembler.tier.")) {
                issueAchievement(player, "repair");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.boiler.solar")) {
                issueAchievement(player, "simplyeco");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.boiler.bronze")) {
                issueAchievement(player, "firststeam");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.boiler.steel")) {
                issueAchievement(player, "highpressure");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.bronzemachine.macerator")) {
                issueAchievement(player, "macerator");
                issueAchievement(player, "buildMacerator");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.bronzemachine.alloysmelter")) {
                issueAchievement(player, "alloysmelter");
                issueAchievement(player, "buildElecFurnace");
                if(stack.getUnlocalizedName().equals("gt.blockmachines.bronzemachine.alloysmelter.tier.3")){
                	issueAchievement(player, "buildIndFurnace");
                }
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.bronzemachine.extractor")) {
                issueAchievement(player, "extract");
                issueAchievement(player, "buildCompressor");
                issueAchievement(player, "buildExtractor");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.automation.superbuffer.tier.")) {
                issueAchievement(player, "superbuffer");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.quantum.tank.tier.")) {
                issueAchievement(player, "whereistheocean");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.quantum.chest.tier.")) {
                issueAchievement(player, "newstorage");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.hammer.tier.")) {
                issueAchievement(player, "cheapermac");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.automation.chestbuffer.tier.")) {
                issueAchievement(player, "buffer");
                issueAchievement(player, "buildBatBox");
                if(stack.getUnlocalizedName().startsWith("gt.blockmachines.automation.chestbuffer.tier.3")){
                	issueAchievement(player, "buildMFE");
                }
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicgenerator.steamturbine.tier.")) {
                issueAchievement(player, "steampower");
                issueAchievement(player, "buildGenerator");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.basicmachine.pump.tier.03")) {
                issueAchievement(player, "slurp");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.assembler.tier.")) {
                issueAchievement(player, "avengers");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.multimachine.blastfurnace")) {
                issueAchievement(player, "ebf");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.batterybuffer.")) {
                issueAchievement(player, "batterys");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.hatch.energy.tier.02")) {
                issueAchievement(player, "upgradeebf");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.multimachine.multifurnace")) {
                issueAchievement(player, "highpowersmelt");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.hatch.energy.tier.01")) {
                issueAchievement(player, "energyhatch");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.multimachine.processingarray")) {
                issueAchievement(player, "factory");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.basicgenerator.magicenergyconverter.tier.01")) {
                issueAchievement(player, "magic");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.basicgenerator.magicenergyabsorber.tier.03")) {
                issueAchievement(player, "highmage");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.amplifab.tier.")) {
                issueAchievement(player, "amplifier");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.massfab.tier.")) {
                issueAchievement(player, "universal");
                issueAchievement(player, "buildMassFab");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicgenerator.naquadah.tier.")) {
                issueAchievement(player, "alienpower");
            } else if (stack.getUnlocalizedName().startsWith("gt.blockmachines.basicmachine.replicator.tier.")) {
                issueAchievement(player, "replication");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.basicgenerator.plasmagenerator.tier.07")) {
                issueAchievement(player, "fullefficiency");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.multimachine.largeturbine")) {
                issueAchievement(player, "muchsteam");
            } else if (stack.getUnlocalizedName().equals("gt.blockmachines.multimachine.largehpturbine")) {
                issueAchievement(player, "efficientsteam");
            }
        } else if (stack.getUnlocalizedName().equals("gt.neutronreflector")) {
            issueAchievement(player, "reflect");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.1")) {
            issueAchievement(player, "upgrade");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.2")) {
            issueAchievement(player, "upgrade2");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.3")) {
            issueAchievement(player, "upgrade3");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.4")) {
            issueAchievement(player, "upgrade4");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.5")) {
            issueAchievement(player, "upgrade5");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings5.6")) {
            issueAchievement(player, "over9000");
        } else if (stack.getUnlocalizedName().equals("gt.blockcasings.15")) {
            issueAchievement(player, "conducting");
        }
    }

    @SubscribeEvent
    public void onSmelting(ItemSmeltedEvent event) {
        EntityPlayer player = event.player;
        ItemStack stack = event.smelting;
        if (player == null || stack == null) {
            return;
        }
        if (stack.getItem() == Items.bread) {
            event.player.triggerAchievement(AchievementList.makeBread);
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack stack = event.item.getEntityItem();
        if (player == null || stack == null) {
            return;
        }
        ItemData data = GT_OreDictUnificator.getItemData(stack);
        if (data != null) {
            if (data.mPrefix == OrePrefixes.dust) {
                if (data.mMaterial.mMaterial == Materials.Lutetium) {
                    issueAchievement(player, "newmetal");
                }
                if(data.mMaterial.mMaterial != Materials.Gunpowder){
                issueAchievement(player, "cleandust");
                }
            } else if (data.mPrefix == OrePrefixes.ore || data.mPrefix == OrePrefixes.oreBlackgranite || data.mPrefix == OrePrefixes.oreEndstone
                    || data.mPrefix == OrePrefixes.oreNetherrack || data.mPrefix == OrePrefixes.oreRedgranite) {
                for (int i = 0; i < data.getAllMaterialStacks().size(); i++) {
                    issueAchievement(player, data.getAllMaterialStacks().get(i).mMaterial.name());
                    if (data.getAllMaterialStacks().get(i).mMaterial == Materials.AnyIron) {
                        issueAchievement(player, "iron");
                    }
                if(data.getAllMaterialStacks().get(i).mMaterial == Materials.Copper||data.getAllMaterialStacks().get(i).mMaterial == Materials.Tin){
                	issueAchievement(event.entityPlayer, "mineOre");
                }

                }
            } else if (data.mPrefix == OrePrefixes.crushed) {
                issueAchievement(player, "crushed");
            } else if (data.mPrefix == OrePrefixes.crushedPurified) {
                issueAchievement(player, "washing");
            } else if (data.mPrefix == OrePrefixes.crushedCentrifuged) {
                issueAchievement(player, "spinit");
            } else if (data.mMaterial.mMaterial == Materials.Steel) {
                if (data.mPrefix == OrePrefixes.ingot && stack.stackSize == stack.getMaxStackSize()) {
                    issueAchievement(player, "steel");
                } else if (data.mPrefix == OrePrefixes.nugget && Loader.isModLoaded("Thaumcraft")) {
                    if (ThaumcraftApiHelper.isResearchComplete(player.getDisplayName(), "GT_IRON_TO_STEEL")) {
                        issueAchievement(player, "steel");
                    }
                }
            } else if (data.mPrefix == OrePrefixes.circuit && data.mMaterial.mMaterial == Materials.Advanced) {
                issueAchievement(player, "stepforward");
            }
        }
        if (stack.getUnlocalizedName().startsWith("gt.metaitem.")) {
            if (stack.getUnlocalizedName().equals("gt.metaitem.02.32500")) {
                issueAchievement(player, "havestlead");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32501")) {
                issueAchievement(player, "havestsilver");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32503")) {
                issueAchievement(player, "havestiron");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32504")) {
                issueAchievement(player, "havestgold");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32530")) {
                issueAchievement(player, "havestcopper");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32540")) {
                issueAchievement(player, "havesttin");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32510")) {
                issueAchievement(player, "havestoil");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.02.32511")) {
                issueAchievement(player, "havestemeralds");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32706")) {
                issueAchievement(player, "energyflow");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32702")) {
                issueAchievement(player, "bettercircuits");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32707")) {
                issueAchievement(player, "datasaving");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32597")) {
                issueAchievement(player, "orbs");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32599")) {
                issueAchievement(player, "thatspower");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32598")) {
                issueAchievement(player, "luck");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32749")) {
                issueAchievement(player, "closeit");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32730")) {
                issueAchievement(player, "manipulation");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32729")) {
                issueAchievement(player, "filterregulate");
            } else if (stack.getUnlocalizedName().equals("gt.metaitem.01.32605")) {
                issueAchievement(player, "whatnow");
            }
        } else if (stack.getUnlocalizedName().equals("gt.Thoriumcell")) {
                issueAchievement(player, "newfuel");
            }else if ((stack.getItem() == Ic2Items.quantumBodyarmor.getItem()) || (stack.getItem() == Ic2Items.quantumBoots.getItem()) ||
            		(stack.getItem() == Ic2Items.quantumHelmet.getItem()) || (stack.getItem() == Ic2Items.quantumLeggings.getItem())) {
                issueAchievement(player, "buildQArmor");}
    }
}
