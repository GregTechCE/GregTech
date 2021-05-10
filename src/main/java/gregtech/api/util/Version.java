package gregtech.api.util;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Version implements Comparable<Version> {

    private final int[] nums;

    public Version(int... nums) {
        if (nums.length == 0) {
            throw new IllegalArgumentException("Must be at least one version number!");
        }
        for (int num : nums) {
            if (num < 0) {
                throw new IllegalArgumentException("Version numbers must be positive!");
            }
        }
        this.nums = nums;
    }

    public static Version parse(String vStr) {
        return new Version(Arrays.stream(vStr.split(Pattern.quote("."))).mapToInt(Integer::parseInt).toArray());
    }

    public int getNumber(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be nonnegative!");
        }
        return index < nums.length ? nums[index] : 0;
    }

    @Override
    public int compareTo(Version o) {
        int numBound = Math.max(nums.length, o.nums.length);
        for (int i = 0; i < numBound; i++) {
            int cmp = Integer.compare(getNumber(i), o.getNumber(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Version && compareTo((Version) obj) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < nums.length; i++) {
            hash ^= Integer.rotateLeft(nums[i], i * 7);
        }
        return hash;
    }

    @Override
    public String toString() {
        return toString(nums.length);
    }

    public String toString(int sigPlaces) {
        return Arrays.stream(nums, 0, Math.min(sigPlaces, nums.length))
                .mapToObj(Integer::toString)
                .collect(Collectors.joining("."));
    }

}
