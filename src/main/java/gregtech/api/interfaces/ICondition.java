package gregtech.api.interfaces;

public interface ICondition<O> {
    public boolean isTrue(O aObject);

    // Utility Classes for adding relations between Conditions.

    public static class Not<O> implements ICondition<O> {
        private final ICondition<O> mCondition;

        public Not(ICondition<O> aCondition) {
            mCondition = aCondition;
        }

        @Override
        public boolean isTrue(O aObject) {
            return !mCondition.isTrue(aObject);
        }
    }

    public static class Or<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        public Or(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (tCondition.isTrue(aObject)) return true;
            return false;
        }
    }

    public static class Nor<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        public Nor(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (tCondition.isTrue(aObject)) return false;
            return true;
        }
    }

    public static class And<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        public And(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (!tCondition.isTrue(aObject)) return false;
            return true;
        }
    }

    public static class Nand<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        public Nand(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (!tCondition.isTrue(aObject)) return true;
            return false;
        }
    }

    public static class Xor<O> implements ICondition<O> {
        private final ICondition<O> mCondition1, mCondition2;

        public Xor(ICondition<O> aCondition1, ICondition<O> aCondition2) {
            mCondition1 = aCondition1;
            mCondition2 = aCondition2;
        }

        @Override
        public boolean isTrue(O aObject) {
            return mCondition1.isTrue(aObject) != mCondition2.isTrue(aObject);
        }
    }

    public static class Equal<O> implements ICondition<O> {
        private final ICondition<O> mCondition1, mCondition2;

        public Equal(ICondition<O> aCondition1, ICondition<O> aCondition2) {
            mCondition1 = aCondition1;
            mCondition2 = aCondition2;
        }

        @Override
        public boolean isTrue(O aObject) {
            return mCondition1.isTrue(aObject) == mCondition2.isTrue(aObject);
        }
    }
}