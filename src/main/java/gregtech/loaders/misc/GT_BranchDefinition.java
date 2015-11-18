package gregtech.loaders.misc;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.apiculture.genetics.alleles.AlleleEffect;
import forestry.core.genetics.alleles.Allele;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;

import java.util.Arrays;


public enum GT_BranchDefinition {

    ORGANIC("Fuelis") {
        @Override
        protected void setBranchProperties(IAllele[] alleles) {
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_2);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.MUSHROOMS);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOW);
        }
    },
    GEM("Ornamentis") {
        @Override
        protected void setBranchProperties(IAllele[] alleles) {
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
        }
    },
    METAL("Metaliferis") {
        @Override
        protected void setBranchProperties(IAllele[] alleles) {
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_2);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.JUNGLE);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWER);
        }
    },
    RAREMETAL("Mineralis") {
        @Override
        protected void setBranchProperties(IAllele[] alleles) {
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.CACTI);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.FAST);
        }
    },
    RADIOACTIVE("Criticalis") {
        @Override
        protected void setBranchProperties(IAllele[] alleles) {
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.END);
            AlleleHelper.instance.set(alleles, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.AVERAGE);
        }
    };

    private static IAllele[] defaultTemplate;
    private final IClassification branch;

    GT_BranchDefinition(String scientific) {
        branch = BeeManager.beeFactory.createBranch(this.name().toLowerCase(), scientific);
    }

    private static IAllele[] getDefaultTemplate() {
        if (defaultTemplate == null) {
            defaultTemplate = new IAllele[EnumBeeChromosome.values().length];

            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.SPEED, EnumAllele.Speed.SLOWEST);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FERTILITY, EnumAllele.Fertility.NORMAL);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.NONE);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.NOCTURNAL, false);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.NONE);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TOLERANT_FLYER, false);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.CAVE_DWELLING, false);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FLOWER_PROVIDER, EnumAllele.Flowers.VANILLA);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.FLOWERING, EnumAllele.Flowering.SLOWEST);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.TERRITORY, EnumAllele.Territory.AVERAGE);
            AlleleHelper.instance.set(defaultTemplate, EnumBeeChromosome.EFFECT, AlleleEffect.effectNone);
        }
        return Arrays.copyOf(defaultTemplate, defaultTemplate.length);
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

}
