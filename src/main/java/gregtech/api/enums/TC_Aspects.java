package gregtech.api.enums;

import java.util.List;

public enum TC_Aspects {
    AER(1), ALIENIS(20), AQUA(3), ARBOR(1), AURAM(16), BESTIA(6), COGNITIO(2), CORPUS(2), ELECTRUM(24), EXANIMIS(32), FABRICO(2), FAMES(2), GELUM(1), GRANUM(4), HERBA(2), HUMANUS(8), IGNIS(4), INSTRUMENTUM(4), ITER(6), LIMUS(3), LUCRUM(32), LUX(4), MACHINA(16), MAGNETO(24), MESSIS(3), METALLUM(8), METO(2), MORTUUS(16), MOTUS(4), NEBRISUM(48), ORDO(8), PANNUS(6), PERDITIO(2), PERFODIO(4), PERMUTATIO(12), POTENTIA(16), PRAECANTATIO(16), RADIO(48), SANO(24), SENSUS(4), SPIRITUS(24), STRONTIO(64), TELUM(6), TERRA(1), TEMPESTAS(64), TENEBRAE(24), TUTAMEN(12), VACUOS(6), VENENUM(16), VICTUS(4), VINCULUM(16), VITIUM(48), VITREUS(3), VOLATUS(12);

    /**
     * The Thaumcraft Aspect Object of the Mod itself.
     */
    public Object mAspect;
    public int mValue;

    private TC_Aspects(int aValue) {
        mValue = aValue;
    }

    public static class TC_AspectStack {
        public TC_Aspects mAspect;
        public long mAmount;


        public TC_AspectStack(TC_Aspects aAspect, long aAmount) {
            mAspect = aAspect;
            mAmount = aAmount;
        }

        public TC_AspectStack copy() {
            return new TC_AspectStack(mAspect, mAmount);
        }

        public TC_AspectStack copy(long aAmount) {
            return new TC_AspectStack(mAspect, aAmount);
        }

        public List<TC_AspectStack> addToAspectList(List<TC_AspectStack> aList) {
            if (mAmount == 0) return aList;
            for (TC_AspectStack tAspect : aList)
                if (tAspect.mAspect == mAspect) {
                    tAspect.mAmount += mAmount;
                    return aList;
                }
            aList.add(copy());
            return aList;
        }

        public boolean removeFromAspectList(List<TC_AspectStack> aList) {
            for (TC_AspectStack tAspect : aList)
                if (tAspect.mAspect == mAspect) {
                    if (tAspect.mAmount >= mAmount) {
                        tAspect.mAmount -= mAmount;
                        if (tAspect.mAmount == 0) aList.remove(tAspect);
                        return true;
                    }
                }
            return false;
        }
    }
}