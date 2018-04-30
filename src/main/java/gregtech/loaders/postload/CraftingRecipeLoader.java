package gregtech.loaders.postload;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTLog;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CraftingRecipeLoader {

    public static void init() {
        loadCraftingRecipes();
    }

    private static void loadCraftingRecipes() {

        ModHandler.addShapelessRecipe("chum_on_stick", MetaItems.FOOD_CHUM_ON_STICK.getStackForm(), new UnificationEntry(OrePrefix.stick, Materials.Wood), MetaItems.FOOD_CHUM);
        ModHandler.addShapedRecipe("rubber_ring", OreDictUnifier.get(OrePrefix.ring, Materials.Rubber), "k", "X", 'X', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

        ModHandler.addShapelessRecipe("iron_magnetic_stick", OreDictUnifier.get(OrePrefix.stick, Materials.IronMagnetic), new UnificationEntry(OrePrefix.stick, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone), new UnificationEntry(OrePrefix.dust, Materials.Redstone));
        ModHandler.addShapedRecipe("paper_ring", OreDictUnifier.get(OrePrefix.ring, Materials.Paper), "PPk", 'P', new UnificationEntry(OrePrefix.plate, Materials.Paper));

        ModHandler.addShapedRecipe("torch_sulfur", new ItemStack(Blocks.TORCH, 2), "C", "S", 'C', new UnificationEntry(OrePrefix.dust, Materials.Sulfur), 'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));
        ModHandler.addShapedRecipe("torch_phosphor", new ItemStack(Blocks.TORCH, 6), "C", "S", 'C', new UnificationEntry(OrePrefix.dust, Materials.Phosphorus), 'S', new UnificationEntry(OrePrefix.stick, Materials.Wood));

        ModHandler.addShapedRecipe("piston_bronze", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Bronze));
        ModHandler.addShapedRecipe("piston_aluminium", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Aluminium));
        ModHandler.addShapedRecipe("piston_steel", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Steel));
        ModHandler.addShapedRecipe("piston_titanium", new ItemStack(Blocks.PISTON, 1), "WWW", "CBC", "CRC", 'W', new UnificationEntry(OrePrefix.plank, Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', new UnificationEntry(OrePrefix.dust, Materials.Redstone), 'B', new UnificationEntry(OrePrefix.ingot, Materials.Titanium));

        GTLog.logger.info("Modifying vanilla recipes according to config. DON'T BE SCARED OF FML's WARNING ABOUT DANGEROUS ALTERNATIVE PREFIX.");

        if (ConfigHolder.vanillaRecipes.bucketRequirePlatesAndHammer) {
            ModHandler.addShapedRecipe("iron_bucket", new ItemStack(Items.BUCKET), "XhX", " X ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:bucket"));
        }
        if (ConfigHolder.vanillaRecipes.ironConsumingCraftingRecipesRequirePlates) {
            ModHandler.addShapedRecipe("iron_pressure_plate", new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:heavy_weighted_pressure_plate"));

            ModHandler.addShapedRecipe("gold_pressure_plate", new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Gold));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:light_weighted_pressure_plate"));

            ModHandler.addShapedRecipe("iron_door", new ItemStack(Items.IRON_DOOR, 3), "XX ", "XXh", "XX ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_door"));

            ModHandler.addShapedRecipe("iron_trapdor", new ItemStack(Blocks.IRON_TRAPDOOR), "XX ", "XXh", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_trapdor"));

            ModHandler.addShapedRecipe("cauldron", new ItemStack(Items.CAULDRON), "X X", "XhX", "XXX", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:cauldron"));

            ModHandler.addShapedRecipe("hopper", new ItemStack(Blocks.HOPPER), "XwX", "XCX", " X ", 'X', new UnificationEntry(OrePrefix.plate, Materials.Iron), 'C', "craftingChest");
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:hopper"));

            ModHandler.addShapedRecipe("iron_bars", new ItemStack(Blocks.IRON_BARS, 8), " w ", "XXX", "XXX", 'X', new UnificationEntry(OrePrefix.stick, Materials.Iron));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:iron_bars"));
        }

        if (ConfigHolder.vanillaRecipes.bowlRequireKnife) {
            ModHandler.addShapedRecipe("bowl", new ItemStack(Items.BOWL), "k", "X", 'X', new UnificationEntry(OrePrefix.plank, Materials.Wood));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:bowl"));
        }

        if (ConfigHolder.vanillaRecipes.nerfedStickCrafting) {
            ModHandler.addShapedRecipe("stick_1", new ItemStack(Items.STICK, 4), "s", "P", "P", 'P', new UnificationEntry(OrePrefix.plank, Materials.Wood));
            ModHandler.addShapedRecipe("stick_2", new ItemStack(Items.STICK, 2), "P", "P", 'P', new UnificationEntry(OrePrefix.plank, Materials.Wood));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:stick"));
        } else {
            ModHandler.addShapedRecipe("stick_1", new ItemStack(Items.STICK, 5), "s", "P", "P", 'P', new UnificationEntry(OrePrefix.plank, Materials.Wood));
        }

        if (!ConfigHolder.vanillaRecipes.steelRequireBlastFurnace) {
            ModHandler.addShapelessRecipe("steel_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Coal));
        }

//        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 3), "NPT", "CCC", "HPT", 'H', new UnificationEntry(OrePrefix.cell, Materials.Helium), 'N', new UnificationEntry(OrePrefix.cell, Materials.Nitrogen), 'T', new UnificationEntry(OrePrefix.pipeTiny, Materials.TungstenSteel), 'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.NiobiumTitanium));
//        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 3), "NPT", "CCC", "HPT", 'H', new UnificationEntry(OrePrefix.cell, Materials.Helium), 'N', new UnificationEntry(OrePrefix.cell, Materials.Nitrogen), 'T', new UnificationEntry(OrePrefix.pipeTiny, Materials.TungstenSteel), 'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.VanadiumGallium));
//        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 3), "NPT", "CCC", "NPT", 'N', new UnificationEntry(OrePrefix.cell, Materials.Nitrogen), 'T', new UnificationEntry(OrePrefix.pipeTiny, Materials.TungstenSteel), 'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.YttriumBariumCuprate));
//        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor, 3), "NPT", "CCC", "NPT", 'N', new UnificationEntry(OrePrefix.cell, Materials.Nitrogen), 'T', new UnificationEntry(OrePrefix.pipeTiny, Materials.TungstenSteel), 'P', MetaItems.ELECTRIC_PUMP_LV, 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Naquadah));
//
//        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Saltpeter, 5), new UnificationEntry(OrePrefix.dust, Materials.Potassium), new UnificationEntry(OrePrefix.cell, Materials.Nitrogen), new UnificationEntry(OrePrefix.cell, Materials.Oxygen), new UnificationEntry(OrePrefix.cell, Materials.Oxygen), new UnificationEntry(OrePrefix.cell, Materials.Oxygen));

        if (ConfigHolder.vanillaRecipes.nerfedDustCrafting) {
            ModHandler.addShapelessRecipe("dustsmall_electrum", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Electrum, 6), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Gold));
            ModHandler.addShapelessRecipe("dust_brass", OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 3), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
            ModHandler.addShapelessRecipe("dustsmall_brass", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Brass, 9), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
            ModHandler.addShapelessRecipe("dust_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 3), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Tin));
            ModHandler.addShapelessRecipe("dustsmall_bronze", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bronze, 9), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tin));
            ModHandler.addShapelessRecipe("dustsmall_invar", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Invar, 9), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Nickel));
            ModHandler.addShapelessRecipe("dustsmall_cupronickel", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Cupronickel, 6), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Copper));
            ModHandler.addShapelessRecipe("dustsmall_nichrome", OreDictUnifier.get(OrePrefix.dustSmall, Materials.Nichrome, 1), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        } else {
            ModHandler.addShapelessRecipe("dust_electrum", OreDictUnifier.get(OrePrefix.dust, Materials.Electrum, 2), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Gold));
            ModHandler.addShapelessRecipe("dust_brass", OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 4), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
            ModHandler.addShapelessRecipe("dust_brass", OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 3), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Zinc));
            ModHandler.addShapelessRecipe("dust_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 4), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Tin));
            ModHandler.addShapelessRecipe("dust_bronze", OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 3), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tetrahedrite), new UnificationEntry(OrePrefix.dust, Materials.Tin));
            ModHandler.addShapelessRecipe("dust_invar", OreDictUnifier.get(OrePrefix.dust, Materials.Invar, 3), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Nickel));
            ModHandler.addShapelessRecipe("dust_cupronickel", OreDictUnifier.get(OrePrefix.dust, Materials.Cupronickel, 2), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Copper));
            ModHandler.addShapelessRecipe("dust_nichrome", OreDictUnifier.get(OrePrefix.dust, Materials.Nichrome, 5), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        }
        ModHandler.addShapelessRecipe("dust_rosegold", OreDictUnifier.get(OrePrefix.dust, Materials.RoseGold, 5), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_sterlingsilver", OreDictUnifier.get(OrePrefix.dust, Materials.SterlingSilver, 5), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_blackbronze", OreDictUnifier.get(OrePrefix.dust, Materials.BlackBronze, 5), new UnificationEntry(OrePrefix.dust, Materials.Gold), new UnificationEntry(OrePrefix.dust, Materials.Silver), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_bismuthbronze", OreDictUnifier.get(OrePrefix.dust, Materials.BismuthBronze, 5), new UnificationEntry(OrePrefix.dust, Materials.Bismuth), new UnificationEntry(OrePrefix.dust, Materials.Zinc), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_blacksteel", OreDictUnifier.get(OrePrefix.dust, Materials.BlackSteel, 5), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.BlackBronze), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel));
        ModHandler.addShapelessRecipe("dust_redsteel", OreDictUnifier.get(OrePrefix.dust, Materials.RedSteel, 8), new UnificationEntry(OrePrefix.dust, Materials.SterlingSilver), new UnificationEntry(OrePrefix.dust, Materials.BismuthBronze), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel));
        ModHandler.addShapelessRecipe("dust_bluesteel", OreDictUnifier.get(OrePrefix.dust, Materials.BlueSteel, 8), new UnificationEntry(OrePrefix.dust, Materials.RoseGold), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel), new UnificationEntry(OrePrefix.dust, Materials.BlackSteel));

        ModHandler.addShapelessRecipe("dust_ultimet", OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 9), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum));
        ModHandler.addShapelessRecipe("dust_cobaltbrass", OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 9), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Brass), new UnificationEntry(OrePrefix.dust, Materials.Aluminium), new UnificationEntry(OrePrefix.dust, Materials.Cobalt));
        ModHandler.addShapelessRecipe("dust_stainlesssteel", OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 9), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Nickel), new UnificationEntry(OrePrefix.dust, Materials.Manganese), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        ModHandler.addShapelessRecipe("dust_yttriumbariumcuprate", OreDictUnifier.get(OrePrefix.dust, Materials.YttriumBariumCuprate, 6), new UnificationEntry(OrePrefix.dust, Materials.Yttrium), new UnificationEntry(OrePrefix.dust, Materials.Barium), new UnificationEntry(OrePrefix.dust, Materials.Barium), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper), new UnificationEntry(OrePrefix.dust, Materials.Copper));
        ModHandler.addShapelessRecipe("dust_kanthal", OreDictUnifier.get(OrePrefix.dust, Materials.Kanthal, 3), new UnificationEntry(OrePrefix.dust, Materials.Iron), new UnificationEntry(OrePrefix.dust, Materials.Aluminium), new UnificationEntry(OrePrefix.dust, Materials.Chrome));

        ModHandler.addShapelessRecipe("dust_ultimet", OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome), new UnificationEntry(OrePrefix.dustTiny, Materials.Nickel), new UnificationEntry(OrePrefix.dustTiny, Materials.Molybdenum));
        ModHandler.addShapelessRecipe("dust_cobaltbrass", OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Brass), new UnificationEntry(OrePrefix.dustTiny, Materials.Aluminium), new UnificationEntry(OrePrefix.dustTiny, Materials.Cobalt));
        ModHandler.addShapelessRecipe("dust_stainlesssteel", OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 1), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Nickel), new UnificationEntry(OrePrefix.dustTiny, Materials.Manganese), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome));
        ModHandler.addShapelessRecipe("dusttiny_yttriumbariumcuprate", OreDictUnifier.get(OrePrefix.dustTiny, Materials.YttriumBariumCuprate, 6), new UnificationEntry(OrePrefix.dustTiny, Materials.Yttrium), new UnificationEntry(OrePrefix.dustTiny, Materials.Barium), new UnificationEntry(OrePrefix.dustTiny, Materials.Barium), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper), new UnificationEntry(OrePrefix.dustTiny, Materials.Copper));
        ModHandler.addShapelessRecipe("dusttiny_kanthal", OreDictUnifier.get(OrePrefix.dustTiny, Materials.Kanthal, 3), new UnificationEntry(OrePrefix.dustTiny, Materials.Iron), new UnificationEntry(OrePrefix.dustTiny, Materials.Aluminium), new UnificationEntry(OrePrefix.dustTiny, Materials.Chrome));

        ModHandler.addShapelessRecipe("dust_vanadiumsteel", OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumSteel, 9), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Steel), new UnificationEntry(OrePrefix.dust, Materials.Vanadium), new UnificationEntry(OrePrefix.dust, Materials.Chrome));
        ModHandler.addShapelessRecipe("dust_hssg", OreDictUnifier.get(OrePrefix.dust, Materials.HSSG, 9), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.TungstenSteel), new UnificationEntry(OrePrefix.dust, Materials.Chrome), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum), new UnificationEntry(OrePrefix.dust, Materials.Molybdenum), new UnificationEntry(OrePrefix.dust, Materials.Vanadium));
        ModHandler.addShapelessRecipe("dust_hsse", OreDictUnifier.get(OrePrefix.dust, Materials.HSSE, 9), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.Cobalt), new UnificationEntry(OrePrefix.dust, Materials.Manganese), new UnificationEntry(OrePrefix.dust, Materials.Silicon));
        ModHandler.addShapelessRecipe("dust_hsss", OreDictUnifier.get(OrePrefix.dust, Materials.HSSS, 9), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.HSSG), new UnificationEntry(OrePrefix.dust, Materials.Iridium), new UnificationEntry(OrePrefix.dust, Materials.Iridium), new UnificationEntry(OrePrefix.dust, Materials.Osmium));

        ModHandler.addShapelessRecipe("powder_coal", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Coal), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));
        ModHandler.addShapelessRecipe("powder_charcoal", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Charcoal), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));
        ModHandler.addShapelessRecipe("powder_carbon", new ItemStack(Items.GUNPOWDER, 6), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Carbon), new UnificationEntry(OrePrefix.dust, Materials.Sulfur), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter), new UnificationEntry(OrePrefix.dust, Materials.Saltpeter));

        if (ConfigHolder.vanillaRecipes.nerfedPaperCrafting) {
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:paper"));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:sugar"));
            ModHandler.addShapedRecipe("paper_dust", OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 2), "SSS", " m ", 'S', new ItemStack(Items.REEDS));
            ModHandler.addShapedRecipe("sugar", OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), "Sm ", 'S', new ItemStack(Items.REEDS));
            ModHandler.addShapedRecipe("paper", OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2), " C ", "SSS", " C ", 'S', OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB));
        }

        if (ConfigHolder.vanillaRecipes.flintAndSteelRequireSteel) {
            ModHandler.addShapedRecipe("flint_and_steel", new ItemStack(Items.FLINT_AND_STEEL), "S ", " F", 'F', new ItemStack(Items.FLINT, 1), 'S', new UnificationEntry(OrePrefix.nugget, Materials.Steel));
            ModHandler.removeRecipeByName(new ResourceLocation("minecraft:flint_and_steel"));
        }
    }
}
