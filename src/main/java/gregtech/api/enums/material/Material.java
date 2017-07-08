package gregtech.api.enums.material;

import com.google.common.collect.ImmutableList;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GTControlledRegistry;
import gregtech.api.util.GT_Log;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import static gregtech.api.enums.GT_Values.M;

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
		/**
		 * When specified, this material will have plasma fluid generated for it
		 * Note that this option is handled differently for solid materials and liquid materials, and cannot be applied to rough and dust material kinds
		 */
		public static final int GENERATE_PLASMA =                createFlag(1);

		public static final int GENERATE_DECOMPOSITION_RECIPES = createFlag(2);
		public static final int DECOMPOSITION_BY_ELECTROLYZING = createFlag(3);
		public static final int DECOMPOSITION_BY_CENTRIFUGING =  createFlag(4);

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
	 * True if this material can be safely unified by OreDictionary
	 * False for generic materials like AnyIron or AnyBronze
	 */
	public final boolean unifiable;

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
	 * @see gregtech.api.enums.material.SolidMaterial.MatFlags
	 */
	private int materialGenerationFlags;

	/**
	 * Number to multiply standard Material Unit by
	 */
	private final float densityMultiplier;

	/**
	 * Element of this material consist of
	 */
	public final Element element;

	/**
	 * True if this material should be initialized and loaded
	 * False otherwise
	 */
	public final boolean mHasParentMod;

	/**
	 * Material kind is type of material
	 */
	public abstract MaterialKind getKind();

	public abstract FluidStack getFluid(int amount);
	public abstract FluidStack getPlasma(int amount);

	protected Material(String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, boolean unifiable, ImmutableList<MaterialStack> materialComponents, ImmutableList<Material> oreReRegistrations, ImmutableList<SubTag> subTags, float densityMultiplier, Element element, boolean mHasParentMod, String chemicalFormula) {
		this.defaultLocalName = defaultLocalName;
		this.materialRGB = materialRGB;
		this.materialIconSet = materialIconSet;
		this.unifiable = unifiable;
		this.materialComponents = materialComponents;
		this.oreReRegistrations = oreReRegistrations;
		this.subTags = subTags;
		this.densityMultiplier = densityMultiplier;
		this.element = element;
		this.mHasParentMod = mHasParentMod;
		this.chemicalFormula = chemicalFormula;
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