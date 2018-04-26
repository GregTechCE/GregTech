package gregtech.api.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// this class should extend RegistryNamespaced but due to
// ForgeGradle bug (https://github.com/MinecraftForge/ForgeGradle/issues/498) it gives compile errors in CI environment
public class GTControlledRegistry<T> extends RegistrySimple<String, T> {

    private HashMap<String, String> modRegistryTracking = new HashMap<>();
    private boolean frozen = false;
    private final int maxId;

    public GTControlledRegistry(int maxId) {
        this.maxId = maxId;
        this.inverseObjectRegistry = ((BiMap<String, T>)this.registryObjects).inverse();
    }

    public void freezeRegistry() {
        if(frozen) {
            throw new IllegalStateException("Registry is already frozen!");
        }
        this.frozen = true;
    }

    public Iterable<T> getObjectsWithIds() {
        return underlyingIntegerMap;
    }

    public void register(int id, String key, T value) {
        if(id < 0 || id >= maxId) {
            throw new IndexOutOfBoundsException("Id is out of range: " + id);
        }
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

    public int getIdByObjectName(String key) {
        T valueWithKey = getObject(key);
        return valueWithKey == null ? 0 : getIDForObject(valueWithKey);
    }

    public String getNameForObjectId(int id) {
        T valueWithKey = getObjectById(id);
        return valueWithKey == null ? null : getNameForObject(valueWithKey);
    }

//     =================== RegistryNamespaced stuff ===================

    protected final IntIdentityHashBiMap<T> underlyingIntegerMap = new IntIdentityHashBiMap<>(256);
    protected final Map<T, String> inverseObjectRegistry;

    @Override
    protected Map<String, T> createUnderlyingMap()
    {
        return HashBiMap.create();
    }

    @Nullable
    public String getNameForObject(T value)
    {
        return this.inverseObjectRegistry.get(value);
    }

    public int getIDForObject(@Nullable T value)
    {
        return this.underlyingIntegerMap.getId(value);
    }

    @Nullable
    public T getObjectById(int id)
    {
        return this.underlyingIntegerMap.get(id);
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.underlyingIntegerMap.iterator();
    }
}
