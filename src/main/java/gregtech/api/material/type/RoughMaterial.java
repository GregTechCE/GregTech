package gregtech.api.material.type;

import com.google.common.collect.ImmutableList;
import gregtech.api.material.Element;
import gregtech.api.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;

public class RoughMaterial extends DustMaterial{
	public RoughMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags, Element element) {
		super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags, element);
	}

	public RoughMaterial(int metaItemSubId, String name, String defaultLocalName, int materialRGB, MaterialIconSet materialIconSet, ImmutableList<MaterialStack> materialComponents, long materialGenerationFlags) {
		super(metaItemSubId, name, defaultLocalName, materialRGB, materialIconSet, materialComponents, materialGenerationFlags);
	}
}
