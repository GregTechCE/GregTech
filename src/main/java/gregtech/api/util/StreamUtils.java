package gregtech.api.util;

import net.minecraft.item.*;
import net.minecraftforge.items.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Various Quality-of-Life methods for working with Java 8 Streams.
 */
public class StreamUtils {
    /**
     * Create an inverse predicate from another predicate. Makes it less ugly to use method references.
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

    /**
     * Reduce multiple OR predicates into a single predicate.
     */
    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                     .reduce(x -> false, Predicate::or);
    }

    /**
     * Reduce multiple AND predicates into a single predicate.
     */
    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<T>... predicates) {
        return Arrays.stream(predicates)
                     .reduce(x -> true, Predicate::and);
    }

    /**
     * Creates a stream view of the actual contents of the inventory.
     * Caller must not modify the contents of the inventory returned by this stream.
     *
     * @param inventory the target inventory
     * @return a stream over the contents of the target inventory
     */
    public static Stream<ItemStack> streamFrom(IItemHandler inventory) {
        return StreamSupport.stream(iterableFrom(inventory).spliterator(),
                                    false);
    }

    /**
     * Simple Iterable supplier to permit iterating over an inventory using for-each semantics.
     *
     * @param inventory the target inventory
     * @return an Iterable over the slots of the specified inventory.
     * @throws UnsupportedOperationException if {@link Iterator#remove()} is called on the iterator returned by
     *                                       {@link Iterable#iterator()}
     * @see Iterator
     * @see Iterable
     */
    public static Iterable<ItemStack> iterableFrom(IItemHandler inventory) {
        return new Iterable<ItemStack>() {
            @Override
            public Iterator<ItemStack> iterator() {
                return new Iterator<ItemStack>() {
                    private int cursor = 0;

                    @Override
                    public boolean hasNext() {
                        return cursor < inventory.getSlots();
                    }

                    @Override
                    public ItemStack next() {
                        if(!hasNext())
                            throw new NoSuchElementException();

                        ItemStack next = inventory.getStackInSlot(cursor);
                        cursor++;
                        return next;
                    }
                };
            }

            @Override
            public Spliterator<ItemStack> spliterator() {
                return Spliterators.spliterator(iterator(), inventory.getSlots(), 0);
            }
        };
    }
}
