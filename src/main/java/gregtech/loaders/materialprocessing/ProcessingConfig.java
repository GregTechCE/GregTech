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
    public void onComponentInit() {
        /** This is just left here as an example of how to add components. **/
        /* Enabling specific components:
        OrePrefixes.spring.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.plateTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDense.enableComponent(Materials.Cobalt); */
    }

    @Override
    public void onComponentIteration(Materials aMaterial) {
        /** This is just left here as an example of how to add components. **/
        /*Enabling/Disabling components depending on the current Materials values:
        if ((aMaterial.mTypes & 0x40) != 0) { //This material can be made into tool heads
            OrePrefixes.plateQuadruple.mDisabledItems.remove(aMaterial);
        } */
    }
}
