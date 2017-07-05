package gregtech.loaders.materialprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraftforge.fml.common.Loader;

public class ProcessingModSupport implements gregtech.api.interfaces.IMaterialHandler {
    public static boolean aTGregSupport = Loader.isModLoaded("TGregworks");
    public static boolean aEnableUBCMats = Loader.isModLoaded("UndergroundBiomes") || aTGregSupport;
    public static boolean aEnableThaumcraftMats = Loader.isModLoaded("Thaumcraft") || aTGregSupport;
    public static boolean aEnableRotaryCraftMats = Loader.isModLoaded("RotaryCraft") || aTGregSupport;
    public static boolean aEnableThermalFoundationMats = Loader.isModLoaded("ThermalFoundation") || aTGregSupport;
    public static boolean aEnableEnderIOMats = Loader.isModLoaded("EnderIO") || aTGregSupport;
    public static boolean aEnableRailcraftMats = Loader.isModLoaded(GT_Values.MOD_ID_RC) || aTGregSupport;
    public static boolean aEnableGCMarsMats = Loader.isModLoaded("GalacticraftMars") || aTGregSupport;
    public static boolean aEnableTwilightMats = Loader.isModLoaded(GT_Values.MOD_ID_TF) || aTGregSupport;
    public static boolean aEnableMetallurgyMats = Loader.isModLoaded("Metallurgy") || aTGregSupport;

    public ProcessingModSupport() {
        Materials.add(this);
    }

    @Override
    public void onMaterialsInit() {
        //Disable Materials if Parent Mod is not loaded
        if (!aTGregSupport) {
            Materials.Dysprosium.mHasParentMod = false;
            Materials.Erbium.mHasParentMod = false;
            Materials.Gadolinium.mHasParentMod = false;
            Materials.Holmium.mHasParentMod = false;
            Materials.Indium.mHasParentMod = false;
            Materials.Lanthanum.mHasParentMod = false;
            Materials.Praseodymium.mHasParentMod = false;
            Materials.Promethium.mHasParentMod = false;
            Materials.Rubidium.mHasParentMod = false;
            Materials.Samarium.mHasParentMod = false;
            Materials.Scandium.mHasParentMod = false;
            Materials.Strontium.mHasParentMod = false;
            Materials.Tellurium.mHasParentMod = false;
            Materials.Terbium.mHasParentMod = false;
            Materials.Thulium.mHasParentMod = false;
            Materials.Ytterbium.mHasParentMod = false;
            Materials.Endium.mHasParentMod = false;
            Materials.DarkIron.mHasParentMod = false;
            Materials.ElectrumFlux.mHasParentMod = false;
            Materials.Force.mHasParentMod = false;
            Materials.Nikolite.mHasParentMod = false;
            Materials.Sunnarium.mHasParentMod = false;
            Materials.BlueAlloy.mHasParentMod = false;
            Materials.Vinteum.mHasParentMod = false;
            Materials.ChromiumDioxide.mHasParentMod = false;
        }
        if (!aEnableUBCMats) {
            Materials.Blueschist.mHasParentMod = false;
            Materials.Chert.mHasParentMod = false;
            Materials.Dacite.mHasParentMod = false;
            Materials.Eclogite.mHasParentMod = false;
            Materials.Gabbro.mHasParentMod = false;
            Materials.Gneiss.mHasParentMod = false;
            Materials.Greenschist.mHasParentMod = false;
            Materials.Greywacke.mHasParentMod = false;
            Materials.Komatiite.mHasParentMod = false;
            Materials.Rhyolite.mHasParentMod = false;
        }
        if (!aEnableGCMarsMats) {
            Materials.Desh.mHasParentMod = false;
            Materials.MeteoricIron.mHasParentMod = false;
            Materials.MeteoricSteel.mHasParentMod = false;
        }
        if (!aEnableThermalFoundationMats) {
            Materials.Blizz.mHasParentMod = false;
            Materials.Enderium.mHasParentMod = false;
        }
        if (!aEnableRailcraftMats) {
            Materials.Firestone.mHasParentMod = false;
        }

        //Enable Materials if correct mod is Loaded
        Materials.ChromiumDioxide.mHasParentMod = Loader.isModLoaded("computronics");
    }

    @Override
    public void onComponentInit() {
        if (Loader.isModLoaded("computronics")) {
            OrePrefixes.ring.enableComponent(Materials.RedAlloy);
            OrePrefixes.ring.enableComponent(Materials.NiobiumTitanium);
            OrePrefixes.foil.enableComponent(Materials.StainlessSteel);
            OrePrefixes.foil.enableComponent(Materials.ChromiumDioxide);
            OrePrefixes.foil.enableComponent(Materials.Iron);
            OrePrefixes.plate.enableComponent(Materials.ChromiumDioxide);
            OrePrefixes.screw.enableComponent(Materials.Copper);
        }
    }

    @Override
    public void onComponentIteration(Materials aMaterial) {
        //NOOP
    }
}
