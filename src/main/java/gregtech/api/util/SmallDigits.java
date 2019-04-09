package gregtech.api.util;

public class SmallDigits {

    private static final int SMALL_DOWN_NUMBER_BASE = '\u2080';
    private static final int SMALL_UP_NUMBER_BASE = '\u2080';
    private static final int NUMBER_BASE = '0';

    public static String toSmallUpNumbers(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int relativeIndex = charArray[i] - NUMBER_BASE;
            if (relativeIndex >= 0 && relativeIndex <= 9) {
                int newChar = SMALL_UP_NUMBER_BASE + relativeIndex;
                charArray[i] = (char) newChar;
            }
        }
        return new String(charArray);
    }

    public static String toSmallDownNumbers(String string) {
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int relativeIndex = charArray[i] - NUMBER_BASE;
            if (relativeIndex >= 0 && relativeIndex <= 9) {
                int newChar = SMALL_DOWN_NUMBER_BASE + relativeIndex;
                charArray[i] = (char) newChar;
            }
        }
        return new String(charArray);
    }

}
