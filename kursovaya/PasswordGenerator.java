package generator
import java.security.SecureRandom;
import java.util.Scanner;

public class PasswordGenerator {
    private static final String LOWERCASE_LATIN = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LATIN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CYRILLIC = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private static final String UPPERCASE_CYRILLIC = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:,.<>?";
    private static final int MAX_PASSWORD_LENGTH = 1_000_000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();

        // Ввод длины пароля
        System.out.print("Введите длину пароля (максимум 1 миллион): ");
        int length = getPasswordLength(scanner);

        // Ввод количества языков
        System.out.print("Введите количество используемых языков (1 - латиница, 2 - латиница и кириллица, 3 - латиница, кириллица и цифры): ");
        int languagesCount = getLanguagesCount(scanner);

        // Ввод наличия разных регистров
        System.out.print("Наличие разных регистров (true/false): ");
        boolean useDifferentCases = getBooleanInput(scanner);

        // Ввод наличия специальных символов
        System.out.print("Наличие специальных символов (true/false): ");
        boolean useSpecialCharacters = getBooleanInput(scanner);

        // Ввод определенных цифр
        System.out.print("Введите определенные цифры (если не нужно, оставьте пустым): ");
        String digits = scanner.next();

        // Генерация пароля
        String password = generatePassword(length, languagesCount, useDifferentCases, useSpecialCharacters, digits);
        System.out.println("Сгенерированный пароль: " + password);

        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения: " + (endTime - startTime) + " миллисекунд");
    }

    private static int getPasswordLength(Scanner scanner) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input < 1 || input > MAX_PASSWORD_LENGTH) {
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
                System.out.println("Некорректный ввод. Пожалуйста, введите число от 1 до 3.");
            }
        }
    }

    private static boolean getBooleanInput(Scanner scanner) {
        while (true) {
            String input = scanner.next();
            if ("true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input)) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Некорректный ввод. Введите 'true' или 'false'.");
            }
        }
    }

    public static String generatePassword(int length, int languagesCount, boolean useDifferentCases, boolean useSpecialCharacters, String digits) {
        if (length > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Длина пароля не может превышать 1 миллион символов.");
        }

        StringBuilder characterPool = new StringBuilder();

        // Добавление букв в зависимости от выбранных языков
        if (languagesCount >= 1) {
            characterPool.append(LOWERCASE_LATIN);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_LATIN);
            }
        }
        if (languagesCount >= 2) {
            characterPool.append(LOWERCASE_CYRILLIC);
            if (useDifferentCases) {
                characterPool.append(UPPERCASE_CYRILLIC);
            }
        }
        if (languagesCount == 3) {
            // Для третьего языка просто добавим цифры
        }
        if (useSpecialCharacters) {
            characterPool.append(SPECIAL_CHARACTERS);
        }

        // Добавление выбранных цифр в пул символов, если они имеются
        StringBuilder password = new StringBuilder();

        // Проверка, что цифры не пустые
        if (!digits.isEmpty()) {
            // Добавляем выбранные цифры в начало пароля
            for (char digit : digits.toCharArray()) {
                password.append(digit);
            }
            // Уменьшаем длину пароля на количество добавленных цифр
            length -= digits.length();
        }

        // Проверка, что пул символов не пуст
        if (characterPool.length() == 0 && length > 0) {
            throw new IllegalArgumentException("Необходимо выбрать хотя бы один символ для генерации пароля.");
        }

        // Генерация оставшейся части пароля
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characterPool.length());
            password.append(characterPool.charAt(index));
        }

        // Перемешиваем пароль, чтобы цифры не были только в начале
        String randomizedPassword = shuffleString(password.toString(), random);

        return randomizedPassword;
    }

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