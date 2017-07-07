package gregtech.common.blocks.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import gregtech.api.enums.material.Materials;
import net.minecraft.block.properties.PropertyHelper;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author exidex.
 * @since 03.05.2017.
 */
public class PropertyMaterial extends PropertyHelper<Materials> {

	private final ImmutableSet<Materials> allowedValues;

	protected PropertyMaterial(String name, Collection<Materials> allowedValues) {
		super(name, Materials.class);
		this.allowedValues = ImmutableSet.copyOf(allowedValues);
	}

	public static PropertyMaterial create(String name, Collection<Materials> allowedValues) {
		return new PropertyMaterial(name, allowedValues);
	}

	public static PropertyMaterial create(String name, Materials... allowedValues) {
		return new PropertyMaterial(name, Lists.newArrayList(allowedValues));
	}

	@Override
	public Collection<Materials> getAllowedValues() {
		return ImmutableSet.copyOf(this.allowedValues);
	}

	@Override
	public Optional<Materials> parseValue(String value) {
		Materials material = Materials.get(value);
		if (this.allowedValues.contains(material)) {
			return Optional.of(material);
		}
		return Optional.absent();
	}

	@Nonnull
	public Materials getFirstType() {
		return getAllowedValues().iterator().next();
	}

	@Override
	public String getName(Materials material) {
		return material.mName.toLowerCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PropertyMaterial && super.equals(obj)) {
			PropertyMaterial propertyMaterial = (PropertyMaterial) obj;
			return this.allowedValues.equals(propertyMaterial.allowedValues);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int i = super.hashCode();
		i = 31 * i + this.allowedValues.hashCode();
		return i;
	}
}
