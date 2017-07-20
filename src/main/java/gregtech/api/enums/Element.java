package gregtech.api.enums;

import gregtech.api.enums.material.Materials;
import gregtech.api.util.GT_Utility;

import java.util.ArrayList;

/**
 * This is some kind of Periodic Table, which I use to determine Properties of the Materials.
 */
public enum Element {

    H(1, 0, -1, null, "Hydrogen", false),
    D(1, 1, -1, "H", "Deuterium", true),
    T(1, 2, -1, "D", "Tritium", true),
    He(2, 2, -1, null, "Helium", false),
    He_3(2, 1, -1, "H&D", "Helium-3", true),
    Li(3, 4, -1, null, "Lithium", false),
    Be(4, 5, -1, null, "Beryllium", false),
    B(5, 5, -1, null, "Boron", false),
    C(6, 6, -1, null, "Carbon", false),
    N(7, 7, -1, null, "Nitrogen", false),
    O(8, 8, -1, null, "Oxygen", false),
    F(9, 9, -1, null, "Fluorine", false),
    Ne(10, 10, -1, null, "Neon", false),
    Na(11, 11, -1, null, "Sodium", false),
    Mg(12, 12, -1, null, "Magnesium", false),
    Al(13, 13, -1, null, "Aluminium", false),
    Si(14, 14, -1, null, "Silicon", false),
    P(15, 15, -1, null, "Phosphorus", false),
    S(16, 16, -1, null, "Sulfur", false),
    Cl(17, 18, -1, null, "Chlorine", false),
    Ar(18, 22, -1, null, "Argon", false),
    K(19, 20, -1, null, "Potassium", false),
    Ca(20, 20, -1, null, "Calcium", false),
    Sc(21, 24, -1, null, "Scandium", false),
    Ti(22, 26, -1, null, "Titanium", false),
    V(23, 28, -1, null, "Vanadium", false),
    Cr(24, 28, -1, null, "Chrome", false),
    Mn(25, 30, -1, null, "Manganese", false),
    Fe(26, 30, -1, null, "Iron", false),
    Co(27, 32, -1, null, "Cobalt", false),
    Ni(28, 30, -1, null, "Nickel", false),
    Cu(29, 34, -1, null, "Copper", false),
    Zn(30, 35, -1, null, "Zinc", false),
    Ga(31, 39, -1, null, "Gallium", false),
    Ge(32, 40, -1, null, "Germanium", false),
    As(33, 42, -1, null, "Arsenic", false),
    Se(34, 45, -1, null, "Selenium", false),
    Br(35, 45, -1, null, "Bromine", false),
    Kr(36, 48, -1, null, "Krypton", false),
    Rb(37, 48, -1, null, "Rubidium", false),
    Sr(38, 49, -1, null, "Strontium", false),
    Y(39, 50, -1, null, "Yttrium", false),
    Zr(40, 51, -1, null, "Zirconium", false),
    Nb(41, 53, -1, null, "Niobium", false),
    Mo(42, 53, -1, null, "Molybdenum", false),
    Tc(43, 55, -1, null, "Technetium", false),
    Ru(44, 57, -1, null, "Ruthenium", false),
    Rh(45, 58, -1, null, "Rhodium", false),
    Pd(46, 60, -1, null, "Palladium", false),
    Ag(47, 60, -1, null, "Silver", false),
    Cd(48, 64, -1, null, "Cadmium", false),
    In(49, 65, -1, null, "Indium", false),
    Sn(50, 68, -1, null, "Tin", false),
    Sb(51, 70, -1, null, "Antimony", false),
    Te(52, 75, -1, null, "Tellurium", false),
    I(53, 74, -1, null, "Iodine", false),
    Xe(54, 77, -1, null, "Xenon", false),
    Cs(55, 77, -1, null, "Caesium", false),
    Ba(56, 81, -1, null, "Barium", false),
    La(57, 81, -1, null, "Lantanium", false),
    Ce(58, 82, -1, null, "Cerium", false),
    Pr(59, 81, -1, null, "Praseodymium", false),
    Nd(60, 84, -1, null, "Neodymium", false),
    Pm(61, 83, -1, null, "Promethium", false),
    Sm(62, 88, -1, null, "Samarium", false),
    Eu(63, 88, -1, null, "Europium", false),
    Gd(64, 93, -1, null, "Gadolinium", false),
    Tb(65, 93, -1, null, "Terbium", false),
    Dy(66, 96, -1, null, "Dysprosium", false),
    Ho(67, 97, -1, null, "Holmium", false),
    Er(68, 99, -1, null, "Erbium", false),
    Tm(69, 99, -1, null, "Thulium", false),
    Yb(70, 103, -1, null, "Ytterbium", false),
    Lu(71, 103, -1, null, "Lutetium", false),
    Hf(72, 106, -1, null, "Hafnium", false),
    Ta(73, 107, -1, null, "Tantalum", false),
    W(74, 109, -1, null, "Wolframium", false),
    Re(75, 111, -1, null, "Rhenium", false),
    Os(76, 114, -1, null, "Osmium", false),
    Ir(77, 115, -1, null, "Iridium", false),
    Pt(78, 117, -1, null, "Platinum", false),
    Au(79, 117, -1, null, "Gold", false),
    Hg(80, 120, -1, null, "Mercury", false),
    Tl(81, 123, -1, null, "Thallium", false),
    Pb(82, 125, -1, null, "Lead", false),
    Bi(83, 125, -1, null, "Bismuth", false),
    Po(84, 124, -1, null, "Polonium", false),
    At(85, 124, -1, null, "Astatine", false),
    Rn(86, 134, -1, null, "Radon", false),
    Fr(87, 134, -1, null, "Francium", false),
    Ra(88, 136, -1, null, "Radium", false),
    Ac(89, 136, -1, null, "Actinium", false),
    Th(90, 140, -1, null, "Thorium", false),
    Pa(91, 138, -1, null, "Protactinium", false),
    U(92, 146, -1, null, "Uranium", false),
    U_235(92, 143, -1, null, "Uranium-235", true),
    Np(93, 144, -1, null, "Neptunium", false),
    Pu(94, 152, -1, null, "Plutonium", false),
    Pu_241(94, 149, -1, null, "Plutonium-241", true),
    Am(95, 150, -1, null, "Americium", false),
    Cm(96, 153, -1, null, "Curium", false),
    Bk(97, 152, -1, null, "Berkelium", false),
    Cf(98, 153, -1, null, "Californium", false),
    Es(99, 153, -1, null, "Einsteinium", false),
    Fm(100, 157, -1, null, "Fermium", false),
    Md(101, 157, -1, null, "Mendelevium", false),
    No(102, 157, -1, null, "Nobelium", false),
    Lr(103, 159, -1, null, "Lawrencium", false),
    Rf(104, 161, -1, null, "Rutherfordium", false),
    Db(105, 163, -1, null, "Dubnium", false),
    Sg(106, 165, -1, null, "Seaborgium", false),
    Bh(107, 163, -1, null, "Bohrium", false),
    Hs(108, 169, -1, null, "Hassium", false),
    Mt(109, 167, -1, null, "Meitnerium", false),
    Ds(110, 171, -1, null, "Darmstadtium", false),
    Rg(111, 169, -1, null, "Roentgenium", false),
    Cn(112, 173, -1, null, "Copernicium", false),
    Uut(113, 171, -1, null, "Ununtrium", false),
    Fl(114, 175, -1, null, "Flerovium", false),
    Uup(115, 173, -1, null, "Ununpentium", false),
    Lv(116, 177, -1, null, "Livermorium", false),
    Fa(117, 177, -1, null, "Farnsium", false),
    Uuo(118, 176, -1, null, "Ununoctium", false);

