import java.security.SecureRandom;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A password generator with specified parameters. It contains constant variables and other methods */
public class PasswordGenerator {
    private static final Logger LOGGER = Logger.getLogger(PasswordGenerator.class.getName());
    private static final String LOWERCASE_LATIN = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LATIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CYRILLIC = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String UPPERCASE_CYRILLIC = "АБВГДЕЖЗЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:,.<>?";
    private static final int MAX_PASSWORD_LENGTH = 1_000_000;

    /**
     * A method main to generate a password.
     *
     * @param args scanner reads information from each line entered
     */
    public static void main(String[] args) {
        LOGGER.info("Password generation started.");
        Scanner scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        LOGGER.info("Prompting user for password length.");
        System.out.print("Введите длину пароля (максимум 1 миллион): ");
        int length = getPasswordLength(scanner);

        LOGGER.info("Prompting user for number of languages.");
        System.out.print("Введите количество используемых языков (1 - латиница, 2 - кириллица, 3 - латиница и кириллица): ");
        int languagesCount = getLanguagesCount(scanner);

        LOGGER.info("Prompting user for case sensitivity.");
        System.out.print("Наличие разных регистров (true/false): ");
        boolean useDifferentCases = getBooleanInput(scanner);

        LOGGER.info("Prompting user for special characters inclusion.");
        System.out.print("Наличие специальных символов (true/false): ");
        boolean useSpecialCharacters = getBooleanInput(scanner);

        String digits = askForDigits(scanner);
        LOGGER.info("Generating password using the specified parameters.");

        String password = generatePassword(length, languagesCount, useDifferentCases, useSpecialCharacters, digits);
        System.out.println("Сгенерированный пароль: " + password);
        long endTime = System.currentTimeMillis();

        LOGGER.info("Password generation finished.");
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
    }

    /**
     * A method getPasswordLength receives the input password length no more than the specified limit in the length constant.
     *
     * @param scanner the scanner object to read user input
     * @return the password length
     */
    private static int getPasswordLength(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > MAX_PASSWORD_LENGTH) {
                    LOGGER.warning("Invalid password length input: " + input);
                    System.out.println("Введите число еще раз (до 1 млн): ");
                    continue;
                }
                LOGGER.info("Password length received: " + input);
                return input;
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid input for password length.");
                System.out.println("Некорректный ввод. Пожалуйста, введите число (до 1 млн): ");
            }
        }
    }

    /**
     * A method getLanguagesCount receives the number of languages to be used in the password.
     *
     * @param scanner the scanner object to read user input
     * @return the number of languages
     */
    private static int getLanguagesCount(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > 3) {
                    LOGGER.warning("Invalid input for languages count: " + input);
                    System.out.println("Пожалуйста, введите число от 1 до 3.");
                    continue;
                }
                LOGGER.info("Languages count received: " + input);
                return input;
            } catch (NumberFormatException e) {
                LOGGER.severe("Invalid input for languages count.");
                System.out.println("Некорректный ввод. Пожалуйста, введите число от 1 до 3.");
            }
        }
    }

    /**
     * A method getBooleanInput receives boolean input from the user.
     *
     * @param scanner the scanner object to read user input
     * @return the boolean input
     */
    private static boolean getBooleanInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if ("true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input)) {
                LOGGER.info("Boolean input received: " + input);
                return Boolean.parseBoolean(input);
            } else {
                LOGGER.warning("Invalid boolean input: " + input);
                System.out.println("Некорректный ввод. Введите 'true' или 'false'.");
            }
        }
    }

    /**
     * A method askForDigits asks the user if they want to include certain digits in the password.
     *
     * @param scanner the scanner object to read user input
     * @return the specified digits
     */
    private static String askForDigits(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Хотите ли добавить определенные цифры? Нажмите 1 для 'Да' или 2 для 'Нет': ");
            choice = scanner.nextInt();
            if (choice == 1 || choice == 2) {
                break;
            } else {
                LOGGER.warning("Invalid choice for digits input: " + choice);
                System.out.println("Некорректный ввод. Пожалуйста, выберите 1 или 2.");
            }
        }

        if (choice == 1) {
            System.out.print("Введите определенные цифры через пробел: ");
            scanner.nextLine();
            String digits = scanner.nextLine();
            LOGGER.info("Digits received: " + digits);
            return digits;
        } else {
            LOGGER.info("No digits will be added.");
            return "";
        }
    }

    /**
     * A method generatePassword generates a password according to the specified parameters.
     *
     * @param length            the length of the password
     * @param languagesCount    the number of languages to be used
     * @param useDifferentCases whether to use different cases or not
     * @param useSpecialCharacters whether to use special characters or not
     * @param digits            the specified digits to include in the password
     * @return the generated password
     */
    public static String generatePassword(int length, int languagesCount, boolean useDifferentCases, boolean useSpecialCharacters, String digits) {
        if (length > MAX_PASSWORD_LENGTH) {
            LOGGER.severe("Password length exceeds maximum limit: " + length);
            throw new IllegalArgumentException("Длина пароля не может превышать 1 миллион символов.");
        }

        LOGGER.info("Generating password with parameters - Length: " + length + ", Languages Count: " + languagesCount +
                ", Different Cases: " + useDifferentCases + ", Special Characters: " + useSpecialCharacters + ", Digits: " + digits);

        StringBuilder characterPool = new StringBuilder();

        if (languagesCount == 1) {
            // латиница
            characterPool.append(LOWERCASE_LATIN);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_LATIN);
            }
            if (useSpecialCharacters) {
                characterPool.append(SPECIAL_CHARACTERS);
            }
        } else if (languagesCount == 2) {
            // кириллица
            characterPool.append(LOWERCASE_CYRILLIC);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_CYRILLIC);
            }
            if (useSpecialCharacters) {
                characterPool.append(SPECIAL_CHARACTERS);
            }
        } else if (languagesCount == 3) {
            // кириллица+латиница+спецсимв
            characterPool.append(LOWERCASE_LATIN);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_LATIN);
            }
            characterPool.append(LOWERCASE_CYRILLIC);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_CYRILLIC);
            }
            if (useSpecialCharacters) {
                characterPool.append(SPECIAL_CHARACTERS);
            }
        }

        StringBuilder password = new StringBuilder();
        if (!digits.isEmpty()) {
            for (String digit : digits.split(" ")) {
                password.append(digit.trim());
            }
            length -= digits.split(" ").length;
        }
        if (characterPool.length() == 0 && length > 0) {
            LOGGER.severe("Character pool empty with remaining length: " + length);
            throw new IllegalArgumentException("Необходимо выбрать хотя бы один символ для генерации пароля.");
        }

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        String randomizedPassword = shuffleString(password.toString(), random);
        LOGGER.info("Generated password: " + randomizedPassword);

        return randomizedPassword;
    }

    /**
     * A method shuffleString shuffles the characters in a string.
     *
     * @param input  the input string
     * @param random the SecureRandom object for generating random numbers
     * @return the shuffled string
     */
    private static String shuffleString(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Меняем местами
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}