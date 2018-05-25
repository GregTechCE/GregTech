package gregtech.api.metatileentity.multiblock;

import gregtech.api.unification.stack.SimpleItemStack;

import java.util.function.Predicate;

public enum MultiblockIssue {

    ;

    public final String name;
    public final Predicate<SimpleItemStack> toolPredicate;

    MultiblockIssue(String name, Predicate<SimpleItemStack> toolPredicate) {
        this.name = name;
        this.toolPredicate = toolPredicate;
    }
}
