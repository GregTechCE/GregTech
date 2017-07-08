package gregtech.api.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.HashMap;

public class GTControlledRegistry<T> extends RegistryNamespaced<String, T> {

    private HashMap<String, String> modRegistryTracking = new HashMap<>();
    private boolean frozen = false;

    public void freezeRegistry() {
        if(frozen) {
            throw new IllegalStateException("Registry is already frozen!");
        }
        this.frozen = true;
    }

    @Override
    public void register(int id, String key, T value) {
        this.putObject(key, value);
        underlyingIntegerMap.put(value, id);
    }

    @Override
    public void putObject(String key, T value) {
        ModContainer activeMod = Loader.instance().activeModContainer();
        if(activeMod == null) {
            throw new IllegalThreadStateException("Tried to access registry outside mod context!");
        }
        if(frozen) {
            throw new IllegalStateException(String.format("Mod %s tried to register entry %s when registry was already in frozen state!",
                    activeMod.getModId(), key));
        }
        if(modRegistryTracking.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Mod %s tries to overwrite registry entry %s, registered by mod %s!",
                    activeMod.getModId(), key, modRegistryTracking.get(key)));
        }
        modRegistryTracking.put(key, activeMod.getModId());
        super.putObject(key, value);
    }

    public ResourceLocation getFullNameForObject(T value) {
        String key = getNameForObject(value);
        String modId = modRegistryTracking.get(key);
        return new ResourceLocation(modId, key);
    }

    public T getObjectByFullName(ResourceLocation fullName) {
        if(!fullName.getResourceDomain().equals(modRegistryTracking.get(fullName.getResourcePath()))) {
            return null;
        }
        return getObject(fullName.getResourcePath());
    }

}
