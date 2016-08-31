package gregtech.api.interfaces;

import gregtech.api.enums.Materials;

public interface IMaterialRegistrator {
    void onMaterialsInit();

    void onComponentRegistration(Materials aMaterial);
}
