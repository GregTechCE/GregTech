package gregtech.api.unification.material.type;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTControlledRegistry;
import gregtech.api.util.GTLog;
import gregtech.common.ConfigHolder;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import static gregtech.api.GTValues.M;
import static gregtech.api.util.GTUtility.createFlag;

public abstract class Material implements Comparable<Material> {

	public static GTControlledRegistry<Material> MATERIAL_REGISTRY = new GTControlledRegistry<>(1000);

	/**
	 * Initializes materials registry
	 */
	public static void init() {
		MATERIAL_REGISTRY.freezeRegistry();
        Map<String, String[]> materialFlags = ConfigHolder.materialFlags;
        for(String materialName : materialFlags.keySet()) {
            Material material = MATERIAL_REGISTRY.getObject(materialName);
            if(material == null) {
                GTLog.logger.error("Couldn't find material {} from configuration of material flags. Skipping it..", materialName);
                continue;
            }
            Class<? extends Material> materialClass = material.getClass();
            long additionalFlags = 0L;
            for(String flagName : materialFlags.get(materialName)) {
                try {
                    additionalFlags |= MatFlags.resolveFlag(flagName, materialClass);
                } catch (IllegalArgumentException exception) {
                    GTLog.logger.error("Couldn't apply configuration material flag {} to material {}: {}",
                        flagName, material.toString(), exception.getMessage());
                }
            }
            material.add(additionalFlags);
        }

	}

	public static final class MatFlags {

	    private static Map<String, Entry<Long, Class<? extends Material>>> materialFlagRegistry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	    public static void registerMaterialFlag(String name, long value, Class<? extends Material> classFilter) {
	        if(materialFlagRegistry.containsKey(name))
	            throw new IllegalArgumentException("Flag with name " + name + " already registered!");
	        materialFlagRegistry.put(name, new SimpleEntry<>(value, classFilter));
        }

        public static void registerMaterialFlagsHolder(Class<?> holder, Class<? extends Material> lowerBounds) {
	        for(Field holderField : holder.getFields()) {
	            int modifiers = holderField.getModifiers();
	            if(holderField.getType() != long.class ||
                    !Modifier.isPublic(modifiers) ||
                    !Modifier.isStatic(modifiers) ||
                    !Modifier.isFinal(modifiers))
	                continue;
	            String flagName = holderField.getName();
                long flagValue;
                try {
                    flagValue = holderField.getLong(null);
                } catch (IllegalAccessException exception) {
                    throw new RuntimeException(exception);
                }
                registerMaterialFlag(flagName, flagValue, lowerBounds);
            }
        }

        public static long resolveFlag(String name, Class<? extends Material> selfClass) {
            Entry<Long, Class<? extends Material>> flagEntry = materialFlagRegistry.get(name);
            if(flagEntry == null)
                throw new IllegalArgumentException("Flag with name " + name + " not registered");
            else if(!flagEntry.getValue().isAssignableFrom(selfClass))
                throw new IllegalArgumentException("Flag " + name + " cannot be applied to material type " +
                    selfClass.getSimpleName() + ", lower bound is " + flagEntry.getValue().getSimpleName());
            return flagEntry.getKey();
        }

        static {
	        registerMaterialFlagsHolder(MatFlags.class, Material.class);
        }

		/**
		 * Enables electrolyzer decomposition recipe generation
		 */
		public static final long DECOMPOSITION_BY_ELECTROLYZING = createFlag(0);

		/**
		 * Enables centrifuge decomposition recipe generation
		 */
		public static final long DECOMPOSITION_BY_CENTRIFUGING = createFlag(1);

        /**
         * Add to material if it has constantly burning aura
         */
        public static final long BURNING = createFlag(7);

		/**
		 * Add to material if it is some kind of flammable
		 */
		public static final long FLAMMABLE = createFlag(2);

		/**
		 * Add to material if it is some kind of explosive
		 */
		public static final long EXPLOSIVE = createFlag(4);

		/**
		 * Add to material to disable it's unification fully
		 */
		public static final long NO_UNIFICATION = createFlag(5);

		/**
		 * Add to material if any of it's items cannot be recycled to get scrub
		 */
		public static final long NO_RECYCLING = createFlag(6);
	}

	/**
	 * Color of material in RGB format
	 */
	public final int materialRGB;

	/**
	 * Chemical formula of this material
	 */
	public final String chemicalFormula;

	/**
	 * Icon set for this material meta-items generation
	 */
	public final MaterialIconSet materialIconSet;

	/**
	 * List of this material component
	 */
	public final ImmutableList<MaterialStack> materialComponents;

	/**
	 * Generation flags of this material
	 * @see MatFlags
	 * @see DustMaterial.MatFlags
	 */
	protected long materialGenerationFlags;

