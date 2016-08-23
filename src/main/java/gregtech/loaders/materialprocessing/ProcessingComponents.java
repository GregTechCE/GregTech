package gregtech.loaders.materialprocessing;

import gregtech.api.enums.Materials;

public class ProcessingComponents implements gregtech.api.interfaces.IComponentRegistrator {
    public ProcessingComponents() {
        Materials.add(this);
    }

    @Override
    public void registerComponents(Materials aMaterial) {
        //Disable components by default
    }
}
