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
        /** This is just left here as an example of how to add components. **/

        /*Enabling/Disabling components depending on the current Materials values:
        if ((aMaterial.mTypes & 0x40) != 0) { //This material can be made into tool heads
            OrePrefixes.plateQuadruple.mDisabledItems.remove(aMaterial);
        } */

        /* Enabling specific components:
        OrePrefixes.spring.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.ingotDouble.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.ingotTriple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.ingotQuadruple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.ingotQuintuple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.plateDouble.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.plateTriple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.plateQuadruple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.plateQuintuple.mDisabledItems.remove(Materials.Cobalt);
        OrePrefixes.plateDense.mDisabledItems.remove(Materials.Cobalt); */
    }
}
