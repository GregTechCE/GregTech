package gregtech.api.interfaces;

import gregtech.api.enums.Materials;

public interface IMaterialHandler {
    void onMaterialsInit();

    void onComponentInit();

    void onComponentIteration(Materials aMaterial);
}
