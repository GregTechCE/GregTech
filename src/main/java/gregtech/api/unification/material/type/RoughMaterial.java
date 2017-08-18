package gregtech.api.unification.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.stack.MaterialStack;

public class RoughMaterial extends DustMaterial{
	public RoughMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
		super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
	}

	public RoughMaterial(int metaItemSubId, String name, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
		super(metaItemSubId, name, materialRGB, materialIconSet, materialComponents, materialGenerationFlags);
	}
}
