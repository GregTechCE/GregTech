package gregtech.api.enums;

import java.util.List;

public enum TC_Aspects {
	  AER
	, ALIENIS
	, AQUA
	, ARBOR
	, AURAM
	, BESTIA
	, COGNITO
	, CORPUS
	, ELECTRUM
	, EXAMINIS
	, FABRICO
	, FAMES
	, GELUM
	, GRANUM
	, HERBA
	, HUMANUS
	, IGNIS
	, INSTRUMENTUM
	, ITER
	, LIMUS
	, LUCRUM
	, LUX
	, MACHINA
	, MAGNETO
	, MESSIS
	, METALLUM
	, METO
	, MORTUUS
	, MOTUS
	, NEBRISUM
	, ORDO
	, PANNUS
	, PERDITIO
	, PERFODIO
	, PERMUTATIO
	, POTENTIA
	, PRAECANTIO
	, RADIO
	, SANO
	, SENSUS
	, SPIRITUS
	, STRONTIO
	, TELUM
	, TERRA
	, TEMPESTAS
	, TENEBRAE
	, TUTAMEN
	, VACUOS
	, VENENUM
	, VICTUS
	, VINCULUM
	, VITIUM
	, VITREUS
	, VOLATUS
	;
	
	/**
	 * The Thaumcraft Aspect Object of the Mod itself.
	 */
	public Object mAspect;
	
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
			for (TC_AspectStack tAspect : aList) if (tAspect.mAspect == mAspect) {tAspect.mAmount += mAmount; return aList;}
			aList.add(copy());
			return aList;
		}
		
		public boolean removeFromAspectList(List<TC_AspectStack> aList) {
			for (TC_AspectStack tAspect : aList) if (tAspect.mAspect == mAspect) {
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