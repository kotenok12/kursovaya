/**
 * Configuration class to store password generation parameters.
 */
public class PasswordConfig {
    public static final int MAX_PASSWORD_LENGTH = 1_000_000;

    private final int length;
    private final int languagesCount;
    private final boolean useDifferentCases;
    private final boolean useSpecialCharacters;
    private final String digits;

    public PasswordConfig(int length, int languagesCount, boolean useDifferentCases, boolean useSpecialCharacters, String digits) {
        this.length = length;
        this.languagesCount = languagesCount;
        this.useDifferentCases = useDifferentCases;
        this.useSpecialCharacters = useSpecialCharacters;
        this.digits = digits;
    }

    public int getLength() {
        return length;
    }

    public int getLanguagesCount() {
        return languagesCount;
    }

    public boolean isUseDifferentCases() {
        return useDifferentCases;
    }

    public boolean isUseSpecialCharacters() {
        return useSpecialCharacters;
    }

    public String getDigits() {
        return digits;
    }
}