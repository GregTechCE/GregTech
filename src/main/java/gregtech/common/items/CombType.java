package gregtech.common.items;

import gregtech.api.enums.Materials;
import gregtech.api.util.GT_LanguageManager;

public enum CombType {
    //Organic
    LIGNIE("lignite", true, Materials.Lignite, 100),
    COAL("coal", true, Materials.Coal, 100),
    STICKY("stickyresin", true, Materials._NULL, 50),
    OIL("oil", true, Materials._NULL, 100),
    //Gem Line
    STONE("stone", true, Materials._NULL, 70),
    CERTUS("certus", true, Materials.CertusQuartz, 100),
    REDSTONE("redstone", true, Materials.Redstone, 100),
    LAPIS("lapis", true, Materials.Lapis, 100),
    RUBY("ruby", true, Materials.Ruby, 100),
    SAPPHIRE("sapphire", true, Materials.Sapphire, 100),
    DIAMOND("diamond", true, Materials.Diamond, 100),
    OLIVINE("olivine", true, Materials.Olivine, 100),
    EMERALD("emerald", true, Materials.Emerald, 100),

    // Metals Line
    SLAG("slag", true, Materials._NULL, 50),
    COPPER("coppon", true, Materials.Copper, 100),
    TIN("tine", true, Materials.Tin, 100),
    LEAD("plumbilia", true, Materials.Lead, 100),
    IRON("ferru", true, Materials.Iron, 100),
    STEEL("steeldust", true, Materials.Steel, 100),
    NICKEL("nickeldust", true, Materials.Nickel, 100),
    ZINC("galvania", true, Materials.Zinc, 100),
    SILVER("argentia", true, Materials.Silver, 100),
    GOLD("aurelia", true, Materials.Gold, 100),

    // Rare Metals Line    
    ALUMINIUM("bauxia", true, Materials.Aluminium, 60),
    MANGANESE("pyrolusium", true, Materials.Manganese, 30),
    TITANIUM("titanium", true, Materials.Ilmenite, 100),
    CHROME("chromium", true, Materials.Chrome, 50),
    TUNGSTEN("scheelinium", true, Materials.Tungstate, 100),
    PLATINUM("platina", true, Materials.Platinum, 40),
    IRIDIUM("quantaria", true, Materials.Iridium, 20),


    // Radioactive Line
    URANIUM("urania", true, Materials.Uranium, 50),
    PLUTONIUM("plutonium", true, Materials.Plutonium, 10),
    NAQUADAH("stargatium", true, Materials.Naquadah, 10),;

    private static int[][] colours = new int[][]{
            {0x906237, 0x58300B},
            {0x666666, 0x525252},
            {0x2E8F5B, 0xDCC289},
            {0x4C4C4C, 0x333333},
            {0x808080, 0x999999},
            {0x57CFFB, 0xBBEEFF},
            {0x7D0F0F, 0xD11919},
            {0x1947D1, 0x476CDA},
            {0xE6005C, 0xCC0052},
            {0x0033CC, 0x00248F},
            {0xCCFFFF, 0xA3CCCC},
            {0x248F24, 0xCCFFCC},
            {0x248F24, 0x2EB82E},
            {0xD4D4D4, 0x58300B},
            {0xFF6600, 0xE65C00},
            {0xD4D4D4, 0xDDDDDD},
            {0x666699, 0xA3A3CC},
            {0xDA9147, 0xDE9C59},
            {0x808080, 0x999999},
            {0x8585AD, 0x9D9DBD},
            {0xF0DEF0, 0xF2E1F2},
            {0xC2C2D6, 0xCECEDE},
            {0xE6B800, 0xCFA600},
            {0x008AB8, 0xD6D6FF},
            {0xD5D5D5, 0xAAAAAA},
            {0xCC99FF, 0xDBB8FF},
            {0xEBA1EB, 0xF2C3F2},
            {0x62626D, 0x161620},
            {0xE6E6E6, 0xFFFFCC},
            {0xDADADA, 0xD1D1E0},
            {0x19AF19, 0x169E16},
            {0x335C33, 0x6B8F00},
            {0x003300, 0x002400},
    };
    public boolean showInList;
    public Materials material;
    public int chance;
    private String name;
    private CombType(String pName, boolean show, Materials material, int chance) {
        this.name = pName;
        this.material = material;
        this.chance = chance;
        this.showInList = show;
    }

    public void setHidden() {
        this.showInList = false;
    }

    public String getName() {
//		return "gt.comb."+this.name;
        return GT_LanguageManager.addStringLocalization("comb." + this.name, this.name.substring(0, 1).toUpperCase() + this.name.substring(1) + " Comb");
    }

    public int[] getColours() {
        return colours[this.ordinal()];
    }
}