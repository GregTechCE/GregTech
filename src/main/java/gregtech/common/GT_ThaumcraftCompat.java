package gregtech.common;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.TC_Aspects;
import gregtech.api.interfaces.internal.IThaumcraftCompat;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GT_ThaumcraftCompat
        implements IThaumcraftCompat {
    public GT_ThaumcraftCompat() {
        TC_Aspects.AER.mAspect = Aspect.AIR;
        TC_Aspects.ALIENIS.mAspect = Aspect.ELDRITCH;
        TC_Aspects.AQUA.mAspect = Aspect.WATER;
        TC_Aspects.ARBOR.mAspect = Aspect.TREE;
        TC_Aspects.AURAM.mAspect = Aspect.AURA;
        TC_Aspects.BESTIA.mAspect = Aspect.BEAST;
        TC_Aspects.COGNITIO.mAspect = Aspect.MIND;
        TC_Aspects.CORPUS.mAspect = Aspect.FLESH;
        TC_Aspects.EXANIMIS.mAspect = Aspect.UNDEAD;
        TC_Aspects.FABRICO.mAspect = Aspect.CRAFT;
        TC_Aspects.FAMES.mAspect = Aspect.HUNGER;
        TC_Aspects.GELUM.mAspect = Aspect.COLD;
        TC_Aspects.GRANUM.mAspect = Aspect.PLANT;
        TC_Aspects.HERBA.mAspect = Aspect.PLANT;
        TC_Aspects.HUMANUS.mAspect = Aspect.MAN;
        TC_Aspects.IGNIS.mAspect = Aspect.FIRE;
        TC_Aspects.INSTRUMENTUM.mAspect = Aspect.TOOL;
        TC_Aspects.ITER.mAspect = Aspect.TRAVEL;
        TC_Aspects.LIMUS.mAspect = Aspect.SLIME;
        TC_Aspects.LUCRUM.mAspect = Aspect.GREED;
        TC_Aspects.LUX.mAspect = Aspect.LIGHT;
        TC_Aspects.MACHINA.mAspect = Aspect.MECHANISM;
        TC_Aspects.MESSIS.mAspect = Aspect.CROP;
        TC_Aspects.METALLUM.mAspect = Aspect.METAL;
        TC_Aspects.METO.mAspect = Aspect.HARVEST;
        TC_Aspects.MORTUUS.mAspect = Aspect.DEATH;
        TC_Aspects.MOTUS.mAspect = Aspect.MOTION;
        TC_Aspects.ORDO.mAspect = Aspect.ORDER;
        TC_Aspects.PANNUS.mAspect = Aspect.CLOTH;
        TC_Aspects.PERDITIO.mAspect = Aspect.ENTROPY;
        TC_Aspects.PERFODIO.mAspect = Aspect.MINE;
        TC_Aspects.PERMUTATIO.mAspect = Aspect.EXCHANGE;
        TC_Aspects.POTENTIA.mAspect = Aspect.ENERGY;
        TC_Aspects.PRAECANTATIO.mAspect = Aspect.MAGIC;
        TC_Aspects.SANO.mAspect = Aspect.HEAL;
        TC_Aspects.SENSUS.mAspect = Aspect.SENSES;
        TC_Aspects.SPIRITUS.mAspect = Aspect.SOUL;
        TC_Aspects.TELUM.mAspect = Aspect.WEAPON;
        TC_Aspects.TERRA.mAspect = Aspect.EARTH;
        TC_Aspects.TEMPESTAS.mAspect = Aspect.WEATHER;
        TC_Aspects.TENEBRAE.mAspect = Aspect.DARKNESS;
        TC_Aspects.TUTAMEN.mAspect = Aspect.ARMOR;
        TC_Aspects.VACUOS.mAspect = Aspect.VOID;
        TC_Aspects.VENENUM.mAspect = Aspect.POISON;
        TC_Aspects.VICTUS.mAspect = Aspect.LIFE;
        TC_Aspects.VINCULUM.mAspect = Aspect.TRAP;
        TC_Aspects.VITIUM.mAspect = Aspect.TAINT;
        TC_Aspects.VITREUS.mAspect = Aspect.CRYSTAL;
        TC_Aspects.VOLATUS.mAspect = Aspect.FLIGHT;

        TC_Aspects.STRONTIO.mAspect = new Aspect("strontio", 15647411, new Aspect[]{Aspect.MIND, Aspect.ENTROPY}, new ResourceLocation("gregtech:textures/aspects/" + TC_Aspects.STRONTIO.name() + ".png"), 1);
        TC_Aspects.NEBRISUM.mAspect = new Aspect("nebrisum", 15658622, new Aspect[]{Aspect.MINE, Aspect.GREED}, new ResourceLocation("gregtech:textures/aspects/" + TC_Aspects.NEBRISUM.name() + ".png"), 1);
        TC_Aspects.ELECTRUM.mAspect = new Aspect("electrum", 12644078, new Aspect[]{Aspect.ENERGY, Aspect.MECHANISM}, new ResourceLocation("gregtech:textures/aspects/" + TC_Aspects.ELECTRUM.name() + ".png"), 1);
        TC_Aspects.MAGNETO.mAspect = new Aspect("magneto", 12632256, new Aspect[]{Aspect.METAL, Aspect.TRAVEL}, new ResourceLocation("gregtech:textures/aspects/" + TC_Aspects.MAGNETO.name() + ".png"), 1);
        TC_Aspects.RADIO.mAspect = new Aspect("radio", 12648384, new Aspect[]{Aspect.LIGHT, Aspect.ENERGY}, new ResourceLocation("gregtech:textures/aspects/" + TC_Aspects.RADIO.name() + ".png"), 1);

        GT_LanguageManager.addStringLocalization("tc.aspect.strontio", "Stupidness, Incompetence");
        GT_LanguageManager.addStringLocalization("tc.aspect.nebrisum", "Cheatyness, Raiding");
        GT_LanguageManager.addStringLocalization("tc.aspect.electrum", "Electricity, Lightning");
        GT_LanguageManager.addStringLocalization("tc.aspect.magneto", "Magnetism, Attraction");
        GT_LanguageManager.addStringLocalization("tc.aspect.radio", "Radiation");
    }

    private static final AspectList getAspectList(List<TC_Aspects.TC_AspectStack> aAspects) {
        AspectList rAspects = new AspectList();
        TC_Aspects.TC_AspectStack tAspect;
        for (Iterator i$ = aAspects.iterator(); i$.hasNext(); rAspects.add((Aspect) tAspect.mAspect.mAspect, (int) tAspect.mAmount)) {
            tAspect = (TC_Aspects.TC_AspectStack) i$.next();
        }
        return rAspects;
    }

    public Object addResearch(String aResearch, String aName, String aText, String[] aParentResearches, String aCategory, ItemStack aIcon, int aComplexity, int aType, int aX, int aY, List<TC_Aspects.TC_AspectStack> aAspects, ItemStack[] aResearchTriggers, Object[] aPages) {
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.researches, aResearch, true)) {
            return null;
        }
        ResearchCategoryList tCategory = ResearchCategories.getResearchList(aCategory);
        if (tCategory == null) {
            return null;
        }
        for (Iterator i$ = tCategory.research.values().iterator(); i$.hasNext(); ) {
            ResearchItem tResearch = (ResearchItem) i$.next();
            if ((tResearch.displayColumn == aX) && (tResearch.displayRow == aY)) {
                aX += (aX > 0 ? 5 : -5);
                aY += (aY > 0 ? 5 : -5);
            }
        }
        ResearchItem rResearch = new ResearchItem(aResearch, aCategory, getAspectList(aAspects), aX, aY, aComplexity, aIcon);
        ArrayList<ResearchPage> tPages = new ArrayList(aPages.length);
        GT_LanguageManager.addStringLocalization("tc.research_name." + aResearch, aName);
        GT_LanguageManager.addStringLocalization("tc.research_text." + aResearch, "[GT] " + aText);
        for (Object tPage : aPages) {
            if ((tPage instanceof String)) {
                tPages.add(new ResearchPage((String) tPage));
            } else if ((tPage instanceof IRecipe)) {
                tPages.add(new ResearchPage((IRecipe) tPage));
            } else if ((tPage instanceof IArcaneRecipe)) {
                tPages.add(new ResearchPage((IArcaneRecipe) tPage));
            } else if ((tPage instanceof CrucibleRecipe)) {
                tPages.add(new ResearchPage((CrucibleRecipe) tPage));
            } else if ((tPage instanceof InfusionRecipe)) {
                tPages.add(new ResearchPage((InfusionRecipe) tPage));
            } else if ((tPage instanceof InfusionEnchantmentRecipe)) {
                tPages.add(new ResearchPage((InfusionEnchantmentRecipe) tPage));
            }
        }
        if ((aType & 0x40) != 0) {
            rResearch.setAutoUnlock();
        }
        if ((aType & 0x1) != 0) {
            rResearch.setSecondary();
        }
        if ((aType & 0x20) != 0) {
            rResearch.setSpecial();
        }
        if ((aType & 0x8) != 0) {
            rResearch.setVirtual();
        }
        if ((aType & 0x4) != 0) {
            rResearch.setHidden();
        }
        if ((aType & 0x10) != 0) {
            rResearch.setRound();
        }
        if ((aType & 0x2) != 0) {
            rResearch.setStub();
        }
        if (aParentResearches != null) {
            ArrayList<String> tParentResearches = new ArrayList();
            for (String tParent : aParentResearches) {
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.researches, aResearch, true)) {
                    tParentResearches.add(tParent);
                }
            }
            if (tParentResearches.size() > 0) {
                rResearch.setParents((String[]) tParentResearches.toArray(new String[tParentResearches.size()]));
                rResearch.setConcealed();
            }
        }
        if (aResearchTriggers != null) {
            rResearch.setItemTriggers(aResearchTriggers);
            rResearch.setHidden();
        }
        rResearch.setPages((ResearchPage[]) tPages.toArray(new ResearchPage[tPages.size()]));
        return rResearch.registerResearchItem();
    }

    public Object addCrucibleRecipe(String aResearch, Object aInput, ItemStack aOutput, List<TC_Aspects.TC_AspectStack> aAspects) {
        if ((GT_Utility.isStringInvalid(aResearch)) || (aInput == null) || (aOutput == null) || (aAspects == null) || (aAspects.isEmpty())) {
            return null;
        }
        return ThaumcraftApi.addCrucibleRecipe(aResearch, GT_Utility.copy(new Object[]{aOutput}), ((aInput instanceof ItemStack)) || ((aInput instanceof ArrayList)) ? aInput : aInput.toString(), getAspectList(aAspects));
    }

    public Object addInfusionRecipe(String aResearch, ItemStack aMainInput, ItemStack[] aSideInputs, ItemStack aOutput, int aInstability, List<TC_Aspects.TC_AspectStack> aAspects) {
        if ((GT_Utility.isStringInvalid(aResearch)) || (aMainInput == null) || (aSideInputs == null) || (aOutput == null) || (aAspects == null) || (aAspects.isEmpty())) {
            return null;
        }
        return ThaumcraftApi.addInfusionCraftingRecipe(aResearch, GT_Utility.copy(new Object[]{aOutput}), aInstability, getAspectList(aAspects), aMainInput, aSideInputs);
    }
    
	public boolean registerThaumcraftAspectsToItem(ItemStack aExampleStack, List<TC_Aspects.TC_AspectStack> aAspects, String aOreDict) {
		if (aAspects.isEmpty()) return false;
		ThaumcraftApi.registerObjectTag(aOreDict, (AspectList)getAspectList(aAspects));
		return true;
	}

	public boolean registerThaumcraftAspectsToItem(ItemStack aStack, List<TC_Aspects.TC_AspectStack> aAspects, boolean aAdditive) {
		if (aAspects.isEmpty()) return false;
		if (aAdditive) {
			ThaumcraftApi.registerComplexObjectTag(aStack, (AspectList)getAspectList(aAspects));
			return true;
		}
		AspectList tAlreadyRegisteredAspects = ThaumcraftApiHelper.getObjectAspects(aStack);
		if (tAlreadyRegisteredAspects == null || tAlreadyRegisteredAspects.size() <= 0) {
			ThaumcraftApi.registerObjectTag(aStack, (AspectList)getAspectList(aAspects));
		}
		return true;
	}

    public boolean registerPortholeBlacklistedBlock(Block aBlock) {
        ThaumcraftApi.portableHoleBlackList.add(aBlock);
        return true;
    }
}
