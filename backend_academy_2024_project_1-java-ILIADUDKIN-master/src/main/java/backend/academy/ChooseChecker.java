package backend.academy;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import lombok.experimental.UtilityClass;
import static backend.academy.ChooseWord.showCategory;
import static backend.academy.ExceptionFromUserMistake.THIRD_STATUS;
import static backend.academy.ExceptionFromUserMistake.setException;

@UtilityClass
public class ChooseChecker {
    final static String CONFIRM_SELECTION = "1 - Да, желаю\n";
    final static String UNCONFIRMED_SELECTION = "0 - Нет, не желаю";

    public static int isCategoryChosen(PrintStream output) {
        output.println(
            "Приветствуем вас на игре 'Виселица'." + "Желаете ли вы выбрать категорию?\n" + CONFIRM_SELECTION
                + UNCONFIRMED_SELECTION);
        Scanner in = new Scanner(System.in);
        return setException(output, 1, in);
    }

    public static int isHardLevelChosen(PrintStream output) {
        output.println("Желаете ли вы выбрать уровень сложности?\n" + CONFIRM_SELECTION + UNCONFIRMED_SELECTION);
        Scanner in = new Scanner(System.in);
        return setException(output, 1, in);
    }

    public static ChooseWord categoryChosen(ChooseWord newChooseWord, PrintStream output)
        throws InputMismatchException {
        showCategory(output);
        output.println("Введите номер категории: ");
        Scanner in = new Scanner(System.in);
        int userNum = setException(output, 2, in);
        for (ChooseWord.Category category : ChooseWord.Category.values()) {
            if (category.ordinal() == userNum) {
                output.println("Поздравляем! Ваша категория - " + category);
                newChooseWord.selectedCategory(category);
            }
        }
        return newChooseWord;
    }

    public static int hardLevelChosen(PrintStream output) throws InputMismatchException {
        output.println("Введите уровень сложности, где ");
        output.println("3 - легкий");
        output.println("2 - средний ");
        output.println("1 - сложный ");
        Scanner in = new Scanner(System.in);
        return setException(output, THIRD_STATUS, in);
    }
}
