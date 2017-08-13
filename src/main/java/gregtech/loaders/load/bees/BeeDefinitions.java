package gregtech.loaders.load.bees;

import forestry.api.apiculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesType;
import forestry.apiculture.PluginApiculture;
import forestry.apiculture.genetics.Bee;
import forestry.apiculture.genetics.IBeeDefinition;
import forestry.apiculture.items.EnumHoneyComb;
import forestry.core.genetics.alleles.AlleleHelper;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.common.items.CombType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;
import java.util.Locale;


public enum BeeDefinitions implements IBeeDefinition, ISpeciesType {

    CLAY(BranchDefinitions.ORGANIC, "Clay", true, 0x19d0ec, 0xffdc16) {

        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addSpecialty(PluginApiculture.items.beeComb.get(EnumHoneyComb.HONEY, 1), 0.30f);
            beeSpecies.addProduct(new ItemStack(Items.CLAY_BALL, 1), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
            IBeeMutationBuilder tMutation = registerMutation(getSpecies("Industrious"), getSpecies("Diligent"), 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    SLIMEBALL(BranchDefinitions.ORGANIC, "SlimeBall", true, 0x4E9E55, 0x00FF15) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(PluginApiculture.items.beeComb.get(EnumHoneyComb.MELLOW, 1), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STICKY), 0.30f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Marshy"), CLAY.species, 15);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    PEAT(BranchDefinitions.ORGANIC, "Peat", true, 0x906237, 0x58300B) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.LIGNIE), 0.30f);
            beeSpecies.addProduct(PluginApiculture.items.beeComb.get(EnumHoneyComb.HONEY, 1), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Rural"), CLAY.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    STICKYRESIN(BranchDefinitions.ORGANIC, "StickyResin", true, 0x2E8F5B, 0xDCC289) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(PluginApiculture.items.beeComb.get(EnumHoneyComb.HONEY, 1), 0.30f);
            beeSpecies.addProduct(ItemList.IC2_Resin.get(1), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(SLIMEBALL.species, PEAT.species, 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    COAL(BranchDefinitions.ORGANIC, "Coal", true, 0x666666, 0x525252) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.LIGNIE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.COAL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Industrious"), PEAT.species, 18);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    OIL(BranchDefinitions.ORGANIC, "Oil", true, 0x4C4C4C, 0x333333) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(PluginApiculture.items.beeComb.get(EnumHoneyComb.HONEY, 1), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.OIL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(COAL.species, STICKYRESIN.species, 8);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    REDSTONE(BranchDefinitions.GEM, "Redstone", true, 0x7D0F0F, 0xD11919) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.REDSTONE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Industrious"), getSpecies("Demonic"), 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    LAPIS(BranchDefinitions.GEM, "Lapis", true, 0x1947D1, 0x476CDA) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.LAPIS), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Demonic"), getSpecies("Imperial"), 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    CERTUS(BranchDefinitions.GEM, "CertusQuartz", true, 0x57CFFB, 0xBBEEFF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.CERTUS), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Hermitic"), LAPIS.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    RUBY(BranchDefinitions.GEM, "Ruby", true, 0xE6005C, 0xCC0052) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.RUBY), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(REDSTONE.species, DIAMOND.species, 10);
            BeeManager.beeRoot.registerMutation(tMutation.build());

        }
    },
    SAPPHIRE(BranchDefinitions.GEM, "Sapphire", true, 0x0033CC, 0x00248F) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SAPPHIRE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(CERTUS.species, LAPIS.species, 10);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    DIAMOND(BranchDefinitions.GEM, "Diamond", true, 0xCCFFFF, 0xA3CCCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.DIAMOND), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(CERTUS.species, COAL.species, 6);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    OLIVINE(BranchDefinitions.GEM, "Olivine", true, 0x248F24, 0xCCFFCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.OLIVINE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(CERTUS.species, getSpecies("Ended"), 10);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    EMERALD(BranchDefinitions.GEM, "Emerald", true, 0x248F24, 0x2EB82E) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STONE), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.EMERALD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(OLIVINE.species, DIAMOND.species, 8);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    COPPER(BranchDefinitions.METAL, "Copper", true, 0xFF6600, 0xE65C00) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.COPPER), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Majestic"), CLAY.species, 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    TIN(BranchDefinitions.METAL, "Tin", true, 0xD4D4D4, 0xDDDDDD) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.TIN), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(CLAY.species, getSpecies("Diligent"), 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    LEAD(BranchDefinitions.METAL, "Lead", true, 0x666699, 0xA3A3CC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.LEAD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.DAMP);
            beeSpecies.setTemperature(EnumTemperature.WARM);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(COAL.species, COPPER.species, 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    IRON(BranchDefinitions.METAL, "Iron", true, 0xDA9147, 0xDE9C59) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.IRON), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(TIN.species, COPPER.species, 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    STEEL(BranchDefinitions.METAL, "Steel", true, 0x808080, 0x999999) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.STEEL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(IRON.species, COAL.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    NICKEL(BranchDefinitions.METAL, "Nickel", true, 0x8585AD, 0x8585AD) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.NICKEL), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(IRON.species, COPPER.species, 25);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    ZINC(BranchDefinitions.METAL, "Zinc", true, 0xF0DEF0, 0xF2E1F2) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.ZINC), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(IRON.species, TIN.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    SILVER(BranchDefinitions.METAL, "Silver", true, 0xC2C2D6, 0xCECEDE) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SILVER), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(LEAD.species, TIN.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    GOLD(BranchDefinitions.METAL, "Gold", true, 0xEBC633, 0xEDCC47) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.GOLD), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.NORMAL);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(LEAD.species, COPPER.species, 20);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    ALUMINIUM(BranchDefinitions.RAREMETAL, "Aluminium", true, 0xB8B8FF, 0xD6D6FF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.ALUMINIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(NICKEL.species, ZINC.species, 18);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    TITANIUM(BranchDefinitions.RAREMETAL, "Titanium", true, 0xCC99FF, 0xDBB8FF) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.TITANIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(REDSTONE.species, ALUMINIUM.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.build());
        }
    },
    CHROME(BranchDefinitions.RAREMETAL, "Chrome", true, 0xEBA1EB, 0xF2C3F2) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.CHROME), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(TITANIUM.species, RUBY.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal2.getStateFromMeta(3)).build());
        }
    },
    MANGANESE(BranchDefinitions.RAREMETAL, "Manganese", true, 0xD5D5D5, 0xAAAAAA) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.MANGANESE), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(TITANIUM.species, ALUMINIUM.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal4.getStateFromMeta(6)).build());
        }
    },
    TUNGSTEN(BranchDefinitions.RAREMETAL, "Tungsten", true, 0x5C5C8A, 0x7D7DA1) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.TUNGSTEN), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Heroic"), MANGANESE.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal7.getStateFromMeta(11)).build());
        }
    },
    PLATINUM(BranchDefinitions.RAREMETAL, "Platinum", true, 0xE6E6E6, 0xFFFFCC) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.PLATINUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HOT);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(DIAMOND.species, CHROME.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal5.getStateFromMeta(12)).build());
        }
    },
    IRIDIUM(BranchDefinitions.RAREMETAL, "Iridium", true, 0xDADADA, 0xD1D1E0) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.TUNGSTEN), 0.15f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.PLATINUM), 0.15f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.IRIDIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.HELLISH);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(TUNGSTEN.species, PLATINUM.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal3.getStateFromMeta(12)).build());
        }
    },
    URANIUM(BranchDefinitions.RADIOACTIVE, "Uranium", true, 0x19AF19, 0x169E16) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.URANIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.COLD);
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(getSpecies("Avenging"), PLATINUM.species, 5);
            BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal7.getStateFromMeta(14)).build());
        }
    },
    PLUTONIUM(BranchDefinitions.RADIOACTIVE, "Plutonium", true, 0x335C33, 0x6B8F00) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.PLUTONIUM), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.NORMAL);
            beeSpecies.setTemperature(EnumTemperature.ICY);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(URANIUM.species, EMERALD.species, 5);
            //BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal4.getStateFromMeta(13)).build());
        }
    },
    NAQUADAH(BranchDefinitions.RADIOACTIVE, "Naquadah", true, 0x003300, 0x002400) {
        @Override
        protected void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies) {
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.SLAG), 0.30f);
            beeSpecies.addProduct(BeeLoader.combs.getStackForType(CombType.NAQUADAH), 0.15f);
            beeSpecies.setHumidity(EnumHumidity.ARID);
            beeSpecies.setTemperature(EnumTemperature.ICY);
            beeSpecies.setNocturnal();
        }

        @Override
        protected void setAlleles(IAllele[] template) {
        }

        @Override
        protected void registerMutations() {
        	IBeeMutationBuilder tMutation = registerMutation(PLUTONIUM.species, IRIDIUM.species, 3);
            //BeeManager.beeRoot.registerMutation(tMutation.requireResource(GregTech_API.sBlockMetal4.getStateFromMeta(12)).build());
        }
    };


    private final BranchDefinitions branch;
    private final IAlleleBeeSpeciesBuilder speciesBuilder;
    public IAlleleBeeSpecies species;

    private IAllele[] template;
    private IBeeGenome genome;

    BeeDefinitions(BranchDefinitions branch, String binomial, boolean dominant, int primary, int secondary) {
        String lowercaseName = this.toString().toLowerCase(Locale.ENGLISH);
        String species = "species" + WordUtils.capitalize(lowercaseName);

        String uid = "forestry." + species;
        String description = "for.description." + species;
        String name = "for.bees.species." + lowercaseName;

        this.branch = branch;
        this.speciesBuilder = BeeManager.beeFactory.createSpecies(uid, dominant, "Sengir", name, description, branch.getBranch(), binomial, primary, secondary);
    }

    public static void initBees() {
        for (BeeDefinitions bee : values()) {
            bee.init();
        }
        for (BeeDefinitions bee : values()) {
            bee.registerMutations();
        }
    }

    private static IAlleleBeeSpecies getSpecies(String name) {
        return (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele((new StringBuilder()).append("forestry.species").append(name).toString());
    }

    protected abstract void setSpeciesProperties(IAlleleBeeSpeciesBuilder beeSpecies);

    protected abstract void setAlleles(IAllele[] template);

    protected abstract void registerMutations();

    private void init() {
        setSpeciesProperties(speciesBuilder);
        species = speciesBuilder.build();

        this.template = branch.getTemplate();
        AlleleHelper.instance.set(template, EnumBeeChromosome.SPECIES, species);
        setAlleles(template);

        this.genome = BeeManager.beeRoot.templateAsGenome(template);

        BeeManager.beeRoot.registerTemplate(template);
    }

    protected final IBeeMutationBuilder registerMutation(IAlleleBeeSpecies parent1, IAlleleBeeSpecies parent2, int chance) {
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
        return BeeManager.beeRoot.getMemberStack(bee, this);
    }

}
