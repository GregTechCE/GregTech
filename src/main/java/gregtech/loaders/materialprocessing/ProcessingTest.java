package gregtech.loaders.materialprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;

public class ProcessingTest implements gregtech.api.interfaces.IMaterialRegistrator {
    public ProcessingTest() {
        Materials.add(this);
    }

    /**
     * Just a test class to fill the empty spaces in sGeneratedMaterials for performance testing.
     * This is disabled in GT_Loader_MaterialProcessing, but can be enabled for testing.
     */
    @Override
    public void onMaterialsInit() {
        for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (!Materials.USED_IDS.contains(i)) {
                new Materials(i, TextureSet.SET_NONE, 1.0F, 0, 2, 1 | 2 | 4 | 8 | 16 | 32 | 64 | 128, 92, 0, 168, 0, "TestMat" + i, 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL, "testmat");
            }
        }
    }
}
