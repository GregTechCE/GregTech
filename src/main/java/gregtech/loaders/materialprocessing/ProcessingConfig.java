package gregtech.loaders.materialprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;

public class ProcessingConfig implements gregtech.api.interfaces.IMaterialHandler {
    public ProcessingConfig() {
        Materials.add(this);
    }

    @Override
    public void onMaterialsInit() {
        int i = 0;
        for (int j = GregTech_API.sMaterialProperties.get("general", "AmountOfCustomMaterialSlots", 16); i < j; i++) {
            String aID = (i < 10 ? "0" : "") + i;
            new Materials(-1, TextureSet.SET_METALLIC, 1.0F, 0, 0, 0, 255, 255, 255, 0, "CustomMat" + aID, "CustomMat" + aID, 0, 0, 0, 0, false, false, 1, 1, 1, Dyes._NULL, "custom", true, aID);
        }
    }

    @Override
    public void onComponentRegistration(Materials aMaterial) {
        /** This is just left here as an example of how to add components.
        switch (aMaterial.mName) {
            case "Cobalt":
                OrePrefixes.spring.mDisabledItems.remove(aMaterial);
                OrePrefixes.ingotDouble.mDisabledItems.remove(aMaterial);
                OrePrefixes.ingotTriple.mDisabledItems.remove(aMaterial);
                OrePrefixes.ingotQuadruple.mDisabledItems.remove(aMaterial);
                OrePrefixes.ingotQuintuple.mDisabledItems.remove(aMaterial);
                OrePrefixes.plateDouble.mDisabledItems.remove(aMaterial);
                OrePrefixes.plateTriple.mDisabledItems.remove(aMaterial);
                OrePrefixes.plateQuadruple.mDisabledItems.remove(aMaterial);
                OrePrefixes.plateQuintuple.mDisabledItems.remove(aMaterial);
                OrePrefixes.plateDense.mDisabledItems.remove(aMaterial);
                break;
        }**/
    }
}
