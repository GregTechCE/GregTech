package gregtech.common.blocks.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.PropertyHelper;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PropertyString extends PropertyHelper<String> {

    private final List<String> valuesSet;

    public PropertyString(String name, Collection<String> values) {
        super(name, String.class);
        valuesSet = new LinkedList<>(values);
    }

    public PropertyString(String name, String... values) {
        super(name, String.class);
        valuesSet = new LinkedList<>();
        Collections.addAll(valuesSet, values);
    }

    public List<String> values() {
        return ImmutableList.copyOf(valuesSet);
    }

    @Nonnull
    @Override
    public Collection<String> getAllowedValues() {
        return ImmutableList.copyOf(valuesSet);
    }

    @Nonnull
    @Override
    public Optional<String> parseValue(@Nonnull String value) {
        if (valuesSet.contains(value)) {
            return Optional.of(value);
        }
        return Optional.absent();
    }

    @Nonnull
    @Override
    public String getName(@Nonnull String value) {
        return value;
    }

    public static PropertyString create(String name, String... values) {
        return new PropertyString(name, values);
    }

    public static PropertyString create(String name, Collection<String> values) {
        return new PropertyString(name, values);
    }
}

