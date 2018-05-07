package gregtech.loaders.load;

import gregtech.api.GTValues;
import gregtech.api.items.OreDictNames;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static gregtech.api.GTValues.W;
import static gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType.*;
import static gregtech.common.blocks.BlockMachineCasing.MachineCasingType.*;
import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.*;
import static gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType.ASSEMBLER_CASING;
import static gregtech.common.blocks.BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING;
import static gregtech.common.blocks.BlockTurbineCasing.TurbineCasingType.*;
import static gregtech.common.blocks.BlockWarningSign.SignType.*;
import static gregtech.common.blocks.BlockWireCoil.CoilType.*;

public class MetaTileEntityLoader {

    public static void init() {

        ModHandler.addShapedRecipe("casing_ulv", MetaBlocks.MACHINE_CASING.getItemVariant(ULV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron));
        ModHandler.addShapedRecipe("casing_lv", MetaBlocks.MACHINE_CASING.getItemVariant(LV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel));
        ModHandler.addShapedRecipe("casing_mv", MetaBlocks.MACHINE_CASING.getItemVariant(MV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium));
        ModHandler.addShapedRecipe("casing_hv", MetaBlocks.MACHINE_CASING.getItemVariant(HV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel));
        ModHandler.addShapedRecipe("casing_ev", MetaBlocks.MACHINE_CASING.getItemVariant(EV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium));
        ModHandler.addShapedRecipe("casing_iv", MetaBlocks.MACHINE_CASING.getItemVariant(IV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel));
        ModHandler.addShapedRecipe("casing_luv", MetaBlocks.MACHINE_CASING.getItemVariant(LuV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Chrome));
        ModHandler.addShapedRecipe("casing_zpm", MetaBlocks.MACHINE_CASING.getItemVariant(ZPM), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Iridium));
        ModHandler.addShapedRecipe("casing_uv", MetaBlocks.MACHINE_CASING.getItemVariant(UV), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Osmium));
        ModHandler.addShapedRecipe("casing_max", MetaBlocks.MACHINE_CASING.getItemVariant(MAX), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Darmstadtium));

        ModHandler.addShapedRecipe("casing_bronze_bricks", MetaBlocks.METAL_CASING.getItemVariant(BRONZE_BRICKS, 2), "PhP", "PBP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'B', new ItemStack(Blocks.BRICK_BLOCK, 1));
        ModHandler.addShapedRecipe("casing_steel_solid", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Steel));
        ModHandler.addShapedRecipe("casing_titanium_stable", MetaBlocks.METAL_CASING.getItemVariant(TITANIUM_STABLE, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium));
        ModHandler.addShapedRecipe("casing_invar_heatproof", MetaBlocks.METAL_CASING.getItemVariant(INVAR_HEATPROOF, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Invar), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Invar));
        ModHandler.addShapedRecipe("casing_aluminium_frostproof", MetaBlocks.METAL_CASING.getItemVariant(ALUMINIUM_FROSTPROOF, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Aluminium));
        ModHandler.addShapedRecipe("casing_stainless_clean", MetaBlocks.METAL_CASING.getItemVariant(STAINLESS_CLEAN, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.StainlessSteel));
        ModHandler.addShapedRecipe("casing_tungstensteel_robust", MetaBlocks.METAL_CASING.getItemVariant(TUNGSTENSTEEL_ROBUST, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel));

        ModHandler.addShapedRecipe("casing_steel_turbine_casing", MetaBlocks.TURBINE_CASING.getItemVariant(STEEL_TURBINE_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Magnalium), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.BlueSteel));
        ModHandler.addShapedRecipe("casing_stainless_turbine_casing", MetaBlocks.TURBINE_CASING.getItemVariant(STAINLESS_TURBINE_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel), 'F', MetaBlocks.TURBINE_CASING.getItemVariant(STEEL_TURBINE_CASING));
        ModHandler.addShapedRecipe("casing_titanium_turbine_casing", MetaBlocks.TURBINE_CASING.getItemVariant(TITANIUM_TURBINE_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium), 'F', MetaBlocks.TURBINE_CASING.getItemVariant(STEEL_TURBINE_CASING));
        ModHandler.addShapedRecipe("casing_tungstensteel_turbine_casing", MetaBlocks.TURBINE_CASING.getItemVariant(TUNGSTENSTEEL_TURBINE_CASING, 2), "PhP", "PFP", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel), 'F', MetaBlocks.TURBINE_CASING.getItemVariant(STEEL_TURBINE_CASING));

        ModHandler.addShapedRecipe("casing_bronze_pipe", MetaBlocks.BOILER_CASING.getItemVariant(BRONZE_PIPE, 2), "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Bronze), 'I', new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze));
        ModHandler.addShapedRecipe("casing_steel_pipe", MetaBlocks.BOILER_CASING.getItemVariant(STEEL_PIPE, 2), "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Steel), 'I', new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel));
        ModHandler.addShapedRecipe("casing_titanium_pipe", MetaBlocks.BOILER_CASING.getItemVariant(TITANIUM_PIPE, 2), "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium), 'I', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium));
        ModHandler.addShapedRecipe("casing_tungstensteel_pipe", MetaBlocks.BOILER_CASING.getItemVariant(TUNGSTENSTEEL_PIPE, 2), "PIP", "IFI", "PIP", 'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel), 'I', new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel));
        ModHandler.addShapedRecipe("casing_bronze_firebox", MetaBlocks.BOILER_CASING.getItemVariant(BRONZE_FIREBOX, 2), "PSP", "SFS", "PSP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Bronze), 'S', new UnificationEntry(OrePrefix.stick, Materials.Bronze));
        ModHandler.addShapedRecipe("casing_steel_firebox", MetaBlocks.BOILER_CASING.getItemVariant(STEEL_FIREBOX, 2), "PSP", "SFS", "PSP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Steel), 'S', new UnificationEntry(OrePrefix.stick, Materials.Steel));
        ModHandler.addShapedRecipe("casing_titanium_firebox", MetaBlocks.BOILER_CASING.getItemVariant(TITANIUM_FIREBOX, 2), "PSP", "SFS", "PSP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium), 'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium));
        ModHandler.addShapedRecipe("casing_tungstensteel_firebox", MetaBlocks.BOILER_CASING.getItemVariant(TUNGSTENSTEEL_FIREBOX, 2), "PSP", "SFS", "PSP", 'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel), 'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel));

        ModHandler.addShapedRecipe("casing_bronze_gearbox", MetaBlocks.TURBINE_CASING.getItemVariant(BRONZE_GEARBOX, 2), "PhP", "GFG", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Bronze), 'G', new UnificationEntry(OrePrefix.gear, Materials.Bronze));
        ModHandler.addShapedRecipe("casing_steel_gearbox", MetaBlocks.TURBINE_CASING.getItemVariant(STEEL_GEARBOX, 2), "PhP", "GFG", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Steel), 'G', new UnificationEntry(OrePrefix.gear, Materials.Steel));
        ModHandler.addShapedRecipe("casing_titanium_gearbox", MetaBlocks.TURBINE_CASING.getItemVariant(TITANIUM_GEARBOX, 2), "PhP", "GFG", "PwP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium), 'G', new UnificationEntry(OrePrefix.gear, Materials.Titanium));

        ModHandler.addShapedRecipe("casing_grate_casing", MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(GRATE_CASING, 2), "PVP", "PFP", "PMP", 'P', new ItemStack(Blocks.IRON_BARS, 1), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.Steel), 'M', MetaItems.ELECTRIC_MOTOR_MV, 'V', new UnificationEntry(OrePrefix.rotor, Materials.Steel));
        ModHandler.addShapedRecipe("casing_assembler_casing", MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(ASSEMBLER_CASING, 2), "PVP", "PFP", "PMP", 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data), 'F', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel), 'M', MetaItems.ELECTRIC_MOTOR_IV, 'V', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite));

        ModHandler.addShapedRecipe("wire_coil_cupronickel", MetaBlocks.WIRE_COIL.getItemVariant(CUPRONICKEL), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.Cupronickel));
        ModHandler.addShapedRecipe("wire_coil_kanthal", MetaBlocks.WIRE_COIL.getItemVariant(KANTHAL), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.Kanthal));
        ModHandler.addShapedRecipe("wire_coil_nichrome", MetaBlocks.WIRE_COIL.getItemVariant(NICHROME), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.Nichrome));
        ModHandler.addShapedRecipe("wire_coil_tungstensteel", MetaBlocks.WIRE_COIL.getItemVariant(TUNGSTENSTEEL), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.TungstenSteel));
        ModHandler.addShapedRecipe("wire_coil_hss_g", MetaBlocks.WIRE_COIL.getItemVariant(HSS_G), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.HSSG));
        ModHandler.addShapedRecipe("wire_coil_naquadah", MetaBlocks.WIRE_COIL.getItemVariant(NAQUADAH), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.Naquadah));
        ModHandler.addShapedRecipe("wire_coil_naquadah_alloy", MetaBlocks.WIRE_COIL.getItemVariant(NAQUADAH_ALLOY), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, Materials.NaquadahAlloy));
        ModHandler.addShapedRecipe("wire_coil_superconductor", MetaBlocks.WIRE_COIL.getItemVariant(SUPERCONDUCTOR), "PPP", "PwP", "PPP", 'P', new UnificationEntry(OrePrefix.wireGtDouble, MarkerMaterials.Tier.Superconductor));

        ModHandler.addShapedRecipe("warning_sign_yellow_stripes", MetaBlocks.WARNING_SIGN.getItemVariant(YELLOW_STRIPES), "Y  ", " M ", "  B", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_small_yellow_stripes", MetaBlocks.WARNING_SIGN.getItemVariant(SMALL_YELLOW_STRIPES), "  Y", " M ", "B  ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_radioactive_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(RADIOACTIVE_HAZARD), " YB", " M ", "   ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_bio_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(BIO_HAZARD), " Y ", " MB", "   ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_explosion_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(EXPLOSION_HAZARD), " Y ", " M ", "  B", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_fire_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(FIRE_HAZARD), " Y ", " M ", " B ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_acid_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(ACID_HAZARD), " Y ", " M ", "B  ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_magic_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(MAGIC_HAZARD), " Y ", "BM ", "   ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_frost_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(FROST_HAZARD), "BY ", " M ", "   ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");
        ModHandler.addShapedRecipe("warning_sign_noise_hazard", MetaBlocks.WARNING_SIGN.getItemVariant(NOISE_HAZARD), "   ", " M ", "BY ", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'Y', "dyeYellow", 'B', "dyeBlack");

        ModHandler.addShapelessRecipe("yellow_stripes_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(YELLOW_STRIPES));
        ModHandler.addShapelessRecipe("small_yellow_stripes_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(SMALL_YELLOW_STRIPES));
        ModHandler.addShapelessRecipe("radioactive_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(RADIOACTIVE_HAZARD));
        ModHandler.addShapelessRecipe("bio_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(BIO_HAZARD));
        ModHandler.addShapelessRecipe("explosion_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(EXPLOSION_HAZARD));
        ModHandler.addShapelessRecipe("fire_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(FIRE_HAZARD));
        ModHandler.addShapelessRecipe("acid_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(ACID_HAZARD));
        ModHandler.addShapelessRecipe("magic_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(MAGIC_HAZARD));
        ModHandler.addShapelessRecipe("frost_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(FROST_HAZARD));
        ModHandler.addShapelessRecipe("noise_hazard_2_steel_solid_casing", MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), MetaBlocks.WARNING_SIGN.getItemVariant(NOISE_HAZARD));

        if (ConfigHolder.harderMachineHulls) {
            ModHandler.addShapedRecipe("hull_ulv", MetaTileEntities.HULL[GTValues.ULV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(ULV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead), 'H', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron), 'P', new UnificationEntry(OrePrefix.plate, Materials.Wood));
            ModHandler.addShapedRecipe("hull_lv", MetaTileEntities.HULL[GTValues.LV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(LV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin), 'H', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'P', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron));
            ModHandler.addShapedRecipe("hull_mv", MetaTileEntities.HULL[GTValues.MV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(MV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper), 'H', new UnificationEntry(OrePrefix.plate, Materials.Aluminium), 'P', new UnificationEntry(OrePrefix.plate, Materials.WroughtIron));
            ModHandler.addShapedRecipe("hull_hv", MetaTileEntities.HULL[GTValues.HV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(HV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'H', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel), 'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));
            ModHandler.addShapedRecipe("hull_ev", MetaTileEntities.HULL[GTValues.EV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(EV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'H', new UnificationEntry(OrePrefix.plate, Materials.Titanium), 'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));
            ModHandler.addShapedRecipe("hull_iv", MetaTileEntities.HULL[GTValues.IV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(IV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten), 'H', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel), 'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));
            ModHandler.addShapedRecipe("hull_luv", MetaTileEntities.HULL[GTValues.LuV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(LuV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium), 'H', new UnificationEntry(OrePrefix.plate, Materials.Chrome), 'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));
            ModHandler.addShapedRecipe("hull_zpm", MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(ZPM), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah), 'H', new UnificationEntry(OrePrefix.plate, Materials.Iridium), 'P', new UnificationEntry(OrePrefix.plate, Materials.Polytetrafluoroethylene));
            ModHandler.addShapedRecipe("hull_uv", MetaTileEntities.HULL[GTValues.UV].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(UV), 'C', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy), 'H', new UnificationEntry(OrePrefix.plate, Materials.Osmium), 'P', new UnificationEntry(OrePrefix.plate, Materials.Polytetrafluoroethylene));
            ModHandler.addShapedRecipe("hull_max", MetaTileEntities.HULL[GTValues.MAX].getStackForm(), "PHP", "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(MAX), 'C', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor), 'H', new UnificationEntry(OrePrefix.plate, Materials.Darmstadtium), 'P', new UnificationEntry(OrePrefix.plate, Materials.Polytetrafluoroethylene));
        } else {
            ModHandler.addShapedRecipe("hull_ulv", MetaTileEntities.HULL[GTValues.ULV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(ULV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead));
            ModHandler.addShapedRecipe("hull_lv", MetaTileEntities.HULL[GTValues.LV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(LV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
            ModHandler.addShapedRecipe("hull_mv", MetaTileEntities.HULL[GTValues.MV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(MV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
            ModHandler.addShapedRecipe("hull_hv", MetaTileEntities.HULL[GTValues.HV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(HV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold));
            ModHandler.addShapedRecipe("hull_ev", MetaTileEntities.HULL[GTValues.EV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(EV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium));
            ModHandler.addShapedRecipe("hull_iv", MetaTileEntities.HULL[GTValues.IV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(IV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten));
            ModHandler.addShapedRecipe("hull_luv", MetaTileEntities.HULL[GTValues.LuV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(LuV), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium));
            ModHandler.addShapedRecipe("hull_zpm", MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(ZPM), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah));
            ModHandler.addShapedRecipe("hull_uv", MetaTileEntities.HULL[GTValues.UV].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(UV), 'C', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy));
            ModHandler.addShapedRecipe("hull_max", MetaTileEntities.HULL[GTValues.MAX].getStackForm(), "CMC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(MAX), 'C', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor));
        }

        ModHandler.addShapedRecipe("transformer_lv", MetaTileEntities.TRANSFORMER[GTValues.LV - 1].getStackForm(), " BB", "CM ", " BB", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead));
        ModHandler.addShapedRecipe("transformer_mv", MetaTileEntities.TRANSFORMER[GTValues.MV - 1].getStackForm(), " BB", "CM ", " BB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
        ModHandler.addShapedRecipe("transformer_hv", MetaTileEntities.TRANSFORMER[GTValues.HV - 1].getStackForm(), " BB", "CM ", " BB", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("transformer_ev", MetaTileEntities.TRANSFORMER[GTValues.EV - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'K', MetaItems.CIRCUIT_ADVANCED);
        ModHandler.addShapedRecipe("transformer_iv", MetaTileEntities.TRANSFORMER[GTValues.IV - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'K', MetaItems.CIRCUIT_ADVANCED);
        ModHandler.addShapedRecipe("transformer_luv", MetaTileEntities.TRANSFORMER[GTValues.LuV - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten), 'K', MetaItems.CIRCUIT_ELITE);
        ModHandler.addShapedRecipe("transformer_zpm", MetaTileEntities.TRANSFORMER[GTValues.ZPM - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium), 'K', MetaItems.CIRCUIT_ELITE);
        ModHandler.addShapedRecipe("transformer_uv", MetaTileEntities.TRANSFORMER[GTValues.UV - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy), 'B', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah), 'K', MetaItems.CIRCUIT_MASTER);
        ModHandler.addShapedRecipe("transformer_max", MetaTileEntities.TRANSFORMER[GTValues.MAX - 1].getStackForm(), "KBB", "CM ", "KBB", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor), 'B', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy), 'K', MetaItems.CIRCUIT_MASTER);

        ModHandler.addShapedRecipe("energy_output_hatch_ulv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.ULV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead));
        ModHandler.addShapedRecipe("energy_output_hatch_lv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.LV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
        ModHandler.addShapedRecipe("energy_output_hatch_mv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.MV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("energy_output_hatch_hv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.HV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold));
        ModHandler.addShapedRecipe("energy_output_hatch_ev", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.EV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium));
        ModHandler.addShapedRecipe("energy_output_hatch_iv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.IV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten));
        ModHandler.addShapedRecipe("energy_output_hatch_luv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.LuV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium));
        ModHandler.addShapedRecipe("energy_output_hatch_zpm", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.ZPM].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah));
        ModHandler.addShapedRecipe("energy_output_hatch_uv", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.UV].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy));
        ModHandler.addShapedRecipe("energy_output_hatch_max", MetaTileEntities.ENERGY_OUTPUT_HATCH[GTValues.MAX].getStackForm(), " MC", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor));

        ModHandler.addShapedRecipe("energy_input_hatch_ulv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.ULV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead));
        ModHandler.addShapedRecipe("energy_input_hatch_lv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
        ModHandler.addShapedRecipe("energy_input_hatch_mv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("energy_input_hatch_hv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.HV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold));
        ModHandler.addShapedRecipe("energy_input_hatch_ev", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.EV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium));
        ModHandler.addShapedRecipe("energy_input_hatch_iv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.IV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tungsten));
        ModHandler.addShapedRecipe("energy_input_hatch_luv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LuV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.VanadiumGallium));
        ModHandler.addShapedRecipe("energy_input_hatch_zpm", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.ZPM].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah));
        ModHandler.addShapedRecipe("energy_input_hatch_uv", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.UV].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy));
        ModHandler.addShapedRecipe("energy_input_hatch_max", MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.MAX].getStackForm(), "CM ", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'C', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor));

        ModHandler.addShapedRecipe("fluid_import_hatch_ulv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.ULV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_lv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_mv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.MV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_hv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.HV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_ev", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.EV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_iv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.IV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_luv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.LuV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_zpm", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.ZPM].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_uv", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.UV].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_import_hatch_max", MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.MAX].getStackForm(), "G", "M", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'G', new ItemStack(Blocks.GLASS));

        ModHandler.addShapedRecipe("fluid_export_hatch_ulv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.ULV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_lv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_mv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.MV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_hv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.HV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_ev", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.EV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_iv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.IV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("fluid_export_hatch_luv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.LuV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_export_hatch_zpm", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.ZPM].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_export_hatch_uv", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.UV].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("fluid_export_hatch_max", MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.MAX].getStackForm(), "M", "G", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'G', new ItemStack(Blocks.GLASS));

        ModHandler.addShapedRecipe("item_import_bus_ulv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ULV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_lv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_mv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.MV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_hv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.HV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_ev", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.EV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_iv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.IV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_luv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LuV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_zpm", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ZPM].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_uv", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.UV].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_import_bus_max", MetaTileEntities.ITEM_IMPORT_BUS[GTValues.MAX].getStackForm(), "C", "M", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'C', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("item_export_bus_ulv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.ULV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_lv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_mv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.MV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_hv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.HV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_ev", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.EV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_iv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.IV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_luv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LuV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_zpm", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.ZPM].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_uv", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.UV].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("item_export_bus_max", MetaTileEntities.ITEM_EXPORT_BUS[GTValues.MAX].getStackForm(), "M", "C", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'C', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("battery_buffer_ulv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.ULV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Lead), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_lv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.LV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_mv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.MV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Copper), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_hv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.HV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Gold), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_ev_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.EV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Aluminium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_iv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.IV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tungsten), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_luv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.LuV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.VanadiumGallium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_zpm_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.ZPM][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Naquadah), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_uv_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.UV][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.NaquadahAlloy), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_max_1x1", MetaTileEntities.BATTERY_BUFFER[GTValues.MAX][0].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor), 'T', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("battery_buffer_ulv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.ULV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Lead), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_lv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.LV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Tin), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_mv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.MV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_hv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.HV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Gold), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_ev_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.EV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Aluminium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_iv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.IV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Tungsten), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_luv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.LuV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.VanadiumGallium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_zpm_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.ZPM][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Naquadah), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_uv_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.UV][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_max_2x2", MetaTileEntities.BATTERY_BUFFER[GTValues.MAX][1].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, MarkerMaterials.Tier.Superconductor), 'T', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("battery_buffer_ulv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.ULV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Lead), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_lv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.LV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Tin), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_mv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.MV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Copper), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_hv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.HV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Gold), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_ev_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.EV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Aluminium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_iv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.IV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Tungsten), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_luv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.LuV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.VanadiumGallium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_zpm_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.ZPM][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.Naquadah), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_uv_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.UV][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, Materials.NaquadahAlloy), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_max_3x3", MetaTileEntities.BATTERY_BUFFER[GTValues.MAX][2].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtOctal, MarkerMaterials.Tier.Superconductor), 'T', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("battery_buffer_ulv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.ULV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Lead), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_lv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.LV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Tin), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_mv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.MV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Copper), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_hv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.HV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Gold), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_ev_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.EV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Aluminium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_iv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.IV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Tungsten), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_luv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.LuV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.VanadiumGallium), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_zpm_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.ZPM][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Naquadah), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_uv_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.UV][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.NaquadahAlloy), 'T', OreDictNames.craftingChest);
        ModHandler.addShapedRecipe("battery_buffer_max_4x4", MetaTileEntities.BATTERY_BUFFER[GTValues.MAX][3].getStackForm(), "WTW", "WMW", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, MarkerMaterials.Tier.Superconductor), 'T', OreDictNames.craftingChest);

        ModHandler.addShapedRecipe("charger_ulv", MetaTileEntities.CHARGER[GTValues.ULV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Lead), 'T', OreDictNames.craftingChest, 'B', MetaItems.BATTERY_RE_ULV_TANTALUM, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Primitive));
        ModHandler.addShapedRecipe("charger_lv", MetaTileEntities.CHARGER[GTValues.LV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Tin), 'T', OreDictNames.craftingChest, 'B', MetaItems.BATTERY_RE_LV_LITHIUM, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic));
        ModHandler.addShapedRecipe("charger_mv", MetaTileEntities.CHARGER[GTValues.MV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Copper), 'T', OreDictNames.craftingChest, 'B', MetaItems.BATTERY_RE_MV_LITHIUM, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good));
        ModHandler.addShapedRecipe("charger_hv", MetaTileEntities.CHARGER[GTValues.HV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Gold), 'T', OreDictNames.craftingChest, 'B', MetaItems.BATTERY_RE_HV_LITHIUM, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced));
        ModHandler.addShapedRecipe("charger_ev", MetaTileEntities.CHARGER[GTValues.EV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Aluminium), 'T', OreDictNames.craftingChest, 'B', new UnificationEntry(OrePrefix.battery, MarkerMaterials.Tier.Master), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data));
        ModHandler.addShapedRecipe("charger_iv", MetaTileEntities.CHARGER[GTValues.IV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Tungsten), 'T', OreDictNames.craftingChest, 'B', MetaItems.ENERGY_LAPOTRONICORB, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite));
        ModHandler.addShapedRecipe("charger_luv", MetaTileEntities.CHARGER[GTValues.LuV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.VanadiumGallium), 'T', OreDictNames.craftingChest, 'B', MetaItems.ENERGY_LAPOTRONICORB2, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master));
        ModHandler.addShapedRecipe("charger_zpm", MetaTileEntities.CHARGER[GTValues.ZPM].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.Naquadah), 'T', OreDictNames.craftingChest, 'B', MetaItems.ENERGY_LAPOTRONICORB2, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate));
        ModHandler.addShapedRecipe("charger_uv", MetaTileEntities.CHARGER[GTValues.UV].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, Materials.NaquadahAlloy), 'T', OreDictNames.craftingChest, 'B', MetaItems.ZPM2, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor));
        ModHandler.addShapedRecipe("charger_max", MetaTileEntities.CHARGER[GTValues.MAX].getStackForm(), "WTW", "WMW", "BCB", 'M', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtHex, MarkerMaterials.Tier.Superconductor), 'T', OreDictNames.craftingChest, 'B', MetaItems.ZPM2, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor));

        ModHandler.addShapedRecipe("rotor_holder_ulv", MetaTileEntities.ROTOR_HOLDER[GTValues.ULV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.ULV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.WroughtIron), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_lv", MetaTileEntities.ROTOR_HOLDER[GTValues.LV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Steel), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_mv", MetaTileEntities.ROTOR_HOLDER[GTValues.MV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Aluminium), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_hv", MetaTileEntities.ROTOR_HOLDER[GTValues.HV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.StainlessSteel), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_ev", MetaTileEntities.ROTOR_HOLDER[GTValues.EV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Titanium), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_iv", MetaTileEntities.ROTOR_HOLDER[GTValues.IV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.TungstenSteel), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_luv", MetaTileEntities.ROTOR_HOLDER[GTValues.LuV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.LuV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Chrome), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_zpm", MetaTileEntities.ROTOR_HOLDER[GTValues.ZPM].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.ZPM].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Iridium), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_uv", MetaTileEntities.ROTOR_HOLDER[GTValues.UV].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Osmium), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));
        ModHandler.addShapedRecipe("rotor_holder_max", MetaTileEntities.ROTOR_HOLDER[GTValues.MAX].getStackForm(), "   ", "WHW", "WRW", 'H', MetaTileEntities.HULL[GTValues.MAX].getStackForm(), 'W', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Darmstadtium), 'R', new UnificationEntry(OrePrefix.ring, Materials.Rubber));

        // STEAM MACHINES
        ModHandler.addShapedRecipe("bronze_hull", MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_HULL), "PPP", "PhP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze));
        ModHandler.addShapedRecipe("bronze_bricks_hull", MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), "PPP", "PhP", "BBB", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'B', new ItemStack(Blocks.BRICK_BLOCK));
        ModHandler.addShapedRecipe("steel_hull", MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_HULL), "PPP", "PhP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel));
        ModHandler.addShapedRecipe("steel_bricks_hull", MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_BRICKS_HULL), "PPP", "PhP", "BBB", 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'B', new ItemStack(Blocks.BRICK_BLOCK));

        ModHandler.addShapedRecipe("steam_boiler_coal_bronze", MetaTileEntities.STEAM_BOILER_COAL_BRONZE.getStackForm(), "PPP", "P P", "BFB", 'F', OreDictNames.craftingFurnace, 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'B', new ItemStack(Blocks.BRICK_BLOCK));
        ModHandler.addShapedRecipe("steam_boiler_coal_steel", MetaTileEntities.STEAM_BOILER_COAL_STEEL.getStackForm(), "PPP", "P P", "BFB", 'F', OreDictNames.craftingFurnace, 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'B', new ItemStack(Blocks.BRICK_BLOCK));
        ModHandler.addShapedRecipe("steam_boiler_lava_bronze", MetaTileEntities.STEAM_BOILER_LAVA_BRONZE.getStackForm(), "PPP", "GGG", "PMP", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("steam_boiler_lava_steel", MetaTileEntities.STEAM_BOILER_LAVA_STEEL.getStackForm(), "PPP", "GGG", "PMP", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_BRICKS_HULL), 'P', new UnificationEntry(OrePrefix.plate, Materials.Steel), 'G', new ItemStack(Blocks.GLASS, 1));
        ModHandler.addShapedRecipe("steam_boiler_solar_bronze", MetaTileEntities.STEAM_BOILER_SOLAR_BRONZE.getStackForm(), "GGG", "SSS", "PMP", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), 'P', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'S', new UnificationEntry(OrePrefix.plate, Materials.Silver), 'G', new ItemStack(Blocks.GLASS));

        ModHandler.addShapedRecipe("steam_furnace_bronze", MetaTileEntities.STEAM_FURNACE_BRONZE.getStackForm(), "XXX", "XMX", "XFX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'F', OreDictNames.craftingFurnace);
        ModHandler.addShapedRecipe("steam_furnace_steel", MetaTileEntities.STEAM_FURNACE_STEEL.getStackForm(), "XXX", "XMX", "XFX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_BRICKS_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'F', OreDictNames.craftingFurnace);
        ModHandler.addShapedRecipe("steam_macerator_bronze", MetaTileEntities.STEAM_MACERATOR_BRONZE.getStackForm(), "DXD", "XMX", "PXP", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'P', OreDictNames.craftingPiston, 'D', new UnificationEntry(OrePrefix.gem, Materials.Diamond));
        ModHandler.addShapedRecipe("steam_macerator_bronze", MetaTileEntities.STEAM_MACERATOR_BRONZE.getStackForm(), "DXD", "XMX", "PXP", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Steel), 'P', OreDictNames.craftingPiston, 'D', new UnificationEntry(OrePrefix.gem, Materials.Diamond));
        ModHandler.addShapedRecipe("steam_extractor_bronze", MetaTileEntities.STEAM_EXTRACTOR_BRONZE.getStackForm(), "XXX", "PMG", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'P', OreDictNames.craftingPiston, 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("steam_extractor_steel", MetaTileEntities.STEAM_EXTRACTOR_STEEL.getStackForm(), "XXX", "PMG", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Steel), 'P', OreDictNames.craftingPiston, 'G', new ItemStack(Blocks.GLASS));
        ModHandler.addShapedRecipe("steam_hammer_bronze", MetaTileEntities.STEAM_HAMMER_BRONZE.getStackForm(), "XPX", "XMX", "XAX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil);
        ModHandler.addShapedRecipe("steam_hammer_steel", MetaTileEntities.STEAM_HAMMER_STEEL.getStackForm(), "XPX", "XMX", "XAX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Steel), 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil);
        ModHandler.addShapedRecipe("steam_compressor_bronze", MetaTileEntities.STEAM_COMPRESSOR_BRONZE.getStackForm(), "XXX", "PMP", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'P', OreDictNames.craftingPiston);
        ModHandler.addShapedRecipe("steam_compressor_steel", MetaTileEntities.STEAM_COMPRESSOR_STEEL.getStackForm(), "XXX", "PMP", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Steel), 'P', OreDictNames.craftingPiston);
        ModHandler.addShapedRecipe("steam_alloy_smelter_bronze", MetaTileEntities.STEAM_ALLOY_SMELTER_BRONZE.getStackForm(), "XXX", "FMF", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Bronze), 'F', OreDictNames.craftingFurnace);
        ModHandler.addShapedRecipe("steam_alloy_smelter_steel", MetaTileEntities.STEAM_ALLOY_SMELTER_STEEL.getStackForm(), "XXX", "FMF", "XXX", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(STEEL_BRICKS_HULL), 'X', new UnificationEntry(OrePrefix.pipeSmall, Materials.Steel), 'F', OreDictNames.craftingFurnace);

        // MULTI BLOCK CONTROLLERS
        ModHandler.addShapedRecipe("bronze_primitive_blast_furnace", MetaTileEntities.BRONZE_PRIMITIVE_BLAST_FURNACE.getStackForm(), "PFP", "FwF", "PFP", 'P', new UnificationEntry(OrePrefix.plate, Materials.Bronze), 'F', OreDictNames.craftingFurnace);
        ModHandler.addShapedRecipe("electric_blast_furnace", MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm(), "FFF", "CMC", "WCW", 'M', MetaBlocks.METAL_CASING.getItemVariant(INVAR_HEATPROOF), 'F', OreDictNames.craftingFurnace, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
        ModHandler.addShapedRecipe("vacuum_freezer", MetaTileEntities.VACUUM_FREEZER.getStackForm(), "PPP", "CMC", "WCW", 'M', MetaBlocks.METAL_CASING.getItemVariant(ALUMINIUM_FROSTPROOF), 'P', MetaItems.ELECTRIC_PUMP_HV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold));
        ModHandler.addShapedRecipe("implosion_compressor", MetaTileEntities.IMPLOSION_COMPRESSOR.getStackForm(), "OOO", "CMC", "WCW", 'M', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID), 'O', new UnificationEntry(OrePrefix.stone, Materials.Obsidian), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium));
        ModHandler.addShapedRecipe("distillation_tower", MetaTileEntities.DISTILLATION_TOWER.getStackForm(), "CBC", "FMF", "CBC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(EV), 'B', new UnificationEntry(OrePrefix.pipeLarge, Materials.StainlessSteel), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data), 'F', MetaItems.ELECTRIC_PUMP_EV);

//        ModHandler.addShapedRecipe(MetaTileEntities.MACHINE_MULTI_HEATEXCHANGER.get(1L, new Object[0]), new Object[]{"WCW", "CMC", "WCW", 'M', MetaBlocks.BOILER_CASING.getItemVariant(TITANIUM_PIPE), 'C', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium), 'W', MetaItems.ELECTRIC_PUMP_EV});
//
//        ModHandler.addShapedRecipe(MetaTileEntities.CHARCOAL_PILE.get(1L, new Object[0]), new Object[]{"EME", "CCC", 'M', MetaBlocks.MACHINE_CASING.getItemVariant(BRONZE_BRICKS_HULL), 'E', new UnificationEntry(OrePrefix.nugget, Materials.WroughtIron), 'C', new ItemStack(Items.FLINT, 1)});
//
//        ModHandler.addShapedRecipe(MetaTileEntities.OILDRILL1.get(1L, new Object[0]), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.HULL_MV, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.Steel), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good), 'C', MetaItems.ELECTRIC_MOTOR_MV});
//        ModHandler.addShapedRecipe(MetaTileEntities.OILDRILL2.get(1L, new Object[0]), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.OILDRILL1, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'C', MetaItems.ELECTRIC_MOTOR_HV});
//        ModHandler.addShapedRecipe(MetaTileEntities.OILDRILL3.get(1L, new Object[0]), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.OILDRILL2, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data), 'C', MetaItems.ELECTRIC_MOTOR_EV});
//
//        ModHandler.addShapedRecipe(MetaTileEntities.OREDRILL1.get(1L), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.HULL_EV, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.Titanium), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data), 'C', MetaItems.ELECTRIC_MOTOR_EV});
//        ModHandler.addShapedRecipe(MetaTileEntities.OREDRILL2.get(1L), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.OREDRILL1, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.TungstenSteel), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'C', MetaItems.ELECTRIC_MOTOR_IV});
//        ModHandler.addShapedRecipe(MetaTileEntities.OREDRILL3.get(1L), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.OREDRILL2, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.Osmiridium), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master), 'C', MetaItems.ELECTRIC_MOTOR_LUV});
//        ModHandler.addShapedRecipe(MetaTileEntities.OREDRILL4.get(1L), new Object[]{"WWW", "EME", "CCC", 'M', MetaTileEntities.OREDRILL3, 'W', new UnificationEntry(OrePrefix.frameGt, Materials.Tritanium), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate), 'C', MetaItems.ELECTRIC_MOTOR_ZPM});
//
//        ModHandler.addShapedRecipe(MetaTileEntities.OILCRACKER.get(1L, new Object[0]), new Object[]{"WCW", "EME", "WCW", 'M', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'W', MetaBlocks.WIRE_COIL.getItemVariant(CUPRONICKEL), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'C', MetaItems.ELECTRIC_PUMP_HV});
//        ModHandler.addShapedRecipe(MetaTileEntities.CURINGOVEN.get(1L, new Object[0]), new Object[]{"CWC", "CMC", "EWE", 'M', MetaTileEntities.HULL[GTValues.LV].getStackForm(), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'W', new UnificationEntry(OrePrefix.cableGtDouble, Materials.Tin), 'C', new UnificationEntry(OrePrefix.wireGtSingle, Materials.Cupronickel)});
//        ModHandler.addShapedRecipe(MetaTileEntities.MACHINE_MULTI_ASSEMBLYLINE.get(1L, new Object[0]), new Object[]{"WCW", "EME", "WCW", 'M', MetaTileEntities.HULL[GTValues.IV].getStackForm(), 'W', MetaTileEntities.Casing_Assembler, 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'C', MetaItems.ROBOT_ARM_IV});

        ModHandler.addShapedRecipe("pyrolyse_oven", MetaTileEntities.PYROLYSE_OVEN.getStackForm(), "WEP", "EME", "WCP", 'M', MetaTileEntities.HULL[GTValues.MV].getStackForm(), 'W', MetaItems.ELECTRIC_PISTON_MV, 'P', new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Cupronickel), 'E', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good), 'C', MetaItems.ELECTRIC_PUMP_MV);
        ModHandler.addShapedRecipe("diesel_engine", MetaTileEntities.DIESEL_ENGINE.getStackForm(), "PCP", "EME", "GWG", 'M', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'P', MetaItems.ELECTRIC_PISTON_EV, 'E', MetaItems.ELECTRIC_MOTOR_EV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'W', new UnificationEntry(OrePrefix.wireGtSingle, Materials.TungstenSteel), 'G', new UnificationEntry(OrePrefix.gear, Materials.Titanium));
        ModHandler.addShapedRecipe("engine_intake_casing", MetaBlocks.MUTLIBLOCK_CASING.getItemVariant(MultiblockCasingType.ENGINE_INTAKE_CASING), "PhP", "RFR", "PwP", 'R', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium), 'F', MetaBlocks.METAL_CASING.getItemVariant(TITANIUM_STABLE), 'P', new UnificationEntry(OrePrefix.rotor, Materials.Titanium));
        ModHandler.addShapedRecipe("multi_furnace", MetaTileEntities.MULTI_FURNACE.getStackForm(), "PPP", "ASA", "CAC", 'P', Blocks.FURNACE, 'A', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'S', MetaBlocks.METAL_CASING.getItemVariant(INVAR_HEATPROOF), 'C', new UnificationEntry(OrePrefix.cableGtSingle, Materials.AnnealedCopper));

        ModHandler.addShapedRecipe("large_steam_turbine", MetaTileEntities.LARGE_STEAM_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.Steel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'A', MetaTileEntities.HULL[GTValues.HV].getStackForm(), 'C', MetaBlocks.METAL_CASING.getItemVariant(STEEL_SOLID));
        ModHandler.addShapedRecipe("large_gas_turbine", MetaTileEntities.LARGE_GAS_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.StainlessSteel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'A', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'C', MetaBlocks.METAL_CASING.getItemVariant(STAINLESS_CLEAN));
        ModHandler.addShapedRecipe("large_plasma_turbine", MetaTileEntities.LARGE_PLASMA_TURBINE.getStackForm(), "PSP", "SAS", "CSC", 'S', new UnificationEntry(OrePrefix.gear, Materials.TungstenSteel), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master), 'A', MetaTileEntities.HULL[GTValues.UV].getStackForm(), 'C', MetaBlocks.METAL_CASING.getItemVariant(TUNGSTENSTEEL_ROBUST));

        ModHandler.addShapedRecipe("large_bronze_boiler", MetaTileEntities.LARGE_BRONZE_BOILER.getStackForm(), "PSP", "SAS", "PSP", 'P', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin), 'S', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'A', MetaBlocks.BOILER_CASING.getItemVariant(BRONZE_FIREBOX));
        ModHandler.addShapedRecipe("large_steel_boiler", MetaTileEntities.LARGE_STEEL_BOILER.getStackForm(),  "PSP", "SAS", "PSP", 'P', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper), 'S', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'A', MetaBlocks.BOILER_CASING.getItemVariant(STEEL_FIREBOX));
        ModHandler.addShapedRecipe("large_titanium_boiler", MetaTileEntities.LARGE_TITANIUM_BOILER.getStackForm(),  "PSP", "SAS", "PSP", 'P', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'S', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'A', MetaBlocks.BOILER_CASING.getItemVariant(TITANIUM_FIREBOX));
        ModHandler.addShapedRecipe("large_tungstensteel_boiler", MetaTileEntities.LARGE_TUNGSTENSTEEL_BOILER.getStackForm(),  "PSP", "SAS", "PSP", 'P', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium), 'S', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master), 'A', MetaBlocks.BOILER_CASING.getItemVariant(TUNGSTENSTEEL_FIREBOX));

        ModHandler.addShapedRecipe("distillation_tower", MetaTileEntities.DISTILLATION_TOWER.getStackForm(),  "PSP", "DAD", "PSP", 'S', MetaBlocks.METAL_CASING.getItemVariant(STAINLESS_CLEAN), 'P', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite), 'A', MetaTileEntities.HULL[GTValues.EV].getStackForm(), 'D', MetaItems.ELECTRIC_PUMP_EV);

        // GENERATORS
        ModHandler.addShapedRecipe("diesel_generator_lv", MetaTileEntities.DIESEL_GENERATOR[GTValues.LV - 1].getStackForm(), "PCP", "EME", "GWG", 'M', MetaTileEntities.HULL[GTValues.LV - 1].getStackForm(), 'P', MetaItems.ELECTRIC_PISTON_LV, 'E', MetaItems.ELECTRIC_MOTOR_LV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin), 'G', new UnificationEntry(OrePrefix.gear, Materials.Steel));
        ModHandler.addShapedRecipe("diesel_generator_mv", MetaTileEntities.DIESEL_GENERATOR[GTValues.MV - 1].getStackForm(), "PCP", "EME", "GWG", 'M', MetaTileEntities.HULL[GTValues.MV - 1].getStackForm(), 'P', MetaItems.ELECTRIC_PISTON_MV, 'E', MetaItems.ELECTRIC_MOTOR_MV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper), 'G', new UnificationEntry(OrePrefix.gear, Materials.Aluminium));
        ModHandler.addShapedRecipe("diesel_generator_hv", MetaTileEntities.DIESEL_GENERATOR[GTValues.HV - 1].getStackForm(), "PCP", "EME", "GWG", 'M', MetaTileEntities.HULL[GTValues.HV - 1].getStackForm(), 'P', MetaItems.ELECTRIC_PISTON_HV, 'E', MetaItems.ELECTRIC_MOTOR_HV, 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'G', new UnificationEntry(OrePrefix.gear, Materials.StainlessSteel));

        ModHandler.addShapedRecipe("gas_turbine_lv", MetaTileEntities.GAS_TURBINE[GTValues.LV - 1].getStackForm(), "CRC", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.LV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_LV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Tin), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin));
        ModHandler.addShapedRecipe("gas_turbine_mv", MetaTileEntities.GAS_TURBINE[GTValues.MV - 1].getStackForm(), "CRC", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.MV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_MV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Bronze), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper));
        ModHandler.addShapedRecipe("gas_turbine_hv", MetaTileEntities.GAS_TURBINE[GTValues.HV - 1].getStackForm(), "CRC", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.HV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_HV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Steel), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold));

        ModHandler.addShapedRecipe("steam_turbine_lv", MetaTileEntities.STEAM_TURBINE[GTValues.LV - 1].getStackForm(), "PCP", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.LV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_LV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Tin), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin), 'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze));
        ModHandler.addShapedRecipe("steam_turbine_mv", MetaTileEntities.STEAM_TURBINE[GTValues.MV - 1].getStackForm(), "PCP", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.MV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_MV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Bronze), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper), 'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel));
        ModHandler.addShapedRecipe("steam_turbine_hv", MetaTileEntities.STEAM_TURBINE[GTValues.HV - 1].getStackForm(), "PCP", "RMR", "EWE", 'M', MetaTileEntities.HULL[GTValues.HV - 1].getStackForm(), 'E', MetaItems.ELECTRIC_MOTOR_HV, 'R', new UnificationEntry(OrePrefix.rotor, Materials.Steel), 'C', new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced), 'W', new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold), 'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel));

        // MACHINES
        registerMachineRecipe(MetaTileEntities.PACKER, "BCB", "RMV", "WCW", 'M', Type.WORSE_HULL, 'R', Type.ROBOT_ARM, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'B', OreDictNames.craftingChest);
        registerMachineRecipe(MetaTileEntities.BREWERY, "GPG", "WMW", "CBC", 'M', Type.WORSE_HULL, 'P', Type.PUMP, 'B', Type.STICK_DISTILLATION, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', new ItemStack(Blocks.GLASS));
        registerMachineRecipe(MetaTileEntities.ALLOY_SMELTER, "ECE", "CMC", "WCW", 'M', Type.HULL, 'E', Type.CIRCUIT, 'W', Type.CABLE, 'C', Type.COIL_HEATING_DOUBLE);
        registerMachineRecipe(MetaTileEntities.ASSEMBLER, "ACA", "VMV", "WCW", 'M', Type.HULL, 'V', Type.CONVEYOR, 'A', Type.ROBOT_ARM, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.BENDER, "PwP", "CMC", "EWE", 'M', Type.HULL, 'E', Type.MOTOR, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.CANNER, "WPW", "CMC", "GGG", 'M', Type.HULL, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.COMPRESSOR, " C ", "PMP", "WCW", 'M', Type.HULL, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.CUTTER, "WCG", "VMB", "CWE", 'M', Type.HULL, 'E', Type.MOTOR, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS, 'B', OreDictNames.craftingDiamondBlade);
        registerMachineRecipe(MetaTileEntities.ELECTRIC_FURNACE, "ECE", "CMC", "WCW", 'M', Type.HULL, 'E', Type.CIRCUIT, 'W', Type.CABLE, 'C', Type.COIL_HEATING);
        registerMachineRecipe(MetaTileEntities.EXTRACTOR, "GCG", "EMP", "WCW", 'M', Type.HULL, 'E', Type.PISTON, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.EXTRUDER, "CCE", "XMP", "CCE", 'M', Type.HULL, 'X', Type.PISTON, 'E', Type.CIRCUIT, 'P', Type.PIPE, 'C', Type.COIL_HEATING_DOUBLE);
        registerMachineRecipe(MetaTileEntities.LATHE, "WCW", "EMD", "CWP", 'M', Type.HULL, 'E', Type.MOTOR, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'D', Type.DIAMOND);
        registerMachineRecipe(MetaTileEntities.MACERATOR, "PEG", "WWM", "CCW", 'M', Type.HULL, 'E', Type.MOTOR, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GRINDER);
        registerMachineRecipe(MetaTileEntities.MICROWAVE, "LWC", "LMR", "LEC", 'M', Type.HULL, 'E', Type.MOTOR, 'R', Type.EMITTER, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'L', new UnificationEntry(OrePrefix.plate, Materials.Lead));
        registerMachineRecipe(MetaTileEntities.PRINTER, "EWE", "CMC", "WEW", 'M', Type.HULL, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.WIREMILL, "EWE", "CMC", "EWE", 'M', Type.HULL, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.CENTRIFUGE, "CEC", "WMW", "CEC", 'M', Type.HULL, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.ELECTROLYZER, "IGI", "IMI", "CWC", 'M', Type.HULL, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'I', Type.WIRE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.THERMAL_CENTRIFUGE, "CEC", "OMO", "WEW", 'M', Type.HULL, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'O', Type.COIL_HEATING_DOUBLE);
        registerMachineRecipe(MetaTileEntities.ORE_WASHER, "RGR", "CEC", "WMW", 'M', Type.HULL, 'R', Type.ROTOR, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.PACKER, "BCB", "RMV", "WCW", 'M', Type.HULL, 'R', Type.ROBOT_ARM, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'B', OreDictNames.craftingChest);
        registerMachineRecipe(MetaTileEntities.UNPACKER, "BCB", "VMR", "WCW", 'M', Type.HULL, 'R', Type.ROBOT_ARM, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'B', OreDictNames.craftingChest);
        registerMachineRecipe(MetaTileEntities.CHEMICAL_REACTOR, "GRG", "WEW", "CMC", 'M', Type.HULL, 'R', Type.ROTOR, 'E', Type.MOTOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.FLUID_CANNER, "GCG", "GMG", "WPW", 'M', Type.HULL, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.BREWERY, "GPG", "WMW", "CBC", 'M', Type.HULL, 'P', MetaItems.ELECTRIC_PUMP_LV, 'B', Type.STICK_DISTILLATION, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', new ItemStack(Blocks.GLASS));
        registerMachineRecipe(MetaTileEntities.FERMENTER, "WPW", "GMG", "WCW", 'M', Type.HULL, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.FLUID_EXTRACTOR, "GCG", "PME", "WCW", 'M', Type.HULL, 'E', Type.PISTON, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.FLUID_SOLIDIFIER, "PGP", "WMW", "CBC", 'M', Type.HULL, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS, 'B', OreDictNames.craftingChest);
        registerMachineRecipe(MetaTileEntities.DISTILLERY, "GBG", "CMC", "WPW", 'M', Type.HULL, 'P', Type.PUMP, 'B', Type.STICK_DISTILLATION, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.CHEMICAL_BATH, "VGW", "PGV", "CMC", 'M', Type.HULL, 'P', Type.PUMP, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.POLARIZER, "ZSZ", "WMW", "ZSZ", 'M', Type.HULL, 'S', Type.STICK_ELECTROMAGNETIC, 'Z', Type.COIL_ELECTRIC, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.ELECTROMAGNETIC_SEPARATOR, "VWZ", "WMS", "CWZ", 'M', Type.HULL, 'S', Type.STICK_ELECTROMAGNETIC, 'Z', Type.COIL_ELECTRIC, 'V', Type.CONVEYOR, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.AUTOCLAVE, "IGI", "IMI", "CPC", 'M', Type.HULL, 'P', Type.PUMP, 'C', Type.CIRCUIT, 'I', Type.PLATE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.MIXER, "GRG", "GEG", "CMC", 'M', Type.HULL, 'E', Type.MOTOR, 'R', Type.ROTOR, 'C', Type.CIRCUIT, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.LASER_ENGRAVER, "PEP", "CMC", "WCW", 'M', Type.HULL, 'E', Type.EMITTER, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.FORMING_PRESS, "WPW", "CMC", "WPW", 'M', Type.HULL, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.FORGE_HAMMER, "WPW", "CMC", "WAW", 'M', Type.HULL, 'P', Type.PISTON, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'A', OreDictNames.craftingAnvil);
        registerMachineRecipe(MetaTileEntities.FLUID_HEATER, "OGO", "PMP", "WCW", 'M', Type.HULL, 'P', Type.PUMP, 'O', Type.COIL_HEATING_DOUBLE, 'C', Type.CIRCUIT, 'W', Type.CABLE, 'G', Type.GLASS);
        registerMachineRecipe(MetaTileEntities.SIFTER, "WFW", "PMP", "CFC", 'M', Type.HULL, 'P', Type.PISTON, 'F', OreDictNames.craftingFilter, 'C', Type.CIRCUIT, 'W', Type.CABLE);
        registerMachineRecipe(MetaTileEntities.ARC_FURNACE, "WGW", "CMC", "PPP", 'M', Type.HULL, 'P', Type.PLATE, 'C', Type.CIRCUIT, 'W', Type.CABLE_QUAD, 'G', ModHandler.getCellWithFluid(Materials.Graphite));
        registerMachineRecipe(MetaTileEntities.PLASMA_ARC_FURNACE, "WGW", "CMC", "TPT", 'M', Type.HULL, 'P', Type.PLATE, 'C', Type.BETTER_CIRCUIT, 'W', Type.CABLE_QUAD, 'T', Type.PUMP, 'G', ModHandler.getCellWithFluid(Materials.Graphite));
        registerMachineRecipe(MetaTileEntities.PUMP, "WGW", "GMG", "TGT", 'M', Type.HULL, 'W', Type.CIRCUIT, 'G', Type.PUMP, 'T', Type.PISTON);
    }

    public enum Type {
        PUMP,
        CABLE,
        WIRE,
        CABLE_QUAD,
        HULL,
        WORSE_HULL,
        PIPE,
        GLASS,
        PLATE,
        MOTOR,
        ROTOR,
        SENSOR,
        GRINDER,
        DIAMOND,
        PISTON,
        CIRCUIT,
        EMITTER,
        CONVEYOR,
        ROBOT_ARM,
        COIL_HEATING,
        COIL_ELECTRIC,
        STICK_MAGNETIC,
        STICK_DISTILLATION,
        BETTER_CIRCUIT,
        FIELD_GENERATOR,
        COIL_HEATING_DOUBLE,
        STICK_ELECTROMAGNETIC
    }

    public static <T extends MetaTileEntity> void registerMachineRecipe(T[] metaTileEntities, Object... recipe) {
        for (int i = 0; i < metaTileEntities.length; i++) {
            ModHandler.addShapedRecipe(String.format("%s", metaTileEntities[i].getMetaName()), metaTileEntities[i].getStackForm(), prepareRecipe(i + 1, Arrays.copyOf(recipe, recipe.length)));
        }
    }

    private static Object[] prepareRecipe(int tier, Object... recipe) {
        for (int i = 3; i < recipe.length; i++) {
            if (recipe[i] == Type.CIRCUIT) {
                switch (tier) {
                    case 0:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Primitive);
                        break;
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.BETTER_CIRCUIT) {
                switch (tier) {
                    case 0:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Basic);
                        break;
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Good);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Advanced);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Data);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Elite);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Master);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Ultimate);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.Superconductor);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.WORSE_HULL) {
                recipe[i] = MetaTileEntities.HULL[tier].getStackForm();
                continue;
            }

            if (recipe[i] == Type.HULL) {
                recipe[i] = MetaTileEntities.HULL[tier - 1].getStackForm();
                continue;
            }

            if (recipe[i] == Type.GRINDER) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.gem, Materials.Diamond);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.gem, Materials.Diamond);
