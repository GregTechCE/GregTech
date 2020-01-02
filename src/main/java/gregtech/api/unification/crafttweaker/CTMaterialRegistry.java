package gregtech.api.unification.crafttweaker;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.stack.MaterialStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.List;

@ZenClass("mods.gregtech.material.MaterialRegistry")
@ZenRegister
public class CTMaterialRegistry {

    @ZenMethod
    @Nullable
    public static Material get(String name) {
        return Material.MATERIAL_REGISTRY.getObject(name);
    }

    @ZenMethod
    public static List<Material> getAllMaterials() {
        return Lists.newArrayList(Material.MATERIAL_REGISTRY);
    }

    private static ImmutableList<MaterialStack> validateComponentList(@Nullable MaterialStack[] components) {
        return components == null || components.length == 0 ? ImmutableList.of() : ImmutableList.copyOf(components);
    }

    @ZenMethod
    public static FluidMaterial createFluidMaterial(int metaItemSubId, String name, int color, String iconSet, @Optional MaterialStack[] materialComponents) {
        return new FluidMaterial(metaItemSubId, name, color,
            MaterialIconSet.getByName(iconSet),
            validateComponentList(materialComponents), 0);
    }

    @ZenMethod
    public static DustMaterial createDustMaterial(int metaItemSubId, String name, int color, String iconSet, int harvestLevel, @Optional MaterialStack[] materialComponents) {
        return new DustMaterial(metaItemSubId, name, color,
            MaterialIconSet.getByName(iconSet), harvestLevel,
            validateComponentList(materialComponents), 0);
    }

    @ZenMethod
    public static GemMaterial createGemMaterial(int metaItemSubId, String name, int color, String iconSet, int harvestLevel, @Optional MaterialStack[] materialComponents, @Optional float toolSpeed, @Optional float attackDamage, @Optional int toolDurability) {
        return new GemMaterial(metaItemSubId, name, color,
            MaterialIconSet.getByName(iconSet), harvestLevel,
            validateComponentList(materialComponents), 0, null,
            Math.max(0.0f, toolSpeed), Math.max(0.0f, attackDamage), Math.max(0, toolDurability));
    }

    @ZenMethod
    public static IngotMaterial createIngotMaterial(int metaItemSubId, String name, int color, String iconSet, int harvestLevel, @Optional MaterialStack[] materialComponents, @Optional float toolSpeed, @Optional float attackDamage, @Optional int toolDurability, @Optional int blastFurnaceTemperature) {
        return new IngotMaterial(metaItemSubId, name, color,
            MaterialIconSet.getByName(iconSet), harvestLevel,
            validateComponentList(materialComponents), 0, null,
            Math.max(0.0f, toolSpeed), Math.max(0.0f, attackDamage), Math.max(0, toolDurability), blastFurnaceTemperature);
    }

}
