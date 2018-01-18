package gregtech.api.util;

public interface IWeighted {

    public int getWeight();

    public static class Wrapper<T> implements IWeighted {
        private final T object;
        private final int weight;

        public Wrapper(T object, int weight) {
            this.object = object;
            this.weight = weight;
        }

        public T getObject() {
            return object;
        }

        @Override
        public int getWeight() {
            return weight;
        }
    }
}
