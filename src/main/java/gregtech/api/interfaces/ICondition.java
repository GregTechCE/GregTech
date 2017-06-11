package gregtech.api.interfaces;

public interface ICondition<T> {

    boolean isTrue(T object);

    // Utility Classes for adding relations between Conditions.

    class Not<T> implements ICondition<T> {

        private final ICondition<T> condition;

        public Not(ICondition<T> condition) {
            this.condition = condition;
        }

        @Override
        public boolean isTrue(T object) {
            return !condition.isTrue(object);
        }

    }

    class Or<T> implements ICondition<T> {

        private final ICondition<T>[] conditions;

        @SafeVarargs
        public Or(ICondition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (ICondition<T> condition : conditions)
                if (condition.isTrue(object)) return true;
            return false;
        }

    }

    class Nor<T> implements ICondition<T> {

        private final ICondition<T>[] conditions;

        @SafeVarargs
        public Nor(ICondition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (ICondition<T> condition : conditions)
                if (condition.isTrue(object)) return false;
            return true;
        }

    }

    class And<T> implements ICondition<T> {

        private final ICondition<T>[] conditions;

        @SafeVarargs
        public And(ICondition<T>... aConditions) {
            this.conditions = aConditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (ICondition<T> condition : conditions)
                if (!condition.isTrue(object)) return false;
            return true;
        }

    }

    class Nand<T> implements ICondition<T> {

        private final ICondition<T>[] conditions;

        @SafeVarargs
        public Nand(ICondition<T>... conditions) {
            this.conditions = conditions;
        }

        @Override
        public boolean isTrue(T object) {
            for (ICondition<T> condition : conditions)
                if (!condition.isTrue(object)) return true;
            return false;
        }

    }

    class Xor<T> implements ICondition<T> {

        private final ICondition<T> firstCondition, secondCondition;

        public Xor(ICondition<T> firstCondition, ICondition<T> secondCondition) {
            this.firstCondition = firstCondition;
            this.secondCondition = secondCondition;
        }

        @Override
        public boolean isTrue(T object) {
            return firstCondition.isTrue(object) != secondCondition.isTrue(object);
        }
    }

    class Equal<T> implements ICondition<T> {

        private final ICondition<T> firstCondition, secondCondition;

        public Equal(ICondition<T> firstCondition, ICondition<T> secondCondition) {
            this.firstCondition = firstCondition;
            this.secondCondition = secondCondition;
        }

        @Override
        public boolean isTrue(T aObject) {
            return firstCondition.isTrue(aObject) == secondCondition.isTrue(aObject);
        }

    }

}