    /*$H(-1, -0, -1, null, "Anti-Hydrogen", false),
    $D(-1, -1, -1, "H", "Anti-Deuterium", true),
    $T(-1, -2, -1, "D", "Anti-Tritium", true),
    $He(-2, -2, -1, null, "Anti-Helium", false),
    $He_3(-2, -1, -1, "H&D", "Anti-Helium-3", true),
    $Li(-3, -4, -1, null, "Anti-Lithium", false),
    $Be(-4, -5, -1, null, "Anti-Beryllium", false),
    $B(-5, -5, -1, null, "Anti-Boron", false),
    $C(-6, -6, -1, null, "Anti-Carbon", false),
    $N(-7, -7, -1, null, "Anti-Nitrogen", false),
    $O(-8, -8, -1, null, "Anti-Oxygen", false),
    $F(-9, -9, -1, null, "Anti-Fluorine", false),
    $Ne(-10, -10, -1, null, "Anti-Neon", false),
    $Na(-11, -11, -1, null, "Anti-Sodium", false),
    $Mg(-12, -12, -1, null, "Anti-Magnesium", false),
    $Al(-13, -13, -1, null, "Anti-Aluminium", false),
    $Si(-14, -14, -1, null, "Anti-Silicon", false),
    $P(-15, -15, -1, null, "Anti-Phosphorus", false),
    $S(-16, -16, -1, null, "Anti-Sulfur", false),
    $Cl(-17, -18, -1, null, "Anti-Chlorine", false),
    $Ar(-18, -22, -1, null, "Anti-Argon", false),
    $K(-19, -20, -1, null, "Anti-Potassium", false),
    $Ca(-20, -20, -1, null, "Anti-Calcium", false),
    $Sc(-21, -24, -1, null, "Anti-Scandium", false),
    $Ti(-22, -26, -1, null, "Anti-Titanium", false),
    $V(-23, -28, -1, null, "Anti-Vanadium", false),
    $Cr(-24, -28, -1, null, "Anti-Chrome", false),
    $Mn(-25, -30, -1, null, "Anti-Manganese", false),
    $Fe(-26, -30, -1, null, "Anti-Iron", false),
    $Co(-27, -32, -1, null, "Anti-Cobalt", false),
    $Ni(-28, -30, -1, null, "Anti-Nickel", false),
    $Cu(-29, -34, -1, null, "Anti-Copper", false),
    $Zn(-30, -35, -1, null, "Anti-Zinc", false),
    $Ga(-31, -39, -1, null, "Anti-Gallium", false),
    $Ge(-32, -40, -1, null, "Anti-Germanium", false),
    $As(-33, -42, -1, null, "Anti-Arsenic", false),
    $Se(-34, -45, -1, null, "Anti-Selenium", false),
    $Br(-35, -45, -1, null, "Anti-Bromine", false),
    $Kr(-36, -48, -1, null, "Anti-Krypton", false),
    $Rb(-37, -48, -1, null, "Anti-Rubidium", false),
    $Sr(-38, -49, -1, null, "Anti-Strontium", false),
    $Y(-39, -50, -1, null, "Anti-Yttrium", false),
    $Zr(-40, -51, -1, null, "Anti-Zirconium", false),
    $Nb(-41, -53, -1, null, "Anti-Niobium", false),
    $Mo(-42, -53, -1, null, "Anti-Molybdenum", false),
    $Tc(-43, -55, -1, null, "Anti-Technetium", false),
    $Ru(-44, -57, -1, null, "Anti-Ruthenium", false),
    $Rh(-45, -58, -1, null, "Anti-Rhodium", false),
    $Pd(-46, -60, -1, null, "Anti-Palladium", false),
    $Ag(-47, -60, -1, null, "Anti-Silver", false),
    $Cd(-48, -64, -1, null, "Anti-Cadmium", false),
    $In(-49, -65, -1, null, "Anti-Indium", false),
    $Sn(-50, -68, -1, null, "Anti-Tin", false),
    $Sb(-51, -70, -1, null, "Anti-Antimony", false),
    $Te(-52, -75, -1, null, "Anti-Tellurium", false),
    $I(-53, -74, -1, null, "Anti-Iodine", false),
    $Xe(-54, -77, -1, null, "Anti-Xenon", false),
    $Cs(-55, -77, -1, null, "Anti-Caesium", false),
    $Ba(-56, -81, -1, null, "Anti-Barium", false),
    $La(-57, -81, -1, null, "Anti-Lantanium", false),
    $Ce(-58, -82, -1, null, "Anti-Cerium", false),
    $Pr(-59, -81, -1, null, "Anti-Praseodymium", false),
    $Nd(-60, -84, -1, null, "Anti-Neidymium", false),
    $Pm(-61, -83, -1, null, "Anti-Promethium", false),
    $Sm(-62, -88, -1, null, "Anti-Samarium", false),
    $Eu(-63, -88, -1, null, "Anti-Europium", false),
    $Gd(-64, -93, -1, null, "Anti-Gadolinium", false),
    $Tb(-65, -93, -1, null, "Anti-Terbium", false),
    $Dy(-66, -96, -1, null, "Anti-Dysprosium", false),
    $Ho(-67, -97, -1, null, "Anti-Holmium", false),
    $Er(-68, -99, -1, null, "Anti-Erbium", false),
    $Tm(-69, -99, -1, null, "Anti-Thulium", false),
    $Yb(-70, -103, -1, null, "Anti-Ytterbium", false),
    $Lu(-71, -103, -1, null, "Anti-Lutetium", false),
    $Hf(-72, -106, -1, null, "Anti-Hafnium", false),
    $Ta(-73, -107, -1, null, "Anti-Tantalum", false),
    $W(-74, -109, -1, null, "Anti-Wolframium", false),
    $Re(-75, -111, -1, null, "Anti-Rhenium", false),
    $Os(-76, -114, -1, null, "Anti-Osmium", false),
    $Ir(-77, -115, -1, null, "Anti-Iridium", false),
    $Pt(-78, -117, -1, null, "Anti-Platinum", false),
    $Au(-79, -117, -1, null, "Anti-Gold", false),
    $Hg(-80, -120, -1, null, "Anti-Mercury", false),
    $Tl(-81, -123, -1, null, "Anti-Thallium", false),
    $Pb(-82, -125, -1, null, "Anti-Lead", false),
    $Bi(-83, -125, -1, null, "Anti-Bismuth", false),
    $Po(-84, -124, -1, null, "Anti-Polonium", false),
    $At(-85, -124, -1, null, "Anti-Astatine", false),
    $Rn(-86, -134, -1, null, "Anti-Radon", false),
    $Fr(-87, -134, -1, null, "Anti-Francium", false),
    $Ra(-88, -136, -1, null, "Anti-Radium", false),
    $Ac(-89, -136, -1, null, "Anti-Actinium", false),
    $Th(-90, -140, -1, null, "Anti-Thorium", false),
    $Pa(-91, -138, -1, null, "Anti-Protactinium", false),
    $U(-92, -146, -1, null, "Anti-Uranium", false),
    $U_235(-92, -143, -1, null, "Anti-Uranium-235", true),
    $Np(-93, -144, -1, null, "Anti-Neptunium", false),
    $Pu(-94, -152, -1, null, "Anti-Plutonium", false),
    $Pu_241(-94, -149, -1, null, "Anti-Plutonium-241", true),
    $Am(-95, -150, -1, null, "Anti-Americum", false),
    $Cm(-96, -153, -1, null, "Anti-Curium", false),
    $Bk(-97, -152, -1, null, "Anti-Berkelium", false),
    $Cf(-98, -153, -1, null, "Anti-Californium", false),
    $Es(-99, -153, -1, null, "Anti-Einsteinium", false),
    $Fm(-100, -157, -1, null, "Anti-Fermium", false),
    $Md(-101, -157, -1, null, "Anti-Mendelevium", false),
    $No(-102, -157, -1, null, "Anti-Nobelium", false),
    $Lr(-103, -159, -1, null, "Anti-Lawrencium", false),
    $Rf(-104, -161, -1, null, "Anti-Rutherfordium", false),
    $Db(-105, -163, -1, null, "Anti-Dubnium", false),
    $Sg(-106, -165, -1, null, "Anti-Seaborgium", false),
    $Bh(-107, -163, -1, null, "Anti-Bohrium", false),
    $Hs(-108, -169, -1, null, "Anti-Hassium", false),
    $Mt(-109, -167, -1, null, "Anti-Meitnerium", false),
    $Ds(-110, -171, -1, null, "Anti-Darmstadtium", false),
    $Rg(-111, -169, -1, null, "Anti-Roentgenium", false),
    $Cn(-112, -173, -1, null, "Anti-Copernicium", false),
    $Uut(-113, -171, -1, null, "Anti-Ununtrium", false),
    $Fl(-114, -175, -1, null, "Anti-Flerovium", false),
    $Uup(-115, -173, -1, null, "Anti-Ununpentium", false),
    $Lv(-116, -177, -1, null, "Anti-Livermorium", false),
    $Fa(-117, -177, -1, null, "Anti-Ununseptium", false),
    $Uuo(-118, -176, -1, null, "Anti-Ununoctium", false);*/

