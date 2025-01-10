import java.security.SecureRandom;
import java.util.logging.Logger;

/**
 * Service class responsible for password generation logic.
 */
public class PasswordService {
    private static final Logger LOGGER = Logger.getLogger(PasswordService.class.getName());

    private static final String LOWERCASE_LATIN = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LATIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CYRILLIC = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String UPPERCASE_CYRILLIC = "АБВГДЕЖЗЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:,.<>?";

    public String generatePassword(PasswordConfig config) {
        StringBuilder characterPool = new StringBuilder();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        addCharacters(config, characterPool);

        if (!config.getDigits().isEmpty()) {
            for (String digit : config.getDigits().split(" ")) {
                password.append(digit.trim());
            }
        }

        for (int i = password.length(); i < config.getLength(); i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        return shuffleString(password.toString(), random);
    }

    private void addCharacters(PasswordConfig config, StringBuilder characterPool) {
        if (config.getLanguagesCount() == 1) {
            characterPool.append(LOWERCASE_LATIN);
            if (config.isUseDifferentCases()) characterPool.append(UPPERCASE_LATIN);
        } else {
            characterPool.append(LOWERCASE_CYRILLIC);
            if (config.isUseDifferentCases()) characterPool.append(UPPERCASE_CYRILLIC);
        }
        if (config.isUseSpecialCharacters()) {
            characterPool.append(SPECIAL_CHARACTERS);
        }
    }

    private String shuffleString(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}