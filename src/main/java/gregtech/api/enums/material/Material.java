package gregtech.api.enums.material;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.types.DustMaterial;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GTControlledRegistry;
import gregtech.api.util.GT_Log;
import net.minecraft.util.ResourceLocation;

import static gregtech.api.enums.GT_Values.M;
import static gregtech.api.enums.SubTag.getNewSubTag;

public abstract class  Material implements ISubTagContainer, Comparable<Material> {

	public static GTControlledRegistry<Material> MATERIAL_REGISTRY = new GTControlledRegistry<>();
	public static GTControlledRegistry<IMaterialHandler> MATERIAL_HANDLER_REGISTRY = new GTControlledRegistry<>();

	/**
	 * Initializes material and also creates fluid instances
	 */
	public static void init() {
		MATERIAL_HANDLER_REGISTRY.freezeRegistry();
		for (String name : MATERIAL_HANDLER_REGISTRY.getKeys()) {
			IMaterialHandler materialHandler = MATERIAL_HANDLER_REGISTRY.getObject(name);
			try {
				materialHandler.onMaterialsInit();
			} catch (Throwable exception) {
				GT_Log.logger.warn("Caught exception while trying to init materials by handler " +
						MATERIAL_HANDLER_REGISTRY.getFullNameForObject(materialHandler), exception);
			}
		}

		MATERIAL_REGISTRY.freezeRegistry();
		for(String name : MATERIAL_REGISTRY.getKeys()) {
			Material material = MATERIAL_REGISTRY.getObject(name);
			ResourceLocation resourceLocation = MATERIAL_REGISTRY.getFullNameForObject(material);
			material.initMaterial(resourceLocation);
		}
	}

	public static final class MatFlags {

		public static final int GENERATE_DECOMPOSITION_RECIPES = createFlag(0);
		public static final int DECOMPOSITION_BY_ELECTROLYZING = createFlag(1);
		public static final int DECOMPOSITION_BY_CENTRIFUGING =  createFlag(2);

		/**
		 * This Material cannot be unificated
		 */
		public static final SubTag NO_UNIFICATION = getNewSubTag("NO_UNIFICATION");

		/**
		 * This Material cannot be used in any Recycler. Already listed are:
		 * Stone, Glass, Water
		 */
		public static final SubTag NO_RECYCLING = getNewSubTag("NO_RECYCLING");

		public static int createFlag(int id) {
			return (int) Math.pow(2, id);
		}

	}

	/**
	 * Default Localized Material Name
	 * Since material amount is huge, default localization entries for them are auto generated
	 */
	public final String defaultLocalName;

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
	 * List of materials to re-register this material into
	 * As example, all Iron materials are re-registered into generic material AnyIron
	 */
	public final ImmutableList<Material> oreReRegistrations;

	/**
	 * List of SubTags added to this material
	 */
	public final ImmutableList<SubTag> subTags;

	/**
	 * Generation flags of this material
	 * @see MatFlags
	 * @see DustMaterial.MatFlags
	 */
	private final int materialGenerationFlags;

	/**
	 * Number to multiply standard Material Unit by
	 */
	private final float densityMultiplier;

	/**
	 * Element of this material consist of
	 */
	public final Element element;

	public Material(String defaultLocalName, int materialRGB, String chemicalFormula, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, int materialGenerationFlags, float densityMultiplier, Element element) {
		this.defaultLocalName = defaultLocalName;
		this.materialRGB = materialRGB;
		this.chemicalFormula = chemicalFormula;
		this.materialIconSet = materialIconSet;
		this.materialComponents = materialComponents;
		this.oreReRegistrations = oreReRegistrations;
		this.subTags = subTags;
		this.materialGenerationFlags = materialGenerationFlags;
		this.densityMultiplier = densityMultiplier;
		this.element = element;
	}

	public boolean hasFlag(int generationFlag) {
		return (materialGenerationFlags & generationFlag) != 0;
	}

	protected void initMaterial(ResourceLocation resourceLocation) {
	}

	public boolean isRadioactive() {
		if (element != null)
			return element.mHalfLifeSeconds >= 0;
		for (MaterialStack tMaterial : materialComponents)
			if (tMaterial.mMaterial.isRadioactive()) return true;
		return false;
	}

	public long getProtons() {
		if (element != null)
			return element.getProtons();
		if (materialComponents.size() <= 0)
			return Element.Tc.getProtons();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack tMaterial : materialComponents) {
			totalAmount += tMaterial.mAmount;
			totalProtons += tMaterial.mAmount * tMaterial.mMaterial.getProtons();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getNeutrons() {
		if (element != null)
			return element.getNeutrons();
		if (materialComponents.size() <= 0)
			return Element.Tc.getNeutrons();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack tMaterial : materialComponents) {
			totalAmount += tMaterial.mAmount;
			totalProtons += tMaterial.mAmount * tMaterial.mMaterial.getNeutrons();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getMass() {
		if (element != null)
			return element.getMass();
		if (materialComponents.size() <= 0)
			return Element.Tc.getMass();
		long totalProtons = 0, totalAmount = 0;
		for (MaterialStack tMaterial : materialComponents) {
			totalAmount += tMaterial.mAmount;
			totalProtons += tMaterial.mAmount * tMaterial.mMaterial.getMass();
		}
		return (getDensity() * totalProtons) / (totalAmount * M);
	}

	public long getDensity() {
		return (long) (M * densityMultiplier);
	}

	@Override
	public boolean contains(SubTag subTag) {
		return subTags.contains(subTag);
	}

	public boolean hasGenerationFlag(int flag) {
		return (materialGenerationFlags & flag) > 0;
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

}