    public final long protons, neutrons, halfLifeSeconds;
    public final String name, decayTo;
    public final boolean isIsotope;

    /**
     * Links to every pure Material containing just this Element.
     */
    public ArrayList<Materials> mLinkedMaterials = new ArrayList<Materials>();

    /**
     * @param protons         Amount of Protons
     * @param neutrons        Amount of Neutrons (I could have made mistakes with the Neutron amount calculation, please tell me if I did something wrong)
     * @param halfLifeSeconds Amount of Half Life this Material has in Seconds. -1 for stable Materials
     * @param decayTo         String representing the Elements it decays to. Separated by an '&' Character
     * @param name            Name of the Element
     */
    private Element(long protons, long neutrons, long halfLifeSeconds, String decayTo, String name, boolean isIsotope) {
        this.protons = protons;
        this.neutrons = neutrons;
        this.halfLifeSeconds = halfLifeSeconds;
        this.decayTo = decayTo;
        this.name = name;
        this.isIsotope = isIsotope;
    }

    public static Element get(String materialName) {
        Object tObject = GT_Utility.getFieldContent(Element.class, materialName, false, false);
        if (tObject != null && tObject instanceof Element) return (Element) tObject;
        return H;
    }

    public long getProtons() {
        return protons;
    }

    public long getNeutrons() {
        return neutrons;
    }

    public long getMass() {
        return protons + neutrons;
    }

}