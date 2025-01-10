import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main class to run the password generation process.
 */
public class PasswordGenerator {
    private static final Logger LOGGER = Logger.getLogger(PasswordGenerator.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Password generation started.");
        Scanner scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        // Сбор параметров для конфигурации
        PasswordConfig config = collectPasswordConfig(scanner);

        // Генерация пароля
        PasswordService passwordService = new PasswordService();
        String password = passwordService.generatePassword(config);

        System.out.println("Сгенерированный пароль: " + password);
        long endTime = System.currentTimeMillis();
        LOGGER.info("Password generation finished.");
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
    }

    /**
     * Method to collect password configuration from user input.
     */
    private static PasswordConfig collectPasswordConfig(Scanner scanner) {
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

        return new PasswordConfig(length, languagesCount, useDifferentCases, useSpecialCharacters, digits);
    }

    private static int getPasswordLength(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > PasswordConfig.MAX_PASSWORD_LENGTH) {
                    System.out.println("Введите число еще раз (до 1 млн): ");
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число (до 1 млн): ");
            }
        }
    }

    private static int getLanguagesCount(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > 3) {
                    System.out.println("Пожалуйста, введите число от 1 до 3.");
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите число от 1 до 3.");
            }
        }
    }

    private static boolean getBooleanInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if ("true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input)) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Введите 'true' или 'false'.");
            }
        }
    }

    private static String askForDigits(Scanner scanner) {
        System.out.print("Добавить определенные цифры? (1 - Да, 2 - Нет): ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.print("Введите цифры через пробел: ");
            scanner.nextLine();
            return scanner.nextLine();
        }
        return "";
    }
}