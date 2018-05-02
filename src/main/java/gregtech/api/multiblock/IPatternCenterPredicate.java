package gregtech.api.multiblock;

import java.util.function.Predicate;

public interface IPatternCenterPredicate extends Predicate<BlockWorldState> {

    static IPatternCenterPredicate wrap(Predicate<BlockWorldState> predicate) {
        return predicate::test;
    }

}