	/**
	 * Element of this material consist of
	 */
	public final Element element;

	private String calculateChemicalFormula() {
	    if(element != null) {
	        return element.name();
        }
        if(!materialComponents.isEmpty()) {
	        StringBuilder components = new StringBuilder();
	        for(MaterialStack component : materialComponents)
	            components.append(component.toString());
	        return components.toString();
        }
        return "";
    }

	public Material(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
		this.materialRGB = materialRGB;
		this.materialIconSet = materialIconSet;
		this.materialComponents = materialComponents;
		this.materialGenerationFlags = verifyMaterialBits(materialGenerationFlags);
		this.element = element;
        this.chemicalFormula = calculateChemicalFormula();
        calculateDecompositionType();
        initializeMaterial();
        //do not register any marker materials in registry
        if(!(this instanceof MarkerMaterial)) {
            if(metaItemSubId > -1) {
                //if we have an generated metaitem, register ourselves with meta item ID
                MATERIAL_REGISTRY.register(metaItemSubId, name, this);
            } else {
                //if we doesn't, just put name mapping for this material
                MATERIAL_REGISTRY.putObject(name, this);
            }
        }
	}

	protected void initializeMaterial() {
    }

	protected long verifyMaterialBits(long materialBits) {
		return materialBits;
	}

	public void add(long... materialGenerationFlags) {
		long combined = 0;
		for (long materialGenerationFlag : materialGenerationFlags) {
			combined |= materialGenerationFlag;
		}
		this.materialGenerationFlags |= verifyMaterialBits(combined);
	}

	public boolean hasFlag(long generationFlag) {
		return (materialGenerationFlags & generationFlag) != 0;
	}

	protected void calculateDecompositionType() {
	    if(!materialComponents.isEmpty() &&
            !hasFlag(MatFlags.DECOMPOSITION_BY_CENTRIFUGING) &&
            !hasFlag(MatFlags.DECOMPOSITION_BY_ELECTROLYZING)) {
	        boolean onlyFluidMaterials = true;
	        boolean onlyMetalMaterials = true;
	        for(MaterialStack materialStack : materialComponents) {
	            Material material = materialStack.material;
	            onlyFluidMaterials &= material.getClass() == FluidMaterial.class;
	            onlyMetalMaterials &= material.getClass() == MetalMaterial.class;
            }
            if(onlyFluidMaterials || onlyMetalMaterials) {
	            //if we contain only fluids or only metals, then centrifuging will do it's job
	            materialGenerationFlags |= MatFlags.DECOMPOSITION_BY_CENTRIFUGING;
            } else {
	            //otherwise, we use electrolyzing to break material into ions
	            materialGenerationFlags |= MatFlags.DECOMPOSITION_BY_ELECTROLYZING;
            }
        }
    }

	public boolean isRadioactive() {
		if (element != null)
			return element.halfLifeSeconds >= 0;
		for (MaterialStack material : materialComponents)
			if (material.material.isRadioactive()) return true;
		return false;
	}

	public long getProtons() {
		if (element != null)
			return element.getProtons();
		if (materialComponents.size() <= 0)
			return Element.Tc.getProtons();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack material : materialComponents) {
			totalAmount += material.amount;
			totalProtons += material.amount * material.material.getProtons();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getNeutrons() {
		if (element != null)
			return element.getNeutrons();
		if (materialComponents.size() <= 0)
			return Element.Tc.getNeutrons();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack material : materialComponents) {
			totalAmount += material.amount;
			totalProtons += material.amount * material.material.getNeutrons();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getMass() {
		if (element != null)
			return element.getMass();
		if (materialComponents.size() <= 0)
			return Element.Tc.getMass();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack material : materialComponents) {
			totalAmount += material.amount;
			totalProtons += material.amount * material.material.getMass();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getDensity() {
		return M;
	}

	public String toCamelCaseString() {
		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, toString());
	}

	public String getUnlocalizedName() {
	    return "material." + MATERIAL_REGISTRY.getNameForObject(this);
    }

	@SideOnly(Side.CLIENT)
	public String getLocalizedName() {
		return I18n.format(getUnlocalizedName());
	}

	@Override
	public String toString() {
		return MATERIAL_REGISTRY.getNameForObject(this);
	}

	@Override
	public int compareTo(Material material) {
		String anotherId = MATERIAL_REGISTRY.getNameForObject(material);
		return MATERIAL_REGISTRY.getNameForObject(this).compareTo(anotherId);
	}

	public static @Nullable Material get(String matUnlocalizedName) {
		return MATERIAL_REGISTRY.getObject(matUnlocalizedName);
	}
}