package gregtech.api.util;

import java.util.function.Function;

public class LambdaUtil {

    public interface UnsafeSupplier<T> {
        T get() throws Throwable;
    }

    public static <T> T catching(UnsafeSupplier<T> body, Function<Throwable, T> errorHandler) {
        try {
            return body.get();
        } catch (Throwable exception) {
            return errorHandler.apply(exception);
        }
    }

}
