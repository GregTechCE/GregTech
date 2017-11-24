package gregtech.common;


import com.google.common.base.CaseFormat;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.util.GTResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

@SuppressWarnings("WeakerAccess")
public class MetaFluids {

    public enum FluidType {
        LIQUID,
        GAS,
        PLASMA
    }

    @SuppressWarnings("deprecation")
    public static Fluid registerFluid(FluidMaterial material, FluidType type, int temp) {
        String materialName = material.toString();
        String typeName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, type.name());
        Fluid fluid = new Fluid(typeName + "." + materialName,
            new GTResourceLocation("blocks/fluids/" + materialName + "_still"),
            new GTResourceLocation("blocks/fluids/" + materialName + "_flow"));
        fluid.setTemperature(temp);

        switch(type) {
            case LIQUID:
                fluid.setGaseous(false);
                fluid.setViscosity(1000);
                material.setMaterialFluid(fluid);
                break;
            case GAS:
                fluid.setGaseous(true);
                fluid.setDensity(-100);
                fluid.setViscosity(200);
                material.setMaterialFluid(fluid);
                break;
            case PLASMA:
                fluid.setGaseous(true);
                fluid.setDensity(55536);
                fluid.setViscosity(10);
                fluid.setLuminosity(15);
                material.setMaterialPlasma(fluid);
                break;
        }

        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
        return fluid;
    }

    @SuppressWarnings("deprecation")
    public static void init() {

        Materials.Water.setMaterialFluid(FluidRegistry.WATER);
        Materials.Ice.setMaterialFluid(FluidRegistry.WATER);
        Materials.Lava.setMaterialFluid(FluidRegistry.LAVA);

        //TODO TWEAK VALUES
        registerFluid(Materials.Air, FluidType.GAS, 295);
        registerFluid(Materials.Oxygen, FluidType.LIQUID, 295);
        registerFluid(Materials.Hydrogen, FluidType.LIQUID, 295);
        registerFluid(Materials.Deuterium, FluidType.LIQUID, 295);
        registerFluid(Materials.Tritium, FluidType.LIQUID, 295);
        registerFluid(Materials.Helium, FluidType.LIQUID, 295);
        registerFluid(Materials.Argon, FluidType.LIQUID, 295);
        registerFluid(Materials.Radon, FluidType.LIQUID, 295);
        registerFluid(Materials.Fluorine, FluidType.LIQUID, 53);
        registerFluid(Materials.Titaniumtetrachloride, FluidType.LIQUID, 2200);
        registerFluid(Materials.Helium3, FluidType.LIQUID, 295);
        registerFluid(Materials.Methane, FluidType.LIQUID, 295);
        registerFluid(Materials.Nitrogen, FluidType.LIQUID, 295);
        registerFluid(Materials.NitrogenDioxide, FluidType.LIQUID, 295);
        registerFluid(Materials.Steam, FluidType.GAS, 295);

//        GregTechMod.gregtechproxy.addFluid("liquid_extra_heavy_oil", "Very Heavy Oil", null, 1, 295);
//        GregTechMod.gregtechproxy.addFluid("liquid_epichlorhydrin", "Epichlorohydrin", null, 1, 295);
//        GregTechMod.gregtechproxy.addFluid("liquid_drillingfluid", "Drilling Fluid", null, 1, 295);
//        GregTechMod.gregtechproxy.addFluid("liquid_toluene", "Toluene", null, 1, 295);
//        GregTechMod.gregtechproxy.addFluid("liquid_nitrationmixture", "Nitration Mixture", null, 1, 295);

        registerFluid(Materials.OilHeavy, FluidType.LIQUID, 295);
        registerFluid(Materials.OilMedium, FluidType.LIQUID, 295);
        registerFluid(Materials.OilLight, FluidType.LIQUID, 295);

        registerFluid(Materials.HydricSulfide, FluidType.GAS, 295);
        registerFluid(Materials.SulfuricGas, FluidType.GAS, 295);
        registerFluid(Materials.Gas, FluidType.GAS, 295);

        registerFluid(Materials.SulfuricNaphtha, FluidType.LIQUID, 295);
        registerFluid(Materials.SulfuricLightFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.SulfuricHeavyFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.Naphtha, FluidType.LIQUID, 295);
        registerFluid(Materials.LightFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.HeavyFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.LPG, FluidType.LIQUID, 295);
        registerFluid(Materials.CrackedLightFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.CrackedHeavyFuel, FluidType.LIQUID, 295);

        registerFluid(Materials.UUAmplifier, FluidType.LIQUID, 295);
        registerFluid(Materials.Chlorine, FluidType.GAS, 295);
        registerFluid(Materials.Mercury, FluidType.LIQUID, 295);
        registerFluid(Materials.NitroFuel, FluidType.LIQUID, 295);
        registerFluid(Materials.SodiumPersulfate, FluidType.LIQUID, 295);
        registerFluid(Materials.Glyceryl, FluidType.LIQUID, 295);

        registerFluid(Materials.Lubricant, FluidType.LIQUID, 295);
        registerFluid(Materials.Creosote, FluidType.LIQUID, 295);
        registerFluid(Materials.SeedOil, FluidType.LIQUID, 295);
        registerFluid(Materials.FishOil, FluidType.LIQUID, 295);
        registerFluid(Materials.Oil, FluidType.LIQUID, 295);
        registerFluid(Materials.Fuel, FluidType.LIQUID, 295);
        registerFluid(Materials.Honey, FluidType.LIQUID, 295);
        registerFluid(Materials.Biomass, FluidType.LIQUID, 295);
        registerFluid(Materials.Ethanol, FluidType.LIQUID, 295);
        registerFluid(Materials.SulfuricAcid, FluidType.LIQUID, 295);
        registerFluid(Materials.Milk, FluidType.LIQUID, 290);
        registerFluid(Materials.McGuffium239, FluidType.LIQUID, 295);
        registerFluid(Materials.Glue, FluidType.LIQUID, 295);
        registerFluid(Materials.FryingOilHot, FluidType.LIQUID, 400);

        registerFluid(Materials.HolyWater, FluidType.LIQUID, 295);

//        Dyes.dyeBlack.addFluidDye(GregTechMod.gregtechproxy.addFluid("squidink", "Squid Ink", null, 1, 295));
//        Dyes.dyeBlue.addFluidDye(GregTechMod.gregtechproxy.addFluid("indigo", "Indigo Dye", null, 1, 295));
//        for (byte i = 0; i < Dyes.VALUES.length; i = (byte) (i + 1)) {
//            Dyes tDye = Dyes.VALUES[i];
//            Fluid tFluid;
//            tDye.addFluidDye(tFluid = GregTechMod.gregtechproxy.addFluid("dye.watermixed." + tDye.name().toLowerCase(Locale.ENGLISH), "dyes", "Water Mixed " + tDye.mName + " Dye", null, tDye.getRGBA(), 1, 295, null, null, 0));
//            tDye.addFluidDye(tFluid = GregTechMod.gregtechproxy.addFluid("dye.chemical." + tDye.name().toLowerCase(Locale.ENGLISH), "dyes", "Chemical " + tDye.mName + " Dye", null, tDye.getRGBA(), 1, 295, null, null, 0));
//            FluidContainerRegistry.registerFluidContainer(new FluidStack(tFluid, 2304), ItemList.SPRAY_CAN_DYES[i].get(1), ItemList.Spray_Empty.get(1));
//        }
//
//        GregTechMod.gregtechproxy.addFluid("molten.glass", "Molten Glass", Materials.Glass, 4, 1500);
//        GregTechMod.gregtechproxy.addFluid("molten.redstone", "Molten Redstone", Materials.Redstone, 4, 500);
//        GregTechMod.gregtechproxy.addFluid("molten.blaze", "Molten Blaze", Materials.Blaze, 4, 6400);
//        GregTechMod.gregtechproxy.addFluid("molten.concrete", "Wet Concrete", Materials.Concrete, 4, 300);


        for (Material material : Material.MATERIAL_REGISTRY) {
            if (material instanceof FluidMaterial) {
                FluidMaterial fluidMaterial = (FluidMaterial) material;
                if (fluidMaterial.shouldGenerateFluid() && fluidMaterial.getMaterialFluid() == null) {
                    int temp;
                    if (fluidMaterial instanceof MetalMaterial) {
                        temp = ((MetalMaterial) fluidMaterial).blastFurnaceTemperature;
                    } else if (fluidMaterial instanceof DustMaterial) {
                        temp = 1000;
                    } else {
                        temp = 295;
                    }
                    fluidMaterial.setMaterialFluid(registerFluid(fluidMaterial, FluidType.LIQUID, temp));
                }

                if (fluidMaterial.shouldGeneratePlasma() && fluidMaterial.getMaterialPlasma() != null) {
                    fluidMaterial.setMaterialPlasma(registerFluid(fluidMaterial, FluidType.PLASMA, 30000));
                }
            }
        }

//        GregTechMod.gregtechproxy.addFluid("potion.purpledrink", "Purple Drink", null, 1, 275, ItemList.Bottle_Purple_Drink.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.grapejuice", "Grape Juice", null, 1, 295, ItemList.Bottle_Grape_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.wine", "Wine", null, 1, 295, ItemList.Bottle_Wine.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.vinegar", "Vinegar", null, 1, 295, ItemList.Bottle_Vinegar.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.potatojuice", "Potato Juice", null, 1, 295, ItemList.Bottle_Potato_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.vodka", "Vodka", null, 1, 275, ItemList.Bottle_Vodka.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.leninade", "Leninade", null, 1, 275, ItemList.Bottle_Leninade.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.mineralwater", "Mineral Water", null, 1, 275, ItemList.Bottle_Mineral_Water.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.saltywater", "Salty Water", null, 1, 275, ItemList.Bottle_Salty_Water.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.reedwater", "Reed Water", null, 1, 295, ItemList.Bottle_Reed_Water.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.rum", "Rum", null, 1, 295, ItemList.Bottle_Rum.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.piratebrew", "Pirate Brew", null, 1, 295, ItemList.Bottle_Pirate_Brew.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.hopsjuice", "Hops Juice", null, 1, 295, ItemList.Bottle_Hops_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.darkbeer", "Dark Beer", null, 1, 275, ItemList.Bottle_Dark_Beer.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.dragonblood", "Dragon Blood", null, 1, 375, ItemList.Bottle_Dragon_Blood.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.wheatyjuice", "Wheaty Juice", null, 1, 295, ItemList.Bottle_Wheaty_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.scotch", "Scotch", null, 1, 275, ItemList.Bottle_Scotch.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.glenmckenner", "Glen McKenner", null, 1, 275, ItemList.Bottle_Glen_McKenner.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.wheatyhopsjuice", "Wheaty Hops Juice", null, 1, 295, ItemList.Bottle_Wheaty_Hops_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.beer", "Beer", null, 1, 275, ItemList.Bottle_Beer.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.chillysauce", "Chilly Sauce", null, 1, 375, ItemList.Bottle_Chilly_Sauce.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.hotsauce", "Hot Sauce", null, 1, 380, ItemList.Bottle_Hot_Sauce.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.diabolosauce", "Diabolo Sauce", null, 1, 385, ItemList.Bottle_Diabolo_Sauce.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.diablosauce", "Diablo Sauce", null, 1, 390, ItemList.Bottle_Diablo_Sauce.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.diablosauce.strong", "Old Man Snitches glitched Diablo Sauce", null, 1, 999, ItemList.Bottle_Snitches_Glitch_Sauce.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.applejuice", "Apple Juice", null, 1, 295, ItemList.Bottle_Apple_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.cider", "Cider", null, 1, 295, ItemList.Bottle_Cider.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.goldenapplejuice", "Golden Apple Juice", null, 1, 295, ItemList.Bottle_Golden_Apple_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.goldencider", "Golden Cider", null, 1, 295, ItemList.Bottle_Golden_Cider.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.idunsapplejuice", "Idun's Apple Juice", null, 1, 295, ItemList.Bottle_Iduns_Apple_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.notchesbrew", "Notches Brew", null, 1, 295, ItemList.Bottle_Notches_Brew.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.lemonjuice", "Lemon Juice", null, 1, 295, ItemList.Bottle_Lemon_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.limoncello", "Limoncello", null, 1, 295, ItemList.Bottle_Limoncello.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.lemonade", "Lemonade", null, 1, 275, ItemList.Bottle_Lemonade.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.alcopops", "Alcopops", null, 1, 275, ItemList.Bottle_Alcopops.get(1), ItemList.Bottle_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.cavejohnsonsgrenadejuice", "Cave Johnsons Grenade Juice", null, 1, 295, ItemList.Bottle_Cave_Johnsons_Grenade_Juice.get(1), ItemList.Bottle_Empty.get(1), 250);
//
//        GregTechMod.gregtechproxy.addFluid("potion.darkcoffee", "Dark Coffee", null, 1, 295, ItemList.ThermosCan_Dark_Coffee.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.darkcafeaulait", "Dark Cafe au lait", null, 1, 295, ItemList.ThermosCan_Dark_Cafe_au_lait.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.coffee", "Coffee", null, 1, 295, ItemList.ThermosCan_Coffee.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.cafeaulait", "Cafe au lait", null, 1, 295, ItemList.ThermosCan_Cafe_au_lait.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.laitaucafe", "Lait au cafe", null, 1, 295, ItemList.ThermosCan_Lait_au_cafe.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.darkchocolatemilk", "Bitter Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Dark_Chocolate_Milk.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.chocolatemilk", "Chocolate Milk", null, 1, 295, ItemList.ThermosCan_Chocolate_Milk.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.tea", "Tea", null, 1, 295, ItemList.ThermosCan_Tea.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.sweettea", "Sweet Tea", null, 1, 295, ItemList.ThermosCan_Sweet_Tea.get(1), ItemList.ThermosCan_Empty.get(1), 250);
//        GregTechMod.gregtechproxy.addFluid("potion.icetea", "Ice Tea", null, 1, 255, ItemList.ThermosCan_Ice_Tea.get(1), ItemList.ThermosCan_Empty.get(1), 250);
    }
}
