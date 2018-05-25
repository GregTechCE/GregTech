package gregtech.api.util;

@FunctionalInterface
public interface Condition<T> {

    boolean isTrue(T object);

    class Not<T> implements Condition<T> {

        private final Condition<T> condition;

        public Not(Condition<T> condition) {
            this.condition = condition;
        }

        @Override
        public boolean isTrue(T object) {
            return !condition.isTrue(object);
        }

    }

    class Or<T> implements Condition<T> {

        private final Condition<T>[] conditions;

        @SafeVarargs
        public Or(Condition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (Condition<T> condition : conditions)
                if (condition.isTrue(object)) return true;
            return false;
        }

    }

    class Nor<T> implements Condition<T> {

        private final Condition<T>[] conditions;

        @SafeVarargs
        public Nor(Condition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (Condition<T> condition : conditions)
                if (condition.isTrue(object)) return false;
            return true;
        }

    }

    class And<T> implements Condition<T> {

        private final Condition<T>[] conditions;

        @SafeVarargs
        public And(Condition<T>... aConditions) {
            this.conditions = aConditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (Condition<T> condition : conditions)
                if (!condition.isTrue(object)) return false;
            return true;
        }

    }

    class Nand<T> implements Condition<T> {

        private final Condition<T>[] conditions;

        @SafeVarargs
        public Nand(Condition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (Condition<T> condition : conditions)
                if (!condition.isTrue(object)) return true;
            return false;
        }

    }

    class Xor<T> implements Condition<T> {

        private final Condition<T> firstCondition, secondCondition;

        public Xor(Condition<T> firstCondition, Condition<T> secondCondition) {
            this.firstCondition = firstCondition;
            this.secondCondition = secondCondition;
        }

        @Override
        public boolean isTrue(T object) {
            return firstCondition.isTrue(object) != secondCondition.isTrue(object);
        }
    }

    class Equal<T> implements Condition<T> {

        private final Condition<T> firstCondition, secondCondition;

        public Equal(Condition<T> firstCondition, Condition<T> secondCondition) {
            this.firstCondition = firstCondition;
            this.secondCondition = secondCondition;
        }

        @Override
        public boolean isTrue(T aObject) {
            return firstCondition.isTrue(aObject) == secondCondition.isTrue(aObject);
        }

    }

}