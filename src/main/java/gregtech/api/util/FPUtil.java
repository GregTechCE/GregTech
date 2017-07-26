package gregtech.api.util;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FPUtil {

    public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
        return Collector.of(ImmutableList::builder, ImmutableList.Builder::add, (b1, b2) -> { b1.addAll(b2.build()); return b1; }, ImmutableList.Builder::build);
    }

    public static <T, R> Function<ImmutableList<T>, ImmutableList<R>> wrapAsListRemapper(Function<T, R> remapper) {
        return in -> in.stream().map(remapper).collect(toImmutableList());
    }

    public static <T, R, N extends R> Function<T, N> wrapCasting(Function<T, R> toWrap, Class<N> cast) {
        return toWrap.andThen(cast::cast);
    }

    public static <T, R> Function<T, R> wrapAsFunction(Supplier<R> supplier) {
        return __ -> supplier.get();
    }

}
