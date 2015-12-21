package gregtech.loaders.misc;

import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.BeeDefinition;
import forestry.apiculture.genetics.BeeVariation;
import forestry.apiculture.genetics.IBeeDefinition;
//import forestry.apiculture.items.EnumHoneyComb;
import forestry.core.genetics.alleles.AlleleHelper;
//import forestry.plugins.PluginApiculture;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.items.CombType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;
import java.util.Locale;

public enum GT_BeeDefinition implements IBeeDefinition {
    CLAY(GT_BranchDefinition.ORGANIC, "Clay", true, 0x19d0ec, 0xffdc16) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_ModHandler.getModItem(GT_Values.MOD_ID_FR, "beeCombs", 1, 0), 0.30f);
            beeSpecies.addProduct(new ItemStack(Items.clay_ball, 1), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.MEADOWS.getTemplate();
        }

        @Override
        protected void registerMutations() {
            IBeeMutationCustom tMutation = registerMutation(getSpecies("Industrious"), getSpecies("Diligent"), 20);
        }
    },
    SLIMEBALL(GT_BranchDefinition.ORGANIC, "SlimeBall", true, 0x4E9E55, 0x00FF15) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_ModHandler.getModItem(GT_Values.MOD_ID_FR, "beeCombs", 1, 15), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STICKY), 0.30f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.MARSHY.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Marshy"), CLAY.species, 15);
        }
    },
    PEAT(GT_BranchDefinition.ORGANIC, "Peat", true, 0x906237, 0x58300B) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.LIGNIE), 0.30f);
            beeSpecies.addProduct(GT_ModHandler.getModItem(GT_Values.MOD_ID_FR, "beeCombs", 1, 0), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.RURAL.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Rural"), CLAY.species, 20);
        }
    },
    STICKYRESIN(GT_BranchDefinition.ORGANIC, "StickyResin", true, 0x2E8F5B, 0xDCC289) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_ModHandler.getModItem(GT_Values.MOD_ID_FR, "beeCombs", 1, 0), 0.30f);
            beeSpecies.addProduct(ItemList.IC2_Resin.get(1, new Object[0]), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.MEADOWS.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(SLIMEBALL.species, PEAT.species, 25);
        }
    },
    COAL(GT_BranchDefinition.ORGANIC, "Coal", true, 0x666666, 0x525252) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.LIGNIE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.COAL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.AUSTERE.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Industrious"), PEAT.species, 18);
        }
    },
    OIL(GT_BranchDefinition.ORGANIC, "Oil", true, 0x4C4C4C, 0x333333) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_ModHandler.getModItem(GT_Values.MOD_ID_FR, "beeCombs", 1, 0), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.OIL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.MEADOWS.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(COAL.species, STICKYRESIN.species, 8);
        }
    },
    REDSTONE(GT_BranchDefinition.GEM, "Redstone", true, 0x7D0F0F, 0xD11919) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.REDSTONE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Industrious"), getSpecies("Demonic"), 20);
        }
    },
    LAPIS(GT_BranchDefinition.GEM, "Lapis", true, 0x1947D1, 0x476CDA) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.LAPIS), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Demonic"), getSpecies("Imperial"), 20);
        }
    },
    CERTUS(GT_BranchDefinition.GEM, "CertusQuartz", true, 0x57CFFB, 0xBBEEFF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.CERTUS), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Hermitic"), LAPIS.species, 20);
        }
    },
    RUBY(GT_BranchDefinition.GEM, "Ruby", true, 0xE6005C, 0xCC0052) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.RUBY), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(REDSTONE.species, DIAMOND.species, 10);
        }
    },
    SAPPHIRE(GT_BranchDefinition.GEM, "Sapphire", true, 0x0033CC, 0x00248F) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SAPPHIRE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(CERTUS.species, LAPIS.species, 10);
        }
    },
    DIAMOND(GT_BranchDefinition.GEM, "Diamond", true, 0xCCFFFF, 0xA3CCCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.DIAMOND), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(CERTUS.species, COAL.species, 6);
        }
    },
    OLIVINE(GT_BranchDefinition.GEM, "Olivine", true, 0x248F24, 0xCCFFCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.OLIVINE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(CERTUS.species, getSpecies("Ended"), 10);
        }
    },
    EMERALD(GT_BranchDefinition.GEM, "Emerald", true, 0x248F24, 0x2EB82E) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.EMERALD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(OLIVINE.species, DIAMOND.species, 8);
        }
    },
    COPPER(GT_BranchDefinition.METAL, "Copper", true, 0xFF6600, 0xE65C00) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.COPPER), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Majestic"), CLAY.species, 25);
        }
    },
    TIN(GT_BranchDefinition.METAL, "Tin", true, 0xD4D4D4, 0xDDDDDD) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.TIN), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(CLAY.species, getSpecies("Diligent"), 25);
        }
    },
    LEAD(GT_BranchDefinition.METAL, "Lead", true, 0x666699, 0xA3A3CC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.LEAD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(COAL.species, COPPER.species, 25);
        }
    },
    IRON(GT_BranchDefinition.METAL, "Iron", true, 0xDA9147, 0xDE9C59) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.IRON), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(TIN.species, COPPER.species, 25);
        }
    },
    STEEL(GT_BranchDefinition.METAL, "Steel", true, 0x808080, 0x999999) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.STEEL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(IRON.species, COAL.species, 20);
        }
    },
    NICKEL(GT_BranchDefinition.METAL, "Nickel", true, 0x8585AD, 0x8585AD) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.NICKEL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(IRON.species, COPPER.species, 25);
        }
    },
    ZINC(GT_BranchDefinition.METAL, "Zinc", true, 0xF0DEF0, 0xF2E1F2) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.ZINC), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(IRON.species, TIN.species, 20);
        }
    },
    SILVER(GT_BranchDefinition.METAL, "Silver", true, 0xC2C2D6, 0xCECEDE) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SILVER), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(LEAD.species, TIN.species, 20);
        }
    },
    GOLD(GT_BranchDefinition.METAL, "Gold", true, 0xEBC633, 0xEDCC47) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.GOLD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(LEAD.species, COPPER.species, 20);
        }
    },
    ALUMINIUM(GT_BranchDefinition.RAREMETAL, "Aluminium", true, 0xB8B8FF, 0xD6D6FF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.ALUMINIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(NICKEL.species, ZINC.species, 18);
        }
    },
    TITANIUM(GT_BranchDefinition.RAREMETAL, "Titanium", true, 0xCC99FF, 0xDBB8FF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.TITANIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(REDSTONE.species, ALUMINIUM.species, 5);
        }
    },
    CHROME(GT_BranchDefinition.RAREMETAL, "Chrome", true, 0xEBA1EB, 0xF2C3F2) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.CHROME), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(TITANIUM.species, RUBY.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal2, 3);
        }
    },
    MANGANESE(GT_BranchDefinition.RAREMETAL, "Manganese", true, 0xD5D5D5, 0xAAAAAA) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.MANGANESE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(TITANIUM.species, ALUMINIUM.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal4, 6);
        }
    },
    TUNGSTEN(GT_BranchDefinition.RAREMETAL, "Tungsten", true, 0x5C5C8A, 0x7D7DA1) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.TUNGSTEN), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Heroic"), MANGANESE.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal7, 11);
        }
    },
    PLATINUM(GT_BranchDefinition.RAREMETAL, "Platinum", true, 0xE6E6E6, 0xFFFFCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.PLATINUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(DIAMOND.species, CHROME.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal5, 12);
        }
    },
    IRIDIUM(GT_BranchDefinition.RAREMETAL, "Iridium", true, 0xDADADA, 0xD1D1E0) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.TUNGSTEN), 0.15f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.PLATINUM), 0.15f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.IRIDIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HELLISH);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.COMMON.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(TUNGSTEN.species, PLATINUM.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal3, 12);
        }
    },
    URANIUM(GT_BranchDefinition.RADIOACTIVE, "Uranium", true, 0x19AF19, 0x169E16) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.URANIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.AVENGING.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(getSpecies("Avenging"), PLATINUM.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal7, 14);
        }
    },
    PLUTONIUM(GT_BranchDefinition.RADIOACTIVE, "Plutonium", true, 0x335C33, 0x6B8F00) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.PLUTONIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.ICY);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.AVENGING.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(URANIUM.species, EMERALD.species, 5);
        	tMutation.requireResource(GregTech_API.sBlockMetal5, 13);
        }
    },
    NAQUADAH(GT_BranchDefinition.RADIOACTIVE, "Naquadah", true, 0x003300, 0x002400) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies) {
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(GT_Bees.combs.getStackForType(CombType.NAQUADAH), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.ICY);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
            template = BeeDefinition.AVENGING.getTemplate();
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationCustom tMutation = registerMutation(PLUTONIUM.species, IRIDIUM.species, 3);
        	tMutation.requireResource(GregTech_API.sBlockMetal4, 12);
        }
    };


    private final GT_BranchDefinition branch;
    private final IAlleleBeeSpeciesCustom species;

    private IAllele[] template;
    private IBeeGenome genome;

    GT_BeeDefinition(GT_BranchDefinition branch, String binomial, boolean dominant, int primary, int secondary) {
        String lowercaseName = this.toString().toLowerCase(Locale.ENGLISH);
        String species = "species" + WordUtils.capitalize(lowercaseName);

        String uid = "forestry." + species;
        String description = "for.description." + species;
        String name = "for.bees.species." + lowercaseName;

        this.branch = branch;
        this.species = BeeManager.beeFactory.createSpecies(uid, dominant, "Sengir", name, description, branch.getBranch(), binomial, primary, secondary);
    }

    public static void initBees() {
        for (GT_BeeDefinition bee : values()) {
            bee.init();
        }
        for (GT_BeeDefinition bee : values()) {
            bee.registerMutations();
        }
    }

    private static IAlleleBeeSpecies getSpecies(String name) {
        return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("forestry.species").append(name).toString());
    }

    protected abstract void setSpeciesProperties(IAlleleBeeSpeciesCustom beeSpecies);

    protected abstract void setAlleles(IAllele[] template);

    protected abstract void registerMutations();

    private void init() {
        setSpeciesProperties(species);

        template = branch.getTemplate();
        AlleleHelper.instance.set(template, EnumBeeChromosome.SPECIES, species);
        setAlleles(template);

        genome = BeeManager.beeRoot.templateAsGenome(template);

        BeeManager.beeRoot.registerTemplate(template);
    }

    protected final IBeeMutationCustom registerMutation(IAlleleBeeSpecies parent1, IAlleleBeeSpecies parent2, int chance) {
        return BeeManager.beeMutationFactory.createMutation(parent1, parent2, getTemplate(), chance);
    }

    @Override
    public final IAllele[] getTemplate() {
        return Arrays.copyOf(template, template.length);
    }

    @Override
    public final IBeeGenome getGenome() {
        return genome;
    }

    @Override
    public final IBee getIndividual() {
        return new Bee(genome);
    }

    @Override
    public final ItemStack getMemberStack(EnumBeeType beeType) {
        IBee bee = getIndividual();
        return BeeManager.beeRoot.getMemberStack(bee, beeType.ordinal());
    }

    public final IBeeDefinition getRainResist() {
        return new BeeVariation.RainResist(this);
    }

}
