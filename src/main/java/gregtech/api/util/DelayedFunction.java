package gregtech.api.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class DelayedFunction<T, R> {

    private T argument;
    private R computedResult;
    private Function<T, R> function;
    private boolean wasComputed = false;

    public DelayedFunction(T argument, Function<T, R> function) {
        this.argument = argument;
        this.function = function;
    }

    public DelayedFunction(Supplier<R> supplier) {
        this.argument = null;
        this.function = FPUtil.wrapAsFunction(supplier);
    }

    public void compute() {
        if(!wasComputed) {
            this.computedResult = function.apply(argument);
            this.wasComputed = true;
        }
    }

    public R get() {
        compute();
        return computedResult;
    }

}
