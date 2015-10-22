package gregtech.loaders.misc;

import java.util.Arrays;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.core.genetics.alleles.Allele;
import forestry.core.genetics.alleles.EnumAllele;



public enum GT_BranchDefinition {
	
	ORGANIC("Fuelis"){
		@Override
		protected void setBranchProperties(IAllele[] alleles) {
			Allele.helper.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			Allele.helper.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
		}
	},
	GEM("Ornamentis"){
		@Override
		protected void setBranchProperties(IAllele[] alleles) {
			Allele.helper.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			Allele.helper.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
		}
	},
	METAL("Metaliferis"){
		@Override
		protected void setBranchProperties(IAllele[] alleles) {
			Allele.helper.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			Allele.helper.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
		}
	},
	RAREMETAL("Mineralis"){
		@Override
		protected void setBranchProperties(IAllele[] alleles) {
			Allele.helper.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			Allele.helper.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
		}
	},
	RADIOACTIVE("Criticalis"){
		@Override
		protected void setBranchProperties(IAllele[] alleles) {
			Allele.helper.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			Allele.helper.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
		}
	};
	
	private final IClassification branch;

	GT_BranchDefinition(String scientific) {
		branch = BeeManager.beeFactory.createBranch(this.name().toLowerCase(), scientific);
	}

	protected void setBranchProperties(IAllele[] template) {

	}

	public final IAllele[] getTemplate() {
		IAllele[] template = getDefaultTemplate();
		setBranchProperties(template);
		return template;
	}

	public final IClassification getBranch() {
		return branch;
	}

	private static IAllele[] defaultTemplate;

	private static IAllele[] getDefaultTemplate() {
		if (defaultTemplate == null) {
			defaultTemplate = new IAllele[EnumBeeChromosome.values().length];

			Allele.helper.set(defaultTemplate, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.NOCTURNAL, false);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.TOLERANT_FLYER, false);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.CAVE_DWELLING, false);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
			Allele.helper.set(defaultTemplate, EnumBeeChromosome.EFFECT, Allele.effectNone);
		}
		return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
	}

}
