package gregtech.api.multiblock;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;

import gregtech.api.multiblock.BlockPattern.RelativeDirection;
import static gregtech.api.multiblock.BlockPattern.RelativeDirection.*;

public class FactoryBlockPattern {

    private static final Joiner COMMA_JOIN = Joiner.on(",");
    private final List<String[]> depth = new ArrayList<>();
    private final List<int[]> aisleRepetitions = new ArrayList<>();
    private final Map<Character, Predicate<BlockWorldState>> symbolMap = new HashMap<>();
    private int aisleHeight;
    private int rowWidth;
    private RelativeDirection[] structureDir = new RelativeDirection[3];

    private FactoryBlockPattern(RelativeDirection charDir, RelativeDirection stringDir, RelativeDirection aisleDir) {
        structureDir[0] = charDir;
        structureDir[1] = stringDir;
        structureDir[2] = aisleDir;
        int flags = 0;
        for (int i = 0; i < 3; i++) {
            switch (structureDir[i]) {
                case UP:
                case DOWN: flags |= 0x1; break;
                case LEFT:
                case RIGHT: flags |= 0x2; break;
                case FRONT:
                case BACK: flags |= 0x4; break;
            }
        }
        if (flags != 0x7) throw new IllegalArgumentException("Must have 3 different axes!");
        this.symbolMap.put(' ', k -> true);
    }

    /**
     * Adds a repeatable aisle to this pattern.
     */
    public FactoryBlockPattern aisleRepeatable(int minRepeat, int maxRepeat, String... aisle) {
        if (!ArrayUtils.isEmpty(aisle) && !StringUtils.isEmpty(aisle[0])) {
            if (this.depth.isEmpty()) {
                this.aisleHeight = aisle.length;
                this.rowWidth = aisle[0].length();
            }

            if (aisle.length != this.aisleHeight) {
                throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight + ", but was given one with a height of " + aisle.length + ")");
            } else {
                for (String s : aisle) {
                    if (s.length() != this.rowWidth) {
                        throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.rowWidth + ", found one with " + s.length() + ")");
                    }

                    for (char c0 : s.toCharArray()) {
                        if (!this.symbolMap.containsKey(c0)) {
                            this.symbolMap.put(c0, null);
                        }
                    }
                }

                this.depth.add(aisle);
                if (minRepeat > maxRepeat) throw new IllegalArgumentException("Lower bound of repeat counting must smaller than upper bound!");
                aisleRepetitions.add(new int[]{minRepeat, maxRepeat});
                return this;
            }
        } else {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
    }

    /**
     * Adds a single aisle to this pattern. (so multiple calls to this will increase the aisleDir by 1)
     */
    public FactoryBlockPattern aisle(String... aisle) {
        return aisleRepeatable(1, 1, aisle);
    }

    /**
     * Set last aisle repeatable
     */
    public FactoryBlockPattern setRepeatable(int minRepeat, int maxRepeat) {
        if (minRepeat > maxRepeat) throw new IllegalArgumentException("Lower bound of repeat counting must smaller than upper bound!");
        aisleRepetitions.set(aisleRepetitions.size() - 1, new int[]{minRepeat, maxRepeat});
        return this;
    }

    /**
     * Set last aisle repeatable
     */
    public FactoryBlockPattern setRepeatable(int repeatCount) {
        return setRepeatable(repeatCount, repeatCount);
    }

    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern(RIGHT, UP, BACK);
    }

    public static FactoryBlockPattern start(RelativeDirection charDir, RelativeDirection stringDir, RelativeDirection aisleDir) {
        return new FactoryBlockPattern(charDir, stringDir, aisleDir);
    }

    public FactoryBlockPattern where(char symbol, Predicate<BlockWorldState> blockMatcher) {
        this.symbolMap.put(symbol, blockMatcher);
        return this;
    }

    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray(), structureDir, aisleRepetitions.toArray(new int[aisleRepetitions.size()][]));
    }

    @SuppressWarnings("unchecked")
    private Predicate<BlockWorldState>[][][] makePredicateArray() {
        this.checkMissingPredicates();
        Predicate<BlockWorldState>[][][] predicate = (Predicate<BlockWorldState>[][][]) Array.newInstance(Predicate.class, this.depth.size(), this.aisleHeight, this.rowWidth);

        for (int i = 0; i < this.depth.size(); ++i) {
            for (int j = 0; j < this.aisleHeight; ++j) {
                for (int k = 0; k < this.rowWidth; ++k) {
                    predicate[i][j][k] = this.symbolMap.get(this.depth.get(i)[j].charAt(k));
                }
            }
        }

        return predicate;
    }

    private void checkMissingPredicates() {
        List<Character> list = new ArrayList<>();

        for (Entry<Character, Predicate<BlockWorldState>> entry : this.symbolMap.entrySet()) {
            if (entry.getValue() == null) {
                list.add(entry.getKey());
            }
        }

        if (!list.isEmpty()) {
            throw new IllegalStateException("Predicates for character(s) " + COMMA_JOIN.join(list) + " are missing");
        }
    }
}