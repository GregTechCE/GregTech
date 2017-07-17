package gregtech.api.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import gregtech.api.interfaces.ICondition;
import gregtech.api.interfaces.ISubTagContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public final class SubTag implements ICondition<ISubTagContainer> {

    private static final HashMap<String, SubTag> subTags = new HashMap<>();
    public final String name;

    private final Collection<ISubTagContainer> relevantTaggedItems = new HashSet<>(1);

    public static ImmutableMap<String, SubTag> getSubTags() {
        return ImmutableMap.copyOf(subTags);
    }

    public Collection<ISubTagContainer> getRelevantTaggedItems() {
        return ImmutableList.copyOf(relevantTaggedItems);
    }

    private SubTag(String name) {
        this.name = name;
        subTags.put(name, this);
    }

    public static SubTag getNewSubTag(String name) {
        for (SubTag subTag : subTags.values())
            if (subTag.name.equals(name)) return subTag;
        return new SubTag(name);
    }

    @Override
    public String toString() {
        return name;
    }

    public SubTag addContainerToList(ISubTagContainer... containers) {
        if (containers != null) for (ISubTagContainer container : containers)
            if (container != null && !relevantTaggedItems.contains(container)) relevantTaggedItems.add(container);
        return this;
    }

    @Override
    public boolean isTrue(ISubTagContainer object) {
        return object.contains(this);
    }

}