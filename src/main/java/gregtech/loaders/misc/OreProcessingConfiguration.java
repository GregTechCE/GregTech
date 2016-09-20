package gregtech.loaders.misc;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class OreProcessingConfiguration
    implements Runnable
{
    private final Configuration mConfiguration;
    private final HashMap<String, Boolean> mEnabledMaterials;
    public OreProcessingConfiguration(File aModConfigurationDirectory)
    {
        this.mEnabledMaterials = new HashMap<String, Boolean>();
        this.mConfiguration =
            new Configuration(new File(new File(aModConfigurationDirectory, "GregTech"), "OreProcessing.cfg"));
        this.mConfiguration.load();
        this.loadConfiguration();
        if (this.mConfiguration.hasChanged())
        {
            this.mConfiguration.save();
        }
    }
    private void loadConfiguration()
    {
        for (Materials tMaterial : GregTech_API.sGeneratedMaterials)
        {
            if (tMaterial != null && tMaterial != Materials._NULL)
            {
                String tMaterialName = tMaterial.name();
                boolean tDefaultValue = (tMaterial.mTypes & 8) != 0;
                Property tProperty =
                    this.mConfiguration.get("processores", tMaterialName + "_" + tDefaultValue, tDefaultValue);
                this.mEnabledMaterials.put(tMaterialName, tProperty.getBoolean(tDefaultValue));
            }
        }
    }
    @Override
    public void run() {
        for (Map.Entry<String, Boolean> aEntry : this.mEnabledMaterials.entrySet()) {
            if (this.mEnabledMaterials.get(aEntry.getKey())) {
                Materials.valueOf(aEntry.getKey()).mTypes |= 8;
            } else if ((Materials.valueOf(aEntry.getKey()).mTypes & 8) != 0) {
                Materials.valueOf(aEntry.getKey()).mTypes ^= 8;
            }
        }
    }
}