//                        recipe[i] = OreDictNames.craftingIndustrialDiamond;
                        break;
                    default:
                        recipe[i] = OreDictNames.craftingGrinder;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.WIRE) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Gold);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Silver);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Electrum);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Platinum);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Osmium);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.DIAMOND) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.gem, Materials.Diamond);
                        break;
                    default:
                        recipe[i] = OreDictNames.craftingIndustrialDiamond;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.CABLE) {
                switch (tier) {
                    case 0:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Lead);
                        break;
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Tin);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Copper);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Gold);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Aluminium);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Platinum);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.NiobiumTitanium);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, Materials.Naquadah);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, MarkerMaterials.Tier.Superconductor);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.CABLE_QUAD) {
                switch (tier) {
                    case 0:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Lead);
                        break;
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Tin);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Copper);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Gold);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Aluminium);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Platinum);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.NiobiumTitanium);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtQuadruple, Materials.Naquadah);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.cableGtSingle, MarkerMaterials.Tier.Superconductor);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, MarkerMaterials.Tier.Superconductor);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.GLASS) {
                switch (tier) {
//                        case 6:
//                        case 7:
//                        case 8:
//                            recipe[i] = Ic2Items.reinforcedGlass;
//                            break;
                    default:
                        recipe[i] = new ItemStack(Blocks.GLASS, 1, W);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.PLATE) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.Steel);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.Aluminium);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.Titanium);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.HSSG);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.HSSE);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.Darmstadtium);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.PIPE) {
                switch (tier) {
//                    case 0:
//                    case 1:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze);
//                        break;
//                    case 2:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel);
//                        break;
//                    case 3:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel);
//                        break;
//                    case 4:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium);
//                        break;
//                    case 5:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
//                        break;
//                    case 6:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeSmall, MarkerMaterials.Tier.Ultimate);
//                        break;
//                    case 7:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, MarkerMaterials.Tier.Ultimate);
//                        break;
//                    case 8:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeLarge, MarkerMaterials.Tier.Ultimate);
//                        break;
//                    default:
//                        recipe[i] = new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel);
//                        break;
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.SMALL_BRONZE_PIPE;
                        break;
                    default:
                        recipe[i] = MetaItems.SMALL_STEEL_PIPE;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.COIL_HEATING) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Cupronickel);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Kanthal);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Nichrome);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.TungstenSteel);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.HSSG);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Naquadah);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.NaquadahAlloy);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtOctal, Materials.Nichrome);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.COIL_HEATING_DOUBLE) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Cupronickel);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Kanthal);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Nichrome);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.TungstenSteel);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.HSSG);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Naquadah);
                        break;
                    case 8:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.NaquadahAlloy);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtHex, Materials.Nichrome);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.STICK_DISTILLATION) {
                switch (tier) {
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.Blaze);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.STICK_MAGNETIC) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic);
                        break;
                    case 2:
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic);
                        break;
                    case 4:
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic);
                        break;
                    case 6:
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.stickLong, Materials.NeodymiumMagnetic);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.block, Materials.NeodymiumMagnetic);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.STICK_ELECTROMAGNETIC) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.Iron);
                        break;
                    case 2:
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.Steel);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.Neodymium);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.stick, Materials.VanadiumGallium);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.COIL_ELECTRIC) {
                switch (tier) {
                    case 0:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtSingle, Materials.Tin);
                        break;
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Tin);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtDouble, Materials.Copper);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.Copper);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtOctal, Materials.AnnealedCopper);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtOctal, Materials.AnnealedCopper);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.YttriumBariumCuprate);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtOctal, MarkerMaterials.Tier.Superconductor);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.wireGtHex, MarkerMaterials.Tier.Superconductor);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.ROBOT_ARM) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.ROBOT_ARM_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.ROBOT_ARM_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.ROBOT_ARM_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.ROBOT_ARM_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.ROBOT_ARM_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.ROBOT_ARM_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.ROBOT_ARM_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.ROBOT_ARM_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.PUMP) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.ELECTRIC_PUMP_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.ROTOR) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Tin);
                        break;
                    case 2:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Bronze);
                        break;
                    case 3:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Steel);
                        break;
                    case 4:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel);
                        break;
                    case 5:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel);
                        break;
                    case 6:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Chrome);
                        break;
                    case 7:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Iridium);
                        break;
                    default:
                        recipe[i] = new UnificationEntry(OrePrefix.rotor, Materials.Osmium);
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.MOTOR) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.ELECTRIC_MOTOR_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.PISTON) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.ELECTRIC_PISTON_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.CONVEYOR) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.CONVEYOR_MODULE_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.EMITTER) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.EMITTER_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.EMITTER_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.EMITTER_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.EMITTER_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.EMITTER_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.EMITTER_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.EMITTER_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.EMITTER_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.SENSOR) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.SENSOR_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.SENSOR_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.SENSOR_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.SENSOR_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.SENSOR_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.SENSOR_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.SENSOR_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.SENSOR_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] == Type.FIELD_GENERATOR) {
                switch (tier) {
                    case 0:
                    case 1:
                        recipe[i] = MetaItems.FIELD_GENERATOR_LV;
                        break;
                    case 2:
                        recipe[i] = MetaItems.FIELD_GENERATOR_MV;
                        break;
                    case 3:
                        recipe[i] = MetaItems.FIELD_GENERATOR_HV;
                        break;
                    case 4:
                        recipe[i] = MetaItems.FIELD_GENERATOR_EV;
                        break;
                    case 5:
                        recipe[i] = MetaItems.FIELD_GENERATOR_IV;
                        break;
                    case 6:
                        recipe[i] = MetaItems.FIELD_GENERATOR_LUV;
                        break;
                    case 7:
                        recipe[i] = MetaItems.FIELD_GENERATOR_ZPM;
                        break;
                    default:
                        recipe[i] = MetaItems.FIELD_GENERATOR_UV;
                        break;
                }
                continue;
            }

            if (recipe[i] instanceof Type)
                throw new IllegalArgumentException("Missing Type mapping for: " + recipe[i] + " at tier " + tier);
        }
        return recipe;
    }
}