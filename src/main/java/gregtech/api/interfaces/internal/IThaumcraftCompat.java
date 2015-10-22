package gregtech.api.interfaces.internal;

import gregtech.api.enums.TC_Aspects;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IThaumcraftCompat {
    public static final int RESEARCH_TYPE_NORMAL = 0, RESEARCH_TYPE_SECONDARY = 1, RESEARCH_TYPE_FREE = 2, RESEARCH_TYPE_HIDDEN = 4, RESEARCH_TYPE_VIRTUAL = 8, RESEARCH_TYPE_ROUND = 16, RESEARCH_TYPE_SPECIAL = 32, RESEARCH_TYPE_AUTOUNLOCK = 64;

    /**
     * The Research Keys of GT
     */
    public static final String
            IRON_TO_STEEL = "GT_IRON_TO_STEEL", FILL_WATER_BUCKET = "GT_FILL_WATER_BUCKET", WOOD_TO_CHARCOAL = "GT_WOOD_TO_CHARCOAL", TRANSZINC = "GT_TRANSZINC", TRANSNICKEL = "GT_TRANSNICKEL", TRANSCOBALT = "GT_TRANSCOBALT", TRANSBISMUTH = "GT_TRANSBISMUTH", TRANSANTIMONY = "GT_TRANSANTIMONY", TRANSCUPRONICKEL = "GT_TRANSCUPRONICKEL", TRANSBATTERYALLOY = "GT_TRANSBATTERYALLOY", TRANSSOLDERINGALLOY = "GT_TRANSSOLDERINGALLOY", TRANSBRASS = "GT_TRANSBRASS", TRANSBRONZE = "GT_TRANSBRONZE", TRANSINVAR = "GT_TRANSINVAR", TRANSELECTRUM = "GT_TRANSELECTRUM", TRANSALUMINIUM = "GT_TRANSALUMINIUM", CRYSTALLISATION = "GT_CRYSTALLISATION", ADVANCEDENTROPICPROCESSING = "GT_ADVANCEDENTROPICPROCESSING", ADVANCEDMETALLURGY = "GT_ADVANCEDMETALLURGY";

    public boolean registerPortholeBlacklistedBlock(Block aBlock);

    public boolean registerThaumcraftAspectsToItem(ItemStack aStack, List<TC_AspectStack> aAspects, boolean aAdditive);

    public boolean registerThaumcraftAspectsToItem(ItemStack aStack, List<TC_AspectStack> aAspects, String aOreDict);

    public Object addCrucibleRecipe(String aResearch, Object aInput, ItemStack aOutput, List<TC_AspectStack> aAspects);

    public Object addInfusionRecipe(String aResearch, ItemStack aMainInput, ItemStack[] aSideInputs, ItemStack aOutput, int aInstability, List<TC_Aspects.TC_AspectStack> aAspects);

    public Object addResearch(String aResearch, String aName, String aText, String[] aParentResearches, String aCategory, ItemStack aIcon, int aComplexity, int aType, int aX, int aY, List<TC_AspectStack> aAspects, ItemStack[] aResearchTriggers, Object[] aPages);
}