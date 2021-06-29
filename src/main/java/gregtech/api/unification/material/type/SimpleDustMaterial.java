package gregtech.api.unification.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTControlledRegistry;

import javax.annotation.Nonnull;

import static gregtech.api.unification.material.type.Material.MatFlags.registerMaterialFlag;
import static gregtech.api.util.GTUtility.createFlag;

public class SimpleDustMaterial extends SimpleMaterial<SimpleDustMaterial> implements Comparable<SimpleDustMaterial> {

    public static final long GENERATE_SMALL_TINY = createFlag(54);
    static {
        registerMaterialFlag("GENERATE_SMALL_TINY", GENERATE_SMALL_TINY, SimpleDustMaterial.class);
    }

    public static final GTControlledRegistry<String, SimpleDustMaterial> MATERIAL_REGISTRY = new GTControlledRegistry<>(10000);

    public final MaterialIconSet materialIconSet;
    private final boolean hasSmallTiny;

    // new DustMaterial(
    // 977,
    // "sodium_hydroxide",
    // 0x003380,
    // DULL,
    // 1,
    // of(new MaterialStack(Sodium, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)),
    // 0);

    public SimpleDustMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet iconSet, ImmutableList<MaterialStack> components, long flags) {
        super(materialRGB, components, flags);
        this.materialIconSet = iconSet;
        MATERIAL_REGISTRY.register(metaItemSubId, name, this);
        hasSmallTiny = true;//(flags | GENERATE_SMALL_TINY) == 1;
    }

    @Override
    public GTControlledRegistry<String, SimpleDustMaterial> getRegistry() {
        return MATERIAL_REGISTRY;
    }

    @Override
    public Class<SimpleDustMaterial> getMaterialClass() {
        return SimpleDustMaterial.class;
    }

    @Override
    public int compareTo(@Nonnull SimpleDustMaterial o) {
        return toString().compareTo(toString());
    }

    public boolean hasPrefix(OrePrefix prefix) {
        switch (prefix) {
            case dust: return true;
            case dustSmall:
            case dustTiny: return hasSmallTiny;
        }
        return false;
    }

    @Override
    public String toString() {
        return MATERIAL_REGISTRY.getNameForObject(this);
    }
}
