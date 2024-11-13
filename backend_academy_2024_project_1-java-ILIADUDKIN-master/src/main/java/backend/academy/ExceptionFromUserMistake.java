package backend.academy;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.experimental.UtilityClass;
import static backend.academy.ChooseWord.EASY_LEVEL;
import static backend.academy.ChooseWord.HARD_LEVEL;
import static backend.academy.ChooseWord.MEDIUM_LEVEL;

@UtilityClass
public class ExceptionFromUserMistake {
    final static int CAT0 = 0;
    final static int CAT1 = 1;
    final static int CAT2 = 2;
    final static int CAT3 = 3;
    final static int CAT4 = 4;
    final static int CAT5 = 5;
    final static int CAT6 = 6;
    final static int CAT7 = 7;
    final static int FIRST_STATUS = 1;
    final static int SECOND_STATUS = 2;
    final static int THIRD_STATUS = 3;
    final static String ERROR_MESSAGE = "Ошибка! Введите целое число.";

    public static boolean choiceOfOneZero(int choice, boolean validInput, PrintStream output) {
        return switch (choice) {
            case 0, 1 -> true; // Ввод корректен
            default -> {
                output.println("Ошибка! Введите 1 или 0.");
                yield false;
            }
        };
    }

    public static boolean choiceOfOneSeven(int choice, boolean validInput, PrintStream output) {
        return switch (choice) {
            case CAT0, CAT1, CAT2, CAT3, CAT4, CAT5, CAT6, CAT7 -> true; // Ввод корректен
            default -> {
                output.println("Ошибка! Введите число от 0 до 7.");
                yield false;
            }
        };
    }

    public static boolean choiceOfOneThree(int choice, boolean validInput, PrintStream output) {
        return switch (choice) {
            case HARD_LEVEL, MEDIUM_LEVEL, EASY_LEVEL -> true; // Ввод корректен
            default -> {
                output.println("Ошибка! Введите число от 1 до 3.");
                yield false;
            }
        };
    }

    public static boolean choiceStatus(int status, int choice, boolean validInput, PrintStream output) {
        return switch (status) {
            case FIRST_STATUS -> choiceOfOneZero(choice, validInput, output);
            case SECOND_STATUS -> choiceOfOneSeven(choice, validInput, output);
            case THIRD_STATUS -> choiceOfOneThree(choice, validInput, output);
            default -> throw new InputMismatchException("Неверный статус исключения.");
        };
    }

    // Метод, обрабатывающий 3 вида исключений
    public static int setException(PrintStream output, int status, Scanner in) {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) { // Цикл до получения корректного ввода
            try {
                choice = in.nextInt();
                validInput = choiceStatus(status, choice, validInput, output);

            } catch (InputMismatchException e) {
                output.println(ERROR_MESSAGE);
                in.next(); // Очистка буфера ввода
            }
        }
        return choice;
    }
}
